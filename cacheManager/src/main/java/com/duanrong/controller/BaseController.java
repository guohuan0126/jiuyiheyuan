package com.duanrong.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.duanrong.authentication.service.AuthService;
import com.duanrong.business.permission.model.UserCookie;
import com.duanrong.util.PropReader;

@Controller
public class BaseController {
	
	@Resource
	protected AuthService authService;
	
	protected Log log = LogFactory.getLog(BaseController.class);
	
	protected PropReader pd = new PropReader("/config.properties");
	
	protected static final int pageNo = 1;
	protected static final int pageSize = 25; 
	protected static final String ENCODING = "UTF-8";
	
	protected UserCookie getCookie(HttpServletRequest request, HttpServletResponse response){
		UserCookie cookie = new UserCookie();
		//cookie.setUserId("13261356043ppfh");
		return authService.getUser(request, response);
		//return cookie;
	}
}
