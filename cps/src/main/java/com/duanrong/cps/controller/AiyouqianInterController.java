package com.duanrong.cps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.cps.business.aiyouqian.service.AiyouqianService;
import com.duanrong.cps.business.platform.service.PlatformService;
import com.duanrong.cps.util.MD5Encry;
import com.duanrong.cps.util.ReadProperties;
import com.duanrong.utility.LogUtil;

@Controller
@RequestMapping(value="aiyouqianInter")
public class AiyouqianInterController extends BaseController{

	
	@Autowired
	private AiyouqianService aiyouqianService;
	
	@Autowired
	private PlatformService platformService;
	
	/**
	 * 爱有钱刷新用户数据
	 */
	@RequestMapping(value="/getAccInfo")
	@ResponseBody
	public JSONObject getAccInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject resultObj=new JSONObject();                                                             
		String message="";
		String status="";
		try {
			
			String sign=request.getParameter("sign");  //验签
			System.out.println("########爱有钱刷新用户数据传过来的sign:"+sign);
			String queryParams=request.getQueryString();
			String[] paramsArray=queryParams.split("&");
			String mingwen="";
	       for(int i=0; i<paramsArray.length; i++){
	       	if(!paramsArray[i].contains("sign")){
	       		mingwen=mingwen+paramsArray[i]+"&";
	       	}
	       }
			mingwen=mingwen.substring(0, mingwen.length()-1);
			String aiyouqian_key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_key");
			System.out.println("###########sign明文："+mingwen+aiyouqian_key);
			String ourSign=MD5Encry.MD5Low(mingwen+aiyouqian_key);
			System.out.println("######sign密文："+ourSign);
			if(!ourSign.equals(sign)){
				resultObj.put("result", 0); //绑定失败
	   		    resultObj.put("remark", "验签失败");
	   		    System.out.println("###########验签失败");
	   		    return resultObj;
			}
			String userId=request.getParameter("bind_uid");
			JSONObject dataObject=aiyouqianService.getAccInfo(userId);
			resultObj.put("result", 1); //调用成功
			resultObj.put("remark", "");
			resultObj.put("data", dataObject);
			status="1";
			return resultObj;
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.put("result", 0); //绑定失败
   		    resultObj.put("remark", e.getMessage());
   		    message=e.getMessage();
   		    status="0";
   		    System.out.println("###########爱有钱刷新用户数据失败");
   		    return resultObj;
		}finally{
			Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
			logMap.put("request", request.getQueryString());
			logMap.put("type", "爱有钱刷新用户数据");
			logMap.put("createTime", new Date());
			logMap.put("message", message);
			logMap.put("status", status);
			logMap.put("ip", request.getAttribute("ip"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String nowDate = sdf.format(new Date());
			nowDate = nowDate.replace("-", "");
			logMap.put("tableName", "aiyouqian_requestlog_" + nowDate);
			SimpleDateFormat sdfAll=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LogUtil.infoLog("爱有钱刷新用户数据",                  //写入日志数据库
					"request:"+request.getQueryString()+";"+
			        "createTime:"+sdfAll.format(new Date())+";"+
					"message:"+message+";"+
			        "status:"+status+";");
			//platformService.logInsertRequestLog(logMap);
		}
		
	}
	
	/**
	 * 
	 * 查询标的投资情况接口
	 */
	@RequestMapping(value="/getLoanInvest")
	@ResponseBody
	public JSONObject getLoanInvest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject resultObj=new JSONObject(); 
		String message="";
		String status="1";
		try {
			String sign=request.getParameter("sign");  //验签
			System.out.println("########爱有钱查询标的投资情况接口的sign:"+sign);
			String queryParams=request.getQueryString();
			String[] paramsArray=queryParams.split("&");
			String mingwen="";
	       for(int i=0; i<paramsArray.length; i++){
	       	if(!paramsArray[i].contains("sign")){
	       		mingwen=mingwen+paramsArray[i]+"&";
	       	}
	       }
			mingwen=mingwen.substring(0, mingwen.length()-1);
			String aiyouqian_key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_key");
			System.out.println("###########sign明文："+mingwen+aiyouqian_key);
			String ourSign=MD5Encry.MD5Low(mingwen+aiyouqian_key);
			System.out.println("######sign密文："+ourSign);
			if(!ourSign.equals(sign)){
				resultObj.put("result", 0); //绑定失败
	   		    resultObj.put("remark", "验签失败");
	   		    System.out.println("###########验签失败");
	   		    return resultObj;
			}
			String loanId=request.getParameter("productid");
			JSONObject dataObject=aiyouqianService.getLoanInvest(loanId);
			if(dataObject.size()>0){
				resultObj.put("result", 1);
				resultObj.put("remark", "");
				resultObj.put("data",dataObject);
			}else{
				resultObj.put("result", 0);
				resultObj.put("remark", "传入参数product_id有误");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			message=e.getMessage();
			status="0";
		}finally{
			Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
			logMap.put("request", request.getQueryString());
			logMap.put("type", "查询标的投资情况接口");
			logMap.put("createTime", new Date());
			logMap.put("message", message);
			logMap.put("status", status);
			logMap.put("ip", request.getAttribute("ip"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String nowDate = sdf.format(new Date());
			nowDate = nowDate.replace("-", "");
			logMap.put("tableName", "aiyouqian_requestlog_" + nowDate);
			SimpleDateFormat sdfAll=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LogUtil.infoLog("爱有钱查询标的投资情况接口", 
					"request:"+request+";"+
			        "createTime:"+sdfAll.format(new Date())+";"+
					"message:"+message+";"+
			        "status:"+status+";");
			//platformService.logInsertRequestLog(logMap);
		}
		return resultObj;
		
		
		
	}
	 
	 public static void main(String[] args){
		 
	//	 String aa=MD5Encry.MD5Low("service=register_bind&uid=1202&cardno=342501200101015521&mobile=13212345678&realname=zhangsan");
		// System.out.println(aa);
		 //d969fb9469e6b9d353cb8cfc5c451a70
		 
		 String aa=MD5Encry.MD5Low("service=get_userinfo&productid=161018170023825001858616ed8048140538f8e7cc1d264b9d");
		 System.out.println(aa);
		 //sign=3f9ce34796eaf606bafe9c41fa39e836
		 //b665a1d5a84ab3fa896111d276be85ef
	 }
}
