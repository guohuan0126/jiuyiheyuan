package com.duanrong.thirdPartyInterface.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import base.model.Loan;

import com.duanrong.business.token.model.AccessToken;
import com.duanrong.business.token.service.AccessTokenService;
import com.duanrong.jsonservice.util.SecurityUtil;
import com.duanrong.thirdPartyInterface.model.WDZJLoan;
import com.duanrong.thirdPartyInterface.service.WDZJService;
import com.duanrong.util.DateUtil;

@Controller
public class WDZJController {

	@Resource
	WDZJService wdzjService;

	@Resource
	AccessTokenService accessTokenService;

	/**
	 * 
	 * @description 登录
	 * @author 孙铮
	 * @time 2014-11-11 下午6:19:58
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tdt/login.json", method = RequestMethod.GET)
	@ResponseBody
	public String login(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		int flag = 0;
		String token = null;
		if ("duanrong".equals(userName) && "duanrongwdzj!@~".equals(password)) {
			token = "c9f896e6934111e5b80eac162d8afeb0";
			flag = 1;
		}
		JSONObject json2 = new JSONObject();
		json2.put("token", token);
		JSONObject json = new JSONObject();
		json.put("data", json2).put("return", flag);
		System.out.println(json.toString());
		return json.toString();
	}

	/**
	 * 
	 * @description 获取当前正在进行投标中的标信息
	 * @author 孙铮
	 * @time 2014-11-11 下午6:19:54
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tdt/getNowProjects.json", method = RequestMethod.GET)
	@ResponseBody
	public List<WDZJLoan> getNowProjects(HttpServletRequest request) {
		System.out.println("WDdata##############");
		String token = request.getParameter("token");
		if (token !=null && token.equals("c9f896e6934111e5b80eac162d8afeb0") ) {
			// 去数据库中查询相关表并进行封装
			Loan loan = new Loan();
			loan.setStatus("WDZJstatus");
			List<WDZJLoan> projects = wdzjService.getProjects(loan);
			return projects;
		} else {
			// token验证失败
			return null;
		}
	}

	/**
	 * 
	 * @description 获取所有在’date’这一天成功借款（即满标时间为date）的标。date格式: ‘2013-09-01’
	 * @author 孙铮
	 * @time 2014-11-11 下午6:42:45
	 * @param token
	 * @param date
	 * @return
	 */
	@RequestMapping(value = "/tdt/getProjectsByDate.json", method = RequestMethod.GET)
	@ResponseBody
	public List<WDZJLoan> getProjectsByDate(HttpServletRequest request) {
		String token = request.getParameter("token");
		String date = request.getParameter("date");
		if (token !=null && token.equals("c9f896e6934111e5b80eac162d8afeb0") ) {
			// 去数据库中查询相关表并进行封装
			Loan loan = new Loan();
			loan.setStatus("WDZJstatus1");
			String startDate = date + " 00:00:00";
			String endDate = date + " 23:59:59";
			loan.setGiveMoneyOperationTime(DateUtil.StringToDate(startDate));
			loan.setCommitTime(DateUtil.StringToDate(endDate));
			// loan.setExpectTime(DateUtil.StringToDate(date));
			List<WDZJLoan> projects = wdzjService.getProjects(loan);
			return projects;
		} else {
			// token验证失败
			return null;
		}
	}
}