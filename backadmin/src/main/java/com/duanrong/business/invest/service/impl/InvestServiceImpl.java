package com.duanrong.business.invest.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyCollectionUtils;
import util.MyStringUtils;
import base.exception.ExceedMaxAcceptableRate;
import base.exception.ExceedMoneyNeedRaised;
import base.exception.InsufficientBalance;
import base.exception.InvestMoneyException;
import base.exception.InvestorsAndFinanciersIDException;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.autoinvest.model.AutoInvest;
import com.duanrong.business.autoinvest.service.AutoInvestService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestConfirmation;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.msg.model.Email;
import com.duanrong.business.msg.service.EmailService;
import com.duanrong.business.repay.model.repayMent;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.IdUtil;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.ToType;
import com.duanrong.util.UUIDGenerator;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:31:26
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.service.impl
 *              InvestServiceImpl.java
 * 
 */
@Service
public class InvestServiceImpl implements InvestService {

	final Lock lock = new ReentrantLock();

	@Resource
	InvestDao investDao;

	@Resource
	LoanDao loanDao;

	@Resource
	LoanService loanService;

	@Resource
	RepayService repayService;

	@Resource
	Log log;

	@Resource
	EmailService emailService;
	
	@Resource
	UserService userService;
	
	@Resource
	AutoInvestService autoInvestService;

	@Resource
	UserAccountService userAccountService;
	
	@Override
	public Double acquirePersonalRemainInvestMoney(String loanId, String userId) {
		Double personalInvestCeiling = this.readPersonalInvestCeiling(userId,
				loanId);
		Loan loan = loanDao.get(loanId);
		if (loan.getMaxInvestMoney() != null && loan.getMaxInvestMoney() > 0) {
			return loan.getMaxInvestMoney() - personalInvestCeiling;
		} else {
			return loanService.calculateMoneyNeedRaised(loanId);
		}

	}

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 */
	public PageData<Invest> readPaging(int pageNo, int pageSize, Invest invest) {
		return investDao.findPaging(pageNo, pageSize, invest);
	}

	public Double readInvestTotalMoney(String userId) {
		Double investTotalMoney = investDao.getInvestTotalMoney(userId);
		if (investTotalMoney == null) {
			return 0.00;
		}

		return ArithUtil.round(investTotalMoney.doubleValue(), 2);
	}

	public Double readInvestsTotalInterest(String userId) {
		Double InvestsTotalInterest = investDao.getInvestsTotalInterest(userId);
		if (InvestsTotalInterest == null) {
			return 0.00;
		}

		return ArithUtil.round(InvestsTotalInterest.doubleValue(), 2);
	}

	/**
	 * 
	 * @description 查询所有投资记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:05:44
	 * @return
	 */
	public List<Invest> readAll() {
		List<Invest> invests = investDao.findAll();
		return invests;
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录(该方法已经移动到LoanServiceImpl中)
	 * @author 孙铮
	 * @time 2014-9-1 下午12:02:28
	 * @param invest
	 * @return
	 */
	// public List<Invest> getInvestsByGroupCondition(Invest invest) {
	// List<Invest> invests = investDao.getInvestsByGroupCondition(invest);
	// return invests;
	// }

	/**
	 * 
	 * @description 根据id查询单条投资记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:07:41
	 * @param id
	 * @return
	 */
	public Invest read(String id) {
		Invest invest = investDao.get(id);
		return invest;
	}

	public void update(Invest invest) {
		investDao.update(invest);
	}

	public List<Invest> readInvestByLoan(String loanId) {
		return investDao.getInvestByLoan(loanId);
	}

	public void create(Invest invest) throws InsufficientBalance,
			ExceedMaxAcceptableRate, InvestMoneyException,
			InvestorsAndFinanciersIDException {

		String loanId = null;
		Loan loan = null;
		String userId = null;
		/*********************** 参数校验 ***********************/
		if (invest != null) {
			userId = invest.getInvestUserID();
			loanId = invest.getLoanId();
			Double money = invest.getMoney();

			// FIXME：需要判断的参数是哪些？
			if (MyStringUtils.isAnyBlank(userId, loanId)) {
				log.errLog(this.getClass().getName() + ".create(..)",
						"传入的某个参数为空");
				return;
			} else if (money == null || money <= 0) {
				log.errLog(this.getClass().getName() + ".create(..)",
						"传入的投标金额不正确");
				return;
			}

			loan = loanService.read(loanId);
			if (StringUtils.equals(loan.getBorrowMoneyUserID(), userId)) {
				log.errLog(this.getClass().getName() + ".create(..)",
						"投资人与融资人ID一致：" + userId);
				throw new InvestorsAndFinanciersIDException("投资人与融资人ID一致");
			}
			if(invest.getIsAutoInvest()){
				if (MyStringUtils.notEquals(loan.getStatus(), LoanConstants.LoanStatus.RAISING) 
						&& MyStringUtils.notEquals(loan.getStatus(), LoanConstants.LoanStatus.DQGS)) {
					log.errLog(this.getClass().getName() + ".create(..)",
							"自动投标项目状态不是筹款中或贷前公告，不能投资：" + userId);
					throw new InvestorsAndFinanciersIDException("自动投标项目状态不是筹款中或贷前公告，不能投资");
				}
			}else{
				if(MyStringUtils.notEquals(loan.getStatus(), LoanConstants.LoanStatus.RAISING)){
					log.errLog(this.getClass().getName() + ".create(..)",
							"项目状态不是筹款中，不能投资：" + userId);
					throw new InvestorsAndFinanciersIDException("项目状态不是筹款中，不能投资");
				}
				
			}			
		} else {
			log.errLog(this.getClass().getName() + ".create(..)", "传入的对象为空");
			return;
		}

		// 判断用户投资金额是否大于投资上限金额
		Double personalInvestCeiling = this.readPersonalInvestCeiling(
				invest.getInvestUserID(), loanId);
		if (loan.getMaxInvestMoney() != null
				&& loan.getMaxInvestMoney() != 0
				&& loan.getMaxInvestMoney() - personalInvestCeiling < invest
						.getMoney()) {
			throw new InvestMoneyException("投资金额大于个人投资上限金额，用户ID：" + userId);
		}

		invest.setLoan(loan);

		loanService.verifyInvestMoney(loanId, invest.getMoney());

		// 用户填写认购的钱数以后，判断余额，如果余额不够，不能提交，弹出新页面让用户充值
		UserAccount userAccount = userAccountService.readUserAccount(invest.getInvestUserID());
		double banlance = userAccount == null ? 0 : userAccount.getAvailableBalance();
		
		if (invest.getMoney() > banlance) {
			throw new InsufficientBalance();
		}
		invest.setRate(loan.getRate());
		invest.setStatus(InvestConstants.InvestStatus.WAIT_AFFIRM);
		// 设置投资类型
		/*if (StringUtils.isEmpty(invest.getType())) {
			invest.setType(InvestConstants.InvestType.NONE);
		}*/
		// 设置时间
		invest.setTime(new Date());
		// FIXME:设置是否自动投标
		if (invest.getIsAutoInvest() == null) {
			invest.setIsAutoInvest(false);
		}

		// 计算预期利息
		if (invest.getInterest() == null) {
			int periods = 0;
			if ("天".equals(loan.getOperationType())) {
				periods = loan.getDay();
			}else{
				periods = loan.getDeadline();
			}
			invest.setInterest(InterestUtil.getInterestByPeriod(
					invest.getMoney(), loan.getRate(), periods, loan.getOperationType(), loan.getRepayType()));
		}
		// 生成投资ID
		if (invest.getIsAutoInvest()) {
			invest.setId(IdUtil.generateId(ToType.ZDTB));
		} else {
			invest.setId(IdUtil.generateId(ToType.INVE));
		}
		
	}

	public Long readInvestCountByloanId(String loanId, String status) {
		return investDao.GetInvestCountByLoanId(loanId, status);

	}

	public PageData<Invest> readPagerInvestByLoanId(int pageIndex, int pageSize,
			String loanId, String status) {
		return investDao.GetPagerInvestByLoanId(pageIndex, pageSize, loanId,
				status);
	}

	public List<Invest> readInvestInterestDetail(String userId) {
		return investDao.getInvestInterestDetail(userId);
	}

	public List<Invest> readInvestLoan(Invest invest) {
		return investDao.getInvestLoan(invest);
	}

	public Double readInvestLoanTotalMoney(Invest invest) {
		return investDao.getInvestLoanTotalMoney(invest);
	}

	public Double readInvestLoanTotalInterest(Invest invest) {
		return investDao.getInvestLoanTotalInterest(invest);
	}

	public Double readInvestLoanTotalPaidInterest(Invest invest) {
		return investDao.getInvestLoanTotalPaidInterest(invest);
	}

	public Double readInvestMoney(Invest invest) {

		if (!StringUtils.equals(invest.getStatus(), "!流标")) {
			String[] conditions = StringUtils.split(invest.getStatus(), ",");
			invest.setConditions(conditions);
		}

		Double money = investDao.getInvestMoney(invest);
		if (money == null) {
			return 0.00;
		}

		return money;
	}

	@Override
	public Double readPersonalInvestCeiling(String userId, String loanId) {
		return investDao.getPersonalInvestCeiling(userId, loanId);
	}

	@Override
	public List<Invest> readInvestDynamic(Long recordNumber) {
		return investDao.getInvestDynamic(recordNumber);
	}

	@Override
	public Double readAvgRate(String userId) {
		Double avgRate = investDao.getAvgRate(userId);
		// 格式化
		return new BigDecimal(avgRate * 100).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	@Override
	public void syncInvest(Invest invest, String type)
			throws ExceedMoneyNeedRaised, InsufficientBalance {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
		lock.lock();
		try {

			if (StringUtils.equals(type, "S2SSuccess")) {
				// 必须保证invest表的投资状态改为投资成功
				invest.setStatus(InvestConstants.InvestStatus.BID_SUCCESS);
				update(invest);
				return;
			}

			Loan loan = invest.getLoan();
			String loanId = loan.getId();
			String loanStatus = loan.getStatus();
			String userId = invest.getInvestUserID();

			Double investMoney = invest.getMoney();
			if (StringUtils.equals(type, "S2SFail")) {
				invest.setStatus(InvestConstants.InvestStatus.CANCEL);
				investDao.update(invest);

				// 改项目状态，项目如果是等待复核的状态，改为筹款中
				if (StringUtils.equals(loanStatus,
						LoanConstants.LoanStatus.RECHECK)) {
					loan.setStatus(LoanConstants.LoanStatus.RAISING);
					loanService.update(loan);
				}

				// 解冻投资金额
				try {
					userAccountService.unfreeze(invest.getInvestUserID(),
							invest.getMoney(), BusinessEnum.bidders, "解冻：投资"
									+ invest.getLoan().getName(), "借款ID："
									+ invest.getLoan().getId(), invest.getId());
				} catch (Exception e) {
					log.errLog("解冻投资金额失败", e);
				}

				return;
			}

			if (StringUtils.equals(type, "create")) {
				// 判断项目尚未认购的金额，如果用户想认购的金额大于此金额，则。。。
				Double validInvestSumByLoan = investDao
						.getValidInvestSumByLoan(loan.getId());

				log.infoLog(
						"投资项目ID：" + loan.getId(),
						"用户id：" + userId + "，投资金额：" + investMoney + "，项目已投金额："
								+ validInvestSumByLoan + "，执行时间："
								+ sdf.format(new Date()));
				if (validInvestSumByLoan + investMoney > loan.getTotalmoney()) {
					log.errLog("投资金额大于项目剩余募集的金额", MyStringUtils.append(
							"用户ID为：", userId, "，金额为：", investMoney));
					throw new ExceedMoneyNeedRaised();
				} else {
					// 判断项目是否投满,投满则修改项目状态
					if (validInvestSumByLoan + investMoney == loan
							.getTotalmoney()) {
						loan.setStatus(LoanConstants.LoanStatus.RECHECK);
						loanService.update(loan);
						//如果是最后一笔投资，借款管理费
						invest.setManagementExpense(0);					
						if(loan.getLoanGuranteeFee() > 0){		
							double fee = ArithUtil.round(
									loan.getLoanGuranteeFee()-investDao.getInvestSeccessFeeByLoanId(loanId), 2);
							invest.setManagementExpense(fee <= 0 ? 0 : fee);
						}
					}
				}
				// 将金额冻结，借款项目执行时，把钱打给借款者
				try{
					userAccountService.freeze(userId, investMoney, BusinessEnum.invest, "冻结：投资" + loan.getName(), "借款ID:" + loan.getId()
							+ "  投资id:" + invest.getId(), invest.getId());
				}catch(Exception e){
					log.errLog("自动投标异常", e);
					e.addSuppressed(e);
				}	
				investDao.insert(invest);
			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Double readCurrentInvestMoney(String userId) {
		return investDao.getCurrentInvestMoney(userId);
	}

	// 今日投资收益=当前投资项目1投资额*年利率/365+当前投资项目2投资额*年利率/365+...+当前投资项目N投资额*年利率/365
	@Override
	public Double readTodayInvestmentIncome(String userId) {
		Invest invest = new Invest();
		invest.setInvestUserID(userId);
		invest.setStatus(InvestConstants.InvestStatus.REPAYING);
		List<Invest> invests = investDao.getInvestLoan(invest);
		if (MyCollectionUtils.isNotBlank(invests)) {
			Double investmentIncome = 0D;
			for (Invest invest2 : invests) {
				Double money = invest2.getMoney();
				Double rate = invest2.getLoan().getRate();
				investmentIncome += money * rate / 365;
			}
			return ArithUtil.round(investmentIncome, 2);
		} else {
			return 0D;
		}
	}

	@Override
	public Date readInvestDateLastOne(String loanId) {
		return investDao.getInvestDateLastOne(loanId);
	}

	@Override
	public PageInfo<Invest> readInvestByLoan(int pageNo, int pageSize,
			Map<String, Object> map) {
		return investDao.getInvestByLoan(pageNo, pageSize, map);
	}

	@Override
	public PageInfo<Invest> readInvestByUser(int pageNo, int pageSize,
			String userId) {

		return investDao.getInvestByUser(pageNo, pageSize, userId);
	}

	@Override
	public List<Invest> readInvestByLoan(Map<String, Object> map) {
		return investDao.getInvestByLoan(map);
	}

	@Override
	public String createInvestConfimcation(String loanId, String createUserId) {
		
		Loan loan = loanService.read(loanId);
		if(!"还款中".equals(loan.getStatus().trim())){
			return "该项目不是还款中,无法创建";
		}
		List<InvestConfirmation> investCons = investDao
				.getInvestConfirmation(loanId);
		if (investCons.isEmpty()) {
			return "该项目无人投资,无法创建";
		}
		List<Email> emails = emailService.readEmailByLoan(loanId);
		for (Email email : emails) {
			if (!"未发送".equals(email.getStatus())) {
				return "该项目的投资确认函已经发送过了, 无法创建";
			}
		}
		try {
			emailService.deleteEmailByLoan(loanId);
			for (InvestConfirmation investCon : investCons) {
				StringBuffer content = new StringBuffer();
				Template t1 = userService
						.readTemplateById("invest_confirmation");
				String title = t1 == null ? "" : t1.getContent().trim();
				title = title.replaceAll("loanname", investCon.getLoanName());
				title = title
						.replace("username", investCon.getInvestUserName());
				title = title.replace("itemnumber", investCon.getLoanId());
				
				String dealLine;
				
				if(loan.getOperationType().equals("月")){
					dealLine = loan.getDeadline()+"个月";
				}else{
					int day=loan.getDay();
					if(loan.getBeforeRepay().equals("是") && 
							loan.getRepayType().equals(LoanConstants.RepayType.RFCL)){						
						day+=loan.getSymbol();						
						
					}
					dealLine = day+"天";
					
				}
				title = title.replace("deadline", dealLine);
				title = title.replace("rate", "" + new DecimalFormat("##0.0").format(investCon.getRate() * 100));
				content.append(title);
				Template t2 = userService
						.readTemplateById("invest_confirmation_datas");
				if (t2 != null) {
					
					List<Invest> invests = readInvestByUserAndLoan(
							investCon.getInvestUserId(), loanId);
					for (Invest invest : invests) {
						String data = t2.getContent();
						data = data.replace("time", DateUtil.DateToString(
								invest.getTime(), "yyyy年MM月dd日"));
						data = data.replace("money", "" + invest.getMoney());
						data = data.replace("interest", "" + invest.getInterest());
						content.append(data);
					}
				}
				Template t3 = userService
						.readTemplateById("invest_confirmation_total");
				String total = t3 == null ? "" : t3.getContent().trim();
				// 计息日期处理:发送给用户的邮件要在放款日期的基础上+1天,并只保留年月日格式
				Date giveMoneyTime = investCon.getGiveMoneyTime();
				String formatGiveMoneyTime = DateUtil.DateToString(
						giveMoneyTime, "yyyy年MM月dd日");
				total = total.replace("givemoneytime", formatGiveMoneyTime);
				total = total.replace("totalinvestmoeny",
						"" + investCon.getTotalMoney());
				String totalInterest;
				if(loan.getOperationType().equals("月")){
					totalInterest = new DecimalFormat("##0.0").format((investCon.getTotalMoney()
						* investCon.getRate() / 12)
						* investCon.getDeadLine());
				}else{
					int day=loan.getDay();
					if(loan.getBeforeRepay().equals("是") && 
							loan.getRepayType().equals(LoanConstants.RepayType.RFCL)){						
						day+=loan.getSymbol();						
						
					}
					totalInterest = new DecimalFormat("##0.0").format(repayService.getRFCLInterestByPeriodDay(
							investCon.getTotalMoney(), loan.getRate(), day));
					
				}
				total = total.replace("totalinterest", totalInterest);
				
				content.append(total);
				Email email = new Email();
				email.setId(UUIDGenerator.randomUUID());
				email.setContent(content.toString());
				email.setLoanId(loanId);
				email.setTime(new Date());
				email.setTitle(investCon.getLoanName());
				email.setEmail(investCon.getEmail());
				email.setUserId(investCon.getInvestUserId());
				email.setCreateUserID(createUserId);
				emailService.insertEmial(email);
			}
			Loan loan1 = new Loan();
			loan1.setId(loanId);
			loan1.setEmailSend("已创建");
			loanService.update(loan1);
		} catch (Exception e) {
			e.printStackTrace();
			return "创建失败";
		}
		return "创建成功";
	}

	@Override
	public List<Invest> readInvestByUserAndLoan(String userId, String loanId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("loanId", loanId);
		return investDao.getInvestByUserAndLoan(map);
	}

	@Override
	public void updateInvestRecord4ConfigTable() {
		investDao.updateInvestRecord4ConfigTable(investDao
				.getSuccessInvestRecordNumber());
	}

	@Override
	public Invest readInvest(String id) {
		return investDao.getInvest(id);
	}

	@Override
	public Double readValidInvestSumByLoan(String loanID) {
		Double validInvestSumByLoan = investDao.getValidInvestSumByLoan(loanID);
		if (validInvestSumByLoan == null) {
			validInvestSumByLoan = 0D;
		}
		return validInvestSumByLoan;
	}

	@Override
	public User readInvestUserById(String userId) {		
		User user = userService.read(userId);
		AutoInvest autoInvest = autoInvestService.read(userId);
		if(autoInvest == null || autoInvest.getStatus().equals("off")){
			user.setAutoInvest(false);
		}else{
			user.setAutoInvest(true);
		}
		UserAccount userAccount = userAccountService.readUserAccount(userId);
		user.setBalance(userAccount.getBalance());
		user.setFrozenMoney(userAccount.getFreezeAmount());
		return user;
	}

	@Override
	public List<Invest> readInvestNum(Map map) {
		return investDao.getInvestNum(map);
	}

	@Override
	public int readAutoInvestByLoan(String loanId) {
		
		return investDao.getAutoInvestByLoan(loanId);
	}

	@Override
	public String sendInvestInfomation(String loanId, String userId) {
		String status = emailService.sendEmail(loanId, userId);
		Loan loan = new Loan();
		loan.setId(loanId);
		loan.setEmailSend("已发送");
		loanService.update(loan);
		return status;
	}

	@Override
	public PageInfo<Invest> readPageInfo(int pageNo, int pageSize, Map map) {
		return investDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public List<Invest> readInvests(Map<String, Object> map) {
		return investDao.getInvests(map);
	}

	@Override
	public Double readTotalMoney(Map map) {
			Double d=0d;
			d= investDao.getTotalMoney(map);
			if( d!=null) return d ; 
			else return (double) 0;
	}

	@Override
	public Long readInvestNums() {
		Long d=0l;
		d= investDao.getInvestNums();
		if( d!=null) return d ; 
		else return (long) 0;
	}

	@Override
	public List<Invest> readInvestByDate(Date date) {
		
		return investDao.getInvestByDate(date);
	}

	@Override
	public int readCountInvestByRedpacketForVisitor(String userId) {
		
		return investDao.getCountInvestByRedpacketForVisitor(userId);
	}

	@Override
	public Date readFirstAvilidInvestTimeByUserId(String userId) {
		
		return investDao.getFirstAvilidInvestTimeByUserId(userId);
	}

	@Override
	public double readAutoInvestMoneyByLoanId(String loanId) {
		
		return investDao.getAutoInvestMoneyByLoanId(loanId);
	}
	
	/**
	 * 投资记录（只显示天眼用户的投资记录）
	 */
	public PageInfo<Invest> readInvestRecordsNetLoanEye(int pageNo, int pageSize, Map<String, Object> map){
		return investDao.getInvestRecordsNetLoanEye(pageNo, pageSize, map);
	}

	/**
	 * 
	 */
	public BigDecimal readTotalMoneyForNetLoanEye(Map<String, Object> map) {
		return investDao.getTotalMoneyForNetLoanEye(map);
	}

	@Override
	public int readTotalNumForNetLoanEye(Map<String, Object> map) {
		return investDao.getTotalNumForNetLoanEye(map);
	}

	@Override
	public List<Invest> readExportInvestInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return investDao.getExportInvestInfo(map);
	}

	@Override
	public PageInfo<InvestRedpacket> readPageLiteInvestredpacket(int pageNo,
			int pageSize, InvestRedpacket investRedpacket) {
		return investDao.pageLiteInvestredpacket(pageNo, pageSize, investRedpacket);
	}

	@Override
	public List<Invest> readUserInvest(Map<String, Object> map) {
		return investDao.getUserInvest(map);
	}

	@Override
	public int readTotalPeople(Map<String, Object> map) {
		return investDao.getTotalPeople(map);
	}

	@Override
	public BigDecimal readFirstInvestMoney(Map<String, Object> map) {
		return investDao.getFirstInvestMoney(map);
	}

	@Override
	public int readFirstInvestPeople(Map<String, Object> map) {
		return investDao.getFirstInvestPeople(map);
	}

	@Override
	public int readCheckFirstInvest(String userId, Date time) {
		return investDao.getCheckFirstInvest(userId,time);
	}

	@Override
	public PageInfo<repayMent> readRepayMentRecords(int pageNo, int pageSize,
			Map<String, Object> map,String end) {
		return investDao.getRepayMentRecords(pageNo,pageSize,map,end);
	}

	@Override
	public int readInvestCountByUserId(String userId) {
		return investDao.getInvestCountByUserId(userId);
	}

	@Override
	public double readRedpacketUableMoeny(String userId) {
		return investDao.getRedpacketUableMoeny(userId);
	}

	@Override
	public double readRedpacketUableRate(String userId) {
		return investDao.getRedpacketUableRate(userId);
	}

	@Override
	public List<repayMent> readRepayMentRecordsList(Map<String, Object> map) {
		return investDao.getRepayMentRecordsList(map);
	}

	
	
}
