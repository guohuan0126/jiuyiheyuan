package com.duanrong.cps.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.duanrong.cps.business.permission.service.PermissionService;
import com.duanrong.cps.util.CookieUtil;
import com.duanrong.cps.util.MyStringUtils;

public class PermissionIntercepter implements HandlerInterceptor {
	@Resource
	PermissionService permissionService;
    
	/**
	 * 加载前
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 获取url
		String url = request.getRequestURI();
		return ipPermission(url,request,response);
	}

	/**
	 * 判断超级密码和权限
	 * @param url
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean ipPermission(String url, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/**
		 * 判断session中是否有这个参数 有的话就放行 没有就去查询并 将信息放到session中在放行
		 * 
		 */
		String method="";
		String value="";
	System.out.println("url:"+url);	
	if(isPageOK(url)) {
			//	super 入口
			String param = request.getQueryString();
			// 非授权IP需要super参数
			if (MyStringUtils.isNotAnyBlank(param) && param.contains("=")) {
				String[] params = param.split("=");
				method = params[0]; 
				value = params[1];
				String cookieValue=CookieUtil.getCookie("super", request, response);
				if (cookieValue == null) {
					System.out.println(permissionService.getSpwd(method));
					if (permissionService.getSpwd(method).endsWith(value)) {
						CookieUtil.addCookie("super", value, 28800, response);
					}else{
						return false;
					}
				}
			}  
			return true;
		}else{					
				String pwa=CookieUtil.getCookie("super", request, response);
			if (pwa!=null) {
				//如果session中的super的值不为空 说明超级密码是正确的 不需要验证超级密码了 验证IP白名单和请求url白名单
				return true;
				//session中超级密码不存在 说明超级密码不正确 判断ip白名单
			}else{
				//来访者IP
				String UIp=request.getRemoteAddr();
				if(isIPOK(UIp)){					
					return true;
				}
			} 
		}
	response.getWriter().print("6");
	return false;
	}	
	
	
	
	
	public boolean isIPOK(String uip){
		// url上没有参数 判断ip白名单
		ArrayList<String> ips = new ArrayList<String>();
		String id = "ip_check";
		ips = permissionService.getIps(id);
		// 来访者ip
		System.out.println(uip);
		for (String ip : ips) {
			// 判断这个ip是否在ip白名单内
			if (uip.equals(ip)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPageOK(String url){
		//使用 map防止url白名单
		Map<String,String> urlMap = new HashMap<String,String>();
		urlMap.put("/", "登陆页面");
		return urlMap.containsKey(url);
	}

                                               
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
	


}
