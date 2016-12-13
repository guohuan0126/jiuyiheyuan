package com.duanrong.dataAnalysis.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.duanrong.dataAnalysis.util.CookieUtil;


/**
 * 用户登录的拦截器
 * @author 
 *
 */
public class loginInterceptor implements HandlerInterceptor{
	
	
	//进入controller之前
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
			// 判断用户是否登陆
		//在handler执行之前处理，先执行此方法
		//返回值如果是true放行，false：拦截。
		//判断用户是否登录
		String cookieValue=CookieUtil.getCookie("userinfo", request, response);
		if (cookieValue!=null) {
			return true;
		}
		//用户没有登录
		//判断用户访问的是不是登录或者登录提交的url
		String requestURI = request.getRequestURI();
		if ( requestURI.equals("/login")||requestURI.equals("/")) {
			//放行
			return true;
		}else{
		//跳转到登录页面
			response.sendRedirect("/");
			return false;
		}
		
	}
	//方法后
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	//页面渲染后
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	
	
}
