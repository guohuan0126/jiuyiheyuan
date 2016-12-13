package com.duanrong.newadmin.controller.ruralfinance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;
import com.duanrong.business.ruralfinance.service.AgricultureForkLoansService;
import com.duanrong.business.ruralfinance.service.AgricultureLoanerInfoService;
import com.duanrong.business.ruralfinance.service.AgricultureRepaymentPlanService;
import com.duanrong.newadmin.controller.BaseController;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.ForkloansCompute;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.newadmin.utility.InterestAccrual;
import com.duanrong.newadmin.utility.RegexInput;
import com.duanrong.util.InterestUtil;

/**
 * 拆标管理和还款计划
 * 
 * @author YUMIN
 * 
 */
@Controller
public class AgricultureForkLoansController extends BaseController {

	@Autowired
	private AgricultureForkLoansService agricultureForkLoansService;
	@Autowired
	private AgricultureLoanerInfoService agricultureLoanerInfoService;
	@Autowired
	private AgricultureRepaymentPlanService agricultureRepaymentPlanService;

	/**
	 * 批量拆标
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ruralfinance/forkLoans")
	@ResponseBody
	public String AgricultureForkLoans(
			@RequestParam(value = "ids[]", required = true) String[] ids,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		String result = "success";
		if (ids != null && ids.length > 0) {
			result = agricultureForkLoansService.BatchForkLoans(ids);
		}
		return result;
	}

	/**
	 * 
	 * @description 批量生成还款计划
	 * @author YUMIN
	 * @time 2016-3-31 下午4:05:04
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ruralfinance/BatchLoanManagement")
	@ResponseBody
	public String BatchLoanManagement(
			@RequestParam(value = "ids[]", required = true) String[] ids,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		if (ids != null && ids.length > 0) {
			for (int id = 0; id < ids.length; id++) {
				// System.out.println(ids[id]);
				List<AgricultureRepaymentPlan> list = new ArrayList<AgricultureRepaymentPlan>();
				AgricultureLoaninfo agricultureLoaninfo = agricultureLoanerInfoService
						.readAgricultureLoaninfoByIdAndFlag(ids[id]);
				if (agricultureLoaninfo != null
						&& agricultureLoaninfo.getFlag() == 0) {
					int loan_term = agricultureLoaninfo.getLoanTerm();
					double money = agricultureLoaninfo.getMoney();// 合同金额
					double rate = agricultureLoaninfo.getRate();// 利率
					double loanMoney = agricultureLoaninfo.getLoanMoney(); // 放款金额
					double compositeInteresRate = agricultureLoaninfo
							.getCompositeInteresRate();// 综合费率
					String agriculturalType = agricultureLoaninfo
							.getAgriculturalType(); // 农贷类型，农贷，及时贷，韭农贷
					Date giveMoneyTime = agricultureLoaninfo.getGiveMoneyTime();
					String repayType = agricultureLoaninfo.getRepayType();
					JSONArray array = null;
					if ("等额本息".equals(repayType)) {
						if (agriculturalType != null
								&& ("韭农贷".equals(agriculturalType) || "惠牧贷"
										.equals(agriculturalType))) {
							array = InterestAccrual.gethuimu(money, loanMoney,
									compositeInteresRate, rate, loan_term);
						} else {
							array = InterestAccrual.getDeBenxi(money, rate,
									loan_term, loanMoney, compositeInteresRate);
						}
					} else {
						if (agriculturalType != null
								&& ("韭农贷".equals(agriculturalType) || "惠牧贷"
										.equals(agriculturalType))) {
							array = InterestAccrual.getjisuanLeek(money,
									loanMoney, compositeInteresRate,rate,loan_term);
						} else {
							array = InterestAccrual.getTimelyDeBenxi(money,
									compositeInteresRate, loan_term);
						}
					}
					for (int j = 0; j < array.size(); j++) {
						AgricultureRepaymentPlan repayment = new AgricultureRepaymentPlan();
						JSONObject obj = array.getJSONObject(j);
						repayment.setId(IdGenerator.randomUUID());
						repayment.setLoanDataId(ids[id]);
						Date RepayDate = DateUtil
								.addMonth(giveMoneyTime, j + 1);
						repayment.setRepayDate(RepayDate);
						repayment.setMonthMoney(obj.getDouble("money"));
						repayment.setCorpus(obj.getDouble("benjin"));
						repayment.setIntereat(obj.getDouble("lixi"));
						if (agriculturalType != null
								&& ("韭农贷".equals(agriculturalType) || "惠牧贷"
										.equals(agriculturalType))) {
							repayment.setServiceFee(obj
									.getDouble("serviceMoney"));
						}
						repayment.setPeriod(j + 1);
						repayment.setRepayStatus("unrepay");
						list.add(repayment);
					}
					try {
						agricultureRepaymentPlanService.insertBatch(list);
						Map<String, Object> loanerInfo = new HashMap<>();
						loanerInfo.put("flag", 1);
						loanerInfo.put("id", ids[id]);
						agricultureLoanerInfoService.updateFlag(loanerInfo);
					} catch (Exception e) {
						e.printStackTrace();
						return "error";
					}
				}
			}
			return "success";
		}
		return "error";

	}

	/**
	 * 农贷手动修改还款计划页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ruralfinance/toeditRepaymentplan")
	public String toeditRepaymentplan(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		try {
			String repayId = request.getParameter("repayId");
			AgricultureRepaymentPlan repaymentPlan = null;
			if (repayId != null && !"".equals(repayId)) {
				repaymentPlan = agricultureLoanerInfoService
						.readRepaymentPlanById(repayId);
			}
			model.addAttribute("repaymentPlan", repaymentPlan);
			model.addAttribute("repayId", repayId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ruralfinance/editRepaymentplan";

	}

	/**
	 * 执行农贷手动修改还款计划
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ruralfinance/editRepaymentplan")
	public void editRepaymentplan(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String operationTime = request.getParameter("operationTime");
		String realrepayMoney = request.getParameter("realrepayMoney");
		String latePenalty = request.getParameter("latePenalty");
		String serviceFee = request.getParameter("serviceFee");
		String repayStatus = request.getParameter("repayStatus");
		String returnMoney = request.getParameter("returnMoney");
		String remitCorpus = request.getParameter("remitCorpus");
		String remitIntereat = request.getParameter("remitIntereat");
		try {
			Map<String, Object> params = new HashMap<>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			if (operationTime != null && !"".equals(operationTime)) {
				params.put("operationTime", df.parse(operationTime));
			}

			if (realrepayMoney != null && !"".equals(realrepayMoney)) {
				params.put("realrepayMoney", Double.parseDouble(realrepayMoney));
			}
			if (latePenalty != null && !"".equals(latePenalty)) {
				params.put("latePenalty", Double.parseDouble(latePenalty));
			}

			if (repayStatus != null && !"".equals(repayStatus)) {
				params.put("repayStatus", repayStatus);
			}
			params.put("remitIntereat", Double.parseDouble(remitIntereat));
			params.put("remitCorpus", Double.parseDouble(remitCorpus));
			params.put("serviceFee", Double.parseDouble(serviceFee));
			params.put("returnMoney", Double.parseDouble(returnMoney));
			params.put("id", id);
			agricultureRepaymentPlanService.updateRepaymentplanById(params);
			response.getWriter().print("update_success");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}