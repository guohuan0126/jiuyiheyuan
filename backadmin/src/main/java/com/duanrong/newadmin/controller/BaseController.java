package com.duanrong.newadmin.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import util.MyStringUtils;

import com.duanrong.business.permission.service.NewMenuService;
import com.duanrong.business.permission.service.PermissionService;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.RoleService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-11-17 下午1:43:57
 * @Description : drpc com.duanrong.pcweb.controller BaseController.java
 * 
 */
@Controller
public class BaseController {

	@Resource
	NewMenuService menuService;

	@Resource
	RoleService roleService;

	@Resource
	PermissionService permissionService;
	
	@Resource
	UserService userService;

	private int pageSize = 25;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 处理异常，跳转到错误信息页面
	 * 
	 * @description 处理异常，跳转到错误信息页面
	 * @author 孙铮
	 * @time 2014-9-1 下午2:36:29
	 * @return
	 */
	public ModelAndView HandelError() {

		ModelAndView mv = new ModelAndView("error");
		mv.addObject("desc", "网站出现问题，请重新刷新页面或者联系网站客服。");

		return mv;
	}

	/**
	 * 
	 * @description 调用前台用户提示框
	 * @author 孙铮
	 * @time 2014-11-25 下午5:34:38
	 * @param model
	 * @param message
	 *            需要喜事的消息
	 */
	public void userMessage(Model model, HttpServletRequest request) {
		if (!StringUtils.isBlank(request.getParameter("userMessage"))) {
			model.addAttribute("userMessage", "userMessage");
			model.addAttribute("title", request.getParameter("title"));
			model.addAttribute("content", request.getParameter("content"));
		}
	}

	/**
	 * 转发
	 */
	public final static String forward = "forward:";
	/**
	 * 重定向:后边跟的应该是一个controller地址
	 */
	public final static String redirect = "redirect:";

	/**
	 * 
	 * @description 一个controller通过重定向方式调用另一个controller,并且附带参数,不拼接url
	 * @author 孙铮
	 * @time 2014-11-25 下午5:34:38
	 * @param model
	 * @param message
	 *            需要喜事的消息
	 */
	public void userMessage(RedirectAttributes redirectAttributes,
			String title, String content) {
		redirectAttributes.addFlashAttribute("userMessage", "userMessage");
		redirectAttributes.addFlashAttribute("title", title);
		redirectAttributes.addFlashAttribute("content", content);
	}

	/**
	 * 处理异常，跳转到错误信息页面
	 * 
	 * @description 处理异常，跳转到错误信息页面
	 * @author 孙铮
	 * @time 2014-9-1 下午2:36:29
	 * @return
	 */
	public ModelAndView businessError(String message) {

		ModelAndView mv = new ModelAndView("base/businessError");
		mv.addObject("message", message);

		return mv;
	}

	/**
	 * 
	 * @description 处理异常，跳转到错误信息页面
	 * @author 孙铮
	 * @time 2014-9-1 下午2:36:29
	 * @return
	 */
	public ModelAndView HandelError(String info) {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("desc", info);
		return mv;
	}

	/**
	 * 判斷用戶是否具有指定的权限
	 * 
	 * @param permissionId
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean havePermissionById(String permissionId,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> permission = (List<String>) request.getSession()
				.getAttribute("permission");
		if (permission == null) {
			UserCookie user = UserCookieHelper.GetUserCookie(request, response);
			permission = (List<String>) permissionService
					.readHasActivePermission(user.getUserId());
			request.getSession().setAttribute("permission", permission);
		}
		for (String s : permission) {
			if (s.equals(permissionId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @description 判斷用戶是否具有指定的角色
	 * @date 2015-6-24
	 * @time 下午4:29:50
	 * @author SunZ
	 * @param roleId
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean haveRoleById(String roleId, HttpServletRequest request,
			HttpServletResponse response) {
		UserCookie user = UserCookieHelper.GetUserCookie(request, response);
		List<Role> roles = roleService.readRolesByUserId(user.getUserId());
		for (Role role : roles) {
			if (roleId.equals(role.getId())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 判断或者得到登录用户，如果当前用户已经登录，返回用户信息，如果没有登录返回null 用户信息暂时保存在Cookie中
	 */
	public UserCookie GetLoginUser(HttpServletRequest request,
			HttpServletResponse response) {

		UserCookie user = UserCookieHelper.GetUserCookie(request, response);
		if (user != null) {
			return user;
		} else {
			return null;
		}
	}

	/**
	 * 重载getloginUser方法，IsFromSoA,，实时获取用户的信息，避免本地cookie数据和数据库中不一致，
	 * 主要是更新登陆用户是否具备投资者权限。 如果有其他数据要和数据实时一致，继续更新这段代码
	 * 
	 * @param request
	 * @param response
	 * @param isfromSoa
	 * @return
	 */
	public UserCookie GetLoginUser(HttpServletRequest request,
			HttpServletResponse response, boolean isfromSoa) {

		UserCookie user = GetLoginUser(request, response);
		if (user == null) {
			return null;
		}
		if (isfromSoa == true) {
			try {
				List<Role> roles = roleService.readRolesByUserId(user
						.getUserId());
				for (Role role : roles) {// 判断是否是用户和投资人
					if ("INVESTOR".equals(role.getId())) {
						user.setInvest(true);
						continue;
					}
					if ("MEMBER".equals(role.getId())) {
						user.setMember(true);
						;
						continue;
					}
				}
				String tempUserId = user.getUserId();
				// 同步更新用户cookie
				UserCookieHelper.CreateUserCookie(user, response);
				user.setUserId(tempUserId);
				return user;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return user;
		}
	}
	
	
	/**
	 * 根据条件获取用户id
	 * @param param
	 * @return
	 */
	protected String getUserId(String param){
		if(param != null && param != ""){
			User user = null;
			if(param.contains("@")){
				user = userService.readUserByMail(param);
			}else if(MyStringUtils.isNumeric(param) && param.length() == 11){
				user = userService.readUserByMobileNumber(param);
			}else if(regexIDcard(param)){
				user = userService.readUserByIdCard(param);
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
