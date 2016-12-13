package com.duanrong.cps.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.authentication.utils.ReadProperties;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiAccInvest;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiLoan;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiNotice;
import com.duanrong.cps.business.fengchelicai.service.FengchelicaiService;
import com.duanrong.cps.business.invest.service.InvestService;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.loan.service.LoanService;
import com.duanrong.cps.business.platform.service.PlatformService;
import com.duanrong.cps.util.WrbCoopDESUtil;
import com.duanrong.utility.LogUtil;

@Controller
@RequestMapping(value="fengchelicaiInter")
public class FengchelicaiController extends BaseController{
	
	@Resource
	LoanService loanService;
	
	@Resource
	InvestService investService;

	
	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private FengchelicaiService fengchelicaiService;
	
	/**
	 * 风车理财投资记录查询
	 */

	@RequestMapping(value="/getInvestList")
	@ResponseBody
	public JSONObject getInvestList(HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		Map<String,Object> paramsMap=new HashMap<String,Object>();
		String message="";
		String status="";
		try {
			paramsMap=this.getParamMap(request);
			String loanId=paramsMap.get("id").toString();
			if(!StringUtils.isBlank(loanId)){
				Loan loan = loanService.get(loanId);
				if(null != loan){
					resultJson.put("invest_all_money", loan.getTotalmoney());
					resultJson.put("borrow_id", loan.getBorrowMoneyUserID());
					resultJson.put("last_invest_time", investService.getInvestDateLastOne(loanId));
					resultJson.put("first_invest_time", investService.getInvestDateFirstOne(loanId));
					resultJson.put("all_investors", investService.getTotalCount(loanId));
					resultJson.put("invest_list", investService.getInvestRecords(loanId));
					resultJson.put("retcode", 0);
					resultJson.put("retmsg", 0);
					}else{
						resultJson.put("retcode", 1);
						resultJson.put("retmsg", "无此标的");
					}
			}else{
				resultJson.put("retcode", 1);
				resultJson.put("retmsg", "参数错误");
			}
		} catch (Exception e) {
			resultJson.put("retcode", 1);
			resultJson.put("retmsg", e.getMessage());
			status="0";
			message=e.getMessage();
			e.printStackTrace();
		}finally{
			Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
			logMap.put("request", paramsMap.toString());
			// logMap.put("response", gson.toJsonTree(ret));
			logMap.put("type", "投资记录查询");
			logMap.put("createTime", new Date());
			logMap.put("message", message);
			logMap.put("status", status);
			logMap.put("ip", request.getAttribute("ip"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String nowDate = sdf.format(new Date());
			nowDate = nowDate.replace("-", "");
			logMap.put("tableName", "fengchelicai_requestlog_" + nowDate);
			
			SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LogUtil.infoLog("风车理财_投资记录查询", 
					        "request:"+paramsMap.toString()+";"+
			                "createTime:"+sdfAll.format(new Date())+";"+
					        "message:"+message+";"+
			                "status:"+status+";");
			//platformService.logInsertRequestLog(logMap);
		}
		
		return resultJson;
	}
   
    
    /**
	 * 风车理财汇总数据查询接口
	 */

	@RequestMapping(value="/getAggregate")
	@ResponseBody
	public JSONObject getaAggregate(HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		Map<String,Object> paramsMap=new HashMap<String,Object>();
		String message="";
		String status="";
		try {
			paramsMap=this.getParamMap(request);
			String date=paramsMap.get("date").toString();
			Map<String,Object> selectMap = fengchelicaiService.getaAggregate(date);
			resultJson.put("retcode", 0);
			resultJson.put("retmsg", 0);
			resultJson.put("lend_count", selectMap.get("lend_count"));
			resultJson.put("borrow_count", selectMap.get("borrow_count"));
			resultJson.put("invest_all_money", selectMap.get("invest_all_money"));
			resultJson.put("all_wait_back_money", selectMap.get("all_wait_back_money"));
		} catch (Exception e) {
			resultJson.put("retcode", 1);
			resultJson.put("retmsg", e.getMessage());
			status="0";
			message=e.getMessage();
			e.printStackTrace();
		}finally{
			Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
			logMap.put("request", paramsMap.toString());
			// logMap.put("response", gson.toJsonTree(ret));
			logMap.put("type", "汇总数据查询");
			logMap.put("createTime", new Date());
			logMap.put("message", message);
			logMap.put("status", status);
			logMap.put("ip", request.getAttribute("ip"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String nowDate = sdf.format(new Date());
			nowDate = nowDate.replace("-", "");
			logMap.put("tableName", "fengchelicai_requestlog_" + nowDate);
			
			SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LogUtil.infoLog("风车理财_汇总数据查询", 
					        "request:"+paramsMap.toString()+";"+
			                "createTime:"+sdfAll.format(new Date())+";"+
					        "message:"+message+";"+
			                "status:"+status+";");
			
			//platformService.logInsertRequestLog(logMap);
		}
		
		return resultJson;
	}
	
	
    /**
	 * 风车理财投资记录查询接口
	 */

	@RequestMapping(value="/getAccInvest")
	@ResponseBody
	public JSONObject getAccInvest(HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		Map<String,Object> paramsMap=new HashMap<String,Object>();
		String message="";
		String status="";
		try {
		paramsMap=this.getParamMap(request);
		String userId=paramsMap.get("pf_user_id")+"";
		String startTime=paramsMap.get("start_time")+"";
		String endTime=paramsMap.get("end_time")+"";
		String investStatus=paramsMap.get("invest_status")+"";
		String offset=paramsMap.get("offset")+"";
		String limit=paramsMap.get("limit")+"";
		String investRecordId=paramsMap.get("invest_record_id")+"";
		
		List<FengchelicaiAccInvest> resultList=new ArrayList<FengchelicaiAccInvest>();
		if(userId!=null && !"".equals(userId)){
			resultList = fengchelicaiService.getAccInvest(userId,startTime,endTime,investStatus,offset,limit,investRecordId);
		}
			resultJson.put("retcode", 0);
			resultJson.put("retmsg", 0);
			resultJson.put("invest_records", resultList);

		} catch (Exception e) {
			resultJson.put("retcode", 1);
			resultJson.put("retmsg", e.getMessage());
			status="0";
			message=e.getMessage();
			e.printStackTrace();
		}finally{
			Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
			logMap.put("request", paramsMap.toString());
			// logMap.put("response", gson.toJsonTree(ret));
			logMap.put("type", "投资记录查询");
			logMap.put("createTime", new Date());
			logMap.put("message", message);
			logMap.put("status", status);
			logMap.put("ip", request.getAttribute("ip"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String nowDate = sdf.format(new Date());
			nowDate = nowDate.replace("-", "");
			logMap.put("tableName", "fengchelicai_requestlog_" + nowDate);
			
			SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LogUtil.infoLog("风车理财_投资记录查询", 
					        "request:"+paramsMap.toString()+";"+
			                "createTime:"+sdfAll.format(new Date())+";"+
					        "message:"+message+";"+
			                "status:"+status+";");
			//platformService.logInsertRequestLog(logMap);
		}
		
		return resultJson;
	}
	
	
	/**
	 * 风车理财平台公告查询接口
	 */

	@RequestMapping(value="/getNoticeInfo")
	@ResponseBody
	public JSONObject getNoticeInfo(HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		Map<String,Object> paramsMap=new HashMap<String,Object>();
		String message="";
		String status="";
		try {
			paramsMap=this.getParamMap(request);
			String page=paramsMap.get("page")+"";
			String limit=paramsMap.get("limit")+"";
			List<FengchelicaiNotice> resultList = fengchelicaiService.getNoticeInfo(page,limit);
			resultJson.put("retcode", 0);
			resultJson.put("retmsg", 0);
			resultJson.put("all_notices", resultList);

		} catch (Exception e) {
			resultJson.put("retcode", 1);
			resultJson.put("retmsg", e.getMessage());
			status="0";
			message=e.getMessage();
			e.printStackTrace();
		}finally{
			Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
			logMap.put("request", paramsMap.toString());
			// logMap.put("response", gson.toJsonTree(ret));
			logMap.put("type", "查询个人信息");
			logMap.put("createTime", new Date());
			logMap.put("message", message);
			logMap.put("status", status);
			logMap.put("ip", request.getAttribute("ip"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String nowDate = sdf.format(new Date());
			nowDate = nowDate.replace("-", "");
			logMap.put("tableName", "fengchelicai_requestlog_" + nowDate);
			
			
			SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LogUtil.infoLog("风车理财_公告查询接口", 
					        "request:"+paramsMap.toString()+";"+
			                "createTime:"+sdfAll.format(new Date())+";"+
					        "message:"+message+";"+
			                "status:"+status+";");
			//platformService.logInsertRequestLog(logMap);
		}
		
		return resultJson;
	}
	
	
	/**
	 * 风车理财个人信息查询接口
	 */

	@RequestMapping(value="/getAccInfo")
	@ResponseBody
	public JSONObject getAccInfo(HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		Map<String,Object> paramsMap=new HashMap<String,Object>();
		String message="";
		String status="";
		try {
			paramsMap=this.getParamMap(request);
			String userId=paramsMap.get("pf_user_id").toString();
			Map<String,Object> resultMap = fengchelicaiService.getAccInfo(userId);
			resultJson.put("retcode", 0);
			resultJson.put("retmsg", 0);
			resultJson.put("all_balance", resultMap.get("all_balance"));
			resultJson.put("available_balance", resultMap.get("available_balance"));
			resultJson.put("frozen_money", resultMap.get("frozen_money"));
			resultJson.put("reward", resultMap.get("reward"));
			resultJson.put("investing_principal", resultMap.get("investing_principal"));
			resultJson.put("investing_interest", resultMap.get("investing_interest"));
			resultJson.put("earned_interest", resultMap.get("earned_interest"));
			resultJson.put("current_money", resultMap.get("current_money"));

		} catch (Exception e) {
			resultJson.put("retcode", 1);
			resultJson.put("retmsg", e.getMessage());
			status="0";
			message=e.getMessage();
			e.printStackTrace();
		}finally{
			Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
			logMap.put("request", paramsMap.toString());
			// logMap.put("response", gson.toJsonTree(ret));
			logMap.put("type", "查询个人信息");
			logMap.put("createTime", new Date());
			logMap.put("message", message);
			logMap.put("status", status);
			logMap.put("ip", request.getAttribute("ip"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String nowDate = sdf.format(new Date());
			nowDate = nowDate.replace("-", "");
			logMap.put("tableName", "fengchelicai_requestlog_" + nowDate);
			
			SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LogUtil.infoLog("风车理财_查询个人信息", 
					        "request:"+paramsMap.toString()+";"+
			                "createTime:"+sdfAll.format(new Date())+";"+
					        "message:"+message+";"+
			                "status:"+status+";");
			//platformService.logInsertRequestLog(logMap);
		}
		
		return resultJson;
	}
	
	

	/**
	 * 风车理财查询标的
	 */

	@RequestMapping(value="getLoan")
	@ResponseBody
    public JSONObject getLoan(HttpServletRequest request, HttpServletResponse response){
		JSONObject result= new JSONObject();
		String message="";
		String status="";
		Map<String,Object>paramsMap=new HashMap<String,Object>();
		try {
			paramsMap=this.getParamMap(request);
			System.out.println("#######风车理财查询标的，参数："+paramsMap.toString());
			String loanId=paramsMap.get("invest_id")+"";
			Map<String,Object>params=new HashMap<String,Object>();
			params.put("loanIds",loanId);
			List<FengchelicaiLoan>list=fengchelicaiService.getLoanInfo(params);
			JSONArray dataArray=JSONArray.fromObject(list);
			result.put("retcode", "0");
			result.put("retmsg", " ");
			result.put("invest_list", dataArray);
			status="1";
		} catch (Exception e) {
			result.put("retcode", "1");
			result.put("retmsg", e.getMessage());
			status="0";
			message=e.getMessage();
			e.printStackTrace();
		}finally{
			Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
			logMap.put("request", paramsMap.toString());
			// logMap.put("response", gson.toJsonTree(ret));
			logMap.put("type", "查询标的信息");
			logMap.put("createTime", new Date());
			logMap.put("message", message);
			logMap.put("status", status);
			logMap.put("ip", request.getAttribute("ip"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String nowDate = sdf.format(new Date());
			nowDate = nowDate.replace("-", "");
			logMap.put("tableName", "fengchelicai_requestlog_" + nowDate);
			
			SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LogUtil.infoLog("风车理财_查询标的信息", 
					        "request:"+paramsMap.toString()+";"+
			                "createTime:"+sdfAll.format(new Date())+";"+
					        "message:"+message+";"+
			                "status:"+status+";");
			
			// platformService.logInsertRequestLog(logMap);
		}
    	return result;
    }
	
	/**
	 * 加密
	 */
	public String encodeParams(String params){
		String result="";
		try {
			String key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "fengchelicai_key");
			result=WrbCoopDESUtil.desEncrypt(key, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取request里解密后的参数
	 */
	public Map<String,Object>getParamMap(HttpServletRequest request){
		Map<String,Object>paramMap=new HashMap<String,Object>();
		try {
			String param=request.getParameter("param");
			if(StringUtils.isNotBlank(param)){
				String key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "fengchelicai_key");
				String paramData=WrbCoopDESUtil.desDecrypt(key, param);
				String[] paramArray=paramData.split("&");
				
				for(int i=0; i<paramArray.length; i++){
					paramMap.put(paramArray[i].substring(0, paramArray[i].indexOf("=")).trim(),
							     URLDecoder.decode(paramArray[i].substring(paramArray[i].indexOf("=")+1).trim(),"utf-8"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramMap;
	}
	
	
	public static void main(String[] args) throws IOException, Exception{
		String key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "fengchelicai_key");
		String param="pf_user_id=fqyA3ifMV3m2aizb&offset=0&limit=10";
		
		String paramData=WrbCoopDESUtil.desEncrypt(key, param);
		System.out.println(paramData);
		//String aa= URLDecoder.decode("QTUaoEHQXGJgb8cTgWqas8bd1fJ8EUDMJ5wN1dYL5aMMWSO%2bihtzMFCz54C9AgtHtFAiaHz6PA98JqQNdn7fkvWjIdmlm77jF0Om4M96b61juijh2BylfARvXCqS79BiOADotb2EdXGnzNWYU5jdVGzBW2Npx7UeYFYlABEHJAO%2fBzshgDFqUgHs%2bP1PP69rQbj062qzDuY%3d");
		//System.out.println(aa);
		String aa="n8ht4uF0qwLzZLS14Icukyqtst4YAGtpORSmRMvlzvMWPlP/fXizSZZL0z0Dl7F0OEF0kbRmsGs=";
		System.out.println(WrbCoopDESUtil.desDecrypt(key, paramData));
		
	
		
	
	}
}
