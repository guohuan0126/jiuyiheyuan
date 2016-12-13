package com.duanrong.newadmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import util.Log;
import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.recharge.model.Recharge;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.model.AuthInfo;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.AuthInfoService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.withdraw.model.WithdrawCash;
import com.duanrong.business.withdraw.service.WithdrawCashService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.FormUtil;

/**
 * 首页C层
 * 
 * @author wangjing
 * @date 2014-11-19下午2:49:41
 */
@Controller
public class SystemController extends BaseController {

	@Resource
	OperaRecordService operaRecordService;
	@Resource
	AuthInfoService authInfoService;
	@Resource
	WithdrawCashService withdrawCashService;
	@Resource
	RechargeService rechargeService;
	@Resource
	UserService userService;
	@Resource
	SmsService smsService;
	@Resource
	InvestService investService;
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	@Resource
	Log log;
	
	@RequestMapping(value = "/sys/recordList")
	public String recordList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String userId = request.getParameter("userId");
				String title = request.getParameter("title");
				model.addAttribute("url", "/sys/recordList");
				String name="";
				String pageNo = request.getParameter("pageNo");
				if(MyStringUtils.isAnyBlank(pageNo)){
					pageNo = "1";
				}
				Map<String, Object> params = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(request.getParameter("title"))) {
					name = java.net.URLDecoder.decode(request.getParameter("title"), "UTF-8");
				}
				params.put("userId", userId);
				params.put("title", title);
				PageInfo pageInfo= operaRecordService.readPageInfo(Integer.parseInt(pageNo), 10, FormUtil.getForParamToBean(params));
				request.setAttribute("str", FormUtil.getForParam(params));
				request.setAttribute("pageInfo", pageInfo);
				request.setAttribute("title", name);
				request.setAttribute("userId", userId);
				return "sys/recordList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					" com.duanrong.newadmin.controller.FoundController.accountList()",
					e);

		}
		return null;
	}
	@RequestMapping(value = "/sys/dataList/{type}")
	public String dataList(HttpServletRequest request,
			HttpServletResponse response, Model model,@PathVariable String type) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				HashMap<String,Object> map=new HashMap<String,Object>();
				map.put("type", type);
				String start = request.getParameter("start");
				String end = request.getParameter("end");
				if(start!=null && !"".equals(start)){
					map.put("start", start);
				}
				if(end!=null && !"".equals(end)){
					String endtime=DateUtil.addDay(end, 1);
					map.put("end", endtime);
				}
				List<AuthInfo> authInfo=authInfoService.readAuthNum(map);
				model.addAttribute("authInfo", authInfo);
				List<Sms> sms=smsService.readSmsNum(map);
				
				model.addAttribute("sms", sms);
				List<WithdrawCash> withdrawCash=withdrawCashService.readCashNum(map);
				model.addAttribute("withdrawCash", withdrawCash);
				List<Recharge> recharge=rechargeService.readRechargeNum(map);
				model.addAttribute("recharge", recharge);
				List<User> user=userService.readUserNum(map);
				model.addAttribute("user", user);
				List<Invest> invest=investService.readInvestNum(map);
				model.addAttribute("invest", invest);
				int smsNum=0;int wNum=0; int rNum=0;int uNum=0;int iNum=0;int aNum=0;
				for(Sms s:sms){
					if(s.getNum()>smsNum){
						smsNum=s.getNum();
					}
				}
				for(AuthInfo s:authInfo){
					if(s.getNum()>aNum){
						aNum=s.getNum();
					}
				}
				for(WithdrawCash s:withdrawCash){
					if(s.getNum()>wNum){
						wNum=s.getNum();
					}
				}
				for(Recharge s:recharge){
					if(s.getNum()>rNum){
						rNum=s.getNum();
					}
				}
				for(User s:user){
					if(s.getNum()>uNum){
						uNum=s.getNum();
					}
				}
				for(Invest s:invest){
					if(s.getNum()>iNum){
						iNum=s.getNum();
					}
				}
				model.addAttribute("smsNum", smsNum);
				model.addAttribute("wNum", wNum);
				model.addAttribute("rNum", rNum);
				model.addAttribute("uNum", uNum);
				model.addAttribute("iNum", iNum);
				model.addAttribute("aNum", aNum);
				model.addAttribute("start", start);
				model.addAttribute("end", end);
				return "sys/dataList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					" com.duanrong.newadmin.controller.SystemController.dataList()",
					e);

		}
		return null;
	}
	
	/**
	 * 
	 * @description 易宝操作记录列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sys/toList")
	public String toList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				String str = "";
				String type = request.getParameter("type");
				String start = request.getParameter("start");
				String end = request.getParameter("end");
				String id=request.getParameter("userId");
				String userId = getUserId(id);
				String name = "";
				Map<String, Object> params = new HashMap<String, Object>();
				
				if (StringUtils.isNotBlank(userId)) {
					str += "&userId=" + userId;
					params.put("userId", userId);
				}
				if (StringUtils.isNotBlank(type)) {
					str += "&type=" + type;
					params.put("type", type);
				}
				
				if (StringUtils.isNotBlank(start)) {
					params.put("start", start);
					str += "&start=" + start;
				}
				if (StringUtils.isNotBlank(end)) {
					String endtime=DateUtil.addDay(end, 1);
					params.put("end", endtime);
					str += "&end=" + end;
				}
				PageInfo pageInfo = trusteeshipOperationService.readPageInfo(
						Integer.parseInt(pageNo), 10, params);
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("start", start);
				model.addAttribute("end", end);
				model.addAttribute("type", type);
				model.addAttribute("userId", id);
				model.addAttribute("str", str);
				return "sys/toList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					" com.duanrong.newadmin.controller.SystemController.toList()",
					e);
		}
		return null;
	}

	
	/**
	 * 
	 * @description 易宝操作记录列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sys/ocs/toRemoveOcs")
	public String toRemoveOcs(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "sys/toRemoveOcs";
	
	}
	
	/**
	 * 
	 * @description 易宝操作记录列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sys/ocs/removeOcs")
	public String removeOcs(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//OcsUtils.deleteExclusiveHostName(request.getParameter("key"));
		return "sys/toRemoveOcs";
	
	}
}
