package com.duanrong.newadmin.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.Log;

import com.duanrong.business.permission.model.NewMenu;
import com.duanrong.business.permission.service.NewMenuService;
import com.duanrong.business.permission.service.PermissionService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;

/**
 * 首页C层
 * 
 * @author  wangjing
 * @date 2014-11-19下午2:49:41
 */
@Controller
public class IndexController extends BaseController {
	
	@Resource
	NewMenuService newMenuService;
	
	@Resource
	PermissionService permissionService;
	
	@Resource
	Log log;
	/**
	 * 
	 * @description 跳转登陆页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/")
	public String login(HttpServletRequest request,
			HttpServletResponse response) {		
		List<String> ips = permissionService.readIpList();
		request.setAttribute("ips", ips);
		return "user/login";
	}
	/**
	 * 
	 * @description 跳转主页
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView test(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {			
			model.addAttribute("url", "/index");
			ModelAndView m=new ModelAndView("index");
			return m;
		} catch (Exception e) {
			log.errLog("newadmin index", e);
			e.printStackTrace();
		}
		return null;
	}
}
