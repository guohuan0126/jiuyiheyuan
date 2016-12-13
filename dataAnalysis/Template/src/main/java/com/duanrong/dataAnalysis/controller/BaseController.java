package com.duanrong.dataAnalysis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;


public class BaseController {
	
	/**
	 * get current login user;
	 * @param request
	 * @param response
	 * @return
	 */
	public User GetLoginUser(HttpServletRequest request,HttpServletResponse response)
	{
		return UserCookieHelper.GetUserCookie(request, response);
	}
}
