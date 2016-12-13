package com.duanrong.cps.business.fengchelicai.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.Log;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiAccInvest;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiLoan;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiNotice;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiPushInvest;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiRepayment;
import com.duanrong.cps.business.fengchelicai.model.Repay;
import com.duanrong.cps.business.fengchelicai.service.FengchelicaiService;
import com.duanrong.cps.business.invest.dao.InvestDao;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.dao.LoanDao;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.dao.PlatformDao;
import com.duanrong.cps.business.platform.dao.PlatformPushDao;
import com.duanrong.cps.business.platform.model.PushInvest;
import com.duanrong.cps.util.HttpUtil;
import com.duanrong.cps.util.ReadProperties;
import com.duanrong.cps.util.WrbCoopDESUtil;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.InterestUtil;

@Service("fengchelicaiService")
public class FengchelicaiServiceImpl implements FengchelicaiService {

	@Autowired
	private PlatformDao platformDao;

	@Autowired
	private PlatformPushDao platformPushDao;

	@Autowired
	private InvestDao investDao;

	@Autowired
	private LoanDao loanDao;

	private static DecimalFormat df1 = new DecimalFormat("##0.00");

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("参数scale必须为整数或零!");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	@Autowired
	private Log log;

	private String chedaifengkong = " 风控措施:质押车辆存入短融网专有车库进行监管，并由专人维护。 "
			+ "逾期处理:如出现借款人未能按时还款的情形，1通过诉讼、拍卖、变卖等法律流程进行抵质押物处置变现；2投资人可授权短融网寻找资产管理公司、债权劣后基金等第三方收购该笔债权。"
			+ " 风险提示:本产品存在到期后借款人无力偿还本息、抵质押物贬值、抵质押物处置周期过长、资产收购不成功而导致的无法及时还款风险。 ";
	private String fangdaifengkong = " 风控措施:1由专业房产评估人员对房产价值进行评估，合理控制抵押率；2办理房产抵押登记及借款公证；"
			+ "逾期处理:如出现借款人未能按时还款的情形，1通过诉讼、拍卖、变卖等法律流程进行抵质押物处置变现；2投资人可授权短融网寻找资产管理公司、债权劣后基金等第三方收购该笔债权。"
			+ "风险提示:本产品存在到期后借款人无力偿还本息、抵质押物贬值、抵质押物处置周期过长、资产收购不成功而导致的无法及时还款风险。 ";
	private String qiyefengkong = " 风控措施:1.实际控制人及另一家商贸企业提供连带保证责任担保；2.存货质押监管"
			+ "逾期处理:若借款人未按合同约定及时还本付息，且担保人未代偿的，在一定的期限内，经投资人授权短融网可寻找包括但不限于金融机构、资产管理公司、投资公司等第三方收购该笔债权，同时第三方向借款人及担保人清收、处置抵押物。"
			+ "风险提示:本产品存在到期后借款人无力偿还本息、抵质押物贬值、抵质押物处置周期过长、资产收购不成功而导致的无法及时还款风险。 ";
	private String otherfengkong = "风控措施：深入到借款人所在社区，考察借款人的还款能力、还款意愿和信用口碑，对借款人进行详尽的实地尽职调查； 2、严格按照小额分散原则控制借款额度，降低违约风险。"
			+ " 逾期处理若借款人未按合同约定及时还本付息在一定的期限内，经投资人授权短融网可寻找包括但不限于金融机构、资产管理公司、投资公司、债权劣后基金等第三方收购该笔债权。 ";

	/**
	 * 查询产品信息
	 */
	@Override
	public List<FengchelicaiLoan> getLoanInfo(Map<String, Object> params) {
		Map<String, Object> paramData = new HashMap<String, Object>();
		System.out.println("#######风车理财取查询产品信息:");
		List<FengchelicaiLoan> resultList = new ArrayList<FengchelicaiLoan>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String loanId = params.get("loanIds") + "";
		List<Loan> loanList = new ArrayList<Loan>();

		if (!"".equals(loanId) && loanId != null && !"null".equals(loanId)) {
			paramData.put("loanIds", "'" + loanId + "'");
		} else {
			paramData.put("loanStatus", "筹款中"); // 不根据id查询时，只查可以可投的标的
		}
		loanList = platformDao.queryLoan(paramData);
		try {
			for (int i = 0; i < loanList.size(); i++) {
				FengchelicaiLoan fengchelicai = new FengchelicaiLoan();
				fengchelicai.setInvest_id(loanList.get(i).getId()); // 标的id
				fengchelicai.setInvest_title(loanList.get(i).getName()); // 标题
				String loanUrl = ReadProperties.getPropetiesValue(
						"constant/thiredPlatform.properties", "pc_url");
				fengchelicai.setInvest_url(loanUrl + "loanDetail/"
						+ loanList.get(i).getId()); // 该标的的具体路径
				if ("天".equals(loanList.get(i).getOperationType())) {
					fengchelicai.setTime_limit(loanList.get(i).getDay());// 期限
																			// 123
																			// 折算成天
					fengchelicai.setTime_limit_desc(loanList.get(i).getDay()
							+ "天");
				} else {
					fengchelicai
							.setTime_limit(loanList.get(i).getDeadline() * 30); // 期限
																				// 123
																				// 折算成天
					fengchelicai.setTime_limit_desc(loanList.get(i)
							.getDeadline() + "个月");
				}
				fengchelicai.setTotal_amount(loanList.get(i).getTotalmoney()); // 标的总额
				fengchelicai.setRate(ArithUtil.round(
						loanList.get(i).getRate() * 100, 2)); // 年化利率
				Double sumMoney = 0.0;
				if (loanList.get(i).getSumMoney() != null) {
					sumMoney = loanList.get(i).getSumMoney();
				}
				fengchelicai.setProgress(progress(loanList.get(i)
						.getTotalmoney(), sumMoney)); // 进度56 为56%
				if (loanList.get(i).getExpectTime() != null) {
					fengchelicai.setStart_time(sdf.format(loanList.get(i)
							.getExpectTime())); // 开售时间
				} else {
					fengchelicai.setStart_time(sdf.format(loanList.get(i)
							.getCommitTime())); // 开售时间
				}

				fengchelicai.setPayback_way(loanList.get(i).getRepayType()); // 还款方式
				if ("否".equals(loanList.get(i).getNewbieEnjoy())) {
					fengchelicai.setInvest_condition(" "); // 投资条件
															// 新手标invest_condition:”新手”
				} else {
					fengchelicai.setInvest_condition("新手"); // 投资条件
															// 新手标invest_condition:”新手”
				}
				String loanType = loanList.get(i).getLoanType(); // 资产类型
				if ("房贷".equals(loanType)) {
					fengchelicai.setProject_description(fangdaifengkong); // 项目描述
				} else if ("车贷".equals(loanType)) {
					fengchelicai.setProject_description(chedaifengkong); // 项目描述
				} else if ("企业贷".equals(loanType)) {
					fengchelicai.setProject_description(qiyefengkong); // 项目描述
				} else {
					fengchelicai.setProject_description(otherfengkong); // 项目描述
				}
				if ("流标".equals(loanList.get(i).getStatus())) {
					fengchelicai.setLose_invest("1"); // 流标情况 0表示没有流标 1表示流标
					fengchelicai.setProgress(100.00); // 流标的进度要为100
				} else {
					fengchelicai.setLose_invest("0");
				}
				resultList.add(fengchelicai);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	@Override
	public Map<String, Object> getaAggregate(String date) {
		Map<String, Object> selectMap = new HashMap<String, Object>();
		int lendCount = investDao.getLendCount(date);
		int borrowCount = loanDao.getBorrowCount(date);
		Double investAllMoney = loanDao.getInvestAllMoney(date);
		Double allWaitBackMoney = loanDao.getAllWaitBackMoney(date);
		selectMap.put("lend_count", lendCount);
		selectMap.put("borrow_count", borrowCount);
		selectMap.put("invest_all_money", investAllMoney);
		selectMap.put("all_wait_back_money", allWaitBackMoney);
		return selectMap;
	}

	@Override
	public List<FengchelicaiAccInvest> getAccInvest(String userId,
			String startTime, String endTime, String investStatus,
			String offset, String limit, String investRecordId) {
		String url = ReadProperties.getPropetiesValue(
				"constant/thiredPlatform.properties", "pc_url");
		
		System.out.println("######风车理财查询getAccInvest：userId:"+userId+",investStatus:"+investStatus);
		List<Invest> selectList = investDao.getAccInvest(userId, startTime,
				endTime, investStatus, offset, limit, investRecordId);
		System.out.println("######风车理财查询getAccInvest:selectList.size()"+selectList.size());
		List<FengchelicaiAccInvest> resultList = new ArrayList<FengchelicaiAccInvest>();
		if (selectList.size() > 0) {
			for (Invest invest : selectList) {
				FengchelicaiAccInvest fengchelicaiAccInvest = new FengchelicaiAccInvest();
				fengchelicaiAccInvest.setInvest_time(invest.getTime());
				fengchelicaiAccInvest.setInvest_money(invest.getMoney());
				fengchelicaiAccInvest.setAll_back_principal(round(
						invest.getPaidMoney(), 2));
				fengchelicaiAccInvest.setAll_back_interest(round(
						invest.getInterest(), 2));
				fengchelicaiAccInvest.setInvest_reward(null);
				fengchelicaiAccInvest.setInvest_record_id(invest.getId());
				fengchelicaiAccInvest.setProject_title(invest.getLoan()
						.getName());
				fengchelicaiAccInvest.setProject_id(invest.getLoanId());
				fengchelicaiAccInvest.setProject_url(url + "loanDetail/"
						+ invest.getLoanId());
				fengchelicaiAccInvest.setProject_rate((float) (invest.getLoan()
						.getRate() * 100));
				if (invest.getLoan().getOperationType().equals("天")) {
					fengchelicaiAccInvest.setProject_timelimit(invest.getLoan()
							.getDay());
					fengchelicaiAccInvest.setProject_timelimit_desc(invest
							.getLoan().getDay() + "天");
				} else {
					fengchelicaiAccInvest.setProject_timelimit(invest.getLoan()
							.getDeadline() * 30);
					fengchelicaiAccInvest.setProject_timelimit_desc(invest
							.getLoan().getDeadline() + "个月");
				}
				if (invest.getStatus().equals("还款中")) {
					fengchelicaiAccInvest.setInvest_status(0);
				} else if (invest.getStatus().equals("完成")) {
					fengchelicaiAccInvest.setInvest_status(1);
				}

				Map<String, Object> map = new HashMap<String, Object>();

				if (userId != null && !userId.equals("")) {
					map.put("userId", userId);
				}

				if (startTime != null && !startTime.equals("") && !"null".equals(startTime)) {
					map.put("startTime", startTime);
				}
				if (StringUtils.isNotBlank(endTime) && !"null".equals(endTime)) {
					map.put("endTime", endTime);
				}
				if (StringUtils.isNotBlank(invest.getId()) && !"null".equals(invest.getId())) {
					map.put("investId", invest.getId());
				}
				FengchelicaiRepayment repayMent = investDao
						.getRepayMentRecordsList(map);
				if (repayMent != null) {
					String repayType = repayMent.getRepayType();
					String type = repayMent.getOperationType();
					if (type.equals("天")) {
						Integer day = repayMent.getDay();
						repayMent.setLoanTime(day + "天");
						repayMent.setRepayMoeny(repayMent.getInvestMoney());
						double interest = InterestUtil.getInterestByPeriod(
								repayMent.getInvestMoney(),
								repayMent.getReate(), repayMent.getDay(),
								repayMent.getOperationType(),
								repayMent.getRepayType());
						repayMent.setRepayInterest(interest);
					} else {
						// 月标
						if (repayType.equals("一次性到期还本付息")) {
							double interest = InterestUtil
									.getDQHBFXInterestByPeriodMoth(
											repayMent.getMoney(),
											repayMent.getReate(),
											repayMent.getDeadline());
							repayMent.setRepayInterest(interest);
							repayMent.setRepayMoeny(repayMent.getMoney());
							repayMent
									.setLoanTime(repayMent.getDeadline() + "月");
						} else if (repayType.equals("按月付息到期还本")) {
							// 第几期 和项目周期进行比对 如果是最后一期 就是 利息 本金都有
							if (repayMent.getRepayItem() == repayMent
									.getDeadline()) {
								double interest = InterestUtil
										.getRFCLInterestByPeriodMoth(
												repayMent.getMoney(),
												repayMent.getReate(), 1);
								System.out.println("###############风车理财interest1:"+interest);
								repayMent.setRepayInterest(interest);
								repayMent.setRepayMoeny(repayMent
										.getInvestMoney());
								repayMent.setLoanTime(repayMent.getDeadline()
										+ "月");
							} else {
								double interest = InterestUtil
										.getRFCLInterestByPeriodMoth(
												repayMent.getMoney(),
												repayMent.getReate(), 1);
								repayMent.setRepayInterest(interest);
								repayMent.setLoanTime(repayMent.getDeadline()
										+ "月");
								repayMent.setRepayMoeny(0.0);
								System.out.println("###############风车理财interest2:"+interest);
							}
						} else if (repayType.equals("等额本息")) {
							// 计算应还本金
							double corpus = InterestUtil.corpus(
									repayMent.getInvestMoney(),
									repayMent.getReate(),
									repayMent.getDeadline(),
									repayMent.getRepayItem());
							// 计算应还利息
							double interest = InterestUtil.interest(
									repayMent.getInvestMoney(),
									repayMent.getReate(),
									repayMent.getDeadline(),
									repayMent.getRepayItem());
							repayMent.setRepayInterest(interest);
							repayMent.setRepayMoeny(corpus);
							repayMent
									.setLoanTime(repayMent.getDeadline() + "月");
						}
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (null != repayMent) {
					if(repayMent.getRepayData() != null){
						fengchelicaiAccInvest.setNext_back_date(sdf.format(repayMent
								.getRepayData()));
					}else{
						fengchelicaiAccInvest.setNext_back_date(null);
					}
					
					SimpleDateFormat df = new SimpleDateFormat("d");
					System.out.println("风车：repayMent.getRepayData()："+repayMent.getRepayData());
					if(repayMent.getRepayData()!=null){
						fengchelicaiAccInvest.setMonthly_back_date(df
								.format(repayMent.getRepayData()));
					}else{
						fengchelicaiAccInvest.setMonthly_back_date(null);
					}
					System.out.println("####风车理财：Next_back_money:"+(repayMent.getRepayMoeny()+repayMent.getRepayInterest()));
					fengchelicaiAccInvest.setNext_back_money(repayMent.getRepayMoeny()+repayMent.getRepayInterest());
					System.out.println("####风车理财：Next_back_principal:"+(repayMent.getRepayMoeny()));
					fengchelicaiAccInvest.setNext_back_principal(repayMent.getRepayMoeny());
					System.out.println("####风车理财：Next_back_interest:"+(repayMent.getRepayInterest()));
					fengchelicaiAccInvest.setNext_back_interest(repayMent.getRepayInterest());
				} else {
					fengchelicaiAccInvest.setNext_back_date(null);
					fengchelicaiAccInvest.setMonthly_back_date(null);
					fengchelicaiAccInvest.setNext_back_money(null);
					fengchelicaiAccInvest.setNext_back_principal(null);
					fengchelicaiAccInvest.setNext_back_interest(null);
				}

				Repay repay2 = investDao.getNextRepay(invest.getLoanId(),
						userId);
				if (null != repay2 && !"".equals(repay2)) {
					fengchelicaiAccInvest.setNext_back_date(sdf.format(repay2
							.getRepayDay()));

					SimpleDateFormat df = new SimpleDateFormat("d");
					fengchelicaiAccInvest.setMonthly_back_date(df.format(repay2
							.getRepayDay()).toString());
				} else {
					fengchelicaiAccInvest.setMonthly_back_date(null);
					fengchelicaiAccInvest.setNext_back_date(null);
				}
				fengchelicaiAccInvest.setPayback_way(invest.getLoan()
						.getRepayType());
				fengchelicaiAccInvest.setAttorn_state(0);
				fengchelicaiAccInvest.setAttorn_time(null);
				resultList.add(fengchelicaiAccInvest);
			}
		}
		return resultList;
	}

	@Override
	public List<FengchelicaiNotice> getNoticeInfo(String page, String limit) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		String url = ReadProperties.getPropetiesValue(
				"constant/thiredPlatform.properties", "pc_url");
		List<FengchelicaiNotice> selectList = platformDao
				.getNotice(page, limit);
		List<FengchelicaiNotice> resultList = new ArrayList<FengchelicaiNotice>();
		for (FengchelicaiNotice fengchelicaiNotice : selectList) {
			fengchelicaiNotice.setUrl(url + "node/wangzhangonggao/"
					+ fengchelicaiNotice.getId() + ".htm");
			resultList.add(fengchelicaiNotice);
			Date date = null;
			try {
				date = sdf.parse(fengchelicaiNotice.getRelease_time());
				fengchelicaiNotice.setRelease_time(sdf.format(date));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		return resultList;
	}

	@Override
	public Map<String, Object> getAccInfo(String userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> selectMap = platformDao
				.queryUserEnableAccount(userId);
		resultMap.put("available_balance", selectMap.get("enableMoney"));
		resultMap.put("frozen_money", selectMap.get("frozen"));
		Double awardTotalMoney = platformDao.getAwardTotalMoney(userId);
		if (awardTotalMoney == null) {
			awardTotalMoney = 0.00;
		}
		double fillRate = platformDao.getRepayInterestTotalMoney(userId, "1");// 已完成的补息
		if ((awardTotalMoney - fillRate) < 0) {
			awardTotalMoney = 0.00;
		} else {
			awardTotalMoney = ArithUtil.round(
					ArithUtil.round(awardTotalMoney.doubleValue(), 2)
							- fillRate, 2);// 奖励总额-已完成的补息
		}
		Double reward = round(awardTotalMoney, 2);
		resultMap.put("reward", reward);

		Double investing_principal = round(
				platformDao.getRecycledMoney(userId, "还款中"), 2);
		double avalidMoney = round(
				platformDao.getDemandAvlidTreasureMoney(userId), 2);
		// double interest = platformDao.getDemandOutTreasureMoney;
		double principal = round(
				platformDao.getDemandOutTreasureMoney1(userId), 2);
		// 6本金
		Double investing_principal1 = ArithUtil.round(avalidMoney - principal,
				2);

		double profit = 0;
		profit = ArithUtil.round(
				platformDao.getInvestLoanDueInterest(userId, "!流标"), 2);
		if (!selectMap.get("frozen").equals("0")) {
			Double investing_principal2 = investing_principal
					+ investing_principal1;
			if (investing_principal2 > 0) {
				resultMap.put("investing_principal", investing_principal2);
			} else {
				resultMap.put("investing_principal", 0);
			}

		} else {
			resultMap.put("investing_principal", investing_principal
					+ investing_principal1);
		}

		resultMap.put("investing_interest", profit);
		Double earned_interest = round(
				platformDao.getInvestsTotalInterest(userId), 2);
		resultMap.put("earned_interest", earned_interest);
		Double current_money = round(platformDao.getDemandInMoney(userId), 2);
		resultMap.put("current_money", current_money);
		resultMap.put("all_balance", (Double) selectMap.get("enableMoney")
				+ (Double) selectMap.get("frozen") + investing_principal
				+ profit + current_money);
		return resultMap;
	}

	public static double progress(double totalMoney, double sumInvestMoney) {
		double p = 0;
		MathContext mc = new MathContext(3, RoundingMode.HALF_DOWN);
		BigDecimal b1 = new BigDecimal(totalMoney);
		BigDecimal b2 = new BigDecimal(sumInvestMoney);
		double rate = b2.divide(b1, mc).multiply(new BigDecimal(100), mc)
				.doubleValue();
		return rate;
	}

	/**
	 * 向风车理财推送投资记录
	 */
	@Override
	public void pushMethod(Map<String, Object> params) throws Exception {
		System.out.println("推送风车理财投资记录");
		String investId = params.get("id") + "";
		params.put("id", investId);
		params.put("platformType", "fengchelicai");
		List<Invest> investList = platformDao.queryInvest(params);
		this.pushInvest(investList.get(0));
	}

	/**
	 * 
	 * 推送投资记录
	 */
	public void pushInvest(Invest invest) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		params.put("type", "fengchelicai");
		params.put("investId", invest.getId());
		List<PushInvest> pushInvestList = platformPushDao
				.getPushInvestorsInfo(params);
		if (pushInvestList.size() <= 0) { // 判断是否推送过，如果推送过则不再推送
			FengchelicaiPushInvest pushInvest = new FengchelicaiPushInvest();
			pushInvest.setPf_user_id(invest.getUserId()); // 平台用户id
			pushInvest.setInvest_time(sdf.format(invest.getTime())); // 投资时间
			pushInvest.setInvest_sno(invest.getId()); // 投资记录流水号
			pushInvest.setInvest_money(invest.getMoney()); // 投资金额
			if ("天".equals(invest.getLoanOperationType())) {
				pushInvest
						.setInvest_limit(Integer.parseInt(invest.getLoanDay())); // 投资期限
																					// 按天计算
			} else {
				pushInvest.setInvest_limit(invest.getDeadline() * 30); // 投资期限
																		// 按天计算

			}
			pushInvest.setInvest_rate(invest.getRate() * 100); // 投资利率
			pushInvest.setBack_way(invest.getRepayType()); // 还款方式
			pushInvest.setInvest_title(invest.getLoanName()); // 投资项目标题
			Map<String, Object> resultMap = this.fengchelicaiInter(pushInvest);
			if (!"0".equals(resultMap.get("code") + "")) { // 推送投资记录失败
				System.out.println("###############风车理财推送投资记录出错："
						+ resultMap.get("result") + invest.getId());
				log.errLog(
						"风车理财推送投资记录",
						"风车理财推送投资记录出错：" + resultMap.get("result")
								+ invest.getId());
			} else { // 推送成功刚插入数据库
				PushInvest pushInvestData = new PushInvest();
				pushInvestData.setId(UUID.randomUUID().toString()
						.replace("-", ""));
				pushInvestData.setInvestId(invest.getId());
				pushInvestData.setUserId(invest.getUserId());
				pushInvestData.setLoanId(invest.getLoanId());
				pushInvestData.setAmount(invest.getMoney());
				pushInvestData.setInvestTime(invest.getTime());
				pushInvestData.setSendTime(new Date());
				pushInvestData.setInterest(invest.getInterest());
				pushInvestData.setInvestChannel(invest.getUserSource());
				pushInvestData.setType("fengchelicai");
				platformPushDao.insertPushInvest(pushInvestData);
			}
		}
	}

	/**
	 * 调用风车理财接口
	 */
	public Map<String, Object> fengchelicaiInter(
			FengchelicaiPushInvest pushInvest) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject objectJson = (JSONObject) JSONObject.toJSON(pushInvest);
		String param = "";
		for (String key : objectJson.keySet()) {
			param = param + key + "=" + objectJson.getString(key) + "&";
		}
		param = param.substring(0, param.length() - 1);
		String paramEncode = this.encodeParams(param);
		String url = ReadProperties.getPropetiesValue(
				"constant/thiredPlatform.properties", "fengchelicai_url")
				+ "/ws_info_call_back.json";
		JSONObject json = new JSONObject();
		json.put("from", "dr");
		json.put("param", paramEncode);
		json.put("ts", System.currentTimeMillis());
		JSONObject result = HttpUtil.platformExecutePost(json, url);
		String code = result.getJSONObject("content").getString("retcode");
		if ("0".equals(code)) { // 推送成功
			resultMap.put("code", "0");
			resultMap.put("result", "推送风车理财投资记录成功");
		} else {
			resultMap.put("code", "1");
			resultMap.put("result", "推送风车理财投资记录失败");
		}
		return resultMap;
	}

	/**
	 * 加密
	 */
	public String encodeParams(String params) {
		String result = "";
		try {
			String key = ReadProperties.getPropetiesValue(
					"constant/thiredPlatform.properties", "fengchelicai_key");
			result = WrbCoopDESUtil.desEncrypt(key, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void pushLoanAbout(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args){
		System.out.println(InterestUtil.getRFCLInterestByPeriodMoth(
				1000,
				0.09, 1));
		
	}

}
