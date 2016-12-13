package com.duanrong.dataAnalysis.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import base.pagehelper.PageInfo;



import com.duanrong.dataAnalysis.business.CouponByPerson.service.CouponByPersonService;
import com.duanrong.dataAnalysis.business.UserInfo.model.ComeBack;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserInfo;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserSourceData;
import com.duanrong.dataAnalysis.business.UserInfo.service.UserInfoService;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;

@Controller
public class UserInfoController {

	
	
	
	@RequestMapping("/userData/toUserData")
	public String toShowUserInfo(HttpServletRequest request,HttpServletResponse response,Model model){
		User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);
		return "userInfo";
	}
	/**
	 * 用户信息管理
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private CouponByPersonService couponByPersonService;
		@RequestMapping("/userData/toShowUserData")
		public  String showUserInfo(
				@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
				 @RequestParam(value="pageSize",defaultValue="30")Integer pageSize,
				 HttpServletRequest request,HttpServletResponse response,Model model
				){
			User user=UserCookieHelper.GetUserCookie(request, response);
			
		String userSource=request.getParameter("userSource");
		String registerTimeBegin=request.getParameter("registerTimeBegin");
		String registerTimeEnd1=request.getParameter("registerTimeEnd");
		String liveInvestMin=request.getParameter("liveInvestMin");
		String liveInvestMax=request.getParameter("liveInvestMax");
		String regularInvestMin=request.getParameter("regularInvestMin");
		String regularInvestMax=request.getParameter("regularInvestMax");
		String minRedPackCount=request.getParameter("minRedPackCount");
		String maxRedPackCount=request.getParameter("maxRedPackCount");
		String minRateCount=request.getParameter("minRateCount");
		String maxRateCount=request.getParameter("maxRateCount");
		String minInvestMoney=request.getParameter("minInvestMoney");
		String maxInvestMoney=request.getParameter("maxInvestMoney");
		String real=request.getParameter("real");
		PageInfo<UserInfo> pageInfo=userInfoService.pageLite4Map(pageNo,pageSize,userSource,liveInvestMin,liveInvestMax,regularInvestMin,regularInvestMax,
						minRedPackCount,maxRedPackCount,minRateCount,maxRateCount,minInvestMoney,maxInvestMoney,real,registerTimeBegin,registerTimeEnd1);
			
			if(pageInfo.getTotalRecord()==0){
				String error="没有可以查询的有效数据";
				model.addAttribute("error", error);
			}
		//拿到userId 根据userId 去查询数据 封装到 userInfo对象中
			List<UserInfo> list=pageInfo.getResults();
			for (UserInfo userInfo : list) {
			String id=userInfo.getId();
			//根据id 查询出账户余额
			double userAllMoney=userInfoService.getUserAllMoneyById(id);
			//账户总资产
			userInfo.setUserAllMoney(userAllMoney);
			//可用余额
			userInfo.setUsedMoney(couponByPersonService.getUserMoney(id));
			}
			//底部数据
			UserSourceData userSourceData=userInfoService.getUserSourceData(userSource,registerTimeBegin,registerTimeEnd1);
			
			userSourceData.setInvestCount(userSourceData.getRegularInvestCount()+userSourceData.getLiveInvestCount());
			//账户可用余额
			userSourceData.setUserUsedMoney(userInfoService.getUserUsedMoney(userSource,registerTimeBegin,registerTimeEnd1));
			//账户总资产
			userSourceData.setAllMoney(userInfoService.getUserAllMoney(userSource,registerTimeBegin,registerTimeEnd1));
			
		model.addAttribute("user", user);
		model.addAttribute("userSource", userSource);
		model.addAttribute("liveInvestMin", liveInvestMin);
		model.addAttribute("liveInvestMax", liveInvestMax);
		model.addAttribute("regularInvestMin", regularInvestMin);
		model.addAttribute("regularInvestMax", regularInvestMax);
		model.addAttribute("minRedPackCount", minRedPackCount);
		model.addAttribute("maxRedPackCount", maxRedPackCount);
		model.addAttribute("minRateCount", minRateCount);
		model.addAttribute("maxRateCount", maxRateCount);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("minInvestMoney", minInvestMoney);
		model.addAttribute("maxInvestMoney", maxInvestMoney);
		model.addAttribute("userSourceData", userSourceData);
		model.addAttribute("real", real);
		model.addAttribute("registerTimeBegin", registerTimeBegin);
		model.addAttribute("registerTimeEnd", registerTimeEnd1);
		return "userInfo";
	}
	
		@ResponseBody
		@RequestMapping("/userData/toShowUserDataBack")
		public String showBack(HttpServletRequest request, HttpServletResponse response){
			String id=request.getParameter("id");
			List<ComeBack> comeBack=userInfoService.getComeBack(id);
			JSONArray json = JSONArray.fromObject(comeBack);
			
			return json.toString();
		
			
		}
	
}
