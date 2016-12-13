package com.duanrong.business.followInvestAward.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import util.Log;

import com.duanrong.business.followInvestAward.dao.FollowInvestDao;
import com.duanrong.business.followInvestAward.service.FollowInvestAwardService;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.User;
import com.duanrong.business.withdraw.model.WithdrawCash;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.DateUtil;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-12-1 下午6:57:46
 * @Description : drsoa Maven Webapp
 *              com.duanrong.business.followInvestAward.service.impl
 *              FollowInvestAwardServiceImpl.java
 * 
 */
@Service("followInvestAwardService")
public class FollowInvestAwardServiceImpl implements FollowInvestAwardService {

	@Resource
	private UserDao userDao;
	@Resource
	private InvestDao investDao;
	@Resource
	private FollowInvestDao followInvestDao;
	@Resource
	Log log;

	/**
	 * 
	 * @description 得到有效期截至时间
	 * @author 孙铮
	 * @time 2014-8-25 下午5:45:18
	 * @param registerTime
	 * @return
	 */
	public Date readValidTime(Date registerTime) {
		Date monthAdd = MonthAdd(registerTime, 3);
		return monthAdd;
	}

	/**
	 * 
	 * @description 得到跟投截至时间
	 * @author 孙铮
	 * @time 2014-8-25 下午5:45:18
	 * @param registerTime
	 * @return
	 */
	public Date readFollowInvestValidTime(Date registerTime) {
		Date monthAdd = MonthAdd(registerTime, 12);
		return monthAdd;
	}

	/**
	 * 
	 * @description 获取用户跟投截至时间
	 * @author 孙铮
	 * @time 2014-8-14 下午1:26:21
	 * @param userId
	 * @return
	 */
	public Date readFollowRechargeTime(String userId, Date endTime) {
		try {
			Date followRechargeTime = followInvestDao.getFollowRechargeTime(
					userId, endTime);
			return followRechargeTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @description 根据用户名注册时间开始获取一段时间内,用户总充值金额
	 * @author 孙铮
	 * @time 2014-8-14 下午12:59:05
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double readRecharges(String userId, Date registerTime,
			Date addRegisterTime) {
		try {
			Double rechargeTotal = followInvestDao.getRecharges(userId,
					registerTime, addRegisterTime);
			if (rechargeTotal == null) {
				rechargeTotal = 0D;
			}
			return ArithUtil.round(rechargeTotal, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @description 根据用户名注册时间开始获取一段时间内,用户总提现金额
	 * @author 孙铮
	 * @time 2014-8-14 下午1:02:17
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double readWithdrawCashs(String userId, Date registerTime,
			Date addRegisterTime) {
		try {
			Double withdrawCashTotal = followInvestDao.getWithdrawCashs(userId,
					registerTime, addRegisterTime);
			if (withdrawCashTotal == null) {
				withdrawCashTotal = 0D;
			}
			return ArithUtil.round(withdrawCashTotal, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @description 得到某用户需要回池的金额
	 * @author 孙铮
	 * @time 2014-8-22 下午1:59:31
	 * @param userID
	 * @return
	 */
	public Double readReturnPondByY(String userID) {
		try {
			Double returnPondTotalMoney = followInvestDao
					.getReturnPondByY(userID);
			if (returnPondTotalMoney == null) {
				returnPondTotalMoney = 0D;
			}
			return returnPondTotalMoney;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @description 得到有效期内用户获得的投资所得利息
	 * @author 孙铮
	 * @time 2014-8-15 上午11:27:45
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double readPaidInterest(String userId, Date registerTime,
			Date addRegisterTime) {
		try {
			Double paidInterest = followInvestDao.getPaidInterest(userId,
					registerTime, addRegisterTime);
			if (paidInterest == null) {
				paidInterest = 0D;
			}
			return ArithUtil.round(paidInterest, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @description 获取有效时间内用户通过平台划款方式得到的收入
	 * @author 孙铮
	 * @time 2014-8-15 上午11:33:38
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double readPlatformTransferByUsername(String userId,
			Date registerTime, Date addRegisterTime) {
		try {
			Double actualMoney = followInvestDao.getPlatformTransferByUsername(
					userId, registerTime, addRegisterTime);
			if (actualMoney == null) {
				actualMoney = 0D;
			}
			return ArithUtil.round(actualMoney, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @description 判断该笔投资是否需要回池(还款使用)
	 * @author 孙铮
	 * @time 2014-8-26 上午9:46:58
	 * @param invest
	 */
	public void investReturnPond(Invest invest, String userID) {
		try {
			String returnPond = invest.getReturnPond();
			if (returnPond == null) {
				return;
			} else if ("Y".equals(invest.getReturnPond())) {
				User user = userDao.get(userID);
				Double returnPondMoney = invest.getReturnPondMoney();
				if (returnPondMoney != null && returnPondMoney > 0) {
					if (user != null) {
						invest.setReturnPond("H");
						user.setInvestMoneyTotal1(user.getInvestMoneyTotal1()
								+ returnPondMoney);
						userDao.update(user);
						investDao.update(invest);
					}
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新某个用户总投资额及跟投回炉金额
	 * 
	 * @param userId
	 *            用户ID
	 * @param currentMoney
	 *            本次投资金额
	 */
	public void updateInvestMoneyTotalAndInvestMoneyTotal1ByUserID(Invest invest) {
		if (invest == null) {
			return;
		}
		try {
			/**
			 * 更新用户总投资金额
			 */
			User user = userDao.get(invest.getInvestUserID());
			if (user == null) {
				return;
			}
			Double investMoneyTotal = user.getInvestMoneyTotal();
			if (investMoneyTotal == null) {
				user.setInvestMoneyTotal(invest.getMoney());
			} else {
				user.setInvestMoneyTotal(investMoneyTotal + invest.getMoney());
			}		
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description 该用户如果在有效期内发生提现,并且池中有金额,那么对应池中金额要相应扣除(提现需要用)
	 * @author 孙铮
	 * @time 2014-10-10 上午11:12:51
	 * @param withdrawCash
	 */
	@Override
	public void updateInvestMoneyTotal1(WithdrawCash withdrawCash) {
		try {
			if (withdrawCash == null) {
				return;
			} else {

				String userID = withdrawCash.getUserId();
				User user = userDao.get(userID);
				if (user == null) {
					return;
				}
				// 用户注册时间
				Date registerTime = user.getRegisterTime();
				// 有效期时间
				Date validTime = MonthAdd(registerTime, 3);
				Double investMoneyTotal12 = user.getInvestMoneyTotal1();
				if (investMoneyTotal12 == null) {
					user.setInvestMoneyTotal1(0D);
					userDao.update(user);
					return;
				}
				if (investMoneyTotal12 > 0
						&& withdrawCash.getTime().getTime() <= validTime
								.getTime()) {

					// 未修改前的池中金额
					Double investMoneyTotal1 = user.getInvestMoneyTotal1();
					// 修改后的池中金额
					Double alterInvestMoneyTotal1 = 0D;

					if (withdrawCash.getActualMoney() >= investMoneyTotal1) {
						user.setInvestMoneyTotal1(0D);
					} else {
						alterInvestMoneyTotal1 = ArithUtil.round(
								investMoneyTotal1
										- withdrawCash.getActualMoney(), 0);
						user.setInvestMoneyTotal1(alterInvestMoneyTotal1);
					}
					log.infoLog("有效期提现,并且池中有金额", userID + "在有效期内发生提现"
							+ withdrawCash.getActualMoney() + ",并且提现时池中有金额为"
							+ investMoneyTotal1 + ",对应池中金额扣除后池中金额为"
							+ alterInvestMoneyTotal1);
					userDao.update(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("提现updateInvestMoneyTotal1", e);
		}
	}

	private static Date MonthAdd(Date d, int monthAmount) {
		try {
			Calendar cNow = Calendar.getInstance();
			cNow.setTime(d);
			cNow.add(Calendar.MONTH, monthAmount);// 月份加1个月。
			return cNow.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算两个日期相差天数
	 * 
	 * @param startDay
	 *            开始日期
	 * @param endDay
	 *            还款日期
	 * @return
	 */
	private static Integer dayDifference(String startDay, String endDay) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			long to;
			long from;
			Integer dayNum = 0;
			try {
				to = df.parse(endDay).getTime();
				from = df.parse(startDay).getTime();
				dayNum = (int) ((to - from) / (1000 * 60 * 60 * 24));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return dayNum;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
