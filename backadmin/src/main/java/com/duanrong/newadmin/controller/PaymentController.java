package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.paymentInstitution.model.PaymentRechargeRecord;
import com.duanrong.business.paymentInstitution.service.PaymentCompanyService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.withdraw.model.PaymentWithdrawRecord;
import com.duanrong.business.withdraw.service.PaymentWithdrawRecordService;
import com.duanrong.newadmin.utility.FormUtil;
import com.duanrong.payment.model.ReapTransContent;
import com.duanrong.payment.service.PaymentWithdrawCashService;
import com.duanrong.util.xml.JaxbXmlUtil;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : SunZ
 * @CreateTime : 2015-4-2 下午2:16:27
 * @Description : NewAdmin com.duanrong.newadmin.controller
 *              YeepayNotifyController.java
 * 
 */
@Controller
public class PaymentController extends BaseController {

	@Resource
	PaymentWithdrawCashService paymentWithdrawCashService;
	
	@Resource
	PaymentWithdrawRecordService paymentWithdrawRecordService;
	
	@Resource
	PaymentCompanyService paymentCompanyService;
	
	@Resource
	UserService userService;
	
	@Resource
	com.duanrong.business.withdraw.service.PaymentWithdrawRecordService withdrawRecordService;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@ResponseBody
	@RequestMapping(value="payMent/verifyWithdrawCash")
	public String verifyWithdrawCash(HttpServletResponse response,HttpServletRequest request,Model model){
		return paymentWithdrawCashService.verifyWithdrawCash();
	}
	
	@ResponseBody
	@RequestMapping(value="payMent/confirmWithdrawCash")
	public String confirmWithdrawCash(HttpServletResponse response,HttpServletRequest request,Model model){
			
		return paymentWithdrawCashService.confirmWithdrawCash(request.getParameter("id"),request.getParameter("drepayType"));
	}
	

	@RequestMapping(value="payMent/queryInfo")
	public String queryInfo(HttpServletResponse response,HttpServletRequest request,Model model){
		return "paymentInstitution/tradeQuery";
	}
	
	@RequestMapping(value="/payMent/queryFuiouInfo")
	public String queryFuiouInfo(HttpServletResponse response,HttpServletRequest request,Model model){
		return "paymentInstitution/tradeFuiouQuery";
	}
	
	@RequestMapping(value="payMent/tradeFuiouQuery")
	public String tradeFuiouQuery(HttpServletResponse response,HttpServletRequest request,Model model){
		String orderId = request.getParameter("orderId");
		if(StringUtils.isNotBlank(orderId)){
			String fuiouOrderId=paymentCompanyService.readFuiouOrderId(orderId);
			PaymentRechargeRecord record = paymentCompanyService.readPaymentRechargeRecord(orderId, orderId, "Fuiou");
			if(record!=null&&fuiouOrderId!=null){
				String userId = record.getUserId();
				User user = userService.read(userId);
				Map<String,String> map = paymentWithdrawCashService.tradeFuiouQuery(fuiouOrderId);
				model.addAttribute("map",map);
				model.addAttribute("userId",userId);
				if(user!=null){
					model.addAttribute("realname",user.getRealname());
				}else{
					model.addAttribute("realname","未找到");
				}
				if(!"5185".equals(map.get("Rcd"))){
					model.addAttribute("message",map.get("RDesc"));
				}else {
					model.addAttribute("record",record);
					model.addAttribute("message",map.get("RDesc"));
				}
			}else{
				model.addAttribute("message","未找到数据");
			}
			model.addAttribute("orderId", orderId);
		}
		return "paymentInstitution/tradeFuiouQuery";
	}
		
	
	@RequestMapping(value="payMent/tradeQueryByChannelType")
	public String tradeQuery(HttpServletResponse response,HttpServletRequest request,Model model){
		String orderId = request.getParameter("orderId");
		if(StringUtils.isNotBlank(orderId)){
			String channelType=request.getParameter("channelType");
			if(channelType.equals("JDpay")){
				try {
					PaymentWithdrawRecord record = paymentWithdrawRecordService.read(orderId, orderId, "JDpay");
					if(record!=null){
						String userId = record.getUserId();
						User user = userService.read(userId);
						Map<String,String> map = paymentWithdrawCashService.tradeQuery(orderId);
						model.addAttribute("map",map);
						model.addAttribute("userId",userId);
						model.addAttribute("requestTime",record.getRequestTime());
						if(user!=null){
							model.addAttribute("realname",user.getRealname());
						}else{
							model.addAttribute("realname","未找到");
						}
						if(!"0000".equals(map.get("response_code"))){
							model.addAttribute("message",map.get("response_message"));
						}
					}else{
						model.addAttribute("message","未找到数据");
					}
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message","查询失败");
				}
				model.addAttribute("orderId", orderId);
				model.addAttribute("channelType", channelType);
				return "paymentInstitution/tradeQuery";
			}else if(channelType.equals("BaoFoo")){
				try {
					PaymentWithdrawRecord record = paymentWithdrawRecordService.read(orderId, orderId, "Baofoo");
					if(record!=null){
						String userId = record.getUserId();
						User user = userService.read(userId);
						model.addAttribute("userId",userId);
						model.addAttribute("requestTime",record.getRequestTime());
						ReapTransContent str2Obj = new ReapTransContent();
						String reapTransContent=paymentWithdrawCashService.queryPaymentDefrayPay(orderId);
						if(StringUtils.isNotBlank(reapTransContent)){
							str2Obj = JaxbXmlUtil.xmlToObj(reapTransContent, ReapTransContent.class);
							if(str2Obj.getTransHead().getReturnCode().equals("0000")&&str2Obj.getTransHead().getReturnMsg().equals("代付请求交易成功")){
								//金额
								model.addAttribute("money",str2Obj.getTransReqDatas().get(0).getTrans_money());
								//状态
								model.addAttribute("status",str2Obj.getTransReqDatas().get(0).getState());
								//响应时间
								model.addAttribute("responseTime", str2Obj.getTransReqDatas().get(0).getTrans_endtime());
							}else{
								model.addAttribute("message","宝付单笔查询有误");
							}
						}else {
							model.addAttribute("message","未找到数据");
						}
						if(user!=null){
							model.addAttribute("realname",user.getRealname());
						}else{
							model.addAttribute("realname","未找到");
						}
						
					}else{
						model.addAttribute("message","未找到数据");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				model.addAttribute("orderId", orderId);
				model.addAttribute("channelType", channelType);
				return "paymentInstitution/tradeQueryBaofoo";
			}
			
		}
		return null;
	}
	
	/**
	 * 
	 * @description 京东提现列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cash/cashList/jdpay")
	public String cashListJD(HttpServletRequest request,
			HttpServletResponse response, Model model, PaymentWithdrawRecord paymentWithdrawRecord) {
		try {
			int pageNo = 0;
			int pageSize = 25;
			if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			}			
			paymentWithdrawRecord = (PaymentWithdrawRecord) FormUtil.getForParamToBean(paymentWithdrawRecord);
			String user = paymentWithdrawRecord.getUserId();
			if(paymentWithdrawRecord != null && null != paymentWithdrawRecord.getUserId() 
					&& !"".equals(paymentWithdrawRecord.getUserId())){
				paymentWithdrawRecord.setUserId(getUserId(paymentWithdrawRecord.getUserId()));
			}			
			paymentWithdrawRecord.setDate(new Date());
			PageInfo<PaymentWithdrawRecord> pageInfo = paymentWithdrawRecordService.readPageLite(pageNo, pageSize, paymentWithdrawRecord);			
			request.setAttribute("record", paymentWithdrawRecord);
			request.setAttribute("url", "/cash/cashList/jdpay");
			request.setAttribute("str", FormUtil.getForParam(paymentWithdrawRecord));
			request.setAttribute("pageInfo", pageInfo);
			//request.setAttribute("record", paymentWithdrawRecord);
			request.setAttribute("user", user);
			return "found/JDcashList";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @description 京东提现列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */ 
	@RequestMapping(value = "/cash/cashList/tojdpay")
	public String cashListJD2(HttpServletRequest request) {
		try {	
			PaymentWithdrawRecord paymentWithdrawRecord = new PaymentWithdrawRecord();
			String isDefrayPay = request.getParameter("isDefrayPay");
			String status = request.getParameter("status");
			String begin = request.getParameter("begin");
			if(begin != null && !begin.equals("")){
				paymentWithdrawRecord.setBegin(format.parse(begin));
			}
			String end = request.getParameter("end");
			if(end != null && !end.equals("")){
				paymentWithdrawRecord.setEnd(format.parse(end));
			}
			paymentWithdrawRecord.setIsDefrayPay(isDefrayPay);
			paymentWithdrawRecord.setStatus(status);
			String title = "";
			if(status.equals("sended,frozen,fail") && isDefrayPay.equals("success,fail")){
				title = "京东代付失败列表";
				request.setAttribute("url", "/cash/cashList/tojdpay?status=frozen&isDefrayPay=success,fail");
			}else if(status.equals("confirm") && isDefrayPay.equals("success")){
				title = "京东代付成功列表";
				request.setAttribute("url", "/cash/cashList/tojdpay?status=confirm&isDefrayPay=success");
			}else if(status.equals("frozen") && isDefrayPay.equals("sended,undeal")){
				title = "京东代付列表";
				request.setAttribute("BFbankence", paymentWithdrawCashService.getBaoFoobalance());
				request.setAttribute("jdbanlence", paymentWithdrawCashService.getJDbalance());
				request.setAttribute("totalMoney", withdrawRecordService.readWithdrawMoneyPerDay(new PaymentWithdrawRecord()));
				request.setAttribute("total", withdrawRecordService.readWithdrawCountPerDay(null));
				paymentWithdrawRecord.setRequestTime(new Date());
			}
			PageInfo<PaymentWithdrawRecord> pageInfo = 
					paymentWithdrawRecordService.readPageLite(0, 99999, paymentWithdrawRecord);						
			request.setAttribute("pageInfo", pageInfo);		
			request.setAttribute("title", title);
			return "found/JDcashList2";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param transfer
	 */
	@RequestMapping(value = "/cash/cashList/{transfer}")
	public void transfer(HttpServletRequest request, HttpServletResponse response, @PathVariable String transfer) {
		try {
			String id = request.getParameter("id");
			PaymentWithdrawRecord record = paymentWithdrawRecordService.read(id);
			response.setCharacterEncoding("utf-8");
			if(record == null){
				response.getWriter().print("null");
				return ;
			}
			if(transfer.equals("cancle")){
				if(record.getIsDefrayPay().equals("success")){
					response.getWriter().print("代付已成功，不能退款！");
					return ;
				}
				response.getWriter().print(paymentWithdrawCashService.transferCancle(record.getMarkId()));
				return ;
			}else{
				if(record.getIsDefrayPay().equals("success")){
					response.getWriter().print(paymentWithdrawCashService.transferConfirm(record.getMarkId()));
					return ;
				}else{
					response.getWriter().print("代付未成功，不能确认！");
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}