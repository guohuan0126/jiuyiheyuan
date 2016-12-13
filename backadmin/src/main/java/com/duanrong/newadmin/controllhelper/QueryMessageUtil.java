package com.duanrong.newadmin.controllhelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duanrong.business.user.service.InformationService;
import com.duanrong.newadmin.controller.BaseController;
import com.duanrong.newadmin.model.UserCookie;
/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved
 * Company : 久亿财富（北京）投资有限公司
 * @Author : 孙铮
 * @CreateTime : 2014-11-17 下午3:00:27 
 * @Description : drpc com.duanrong.pcweb.controllhelper QueryMessageUtil.java
 *
 */
public class QueryMessageUtil extends BaseController {
	
	@Resource
	InformationService informationService;
	
	public Object[] query(HttpServletRequest request,HttpServletResponse response) {	
			UserCookie getLoginUser = GetLoginUser(request, response);
			if(getLoginUser == null){
				return null;
			}else{
				String userId = getLoginUser.getUserId();
				Long noReadCount = informationService.readNotReadByUserId(userId);
				Object[] objs= new Object[2];
				objs[0] = userId;
				objs[1] = noReadCount;
				//计算安全级别
				int calculateSecurityLevel = calculateSecurityLevel(getLoginUser);
				request.setAttribute("degree", calculateSecurityLevel);
				return objs;
			}		
	}
	
	/**
	 * 计算安全级别
	 * 
	 * @param userId
	 * @return
	 */
	public int calculateSecurityLevel(UserCookie getLoginUser) {
		int level = 0;
		if(getLoginUser == null)
		{
			return 0;
		}
		if (getLoginUser.getEmail() != null) {
			level = level + 48;
		}
		if (getLoginUser.isInvest()) {
			level = level + 44;
		}
		return level;
	}
}
