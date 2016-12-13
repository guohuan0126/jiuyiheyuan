package com.duanrong.newadmin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import util.DateUtilPlus;
import util.Log;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.exception.InsufficientBalance;
import base.exception.InsufficientBalanceException;
import base.exception.InsufficientFreezeAmountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.PaymentEnum;
import com.duanrong.business.account.model.PaymentAccount;
import com.duanrong.business.account.model.PaymentChannel;
import com.duanrong.business.account.model.UserBill;
import com.duanrong.business.account.service.PaymentAccountService;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.invest.model.Encryption;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.repay.model.repayMent;
import com.duanrong.business.riskfund.model.Riskfund;
import com.duanrong.business.riskfund.model.RiskfundInfoDisclosure;
import com.duanrong.business.riskfund.model.Riskfunddetail;
import com.duanrong.business.riskfund.model.Riskloan;
import com.duanrong.business.riskfund.service.RiskfundInfoDisclosureService;
import com.duanrong.business.riskfund.service.RiskfundService;
import com.duanrong.business.riskfund.service.RiskfunddetailService;
import com.duanrong.business.riskfund.service.RiskloanService;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipQuery;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.dao.AccountCheckingDao;
import com.duanrong.business.user.model.AccountChecking;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserAccount;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.withdraw.service.WithdrawCashService;
import com.duanrong.business.yeepay.constants.GeneralTransferConstants;
import com.duanrong.business.yeepay.model.Freeze;
import com.duanrong.business.yeepay.model.TransactionAuthorization;
import com.duanrong.business.yeepay.model.TransactionAuthorizationDetail;
import com.duanrong.business.yeepay.model.Withhold;
import com.duanrong.business.yeepay.model.WithholdBank;
import com.duanrong.business.yeepay.service.FailLoanService;
import com.duanrong.business.yeepay.service.FreezeService;
import com.duanrong.business.yeepay.service.GeneralTransactionService;
import com.duanrong.business.yeepay.service.WithholdBankService;
import com.duanrong.business.yeepay.service.WithholdService;
import com.duanrong.newadmin.constants.FundConstants;
import com.duanrong.newadmin.controllhelper.SendData2YeePay;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.AES;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.newadmin.utility.IdUtil;
import com.duanrong.newadmin.utility.ToType;
import com.duanrong.payment.PaymentConstants;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.LoadConstantProterties2;
import com.duanrong.yeepay.service.TrusteeshipPlatformTransferService;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 首页C层
 * 
 * @author wangjing
 * @date 2014-11-19下午2:49:41
 */
@Controller
public class FoundController extends BaseController {

	@Resource
	RechargeService rechargeService;
	@Resource
	WithdrawCashService withdrawCashService;
	@Resource
	UserService userService;
	@Resource
	UserMoneyService userMoneyService;
	@Resource
	Log log;
	@Resource
	AccountCheckingDao accountCheckingDao;
	@Resource
	PlatformTransferService platformTransferService;

	@Resource
	TrusteeshipPlatformTransferService trusteeshipPlatformTransferService;

	@Resource
	FreezeService freezeService;
	@Resource
	InvestService investService;
	@Resource
	FailLoanService failLoanService;
	@Resource
	OperaRecordService operaRecordService;
	@Resource
	GeneralTransactionService generalTransactionService;
	@Resource
	RiskfundService riskfundService;
	@Resource
	RiskfunddetailService riskfunddetailService;
	@Resource
	LoanService loanService;
	@Resource
	RiskloanService riskloanService;
	@Resource
	WithholdService withholdService;
	@Resource
	WithholdBankService withholdBankService;
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	RiskfundInfoDisclosureService disclosureService;

	@Resource
	UserAccountService userAccountService;
	@Resource
	PlatformAccountService platformAccountService;
	@Resource
	PaymentAccountService paymentAccountService;

	/**
	 * 账户余额不平衡的用户信息
	 */
	@RequestMapping(value = "/found/accountCheckingList")
	public String accountCheckingList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}

				String start = request.getParameter("start");
				String end = request.getParameter("end");

				Map<String, Object> params = new HashMap<>();

				if (StringUtils.isNotBlank(start)) {
					start = DateUtil.StringToString(start, "mm/dd/yyyy",
							"yyyy-mm-dd");
					params.put("start", start);
				}
				if (StringUtils.isNotBlank(end)) {
					end = DateUtil.StringToString(end, "mm/dd/yyyy",
							"yyyy-mm-dd");
					params.put("end", end);
				}

				PageInfo<AccountChecking> pageInfo = accountCheckingDao
						.pageLite4Map(Integer.parseInt(pageNo), 10, params);
				request.setAttribute("pageInfo", pageInfo);
				return "found/accountCheckingList";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @description 账户管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account/accountList")
	public String accountList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {

			String realName = request.getParameter("realName");
			String id = request.getParameter("userId");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String user_Id = "";
			if (id != null) {
				user_Id = this.getUserId(id.trim());
			} else {
				user_Id = this.getUserId(id);
			}
			List<UserAccount> list = null;
			if (realName != null || id != null) {
				String userId = getUserId(user_Id);
				list = userService.readAccountInfo(userId, realName, start, end
						+ "  23:59");
				com.duanrong.business.account.model.UserAccount userAccount = userAccountService
						.readUserAccount(user_Id);
				List<UserBill> userBills = userAccountService
						.readUserBills(user_Id);
				model.addAttribute("userAccount", userAccount);
				model.addAttribute("userBills", userBills);
			}
			model.addAttribute("list", list);
			model.addAttribute("url", "/account/accountList");
			model.addAttribute("realName", realName);
			model.addAttribute("start", start);
			model.addAttribute("end", request.getParameter("end"));
			model.addAttribute("userId", id);
			return "found/accountList";

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					" com.duanrong.newadmin.controller.FoundController.accountList()",
					e);

		}
		return null;
	}

	/**
	 * 查询账号可用余额
	 * 
	 * @return
	 */
	@RequestMapping(value = "/account/userUsedMoney")
	public String getUserUsedMoeny(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String id = request.getParameter("userId");
		String user_Id = "";
		String time = request.getParameter("end");
		if (id != null) {
			user_Id = this.getUserId(id.trim());
		} else {
			user_Id = this.getUserId(id);
		}
		double usedMoney = userMoneyService.readUsedMoeny(user_Id, time);
		model.addAttribute("userId", id);
		model.addAttribute("time", time);
		model.addAttribute("usedMoney", usedMoney);
		return "found/accountList";

	}

	/**
	 * 
	 * @description 账户管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userbill/userbillList")
	public String userbillList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {

			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String str = "";
			String realname = request.getParameter("realname");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String id = request.getParameter("userId");
			String userId = getUserId(id);
			String name = "";
			Map<String, Object> params = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(userId)) {
				str += "&userId=" + userId;
				params.put("userId", userId);
			}
			if (StringUtils.isNotBlank(realname)) {
				name = java.net.URLDecoder.decode(realname, "UTF-8");
				params.put("realname", name);
				str += "&realname=" + name;

			}
			if (StringUtils.isNotBlank(start)) {
				params.put("start", start);
				str += "&start=" + start;
			}
			if (StringUtils.isNotBlank(end)) {
				String endtime = DateUtil.addDay(end, 1);
				params.put("end", endtime);
				str += "&end=" + end;
			}
			PageInfo pageInfo = userMoneyService.readPageInfo(
					Integer.parseInt(pageNo), 30, params);
			List<User> list = userMoneyService.readBorrowerUserId();

			model.addAttribute("list", list);
			model.addAttribute("url", "/userbill/userbillList");
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("start", start);
			model.addAttribute("end", end);
			model.addAttribute("realname", name);
			model.addAttribute("userId", id);
			model.addAttribute("str", str);
			return "found/userbillList";

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.userbillList()",
					e);
		}
		return null;

	}

	/**
	 * 跳转到用户资金流水下载页面
	 * 
	 */
	@RequestMapping(value = "/userbill/export")
	public String toUserBillListExport(Model model) {
		List<User> list = userMoneyService.readBorrowerUserId();
		model.addAttribute("list", list);
		return "found/userbillExport";

	}

	/**
	 * 
	 * @description 账户管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userbill/userbillListExport")
	public void userbillListExport(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String userId = request.getParameter("userId");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			userId = getUserId(userId);
			Map<String, Object> params = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(userId)) {
				params.put("userId", userId);
			}
			if (StringUtils.isNotBlank(start)) {
				params.put("start", start);
			}
			if (StringUtils.isNotBlank(end)) {
				String endtime = DateUtil.addDay(end, 1);
				params.put("end", endtime);
			}
			PageInfo pageInfo = userMoneyService.readPageInfo(1, 9999999,
					params);
			Map<String, String> title = new LinkedHashMap<>();
			title.put("userId", "用户编号");
			title.put("type", "费用类型");
			title.put("money", "金额(元)");
			title.put("typeInfo", "费用说明");
			title.put("detail", "费用详情");
			title.put("time", "时间");

			int[] style = { 16, 8, 8, 16, 22, 16 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "userbill" + userId + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			String t = "用户资金流水";
			excelConvertor.excelExport(pageInfo.getResults(), title, t, style);

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.userbillListExport()",
					e);
		}
	}

	/**
	 * 
	 * @description 跳转管理员干预页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userbill/toadd")
	public String toAdd(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 判断用户是否登录
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		}
		model.addAttribute("url", "/userbill/userbillList");
		model.addAttribute("addUrl", "/userbill/addUserBill");
		return "found/addUserBill";
	}

	/**
	 * 
	 * @description 管理员干预
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userbill/addUserBill", method = RequestMethod.POST)
	public ModelAndView addUserBill(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 判断用户是否登录
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return new ModelAndView("user/login");
		}
		try {
			String userId = request.getParameter("userId");
			String type = request.getParameter("type");
			String money = request.getParameter("money");
			String typeInfo = request.getParameter("typeInfo");
			String detail = request.getParameter("detail");
			String channel = request.getParameter("channel");
			String rechargeType = request.getParameter("rechargeType");
			double m = Double.parseDouble(money);
			String requestNo = IdUtil.randomUUID();
			PaymentAccount paymentAccount = paymentAccountService
					.readPaymentAccountByPaymentId(channel);
			com.duanrong.business.account.model.UserAccount userAccount = userAccountService
					.readUserAccount(userId);
			if (type.equals("ti_balance")) {
				userAccountService.transferIn(userId, m,
						BusinessEnum.transfer_in, typeInfo, "管理员-转入到余额,userId:"
								+ userId + "，" + detail, requestNo);
			} else if (type.equals("to_balance")) {
				if (userAccount.getAvailableBalance() < m) {
					throw new InsufficientBalanceException("用户可用余额不足");
				}
				userAccountService.transferOut(userId, m,
						BusinessEnum.transfer_out, typeInfo,
						"管理员-从余额转出,userId:" + userId + "，" + detail, requestNo);
			} else if (type.equals("freeze")) {
				if (userAccount.getAvailableBalance() < m) {
					throw new InsufficientBalanceException("用户可用余额不足");
				}
				userAccountService.freeze(userId, m, BusinessEnum.freeze,
						typeInfo, "管理员-冻结金额,userId:" + userId + "，" + detail,
						requestNo);
			} else if (type.equals("unfreeze")) {
				if (userAccount.getFreezeAmount() < m) {
					throw new InsufficientFreezeAmountException("用户冻结金额不足");
				}
				userAccountService.unfreeze(userId, m, BusinessEnum.unfreeze,
						typeInfo, "管理员-解冻金额,userId:" + userId + "，" + detail,
						requestNo);
			} else if (type.equals("to_frozen_normal")) {
				userAccountService.transferIn(userId, m, BusinessEnum.refund,
						typeInfo, "管理员-提现退款,userId:" + userId + "，" + detail,
						requestNo);
				platformAccountService.transferIn(2, BusinessEnum.refund,
						typeInfo + "，userId：" + userId, requestNo);
			} else if (type.equals("to_frozen_quick")) {
				double fee = 2;
				if (m > 4000) {
					fee = ArithUtil.round(m / 10000 * 5, 2);
				}
				userAccountService.transferIn(userId, m, BusinessEnum.refund,
						typeInfo, "管理员-提现退款,userId:" + userId + "，" + detail,
						requestNo);
				userAccountService.transferIn(userId, fee, BusinessEnum.refund,
						typeInfo, "管理员-加急提现退款手续费,userId:" + userId + "，"
								+ detail, requestNo);
			} else if (type.equals("ti_balance_recharge")) {
				double rate = 0.0;
				PaymentChannel paymentChannel = paymentAccountService
						.readChannelByCode(channel);
				if (MyStringUtils.equalsIgnoreCaseAnyString(rechargeType,
						"quick", null)) {
					rate = paymentChannel.getRateQuick();
				} else {
					rate = paymentChannel.getRateGateway();
				}
				double fee = ArithUtil.round(m * rate, 2);
				if ("Yeepay".equals(channel)) {
					userAccountService.transferIn(userId, m,
							BusinessEnum.transfer_in, typeInfo,
							"管理员-转入到余额（充值）,userId:" + userId + "，" + detail,
							requestNo);
					platformAccountService.transferOut(fee, BusinessEnum.fee,
							"管理员-转入到余额（充值）手续费，userId：" + userId + "，"
									+ typeInfo, requestNo);
				} else {
					if (ArithUtil.add(m, paymentAccount.getAvailableBalance()) < fee) {
						throw new InsufficientBalance("支付账户充值金额与可用金额之和小于手续费！");
					}
					userAccountService.transferIn(userId, m,
							BusinessEnum.transfer_in, typeInfo,
							"管理员-转入到余额（充值）,userId:" + userId + "，" + detail,
							requestNo);
					platformAccountService.transferOut(fee, BusinessEnum.fee,
							"管理员-转入到余额（充值）手续费，userId：" + userId + "，"
									+ typeInfo, requestNo);
					paymentAccountService.transferIn(
							PaymentEnum.valueOf(channel), m,
							BusinessEnum.recharge, "管理员-转入到余额（充值），userId："
									+ userId + "，" + typeInfo, requestNo);
					paymentAccountService.transferOut(
							PaymentEnum.valueOf(channel), fee,
							BusinessEnum.fee, "管理员-转入到余额（充值）手续费，userId："
									+ userId + "，" + typeInfo, requestNo);
				}
			} else if (type.equals("to_balance_withdraw")) {
				double rate = 0.0;
				PaymentChannel paymentChannel = paymentAccountService
						.readChannelByCode(channel);
				if (MyStringUtils.equalsIgnoreCaseAnyString(rechargeType,
						"quick", null)) {
					rate = paymentChannel.getRateQuick();
				} else {
					rate = paymentChannel.getRateGateway();
				}
				double fee = ArithUtil.round(m * rate, 2);
				if (userAccount.getAvailableBalance() < m) {
					throw new InsufficientBalanceException("用户可用余额不足");
				}
				if ("Yeepay".equals(channel)) {
					userAccountService.transferOut(userId, m,
							BusinessEnum.transfer_out, typeInfo,
							"管理员-从余额转出（提现）,userId:" + userId + "，" + detail,
							requestNo);
					platformAccountService.transferOut(fee, BusinessEnum.fee,
							"管理员-从余额转出（提现）手续费，userId：" + userId + "，"
									+ typeInfo, requestNo);
				} else {
					if (ArithUtil.add(m, fee) > paymentAccount
							.getAvailableBalance()) {
						throw new InsufficientBalance("支付账户充值金额与可用金额之和小于手续费！");
					}
					userAccountService.transferOut(userId, m,
							BusinessEnum.transfer_out, typeInfo,
							"管理员-从余额转出（提现）,userId:" + userId + "，" + detail,
							requestNo);
					platformAccountService.transferOut(fee, BusinessEnum.fee,
							"管理员-从余额转出（提现）手续费，userId：" + userId + "，"
									+ typeInfo, requestNo);
					paymentAccountService.transferOut(
							PaymentEnum.valueOf(channel), m,
							BusinessEnum.withdraw_cash, "管理员-从余额转出（提现），userId："
									+ userId + "，" + typeInfo, requestNo);
					paymentAccountService.transferOut(
							PaymentEnum.valueOf(channel), fee,
							BusinessEnum.fee, "管理员-从余额转出（提现）手续费，userId："
									+ userId + "，" + typeInfo, requestNo);
				}
			}
			response.getWriter().print("ok");
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.addUserBill()",
					e);

		}
		return null;
	}

	/**
	 * 
	 * @description 账户管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/platform/platformList")
	public String platformList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				String str = "";
				String mnumber = request.getParameter("mnumber");
				String realname = request.getParameter("realname");
				String start = request.getParameter("start");
				String end = request.getParameter("end");
				String userId = request.getParameter("userId");
				String name = "";
				Map<String, Object> params = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(mnumber)) {
					str += "&mnumber=" + mnumber;
					params.put("mnumber", mnumber);
				}
				if (StringUtils.isNotBlank(userId)) {
					str += "&userId=" + userId;
					params.put("userId", userId);
				}
				if (StringUtils.isNotBlank(realname)) {
					name = java.net.URLDecoder.decode(realname, "UTF-8");
					params.put("realname", name);
					str += "&realname=" + name;

				}
				if (StringUtils.isNotBlank(start)) {
					params.put("start", start);
					str += "&start=" + start;
				}
				if (StringUtils.isNotBlank(end)) {					
					String endtime = DateUtil.addDay(end, 1);
					params.put("end", endtime);
					str += "&end=" + end;
				}
				PageInfo<PlatformTransfer> pageInfo = platformTransferService.readPageInfo(
						Integer.parseInt(pageNo), 10, params);
				model.addAttribute("url", "/platform/platformList");
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("mnumber", mnumber);
				model.addAttribute("start", start);
				model.addAttribute("end", end);
				model.addAttribute("realname", name);
				model.addAttribute("userId", userId);
				model.addAttribute("str", str);
				return "found/platformList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.platformList()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description 冻结解冻
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/freeze/freezeList")
	public String freezeList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				PageInfo<Freeze> pageInfo = freezeService.readPageInfo(
						Integer.parseInt(pageNo), 10);
				List<Freeze> freezes = null;
				if (pageInfo.getResults().size() > 0) {
					freezes = pageInfo.getResults();
					for (Freeze freeze : freezes) {
						if (freeze != null) {
							com.duanrong.business.account.model.UserAccount userAccount = userAccountService.readUserAccount(freeze.getUserId());						
							UserAccount account = new UserAccount();
							account = userService.getCallAccountInfo(
									freeze.getUserId(), account);
							freeze.setBalance(userAccount.getAvailableBalance());
							freeze.setFreezeMoney(userAccount.getFreezeAmount());
							freeze.setYeepaybalance(account
									.getAvailableAmount());
							freeze.setYeepayfreezeMoney(account
									.getFreezeAmount());
						}
					}
				}
				pageInfo.setResults(freezes);
				model.addAttribute("url", "/freeze/freezeList");
				model.addAttribute("pageInfo", pageInfo);
				return "yeepay/freezeList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.freezeList()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description 冻结解冻
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/freeze/freezeMoney")
	public String freezeMoney(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				try {
					response.sendRedirect("user/login");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Freeze freeze = new Freeze();
				String userId = request.getParameter("userId");
				String description = request.getParameter("description");
				String expiredTime = request.getParameter("expiredTime");
				String money = request.getParameter("money");
				freeze.setId(IdUtil.generateId(ToType.FROZ));
				freeze.setFreezeTime(new Date());
				freeze.setFreezeUserId(getLoginUser.getUserId());
				freeze.setStatus(FundConstants.Status.UNFROZE);
				freeze.setDescription(description);
				freeze.setExpiredTime(DateUtil.StringToDate(expiredTime));
				freeze.setMoney(Double.parseDouble(money));
				freeze.setUserId(userId);
				String result = "";
				try {
					result = freezeService.freeze(freeze);
				} catch (InsufficientBalance e) {
					if (e.getMessage().contains("balance")) {
						response.getWriter().print("balance");
					} else if (e.getMessage().contains("unfreeze")) {
						response.getWriter().print("unfreeze");
					} else {
						response.getWriter().print("fail");
					}
				}
				model.addAttribute("url", "/freeze/freezeList");
				response.getWriter().print(result);
			}
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.freezeMoney()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description 冻结解冻
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/unfreeze/tounfreeze")
	public void tounfreeze(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				try {
					response.sendRedirect("user/login");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				String id = request.getParameter("id");
				Freeze freeze = freezeService.read(id);
				freeze.setUnfreezeUserId(getLoginUser.getUserId());
				freeze.setUnFreezeTime(new Date());
				String result = "";
				try {
					result = freezeService.unfreeze(freeze);
				} catch (InsufficientBalance e) {
					if (e.getMessage().contains("balance")) {
						response.getWriter().print("balance");
					} else if (e.getMessage().contains("unfreeze")) {
						response.getWriter().print("unfreeze");
					} else {
						response.getWriter().print("fail");
					}
				}
				model.addAttribute("url", "/freeze/freezeList");
				response.getWriter().print(result);
			}
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.freezeMoney()",
					e);
		}
	}

	/**
	 * 
	 * @description 流标
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/failLoan/failLoanList")
	public String failLoanList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				response.sendRedirect("user/login");
			} else {

				String id = request.getParameter("investId");
				Invest invest = investService.readInvest(id);
				JSONObject jsonArr = JSONObject.fromObject(invest);
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(jsonArr.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.failLoanList()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description
	 * @author wangjing
	 * @time 2014-11-18 下午3:36:10
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/failLoan/tofailLoan", method = RequestMethod.GET)
	public String tofailLoan(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";

			} else {
				model.addAttribute("url", "/failLoan/tofailLoan");
				return "yeepay/failLoanList";
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.pcweb.controller.FoundController.tofailLoan().IOException",
					e);
			return "user/login";

		}
	}
	
	
	@RequestMapping(value = "/failLoan/localfailLoanBill")
	public String localFailLoanBill(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				response.sendRedirect("user/login");
			} else {

				String id = request.getParameter("investId");
				Invest invest = investService.readInvest(id);

				String result = "";
				/*if (invest == null || invest.getStatus() == null
						|| !invest.getStatus().equals("投标成功")) {
					response.getWriter().print("投资状态不正确");
					return null;
				}*/
				try {
					failLoanService.failLoan2(invest);
					operaRecordService.insertRecord("单个接口流标",
							getLoginUser.getUserId(), "投资流水号：" + id);
				} catch (InsufficientBalance e) {
					if (e.getMessage().contains("balance")) {
						response.getWriter().print("balance");
					} else if (e.getMessage().contains("unfreeze")) {
						response.getWriter().print("unfreeze");
					} else {
						response.getWriter().print("fail");
					}
				}
				model.addAttribute("url", "/failLoan/tofailLoan");
				response.getWriter().print(result);

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.failLoanBill()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description 流标
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/failLoan/failLoanBill")
	public String failLoanBill(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				response.sendRedirect("user/login");
			} else {

				String id = request.getParameter("investId");
				Invest invest = investService.readInvest(id);

				String result = "";
				if (invest == null || invest.getStatus() == null
						|| !invest.getStatus().equals("投标成功")) {
					response.getWriter().print("投资状态不正确");
					return null;
				}
				try {
					result = failLoanService.failLoan(invest);
					operaRecordService.insertRecord("单个接口流标",
							getLoginUser.getUserId(), "投资流水号：" + id);
				} catch (InsufficientBalance e) {
					if (e.getMessage().contains("balance")) {
						response.getWriter().print("balance");
					} else if (e.getMessage().contains("unfreeze")) {
						response.getWriter().print("unfreeze");
					} else {
						response.getWriter().print("fail");
					}
				}
				model.addAttribute("url", "/failLoan/tofailLoan");
				response.getWriter().print(result);

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.failLoanBill()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description 流标
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/failLoan/failLoanBill/yeepay")
	public String failLoanBillForYeepay(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				response.sendRedirect("user/login");
			} else {
				String id = request.getParameter("id");

				String result = failLoanService.sendV2(id);
				operaRecordService.insertRecord("单个接口流标",
						getLoginUser.getUserId(), "投资流水号：" + id);
				model.addAttribute("url", "/failLoan/tofailLoan");
				response.getWriter().print(result);
			}
		} catch (Exception e) {

			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.failLoanBill()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description
	 * @author wangjing
	 * @time 2014-11-18 下午3:36:10
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trans/authList")
	public String authList(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";

			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				String str = "";
				String id = request.getParameter("userId");
				String userId = getUserId(id);
				// String name = "";
				Map<String, Object> params = new HashMap<String, Object>();

				if (StringUtils.isNotBlank(userId)) {
					str += "&userId=" + userId;
					params.put("userId", userId);
				}
				PageInfo<TransactionAuthorization> pageInfo = generalTransactionService
						.readPageInfo(Integer.parseInt(pageNo), 10, params);
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("userId", id);
				model.addAttribute("str", str);
				model.addAttribute("url", "/trans/authList");
				return "yeepay/authList";
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.pcweb.controller.FoundController.authList().IOException",
					e);
			return "user/login";

		}
	}

	/**
	 * 
	 * @description
	 * @author wangjing
	 * @time 2014-11-18 下午3:36:10
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trans/authdetail")
	public String authdetail(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";

			} else {
				String tranid = request.getParameter("tranid");
				List<TransactionAuthorizationDetail> details = generalTransactionService
						.readDetails(tranid);
				model.addAttribute("details", details);
				return "yeepay/authdetail";
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.pcweb.controller.FoundController.authdetail().IOException",
					e);
			return "user/login";

		}
	}

	@RequestMapping(value = "/trans/authconfirm")
	public void authConfirm(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie cookie = UserCookieHelper.GetUserCookie(request, response);
		String tranid = request.getParameter("tranid");
		String flag = request.getParameter("flag");
		if (MyStringUtils.isNotAnyBlank(tranid)) {
			try {
				operaRecordService.insertRecord("转账", cookie.getUserId(),
						"请求号：" + tranid);
				generalTransactionService.authConfirm(tranid, flag);
				response.getWriter().print("ok");
			} catch (InsufficientFreezeAmountException e) {
				log.errLog("转账确认错误", "转账ID" + tranid + "reason:余额不足.");
			} catch (IOException e) {
				log.errLog("转账确认错误" + tranid, e);
			} catch (Exception e) {
				log.errLog("转账确认错误" + tranid, e);
			}
		}
	}

	/**
	 * 
	 * @description 保障金管理
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/risk/riskList")
	public String riskList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				List<Riskfund> risks = riskfundService.readAll();
				model.addAttribute("risks", risks);
				return "found/riskList";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/risk/toedit")
	public String toedit(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (type != null && !type.equals("")) {
			if (type.equals("vehicle")) {
				params.put("type", "车贷");
				map.put("type", "车贷");
			} else if (type.equals("house")) {
				params.put("type", "房贷");
				map.put("type", "房贷");
			} else if (type.equals("enterprise")) {
				params.put("type", "企业贷");
				map.put("type", "企业贷");
			} else if (type.equals("supplychain")) {
				params.put("type", "供应宝");
				map.put("type", "供应宝");
			} else if (type.equals("ruralfinance")) {
				params.put("type", "金农宝");
				map.put("type", "金农宝");
			} else {
				params.put("type", "all");
				map.put("type", "all");
			}
		}
		map.put("status", "还款中");
		params.put("status", "流标");
		double totalmoney = loanService.readTotalMoney(params);
		double remoney = loanService.readTotalMoney(map);
		Riskfund risk = riskfundService.read(Integer.parseInt(id));
		List<Riskfunddetail> details = riskfunddetailService.read(risk.getId());
		model.addAttribute("totalmoney", totalmoney);
		model.addAttribute("remoney", remoney);
		model.addAttribute("risk", risk);
		model.addAttribute("details", details);
		model.addAttribute("num", details.size());
		return "found/editrisk";
	}

	@RequestMapping(value = "/risk/updateRisk", method = RequestMethod.POST)
	public void updateRisk(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String riskid = request.getParameter("riskid");
		String overmoney = request.getParameter("overmoney");
		String totalmoney = request.getParameter("totalmoney");
		String remoney = request.getParameter("remoney");
		String spotmoney = request.getParameter("spotmoney");
		String details = request.getParameter("details");
		List<Riskfunddetail> rf = null;
		if (MyStringUtils.isNumeric(riskid)) {
			Riskfund r = new Riskfund();
			r.setId(Integer.parseInt(riskid));
			r.setOvermoney(Double.parseDouble(overmoney));
			r.setTotalmoney(Double.parseDouble(totalmoney));
			r.setSpotmoney(Double.parseDouble(spotmoney));
			r.setDuemoney(Double.parseDouble(remoney));
			r.setTime(new Date());
			if (MyStringUtils.isNotAnyBlank(details)) {
				if (details.substring(details.length() - 1).equals(";")) {
					details = details.substring(0, details.length() - 1);
				}
				rf = toDetailFromStr(details);
			}
			try {
				riskfundService.updateRiskfund(r, rf);
				response.getWriter().print("ok");
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().print("error");
			}
		} else {
			response.getWriter().print("error");
		}
	}

	private List<Riskfunddetail> toDetailFromStr(String details) {
		String[] risks = details.split(";");
		List<Riskfunddetail> list = new ArrayList<>();
		for (int i = 0; i < risks.length; i++) {
			String[] risk = risks[i].split("、");
			Riskfunddetail rf = new Riskfunddetail();
			rf.setId(Integer.parseInt(risk[0]));
			// rf.setOvertime(risk[1]);
			rf.setOvermoney(Double.parseDouble(risk[2]));
			rf.setOverrate(Double.parseDouble(risk[3]));
			rf.setRecyclingmoney(Double.parseDouble(risk[4]));
			rf.setRecyledmoney(Double.parseDouble(risk[5]));
			rf.setTime(new Date());
			list.add(rf);
		}
		return list;
	}

	@RequestMapping(value = "/user/savereferrer", method = RequestMethod.POST)
	public String savereferrer(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String totalmoney = request.getParameter("totalmoney");
		String id = request.getParameter("id");
		try {
			Riskfund r = new Riskfund();
			r.setTotalmoney(Double.parseDouble(totalmoney));
			r.setId(Integer.parseInt(id));
			r.setTime(new Date());
			riskfundService.update(r);
			return forward + "/risk/riskList";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/risk/todetail")
	public String todetail(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return "found/detailrisk";
	}

	@RequestMapping(value = "/found/detailList")
	public void detailList(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String detailid = request.getParameter("detailid");
		List<Riskloan> list = riskloanService.readList(Integer
				.parseInt(detailid));
		response.setCharacterEncoding("utf-8");
		Object[] array = new Object[2];
		array[0] = list;
		array[1] = detailid;
		JSONArray jsonArr = JSONArray.fromObject(array);
		response.getWriter().print(jsonArr.toString());
	}

	@RequestMapping(value = "/found/deldetail")
	public void delrmark(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		try {
			riskloanService.delete(Integer.parseInt(id));
		} catch (Exception e) {
			response.getWriter().print("fail");
			e.printStackTrace();
		}
		response.getWriter().print("ok");
	}

	@RequestMapping(value = "/found/withhold")
	public String withhold(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				Withhold hold = new Withhold();
				if (!getLoginUser.getUserId().equals("admin")) {
					hold.setUserId(getLoginUser.getUserId());
				}
				hold.setStatus(1);
				PageInfo pageInfo = withholdService.readPageInfo(
						Integer.parseInt(pageNo), 10, hold);
				model.addAttribute("pageInfo", pageInfo);
				return "found/withhold";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/found/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String loanName = request.getParameter("loanName");
		String id = request.getParameter("id");
		Loan l = new Loan();
		l.setName(loanName);
		List<Loan> list = loanService.readLoansByGroupCondition(l);
		if (list != null && list.size() == 1) {
			try {
				Riskloan risk = new Riskloan();
				risk.setDetailid(Integer.parseInt(id));
				risk.setLoanId(list.get(0).getId());
				risk.setLoanName(list.get(0).getName());
				risk.setLoanmoney(list.get(0).getTotalmoney());
				risk.setTime(new Date());
				risk.setStatus(1);
				riskloanService.insert(risk);

			} catch (Exception e) {
				response.getWriter().print("fail");
				e.printStackTrace();
			}
			response.getWriter().print(id);
		} else {
			response.getWriter().print("loan");
			return;
		}
	}

	/**
	 * 删除流程
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/found/withholdDel")
	public void withholdDel(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String id = request.getParameter("id");
		if (withholdService.withholdDel(id)) {
			response.getWriter().print("ok");
		} else {
			response.getWriter().print("error");
		}

	}

	@RequestMapping(value = "/found/withCardDel")
	public void withCardDel(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String id = request.getParameter("id");
		if (withholdBankService.delete(id)) {
			response.getWriter().print("ok");
		} else {
			response.getWriter().print("error");
		}
	}

	@RequestMapping(value = "/found/withhold/toadd")
	public String holdtoAdd(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "found/addHold";
	}

	@RequestMapping(value = "/found/withhold/add", method = RequestMethod.POST)
	public void addHold(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {

			String mobileNum = request.getParameter("name");
			User user = userService.readUserByMobileNumber(mobileNum);
			if (user == null) {
				response.getWriter().print("noexist");
				return;
			}
			Withhold withhold = new Withhold();
			withhold.setUserId(user.getUserId());
			withhold.setStatus(1);
			PageInfo p = withholdService.readPageInfo(1, 1000, withhold);
			if (p != null && p.getTotalRecord() > 0) {
				response.getWriter().print("notNull");
			} else {
				withhold.setId(IdGenerator.randomUUID());
				withhold.setCard(user.getIdCard());
				withhold.setMobileNum(mobileNum);
				withhold.setRealname(user.getRealname());
				withhold.setStatus(1);
				withhold.setTime(new Date());
				withholdService.insert(withhold);
				response.getWriter().print("ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/found/withholdBank/toadd")
	public String banktoAdd(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String userId = request.getParameter("id");
		model.addAttribute("userId", userId);
		return "found/addBank";
	}

	@RequestMapping(value = "/found/withhold/addBank", method = RequestMethod.POST)
	public void addBank(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String userId = request.getParameter("userId");
			String bankCard = request.getParameter("name");
			String bankCode = request.getParameter("bank");
			String bankName = "";
			WithholdBank withholdBank = new WithholdBank();
			withholdBank.setStatus(1);
			withholdBank.setBankCount(bankCard);
			List<WithholdBank> banks = withholdBankService
					.readList(withholdBank);
			if (banks != null && banks.size() > 0) {
				response.getWriter().print("noexist");
				return;
			}
			if (bankCode.equals("103")) {
				bankName = "中国农业银行";
			}
			if (bankCode.equals("104")) {
				bankName = "中国银行";
			}
			if (bankCode.equals("105")) {
				bankName = "中国建设银行";
			}
			if (bankCode.equals("301")) {
				bankName = "交通银行";
			}
			withholdBank.setId(IdGenerator.randomUUID());
			withholdBank.setUserId(userId);
			withholdBank.setBankCode(bankCode);
			withholdBank.setBankName(bankName);
			withholdBank.setTime(new Date());
			withholdBank.setStatus(1);
			withholdBankService.insert(withholdBank);
			response.getWriter().print("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @RequestMapping(value = "/found/withRecharg")
	public void withRecharg(HttpServletResponse response,
			HttpServletRequest request) {
		String bankid = request.getParameter("bankid");
		String amount = request.getParameter("amount");
		String userId = request.getParameter("userId");
		WithholdBank withholdBank = new WithholdBank();
		withholdBank.setId(bankid);
		withholdBank.setStatus(1);
		List<WithholdBank> p = withholdBankService.readList(withholdBank);
		try {
			if (p == null || p.size() != 1) {
				response.getWriter().print("bank");
				return;
			} else if (p.get(0).getUserId() == null) {
				response.getWriter().print("user");
				return;
			} else if (!userId.equals(p.get(0).getUserId())) {
				response.getWriter().print("usereq");
				return;
			} else {
				User user = userService.read(p.get(0).getUserId());
				if (user == null) {
					response.getWriter().print("nouser");
					return;
				}
				operaRecordService.insertRecord("代扣", userId, "银行卡号："
						+ p.get(0).getBankCount() + "金额：" + amount);
				/*
				 * String desc = withholdBankService.withHold(p.get(0), amount,
				 * user);
				 */
				response.getWriter().print("no");
			}
		} catch (IOException e) {
			log.errLog("代扣错误", "reason:IOException.");
		} catch (Exception e) {
			log.errLog("代扣错误", "reason:IOException.");
		}
	}

	@RequestMapping(value = "/account/query")
	public String query(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String stype = request.getParameter("stype");
			String content = request.getParameter("content");
			if (content != null && !"".equals(content)) {
				TrusteeshipQuery t = trusteeshipOperationService.query(content,
						stype);
				model.addAttribute("query", t);
			}
			model.addAttribute("content", content);
			model.addAttribute("stype", stype);
			return "found/queryList";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					" com.duanrong.newadmin.controller.FoundController.query()",
					e);

		}
		return null;
	}

	@RequestMapping(value = "/trans/toCancle")
	public String toTrans(HttpServletRequest request,
			HttpServletResponse response) {

		return "found/trans";
	}

	@RequestMapping(value = "/trans/cancel")
	public void transCancel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String reqNo = request.getParameter("reqNo");
		String type = request.getParameter("type");
		try {
			String result = trusteeshipPlatformTransferService
					.confirmTransferTrusteeship(reqNo, type);
			if ("seccess".equals(result)) {
				response.getWriter().print("ok");
			} else if ("fail".equals(result)) {
				response.getWriter().print("fail");
			} else {
				response.getWriter().print("req");
			}
		} catch (Exception e) {
			response.getWriter().print("sys");
		}
	}

	/**
	 * 内部资金转账页面（个人对个人，个人对公司）
	 */
	@RequestMapping(value = "/fund/transfermoney")
	public ModelAndView TransferFund() {
		ModelAndView mv = new ModelAndView("found/transfermoney");
		return mv;
	}

	/**
	 * 内部资金转账页面（个人对个人，个人对公司）
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/fund/starttransfermoney", method = RequestMethod.POST)
	public ModelAndView StartTransferFund(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String accountOut = request.getParameter("txt_accountout").trim();
		String accountIn = request.getParameter("txt_accountin").trim();
		String tempMoney = request.getParameter("txt_money").trim();

		ModelAndView mv = new ModelAndView("found/transfermoney");
		if (accountOut.length() != 11 || accountIn.length() != 11) {
			mv.addObject("error", "转入账户或者转出账户无效");
			return mv;
		}
		double money = 0;
		try {
			money = Double.parseDouble(tempMoney);
			if (money <= 0) {
				mv.addObject("error", "金额输入无效");
				return mv;
			}
		} catch (Exception e) {
			mv.addObject("error", "金额输入无效");
			return mv;
		}

		// 转出账户
		User userOut = userService.readUserByMobileNumber(accountOut);
		if (userOut == null) {
			mv.addObject("error", "转出账户无效");
			return mv;
		}
		// 转入账户
		User userIn = new User();
		if (accountIn.equals("10012401196")) {

			userIn.setUserId("10012401196");
			userIn.setUserType("MERCHANT");
		} else {
			User tempuser = userService.readUserByMobileNumber(accountIn);// 转出账户
			if (tempuser == null) {
				mv.addObject("error", "转入账户无效");
				return mv;
			}
			userIn.setUserId(tempuser.getUserId());
			userIn.setUserType("MEMBER");
		}
		TransactionAuthorization ta = new TransactionAuthorization();
		String taid = IdUtil.generateId(ToType.GETH);// 主键，流水号
		Date expired = DateUtils.addDays(new Date(), 1);
		try {
			ta.setUserId(userOut.getUserId());
			ta.setId(taid);
			ta.setAmount(money);
			ta.setExpired(expired);
			ta.setCommitTime(new Date());
			ta.setStatus(GeneralTransferConstants.TransferStatus.WAIT);
			TransactionAuthorizationDetail tad = new TransactionAuthorizationDetail();
			tad.setAmount(money);
			tad.setTransactionAuthorizationId(taid);
			tad.setUserId(userIn.getUserId());
			tad.setStatus(GeneralTransferConstants.TransferStatus.WAIT);

			List<TransactionAuthorizationDetail> listDs = new ArrayList<TransactionAuthorizationDetail>();
			listDs.add(tad);
			ta.setTransactionAuthorizationDetails(listDs);

			generalTransactionService.insertGeneralTransaction(ta);

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("添加转账记录失败", e);
			mv.addObject("error", "插入转账记录失败");
			return mv;
		}
		String content = "";
		try {
			/*********************** XML拼接 ***********************/
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			// 商户编号:商户在易宝唯一标识
			xml.append("<request platformNo=\""
					+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
			// 请求流水号:接入方必须保证参数内的 requestNo 全局唯一，并且需要保存，留待后续业务使用
			xml.append("<requestNo>" + taid + "</requestNo>");
			// 出款人ID
			xml.append("<platformUserNo>" + userOut.getUserId().trim()
					+ "</platformUserNo>");
			// 出款人类型（个人会员）
			xml.append("<userType>MEMBER</userType>");
			xml.append("<bizType>TRANSFER</bizType>");
			xml.append("<expired>" + DateUtilPlus.formatDateTime(expired)
					+ "</expired>");

			// 转入账户
			xml.append("<details>");
			xml.append("<detail>");
			// 转入金额
			xml.append(MyStringUtils.append("<amount>", money, "</amount>"));
			// 收款人类型，个人或者企业
			xml.append("<targetUserType>" + userIn.getUserType()
					+ "</targetUserType>");
			// 收款人ID
			xml.append("<targetPlatformUserNo>" + userIn.getUserId()
					+ "</targetPlatformUserNo>");
			xml.append("<bizType>TRANSFER</bizType>");
			xml.append("</detail>");
			xml.append("</details>");

			xml.append("<notifyUrl>"
					+ new StringBuffer(
							TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL)
							.append("geth") + "</notifyUrl>");
			// 页面回跳URL
			xml.append("<callbackUrl>"
					+ LoadConstantProterties2
							.getValueByDefaultPro("ResponseWebUrl_PRE_RESPONSE_URL")
					+ "fund/THAN" + "</callbackUrl>");
			xml.append("</request>");
			content = xml.toString();
		} catch (Exception e) {
			log.errLog("拼接转账xml失败", e);
			mv.addObject("error", "拼接转账xml失败");
			e.printStackTrace();
			return mv;
		}

		System.out.println(content);
		String sign = "";
		try {
			sign = CFCASignUtil.sign(content);
		} catch (Exception e) {
			log.errLog("签名失败", e);
			mv.addObject("error", "签名失败");
			e.printStackTrace();
			return mv;
		}

		log.infoLog("直接转账XML", content);
		log.infoLog("直接转账sign", sign);
		// insert to record
		TrusteeshipOperation to = new TrusteeshipOperation();
		to.setId(IdGenerator.randomUUID());
		to.setMarkId(taid);
		to.setOperator(taid);
		to.setRequestTime(new Date());
		to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.TO_CP_TRANSACTION);
		to.setRequestData(sign + "========" + content);
		to.setType(TrusteeshipYeepayConstants.OperationType.TO_CP_TRANSACTION);
		to.setTrusteeship("yeepay");
		to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
		// 需要设置userId，进行记录
		to.setUserId(userOut.getUserId());
		trusteeshipOperationService.insert(to);

		try {
			SendData2YeePay.sendOperation(content, ToType.THAN, response, sign);
		} catch (Exception e) {
			log.errLog("拼接转账xml失败", e);
			mv.addObject("error", "拼接转账xml失败");
			e.printStackTrace();
			return mv;
		}
		return null;
	}

	@RequestMapping(value = "/fund/THAN")
	public String tranauthCallBack(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		return redirect + "/trans/authList";
		/*
		 * try { // 响应的参数 为xml格式 String respXML = request.getParameter("resp");
		 * // 签名 String sign = request.getParameter("sign");
		 */

		/*
		 * if (respXML == null || sign == null) {
		 * 
		 * return redirect+"trans/authList"; } if
		 * (CFCASignUtil.isVerifySign(respXML, sign) == false) {
		 * 
		 * return redirect+"trans/authList"; } // 将xml个数数据转换成map
		 * 
		 * @SuppressWarnings("unchecked") Map<String, String> resultMap =
		 * Dom4jUtil.xmltoMap(respXML); String requestNo =
		 * resultMap.get("requestNo"); String code = resultMap.get("code");
		 * String description = resultMap.get("description");
		 * log.infoLog(requestNo+"转账callback返回值", respXML); if
		 * ("1".equals(code)) {
		 * 
		 * return redirect+"trans/authList"; }else{
		 * 
		 * return redirect+"trans/authList"; } } catch (Exception e) {
		 * log.errLog(
		 * "充值CallBack:com.duanrong.pcweb.controller TranauthController.tranauthCallBack().Exception"
		 * , e); e.printStackTrace(); return redirect+"trans/authList"; }
		 */
	}

	/**
	 * 内部资金转账页面（个人对个人，个人对公司）
	 */
	@RequestMapping(value = "/fund/getuserinfo/{m}", method = RequestMethod.POST)
	@ResponseBody
	public String GetuserInfoByMobile(@PathVariable String m) {
		if (m.equals("10012401196")) {
			return "久亿财富易宝账户";
		}
		if (m == null || m.length() != 11) {
			return "手机号无效";
		}
		User user = userService.readUserByMobileNumber(m);
		if (user != null) {
			return "用户编号：" + user.getUserId() + ",真实姓名：" + user.getRealname();
		} else {
			return "该手机号对应用户不存在";
		}
	}

	/**
	 * 获取30天前的日期
	 * 
	 * @return
	 */
	public static String getLastMonthData() {
		// Java获取当前日期的前一个月,前一天时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);// 得到前一天
		calendar.add(Calendar.MONTH, -1);// 得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		String data = year + "-" + month + "-" + date;
		return data;
	}

	/**
	 * 信息披露管理列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/risk/infoDisclosureList")
	public String infoDisclosureList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String start = request.getParameter("start");
				String end = request.getParameter("end");
				Map<String, Object> params = new HashMap<>();
				String str = "";
				if (start != null && !start.equals("")) {
					str += "&start=" + start;
					params.put("start", start);
				}
				if (end != null && !end.equals("")) {
					str += "&end=" + end;
					params.put("end", end + " 23:59:59");
				}
				List<RiskfundInfoDisclosure> disclosureList = disclosureService
						.readList(params);
				model.addAttribute("url", "risk/infoDisclosureList");
				model.addAttribute("disclosureList", disclosureList);
				model.addAttribute("start", start);
				model.addAttribute("end", end);
				model.addAttribute("str", str);
				return "found/infoDisclosureList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					" com.duanrong.newadmin.controller.RiskfundDisclosureController.infoDisclosureList()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/risk/addInfoDisclosure")
	public ModelAndView addInfoDisclosure(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return new ModelAndView("found/addInfoDisclosure");
	}

	/**
	 * 添加信息披露
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/risk/saveInfoDisclosure", method = RequestMethod.POST)
	public void saveInfoDisclosure(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			RiskfundInfoDisclosure infoDisclosure = new RiskfundInfoDisclosure();
			String currentTotalOverdue = request
					.getParameter("currentTotalOverdue");
			String historyTotalOverdue = request
					.getParameter("historyTotalOverdue");
			String badDebtOverdue = request.getParameter("badDebtOverdue");
			infoDisclosure.setId(IdGenerator.randomUUID());
			if (!StringUtils.isBlank(currentTotalOverdue)) {
				infoDisclosure.setCurrentTotalOverdue(Double
						.parseDouble(currentTotalOverdue));
			}
			if (!StringUtils.isBlank(historyTotalOverdue)) {
				infoDisclosure.setHistoryTotalOverdue(Double
						.parseDouble(historyTotalOverdue));
			}
			if (!StringUtils.isBlank(badDebtOverdue)) {
				infoDisclosure.setBadDebtOverdue(Double
						.parseDouble(badDebtOverdue));
			}
			infoDisclosure.setCreateTime(new Date());
			infoDisclosure.setOperationUserid(getLoginUser.getUserId());
			disclosureService.insert(infoDisclosure);
			response.getWriter().print("ok");

		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/risk/deleteInfoDisclosure")
	public void deleteInfoDisclosure(HttpServletResponse response,
			HttpServletRequest request) {
		String id = request.getParameter("id");
		// UserCookie cookie = UserCookieHelper.GetUserCookie(request,
		// response);
		try {
			if (!StringUtils.isBlank(id)) {
				disclosureService.deleteByPrimaryKey(id);
				response.getWriter().print("ok");
			}
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/repay/toRepayMentRecods")
	public String toRepayMentRecods(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		return "found/repayMnet";
	}

	/**
	 * 还款追踪
	 */
	@RequestMapping(value = "/repay/repayMentRecods")
	public String repayMentRecods(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		int pageNo = 0;
		int pageSize = 10;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		String str = "";
		Map<String, Object> map = new HashMap<String, Object>();

		String userId = request.getParameter("userId");
		if (userId != null && !userId.equals("")) {
			str += "&userId=" + userId;
			map.put("userId", getUserId(userId));
		}
		String start = request.getParameter("start");
		if (start != null && !start.equals("")) {
			str += "&start=" + start;
			map.put("start", start);
		}
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end)) {
			str += "&end=" + end;
		}
		PageInfo<repayMent> pageInfo = investService.readRepayMentRecords(
				pageNo, pageSize, map, end);
		List<repayMent> list = pageInfo.getResults();
		double repayMoney = 0;
		int peopleCount = 0;
		if (list != null) {
			for (repayMent repayMent : list) {
				// 根据用户编号 查询投资次数
				if (repayMent.getUserId() != null) {
					int investCount = investService
							.readInvestCountByUserId(repayMent.getUserId());
					repayMent.setInvestCount(investCount);
					// 查询可用红包总金额
					double redpacketUableMoeny = investService
							.readRedpacketUableMoeny(repayMent.getUserId());
					repayMent.setUsableRedpacketMoney(redpacketUableMoeny);
					// 查询可用加息券额度
					double redpacketUableRate = investService
							.readRedpacketUableRate(repayMent.getUserId());
					repayMent.setUsableRedpacketRate(redpacketUableRate);
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
											repayMent.getInvestMoney(),
											repayMent.getReate(),
											repayMent.getDeadline());
							repayMent.setRepayInterest(interest);
							repayMent.setRepayMoeny(repayMent.getInvestMoney());
							repayMent
									.setLoanTime(repayMent.getDeadline() + "月");
						} else if (repayType.equals("按月付息到期还本")) {
							// 第几期 和项目周期进行比对 如果是最后一期 就是 利息 本金都有
							if (repayMent.getRepayItem() == repayMent
									.getDeadline()) {
								double interest = InterestUtil
										.getRFCLInterestByPeriodMoth(
												repayMent.getInvestMoney(),
												repayMent.getReate(), 1);
								repayMent.setRepayInterest(interest);
								repayMent.setRepayMoeny(repayMent
										.getInvestMoney());
								repayMent.setLoanTime(repayMent.getDeadline()
										+ "月");
							} else {
								double interest = InterestUtil
										.getRFCLInterestByPeriodMoth(
												repayMent.getInvestMoney(),
												repayMent.getReate(), 1);
								repayMent.setRepayInterest(interest);
								repayMent.setLoanTime(repayMent.getDeadline()
										+ "月");
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
			}
		}

		PageInfo<repayMent> pageInfo1 = investService.readRepayMentRecords(
				pageNo, 99999, map, end);
		List<repayMent> list1 = pageInfo1.getResults();
		if (list1 != null) {
			for (repayMent repayMent : list1) {
				if (repayMent.getUserId() != null) {
					String repayType = repayMent.getRepayType();
					String type = repayMent.getOperationType();
					if (type != null) {
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
												repayMent.getInvestMoney(),
												repayMent.getReate(),
												repayMent.getDeadline());
								repayMent.setRepayInterest(interest);
								repayMent.setRepayMoeny(repayMent
										.getInvestMoney());
								repayMent.setLoanTime(repayMent.getDeadline()
										+ "月");
							} else if (repayType.equals("按月付息到期还本")) {
								// 第几期 和项目周期进行比对 如果是最后一期 就是 利息 本金都有
								if (repayMent.getRepayItem() == repayMent
										.getDeadline()) {
									double interest = InterestUtil
											.getRFCLInterestByPeriodMoth(
													repayMent.getInvestMoney(),
													repayMent.getReate(), 1);
									repayMent.setRepayInterest(interest);
									repayMent.setRepayMoeny(repayMent
											.getInvestMoney());
									repayMent.setLoanTime(repayMent
											.getDeadline() + "月");
								} else {
									double interest = InterestUtil
											.getRFCLInterestByPeriodMoth(
													repayMent.getInvestMoney(),
													repayMent.getReate(), 1);
									repayMent.setRepayInterest(interest);
									repayMent.setLoanTime(repayMent
											.getDeadline() + "月");
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
								repayMent.setLoanTime(repayMent.getDeadline()
										+ "月");
							}
						}
					}
				}
				repayMoney += repayMent.getRepayMoeny()
						+ repayMent.getRepayInterest();
				peopleCount++;
			}
		}
		model.addAttribute("peopleCount", peopleCount);
		model.addAttribute("repayMoney", repayMoney);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("start", request.getParameter("start"));
		model.addAttribute("end", request.getParameter("end"));
		model.addAttribute("userId", request.getParameter("userId"));
		model.addAttribute("str", str);
		return "found/repayMnet";
	}

	@RequestMapping(value = "found/encryption")
	public String encryption() {

		return "found/encryption";
	}

	@RequestMapping(value = "/found/uploadinfo", method = RequestMethod.POST)
	public String impData(
			@RequestParam("filetest") CommonsMultipartFile filetest,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		// 根据活动编号获取用户id
		// String content="";
		BufferedReader in = new BufferedReader(new InputStreamReader(
				filetest.getInputStream()));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
			buffer.append(",");
		}
		model.addAttribute("content", buffer);
		return "found/encryption";
	}

	@RequestMapping(value = "/found/subData", method = RequestMethod.POST)
	public String subData(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		String content = request.getParameter("content"); // 类型
		String type = request.getParameter("type");
		String[] arr = content.split(",");
		List<Encryption> list = new ArrayList<>();
		if (type.equals("1")) {
			for (int i = 0; i < arr.length; i++) {
				Encryption encryption = new Encryption();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("key", "key");
				map1.put("userId", arr[i]);
				// 获取解密后的用户编号
				String userId = userService.readEncodeUserId(map1);
				userId = new String(userId.getBytes(), "utf-8");
				encryption.setEncryption(userId);
				encryption.setUnEncryption(arr[i]);
				list.add(encryption);
			}
		} else {
			for (int i = 0; i < arr.length; i++) {
				Encryption encryption = new Encryption();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("key", "key");
				map1.put("userId", arr[i]);
				// 获取解密后的用户编号
				String userId = userService.readEncodeUserId(map1);
				userId = new String(userId.getBytes(), "utf-8");
				encryption.setEncryption(userId);
				encryption.setUnEncryption(getUserId(arr[i]));
				list.add(encryption);
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("content", content);
		model.addAttribute("type", type);
		return "found/encryption";
	}

	/**
	 * 导出加密数据
	 */
	@RequestMapping(value = "/found/exportEncrypation")
	public void exportListData(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String content = request.getParameter("listData");
			String type = request.getParameter("type");
			String[] arr = content.split(",");
			List<Encryption> list = new ArrayList<>();
			if (type.equals("1")) {
				for (int i = 0; i < arr.length; i++) {
					Encryption encryption = new Encryption();
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("key", "key");
					map1.put("userId", arr[i]);
					// 获取解密后的用户编号
					String userId = userService.readEncodeUserId(map1);
					userId = new String(userId.getBytes(), "utf-8");
					encryption.setEncryption(userId);
					encryption.setUnEncryption(arr[i]);
					list.add(encryption);
				}
			} else {
				for (int i = 0; i < arr.length; i++) {
					Encryption encryption = new Encryption();
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("key", "key");
					map1.put("userId", arr[i]);
					// 获取解密后的用户编号
					String userId = userService.readEncodeUserId(map1);
					userId = new String(userId.getBytes(), "utf-8");
					encryption.setEncryption(userId);
					encryption.setUnEncryption(getUserId(arr[i]));
					list.add(encryption);
				}
			}

			Map<String, String> title = new LinkedHashMap<>();
			title.put("unEncryption", "未加密数据");
			title.put("encryption", "加密后数据");
			int[] style = { 16, 30 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "加密数据" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor;
			excelConvertor = new ExcelConvertor(response.getOutputStream(),
					fileName);
			String t = "加密记录";
			excelConvertor.excelExport(list, title, t, style);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "found/encryptDecode")
	public String encryptDecode() {

		return "found/encryptDecode";
	}

	public static void main(String[] args) {
		Encryption encryption = new Encryption();
		List<Encryption> list = new ArrayList<>();
		list.add(encryption);
	}

	/**
	 * 加密解密处理
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/found/encryptOrDecode")
	public String encryptOrDecode(HttpServletResponse response,
			HttpServletRequest request) {
		String encodeOrdecode = "fail";
		try {
			String content = request.getParameter("content");
			String type = request.getParameter("type");
			if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(content)) {
				if ("加密".equals(type)) {
					encodeOrdecode = AES.encode(content);
				}
				if ("解密".equals(type)) {
					encodeOrdecode = AES.decode(content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodeOrdecode;
	}

}