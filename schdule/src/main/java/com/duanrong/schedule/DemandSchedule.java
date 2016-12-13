package com.duanrong.schedule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.Log;

import com.duanrong.business.demand.DemandtreasureConstants;
import com.duanrong.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.business.demand.model.AvailableMoneyRecord;
import com.duanrong.business.demand.model.DemandTransferFork;
import com.duanrong.business.demand.model.DemandTreasureBill;
import com.duanrong.business.demand.model.DemandTreasureInvest;
import com.duanrong.business.demand.model.DemandTreasureOpration;
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.business.demand.service.AvailableMoneyRecordService;
import com.duanrong.business.demand.service.DateRateService;
import com.duanrong.business.demand.service.DemandTreasureBillService;
import com.duanrong.business.demand.service.DemandTreasureInvestService;
import com.duanrong.business.demand.service.DemandTreasureLoanService;
import com.duanrong.business.demand.service.DemandtreasureTransferInService;
import com.duanrong.business.demand.service.DemandtreasureTransferOutService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.yeepay.constants.GeneralTransferConstants;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.IdUtil;
import com.duanrong.util.client.DRHTTPClient;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;

/**
 * @author xiao
 * @date 2015年7月20日上午11:35:45
 */
/*@Component*/
public class DemandSchedule {

	@Resource
	DemandTreasureBillService demandTreasureBillService;

	@Resource
	DateRateService dateRateService;

	@Resource
	DemandtreasureTransferInService demandtreasureTransferInService;

	@Resource
	DemandtreasureTransferOutService demandtreasureTransferOutService;

	@Resource
	DemandTreasureLoanService demandTreasureLoanService;

	@Resource
	DemandTreasureInvestService demandTreasureInvestService;

	@Resource
	DemandTreasureBillDao demandTreasureBillDao;

	@Resource
	DemandtreasureTransferOutDao demandtreasureTransferOutDao;
	
	@Resource
	private Log log;
	
	@Resource
	UserService userService;

	@Resource
	AvailableMoneyRecordService availableMoneyRecordService;

	private static final double FORK_MONEY = 500;

	private static final int FORK_LOAN_SIZE = 20;

	// 用于统计资产已分配金额
	private double forkMoney = 0;

	/**
	 * 活期宝每日利息结算轮询
	 * 
	 * 每日凌晨 2 点, 结算前一天利息 本金计算方式 （历史转入本金-历史转出本金-前一天 15 点以后转入的本金） 利率, 取前一天当日利率
	 * 
	 * @author xiao
	 * @date 2015年7月20日上午11:35:45
	 */
	// 凌晨2点执行一次
	/*@Scheduled(cron = "0 0 2 * * ?")*/
	public void dayDateSchedule() {
		System.out.println("###############活期宝每日利息结算轮询开始"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()) + "######################");
		List<String> listStr = demandTreasureBillService.getInterestToday();
		List<DemandTreasureBill> list = new ArrayList<DemandTreasureBill>();
		log.infoLog(
				"活期宝每日利息结算开始",
				"需计息人数："
						+ listStr.size()
						+ "*******"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
		String interestDate = getBeforeDay();
		int num = 0;
		double interest = 0.0;
		try {
			for (String str : listStr) {
				String[] arr = str.split(",");
				double money = Double.parseDouble(arr[1]);
				String userId = arr[0];
				if (money >= 0.01) {
					DemandTreasureBill bill = new DemandTreasureBill();
					bill.setId(IdGenerator.randomUUID());
					bill.setUserId(userId);
					bill.setMoney(money);
					bill.setCreateTime(new Date());
					bill.setType("interest");
					bill.setTranferWay("pc");
					bill.setStatus("success");
					bill.setDetail(interestDate + "利息");
					System.out.println(bill.getUserId() + "派息："
							+ bill.toString());
					log.infoLog(bill.getUserId() + "派息成功!", bill.toString());
					list.add(bill);
					num++;
					interest += money;
				}

			}
			demandTreasureBillService.insertInterestBatch(list);

			double totalMoney = ArithUtil.round(
					demandTreasureBillDao.getDemandTreasureMoneyByType("in")
							- demandTreasureBillDao
									.getDemandTreasureMoneyByType("out"), 2);

			String str = "天天赚派息时间：["
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date()) + "], 派息人数：[" + num
					+ "], 天天赚总金额：[￥" + ArithUtil.round(totalMoney, 2) + "], 派发总利息：[￥"
					+ ArithUtil.round(interest, 2) + "]";

			String url = "http://soa-log2.duanrong.com/basic/mail/send";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("subject", "天天赚每日派息提醒"));
			params.add(new BasicNameValuePair("content", str));
			params.add(new BasicNameValuePair("mailtos",
					"guolixiao@duanrong.com,zhangjunying@duanrong.com"));
			try {
				DRHTTPClient.sendHTTPRequestPost(url,
						new BasicHeader[] { new BasicHeader("sign",
								"b688513358a7da6a97b4069ec1d062ec") }, params);

			} catch (IOException e) {
				log.errLog("发送邮件异常", e);
			}

			log.infoLog(
					"活期宝每日利息结算结束",
					"派息人数："
							+ num
							+ "*******"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(new Date()));
		} catch (Exception e) {
			log.errLog("活期宝每日利息结算失败！！！", e.getMessage());
		}

	}

	/**
	 * 获得前一天的日期
	 * 
	 * @return
	 */
	public static String getBeforeDay() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 计算用户当日利息
	 * 
	 * @param userId
	 * @return
	 */
	public double getInterest(String userId) {
		return ArithUtil.round(
				demandTreasureBillService.getDemandTreasureValidMoney(userId)
						* (dateRateService.getRate(new Date()) / 365D), 2);
	}

	/**
	 * 转入确认轮询 3分钟一次
	 * 
	 * @throws InterruptedException
	 */
	/*@Scheduled(cron = "0 0/3 7-23 * * ?")*/
	public void ComfirmSchedule() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("##############确定转入轮询开始：" + sdf.format(new Date())
				+ "#####################");
		DemandtreasureTransferIn demandtreasureTransferIn = new DemandtreasureTransferIn();
		demandtreasureTransferIn
				.setStatus(DemandtreasureConstants.TransferInStatus.FREEZE);
		List<DemandtreasureTransferIn> list = demandtreasureTransferInService
				.findTran(demandtreasureTransferIn);
		log.infoLog("转入确认轮询", "轮询转入项目数：" + list.size());
		for (DemandtreasureTransferIn demandIn : list) {
			try {
				if (demandIn != null && demandIn.getStatus().equals("freeze")) {
					demandtreasureTransferInService.transferInConfirm(demandIn,
							GeneralTransferConstants.TransferStatus.CONFIRM);
				}
				log.infoLog("转入金额",
						demandIn.getId() + "," + demandIn.getMoney());
			} catch (Exception e) {
				log.errLog("转入确认轮询失败！！！", e.getMessage());
			}
		}
	}

	/**
	 * 天天赚转入资金匹配 3分钟一次
	 * 
	 * @throws InterruptedException
	 */
	/*@Scheduled(cron = "0 0/3 7-23 * * ?")*/
	public void transferInFrokSedule() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("############## 天天赚资产匹配轮询开始："
				+ sdf.format(new Date()) + "#####################");

		List<DemandtreasureTransferIn> transfers = demandtreasureTransferInService
				.getTransferInByFork();
		log.infoLog("天天赚资产匹配", "共轮询出[" + transfers.size() + "]条，待匹配的资产");
		for (DemandtreasureTransferIn transfer : transfers) {

			try {
				double money = ArithUtil.round(transfer.getMoney(), 2);
				log.infoLog("天天赚资产匹配", "userId:" + transfer.getUserId()
						+ ", transferInId: " + transfer.getId() + ", 转入 "
						+ money + " 元成功，开始进新房资产匹配");
				Map<String, DemandTransferFork> forks = new HashMap<>();
				List<DemandtreasureLoan> loans = new ArrayList<>();
				double balance = 0;
				if (money <= 0) {
					log.infoLog("天天赚资产匹配", "userId:" + transfer.getUserId()
							+ ", 转入金额为 " + money + " 元，无法匹配资产");
					continue;
				} else if (money > 0 && money < 10000) {
					loans = demandTreasureLoanService
							.getDemandTreasureLoanByStatus("ASC");
					// 转入金额小于 10000 则按控点金额 FORK_MONEY 分配
					balance = FORK_MONEY;
				} else if (money >= 10000) {
					loans = demandTreasureLoanService
							.getDemandTreasureLoanByStatus("DESC");
					// 转入金额大于等于 10000 则按控点项目数量 FORK_LOAN_SIZE 分配
					balance = FORK_LOAN_SIZE;
				}
				// 递归匹配资产，如果loans一遍没有将金额分配完，则进行资产递归分配，知道所有的资产都投满为止
				while (money > 0) {
					// 如果项目小于 10 个，则优先按项目个数分配金额
					if (loans.size() <= 10) {
						balance = transfer.getMoney() / loans.size();
					}
					if (balance < FORK_MONEY) {
						balance = FORK_MONEY;
					}
					money = transferInFrok(loans, money, balance,
							transfer.getId(), "", transfer.getUserId(), forks,
							"in");
				}
			} catch (Exception e) {

				log.errLog("天天赚资产匹配", "transferInId:" + transfer.getId()
						+ "执行续投异常," + e.getMessage());
			}
		}

		log.infoLog("天天赚资产匹配", "天天赚资产匹配结束");
		System.out.println("############## 天天赚资产匹配轮询结束："
				+ sdf.format(new Date()) + "#####################");

	}
	
	/**
	 * 续投/退出调度
	 */
	/*@Scheduled(cron = "0 39 12 * * ?")*/
	public void init() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("############## 天天赚项目到期续投/退出调度："
				+ sdf.format(new Date()) + "#####################");
			List<DemandTreasureInvest> invests = demandTreasureInvestService
					.getInvestByLoan();
			log.infoLog("天天赚续投资产匹配", "共轮询出[" + invests.size() + "]条，到期的资产");
			if (demandTreasureLoanService.getDemandTreasureLoanByStatus("ASC")
					.size() > 0) {
				for (DemandTreasureInvest invest : invests) {
					try {
						double money = ArithUtil.round(invest.getMoney(), 2);
						Map<String, DemandTransferFork> forks = new HashMap<>();
						List<DemandtreasureLoan> loans = new ArrayList<>();
						double balance = 0;
						if (money < 0) {
							log.infoLog("天天赚资产匹配", "investId:" + invest.getId()
									+ ", 到期金额为 " + money + " 元，无法匹配资产");
							continue;
						} else if (money > 0 && money < 10000) {
							loans = demandTreasureLoanService
									.getDemandTreasureLoanByStatus("ASC");
							// 转入金额小于 10000 则按控点金额 FORK_MONEY 分配
							balance = FORK_MONEY;
						} else if (money >= 10000) {
							loans = demandTreasureLoanService
									.getDemandTreasureLoanByStatus("DESC");
							// 转入金额大于等于 10000 则按控点项目数量 FORK_LOAN_SIZE 分配
							balance = FORK_LOAN_SIZE;
						}
						// 递归匹配资产，如果loans一遍没有将金额分配完，则进行资产递归分配，知道所有的资产都投满为止
						while (money > 0 && loans.size() > 0) {

							// 如果项目小于 10 个，则优先按项目个数分配金额
							if (loans.size() <= 10) {
								balance = invest.getMoney() / loans.size();
							}
							if (balance < FORK_MONEY) {
								balance = FORK_MONEY;
							}
							money = transferInFrok(loans, money, balance,
									invest.getDemandTransferId(),
									invest.getId(), invest.getUserId(), forks,
									"transfer");
						}
					} catch (Exception e) {
						log.errLog("天天赚续投异常", "investId:" + invest.getId()
								+ "执行续投异常," + e.getMessage());
					}

				}
		}

	}

	/**
	 * 续投/退出调度
	 */
	/*@Scheduled(cron = "0 0 3 * * ?")*/
	public void expireDemandLoanSedule() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("############## 天天赚项目到期续投/退出调度："
				+ sdf.format(new Date()) + "#####################");
		double expMoney = 0;
		//匹配金额
		forkMoney = 0;
		// 跟新天天赚信息，显示倒计时
		AvailableMoneyRecord record = availableMoneyRecordService.getEndLine();
		List<DemandTreasureInvest> invests = demandTreasureInvestService
				.getInvestByLoan();
		// 如果有可续投资产，进行续投
		if (record != null && record.getType().equals("expired")) {
			forkMoney = record.getMoney();
			log.infoLog("天天赚续投资产匹配", "共有：" + forkMoney + "元，共续投");		
			log.infoLog("天天赚续投资产匹配", "共轮询出[" + invests.size() + "]条，到期的资产");
			if (demandTreasureLoanService.getDemandTreasureLoanByStatus("ASC")
					.size() > 0) {
				for (DemandTreasureInvest invest : invests) {
					expMoney += invest.getMoney();
					try {
						double money = ArithUtil.round(invest.getMoney(), 2);
						Map<String, DemandTransferFork> forks = new HashMap<>();
						List<DemandtreasureLoan> loans = new ArrayList<>();
						double balance = 0;
						if (money < 0) {
							log.infoLog("天天赚资产匹配", "investId:" + invest.getId()
									+ ", 到期金额为 " + money + " 元，无法匹配资产");
							continue;
						} else if (money > 0 && money < 10000) {
							loans = demandTreasureLoanService
									.getDemandTreasureLoanByStatus("ASC");
							// 转入金额小于 10000 则按控点金额 FORK_MONEY 分配
							balance = FORK_MONEY;
						} else if (money >= 10000) {
							loans = demandTreasureLoanService
									.getDemandTreasureLoanByStatus("DESC");
							// 转入金额大于等于 10000 则按控点项目数量 FORK_LOAN_SIZE 分配
							balance = FORK_LOAN_SIZE;
						}
						// 递归匹配资产，如果loans一遍没有将金额分配完，则进行资产递归分配，知道所有的资产都投满为止
						while (money > 0 && loans.size() > 0) {

							// 如果项目小于 10 个，则优先按项目个数分配金额
							if (loans.size() <= 10) {
								balance = invest.getMoney() / loans.size();
							}
							if (balance < FORK_MONEY) {
								balance = FORK_MONEY;
							}
							money = transferInFrok(loans, money, balance,
									invest.getDemandTransferId(),
									invest.getId(), invest.getUserId(), forks,
									"transfer");
						}
					} catch (Exception e) {
						log.errLog("天天赚续投异常", "investId:" + invest.getId()
								+ "执行续投异常," + e.getMessage());
					}

				}
			}		
			// 将开放资产由续投改为开放
			if (record.getType().equals("expired")) {
				record.setType("add");
				record.setRealExpiredMoney(ArithUtil.round(record.getMoney()
						- forkMoney, 2));
				record.setMoney(ArithUtil.round(forkMoney, 2));
			}
			availableMoneyRecordService.update(record);	
		}
		StringBuffer str = new StringBuffer("[" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"]日<br>");	
		str.append("天天赚到期匹配记录： "+invests.size()+"个, 到期匹配总金额： " + expMoney + "元");
		str.append("天天赚开放总资产： ["+record.getMoney()+"]元, 续投金额： "+forkMoney+"元<br>");
		
		log.infoLog("天天赚续投资产匹配", "剩余：" + forkMoney + "元，续投");
		// 续投完成，剩余资金全部退出
		List<DemandTreasureInvest> invests2 = demandTreasureInvestService
				.getInvestMoneyByLoan();
		double eMoney = 0;
		for (DemandTreasureInvest invest : invests2) {
			eMoney += invest.getTotalMoney();
			/*demandTreasureInvestService.expired(invest.getTotalMoney(),
					invest.getUserId());*/
		}		
		str.append("天天赚应退出资产数： ["+invests2.size()+"]个, 应退出总金额： "+eMoney+"元<br>");
		
		// 批量跟新所有到期项目状态
		demandTreasureLoanService.finishLoan();	
		String host = userService.getConfigById("mail_smtp").getValue();
		String from = userService.getConfigById("mail_username").getValue();
		String password = userService.getConfigById("mail_password").getValue();
		MailSendInfo sendInfo = new MailSendInfo();
		sendInfo.setFromAddress(from);
		sendInfo.setMailServerHost(host);
		sendInfo.setPassword(password);
		sendInfo.setUserName(from);
		sendInfo.setContent(str.toString());
		sendInfo.setSubject("天天赚到期续投");
		SimpleEmailSend send = new SimpleEmailSend();
		try {
			sendInfo.setToAddress("guolixiao@duanrong.com");
			send.sendHtmlMail(sendInfo);
			sendInfo.setToAddress("zhangjunying@duanrong.com");
			send.sendHtmlMail(sendInfo);		
			sendInfo.setToAddress("zhouwen@duanrong.com");
			send.sendHtmlMail(sendInfo);		 
		} catch (MessagingException ex) {
			log.errLog("邮件发送失败", ex);
		}
		
		log.infoLog("天天赚续投资产匹配", "天天赚资产匹配结束");
		System.out.println("############## 天天赚资产匹配轮询结束："
				+ sdf.format(new Date()) + "#####################");
	}

	/**
	 * 转入/续投资产匹配
	 * 
	 * @param money
	 *            转入的金额
	 * @param balanceMoney
	 *            分配金额
	 * @param loans
	 *            资产队列
	 * @param transferId
	 * @return
	 */
	public double transferInFrok(List<DemandtreasureLoan> loans, double money,
			double balanceMoney, String transferId, String investId,
			String userId, Map<String, DemandTransferFork> forks, String type) {
		Iterator<DemandtreasureLoan> it = loans.iterator();
		while (it.hasNext()) {
			DemandtreasureLoan loan = it.next();
			double investMoney = 0;
			// 资产匹配
			if (money <= balanceMoney && money <= loan.getValidMoney()) {
				investMoney = money;
			} else if ((money <= balanceMoney && money > loan.getValidMoney())
					|| (money > balanceMoney && money > loan.getValidMoney() && loan
							.getValidMoney() < balanceMoney)) {
				investMoney = loan.getValidMoney();
			} else if ((money > balanceMoney && money <= loan.getValidMoney())
					|| (money > balanceMoney && money > loan.getValidMoney() && loan
							.getValidMoney() >= balanceMoney)) {
				investMoney = balanceMoney;
			}
			investMoney = ArithUtil.round(investMoney, 2);
			// 续投只匹配可用于续投的资产
			if (type.equals("transfer")) {

				if (forkMoney <= 0) {
					return 0;
				}

				if (investMoney > forkMoney) {
					investMoney = forkMoney;
				}
				forkMoney = ArithUtil.round(forkMoney - investMoney, 2);
			}

			// 保存项目的剩余可分配资产
			// 如果此项目以分配过资产，则更新匹配记录， 否则新建记录
			if (forks.containsKey(loan.getId())) {
				DemandTransferFork fork = forks.get(loan.getId());
				fork.setValidMoney(fork.getValidMoney() - investMoney);
				fork.getInvest().setMoney(
						fork.getInvest().getMoney() + investMoney);
				fork.getOpration().setMoney(
						fork.getOpration().getMoney() + investMoney);
				forks.put(loan.getId(), fork);
			} else {
				DemandTransferFork fork = new DemandTransferFork();
				fork.setValidMoney(loan.getValidMoney() - investMoney);
				// 生成资产匹配记录
				DemandTreasureInvest invest = new DemandTreasureInvest();
				String id = IdUtil.randomUUID();
				invest.setId(id);
				invest.setUserId(userId);
				invest.setDemandLoanId(loan.getId());
				invest.setDemandTransferId(transferId);
				invest.setMoney(investMoney);
				fork.setInvest(invest);
				// 生成资产匹配操作记录表
				DemandTreasureOpration opration = new DemandTreasureOpration();
				opration.setId(IdUtil.randomUUID());
				opration.setDemandLoanId(loan.getId());
				opration.setDemandTransferId(invest.getDemandTransferId());
				opration.setDemandTreasureInvestId(invest.getId());
				opration.setMoney(invest.getMoney());
				opration.setType(type);
				fork.setOpration(opration);
				forks.put(loan.getId(), fork);
			}
			// 更新项目可分配资产
			loan.setValidMoney(loan.getValidMoney() - investMoney);
			money = ArithUtil.round(money - investMoney, 2);
			// 如果项目的可分配金额已分配完，则剔除队列
			if (loan.getValidMoney() <= 0) {
				it.remove();
			}
			// 如果 转入金额已分配完，则执行分配操作，并退出
			if (money <= 0) {
				if (type.equals("in")) {
					demandTreasureInvestService.transferInFork(forks,
							transferId, type, money, userId);
				} else if (type.equals("transfer")) {
					// 执行续投
					demandTreasureInvestService.transferInFork(forks, investId,
							type, money, userId);
				}
				return 0;

				// 如果 剩余可分配金额大于 0， 并且没有可分配的项目了
			} else if (money > 0 && loans.size() <= 0) {

				// 续投操作，匹配完成的部分执行续投，超出的 金额部分 则执行强制退出，并退出循环
				// 其他操作，不做任何处理，退出循环，回滚一切匹配操作
				if (type.equals("transfer")) {
					// 执行续投/退出
					demandTreasureInvestService.transferInFork(forks, investId,
							type, money, userId);
				}
				return 0;
			}

		}
		return money;
	}

	/**
	 * 转让 1.从转入与项目匹配中间表中，按规则减去转让的钱 2.同时在项目表中增加可投放的转让钱数 3分钟一次
	 * 
	 * @throws InterruptedException
	 */
	/*@Scheduled(cron = "0 0/3 12,15 * * ?")*/
	public void transferOutFrokSedule() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("##############转让轮询开始：" + sdf.format(new Date())
				+ "#####################");

		List<DemandtreasureTransferOut> outList = demandtreasureTransferOutService
				.findAllSuccess();// 转让数目
		log.infoLog("转让轮询", "转让轮询目数：" + outList.size());
		for (DemandtreasureTransferOut outObj : outList) {// 遍历转让数据，进行操作
			try {
				String userid = outObj.getUserId();
				double principal = outObj.getPrincipal();// 转让的本金
				//只转出本金，则不进行资产匹配
				if(principal <= 0){
					DemandtreasureTransferOut outObj2 = outObj;
					outObj2.setFork(1);
					demandtreasureTransferOutDao.update(outObj2);
					continue ;
				}
						
				log.infoLog("转让轮询", "用户：" + userid + " 转让金额:" + principal);
				Map<String, String> paraMap = new HashMap<>();// 查询匹配中间表条件
				String sort = "";// 排序
				List<Map<String, Object>> resultList = new ArrayList<>();
				if (principal < DemandtreasureConstants.OUTMONEY_LEVEL) {// 小于
					sort = " asc";
				}
				if (principal >= DemandtreasureConstants.OUTMONEY_LEVEL) {// 大于
					sort = " desc";
				}
				paraMap.put("userId", userid);
				paraMap.put("sortValue", sort);
				List<DemandTreasureInvest> investList = demandTreasureInvestService
						.findAllOfUser(paraMap);
				List<DemandTreasureOpration> oprations = new ArrayList<>();
				for (DemandTreasureInvest investObj : investList) {
					if (investObj.getMoney() <= 0) {
						continue;
					}
					if (principal <= 0) {
						break;
					}
					Map<String, Object> resultMap = new HashMap<>();
					resultMap.put("id", investObj.getId());
					resultMap.put("demandLoanId", investObj.getDemandLoanId());
					DemandTreasureOpration opration = new DemandTreasureOpration();
					opration.setId(IdUtil.randomUUID());
					opration.setDemandLoanId(investObj.getDemandLoanId());
					opration.setDemandTransferId(investObj
							.getDemandTransferId());
					opration.setDemandTreasureInvestId(investObj.getId());
					opration.setType("out");
					opration.setRemark("天天赚转让资产匹配");
					if (principal > investObj.getMoney()) {
						principal -= investObj.getMoney();
						resultMap.put("money", investObj.getMoney());
						opration.setMoney(investObj.getMoney());
					} else {
						resultMap.put("money", principal);
						opration.setMoney(principal);
						principal = 0;
					}
					resultList.add(resultMap);
					oprations.add(opration);
				}
				outObj.setFork(1);// 已执行转让资产匹配标志
				demandTreasureInvestService.updateOutInvest(resultList, outObj,
						oprations);
			} catch (Exception e) {
				log.errLog("转让轮询失败！！！", e.getMessage());
			}
		}
	}
}