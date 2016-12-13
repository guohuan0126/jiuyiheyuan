package com.duanrong.cps.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.duanrong.authentication.service.AuthService;
import com.duanrong.authentication.service.impl.AuthServiceImpl;
import com.duanrong.cps.business.loan.model.LoanData;
import com.duanrong.cps.business.loan.service.LoanDataService;
import com.duanrong.cps.business.user.model.User;
import com.duanrong.cps.business.user.model.UserData;
import com.duanrong.cps.business.user.service.UserDataService;
import com.duanrong.cps.business.user.service.UserService;
import com.duanrong.cps.controllerHelper.UserCookieHelper;

@Controller
public class IndexController extends BaseController{

@Resource
private UserService userService;
@Resource
UserDataService userDataService;
@Resource
private LoanDataService loanDataService;	
	
	@RequestMapping("/")
	public String Exitindex(HttpServletRequest request){	
		
		return "redirect:/index";   
	}
	@RequestMapping("/exit")
	public String index(HttpServletRequest request,HttpServletResponse response){	
		//拿到cookie
		AuthService auth=new AuthServiceImpl();
		String url=auth.signOut();
		return "redirect:"+url;   
	}
	@RequestMapping("/index")
	public ModelAndView toIndex(HttpServletRequest request,HttpServletResponse response,Model model){
		User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);
		return new ModelAndView("index");
	}
	
	@RequestMapping("/showAllData")
	public String toData(HttpServletRequest request,HttpServletResponse response,Model model){
		User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);
		return "houtai2";
	}
/*	//去现金红包页面
	@RequestMapping("/toShowRedPackage")
	public String toRedPackage(HttpServletRequest request,HttpServletResponse response,Model model){
		User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);
		return "redPackage";
	}*/
	
	//去数据显示页面
			@RequestMapping("/showData")
			public String toShowData(Model model,HttpServletRequest request,HttpServletResponse response){
				UserData userData=userDataService.getUserData();
				model.addAttribute("userData", userData);
				LoanData loanData=loanDataService.getLoanData();
				model.addAttribute("loanData", loanData);
				User user=UserCookieHelper.GetUserCookie(request, response);
				model.addAttribute("user", user);
				return "houtai1";
			}
}
		
		
		
		
		

	   
	

