package com.duanrong.dataAnalysis.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanrong.dataAnalysis.business.permission.service.PermissionService;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.business.user.service.UserService;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;
import com.duanrong.dataAnalysis.model.UserCookie;

@Controller
public class LoginController extends BaseController {
	
	@Resource
	private UserService userService;
	@Resource
	private PermissionService permissionService;
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(HttpServletRequest req,HttpServletResponse response,Model model){
		 
		String username=req.getParameter("username");			
		String password=req.getParameter("password");
		
		String result = userService.login(username, password);
		if(result.equals("1"))
		{	
			User user = userService.getUser( username, password);
			UserCookie cookie =  new UserCookie();
			if(user !=null)
			{	cookie.setUsername(user.getUsername());
				cookie.setRealName(user.getRealname());
				cookie.setRoleId("data_analysis");
				cookie.setUserId(user.getUserId());
				try {
					UserCookieHelper.CreateUserCookie(cookie, response);
					return "1";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "-5";
				
				}
			}
			else
			{
				return "-1";
			}			
		}
		else
		{
			return result;
		}
	}

}
