package com.duanrong.cps.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.FormUtil;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.authentication.service.AuthService;
import com.duanrong.authentication.service.impl.AuthServiceImpl;
import com.duanrong.business.permission.model.UserCookie;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.invest.service.InvestService;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.loan.service.LoanService;
import com.duanrong.cps.business.netloaneye.model.NetLoanModel;
import com.duanrong.cps.business.netloaneye.model.PushLoan;
import com.duanrong.cps.business.netloaneye.service.NetLoanEyeService;
import com.duanrong.cps.business.netloaneye.service.PushService;
import com.duanrong.cps.business.user.model.User;
import com.duanrong.util.DateUtil;

/**
 * 网贷天眼相关后台处理
 * @author bj
 *
 */
@Controller
@RequestMapping("/netLoanEye")
public class NetLoanEyeController extends BaseController{

	@Autowired
	private LoanService loanService;
	
	@Autowired
	private NetLoanEyeService netLoanEysService;
	
	@Autowired
	InvestService investService;
	
	/**
	 * 查询可以推送天眼的项目列表
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/waitPushLoanList")
	public String list(String pageNo,Loan loan,Model m){
		if( pageNo == null || "".equals(pageNo) )
			pageNo = "1";
		//分页，带查询
		PageInfo<Loan> pageInfo = loanService.getwaitPushLoanList(pageNo,loan);
		m.addAttribute("pageInfo", pageInfo);
		m.addAttribute("loan", loan);
		return "netLoanEye/loanList";
	}

	
	@Autowired
	private PushService pushService;
	/**
	 * 向天眼推送项目
	 * @param m
	 * @return
	 */
	@RequestMapping("/pushNetLoan")
	@ResponseBody
	public String pushNetLoan(String id,Model m,
			HttpServletRequest request,HttpServletResponse response){
		try {
			AuthService authService=new AuthServiceImpl();
			UserCookie user=authService.getUser(request, response);
			//User user = GetLoginUser(request, response);
			int n=netLoanEysService.pushNetLoan(id,user.getUserId());
			if( n >=1 ){
				return "{\"result\":\"success\"}";
			}else {
				//return "fail";
				return "{\"result\":\"fail\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"fail\"}";
		}
		
		
	}
	
	
	
	
	/**
	 * 查询项目推送记录
	 * @param loan
	 * @param m
	 * @return
	 */
	@RequestMapping("/pushRecords")
	public String pushRecords(String pageNo,Loan loan,Model m){
		if( pageNo == null || "".equals(pageNo) )
			pageNo = "1";
		//分页，带查询
		PageInfo<Loan> pageInfo = netLoanEysService.getPushRecords(pageNo,loan);
		NetLoanModel netLoanModel = netLoanEysService.getNetLoanCount(loan);
		NetLoanModel netLoan = netLoanEysService.getNetLoanSum(loan);
		m.addAttribute("pageInfo", pageInfo);
		m.addAttribute("netLoanModel", netLoanModel);
		m.addAttribute("netLoan", netLoan);
		m.addAttribute("loan", loan);
		return "netLoanEye/pushRecordsList";
	}
	/**
	 * 投资记录（只显示天眼用户的投资记录）(导出)
	 * @param m
	 * @return
	 */
	
	@RequestMapping(value="exportUserInvestInfo")
	public void exportUserInvestInfo(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanId", request.getParameter("loanId"));
		map.put("loanName", request.getParameter("loanName"));
		map.put("userId", request.getParameter("userId"));
		map.put("mobile", request.getParameter("mobile"));
		map.put("realname", request.getParameter("realname"));
		map.put("start", request.getParameter("datepicker"));
		String end = request.getParameter("datepicker1");
		if (StringUtils.isNotBlank(end)) {
			String endtime = DateUtil.addDay(end, 1);
			map.put("end", endtime);
		}
		map.put("status", request.getParameter("stLoanType"));
		map.put("regStart", request.getParameter("datepickerReg"));
		map.put("regEnd", request.getParameter("datepickerReg2"));
		map.put("whetherNew", request.getParameter("stUserType"));
		try {
			map = FormUtil.getForParamToBean(map);	
		
		List<Invest> investList=investService.getExportInvestInfo(map);
		for (Invest invest : investList) {
			Loan loan=invest.getLoan();
			String type=loan.getOperationType();
			if(type.equals("月")){
				Integer month=loan.getDeadline();
				invest.setLoanTime(month+"月");
			}else {
				Integer day=loan.getDay();
				invest.setLoanTime(day+"天");
			}
			
		}
		Map<String, String> title = new LinkedHashMap<>();
		title.put("loanId", "项目编号");
		title.put("loanName", "项目名称");
		title.put("loanTime","项目周期");
		title.put("id", "投资编号");
		title.put("status", "投资状态");
		title.put("investUserID", "投资人编号");
		title.put("investUserName", "投资人姓名");
		title.put("time", "投资时间");
		title.put("money", "投资本金");
		title.put("interest", "预期收益");
		title.put("paidInterest", "已收回收益");
		title.put("paidMoney", "已收回本金");
		title.put("isAutoInvest", "投标方式(是否自动投标)");
		title.put("userSource", "投标来源");
		int[] style = { 16, 20,10,30, 10, 22, 16 ,22,20,20,20,20,23,30};
		String fileName = DateUtil.DateToString(new Date(),
				"yyyyMMddHHmmss");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		fileName = "投资记录" + fileName + ".xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ fileName);
		ExcelConvertor excelConvertor = new ExcelConvertor(
				response.getOutputStream(), fileName);
		String t ="投资记录(网贷天眼)";
		excelConvertor.excelExport(investList, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 投资记录（只显示天眼用户的投资记录）
	 * @param m
	 * @return
	 */
	@RequestMapping("/investRecords")
	public String investRecords(HttpServletResponse response,
			HttpServletRequest request, Model model){
		int pageNo = 0;
		int pageSize = 10;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		String str="";
		Map<String, Object> map = new HashMap<String, Object>();
		String loanId=request.getParameter("loanId");
		if (loanId != null && !loanId.equals("")) {
				str += "&loanId=" + loanId;
				map.put("loanId",loanId );
			}
			
		String loanName=request.getParameter("loanName");		
		if (loanName != null && !loanName.equals("")) {
			str += "&loanName=" + loanName;
			map.put("loanName",loanName );
		}
		String userId=request.getParameter("userId");		
		if (userId != null && !userId.equals("")) {
			str += "&userId=" + userId;
			map.put("userId",userId );
		}
		String mobile=request.getParameter("mobile");		
		if (mobile != null && !mobile.equals("")) {
			str += "&mobile=" + mobile;
			map.put("mobile",mobile );
		}
		String realname=request.getParameter("realname");		
		if (realname != null && !realname.equals("")) {
			str += "&realname=" + realname;
			map.put("realname",realname );
		}
		String start=request.getParameter("start");		
		if (start != null && !start.equals("")) {
			str += "&start=" + start;
			map.put("start",start );
		}
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end)) {
			String endtime = DateUtil.addDay(end, 1);
			map.put("end", endtime);
			str += "&end=" + endtime;
		}
		
		String status=request.getParameter("status");		
		if (status != null && !status.equals("")) {
			str += "&status=" + status;
			map.put("status",status );
		}
		String regStart=request.getParameter("regStart");		
		if (regStart != null && !regStart.equals("")) {
			str += "&regStart=" + regStart;
			map.put("regStart",regStart );
		}
		String regEnd=request.getParameter("regEnd");		
		if (regEnd != null && !regEnd.equals("")) {
			str += "&regEnd=" + regEnd;
			map.put("regEnd",regEnd );
		}
		String whetherNew=request.getParameter("whetherNew");		
		if (whetherNew != null && !whetherNew.equals("")) {
			str += "&whetherNew=" + whetherNew;
			map.put("whetherNew",whetherNew );
		}
		try {
			map = FormUtil.getForParamToBean(map);	
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		PageInfo<Invest> pageInfo = investService.getInvestRecordsNetLoanEye(pageNo,
				pageSize, map);
		List<Invest> invests =	pageInfo.getResults();
		List<Invest>  investsByPush=new ArrayList<Invest>();
		for(int i=0;i<invests.size();i++){
			PushLoan   pushLoan =	netLoanEysService.selectByLoanId(invests.get(i).getLoanId());
			//设置已经推送的为1
			if(pushLoan!=null){
				invests.get(i).setIsPush(1);
			}
			investsByPush.add(invests.get(i));
		}
		
		pageInfo.setResults(investsByPush);
		request.setAttribute("pageInfo", pageInfo);

		String status1 = request.getParameter("status");
		String loanName1 = request.getParameter("loanName");
		String realname1 = request.getParameter("realname");
		try {
			if (status != null 
					&& !"".equals(status1)) {
				status1 = URLDecoder.decode(status1, "UTF-8");
			}
			if (loanName1 != null && !"".equals(loanName1)) {
				loanName1 = URLDecoder.decode(loanName1, "UTF-8");
			}
			if (realname1 != null && !"".equals(realname1)) {
				realname1 = URLDecoder.decode(realname1, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BigDecimal tlmc  = investService.getTotalMoneyForNetLoanEye(map);
		int toalNum = investService.getTotalNumForNetLoanEye(map);
		
		model.addAttribute("loanId", request.getParameter("loanId"));
		model.addAttribute("loanName", loanName);
		model.addAttribute("realname", realname);
		model.addAttribute("start", request.getParameter("start"));
		model.addAttribute("end", request.getParameter("end"));
		model.addAttribute("userId", request.getParameter("userId"));
		model.addAttribute("mobile", request.getParameter("mobile"));
		model.addAttribute("status", status);
		model.addAttribute("regStart", request.getParameter("regStart"));
		model.addAttribute("regEnd", request.getParameter("regEnd"));
		model.addAttribute("whetherNew", request.getParameter("whetherNew"));
		model.addAttribute("totalInvestMoney", tlmc);
		model.addAttribute("totalInvestNum", toalNum);
		request.setAttribute("str",str);
		return "netLoanEye/investRecordsList";
	}
	
	
	/**
	 * 补推管理
	 */
	@RequestMapping(value="/pushAgain")
	public String pushAgain(HttpServletResponse response,
			HttpServletRequest request, Model model){
		int pageNo = 0;
		int pageSize = 10;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		String str="";
		Map<String, Object> map = new HashMap<String, Object>();
		String loanId=request.getParameter("loanId");
		if (loanId != null && !loanId.equals("")) {
				str += "&loanId=" + loanId;
				map.put("loanId",loanId );
			}
			
		
		String userId=request.getParameter("userId");		
		if (userId != null && !userId.equals("")) {
			str += "&userId=" + userId;
			map.put("userId",userId );
		}
		
		String regStart=request.getParameter("regStart");		
		if (regStart != null && !regStart.equals("")) {
			str += "&regStart=" + regStart;
			map.put("regStart",regStart );
		}
		String regEnd=request.getParameter("regEnd");		
		if (regEnd != null && !regEnd.equals("")) {
			str += "&regEnd=" + regEnd;
			map.put("regEnd",regEnd );
		}
		String whetherNew=request.getParameter("whetherNew");		
		if (whetherNew != null && !whetherNew.equals("")) {
			str += "&whetherNew=" + whetherNew;
			map.put("whetherNew",whetherNew );
		}
		try {
			map = FormUtil.getForParamToBean(map);	
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		PageInfo<Invest> pageInfo = investService.getInvestRecordsNetLoanEye(pageNo,
				pageSize, map);
		
		request.setAttribute("pageInfo", pageInfo);

		BigDecimal tlmc  = investService.getTotalMoneyForNetLoanEye(map);
		int toalNum = investService.getTotalNumForNetLoanEye(map);
		
		model.addAttribute("loanId", request.getParameter("loanId"));
		model.addAttribute("userId", request.getParameter("userId"));
		model.addAttribute("regStart", request.getParameter("regStart"));
		model.addAttribute("regEnd", request.getParameter("regEnd"));
		model.addAttribute("whetherNew", request.getParameter("whetherNew"));
		model.addAttribute("totalInvestMoney", tlmc);
		model.addAttribute("totalInvestNum", toalNum);
		request.setAttribute("str",str);
		return "netLoanEye/pushLoanList";
	}

	/**
	 * 向天眼推送项目  并同送整个项目下所有网贷天眼用户的交易记录
	 * @param m
	 * @return
	 */
	@RequestMapping("/pushNetLoanAgain")
	@ResponseBody
	public String pushNetLoanAgain(String id,Model m,
			HttpServletRequest request,HttpServletResponse response){
		User user = GetLoginUser(request, response);
		if( netLoanEysService.pushNetLoan(id,user.getUserId()) >=1 ){
			
			Integer pushCode = pushService.pushInvestPerson(id);
			if(pushCode == 200){
				System.out.println("投资人信息推送成功："+pushCode);
				return "success";
			}else{
				System.out.println("投资人信息推送失败："+pushCode);
				return "fail";
			}
		}else {
			return "fail";
		}
		
	}
	/**
	 * 推送更新项目状态
	 */
	@RequestMapping("/pushNetLoanStatus")
	@ResponseBody
	public String pushNetLoanStatus(Model m,
			HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		Loan loan=loanService.get(id);
		if( netLoanEysService.pushNetLoanStatus(loan) ==200 ){
				return "success";
		}else {
			return "fail";
		}
	}
	
	
	/**
	 * 向天眼推送项目  并同送整个项目下所有网贷天眼用户的交易记录
	 * @param m
	 * @return
	 */
	@RequestMapping("/pushNetInvestByUserId")
	@ResponseBody
	public String pushNetInvestByUserId(String id,Model m,
			HttpServletRequest request,HttpServletResponse response){
		System.out.println("#####向天眼推送项目  并同送整个项目下所有网贷天眼用户的交易记录");
		User user = GetLoginUser(request, response);
		Map<String, Object> params=new HashMap<>();	
		String userId=request.getParameter("userId");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		if (userId != null && !userId.equals("")) {
			params.put("userId",getUserId(userId));
		}
		if (startTime != null && !startTime.equals("")) {
			params.put("startTime",startTime);
		}
		if (endTime != null && !endTime.equals("")) {
			params.put("endTime",endTime+"23:59");
		}
		Integer pushCode = pushService.pushInvestPersonByUserId(params);
			if(pushCode == 200){
				System.out.println("投资人信息推送成功："+pushCode);
				return "{\"result\":\"success\"}";
			}else{
				System.out.println("投资人信息推送失败："+pushCode);
				return "{\"result\":\"fail\"}";
			}
		
		}
		
	}
	


