package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.alibaba.druid.util.StringUtils;
import com.duanrong.business.activity.model.Activity;
import com.duanrong.business.activity.service.ActivityService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.FormUtil;

/**
 * 活动和专题页面Controller
 * @author Qiu Feihu
 * @Time 2015年6月8日12:08:19
 */
@Controller
public class ActivityController {
   
	
	@Autowired
	public ActivityService activityService;
	
	@Autowired
	public UserService userService;
	
	/**
	 * 跳转至列表页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/activity/list")
	public String list(
			HttpServletRequest request,
			HttpServletResponse response){
		int pageNo = 0;
		int pageSize = 10;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		if (MyStringUtils.isNumeric(request.getParameter("pageSize"))) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		String picStatus = request.getParameter("picStatus");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(startTime != null){startTime = startTime.replace("%20"," ");};
		if(endTime != null){endTime = endTime.replace("%20"," ");};
		String status = request.getParameter("status");
        String type = request.getParameter("type");
        String isEndStr = request.getParameter("isEnd");
		Integer isEnd = null;
        if(!StringUtils.isEmpty(isEndStr)){
			isEnd = Integer.parseInt(isEndStr);
		}
        
		Activity act = new Activity();
		act.setStartTime(startTime);
		act.setEndTime(endTime);
		act.setStatus(status);
		act.setIsEnd(isEnd);
		act.setType(type);
		PageInfo<Activity> pageInfo = activityService.readPageLite(pageNo, pageSize, act);
		List<Activity> list = pageInfo.getResults();
		
		pageInfo.setResults(list);
		request.setAttribute("url", "/activity/list");
		request.setAttribute("str", FormUtil.getForParam(act));
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("status",status);
		request.setAttribute("type",type);
		request.setAttribute("isEnd",isEnd);
		return "/activity/list";
	}
	
	/**
	 * 跳转至添加页面
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/activity/goAdd")
	public String goAdd(HttpServletRequest request,
			HttpServletResponse response){
    	request.setAttribute("editType","add");
		return "/activity/edit";
	}
	
    
    /**
     * 跳转至更新页面
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/activity/goUpdate")
	public String goUpdate(
			@RequestParam("id")Integer id,
			HttpServletRequest request,
			HttpServletResponse response){
    	Activity act = this.activityService.read(id);
    	request.setAttribute("act",act);
    	request.setAttribute("editType","update");
		return "/activity/edit";
	}

	/**
	 * 修改或保存
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/activity/edit")
	public void addAppBanner(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
        String editType = request.getParameter("editType");
        Activity activity = new Activity();
        activity.setTitle(request.getParameter("title"));
        activity.setContent(request.getParameter("content"));
        activity.setImageUrl(request.getParameter("imageUrl"));
        activity.setStartTime(request.getParameter("startTime"));
        activity.setEndTime(request.getParameter("endTime"));
        activity.setStatus(request.getParameter("status"));
        activity.setUrl(request.getParameter("url"));
        activity.setType(request.getParameter("type"));
        activity.setTarget(request.getParameter("target"));
        String isEndStr = request.getParameter("isEnd");
		Integer isEnd = null;
        if(!StringUtils.isEmpty(isEndStr)){
			isEnd = Integer.parseInt(isEndStr);
			activity.setIsEnd(isEnd);
		}
        String success = "";
        if(editType != null && editType.equals("add")){
            UserCookie userCookie = UserCookieHelper.GetUserCookie(request, response);
            User user = this.userService.read(userCookie.getUserId());
            activity.setUser(user);
        	activity.setCreateTime(new Date());
        	this.activityService.add(activity);      	
        	success = "add_success";
        }else if(editType != null && editType.equals("update")){
        	User user = this.userService.read(request.getParameter("userId"));
        	activity.setUser(user);
        	activity.setId(Integer.parseInt(request.getParameter("id")));
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        	Date date = null;
			try {
				date = sdf.parse(request.getParameter("createTime"));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
        	activity.setCreateTime(date);
        	activity.setUpdateTime(new Date());
        	this.activityService.update(activity);
        	success = "update_success";
        }       
        response.getWriter().print(success);
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/activity/delete")
	public String deleteAppBanner(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("id")Integer id) throws IOException{	
	    this.activityService.delete(id);
	    return this.list(request, response);
	}
	
	/**·1·
	 * 
	 * @description 处理上传
	 * @author qiufeihu
	 * @time 2015年6月8日17:36:11 
	 * @param files
	 * @param response
	 * @param request
	 * @throws IOException 
	 */ 
	@RequestMapping(value = "/activity/uploadImages", method = RequestMethod.POST)
	public void uploadImages(
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String str= this.activityService.uploadFile(files, request);
		response.getWriter().print(str);
	}	
	
}
