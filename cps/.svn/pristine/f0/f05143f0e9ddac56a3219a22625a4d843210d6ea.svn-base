package com.duanrong.cps.controllerHelper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.duanrong.cps.business.user.model.User;
import com.duanrong.cps.model.UserCookie;
import com.duanrong.cps.util.AESUtil;
import com.duanrong.cps.util.CookieUtil;
import com.duanrong.cps.util.LogUtil;




/**
 * UserCookie操作工具类
 * @author Qfh
 *
 */
public class UserCookieHelper {

	public static String cookieKey = "userinfo";  //登录存贮Key
	
    /**
     * 在Cookie中创建用户信息
     * @param cookie
     * @param response
     * @throws Exception
     */
	public static void CreateUserCookie(UserCookie cookie,
			HttpServletResponse response) throws Exception {
	   
		if(cookie != null){
			try{
				JSONObject object = JSONObject.fromObject(cookie);
				String temp = object.toString();
				temp = AESUtil.encode(temp);  //加密
				CookieUtil.addCookie(cookieKey,temp,60 * 30, response);
			}catch(Exception e){
				LogUtil.errLog("createUserCookieEx", e);
			}
		}else{
			throw new Exception("user is null");
		}
		
	 }
	/**
	 * 获取Cookie中的用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	public static User GetUserCookie(HttpServletRequest request,
			HttpServletResponse response) {
		
		String temp = CookieUtil.getCookie(cookieKey, request, response);
		if(temp != null && temp.length() > 0) {
			User user = null;
			try {
				temp = AESUtil.decode(temp); //解密
				JSONObject object = JSONObject.fromObject(temp);
				user = (User)JSONObject.toBean(object, User.class);
				return user;
			}catch(Exception e){
				e.printStackTrace();
				LogUtil.errLog("GetUserCookie", e);
			}
		}
		return null;
	}
	

	/**
	 * 
	 * @description 注销登录用户
	 * @author 
	 * @time 2014-12-5 下午5:23:38
	 * @param response
	 * @throws Exception
	 */
	public static void logoutUserCookie(HttpServletResponse response){
		CookieUtil.addCookie(cookieKey,"",1, response);  //1秒钟清空
	}
}