package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import util.Log;
import util.MyCollectionUtils;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.lostcustomer.model.LostCustomer;
import com.duanrong.business.lostcustomer.service.LostCustomerService;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.udesk.service.UdeskService;

@Controller
public class LostCustomerController extends BaseController{
	
	@Autowired
	LostCustomerService lostCustomerService ;
	
	@Autowired
	UdeskService udeskService;
	
	@Resource
	Log log ;
	
	/**
	 * 获取备注
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/lostCustomer/tormark")
	public void tormark(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String evaluation = request.getParameter("evaluation");
		String userId = request.getParameter("userId");
		Map< String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("evaluation", evaluation);
		response.setContentType("text/html;charset=utf-8");
		try {
			lostCustomerService.updateCustomer(params);
		} catch (Exception e) {
			response.getWriter().print("fail");
			e.printStackTrace();
		}
		if (evaluation.equals("0")) {
			response.getWriter().print("待评定");
		}
		if (evaluation.equals("1")) {
			response.getWriter().print("无效激活");
		}
		if (evaluation.equals("2")) {
			response.getWriter().print("有效激活");
		}
	}
	/**
	 * 获取流失预警客户列表
	 * @param request
	 * @param response
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/lostCustomer/lostCustomerList/{type}")
	public String getLostCustomerView(HttpServletRequest request,
				HttpServletResponse response, Model model,@PathVariable String type) {
			try {

				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}

				String param = request.getParameter("userId");
				String userId = "";
				if (param != null) {
					userId = this.getUserId(param.trim());
				} else {
					userId = this.getUserId(param);
				}

				String str = "";
				String realname = request.getParameter("realname");
				String mobileNumber = request.getParameter("mobileNumber");
				String referrer = request.getParameter("referrer");
				String usertype = request.getParameter("usertype");
				String minTime = request.getParameter("minTime");
				String maxTime = request.getParameter("maxTime");
				String maxLostDay = request.getParameter("maxDay");
				String minLostDay = request.getParameter("minDay");
				String lostStatus = request.getParameter("lostStatus");
				String oreferrerStatus = request.getParameter("oreferrerStatus");
				String customerService = request.getParameter("customerService");
				String evaluation = request.getParameter("evaluation");
				String name = "";
				
				Map<String, Object> params = new HashMap<String, Object>();
							
				if (StringUtils.isNotBlank(userId)) {
					str += "&userId=" + userId;
					params.put("userId", userId);
				}
			
				if (StringUtils.isNotBlank(mobileNumber)) {
					str += "&mobileNumber=" + mobileNumber;
					params.put("mobileNumber", mobileNumber);
				}

				if (StringUtils.isNotBlank(lostStatus)) {
					str += "&lostStatus=" + lostStatus;
					params.put("lostStatus", lostStatus);
				}
				
				if (StringUtils.isNotBlank(minLostDay)) {
					str += "&minDay=" + minLostDay;
					params.put("minLostDay", minLostDay);
				}
				if (StringUtils.isNotBlank(maxLostDay)) {
					str += "&maxDay=" + maxLostDay;
					params.put("maxLostDay", maxLostDay);
				}
				if (StringUtils.isNotBlank(realname)) {
					name = java.net.URLDecoder.decode(realname, "UTF-8");
					params.put("realname", realname);
					str += "&realname=" + realname;
				}
				
				if (StringUtils.isNotBlank(referrer)) {
					referrer = java.net.URLDecoder.decode(referrer, "UTF-8");
					params.put("referrer", referrer);
					str += "&referrer=" + referrer;
				}
				
				if (StringUtils.isNotBlank(usertype)) {
					if(usertype.equals("enterprise") || usertype.equals("user")){}
					params.put("usertype", usertype);
					str += "&usertype=" + usertype;
				}
				if (StringUtils.isNotBlank(minTime)) {
					params.put("minTime", minTime);
					str += "&minTime=" + minTime;
				}

				if (StringUtils.isNotBlank(maxTime)) {
					params.put("maxTime", maxTime);
					str += "&maxTime=" + maxTime;
				}
				
				if (StringUtils.isNotBlank(evaluation)) {
					params.put("evaluation", evaluation);
					str += "&evaluation=" + evaluation;
				}
				
				if (StringUtils.isNotBlank(customerService)) {
					customerService = java.net.URLDecoder.decode(customerService, "UTF-8");
					params.put("customerService", customerService);
					str += "&customerService=" + customerService;
				}
				if (StringUtils.isNotBlank(oreferrerStatus)) {
					params.put("oreferrerStatus", oreferrerStatus);
					str += "&oreferrerStatus=" + oreferrerStatus;
				}
				PageInfo<LostCustomer> pageInfo = lostCustomerService.readPageInfo(
						Integer.parseInt(pageNo), 30, params);
				/*PageInfo<LostCustomer> readUserTotalInvest = lostCustomerService.readUserTotalInvest(Integer.parseInt(pageNo), 30,params);
				List<LostCustomer> list1 = readUserTotalInvest.getResults();*/
				PageInfo<LostCustomer> readUserTotalInvest1 = lostCustomerService.readUserTotalInvest1(Integer.parseInt(pageNo), 30,params);
				List<LostCustomer> list2 = readUserTotalInvest1.getResults();
				PageInfo<LostCustomer> readUserTotalDemand1 = lostCustomerService.readUserTotalDemand1(Integer.parseInt(pageNo), 30,params);
				List<LostCustomer> list3 = readUserTotalDemand1.getResults();
				/*LostCustomer lostCustomer1 = new LostCustomer();*/
				LostCustomer lostCustomer2 = new LostCustomer();
				LostCustomer lostCustomer3 = new LostCustomer();
				/*if (MyCollectionUtils.isNotBlank(list1)) {
					lostCustomer1 = list1.get(0);
					if (lostCustomer1 != null) {
						if (lostCustomer1.getTotalInvest() == null) {
							lostCustomer1.setTotalInvest(0D);
						}
					} else {
						lostCustomer1 = new LostCustomer();
					}
				}*/
				if (MyCollectionUtils.isNotBlank(list2)) {
					
					lostCustomer2 = list2.get(0);
					if (lostCustomer2 != null) {
						if (lostCustomer2.getTotalInvest1() == null) {
							lostCustomer2.setTotalInvest1(0D);
						}
					} else {
						lostCustomer2 = new LostCustomer();
					}
				}
				if (MyCollectionUtils.isNotBlank(list3)) {
					
					lostCustomer3 = list3.get(0);
					if (lostCustomer3 != null) {
						if (lostCustomer3.getTotalDemand1() == null) {
							lostCustomer3.setTotalDemand1(0D);
						}
					} else {
						lostCustomer3 = new LostCustomer();
					}
				}
				
				model.addAttribute("url", "/lostCustomer/lostCustomerList/" + type);
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("minTime", minTime);
				model.addAttribute("maxTime", maxTime);
				model.addAttribute("mobileNumber", mobileNumber);
				model.addAttribute("lostStatus", lostStatus);
				model.addAttribute("realname", realname);
				model.addAttribute("oreferrerStatus", oreferrerStatus);
				model.addAttribute("maxDay", maxLostDay);
				model.addAttribute("minDay", minLostDay);
				model.addAttribute("userId", param);
				model.addAttribute("referrer", referrer);
				model.addAttribute("str", str);
				model.addAttribute("customerService", customerService);
				model.addAttribute("evaluation", evaluation);
				model.addAttribute("usertype", usertype);
				model.addAttribute("type", type);
				/*model.addAttribute("lostCustomer1",lostCustomer1);*/
				model.addAttribute("lostCustomer2",lostCustomer2);
				model.addAttribute("lostCustomer3",lostCustomer3);
				
				return "lostCustomer/lostCustomerList";
			} catch (Exception e) {
				e.printStackTrace();
				log.errLog(
						"com.duanrong.newadmin.controller.LostCustomerController.lostCustomerList()",
						e);
			}
			return null;
		
	}
	/**
	 * 导出excle
	 * @param response
	 * @param request
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/lostCustomer/lostCustomerExport")
	public void userExport(HttpServletResponse response,
			HttpServletRequest request, Model model)
			throws IOException {
		String str = "";
		String userId = request.getParameter("userId");
		String realname = request.getParameter("realname");
		String mobileNumber = request.getParameter("mobileNumber");
		String referrer = request.getParameter("referrer");
		String usertype = request.getParameter("usertype");
		String minTime = request.getParameter("minTime");
		String maxTime = request.getParameter("maxTime");
		String maxLostDay = request.getParameter("maxDay");
		String minLostDay = request.getParameter("minDay");
		String lostStatus = request.getParameter("lostStatus");
		String oreferrerStatus = request.getParameter("oreferrerStatus");
		String customerService = request.getParameter("customerService");
		String evaluation = request.getParameter("evaluation");
		String name = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(userId)) {
			str += "&userId=" + userId;
			params.put("userId", userId);
		}
	
		if (StringUtils.isNotBlank(mobileNumber)) {
			str += "&mobileNumber=" + mobileNumber;
			params.put("mobileNumber", mobileNumber);
		}
		

		if (StringUtils.isNotBlank(lostStatus)) {
			str += "&lostStatus=" + lostStatus;
			params.put("lostStatus", lostStatus);
		}
		
		if (StringUtils.isNotBlank(minLostDay)) {
			str += "&minLostDay=" + minLostDay;
			params.put("minLostDay", minLostDay);
		}
		if (StringUtils.isNotBlank(maxLostDay)) {
			str += "&maxLostDay=" + maxLostDay;
			params.put("maxLostDay", maxLostDay);
		}
		if (StringUtils.isNotBlank(realname)) {
			name = java.net.URLDecoder.decode(realname, "UTF-8");
			params.put("realname", realname);
			str += "&realname=" + realname;

		}
		
		if (StringUtils.isNotBlank(referrer)) {
			params.put("referrer", referrer);
			str += "&referrer=" + referrer;
		}
		
		if (StringUtils.isNotBlank(usertype)) {
			if(usertype.equals("enterprise") || usertype.equals("user")){}
			params.put("usertype", usertype);
			str += "&usertype=" + usertype;
		}
		if (StringUtils.isNotBlank(minTime)) {
			params.put("minTime", minTime);
			str += "&minTime=" + minTime;
		}

		if (StringUtils.isNotBlank(maxTime)) {
			params.put("maxTime", maxTime);
			str += "&maxTime=" + maxTime;
		}
		if (StringUtils.isNotBlank(oreferrerStatus)) {
			params.put("oreferrerStatus", oreferrerStatus);
			str += "&oreferrerStatus=" + oreferrerStatus;
		}
		if (StringUtils.isNotBlank(customerService)) {
			params.put("customerService", customerService);
			str +="&customerService=" + customerService;
		}
		if (StringUtils.isNotBlank(evaluation)) {
			params.put("evaluation", evaluation);
			str +="&evaluation=" + evaluation;
		}
		
		params.put("notmobile", "notmobile");
		String t = "";
		

		try {
			List<LostCustomer> lostCustomers = lostCustomerService.readExportList(params);
			Map<String, String> title = new LinkedHashMap<>();
			title.put("realname", "姓名");
			title.put("userSource", "来源");
			title.put("mobileNumber", "手机号");
			title.put("registerTime", " 注册时间");
			title.put("remark", "备注信息");
			title.put("remarkTime", "最后一次备注时间");
			title.put("customerService", "备注人");

			int[] style = { 15, 15, 15, 20, 50, 20, 15};
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = t + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			excelConvertor.excelExport(lostCustomers, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 同步到Udesk
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/lostCustomer/synchronizeUdesk")
	public void synchronizeUdesk(HttpServletRequest request,
			HttpServletResponse response, Model model) {
	
	try {							
				String param = request.getParameter("userId");
				/*String userId = getUserId(mobileNumber);*/
				String realname = request.getParameter("realname");
				String minTime = request.getParameter("minTime");
				String maxTime = request.getParameter("maxTime");
				String usertype=request.getParameter("usertype");
				
				String referrer = request.getParameter("referrer");
				String maxLostDay = request.getParameter("maxDay");
				String minLostDay = request.getParameter("minDay");
				String lostStatus = request.getParameter("lostStatus");
				String oreferrerStatus = request.getParameter("oreferrerStatus");
				String customerService = request.getParameter("customerService");
				String evaluation = request.getParameter("evaluation");
				String name = "";
				String userId = "";
				if (param != null) {
					userId = this.getUserId(param.trim());
				} else {
					userId = this.getUserId(param);
				}
				Map<String, Object> params = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(usertype) && usertype.equals("duanrongw")) {					
					params.put("duanrongw", usertype);
				}
				if (StringUtils.isNotBlank(usertype) && usertype.equals("furen")) {
					params.put("furen", usertype);
				}
				if (StringUtils.isNotBlank(userId)) {
					params.put("userId", userId);
				}
				if (StringUtils.isNotBlank(usertype)&& !usertype.equals("furen") && !usertype.equals("duanrongw")) {
					params.put("usertype", usertype);
				}
				if (StringUtils.isNotBlank(realname)) {
					name = URLDecoder.decode(realname, "UTF-8");
					params.put("realname", name);
				}
				if (StringUtils.isNotBlank(minTime)) {
					minTime=URLDecoder.decode(minTime);
					params.put("minTime", minTime);
				}
				if (StringUtils.isNotBlank(maxTime)) {
					maxTime=URLDecoder.decode(maxTime);
					params.put("maxTime", maxTime);					
				}				
				if (StringUtils.isNotBlank(lostStatus)) {
					params.put("lostStatus", lostStatus);
				}
				if (StringUtils.isNotBlank(minLostDay)) {
					params.put("minLostDay", minLostDay);
				}
				if (StringUtils.isNotBlank(maxLostDay)) {
					params.put("maxLostDay", maxLostDay);
				}
				if (StringUtils.isNotBlank(referrer)) {
					params.put("referrer", referrer);
				}
				if (StringUtils.isNotBlank(oreferrerStatus)) {
					params.put("oreferrerStatus", oreferrerStatus);
				}
				if (StringUtils.isNotBlank(customerService)) {
					params.put("customerService", customerService);
				}
				if (StringUtils.isNotBlank(evaluation)) {
					params.put("evaluation", evaluation);
				}
				
		        List<LostCustomer> lostCustomers = lostCustomerService.readLostCustomers(params);
				JSONObject userpParam = new JSONObject();		
				JSONObject customerParams = new JSONObject();
				for (LostCustomer lostCustomer : lostCustomers) {
						//把查询的用户信息插入udesk系统里面
				 if(StringUtils.isNotBlank(lostCustomer.getMobileNumber())){
					userpParam.put("email", lostCustomer.getEmail());
					if(StringUtils.isBlank(lostCustomer.getRealname())){
						userpParam.put("nick_name", lostCustomer.getMobileNumber());	
					}else{
						userpParam.put("nick_name", lostCustomer.getRealname());
					}
					
					 userpParam.put("cellphone",  lostCustomer.getMobileNumber());
					 userpParam.put("qq", lostCustomer.getQq());
					 userpParam.put("weixin_id", lostCustomer.getWeixin());
		             String cousname=userService.readUdeskField("姓名");
					 String idcard=userService.readUdeskField("身份证号");
					 String mobileNum=userService.readUdeskField("手机号");
					 String userType=userService.readUdeskField("用户类型");
					 String QQ=userService.readUdeskField("QQ");
					 String regiterTime=userService.readUdeskField("注册时间");
					 String remark=userService.readUdeskField("备注");
					 String offer=userService.readUdeskField("推荐人");
					 String manageroffer=userService.readUdeskField("经理推荐人");	
					 String resource=userService.readUdeskField("来源");
					 if(StringUtils.isNotBlank(cousname)){
				     customerParams.put(cousname, lostCustomer.getRealname());
				     }
					 if(StringUtils.isNotBlank(idcard)){
					     customerParams.put(idcard, lostCustomer.getIdCard());
					 }
					 if(StringUtils.isNotBlank(mobileNum)){
					     customerParams.put(mobileNum, lostCustomer.getMobileNumber());
					}
					if(StringUtils.isNotBlank(QQ)){
					     customerParams.put(QQ, lostCustomer.getQq());
					}
					if(StringUtils.isNotBlank(regiterTime)){
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");						
					    customerParams.put(regiterTime, df.format(lostCustomer.getRegisterTime()));
					}
					if(StringUtils.isNotBlank(offer)){
					     customerParams.put(offer, lostCustomer.getReferrer());
					}
					if(StringUtils.isNotBlank(manageroffer)){
					     customerParams.put(manageroffer, lostCustomer.getOreferrer());
					}
					if(StringUtils.isNotBlank(resource)){
					     customerParams.put(resource, lostCustomer.getUserOtherInfo().getUserSource());
					}
					if(StringUtils.isNotBlank(remark)){
					     customerParams.put(remark, "");
					}
					if(StringUtils.isNotBlank(userType)){
					     customerParams.put(userType,lostCustomer.getUserType());
					}
					udeskService.udeskCustomerImport(customerParams, userpParam);	
				 }
				}
				response.getWriter().print("数据同步成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.nouserList()",
					e);
			try {
				response.getWriter().print("数据同步失败！");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	
	}

}
