package com.duanrong.cps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.authentication.service.AuthService;
import com.duanrong.authentication.service.impl.AuthServiceImpl;
import com.duanrong.authentication.utils.ReadProperties;
import com.duanrong.business.permission.model.UserCookie;
import com.duanrong.cps.business.xicaiwang.model.XiCaiInvestInfo;
import com.duanrong.cps.business.aiyouqian.model.AiyouqianPushLoan;
import com.duanrong.cps.business.aiyouqian.service.AiyouqianService;
import com.duanrong.cps.business.invest.service.InvestService;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.loan.service.LoanService;
import com.duanrong.cps.business.platform.service.PlatformService;
import com.duanrong.cps.business.xicaiwang.model.XiCaiLoanInfo;
import com.duanrong.cps.business.xicaiwang.model.XiCaiUserInfo;
import com.duanrong.cps.business.xicaiwang.service.XicaiwangService;
import com.duanrong.cps.util.HttpUtil;
import com.duanrong.cps.util.MD5Encry;
import com.duanrong.utility.LogUtil;


@Controller
@RequestMapping(value="xicaiwangInter")
public class XicaiwangController extends BaseController{
	
	@Resource
	XicaiwangService xicaiwangService;
	
    @Autowired
    private PlatformService platformService;
    
    @Resource
    AiyouqianService aiyouqianService;
    
    @Resource
    LoanService loanService;
    
    @Resource
    InvestService investService;
    

	/**
	 * 希财获取产品数据接口
	 */

	@RequestMapping(value="/getLoanInfo")
	@ResponseBody
	public JSONObject getLoanInfo(HttpServletRequest request, HttpServletResponse response){
		String key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "xicaiwang_client_secret");
		JSONObject resultJson = new JSONObject();
		Map<String,Object> paramsMap=new HashMap<String,Object>();
		String message="";
		String status="";
		String loanId = request.getParameter("pid")+"";
		String client_secret = request.getParameter("client_secret")+"";
		if(!StringUtils.isBlank(client_secret)&& client_secret.equals(key)){
			try {
				XiCaiLoanInfo resultLoan = xicaiwangService.getLoanInfo(loanId);
				resultJson.put("code", 0);
				resultJson.put("data", resultLoan);
			} catch (Exception e) {
				resultJson.put("code", 1);
				resultJson.put("msg", e.getMessage());
				status="0";
				message=e.getMessage();
				e.printStackTrace();
			}finally{
				Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
				logMap.put("request", paramsMap.toString());
				// logMap.put("response", gson.toJsonTree(ret));
				logMap.put("type", "获取产品数据接口");
				logMap.put("createTime", new Date());
				logMap.put("message", message);
				logMap.put("status", status);
				logMap.put("ip", request.getAttribute("ip"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String nowDate = sdf.format(new Date());
				nowDate = nowDate.replace("-", "");
				logMap.put("tableName", "xicaiwang_requestlog_" + nowDate);
				
				SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				LogUtil.infoLog("希财网_获取产品数据接口", 
						        "request:"+paramsMap.toString()+";"+
				                "createTime:"+sdfAll.format(new Date())+";"+
						        "message:"+message+";"+
				                "status:"+status+";");
				
			//	platformService.logInsertRequestLog(logMap);
			}
		}else{
			resultJson.put("code", 1);
			resultJson.put("msg", "client_secret不合法");
		}
		
		
		return resultJson;
	}
	
	
	/**
	 * 希财获取用户统计接口
	 */

	@RequestMapping(value="/getUserInfo")
	@ResponseBody
	public JSONObject getUserInfo(HttpServletRequest request, HttpServletResponse response){
		String key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "xicaiwang_client_secret");
		JSONObject resultJson = new JSONObject();
		Map<String,Object> paramsMap=new HashMap<String,Object>();
		String message="";
		String status="";
		String temp=request.getParameter("t")+"";
		String token=request.getParameter("token")+"";
		String startdate=request.getParameter("startdate")+"";
		String enddate=request.getParameter("enddate")+"";
		String page=request.getParameter("page")+"";
		String pagesize=request.getParameter("pagesize")+"";
		if(StringUtils.isBlank(pagesize) || pagesize.equals("null")){
			pagesize = "10";
		}
		String token1 = MD5Encry.Encry(MD5Encry.Encry(temp)+key);
		if(!StringUtils.isBlank(token)&& token.equals(token1)){
			try {
				List<XiCaiUserInfo> resultList = xicaiwangService.getUserInfo(startdate,enddate,page,pagesize);
				resultJson.put("total", xicaiwangService.getTotalUser(startdate,enddate,page,pagesize));
				resultJson.put("code", 0);
				resultJson.put("list", resultList);
			} catch (Exception e) {
				resultJson.put("code", 1);
				resultJson.put("msg", e.getMessage());
				status="0";
				message=e.getMessage();
				e.printStackTrace();
			}finally{
				Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
				logMap.put("request", paramsMap.toString());
				// logMap.put("response", gson.toJsonTree(ret));
				logMap.put("type", "获取用户统计接口");
				logMap.put("createTime", new Date());
				logMap.put("message", message);
				logMap.put("status", status);
				logMap.put("ip", request.getAttribute("ip"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String nowDate = sdf.format(new Date());
				nowDate = nowDate.replace("-", "");
				logMap.put("tableName", "xicaiwang_requestlog_" + nowDate);
				
				SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				LogUtil.infoLog("希财网_获取用户统计接口", 
						        "request:"+paramsMap.toString()+";"+
				                "createTime:"+sdfAll.format(new Date())+";"+
						        "message:"+message+";"+
				                "status:"+status+";");
				
			//	platformService.logInsertRequestLog(logMap);
			}
		}else{
			resultJson.put("code", 1);
			resultJson.put("msg", "token不合法");
		}
		
		
		return resultJson;
	}
	
	

	/**
	 * 希财获取用户统计接口
	 */

	@RequestMapping(value="/getInvestInfo")
	@ResponseBody
	public JSONObject getInvestInfo(HttpServletRequest request, HttpServletResponse response){
		String key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "xicaiwang_client_secret");
		JSONObject resultJson = new JSONObject();
		Map<String,Object> paramsMap=new HashMap<String,Object>();
		String message="";
		String status="";
		String temp=request.getParameter("t")+"";
		String token=request.getParameter("token")+"";
		String startdate=request.getParameter("startdate")+"";
		String enddate=request.getParameter("enddate")+"";
		String page=request.getParameter("page")+"";
		String pagesize=request.getParameter("pagesize")+"";
		String token1 = MD5Encry.Encry(MD5Encry.Encry(temp)+key);
		if(StringUtils.isBlank(pagesize) || pagesize.equals("null")){
			pagesize = "10";
		}
		if(!StringUtils.isBlank(token)&& token.equals(token1)){
			try {
				List<XiCaiInvestInfo> resultList = xicaiwangService.getInvestInfo(startdate,enddate,page,pagesize);
				System.out.println("#############查询total");
				resultJson.put("total", xicaiwangService.getTotalInvest(startdate,enddate));
				resultJson.put("code", 0);
				resultJson.put("list", resultList);
			} catch (Exception e) {
				resultJson.put("code", 1);
				resultJson.put("msg", e.getMessage());
				status="0";
				message=e.getMessage();
				e.printStackTrace();
			}finally{
				Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
				logMap.put("request", paramsMap.toString());
				// logMap.put("response", gson.toJsonTree(ret));
				logMap.put("type", "获取用户投资统计接口");
				logMap.put("createTime", new Date());
				logMap.put("message", message);
				logMap.put("status", status);
				logMap.put("ip", request.getAttribute("ip"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String nowDate = sdf.format(new Date());
				nowDate = nowDate.replace("-", "");
				logMap.put("tableName", "xicaiwang_requestlog_" + nowDate);
				
				SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				LogUtil.infoLog("希财网_获取用户投资统计接口", 
						        "request:"+paramsMap.toString()+";"+
				                "createTime:"+sdfAll.format(new Date())+";"+
						        "message:"+message+";"+
				                "status:"+status+";");
				
				//platformService.logInsertRequestLog(logMap);
			}
		}else{
			resultJson.put("code", 1);
			resultJson.put("msg", "token不合法");
		}
		return resultJson;
	}
	
	
	/**
	 * 向爱希财推送项目
	 * @param m
	 * @return
	 */
	@RequestMapping("/aixicaiPushLoan")
	@ResponseBody
	public Map<String,Object> bsyPushLoan(String id,Model m,
			HttpServletRequest request,HttpServletResponse response){
            String url="";
            String aixicai_client_id="";
            String aixicai_client_secret="";

		url=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","xicaiwang_url");
		aixicai_client_id=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","xicaiwang_client_id");
		aixicai_client_secret=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","xicaiwang_client_secret");
		
		AuthService authService=new AuthServiceImpl();
		UserCookie user = authService.getUser(request, response);
//		 Map<String,Object> returnMap = aiyouqianService.InsertPushLoanToAiyouqian(id, user.getUserId());
//		 List<AiyouqianPushLoan> resultList= (List<AiyouqianPushLoan>) returnMap.get("result");
//		 String dataJson=null;
//		 try {
//			dataJson=JSONObject.toJSONString(resultList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		 String str="id"+id+"&"
				+"client_id="+aixicai_client_id+"&"
		 		+ "client_secret="+aixicai_client_secret;
		 System.out.println("爱希财sign明文："+str);
		 Loan loan = loanService.get(id);
		 loan.setSumMoney(investService.getTotalMoney4AlreadyFundraising(id));
		 loan.setAwardLink("111");

		 JSONObject jsonObject=new JSONObject();
		 jsonObject.put("pid", id);
		 jsonObject.put("client_id", aixicai_client_id);
		 jsonObject.put("client_secret", aixicai_client_secret);
		System.out.println("爱希财参数："+jsonObject);
		System.out.println("##############爱希财URL："+url+"/push_p2p");
		JSONObject resultObject = HttpUtil.platformExecutePost(jsonObject, url+"/push_p2p");
		System.out.println("#############爱希财返回："+resultObject);
		
		String code=resultObject.getJSONObject("content").getString("code");
		String message=resultObject.getJSONObject("content").getString("msg");
		String result="";
		if("0".equals(code)){
			result="推送成功";
			xicaiwangService.insertToPushLoan(loan);
		}else{
			result="推送失败!"+message;
		}
		System.out.println(resultObject);
		Map<String, Object>resultMap=new HashMap<String,Object>();
		resultMap.put("result", result);
		return resultMap;
	}
}
