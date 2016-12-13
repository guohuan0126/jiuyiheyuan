package com.duanrong.cps.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;




import org.springframework.web.bind.annotation.ResponseBody;

import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.authentication.service.AuthService;
import com.duanrong.authentication.service.GetMenu;
import com.duanrong.authentication.service.impl.AuthServiceImpl;
import com.duanrong.authentication.service.impl.GetMenuImpl;
import com.duanrong.business.permission.model.NewMenu;
import com.duanrong.business.permission.model.UserCookie;
import com.duanrong.cps.business.bsy.model.BsyInvest;
import com.duanrong.cps.business.bsy.model.BsyPushInterest;
import com.duanrong.cps.business.bsy.model.BsyPushRepayMent;
import com.duanrong.cps.business.bsy.model.BsyPushedInvest;
import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.bsy.model.PlatformUserRelation;
import com.duanrong.cps.business.bsy.service.BsyInvestService;
import com.duanrong.cps.business.bsy.service.BsyService;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.loan.service.LoanService;
import com.duanrong.cps.business.user.model.User;
import com.duanrong.util.DateUtil;


/**
 * 比搜益相关后台处理
 * @author bj
 *
 */
@Controller
@RequestMapping("/bsy")
public class BsyController extends BaseController{

	@Resource
	private LoanService loanService;
	@Resource
	private BsyService bsyService;
	
	@Resource
	private BsyInvestService bsyInvestService;
	
	private Logger  logger  = Logger.getLogger(BsyController.class) ;
	
	/**
	 * 查询可以推送比搜益的项目列表
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/getBsyWaitPushLoanList")
	public String list(String pageNo,Loan loan,Model m){
		if( pageNo == null || "".equals(pageNo) )
			pageNo = "1";
		//分页，带查询
		try{
			PageInfo<Loan> pageInfo = loanService.getBsyWaitPushLoanList(pageNo,loan);
			m.addAttribute("pageInfo", pageInfo);
			m.addAttribute("loan", loan);
		}
		catch(Exception e){
			logger.info("查询可推送比搜益项目异常", e);
		}
		
		return "bsy/bsy_loanList";
	}
	/**
	 * 向比搜益推送项目
	 * @param m
	 * @return
	 */
	@RequestMapping("/bsyPushLoan")
	@ResponseBody
	public String bsyPushLoan(String id,Model m,
			HttpServletRequest request,HttpServletResponse response){
		
		 AuthService authService=new AuthServiceImpl();
		 //User user = GetLoginUser(request, response);
		 UserCookie user = authService.getUser(request, response);
		 JSONObject json = new JSONObject();
		 System.out.println("############比搜益############：userID"+user.getUserId());
		 Map returnMap = bsyService.insertBsyPushLoan(id, user.getUserId());
		 
		// Map returnMap = bsyService.insertBsyPushLoan(id, "18210132758my");
		 String flag = returnMap.get("flag").toString();
		 if(flag.equals("0")){
			 json.put("flag", "fail");
			 return json.toString();
		 }
		 else{
			 json.put("flag", "success");
			
			 if(returnMap.get("failurecodes")!=null){
				 json.put("failurecodes", returnMap.get("failurecodes"));
			 }
			 else{
				 json.put("failurecodes","");
			 }
			return json.toString();
		 }
	}
	
   /**
    * 查询所有推送的交易记录
    */
	@RequestMapping(value="/bsyPushInvest")
	public String  bsyPushInvest(HttpServletRequest request,HttpServletResponse response,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "10";
		if (pageNo == null) {
			pageNo = "1";
		}
		Map<String,Object>params=new HashMap<String, Object>();
		String investId=request.getParameter("investId");
		String loanId=request.getParameter("loanId");
		String investTimeBegin=request.getParameter("investTimeBegin");
		String investTimeEnd=request.getParameter("investTimeEnd");
		
		if (investId != null && !investId.equals("")) {
			params.put("investId",investId );
		}
		if (loanId != null && !loanId.equals("")) {
			params.put("loanId",loanId );
		}
		if (investTimeBegin != null && !investTimeBegin.equals("")) {
			params.put("investTimeBegin",investTimeBegin );
		}
		if (investTimeEnd != null && !investTimeEnd.equals("")) {
			params.put("investTimeEnd",investTimeEnd+" 23:59" );
		}
		PageInfo<BsyPushedInvest> pageInfo =bsyService.getInvestStatus(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("investId",investId);
		model.addAttribute("loanId", loanId);
		model.addAttribute("investTimeBegin", investTimeBegin);
		model.addAttribute("investTimeEnd", investTimeEnd);
		return "bsy/bsyPushInvest";
	}
	
   /**
    * 描述：显示比搜益推送记录
 * @throws UnsupportedEncodingException 
    * 
    * */
	@RequestMapping(value="/getPushLoanHistory")
	public String getPushLoanHistory(HttpServletRequest request,HttpServletResponse response, String pageNo,Loan loan,Model m) throws UnsupportedEncodingException{
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String loanId=request.getParameter("loanId");
		String timeBegin=request.getParameter("timeBegin");
		String timeEnd=request.getParameter("timeEnd");
		String loanStatus="";
		if(request.getParameter("loanStatus")!=null){
			loanStatus=new String(request.getParameter("loanStatus").getBytes("iso-8859-1"), "utf-8");
		}
		String loanName="";
		if(request.getParameter("loanName")!=null){
			loanName=new String(request.getParameter("loanName").getBytes("iso-8859-1"), "utf-8");
		}
		String pushTimeBegin=request.getParameter("pushTimeBegin");
		String pushtimeEnd=request.getParameter("pushtimeEnd");
		String loanType="";
		if(request.getParameter("loanType")!=null){
			loanType= new String(request.getParameter("loanType").getBytes("iso-8859-1"), "utf-8");
		}
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		if(loanId!=null && !"".equals(loanId)){
			paramMap.put("loanId", loanId);
		}
		if(timeBegin!=null && !"".equals(timeBegin)){
			paramMap.put("timeBegin", timeBegin+" 00:00:00");
		}
		if(timeEnd!=null && !"".equals(timeEnd)){
			paramMap.put("timeEnd", timeEnd+" 23:59:59");
		}
		if(loanStatus!=null && !"".equals(loanStatus) && !"0".equals(loanStatus)){
			paramMap.put("loanStatus", loanStatus);
		}
		if(loanName!=null && !"".equals(loanName)){
			paramMap.put("loanName", loanName);
		}
		if(pushTimeBegin!=null && !"".equals(pushTimeBegin)){
			paramMap.put("pushTimeBegin", pushTimeBegin+" 00:00:00");
		}
		if(pushtimeEnd!=null && !"".equals(pushtimeEnd)){
			paramMap.put("pushtimeEnd", pushtimeEnd+" 23:59:59");
		}
		if(loanType!=null && !"".equals(loanType) && !"0".equals(loanType)){
			paramMap.put("loanType", loanType);
		}
		if( pageNo == null || "".equals(pageNo) )
			pageNo = "1";
		//分页，带查询
		try{
			PageInfo<Loan> pageInfo = loanService.getBsyLoanHistory(pageNo,paramMap);
			System.out.println(pageInfo.getResults().get(0).getNowTime());
			m.addAttribute("pageInfo", pageInfo);
			m.addAttribute("loan", loan);
			m.addAttribute("loanId", loanId);
			m.addAttribute("timeBegin", timeBegin);
			m.addAttribute("timeEnd",timeEnd);
			m.addAttribute("loanStatus", loanStatus);
			m.addAttribute("loanName", loanName);
			m.addAttribute("pushTimeBegin", pushTimeBegin);
			m.addAttribute("pushtimeEnd", pushtimeEnd);
			m.addAttribute("loanType", loanType);
		}
		catch(Exception e){
			logger.info("显示比搜益推送记录异常", e);
		}
		
		return "bsy/bsyLoanHistory";
		
	}
	/**
	 * 导出比搜益用户的投资记录 
	 */
	@RequestMapping(value="/exportUserInvestInfo")
	public void exportUserInvestInfo(HttpServletRequest request,
			HttpServletResponse response){
		String pageNo =null;
		String pageSize = "99999999";
		if (pageNo == null) {
			pageNo = "1";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("investId", request.getParameter("investId"));
		map.put("loanId", request.getParameter("loanId"));
		map.put("userId", request.getParameter("userId"));
		map.put("investTimeBegin", request.getParameter("investTimeBegin"));
		String investTimeEnd=request.getParameter("investTimeEnd");
		if (investTimeEnd != null && !investTimeEnd.equals("")) {
			map.put("investTimeEnd",investTimeEnd+" 23:59" );
		}
		map.put("userType", request.getParameter("userType"));
		map.put("type", "BSY");
		PageInfo<InvestByBsy> pageInfo =bsyService.getInvestRecordsByBsy(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), map);
		List<InvestByBsy>list=pageInfo.getResults();
		for (InvestByBsy investByBsy : list) {
		
			String type=investByBsy.getOperationType();
		if(StringUtils.isNotBlank(type)){
			 if(type.equals("月")){
					String month=investByBsy.getMonth();
					investByBsy.setLoanTime(month+"月");
				}else {
					String day=investByBsy.getDays();
					investByBsy.setLoanTime(day+"天");
				}
			 
		 }			
		}
		
		try {
			Map<String, String> title = new LinkedHashMap<>();
			title.put("investId", "投资编号");
			title.put("interest", "预期收益");
			title.put("investMoney","投资金额");
			title.put("rate", "年化利率");
			title.put("investStatus", "投资状态");
			title.put("investTime", "投资时间");
			title.put("loanId", "项目编号");
			title.put("loanName", "项目名称");
			title.put("loanTime", "项目周期");
			title.put("userId", "用户编号");
			title.put("userName", "用户姓名");
			int[] style = { 16, 20,10,30, 10, 22, 16 ,22,20,20,20};
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
			String time=format.format(date);
			String fileName = time;
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "投资记录" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			String t ="投资记录(比搜益)";
			excelConvertor.excelExport(list, title, t, style);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * 查询所有比搜益用户的投资记录
	 */
	@RequestMapping(value="/investByBsy")
	public String investByBsy(HttpServletRequest request,HttpServletResponse response,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "10";
		if (pageNo == null) {
			pageNo = "1";
		}
		String userId=request.getParameter("userId");
		String investId=request.getParameter("investId");
		String loanId=request.getParameter("loanId");
		String investTimeBegin=request.getParameter("investTimeBegin");
		String investTimeEnd=request.getParameter("investTimeEnd");
		String userType=request.getParameter("userType");
		Map<String,Object>params=new HashMap<String, Object>();

		if (userType != null && !userType.equals("")) {
			params.put("userType",userType );
		}
		if (investId != null && !investId.equals("")) {
			params.put("investId",investId );
		}
		if (userId != null && !userId.equals("")) {
			params.put("userId",userId );
		}
		if (loanId != null && !loanId.equals("")) {
			params.put("loanId",loanId );
		}
		if (investTimeBegin != null && !investTimeBegin.equals("")) {
			params.put("investTimeBegin",investTimeBegin );
		}
		if (investTimeEnd != null && !investTimeEnd.equals("")) {
			params.put("investTimeEnd",investTimeEnd+" 23:59" );
		}
		params.put("type", "BSY");
		PageInfo<InvestByBsy> pageInfo =bsyService.getInvestRecordsByBsy(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		List<InvestByBsy> list=pageInfo.getResults();
		for (InvestByBsy investByBsy : list) {
			String mobileNo=investByBsy.getMobileNumber();
			String mobileNo2 = mobileNo.substring(7);
			mobileNo = "*******" + mobileNo2;
			investByBsy.setMobileNumber(mobileNo);
		}
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("investId", investId);
		model.addAttribute("userId", userId);
		model.addAttribute("loanId", loanId);
		model.addAttribute("investTimeBegin", investTimeBegin);
		model.addAttribute("investTimeEnd", investTimeEnd);
		model.addAttribute("userType", userType);
		return "bsy/investRecordByBsy";
	}
	/**
	 * 推送起息状态记录
	 */
	@RequestMapping(value="/bsyPushInterest")
	public String bsyPushInterest(HttpServletRequest request,HttpServletResponse response,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "10";
		if (pageNo == null) {
			pageNo = "1";
		}
		String investId=request.getParameter("investId");
		String loanId=request.getParameter("loanId");
		String userId=request.getParameter("userId");
		String pushTimeBegin=request.getParameter("pushTimeBegin");
		String pushTimeEnd=request.getParameter("pushTimeEnd");
		Map<String,Object>params=new HashMap<String, Object>();
		if (investId != null && !investId.equals("")) {
			params.put("investId",investId );
		}
		if (userId != null && !userId.equals("")) {
			params.put("userId",userId );
		}
		if (loanId != null && !loanId.equals("")) {
			params.put("loanId",loanId );
		}
		if (pushTimeBegin != null && !pushTimeBegin.equals("")) {
			params.put("pushTimeBegin",pushTimeBegin );
		}
		if (pushTimeEnd != null && !pushTimeEnd.equals("")) {
			params.put("pushTimeEnd",pushTimeEnd+" 23:59" );
		}
		PageInfo<BsyPushInterest> pageInfo =bsyService.getbsyPushInterest(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("investId", investId);
		model.addAttribute("userId", userId);
		model.addAttribute("loanId", loanId);
		model.addAttribute("pushTimeBegin", pushTimeBegin);
		model.addAttribute("pushTimeEnd", pushTimeEnd);
		return "bsy/bsyPushInterest";
	}
	
	/**
	 * 推送回款状态
	 */
	@RequestMapping(value="/bsyPushRepayMent")
	public String bsyPushRepayMent(HttpServletRequest request,HttpServletResponse response,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "10";
		if (pageNo == null) {
			pageNo = "1";
		}
		Map<String,Object>params=new HashMap<String, Object>();
		String investId=request.getParameter("investId");
		String loanId=request.getParameter("loanId");
		String userId=request.getParameter("userId");
		String pushTimeBegin=request.getParameter("pushTimeBegin");
		String pushTimeEnd=request.getParameter("pushTimeEnd");
		if (investId != null && !investId.equals("")) {
			params.put("investId",investId );
		}
		if (userId != null && !userId.equals("")) {
			params.put("userId",userId );
		}
		if (loanId != null && !loanId.equals("")) {
			params.put("loanId",loanId );
		}
		if (pushTimeBegin != null && !pushTimeBegin.equals("")) {
			params.put("pushTimeBegin",pushTimeBegin );
		}
		if (pushTimeEnd != null && !pushTimeEnd.equals("")) {
			params.put("pushTimeEnd",pushTimeEnd+" 23:59" );
		}
		PageInfo<BsyPushRepayMent> pageInfo =bsyService.getbsyPushRepayMent(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("investId", investId);
		model.addAttribute("userId", userId);
		model.addAttribute("loanId", loanId);
		model.addAttribute("pushTimeBegin", pushTimeBegin);
		model.addAttribute("pushTimeEnd", pushTimeEnd);
		return "bsy/bsyPushRepayMent";
	}
	/**
	 *  查询比搜益用户的个人信息
	 */
	@RequestMapping(value="/bsyUserInfo")
	public String bsyUserInfo(HttpServletRequest request,HttpServletResponse response,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "10";
		if (pageNo == null) {
			pageNo = "1";
		}
		Map<String,Object>params=new HashMap<String, Object>();
		String userId=request.getParameter("userId");
		String TimeBegin=request.getParameter("TimeBegin");
		String TimeEnd=request.getParameter("TimeEnd");
		if (userId != null && !userId.equals("")) {
			params.put("userId",userId );
		}
		if (TimeBegin != null && !TimeBegin.equals("")) {
			params.put("TimeBegin",TimeBegin );
		}
		if (TimeEnd != null && !TimeEnd.equals("")) {
			params.put("TimeEnd",TimeEnd+" 23:59");
		}
		params.put("type", "BSY");
		PageInfo<PlatformUserRelation> pageInfo =bsyService.getBsyUserInfo(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("userId", userId);
		model.addAttribute("TimeBegin", TimeBegin);
		model.addAttribute("TimeEnd", TimeEnd);
		return "bsy/bsyUserInfo";
	}

	/**
	 * 得到用户所拥有的菜单
	 * @param systypeId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getMenu")
	public void getMenu(String systypeId,HttpServletRequest request,HttpServletResponse response){
		AuthService authService=new AuthServiceImpl();
		UserCookie user= authService.getUser(request, response);
		String userID="";
		if(user!=null){
			userID=user.getUserId();
		}
		List<NewMenu>newMenu=authService.getMenu(userID, "dbef77f4c2854777ad8fb2bce75f4c7a");
		JSONArray jsonArry=JSONArray.fromObject(newMenu);
		try {
			response.setCharacterEncoding("UTF-8");
	        response.getWriter().print(jsonArry.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
