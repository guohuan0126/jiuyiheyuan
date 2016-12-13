package com.duanrong.newadmin.interceptor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import util.Log;
import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.permission.model.NewMenu;
import com.duanrong.business.permission.service.NewMenuService;
import com.duanrong.business.permission.service.PermissionService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;

/***
 * 权限拦截器
 * 
 * @author Administrator
 * 
 */
public class PermissionIntercepter implements HandlerInterceptor {

	@Resource
	PermissionService permissionService;

	@Resource
	NewMenuService newMenuService;

	@Resource
	Log log;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		// TODO ip校验
		String url = request.getRequestURI();		
		if (url.contains("trusteeship_return_s2s")||url.contains("S2SCallback")||url.contains("UdeskInter")) {
			return true;
		}

		if (UserCookieHelper.GetUserCookie(request, response) != null) {
			return permiss(request, response, url);
		}
		return ipCheck(request, response, url);

	}

	// IP拦截
	private boolean ipCheck(HttpServletRequest request,
			HttpServletResponse response, String url)
			throws IOException {
		String method = "";
		String value = "";
		
		//  "/" 不做拦截 		
		if ("/".equals(url)) {
			
			//	super 入口
			String param = request.getQueryString();
			// 非授权IP需要super参数
			if (MyStringUtils.isNotAnyBlank(param) && param.contains("=")) {
				param = URLDecoder.decode(param, "UTF-8");
				String[] params = param.split("=");
				method = params[0];
				value = params[1];
				if (request.getSession().getAttribute("super") == null) {
					System.out.println(permissionService.readSuper(method));
					if (permissionService.readSuper(method).endsWith(value)) {
						request.getSession().setAttribute("super", value);
					}
				}
			}
			return true;
		} else {
			
			//TODO IP拦截
			
			//super 入口不需要验证IP
			if (request.getSession().getAttribute("super") != null) {
				
				//登录权限在login中处理，这里不做权限拦截
				if ("/memberLogin".endsWith(url)) {
					return true;
				}				
				//权限拦截
				return permiss(request, response, url);
			}
						
			// 内网测试ip，正式环境需删除
			
			if(getIpAddr(request).contains("192.168.1")){ return
			permiss(request, response, url); }	
						
			// 授权IP可以直接访问
			List<String> ips = new ArrayList<>();
			if (request.getSession().getAttribute("ips") == null) {
				ips = permissionService.readIpList();
				request.getSession().setAttribute("ips", ips);
			} else {
				ips = (List<String>) request.getSession().getAttribute("ips");
			}
			for (String ip : ips) {
				if (getIpAddr(request).equals(ip)) {
					if ("/memberLogin".endsWith(url)) {
						return true;
					}
					return permiss(request, response, url);
				}
			}
			
			log.errLog("ip拦截", "URL:" + url + "," + "Ip:"
						+ getIpAddr(request) + "不再白名单《被拦截");
			response.getWriter().print("<body onload=\"init();\"><script language=\"JavaScript\" type=\"text/JavaScript\">"
								+"function init(){alert(\"IP访问受限!\"); window.location.href=\"/\";}</script></body>");
			
			
			return false;
		}

	}

	// 权限校验
	private boolean permiss(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException {
		// TODO 权限校验。
		UserCookie user = UserCookieHelper.GetUserCookie(request, response);
		if (user != null) {
			List<NewMenu> newMenus = new ArrayList<>();
			if (request.getSession().getAttribute("menus") == null) {
				PageInfo<NewMenu> pageInfo = newMenuService.readAllNewMenu(1, 0,
						new NewMenu());
				newMenus = pageInfo.getResults();
				request.getSession().setAttribute("menus", newMenus);
			} else {
				newMenus = (List<NewMenu>) request.getSession().getAttribute(
						"menus");
			}

			for (NewMenu newMenu : newMenus) {
				if (url.equals(newMenu.getUrl())) {
					List<NewMenu> menus = new ArrayList<>();
					if (request.getSession().getAttribute("menu") == null) {
						menus = newMenuService.readNewMenuByUserId(user
								.getUserId());
						request.getSession().setAttribute("menu", menus);
					} else {
						menus = (List<NewMenu>) request.getSession()
								.getAttribute("menu");
					}
					for (NewMenu menu : menus) {
						if (menu.getUrl() != null && menu.getUrl().equals(url)) {
							return true;
						}
					}
					log.errLog("Url拦截", "URL:" + url + "," + "Ip:"
							+ getIpAddr(request) + "," + user.getUserId()
							+ ":用户没有权限");
					response.getWriter()
							.print("<body onload=\"init();\"><script language=\"JavaScript\" type=\"text/JavaScript\">"
									+ "function init(){alert(\"您没有权限进行此操作,请与管理员联系！\"); window.location.href=\"/\";}</script></body>");
				}

			}

			return true;
		} else {
			response.sendRedirect("/");
		}
		return false;
	}

	// 获取请求ip
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1") || ip.contains("192.168.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
