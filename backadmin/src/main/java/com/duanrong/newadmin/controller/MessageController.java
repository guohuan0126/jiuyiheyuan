package com.duanrong.newadmin.controller;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import util.Log;
import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.user.model.AuthInfo;
import com.duanrong.business.user.model.Information;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.AuthInfoService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.FormUtil;

/**
 * 消息管理
 * 
 * @author  wangjing
 * @date 2014-11-19下午2:49:41
 */
@Controller
public class MessageController extends BaseController {
	
	@Resource
	AuthInfoService authInfoService;
	@Resource
	SmsService smsService;
	@Resource
	UserService userService;
	
	@Resource
	InformationService informationService;
	@Resource
	OperaRecordService operaRecordService;
	@Resource
	Log log;

	/**
	 * 
	 * @description 验证码列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authInfo/authInfoList")
	public String authInfoList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if(MyStringUtils.isAnyBlank(pageNo)){
					pageNo = "1";
				}
				String str = "";
				String mnumber = request.getParameter("mnumber");
				String type = request.getParameter("type");
				String source =request.getParameter("source");
				if (StringUtils.isNotBlank(mnumber)) {
					str += "&mnumber=" + mnumber;
				}
				if (StringUtils.isNotBlank(type)) {
					str += "&type=" +  type;
				}
				if (StringUtils.isNotBlank(source)) {
					str += "&source=" +  source;
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("mnumber", mnumber);
				params.put("type", type);
				params.put("source", source);
				PageInfo pageInfo=authInfoService.readPageInfo(Integer.parseInt(pageNo), 30, params);
				model.addAttribute("url", "/authInfo/authInfoList");
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("mnumber", mnumber);
				model.addAttribute("type", type);
				model.addAttribute("str",str);
				model.addAttribute("source",source);
				return "message/authInfoList";
			}		
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(" com.duanrong.newadmin.controller.MessageController.authInfoList()", e);
		}
		return null;
	}
	/**
	 * 
	 * @description 验证码列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sms/smsList")
	public String smsList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if(MyStringUtils.isAnyBlank(pageNo)){
					pageNo = "1";
				}
				String str = "";
				String type = request.getParameter("type");
				String realname = request.getParameter("realname");
				String start = request.getParameter("start");
				String end = request.getParameter("end");
				String id=request.getParameter("userId");
				String userId =getUserId(id);
				String name="";
				Map<String, Object> params = new HashMap<String, Object>();
				
				if (StringUtils.isNotBlank(userId)) {
					str += "&userId=" + userId;
					params.put("userId", userId);
				}
				
				if (StringUtils.isNotBlank(type)) {
					str += "&type=" +  type;
					params.put("type", type);
				}
				if (StringUtils.isNotBlank(realname)) {
					name = java.net.URLDecoder.decode(realname, "UTF-8");
					params.put("realname", name);
					str += "&realname=" +  name;
					
				}
				if (StringUtils.isNotBlank(start)) {
				//	String stime= DateUtil.StringToString(start,"mm/dd/yyyy", "yyyy-mm-dd");
					params.put("start", start);
					str += "&start=" + start;
				}
				if (StringUtils.isNotBlank(end)) {
					//String etime= DateUtil.StringToString(end,"mm/dd/yyyy", "yyyy-mm-dd");
					String endtime=DateUtil.addDay(end, 1);
					params.put("end", endtime);
					str += "&end=" + end;
				}
				
				PageInfo pageInfo=smsService.readPageInfo(Integer.parseInt(pageNo), 10, params);
				model.addAttribute("url", "/sms/smsList");
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("type", type);
				model.addAttribute("start", start);
				model.addAttribute("end", end);
				model.addAttribute("realname", name);
				model.addAttribute("userId", id);
				model.addAttribute("str",str);
				return "message/smsList";
			}		
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(" com.duanrong.newadmin.controller.MessageController.authInfoList()", e);
		}
		return null;
	}
	/**
	 * 
	 * @description 站内信列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info/infoList")
	public String infoList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if(MyStringUtils.isAnyBlank(pageNo)){
					pageNo = "1";
				}
				String name="";
				Map<String, Object> params = new HashMap<String, Object>();
				String id=request.getParameter("userId");
				params.put("userId", getUserId(id));
				params.put("type", request.getParameter("type"));
				params.put("realname", request.getParameter("realname"));
				params.put("start", request.getParameter("start"));
				String end=request.getParameter("end");
				if (StringUtils.isNotBlank(end)) {
					String endtime=DateUtil.addDay(end, 1);
					params.put("end", endtime);
				}
				 
				if (StringUtils.isNotBlank(request.getParameter("realname"))) {
					name = java.net.URLDecoder.decode(request.getParameter("realname"), "UTF-8");
				}
				PageInfo<Information> pageInfo = informationService.readPageInfo(Integer.parseInt(pageNo), 10, FormUtil.getForParamToBean(params));
				request.setAttribute("url", "/info/infoList");			
				request.setAttribute("str", FormUtil.getForParam(params));
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("map", FormUtil.getForParamToBean(params));
				model.addAttribute("type", request.getParameter("type"));
				model.addAttribute("start", request.getParameter("start"));
				model.addAttribute("end", request.getParameter("end"));
				model.addAttribute("realname", name);
				model.addAttribute("userId", request.getParameter("userId"));
				return "message/infoList";
			}		
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(" com.duanrong.newadmin.controller.MessageController.infoList()", e);
		}
		return null;
	}
	
	
	/**
	 * 
	 * @description 站内信列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info/toSend")
	public String toSendInfo() {
		
		return "message/sendInfo";						
	}
	
	/**
	 * 
	 * @description 站内信列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info/send")
	public void sendInfo(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		try{
			List<User> user = userService.readAll();
			int i = 0;
			for(User u : user){
				if(null != u && null != u.getUserId()){				
					informationService.sendInformation(u.getUserId(), title, content);
					i++;
				}				
			}
			response.getWriter().print(i);
		}catch(Exception e){
			e.printStackTrace();
			log.errLog("info", e);
		}	
	}
	
	/**
	 * 
	 * @description 验证码列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authInfo/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
				String id = request.getParameter("id");
				String mobile = request.getParameter("mobile");
				AuthInfo authInfo=new AuthInfo();
				authInfo.setId(id);
				authInfo.setStatus("0");
			    authInfo.setDeadline(DateUtil.addMinute(new Date(), 30));
			    authInfoService.updateauth(authInfo);
			    operaRecordService.insertRecord("延长验证码失效时间", getLoginUser.getUserId(), "修改手机号"+mobile+"id:"+id);
				return "redirect:/authInfo/authInfoList";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(" com.duanrong.newadmin.controller.MessageController.edit()", e);
		}
		return null;
	}
}