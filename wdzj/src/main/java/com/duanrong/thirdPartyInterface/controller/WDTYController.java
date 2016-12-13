package com.duanrong.thirdPartyInterface.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import base.model.Invest;
import base.model.Loan;

import com.duanrong.business.token.model.AccessToken;
import com.duanrong.business.token.service.AccessTokenService;
import com.duanrong.jsonservice.util.SecurityUtil;
import com.duanrong.thirdPartyInterface.model.WDTYInvest;
import com.duanrong.thirdPartyInterface.model.WDTYLoan;
import com.duanrong.thirdPartyInterface.service.WDTYService;
import com.duanrong.thirdPartyInterface.util.LoanUtil;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.DateUtil;

/**
 * 网贷天眼Controller层
 * 
 * @author 尹逊志
 * @date 2014-11-12下午5:08:22
 */
@Controller
public class WDTYController {
	@Resource
	WDTYService wdtyService;

	@Resource
	AccessTokenService accessTokenService;

	/**
	 * 获取借款列表
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-12下午5:09:08
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api/loans", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> getLoanList(HttpServletRequest request) {
		String token = request.getParameter("token");
		if (token != null && token.equals("c9f896e6934111e5b80eac162d8afeb0")) {
			String status = request.getParameter("status");
			String time_from = request.getParameter("time_from");
			String time_to = request.getParameter("time_to");
			String page_size = request.getParameter("page_size");
			String page_index = request.getParameter("page_index");
			Integer begin = 0;
			Integer end = 20;
			if (page_size == null) {
				page_size = "20";
			}
			if (page_index == null) {
				page_index = "1";
			}
			begin = (Integer.parseInt(page_index) - 1)
					* Integer.parseInt(page_size);
			end = Integer.parseInt(page_size);
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("loanStatus", status);
			map.put("time_from", time_from);
			map.put("time_to", time_to);
			map.put("begin", begin);
			map.put("end", end);

			List<Loan> loans = wdtyService.findAllLoans(map);
			long allLoansSize = wdtyService.findLoansByTime(time_from, time_to);
			Integer pageCount = 1;
			if (allLoansSize % Integer.parseInt(page_size) == 0) {
				pageCount = (int) (allLoansSize / Integer.parseInt(page_size));
			} else {
				pageCount = (int) (allLoansSize / Integer.parseInt(page_size) + 1);
			}
			List<WDTYLoan> loanList = new ArrayList<WDTYLoan>();
			if (loans.size() <= 0) {
				Map<Object, Object> jsonmap = new HashMap<Object, Object>();
				jsonmap.put("result_code", "-1");
				jsonmap.put("result_msg", "没有返回记录!");
				jsonmap.put("page_count", null);
				jsonmap.put("page_index", null);
				jsonmap.put("loans", null);
				jsonmap.put("total", null);
				return jsonmap;
			} else {
				WDTYLoan wdtyLoan;
				for (Loan loan : loans) {
					wdtyLoan = LoanUtil.convertToWDTYLoan(loan);
					double remainMoney = ArithUtil.sub(loan.getTotalmoney(),
							wdtyService.getInvestedMoney(loan.getId()));
					double process = ArithUtil.round(
							remainMoney / loan.getTotalmoney(), 2);
					wdtyLoan.setProcess(process);
					Long investNum = wdtyService.getInvestNum(loan.getId());
					wdtyLoan.setInvest_num(NumberUtils.toInt(investNum
							.toString()));
					loanList.add(wdtyLoan);
				}
				Map<Object, Object> jsonmap = new HashMap<Object, Object>();
				jsonmap.put("result_code", "1");
				jsonmap.put("result_msg", "获取数据成功");
				jsonmap.put("page_count", pageCount.toString());
				jsonmap.put("page_index", page_index);
				jsonmap.put("loans", loanList);
				return jsonmap;
			}
		} else {
			Map<Object, Object> jsonmap = new HashMap<Object, Object>();
			jsonmap.put("result_code", "-1");
			jsonmap.put("result_msg", "未授权的访问!");
			jsonmap.put("page_count", null);
			jsonmap.put("page_index", null);
			jsonmap.put("loans", null);
			jsonmap.put("total", null);
			return jsonmap;
		}

	}

	public static void main(String[] args) {
		long allLoansSize = 48;
		System.out.println(allLoansSize % Integer.parseInt("7"));
	}

	/**
	 * 获取投资列表
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-13下午3:36:58
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api/data", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> getInvestList(HttpServletRequest request) {
		String token = request.getParameter("token");
		if (token.equals("c9f896e6934111e5b80eac162d8afeb0")) {
			String loanId = request.getParameter("id");
			String time_from = request.getParameter("time_from");
			String time_to = request.getParameter("time_to");
			String page_size = request.getParameter("page_size");
			String page_index = request.getParameter("page_index");
			Integer begin = 0;
			Integer end = 20;
			if (page_size == null) {
				page_size = "20";
			}
			if (page_index == null) {
				page_index = "1";
			}
			begin = (Integer.parseInt(page_index) - 1)
					* Integer.parseInt(page_size);
			end = Integer.parseInt(page_size);
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("loanId", loanId);
			map.put("time_from", time_from);
			map.put("time_to", time_to);
			map.put("begin", begin);
			map.put("end", end);
			List<Invest> investList = wdtyService.findAllInvests(map);
			long allInvestSize = investList.size();
			Integer pageCount = 1;
			if (allInvestSize % Integer.parseInt(page_size) == 0) {
				pageCount = (int) (allInvestSize / Integer.parseInt(page_size));
			} else {
				pageCount = (int) (allInvestSize / Integer.parseInt(page_size) + 1);
			}
			if (investList.size() <= 0) {
				Map<Object, Object> jsonmap = new HashMap<Object, Object>();
				jsonmap.put("result_code", "-1");
				jsonmap.put("result_msg", "没有查询到有效数据!");
				jsonmap.put("page_count", null);
				jsonmap.put("page_index", null);
				jsonmap.put("loans", null);
				jsonmap.put("total", "0");
				return jsonmap;
			} else {
				List<WDTYInvest> list = new ArrayList<>();
				for (Invest invest : investList) {
					WDTYInvest wdtyInvest = LoanUtil
							.convertToWDTYInvest(invest);
					list.add(wdtyInvest);
				}
				Map<Object, Object> jsonmap = new HashMap<Object, Object>();
				jsonmap.put("result_code", "1");
				jsonmap.put("result_msg", "获取数据成功");
				jsonmap.put("page_count", pageCount.toString());
				jsonmap.put("page_index", page_index);
				jsonmap.put("loans", list);
				return jsonmap;
			}
		} else {
			Map<Object, Object> jsonmap = new HashMap<Object, Object>();
			jsonmap.put("result_code", "-1");
			jsonmap.put("result_msg", "未授权的访问!");
			jsonmap.put("page_count", null);
			jsonmap.put("page_index", null);
			jsonmap.put("loans", null);
			jsonmap.put("total", "0");
			return jsonmap;
		}
	}

	/**
	 * 网贷天眼获取token
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-13下午7:02:49
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api/token", method = RequestMethod.GET)
	@ResponseBody
	public String login(HttpServletRequest request) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		int flag = 0;
		String token = null;
		if ("duanrongwdty".equals(userName) && "duanrongwdty".equals(password)) {
			token = "c9f896e6934111e5b80eac162d8afeb0";
			flag = 1;
		}
		JSONObject json2 = new JSONObject();
		json2.put("token", token);
		JSONObject json = new JSONObject();
		json.put("data", json2).put("result", flag);
		return json.toString();
	}
}
