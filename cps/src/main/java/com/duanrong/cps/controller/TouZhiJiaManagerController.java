package com.duanrong.cps.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.platform.service.PlatformService;

@Controller
@RequestMapping(value="/touzhijia")
public class TouZhiJiaManagerController extends BaseController{

	
	@Autowired
	private PlatformService platformService;

	/**
	 * 查询投之家用户信息
	 * @param request
	 * @param response	
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/UserInfo")
	public String bjsUserInfo(HttpServletRequest request,HttpServletResponse response,Model model){
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
		params.put("type", "TouZhiJia");
		PageInfo<PlatformUserRelation> pageInfo =platformService.getPlatformUserInfo(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("userId", userId);
		model.addAttribute("TimeBegin", TimeBegin);
		model.addAttribute("TimeEnd", TimeEnd);
		return "touzhijia/touzhijiaUserInfo";
	}
	
	
	/**
	 * 查询所有投之家用户的投资记录
	 */
	@RequestMapping(value="/investInfo")
	public String investByBjs(HttpServletRequest request,HttpServletResponse response,Model model){
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
		params.put("type", "TouZhiJia");
		if (userType != null && !userType.equals("")) {  //新老用户条件
			if("3".equals(userType)){
				params.put("investmethod","TouZhiJia");
				
			}else{
				params.put("isNewUser",Integer.parseInt(userType));	
			}
								
		}else{
			params.put("isNewUser",null);	
		}		
		PageInfo<InvestByBsy> pageInfo =platformService.getInvestRecords(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		List<InvestByBsy> list=pageInfo.getResults();	
//      if(list!=null && list.size()>0){	
//		for (InvestByBsy investByBsy : list) {
//			String mobileNo=investByBsy.getMobileNumber();
//			String mobileNo2 = mobileNo.substring(7);
//			mobileNo = "*******" + mobileNo2;
//			investByBsy.setMobileNumber(mobileNo);
//		}
//      }
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("investId", investId);
		model.addAttribute("userId", userId);
		model.addAttribute("loanId", loanId);
		model.addAttribute("investTimeBegin", investTimeBegin);
		model.addAttribute("investTimeEnd", investTimeEnd);
		model.addAttribute("userType", userType);
		return "touzhijia/investRecord";
	}
	
	
	/**
	 * 导出投之家用户的投资记录 
	 */
	@RequestMapping(value="/exportUserInvestInfoTouZhiJia")
	public void exportUserInvestInfoBjs(HttpServletRequest request,
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
		if (request.getParameter("userType") != null && !request.getParameter("userType").equals("")) {
			if("3".equals(request.getParameter("userType"))){
				map.put("investmethod", "TouZhiJia");
			}else{
				map.put("isNewUser", Integer.parseInt(request.getParameter("userType")));
			}
									
		}else{
			map.put("isNewUser",null);	
		}				
		map.put("type", "TouZhiJia");
		PageInfo<InvestByBsy> pageInfo =platformService.getInvestRecords(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), map);
		List<InvestByBsy>list=pageInfo.getResults();
		
		try {
		
		if(list!=null && list.size()>0){	
		for (InvestByBsy investByBsy : list) {
		
			
			String type=investByBsy.getOperationType();
			if("月".equals(type)){
				String month=investByBsy.getDeadline()+"";
				investByBsy.setLoanTime(month+"月");
			}else {
				String day=investByBsy.getDays();
				investByBsy.setLoanTime(day+"天");
			}
			
			String radType=investByBsy.getRedType();
			if("money".equals(radType)){
				investByBsy.setRedMoneyOrRate(investByBsy.getRedMoney()+"元");
			}else if("rate".equals(radType)){
				investByBsy.setRedMoneyOrRate((investByBsy.getRedRate()*100)+"%");
			}
			
		}
		}
		
			Map<String, String> title = new LinkedHashMap<>();
			title.put("investId", "投资编号");
			title.put("loanId", "项目编号");
			title.put("loanName", "项目名称");
			title.put("userId", "用户编号");
			title.put("userName", "用户姓名");
			title.put("investMoney","投资金额");
			title.put("loanTime", "项目周期");
			title.put("investTime", "投资时间");
			title.put("rate", "年化利率");
			title.put("interest", "预期收益");
			title.put("investStatus", "投资状态");
			title.put("userSource", "投标来源");
			title.put("redMoneyOrRate", "优惠券");
			title.put("activityName", "活动名称");
			title.put("activityId", "活动ID");
			
			
			int[] style = { 16, 20,10,30, 10, 22, 16 ,22,20,20,20,20,20,20,20};
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
			String t ="投资记录(投之家)";
			excelConvertor.excelExport(list, title, t, style);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
}
