package com.duanrong.cps.controller;

import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MyStringUtils;

import com.duanrong.cps.business.netloaneye.model.UserCookie;
import com.duanrong.cps.business.user.model.User;
import com.duanrong.cps.business.user.service.UserService;
import com.duanrong.cps.controllerHelper.UserCookieHelper;


public class BaseController {
	
	@Resource
	UserService userService;
	
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
	
	/*
	 * 判断或者得到登录用户，如果当前用户已经登录，返回用户信息，如果没有登录返回null 用户信息暂时保存在Cookie中
	 */
	/*public UserCookie GetLoginUserP2PEye(HttpServletRequest request,
			HttpServletResponse response) {

		UserCookie user = UserCookieHelper.GetUserCookie(request, response);
		if (user != null) {
			return user;
		} else {
			return null;
		}
	}*/
	
	/**
	 * 根据条件获取用户id
	 * @param param
	 * @return
	 */
	protected String getUserId(String param){
		if(param != null && param != ""){
			User user = null;
			if(param.contains("@")){
				user = userService.getUserByMail(param);
			}else if(MyStringUtils.isNumeric(param) && param.length() == 11){
				user = userService.getUserByMobileNumber(param);
			}else if(regexIDcard(param)){
				user = userService.getUserByIdCard(param);
			}
			if(user != null){
				param = user.getUserId();
			}
			return param;
		}
		return "";
	}
	
	/**
	 * 校验身份证
	 * @param idCard
	 * @return
	 */
	protected boolean regexIDcard(String idCard){
		String REGEX_ID_CARD = "(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$)|(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)";
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}
}
