package com.duanrong.business.award.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.Log;
import util.MyBeanUtils;
import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.award.dao.AwardItemDao;
import com.duanrong.business.award.dao.AwardItemUserDao;
import com.duanrong.business.award.model.AwardItem;
import com.duanrong.business.award.model.AwardItemUser;
import com.duanrong.business.award.model.ItemType;
import com.duanrong.business.award.model.ResponseUser;
import com.duanrong.business.award.model.ResposeParam;
import com.duanrong.business.award.model.WeiXinActivity;
import com.duanrong.business.award.service.AwardItemService;
import com.duanrong.business.flow.model.ItemFlow;
import com.duanrong.business.flow.service.FlowService;
import com.duanrong.business.flow.service.ItemFlowService;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.User;
import com.duanrong.newadmin.constants.AwardConstants;
import com.duanrong.newadmin.constants.LoanConstants;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.util.ArithUtil;
import com.duanrong.yeepay.service.TrusteeshipPlatformTransferService;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-2-10 下午3:50:35
 * @Description : NewAdmin com.duanrong.business.award.service.impl
 *              AwardItemServiceImpl.java
 * 
 */
@Service
public class AwardItemServiceImpl implements AwardItemService {

	private final Lock lock = new ReentrantLock();

	@Autowired
	AwardItemDao awardItemDao;

	@Autowired
	private OperaRecordService operaRecordService;

	@Autowired
	UserDao userDao;

	@Autowired
	RepayService repayService;

	@Autowired
	LoanDao loanDao;

	@Autowired
	InvestDao investDao;

	@Autowired
	Log log;

	@Autowired
	AwardItemUserDao awardItemUserDao;

	@Autowired
	ItemFlowService itemFlowService;

	@Autowired
	FlowService flowService;

	@Autowired
	TrusteeshipPlatformTransferService trusteeshipPlatformTransferService;

	@Autowired
	SmsService smsService;

	@Autowired
	LoanService loanService;

	private boolean addAwardItem(AwardItem awardItem) {

		if (awardItem.getUserCount() > 0 && awardItem.getMoneyAmount() > 0) {
			int flag = awardItemDao.insertAwardItem(awardItem);
			if (flag >= 0 && awardItem.getId() >= 0) {
				return addAwrdItemUser(awardItem.getAwardItemUsers(),
						awardItem.getId())
						&& itemFlowService.issueItem(awardItem.getFlowId(),
								awardItem.getId(), ItemType.AWARD);
			}
		}
		return false;
	}

	private boolean addAwrdItemUser(List<AwardItemUser> awardItemUsers,
			int itemId) {
		int flag = 0;
		if (!awardItemUsers.isEmpty()) {
			for (AwardItemUser awardItemUser : awardItemUsers) {
				awardItemUser.setAwardItemId(itemId);
				flag += awardItemUserDao.insertAwardItemUser(awardItemUser);
			}
		}
		log.infoLog("AwardItemServiceImpl.class addAwrdItemUser", "共插入： "
				+ flag + " 条奖励明细");
		return flag > 0;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String saveAwardItem(String loginUser, HttpServletRequest request) {
		AwardItem awardItem = new AwardItem();
		String verifyParam = verifyParam(request, awardItem);
		if ("pass".equals(verifyParam)) {
			awardItem.setCreateUserID(loginUser);
			awardItem.setCreateTime(new Date());
			// 创建一个奖励明细集合
			int countValue = 0;
			if ("wu".equals(request.getParameter("radio"))) {// 没关系
				String groupCountIpt = request.getParameter("groupCountIpt");// 获取用户填写的最大组数
				countValue = Integer.parseInt(groupCountIpt);
			} else {// 有关系
				if ("percentage".equals(awardItem.getMoneyType())
						&& !StringUtils.isBlank(awardItem.getLoanId())) {// 保存百分比奖励并且有loanId时填写的奖励百分比
					String moneyTypeInput = request
							.getParameter("moneyTypeInput");
					boolean number = NumberUtils.isNumber(moneyTypeInput);
					if (number) {
						awardItem.setPercentageRate(Double
								.parseDouble(moneyTypeInput));
					}
				}
				// 判断奖励是否重复创建
				if (!StringUtils.isBlank(awardItem.getLoanId())) {
					awardItem.setStatus("sendAndCreate");
					List<AwardItem> sendAward = awardItemDao
							.isSendAward(awardItem);
					if (sendAward != null && sendAward.size() > 0) {
						return "exist";
					}
				}
				String c = request.getParameter("count");
				countValue = Integer.parseInt(c);
				if ("weixin".equals(awardItem.getItemType())) {
					WeiXinActivity weiXinActivity = new WeiXinActivity();
					weiXinActivity.setStatuss("已创建");
					weiXinActivity.setLoanId(awardItem.getLoanId());
					awardItemDao.updateWeiXinActivity(weiXinActivity);
				}
			}
			awardItem.setStatus(AwardConstants.AwardItemStatus.WAIT_SENDED);
			return saveAwardItem(countValue, request, awardItem);
		} else {
			return verifyParam;
		}
	}

	private String saveAwardItem(int count, HttpServletRequest request,
			AwardItem awardItem) {
		int userConut = 0;
		double moneyAmount = 0;
		List<AwardItemUser> awardItemUsers = new ArrayList<AwardItemUser>();
		String radio = request.getParameter("radio");
		List<String> list = new ArrayList<String>(awardItem.getUserCount());
		for (int i = 0; i < count; i++) {
			String userMobileNum = request.getParameter("userMobileNum" + i);
			if (StringUtils.isBlank(userMobileNum)) {
				continue;
			}
			User user = userDao.getUserByMobileNumber(userMobileNum);
			if (user == null) {
				continue;
			}
			if ("wu".equals(radio)) {
				if (list.contains(user.getMobileNumber())) {
					continue;
				}
			}
			String money = request.getParameter("money" + i);
			money = money.trim();
			if (StringUtils.isBlank(money) || !NumberUtils.isNumber(money)) {
				continue;
			}
			int lastIndexOf = money.lastIndexOf(".");
			if (lastIndexOf > -1) {
				int length = money.substring(lastIndexOf + 1).length();
				if (length > 2) {
					continue;
				}
			}
			double parseMoney = Double.parseDouble(money);
			if (parseMoney <= 0) {
				continue;
			}
			// 每循环一个用户就创建一个awardItemUser对象,并设置属性
			AwardItemUser awardItemUser = new AwardItemUser();
			awardItemUser.setUserId(user.getUserId());
			awardItemUser
					.setStatus(AwardConstants.AwardItemUserStatus.UNSENDED);
			awardItemUser.setCreateTime(new Date());
			awardItemUser.setMoney(parseMoney);
			// 将awardItemUser对象放入集合
			awardItemUsers.add(awardItemUser);
			list.add(user.getMobileNumber());
			++userConut;
			moneyAmount += parseMoney;
		}
		// 判断总人数和总钱数是否大于0 true设置值并保存,false返回前台错误提示
		if (userConut > 0 && moneyAmount > 0 && awardItemUsers.size() > 0) {
			awardItem.setAwardItemUsers(awardItemUsers);
			awardItem.setMoneyAmount(ArithUtil.round(moneyAmount, 2));
			awardItem.setUserCount(userConut);
			// 将奖励明细集合设置到awardItem对象中
			if (addAwardItem(awardItem)) {
				operaRecordService.insertRecord("保存奖励项目",
						awardItem.getCreateUserID(), "奖励项目保存成功,奖励项目id:"
								+ awardItem.getId());
				return "ok";
			}
		} else {
			return "UCMA";
		}
		return null;
	}

	/**
	 * 
	 * @description 验证页面提交参数是否正确
	 * @author 孙铮
	 * @time 2015-2-10 下午3:31:07
	 * @param request
	 * @param awardItem
	 * @return
	 */
	private String verifyParam(HttpServletRequest request, AwardItem awardItem) {
		// 奖励项目:名称
		String name = request.getParameter("name");
		// 奖励项目:描述
		String description = request.getParameter("description");
		// 奖励项目:消息
		String messageContent = request.getParameter("messageContent");
		// 奖励项目:金额类型
		String moneyType = request.getParameter("moneyType1");
		// 奖励项目:项目类型
		String itemType = request.getParameter("itemType");
		// 奖励项目是否与借款项目有关
		String radio = request.getParameter("radio");
		// 奖励项目流程
		String itemFlow = request.getParameter("itemFlow");

		StringBuffer sb = new StringBuffer();

		if (StringUtils.isBlank(name)) {
			sb.append("N");
		}
		if ("wu".equals(radio)) {
			String groupCountIpt = request.getParameter("groupCountIpt");
			if (StringUtils.isBlank(groupCountIpt)
					|| !groupCountIpt.matches("[0-9]+")) {
				if (sb.length() > 0) {
					sb.append(",G");
				} else {
					sb.append("G");
				}
			} else {
				awardItem.setUserCount(Integer.parseInt(groupCountIpt));
			}
		} else {
			String count = request.getParameter("count");
			if (StringUtils.isBlank(count) || !count.matches("[0-9]+")) {
				if (sb.length() > 0) {
					sb.append(",G");
				} else {
					sb.append("G");
				}
			}
			String loanId = request.getParameter("loanId");
			if (StringUtils.isBlank(loanId)) {
				if (sb.length() > 0) {
					sb.append(",loanId");
				} else {
					sb.append("loanId");
				}
			} else {
				Loan loan = loanService.get(loanId);
				if (loan == null) {
					if (sb.length() > 0) {
						sb.append(",loanId");
					} else {
						sb.append("loanId");
					}
				} else {
					awardItem.setLoanId(loanId);
				}

			}
		}
		if (StringUtils.isBlank(description)) {
			if (sb.length() > 0) {
				sb.append(",D");
			} else {
				sb.append("D");
			}
		}
		if (StringUtils.isBlank(messageContent)) {
			if (sb.length() > 0) {
				sb.append(",MC");
			} else {
				sb.append("MC");
			}
		}

		if (!StringUtils.isNumeric(itemFlow)) {
			if (sb.length() > 0) {
				sb.append(",IF");
			} else {
				sb.append("IF");
			}
		}
		if (sb.length() > 0) {
			return sb.toString();
		} else {
			awardItem.setDescription(description);
			awardItem.setName(name);
			awardItem.setItemType(itemType);
			awardItem.setMoneyType(moneyType);
			awardItem.setUserMsg(messageContent);
			awardItem.setFlowId(Integer.parseInt(itemFlow));
			return "pass";
		}
	}

	@Override
	public String queryUser(HttpServletRequest request) {
		String mn = request.getParameter("mn");
		// 判断手机好是否符合要求
		User user;
		if (NumberUtils.isNumber(mn) && mn.length() == 11) {
			user = userDao.getUserByMobileNumber(mn);
		} else {
			user = userDao.get(mn);			
		}
		if (user == null) {
			return "fail";
		} else {
			return "帐号: " + user.getUserId() + "姓名:"
						+ user.getRealname();			
		}
	}

	
	/**
	 * 
	 * @description itemType为weixin
	 * @author 孙铮
	 * @time 2015-3-9 下午2:00:25
	 * @param list
	 * @param resposeParam
	 * @param loanId
	 * @return
	 */
	private ResposeParam itemTypeByWeiXin(List<ResponseUser> list,
			ResposeParam resposeParam, String loanId) {
		WeiXinActivity weiXinActivity = new WeiXinActivity();
		weiXinActivity.setLoanId(loanId);
		weiXinActivity.setStatuss("等待创建");
		List<WeiXinActivity> weiXinActivityByloanId = awardItemDao
				.getWeiXinActivity(weiXinActivity);

		for (WeiXinActivity weixin : weiXinActivityByloanId) {
			User user = userDao.get(weixin.getUserId());
			ResponseUser responseUser = new ResponseUser(weixin.getUserId(),
					weixin.getMobileNumber(), null, user.getRealname(), null);
			responseUser.setAwardMoney(ArithUtil.round(weixin.getWeixinMoney(),
					2));
			list.add(responseUser);
		}
		resposeParam.setList(list);
		resposeParam.setInvestCount(list.size());
		return resposeParam;
	}

	/**
	 * 
	 * @description itemType为compensation
	 * @author 孙铮
	 * @time 2015-3-9 下午2:02:45
	 * @param list
	 * @param resposeParam
	 * @param loan
	 * @return
	 */
	private ResposeParam itemTypeByCompensation(List<ResponseUser> list,
			ResposeParam resposeParam, Loan loan) {
		// if ("还款中".equals(loan.getStatus())) {
		Invest in = new Invest();
		in.setLoanId(loan.getId());
		List<Invest> invests = investDao.getInvestByLoanSortAsc(in);// 得到所有有效投资人
		Date giveMoneyTime = loan.getGiveMoneyTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Invest invest : invests) {
			int dayDifference = DateUtil.dayDifference(
					sdf.format(invest.getTime()), sdf.format(giveMoneyTime));

			Date d = DateUtil.StringToDate("2015-04-23 00:00:00");

			if (giveMoneyTime.getTime() <= d.getTime()) {
				dayDifference = dayDifference - 3;
			} else {
				dayDifference = dayDifference - 1;
			}
			if (dayDifference > 0) {
				double awardMoney = dayDifference * loan.getRate() / 365
						* invest.getMoney();
				User user = userDao.get(invest.getInvestUserID());
				ResponseUser responseUser = new ResponseUser(user.getUserId(),
						user.getMobileNumber(), invest.getMoney(),
						user.getRealname(), invest.getReturnPondMoney());
				responseUser.setInvestTime(sdf.format(invest.getTime()));
				responseUser.setAwardMoney(ArithUtil.round(awardMoney, 2));
				list.add(responseUser);
			}
		}
		resposeParam.setInvestCount(list.size());
		resposeParam.setList(list);
		// } else {
		// resposeParam.setStatus("fail");
		// resposeParam.setError("投资补偿的借款项目状态不正确");
		// }
		return resposeParam;
	}

	/**
	 * 
	 * @description 普通的需要查询loan下的投资(合并)
	 * @author 孙铮
	 * @time 2015-3-9 下午3:38:07
	 * @param list
	 * @param resposeParam
	 * @param loan
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private ResposeParam yMerge(List<ResponseUser> list,
			ResposeParam resposeParam, Loan loan) {
		Invest invest = new Invest();
		invest.setLoanId(loan.getId());
		invest.setStatus("!流标");
		List<Invest> invests = loanDao.getInvestsByGroupCondition(invest);// 查询所有投资人
		Map<String, ResponseUser> irs = new HashMap<String, ResponseUser>();// 创建一个map用来合并重复投资人数据
		for (int i = 0; i < invests.size(); i++) {
			Invest in = invests.get(i);
			String investUserID = in.getInvestUserID();
			if (irs.containsKey(investUserID)) {
				ResponseUser responseUser = irs.get(investUserID);
				Double money = responseUser.getTotalInvestMoney();
				Double followInvestTotalMoney = responseUser
						.getFollowInvestTotalMoney();
				money += in.getMoney();
				money = ArithUtil.round(money, 2);
				followInvestTotalMoney += in.getReturnPondMoney();
				followInvestTotalMoney = ArithUtil.round(
						followInvestTotalMoney, 2);
				responseUser.setTotalInvestMoney(money);
				responseUser.setFollowInvestTotalMoney(followInvestTotalMoney);
				irs.put(investUserID, responseUser);
			} else {
				User user = userDao.get(in.getInvestUserID());
				ResponseUser responseUser = new ResponseUser(user.getUserId(),
						user.getMobileNumber(), in.getMoney(),
						user.getRealname(), in.getReturnPondMoney());
				irs.put(investUserID, responseUser);
			}
		}
		Set<String> key = irs.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			ResponseUser responseUser = irs.get(s);
			list.add(responseUser);
		}
		resposeParam.setInvestCount(list.size());
		resposeParam.setList(list);
		return resposeParam;
	}

	/**
	 * 
	 * @description 普通的需要查询loan下的投资(不合并)
	 * @author 孙铮
	 * @time 2015-3-9 下午3:39:18
	 * @param list
	 * @param resposeParam
	 * @param loan
	 * @return
	 */
	private ResposeParam noMerge(List<ResponseUser> list,
			ResposeParam resposeParam, Loan loan) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Invest in = new Invest();
		in.setLoanId(loan.getId());
		List<Invest> investByLoan = investDao.getInvestByLoanSortAsc(in);// 得到所有有效投资人
		for (Invest invest : investByLoan) {
			User user = userDao.get(invest.getInvestUserID());
			ResponseUser responseUser = new ResponseUser(user.getUserId(),
					user.getMobileNumber(), invest.getMoney(),
					user.getRealname(), invest.getReturnPondMoney());
			responseUser.setInvestTime(sdf.format(invest.getTime()));
			list.add(responseUser);
		}
		resposeParam.setInvestCount(list.size());
		resposeParam.setList(list);
		return resposeParam;
	}

	@Override
	public Object queryLoan(HttpServletRequest request) {
		String loanId = request.getParameter("loanId");
		String merge = request.getParameter("merge");
		String itemType = request.getParameter("itemType");
		String ladder = request.getParameter("ladder");
		ResposeParam resposeParam = new ResposeParam();
		List<ResponseUser> list = new ArrayList<ResponseUser>();
		if (StringUtils.isBlank(loanId)) {// 借款项目id为空直接返回
			resposeParam.setStatus("fail");
			resposeParam.setError("loanIdNULL");
			return resposeParam;
		} else {
			Loan loan = loanDao.get(loanId);
			if (loan == null) {
				resposeParam.setStatus("fail");
				resposeParam.setError("loanNULL");
				return resposeParam;
			} else {// 结果排序
				if ("weixin".equals(itemType)) {
					resposeParam = itemTypeByWeiXin(list, resposeParam, loanId);
				} else if ("compensation".equals(itemType)) {
					resposeParam = itemTypeByCompensation(list, resposeParam,
							loan);
				} else if ("newbieEnjoy".equals(itemType)) {
					resposeParam = itemTypeByNewbieEnjoy(list, resposeParam,
							loan);
				} else if ("51AddInterest".equals(itemType)) {
					resposeParam = itemTypeBy51AddInterest(list, resposeParam,
							loan);
				} else if ("ladder".equals(ladder)) {
					String status = "'等待发送','等待复核','复核通过','已发送'";
					AwardItem createFollowInvestAwardItem = isCreateFollowInvestAwardItem(
							loan.getId(), status);
					if (createFollowInvestAwardItem != null) {
						resposeParam.setStatus("fail");
						resposeParam.setError("followexist");
						return resposeParam;
					}
					List<Repay> repays = repayService.getRepayByLoan(loan
							.getId());
					loan.setRepays(repays);
					resposeParam = moneyTypeByLadder(list, resposeParam, loan);
				} else {
					if ("Ymerge".equals(merge)) {
						resposeParam = yMerge(list, resposeParam, loan);
					} else {
						resposeParam = noMerge(list, resposeParam, loan);
					}
				}
				if (StringUtils.isBlank(resposeParam.getStatus())) {
					String html = "借款项目名称:" + loan.getName() + "		利率:"
							+ loan.getRatePercent() + "%		类型:"
							+ loan.getLoanType() + "		状态:" + loan.getStatus();
					resposeParam.setStatus("ok");
					resposeParam.setHtml(html);
				}
				return resposeParam;
			}
		}
	}

	/**
	 * @description 51加息活动
	 * @date 2015-5-27
	 * @time 下午2:51:25
	 * @author SunZ
	 * @param list
	 * @param resposeParam
	 * @param loan
	 * @return
	 */
	private ResposeParam itemTypeBy51AddInterest(List<ResponseUser> list,
			ResposeParam resposeParam, Loan loan) {
		Invest invest = new Invest();
		invest.setLoanId(loan.getId());
		invest.setStatus("!流标");
		List<Invest> invests = loanDao.getInvestsByGroupCondition(invest);// 查询所有投资人
		if (invests != null && invests.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Integer deadline = loan.getDeadline();
			long time2 = DateUtil.StringToDate("2015-04-23 00:00:00").getTime();
			long time3 = DateUtil.StringToDate("2015-05-01 23:59:59").getTime();
			for (Invest i : invests) {
				long time = i.getTime().getTime();
				if (time >= time2 && time <= time3) {
					Double money = i.getMoney();
					Double awardMoney = 0D;
					if (money >= 10000 && money < 50000) {
						awardMoney = AddInterestAwardMoney51(0.02, money,
								deadline);
					} else if (money >= 50000 && money < 100000) {
						awardMoney = AddInterestAwardMoney51(0.03, money,
								deadline);
					} else if (money >= 100000) {
						awardMoney = AddInterestAwardMoney51(0.04, money,
								deadline);
					}
					if (awardMoney > 0) {
						User user = userDao.get(i.getInvestUserID());
						ResponseUser responseUser = new ResponseUser();
						responseUser.setAwardMoney(ArithUtil.round(awardMoney,
								1));
						responseUser.setInvestTime(sdf.format(i.getTime()));
						responseUser.setRealName(user.getRealname());
						responseUser.setMobileNum(user.getMobileNumber());
						responseUser.setTotalInvestMoney(i.getMoney());
						responseUser.setUserId(i.getInvestUserID());
						list.add(responseUser);
					}
				}
			}
		}
		resposeParam.setInvestCount(list.size());
		resposeParam.setList(list);
		return resposeParam;
	}

	private Double AddInterestAwardMoney51(double rate, double money,
			int deadline) {
		return money * rate * deadline / 12;
	}

	/**
	 * @description 新手专享项目奖励
	 * @date 2015-5-21
	 * @time 上午10:08:07
	 * @author SunZ
	 * @param list
	 * @param resposeParam
	 * @param loan
	 * @return
	 */
	private ResposeParam itemTypeByNewbieEnjoy(List<ResponseUser> list,
			ResposeParam resposeParam, Loan loan) {
		if (StringUtils.isBlank(loan.getNewbieEnjoy())
				|| "否".equals(loan.getNewbieEnjoy())) {
			resposeParam.setStatus("fail");
			resposeParam.setError("notNewbieEnjoy");
		} else {
			Invest invest = new Invest();
			invest.setLoanId(loan.getId());
			invest.setStatus("!流标");
			List<Invest> invests = loanDao.getInvestsByGroupCondition(invest);// 查询所有投资人
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Invest i : invests) {
				invest = investDao.getCountByNewbieEnjoy(i);
				if (invest != null) {
					if (i.getId().equals(invest.getId())) {
						User user = userDao.get(i.getInvestUserID());
						ResponseUser responseUser = new ResponseUser();
						responseUser.setAwardMoney(i.getPaidInterest());
						responseUser.setInvestTime(sdf.format(i.getTime()));
						responseUser.setRealName(user.getRealname());
						responseUser.setMobileNum(user.getMobileNumber());
						responseUser.setTotalInvestMoney(i.getMoney());
						responseUser.setUserId(i.getInvestUserID());
						list.add(responseUser);
					}
				}
			}
			resposeParam.setInvestCount(list.size());
			resposeParam.setList(list);
		}
		return resposeParam;
	}

	/**
	 * @param id
	 * @return
	 */
	private AwardItem isCreateFollowInvestAwardItem(String loanId, String status) {
		return awardItemDao.isCreateFollowInvestAwardItem(loanId, status);
	}

	/**
	 * @param list
	 * @param resposeParam
	 * @param loan
	 * @return
	 */
	private ResposeParam moneyTypeByLadder(List<ResponseUser> list,
			ResposeParam resposeParam, Loan loan) {
		Invest in = new Invest();
		in.setLoanId(loan.getId());
		List<Invest> invests = investDao.getInvestByLoanSortAsc(in);// 得到所有有效投资人
		for (Invest invest : invests) {
			Double awardMoney = 0D;
			User user = userDao.get(invest.getInvestUserID());
			ResponseUser responseUser = new ResponseUser(user.getUserId(),
					user.getMobileNumber(), invest.getMoney(),
					user.getRealname(), invest.getReturnPondMoney());
			list.add(responseUser);
			invest.setLoan(loan);
			String fixedTime = "2014-10-25 00:00:00";
			String fixedTime1 = "2014-12-31 23:59:59";
			Date fixedTimeDate = DateUtil.StringToDate(fixedTime);
			Date fixedTimeDate1 = DateUtil.StringToDate(fixedTime1);
			Double maxInvestMoney = invest.getReturnPondMoney();
			if ((user.getRegisterTime().getTime() >= fixedTimeDate.getTime() && user
					.getRegisterTime().getTime() <= fixedTimeDate1.getTime())
					|| (maxInvestMoney == null || maxInvestMoney == 0D)) {
				responseUser.setAwardMoney(awardMoney);
				continue;
			}
			if (maxInvestMoney <= 100000D) {
				awardMoney = getAwardMoney(invest, 0.1, maxInvestMoney);
				awardMoney = ArithUtil.round(awardMoney, 0);
			} else {
				Double money1 = 0D;
				Double money2 = maxInvestMoney - 100000D;
				money1 = getAwardMoney(invest, 0.1, 100000D);
				money2 = getAwardMoney(invest, 0.2, money2);
				awardMoney = money1 + money2;
				awardMoney = ArithUtil.round(awardMoney, 0);
			}
			responseUser.setAwardMoney(awardMoney);
		}
		resposeParam.setInvestCount(list.size());
		resposeParam.setList(list);
		return resposeParam;
	}

	@Override
	public PageInfo<AwardItem> pageLite(int pageNo, int pageSize,
			AwardItem awardItem, String userId) {

		PageInfo<AwardItem> awardItems = awardItemDao.pageLite(pageNo,
				pageSize, awardItem);
		for (AwardItem award : awardItems.getResults()) {
			if (MyBeanUtils.nonNull(award)) {
				if (award.getItemType() != null
						& !award.getItemType().equals("")) {
					ItemFlow itemFlow = itemFlowService.GetCurrentItemFlow(
							award.getId(), ItemType.AWARD, userId);
					award.setItemFlow(itemFlow);
				}
			}
		}

		return awardItems;
	}

	@Override
	public PageInfo<AwardItemUser> pageLiteForAwardUser(int pageNo,
			int pageSize, AwardItemUser awardItemUser) {
		return awardItemUserDao.pageLite(pageNo, pageSize, awardItemUser);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean createSendVerifyCodeAndMaxAwardMoneyRestrict(int itemId) {
		// 发送时候的6位随机数验证
		String code = RandomStringUtils.random(6, false, true);
		AwardItem awardItem = awardItemDao.get(itemId);
		if (awardItem == null) {
			return false;
		}
		awardItem.setSendVerifyCode(code);
		awardItem.setMaxAwardMoneyRestrict(awardItem.getMoneyAmount());
		awardItemDao.update(awardItem);
		return true;
	}

	@Override
	public String sendAward(String itemId) {
		lock.lock();
		try {
			if (!MyStringUtils.isNumeric(itemId)) {
				return "奖励项目id不正确";
			}
			int parseItemId = Integer.parseInt(itemId);
			AwardItem awardItem = awardItemDao.get(parseItemId);
			if (awardItem == null) {
				return "没有找到对应的奖励项目";
			}

			/******************************** 判断当前该奖励是否已经发送过了 *********************************/
			if (awardItem.getMaxAwardMoneyRestrict() <= 0
					|| AwardConstants.AwardItemStatus.SENDED.equals(awardItem
							.getStatus())) {// 最大限制金额如果为0,说明该项目已经发送
				return "该奖励项目已经发送过了";
			}

			/******************************** 判断是否有与当前奖励相同的奖励已经发送过了 *********************************/
			if (!StringUtils.isBlank(awardItem.getLoanId())) {
				String str = awardItem.getStatus();
				awardItem.setStatus(AwardConstants.AwardItemStatus.SENDED);
				List<AwardItem> sendAward = awardItemDao.isSendAward(awardItem);
				if (sendAward != null && sendAward.size() > 0) {
					if ("percentage".equals(awardItem.getMoneyType())) {
						return "该借款项目:" + awardItem.getLoanId() + "的"
								+ awardItem.getPercentageRate() + "%已经发送过了";
					} else if ("ladder".equals(awardItem.getMoneyType())) {
						return "该借款项目:" + awardItem.getLoanId() + "的跟投奖励已经发送过了";
					}
				}
				awardItem.setStatus(str);
			}

			/******************************** 查询该奖励项目下所有状态为"未发送"的明细 *********************************/
			List<AwardItemUser> awardItemUsers = awardItemUserDao
					.getAwardItemUser(itemId);

			/******************************** 循环所有允许发送的用户 *********************************/
			StringBuffer resultSb = new StringBuffer();
			if (awardItemUsers != null && awardItemUsers.size() > 0) {
				// 发送金额累加
				Double countMoney = 0D;
				for (AwardItemUser awardItemUser : awardItemUsers) {
					// 判断累减金额是否小于最大限制金额
					if (countMoney <= awardItem.getMaxAwardMoneyRestrict()) {// 200

						String sendContent = awardItem.getUserMsg();

						if (StringUtils.isBlank(sendContent)) {
							String money = Double.toString(awardItemUser
									.getMoney());
							sendContent = StringUtils.replace(sendContent,
									"{money}", money);
						}
						String userId = awardItemUser.getUserId();

						// 发起平台还款
						String ptResult = trusteeshipPlatformTransferService
								.platformTransferTrusteeship("", userId,
										awardItemUser.getMoney(), sendContent, "", "", "");
						if ("success".equals(ptResult)) {
							awardItemUser
									.setStatus(AwardConstants.AwardItemUserStatus.SENDED_SUCCESS);
							countMoney += awardItemUser.getMoney();// 发送成功将发送金额累加
							countMoney = ArithUtil.round(countMoney, 2);
							try {
								smsService.sendSms(userId, sendContent,
										SmsConstants.AWARD);
							} catch (Exception ex) {
								log.errLog("发送奖励短信异常awardItemUserId:"
										+ awardItemUser.getId(), ex);// 短信发送异常也不应该影响程序继续执行
							}
						} else {
							awardItemUser
									.setStatus(AwardConstants.AwardItemUserStatus.SENDED_FAILURE);
							resultSb.append(ptResult);
						}
						String detail = awardItemUser.getDetail();
						if (detail == null) {
							detail = ptResult;
						} else {
							detail = detail + ptResult;
						}
						awardItemUser.setDetail(detail);
						awardItemUser.setSendTime(new Date());
						awardItemUserDao.update(awardItemUser);
					}
				}
				if (resultSb.length() == 0) {
					awardItem.setMaxAwardMoneyRestrict(0);
					awardItem.setSendVerifyCode("0");
					awardItem.setStatus(AwardConstants.AwardItemStatus.SENDED);
					awardItemDao.update(awardItem);
					return "发送成功";
				} else {
					awardItem.setMaxAwardMoneyRestrict(ArithUtil.round(
							awardItem.getMaxAwardMoneyRestrict() - countMoney,
							2));
					awardItemDao.update(awardItem);
					return resultSb.toString();
				}
			} else {
				return "没有找到可发送记录";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("发送奖励异常", e);
			return "发送奖励异常";
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean deleteAwardItem(int itemId) {
		if (awardItemDao.get(itemId) != null) {
			return awardItemDao.deleteAwardItemByItemId(itemId) > 0;
		}
		return false;
	}

	@Override
	public void deleteWeiXin(int itemId) {
		AwardItem awardItem = awardItemDao.get(itemId);
		if ("weixin".equals(awardItem.getItemType())) {
			WeiXinActivity weiXinActivity = new WeiXinActivity();
			weiXinActivity.setLoanId(awardItem.getLoanId());
			weiXinActivity.setStatuss("等待创建");
			awardItemDao.updateWeiXinActivity(weiXinActivity);
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String saveAwardItem5And10(String userId, HttpServletRequest request) {
		String loanId = request.getParameter("loanId");
		if (StringUtils.isBlank(loanId)) {
			return "创建失败！借款项目ID不存在";
		}

		Loan loan = loanService.get(loanId);
		if (loan == null) {
			return "创建失败！借款项目不存在";
		}

		List<Repay> repays = repayService.getRepayByLoan(loan.getId());
		if (repays == null || repays.isEmpty()) {
			return "创建失败！该借款项目尚未放款";
		}
		loan.setRepays(repays);

		Invest i = new Invest();
		i.setLoanId(loan.getId());
		i.setStatus("!流标");
		List<Invest> invests = loanDao.getInvestsByGroupCondition(i);// 查询所有投资人
		if (invests == null || invests.isEmpty()) {
			return "该奖励项目类型没有找到复核要求的投资人";
		}
		loan.setInvests(invests);

		StringBuffer sb = new StringBuffer();
		AwardItem createPercentageAwardItem_10 = awardItemDao
				.isCreatePercentageAwardItem(loan.getId(), 10, "invest",
						"percentage");

		AwardItem createPercentageAwardItem_5 = awardItemDao
				.isCreatePercentageAwardItem(loan.getId(), 5, "invest",
						"percentage");

		if (createPercentageAwardItem_10 != null) {
			sb.append("10");
		}
		if (createPercentageAwardItem_5 != null) {
			if (sb.length() > 0) {
				sb.append("和5");
			} else {
				sb.append("5");
			}
		}
		if (sb.length() > 0) {
			return "该借款项目的" + sb.toString() + "%奖励已经创建过了,不能重复创建";
		}

		AwardItem awardItem_10 = new AwardItem();
		List<AwardItemUser> awardItemUsers_10 = new ArrayList<AwardItemUser>();
		awardItem_10.setFlowId(1);
		awardItem_10.setCreateTime(new Date());
		awardItem_10.setCreateUserID(userId);
		awardItem_10.setAwardItemUsers(awardItemUsers_10);
		awardItem_10.setItemType("invest");
		awardItem_10.setMoneyType("percentage");
		awardItem_10.setName(loan.getName());
		awardItem_10.setStatus(LoanConstants.LoanStatus.RECHECK);
		awardItem_10.setLoan(loan);
		awardItem_10.setLoanId(loan.getId());
		awardItem_10.setPercentageRate(10D);
		awardItem_10.setDescription("投资就享额外收益10%");
		awardItem_10.setUserMsg("尊敬的短融网客户：" + loan.getName()
				+ "项目，投资就享额外收益10%，已发放到您短融网账户，请您注意查收。");

		AwardItem awardItem_5 = new AwardItem();
		List<AwardItemUser> awardItemUsers_5 = new ArrayList<AwardItemUser>();
		awardItem_5.setFlowId(1);
		awardItem_5.setCreateTime(new Date());
		awardItem_5.setCreateUserID(userId);
		awardItem_5.setAwardItemUsers(awardItemUsers_5);
		awardItem_5.setItemType("invest");
		awardItem_5.setMoneyType("percentage");
		awardItem_5.setName(loan.getName());
		awardItem_5.setLoan(loan);
		awardItem_5.setLoanId(loan.getId());
		awardItem_5.setStatus(LoanConstants.LoanStatus.RECHECK);
		awardItem_5.setPercentageRate(5D);
		awardItem_5.setDescription("首次投资即可享额外5%的年化收益");
		awardItem_5.setUserMsg("尊敬的短融网客户：" + loan.getName()
				+ "项目，首次投资就享额外收益5%，已发放到您短融网账户，请您注意查收。");

		Date time_1025 = DateUtil.StringToDate("2014-10-24 23:59:59");
		Date time_1130 = DateUtil.StringToDate("2014-11-30 23:59:59");
		Date time_1231 = DateUtil.StringToDate("2014-12-31 23:59:59");
		for (Invest invest : invests) {
			User user = userDao.get(invest.getInvestUserID());
			invest.setUser(user);
			invest.setLoan(loan);
			if (invest.getUser().getRegisterTime().getTime() <= time_1025
					.getTime()) {
				create5award(invest, awardItem_5);
			} else if (invest.getUser().getRegisterTime().getTime() >= time_1130
					.getTime()
					&& invest.getUser().getRegisterTime().getTime() <= time_1231
							.getTime()) {
				create10award(invest, awardItem_10);
			} else {
				if (conditions5award(invest)) {
					create5award(invest, awardItem_5);
				} else {
					create10award(invest, awardItem_10);
				}
			}
		}
		if (awardItem_5.getUserCount() > 0) {
			if (addAwardItem(awardItem_5)) {
				sb.append("5%");
			}
		}
		if (awardItem_10.getUserCount() > 0) {
			if (addAwardItem(awardItem_10)) {
				sb.append("10%");
			}
		}
		String result = null;
		if (sb.length() > 0) {
			result = "奖励项目创建成功";
		} else {
			result = "奖励项目创建失败";
		}
		operaRecordService.insertRecord("保存5%10%奖励项目", userId, result
				+ "5%奖励项目id:" + awardItem_5.getId() + "10%奖励项目id:"
				+ awardItem_10.getId());
		return result;
	}

	/**
	 * 
	 * @description 5%奖励
	 * @author 孙铮
	 * @time 2015-4-8 上午11:48:05
	 * @param invest
	 * @param awardItem5
	 */
	private void create5award(Invest invest, AwardItem awardItem5) {
		// 判断该用户是否满足活动条件
		boolean conditions5award = conditions5award(invest);
		if (conditions5award) {
			Double awardMoneyBy5 = getAwardMoneyBy5(invest);
			awardMoneyBy5 = ArithUtil.round(awardMoneyBy5, 0);
			if (awardMoneyBy5 <= 0D) {
				return;
			}
			AwardItemUser awardItemUser = getAwardItemUsers(awardItem5,
					invest.getUser(), awardMoneyBy5, awardItem5.getLoanId(),
					invest.getMoney());
			awardItem5.getAwardItemUsers().add(awardItemUser);
			Double moneyAmount = awardItem5.getMoneyAmount();
			Integer userCount = awardItem5.getUserCount();
			if (userCount == null) {
				userCount = 0;
			}
			if (moneyAmount == null) {
				moneyAmount = 0D;
			}
			awardItem5.setMoneyAmount(ArithUtil.round(moneyAmount
					+ awardMoneyBy5, 1));
			awardItem5.setUserCount(++userCount);
		}
	}

	/**
	 * 
	 * @description 10%奖励
	 * @author 孙铮
	 * @time 2015-4-7 下午6:14:56
	 * @param invest
	 * @param awardItem10
	 * @return
	 */
	private void create10award(Invest invest, AwardItem awardItem10) {
		Date registerTime = invest.getUser().getRegisterTime();
		String userID = invest.getInvestUserID();
		if (registerTime == null || userID == null) {
			log.infoLog("创建5%和10%奖励", "10%奖励的用户ID或者注册时间为空" + userID
					+ registerTime);
			return;
		} else {
			Double awardMoney = 0D;
			Double money = invest.getMoney();

			// 得到10-25和12-31的时间
			String start = "2014-10-25 00:00:00";
			String end = "2014-12-31 23:59:59";
			Date startTime = DateUtil
					.StringToDate(start, "yyyy-MM-dd HH:mm:ss");
			Date endTime = DateUtil.StringToDate(end, "yyyy-MM-dd HH:mm:ss");
			if (registerTime.getTime() <= endTime.getTime()// 如果用户注册时期在两个规定时间内
					&& registerTime.getTime() >= startTime.getTime()) {
				Date monthAdd = DateUtil.MonthAdd(registerTime, 1);// 得到用户注册时间加1个月
				if (invest.getTime().getTime() >= registerTime.getTime()
						&& invest.getTime().getTime() <= monthAdd// 投资时间小于用户注册时间打1个月
								.getTime()) {
					awardMoney = getAwardMoneyBy10(invest);
					awardMoney = ArithUtil.round(awardMoney, 0);
					if (awardMoney > 0D) {
						AwardItemUser awardItemUser = getAwardItemUsers(
								awardItem10, invest.getUser(), awardMoney,
								invest.getLoanId(), money);
						awardItem10.getAwardItemUsers().add(awardItemUser);
						Double moneyAmount = awardItem10.getMoneyAmount();
						Integer userCount = awardItem10.getUserCount();
						if (userCount == null) {
							userCount = 0;
						}
						if (moneyAmount == null) {
							moneyAmount = 0D;
						}
						awardItem10.setMoneyAmount(ArithUtil.round(moneyAmount
								+ awardMoney, 1));
						awardItem10.setUserCount(++userCount);
					}
				}
			}
		}
	}

	private boolean conditions5award(Invest invest) {
		boolean falg = false;
		if (invest == null) {
			return falg;
		}
		User user = invest.getUser();
		if (user == null) {
			return falg;
		}
		Date endTime = DateUtil.StringToDate("2014-11-30 23:59:59");
		if (user.getRegisterTime().getTime() <= endTime.getTime()) {
			Double rechargeTotal = awardItemDao.conditions5AwardBySumMoney(
					user.getUserId(), user.getRegisterTime(), endTime,
					invest.getId());
			if (rechargeTotal == null || rechargeTotal == 0D) {
				Date investStartTime = DateUtil
						.StringToDate("2014-12-10 00:00:00");
				Date investEndTime = DateUtil
						.StringToDate("2014-12-31 23:59:59");
				if (invest.getTime().getTime() >= investStartTime.getTime()
						&& invest.getTime().getTime() <= investEndTime
								.getTime()) {
					List<Invest> list = awardItemDao.conditions5AwardByInvests(
							user.getUserId(), investStartTime, investEndTime);
					if (list != null && list.size() > 0) {
						Invest result = (Invest) list.get(0);
						if (invest.getId().equals(result.getId())) {
							falg = true;
						}
					}
				}
			} else {
				return falg;
			}
		} else {
			return falg;
		}
		return falg;
	}

	/**
	 * 
	 * @description 计算5%奖励应该给用户发多少钱
	 * @author 孙铮
	 * @time 2015-1-4 下午3:05:54
	 * @param invest
	 * @return
	 */
	private Double getAwardMoneyBy5(Invest invest) {
		String operationType = invest.getLoan().getOperationType();
		Double awardMoney = 0D;
		if ("月".equals(operationType)) {
			awardMoney = invest.getMoney() * 5 / 100
					* invest.getLoan().getDeadline() / 12;
		} else if ("天".equals(operationType)
				&& "否".equals(invest.getLoan().getBeforeRepay())) {
			awardMoney = invest.getMoney() * 5 / 100
					* invest.getLoan().getDay() / 365;
		} else if ("天".equals(operationType)
				&& "是".equals(invest.getLoan().getBeforeRepay())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Repay> repays = invest.getLoan().getRepays();
			if (repays != null && repays.size() > 0) {
				Repay repay = repays.get(0);
				Date time = repay.getTime();
				Date repayDay = repay.getRepayDay();
				Integer dayDifference = ArithUtil.dayDifference(
						sdf.format(time), sdf.format(repayDay));
				awardMoney = invest.getMoney() * 5 / 100 * dayDifference / 365;
			}
		}
		return awardMoney;
	}

	/**
	 * 
	 * @description 计算发给用户的奖励金额
	 * @author 孙铮
	 * @time 2014-8-14 下午2:51:54
	 * @param invest
	 * @param rate
	 * @param investMoney
	 * @return
	 */
	public Double getAwardMoney(Invest invest, Double rate, Double investMoney) {
		if ("月".equals(invest.getLoan().getOperationType())) {
			investMoney = investMoney * rate * (invest.getRate() / 12)
					* invest.getLoan().getDeadline();
		} else {
			if ("否".equals(invest.getLoan().getBeforeRepay())) {
				investMoney = investMoney * rate * (invest.getRate() / 365)
						* invest.getLoan().getDay();
			} else {
				List<Repay> repays = invest.getLoan().getRepays();
				if (repays != null && repays.size() > 0) {
					Repay repay = repays.get(0);
					Date repayDay = repay.getRepayDay();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Integer dayDifference = ArithUtil.dayDifference(
							sdf.format(invest.getLoan().getGiveMoneyTime()),
							sdf.format(repayDay));
					if (dayDifference <= invest.getLoan().getDay()) {
						dayDifference = invest.getLoan().getDay();
					}
					investMoney = investMoney * rate * (invest.getRate() / 365)
							* dayDifference;
				}
			}
		}
		return investMoney;
	}

	/**
	 * 
	 * @description 计算10%奖励应该给用户发多少钱
	 * @author 孙铮
	 * @time 2015-4-8 上午11:54:52
	 * @param invest
	 * @return
	 */
	private Double getAwardMoneyBy10(Invest invest) {
		String operationType = invest.getLoan().getOperationType();
		Double awardMoney = 0D;
		if ("月".equals(operationType)) {
			awardMoney = invest.getMoney() * 10 / 100
					* invest.getLoan().getRate() / 12
					* invest.getLoan().getDeadline();
		} else if ("天".equals(operationType)
				&& "否".equals(invest.getLoan().getBeforeRepay())) {
			awardMoney = invest.getMoney() * 10 / 100
					* invest.getLoan().getRate() / 365
					* invest.getLoan().getDay();
		} else if ("天".equals(operationType)
				&& "是".equals(invest.getLoan().getBeforeRepay())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Repay> repays = invest.getLoan().getRepays();
			if (repays != null && repays.size() > 0) {
				Repay repay = repays.get(0);
				Date time = repay.getTime();
				Date repayDay = repay.getRepayDay();
				Integer dayDifference = ArithUtil.dayDifference(
						sdf.format(time), sdf.format(repayDay));
				awardMoney = invest.getMoney() * 10 / 100
						* invest.getLoan().getRate() / 365 * dayDifference;
			}
		}
		return awardMoney;
	}

	public AwardItemUser getAwardItemUsers(AwardItem awardItem, User user,
			Double money, String loanId, Double investTotalMoneyByloanID) {
		AwardItemUser awardItemUser = new AwardItemUser();
		awardItemUser.setAwardItemId(awardItem.getId());
		awardItemUser.setCreateTime(new Date());
		awardItemUser.setMoney(money);
		awardItemUser.setUserId(user.getUserId());
		awardItemUser.setInvestTotalMoneyByloanID(investTotalMoneyByloanID);
		awardItemUser.setStatus(AwardConstants.AwardItemUserStatus.UNSENDED);
		return awardItemUser;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void followInvestAwardModify(String userId) {
		List<Invest> findFollowInvestAward = awardItemDao
				.findFollowInvestAward(userId);
		for (Invest invest : findFollowInvestAward) {
			String returnPond = invest.getReturnPond();
			if ("Y".equals(returnPond)) {
				User user = userDao.get(invest.getInvestUserID());
				Double returnPondMoney = invest.getReturnPondMoney();// 回池金额
				if (returnPondMoney == null) {
					returnPondMoney = 0D;
				}
				Double investMoneyTotal1 = user.getInvestMoneyTotal1();
				if (investMoneyTotal1 == null) {
					investMoneyTotal1 = 0D;
				}
				invest.setReturnPond("H");
				user.setInvestMoneyTotal1(investMoneyTotal1 + returnPondMoney);
				try {
					userDao.update(user);
				} catch (Exception e) {
					e.printStackTrace();
				}
				investDao.update(invest);
				log.infoLog(
						"跟投状态修改为H,跟投池金额增加",
						"项目id:" + invest.getLoanId() + "投资id:" + invest.getId()
								+ "投资人:" + invest.getInvestUserID() + "跟投状态:"
								+ invest.getReturnPond() + "项目状态:"
								+ invest.getStatus() + "用户池中金额:"
								+ user.getInvestMoneyTotal1());
			}
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void modifyFollowInvestMoney(String userId) {
		List<Invest> invests = awardItemDao
				.findInvestByFollowStatusAndInvestStatus(userId);
		for (Invest invest : invests) {
			User user = userDao.get(invest.getInvestUserID());
			Double investMoneyTotal1 = user.getInvestMoneyTotal1();
			Double money = invest.getMoney();
			if (investMoneyTotal1 == null || investMoneyTotal1 <= 0) {
				continue;
			}
			if (investMoneyTotal1 >= money) {
				invest.setReturnPond("Y");
				invest.setReturnPondMoney(money);
				user.setInvestMoneyTotal1(ArithUtil.round(investMoneyTotal1
						- money, 2));
			} else if (investMoneyTotal1 < money) {
				invest.setReturnPond("Y");
				invest.setReturnPondMoney(investMoneyTotal1);
				user.setInvestMoneyTotal1(0D);
			}
			investDao.update(invest);
			userDao.update(user);
			log.infoLog(
					"跟投状态修改为Y,跟投池金额减少",
					"项目id:" + invest.getLoanId() + "投资id:" + invest.getId()
							+ "投资人:" + invest.getInvestUserID() + "跟投状态:"
							+ invest.getReturnPond() + "项目状态:"
							+ invest.getStatus() + "用户池中金额:"
							+ user.getInvestMoneyTotal1());
		}
	}

	@Override
	public List<AwardItem> getAwards(Map<String, Object> map) {
		return awardItemDao.getAwards(map);
	}
}