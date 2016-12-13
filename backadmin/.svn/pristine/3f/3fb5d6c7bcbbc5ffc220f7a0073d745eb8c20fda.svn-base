package com.duanrong.newadmin.interceptor;

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

import com.duanrong.newadmin.utility.MD5Encry;
import com.duanrong.newadmin.utility.ReadProperties;



public class UdeskInterceptor implements HandlerInterceptor {
	
private static Map<String,String> ipMap = new HashMap<String,String>();
	
	static{
		ipMap.put("218.11.0.240","Udesk服务器IP");
		ipMap.put("116.211.167.160","Udesk服务器IP");
		ipMap.put("120.55.238.118","Udesk服务器IP");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String ip=this.getIpAddr(request);   //得到请求IP
		request.setAttribute("ip", ip);
		StringBuffer requestURL=request.getRequestURL(); 
		String requestURI=request.getRequestURI(); 
//		if(!ipMap.containsKey(ip)){ //拦截不在范围内的IP
//			return false;
//		}
		//http://b.duanrong.com/?mobileNum=15903672982&timestamp=20160721164024&sign=8c5f4c9acc1ea71ca5909029e52e2956
		//String param = request.getQueryString();
		//String paramUrl = request.getRequestURI();		
		//paramUrl = paramUrl.substring(paramUrl.indexOf("?")+1);
		String mobileNum=request.getParameter("mobileNum");
		String timestamp=request.getParameter("timestamp");		
		//String paramData=param.substring(0,param.lastIndexOf("&"));
		String paramData="mobileNum="+mobileNum+"&timestamp="+timestamp;
		String UdeskSign=request.getParameter("sign");
		System.out.println("##############BjsSignature:"+UdeskSign);
		String key=ReadProperties.getPropetiesValue("constant/UdeskPlatform.properties", "udesk_key");		
		String OurSignature=MD5Encry.Encry(paramData+"&"+key);
		System.out.println("##############OurSignature:"+OurSignature);
		request.setAttribute("request_str","?"+paramData+"&sign="+UdeskSign);
		request.setAttribute("url", requestURI+"?"+paramData+"&sign="+UdeskSign);
		if(OurSignature.equals(UdeskSign)){
			return  true;
		}else{
			return false;
		}
		
	}

	public static void main(String[] args) throws ParseException{
		String aa=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "bjs_platform");
		String bb=MD5Encry.Encry("bajingshe"+"-"+"dr_bjs_key"+"-"+"c2910d38-dcf9-45fe-bba6-0fd101edcd79");
		System.out.println(bb);
		
		
		String requestId=(UUID.randomUUID().toString()).replace("-","");
		System.out.println("requestId:"+requestId);
		String cc=MD5Encry.Encry("bajingshe"+"-"+"dr_bjs_key"+"-"+requestId);
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
