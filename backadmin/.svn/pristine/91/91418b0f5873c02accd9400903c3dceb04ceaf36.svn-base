package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import util.Log;

import com.duanrong.business.permission.model.Permission;
import com.duanrong.business.permission.service.PermissionService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserLoginLog;
import com.duanrong.business.user.service.UserLoginLogService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;

@Controller
public class LoginController extends BaseController {

	@Resource
	PermissionService permissionService;

	@Resource
	UserService userService;

	@Resource
	UserLoginLogService userLoginLogService;

	@Resource
	Log log;

	/**
	 * 
	 * @description 处理登录
	 * @author wangjing
	 * @time 2014-11-18 下午3:35:59
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public void loginAction(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		response.setCharacterEncoding("UTF-8");
		try {
			// 得到用户输入的用户名密码
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = new User();
			if (StringUtils.isNumeric(username)
					&& StringUtils.length(username) == 11) {
				user.setMobileNumber(username);
			} else if (StringUtils.contains(username, "@")) {
				user.setEmail(username);
			} else {
				user.setUserId(username);
			}
			user.setPassword(password);
			User loginUser = userService.readLoginUser(user);
			System.out.println(loginUser.toString());
			if (loginUser != null) {// 登录成功
				String requestURL = request.getParameter("url");
				// model.addAttribute("url",requestURL);
				boolean isAdmin = false;
				// boolean isMember = false;
				// 得到当前登录用户的权限

				//List<Permission> perms = permissionService
				//		.getPermisstionByUserId(loginUser.getUserId());
				List<User> userBean=userService.readAdmin(loginUser);
				System.out.println("*************************"+loginUser.getUserId()+"******************");
//				for (Permission p : perms) {// 判断是否有管理员的基本权限
//					if ("ROLE_ADMIN".equals(p.getId())) {
//						isAdmin = true;
//						break;
//					}
//				}
			
				if(userBean!=null && userBean.size()>0){
					isAdmin=true;
				}
				if (isAdmin == true) {
					// 创建cookie
					UserCookie userCookie = new UserCookie();
					userCookie.setUserId(loginUser.getUserId());
					userCookie.setEmail(loginUser.getEmail());
					userCookie.setUserType(loginUser.getUserType());
					userCookie.setMobileNumber(loginUser.getMobileNumber());

					// 登录成功后给用户浏览器写回一个cookie
					UserCookieHelper.CreateUserCookie(userCookie, response);
					// 保存用户登录记录
					UserLoginLog userLoginLog = new UserLoginLog();
					userLoginLog.setUserId(loginUser.getUserId());
					userLoginLog.setLoginIp(request.getRemoteAddr());
					userLoginLog.setIsSuccess("1");
					userLoginLogService.insert(userLoginLog);
					if (requestURL != null && !"".equals(requestURL)) {
						response.getWriter().write(requestURL);
					} else {
						response.getWriter().write("/index");
					}

					response.getWriter().flush();
				} else {
					response.getWriter().write("notAdmin");
					response.getWriter().flush();
				}
			} else {// 登录失败
				response.getWriter().write("fail");
				response.getWriter().flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.pcweb.controller.LoginController.login().Exception",
					e);
			try {
				response.getWriter().write("fail");
				response.getWriter().flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @description 注销登录
	 * @author 孙铮
	 * @time 2014-11-18 下午3:36:10
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser != null) {
				UserCookieHelper.logoutUserCookie(response);
				request.getSession().removeAttribute("menus");
				request.getSession().removeAttribute("menu");
				request.getSession().removeAttribute("ips");
				request.getSession().removeAttribute("permission");
				return BaseController.redirect + "/";
			}
			return BaseController.redirect + "/";
		} catch (IOException e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.pcweb.controller.LoginController.logout().IOException",
					e);
			return BaseController.redirect + "/";

		} catch (Exception e) {
			log.errLog(
					"com.duanrong.pcweb.controller.LoginController.logout().Exception",
					e);
			e.printStackTrace();
			return BaseController.redirect + "/";
		}
	}

	/**
	 * 
	 * @description 修改密码
	 * @author 孙铮
	 * @time 2014-11-18 下午3:36:10
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toRepass")
	public String toRepass(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser != null) {
			request.setAttribute("loginUser", getLoginUser);
			return "user/rePass";
		}
		return "user/login";
	}

	/**
	 * 
	 * @description 修改密码
	 * @author 孙铮
	 * @time 2014-11-18 下午3:36:10
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/rePass")
	public void rePass(HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		String id = request.getParameter("id");
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		String status = userService.updatePassword(id, oldPass, newPass);
		response.getWriter().print(status);
	}
}
