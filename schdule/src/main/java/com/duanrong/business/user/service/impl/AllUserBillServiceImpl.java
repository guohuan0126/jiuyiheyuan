package com.duanrong.business.user.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.user.UserConstants;
import com.duanrong.business.user.dao.AccountCheckingDao;
import com.duanrong.business.user.dao.AccountCountDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.AccountChecking;
import com.duanrong.business.user.model.AccountCount;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.AllUserBillService;
import com.duanrong.business.user.service.RoleService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.withdraw.service.WithdrawCashService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.DateUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.yeepay.service.TrusteeshipQueryAccounantService;

@Service
public class AllUserBillServiceImpl implements AllUserBillService {

	@Resource
	UserMoneyService ubs;

	@Resource
	UserService userService;

	@Resource
	RoleService roleService;

	@Resource
	AccountCountDao accountCountDao;

	@Resource
	AccountCheckingDao accountCheckingDao;

	@Resource
	TrusteeshipQueryAccounantService trusteeshipQueryAccounantService;

	@Resource
	UserDao userDao;

	@Resource
	WithdrawCashService withdrawCashService;

	@Resource
	RechargeService rechargeService;
	@Resource
	InvestService investService;

	@Resource
	Log log;

	public void accountChecking() throws HttpException, IOException,
			DocumentException {
		// 实名认证用户人数
		long allInvestorsNums = 0;
		double sumBalance = 0D;
		double sumAvailableAmount = 0D;
		double sumFreezeAmount = 0D;
		// 易宝账户总余额
		double sumEbaoBalance = 0D;
		// 易宝账户总可用余额
		double sumEbaoAvailableAmount = 0D;
		// 易宝账户总冻结余额
		double sumEbaoFreezeAmount = 0D;

		int pageNo = 1;
		// FIXME 每页数量
		PageInfo<User> pageLite = userService.pageLite(pageNo, 50, null);
		int totalPage = pageLite.getTotalPage();
		try {
			for (int i = 0; i < totalPage; i++) {
				int n = 0;
				List<User> users = userService.pageLite(i + 1, 50, null)
						.getResults();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				log.infoLog("账户平衡查询start", sdf.format(new Date()));
				for (User user : users) {
					n++;
					String userId = user.getUserId();
					try{
						Map<String, Object> map = ubs.queryUserMoney(userId);
						user.setBalance((double) map.get("money"));
						user.setFrozenMoney((double) map.get("frozen"));
						userService.update(user, false);
					}catch(Exception e){
						log.errLog("账户平衡更新用户失败", e);
					}		
					
					// 没有实名认证的用户或者测试账户没有手机号的用户不进行查询
					if (StringUtils.isAnyBlank(user.getMobileNumber(),
							user.getRealname(), user.getIdCard())) {
						continue;
					}
					List<Role> roles = roleService.getRolesByUserId(userId);
					if (roles == null || roles.size() == 0) {
						continue;
					}
					try {
						allInvestorsNums++;
						AccountChecking accountChecking = trusteeshipQueryAccounantService
								.queryAccount(userId);
						saveAccountChecking(accountChecking, user);
						sumBalance += accountChecking.getBalance();
						sumAvailableAmount += accountChecking
								.getAvailableAmount();
						sumFreezeAmount += accountChecking.getFreezeAmount();
						sumEbaoAvailableAmount += Double
								.parseDouble(accountChecking
										.getEbaoAvailableAmount());
						sumEbaoBalance += Double.parseDouble(accountChecking
								.getEbaoBalance());
						sumEbaoFreezeAmount += Double
								.parseDouble(accountChecking
										.getEbaoFreezeAmount());
					} catch (Exception e) {
						log.errLog("账户平衡查询:" + userId, e);
					}
					log.infoLog("账户平衡查询", "第n=" + n + "个用户");
				}

				log.infoLog("账户平衡查询end", "第 i=" + i + "页");
			}
			log.infoLog("账户资金统计", "账户平衡调度，资金统计开始....");
			try {
				Date now = new Date();
				AccountCount accountCount = new AccountCount();
				accountCount.setId(IdGenerator.randomUUID());
				accountCount.setSumAvailableAmount(sumAvailableAmount);
				accountCount.setSumBalance(sumBalance);
				accountCount.setSumFreezeAmount(sumFreezeAmount);
				accountCount.setSumEbaoBalance(sumEbaoBalance);
				accountCount.setSumEbaoFreezeAmount(sumEbaoFreezeAmount);
				accountCount.setSumEbaoAvailableAmount(sumEbaoAvailableAmount);
				accountCount.setTime(now);
				accountCount.setAllInvestorsNums(allInvestorsNums);
				accountCount.setAllUserNums(userDao.getPerson());
				accountCount
						.setUserNumsPerDay(getTheNumberOfRegisteredEveryDay(accountCount
								.getTime()));
				try {
					accountCount
							.setAccountUserNums(getTheNumberOfAccountEveryDay(accountCount
									.getTime()));
				} catch (Exception e) {
					log.infoLog("实名认证用户", e);
				}
				accountCount.setAllinvestNums(getInvestNums());
				accountCount
						.setSumWithdrawMoneyPerDay(getWithdrawMoneyPerDay(now));
				accountCount
						.setSumRechargeMoneyPerDay(getRechargeMoneyPerDay(now));
				accountCountDao.insert(accountCount);

			} catch (Exception e) {
				log.errLog("账户平衡调度", e);
			}
			log.infoLog("账户资金统计", "账户平衡调度，资金统计结束....");
		} catch (Exception e) {
			log.errLog("账户平衡调度", e);
		}

	}

	/**
	 * 保存账户平衡实体类
	 * 
	 * @param accountChecking
	 * @param userId
	 */
	private void saveAccountChecking(AccountChecking accountChecking, User user) {
		accountChecking.setId(IdGenerator.randomUUID());
		accountChecking.setUserId(user.getUserId());
		accountChecking.setBalance(ArithUtil.round(
				user.getBalance() + user.getFrozenMoney(), 2));
		accountChecking.setAvailableAmount(user.getBalance());
		accountChecking.setFreezeAmount(user.getFrozenMoney());
		accountChecking.setTime(new Date());
		if (accountChecking.getBalance() != Double.parseDouble(accountChecking
				.getEbaoBalance())
				|| accountChecking.getFreezeAmount() != Double
						.parseDouble(accountChecking.getEbaoFreezeAmount())
				|| accountChecking.getAvailableAmount() != Double
						.parseDouble(accountChecking.getEbaoAvailableAmount())) {
			accountCheckingDao.insert(accountChecking);
		}

	}

	/**
	 * 获取每天的充值金额
	 * 
	 * @param time
	 * @return
	 */
	private Double getRechargeMoneyPerDay(Date time) {
		Map<String, Object> params = new HashMap<>();
		params.put("now", time);
		Date yesterday = DateUtil.addDay(time, -1);
		params.put("yesterday", yesterday);
		params.put("status", UserConstants.RechargeStatus.SUCCESS);
		return rechargeService.getRechargeMoneyPerDay(params);
	}

	/**
	 * 获取每天的提现金额
	 * 
	 * @param time
	 * @return
	 */
	private Double getWithdrawMoneyPerDay(Date time) {
		Map<String, Object> params = new HashMap<>();
		params.put("now", time);
		Date yesterday = DateUtil.addDay(time, -1);
		params.put("yesterday", yesterday);
		params.put("status", UserConstants.WithdrawStatus.SUCCESS);
		return withdrawCashService.getWithdrawMoneyPerDay(params);
	}

	/**
	 * 获取每天的注册人数
	 * 
	 * @param time
	 * @return
	 */
	private Long getTheNumberOfRegisteredEveryDay(Date time) {
		Map<String, Object> params = new HashMap<>();
		params.put("now", time);
		Date yesterday = DateUtil.addDay(time, -1);
		params.put("yesterday", yesterday);
		return userService.getTheNumberOfRegisteredEveryDay(params);
	}

	/**
	 * 获取每天的开户数量
	 * 
	 * @param time
	 * @return
	 */
	private Long getTheNumberOfAccountEveryDay(Date time) {
		Map<String, Object> params = new HashMap<>();
		params.put("now", time);
		Date yesterday = DateUtil.addDay(time, -1);
		params.put("yesterday", yesterday);
		params.put("status", UserConstants.AccountStatus.PASSED);
		return userService.getTheNumberOfAccountEveryDay(params);
	}

	/**
	 * 获取投资总数量
	 * 
	 * @param time
	 * @return
	 */
	private Long getInvestNums() {
		try {
			return investService.getInvestNums();
		} catch (Exception e) {
			return 0l;
		}

	}
}
