package com.duanrong.cps.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.service.PlatformPushService;

@Controller
public class PushLoanController extends BaseController{
	
	@Autowired
	private PlatformPushService platformPushService;
	
	/**
	 * 查询可以推送益的项目列表
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/getPlatformWaitPushLoanList")
	public String list(HttpServletRequest request, HttpServletResponse response,Model m){
		String pageNo=request.getParameter("pageNo");
		String type=request.getParameter("type");
		if( pageNo == null || "".equals(pageNo) ){
			pageNo = "1";
		}
		Map<String,String>params=new HashMap<String,String>();
		params.put("type", type.trim());
		JSONObject json=new JSONObject();
		json.put("thirdType", type);
		//分页，带查询
		try{
			PageInfo<Loan> pageInfo = platformPushService.queryWaitPushLoanList(Integer.parseInt(pageNo), 10, json);
			m.addAttribute("pageInfo", pageInfo);
			//m.addAttribute("loan", loan);
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
		
		return type+"/waitPushLoanList";
	}

}
