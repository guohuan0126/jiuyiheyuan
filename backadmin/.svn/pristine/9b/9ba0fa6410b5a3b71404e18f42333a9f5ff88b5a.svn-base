package com.duanrong.newadmin.controller.ruralfinance;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureDebitRecords;
import com.duanrong.business.ruralfinance.model.AgricultureTimelyloansPrepayment;
import com.duanrong.business.ruralfinance.service.AgricultureDebitRecordsService;
import com.duanrong.business.ruralfinance.service.AgricultureTimelyloansPrepaymentService;
import com.duanrong.newadmin.controller.BaseController;
@Controller
public class AgricultureDebitRecordsController extends BaseController {

	@Resource
	private AgricultureDebitRecordsService<AgricultureDebitRecords> debitRecordsService;
	@Resource
	private AgricultureTimelyloansPrepaymentService agricultureTimelyloansPrepaymentService;
	/**
	 * 中金扣款记录查询
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/agricultural/zhongjindebitRecords")
	public String agricultureLoaninfo(HttpServletResponse response,
			HttpServletRequest request,Model model){
		//查询所有
		
				String pageNo = request.getParameter("pageNo");
				String pageSize = "30";
				if (pageNo == null) {
					pageNo = "1";
				}
				String uploadExcelId = request.getParameter("uploadExcelId");
				String start=request.getParameter("start");
				String end=request.getParameter("end");
				String status=request.getParameter("status");
				String id=request.getParameter("name");
				String flag=request.getParameter("flag");
				Map<String,Object>params=new HashMap<String, Object>();
				String str="";
				if (uploadExcelId != null && !uploadExcelId.equals("")) {
					str += "&uploadExcelId=" + uploadExcelId;
					params.put("uploadExcelId",uploadExcelId );
				}	
				if (flag != null && !flag.equals("")) {
					str += "&flag=" + flag;
					params.put("flag",flag );
				}				
				if (start != null && !start.equals("")) {
					str += "&start=" + start;
					params.put("start",start );
				}
				if (end != null && !end.equals("")) {
					str += "&end=" + end;
					params.put("end",end+"23:59" );
				}
				if (status != null && !status.equals("")) {
					str += "&status=" + status;
					params.put("status",status );
				}
				if (id != null && !id.equals("")) {
					str += "&id=" + id;
					params.put("id",id );
				}	
				PageInfo<AgricultureDebitRecords> pageInfo =debitRecordsService.readAgricultureDebitRecords(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize), params);
				model.addAttribute("pageInfo", pageInfo);				
				model.addAttribute("start",start );
				model.addAttribute("end",end );
				model.addAttribute("id",id);
				model.addAttribute("status",status);				
				model.addAttribute("str", str);
				model.addAttribute("flag", flag);
				model.addAttribute("uploadExcelId",uploadExcelId);
				return "ruralfinance/agricultureDebitRecords";   
	}
	/**
	 * 提前还款项目查询
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/agricultural/timelyloansPrepayment")
	public String timelyloansPrepayment(HttpServletResponse response,
			HttpServletRequest request,Model model){
		//查询所有		
				String pageNo = request.getParameter("pageNo");
				String pageSize = "30";
				if (pageNo == null) {
					pageNo = "1";
				}
				String uploadExcelId = request.getParameter("uploadExcelId");
				String start=request.getParameter("start");
				String end=request.getParameter("end");
				String id=request.getParameter("name");
				String flag=request.getParameter("flag");
				Map<String,Object>params=new HashMap<String, Object>();
				String str="";
				if (uploadExcelId != null && !uploadExcelId.equals("")) {
					str += "&uploadExcelId=" + uploadExcelId;
					params.put("uploadExcelId",uploadExcelId );
				}	
				if (flag != null && !flag.equals("")) {
					str += "&flag=" + flag;
					params.put("flag",flag );
				}				
				if (start != null && !start.equals("")) {
					str += "&start=" + start;
					params.put("start",start );
				}
				if (end != null && !end.equals("")) {
					str += "&end=" + end;
					params.put("end",end+"23:59" );
				}
				if (id != null && !id.equals("")) {
					str += "&id=" + id;
					params.put("id",id );
				}	
				PageInfo<AgricultureTimelyloansPrepayment> pageInfo =agricultureTimelyloansPrepaymentService.readAgricultureTimelyPrepayment(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize), params);
				model.addAttribute("pageInfo", pageInfo);				
				model.addAttribute("start",start );
				model.addAttribute("end",end );
				model.addAttribute("id",id);				
				model.addAttribute("str", str);
				model.addAttribute("flag", flag);
				model.addAttribute("uploadExcelId",uploadExcelId);
				return "ruralfinance/agricultureTimelyPrepayment";   
	}
}

