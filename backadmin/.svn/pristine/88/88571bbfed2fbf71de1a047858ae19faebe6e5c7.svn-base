package com.duanrong.newadmin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import base.pagehelper.PageInfo;

import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.paymentInstitution.model.CompanyYeepayTransferUserYeepay;
import com.duanrong.business.paymentInstitution.model.PaymentAdvancefund;
import com.duanrong.business.paymentInstitution.model.PaymentAdvancefundRecord;
import com.duanrong.business.paymentInstitution.model.PaymentCompany;
import com.duanrong.business.paymentInstitution.model.PaymentRechargeRecord;
import com.duanrong.business.paymentInstitution.model.PaymentUserRelation;
import com.duanrong.business.paymentInstitution.service.PaymentCompanyService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.IdUtil;
import com.duanrong.payment.service.PaymentService;


/**
 * 第三方支付管理
 * @author bj
 *
 */
@Controller
public class PaymentCompanyController extends BaseController {

	/**
	 * 第三方支付机构
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	
	@Resource
	 private PaymentCompanyService paymentCompanyService;
	
	@Resource
	PaymentService paymentService;
	
	
	/*@RequestMapping(value="payMent/paymentInstitution")
	public String paymentInstitutionInfo(HttpServletResponse response,
			HttpServletRequest request,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		String paymentCompanyName=request.getParameter("paymentCompanyName");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		Map<String,Object>params=new HashMap<String, Object>();
		String str="";
		if (paymentCompanyName != null && !paymentCompanyName.equals("")) {
			str += "&paymentCompanyName=" + paymentCompanyName;
			params.put("paymentCompanyName",paymentCompanyName );
		}
		if (startTime != null && !startTime.equals("")) {
			str += "&startTime=" + startTime;
			params.put("startTime",startTime );
		}
		
		if (endTime != null && !endTime.equals("")) {
			str += "&endTime=" + endTime;
			params.put("endTime",endTime+" 23:59");
		}
		
			PageInfo<PaymentCompany> pageInfo =paymentCompanyService.readPaymentInstitutionInfo(Integer.parseInt(pageNo),
					Integer.parseInt(pageSize), params);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("paymentCompanyName",paymentCompanyName);
			model.addAttribute("startTime",startTime);
			model.addAttribute("str", str);
			model.addAttribute("endTime", endTime);
		
		
			return "paymentInstitution/paymentInstitution";
	}*/
	/**
	 *  第三方支付流水查询
	 */
	@RequestMapping(value="payMent/payMentBill")
	public String payMentBill(HttpServletResponse response,
			HttpServletRequest request,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		String userId=request.getParameter("userId");
		String markId=request.getParameter("markId");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String status=request.getParameter("status");
		String payMentSelect=request.getParameter("payMentSelect");
		Map<String,Object>params=new HashMap<String, Object>();
		String str="";
		if (userId != null && !userId.equals("")) {
			str += "&userId=" + userId;
			params.put("userId",getUserId(userId) );
		}
		if (markId != null && !markId.equals("")) {
			str += "&markId=" + markId;
			params.put("markId",markId );
		}
		if (startTime != null && !startTime.equals("")) {
			str += "&startTime=" + startTime;
			params.put("startTime",startTime );
		}
		if (endTime != null && !endTime.equals("")) {
			str += "&endTime=" + endTime;
			params.put("endTime",endTime+" 23:59");
		}
		if (status != null && !status.equals("")) {
			str += "&status=" + status;
			params.put("status",status );
		}
		if (payMentSelect != null && !payMentSelect.equals("")) {
			str += "&payMentSelect=" + payMentSelect;
			params.put("payMentSelect",payMentSelect );
		}
		PageInfo<PaymentRechargeRecord> pageInfo =paymentCompanyService.readPaymentRechargeRecordInfo(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		
		List<PayMentChannel> payMentList=paymentCompanyService.readPayMent();
		
		model.addAttribute("payMentList", payMentList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("userId",userId);
		model.addAttribute("startTime",startTime);
		model.addAttribute("markId",markId);
		model.addAttribute("status",status);
		model.addAttribute("str", str);
		model.addAttribute("endTime", endTime);
		model.addAttribute("payMentSelect", payMentSelect);
		return "paymentInstitution/payMentBill";
	}
	/**
	 * 平台易宝给个人易宝转账记录
	 */
	
	
	@RequestMapping(value="payMent/CompanyYeepayTransferUserYeepay")
	public String CompanyYeepayTransferUserYeepay(HttpServletResponse response,
			HttpServletRequest request,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		String userId=request.getParameter("userId");
		String markId=request.getParameter("markId");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String payMentSelect=request.getParameter("payMentSelect");
		
		Map<String,Object>params=new HashMap<String, Object>();
		String str="";
		if (userId != null && !userId.equals("")) {
			str += "&userId=" + userId;
			params.put("userId",getUserId(userId) );
		}
		if (markId != null && !markId.equals("")) {
			str += "&markId=" + markId;
			params.put("markId",markId );
		}
		if (startTime != null && !startTime.equals("")) {
			str += "&startTime=" + startTime;
			params.put("startTime",startTime );
		}
		if (endTime != null && !endTime.equals("")) {
			str += "&endTime=" + endTime;
			params.put("endTime",endTime+" 23:59" );
		}
		if (payMentSelect != null && !payMentSelect.equals("")) {
			str += "&payMentSelect=" + payMentSelect;
			params.put("payMentSelect",payMentSelect );
		}
		PageInfo<CompanyYeepayTransferUserYeepay> pageInfo =paymentCompanyService.readCompanyYeepayTransferUserYeepay(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		
		List<PayMentChannel> payMentList=paymentCompanyService.readPayMent();
		
		model.addAttribute("payMentList", payMentList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("userId",userId);
		model.addAttribute("markId",markId);
		model.addAttribute("startTime",startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("payMentSelect", payMentSelect);
		model.addAttribute("str", str);
		
		return "paymentInstitution/companyYeepayTransferUserYeepay";
	}
	
	/**
	 * 新增用户通道令牌
	 */
	@RequestMapping(value="/payMent/UserPayment")
	public String UserPayment(HttpServletResponse response,
			HttpServletRequest request,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		String userId=request.getParameter("userId");
		
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		Map<String,Object>params=new HashMap<String, Object>();
		String str="";
		if (userId != null && !userId.equals("")) {
			str += "&userId=" + userId;
			params.put("userId",getUserId(userId));
		}
		if (startTime != null && !startTime.equals("")) {
			str += "&startTime=" + startTime;
			params.put("startTime",startTime );
		}
		if (endTime != null && !endTime.equals("")) {
			str += "&endTime=" + endTime;
			params.put("endTime",endTime+" 23:59" );
		}
		PageInfo<PaymentUserRelation> pageInfo =paymentCompanyService.readUserPayment(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("userId",userId);
		model.addAttribute("startTime",startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("str", str);
		return "paymentInstitution/userPayment";
	}
	
	/**
	 * 垫付资金记录表
	 */
	@RequestMapping(value="/payMent/advancefundRecord")
	public String advancefundRecord(HttpServletResponse response,
			HttpServletRequest request,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String type=request.getParameter("type");
		
		Map<String,Object>params=new HashMap<String, Object>();
		String str="";
		if (startTime != null && !startTime.equals("")) {
			str += "&startTime=" + startTime;
			params.put("startTime",startTime );
		}
		if (endTime != null && !endTime.equals("")) {
			str += "&endTime=" + endTime;
			params.put("endTime",endTime+" 23:59" );
		}
		if (type != null && !type.equals("")) {
			str += "&type=" + type;
			params.put("type",type );
		}
		PageInfo<PaymentAdvancefundRecord> pageInfo =paymentCompanyService.readAdvancefundRecord(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		PaymentAdvancefund paymentAdvancefund=paymentCompanyService.readPaymentAdvancefund();
		model.addAttribute("warnMoney", paymentAdvancefund.getWarnMoney());
		model.addAttribute("money",paymentAdvancefund.getMoney());
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("type",type);
		model.addAttribute("startTime",startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("str", str);
		return "paymentInstitution/advancefundRecord";
		
	}
	@RequestMapping(value="payMent/toUpdateAdvancefundMoney")
	public String toUpdateAdvancefundMoney(){
		return "paymentInstitution/createAdvancefundRecord";
		
	}
	@ResponseBody
	@RequestMapping(value="payMent/updateAdvancefundMoney")
	public String updateAdvancefundMoney(HttpServletResponse response,
			HttpServletRequest request,Model model){
		String money=request.getParameter("money");
		String paymentId=request.getParameter("paymentId");
		UserCookie loginUser = GetLoginUser(request, response);
		PaymentAdvancefund paymentAdvancefund=paymentCompanyService.readPaymentAdvancefund();
		String type=request.getParameter("type");
		double oldMoney= paymentAdvancefund.getMoney();
		double newMoney=0;
		if(type.equals("in")){
			newMoney=oldMoney+Double.parseDouble(money);	
		}else {
			newMoney=oldMoney;
		}
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("id", IdUtil.randomUUID());
		params.put("creater", loginUser.getUserId());
		params.put("type", type);
		params.put("money", money);
		params.put("paymentId", paymentId);
		params.put("createTime",new Date());
		//先更新 金额 在添加记录
		Map<String,Object>moneyMap=new HashMap<String, Object>();
		moneyMap.put("newMoney", newMoney);
		moneyMap.put("id",paymentAdvancefund.getId());
		int flag=paymentCompanyService.updateAdvancefundByNewMoney(moneyMap);
		int flag2=0;
		if(flag==1){
			flag2=paymentCompanyService.insertAdvancefundRecord(params);
		}
		if(flag==1&&flag2==1){
			return "success";
		}else{
			return "error";
		}
				
	}
	/**
	 *  更新 警告阀值
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="payMent/updateAdvancefundUpdateWarnMoney")
	public String updateAdvancefundUpdateWarnMoney(HttpServletResponse response,
			HttpServletRequest request,Model model){
		String warnMoney=request.getParameter("warnMoney");
		int flag=paymentCompanyService.updateAdvancefundUpdateWarnMoney(Double.parseDouble(warnMoney));
		if(flag==1){
			return "success";
		}else{
			return "error";
		}
	}
	
	
	/**
	 *  更新 警告阀值
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="payMent/supplementOrder")
	public String supplementOrder(HttpServletResponse response,HttpServletRequest request,Model model){
		try {
			String orderId = request.getParameter("orderId");
			String transferWay = request.getParameter("transferWay");
			return paymentService.supplementOrder(orderId,transferWay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	
}
