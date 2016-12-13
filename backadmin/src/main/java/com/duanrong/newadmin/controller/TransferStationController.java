package com.duanrong.newadmin.controller;

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

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.False;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.Log;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.service.DemandTreasureLoanService;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.LoanExport;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;
import com.duanrong.business.ruralfinance.model.AgricultureTimelyloansPrepayment;
import com.duanrong.business.ruralfinance.model.AgricultureZhongjinbank;
import com.duanrong.business.ruralfinance.model.LoanerinfoExport;
import com.duanrong.business.ruralfinance.model.LoaninfoExport;
import com.duanrong.business.ruralfinance.model.Template;
import com.duanrong.business.ruralfinance.model.ZJRepaymentExport;
import com.duanrong.business.ruralfinance.service.AgricultureLoanerInfoService;
import com.duanrong.business.ruralfinance.service.AgricultureRepaymentPlanService;
import com.duanrong.business.ruralfinance.service.AgricultureTimelyloansPrepaymentService;
import com.duanrong.business.ruralfinance.service.AgricultureZhongjinbankService;
import com.duanrong.business.transferstation.model.TransferStation;
import com.duanrong.business.transferstation.model.TransferStationExport;
import com.duanrong.business.transferstation.model.TransferStationForkLoans;
import com.duanrong.business.transferstation.service.TransferStationService;
import com.duanrong.newadmin.controller.BaseController;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.FormUtil;
import com.duanrong.newadmin.utility.IdUtil;
import com.duanrong.newadmin.utility.RegexInput;

@Controller
public class TransferStationController extends BaseController {

	@Resource
	private AgricultureLoanerInfoService loaninfoService;
	@Resource
	private TransferStationService transferStationService;
	
	private static final byte[] lock = new byte[0];
	private static final byte[] lockloan = new byte[0];
	@Resource
	Log log;

	/**
	 * 车贷中转站基本信息查询
	 * *
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/transferstation/vehicleTransferstation")
	public String Transferstation(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		// log.errLog("", "", 1);
		// 查询所有
		try {		
		String pageNo = request.getParameter("pageNo");
		String pageSize = "20";
		if (pageNo == null) {
			pageNo = "1";
		}
		String contractId = request.getParameter("contractId");// 合同编号
		String start = request.getParameter("start");// 开始时间
		String end = request.getParameter("end");// 结束时间
		String borrowerName = request.getParameter("borrowerName");// 借款人
		String repayType = request.getParameter("repayType");// 还款方式
		String accountingDepartment = request
				.getParameter("accountingDepartment");// 核算公司
		String channelName = request.getParameter("channelName");// 渠道分配
		String onlineBorrower = request.getParameter("onlineBorrower");//线上固定借款人
		String allocationStatus = request.getParameter("allocationStatus");// 是否分配
		String deadline = request.getParameter("deadline");// 借款期限
		String institutionName = request.getParameter("institutionName");// 机构名称
		String remark = request.getParameter("remark");// 备注
		String guaranteeType = request.getParameter("guaranteeType");// 担保方式
		String remarkVehicle = request.getParameter("remarkVehicle");// 新增/展期
		String itemAddress= request.getParameter("itemAddress");//项目地点
		Map<String, Object> params = new HashMap<String, Object>();
		String str = "";
		if (contractId != null && !contractId.equals("")) {
			str += "&contractId=" + contractId;
			params.put("contractId", contractId);
		}
		if (borrowerName != null && !borrowerName.equals("")) {
			borrowerName=URLDecoder.decode(borrowerName,"UTF-8");
			str += "&borrowerName=" + borrowerName;
			params.put("borrowerName", borrowerName);
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
		if (accountingDepartment != null && !accountingDepartment.equals("")) {
			str += "&accountingDepartment=" + accountingDepartment;
			params.put("accountingDepartment", accountingDepartment);
		}
		if (repayType != null && !repayType.equals("")) {
			repayType=URLDecoder.decode(repayType,"UTF-8");
			str += "&repayType=" + repayType;
			params.put("repayType", repayType);
		}
		if (channelName != null && !channelName.equals("")) {
			channelName=URLDecoder.decode(channelName,"UTF-8");
			str += "&channelName=" + channelName;
			params.put("channelName", channelName);
		}
		if (allocationStatus != null && !allocationStatus.equals("")) {
			str += "&allocationStatus=" + allocationStatus;
		} else {
			allocationStatus = "0";
		}
		params.put("allocationStatus", allocationStatus);
		if (deadline != null && !deadline.equals("")) {
			str += "&deadline=" + deadline;
			params.put("deadline", deadline);
		}
		if (institutionName != null && !institutionName.equals("")) {
			institutionName=URLDecoder.decode(institutionName,"UTF-8");
			str += "&institutionName=" + institutionName;
			params.put("institutionName", institutionName);
		}
		if (remark != null && !remark.equals("")) {
			remark=URLDecoder.decode(remark,"UTF-8");
			str += "&remark=" + remark;
			params.put("remark", remark);
		}
		if (guaranteeType != null && !guaranteeType.equals("")) {
			str += "&guaranteeType=" + guaranteeType;
			params.put("guaranteeType", guaranteeType);
		}
		if (remarkVehicle != null && !remarkVehicle.equals("")) {
			str += "&remarkVehicle=" + remarkVehicle;
			params.put("remarkVehicle", remarkVehicle);
		}
		if (itemAddress != null && !itemAddress.equals("")) {
			str += "&itemAddress=" + itemAddress;
			params.put("itemAddress", itemAddress);
		}
		if (onlineBorrower != null && !onlineBorrower.equals("")) {
			str += "&onlineBorrower=" + onlineBorrower;
			params.put("onlineBorrower", onlineBorrower);
		}
		PageInfo<TransferStation> pageInfo = transferStationService
				.readTransferStation(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("contractId", contractId);
		model.addAttribute("borrowerName", borrowerName);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("accountingDepartment", accountingDepartment);
		model.addAttribute("repayType", repayType);
		model.addAttribute("channelName", channelName);
		model.addAttribute("allocationStatus", allocationStatus);
		model.addAttribute("deadline", deadline);
		model.addAttribute("institutionName", institutionName);
		model.addAttribute("remark", remark);
		model.addAttribute("onlineBorrower", onlineBorrower);
		model.addAttribute("guaranteeType", guaranteeType);
		model.addAttribute("remarkVehicle", remarkVehicle);
		model.addAttribute("itemAddress", itemAddress);
		model.addAttribute("str", str);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "transferstation/vehicleTransferstation";
	}

	/**
	 * 删除中转站项目以及对应的车辆信息和图片信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/transferstation/delTransferLoan")
	public String delTransferLoan(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		try {
			if (id != null && !"".equals("id")) {
				transferStationService.delTransferLoan(id);
			} else {
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"om.duanrong.newadmin.controller.TransferStationController.delTransferLoan()",
					e);
			return "fail";
		}
		return "ok";
	}
	/**
	 * 释放中转站机构线下和线下资产
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/transferstation/releaseTransferLoan")
	public String releaseTransferLoan(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		try {
			if (id != null && !"".equals("id")) {
				String ids[] = new String[1];
				ids[0]=id;
				Map<String, Object> demandParam = new HashMap<>();
				demandParam.put("allocationStatus", 0);
				demandParam.put("channelName", "");
				demandParam.put("institutionName", "");
				demandParam.put("arr", ids);
				transferStationService.updateBatchTransferStation(demandParam);
			} else {
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("释放中转站资产异常","异常中转站资源id"+id+",异常信息"+e);
			return "fail";
		}
		return "ok";
	}

	/**
	 * 分配渠道
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/transferstation/allocationLoans")
	@ResponseBody
	public String allocationLoans(
			@RequestParam(value = "ids[]", required = true) String[] ids,
			@RequestParam(value = "qudao", required = true) String channelName,
			@RequestParam(value = "institutionName", required = false) String institutionName,
			@RequestParam(value = "deadline", required = true) String deadline,
			@RequestParam(value = "money", required = true) String money,
			@RequestParam(value = "company", required = true) String company,
			@RequestParam(value = "isunpack", required = false) String isunpack,
			@RequestParam(value = "isnewSubject", required = false) String isnewSubject,
			@RequestParam(value = "borrowerId", required = true) String borrowerId,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
		synchronized (lockloan) {
			UserCookie getLoginUser = GetLoginUser(request, response);
			String result = "fail";
			if (ids != null && ids.length > 0) { 
				if (channelName != null && "活期".equals(channelName)) { // 活期渠道
					result = transferStationService.allocationDemandTreasure(ids, getLoginUser);
				} else if (channelName != null && ("机构线下".equals(channelName)||"线下".equals(channelName))) {
					// 机构线下
					Map<String, Object> demandParam = new HashMap<>();
					demandParam.put("allocationStatus", 1);
					
						demandParam.put("channelName", channelName);
					
					demandParam.put("institutionName", institutionName);
					demandParam.put("arr", ids);
					transferStationService.updateBatchTransferStation(demandParam);
					result = "success";
				} else if (channelName != null
						&& ("定期".equals(channelName) || "机构线上"
								.equals(channelName))) {
					// 定期渠道
					if(isunpack!=null && "isunpack".equals(isunpack)){
						//单人单车打包
						double money1=0.0;
						if(money!=null && !"".equals(money)){
							money1=Double.valueOf(money);
						}	
						
					result = transferStationService.allocationVehicles(ids, getLoginUser, channelName, institutionName, deadline,money1, company, isnewSubject,borrowerId);
					}else{
					result = transferStationService.allocationLoan(ids,
							getLoginUser, channelName, institutionName,isnewSubject);}
				}
			}	
			return result;
		}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	/**
	 * 备注可推送渠道
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/transferstation/remarkChannel")
	public String remarkChannel(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String transactionId = request.getParameter("transactionId");
		String ktsjigou = request.getParameter("ktsjigou");
		try {
			if (StringUtils.isNotBlank(transactionId)) {
				// 项目添加到活期宝之后，修改中转站项目的分配状态
				Map<String, Object> demandParam = new HashMap<>();
				demandParam.put("organizationRemark", ktsjigou);
				demandParam.put("id", transactionId);
				transferStationService.updateTransferStation(demandParam);
			} else {
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"om.duanrong.newadmin.controller.TransferStationController.remarkChannel()",
					e);
			return "fail";
		}
		return "ok";
	}
	/**
	 * 中转站等额本息子标查询列表
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/transferstation/childLoansList")
	public String childLoansList(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		String pageNo = request.getParameter("pageNo");
		String pageSize = "70";
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
		String accountingDepartment=request.getParameter("accountingDepartment");
		String orderbyMoney=request.getParameter("orderbyMoney");
		String onlineBorrower = request.getParameter("onlineBorrower");//线上固定借款人
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
		if (accountingDepartment != null && !accountingDepartment.equals("")) {
			str += "&accountingDepartment=" + accountingDepartment;
			params.put("accountingDepartment", accountingDepartment);
		}
		if (orderbyMoney != null && !orderbyMoney.equals("")) {
			str += "&orderbyMoney=" + orderbyMoney;
			params.put("orderbyMoney", orderbyMoney);
		}
		if (onlineBorrower != null && !onlineBorrower.equals("")) {
			str += "&onlineBorrower=" + onlineBorrower;
			params.put("onlineBorrower", onlineBorrower);
		}
		PageInfo<TransferStationForkLoans> pageInfo = transferStationService.
				readTransferStationForkLoans(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize), params);
		model.addAttribute("loanId", loanId);
		model.addAttribute("contractId", contractId);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("forkStatus", forkStatus);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("loanTerm", loanTerm);
		model.addAttribute("accountingDepartment", accountingDepartment);
		model.addAttribute("orderbyMoney", orderbyMoney);
		model.addAttribute("onlineBorrower", onlineBorrower);
		model.addAttribute("str", str);
		return "transferstation/childLoansList";
	}
	/**
	 * 打包成多人多车分配到定期项目 
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/transferstation/packedForkLoans")
	@ResponseBody
	public String packedForkLoans(
			@RequestParam(value = "ids[]", required = true) String[] ids,
			@RequestParam(value = "qudao", required = true) String channelName,
			@RequestParam(value = "institutionName", required = false) String institutionName,
			@RequestParam(value = "deadline", required = true) String deadline,
			@RequestParam(value = "money", required = true) String money,
			@RequestParam(value = "company", required = true) String company,
			@RequestParam(value = "isnewSubject", required = false) String isnewSubject,
			@RequestParam(value = "forkIds[]", required = true) String[] forkIds,
			@RequestParam(value = "borrowerId", required = true) String borrowerId,			
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			
			UserCookie getLoginUser = GetLoginUser(request, response);
			String result = "fail";
			if (forkIds != null && forkIds.length > 0) { 
				double money1=0.0;
				if(money!=null && !"".equals(money)){
					money1=Double.valueOf(money);
				}				
				result = transferStationService.allocationForkLoan(ids, getLoginUser, channelName, institutionName, deadline, forkIds,money1, company,isnewSubject,borrowerId);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	/**
	 * 中转站里面的导出
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/transferstation/exportTransferstation")
	public void repayExport(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String contractId = request.getParameter("contractId");// 合同编号
		String start = request.getParameter("start");// 开始时间
		String end = request.getParameter("end");// 结束时间
		String borrowerName = request.getParameter("borrowerName");// 借款人
		String repayType = request.getParameter("repayType");// 还款方式
		String accountingDepartment = request
				.getParameter("accountingDepartment");// 核算公司
		String channelName = request.getParameter("channelName");// 渠道分配
		String allocationStatus = request.getParameter("allocationStatus");// 是否分配
		String deadline = request.getParameter("deadline");// 借款期限
		String institutionName = request.getParameter("institutionName");// 机构名称
		String remark = request.getParameter("remark");// 备注
		String guaranteeType = request.getParameter("guaranteeType");// 担保方式
		String remarkVehicle = request.getParameter("remarkVehicle");// 新增/展期
		String itemAddress= request.getParameter("itemAddress");//项目地点
		Map<String, Object> params = new HashMap<String, Object>();	
		if (contractId != null && !contractId.equals("")) {			
			params.put("contractId", contractId);
		}
		if (borrowerName != null && !borrowerName.equals("")) {
			borrowerName=URLDecoder.decode(borrowerName,"UTF-8");
			params.put("borrowerName", borrowerName);
		}
		if (start != null && !start.equals("")) {
			start=URLDecoder.decode(start,"UTF-8");
			params.put("start", start);
		}
		if (end != null && !end.equals("")) {
			end=URLDecoder.decode(end,"UTF-8");
			params.put("end", end + "23:59");
		}
		if (accountingDepartment != null && !accountingDepartment.equals("")) {
			params.put("accountingDepartment", accountingDepartment);
		}
		if (repayType != null && !repayType.equals("")) {
			repayType=URLDecoder.decode(repayType,"UTF-8");
			params.put("repayType", repayType);
		}
		if (channelName != null && !channelName.equals("")) {
			channelName=URLDecoder.decode(channelName,"UTF-8");
			params.put("channelName", channelName);
		}
		if (allocationStatus != null && !allocationStatus.equals("")) {
		
		} else {
			allocationStatus = "0";
		}
		params.put("allocationStatus", allocationStatus);
		if (deadline != null && !deadline.equals("")) {
			params.put("deadline", deadline);
		}
		if (institutionName != null && !institutionName.equals("")) {
			institutionName=URLDecoder.decode(institutionName,"UTF-8");
			params.put("institutionName", institutionName);
		}
		if (remark != null && !remark.equals("")) {
			remark=URLDecoder.decode(remark,"UTF-8");
			params.put("remark", remark);
		}
		if (guaranteeType != null && !guaranteeType.equals("")) {
			params.put("guaranteeType", guaranteeType);
		}
		if (remarkVehicle != null && !remarkVehicle.equals("")) {
			params.put("remarkVehicle", remarkVehicle);
		}
		if (itemAddress != null && !itemAddress.equals("")) {
			params.put("itemAddress", itemAddress);
		}
		try {
			List<TransferStation> transferInfo = transferStationService.readTransferStationinfo(params);		  
			List<TransferStationExport> list = new ArrayList<>();
			for (TransferStation transfer : transferInfo) {
				TransferStationExport export = new TransferStationExport();				
				if (transfer.getAccountingDepartment() == 0) {
					export.setAccountingDepartment("齐海");
				} else {
					export.setAccountingDepartment("久亿");
				}
				if ("A".equals(transfer.getVehicleInfo().getGuaranteeType())) {
					export.setGuaranteeType("质押");
				} else {
					export.setGuaranteeType("抵押");
				}
				if ("A".equals(transfer.getVehicleInfo().getRemark())) {
					export.setRemark("新增");
				} else {
					export.setRemark("展期");
				}
				export.setBorrowerName(transfer.getVehicleInfo().getBorrowerName());
				export.setBorrowerIdCard(transfer.getVehicleInfo().getBorrowerIdCard());
				export.setMoney(transfer.getMoney());
				export.setDeadline(String.valueOf(transfer.getDeadline())+transfer.getOperationType());
				export.setRate(transfer.getRate());
				export.setRepayType(transfer.getRepayType());
				export.setLoanType(transfer.getLoanType());
				export.setContractId(transfer.getContractId());
				export.setChannelName(transfer.getChannelName());
				export.setInstitutionName(transfer.getInstitutionName());
			    export.setItemAddress(transfer.getVehicleInfo().getItemAddress());	
			    export.setYacarAndGps(transfer.getVehicleInfo().getYaCarAndGPS());
			    export.setOrganizationRemark(transfer.getOrganizationRemark());
				list.add(export);
			}
			String t = "中转站车贷信息";
			Map<String, String> title = new LinkedHashMap<>();
			title.put("contractId", "合同编号");
			title.put("borrowerName", "借款人");			
			title.put("borrowerIdCard", "身份证号");
			title.put("money", "借款金额");
			title.put("deadline", "借款期限");
			title.put("rate", "利率");
			title.put("repayType", "还款方式");
			title.put("loanType", "项目类型");
			title.put("channelName", "发行渠道");
			title.put("institutionName", "机构名称");
			title.put("accountingDepartment", "核算公司");
			title.put("guaranteeType", "担保方式");
			title.put("yacarAndGps", "分期/全款");
			title.put("itemAddress", "项目地点");
			title.put("remark", "新增展期");
			title.put("organizationRemark", "备注可推送机构");
			int[] style = { 25, 15,25,15, 15, 15,20, 15, 25,25,25,15,15,25,15,20};
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "中转站车贷信息" + fileName + ".xls";
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
}