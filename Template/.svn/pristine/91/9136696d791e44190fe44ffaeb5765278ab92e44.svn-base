package com.duanrong.dataAnalysis.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.CouponByPerson.model.CouponByPerson;
import com.duanrong.dataAnalysis.business.CouponByPerson.model.UserInfo;
import com.duanrong.dataAnalysis.business.CouponByPerson.service.CouponByPersonService;
import com.duanrong.dataAnalysis.business.UserInfo.service.UserInfoService;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.business.user.service.UserService;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;
import com.duanrong.dataAnalysis.util.MyStringUtils;
/**
 * 现金红包列表页面
 * @author bj
 *
 */

@Controller
public class CouponByPersonController extends BaseController {
	@RequestMapping("/CouponByPerson")
	public String toCouponByPerson(HttpServletRequest request,HttpServletResponse response,Model model){
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/
		return "couponByPerson";
		
	}
	
	
	@Resource
	CouponByPersonService couponByPersonService;
	@Resource
	UserInfoService userInfoService;
	@RequestMapping("/showCouponByPerson")
	public String showCouponByPerson(
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			@RequestParam(value="pageSize",defaultValue="5")Integer pageSize,
			HttpServletRequest request,HttpServletResponse response,Model model){
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/
		String param=request.getParameter("userId");
		//使用情况
		String type=request.getParameter("type");
		//红包还是加息券
		String status=request.getParameter("status");
		String activeId=request.getParameter("activeId");
		User u = null;
		if (param!=null) {
			if (MyStringUtils.isNumeric(param) && param.length() == 11) {
				 u = userService.getUserByMobileNumber(param);
			}else{
				 u = userService.getUserById(param);
			}
			if(u != null){
				PageInfo<CouponByPerson> pageInfo=couponByPersonService.pageLite4Map(u.getMobileNumber(),type,status,activeId,pageNo, pageSize, type);
				//id 信息
				UserInfo userInfo=couponByPersonService.getRedPackageInfo(u.getMobileNumber());		
				 if(userInfo != null){
					 String userId=u.getUserId();
					 double money=couponByPersonService.getUserMoney(userId);
					userInfo.setUserMoney(money);
					userInfo.setInvestMoney(couponByPersonService.getInvestMoney(userId));
					//账户总资产
					userInfo.setAllMoney(userInfoService.getUserAllMoneyById(userId));
				 }
				
				 if(pageInfo.getTotalRecord()==0){
						String error="没有可以查询的有效数据";
						model.addAttribute("error", error);
					}
				 model.addAttribute("pageInfo",pageInfo);
				model.addAttribute("userId",param);
				model.addAttribute("type",type);
				model.addAttribute("status",status);
				model.addAttribute("activeId",activeId);
				model.addAttribute("userInfo",userInfo);
				
			}
			
			
			
		}
		return "couponByPerson";
	}
	
	
	
	@Resource
	UserService userService;
	public  String getUserId(String param){
		if(param != null && param != ""){
			User user = null;
			if(param.contains("@")){
				user = userService.getUserByEmail(param);
			}else if(MyStringUtils.isNumeric(param) && param.length() == 11){
				user = userService.getUserByMobileNumber(param);
			}
			if(user == null){
				return "";
			}
			return user.getUserId();
		}
		return "";
	}

	
}
