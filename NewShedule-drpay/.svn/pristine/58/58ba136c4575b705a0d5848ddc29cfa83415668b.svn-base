package com.duanrong.newadmin.controllhelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.duanrong.newadmin.model.UserCookie;

public class LoginValiateInterceptor implements HandlerInterceptor {


	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		String url = request.getRequestURI();
		
		if(url.contains("trusteeship_return_s2s")){
			
			return true;
		}
		
		if("/".equals(url)|| "/memberLogin".endsWith(url)){
			return true;
		}else{
			UserCookie user = UserCookieHelper.GetUserCookie(request, response);
			if(user !=null)
			{
				return true;
			}		
			else
			{
				response.sendRedirect("/");
			}
			return false;
		}
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		
	}

}
