package com.duanrong.drpay.jsonservice.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.duanrong.drpay.business.config.model.Config;
import com.duanrong.drpay.business.config.service.ConfigService;
import com.duanrong.drpay.config.BusinessEnum;

import util.Log;
import base.error.ErrorCode;

import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.jsonservice.handler.View;
import com.duanrong.drpay.trusteeship.helper.model.UserDevice;
import com.duanrong.util.jedis.DRJedisCacheUtil;

/**
 * @author xiao
 * @datetime 2016年11月30日 下午5:29:42
 */
public class BaseController {

	private static final String API_AUTH_IP = "api_auth_ip";

	private static final Log log = new Log();

	@Resource
	ConfigService configService;
	
	/**
	 * 根据source来源转换为存管通需要的终端类型UserDevice
	 * 
	 * @param source
	 * @return
	 */
	public UserDevice sourceToUserDevice(String source) {
		if (!StringUtils.isBlank(source) && source.toUpperCase().contains("PC")) {
			return UserDevice.PC;
		} else {
			return UserDevice.MOBILE;
		}
	}

	public TerminalEnum viewType(String source) {
		if (!StringUtils.isBlank(source)
				&& (source.toUpperCase().contains("IOS")
						|| source.toUpperCase().contains("ANDROID"))) {
			return TerminalEnum.app;
		} else if(source.toUpperCase().contains("MOBILE")){
			return TerminalEnum.wap;
		}else {
			//
			return TerminalEnum.pc;
		}
	}

	/**
	 * 获取返回视图
	 * 
	 * @return
	 * @version 1.0.0
	 */
	protected View getView() {
		View view = new View();
		view.setError(ErrorCode.SUCCESS);
		view.setVersion("1.0.0");
		view.setType(TerminalEnum.app);
		view.setResponseTime(new Date());
		return view;
	}

	/**
	 * 获取返回视图
	 * 
	 * @return
	 * @version 1.0.0
	 */
	protected View getView(String source) {
		View view = new View();
		view.setError(ErrorCode.SUCCESS);
		view.setVersion("1.0.0");
		view.setType(viewType(source));
		view.setResponseTime(new Date());
		return view;
	}

	/**
	 * 获取返回视图
	 * 
	 * @return
	 * @version 1.0.0
	 */
	protected View getAppView() {
		View view = getView();
		view.setType(TerminalEnum.app);
		return view;
	}

	/**
	 * 获取返回视图
	 * 
	 * @return
	 * @version 1.0.0
	 */
	protected View getPcView() {
		View view = getView();
		view.setType(TerminalEnum.pc);
		return view;
	}

	/**
	 * IP鉴权接口
	 * 
	 * @param request
	 * @return
	 */
	protected boolean authenticationIP(HttpServletRequest request,
			BusinessEnum businessType) {
		String ip = getIp(request);
		if (!StringUtils.isBlank(ip)) {
			String ips = DRJedisCacheUtil
					.hget(API_AUTH_IP, businessType.name()).get(
							businessType.name());
			if(ips == null ){
				Config config = configService.get(businessType.name());
				if(config != null) {
					ips = config.getValue();
					Map<String, Object> map = new HashMap<>();
					map.put(businessType.name(), ips);
					DRJedisCacheUtil.hset(API_AUTH_IP, map);
				}
			}
			if (ips != null && ips.contains(ip))
				return true;
		} else
			return true;
		Enumeration<String> enu = request.getParameterNames();
		StringBuffer buf = new StringBuffer();
		if (null != enu) {
			while (enu.hasMoreElements()) {
				String paraName = enu.nextElement();
				buf.append(paraName + "=" + request.getParameter(paraName)
						+ ";");
			}
		}
		log.errLog("接口ip鉴权错误", "无权调用此接口, 业务：" + businessType.name()
				+ ", 请求者ip: " + ip + ", 请求接口： " + request.getRequestURL()
				+ ", 请求参数: " + buf);
		return false;
	}

	// 获取请求ip
	private String getIp(HttpServletRequest request) {
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
			if (ip.indexOf(",") > 0)
				ip = ip.substring(0, ip.indexOf(","));
		}
		return ip;
	}
}