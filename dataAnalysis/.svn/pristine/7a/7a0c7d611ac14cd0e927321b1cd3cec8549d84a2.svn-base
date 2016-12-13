package com.duanrong.dataAnalysis.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.redPackage.service.RedPackageService;
import com.duanrong.dataAnalysis.business.redPackage.modle.RedPackage;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;
import com.duanrong.dataAnalysis.util.HtmlRegexpUtil;
/**
 * 现金红包列表页面
 * @author bj
 *
 */

@Controller
public class redPackageController extends BaseController {

	@Resource
	private RedPackageService redPackageService;
	@RequestMapping("/showRedPackage")
	public String redPackage(
			 @RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			 @RequestParam(value="pageSize",defaultValue="20")Integer pageSize,
			HttpServletRequest request,HttpServletResponse response,Model model){
		/*User user=UserCookieHelper.GetUserCookie(request, response);
		model.addAttribute("user", user);*/
		String type="money";
		PageInfo<RedPackage> pageInfo = redPackageService.pageLite4Map(pageNo, pageSize, type);
		List<RedPackage> redPackageList=pageInfo.getResults();
		for (RedPackage redPackage : redPackageList) {
			String id=redPackage.getId();
			//红包个数
			redPackage.setCount(redPackageService.getCount(id));
			//领取用户数
			redPackage.setByUserCount(redPackageService.getByUserCount(id));
			//使用用户数
			redPackage.setUsedUserCount(redPackageService.getUsedUserCount(id));
			//红包发放总数
			redPackage.setRedPackageSendedCount(redPackageService.getRedPackageSendedCount(id));
			//过期总数
			redPackage.setUnCount(redPackageService.getUnCount(id));
			//未用总数
			redPackage.setUnUsedCount(redPackageService.getUnUsedCount(id));
			//已用总数
			redPackage.setUsedCount(redPackageService.getUsedCount(id));
			//投入金额
			redPackage.setPayMoney(redPackageService.getPayMoney(id));
			//投资总额
			redPackage.setAllPayMoney(redPackageService.getAllPayMoney(id));
			
			String redString = redPackage.getRedPackageRule();
			redString = new HtmlRegexpUtil().replaceTag(redString);
			System.out.println(redString);
			redPackage.setRedPackageRule(redString);
		}
		model.addAttribute("pageInfo", pageInfo);
		return "redPackage";
	}
	
}
