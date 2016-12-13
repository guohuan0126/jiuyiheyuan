package com.duanrong.dataAnalysis.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;










import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveData;
import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveR;
import com.duanrong.dataAnalysis.business.ActiveData.model.UserR;
import com.duanrong.dataAnalysis.business.ActiveData.service.ActiveDataService;
import com.duanrong.dataAnalysis.business.currnetData.service.CurrnetDataService;
import com.duanrong.dataAnalysis.business.redPackage.modle.RedPackage;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;
import com.duanrong.dataAnalysis.util.MyStringUtils;

@Controller
public class ActiveDataController extends BaseController {
	
	
	@RequestMapping("/activeData/toActiveData")
	public String toShowCurrnet(HttpServletRequest request,HttpServletResponse response,Model model){
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/

		return "activeData";
	}
	
	@Resource 
	private ActiveDataService activeDataService;	
	
	@RequestMapping("/activeData/showActiveData")
	public String String (
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			 @RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
			HttpServletRequest request,HttpServletResponse response,Model model){
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/
		String activeId=request.getParameter("activeId");
		String userSource=request.getParameter("userSource");
		System.out.println(activeId+"--"+userSource);
		PageInfo<ActiveData> pageInfo=activeDataService.pageLite4Map(activeId,pageNo,pageSize);
			List<ActiveData> activeDataList=pageInfo.getResults();
			for (ActiveData activeData : activeDataList) {
				double money=activeData.getRedPackageMoney();
				//发放张数
				activeData.setSendCount(activeDataService.getSendCount(activeId,money));
				//使用数量
				activeData.setUsedCount(activeDataService.getUsedNum(activeId,money));
				//未用数量
				activeData.setUnusedCount(activeDataService.getUnusedCount(activeId,money));
				// 过期数量
				activeData.setExpiredCount(activeDataService.getExpiredCount(activeId,money));
				//投资金额
				activeData.setUserInvestMoney(activeDataService.getUserInvestMoney(activeId,money));
			}
		ActiveR activeR=activeDataService.getData(activeId);
		 double money=0;
		if (userSource!=null&&userSource!="") {
			List<UserR> list=activeDataService.getUserR(activeId,userSource);
			if (list!=null) {                        
				for (UserR userR : list) {
					String userId=userR.getUserId();
					double investMoneyByUser=activeDataService.getActiveDataService(userId);
					money+=investMoneyByUser;
					
				}
			}
		} 
		
		if(pageInfo.getTotalRecord()==0){
		 	String error="没有可以查询的有效数据";
			model.addAttribute("error", error);
		} 
			
		activeR.setInvestMoneyByUser(money);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("activeId", activeId);
		model.addAttribute("userSource", userSource);
		model.addAttribute("activeR", activeR);     
		return "activeData";
	}	
}
