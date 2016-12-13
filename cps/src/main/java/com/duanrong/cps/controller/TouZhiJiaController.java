package com.duanrong.cps.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duanrong.cps.business.platform.service.PlatformService;
import com.duanrong.cps.business.touzhija.model.Message;
import com.duanrong.cps.business.touzhija.model.PlatformException;
import com.duanrong.cps.business.touzhija.model.QueryReq;
import com.duanrong.cps.business.touzhija.model.QueryReq.Index;
import com.duanrong.cps.business.touzhija.model.Redirect;
import com.duanrong.cps.business.touzhija.model.ServiceData;
import com.duanrong.cps.business.touzhija.model.UserInfo;
import com.duanrong.cps.business.touzhija.service.TouZhiJiaException;
import com.duanrong.cps.business.touzhija.service.TouZhiJiaService;
import com.duanrong.cps.util.DesUtil1;
import com.duanrong.cps.util.HttpUtil;
import com.duanrong.cps.util.MD5Encry;
import com.duanrong.cps.util.ReadProperties;
import com.duanrong.cps.util.ThiredPlatformHttpUtil;
import com.duanrong.cps.util.touzhijia.DateTypeAdapter;
import com.duanrong.cps.util.touzhijia.MessageCrypt;
import com.duanrong.utility.Base64Util;
import com.duanrong.utility.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * Created by architect of touzhijia on 2016/3/1.
 */
@Controller
@RequestMapping("/touzhijiaInter")
public class TouZhiJiaController {

	@Resource
	private TouZhiJiaService touZhiJiaService;
	@Resource
	private PlatformService platformService;

	private GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls()
			.setDateFormat("yyyy-MM-dd HH:mm:ss");

	// @Value("${expireTime}")
	private int expireTime = 5;
	
	@RequestMapping(method = RequestMethod.GET, value="/testError")
	public void testErrorHandler () throws PlatformException {
		throw new PlatformException(1001,"test error");
	}

	@RequestMapping(method = POST, produces="application/json")
	@ResponseBody
	public Message command(@RequestBody Message msg,
			HttpServletResponse httpResp, HttpServletRequest request)
			throws Throwable {
		// if ((System.currentTimeMillis() / 1000) - msg.getTimestamp() >
		// expireTime) {
		// throw new PlatformException(100, "时间戳过期");
		// }

		System.out.println("#########################投之家查询接口");
		String message = "";
		String status = "1";
		String requestParam = "";

		MessageCrypt messageCrypt = new MessageCrypt();
		ServiceData req = messageCrypt.decryptMsg(msg); // 解密数据
		ServiceData resp = null; // 返回数据
		try {
			Class<? extends TouZhiJiaService> claz = touZhiJiaService
					.getClass();
			Method[] methods = claz.getMethods();
			Method method = null;
			for (Method m : methods) {
				if (m.getName().equals(req.getService())) {
					method = m;
					break;
				}
			}
			if (method == null) {
				throw new TouZhiJiaException(101, "错误的业务类型:" + req.getService());
			}
			Class<?>[] paramClasses = method.getParameterTypes(); // 获取方法的参数
			if (paramClasses.length != 1) {
				throw new TouZhiJiaException(101, req.getService() + "方法参数错误");
			}
			Gson gson = gsonBuilder.registerTypeAdapter(Date.class,
					new DateTypeAdapter()).create();
			// Gson gson = gsonBuilder.create();
			JsonElement reqBody = req.getBody();

			String methodName = req.getService();
			if ("createUser".equals(methodName)) { // 创建用户调用手机web或pc网站
				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(msg);
				JSONObject reqBodyObject = JSONObject.parseObject(reqBody
						.toString());
				JSONArray tagsArray = reqBodyObject.getJSONArray("tags");
				String url = "";
				if (tagsArray != null && tagsArray.size() > 0) {
					String tags = (String) tagsArray.get(0);
					if ("wap".equals(tags.toLowerCase())) { // 跳转手机
						url = ReadProperties.getPropetiesValue(
								"constant/thiredPlatform.properties",
								"mweb_url");
					} else { // 跳转pc
						url = ReadProperties.getPropetiesValue(
								"constant/thiredPlatform.properties", "pc_url");
					}
				} else { // tags值为空则跳转到pc
					url = ReadProperties.getPropetiesValue(
							"constant/thiredPlatform.properties", "pc_url");
				}
				JSONObject result = ThiredPlatformHttpUtil.executePost(
						jsonObject, url + "touzhijia/createUser");
				System.out.println("#########cps中createUser返回值：" + result);
				ServiceData createUserReq = messageCrypt.decryptMsg(JSONObject
						.toJavaObject(result, Message.class));
				JSONObject retJSON = JSONObject.parseObject(createUserReq
						.getBody().toString());
				String username = retJSON.getString("usernamep");
				if ("".equals(username) || null == username) {
					PlatformException platformException = new PlatformException(
							1001, "手机号已占用");
					httpResp.setStatus(500);
					System.out.println("#######手机号已占用方法:"
							+ platformException.getCode() + ","
							+ platformException.getMessage());
					throw platformException;

					// ErrorHandler errorHandler=new ErrorHandler();
					// errorHandler.platformError(platformException);
					// throw platformException;
				}
				return JSONObject.toJavaObject(result, Message.class);
			}

			requestParam = reqBody.toString(); // 用于插入日志
			System.out.println("###############" + reqBody.toString());
			Object param = gson.fromJson(reqBody, paramClasses[0]); // 把json字符串转化成相应的实体
			if (param instanceof QueryReq) {
				QueryReq q = (QueryReq) param;
				q.validate();
			}
			Object ret = null;
			QueryReq queryReq = (QueryReq) param;
			if (null == queryReq.getIndex().getVals()) {
				String[] aa = {};
				Index index = queryReq.getIndex();
				index.setVals(aa);
				queryReq.setIndex(index);
			}
			try {
				ret = method.invoke(touZhiJiaService, queryReq);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				message = e.getMessage();
				status = "0";
				throw e.getTargetException();
			}
			if (ret instanceof Redirect) {
				Redirect r = (Redirect) ret;
				httpResp.sendRedirect(r.getUri());
			}
			resp = new ServiceData(req.getService(), gson.toJsonTree(ret));

		} catch (Exception e) {
			if (e instanceof PlatformException) {
				PlatformException platformException = new PlatformException(
						1001, "手机号已占用");
				httpResp.setStatus(500);
				throw platformException;
			}
			e.printStackTrace();
			message = e.getMessage();
			status = "0";
		} finally {
			// 插入日志
			Map<String, Object> logMap = new HashMap<String, Object>(); // 向数据库插入日志信息
			logMap.put("request", requestParam);
			// logMap.put("response", gson.toJsonTree(ret));
			logMap.put("type", req.getService());
			logMap.put("createTime", new Date());
			logMap.put("message", message);
			logMap.put("status", status);
			logMap.put("ip", request.getAttribute("ip"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String nowDate = sdf.format(new Date());
			nowDate = nowDate.replace("-", "");
			logMap.put("tableName", "touzhijia_requestlog_" + nowDate);
			System.out.println("############"+"投之家日志入库前");
			
			SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LogUtil.infoLog("投之家_"+req.getService(), 
					        "request:"+requestParam+";"+
			                "createTime:"+sdfAll.format(new Date())+";"+
					        "message:"+message+";"+
			                "status:"+status+";");
			
			//platformService.logInsertRequestLog(logMap);
			System.out.println("############"+"投之家日志入库后");
		}

		return messageCrypt.encryptMsg(resp);
	}

	/**
	 * 关联老用户和登录是表单请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@ResponseBody
	@RequestMapping(value = "/formRequest", method = RequestMethod.POST)
	public Message command(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		String data = request.getParameter("data");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		System.out.println(data);
		Message msg = new Message();
		msg.setData(data);
		msg.setNonce(nonce);
		msg.setSignature(signature);
		msg.setTimestamp(Long.parseLong(timestamp));
		MessageCrypt messageCrypt = new MessageCrypt();
		ServiceData req = messageCrypt.decryptMsg(msg); // 解密数据

		Class<? extends TouZhiJiaService> claz = touZhiJiaService.getClass();
		Method[] methods = claz.getMethods();
		Method method = null;
		for (Method m : methods) {
			if (m.getName().equals(req.getService())) {
				method = m;
				break;
			}
		}
		if (method == null) {
			throw new PlatformException(101, "错误的业务类型:" + req.getService());
		}
		Class<?>[] paramClasses = method.getParameterTypes(); // 获取方法的参数
		if (paramClasses.length != 1) {
			throw new PlatformException(101, req.getService() + "方法参数错误");
		}

		JsonElement reqBody = req.getBody();

		// 跳转到pc网站或手机web

		String methodName = req.getService();
		String url = "";
		JSONObject reqBodyObject = JSONObject.parseObject(reqBody.toString());
		if ("login".equals(methodName)) { // 自动登录方法没有tags值，通过type判断pc还是wap
			Integer type = reqBodyObject.getInteger("type");
			if (type == 1) {
				url = ReadProperties.getPropetiesValue(
						"constant/thiredPlatform.properties", "mweb_url");
			} else {
				url = ReadProperties.getPropetiesValue(
						"constant/thiredPlatform.properties", "pc_url");
			}
		} else {
			JSONArray tagsArray = reqBodyObject.getJSONArray("tags");
			if (tagsArray != null && tagsArray.size() > 0) {
				String tags = (String) tagsArray.get(0);
				if ("wap".equals(tags.toLowerCase())) { // 跳转手机
					url = ReadProperties.getPropetiesValue(
							"constant/thiredPlatform.properties", "mweb_url");
				} else { // 跳转pc
					url = ReadProperties.getPropetiesValue(
							"constant/thiredPlatform.properties", "pc_url");
				}
			} else { // tags值为空则跳转到pc
				url = ReadProperties.getPropetiesValue(
						"constant/thiredPlatform.properties", "pc_url");
			}
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(msg);
		JSONObject result = HttpUtil.executePost(jsonObject, url
				+ "touzhijia/formRequest");
		System.out.println("###################cps登录返回值 ："+result);
		Integer code = result.getInteger("code");
		if (code != null && code == 302) {
			Map<String,Object>pp=this.getParams(result.getString("redirectUrl"));
			String token=pp.get("token")+"";
			String temstap=pp.get("temstap")+"";
			System.out.println("########投之家temstap:"+temstap);
			response.sendRedirect(result.getString("redirectUrl")+"&token="+token+"&temstap="+temstap);
		}
		JSONObject.toJavaObject(result, Message.class);
		return JSONObject.toJavaObject(result, Message.class);
	}
	
	
	
	
	
	public static Map<String,Object> getParams(String url){
		url=url.substring(url.indexOf("?")+1);
	    String[] args=url.split("&");
	    Map<String, Object>map=new HashMap<String,Object>();
	    for(int i=0; i<args.length; i++){
	    	String[] paramArray=args[i].split("=");
	    	map.put(paramArray[0], paramArray[1]);
	    }
	  String PlatformUserId= map.get("PlatformUserId")+"";
	  String userId=map.get("ourUserId")+"";
	  Long temstap=System.currentTimeMillis();
	  String token=MD5Encry.MD5(userId+PlatformUserId);
	  
	  String temstap1=Base64Util.encodeStr(temstap.toString());
	  map.put("temstap", temstap1);
	  map.put("token",token);
	  return map;
	}
	
	public static void main(String[] args){
		
		//TouZhiJiaController.getParams("https://www.duanrong.com/autoLogin?PlatformUserId=maying0124&ourUserId=EBN7ZnJbUVjqktpb&url=/loanDetail/161122170500111060&type=TouZhiJia");
		
		
		 Long temstap=System.currentTimeMillis();
		String temstap1=Base64Util.encodeStr(temstap.toString());
		System.out.println(temstap1);
		
//		String temstap1;
//		try {
//			Long temstap=System.currentTimeMillis();
//			System.out.println(temstap);
//			temstap1 = DesUtil1.encrypt(temstap.toString(), "duanrong");
//			System.out.println(temstap1);
//			String temstap2 = DesUtil1.decrypt(temstap1, "duanrong");
//			System.out.println(temstap2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
//		GsonBuilder gsonBuilderTest = new GsonBuilder().serializeNulls()
//				.setDateFormat("yyyy-MM-dd HH:mm:ss");
//		Gson gson = gsonBuilderTest.registerTypeAdapter(Date.class,
//				new DateTypeAdapter()).create();
//		
////		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//		
//		UserInfo  user = new UserInfo();
//		user.setRegisterAt(new Date());
//		System.out.print(gson.toJson(user));
		
	}

}
