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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.platform.model.DemandBill;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.platform.service.PlatformService;

@Controller
@RequestMapping(value="lixinzk")
public class LixinzkController extends BaseController{
	
	@Autowired
	private PlatformService platformService;
	
	/***
	 * 查询利信众客来源的用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getUserInfo")
	public ModelAndView getuserInfo(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelView=new ModelAndView("lixinzk/lixinzkUserInfo");
		Integer pageNo=1;
		Integer pageSize = 10;
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("userSource", "lixinzk");
		String userId=request.getParameter("userId");
		String TimeBegin=request.getParameter("TimeBegin");
		String TimeEnd=request.getParameter("TimeEnd");
		if (userId != null && !userId.equals("")) {
			params.put("userId",userId );
		}
		if (TimeBegin != null && !TimeBegin.equals("")) {
			params.put("registTimeBegin",TimeBegin );
		}
		if (TimeEnd != null && !TimeEnd.equals("")) {
			params.put("registTimeEnd",TimeEnd+" 23:59:59");
		}
		if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		PageInfo<PlatformUserRelation> userInfoList=platformService.queryUserByUserSrouce(pageNo, pageSize, params);
		modelView.addObject("userId", userId);
		modelView.addObject("TimeBegin", TimeBegin);
		modelView.addObject("TimeEnd", TimeEnd);
		modelView.addObject("pageInfo", userInfoList);
		return modelView;
	}
	
	/***
	 * 查询利信众客来源的用户信息提供给第三方页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/index/getUserInfo")
	public ModelAndView getuserInfoForThird(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelView=new ModelAndView("lixinzk/lixinzkUserInfoForThird");
		Integer pageNo=1;
		Integer pageSize = 10;
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("userSource", "lixinzk");
		String userId=request.getParameter("userId");
		String TimeBegin=request.getParameter("TimeBegin");
		String TimeEnd=request.getParameter("TimeEnd");
		if (userId != null && !userId.equals("")) {
			params.put("userId",userId );
		}
		if (TimeBegin != null && !TimeBegin.equals("")) {
			params.put("registTimeBegin",TimeBegin );
		}
		if (TimeEnd != null && !TimeEnd.equals("")) {
			params.put("registTimeEnd",TimeEnd+" 23:59:59");
		}
		if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		PageInfo<PlatformUserRelation> userInfoList=platformService.queryUserByUserSrouce(pageNo, pageSize, params);
		modelView.addObject("userId", userId);
		modelView.addObject("TimeBegin", TimeBegin);
		modelView.addObject("TimeEnd", TimeEnd);
		modelView.addObject("pageInfo", userInfoList);
		return modelView;
	}
	
	/**
     *为第三方查询投资记录
	 */
	@RequestMapping(value="/investInfoForThird")
	public ModelAndView investByLixinzkForThird(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelView=new ModelAndView("lixinzk/lixinzkInvestForThird");
		Integer pageNo=1;
		Integer pageSize = 10;
		if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("userSource", "lixinzk");
		
		String userId=request.getParameter("userId");
		String investId=request.getParameter("investId");
		String loanId=request.getParameter("loanId");
		String investTimeBegin=request.getParameter("investTimeBegin");
		String investTimeEnd=request.getParameter("investTimeEnd");
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
		
		PageInfo<InvestByBsy> pageInfo =platformService.queryUserInvestByUserSource(pageNo, pageSize, params);
		
		modelView.addObject("pageInfo", pageInfo);
		modelView.addObject("pageInfo", pageInfo);
		modelView.addObject("investId", investId);
		modelView.addObject("userId", userId);
		modelView.addObject("loanId", loanId);
		modelView.addObject("investTimeBegin", investTimeBegin);
		modelView.addObject("investTimeEnd", investTimeEnd);
		return modelView;
	}
	
	
	/**
	 * 查询利信众客用户的投资记录
	 */
	@RequestMapping(value="/investInfo")
	public ModelAndView investByLixinzk(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelView=new ModelAndView("lixinzk/lixinzkInvestForOur");
		Integer pageNo=1;
		Integer pageSize = 10;
		if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("userSource", "lixinzk");
		
		String userId=request.getParameter("userId");
		String investId=request.getParameter("investId");
		String loanId=request.getParameter("loanId");
		String investTimeBegin=request.getParameter("investTimeBegin");
		String investTimeEnd=request.getParameter("investTimeEnd");
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
		
		PageInfo<InvestByBsy> pageInfo =platformService.queryUserInvestByUserSource(pageNo, pageSize, params);
		
		modelView.addObject("pageInfo", pageInfo);
		modelView.addObject("pageInfo", pageInfo);
		modelView.addObject("investId", investId);
		modelView.addObject("userId", userId);
		modelView.addObject("loanId", loanId);
		modelView.addObject("investTimeBegin", investTimeBegin);
		modelView.addObject("investTimeEnd", investTimeEnd);
		return modelView;
	}
	
	
	
	/**
     *为第三方查询投资记录单独页面
	 */
	@RequestMapping(value="/index")
	public ModelAndView investByLixinzkForThirdIndex(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelView=new ModelAndView("lixinzk/lixinzkIndex");
		String key=request.getParameter("key");
		if(!"m040bd9172333e5aefe288023a0f222".equals(key)){
			return new ModelAndView("lixinzk/error");
		}else{
			Integer pageNo=1;
			Integer pageSize = 10;
			if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo"))) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			
			Map<String,Object>params=new HashMap<String,Object>();
			params.put("userSource", "lixinzk");
			
			String userId=request.getParameter("userId");
			String investId=request.getParameter("investId");
			String loanId=request.getParameter("loanId");
			String investTimeBegin=request.getParameter("investTimeBegin");
			String investTimeEnd=request.getParameter("investTimeEnd");
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
			PageInfo<InvestByBsy> pageInfo =platformService.queryUserInvestByUserSource(pageNo, pageSize, params);
			modelView.addObject("pageInfo", pageInfo);
			modelView.addObject("pageInfo", pageInfo);
			modelView.addObject("investId", investId);
			modelView.addObject("userId", userId);
			modelView.addObject("loanId", loanId);
			modelView.addObject("investTimeBegin", investTimeBegin);
			modelView.addObject("investTimeEnd", investTimeEnd);
			return modelView;
		}
	}
	
	
	/**
	 * 查询活期投资记录
	 */
	
	@RequestMapping(value="/demandBill")
	public ModelAndView investDemandBill(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelView=new ModelAndView("lixinzk/demandBillForOur");
		
			Integer pageNo=1;
			Integer pageSize = 10;
			if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo"))) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			Map<String,Object>params=new HashMap<String,Object>();
			params.put("mobileNumber", request.getParameter("mobileNumber"));
			params.put("type", request.getParameter("type"));
			params.put("investTimeBegin", request.getParameter("investTimeBegin"));
			String investTimeEnd=request.getParameter("investTimeEnd");
			if (investTimeEnd != null && !investTimeEnd.equals("")) {
				params.put("investTimeEnd",investTimeEnd+" 23:59" );
			}
			params.put("userSource", "lixinzk");
			PageInfo<DemandBill> pageInfo =platformService.queryDemandBill(pageNo, pageSize, params);
			modelView.addObject("mobileNumber", request.getParameter("mobileNumber"));
			modelView.addObject("type", request.getParameter("type"));
			modelView.addObject("investTimeBegin", request.getParameter("investTimeBegin"));
			modelView.addObject("investTimeEnd", investTimeEnd);
			modelView.addObject("pageInfo", pageInfo);
			modelView.addObject("urlSource", "our");
		return modelView;
	}
	
	/***
	 * 为第三方查询活期投资记录单独页面
	 */
	@RequestMapping(value="/index/demandBill")
	public ModelAndView investByLixinzkForDemandBill(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelView=new ModelAndView("lixinzk/demandBillForThird");
		String key=request.getParameter("key");
		if(!"m040bd9172333e5aefe288023a0f222".equals(key)){
			return new ModelAndView("lixinzk/error");
		}else{
			Integer pageNo=1;
			Integer pageSize = 10;
			if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo"))) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			Map<String,Object>params=new HashMap<String,Object>();
			params.put("mobileNumber", request.getParameter("mobileNumber"));
			params.put("type", request.getParameter("type"));
			params.put("investTimeBegin", request.getParameter("investTimeBegin"));
			String investTimeEnd=request.getParameter("investTimeEnd");
			if (investTimeEnd != null && !investTimeEnd.equals("")) {
				params.put("investTimeEnd",investTimeEnd+" 23:59" );
			}
			params.put("userSource", "lixinzk");
			PageInfo<DemandBill> pageInfo =platformService.queryDemandBill(pageNo, pageSize, params);
			modelView.addObject("mobileNumber", request.getParameter("mobileNumber"));
			modelView.addObject("type", request.getParameter("type"));
			modelView.addObject("investTimeBegin", request.getParameter("investTimeBegin"));
			modelView.addObject("investTimeEnd", investTimeEnd);
			modelView.addObject("pageInfo", pageInfo);
		}
		return modelView;
	}
	
	
	/**
	 * 导出利信众客用户活期投资记录
	 */
    @RequestMapping(value="/index/exportDemandBill")
	public void exportDemandBill(HttpServletRequest request, HttpServletResponse response){
    	String pageNo =null;
		String pageSize = "99999999";
		if (pageNo == null) {
			pageNo = "1";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobileNumber", request.getParameter("mobileNumber"));
		params.put("type", request.getParameter("type"));
		params.put("investTimeBegin", request.getParameter("investTimeBegin"));
		String investTimeEnd=request.getParameter("investTimeEnd");
		if (investTimeEnd != null && !investTimeEnd.equals("")) {
			params.put("investTimeEnd",investTimeEnd+" 23:59" );
		}
		
		params.put("userSource", "lixinzk");
		PageInfo<DemandBill> pageInfo =platformService.queryDemandBill(Integer.parseInt(pageNo), Integer.parseInt(pageSize), params);
		
		List<DemandBill>list=pageInfo.getResults();
		
		try {
			if(list!=null && list.size()>0){	
				for (DemandBill demandBill : list) {
					String type=demandBill.getType();
					if("in".equals(type)){
						demandBill.setType("投资");
					}else if("out".equals(type)){
						demandBill.setType("赎回");
					}
				}
			}
			Map<String, String> title = new LinkedHashMap<>();
			title.put("realname", "用户姓名");
			title.put("mobileNumber", "手机号");
			title.put("createTime", "交易时间");
			title.put("money", "交易金额");
			title.put("type","交易类型");
			int[] style = { 20, 20,20,20, 20};
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
			String time=format.format(date);
			String fileName = time;
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "活期投资记录" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			String t ="活期投资记录(利信众客)";
			excelConvertor.excelExport(list, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
	
	/**
	 * 导出利信众客用户的投资记录 
	 */
	@RequestMapping(value="/index/exportUserInvestInfoLinxizk")
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
		
		map.put("userSource", "lixinzk");
		PageInfo<InvestByBsy> pageInfo =platformService.queryUserInvestByUserSource(Integer.parseInt(pageNo),
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
				
				String isAutoInvest=investByBsy.getIsAutoInvest();
				if("1".equals(isAutoInvest)){
					investByBsy.setIsAutoInvest("自动");
				}else{
					investByBsy.setIsAutoInvest("手动");
				}
			}
		}
		
		String isThird=request.getParameter("type");
		
		Map<String, String> title = new LinkedHashMap<>();
		if(StringUtils.isNotBlank(isThird)){
			title.put("loanName", "项目名称");
			title.put("loanTime", "项目周期");
			title.put("investStatus", "投资状态");
			title.put("mobileNumber", "手机号");
			title.put("userName", "用户姓名");
			title.put("investTime", "投资时间");
			title.put("investMoney","投资金额");
			title.put("interest", "预期收益");
			title.put("rate", "年化利率");
			title.put("isAutoInvest", "投标方式");
		}else{
			title.put("investId", "投资编号");
			title.put("loanId", "项目编号");
			title.put("loanName", "项目名称");
			title.put("userId", "用户编号");
			title.put("userName", "用户姓名");
			title.put("investMoney","投资金额");
			title.put("loanTime", "项目周期");
			title.put("isAutoInvest", "投标方式");
			title.put("investTime", "投资时间");
			title.put("rate", "年化利率");
			title.put("interest", "预期收益");
			title.put("investStatus", "投资状态");
			title.put("userSource", "投标来源");
			title.put("redMoneyOrRate", "优惠券");
			title.put("activityName", "活动名称");
			title.put("activityId", "活动ID");
		}
			int[] style = { 16, 20,10,30, 10, 22, 16 ,22,20,20,20,20,20,20,20,20};
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
			String t ="投资记录(利信众客)";
			excelConvertor.excelExport(list, title, t, style);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
}
