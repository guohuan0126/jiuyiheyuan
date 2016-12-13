package com.duanrong.newadmin.controller;

import java.io.IOException;
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

import com.duanrong.business.app.model.AppBanner;
import com.duanrong.business.app.service.AppBannerService;
import com.duanrong.newadmin.utility.FormUtil;

/**
 * Banner图Controller
 * @author Qiu Feihu
 * @time 2015年6月6日12:00:15
 */
@Controller
public class AppBannerController {
    
	@Autowired
	private AppBannerService appBannerServic;
	
	/**
	 * 列表页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/appBanner/list")
	public String appBannerList(HttpServletRequest request,
			HttpServletResponse response){
		int pageNo = 0;
		int pageSize = 10;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		if (MyStringUtils.isNumeric(request.getParameter("pageSize"))) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		AppBanner app = new AppBanner();
		String picStatus = request.getParameter("picStatus");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(startTime != null){startTime = startTime.replace("%20"," ");};
		if(endTime != null){endTime = endTime.replace("%20"," ");};
		
		app.setPicStatus(picStatus);
		app.setStartTime(startTime);
		app.setEndTime(endTime);
		app = (AppBanner) FormUtil.getForParamToBean(app);
		PageInfo<AppBanner> pageInfo = appBannerServic.readPageLite(pageNo, pageSize, app);
		List<AppBanner> list = pageInfo.getResults();
		
		pageInfo.setResults(list);
		request.setAttribute("url", "/appBanner/list");
		request.setAttribute("str", FormUtil.getForParam(app));
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("picStatus",picStatus);
		request.setAttribute("startTime",startTime);
		request.setAttribute("endTime",endTime);
		return "app/AppBannerList";
	}
	
	
	 
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/appBanner/goAdd")
	public String goAddAppBanner(HttpServletRequest request,
			HttpServletResponse response){
		request.setAttribute("type","add");
		return "app/editAppBanner";
	}
	
	
	
	/**
	 * 跳转到更新页面
	 * @param request
	 * @param response
	 * @param picID
	 * @return
	 */
	@RequestMapping("/appBanner/goUpdate")
	public String goUpdateAppBanner(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("picID")String picID){
		
	    AppBanner app = this.appBannerServic.read(picID);
		request.setAttribute("type","update");
		request.setAttribute("app",app);
		return "app/editAppBanner";
	}
	
	/**
	 * 修改或保存Banner图数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/appBanner/edit")
	public void addAppBanner(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
        String type = request.getParameter("type");
        AppBanner app = new AppBanner();
        app.setPicName(request.getParameter("picName"));
        app.setPicPosition(request.getParameter("picPosition"));
        app.setPicStatus(request.getParameter("picStatus"));
        app.setPicUrl(request.getParameter("picUrl"));
        app.setSeqNum(request.getParameter("seqNum"));
        app.setDescription(request.getParameter("description"));
        app.setShareImgUrl(request.getParameter("shareImgUrl"));
        app.setTime(new Date());
        String success = "";
        if(type != null && type.equals("add")){
        	this.appBannerServic.add(app);
        	success = "add_success";
        }else if(type != null && type.equals("update")){
        	app.setPicID(request.getParameter("picID"));
        	this.appBannerServic.update(app);
        	success = "update_success";
        }
        response.getWriter().print(success);
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param picID
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/appBanner/delete")
	public String deleteAppBanner(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("picID")String picID) throws IOException{
	    this.appBannerServic.delete(picID);
	    return this.appBannerList(request, response);
	}
	
	
	/**
	 * 
	 * @description 处理上传
	 * @author qiufeihu
	 * @time 2015年6月6日12:00:04
	 * @param files
	 * @param response
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/appBanner/uploadImages", method = RequestMethod.POST)
	public void uploadImages(
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String str= this.appBannerServic.uploadFile(files, request);
		response.getWriter().print(str);
	}	
	
}
