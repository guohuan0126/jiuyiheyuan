package com.duanrong.cps.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.authentication.service.AuthService;
import com.duanrong.authentication.service.impl.AuthServiceImpl;
import com.duanrong.business.permission.model.UserCookie;
import com.duanrong.cps.business.aiyouqian.model.AiyouqianPushLoan;
import com.duanrong.cps.business.aiyouqian.service.AiyouqianService;
import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.platform.service.PlatformPushService;
import com.duanrong.cps.business.platform.service.PlatformService;
import com.duanrong.cps.util.HttpUtil;
import com.duanrong.cps.util.MD5Encry;
import com.duanrong.cps.util.ReadProperties;

@Controller
@RequestMapping(value="aiyouqian")
public class AiyouqianController  extends BaseController{
	
	@Autowired 
	private AiyouqianService aiyouqianService;
	@Autowired
	private PlatformService platformService;
	@Autowired
	private PlatformPushService platformPushService;
	
	private String url="";
	private String aiyouqian_partnerId="";
	private String aiyouqian_key="";
	private String version="";
	
	@RequestMapping("/aiyouqianPushLoanList")
	@ResponseBody
	public Map<String,Object>aiyouqianPushLoanList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> result=new HashMap<String,Object>();
		AuthService authService=new AuthServiceImpl();
		 UserCookie user = authService.getUser(request, response);
		 JSONArray loanIdArray=JSONArray.fromObject(request.getParameter("loanIds"));
		 String Message="";
		 for(int i=0; i<loanIdArray.size(); i++){
			 String loanId=loanIdArray.getString(i);
			 String userId="";
			 if(user!=null){
				 userId=user.getUserId();
			 }else{
				 userId="18210132758my";
			 }
			 Map<String,Object> pushResult= this.pushToAiyouqian(loanId, userId);
			 if(!"200".equals(pushResult.get("code"))){
				 Message=Message+loanId+":"+pushResult.get("result")+";";
			 }
		 }
		 if(!"".equals(Message)){
			 result.put("result", Message);
		 }else{
			 result.put("result", "推送成功");
		 }
		 return result;
	}
	
	
	
	public Map<String,Object> pushToAiyouqian(String loanId, String userId){
		url=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_url");
		aiyouqian_partnerId=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_partnerId");
		aiyouqian_key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_key");
		version=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","version");
		 
		 Map<String,Object> returnMap = aiyouqianService.InsertPushLoanToAiyouqian(loanId, userId);
		 List<AiyouqianPushLoan> resultList= (List<AiyouqianPushLoan>) returnMap.get("result");
		 String dataJson=null;
		 try {
			dataJson=JSONObject.toJSONString(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		 String nowTimeStr=sdf.format(new Date());
		 String str="_input_charset=utf-8&"
		 		+ "data="+dataJson+"&"
		 		+ "partner_id="+aiyouqian_partnerId+"&"
		 		+"request_time="+nowTimeStr+"&"
		 		+ "service=borrowing_target&"
		 		+ "version="+version
		 		+ aiyouqian_key;
		 System.out.println("爱有钱sign明文："+str);
		 String sign=MD5Encry.MD5Low(str);
		 JSONObject jsonObject=new JSONObject();
		 jsonObject.put("version", version);
		 jsonObject.put("partner_id", aiyouqian_partnerId);
		 jsonObject.put("_input_charset", "utf-8");
		 jsonObject.put("request_time", nowTimeStr);
		 jsonObject.put("sign_type", "MD5");
		 jsonObject.put("service", "borrowing_target");
		 jsonObject.put("sign", sign);
		 jsonObject.put("data", dataJson);
		System.out.println("爱有钱参数："+jsonObject);
		JSONObject resultObject = HttpUtil.platformExecutePost(jsonObject, url);
		String code=resultObject.getJSONObject("content").getString("response_code");
		String message=ReadProperties.getPropetiesValue("constant/aiyouqianStatus.properties",code);
		String result="";
		if("200".equals(code)){
			result="推送成功";
			aiyouqianService.insertToPushLoan(resultList,"");
		}else{
			result="推送失败!"+message;
		}
		System.out.println(resultObject);
		Map<String, Object>resultMap=new HashMap<String,Object>();
		resultMap.put("result", result);
		resultMap.put("code", code);
		return resultMap;
	}
	
	/**
	 * 向爱有钱推送项目
	 * @param m
	 * @return
	 */
	@RequestMapping("/aiyouqianPushLoan")
	@ResponseBody
	public Map<String,Object> bsyPushLoan(String id,Model m,
			HttpServletRequest request,HttpServletResponse response){
		url=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_url");
		aiyouqian_partnerId=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_partnerId");
		aiyouqian_key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_key");
		version=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","version");
		 AuthService authService=new AuthServiceImpl();
		 UserCookie user = authService.getUser(request, response);
		 Map<String,Object> returnMap = aiyouqianService.InsertPushLoanToAiyouqian(id, user.getUserId());
		 List<AiyouqianPushLoan> resultList= (List<AiyouqianPushLoan>) returnMap.get("result");
		 String dataJson=null;
		 try {
			dataJson=JSONObject.toJSONString(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		 String nowTimeStr=sdf.format(new Date());
		 String str="_input_charset=utf-8&"
		 		+ "data="+dataJson+"&"
		 		+ "partner_id="+aiyouqian_partnerId+"&"
		 		+"request_time="+nowTimeStr+"&"
		 		+ "service=borrowing_target&"
		 		+ "version="+version
		 		+ aiyouqian_key;
		 System.out.println("爱有钱sign明文："+str);
		 String sign=MD5Encry.MD5Low(str);
		 JSONObject jsonObject=new JSONObject();	
		 jsonObject.put("version", version);
		 jsonObject.put("partner_id", aiyouqian_partnerId);
		 jsonObject.put("_input_charset", "utf-8");
		 jsonObject.put("request_time", nowTimeStr);
		 jsonObject.put("sign_type", "MD5");
		 jsonObject.put("service", "borrowing_target");
		 jsonObject.put("sign", sign);
		 jsonObject.put("data", dataJson);
		System.out.println("爱有钱参数："+jsonObject);
		JSONObject resultObject = HttpUtil.platformExecutePost(jsonObject, url);
		String code=resultObject.getJSONObject("content").getString("response_code");
		String message=ReadProperties.getPropetiesValue("constant/aiyouqianStatus.properties",code);
		String result="";
		if("200".equals(code)){
			result="推送成功";
			aiyouqianService.insertToPushLoan(resultList,"");
		}else{
			result="推送失败!"+message;
		}
		System.out.println(resultObject);
		Map<String, Object>resultMap=new HashMap<String,Object>();
		resultMap.put("result", result);
		return resultMap;
	}
	
	
	/**
	 * 查询爱有钱用户信息
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
		params.put("type", "aiyouqian");
		PageInfo<PlatformUserRelation> pageInfo =platformService.getPlatformUserInfo(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("userId", userId);
		model.addAttribute("TimeBegin", TimeBegin);
		model.addAttribute("TimeEnd", TimeEnd);
		return "aiyouqian/aiyouqianUserInfo";
	}
	
	
	/**
	 * 查询所有爱有钱用户的投资记录
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
		params.put("type", "aiyouqian");
		if (userType != null && !userType.equals("")) {  //新老用户条件
			if("3".equals(userType)){
				params.put("investmethod","aiyouqian");
				
			}else{
				params.put("isNewUser",Integer.parseInt(userType));	
			}
								
		}else{
			params.put("isNewUser",null);	
		}		
		PageInfo<InvestByBsy> pageInfo =platformService.getInvestRecords(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		List<InvestByBsy> list=pageInfo.getResults();	
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("investId", investId);
		model.addAttribute("userId", userId);
		model.addAttribute("loanId", loanId);
		model.addAttribute("investTimeBegin", investTimeBegin);
		model.addAttribute("investTimeEnd", investTimeEnd);
		model.addAttribute("userType", userType);
		return "aiyouqian/investRecord";
	}
	
	/**
	 * 爱有钱已推标的记录
	 */
	@RequestMapping(value="/getPushLoanHistory")
	public String getPushLoanHistory(HttpServletRequest request,HttpServletResponse response, String pageNo,Loan loan,Model m) throws UnsupportedEncodingException{
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
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
			paramMap.put("thridType", "aiyouqian");
			PageInfo<Loan> pageInfo = platformPushService.getLoanHistory(Integer.parseInt(pageNo), 10, paramMap);
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
			e.printStackTrace();
		}
		return "aiyouqian/aiyouqianLoanHistory";
	}
	
	/**
	 * 爱有钱已推投资记录
	 */
	@RequestMapping(value="/pushInvestHistory")
	public String  getPushInvestHistory(HttpServletRequest request,HttpServletResponse response,Model model){
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
		params.put("type", "aiyouqian");
		PageInfo<Invest> pageInfo =platformPushService.getInvestHistory(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("investId",investId);
		model.addAttribute("loanId", loanId);
		model.addAttribute("investTimeBegin", investTimeBegin);
		model.addAttribute("investTimeEnd", investTimeEnd);
		return "aiyouqian/aiyouqainPushInvestHistory";
	}
	
	
	
	/**
	 * 导出爱有钱用户的投资记录 
	 */
	@RequestMapping(value="/exportUserInvestInfoAiyouqian")
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
		map.put("type", "aiyouqian");
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
			String t ="投资记录(爱有钱)";
			excelConvertor.excelExport(list, title, t, style);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
