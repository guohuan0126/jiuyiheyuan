package com.duanrong.newadmin.controllhelper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.Base64Util;
import com.duanrong.newadmin.utility.CookieUtil;
import com.duanrong.newadmin.utility.DesUtil;

public class UserCookieHelper {
	
	public static void CreateUserCookie(UserCookie user,HttpServletResponse response) throws Exception
	{
		if(user == null)
		{
			throw new Exception("user is null");
		}
		
		try
		{
		user.setUserId(DesUtil.encrypt(user.getUserId(), DesUtil.DesKey));
		 JSONObject object = JSONObject.fromObject(user);
		 String temp = object.toString();
		 
		  
		
		 
		/* byte[] encoded = ThreeDES.encryptMode(ThreeDES.keyBytes, temp.getBytes());        
	        System.out.println("加密后的字符串:" + new String(encoded));
	        System.out.println("base64后："+Base64Util.encodeStr(new String(encoded)));*/
		 
		 CookieUtil.addCookie("userinfo", Base64Util.encodeStr(temp), 60*60*12, response);
		}catch(Exception e)
		{
			
		}
	}
	
	
	/**
	 * 
	 * @description 注销登录用户
	 * @author 孙铮
	 * @time 2014-12-5 下午5:23:38
	 * @param response
	 * @throws Exception
	 */
	public static void logoutUserCookie(HttpServletResponse response) throws Exception
	{
		CookieUtil.addCookie("userinfo", "",-1, response);
	}
	
	public static UserCookie GetUserCookie(HttpServletRequest request,HttpServletResponse  response)
	{
		String json = CookieUtil.getCookie("userinfo", request, response);
		if(StringUtils.isEmpty(json) )
		{
			return null;
		}
		
		String temp = Base64Util.decodeStr(json);
		/*System.out.println(temp);
		byte[] t = ThreeDES.decryptMode(ThreeDES.keyBytes,temp.getBytes());*/

		
		 UserCookie u2 = null;
		 try {
			JSONObject object = JSONObject.fromObject(temp );
			u2 = (UserCookie)JSONObject.toBean(object, UserCookie.class);
			u2.setUserId(DesUtil.decrypt(u2.getUserId(),DesUtil.DesKey));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		 return u2;
	}
}
