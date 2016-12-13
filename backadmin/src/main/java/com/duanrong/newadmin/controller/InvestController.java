package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import util.Log;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.autoinvest.service.AutoInvestService;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.msg.model.Email;
import com.duanrong.business.msg.service.EmailService;
import com.duanrong.business.permission.service.PermissionService;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.newInvestUser;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.FormUtil;
import com.duanrong.util.DateUtil;
import com.duanrong.util.client.DRHTTPClient;
import com.duanrong.yeepay.service.TrusteeshipGiveMoneyToBorrowerService;

@Controller
public class InvestController extends BaseController {

	@Resource
	InvestService investService;

	@Resource
	UserService userService;

	@Resource
	EmailService emailService;

	@Resource
	AutoInvestService autoInvestService;

	@Resource
	PermissionService permissionService;

	@Resource
	LoanService loanService;

	@Resource
	UserAccountService userAccountService;
	
	@Resource
	UserMoneyService userMoneyService;

	@Resource
	OperaRecordService operaRecordService;

	@Resource
	RepayService repayService;

	@Resource
	TrusteeshipGiveMoneyToBorrowerService trusteeshipGiveMoneyToBorrowerService;
	
	@Resource
	Log log;

	@RequestMapping(value = "/invest/investByLoan")
	public String investView(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		int pageNo = 0;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		Map<String, Object> map = new HashMap<>();
		if (MyStringUtils.isNotAnyBlank(request.getParameter("loanId"))) {
			map.put("loanId", request.getParameter("loanId"));
		}
		String investUserId = request.getParameter("investUserID");
		map.put("investUserID", investUserId);
		String investUserName = request.getParameter("investUserName");
		map.put("investUserName", investUserName);
		String startTime = request.getParameter("startTime");
		map.put("startTime", startTime);
		
		String endTime = request.getParameter("endTime");
		map.put("endTime", endTime);
		
		String minMoney = request.getParameter("minMoney");
		if (MyStringUtils.isNumeric(minMoney)) {
			map.put("minMoney", Double.parseDouble(minMoney));
		}
		
		String maxMoney = request.getParameter("maxMoney");
		if (MyStringUtils.isNumeric(maxMoney)) {
			map.put("maxMoney", Double.parseDouble(maxMoney));
		}
		
		String status = request.getParameter("status");
		map.put("status", status);
		
		String merge = request.getParameter("merge");
		map.put("merge", merge);
		
		map = FormUtil.getForParamToBean(map);
		PageInfo<Invest> pageInfo = investService.readInvestByLoan(pageNo,
				getPageSize(), map);
		request.setAttribute("loanId", map.get("loanId"));
		request.setAttribute("url", "/invest/investByLoan");
		request.setAttribute("str", FormUtil.getForParam(map));
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("investUserID", investUserId);
		request.setAttribute("investUserName", investUserName);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("minMoney", minMoney);
		request.setAttribute("maxMoney", maxMoney);
		request.setAttribute("status", status);
		request.setAttribute("merge", merge);
		return "invest/investList";

	}

	@RequestMapping(value = "/invest/investByUser")
	public String investViewForUser(HttpServletResponse response,
			HttpServletRequest request) {
		int pageNo = 0;
		int pageSize = 25;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		Invest invest = new Invest();
		invest.setInvestUserID(request.getParameter("investUserID"));
		invest = (Invest) FormUtil.getForParamToBean(invest);
		PageInfo<Invest> pageInfo = investService.readInvestByUser(pageNo,
				pageSize, invest.getInvestUserID());
		request.setAttribute("url", "/invest/investByUser");
		request.setAttribute("str", FormUtil.getForParam(invest));
		request.setAttribute("pageInfo", pageInfo);
		return "invest/investList2";

	}

	@RequestMapping(value = "/invest/exportInvest")
	public void exportInvest(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		Map<String, Object> map = new HashMap<>();
		if (MyStringUtils.isNotAnyBlank(request.getParameter("loanId"))) {
			map.put("loanId", request.getParameter("loanId"));
		}
		map.put("investUserID", request.getParameter("investUserID"));
		map.put("investUserName", request.getParameter("investUserName"));
		map.put("startTime", request.getParameter("startTime"));
		map.put("endTime", request.getParameter("endTime"));
		if (MyStringUtils.isNumeric(request.getParameter("minMoney"))) {
			map.put("minMoney",
					Double.parseDouble(request.getParameter("minMoney")));
		}
		if (MyStringUtils.isNumeric(request.getParameter("maxMoney"))) {
			map.put("maxMoney",
					Double.parseDouble(request.getParameter("maxMoney")));
		}
		if (MyStringUtils.isNotAnyBlank(request.getParameter("status"))) {
			String a = new String(request.getParameter("status").getBytes(
					"ISO8859-1"), "UTF-8");
			map.put("status", a);
		}
		if (MyStringUtils.isNotAnyBlank(request.getParameter("merge"))) {
			map.put("merge", request.getParameter("merge"));
		}
		map = FormUtil.getForParamToBean(map);
		try {
			Loan loan = loanService.read(request.getParameter("loanId"));
			List<Invest> invests = investService
					.readInvestByLoan(1, 999999, map).getResults();
			String rate = loan.getRate() * 100 + "%";
			Date giveMoneyTime = loan.getGiveMoneyTime();
			String formatGiveMoneyTime = DateUtil.DateToString(giveMoneyTime,
					"yyyy年MM月dd日");
			String dealLine = "";
			if (loan.getOperationType().equals("月")) {
				dealLine = loan.getDeadline() + "个月";
			}
			if (loan.getOperationType().equals("天")) {
				if (loan.getBeforeRepay().equals("是")) {
					dealLine = loan.getDay() + loan.getSymbol() + "天";
				} else {
					dealLine = loan.getDay() + "天";
				}
			}

			String t = loan.getName() + loan.getRate();
			t = loan.getName() + "      (" + rate + "    "
					+ formatGiveMoneyTime + "   " + dealLine + ")";
			Map<String, String> title = new LinkedHashMap<>();
			List<Invest> list = new ArrayList<>();
			boolean haveRoleById = haveRoleById("production_manager", request,
					response);
			if (havePermissionById("USER_LOOK", request, response)
					|| havePermissionById("USER_MUMBER", request, response)) {
				if (!haveRoleById) {
					title.put("userMobileNumber", "投资人");
				}
				if (!havePermissionById("USER_LOOK", request, response)) {
					for (Invest invest : invests) {
						if (MyStringUtils.isNotAnyBlank(invest
								.getUserMobileNumber())
								&& invest.getUserMobileNumber().length() >= 11) {
							invest.setUserMobileNumber("......."
									+ invest.getUserMobileNumber().substring(7,
											11));
						}
						list.add(invest);
					}
				} else {
					list = invests;
				}

			} else {
				list = invests;
			}
			title.put("investUserName", "姓名");
			if (!haveRoleById) {
				title.put("userMobileNumber", "电话");
			}
			title.put("sumMoney", "金额");
			if (!haveRoleById) {
				title.put("userHomeAddress", "地址");
			}
			title.put("time", "日期");
			if (havePermissionById("USER_LOOK", request, response)) {
				if (!haveRoleById) {
					title.put("email", "邮箱");
				}
			}
			if (!haveRoleById) {
				title.put("investTotal", "次数");
			}
			title.put("returnPondMoney", "跟投");
			if (!haveRoleById) {
				title.put("QQ", "QQ");
			}
			if (!haveRoleById) {
				title.put("remarkremark", "备注");
			}
			int[] style = { 20, 16, 6, 18, 20, 18, 6, 6, 14, 14 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "invest" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			excelConvertor.excelExport(invests, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/invest/createInformation")
	public void createInformation(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		UserCookie cookie = UserCookieHelper.GetUserCookie(request, response);
		String loanId = request.getParameter("loanId");
		response.setContentType("text/html;chartset=utf-8");
		if (MyStringUtils.isNotAnyBlank(request.getParameter("loanId"))) {
			response.getWriter().print(
					investService.createInvestConfimcation(loanId,
							cookie.getUserId()));
		} else {
			response.getWriter().print("项目编号不能为空");
		}
	}

	@RequestMapping(value = "/invest/getInvestInformation")
	public String getInvestInformationView(HttpServletResponse response,
			HttpServletRequest request) {
		String loanId = request.getParameter("loanId");
		List<Email> email = emailService.readEmailByLoan(loanId);
		request.setAttribute("emails", email);
		request.setAttribute("loanId", loanId);
		request.setAttribute("url", "/invest/getInvestInformation");
		return "invest/informationList";
	}

	@RequestMapping(value = "/invest/preview")
	public void preView(HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		String id = request.getParameter("id");
		Email email = emailService.readEmailById(id);
		String content = "";
		if (email != null) {
			content = email.getContent();
		}
		response.setContentType("text/html;chartset=utf-8");
		response.getWriter().print(content);
	}

	@RequestMapping(value = "/invest/sendEmail")
	public void sendEmail(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		UserCookie cookie = UserCookieHelper.GetUserCookie(request, response);
		String loanId = request.getParameter("loanId");
		String status = investService.sendInvestInfomation(loanId,
				cookie.getUserId());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(status);
	}

	@RequestMapping(value = "/invest/autoInvest")
	public void autoInvest(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie cookie = UserCookieHelper.GetUserCookie(request, response);
		String loanId = request.getParameter("loanId");
		if (MyStringUtils.isNotAnyBlank(loanId)) {
			try {
				operaRecordService.insertRecord("自动投标", cookie.getUserId(),
						"项目：" + loanId + ",开启自动投标");
				autoInvestService.autoInvest(loanId);
				response.getWriter().print("ok");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "/invest/toCreateInvest")
	public String toCreateInvest(HttpServletResponse response,
			HttpServletRequest request) {
		return "invest/createInvest";
	}

	@RequestMapping(value = "/invest/invest")
	public void invest(HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		UserCookie cookie = UserCookieHelper.GetUserCookie(request, response);
		String userId = request.getParameter("userId");
		String loanId = request.getParameter("loanId");
		User user;
		if (StringUtils.isNumeric(userId) && StringUtils.length(userId) == 11) {
			user = userService.readUserByMobileNumber(userId);
		} else if (StringUtils.contains(userId, "@")) {
			user = userService.readUserByMail(userId);
		} else {
			user = userService.read(userId);
		}
		if (user == null) {
			response.getWriter().print("UL");
			return;
		}
		
		if (MyStringUtils.isNotAnyBlank(request.getParameter("loanId"),
				request.getParameter("investMoney"))) {
			if (user.getUserId().equals(
					loanService.read(loanId).getBorrowMoneyUserID())) {
				response.getWriter().print("UU");
				return;
			}
			Double investMoney =  Double.parseDouble(request.getParameter("investMoney"));			
			if (userAccountService.readUserAccount(user.getUserId()).getAvailableBalance() < investMoney) {
				response.getWriter().print("UB");
				return;
			}
			try {
				operaRecordService.insertRecord("自动投标", cookie.getUserId(),
						"开启单笔自动投标,项目：" + loanId + ",用户:" + user.getUserId());
				autoInvestService
						.autoInvest(loanId, user.getUserId(), investMoney, request.getParameter("auto"));
				
				String str = "管理员:"+cookie.getUserId() + "为用户[id:" + user.getUserId() 
						+ "\t mobile: " + user.getMobileNumber() + "\t realName: " + user.getRealname() 
						+ "] 在项目[loanId: " + loanId +"] 中开启自动投标, 自动投标金额"+investMoney+"元。" ;
				String url = "http://soa-log2.duanrong.com/basic/mail/send";
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("subject", "单笔自动投标"));
				params.add(new BasicNameValuePair("content", str));
				params.add(new BasicNameValuePair("mailtos",
						"guolixiao@duanrong.com,zhangjunying@duanrong.com,zhouwen@duanrong.com"));
				try {
					DRHTTPClient.sendHTTPRequestPost(url,
							new BasicHeader[] { new BasicHeader("sign",
									"b688513358a7da6a97b4069ec1d062ec") }, params);

				} catch (IOException e) {
					log.errLog("发送邮件异常", e);
				}
				response.getWriter().print("OK");
			} catch (Exception e) {
				response.getWriter().print("ER");
			}

		} else {
			response.getWriter().print("LL");
		}
	}

	@RequestMapping(value = "/invest/getInvestUser")
	public void getInvestUser(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String userId = request.getParameter("userId");
		User user;
		if (StringUtils.isNumeric(userId) && StringUtils.length(userId) == 11) {
			user = userService.readUserByMobileNumber(userId);
		} else if (StringUtils.contains(userId, "@")) {
			user = userService.readUserByMail(userId);
		} else {
			user = userService.read(userId);
		}
		if (user == null) {
			response.getWriter().print("null");
		} else {
			user = investService.readInvestUserById(user.getUserId());
			JSONArray jsonArr = JSONArray.fromObject(user);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(jsonArr.toString());
		}
	}

	//投资信息
	@RequestMapping(value = "/invest/investList")
	public String investList(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		int pageNo = 0;
		int pageSize = 10;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanId", request.getParameter("loanId"));
		map.put("loanName", request.getParameter("loanName"));
		map.put("userId", request.getParameter("userId"));
		map.put("userSource", request.getParameter("userSource"));
		map.put("mobile", request.getParameter("mobile"));
		map.put("realname", request.getParameter("realname"));
		map.put("start", request.getParameter("start"));
		
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end)) {
			String endtime = DateUtil.addDay(end, 1);
			map.put("end", endtime+" 23:59");
		}
		String userSource=request.getParameter("userSource");
		if (StringUtils.isNotBlank(userSource)) {
			map.put("userSource", userSource);
		}
		String userSourceIsNull=request.getParameter("userSourceIsNull");
		if (StringUtils.isNotBlank(userSourceIsNull)) {
			map.put("userSourceIsNull", userSourceIsNull);
		}
		map.put("status", request.getParameter("status"));
		try {
			map = FormUtil.getForParamToBean(map);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageInfo<Invest> pageInfo = investService.readPageInfo(pageNo,
				pageSize, map);
		List<Invest> list = pageInfo.getResults();
		for (Invest invest : list) {
			int count =investService.readCheckFirstInvest(invest.getUserId(),invest.getTime());
			if(count>0){
				invest.setIsFirstInvest("否");
			}else{
				invest.setIsFirstInvest("是");
			}
		}
		request.setAttribute("str", FormUtil.getForParam(map));
		request.setAttribute("pageInfo", pageInfo);

		String status = request.getParameter("status");
		String loanName = request.getParameter("loanName");
		String realname = request.getParameter("realname");
		try {
			if (status != null && !"".equals(status)) {
				status = URLDecoder.decode(status, "UTF-8");
			}
			if (loanName != null && !"".equals(loanName)) {
				loanName = URLDecoder.decode(loanName, "UTF-8");
			}
			if (realname != null && !"".equals(realname)) {
				realname = URLDecoder.decode(realname, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double actualMoney = investService.readTotalMoney(map);
		int actualPeopelCount =investService.readTotalPeople(map);
		BigDecimal firstInvestMoney = investService
				.readFirstInvestMoney(map);
		int firstInvestPeople = investService
				.readFirstInvestPeople(map);
		model.addAttribute("loanId", request.getParameter("loanId"));
		model.addAttribute("loanName", loanName);
		model.addAttribute("realname", realname);
		model.addAttribute("start", request.getParameter("start"));
		model.addAttribute("actualMoney", actualMoney);
		model.addAttribute("userSourceIsNull", userSourceIsNull);
		model.addAttribute("firstInvestMoney", firstInvestMoney);
		model.addAttribute("firstInvestPeople", firstInvestPeople);
		model.addAttribute("actualPeopelCount",actualPeopelCount);
		model.addAttribute("end", request.getParameter("end"));
		model.addAttribute("userId", request.getParameter("userId"));
		model.addAttribute("mobile", request.getParameter("mobile"));
		model.addAttribute("status", status);
		model.addAttribute("userSource",request.getParameter("userSource"));
		return "invest/list";

	}

	@RequestMapping(value = "/invest/export")
	public void export(HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("loanId", request.getParameter("loanId"));
		map.put("loanName", request.getParameter("loanName"));
		map.put("userId", request.getParameter("userId"));
		map.put("mobile", request.getParameter("mobile"));
		map.put("realname", request.getParameter("realname"));
		map.put("start", request.getParameter("start"));
		map.put("end", request.getParameter("end"));
		map.put("status", request.getParameter("status"));
		map = FormUtil.getForParamToBean(map);
		try {
			List<Invest> invests = investService.readInvests(map);
			List<Invest> list = new ArrayList<Invest>();
			String t = "投资信息";
			Map<String, String> title = new LinkedHashMap<>();
			title.put("loanName", "项目名称");
			for (Invest invest : invests) {
				Repay r = new Repay();
				r.setLoanId(invest.getLoanId());
				List<Repay> repayByLoan = repayService.readByCondition(r);
				if (repayByLoan != null && repayByLoan.size() > 0) {
					r = repayByLoan.get(repayByLoan.size() - 1);
				}
				invest.setEndDate(r.getRepayDay());
			}

			if (havePermissionById("USER_LOOK", request, response)
					|| havePermissionById("USER_MUMBER", request, response)) {

				title.put("userMobileNumber", "投资人");
				if (!havePermissionById("USER_LOOK", request, response)) {
					for (Invest invest : invests) {
						if (MyStringUtils.isNotAnyBlank(invest
								.getUserMobileNumber())
								&& invest.getUserMobileNumber().length() >= 11) {
							invest.setUserMobileNumber("......."
									+ invest.getUserMobileNumber().substring(7,
											11));
						}
						list.add(invest);
					}
				} else {
					list = invests;
				}

			} else {
				list = invests;
			}

			title.put("investUserName", "真实姓名");
			title.put("time", "投资时间");
			title.put("money", "投资金额");
			title.put("status", "投标状态");
			title.put("duration", "投资周期");
			title.put("startDate", "计息日");
			title.put("endDate", "到期日");
			int[] style = { 30, 20, 8, 30, 20, 20, 20, 20, 20 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "投资信息" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			excelConvertor.excelExport(list, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 去新增投资用户明细
	 */
	@RequestMapping(value="/invest/toNewUserInvest")
	public String toNewUserInvest(){
		
		return "invest/peopleInvestInfo";
	}
	
	/**
	 * 新增投资用户明细
	 */
	@RequestMapping(value="/invest/newUserInvest")
	public String newUserInvest(HttpServletRequest request,Model model ){
		int pageNo = 0;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		String str = "";
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		String remark=request.getParameter("remark");
		String remarkstart=request.getParameter("remarkstart");
		String remarkend=request.getParameter("remarkend");
		String remarkUserId=request.getParameter("remarkUserId");
		Map<String, Object> params=new HashMap<>();
		PageInfo<newInvestUser> pageInfo =null;
		if (start!=null) {
			start=URLDecoder.decode(start);
			if (StringUtils.isNotBlank(start)) {
				str += "&start=" + start;
				params.put("start", start);
			}
		}
		if (end!=null) {
			end=URLDecoder.decode(end);
			if (StringUtils.isNotBlank(end)) {
				str += "&end=" + end;
				params.put("end", end+" 23:59");
			}
		}
	
			if (StringUtils.isNotBlank(remark)) {				
					str += "&remark=" + remark;
					params.put("remark", remark);				
			}
		
		if (remarkstart!=null) {
			remarkstart=URLDecoder.decode(remarkstart);
			if (StringUtils.isNotBlank(remarkstart)) {
				str += "&remarkstart=" + remarkstart;
				params.put("remarkstart", remarkstart);
			}
		}
		if (remarkend!=null) {
			remarkend=URLDecoder.decode(remarkend);
			if (StringUtils.isNotBlank(remarkend)) {
				str += "&remarkend=" + remarkend;
				params.put("remarkend", remarkend+" 23:59");
				
			}
		}		
			if (StringUtils.isNotBlank(remarkUserId)) {
				if(!"all".equals(remarkUserId)){
				str += "&remarkUserId=" + remarkUserId;
				params.put("remarkUserId", remarkUserId);
				}
			}		
		int peopleCount=0;		
		pageInfo = userService.readInvestUserInfo(pageNo,
				getPageSize(),params);
	    peopleCount=userService.readPeopleCount(params);
	  
		model.addAttribute("str", str);
		model.addAttribute("start",start);
		model.addAttribute("end",end);
		model.addAttribute("remarkstart",remarkstart);
		model.addAttribute("remarkend",remarkend);
		model.addAttribute("remarkUserId",remarkUserId);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("remark",remark);
		model.addAttribute("peopleCount", peopleCount);
		return "invest/peopleInvestInfo";
	}
	
	/**
	 * 单笔放款
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/invest/giveMoneyToBorrowerFromInvest")
	public void giveMoneyToBorrowerFromInvest(HttpServletRequest request, 
			HttpServletResponse response){
		/*response.setCharacterEncoding("utf-8");
		UserCookie cookie = UserCookieHelper.GetUserCookie(request, response);
		String result = "";
		String userId = cookie.getUserId();
		String investId = request.getParameter("investId");
		if(investId == null || investId.equals("")){
			result = "投资编号为空";
		}else{
			Invest invest = investService.read(investId);
			if(invest == null){
				result = "投资记录不存在";
			}else{
				Loan loan = loanService.read(invest.getLoanId());
				if(loan == null || !loan.getStatus().equals("等待复核")){
					result = "项目不存在或项目状态不是等待复核";
				}else{
					try {
						result = trusteeshipGiveMoneyToBorrowerService.giveMoneyToBorrowerFromInvest(loan, invest, userId);
					} catch (Exception e) {
						result = "放款失败，系统异常";
						e.printStackTrace();
					}
				}
			}
			
		}*/
		try {
			response.getWriter().write("单笔款款已屏蔽，请等待自动放款！！！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}