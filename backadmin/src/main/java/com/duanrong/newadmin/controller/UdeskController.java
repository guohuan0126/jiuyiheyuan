package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.MyCollectionUtils;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.activity.model.RedPacketDetail;
import com.duanrong.business.activity.service.RedPacketDetailService;
import com.duanrong.business.demand.service.DemandTreasureBillService;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserOtherInfo;
import com.duanrong.business.user.model.UserVisitInfo;
import com.duanrong.business.user.service.UserOtherInfoService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.user.service.UserVisitInfoService;






/**
 * UdeskController
 * @author YU MIN
 * @time 2016年7月20日16:00:15
 */
@Controller
@RequestMapping(value="UdeskInter")
public class UdeskController extends BaseController{
	@Resource
	UserService userService;
	@Resource
	InvestService investService;
	@Resource
	UserOtherInfoService userOtherInfoService;
	@Resource
	UserVisitInfoService userVisitInfoService; 
	@Resource
	DemandTreasureBillService demandTreasureBillService;
	@Resource
	RedPacketDetailService redPacketDetailService;
	/**
	 * 跳转到客户组件页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getClientComponent")
	public String toClientComponent(HttpServletRequest request,
			HttpServletResponse response,Model model){
   		String mobileNum=request.getParameter("mobileNum");
   		String postUrl=request.getAttribute("url").toString();
   		String request_str=request.getAttribute("request_str").toString();
		String userId = "";
		if (mobileNum != null) {
			userId = this.getUserId(mobileNum.trim());
		} else {
			userId = this.getUserId(mobileNum);
		}
		Map<String, Object> params = new HashMap<String, Object>();						
		if (StringUtils.isNotBlank(userId)) {
			params.put("userId", userId);
		}
		params.put("notmobile", "notmobile");
		params.put("type", "r.register_time");
		params.put("ordertype", "desc");
		PageInfo<User> pageInfo = userService.readPageInfo(
				1, 30, params);
		List<User> list = userService.readUserTotalMoney(params);
		User user = new User();
		if (MyCollectionUtils.isNotBlank(list)) {
			user = list.get(0);
			if (user != null) {
				if (user.getTotalCurrMoney() == null) {
					user.setTotalCurrMoney(0D);
				}
				if (user.getTotalMoney() == null) {
					user.setTotalMoney(0D);
				}
			} else {
				user = new User();
			}
		}
		//获取用户当前天天赚投资总额
		double demandMoney=demandTreasureBillService.readUserDemandTreasure(userId);
	    Map<String,Object> investParam = new HashMap<>();
	    if (StringUtils.isNotBlank(userId)) {
		  investParam.put("userId", userId);
		}
	    String loanName=request.getParameter("loanName");
	    if (StringUtils.isNotBlank(loanName)) {
	    	investParam.put("loanName", loanName);			
		}
	    String startTime=request.getParameter("startTime");
	    String endTime=request.getParameter("endTime");
	    if (StringUtils.isNotBlank(startTime)) {
	    	investParam.put("start", startTime);			
		}
	    if (StringUtils.isNotBlank(endTime)) {
	    	investParam.put("end", endTime+" 23:59");			
		}
	    investParam.put("status", request.getParameter("status"));
		List<Invest> investList = investService.readUserInvest(investParam);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("investList", investList);
		model.addAttribute("user", user);
		model.addAttribute("loanName", loanName);
		model.addAttribute("startTime", startTime);
		model.addAttribute("postUrl", postUrl);	
		model.addAttribute("paramUrl", request_str);	
		model.addAttribute("endTime", endTime);
		model.addAttribute("userId", userId);
		model.addAttribute("demandMoney", demandMoney);
		model.addAttribute("status", request.getParameter("status"));
		return "udesk/clientComponent";
	}
	/**
	 * 查询用户备注信息
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/rmarkList")
	public void rmarkList(HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		UserVisitInfo userVisitInfo = new UserVisitInfo();
		String userid = request.getParameter("userid");
		UserOtherInfo userOtherInfo = userOtherInfoService.read(userid);
		User user = userService.read(userid);
		userVisitInfo.setUserid(userid);
		List<UserVisitInfo> list = userVisitInfoService
				.readAllRemarks(userVisitInfo);
		response.setCharacterEncoding("utf-8");
		Object[] array = new Object[3];
		array[0] = userOtherInfo;
		array[1] = list;
		array[2] = user;
		JSONArray jsonArr = JSONArray.fromObject(array);
		response.getWriter().print(jsonArr.toString());
	}
	/**添加用户备注信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/tormark")
	public void tormark(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userId= request.getParameter("userId");
		String source = request.getParameter("source");
		String p2p = request.getParameter("p2p");
		String notice = request.getParameter("notice");
		String qq = request.getParameter("qq");
		String remark = request.getParameter("remark");
		String id = request.getParameter("id");
		String weixin=request.getParameter("weixin");
		String visitType = request.getParameter("visitType");
		User user=new User();

		try {
			if(StringUtils.isNotBlank(userId)){
			user=userService.read(userId);
			}
			if(user==null){
				response.getWriter().print("userIdError");	
			}else{
			UserOtherInfo userOtherInfo = new UserOtherInfo();
			userOtherInfo.setId(id);
			userOtherInfo.setHasP2p(p2p);
			userOtherInfo.setNotice(notice);
			userOtherInfo.setVisitSource(source);
			userOtherInfoService.insertUpdate(userOtherInfo,weixin, qq, remark,visitType,
					userId);
			response.getWriter().print("ok");
			}
		} catch (Exception e) {
			response.getWriter().print("fail");
			e.printStackTrace();
		}
		
	}
	/**
	 * 删除用户备注
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/delrmark")
	public void delrmark(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		try {
			userVisitInfoService.delete(id);
		} catch (Exception e) {
			response.getWriter().print("fail");
			e.printStackTrace();
		}
		response.getWriter().print("ok");
	}
	/**
	 * 获取加息卷详细信息
	 * @return
	 */
	@RequestMapping(value="/getRedPacket")
	@ResponseBody
	public String getRedPacketDetailById(String redPacketId){
		String retStr = null;
		try {
			RedPacketDetail redPacketDetail = redPacketDetailService.read(Integer.parseInt(redPacketId));
			retStr = JSONObject.toJSONString(redPacketDetail);
		} catch (Exception e) {
		}
		return retStr;
	}
}