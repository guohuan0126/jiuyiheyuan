 package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import util.Log;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;
import base.schedule.constants.ScheduleConstants;

import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.demand.model.AvailableMoneyRecord;
import com.duanrong.business.demand.model.DateRate;
import com.duanrong.business.demand.model.DemandTreasureAccount;
import com.duanrong.business.demand.model.DemandTreasureBill;
import com.duanrong.business.demand.model.Demandtreasure;
import com.duanrong.business.demand.model.DemandtreasureGuOut;
import com.duanrong.business.demand.model.DemandtreasureGuOutDetail;
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.business.demand.service.AvailableMoneyRecordService;
import com.duanrong.business.demand.service.DateRateService;
import com.duanrong.business.demand.service.DemandTreasureBillService;
import com.duanrong.business.demand.service.DemandTreasureLoanService;
import com.duanrong.business.demand.service.DemandtreasureGuOutDetailService;
import com.duanrong.business.demand.service.DemandtreasureGuOutService;
import com.duanrong.business.demand.service.DemandtreasureService;
import com.duanrong.business.demand.service.DemandtreasureTransferInService;
import com.duanrong.business.demand.service.DemandtreasureTransferOutService;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.service.AgricultureForkLoansService;
import com.duanrong.business.ruralfinance.service.AgricultureLoanerInfoService;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.trusteeship.model.TrusteeshipQuery;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.controllhelper.SendData2YeePay;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.newadmin.utility.FormUtil;
import com.duanrong.newadmin.utility.ToType;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.LoadConstantProterties2;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 首页C层
 * 
 * @author wangjing
 * @date 2014-11-19下午2:49:41
 */
@Controller
public class DemandController extends BaseController {

	@Resource
	Log log;
	@Resource
	AvailableMoneyRecordService availableMoneyRecordService;
	@Resource
	DemandtreasureService demandtreasureService;
	@Resource
	DateRateService dayRateService;
	@Resource
	DemandtreasureTransferInService demandtreasureTransferInService;
	@Resource
	OperaRecordService operaRecordService;

	@Resource
	StdScheduler scheduler;

	@Resource
	DemandTreasureBillService demandTreasureBillService;

	@Resource
	UserService userService;

	@Resource
	DemandtreasureTransferOutService demandtreasureTransferOutService;
	@Resource
	DemandtreasureGuOutService demandtreasureGuOutService;
	@Resource
	DemandtreasureGuOutDetailService demandtreasureGuOutDetailService;

	@Resource
	UserAccountService userAccountService;
	
	@Resource
	DemandTreasureLoanService demandTreasureLoanService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	AgricultureForkLoansService agricultureForkLoansService;

	@Resource
	AgricultureLoanerInfoService agricultureLoanerInfoService;

	private static final byte[] lock = new byte[0];

	@RequestMapping(value = "/demand/procut")
	public String procut(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String pageNo = request.getParameter("pageNo");   
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			PageInfo<AvailableMoneyRecord> pageInfo = availableMoneyRecordService.readPageInfo(
					Integer.parseInt(pageNo), 10);
			List<Demandtreasure> list = demandtreasureService.readAll();
			Demandtreasure demond = null;
			if (list != null && list.size() > 0) {
				demond = list.get(0);
			} else {
				demond = new Demandtreasure();
				demond.setName("活期宝");
				demond.setStartTime(0);
				demond.setEndTime(0);
				demond.setOutNumber(0);
			}
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("demond", demond);
			return "demand/procutList";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.procut()",
					e);
		}
		return null;
	}

	// 导出活期宝数据
	@RequestMapping(value = "/demand/procutListExport")
	public void demandListExport(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			PageInfo<AvailableMoneyRecord> pageInfo = availableMoneyRecordService.readPageInfo(1,
					9999999);
			List<AvailableMoneyRecord> results = pageInfo.getResults();
			for (AvailableMoneyRecord availableMoneyRecord : results) {
				if (availableMoneyRecord.getStatus() == 1) {
					availableMoneyRecord.setStatusExport("执行成功");
				} else if (availableMoneyRecord.getStatus() == 2) {
					availableMoneyRecord.setStatusExport("未执行");
				} else {
					availableMoneyRecord.setStatusExport("执行失败");
				}
				if (availableMoneyRecord.getType().equals("add")) {
					availableMoneyRecord.setTypeExport("添加资产");
				} else if (availableMoneyRecord.getType().equals("sub")) {
					availableMoneyRecord.setTypeExport("减少资产");
				} else if (availableMoneyRecord.getType().equals("expired")) {
					availableMoneyRecord.setTypeExport("续投资产");
				} else {
					availableMoneyRecord.setTypeExport("未知");
				}
			}
			Map<String, String> title = new LinkedHashMap<>();
			title.put("id", "编号");
			title.put("expectTime", "预计执行日期");
			title.put("statusExport", "执行状态");
			title.put("typeExport", "状态");
			title.put("begintime", "更新日期");
			title.put("money", "更新金额");
			title.put("endTime", "融资完成时间");
			int[] style = { 40, 30, 30, 30, 30, 30, 30 };
			// 文件名
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			String t = "活期宝基本信息";
			excelConvertor.excelExport(pageInfo.getResults(), title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.userbillListExport()",
					e);
		}
	}

	@RequestMapping(value = "/demand/save/{type}")
	public void save(HttpServletResponse response, HttpServletRequest request,
			@PathVariable String type) {
		String investMaxmoney = request.getParameter("investMaxmoney");
		String availableMoney = request.getParameter("availableMoney");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String outNumber = request.getParameter("outNumber");
		String name = request.getParameter("name");
		try {
			List<Demandtreasure> list = demandtreasureService.readAll();
			if (list == null || list.size() == 0) {
				if (type.equals("saveminMoney")) {
					response.getWriter().print("min");
					return;
				}
				Demandtreasure demandtreasure = new Demandtreasure();
				if (type.equals("saveMoney")) {
					demandtreasure.setAvailableMoney(Double
							.parseDouble(availableMoney));
				} else {
					demandtreasure.setInvestMaxmoney(Double
							.parseDouble(investMaxmoney));
				}
				demandtreasure.setCreateTime(new Date());
				demandtreasure.setId(IdGenerator.randomUUID());
				demandtreasure.setName(name);
				demandtreasure.setUpdateTime(new Date());
				demandtreasureService.insert(demandtreasure);
			} else {
				Demandtreasure demand = new Demandtreasure();
				Demandtreasure d = list.get(0);
				if (type.equals("saveMoney")) {
					List<DemandtreasureTransferIn> ins = demandtreasureTransferInService
							.readStatus();
					if (ins != null && ins.size() != 0) {
						response.getWriter().print("status");
						return;
					}
					demand.setAvailableMoney(Double.parseDouble(availableMoney));
				} else if (type.equals("savemaxMoney")) {
					demand.setInvestMaxmoney(Double.parseDouble(investMaxmoney));
				} else {
					demand.setStartTime(Integer.parseInt(startTime));
					demand.setEndTime(Integer.parseInt(endTime));
					demand.setOutNumber(Integer.parseInt(outNumber));
				}
				demand.setUpdateTime(new Date());
				demand.setId(d.getId());
				demandtreasureService.update(demand);
			}
			response.getWriter().print("ok");
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.errLog("活期宝基本信息保存", e);
		}

	}

	@ResponseBody
	@RequestMapping(value = "/demand/record/sub")
	public String subAvailableMoneyRecord(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie loginUser = GetLoginUser(request, response);
		String money = request.getParameter("money");
		AvailableMoneyRecord availableMoneyRecord = new AvailableMoneyRecord();
		availableMoneyRecord.setMoney(Double.parseDouble(money));
		availableMoneyRecord.setBegintime(new Date());
		availableMoneyRecord.setStatus(1);
		availableMoneyRecord.setOperateorid(loginUser.getUserId());
		availableMoneyRecord.setId(IdGenerator.randomUUID());
		availableMoneyRecord.setType("sub");
		availableMoneyRecordService.insert(availableMoneyRecord);
		// 活期宝可用资产
		double usedMoney = availableMoneyRecordService.readUserUsedMoney();
		double DemandTreasureMoney = usedMoney - Double.parseDouble(money);
		availableMoneyRecordService.updateDemandTreasure(DemandTreasureMoney);
		return "ok";
	}

	@RequestMapping(value = "/demand/record/{type}")
	public String addAvailableMoneyRecord(HttpServletResponse response,
			HttpServletRequest request, Model model, @PathVariable String type)
			throws ParseException {
		if (type.equals("toAdd")) {
			request.setAttribute("tp", "add");
			return "demand/record";
		}
		UserCookie loginUser = GetLoginUser(request, response);
		if (type.equals("add") || type.equals("expired")) {
			synchronized (lock) {
				String time = request.getParameter("time");
				try {

					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					if(time==null ||"".equals(time)){					
					response.getWriter().print("time");
					return null; }
					if (df.parse(time).getTime() < new Date().getTime()) {
						response.getWriter().print("time");
						return null;
					}
					AvailableMoneyRecord record = availableMoneyRecordService
							.readRecord();
					// 判断有没有未执行的资产，只允许添加一条未执行的资产
					if (record.getExpectTime().getTime() > new Date().getTime()) {

						response.getWriter().print("status");
						return null;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String attornMoney = request.getParameter("attornMoney");// 转让总金额
				String totalMoney = request.getParameter("totalMoney");// 新资产总金额
				if (totalMoney == null || totalMoney.equals("")) {
					totalMoney = "0";
				}
				double expiredMoney = Double.parseDouble(request
						.getParameter("expiredMoney"));// 今日项目到期总资产
				double availableMoney = Double.parseDouble(attornMoney)
						+ Double.parseDouble(totalMoney);// 开放总金额

				Date date = null;
				if (time == null || time.trim().equals("")) {
					date = new Date();
				} else {
					date = DateUtil.StringToDate(time);
				}
				double xutouMoney = expiredMoney;// 续投金额
				double openmoney = 0;
				if (availableMoney - xutouMoney <= 0) {
					openmoney = 0;
				} else {
					openmoney = availableMoney - xutouMoney;
				}
				AvailableMoneyRecord availableMoneyRecord = new AvailableMoneyRecord();
				availableMoneyRecord.setMoney(availableMoney);
				availableMoneyRecord.setBegintime(date);
				availableMoneyRecord.setStatus(2);
				availableMoneyRecord.setOperateorid(loginUser.getUserId());
				availableMoneyRecord.setExpectTime(date);
				availableMoneyRecord.setId(IdGenerator.randomUUID());
				availableMoneyRecord.setTotalMoney(availableMoney);// 总金额
				availableMoneyRecord.setTransferMoney(Double
						.parseDouble(attornMoney));// 转让金额
				availableMoneyRecord
						.setAddMoney(Double.parseDouble(totalMoney));// 新资产金额
				if (type.equals("expired")) {
					availableMoneyRecord.setExpiredMoney(expiredMoney);
					availableMoneyRecord.setOpendMoney(openmoney);
					availableMoneyRecord.setType("expired");
					availableMoneyRecord.setFlag("expired");
				} else {
					availableMoneyRecord.setExpiredMoney(0);
					availableMoneyRecord.setOpendMoney(availableMoney);
					availableMoneyRecord.setRealExpiredMoney(0);
					availableMoneyRecord.setType("add");
					double attornMoney1 = 0;
					if (attornMoney != null && !attornMoney.equals("")) {
						attornMoney1 = Double.parseDouble(attornMoney);
					}
					// 如果选中转让资产进行更新金额操作
					if (attornMoney1 <= 0) {
						availableMoneyRecord.setFlag("newAssets");
					} else {
						availableMoneyRecord.setFlag("attorn");
					}
				}
				try {
					if (Double.parseDouble(attornMoney) == 0
							&& Double.parseDouble(totalMoney) == 0) {
						response.getWriter().print("mistake");
					}
					if (availableMoneyRecordService.saveRecord(
							availableMoneyRecord, request)) {
						availableMoneyRecordService
								.insert(availableMoneyRecord);
						response.getWriter().print("ok");
					} else {
						response.getWriter().print("error");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (type.equals("toEdit")) {
			String id = request.getParameter("id");
			AvailableMoneyRecord record = availableMoneyRecordService.read(id);
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Map<String, Object> treasureparam = new HashMap<>();
			treasureparam.put("type", "money");
			List<DemandtreasureLoan> list = demandTreasureLoanService
					.readDemandtreasureLoan(treasureparam);
			String nowdate = format.format(new Date());
			double totalmoney = demandTreasureLoanService.readAssignmentMoney();
			List<DemandtreasureLoan> transferLoan = demandTreasureLoanService
					.readTransferDemandtreasureLoan();
			request.setAttribute("tp", "edit");
			request.setAttribute("record", record);
			request.setAttribute("list", list);
			model.addAttribute("transferLoan", transferLoan);
			model.addAttribute("totalmoney", totalmoney);
			model.addAttribute("nowdate", nowdate);
			return "demand/record";
		}
		if (type.equals("toEditType")) {
			String id = request.getParameter("id");
			AvailableMoneyRecord record = availableMoneyRecordService.read(id);
			request.setAttribute("tp", "editType");
			request.setAttribute("record", record);
			return "demand/editType";
		}
		if (type.equals("edit")) {
			String id = request.getParameter("id");
			String availableMoney = request.getParameter("money");
			String time = request.getParameter("expect");
			String attornMoney = request.getParameter("attornMoney");// 转让总金额
			String totalMoney = request.getParameter("totalMoney");// 新增的转让金额+新资产金额
			double newMoney = Double.parseDouble(totalMoney)
					- Double.parseDouble(attornMoney);// 新资产总金额
			AvailableMoneyRecord record = availableMoneyRecordService.read(id);
			double newmoney = Double.parseDouble(availableMoney)
					- record.getMoney() + record.getOpendMoney();
			double openmoney = 0;
			if (Double.parseDouble(availableMoney) - record.getExpiredMoney() <= 0) {
				openmoney = 0;
			} else {
				openmoney = Double.parseDouble(availableMoney)
						- record.getExpiredMoney();
			}
			Date d = record.getExpectTime();
			if (null != d && d.getTime() > new Date().getTime()) {
				if (Double.parseDouble(availableMoney) == 0) {
					record.setEndtime(new Date());
				}
				record.setOperateorid(loginUser.getUserId());
				if (time != null && !time.trim().equals("")) {
					record.setExpectTime(DateUtil.StringToDate(time.trim()));
				}
				double zongmoney = record.getTotalMoney()
						+ Double.parseDouble(totalMoney);
				double addMoney = record.getAddMoney() + newMoney;
				record.setTotalMoney(zongmoney);
				record.setAddMoney(addMoney);
				if (record.getType().equals("expired")) {
					record.setOpendMoney(openmoney);
				} else {
					record.setOpendMoney(newmoney);
				}
				double attornMoney1 = 0;
				if (attornMoney != null && !attornMoney.equals("")) {
					attornMoney1 = Double.parseDouble(attornMoney);
				}
				record.setMoney(Double.parseDouble(availableMoney));
				// 如果选中转让资产进行更新金额操作
				if (attornMoney1 <= 0) {
					record.setFlag("newAssets");
				} else {
					record.setFlag("attorn");
					double transferMoney = record.getTransferMoney()
							+ Double.parseDouble(attornMoney);
					record.setTransferMoney(transferMoney);
				}
				try {
					if (availableMoneyRecordService.saveRecord(record, request)) {
						availableMoneyRecordService.update(record);				
						response.getWriter().print("ok");

					} else {
						response.getWriter().print("error");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				try {
					response.getWriter().print("avlid");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (type.equals("editType")) {
			String id = request.getParameter("id");
			String editType = request.getParameter("type");
			AvailableMoneyRecord record = availableMoneyRecordService.read(id);
			Date d = record.getExpectTime();
			if (null != d && d.getTime() > new Date().getTime()
					&& record.getStatus() == 2) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				double ExpiredMoney = demandTreasureLoanService
						.readMoneyBydate(DateUtil.addDay(df.format(new Date()),
								1));// 应该续投资产
				if (record.getType().equals("add")
						&& editType.equals("expired")) {
					double openmoney = 0;
					if (record.getTotalMoney() - ExpiredMoney <= 0) {
						openmoney = 0;
					} else {
						openmoney = record.getTotalMoney() - ExpiredMoney;
					}
					record.setOpendMoney(openmoney);
					record.setExpiredMoney(ExpiredMoney);
					record.setType("expired");
					record.setFlag("expired");
				} else if (record.getType().equals("expired")
						&& editType.equals("add")) {
					record.setOpendMoney(record.getTotalMoney());
					record.setExpiredMoney(0);
					record.setRealExpiredMoney(0);
					record.setType("add");
					record.setFlag("attorn");
				}
				try {
					availableMoneyRecordService.update(record);
					response.getWriter().print("ok");

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					response.getWriter().print("avlid");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		if (type.equals("toSub")) {
			double money = availableMoneyRecordService.readUserUsedMoney();
			model.addAttribute("money", money);
			return "demand/subRecord";
		}
		if (type.equals("del")) {
			String id = request.getParameter("id");
			AvailableMoneyRecord record = availableMoneyRecordService.read(id);
			try {
				if (record.getStatus() != 1) {
					if (record.getFlag().equals("newAssets")) {
						record.setStatus(0);
						record.setOperateorid(loginUser.getUserId());

						JobKey jobKey = JobKey
								.jobKey(record.getId(),
										ScheduleConstants.TriggerGroup.CHECK_DEMAND_OVER_EXPECT_TIME);
						scheduler.deleteJob(jobKey);
						Map<String, Object> turnparams2 = new HashMap<String, Object>();
						turnparams2.put("availableId", id);
						// 查询资产开放里是否有新的资产，如果有就把资产释放
						List<DemandtreasureLoan> demandlist2 = demandTreasureLoanService
								.readByAvailableIdNew(turnparams2);
						if (demandlist2 != null && demandlist2.size() > 0) {
							for (int i = 0; i < demandlist2.size(); i++) {
								// 对选择了的资产进行释放
								Map<String, Object> params = new HashMap<>();
								params.put("validMoney", "0");
								params.put("openStatus", "notopen");
								params.put("openAmount", demandlist2.get(i)
										.getTotalMoney());
								params.put("availableId", "");
								params.put("id", demandlist2.get(i).getId());
								demandTreasureLoanService
										.updateAssignmentStatusById(params);
								//查询父标的id是否为空
								if(StringUtils.isNotBlank(demandlist2.get(i).getParentId())){									
										//如果父标字段能够查询把子标隐藏
										Map<String, Object> forkparams = new HashMap<>();
										forkparams.put("display", 0);
										forkparams.put("id", demandlist2.get(i).getId());
										demandTreasureLoanService.updateDemandDisplay(forkparams);	
										//把主标的状态改为隐藏
										Map<String, Object> parentparams = new HashMap<>();
										parentparams.put("display", 1);
										parentparams.put("id",demandlist2.get(i).getParentId());
										demandTreasureLoanService.updateDemandDisplay(parentparams);
								}
							}
						}
						availableMoneyRecordService.update(record);
						response.getWriter().print("ok");
					} else {
						response.getWriter().print("turnerror");
					}
				} else {
					response.getWriter().print("error");
				}
			} catch (IOException | SchedulerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/demand/everyRate")
	public String everyRate(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String pageNo = request.getParameter("pageNo");
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			Map<String, Object> map = new HashMap<String, Object>();
			if (year != null && !year.equals("") && month != null
					&& !month.equals("")) {
				map.put("sdate", year + "-" + month);
			} else if (year != null && !year.equals("")) {
				map.put("year", year);
			} else if (month != null && !month.equals("")) {
				map.put("month", month);
			} else {

			}

			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String str = "";
			if (StringUtils.isNotBlank(year)) {
				str += "&year=" + year;
			}
			if (StringUtils.isNotBlank(month)) {
				str += "&month=" + month;
			}
			PageInfo<DateRate> pageInfo = dayRateService.readPageInfo(
					Integer.parseInt(pageNo), 30, map);
			model.addAttribute("url", "/demand/everyRate");
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("year", year);
			model.addAttribute("month", month);
			model.addAttribute("str", str);
			return "demand/everyRate";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.everyRate()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/demand/addRate")
	public void addRate(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String year = request.getParameter("year1");
			String month = request.getParameter("month1");
			String rate = request.getParameter("rate");
			Calendar now = Calendar.getInstance();
			int y = now.get(Calendar.YEAR);
			int m = now.get(Calendar.MONTH) + 1;
			if (Integer.parseInt(year) > y) {
				dayRateService.addRate(year, month,
						Double.parseDouble(rate) / 100);
				response.getWriter().print("ok");
			} else if (Integer.parseInt(year) == y
					&& Integer.parseInt(month) > m) {
				dayRateService.addRate(year, month,
						Double.parseDouble(rate) / 100);
				response.getWriter().print("ok");
			} else {
				response.getWriter().print("请选择正确的年月");
			}

		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.addRate()",
					e);
		}

	}

	@RequestMapping(value = "/demand/toeditRate")
	public String toeditRate(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String id = request.getParameter("id");
			DateRate dateRate = dayRateService.read(id);
			model.addAttribute("dateRate", dateRate);
			return "demand/editRate";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.toeditRate()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/demand/saveRate")
	public void saveRate(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String id = request.getParameter("id");
			String rate = request.getParameter("rate");
			double rate1 = Double.parseDouble(rate) / 100;
			double interest = ArithUtil.round(10000 * rate1 / 365 * 1, 2);
			DateRate dateRate = new DateRate();
			dateRate.setId(id);
			dateRate.setInterest(interest);
			dateRate.setRate(rate1);
			dateRate.setTime(new Date());
			dateRate.setStatus(1);
			dayRateService.update(dateRate);
			response.getWriter().print("ok");
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.saveRate()",
					e);
		}

	}

	@RequestMapping(value = "/demand/transferIn")
	public String transferIn(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			PageInfo<DemandtreasureTransferIn> pageInfo = demandtreasureTransferInService.readPageInfo(
					Integer.parseInt(pageNo), 30);
			DemandtreasureTransferIn d = demandtreasureTransferInService
					.readTotal();
			model.addAttribute("demand", d);
			model.addAttribute("url", "/demand/transferIn");
			model.addAttribute("pageInfo", pageInfo);
			return "demand/transferIn";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.transferIn()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/demand/demandConfirm")
	public void demandConfirm(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie cookie = UserCookieHelper.GetUserCookie(request, response);
		String tranid = request.getParameter("tranid");
		String flag = request.getParameter("flag");
		if (MyStringUtils.isNotAnyBlank(tranid)) {
			try {
				operaRecordService.insertRecord("活期宝手动转账", cookie.getUserId(),
						"请求号：" + tranid);
				DemandtreasureTransferIn demandIn = new DemandtreasureTransferIn();
				demandIn.setId(tranid);
				DemandtreasureTransferIn d = demandtreasureTransferInService
						.read(tranid);
				String str = "";
				if (d != null && d.getStatus().equals("freeze")) {
					str = demandtreasureTransferInService.transferInConfirm(d,
							flag);
				} else {
					str = "状态不正确";
				}
				response.getWriter().print(str);
			} catch (InsufficientBalance e) {
				log.errLog("转账确认错误", "转账ID" + tranid + "reason:余额不足.");
			} catch (IOException e) {
				log.errLog("转账确认错误", "转账ID" + tranid + "reason:IOException.");
			} catch (Exception e) {
				log.errLog("转账确认", "转账ID" + tranid + "reason:IOException.");
			}
		}
	}

	@RequestMapping(value = "/demand/toDemandBillView")
	public String toDemandBillView(HttpServletRequest request) {
		String id = request.getParameter("userId");
		String userId = getUserId(id);
		DemandTreasureBill bill = new DemandTreasureBill();
		bill.setUserId(userId);
		bill.setType(request.getParameter("type"));
		bill.setStartTime(DateUtil.StringToDate(request
				.getParameter("startTime")));
		bill.setEndTime(DateUtil.StringToDate(request.getParameter("endTime")));
		if (null != bill.getUserId() && !bill.getUserId().equals("")) {
			String u = bill.getUserId();
			if (StringUtils.isNumeric(u) && StringUtils.length(u) == 11) {
				u = userService.readUserByMobileNumber(u).getUserId();
			} else if (StringUtils.contains(u, "@")) {
				u = userService.readUserByMail(u).getUserId();

			} else if (MyStringUtils.isChineseName(u)) {
				List<User> list = userService.readUserByRealname(u);
				if (list.size() > 0) {
					u = list.get(0).getUserId();
				}
			}
		}
		String pageNo = request.getParameter("pageNo");
		if (!MyStringUtils.isNumeric(pageNo)) {
			pageNo = "1";
		}

		PageInfo<DemandTreasureBill> pageInfo = demandTreasureBillService
				.readPageLite(Integer.parseInt(pageNo), 25,
						(DemandTreasureBill) FormUtil.getForParamToBean(bill));
		double[] accounts = null;
		if (userId != null && !userId.equals("")) {
			accounts = demandTreasureBillService.readTreasuerAccount(userId);
		}
		request.setAttribute("url", "/demand/toDemandBillView");
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("bill", bill);
		request.setAttribute("accounts", accounts);
		request.setAttribute("str", FormUtil.getForParam(bill));
		return "demand/demandBillView";

	}

	@RequestMapping(value = "/demand/transferOut")
	public String transferOut(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String pageNo = request.getParameter("pageNo");
			String userId = request.getParameter("userId");
			String realname = request.getParameter("realname");
			String mobileNumber = request.getParameter("mobileNumber");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			Map<String, Object> map = new HashMap<String, Object>();			
			if (userId != null && !userId.equals("")) {				
				map.put("userId", userId);
			}
			String name = "";
			if (realname != null && !realname.equals("")) {
				name = java.net.URLDecoder.decode(realname, "UTF-8");			
				map.put("realname", name);
			}
			if (mobileNumber != null && !mobileNumber.equals("")) {			
				map.put("mobileNumber", mobileNumber);
			}
			PageInfo<DemandtreasureTransferOut> pageInfo = demandtreasureTransferOutService.readPageInfo(
					Integer.parseInt(pageNo), 30, map);
			DemandtreasureTransferOut d = demandtreasureTransferOutService
					.readTotal();

			String account = LoadConstantProterties2
					.getValueByDefaultPro("USER");
			Double balance = userAccountService.readUserAccount(account).getAvailableBalance();
			model.addAttribute("demand", d);
			model.addAttribute("userId", userId);
			model.addAttribute("realname", name);
			model.addAttribute("mobileNumber", mobileNumber);
			model.addAttribute("balance", balance);
			model.addAttribute("account", account);
			model.addAttribute("url", "/demand/transferOut");
			model.addAttribute("pageInfo", pageInfo);
			return "demand/transferOut";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.transferIn()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description 到易宝转账
	 * @author wangjing
	 * @time 2014-11-27 下午1:59:51
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/demand/totransferOut")
	public String toYeepayTranauth(HttpServletRequest request,
			HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			synchronized (lock) {

				DemandtreasureTransferOut d = demandtreasureTransferOutService
						.readTotal();
				String account = LoadConstantProterties2
						.getValueByDefaultPro("USER");
				Double balance = userAccountService.readUserAccount(account).getAvailableBalance();
				//String value = null;
				if (d == null) {
					log.infoLog("转出", "没有可转出的用户");
					userMessage(redirectAttributes, "转账失败", "没有可转账的用户");
					return BaseController.redirect + "/demand/transferOut";
				} else {
					double num = demandtreasureGuOutService.readGuCount();
					if (num > 0) {
						userMessage(redirectAttributes, "转账失败", "有转出的未处理完");
						return BaseController.redirect + "/demand/transferOut";
					}
					if (balance < d.getSummoney()) {
						//value = "余额不足";
						log.infoLog("转出", "余额不足");
						userMessage(redirectAttributes, "转账失败", "余额不足");
						return BaseController.redirect + "/demand/transferOut";
					} else {
						List<DemandtreasureTransferOut> trOut = demandtreasureTransferOutService
								.readTotalUser();
						for (DemandtreasureTransferOut t : trOut) {
							double money = demandTreasureBillService
									.readDemandTreasureMoney(t.getUserId());
							if (t.getSummoney() > money) {
								userMessage(redirectAttributes, "转账失败",
										"活期宝余额不足");
								return BaseController.redirect
										+ "/demand/transferOut";
							}
						}
						List<DemandtreasureTransferOut> list = demandtreasureTransferOutService
								.readAll();
						if (list != null && list.size() != 0) {

							String callUrl = SendData2YeePay
									.unifiedYeepayCallback(ToType.THAN);
							// 7,调用soa接口创建充值订单
							List<String> listStr = demandtreasureTransferOutService
									.createTransferOut(account, list, callUrl,
											d.getSummoney());

							if (listStr != null && listStr.size() == 2) {
								String xmlData = listStr.get(0);
								String sign = listStr.get(1);
								// 给易宝发送充值请求
								SendData2YeePay.sendOperation(xmlData,
										ToType.THAN, response, sign);
							}
						} else {
							log.infoLog("转出", "没有可转出的用户");
							userMessage(redirectAttributes, "转账失败", "没有可转出的用户");
							return BaseController.redirect
									+ "/demand/transferOut";
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			log.errLog(
					"到易宝充值:com.duanrong.newadmin.controller.DemandController.toYeepayTranauth().NumberFormatException",
					e);
			e.printStackTrace();
		} catch (IOException e) {
			log.errLog(
					"到易宝充值:com.duanrong.newadmin.controller DemandController.toYeepayTranauth().IOException",
					e);
			e.printStackTrace();
		} catch (Exception e) {
			log.errLog(
					"到易宝充值:com.duanrong.newadmin.controller DemandController.toYeepayTranauth().Exception",
					e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @description 到易宝转账
	 * @author wangjing
	 * @time 2014-11-27 下午1:59:51
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/demand/startransferOut")
	public String startransferOut(HttpServletRequest request,
			HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			String id = request.getParameter("id");
			DemandtreasureGuOut d = demandtreasureGuOutService.read(id);
			Double balance = userAccountService.readUserAccount(d.getUserId()).getAvailableBalance();
			if (balance < d.getMoney()) {
				log.infoLog("转出", "余额不足");
				userMessage(redirectAttributes, "转账失败", "余额不足");
				return BaseController.redirect + "/demand/guOUtList";
			} else {
				// 7,调用soa接口创建充值订单
				List<String> listStr = demandtreasureTransferOutService
						.createstarTransferOut(d);

				if (listStr != null && listStr.size() == 2) {
					String xmlData = listStr.get(0);
					String sign = listStr.get(1);
					// 给易宝发送充值请求
					SendData2YeePay.sendOperation(xmlData, ToType.THAN,
							response, sign);
				}

			}

		} catch (NumberFormatException e) {
			log.errLog(
					"到易宝充值:com.duanrong.newadmin.controller.DemandController.toYeepayTranauth().NumberFormatException",
					e);
			e.printStackTrace();
		} catch (IOException e) {
			log.errLog(
					"到易宝充值:com.duanrong.newadmin.controller DemandController.toYeepayTranauth().IOException",
					e);
			e.printStackTrace();
		} catch (Exception e) {
			log.errLog(
					"到易宝充值:com.duanrong.newadmin.controller DemandController.toYeepayTranauth().Exception",
					e);
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/yeepay/THAN")
	public String tranauthCallBack(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			// 响应的参数 为xml格式
			String respXML = request.getParameter("resp");
			// 签名
			String sign = request.getParameter("sign");
			if (respXML == null || sign == null) {
				userMessage(redirectAttributes, "转账失败", "转账失败");
				return BaseController.redirect + "/demand/transferOut";
			}
			if (CFCASignUtil.isVerifySign(respXML, sign) == false) {
				userMessage(redirectAttributes, "转账成功", "失败原因：验签失败！");
				return BaseController.redirect + "/demand/transferOut";
			}
			// 将xml个数数据转换成map
			@SuppressWarnings("unchecked")
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respXML);
			String requestNo = resultMap.get("requestNo");
			log.infoLog(requestNo + "转账callback返回值", respXML);
			userMessage(redirectAttributes, "转账成功", "转账成功");
			return BaseController.redirect + "/demand/guOUtList";
		} catch (Exception e) {
			log.errLog(
					"转账CallBack:com.duanrong.newadmin.controller DemandController.tranauthCallBack().Exception",
					e);
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/demand/toTreasuerAccount")
	public String getTreasuerAccount(HttpServletRequest request) {
		int days = 7;
		String day = request.getParameter("days");
		if (MyStringUtils.isNumeric(day)) {
			days = Integer.parseInt(day);
		}
		double[] account = demandTreasureBillService.readTreasuerAccount();
		List<DemandTreasureBill> in = demandTreasureBillService.readAccount(
				days, "'in'");
		List<DemandTreasureBill> out = demandTreasureBillService.readAccount(
				days, "'out','outinterest'");
		List<DemandTreasureBill> interest = demandTreasureBillService
				.readAccount(days, "'interest'");
		request.setAttribute("account", account);
		request.setAttribute("in", in);
		request.setAttribute("out", out);
		request.setAttribute("interest", interest);
		return "demand/treasuerAccount";
	}

	@RequestMapping(value = "/demand/guOUtList")
	public String guOUtList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			PageInfo<DemandtreasureGuOut> pageInfo = demandtreasureGuOutService.readPageInfo(
					Integer.parseInt(pageNo), 30);
			model.addAttribute("url", "/demand/guOUtList");
			model.addAttribute("pageInfo", pageInfo);
			return "demand/guOutList";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.guOUtList()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/demand/userDetail")
	public String userDetail(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String guOutId = request.getParameter("guOutId");
			String str = "";
			if (guOutId != null && !guOutId.equals("")) {
				str += "&guOutId=" + guOutId;
			}
			DemandtreasureGuOutDetail detail = new DemandtreasureGuOutDetail();
			detail.setGuOutId(guOutId);
			PageInfo<DemandtreasureGuOutDetail> list = demandtreasureGuOutDetailService
					.readPageInfo(Integer.parseInt(pageNo), 30, detail);
			model.addAttribute("url", "/demand/userDetail");
			model.addAttribute("pageInfo", list);
			model.addAttribute("guOutId", guOutId);
			model.addAttribute("str", str);
			return "demand/userDetail";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.userDetail()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/demand/demandOutConfirm")
	public void demandOutConfirm(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie cookie = UserCookieHelper.GetUserCookie(request, response);
		String tranid = request.getParameter("tranid");
		String flag = request.getParameter("flag");
		if (MyStringUtils.isNotAnyBlank(tranid)) {
			try {
				operaRecordService.insertRecord("活期宝转出确认", cookie.getUserId(),
						"请求号：" + tranid);
				String str = demandtreasureTransferOutService
						.transferOutConfirm(tranid, flag);
				response.getWriter().print(str);
			} catch (InsufficientBalance e) {
				log.errLog("转账确认错误", "转账ID" + tranid + "reason:余额不足.");
			} catch (IOException e) {
				log.errLog("转账确认错误", "转账ID" + tranid + "reason:IOException.");
			} catch (Exception e) {
				log.errLog("转账确认", "转账ID" + tranid + "reason:IOException.");
			}
		}
	}

	@RequestMapping(value = "/demand/insertDayDate/{date}")
	public void insertDayDate(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String date)
			throws IOException {
		PrintWriter out = response.getWriter();
		String user = request.getParameter("userId");
		if (user != null && !user.equals("")) {
			user = getUserId(user);
		}
		try {
			Date d = DateUtil.StringToDate(date);
			if (DateUtil.calculateIntervalDays(new Date(), d) >= 0) {
				out.print("not");
				return;
			}

			List<String> listStr = demandTreasureBillService.readInterestToday(
					d, user);
			List<DemandTreasureBill> list = new ArrayList<DemandTreasureBill>();
			log.infoLog("活期宝手动派息结算开始", "需计息人数：" + listStr.toString());
			for (String str : listStr) {
				String[] arr = str.split(",");
				double money = Double.parseDouble(arr[1]);
				String userId = arr[0];
				if (money >= 0.01) {
					DemandTreasureBill bill = new DemandTreasureBill();
					bill.setId(IdGenerator.randomUUID());
					bill.setUserId(userId);
					bill.setMoney(money);
					bill.setCreateTime(DateUtil.addDay(d, 1));
					bill.setType("interest");
					bill.setTranferWay("pc");
					bill.setStatus("success");
					bill.setDetail(new SimpleDateFormat("yyyy-MM-dd").format(d)
							+ "利息");
					System.out.println(bill.getUserId() + "派息："
							+ bill.toString());
					log.infoLog(bill.getUserId() + "派息成功!", bill.toString());
					list.add(bill);
				}
			}

			demandTreasureBillService.insertInterestBatch(list);
			out.print("ok");
		} catch (Exception e) {
			log.errLog("手动派息失败", e);
			out.print("error");
		}
	}

	// 查询用户活期宝信息
	@RequestMapping(value = "/demand/demandByUser")
	public void demandByUser(HttpServletResponse response,
			HttpServletRequest request) {
		PrintWriter out;
		try {
			out = response.getWriter();
			String date = request.getParameter("d");
			String userId = request.getParameter("userId");
			Date d = new Date();
			if (userId == null || userId.equals("")) {
				return;
			}
			if (date != null && !date.equals("")) {
				d = DateUtil.StringToDate(date);
			}
			userId = getUserId(userId);
			if (userId == null || userId.equals("")) {
				out.print("not");
				return;
			}

			if (!demandTreasureBillService.readIsDemandUser(userId)) {
				out.print("nod");
				return;
			}
			DemandTreasureAccount demandTreasureAccount = demandTreasureBillService
					.readDemandAccount(userId, d);
			JSONArray jsonArr = JSONArray.fromObject(demandTreasureAccount);
			response.setCharacterEncoding("UTF-8");
			out.print(jsonArr.toString());
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@RequestMapping(value = "/demand/loanList")
	public String loanList(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		try {
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String status = request.getParameter("status");
			String loanName = request.getParameter("loanName");
			String borrower = request.getParameter("borrower");
			String type = request.getParameter("type");
			String loanType = request.getParameter("loanType");
			String openStatus = request.getParameter("openStatus");
			String repayType = request.getParameter("repayType");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String actualStart = request.getParameter("actualStart");
			String actualEnd = request.getParameter("actualEnd");
			
			if (type == "" || type == null) {
				type = "creatTime";
			}
			DemandtreasureLoan detail = new DemandtreasureLoan();
			detail.setLoanName(loanName);
			detail.setBorrower(borrower);
			detail.setLoanStatus(status);
			detail.setType(type);
			detail.setLoanType(loanType);
			detail.setOpenStatus(openStatus);
			detail.setRepayType(repayType);
			detail.setStart(start);
			detail.setEnd(end);
			detail.setActualStart(actualStart);
			detail.setActualEnd(actualEnd);
			String str = "";
			if (loanName != null && !loanName.equals("")) {
				str += "&loanName=" + loanName;
			}
			if (borrower != null && !borrower.equals("")) {
				str += "&borrower=" + borrower;
			}
			if (status != null && !status.equals("")) {
				str += "&status=" + status;
			}
			if (loanType != null && !loanType.equals("")) {
				str += "&loanType=" + loanType;
			}
			if (openStatus != null && !openStatus.equals("")) {
				str += "&openStatus=" + openStatus;
			}
			if (repayType != null && !repayType.equals("")) {
				str += "&repayType=" + repayType;
			}
			if (start != null && !start.equals("")) {
				str += "&start=" + start;
			}
			if (end != null && !end.equals("")) {
				str += "&end=" + end;
			}
			if (actualStart != null && !actualStart.equals("")) {
				str += "&actualStart=" + actualStart;
			}
			if (actualEnd != null && !actualEnd.equals("")) {
				str += "&actualEnd=" + actualEnd;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			double money = demandTreasureLoanService.readMoneyBydate(DateUtil
					.addDay(df.format(new Date()), 1));
			PageInfo<DemandtreasureLoan> list = demandTreasureLoanService
					.readPageInfo(Integer.parseInt(pageNo), 30, detail);
			model.addAttribute("url", "/demand/loanList");
			model.addAttribute("pageInfo", list);
			model.addAttribute("status", status);
			model.addAttribute("loanName", loanName);
			model.addAttribute("borrower", borrower);
			model.addAttribute("money", money);
			model.addAttribute("openStatus", openStatus);
			model.addAttribute("loanType", loanType);
			model.addAttribute("repayType", repayType);
			model.addAttribute("start", start);
			if (end!=null) {
				model.addAttribute("end", URLDecoder.decode(end, "utf-8"));
			}
			model.addAttribute("actualStart", actualStart);
			if (actualEnd!=null) {
				model.addAttribute("actualEnd", URLDecoder.decode(actualEnd, "utf-8"));
			}
			model.addAttribute("type", detail.getType());
			model.addAttribute("str", str);
			return "demand/loanList";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.loanList()",
					e);
		}
		return null;
	}

	/**
	 * 活期宝数据导出
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/demand/demandExport")
	public void demandExport(HttpServletResponse response,
			HttpServletRequest request, Model model) throws IOException {
		String status = request.getParameter("status");
		String loanName = request.getParameter("loanName");
		String borrower = request.getParameter("borrower");
		String type = request.getParameter("type");
		String loanType = request.getParameter("loanType");
		String openStatus = request.getParameter("openStatus");
		String repayType = request.getParameter("repayType");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String actualStart = request.getParameter("actualStart");
		String actualEnd = request.getParameter("actualEnd");
		if (type == "" || type == null) {
			type = "creatTime";
		}
		DemandtreasureLoan detail = new DemandtreasureLoan();
		detail.setLoanName(loanName);
		detail.setBorrower(borrower);
		detail.setLoanStatus(status);
		detail.setType(type);
		detail.setLoanType(loanType);
		detail.setOpenStatus(openStatus);
		detail.setRepayType(repayType);
		detail.setStart(start);
		detail.setEnd(end);
		detail.setActualStart(actualStart);
		detail.setActualEnd(actualEnd);
		String t = "活期宝资产列表";

		try {
			List<DemandtreasureLoan> demand = demandTreasureLoanService
					.readexportList(detail);
			List<DemandtreasureLoan> list = new ArrayList<>();
			for (DemandtreasureLoan exportdemand : demand) {
				DemandtreasureLoan export = new DemandtreasureLoan();
				export.setLoanName(exportdemand.getLoanName());
				export.setLoanStatus(exportdemand.getLoanStatus());
				export.setLoanType(exportdemand.getLoanType());
				export.setLoanAddr(exportdemand.getLoanAddr());
				export.setTotalMoney(exportdemand.getTotalMoney());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				if(exportdemand.getStartTime()!=null){
				String startTime =sdf.format(exportdemand.getStartTime());			
				export.setShowstartTime(startTime);
				}
				if(exportdemand.getFinishTime()!=null&&"finish".equals(exportdemand.getLoanStatus())){				
				String finishTime =sdf.format(exportdemand.getFinishTime());
				export.setShowfinishTime(finishTime);
				}
				export.setOperationType(exportdemand.getOperationType());
				if (exportdemand.getOperationType().equals("月")) {
					export.setMonth(exportdemand.getMonth());
				} else {
					export.setMonth(exportdemand.getDay());
				}
				Date startTime = exportdemand.getStartTime();
				if (startTime!=null) {
					Calendar calendar = new GregorianCalendar(); 
					calendar.setTime(startTime);
					if (exportdemand.getOperationType().equals("月")&&exportdemand.getMonth()!=null) {
						calendar.add(calendar.MONTH,exportdemand.getMonth());//把月份增加
						String finish = sdf.format(calendar.getTime());
						export.setActualFinishTime(finish);
					} else if (exportdemand.getDay()!=null) {
						calendar.add(calendar.DATE,exportdemand.getDay());//把日期增加
						String finish = sdf.format(calendar.getTime());
						export.setActualFinishTime(finish);
					}
				}
				if (exportdemand.getAccountingDepartment()!=null) {
					Integer accountingDepartment = exportdemand.getAccountingDepartment();
					if (accountingDepartment==0) {
						export.setAccountingCompany("齐海");
					}
					if (accountingDepartment==2) {
						export.setAccountingCompany("久亿");
					}
					if (accountingDepartment==1) {
						if (("车押宝").equals(exportdemand.getLoanType())) {
							export.setAccountingCompany("短融");
						}
						if (("金农宝").equals(exportdemand.getLoanType())) {
							export.setAccountingCompany("山水");
						}
					}
				}
				export.setBorrower(exportdemand.getBorrower());
				export.setBrand(exportdemand.getBrand());
				export.setLicensePlateNumber(exportdemand
						.getLicensePlateNumber());
				list.add(export);
			}
			Map<String, String> title = new LinkedHashMap<>();
			title.put("loanName", "项目名称");
			title.put("loanStatus", "项目状态");
			title.put("loanType", " 项目类型");
			title.put("loanAddr", " 项目地点");
			title.put("totalMoney", "借款金额");
			title.put("showstartTime", "开始时间");
			title.put("showfinishTime", "实际结束时间");
			title.put("actualFinishTime", "应结束时间");
			title.put("operationType", "计算方式");
			title.put("month", "借款期限");
			title.put("borrower", "借款人");
			title.put("brand", "品牌和型号");
			title.put("licensePlateNumber", "车牌 号");
			title.put("accountingCompany", "核算公司");
			int[] style = { 15, 15, 15, 15, 15, 25, 25,25, 15, 15, 15, 15, 15, 15 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = t + fileName + ".xls";
			fileName = URLDecoder.decode(fileName, "utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			excelConvertor.excelExport(list, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/demand/delLoan")
	public void delLoan(HttpServletResponse response, HttpServletRequest request) {
		String id = request.getParameter("id");
		UserCookie cookie = UserCookieHelper.GetUserCookie(request, response);
		try {

			Map<String, Object> params = new HashMap<>();
			DemandtreasureLoan demandtreasureLoan = new DemandtreasureLoan();
			demandtreasureLoan.setId(id);
			demandtreasureLoan.setDisplay(0);
			demandtreasureLoan.setModifyPerson(cookie.getUserId());
			demandtreasureLoan.setModifyTime(new Date());
			demandTreasureLoanService.update(demandtreasureLoan);
			DemandtreasureLoan demand = demandTreasureLoanService.read(id);
			// 删除成功后把打包金农宝资产的进行释放以便重新勾选
			if ("金农宝".equals(demand.getLoanType())
					&& StringUtils.isNotBlank(demand.getForkId())) {
				params.put("packing", 0);
				params.put("flag", "");
				String[] arr = { demand.getForkId() };
				params.put("arr", arr);
				agricultureForkLoansService.updatePackStatus(params);
			}
			if ("金农宝".equals(demand.getLoanType())
					&& StringUtils.isNotBlank(demand.getLoaninfoId())) {
				params.put("packingStatus", "0");
				String[] arr = { demand.getLoaninfoId() };
				params.put("arr", arr);
				agricultureLoanerInfoService.updatePackStatus(params);
			}
			response.getWriter().print("ok");
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.DemandController.loanList()",
					e);
		}
	}

	@RequestMapping(value = "/demand/toLoan")
	public String holdtoAdd(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "demand/addLoan";
	}

	/**
	 * 添加农贷资产页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/demand/agricultureLoan")
	public String addAgriculture(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "demand/agricultureLoan";
	}

	@RequestMapping(value = "/demand/addLoan")
	public void addLoan(
			@ModelAttribute("demandtreasureLoan") DemandtreasureLoan demandtreasureLoan,
			HttpServletResponse response, HttpServletRequest request) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			 String result=demandTreasureLoanService.addLoanCommon(demandtreasureLoan, getLoginUser);
			 if("sucess".equals(result)){
				response.getWriter().print("ok");
			 }else{
				response.getWriter().print("fail");
			 }
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.errLog("活期宝基本信息保存", e);
		}
	}

	/**
	 * 添加先息后本农贷资产页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/demand/addTimelyAgriculture")
	public String addTimelyAgriculture(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> params = new HashMap<>();
		params.put("packingStatus", "0");
		List<AgricultureLoaninfo> loanerinfo = agricultureLoanerInfoService
				.readAgriculturePacking(params);
		model.addAttribute("loanerinfo", loanerinfo);
		return "demand/agricultureTimelyloans";
	}

	@RequestMapping(value = "/demand/toeditLoan")
	public String toeditLoan(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		DemandtreasureLoan demandtreasureLoan = demandTreasureLoanService
				.read(id);
		demandtreasureLoan.setSbuyTime(DateUtil.DateToString(
				demandtreasureLoan.getBuyTime(), "yyyy-MM-dd HH:mm:ss"));
		demandtreasureLoan.setSfinishTime(DateUtil.DateToString(
				demandtreasureLoan.getFinishTime(), "yyyy-MM-dd HH:mm:ss"));
		demandtreasureLoan.setSstartTime(DateUtil.DateToString(
				demandtreasureLoan.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
		model.addAttribute("demandtreasureLoan", demandtreasureLoan);
		return "demand/editLoan";
	}

	@RequestMapping(value = "/demand/cancel")
	public void cancel(HttpServletResponse response, HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			DemandtreasureGuOut gu = new DemandtreasureGuOut();
			gu.setId(id);
			gu.setStatus("revoke");
			demandtreasureGuOutService.update(gu);
			response.getWriter().print("ok");
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.errLog("转账撤销", "ID" + id + "reason:IOException.");
		}
	}

	@RequestMapping(value = "/demand/editLoan")
	public void editLoan(
			@ModelAttribute("demandtreasureLoan") DemandtreasureLoan demandtreasureLoan,
			HttpServletResponse response, HttpServletRequest request) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			demandtreasureLoan.setModifyPerson(getLoginUser.getUserId());
			demandtreasureLoan.setModifyTime(new Date());
			demandtreasureLoan.setDisplay(1);
			if (demandtreasureLoan.getOperationType().equals("天")) {
				demandtreasureLoan.setMonth(null);
			} else {
				demandtreasureLoan.setDay(null);
			}
			if (demandtreasureLoan.getSbuyTime() != "") {
				demandtreasureLoan.setBuyTime(DateUtil
						.StringToDate(demandtreasureLoan.getSbuyTime()));
			}
			if (demandtreasureLoan.getSfinishTime() != "") {
				demandtreasureLoan.setFinishTime(DateUtil
						.StringToDate(demandtreasureLoan.getSfinishTime()));
			}
			if (demandtreasureLoan.getSstartTime() != "") {
				demandtreasureLoan.setStartTime(DateUtil
						.StringToDate(demandtreasureLoan.getSstartTime()));
			}
			if (demandtreasureLoan.getOperationType().equals("月")) {
				int month = demandtreasureLoan.getMonth();
				demandtreasureLoan.setFinishTime(DateUtil.addMonth(
						demandtreasureLoan.getStartTime(), month));
			} else if(demandtreasureLoan.getSfinishTime() == ""){
				int days = demandtreasureLoan.getDay();
				demandtreasureLoan.setFinishTime(DateUtil.addDay(
						demandtreasureLoan.getStartTime(), days));
			}
			demandTreasureLoanService.updateLoan(demandtreasureLoan);
			//如果是父标的修改，侧把子标的信息全部修改了
			if("车押宝".equals(demandtreasureLoan.getLoanType()) && "等额本息".equals(demandtreasureLoan.getRepayType())){		
				String parentId = demandtreasureLoan.getId();
				if(demandtreasureLoan.getMonth()!=null && demandtreasureLoan.getTotalMoney()!=null){									
				List<DemandtreasureLoan> readDemadfork = demandTreasureLoanService.readDemadfork(parentId);	
				for (DemandtreasureLoan demadfork : readDemadfork) {
					DemandtreasureLoan demandforkLoans = new DemandtreasureLoan();									
					demandforkLoans.setId(demadfork.getId());	
					demandforkLoans.setLoanName(demadfork.getLoanName());
					demandforkLoans.setDisplay(demadfork.getDisplay());
					demandforkLoans.setTotalMoney(demadfork.getTotalMoney());
					demandforkLoans.setOperationType("月");
					demandforkLoans.setMonth(demadfork.getMonth());
					demandforkLoans.setLoanType(demandtreasureLoan.getLoanType());
					demandforkLoans.setLoanStatus(demandtreasureLoan.getLoanStatus());
					demandforkLoans.setRepayType("等额本息");
     				demandforkLoans.setCreator(demandtreasureLoan.getCreator());
					demandforkLoans.setParentId(parentId);
					demandforkLoans.setCreateTime(demadfork.getCreateTime());					
					demandforkLoans.setLoanAddr(demandtreasureLoan.getLoanAddr());
					demandforkLoans.setStartTime(demandtreasureLoan.getStartTime());
					demandforkLoans.setFinishTime(DateUtil.addMonth(
							demandtreasureLoan.getStartTime(), demadfork.getMonth()));
					demandforkLoans.setBorrower(demandtreasureLoan.getBorrower());
					demandforkLoans.setIdCard(demandtreasureLoan.getIdCard());
					demandforkLoans.setBrand(demandtreasureLoan.getBrand());
					demandforkLoans.setLicensePlateNumber(demandtreasureLoan.getLicensePlateNumber());
					demandforkLoans.setBuyTime(demandtreasureLoan.getBuyTime());
					demandforkLoans.setKilometreAmount(demandtreasureLoan.getKilometreAmount());
					demandforkLoans.setAssessPrice(demandtreasureLoan.getAssessPrice());
					demandforkLoans.setGuaranteeType(demandtreasureLoan.getGuaranteeType());
					demandforkLoans.setGuaranteeRate(demandtreasureLoan.getGuaranteeRate());
					demandforkLoans.setOtherInfo(demandtreasureLoan.getOtherInfo());
					demandforkLoans.setModifyPerson(demandtreasureLoan.getModifyPerson());
					demandforkLoans.setModifyTime(demandtreasureLoan.getModifyTime());					
					demandTreasureLoanService.updateLoan(demandforkLoans);					
				}
				} 
        	}			
			response.getWriter().print("ok");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.errLog("活期宝基本信息保存", e);
		}
	}

	@RequestMapping("/demand/billList")
	public ModelAndView findByBill(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		String tranferWay = request.getParameter("tranferWay");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
	 	String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		DemandTreasureBill bill = new DemandTreasureBill();
		bill.setType(type);
		bill.setStatus(status);
		bill.setTranferWay(tranferWay);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (StringUtils.isNotBlank(startTime)) {
				Date date;

				date = sdf.parse(startTime);
				bill.setStartTime(date);
			}
			if (StringUtils.isNotBlank(endTime)) {
				Date date = sdf.parse(endTime);
				bill.setEndTime(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (pageNo == null) {
			pageNo = "1";
		}
		if (pageSize == null) {
			pageSize = "20";
		}

		PageInfo<DemandTreasureBill> pageInfo = demandTreasureBillService.readPageLite02(
				Integer.parseInt(pageNo), Integer.parseInt(pageSize), bill);

		mv.setViewName("demand/billList");
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("bill", bill);
		mv.addObject("url", "demand/billList");
		mv.addObject("str", FormUtil.getForParam(bill));

		return mv;
	}

	/**
	 * 增强型校验转账 1.校验转出金额是否大于用户的活期宝账户资金 2.校验用户转入记录中金额最大的两笔，做单笔业务查询，是否有效
	 * 
	 * @return
	 */
	@RequestMapping(value = "/demand/EnhanceValidateTransferOut", method = RequestMethod.POST)
	@ResponseBody
	public String EnhanceValidateTransferOut(HttpServletRequest request,
			HttpServletResponse response) {
		int limit = 3;
		if (request.getParameter("limit") != null) {
			try {
				limit = Integer.parseInt(request.getParameter("limit").trim());
			} catch (Exception e) {
				limit = 3;
			}
		}

		List<DemandtreasureTransferOut> trOut = demandtreasureTransferOutService
				.readTotalUser();
		for (DemandtreasureTransferOut t : trOut) {
			double money = demandTreasureBillService.readDemandTreasureMoney(t
					.getUserId());
			// 第一步验证用户转出资金是否超过该用户的活期宝账户资金
			if (t.getSummoney() > money) {
				return "用户：" + t.getUserId() + "转出金额，超过他活期宝当前账户资金";
			}
			// 第二步验证，申请转出的用户，转入记录金额最大的两笔transferin是否在易宝有交易记录，（单笔业务查询）
			List<DemandtreasureTransferIn> list = demandtreasureTransferInService
					.readTransferInRecordTop(t.getUserId(), limit);
			if (list != null && list.size() > 0) {
				for (DemandtreasureTransferIn item : list) {
					String requestNo = item.getId();

					TrusteeshipQuery query = trusteeshipOperationService.query(
							requestNo, "CP_TRANSACTION");
					System.out.println("***userID:" + t.getUserId()
							+ ",requestNO:" + requestNo + ",status:"
							+ query.getTstatus());
					if (!query.getTstatus().equals("CONFIRM")) {
						log.errLog("活期宝转出增强型校验失败", t.getUserId() + "-转入流水"
								+ requestNo);
						return "用户：" + t.getUserId() + ",转入记录：" + requestNo
								+ " 单笔业务查询失败";

					}

				}

			}
		}

		return "校验成功";
	}

	/**
	 * 转入查询，带条件 分页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/demand/getListPageForTranferIn")
	public String getListPageForTranferIn(DemandtreasureTransferIn transferIn,
			HttpServletRequest request, HttpServletResponse response, Model m) {
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");

		if (pageNo == null) {
			pageNo = "1";
		}
		if (pageSize == null) {
			pageSize = "20";
		}
		transferQueryCondition(transferIn.getUser());
		PageInfo pageInfo = demandtreasureTransferInService
				.readListForTranferIn(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize), transferIn);
		// 总转入成功金额
		BigDecimal summoneySuccess = demandtreasureTransferInService
				.readSumMoneyTransferIn(transferIn);
		//成功人数
		int sumPeopleSuccess = demandtreasureTransferInService
				.readSumPeopleTransferIn(transferIn);
		//失败转入金额
		//失败转入人数
		BigDecimal summoneyFail = demandtreasureTransferInService
				.readSumMoneyTransferInFail(transferIn);
		int sumPeopleFail = demandtreasureTransferInService
				.readSumPeopleTransferInFail(transferIn);
		m.addAttribute("pageInfo", pageInfo);
		m.addAttribute("transferIn", transferIn);
		m.addAttribute("url", "/demand/getListPageForTranferIn");
		m.addAttribute("summoneySuccess", summoneySuccess);
		m.addAttribute("sumPeopleSuccess", sumPeopleSuccess);
		m.addAttribute("summoneyFail", summoneyFail);
		m.addAttribute("sumPeopleFail", sumPeopleFail);
		m.addAttribute("fork", transferIn.getFork());
		return "demand/transferInList";
	}

	/**
	 * 转出查询，带条件 分页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/demand/getListPageForTranferOut")
	public String getListPageForTranferOut(
			DemandtreasureTransferOut transferOut, HttpServletRequest request,
			HttpServletResponse response, Model m) {
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");

		if (pageNo == null) {
			pageNo = "1";
		}
		if (pageSize == null) {
			pageSize = "20";
		}
		transferQueryCondition(transferOut.getUser());
		PageInfo pageInfo = demandtreasureTransferOutService
				.readListForTranferOut(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize), transferOut);
		// 总转出 金额
		BigDecimal summoney = demandtreasureTransferOutService
				.readSumMoneyTransferOut(transferOut);
		int sumPeople = demandtreasureTransferOutService
				.readSumPeopleTransferOut(transferOut);
		// 总转出 金额
		BigDecimal summoneyFail = demandtreasureTransferOutService
				.readSumMoneyFailTransferOut(transferOut);
		int sumPeopleFail = demandtreasureTransferOutService
				.readSumPeopleFailTransferOut(transferOut);
		
		m.addAttribute("pageInfo", pageInfo);
		m.addAttribute("transferOut", transferOut);
		m.addAttribute("sumPeople", sumPeople);
		m.addAttribute("summoneyFail", summoneyFail);
		m.addAttribute("sumPeopleFail", sumPeopleFail);
		m.addAttribute("url", "/demand/getListPageForTranferOut");
		m.addAttribute("summoney", summoney);
		m.addAttribute("fork", transferOut.getFork());
		return "demand/transferOutList";
	}

	/**
	 * 转换用户查询条件
	 * 
	 * @param user
	 */
	private void transferQueryCondition(User user) {
		if (user == null)
			return;
		if (MyStringUtils.isNumeric(user.getUserId().trim())
				&& user.getUserId().trim().length() == 11)
			user.setMobileNumber(user.getUserId().trim());
		else if (isChineseChar(user.getUserId()))
			user.setRealname(user.getUserId().trim());
		else
			user.setUsername(user.getUserId().trim());
	}

	/**
	 * 工具类 判断是否包含中文字符 包含返回true
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

	/**
	 * 查询某阶段内活期宝利息发放金额
	 */
	@ResponseBody
	@RequestMapping(value = "/demand/demandMoney")
	public String demandMoney(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String endTime = end + " 23:59:59";
		double money = demandtreasureService.readDemandInterest(start, endTime);
		model.addAttribute("demandInterest", money);
		JSONObject json = new JSONObject();
		json.put("money", money);
		return json.toString();

	}

	/**
	 * 批量添加到活期宝资产
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/demand/addForkLoans")
	@ResponseBody
	public String addForkLoans(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");

		UserCookie getLoginUser = GetLoginUser(request, response);
		String ids = request.getParameter("ids");
		String result = demandTreasureLoanService.insertTreasureLoan(ids,
				getLoginUser.getUserId());
		return result;
	}

	/**
	 * 更新赎回金额
	 */
	@RequestMapping(value = "demand/toUpdateMoney")
	public String toUpdateMoeny(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		double totalmoney = demandTreasureLoanService.readAssignmentMoney();
		double expiredMoney = demandTreasureLoanService
				.readMoneyBydate(DateUtil.addDay(df.format(new Date()), 1));
		double expiredMoneyTomorrow = demandTreasureLoanService
				.readMoneyBydate(DateUtil.addDay(df.format(new Date()), 2));
		double expiredMoneyacquired = demandTreasureLoanService
				.readMoneyBydate(DateUtil.addDay(df.format(new Date()), 3));
		String type = request.getParameter("type");
		if (StringUtils.isBlank(type)) {
			type = "money";
		}
		String nowdate = format.format(new Date());
		Map<String, Object> treasureparam = new HashMap<>();
		treasureparam.put("type", type);
		List<DemandtreasureLoan> treasureLoan = demandTreasureLoanService
				.readDemandtreasureLoan(treasureparam);
		List<DemandtreasureLoan> transferLoan = demandTreasureLoanService
				.readTransferDemandtreasureLoan();
		model.addAttribute("treasureLoan", treasureLoan);
		model.addAttribute("totalmoney", totalmoney);
		model.addAttribute("transferLoan", transferLoan);
		model.addAttribute("nowdate", nowdate);
		model.addAttribute("expiredMoney", expiredMoney);
		model.addAttribute("expiredMoneyTomorrow", expiredMoneyTomorrow);
		model.addAttribute("expiredMoneyacquired", expiredMoneyacquired);
		return "demand/updateRedemption";
	}

	@RequestMapping(value = "/demand/editTransferOutMoney")
	public String editTransferOutMoney(HttpServletRequest request) {
		String id = request.getParameter("userId");
		String userId = getUserId(id);
		DemandTreasureBill bill = new DemandTreasureBill();
		bill.setUserId(userId);
		if (null != bill.getUserId() && !bill.getUserId().equals("")) {
			String u = bill.getUserId();
			if (StringUtils.isNumeric(u) && StringUtils.length(u) == 11) {
				u = userService.readUserByMobileNumber(u).getUserId();
			} else if (StringUtils.contains(u, "@")) {
				u = userService.readUserByMail(u).getUserId();

			} else if (MyStringUtils.isChineseName(u)) {
				List<User> list = userService.readUserByRealname(u);
				if (list.size() > 0) {
					u = list.get(0).getUserId();
				}
			}
		}
		DemandtreasureTransferOut transferOut = new DemandtreasureTransferOut();
		PageInfo pageInfo = null;
		User user = new User();
		if (null != id && !id.equals("")) {
			user.setUserId(id);
			transferOut.setUser(user);
			transferQueryCondition(transferOut.getUser());
			transferOut.setStatus("sended");
			pageInfo = demandtreasureTransferOutService.readListForTranferOut(1,
					20, transferOut);
		}
		double[] accounts = null;
		if (userId != null && !userId.equals("")) {
			accounts = demandTreasureBillService.readTreasuerAccount(userId);
		}
		request.setAttribute("url", "/demand/editTransferOutMoney");
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("bill", bill);
		request.setAttribute("accounts", accounts);
		request.setAttribute("str", FormUtil.getForParam(bill));
		return "demand/editTransferOutMoney";

	}

	/**
	 * 手动修改活期宝赎回金额
	 */
	@ResponseBody
	@RequestMapping(value = "/demand/updateTransferOutMoney")
	public String updateTransferOutMoney(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			String id = request.getParameter("id");
			String userId = request.getParameter("userId");
			double totalMoney = Double.parseDouble(request
					.getParameter("totalMoney"));
			double extractMoney = Double.parseDouble(request
					.getParameter("extractMoney"));
			double bprincipal = Double.parseDouble(request
					.getParameter("bprincipal"));
			double binterest = Double.parseDouble(request
					.getParameter("binterest"));
			double validMoney = Double.parseDouble(String.format("%.2f",
					extractMoney + bprincipal));
			if (totalMoney > validMoney) {
				return "moneyError";
			} else {
				DemandtreasureTransferOut newOut = new DemandtreasureTransferOut();
				double newPrincipal = totalMoney;
				double newMoney = newPrincipal + binterest;
				double oldMoney = bprincipal + binterest;
				newOut.setId(id);
				newOut.setMoney(newMoney);
				newOut.setPrincipal(newPrincipal);
				demandtreasureTransferOutService.update(newOut);
				operaRecordService.insertRecord("活期宝手动修改赎回金额",
						getLoginUser.getUserId(), "手动修改用户: " + userId
								+ "原赎回金额: " + oldMoney + "现赎回金额: " + newMoney);
				log.infoLog("活期宝手动修改赎回金额", getLoginUser.getUserId()
						+ "手动修改用户: " + userId + "原赎回金额: " + oldMoney
						+ "现赎回金额: " + newMoney, 1);
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	/**
	 * 批量及时贷添加到活期宝资产
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/demand/addLoanerinfos")
	@ResponseBody
	public String addLoanerinfos(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");

		UserCookie getLoginUser = GetLoginUser(request, response);
		String ids = request.getParameter("ids");
		String result = demandTreasureLoanService.insertTreasureLoanTimely(ids,
				getLoginUser.getUserId());
		return result;
	}
	/**
	 * 活期宝车贷提前还款
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/demand/prepayment")
	public String prepaymentDeamd(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String parentId = request.getParameter("parentId");
		String loanTerm = request.getParameter("loanTerm");
		try {
	
		String str = "";
		String error="";
		if (parentId != null && !parentId.equals("")) {
			str += "&parentId=" + parentId;
		}
		if (loanTerm != null && !loanTerm.equals("")) {
			str += "&loanTerm=" + loanTerm;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<DemandtreasureLoan> readDemadfork=null;
		if(parentId != null && !parentId.equals("")){
			if(loanTerm != null && !loanTerm.equals("")){
				DemandtreasureLoan parentDemand = demandTreasureLoanService.read(parentId);
				if(parentDemand!=null){
					Date startTime=DateUtil.addMonth(parentDemand.getStartTime(), Integer.parseInt(loanTerm));
					String nowDate = df.format(new Date());
					String parentDate =df.format(startTime);
					if(DateUtil.dayDifference(nowDate, parentDate)>0){
						readDemadfork = demandTreasureLoanService.readDemadfork(parentId);
						error="sucess";
						model.addAttribute("nowDate", new Date());	
						model.addAttribute("startTime", startTime);	
					}else{
						error="loanTermError";
					}
				}else{
					error="parentIdNull";	
				}
			}else{
				error="loanTermNull";		
			}
		}	
		model.addAttribute("url", "demand/prepayment");
		model.addAttribute("pageInfo", readDemadfork);
		model.addAttribute("parentId", parentId);	
		model.addAttribute("loanTerm", loanTerm);
		model.addAttribute("str", str);	
		model.addAttribute("error", error);		
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "demand/prepayment";	
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/demand/editPrepayment")
	public void editPrepayment(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String parentId = request.getParameter("parentId");
		String loanTerm = request.getParameter("loanTerm");
		try {	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<DemandtreasureLoan> readDemadfork=null;
		if(parentId != null && !parentId.equals("")){
			if(loanTerm != null && !loanTerm.equals("")){
				DemandtreasureLoan parentDemand = demandTreasureLoanService.read(parentId);
				if(parentDemand!=null){
					Date endTime=DateUtil.addMonth(parentDemand.getStartTime(), Integer.parseInt(loanTerm));
					String nowDate = df.format(new Date());
					String parentDate =df.format(endTime);
					if(DateUtil.dayDifference(nowDate, parentDate)>0){
					readDemadfork = demandTreasureLoanService.readDemadfork(parentId);
					for (DemandtreasureLoan demandtreasureLoan : readDemadfork) {
						String forkTime=df.format(demandtreasureLoan.getFinishTime());
						//如果子标的结束时间大于实际结束时间，就把子标的结束时间全部改为实际结束时间
						if(DateUtil.dayDifference(parentDate, forkTime)>0){
							Map<String, Object> forkparams = new HashMap<>();																				
							forkparams.put("finishTime",endTime);							
							forkparams.put("id",demandtreasureLoan.getId());
							demandTreasureLoanService.updateDemandDisplay(forkparams);							
						}
					
					}
					//修改主标的结束时间
					Map<String, Object> parentparams = new HashMap<>();					
					parentparams.put("finishTime",endTime);					
					parentparams.put("id",parentId);					
					demandTreasureLoanService.updateDemandDisplay(parentparams);
					response.getWriter().print("ok");						
					}
				}
			}
		}			
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
