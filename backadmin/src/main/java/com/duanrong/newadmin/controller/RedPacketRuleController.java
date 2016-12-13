package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.activity.model.Activity;
import com.duanrong.business.activity.model.RedPacketRule;
import com.duanrong.business.activity.service.RedPacketRuleService;
import com.duanrong.business.user.model.User;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;

/**
 * 活动类型管理Controller
 * @author Administrator
 *
 */
@Controller
public class RedPacketRuleController {
   
	@Autowired
	public RedPacketRuleService redPacketRuleService;
	
	@RequestMapping("/redPacketRule/list")
	public ModelAndView list(
			@RequestParam(value="pageNo",required=false)Integer pageNo,
			@RequestParam(value="pageSize",required=false)Integer pageSize,
			HttpServletRequest request,
			HttpServletResponse response, Model model){
		if(pageNo == null){
			pageNo = 0;
		}
		if(pageSize == null){
			pageSize = 100;
		}
		ModelAndView mv = new ModelAndView();
		RedPacketRule rule = new RedPacketRule();
		PageInfo<RedPacketRule> pageInfo = this.redPacketRuleService.readPageLite(pageNo, pageSize, rule);
		mv.addObject("pageInfo",pageInfo);
		mv.setViewName("/redPacketRule/list");
		return mv;
	}
	
	
	/**
	 * 跳转至添加页面
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/redPacketRule/goAdd")
	public String goAdd(HttpServletRequest request,
			HttpServletResponse response){
    	request.setAttribute("editType","add");
		return "/redPacketRule/edit";
	}
	
    
    /**
     * 跳转至更新页面
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/redPacketRule/goUpdate")
	public String goUpdate(
			@RequestParam("id")Integer id,
			HttpServletRequest request,
			HttpServletResponse response){
    	RedPacketRule rule = this.redPacketRuleService.read(id);
    	request.setAttribute("rule",rule);
    	request.setAttribute("editType","update");
		return "/redPacketRule/edit";
	}

	/**
	 * 修改或保存
	 * @param request
	 * @param response
	 * @throws IOException
	 */
    @ResponseBody
	@RequestMapping("/redPacketRule/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response){
		
        String editType = request.getParameter("editType");
        RedPacketRule rule = new RedPacketRule();
        rule.setName(request.getParameter("name"));
        rule.setType(request.getParameter("type"));
        rule.setSendType(request.getParameter("sendType"));
        rule.setGetRule(request.getParameter("getRule"));
        
        String success = "";
        if(editType != null && editType.equals("add")){
        	this.redPacketRuleService.add(rule);
        	success = "add_success";
        }else if(editType != null && editType.equals("update")){
        	rule.setId(Integer.parseInt(request.getParameter("id")));
        	this.redPacketRuleService.update(rule);
        	success = "update_success";
        }
        
        return success;
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/redPacketRule/delete")
	public ModelAndView delete(
			@RequestParam("id")Integer id,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws IOException{
		
	    this.redPacketRuleService.delete(id);
		
	    return this.list(0,100, request, response, model);
	}
	

	
}
