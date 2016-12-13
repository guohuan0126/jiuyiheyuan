package com.duanrong.dataAnalysis.controller;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.duanrong.authentication.service.AuthService;
import com.duanrong.authentication.service.impl.AuthServiceImpl;
import com.duanrong.business.permission.model.NewMenu;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.business.user.service.UserService;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;
import com.duanrong.dataAnalysis.util.CookieUtil;

@Controller
public class IndexController extends BaseController{

@Resource
private UserService userService;
	
		@RequestMapping("/")
		public String Exitindex(HttpServletRequest request){	
				System.out.println("****************************************************************");
			return "redirect:/showData111";   
		}
		@RequestMapping("/exit")
		public String index(HttpServletRequest request,HttpServletResponse response){	
				//拿到cookie
			AuthService auth=new AuthServiceImpl();
			String url=auth.signOut();
			System.out.println(url);
			return "redirect:"+url;   
		}	

	@RequestMapping("/index")
	public String toIndex(HttpServletRequest request,HttpServletResponse response,Model model){
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/
		return"houtai";
	}
	
	@RequestMapping("/showAllData")
	public String toData(HttpServletRequest request,HttpServletResponse response,Model model){
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/
		return "houtai2";
	}
/*	//去现金红包页面
	@RequestMapping("/toShowRedPackage")
	public String toRedPackage(HttpServletRequest request,HttpServletResponse response,Model model){
		User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);
		return "redPackage";
	}*/
	
	/**
	 * 得到用户所拥有的菜单
	 * @param systypeId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getMenu")
	public void getMenu(String systypeId,HttpServletRequest request,HttpServletResponse response){
		AuthService authService=new AuthServiceImpl();
		com.duanrong.business.permission.model.UserCookie user= authService.getUser(request, response);
		String userID="";
		if(user!=null){
			userID=user.getUserId();
		}
		List<NewMenu>newMenu=authService.getMenu(userID, "9620006b203445d4910306fa817a3fa5");
		JSONArray jsonArry=JSONArray.fromObject(newMenu);
		System.out.println(jsonArry.toString()+"*************************");
		try {
			response.setCharacterEncoding("UTF-8");
	        response.getWriter().print(jsonArry.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
		
		
		
		
		

	   
	

