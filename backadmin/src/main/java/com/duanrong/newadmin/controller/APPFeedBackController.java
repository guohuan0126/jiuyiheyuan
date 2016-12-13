package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import util.Log;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.business.app.model.APP;
import com.duanrong.business.app.model.AppFeedback;
import com.duanrong.business.app.service.AppFeedbackService;
import com.duanrong.business.app.service.AppService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.utility.AES;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.FormUtil;

/**
 * 手机APP用户反馈控制器
 * 
 * @author Qiu Feihu
 * @version 2015年6月4日16:40:29
 */
@Controller
public class APPFeedBackController extends BaseController{
    
	@Autowired
	private AppFeedbackService appFeedbackService;
	@Autowired
	private AppService appService;
	
	@Resource
	private UserService userService;
	@Resource
	Log log;

	
	/**
	 * 手机APP反馈问题分页查询
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/appFeedback/list")
	public String loanView(HttpServletResponse response,
			HttpServletRequest request) {
		int pageNo = 0;
		int pageSize = 30;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		if (MyStringUtils.isNumeric(request.getParameter("pageSize"))) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		AppFeedback app = new AppFeedback();
		String osName = request.getParameter("osName");
		String stratTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(stratTime != null){stratTime = stratTime.replace("%20"," ");};
		if(endTime != null){endTime = endTime.replace("%20"," ");};
		app.setOsName(osName);
		app.setStartTime(stratTime);
		app.setEndTime(endTime);
		app = (AppFeedback) FormUtil.getForParamToBean(app);
		PageInfo<AppFeedback> pageInfo = appFeedbackService.readPageLite(pageNo, pageSize, app);
		List<AppFeedback> list = pageInfo.getResults();
		for (AppFeedback appFeedback : list) {
			if(appFeedback.getContact()!=null){
				try {
					appFeedback.setContact(AES.decode(appFeedback.getContact()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.errLog("手机号解密错误",e);
				}
			}else {
				if(!appFeedback.getUserId().equals("")&&appFeedback.getUserId()!=null){
					User user=userService.read(appFeedback.getUserId());
					appFeedback.setContact(user.getMobileNumber());
				}
				
			}
		}
		pageInfo.setResults(list);
		request.setAttribute("url", "/appFeedback/list");
		request.setAttribute("str", FormUtil.getForParam(app));
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("osName",osName);
		request.setAttribute("startTime",stratTime);
		request.setAttribute("endTime",endTime);
		return "app/AppFeedbackList";

	}
	
	/**
	 * 改变处理状态
	 */
	@RequestMapping(value="/appFeedback/changeHandleType")
	public String changeHandleType(HttpServletResponse response,
			HttpServletRequest request,Model model) {
		String id=request.getParameter("id");
		String handleType=request.getParameter("handleType");
		appFeedbackService.updateChangeHandleType(id,handleType);
		
		return this.loanView(response, request);
	}
	
	/**
	 * 导出手机APP问题反馈列表
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/appFeedback/export")
	public void exportLoan(HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		AppFeedback app = new AppFeedback();
		String osName = request.getParameter("osName");
		String stratTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		app.setOsName(osName);
		app.setStartTime(stratTime);
		app.setEndTime(endTime);
		app = (AppFeedback) FormUtil.getForParamToBean(app);
		List<AppFeedback> appFeeds = appFeedbackService.read(app);
		Map<String, String> title = new LinkedHashMap<>();
		title.put("id", "编号");
		title.put("osName", "系统类型");
		title.put("content", "反馈内容");
		title.put("time", "反馈时间");
		int[] style;
		if (havePermissionById("USER_LOOK", request, response)){
			title.put("contact", "联系方式");
			style = new int[]{8, 25, 40, 25, 20}; 
		}else{
			style = new int[]{8, 25, 40, 25}; 
		}
		String fileName = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		fileName = "APPFeedback" + fileName + ".xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ fileName);
		ExcelConvertor excelConvertor = new ExcelConvertor(
				response.getOutputStream(), fileName);
		String t = "手机APP反馈问题列表";		
		excelConvertor.excelExport(appFeeds, title, t, style);

	}
	@RequestMapping(value = "/app/version")
	public String version(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
				String name = request.getParameter("name");
				String pageNo = request.getParameter("pageNo");
				if(MyStringUtils.isAnyBlank(pageNo)){
					pageNo = "1";
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("name", name);
				PageInfo pageInfo= appService.readPageInfo(Integer.parseInt(pageNo), 10, FormUtil.getForParamToBean(params));
				request.setAttribute("str", FormUtil.getForParam(params));
				request.setAttribute("pageInfo", pageInfo);
				request.setAttribute("name", name);
				model.addAttribute("url", "/app/version");
				return "app/versionList";
			
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					" com.duanrong.newadmin.controller.APPFeedBackController.version()",
					e);

		}
		return null;
	}
	
	@RequestMapping(value = "/app/toversion")
	public String toversion(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
			String id = request.getParameter("id");
			APP p = appService.readAppById(id);
			model.addAttribute("app", p);
		return "app/editVersion";
	}
	@RequestMapping(value = "/app/saveVesrion", method = RequestMethod.POST)
	public void saveVesrion(HttpServletRequest request,
			HttpServletResponse response, APP app, Model model) {		
		try {
			app.setTime(new Date());
			appService.updateApp(app);
			response.getWriter().print("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
