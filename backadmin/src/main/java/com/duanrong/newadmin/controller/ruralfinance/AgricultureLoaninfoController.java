package com.duanrong.newadmin.controller.ruralfinance;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.Log;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;
import com.duanrong.business.ruralfinance.model.AgricultureZhongjinbank;
import com.duanrong.business.ruralfinance.model.LoanerinfoExport;
import com.duanrong.business.ruralfinance.model.LoaninfoExport;
import com.duanrong.business.ruralfinance.model.Template;
import com.duanrong.business.ruralfinance.model.ZJRepaymentExport;
import com.duanrong.business.ruralfinance.service.AgricultureLoanerInfoService;
import com.duanrong.business.ruralfinance.service.AgricultureRepaymentPlanService;
import com.duanrong.business.ruralfinance.service.AgricultureTimelyloansPrepaymentService;
import com.duanrong.business.ruralfinance.service.AgricultureZhongjinbankService;
import com.duanrong.newadmin.controller.BaseController;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.ForkloansCompute;
import com.duanrong.newadmin.utility.IdUtil;
import com.duanrong.newadmin.utility.InterestAccrual;
import com.duanrong.newadmin.utility.RegexInput;

@Controller
public class AgricultureLoaninfoController extends BaseController {

	@Resource
	private AgricultureLoanerInfoService loaninfoService;
	@Autowired
	private AgricultureRepaymentPlanService agricultureRepaymentPlanService;

	@Resource
	private AgricultureZhongjinbankService zhongjinbankService;
	@Autowired
	private AgricultureTimelyloansPrepaymentService agricultureTimelyloansPrepaymentService;

	@Resource
	Log log;


	/**
	 * 借款基本信息查询
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ruralfinance/agricultureLoaninfo")
	public String agricultureLoaninfo(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		// log.errLog("", "", 1);
		// 查询所有
		try {
			String pageNo = request.getParameter("pageNo");
			String pageSize = "10";
			if (pageNo == null) {
				pageNo = "1";
			}
			String forkStatus = request.getParameter("forkStatus");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String status = request.getParameter("status");
			String id = request.getParameter("name");
			String loanTerm = request.getParameter("loanTerm");
			String flag = request.getParameter("flag");
			String fkstart = request.getParameter("fkstart");
			String fkend = request.getParameter("fkend");
			String remark1 = request.getParameter("remark1");
			String accountingDepartment = request
					.getParameter("accountingDepartment");
			String repayType = request.getParameter("repayType");
			String packingStatus = request.getParameter("packingStatus");
			String whetherEarlyRepayment = request
					.getParameter("whetherEarlyRepayment");
			String agriculturalType = request
					.getParameter("agriculturalType");
			Map<String, Object> params = new HashMap<String, Object>();
			String str = "";
			if (flag != null && !flag.equals("")) {
				str += "&flag=" + flag;
				params.put("flag", flag);
			}
			if (forkStatus != null && !forkStatus.equals("")) {
				str += "&forkStatus=" + forkStatus;
				params.put("forkStatus", forkStatus);
			}
			if (start != null && !start.equals("")) {
                start=URLDecoder.decode(start,"UTF-8");				
				str += "&start=" + start;
				params.put("start", start);
			}
			if (end != null && !end.equals("")) {
				end=URLDecoder.decode(end,"UTF-8");
				str += "&end=" + end;
				params.put("end", end + "23:59");
			}
			if (status != null && !status.equals("")) {
				str += "&status=" + status;
				params.put("status", status);
			}
			if (id != null && !id.equals("")) {
				str += "&id=" + id;
				params.put("id", id);
			}
			if (loanTerm != null && !loanTerm.equals("")) {
				str += "&loanTerm=" + loanTerm;
				params.put("loanTerm", loanTerm);
			}
			if (fkstart != null && !fkstart.equals("")) {
				fkstart=URLDecoder.decode(fkstart,"UTF-8");
				str += "&fkstart=" + fkstart;
				params.put("fkstart", fkstart);
			}
			if (fkend != null && !fkend.equals("")) {
				fkend=URLDecoder.decode(fkend,"UTF-8");
				str += "&fkend=" + fkend;
				params.put("fkend", fkend + "23:59");
			}
			if (remark1 != null && !remark1.equals("")) {
				remark1=URLDecoder.decode(remark1,"UTF-8");
				str += "&remark1=" + remark1;
				params.put("remark1", remark1);
			}
			if (accountingDepartment != null && !accountingDepartment.equals("")) {
				str += "&accountingDepartment=" + accountingDepartment;
				params.put("accountingDepartment", accountingDepartment);
			}
			if (repayType != null && !repayType.equals("")) {
				repayType=URLDecoder.decode(repayType,"UTF-8");
				str += "&repayType=" + repayType;
				params.put("repayType", repayType);
			}
			if (packingStatus != null && !packingStatus.equals("")) {
				str += "&packingStatus=" + packingStatus;
				params.put("packingStatus", packingStatus);
			}
			if (whetherEarlyRepayment != null && !whetherEarlyRepayment.equals("")) {
				whetherEarlyRepayment=URLDecoder.decode(whetherEarlyRepayment,"UTF-8");
				str += "&whetherEarlyRepayment=" + whetherEarlyRepayment;
				params.put("whetherEarlyRepayment", whetherEarlyRepayment);
			}
			if (agriculturalType != null && !agriculturalType.equals("")) {
				agriculturalType=URLDecoder.decode(agriculturalType,"UTF-8");
				str += "&agriculturalType=" + agriculturalType;
				params.put("agriculturalType", agriculturalType);
			}
			PageInfo<AgricultureLoaninfo> pageInfo = loaninfoService
					.readAgricultureLoaninfo(Integer.parseInt(pageNo),
							Integer.parseInt(pageSize), params);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("forkStatus", forkStatus);
			model.addAttribute("start", start);
			model.addAttribute("end", end);
			model.addAttribute("id", id);
			model.addAttribute("status", status);
			model.addAttribute("loanTerm", loanTerm);
			model.addAttribute("str", str);
			model.addAttribute("flag", flag);
			model.addAttribute("fkstart", fkstart);
			model.addAttribute("fkend", fkend);
			model.addAttribute("accountingDepartment", accountingDepartment);
			model.addAttribute("repayType", repayType);
			model.addAttribute("packingStatus", packingStatus);
			model.addAttribute("whetherEarlyRepayment", whetherEarlyRepayment);
			model.addAttribute("remark1", remark1);	
			model.addAttribute("agriculturalType", agriculturalType);	
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return "ruralfinance/agricultureLoaninfo";
	}

	@RequestMapping(value = "/ruralfinance/agriculturalexport")
	public void repayExport(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String forkStatus = request.getParameter("forkStatus");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String status = request.getParameter("status");
		String id = request.getParameter("name");
		String loanTerm = request.getParameter("loanTerm");
		String flag = request.getParameter("flag");
		String fkstart = request.getParameter("fkstart");
		String fkend = request.getParameter("fkend");
		String remark1 = request.getParameter("remark1");
		String accountingDepartment = request
				.getParameter("accountingDepartment");
		Map<String, Object> params = new HashMap<String, Object>();
		try {
		if (flag != null && !flag.equals("")) {
			params.put("flag", flag);
		}
		if (forkStatus != null && !forkStatus.equals("")) {
			params.put("forkStatus", forkStatus);
		}
		if (start != null && !start.equals("")) {
			params.put("start", start);
		}
		if (end != null && !end.equals("")) {
			params.put("end", end + "23:59");
		}
		if (status != null && !status.equals("")) {
			params.put("status", status);
		}
		if (id != null && !id.equals("")) {
			params.put("id", id);
		}
		if (loanTerm != null && !loanTerm.equals("")) {
			params.put("loanTerm", loanTerm);
		}
		if (fkstart != null && !fkstart.equals("")) {
			params.put("fkstart", fkstart);
		}
		if (fkend != null && !fkend.equals("")) {
			params.put("fkend", fkend + "23:59");
		}
		if (remark1 != null && !remark1.equals("")) {
			params.put("remark1", remark1);
		}
		if (accountingDepartment != null && !accountingDepartment.equals("")) {
			params.put("accountingDepartment", accountingDepartment);
		}
		
			List<AgricultureLoaninfo> Loaninfo = loaninfoService
					.readAgricultureLoaninfo(params);
			List<LoaninfoExport> list = new ArrayList<>();
			for (AgricultureLoaninfo loaner : Loaninfo) {
				LoaninfoExport export = new LoaninfoExport();
				export.setId(loaner.getId());
				export.setBankcard(loaner.getBankcard());
				if (loaner.getAccountingDepartment() == 1) {
					export.setAccountingCompany("山水");
				} else {
					export.setAccountingCompany("久亿");
				}
				export.setBank(loaner.getBank());
				export.setBranchname(loaner.getBranchname());
				export.setContractId(loaner.getContractId());
				export.setIdCard(loaner.getIdCard());
				export.setMobileNumber(loaner.getMobileNumber());
				export.setName(loaner.getName());
				export.setLoanTime(loaner.getGiveMoneyTime());
				list.add(export);
			}
			String t = "借款人信息";
			Map<String, String> title = new LinkedHashMap<>();
			title.put("bankcard", "银行卡号");
			title.put("name", "客户姓名");
			title.put("bank", "银行名称");
			title.put("idCard", "身份证号");
			title.put("mobileNumber", "电话号码");
			title.put("loanTime", "放款日期");
			title.put("accountingCompany", "核算公司");

			int[] style = { 26, 15, 20, 30, 20, 26, 16 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "借款人信息" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			excelConvertor.excelExport(list, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 子标查询列表
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ruralfinance/childLoansList")
	public String childLoansList(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		String str = "";
		Map<String, Object> params = new HashMap<String, Object>();
		String forkStatus = request.getParameter("forkStatus");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String loanTerm = request.getParameter("loanTerm");
		String loanId = request.getParameter("loanId");
		String contractId = request.getParameter("contractId");

		if (loanId != null && !loanId.equals("")) {
			str += "&loanId=" + loanId;
			params.put("loanId", loanId);
		}
		if (contractId != null && !contractId.equals("")) {
			str += "&contractId=" + contractId;
			params.put("contractId", contractId);
		}
		if (forkStatus != null && !forkStatus.equals("")) {
			str += "&forkStatus=" + forkStatus;
			params.put("forkStatus", forkStatus);
		}
		if (start != null && !start.equals("")) {
			str += "&start=" + start;
			params.put("start", start);
		}
		if (end != null && !end.equals("")) {
			str += "&end=" + end;
			params.put("end", end + "23:59");
		}
		if (loanTerm != null && !loanTerm.equals("")) {
			str += "&loanTerm=" + loanTerm;
			params.put("loanTerm", loanTerm);
		}

		PageInfo<AgricultureForkLoans> pageInfo = loaninfoService
				.readAgricultureForkLoans(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize), params);

		model.addAttribute("loanId", loanId);
		model.addAttribute("contractId", contractId);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("forkStatus", forkStatus);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("loanTerm", loanTerm);
		model.addAttribute("str", str);
		return "ruralfinance/childLoansList";
	}
	
	/**
	 * 还款计划(更新还款信息)
	 */
	@ResponseBody
	@RequestMapping(value = "ruralfinance/updateRepaymentplanList")
	public String updateRepaymentplanList(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		List<AgricultureRepaymentPlan> list = loaninfoService
				.readRuralfinanceFailRepaymentPlan();
		String deadlineTime = request.getParameter("deadlineTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		//调用罚息方法
		try {
			loaninfoService.calculationFaxi(deadlineTime);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
		//以前的方法调用逾期罚息的计算
		/*try {
			for (AgricultureRepaymentPlan agricultureRepaymentPlan : list) {
				// 实际还款日期
				Date operationTime = agricultureRepaymentPlan
						.getOperationTime();
				// 应还日期
				Date repayDate = agricultureRepaymentPlan.getRepayDate();
				// 当前时间
				Date today = new Date();
				if (!StringUtils.isBlank(deadlineTime)) {
					deadlineTime = deadlineTime + " 23:00";
					today = sdf.parse(deadlineTime);
				}

				// 剩余应还金额
				double needRepayMoney = loaninfoService
						.readneedRepayMoney(agricultureRepaymentPlan
								.getLoanDataId());
				// 还款完成 如果实际还款时间大于应还时间 获取相差天数 计算罚息
				if (agricultureRepaymentPlan.getRepayStatus().equals("finish")) {
					int day = DateUtil.DayDifference(repayDate, operationTime);
					if (day > 0) {
						if (day > 3) {
							double money = ArithUtil
									.round(agricultureRepaymentPlan
											.getMonthMoney() * 0.001, 2)
									* 3
									+ ArithUtil.round((needRepayMoney), 2)
									* (day - 3);
							// Round(本期应还金额*0.001， 2)*3 + round（剩余应还金额*0.001) *
							// (n-3）
							agricultureRepaymentPlan.setLatePenalty(ArithUtil
									.round(money, 2));
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", agricultureRepaymentPlan.getId());
							map.put("latePenalty",
									agricultureRepaymentPlan.getLatePenalty());
							loaninfoService.renewalLatePenalty(map);
						} else {
							double money = ArithUtil
									.round(agricultureRepaymentPlan
											.getMonthMoney() * 0.001, 2)
									* day;
							agricultureRepaymentPlan.setLatePenalty(ArithUtil
									.round(money, 2));
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", agricultureRepaymentPlan.getId());
							map.put("latePenalty",
									agricultureRepaymentPlan.getLatePenalty());
							loaninfoService.renewalLatePenalty(map);
						}
					}
					// 未还款如果当前时间大于应还时间 获取相差天数 计算罚息
				} else if (agricultureRepaymentPlan.getRepayStatus().equals(
						"unrepay")) {
					int day = DateUtil.DayDifference(repayDate, today);
					if (day > 0) {
						if (day > 3) {
							double money = ArithUtil
									.round(agricultureRepaymentPlan
											.getMonthMoney() * 0.001, 2)
									* 3
									+ ArithUtil.round((needRepayMoney), 2)
									* (day - 3);
							// Round(本期应还金额*0.001， 2)*3 + round（剩余应还金额*0.001) *
							// (n-3）
							agricultureRepaymentPlan.setLatePenalty(ArithUtil
									.round(money, 2));
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", agricultureRepaymentPlan.getId());
							map.put("latePenalty",
									agricultureRepaymentPlan.getLatePenalty());
							loaninfoService.renewalLatePenalty(map);
						} else {

							double money = ArithUtil
									.round(agricultureRepaymentPlan
											.getMonthMoney() * 0.001, 2)
									* day;
							agricultureRepaymentPlan.setLatePenalty(ArithUtil
									.round(money, 2));
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", agricultureRepaymentPlan.getId());
							map.put("latePenalty",
									agricultureRepaymentPlan.getLatePenalty());
							loaninfoService.renewalLatePenalty(map);
						}
						// 还款失败如果当前时间大于应还时间 获取相差天数 计算罚息
					} else if (agricultureRepaymentPlan.getRepayStatus()
							.equals("repayfail")) {
						int day1 = DateUtil.DayDifference(repayDate, today);
						if (day1 > 0) {
							if (day1 > 3) {
								double money = ArithUtil.round(
										agricultureRepaymentPlan
												.getMonthMoney() * 0.001, 2)
										* 3
										+ ArithUtil.round((needRepayMoney), 2)
										* (day - 3);
								// Round(本期应还金额*0.001， 2)*3 +
								// round（剩余应还金额*0.001) * (n-3）
								agricultureRepaymentPlan
										.setLatePenalty(ArithUtil.round(money,
												2));
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("id", agricultureRepaymentPlan.getId());
								map.put("latePenalty", agricultureRepaymentPlan
										.getLatePenalty());
								loaninfoService.renewalLatePenalty(map);
							} else {
								double money = ArithUtil.round(
										agricultureRepaymentPlan
												.getMonthMoney() * 0.001, 2)
										* day1;
								agricultureRepaymentPlan
										.setLatePenalty(ArithUtil.round(money,
												2));
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("id", agricultureRepaymentPlan.getId());
								map.put("latePenalty", agricultureRepaymentPlan
										.getLatePenalty());
								loaninfoService.renewalLatePenalty(map);
							}
						}

					}

				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";*/

	}

	/**
	 * 查询所有还款计划
	 */
	@RequestMapping(value = "ruralfinance/repaymentplanList")
	public String toRepaymentplan(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		// 查询所有
		try {
			String pageNo = request.getParameter("pageNo");
			String pageSize = "30";
			if (pageNo == null) {
				pageNo = "1";
			}
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String fkstart = request.getParameter("fkstart");
			String fkend = request.getParameter("fkend");
			String accountingDepartment = request
					.getParameter("accountingDepartment");
			String repayType = request.getParameter("repayType");
			String whetherEarlyRepayment = request
					.getParameter("whetherEarlyRepayment");
			String repayStatus = request
					.getParameter("repayStatus");
			Map<String, Object> params = new HashMap<String, Object>();
			String str = "";
			if (end != null && !end.equals("")) {
				end=URLDecoder.decode(end,"UTF-8");
				str += "&end=" + end;
				params.put("end", end + "23:59");
			}
			if (start != null && !start.equals("")) {
				start=URLDecoder.decode(start,"UTF-8");
				str += "&start=" + start;
				params.put("start", start);
			}
			if (id != null && !id.equals("")) {
				str += "&id=" + id;
				params.put("id", id);
			}
			if (name != null && !name.equals("")) {
				name=URLDecoder.decode(name,"UTF-8");
				str += "&name=" + name;
				params.put("name", name);
			}
			if (fkstart != null && !fkstart.equals("")) {
				fkstart=URLDecoder.decode(fkstart,"UTF-8");
				str += "&fkstart=" + fkstart;
				params.put("fkstart", fkstart);
			}
			if (fkend != null && !fkend.equals("")) {
				fkend=URLDecoder.decode(fkend,"UTF-8");
				str += "&fkend=" + fkend;
				params.put("fkend", fkend + "23:59");
			}
			if (accountingDepartment != null && !accountingDepartment.equals("")) {
				str += "&accountingDepartment=" + accountingDepartment;
				params.put("accountingDepartment", accountingDepartment);
			}
			if (repayType != null && !repayType.equals("")) {
				repayType=URLDecoder.decode(repayType,"UTF-8");
				str += "&repayType=" + repayType;
				params.put("repayType", repayType);
			}
			if (whetherEarlyRepayment != null && !whetherEarlyRepayment.equals("")) {
				str += "&whetherEarlyRepayment=" + whetherEarlyRepayment;
				params.put("whetherEarlyRepayment", whetherEarlyRepayment);
			}
			if (repayStatus != null && !repayStatus.equals("")) {
				str += "&repayStatus=" + repayStatus;
				params.put("repayStatus", repayStatus);
			}
			PageInfo<AgricultureRepaymentPlan> pageInfo = loaninfoService
					.readRuralfinanceRepaymentPlan(Integer.parseInt(pageNo),
							Integer.parseInt(pageSize), params);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("name", name);
			model.addAttribute("start", start);
			model.addAttribute("end", end);
			model.addAttribute("id", id);
			model.addAttribute("str", str);
			model.addAttribute("fkstart", fkstart);
			model.addAttribute("fkend", fkend);
			model.addAttribute("accountingDepartment", accountingDepartment);
			model.addAttribute("repayType", repayType);
			model.addAttribute("whetherEarlyRepayment", whetherEarlyRepayment);
			model.addAttribute("repayStatus", repayStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return "ruralfinance/repaymentplan";

	}

	@RequestMapping(value = "/ruralfinance/repaymentPlanexport")
	public void repaymentPlanexport(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String fkstart = request.getParameter("fkstart");
		String fkend = request.getParameter("fkend");
		String accountingDepartment = request
				.getParameter("accountingDepartment");
		Map<String, Object> params = new HashMap<String, Object>();
		if (end != null && !end.equals("")) {
			params.put("end", end + "23:59");
		}
		if (start != null && !start.equals("")) {
			params.put("start", start);
		}
		if (id != null && !id.equals("")) {
			params.put("id", id);
		}
		if (name != null && !name.equals("")) {
			params.put("name", name);
		}
		if (fkstart != null && !fkstart.equals("")) {
			params.put("fkstart", fkstart);
		}
		if (fkend != null && !fkend.equals("")) {
			params.put("fkend", fkend + " 23:59");
		}
		if (accountingDepartment != null && !accountingDepartment.equals("")) {
			params.put("accountingDepartment", accountingDepartment);
		}
		try {
			List<AgricultureRepaymentPlan> repaymentlist = loaninfoService
					.readRuralfinanceRepaymentPlan(params);

			List<ZJRepaymentExport> list = new ArrayList<>();
			for (AgricultureRepaymentPlan reapyment : repaymentlist) {
				ZJRepaymentExport export = new ZJRepaymentExport();
				export.setId(reapyment.getId());
				export.setContractId(reapyment.getContractId());
				export.setCompositeInteresRate(reapyment
						.getCompositeInteresRate());
				export.setMoney(reapyment.getMoney());
				export.setName(reapyment.getName());
				export.setLoanTerm(reapyment.getLoanTerm() + "个月");
				export.setRate(reapyment.getRate());
				export.setCorpus(reapyment.getCorpus());
				export.setIntereat(reapyment.getIntereat());
				list.add(export);
			}
			String t = "还款计划信息";
			Map<String, String> title = new LinkedHashMap<>();
			title.put("name", "借款人姓名");
			title.put("money", "合同金额");
			title.put("compositeInteresRate", "综合费率");
			title.put("loanTerm", "借款期限");
			title.put("corpus", "本金");
			title.put("intereat", "利息");
			int[] style = { 26, 15, 20, 30, 20, 26, 16 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "repayment" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			excelConvertor.excelExport(list, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量修改 状态
	 */
	@ResponseBody
	@RequestMapping(value = "ruralfinance/updateLoans")
	public String updateLoans(HttpServletResponse response,
			HttpServletRequest request, Model model,
			@RequestParam("ids[]") String[] ids) {
		String status = request.getParameter("status");

		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("id", id);
			loaninfoService.updateStatusById(params);
		}
		System.out.println(ids);
		return "success";
	}

	/**
	 * 根据父标id 查询下面的子标信息
	 */
	@RequestMapping(value = "ruralfinance/childLoansListById")
	public String childLoansListById(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		// 查询所有
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}

		String str = "";
		String id = request.getParameter("id");
		if (id != null && !id.equals("")) {
			str += "&id=" + id;
		}
		PageInfo<AgricultureForkLoans> pageInfo = loaninfoService
				.readAgricultureForkLoansById(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize), id);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("str", str);
		return "ruralfinance/childLoansListById";

	}

	/**
	 * 批量修改还款状态
	 */
	@ResponseBody
	@RequestMapping(value = "/ruralfinance/updateRepaymentplanStatusList")
	public String updateRepaymentplanStatusList(HttpServletResponse response,
			HttpServletRequest request, Model model,
			@RequestParam("ids[]") String[] ids) {
		String status = request.getParameter("status");

		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("id", id);
			loaninfoService.updateRepaymentplanStatus(params);
		}
		System.out.println(ids);
		return "success";
	}

	/**
	 * 导出中金模板批量代扣
	 */
	@RequestMapping(value = "ruralfinance/exportRepaymentplanList")
	public void exportDate(HttpServletRequest request,
			HttpServletResponse response) {
		String deadlineTime = request.getParameter("deadlineTime");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isBlank(deadlineTime)) {
			params.put("data", df.format(new Date()));
		} else {
			params.put("data", deadlineTime + " 23:59");
		}
		List<Template> list = loaninfoService.readTemplate(params);
		String[] bankStr = { "邮储", "工商银行", "农业银行", "中国银行", "建设银行", "交通银行",
				"招商银行", "中信银行", "光大银行", "华夏银行", "民生银行", "平安(深发)银行", "兴业银行",
				"浦发银行" };
		List<String> bankList = Arrays.asList(bankStr);
		try {
			int i = 1;
			for (Template template : list) {
				double monthMoney = template.getMonthMoney();
				double latePenalty = template.getLatePenalty();
				template.setMoney(monthMoney + latePenalty);
				String s = new SimpleDateFormat("yyyyMMddHHmmssSSS")
						.format(new Date());
				String num = "";
				if (StringUtils.isBlank(template.getDetailsNumber())
						|| template.getRepayStatus().equals("repayfail")) {
					num = s + IdUtil.getId();
				} else {
					num = template.getDetailsNumber();
				}

				template.setRemark("批量代扣" + i++);
				template.setNum(num);
				template.setBalanceFlag("0001");
				template.setCardType("借记卡");
				template.setBankNum(template.getBankNum());
				template.setIdCard(template.getIdCard());
				if (template.getAccountType() == 0) {
					template.setAccountStatus("个人");
				} else {
					template.setAccountStatus("企业");
				}
				if (template.getIdType() == 0) {
					template.setIdStatus("护照");
				} else {
					template.setIdStatus("身份证");

				}
				template.setMobileNum(template.getMobileNum());
				template.setEmail("");
				template.setProtocolUserID(template.getProtocolUserID());
				String bankname = template.getBankName();
				String bankin = "";
				if (!template.getBankName().equals("中国银行")
						&& template.getBankName().contains("中国")) {
					bankin = template.getBankName().replace("中国", "");
					if (bankin.contains("邮政")) {
						bankin = "邮储";
					}
				} else {
					bankin = template.getBankName();
					if (bankin.contains("邮政")) {
						bankin = "邮储";
					}
				}
				if (bankList.contains(bankin)) {
					bankname = bankin;
				} else {
					AgricultureZhongjinbank zhongjinbank = zhongjinbankService
							.findByCondition(template.getBankName());
					if (zhongjinbank != null)
						bankname = zhongjinbank.getBankCode();
				}
				template.setBankName(bankname);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", template.getId());
				map.put("detailsNumber", num);
				agricultureRepaymentPlanService.updateRepaymentplan(map);
			}
			Map<String, String> title = new LinkedHashMap<>();
			title.put("num", "明细流水号");
			title.put("money", "金额(元)");
			title.put("bankName", "银行名称");
			title.put("accountStatus", "账户类型");
			title.put("userName", "账户名称");
			title.put("bankNum", "账户号码");
			title.put("branchname", "分支行");
			title.put("province", "省份");
			title.put("city", "城市");
			title.put("balanceFlag", "结算标示");
			title.put("remark", "备注");
			title.put("idStatus", "证件类型");
			title.put("idCard", "证件号码");
			title.put("mobileNum", "手机号");
			title.put("email", "电子邮箱");
			title.put("protocolUserID", "协议用户编号");
			title.put("cardType", "卡介质类型");
			int[] style = { 26, 20, 20, 10, 10, 22, 16, 22, 20, 20, 20, 20, 20,
					20, 20, 20, 15 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "中金还款明细" + fileName + ".xls";
			fileName = URLDecoder.decode(fileName, "utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			String sheetName = "批量代扣";
			excelConvertor.excelExport(list, title, style, sheetName);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/*
	 * 导出提前还款明细
	 */
	@RequestMapping(value = "ruralfinance/exportTimeRepaymentplanList/{type}")
	public void exportTimeDate(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String type) {
		List<Template> list = null;
		String fileRealName = "";
		Map<String, Object> params = new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String date =df.format(new Date());
		if (type.equals("early")) {
			fileRealName = "提前还款明细";
			// 1提前还款状态
			params.put("earlyRepayment", 1);
			list = loaninfoService.readTimeTemplate(params);
		} else {
			// 正常还款和逾期还款状态为0
			params.put("earlyRepayment", 0);
			String deadlineTime = request.getParameter("deadlineTime");			
			if (StringUtils.isNotBlank(deadlineTime)) {
				date=deadlineTime;
			} 				
			params.put("data", date);
		}
		if (type.equals("normal")) {
			fileRealName = "正常还款明细";
			// 正常还款设置状态为1
			params.put("timeStatus", 1);
			list = loaninfoService.readTimeTemplate(params);
		}
		if (type.equals("late")) {
			fileRealName = "逾期还款明细";
			// 逾期状态为0
			params.put("timeStatus", 0);
			list = loaninfoService.readTimeTemplate(params);
		}

		String[] bankStr = { "邮储", "工商银行", "农业银行", "中国银行", "建设银行", "交通银行",
				"招商银行", "中信银行", "光大银行", "华夏银行", "民生银行", "平安(深发)银行", "兴业银行",
				"浦发银行" };
		List<String> bankList = Arrays.asList(bankStr);
		try {
			int i = 1;
			for (Template template : list) {
				double monthMoney = template.getMonthMoney();
				double latePenalty = template.getLatePenalty();
				template.setMoney(monthMoney + latePenalty);
				String s = new SimpleDateFormat("yyyyMMddHHmmssSSS")
						.format(new Date());
				String num = "";
				if (StringUtils.isBlank(template.getDetailsNumber())
						|| template.getRepayStatus().equals("repayfail")) {
					num = s + IdUtil.getId();
				} else {
					num = template.getDetailsNumber();
				}

				template.setRemark("批量代扣" + i++);
				template.setNum(num);
				template.setBalanceFlag("0001");
				template.setCardType("借记卡");
				template.setBankNum(template.getBankNum());
				template.setIdCard(template.getIdCard());
				if (template.getAccountType() == 0) {
					template.setAccountStatus("个人");
				} else {
					template.setAccountStatus("企业");
				}
				if (template.getIdType() == 0) {
					template.setIdStatus("护照");
				} else {
					template.setIdStatus("身份证");

				}
				template.setMobileNum(template.getMobileNum());
				template.setEmail("");
				template.setProtocolUserID(template.getProtocolUserID());
				String bankname = template.getBankName();
				String bankin = "";
				if (!template.getBankName().equals("中国银行")
						&& template.getBankName().contains("中国")) {
					bankin = template.getBankName().replace("中国", "");
					if (bankin.contains("邮政")) {
						bankin = "邮储";
					}
				} else {
					bankin = template.getBankName();
					if (bankin.contains("邮政")) {
						bankin = "邮储";
					}
				}
				if (bankList.contains(bankin)) {
					bankname = bankin;
				} else {
					AgricultureZhongjinbank zhongjinbank = zhongjinbankService
							.findByCondition(template.getBankName());
					if (zhongjinbank != null)
						bankname = zhongjinbank.getBankCode();
				}
				template.setBankName(bankname);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", template.getId());
				map.put("detailsNumber", num);
				agricultureRepaymentPlanService.updateRepaymentplan(map);
			}
			Map<String, String> title = new LinkedHashMap<>();
			title.put("num", "明细流水号");
			title.put("money", "金额(元)");
			title.put("bankName", "银行名称");
			title.put("accountStatus", "账户类型");
			title.put("userName", "账户名称");
			title.put("bankNum", "账户号码");
			title.put("branchname", "分支行");
			title.put("province", "省份");
			title.put("city", "城市");
			title.put("balanceFlag", "结算标示");
			title.put("remark", "备注");
			title.put("idStatus", "证件类型");
			title.put("idCard", "证件号码");
			title.put("mobileNum", "手机号");
			title.put("email", "电子邮箱");
			title.put("protocolUserID", "协议用户编号");
			title.put("cardType", "卡介质类型");
			int[] style = { 26, 20, 20, 10, 10, 22, 16, 22, 20, 20, 20, 20, 20,
					20, 20, 20, 15 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			fileName = fileRealName + fileName + ".xls";
			fileName = URLDecoder.decode(fileName, "utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			String sheetName = "批量代扣";
			excelConvertor.excelExport(list, title, style, sheetName);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 导出中金白名单
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/ruralfinance/exportLoanerinfo")
	public void exportLoan(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String status = request.getParameter("status");
		String fkstart = request.getParameter("fkstart");
		String fkend = request.getParameter("fkend");
		String remark1 = request.getParameter("remark1");
		String accountingDepartment = request
				.getParameter("accountingDepartment");
		Map<String, Object> params = new HashMap<String, Object>();
		if (start != null && !start.equals("")) {
			params.put("start", start);
		}
		if (end != null && !end.equals("")) {
			params.put("end", end + "23:59");
		}
		if (status != null && !status.equals("")) {
			params.put("status", status);
		}
		if (fkstart != null && !fkstart.equals("")) {
			params.put("fkstart", fkstart);
		}
		if (fkend != null && !fkend.equals("")) {
			params.put("fkend", fkend + "23:59");
		}
		if (accountingDepartment != null && !accountingDepartment.equals("")) {
			params.put("accountingDepartment", accountingDepartment);
		}
		if (remark1 != null && !remark1.equals("")) {
			params.put("remark1", remark1);
		}
		try {
			List<AgricultureLoaninfo> Loanerinfo = loaninfoService
					.readAgricultureLoaninfo(params);
			List<LoanerinfoExport> list = new ArrayList<>();
			String[] bankStr = { "邮储", "工商银行", "农业银行", "中国银行", "建设银行", "交通银行",
					"招商银行", "中信银行", "光大银行", "华夏银行", "民生银行", "平安(深发)银行", "兴业银行",
					"浦发银行" };
			List<String> bankList = Arrays.asList(bankStr);
			for (AgricultureLoaninfo loaner : Loanerinfo) {
				LoanerinfoExport export = new LoanerinfoExport();
				export.setId(loaner.getId());
				export.setName(loaner.getName());
				export.setIdCard(loaner.getIdCard());
				export.setMobileNumber(loaner.getMobileNumber());
				export.setBranchname(loaner.getBranchname());
				export.setBankcard(loaner.getBankcard());
				export.setOrganizationID("002042");
				if (loaner.getTypeOfId().equals("0")) {
					export.setIdType("护照");
				} else {
					export.setIdType("身份证");
				}
				if (loaner.getCustomerType().equals("1")) {
					export.setCustomerType("企业");
				} else {
					export.setCustomerType("个人");
				}
				String bankname = loaner.getBank();
				String bankin = "";
				if (!loaner.getBank().equals("中国银行")
						&& loaner.getBank().contains("中国")) {
					bankin = loaner.getBank().replace("中国", "");
					if (bankin.contains("邮政")) {
						bankin = "邮储";
					}
				} else {
					bankin = loaner.getBank();
					if (bankin.contains("邮政")) {
						bankin = "邮储";
					}
				}
				if (bankList.contains(bankin)) {
					bankname = bankin;
				} else {
					AgricultureZhongjinbank zhongjinbank = zhongjinbankService
							.findByCondition(loaner.getBank());
					if (zhongjinbank != null)
						bankname = zhongjinbank.getBankCode();
				}
				export.setBank(bankname);
				list.add(export);
			}

			Map<String, String> title = new LinkedHashMap<>();
			title.put("organizationID", "机构ID");
			title.put("bankcard", "账户号码");
			title.put("name", "账户名称");
			title.put("customerType", "账户类型");
			title.put("bank", "银行名称");
			title.put("idType", "证件类型");
			title.put("idCard", "证件号码");
			title.put("mobileNumber", "手机号");

			int[] style = { 15, 20, 23, 13, 23, 15, 20, 20 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "白名单" + fileName + ".xls";
			fileName = URLDecoder.decode(fileName, "utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			String t = "导出中金白名单";
			String sheetName = "白名单";
			excelConvertor.excelExport(list, title, style, sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量修改 状态
	 */
	@ResponseBody
	@RequestMapping(value = "ruralfinance/updateRepaymentplan")
	public String updateLoans(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		try {
			String uploadExcelId = request.getParameter("uploadExcelId");
			Map<String, Object> params = new HashMap<>();
			params.put("repayStatus", "finish");
			params.put("byType", 1);
			params.put("status", 1);
			params.put("uploadExcelId", uploadExcelId);
			// 扣款成功的处理
			agricultureRepaymentPlanService.updateRepaymentplanStatus(params);
			Map<String, Object> params2 = new HashMap<>();
			params2.put("repayStatus", "repayfail");
			params2.put("status", 0);
			params2.put("uploadExcelId", uploadExcelId);
			// 扣款失败的处理
			agricultureRepaymentPlanService.updateRepaymentplanStatus(params2);
			// 更新还款状态后把中金记录标识由0变成1
			agricultureRepaymentPlanService
					.updateRepaymentplanFlag(uploadExcelId);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	/**
	 * 批量修改 提前还款的还款计划和借款项目信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ruralfinance/updateTimelyRepaymentplan")
	public String updateTimelyRepaymentplan(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		String uploadExcelId = request.getParameter("uploadExcelId");
		String result = "success";
		if (StringUtils.isNotBlank(uploadExcelId)) {
			result = agricultureTimelyloansPrepaymentService
					.updateTimelyLoansPayment(uploadExcelId);
		}
		return result;
	}

	/**
	 * 农贷变更客户银行卡页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ruralfinance/agricultureEditcard")
	public String agricultureEditcard(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		// 根据身份证件号或者合同编号查询借款人的银行卡信息
		String str = "";
		Map<String, Object> params = new HashMap<>();
		AgricultureLoaninfo Loaninfo = new AgricultureLoaninfo();
		String ssid = request.getParameter("ssid");
		if (ssid != null && !"".equals(ssid)) {
			str += "&ssid=" + ssid;
			if (RegexInput.regexIDcard(ssid)) {
				params.put("byType", 2);
			} else {
				params.put("byType", 1);
			}
			params.put("ssid", ssid);
			Loaninfo = loaninfoService.readAgricultureBycontractId(params);
		}
		model.addAttribute("Loaninfo", Loaninfo);
		model.addAttribute("str", str);
		model.addAttribute("ssid", ssid);
		
		return "ruralfinance/agricultureEditcard";

	}

	/**
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateLoaninfobank")
	public String updateLoaninfobank(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String cardNo = request.getParameter("cardNo");
		String bank = request.getParameter("bank");
		String branchname = request.getParameter("branchname");
		try {
			if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(cardNo)
					&& StringUtils.isNotBlank(bank)) {
				Map<String, Object> params = new HashMap<>();
				params.put("id", id);
				params.put("bankcard", cardNo);
				params.put("bank", bank);
				params.put("branchname", branchname);
				loaninfoService.updateLoaninfobank(params);
				return "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
		return "fail";
	}

	/**
	 * 韭农贷等额本息（默认只发布到活期宝里）提前还款查询
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ruralfinance/leekPrepayment")
	public String leekPrepayment(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String contractId = request.getParameter("contractId");
		String loanTerm = request.getParameter("loanTerm");
		try {
			String str = "";
			String error = "";
			if (contractId != null && !contractId.equals("")) {
				str += "&contractId=" + contractId;
			}
			if (loanTerm != null && !loanTerm.equals("")) {
				str += "&loanTerm=" + loanTerm;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			List<AgricultureRepaymentPlan> repaymentlist = new ArrayList<>();
			if (contractId != null && !contractId.equals("")) {
				if (loanTerm != null && !loanTerm.equals("")) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", contractId);
					List<AgricultureRepaymentPlan> planList = loaninfoService
							.readRuralfinanceRepaymentPlan(params);
					if (planList != null && planList.size() > 0) {
						Date startTime = DateUtil
								.addMonth(planList.get(0).getRepayDate(),
										Integer.parseInt(loanTerm) - 1);
						String nowDate = df.format(new Date());
						String parentDate = df.format(startTime);
						double shengyu = 0.0;
						if (DateUtil.dayDifference(nowDate, parentDate) > 0) {
							for (int i = 0; i < planList.size(); i++) {
								AgricultureRepaymentPlan repaymentPlan = new AgricultureRepaymentPlan();
								String agriculturalType = planList.get(i).getAgriculturalType();
								double corpus = planList.get(i).getCorpus();
								shengyu += corpus;// 前n期的本金和
								repaymentPlan.setId(planList.get(i).getId());// 还款id
								repaymentPlan
										.setName(planList.get(i).getName());// 还款人姓名
								repaymentPlan.setRepayType(planList.get(i)
										.getRepayType());// 还款方式
								repaymentPlan.setRepayDate(planList.get(i)
										.getRepayDate());// 还款日期（应还日期）
								repaymentPlan.setPeriod(planList.get(i)
										.getPeriod());// 期数（第几期还款）
								repaymentPlan.setRepayStatus(planList.get(i)
										.getRepayStatus());// 状态
								repaymentPlan.setRealrepayMoney(planList.get(i)
										.getRealrepayMoney());// 每月实际还金额
								repaymentPlan.setOperationTime(planList.get(i)
										.getOperationTime());// 实际还款日期								
								if (i + 1 < Integer.parseInt(loanTerm)) {
									repaymentPlan.setMonthMoney(planList.get(i)
											.getMonthMoney());
									repaymentPlan.setCorpus(planList.get(i)
											.getCorpus());
									repaymentPlan.setIntereat(planList.get(i)
											.getIntereat());
									repaymentPlan.setLatePenalty(0.0);
									repaymentPlan.setServiceFee(planList.get(i)
											.getServiceFee());//服务费
								} else if (i + 1 == Integer.parseInt(loanTerm)) {
									if (i + 1 == planList.size()) {
										repaymentPlan.setMonthMoney(planList
												.get(i).getMonthMoney());
										repaymentPlan.setCorpus(planList.get(i)
												.getCorpus());
										repaymentPlan.setServiceFee(planList.get(i)
												.getServiceFee());//服务费
									} else {
										double serviceMoney = InterestAccrual.getServiceMoney(planList.get(i).getMoney(), planList.get(i).getServiceMoney(), planList.get(i).getLoanTerm(), Integer.parseInt(loanTerm));
										//如果是韭农贷或者惠牧贷测不退还服务费
										if(agriculturalType!=null && ("韭农贷".equals(agriculturalType)||"惠牧贷".equals(agriculturalType))){
										 serviceMoney = 0.0;
										}
										//提前还款的金额=合同金额-前n月还款的本金-退还的服务费
										double repayMoney=planList.get(i).getMoney() - shengyu-serviceMoney;
										repaymentPlan
												.setMonthMoney(ForkloansCompute.round(planList.get(i).getMonthMoney()+ repayMoney,2));
										repaymentPlan
												.setCorpus(ForkloansCompute.round(planList.get(i).getCorpus()+ repayMoney,2));										
										repaymentPlan.setServiceFee(planList.get(i)
												.getServiceFee());//服务费
										repaymentPlan.setReturnMoney(serviceMoney);//应退还服务费
									}
									repaymentPlan.setIntereat(planList.get(i)
											.getIntereat());
									repaymentPlan.setLatePenalty(0.0);
								} else {
									repaymentPlan.setMonthMoney(0);
									repaymentPlan.setCorpus(0);
									repaymentPlan.setIntereat(0);
									repaymentPlan.setLatePenalty(0);
									repaymentPlan.setServiceFee(0);
								}
								repaymentlist.add(repaymentPlan);
							}
							error = "sucess";
							model.addAttribute("nowDate", new Date());
							model.addAttribute("startTime", startTime);
						} else {
							error = "loanTermError";
						}
					} else {
						error = "contractIdNull";
					}
				} else {
					error = "loanTermNull";
				}
			}
			model.addAttribute("url", "ruralfinance/leekPrepayment");
			model.addAttribute("pageInfo", repaymentlist);
			model.addAttribute("contractId", contractId);
			model.addAttribute("loanTerm", loanTerm);
			model.addAttribute("str", str);
			model.addAttribute("error", error);
			return "ruralfinance/leekPrepayment";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 等额本息提前还款处理
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/ruralfinance/editLeekPrepayment")
	public void editLeekPrepayment(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String contractId = request.getParameter("contractId");
		String loanTerm = request.getParameter("loanTerm");
		String settle = request.getParameter("settle");
		try {
			if (contractId != null && !contractId.equals("")) {
				if (loanTerm != null && !loanTerm.equals("")) {
			       //调用农贷等额本息提前还款的处理方法
					if (loaninfoService.editLeekPrepayment(contractId,
							loanTerm, settle)) {
						response.getWriter().print("ok");
					} else {
						response.getWriter().print("fail");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				e1.printStackTrace();

			}
		}
	}

}