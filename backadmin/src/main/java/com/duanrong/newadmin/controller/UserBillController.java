package com.duanrong.newadmin.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.recharge.model.Recharge;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.withdraw.model.WithdrawCash;
import com.duanrong.business.withdraw.service.WithdrawCashService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.AESUtil;
import com.duanrong.newadmin.utility.DateUtil;

@Controller
public class UserBillController extends BaseController {

	@Resource
	RechargeService rechargeService;

	@Resource
	WithdrawCashService withdrawCashService;

	/**
	 * 
	 * @description 充值管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recharge/rechargeList")
	public String rechargeList(HttpServletRequest request,
			HttpServletResponse response, Model model, Recharge recharge) {
		try {
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String str = "";
			String end = "";
			if(recharge.getStart()==null){
				Calendar calendar1 = Calendar.getInstance();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				calendar1.add(Calendar.DATE, -3);
				String three_days_ago = sdf1.format(calendar1.getTime());
				recharge.setStart(three_days_ago);
			}
			
			if (recharge.getUserId() != null
					&& !recharge.getUserId().equals("")) {
				str += "&userId=" + recharge.getUserId();
			}
			
			if (recharge.getUserSourceIsNull() != null
					&& !recharge.getUserSourceIsNull().equals("")) {
				str += "&userSourceIsNull=" + recharge.getUserSourceIsNull();
			}
			
			if (recharge.getUserSource() != null
					&& !recharge.getUserSource().equals("")) {
				str += "&userSource=" + recharge.getUserSource();
			}
			
			if (recharge.getRealname() != null
					&& !recharge.getRealname().equals("")) {
				String name = java.net.URLDecoder.decode(
						recharge.getRealname(), "UTF-8");
				recharge.setRealname(name);
				str += "&realname=" + name;
			}
			if (recharge.getMnumber() != null
					&& !recharge.getMnumber().equals("")) {
				str += "&mnumber=" + recharge.getMnumber();
			}
			if (recharge.getStatus() != null
					&& !recharge.getStatus().equals("")) {
				str += "&status=" + recharge.getStatus();
			}
			if (recharge.getStart() != null && !recharge.getStart().equals("")) {

				recharge.setStart(recharge.getStart());
				str += "&start=" + recharge.getStart();
			}
			if (recharge.getEnd() != null && !recharge.getEnd().equals("")) {
				String endtime = DateUtil.addDay(recharge.getEnd(), 1);
				end = recharge.getEnd();
				str += "&end=" + recharge.getEnd();
				recharge.setEnd(endtime);

			}
			if (recharge.getUserType() != null && !recharge.getUserType().equals("")) {				
				str += "&userType=" + recharge.getUserType();
			}
			PageInfo pageInfo = rechargeService.readAllRecharge(
					Integer.parseInt(pageNo), 30, recharge);
			// 总充值金额
			double actualMoney = rechargeService.readTotalRecharge(recharge);
			// 【去除固定借款人】总充值金额
			BigDecimal excludeFixedBorrFee = rechargeService
					.readExcludeFixedBorrowerFee(recharge);
			//充值成功人数
			int  successPeopelReachargeCount=rechargeService.readRechargeSuccessPeople(recharge);
			//充值不成功金额
			BigDecimal excludeFixedBorrFeeFail = rechargeService
					.readExcludeFixedBorrowerFeeFail(recharge);
			//充值失败人数
			int  failPeopelReachargeCount=rechargeService.readRechargeFailPeople(recharge);
			model.addAttribute("url", "/recharge/rechargeList");
			model.addAttribute("successPeopelReachargeCount", successPeopelReachargeCount);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("excludeFixedBorrFeeFail",excludeFixedBorrFeeFail);
			model.addAttribute("failPeopelReachargeCount",failPeopelReachargeCount);
			model.addAttribute("realname", recharge.getRealname());
			model.addAttribute("userId", recharge.getUserId());
			model.addAttribute("userSource", recharge.getUserSource());
			model.addAttribute("userSourceIsNull", recharge.getUserSourceIsNull());
			model.addAttribute("mnumber", recharge.getMnumber());
			model.addAttribute("status", recharge.getStatus());
			model.addAttribute("start", recharge.getStart());
			model.addAttribute("end", end);
			model.addAttribute("str", str);
			model.addAttribute("actualMoney", actualMoney);
			// model.addAttribute("fee", fee);
			model.addAttribute("excludeFixedBorrFee", excludeFixedBorrFee);
			return "found/rechargeList";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出充值
	 *//*
	
	private void exportRechargeList(HttpServletRequest request,
			HttpServletResponse response,Recharge recharge){
		
		try {
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String str = "";
			String end = "";
			if (recharge.getUserId() != null
					&& !recharge.getUserId().equals("")) {
				str += "&userId=" + recharge.getUserId();
			}
			
			if (recharge.getUserSourceIsNull() != null
					&& !recharge.getUserSourceIsNull().equals("")) {
				str += "&userSourceIsNull=" + recharge.getUserSourceIsNull();
			}
			
			if (recharge.getUserSource() != null
					&& !recharge.getUserSource().equals("")) {
				str += "&userSource=" + recharge.getUserSource();
			}
			
			if (recharge.getRealname() != null
					&& !recharge.getRealname().equals("")) {
				String name = java.net.URLDecoder.decode(
						recharge.getRealname(), "UTF-8");
				recharge.setRealname(name);
				str += "&realname=" + name;
			}
			if (recharge.getMnumber() != null
					&& !recharge.getMnumber().equals("")) {
				str += "&mnumber=" + recharge.getMnumber();
			}
			if (recharge.getStatus() != null
					&& !recharge.getStatus().equals("")) {
				str += "&status=" + recharge.getStatus();
			}
			if (recharge.getStart() != null && !recharge.getStart().equals("")) {

				recharge.setStart(recharge.getStart());
				str += "&start=" + recharge.getStart();
			}
			if (recharge.getEnd() != null && !recharge.getEnd().equals("")) {
				String endtime = DateUtil.addDay(recharge.getEnd(), 1);
				end = recharge.getEnd();
				str += "&end=" + recharge.getEnd();
				recharge.setEnd(endtime);

			}
			if (recharge.getUserType() != null && !recharge.getUserType().equals("")) {				
				str += "&userType=" + recharge.getUserType();
			}
			PageInfo pageInfo = rechargeService.getAllRecharge(
					Integer.parseInt(pageNo), 30, recharge);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	*/
	/**
	 * 
	 * @description 提现管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cash/cashList")
	public String cashList(HttpServletRequest request,
			HttpServletResponse response, Model model, WithdrawCash withdrawCash) {
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
				String end = "";
				String registerTimeEnd="";
				
				if (withdrawCash.getUserSourceIsNull() != null
						&& !withdrawCash.getUserSourceIsNull().equals("")) {
					str += "&userSourceIsNull=" + withdrawCash.getUserSourceIsNull();
				}
				
				if (withdrawCash.getUserSource() != null
						&& !withdrawCash.getUserSource().equals("")) {
					str += "&userSource=" + withdrawCash.getUserSource();
				}
				if (withdrawCash.getRegisterTimeStart() != null
						&& !withdrawCash.getRegisterTimeStart().equals("")) {
					str += "&registerTimeStart=" + withdrawCash.getRegisterTimeStart();
				}
				if (withdrawCash.getRegisterTimeEnd() != null
						&& !withdrawCash.getRegisterTimeEnd().equals("")) {
					String endtime = DateUtil.addDay(withdrawCash.getRegisterTimeEnd(), 1);
					registerTimeEnd = withdrawCash.getEnd();
					withdrawCash.setRegisterTimeEnd(endtime);
					str += "&registerTimeEnd=" + registerTimeEnd;
				}
				if (withdrawCash.getUserId() != null
						&& !withdrawCash.getUserId().equals("")) {
					str += "&userId=" + withdrawCash.getUserId();
				}
				if (withdrawCash.getRealname() != null
						&& !withdrawCash.getRealname().equals("")) {
					String name = java.net.URLDecoder.decode(
							withdrawCash.getRealname(), "UTF-8");
					withdrawCash.setRealname(name);
					str += "&realname=" + name;
				}
				if (withdrawCash.getMnumber() != null
						&& !withdrawCash.getMnumber().equals("")) {
					str += "&mnumber=" + withdrawCash.getMnumber();
				}
				if (withdrawCash.getStatus() != null
						&& !withdrawCash.getStatus().equals("")) {
					str += "&status=" + withdrawCash.getStatus();
				}
				if (withdrawCash.getStart() != null
						&& !withdrawCash.getStart().equals("")) {

					withdrawCash.setStart(withdrawCash.getStart());
					str += "&start=" + withdrawCash.getStart();
				}
				if (withdrawCash.getEnd() != null
						&& !withdrawCash.getEnd().equals("")) {
					String endtime = DateUtil.addDay(withdrawCash.getEnd(), 1);
					end = withdrawCash.getEnd();
					withdrawCash.setEnd(endtime);
					str += "&end=" + end;
				}
				if (withdrawCash.getUserType() != null && !withdrawCash.getUserType().equals("")) {				
					str += "&userType=" + withdrawCash.getUserType();
				}
				PageInfo pageInfo = withdrawCashService.readPageInfo(
						Integer.parseInt(pageNo), 30, withdrawCash);
					List<WithdrawCash> list = pageInfo.getResults();
					for (WithdrawCash withdrawCash2 : list) {
						if (StringUtils.isNotBlank(withdrawCash2.getBankCard().getCardNo())) {
							System.out.println(withdrawCash2.getBankCard().getCardNo());
							try {
								String cardNo = AESUtil.decode(withdrawCash2.getBankCard().getCardNo());
								System.out.println(cardNo);
								BankCard bankCard=withdrawCash2.getBankCard();/*(cardNo.substring(0,4)+"********"+cardNo.substring(cardNo.length()-4));*/
								bankCard.setCardNo(cardNo.substring(0,4)+"********"+cardNo.substring(cardNo.length()-4));
							} catch (Exception e) {
								// TODO: handle exception
							}
						} 
					}
				double actualMoney = withdrawCashService
						.readTotalWithdrawCash(withdrawCash);
				double fee = withdrawCashService.readTotalFee(withdrawCash);
				// 总提现金额【去除固定借款人】
				BigDecimal excludeFixedFee = withdrawCashService
						.readExcludeFixedFee(withdrawCash);
				int  excludeFixedFeePeopleCount=withdrawCashService.readExcludeFixedFeePeopleCount(withdrawCash);
				model.addAttribute("excludeFixedFeePeopleCount",excludeFixedFeePeopleCount);
				model.addAttribute("url", "/cash/cashList");
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("realname", withdrawCash.getRealname());
				model.addAttribute("userId", withdrawCash.getUserId());
				model.addAttribute("mnumber", withdrawCash.getMnumber());
				model.addAttribute("status", withdrawCash.getStatus());
				model.addAttribute("registerTimeStart", withdrawCash.getRegisterTimeStart());
				model.addAttribute("registerTimeEnd", registerTimeEnd);
				model.addAttribute("userSource", withdrawCash.getUserSource());
				model.addAttribute("userSourceIsNull", withdrawCash.getUserSourceIsNull());
				model.addAttribute("start", withdrawCash.getStart());
				model.addAttribute("end", end);
				model.addAttribute("str", str);
				model.addAttribute("actualMoney", actualMoney);
				model.addAttribute("fee", fee);
				model.addAttribute("excludeFixedFee", excludeFixedFee);
				return "found/cashList";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 提现导出
	 * @param args
	 */
	@RequestMapping(value="/cash/exportCashList")
	public void exportCashList(HttpServletRequest request,
			HttpServletResponse response,WithdrawCash withdrawCash){
		try {
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String str = "";
			String end = "";
			String registerTimeEnd="";
			
			if (withdrawCash.getUserSourceIsNull() != null
					&& !withdrawCash.getUserSourceIsNull().equals("")) {
				str += "&userSourceIsNull=" + withdrawCash.getUserSourceIsNull();
			}
			
			if (withdrawCash.getUserSource() != null
					&& !withdrawCash.getUserSource().equals("")) {
				str += "&userSource=" + withdrawCash.getUserSource();
			}
			if (withdrawCash.getRegisterTimeStart() != null
					&& !withdrawCash.getRegisterTimeStart().equals("")) {
				str += "&registerTimeStart=" + withdrawCash.getRegisterTimeStart();
			}
			if (withdrawCash.getRegisterTimeEnd() != null
					&& !withdrawCash.getRegisterTimeEnd().equals("")) {
				String endtime = DateUtil.addDay(withdrawCash.getRegisterTimeEnd(), 1);
				registerTimeEnd = withdrawCash.getEnd();
				withdrawCash.setRegisterTimeEnd(endtime);
				str += "&registerTimeEnd=" + registerTimeEnd;
			}
			if (withdrawCash.getUserId() != null
					&& !withdrawCash.getUserId().equals("")) {
				str += "&userId=" + withdrawCash.getUserId();
			}
			if (withdrawCash.getRealname() != null
					&& !withdrawCash.getRealname().equals("")) {
				String name = java.net.URLDecoder.decode(
						withdrawCash.getRealname(), "UTF-8");
				withdrawCash.setRealname(name);
				str += "&realname=" + name;
			}
			if (withdrawCash.getMnumber() != null
					&& !withdrawCash.getMnumber().equals("")) {
				str += "&mnumber=" + withdrawCash.getMnumber();
			}
			if (withdrawCash.getStatus() != null
					&& !withdrawCash.getStatus().equals("")) {
				str += "&status=" + withdrawCash.getStatus();
			}
			if (withdrawCash.getStart() != null
					&& !withdrawCash.getStart().equals("")) {

				withdrawCash.setStart(withdrawCash.getStart());
				str += "&start=" + withdrawCash.getStart();
			}
			if (withdrawCash.getEnd() != null
					&& !withdrawCash.getEnd().equals("")) {
				String endtime = DateUtil.addDay(withdrawCash.getEnd(), 1);
				end = withdrawCash.getEnd();
				withdrawCash.setEnd(endtime);
				str += "&end=" + end;
			}
			if (withdrawCash.getUserType() != null && !withdrawCash.getUserType().equals("")) {				
				str += "&userType=" + withdrawCash.getUserType();
			}
			PageInfo pageInfo = withdrawCashService.readPageInfo(
					Integer.parseInt(pageNo), 999999999, withdrawCash);
				List<WithdrawCash>list=pageInfo.getResults();
				for (WithdrawCash withdrawCash2 : list) {
					if(withdrawCash2.getStatus().equals("wait_verify")){
						withdrawCash2.setStatus("等待提现");
					}else if(withdrawCash2.getStatus().equals("success")){
						withdrawCash2.setStatus("提现成功");
					}
					if(withdrawCash2.getWithdrawType().equals("NORMAL")){
						withdrawCash2.setStatus("否");
					}else if(withdrawCash2.getWithdrawType().equals("URGENT")){
						withdrawCash2.setStatus("是");
					}
					if (StringUtils.isNotBlank(withdrawCash2.getBankCard().getCardNo())) {
						System.out.println(withdrawCash2.getBankCard().getCardNo());
						try {
							String cardNo = AESUtil.decode(withdrawCash2.getBankCard().getCardNo());
							System.out.println(cardNo);
							BankCard bankCard=withdrawCash2.getBankCard();/*(cardNo.substring(0,4)+"********"+cardNo.substring(cardNo.length()-4));*/
							bankCard.setCardNo(cardNo.substring(0,4)+"********"+cardNo.substring(cardNo.length()-4));
						} catch (Exception e) {
							// TODO: handle exception
						}
					} 
				}
				Map<String, String> title = new LinkedHashMap<>();
				title.put("id", "流水号");
				title.put("time", "提现时间");
				title.put("userId","用户名");
				title.put("registerTime", "注册时间");
				title.put("userSource", "用户来源");
				title.put("realname", "用户姓名");
				title.put("actualMoney", "提现金额");
				title.put("fee", "手续费");
				title.put("status", "提现状态");
				title.put("withdrawType", "是否为加急提现");
				int[] style = { 16, 20,10,30, 10, 22, 16 ,22,20,20};
				String fileName = DateUtil.DateToString(new Date(),
						"yyyyMMddHHmmss");
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				fileName = "提现记录" + fileName + ".xls";
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment;filename="
						+ fileName);
				ExcelConvertor excelConvertor = new ExcelConvertor(
						response.getOutputStream(), fileName);
				String t ="提现记录";
				excelConvertor.excelExport(list, title, t, style);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		
	}
	
}
