package com.duanrong.dataAnalysis.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanrong.dataAnalysis.business.user.service.UserService;

@Controller
public class UserController extends BaseController{

	@Resource
	UserService userService;
	
	@RequestMapping(value = "/test")
	public void test(HttpServletRequest request, HttpServletResponse response){
		String mobileNumber = request.getParameter("mobile");		
		String flag = userService.getUserByMobileNumber(mobileNumber).toString();
		try {
			response.getWriter().print(flag);
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
