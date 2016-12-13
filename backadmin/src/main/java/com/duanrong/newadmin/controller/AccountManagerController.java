package com.duanrong.newadmin.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.PaymentEnum;
import com.duanrong.business.account.model.PaymentAccount;
import com.duanrong.business.account.model.PaymentBill;
import com.duanrong.business.account.model.PlatformBill;
import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.service.PaymentAccountService;
import com.duanrong.business.account.model.PlatformAccount;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.user.model.UserMoney;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.newadmin.model.UserAccountInfo;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.util.ArithUtil;

import util.Log;
import util.MyStringUtils;

@Controller
public class AccountManagerController extends BaseController {

	@Resource
	Log log;
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	PaymentAccountService paymentAccountService;
	
	@Resource
	UserMoneyService userMoneyService;
	
	
	@RequestMapping(value = "/account/userAccountInfo")
	public String userAccountInfo(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {		
			String id = request.getParameter("userId");		
			if(id == null || id.trim().equals("")){
				
			}else{
				id = id.trim();
				String userId = getUserId(id);
				UserAccount userAccount = userAccountService.readUserAccount(userId);		
				UserAccountInfo accountInfo = new UserAccountInfo();
				accountInfo.setUserAccount(userAccount);
				accountInfo.setBalance(userMoneyService.readNBalance(userId));
				accountInfo.setFrozen(userMoneyService.readNFrozenMoney(userId));			
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				Map<String, Object> bill = new HashMap<>();
				bill.put("userId", userId);
				PageInfo<UserMoney> pageLite = userMoneyService.readPageInfo(Integer.parseInt(pageNo), 30, bill);		
				accountInfo.setUserBill(pageLite.getResults());
				
				model.addAttribute("pageInfo", pageLite);
				model.addAttribute("accountInfo", accountInfo);
			}
		
			model.addAttribute("userId", id);
			model.addAttribute("str", "&userId="+id);
			return "account/userAccount";
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	@RequestMapping(value = "/account/transfer")
	@ResponseBody
	public String transfer(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {		
			String id = request.getParameter("userId");		
			String seqNum =  request.getParameter("seqNum");
			if(StringUtils.isBlank(id) || StringUtils.isBlank(seqNum)) return "isnull";
			String userId = getUserId(id);
			userAccountService.transfer(userId, Integer.parseInt(seqNum));
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	
	@RequestMapping(value = "/account/userAccountList")
	public String userAccountList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String str = "";
			String id = request.getParameter("userId");
			String realname = request.getParameter("realname");
			String name = "";
			Map<String, Object> params = new HashMap<String, Object>();
			String userId = getUserId(id);
			if (StringUtils.isNotBlank(userId)) {
				str += "&userId=" + userId;
				params.put("userId", userId);
			}
			if (StringUtils.isNotBlank(realname)) {
				name = java.net.URLDecoder.decode(realname, "UTF-8");
				params.put("realname", name);
				str += "&realname=" + name;
			}
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			PageInfo<UserAccount> pageInfo = userAccountService
					.readUserAccounts(Integer.parseInt(pageNo), 30, params);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("userId", userId);
			model.addAttribute("realname", name);
			model.addAttribute("str", str);
			return "account/userAccountList";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.AccountManagerController.userAccountList()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/account/platformAccountList")
	public String platformAccountList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			PlatformAccount platformAccount = platformAccountService
					.readPlatformAccount();
			model.addAttribute("platformAccount", platformAccount);
			PlatformBill platformBill = new PlatformBill();
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String str = "";
			String id = request.getParameter("id");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String requestNo = request.getParameter("requestNo");
			if (StringUtils.isNotBlank(id)) {
				platformBill.setId(Integer.parseInt(id));
				str += "&id=" + id;
			}
			if (StringUtils.isNotBlank(requestNo)) {
				platformBill.setRequestNo(requestNo);
				str += "&requestNo=" + requestNo;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtils.isNotBlank(start)) {
				platformBill.setBeginTime(sdf.parse(start));
				str += "&start=" + start;
			}
			if (StringUtils.isNotBlank(end)) {
				platformBill.setEndTime(sdf.parse(end));
				str += "&end=" + end;
			}
			PageInfo<PlatformBill> pageInfo = platformAccountService.readPageLite(
					Integer.parseInt(pageNo), 30, platformBill);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("start", start);
			model.addAttribute("end", end);
			model.addAttribute("id", id);
			model.addAttribute("requestNo", requestNo);
			model.addAttribute("str", str);
			return "account/platformAccount";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.AccountManagerController.platformAccountList()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/platformAccount/platformAccountIn")
	public String addPlatformAccount(HttpServletRequest request,
			HttpServletResponse response) {
		return "account/platformAccountIn";
	}

	@RequestMapping(value = "/platformAccount/platformAccountOut")
	public String addPlatformAccountOut(HttpServletRequest request,
			HttpServletResponse response) {
		return "account/platformAccountOut";
	}

	@RequestMapping(value = "/account/platformAccountIn")
	public void platformAccountIn(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Double money = Double.valueOf(request.getParameter("money"));
			BusinessEnum businessType = BusinessEnum.recharge;
			String typeInfo = request.getParameter("typeInfo");
			String requestNo = request.getParameter("requestNo");
//			String rechargeType = request.getParameter("rechargeType");
			Double fee =  Double.valueOf(request.getParameter("fee"));
//			double rate = 0.0;
//			PaymentChannel channel = paymentAccountService
//					.readChannelByCode(PaymentEnum.Yeepay.toString());
//			if (MyStringUtils.equalsIgnoreCaseAnyString(rechargeType, "quick",
//					null)) {
//				rate = channel.getRateQuick();
//			} else {
//				rate = channel.getRateGateway();
//			}
//			double fee = ArithUtil.round(money * rate, 2);
			PlatformAccount platformAccount = platformAccountService
					.readPlatformAccount();
			if (ArithUtil.add(money, platformAccount.getAvailableBalance()) < fee) {
				response.getWriter().write("充值金额与平台账户余额之和小于充值手续费！");
				throw new InsufficientBalance("充值金额与平台账户余额之和小于充值手续费！");
			}
			UserCookie userCookie = GetLoginUser(request, response);
			platformAccountService.transferOut(fee, BusinessEnum.fee, "平台账户充值手续费，userId："+userCookie.getUserId()+"，流水号："+requestNo,
					requestNo);
			platformAccountService.transferIn(money, businessType, typeInfo+"，userId："+userCookie.getUserId()+"，流水号："+requestNo,
					requestNo);
			response.getWriter().write("success");
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.AccountManagerController.platformAccountIn()",
					e);
		}
	}

	@RequestMapping(value = "/account/platformAccountOut")
	public void platformAccountOut(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Double money = Double.valueOf(request.getParameter("money"));
			BusinessEnum businessType = BusinessEnum.withdraw_cash;
			String typeInfo = request.getParameter("typeInfo");
			String requestNo = request.getParameter("requestNo");
//			String rechargeType = request.getParameter("rechargeType");
			Double fee = Double.valueOf(request.getParameter("fee"));
//			double rate = 0.0;
//			PaymentChannel channel = paymentAccountService
//					.readChannelByCode(PaymentEnum.Yeepay.toString());
//			if (MyStringUtils.equalsIgnoreCaseAnyString(rechargeType, "quick",
//					null)) {
//				rate = channel.getRateQuick();
//			} else {
//				rate = channel.getRateGateway();
//			}
//			double fee = ArithUtil.round(money * rate, 2);
			PlatformAccount platformAccount = platformAccountService
					.readPlatformAccount();
			if (ArithUtil.add(money, fee) > platformAccount
					.getAvailableBalance()) {
				response.getWriter().write("取现金额与手续费之和大于平台账户可用余额！");
				throw new InsufficientBalance("取现金额与手续费之和大于账户可用余额！");
			}
			UserCookie userCookie = GetLoginUser(request, response);
			platformAccountService.transferOut(fee, BusinessEnum.fee, "平台账户取现手续费，userId："+userCookie.getUserId()+"，流水号："+requestNo,
					requestNo);
			platformAccountService.transferOut(money, businessType, typeInfo+"，userId："+userCookie.getUserId()+"，流水号："+requestNo,
					requestNo);
			response.getWriter().write("success");
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.AccountManagerController.platformAccountOut()",
					e);
		}
	}

	@RequestMapping(value = "/account/paymentAccountList")
	public String paymentAccountList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			List<PaymentAccount> list = paymentAccountService
					.readPaymentAccount();
			model.addAttribute("list", list);
			PaymentBill paymentBill = new PaymentBill();
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String str = "";
			String channel = request.getParameter("channel");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String requestNo = request.getParameter("requestNo");
			if (StringUtils.isNotBlank(channel)) {
				paymentBill.setChannel(channel);
				str += "&channel=" + channel;
			}
			if (StringUtils.isNotBlank(requestNo)) {
				paymentBill.setRequestNo(requestNo);
				str += "&requestNo=" + requestNo;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtils.isNotBlank(start)) {
				paymentBill.setBeginTime(sdf.parse(start));
				str += "&start=" + start;
			}
			if (StringUtils.isNotBlank(end)) {
				paymentBill.setEndTime(sdf.parse(end));
				str += "&end=" + end;
			}
			PageInfo<PaymentBill> pageInfo = paymentAccountService.readPageLite(
					Integer.parseInt(pageNo), 30, paymentBill);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("start", start);
			model.addAttribute("end", end);
			model.addAttribute("channel", channel);
			model.addAttribute("requestNo", requestNo);
			model.addAttribute("str", str);
			return "account/paymentAccount";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.AccountManagerController.paymentAccountList()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/account/paymentAccountIn")
	public void paymentAccountIn(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PaymentEnum paymentId = PaymentEnum.valueOf(request
					.getParameter("channel"));
			Double money = Double.valueOf(request.getParameter("money"));
			Double fee = Double.valueOf(request.getParameter("fee"));
			BusinessEnum businessType = BusinessEnum.recharge_line;
			String typeInfo = request.getParameter("typeInfo");
			String requestNo = request.getParameter("requestNo");
//			String rechargeType = request.getParameter("rechargeType");
//			double rate = 0.0;
//			PaymentChannel channel = paymentAccountService
//					.readChannelByCode(paymentId.toString());
//			if (MyStringUtils.equalsIgnoreCaseAnyString(rechargeType, "quick",
//					null)) {
//				rate = channel.getRateQuick();
//			} else {
//				rate = channel.getRateGateway();
//			}
//			double fee = ArithUtil.round(money * rate, 2);
			PaymentAccount paymentAccount = paymentAccountService
					.readPaymentAccountByPaymentId(paymentId.toString());
			PlatformAccount platformAccount = platformAccountService
					.readPlatformAccount();
			if (ArithUtil.add(money, paymentAccount.getAvailableBalance()) < fee) {
				response.getWriter().write("充值金额与支付账户可用金额之和小于手续费！");
				throw new InsufficientBalance("充值金额与支付账户可用金额之和小于手续费！");
			}
			if (fee > platformAccount.getAvailableBalance()) {
				response.getWriter().write("充值手续费大于平台账户可用余额！");
				throw new InsufficientBalance("充值手续费大于平台账户可用余额！");
			}
			System.out.println(paymentAccount.toString());
			System.out.println(platformAccount.toString());
			UserCookie userCookie = GetLoginUser(request, response);
			paymentAccountService.transferOut(paymentId, fee, BusinessEnum.fee,
					"支付账户充值手续费，userId："+userCookie.getUserId()+"，流水号："+requestNo, requestNo);
			platformAccountService.transferOut(fee, BusinessEnum.fee, "支付账户充值手续费，userId："+userCookie.getUserId()+"，流水号："+requestNo,
					requestNo);
			paymentAccountService.transferIn(paymentId, money, businessType,
					typeInfo+"，userId："+userCookie.getUserId()+"，流水号："+requestNo, requestNo);
			response.getWriter().write("success");
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.AccountManagerController.paymentAccountIn()",
					e);
		}
	}

	@RequestMapping(value = "/account/paymentAccountOut")
	public void paymentAccountOut(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PaymentEnum paymentId = PaymentEnum.valueOf(request
					.getParameter("channel"));
			Double money = Double.valueOf(request.getParameter("money"));
			Double fee = Double.valueOf(request.getParameter("fee"));
			BusinessEnum businessType = BusinessEnum.withdraw_cash;
			String typeInfo = request.getParameter("typeInfo");
			String requestNo = request.getParameter("requestNo");
//			String rechargeType = request.getParameter("rechargeType");
//			double rate = 0.0;
//			PaymentChannel channel = paymentAccountService
//					.readChannelByCode(paymentId.toString());
//			if (MyStringUtils.equalsIgnoreCaseAnyString(rechargeType, "quick",
//					null)) {
//				rate = channel.getRateQuick();
//			} else {
//				rate = channel.getRateGateway();
//			}
//			double fee = ArithUtil.round(money * rate, 2);
			PaymentAccount account = paymentAccountService
					.readPaymentAccountByPaymentId(paymentId.toString());
			if (ArithUtil.add(money, fee) > account.getAvailableBalance()) {
				response.getWriter().write("取现金额与手续费大于支付账户可用余额！");
				throw new InsufficientBalance("取现金额与手续费大于支付账户可用余额！");
			}
			PlatformAccount platformAccount = platformAccountService
					.readPlatformAccount();
			if (fee > platformAccount.getAvailableBalance()) {
				response.getWriter().write("取现手续费大于平台账户可用余额！");
				throw new InsufficientBalance("取现手续费大于平台账户可用余额！");
			}
			UserCookie userCookie = GetLoginUser(request, response);
			paymentAccountService.transferOut(paymentId, fee, BusinessEnum.fee,
					"支付账户取现手续费，userId："+userCookie.getUserId()+"，流水号："+requestNo, requestNo);
			platformAccountService.transferOut(fee, BusinessEnum.fee, "支付账户取现手续费，userId："+userCookie.getUserId()+"，流水号："+requestNo,
					requestNo);
			paymentAccountService.transferOut(paymentId, money, businessType,
					typeInfo+"，userId："+userCookie.getUserId()+"，流水号："+requestNo, requestNo);
			response.getWriter().write("success");
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.AccountManagerController.paymentAccountOut()",
					e);
		}
	}

	@RequestMapping(value = "/paymentAccount/paymentAccountIn")
	public String addPaymentAccount(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String channel = request.getParameter("channel");
		model.addAttribute("channel", channel);
		return "account/addPaymentAccountIn";
	}

	@RequestMapping(value = "/paymentAccount/paymentAccountOut")
	public String addPaymentAccountOut(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String channel = request.getParameter("channel");
		model.addAttribute("channel", channel);
		return "account/addPaymentAccountOut";
	}

}
