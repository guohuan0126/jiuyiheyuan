package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import util.Log;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.LoanRuralfinance;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.BannerPicture;
import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.FixedBorrowers;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.LoanExport;
import com.duanrong.business.loan.model.LoanVip;
import com.duanrong.business.loan.model.RuralfinanceLoaners;
import com.duanrong.business.loan.model.Supplychain;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.loan.service.LoanDetailService;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.loan.service.LoanVipService;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.model.RepayInvest;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.service.AgricultureForkLoansService;
import com.duanrong.business.transferstation.model.TransferStation;
import com.duanrong.business.transferstation.model.TransferStationForkLoans;
import com.duanrong.business.transferstation.service.TransferStationService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.constants.LoanConstants;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.FormUtil;
import com.duanrong.yeepay.service.TrusteeshipCancelInvestService;
import com.duanrong.yeepay.service.TrusteeshipGiveMoneyToBorrowerService;


@Controller
public class LoanController extends BaseController {
	@Resource
	LoanDao loanDao;
	@Autowired
	private Log log;

	@Autowired
	private LoanService loanService;

	@Autowired
	private LoanDetailService loanDetailService;

	@Autowired
	private InvestService investService;

	@Autowired
	private RepayService repayService;

	@Autowired
	private UserService userService;

	@Autowired
	private TrusteeshipGiveMoneyToBorrowerService trusteeshipGiveMoneyToBorrowerService;
	@Autowired
	private TrusteeshipCancelInvestService trusteeshipCancelInvestService;
	@Autowired
	private LoanVipService loanVipService;
	
	@Autowired 
	private AgricultureForkLoansService agricultureForkLoansService;
	
	@Autowired
	private TransferStationService transferStationService;
	
	/**
	 * 
	 * @description 跳转创建借款项目页面
	 * @author 孙铮
	 * @time 2015-3-11 下午1:05:04
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loan/toCreateLoanView", method = RequestMethod.GET)
	public String toCreateLoanView(HttpServletResponse response,
			HttpServletRequest request, Model model) {
 		List<FixedBorrowers> findAllFixedBorrowers = loanService
				.readAllFixedBorrowers();
		model.addAttribute("fbs", findAllFixedBorrowers);
		return "loan/createLoan";
	}

	
	 @RequestMapping({"loan/vehicle/{type}"})
	  public void vehicle(HttpServletResponse response, HttpServletRequest request, @PathVariable String type){
	    String id = request.getParameter("id");
	    String result = "ok";
	    Vehicle vehicle = this.loanDetailService.readVehicleDetail(id);
	    switch (type) { 
	      
	    case "del":
	        if (vehicle == null)
	          result = "no";
	        else {
	          try {
	            this.loanDetailService.deleteVehicle(vehicle);
	          } catch (Exception e) {
	            result = "err";
	          }
	       }
	        break;
	    case "edit":
	     
	        try {
	          String brand = request.getParameter("brand");
	          String licensePlateNumber = request.getParameter("licensePlateNumber");
	          String assessPrice = request.getParameter("assessPrice");
	          String kilometreAmount = request.getParameter("kilometreAmount");
	          String money = request.getParameter("money");
	          double m = money != null ? Double.parseDouble(money) : 0.0D;
	          String otherInfo = request.getParameter("otherInfo");
	          String registrationDate = request.getParameter("registrationDate");
	          SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd" );
	          String displacement = request.getParameter("displacement");
	          String transmission = request.getParameter("transmission");
	          String conditionAssessment = request.getParameter("conditionAssessment");
	          String borrowingPurposes = request.getParameter("borrowingPurposes");
	          String borrowerName = request.getParameter("borrowerName");
	          String itemAddress = request.getParameter("itemAddress");
	          String remark = request.getParameter("remark");
	          String yaCarAndGPS = request.getParameter("yaCarAndGPS");
	          String borrowerIdCard = request.getParameter("borrowerIdCard");
	          vehicle.setAssessPrice(assessPrice);
	          vehicle.setBrand(brand);
	          vehicle.setLicensePlateNumber(licensePlateNumber);
	          vehicle.setKilometreAmount(kilometreAmount);
	          vehicle.setOtherInfo(otherInfo);
	          if(StringUtils.isNotBlank(registrationDate)){
	        	  vehicle.setRegistrationDate(sdf.parse(registrationDate)); 
	          }
	          vehicle.setDisplacement(displacement);
	          vehicle.setTransmission(transmission);
	          vehicle.setConditionAssessment(conditionAssessment);
	          vehicle.setBorrowingPurposes(borrowingPurposes);
	          if (m > 0.0D) {
	            vehicle.setBorrowerName(borrowerName);
	            vehicle.setItemAddress(itemAddress);
	            vehicle.setRemark(remark);
	            vehicle.setYaCarAndGPS(yaCarAndGPS);
	            vehicle.setBorrowerIdCard(borrowerIdCard);
	          }
	          double om = m - vehicle.getMoney().doubleValue();
	          vehicle.setMoney(Double.valueOf(m));
	          this.loanDetailService.updateVehicle(vehicle);
	          Loan loan = this.loanService.read(vehicle.getLoanId());
	          loan.setTotalmoney(Double.valueOf(ArithUtil.round(loan.getTotalmoney().doubleValue() + om, 0)));
	          this.loanService.update(loan);
	        } catch (Exception e) {
	          result = "err";
	        }
	        break;
	        default :
	        break;
	      }
	    try {
	      response.getWriter().print(result);
	    }catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	/**
	 * 
	 * @description 跳转编辑项目基本信息页面
	 * @author 孙铮
	 * @time 2015-3-17 下午4:25:48
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loan/toEditLoanView", method = RequestMethod.GET)
	public String toEditLoanView(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		} else {
			String loanId = request.getParameter("loanId");
			if (StringUtils.isBlank(loanId)) {
				return "loan/loanList";
			}
			Loan loan = loanService.read(loanId);
			if (loan == null) {
				return "loan/loanList";
			}
			List<FixedBorrowers> findAllFixedBorrowers = loanService
					.readAllFixedBorrowers();
			model.addAttribute("fbs", findAllFixedBorrowers);
			model.addAttribute("loan", loan);
			List<BannerPicture> loanInfoPics = loanService
					.readLoanInfoPics(loanId);
			List<Vehicle> vehicles = loanDetailService.readVehicleList(loanId);
			model.addAttribute("vehicles", vehicles);
			model.addAttribute("pics", loanInfoPics);
		}
		return "loan/createLoan";
	}

	/**
	 * 
	 * @description 跳转创建借款项目页面
	 * @author 孙铮
	 * @time 2015-3-11 下午1:05:04
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loan/queryBorrower", method = RequestMethod.POST)
	public void queryBorrower(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			String borrower = request.getParameter("borrower");
			String result = loanService.readBorrowerMessageByUserId(borrower);
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description 处理上传
	 * @author 孙铮
	 * @time 2015-3-11 下午7:53:19
	 * @param files
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/loan/uploadLoanData", method = RequestMethod.POST)
	public void uploadLoanData(
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		loanService.uploadLoanData(files, request);
	}

	/**
	 * 
	 * @description 更新项目资料(图片)
	 * @author 孙铮
	 * @time 2015-3-20 上午11:21:00
	 * @param files
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/loan/updateImage", method = RequestMethod.GET)
	public ModelAndView updateImage(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		response.setCharacterEncoding("UTF-8");
		String op = request.getParameter("op");
		String picId = request.getParameter("picId");
		String loanId = request.getParameter("loanId");
		loanService.updateImage(op, picId, loanId);
		List<BannerPicture> loanInfoPics = loanService.readLoanInfoPics(loanId);
		Loan loan = loanService.read(loanId);
		model.addAttribute("loan", loan);
		model.addAttribute("pics", loanInfoPics);
		return new ModelAndView("loan/pics");
	}

	/**
	 * 更新项目图片seqNum
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loan/updateImageSeqNum", method = RequestMethod.GET)
	public void updateImageSeqNum(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		try {
			response.setCharacterEncoding("UTF-8");
			String picId = request.getParameter("picId");
			String seqNum = request.getParameter("seqNum");
			loanService.updateImageSeqNum(picId, seqNum);
			response.getWriter().write("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新项目图片Title
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loan/updateImageTitle", method = RequestMethod.POST)
	public void updateImageTitle(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		try {
			response.setCharacterEncoding("UTF-8");
			String picId = request.getParameter("id");
			String title = request.getParameter("title");
			String loanId = request.getParameter("loanId");
			loanService.updateImageTitle(picId, title, loanId);
			response.getWriter().write("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description 查看项目资料
	 * @author 孙铮
	 * @time 2015-3-19 下午12:21:37
	 * @param files
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/loan/queryUploadLoanData", method = RequestMethod.GET)
	public ModelAndView queryUploadLoanDataByloanId(
			HttpServletResponse response, HttpServletRequest request,
			Model model) {
		response.setCharacterEncoding("UTF-8");
		String loanId = request.getParameter("loanId");
		List<BannerPicture> loanInfoPics = loanService.readLoanInfoPics(loanId);
		Loan loan = loanService.read(loanId);
		model.addAttribute("loan", loan);
		model.addAttribute("pics", loanInfoPics);
		return new ModelAndView("loan/pics");
	}
	/**
	 * @description 删除借款人信息
	 * @author
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loan/deleteRuralfinanceLoaners", method = RequestMethod.POST)
	@ResponseBody
    public String deleteRuralfinanceLoaners(HttpServletResponse response,
		HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		String id = request.getParameter("ruralfinanceLoanersid");
		String loanId=request.getParameter("loanId");
		int flag=loanDetailService.deleteRuralfinanceLoaner(id, loanId);
		if(flag==1){			
			return "success";
		}else{
			return "fail";
		}
		
	}
	/**
	 * @description 验证借款人信息是否存在
	 * @author
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loan/isExistRuralfinanceLoaners", method = RequestMethod.POST)
	@ResponseBody
    public String isExistRuralfinanceLoaners(HttpServletResponse response,
		HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		String idCard = request.getParameter("idCard");
		String loanId=request.getParameter("loanId");
		try {
			RuralfinanceLoaners ruralfinanceLoaners = new RuralfinanceLoaners();
			ruralfinanceLoaners.setLoanId(loanId);
			ruralfinanceLoaners.setIdCard(idCard);	
		    ruralfinanceLoaners = loanDetailService.readRuralfinanceLoanerID(ruralfinanceLoaners);
			if (ruralfinanceLoaners != null) {		     	
				return "isexist";
			}else{
				return "noexist";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
		
		
	}
	/**
	 * @description 验证借款人信息是否存在
	 * @author
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loan/isExistRuralfinanceloaner", method = RequestMethod.POST)
	@ResponseBody
    public String isExistRuralfinanceloaner(HttpServletResponse response,
		HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		String loanId=request.getParameter("loanId");
		String loanerId=request.getParameter("loanerId");
		try {
			List<RuralfinanceLoaners> ruralfinanceLoanerslist=loanDetailService.readRuralfinanceLoanersDetail(loanId);
			List<String> loanerIdList = new ArrayList<>();
			for (int i = 0; i < ruralfinanceLoanerslist.size(); i++) {
			RuralfinanceLoaners ruralfinanceLoaners=ruralfinanceLoanerslist.get(i);
			loanerIdList.add(ruralfinanceLoaners.getLoanerId().toString());
			}
			if(loanerIdList.contains(loanerId)){
				return "isexist";
			}else{
				return "noexist";
			}
		      
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
		
		
	}

	/**
	 * 
	 * @description 保存借款项目
	 * @author 孙铮
	 * @time 2015-3-12 下午3:13:10
	 * @param files
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/loan/createLoan", method = RequestMethod.POST)
	@ResponseBody
	public void createLoan(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			Object loanOp = loanService.createLoan(request,
					getLoginUser.getUserId());
			JSONObject jsonObject = new JSONObject(loanOp);
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/loan/toCreateLoanDetailView")
	public String toCreateLoanDetailView(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		String param = request.getParameter("param");
		String returnViem = null;
		if (StringUtils.isBlank(param)) {
			returnViem = "/loan/loanList";
		}
		String[] split = param.split(",");
			if (split.length != 3) {
			returnViem = "/loan/loanList";
		}
		String loanId = split[0];
		String loanType = split[2];	
		String commitTime=split[1];
		
		String nowTime="2016-04-11";
		String status="";
		try {
			int days=DateUtil.DayDifference(nowTime,commitTime);
			if(days>0){
				//在固定时间之前
				status="more";	
			}else{
				//现在固定时间之后
				status="less";
			}
		if (StringUtils.isBlank(loanId) || !NumberUtils.isNumber(loanId)
				|| loanId.length() != 18) {
			returnViem = "/loan/loanList";
		} else {
			model.addAttribute("loanId", loanId);
		}
		if ("vehicle".equals(loanType)) {
			  List<Vehicle> vehicleDetail = loanDetailService.readVehicleList(loanId);
		      Loan loan = this.loanService.read(loanId);
		      model.addAttribute("loan", loan);
		      model.addAttribute("vehicles", vehicleDetail);
		      model.addAttribute("status", status);
			returnViem = "loan/vehicle";
		} else if ("enterprise".equals(loanType)) {
			Enterprise enterpriseDetail = loanDetailService
					.readEnterpriseDetail(loanId);
			model.addAttribute("enterprise", enterpriseDetail);
			model.addAttribute("status",status);
			returnViem = "loan/enterprise";
		} else if ("house".equals(loanType)) {
			House houseDetail = loanDetailService.readHouseDetail(loanId);
			model.addAttribute("status",status);
			model.addAttribute("house", houseDetail);
			returnViem = "loan/house";
		}else if ("supplychain".equals(loanType)){
			Supplychain supplychainDetail = loanDetailService.readSupplychainDetail(loanId);
			model.addAttribute("supplychain", supplychainDetail);	
			model.addAttribute("status",status);
			returnViem = "loan/supplychain";			
		}else if ("ruralfinance".equals(loanType)){
			LoanRuralfinance ruralfinanceDetail = loanDetailService.readRuralfinanceDetail(loanId);
			model.addAttribute("ruralfinance", ruralfinanceDetail);	
			Loan loan = loanDao.get(loanId);
			model.addAttribute("loan", loan);
			model.addAttribute("status",status);
			returnViem = "loan/ruralfinance";			
		} else {
			returnViem = "/loan/loanList";
		}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("loanType", loanType);
		return returnViem;
	}
	/**
	 * ajax获项目打包信息
	 * @param pageSize
	 * @param pageNo
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agricultural/PackagingLoan")
	public String getPackagingLoan(
			@RequestParam(value="pageSize",defaultValue="100") Integer pageSize,
			@RequestParam(value="pageNo",defaultValue="1") Integer pageNo,
			@RequestParam(value="loanTerm",defaultValue="1")Integer loanTerm,
			@RequestParam(value="loan_id")String loan_id,
			@RequestParam(value="type")String type,
			HttpServletRequest request,
			HttpServletResponse response){	
		Map<String, Object>params =new HashMap<>();
		params.put("loanTerm", loanTerm);	
		Map<String, Object> findloan=new HashMap<>();
		findloan.put("loanId",loan_id);
		findloan.put("loanTerm", loanTerm);		
		List<AgricultureForkLoans> ForkLoanslist=loanDetailService.readAgricultureForkLoans(findloan);
		if(ForkLoanslist !=null && ForkLoanslist.size()>0){
		
			if(type.equals("change"))
			{
				params.put("packing", "");
				params.put("flag", "demand");
				
			}
			else if(type.equals("demand")){
			   params.put("packing", "0");
			   params.put("flag", "agriculture");
			}
			else{
				params.put("packing", "1");
				params.put("loanId",loan_id);
			}
		}else{
			params.put("packing", "0");
		}	
		PageInfo<AgricultureForkLoans> pageInfo=agricultureForkLoansService.readPackagingLoan(pageNo,pageSize,params);
		JSONArray array = new JSONArray();
		for(Object obj : pageInfo.getResults()){
			AgricultureForkLoans forkLoans = (AgricultureForkLoans)obj;
			JSONObject object = new JSONObject();		
			try {
				object.put("id",forkLoans.getId());			
				object.put("username",forkLoans.getLoaninfo().getName());
				object.put("mobileNum",forkLoans.getLoaninfo().getMobileNumber());
				object.put("idCard",forkLoans.getLoaninfo().getIdCard());
				object.put("contractid",forkLoans.getChildContractid());//子合同编号
				object.put("money",forkLoans.getMoney());//子标金额
				object.put("loan_term",forkLoans.getLoanTerm()); //子标借款期限
				object.put("parentContractId",forkLoans.getLoaninfo().getContractId());//父标合同编号
				object.put("parentMoney",forkLoans.getLoaninfo().getParentMoney());//合同金额
				object.put("loanMoney",forkLoans.getLoaninfo().getLoanMoney());//放款金额
				object.put("serviceMoney",forkLoans.getLoaninfo().getServiceMoney());//服务费用
				object.put("parentLoanTerm",forkLoans.getLoaninfo().getParentLoanTerm());//父标借款期限
				object.put("rate",forkLoans.getLoaninfo().getRate());//父标利率
				object.put("repay_type",forkLoans.getLoaninfo().getRepayType());//父标还款方式
				array.add(object.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		JSONObject json = new JSONObject();
		json.put("pageNo",pageInfo.getPageNo());
		json.put("pageSize",pageInfo.getPageSize());
		json.put("totalPage",pageInfo.getTotalPage());
		json.put("totalRecord",pageInfo.getTotalRecord());
		json.put("list",array);						
		return  json.toString();
		
	}
	
	
	@RequestMapping(value = "/loan/createLoanDetail")
	@ResponseBody
	public String createLoanDetail(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		}
		response.setCharacterEncoding("UTF-8");
		try {
			Object createLoanDetail = loanService.createLoanDetail(request);
			JSONObject jsonObject = new JSONObject(createLoanDetail);
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 金农宝借款人信息添加
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loan/createRuralfinanceLoaners")
	@ResponseBody
	public String createRuralfinanceLoaners(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		}
		response.setCharacterEncoding("UTF-8");
		try {
			Object createLoanDetail = loanService.createRuralfinanceLoaners(request);
			JSONObject jsonObject = new JSONObject(createLoanDetail);
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/loan/loanList")
	public String loanView(HttpServletResponse response,
			HttpServletRequest request) {
		int pageNo = 0;
		int pageSize = 15;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		Loan loan = new Loan();
		loan.setId(request.getParameter("id"));
		loan.setName(request.getParameter("name"));
		loan.setStatus(request.getParameter("status"));
		loan.setTextItem(request.getParameter("textItem"));
		loan.setStartTime(request.getParameter("start"));
		loan.setEndTime(request.getParameter("end"));
		loan.setLoanType(request.getParameter("loanType"));
		loan.setNewbieEnjoy(request.getParameter("newbieEnjoy"));
		loan.setCompanyno(request.getParameter("companyno"));
		String line = request.getParameter("deadline");
		if(line != null && !line.equals("")){
			loan.setDeadline(Integer.parseInt(line));
		}
		loan.setMinMoney(request.getParameter("minMoney"));
		loan.setMaxMoney(request.getParameter("maxMoney"));
		loan = (Loan) FormUtil.getForParamToBean(loan);
		PageInfo<Loan> pageInfo = loanService.readPageList(pageNo, pageSize, loan);
		List<Loan> list = pageInfo.getResults();
		List<Loan> l = new ArrayList<>();
		for (Loan ln : list) {
			ln.setOpendAuto(investService.readAutoInvestByLoan(ln.getId()) > 0 ? true
					: false);
			l.add(ln);
		}
		pageInfo.setResults(l);
		request.setAttribute("url", "/loan/loanList");
		request.setAttribute("str", FormUtil.getForParam(loan));
		request.setAttribute("loanType", loan.getLoanType());
		request.setAttribute("textItem", loan.getTextItem());
		request.setAttribute("status", loan.getStatus());
		request.setAttribute("start", loan.getStartTime());
		request.setAttribute("end", loan.getEndTime());
		request.setAttribute("newbieEnjoy", loan.getNewbieEnjoy());
		request.setAttribute("companyno", loan.getCompanyno());
		request.setAttribute("deadline", loan.getDeadline());
		request.setAttribute("minMoney", loan.getMinMoney());
		request.setAttribute("maxMoney", loan.getMaxMoney());
		request.setAttribute("pageInfo", pageInfo);
		return "loan/loanList";

	}

	@RequestMapping(value = "/loan/toVerifyLoanView")
	public String toVerifyLoanView(HttpServletResponse response,
			HttpServletRequest request, RedirectAttributes redirectAttributes,
			Model model) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		}
		String loanId = request.getParameter("loanId");
		if (StringUtils.isBlank(loanId)) {
			userMessage(redirectAttributes, "温馨提示", "借款项目id错误");
			return BaseController.redirect + "/loan/loanList";
		} else {
			Loan loan = loanService.read(loanId);
			if (loan == null) {
				userMessage(redirectAttributes, "温馨提示", "借款项目错误");
				return BaseController.redirect + "/loan/loanList";
			} else {
				Invest invest = new Invest();
				invest.setStatus("!流标");
				invest.setLoanId(loanId);
				List<Invest> invests = investService.readInvestLoan(invest);
				double remainInvesteMoney = loanService
						.calculateMoneyNeedRaised(loanId);// 尚未募集金额
				Double validInvestSumByLoan = investService
						.readValidInvestSumByLoan(loanId);// 以募集金额(!=流标的)

				User user = userService.read(loan.getBorrowMoneyUserID());
				loan.setBorrowMoneyUserName(user.getRealname());
				Repay repay = new Repay();
				repay.setLoanId(loanId);
				repay.setStatus(LoanConstants.RepayStatus.REPAYING);
				List<Repay> repays = repayService.readByCondition(repay);
				model.addAttribute("alreadyInvestedMoney", validInvestSumByLoan);
				model.addAttribute("remainInvesteMoney", remainInvesteMoney);
				model.addAttribute("invests", invests);
				model.addAttribute("loan", loan);
				model.addAttribute("repays", repays);
				return "loan/verifyLoan";
			}
		}
	}

	@RequestMapping(value = "/loan/giveMoneyToBorrower")
	public String giveMoneyToBorrower(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
	/*	try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			}
			
			SimpleDateFormat format = new SimpleDateFormat("mm");
	        int a = Integer.parseInt(format.format(new Date()));
	        if (10 <  a && 20 > a || 40 < a && 50 > a){
	        	response.getWriter().write("自动放款期间不许手动放款！！！");
	        }else{
	        	String loanId = request.getParameter("loanId");
				String giveMoneyToBorrower = trusteeshipGiveMoneyToBorrowerService
						.giveMoneyToBorrower(getLoginUser.getUserId(), loanId);
				response.getWriter().write(giveMoneyToBorrower);
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
			log.infoLog("放款LoanController", e);
			try {
				response.getWriter().write("放款出现异常,请联系管理员!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}*/
		try {
			response.getWriter().write("手动款款已屏蔽，请等待自动放款！！！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/loan/cancel")
	public String cancel(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			}
			String loanId = request.getParameter("loanId");
			String cancel = "";
			try {
				cancel = trusteeshipCancelInvestService.cancelInvest(
						getLoginUser.getUserId(), loanId);
			} catch (InsufficientBalance e) {
				cancel = "流标失败";
			}
			response.getWriter().write(cancel);
		} catch (IOException e) {
			e.printStackTrace();
			log.infoLog("流标LoanController", e);
			try {
				response.getWriter().write("流标出现异常,请联系管理员!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/loan/giveMoneyToBorrowerByLocal")
	public String giveMoneyToBorrowerByLocal(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		/*try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			}
			String loanId = request.getParameter("loanId");
			String actionTime = request.getParameter("actionTime");
			String actionMessage = request.getParameter("actionMessage");

			String localGiveMoneyToBorrower = loanService
					.localGiveMoneyToBorrower(getLoginUser.getUserId(), loanId,
							actionTime, actionMessage);
			response.getWriter().write(localGiveMoneyToBorrower);
		} catch (IOException e) {
			e.printStackTrace();
			log.infoLog("本地放款LoanController", e);
			try {
				response.getWriter().write("本地放款出现异常,请联系管理员!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}*/
		try {
			response.getWriter().write("手动款款已屏蔽，请等待自动放款！！！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/loan/repayMoneyLocal")
	public String repayMoneyLocal(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);			
			String loanId = request.getParameter("loanId");
			String actionTime = request.getParameter("actionTime");
			String actionMessage = request.getParameter("actionMessage");
			String repayId = request.getParameter("repayId");
			String befoRepay = request.getParameter("befoRepay");
			
			String localRepay = repayService.localRepay(
					getLoginUser.getUserId(), loanId, actionTime,
					actionMessage, repayId, befoRepay);
			response.getWriter().write(localRepay);
		} catch (Exception e) {
			e.printStackTrace();
			log.infoLog("本地还款LoanController", e);
			try {
				response.getWriter().write("本地还款出现异常,请联系管理员!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/loan/getLoanById")
	public void getLoanById(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String loanId = request.getParameter("loanId");
		Loan loan = loanService.read(loanId);
		if (loan == null) {
			response.getWriter().print("null");
		} else {
			JSONArray jsonArr = JSONArray.fromObject(loan);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(jsonArr.toString());
		}
	}

	@RequestMapping(value = "/loan/toRepayView")
	public String toRepayView(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String loanId = request.getParameter("param");
		List<Repay> repays = repayService.readRepayByLoan(loanId);
		request.setAttribute("repays", repays);
		return "repay/repayView";
	}

	@RequestMapping(value = "/loan/toRepay")
	public String toRepay(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String id = request.getParameter("id");
		Repay repay = repayService.read(id);
		request.setAttribute("repay", repay);
		return "repay/alertRepay";
	}

	@RequestMapping(value = "/loan/alertRepay")
	public void alertRepay(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String id = request.getParameter("id");
		String period = request.getParameter("period");
		String time = request.getParameter("time");
		String repayDay = request.getParameter("repayDay");
		Repay repay = repayService.readRepayById(id);
		if (repay != null) {
			if (MyStringUtils.isNumeric(period)) {
				repay.setPeriod(Integer.parseInt(period));
			}
			if (MyStringUtils.isNotAnyBlank(time, repayDay)) {
				repay.setTime(DateUtil.StringToDate(time));
				repay.setRepayDay(DateUtil.StringToDate(repayDay));
			}
			try {
				repayService.alertRapay(repay);
				response.getWriter().print("ok");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@RequestMapping(value = "/loan/repayList/{type}")
	public String repayList(HttpServletResponse response,
			HttpServletRequest request, Model model, @PathVariable String type) {
		int pageNo = 0;
		int pageSize = 30;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		String str="";
		Map<String, Object> map = new HashMap<String, Object>();
		String loanId=request.getParameter("loanId");
		if(loanId!=null && !loanId.equals("")){
			loanId=loanId.trim();
		}
		String loanName=request.getParameter("loanName");
		if(loanName!=null && !loanName.equals("")){
			loanName=loanName.trim();
		}
		String nameOrMobile = request.getParameter("nameOrMobile");
		if (nameOrMobile!=null&&!"".equals(nameOrMobile)) {
			if (MyStringUtils.isNumeric(nameOrMobile) && nameOrMobile.length() == 11) {
				str += "&nameOrMobile=" + nameOrMobile;
				map.put("mobileNumber", nameOrMobile);
			} else {
				try {
					str += "&nameOrMobile=" + nameOrMobile;
					map.put("realname", URLDecoder.decode(nameOrMobile,"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		map.put("loanId",loanId);
		map.put("loanName",loanName);
		map.put("start", request.getParameter("start"));
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end)) {
			//String endtime = DateUtil.addDay(end, 1);
			map.put("end", end+" 23:59:59");
		}
		map.put("onLinestart", request.getParameter("onLinestart"));
		String onLineend = request.getParameter("onLineend");
		if (StringUtils.isNotBlank(onLineend)) {
			//String endtime = DateUtil.addDay(end, 1);
			map.put("onLineend", onLineend+" 23:59:59");
		}

		map.put("status", request.getParameter("status"));

		if (type != null && !"".endsWith(type)) {
			if ("operationTime".equals(type)) {
				map.put("type", "r.operation_time");
			}
			if ("repayDay".equals(type)) {
				map.put("type", "r.repay_day");
			}
		} else {
			map.put("type", "r.operation_time");
		}
		try {
			map = FormUtil.getForParamToBean(map);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		PageInfo<Repay> pageInfo = repayService.readPageInfo(pageNo, pageSize,
				map);
		Repay repayMoney=repayService.readTotalMoney(map);
		List<Repay> list = pageInfo.getResults();
		request.setAttribute("str", FormUtil.getForParam(map)+str);
		request.setAttribute("pageInfo", pageInfo);
		String status = request.getParameter("status");
		if (status != null && !"".equals(status)) {
			try {
				status = URLDecoder.decode(status, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (nameOrMobile!=null) {
			try {
				model.addAttribute("nameOrMobile",URLDecoder.decode(nameOrMobile, "UTF-8"));
			
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("loanId", loanId);
		model.addAttribute("loanName", loanName);
		model.addAttribute("status", status);
		model.addAttribute("start", request.getParameter("start"));
		model.addAttribute("end", request.getParameter("end"));
		model.addAttribute("onLinestart", request.getParameter("onLinestart"));
		if (request.getParameter("onLineend")!=null) {
			try {
				model.addAttribute("onLineend", URLDecoder.decode(request.getParameter("onLineend"), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("interestAnd", repayMoney.getInterestAnd());
		model.addAttribute("principalAnd",  repayMoney.getPrincipalAnd());
		return "loan/repayList";

	}

	@RequestMapping(value = "/loan/fixedborrowers")
	public ModelAndView toFixedBorrowersView(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<FixedBorrowers> queryFixedBorrowers = loanService
				.readAllFixedBorrowers();
		ModelAndView modelAndView = new ModelAndView("loan/fixedborrowers");
		modelAndView.addObject("fbs", queryFixedBorrowers);
		return modelAndView;
	}

	@RequestMapping(value = "/loan/addfixedborrowers")
	public void addFixedBorrowers(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser != null) {
				String borrower = request.getParameter("borrower");
				String email = request.getParameter("email");
				loanService.addFixedBorrower(borrower, email);
				log.infoLog("添加固定借款人", getLoginUser.getUserId() + "添加了一个固定借款人:"
						+ borrower);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("添加固定借款人", e);
		}
	}

	@RequestMapping(value = "/loan/alterFixedBorrowersStatus")
	public void alterFixedBorrowersStatus(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			if (getLoginUser != null) {
				response.setCharacterEncoding("UTF-8");
				String borrower = request.getParameter("borrower");
				String status = request.getParameter("status");
				String email = request.getParameter("email");
				if (StringUtils.isBlank(borrower)) {
					return;
				}
				loanService.alterFixedBorrowersStatus(borrower, status, email);
				log.infoLog("编辑固定借款人", getLoginUser.getUserId() + "编辑了一个固定借款人:"
						+ borrower + "将状态修改为:" + status);
				response.getWriter().write(status);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("编辑固定借款人", e);
		}
	}

	@RequestMapping(value = "/loan/updateOrganizationExclusiveStatus")
	public void updateOrganizationExclusiveStatus(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			if (getLoginUser != null) {
				response.setCharacterEncoding("UTF-8");
				String loanId = request.getParameter("loanId");
				String updateOrganizationExclusiveStatus = loanService
						.updateOrganizationExclusiveStatus(loanId);
				log.infoLog("修改机构专享项目状态", getLoginUser.getUserId() + "LoanId:"
						+ loanId);
				response.getWriter().write(updateOrganizationExclusiveStatus);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("修改机构专享项目状态", e);
		}
	}
	
	@RequestMapping(value = "/loan/delLoan")
	public void delLoan(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		UserCookie getLoginUser = GetLoginUser(request, response);
		String msg = "ok";
		try {
			if (getLoginUser != null) {
				response.setCharacterEncoding("UTF-8");
				String loanId = request.getParameter("loanId");
				if(StringUtils.isBlank(loanId)){
					msg="项目id不正确";
				}
				else{
					/*Loan loan = loanService.read(loanId);*/
					//截取项目编号前16位
					String subLoanId = loanId.substring(0, 16);
					/*loan.setIsdeal(-1);
					loanService.update(loan);*/
					List<Loan> loans = loanService.readSubLoan(subLoanId);
					if (loans.size()!=0) {
						loanService.delLoans(loans,loanId);
					}else {
						msg="无法删除项目";
					}
				}
				log.infoLog("删除项目", getLoginUser.getUserId() + "LoanId:"
						+ loanId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("删除项目异常",e);
			msg="删除项目异常";
		}
		response.getWriter().write(msg);
	}

	@RequestMapping(value = "/loan/exportLoan")
	public void exportLoan(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		Map<String, Object> map = new HashMap<>();		
		map.put("id", request.getParameter("id"));
		map.put("name", request.getParameter("name"));
		if (request.getParameter("loanType")!=null) {
			map.put("loanType", URLDecoder.decode(request.getParameter("loanType"),"UTF-8"));
		}
		map.put("status", request.getParameter("status"));
		map.put("textItem", request.getParameter("textItem"));
		map.put("newbieEnjoy", request.getParameter("newbieEnjoy"));
		map.put("companyno", request.getParameter("companyno"));
		map.put("deadline", request.getParameter("deadline"));
		map.put("minMoney", request.getParameter("minMoney"));
		map.put("maxMoney", request.getParameter("maxMoney"));
		map.put("start", request.getParameter("start"));
		map.put("end", request.getParameter("end"));
		
		map = FormUtil.getForParamToBean(map);
		List<LoanExport> list = new ArrayList<>();
		List<Loan> loans = loanService.readLoansByGroupCondition1(map);				
		for(Loan loan : loans){
			LoanExport export = new LoanExport();
			export.setId(loan.getId());
			if(loan.getTextItem() == "是"){
				export.setCommitTime(null);
			}else{
				export.setCommitTime(loan.getCommitTime());
			}		
			export.setLoanName(loan.getName());
			export.setCalculateMoneyNeedRaised(loan.getCalculateMoneyNeedRaised()/10000D);
			export.setMoney(loan.getTotalmoney()/10000D);
			export.setRate(loan.getRate() * 100);
			if(loan.getOperationType().equals("月")){
				export.setDeal(loan.getDeadline()+"个月");
			}else{
				export.setDeal(loan.getDay() + "天");				
			}
			
			export.setLoanType(loan.getLoanType());
			export.setRepayType(loan.getRepayType());	
			export.setBrank(loan.getBrand());
			export.setLicensePlateNumber(loan.getLicensePlateNumber());		
			export.setItemAddress(loan.getItemAddress());
			export.setRealname(loan.getBorrowerName());
			export.setRemark(loan.getRemark());
			export.setTextItem(loan.getTextItem());
			export.setPawnandgps(loan.getYaCarAndGPS());
			if(loan.getSingleMoney() == 0){
				export.setSingleMoney(null);
			}else{
				export.setSingleMoney(loan.getSingleMoney()/10000);
			}
			
			export.setBorrowerIdCard(loan.getBorrowerIdCard());
			if("是".equals(loan.getOrganizationExclusive()))
			{
				export.setOrganizationExclusiveExport("机构标");
			}
			else
			{
				export.setOrganizationExclusiveExport(" ");
			}
			if(loan.getAccountingDepartment()!= null){
				if(loan.getAccountingDepartment()==0)
				{
					export.setAccountingDepartmentExport("齐海");
				}
				else if(loan.getAccountingDepartment()==1)
				{
					export.setAccountingDepartmentExport("久亿");
				}				
			}else
			{
				export.setAccountingDepartmentExport(" ");
			}
			list.add(export);
		}		
		Map<String, String> title = new LinkedHashMap<>();
		title.put("id", "项目编号");
		title.put("loanName", "项目名称");
		title.put("commitTime", "上线日期");	
		title.put("loanType", "项目类型");
		title.put("calculateMoneyNeedRaised", "募集金额(万)");
		title.put("money", "放款金额(万)");
		title.put("singleMoney", "子标金额(万)");
		title.put("deal", "期限");		
		title.put("rate", "项目年化(%)");		
		title.put("textItem", "是否为测试项目");
		title.put("brank", "车辆型号/房产地址");
		title.put("licensePlateNumber", "车牌号码");
		title.put("pawnandgps", "抵押/GPS");			
		title.put("realname", "实际借款人姓名");
		title.put("borrowerIdCard", "实际借款人身份证");
		title.put("remark", "新增/展期");
		title.put("itemAddress", "项目地点");
		title.put("organizationExclusiveExport", "是否机构标");
		title.put("accountingDepartmentExport", "核算单位");
		int[] style = {20, 20, 18, 12, 12, 12, 12, 12, 12, 12,40, 12, 12, 12, 20, 12, 20,15,15}; 
		String fileName = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		fileName = "loan" + fileName + ".xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ fileName);
		ExcelConvertor excelConvertor = new ExcelConvertor(
				response.getOutputStream(), fileName);
		String t = "借款项目数据";	
		excelConvertor.setDateformat("yyyy-MM-dd");
		excelConvertor.excelExport(list, title, t, style);

	}
	@RequestMapping(value = "/loan/loanMenu")
	public String loanMenu(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		int pageNo = 0;
		int pageSize = 10;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		String end = request.getParameter("end");
		Loan loan=new Loan();
		loan.setStart(request.getParameter("start"));
		if(end!=null && !"".equals(end)){
			loan.setEnd(end+" 23:59:59");
		}
		
		loan.setStatusStr("statusStr");
		PageInfo<Loan> pageInfo = loanService.readPageLite(pageNo, pageSize, loan);
		List<Loan> list = pageInfo.getResults();
		request.setAttribute("str", FormUtil.getForParam(loan));
		request.setAttribute("pageInfo", pageInfo);
		model.addAttribute("start", request.getParameter("start"));
		model.addAttribute("end", request.getParameter("end"));
		return "loan/loanMenu";

	}
	@RequestMapping(value = "/loan/exportList")
	public void export(HttpServletResponse response,
			HttpServletRequest request) throws IOException {				
		Loan loan=new Loan();
		String end = request.getParameter("end");
		loan.setStart(request.getParameter("start"));
		if(end!=null && !"".equals(end)){
			loan.setEnd(end+" 23:59:59");
		}
		loan.setStatusStr("statusStr");
		try{
				List<Loan> loans = loanService.readLoan(loan);
				String t = "放款信息";
				Map<String, String> title = new LinkedHashMap<>();
				title.put("borrowMoneyUserID", "所属账户");
				title.put("borrowMoneyUserName", "所属账户真实姓名");
				title.put("id", "项目编号");
				title.put("name", "项目名称");				
				title.put("loanType", "项目类型");
				title.put("borrowerName", "真实贷款人姓名");
				title.put("brand", "车型");
				title.put("licensePlateNumber", "车牌号");
				title.put("giveMoneyOperationTime", "放款时间");
				title.put("totalmoney", "放款金额");
				title.put("loanGuranteeFee", "扣款金额");
				title.put("maxrepayday", "还款时间");
				title.put("maxreapymoney", "还款金额");
				int[] style = {30, 15,30, 30, 15, 20,20,30,30,15,15,30,15};								
				String fileName = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				fileName = "放款信息"+fileName+".xls";	
				response.setContentType("application/vnd.ms-excel");     
		        response.setHeader("Content-disposition", "attachment;filename=" + fileName); 	
				ExcelConvertor excelConvertor = new ExcelConvertor(response.getOutputStream(), fileName);
				excelConvertor.excelExport(loans, title, t,style);				
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	@RequestMapping(value = "/loan/repayExport")
	public void repayExport(HttpServletResponse response,
			HttpServletRequest request) throws IOException {				
		Map<String, Object> map = new HashMap<String, Object>();
		String loanId=request.getParameter("loanId");
		if(loanId!=null && !loanId.equals("")){
			loanId=loanId.trim();
		}
		String loanName=request.getParameter("loanName");
		if(loanName!=null && !loanName.equals("")){
			loanName=loanName.trim();
		}
		String param = request.getParameter("userId");
		if (param!=null&&!"".equals(param)) {
			if (MyStringUtils.isNumeric(param) && param.length() == 11) {
				map.put("mobileNumber", param);
			} else {
				map.put("realname", param);
			}
		}
		map.put("loanId",loanId);
		map.put("loanName",loanName);
		map.put("start", request.getParameter("start"));
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end)) {
			//String endtime = DateUtil.addDay(end, 1);
			map.put("end", end+" 23:59:59");
		}
		map.put("onLinestart", request.getParameter("onLinestart"));
		String onLineend = request.getParameter("onLineend");
		if (StringUtils.isNotBlank(onLineend)) {
			//String endtime = DateUtil.addDay(end, 1);
			map.put("onLineend", onLineend+" 23:59:59");
		}

		map.put("status", request.getParameter("status"));
		map.put("type", "r.repay_day");
		
		try {
			map = FormUtil.getForParamToBean(map);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
				List<Repay> repays = repayService.readRepay(map);
				List<Repay> list = new ArrayList<>();
				for (Repay repay : repays) {
					Repay repayExport = new Repay();
					repayExport.setItemAddress(repay.getItemAddress());
					repayExport.setLoanType(repay.getLoanType());
					repayExport.setLoanName(repay.getLoanName());
					repayExport.setRealname(repay.getRealname());
					repayExport.setCorpus(repay.getCorpus());
					repayExport.setInterest(repay.getInterest());
					repayExport.setPeriod(repay.getPeriod());
					repayExport.setTime(repay.getTime());
					repayExport.setRepayDay(repay.getRepayDay());
					repayExport.setOperationTime(repay.getOperationTime());
					repayExport.setStatus(repay.getStatus());
					repayExport.setYaCarAndGPS(repay.getYaCarAndGPS());
					repayExport.setRemark(repay.getRemark());
					repayExport.setRate(repay.getLoan().getRate());
					repayExport.setLicensePlateNumber(repay.getLoan().getLicensePlateNumber());
					repayExport.setCommitTime(repay.getLoan().getCommitTime());
					repayExport.setNewbieEnjoy(repay.getLoan().getNewbieEnjoy());
					Integer accountingDepartment = repay.getLoan().getAccountingDepartment();
					if (accountingDepartment!=null) {
						if (accountingDepartment==0) {
							repayExport.setAccountingDepartment("齐海");
						}
						if (accountingDepartment==1) {
							repayExport.setAccountingDepartment("短融");
						}
						
					}
					String companyno = repay.getLoan().getCompanyno();
					if (companyno!=null) {
						if ("0".equals(companyno)) {
							repayExport.setCompanyno("否");
							repayExport.setBorrowerName(repay.getBorrowerName());
						}
						if ("1".equals(companyno)) {
							repayExport.setCompanyno("是");
							repayExport.setBorrowerName("");
						}
						
					}
					
					if ("月".equals(repay.getLoan().getOperationType())) {
						repayExport.setLoanTerm(repay.getLoan().getDeadline()+"月");
					}else{
						repayExport.setLoanTerm(repay.getLoan().getDay()+"日");
					}
					
					list.add(repayExport);
				}
				String t = "还款信息";
				Map<String, String> title = new LinkedHashMap<>();
				title.put("repayDay", "还款日");
				title.put("commitTime","上线时间");
				title.put("time", "起息日");
				title.put("accountingDepartment", "核算公司");
				title.put("itemAddress", "分公司");
				title.put("loanType", "类型");
				title.put("loanName", "项目名称");
				title.put("realname", "固定出借人");	
				title.put("borrowerName", "真实借款人");
				title.put("corpus", "本金");
				title.put("interest", "利息");
				title.put("rate", "发行利率");
				title.put("loanTerm","借款期限");
				title.put("period", "期号");
				title.put("status", "状态");
				title.put("operationTime", "实际还款日");
				title.put("licensePlateNumber", "车牌号码");
				title.put("yaCarAndGPS", "借款方式");
				title.put("remark", "新增/展期");
				title.put("companyno", "是否打包");
				title.put("newbieEnjoy", "是否为新手标");
				int[] style = {15,15,15,15,15,30,20, 20, 15,15,15,15,10,10,10,10,15,12, 10,10,10};								
				String fileName = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				fileName = "还款信息"+fileName+".xls";	
				response.setContentType("application/vnd.ms-excel");     
		        response.setHeader("Content-disposition", "attachment;filename=" + fileName); 	
				ExcelConvertor excelConvertor = new ExcelConvertor(response.getOutputStream(), fileName);
				excelConvertor.excelExport(list, title, t,style);				
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	@RequestMapping(value = "/loan/repayInvest")
	public String repayInvest(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		String id=request.getParameter("id");
		List<RepayInvest> list = repayService.readByReapyId(id);
		model.addAttribute("list", list);
		return "loan/repayInvest";
	}
	@RequestMapping(value = "/loan/vipList")
	public String vipList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String pageNo = request.getParameter("pageNo");
			String id = request.getParameter("id");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			PageInfo pageInfo = loanVipService.readPageInfo(
					Integer.parseInt(pageNo), 10);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("loanId", id);
			return "loan/vipList";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.LoanController.vipList()",
					e);
		}
		return null;
	}
	@RequestMapping(value = "/loan/queryVip", method = RequestMethod.POST)
	public void queryVip(HttpServletRequest request,
			HttpServletResponse response,Model model) {	
		try {
				String search = request.getParameter("search");
				if (StringUtils.isNumeric(search)
						&& StringUtils.length(search) == 11) {
					User u=userService.readUserByMobileNumber(search);
					if(u==null){
						response.getWriter().print("notexist");
						return ;
					}else{
						List<String> roles = userService.readRoles(u.getUserId());
						if (roles.isEmpty() || !roles.contains("INVESTOR")) {
							response.getWriter().print("INVESTOR_NULL");
							return;
						} else {
							response.getWriter().print( "帐号: " + u.getUserId() + "姓名:"
									+ u.getRealname());
						}
					}
				}else {
					User u=userService.read(search);
					if(u==null){
						response.getWriter().print("notexist");
						return;
					}else{
						List<String> roles = userService.readRoles(u.getUserId());
						if (roles.isEmpty() || !roles.contains("INVESTOR")) {
							response.getWriter().print("INVESTOR_NULL");
							return;
						} else {
							response.getWriter().print( "帐号: " + u.getUserId() + "姓名:"
									+ u.getRealname());
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	@RequestMapping(value = "/loan/editVip", method = RequestMethod.POST)
	public void editVip(HttpServletRequest request,
			HttpServletResponse response,Model model) {	
		try {
				String search = request.getParameter("search");
				String loanId = request.getParameter("loanId");
				if (StringUtils.isNumeric(search)
						&& StringUtils.length(search) == 11) {
					User u=userService.readUserByMobileNumber(search);
					if(u==null){
						response.getWriter().print("fail");
						return;
					}else{
						LoanVip vip=loanVipService.read(u.getUserId());
						if(vip!=null){
							response.getWriter().print("fail");
							return;
						}else{
							LoanVip loanVip=new LoanVip();
							loanVip.setLoanId(loanId);
							loanVip.setUserId(u.getUserId());
							loanVip.setStatus(1);
							loanVip.setTime(new Date());
							loanVipService.insert(loanVip);
						}
						
					}
				}else {
					User u=userService.read(search);
					if(u==null){
						response.getWriter().print("fail");
						return;
					}else{
						LoanVip vip=loanVipService.read(u.getUserId());
						if(vip!=null){
							response.getWriter().print("fail");
							return;
						}else{
						LoanVip loanVip=new LoanVip();
						loanVip.setLoanId(loanId);
						loanVip.setUserId(u.getUserId());
						loanVip.setStatus(1);
						loanVip.setTime(new Date());
						loanVipService.insert(loanVip);
						}
					}
				}
			
				response.getWriter().print("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	@RequestMapping(value = "/loan/delVip")
	public void delVip(HttpServletResponse response, HttpServletRequest request) {
		String id = request.getParameter("id");		
		try {
			LoanVip loanVip=new LoanVip();
			loanVip.setId(Integer.parseInt(id));
			loanVipService.update(loanVip);
			response.getWriter().print("ok");
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.errLog("活期宝基本信息删除", e);
		}
	}
	
	
	@RequestMapping(value = "/loan/investRedpacket")
	public String investRedpacket(HttpServletRequest request, Model model) {
		int pageNo = 0;
		int pageSize = 30;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		String loanId = request.getParameter("loanId");
		String investId = request.getParameter("investId");
		String userId = this.getUserId(request.getParameter("user"));
		String sendAllowanceStatus = request.getParameter("sendAllowanceStatus");
		int allowanceStatus = -2;
		if(sendAllowanceStatus != null){
			Integer.parseInt(sendAllowanceStatus);			
		}
		String sendRedpacketStatus = request.getParameter("sendRedpacketStatus");
		int redpacketStatus = -2;
		if(sendRedpacketStatus != null){
			Integer.parseInt(sendRedpacketStatus);
		
		}	
		InvestRedpacket investRedpacket = new InvestRedpacket();
		investRedpacket.setLoanId(loanId);
		investRedpacket.setUserId(userId);
		investRedpacket.setInvestId(investId);
		investRedpacket.setSendAllowanceStatus(allowanceStatus);
		investRedpacket.setSendRedpacketStatus(redpacketStatus);
		PageInfo<InvestRedpacket> pageInfo = investService.readPageLiteInvestredpacket(pageNo, pageSize, investRedpacket);
		request.setAttribute("str", FormUtil.getForParam(investRedpacket));
		request.setAttribute("pageInfo", pageInfo);
		String status = request.getParameter("status");
		if (status != null && !"".equals(status)) {
			try {
				status = URLDecoder.decode(status, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("loanId", loanId);
		model.addAttribute("investId", investId);
		model.addAttribute("user", request.getParameter("user"));
		model.addAttribute("sendAllowanceStatus", sendAllowanceStatus);
		model.addAttribute("sendRedpacketStatus", sendRedpacketStatus);
		return "loan/investRedpacketList";
	}
	@RequestMapping(value = "/loan/updateBatchPicTitle")
	@ResponseBody
	public String allPicTitle(
			@RequestParam(value = "ids[]", required = true) String[] ids,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "loadId", required = true) String loadId,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			String result = "fail";
			if (ids != null && ids.length > 0) { 
				Map<String, Object> demandParam = new HashMap<>();
				demandParam.put("title", title);
				demandParam.put("arr", ids);
				loanService.updateBatchPicTitle(demandParam, loadId);
					result = "success";		
				
			}	
			return result;
		
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	@RequestMapping(value = "/loan/editSortNum" ,method = RequestMethod.POST)
	@ResponseBody
	public String editSortNum(HttpServletResponse response, HttpServletRequest request){
		String loanId = request.getParameter("id");
		String sortNum = request.getParameter("sortNum");
		Loan loan = new Loan();
		loan.setId(loanId);
		loan.setSortNum(Integer.parseInt(sortNum));
		try {
			loanService.updateSortNum(loan);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
}