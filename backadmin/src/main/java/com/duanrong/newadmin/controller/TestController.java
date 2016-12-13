package com.duanrong.newadmin.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class TestController extends BaseController {
	
	@RequestMapping(value="test/ip")
	public String ip(HttpServletRequest request, HttpServletResponse response ) throws UnknownHostException{
		String type = request.getParameter("type");
		switch (type){
			case "1":
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
				 request.setAttribute("ip", ip);
			case "2":
				 String ip2 = request.getRemoteAddr();
				 request.setAttribute("ip2", ip2);
			case "3":
				
			
		}

		return "test/ip";
	}
	
	
	
	
}
