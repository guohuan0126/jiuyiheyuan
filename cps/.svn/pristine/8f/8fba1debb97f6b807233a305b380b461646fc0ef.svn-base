package com.duanrong.cps.controllerHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.duanrong.cps.util.MD5Encry;
import com.duanrong.cps.util.ReadProperties;



public class BjsInterceptor implements HandlerInterceptor {
	
private static Map<String,String> ipMap = new HashMap<String,String>();
	
	static{
		ipMap.put("61.147.81.240","八金社服务器IP");
		ipMap.put("61.147.81.241","八金社服务器IP");
		ipMap.put("61.147.81.242","八金社服务器IP");
		ipMap.put("112.84.186.180","八金社服务器IP");
		ipMap.put("112.84.186.181","八金社服务器IP");
		ipMap.put("112.84.186.182","八金社服务器IP");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String ip=this.getIpAddr(request);   //得到请求IP
		request.setAttribute("ip", ip);
//		if(!ipMap.containsKey(ip)){ //拦截不在范围内的IP
//			return false;
//		}
		String data = inputStreamConver(request.getInputStream());  //取得参数
		JSONObject paramJsonObject=JSONObject.fromObject(data);
		request.setAttribute("paramData", paramJsonObject);
		String appid=paramJsonObject.getString("appid");
		String key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "dr_bjs_key");
		String requestId=paramJsonObject.getString("requestId");
		String ourAppid=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "dr_bjs_appid");
		String OurSignature=MD5Encry.MD5Low(ourAppid+"-"+key+"-"+requestId);
		System.out.println("##############OurSignature:"+OurSignature);
		String bjsSignature=paramJsonObject.getString("signature");
		System.out.println("##############BjsSignature:"+bjsSignature);
		if(OurSignature.equals(bjsSignature)){
			return  true;
		}else{
			return false;
		}
		
	}

	public static void main(String[] args) throws ParseException{
		String aa=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "bjs_platform");
		String bb=MD5Encry.MD5Low("bajingshe"+"-"+"dr_bjs_key"+"-"+"c2910d38-dcf9-45fe-bba6-0fd101edcd79");
		System.out.println(bb);
		
		
		String requestId=(UUID.randomUUID().toString()).replace("-","");
		System.out.println("requestId:"+requestId);
		String cc=MD5Encry.MD5Low("bajingshe"+"-"+"dr_bjs_key"+"-"+requestId);
		System.out.println("cc:"+cc);
		
		String datestr="2016-06-01 00:00:00";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time=sdf.parse(datestr).getTime();
		
	
		
		System.out.println("date:"+time);
//		Date dd=new Date();
//		
//		System.out.println("date:"+dd.getTime());
	}
	

	private String inputStreamConver(InputStream is) {
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader bf;
		if(is != null ){
			try {
				bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				while ((line = bf.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sb.toString();
		}
		return "";
	}
	// 获取请求ip
		private String getIpAddr(HttpServletRequest request) {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				if (ip.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ip = inet.getHostAddress();
				}

			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ip != null && ip.length() > 15) {
				if (ip.indexOf(",") > 0) {
					ip = ip.substring(0, ip.indexOf(","));
				}
			}
			return ip;
		}
	
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
