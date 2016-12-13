package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.Log;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.activity.model.ActivateCoupon;
import com.duanrong.business.activity.model.ActivateCouponRecord;
import com.duanrong.business.activity.model.RedPacketDetail;
import com.duanrong.business.activity.model.RedPacketRule;
import com.duanrong.business.activity.service.RedPacketDetailService;
import com.duanrong.business.activity.service.RedPacketRuleService;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.AES;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.FormUtil;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.newadmin.utility.IdUtil;

/**
 * 活动参加人Controller
 * @author Administrator
 *
 */
@Controller
public class RedPacketDetailController extends BaseController {
	@Autowired
	private Log log;
	@Autowired
	private RedPacketRuleService redPacketRuleService;
	@Autowired
	private UserService userService;
	@Autowired
	private RedPacketDetailService redPacketDetailService;
	@Resource
	OperaRecordService operaRecordService;
	
	
	/**
	 * 
	 * @description 跳转创建加息券项目页面
	 * @author wangjing
	 * @time 2015-2-5 下午1:47:11
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/award/toCreatecoupon", method = RequestMethod.GET)
	public String toCreatecouponView(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		} else {
			List<RedPacketRule> redPacketRules =redPacketRuleService.readAll();
			request.setAttribute("redPacketRules", redPacketRules);
			return "activity/createCoupon";
		}
	}
	
	@RequestMapping(value = "/award/createCoupon", method = RequestMethod.POST)
	public void createCoupon(HttpServletResponse response,
			HttpServletRequest request) throws IOException{	
		UserCookie getLoginUser = GetLoginUser(request, response);
 		//活动类型的id
		String itemType = request.getParameter("itemType");
		String moneyType1 = request.getParameter("moneyType1");
		//创建时间
		String createTime = request.getParameter("createTime");
		//到期时间
		String expiredTime = request.getParameter("expiredTime");
		//invest应用类型
		String usageDetail = request.getParameter("usageDetail");
		//满多少元可用
		String investMoney = request.getParameter("investMoney");
		//满多少利率可用
		String investRate = request.getParameter("investRate");
		//手机号 加息券
		String details = request.getParameter("details");
		//加息券类型
		String redpacketType=request.getParameter("redpacketType");
		//使用期限
		String timeType=request.getParameter("timeType");
		double investMoney1=0;
		double investRate1=0;
		if(investMoney!=null && investMoney!=""){
			investMoney1=Double.parseDouble(investMoney);
		}
		if(investRate!=null && investRate!=""){
			investRate1=Double.parseDouble(investRate);
		}
		RedPacketRule redPacketRule=null;
		if(itemType!=null && !itemType.equals("")){
			redPacketRule=redPacketRuleService.read(Integer.parseInt(itemType));
		}
		List<RedPacketDetail> rf=new ArrayList<RedPacketDetail>() ;
			if(MyStringUtils.isNotAnyBlank(details)){				
				if(details.substring(details.length()-1).equals(";")){				
					details = details.substring(0, details.length()-1);														
				}
				rf=toDetailFromStr(details,redPacketRule,itemType,moneyType1,createTime,expiredTime,usageDetail,investMoney1,investRate1,redpacketType,timeType);	
			}
			try{
				redPacketDetailService.saveDetail(rf);
				operaRecordService.insertRecord("手动发放加息券", getLoginUser.getUserId(), "发放数量: "+rf.size()+" 活动类型: "+itemType+" 开始时间: "+createTime+" 到期时间: "+expiredTime+" 应用类型: "+usageDetail+" 用户手机号: "+details);
				response.getWriter().print("ok");			
			}catch(Exception e){
				e.printStackTrace();
				response.getWriter().print("error");
			}
		
	}
	private List<RedPacketDetail> toDetailFromStr(String details,RedPacketRule redPacketRule,String itemType,String moneyType1,String createTime,String expiredTime,String usageDetail,double investMoney,double investRate,String redpacketType,String timeType){		
		String[] risks = details.split(";");
		List<RedPacketDetail> list = new ArrayList<>();
		for(int i=0; i<risks.length; i++){
			String[] risk = risks[i].split("、");					
			RedPacketDetail rf = new RedPacketDetail();
			rf.setCreateTime(DateUtil.StringToDate(createTime));
			rf.setDeadline(DateUtil.StringToDate(expiredTime));
			if(!StringUtils.isNumeric(risk[0]) || risk[0].length() != 11){
				rf.setMobile(userService.read(risk[0]).getMobileNumber());			
				rf.setUserId(risk[0]);
			}else{
				rf.setMobile(risk[0]);
				rf.setUserId(userService.readUserByMobileNumber(risk[0]).getUserId());
			}		
			rf.setUsageDetail(usageDetail);
			rf.setInvestMoney(investMoney);
			rf.setInvestRate(investRate/100);
			rf.setType(moneyType1);
			rf.setTimeType(timeType);
			rf.setRedpacketType(redpacketType);
			if(usageDetail.equals("invest")){
				rf.setUsageRule("投资可用");
			}else{
				rf.setUsageRule("可申请提现");
			}
			if(moneyType1.equals("money")){
				rf.setMoney(Double.parseDouble(risk[1]));
				//rf.setName(redPacketRule.getName()+'-'+risk[1]);
				rf.setName(redPacketRule.getName());
				rf.setRate(0D);
			}else{
				rf.setRate(Double.parseDouble(risk[1])/100);
				rf.setName(redPacketRule.getName());
				//rf.setName(redPacketRule.getName()+'-'+risk[1]+"%");
				rf.setMoney(0D);
			}
			rf.setRuleId(redPacketRule.getId());
			rf.setSendStatus("unused");
			list.add(rf);
		}	
		return list;
	}
   
	@RequestMapping(value = "/redPacketDetail/del")
	public void del(HttpServletResponse response, HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			RedPacketDetail red = new RedPacketDetail();
			red.setId(Integer.parseInt(id));
			red.setAvailable(0);
			redPacketDetailService.del(red);
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
					"com.duanrong.newadmin.controller.RedPacketDetailController.del()",
					e);
		}
	}

	
	@RequestMapping("/redPacketDetail/list")
	public ModelAndView list(
			@RequestParam(value="pageNo",required=false)Integer pageNo,
			@RequestParam(value="pageSize",required=false)Integer pageSize,
			HttpServletRequest request,
			HttpServletResponse response, Model model){
		if(pageNo == null){
			pageNo = 0;
		}
		if(pageSize == null){
			pageSize = 30;
		}
		String userId = getUserId(request.getParameter("userId"));
		String name = request.getParameter("name");
		String moneyType = request.getParameter("moneyType");
		String investType = request.getParameter("investType");
		String status = request.getParameter("status");
		
		ModelAndView mv = new ModelAndView();	
		List<RedPacketRule> rules=redPacketRuleService.readAll();
		RedPacketDetail detail = new RedPacketDetail();
		detail.setUserId(userId);
		detail.setName(name);
		detail.setType(moneyType);
		detail.setUsageDetail(investType);
		detail.setSendStatus(status);
		PageInfo<RedPacketDetail> pageInfo = this.redPacketDetailService.readPageLite(pageNo, pageSize, (RedPacketDetail)FormUtil.getForParamToBean(detail) );
		String str="";
		if (userId != null && !userId.equals("")) {
			str += "&userId=" + userId;
		}
		String name1="";
		if (name != null && !name.equals("")) {
			
			try {
				name1 = java.net.URLDecoder.decode(name, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			str += "&name=" + name1;
		}
		
		if (moneyType != null && !moneyType.equals("")) {
			str += "&moneyType=" + moneyType;
		}
		if (investType != null && !investType.equals("")) {
			str += "&investType=" + investType;
		}
		if (status != null && !status.equals("")) {
			str += "&status=" + status;
		}
		mv.addObject("pageInfo",pageInfo);
		mv.addObject("rules",rules);
		mv.addObject("userId",userId);
		mv.addObject("name",name1);
		mv.addObject("moneyType",moneyType);
		mv.addObject("investType",investType);
		mv.addObject("status",status);
		request.setAttribute("str", str);
		mv.setViewName("/redPacketDetail/list");
		return mv;
	}
	
	
	/**
	 * 获取加息卷详细信息
	 * @return
	 */
	@RequestMapping(value="/redPacketDetail/getRedPacket")
	@ResponseBody
	public String getRedPacketDetailById(String redPacketId){
		String retStr = null;
		try {
			RedPacketDetail redPacketDetail = redPacketDetailService.read(Integer.parseInt(redPacketId));
			retStr = JSONObject.toJSONString(redPacketDetail);
		} catch (Exception e) {
		}
		return retStr;
	}
	
	/**
	 * 查询可以使用的加息卷
	 * @param redPacketId
	 * @return
	 */
	@RequestMapping(value="/redPacketDetail/toRedPacketUse/{investId}/{redpacketId}/{mobileNumber}")
	public String toRedPacketUse(@PathVariable("investId")String investId,@PathVariable("redpacketId")String redpacketId,
			@PathVariable("mobileNumber")String mobileNumber,Model m){
		//查询可使用加息卷
		List<RedPacketDetail> packetList = redPacketDetailService.readRedPacketUse(investId,mobileNumber,redpacketId);
		m.addAttribute("packetUseList", packetList);
		m.addAttribute("investId", investId);
		m.addAttribute("redpacketId", redpacketId);
		return "invest/redPacketUse";
	}
	
	/**
	 * 更改加息券使用
	 * @param packetId	packeId为null代表取消加息券使用操作
	 * @param investId
	 * @return
	 */
	@RequestMapping("/redPacketDetail/updatePacketUse")
	@ResponseBody
	public String updatePacketUse(String packetId,String investId,HttpServletRequest request,HttpServletResponse response){
		 UserCookie userCookie = UserCookieHelper.GetUserCookie(request, response);
		if( "".equals(packetId) || "null".equals(packetId )){
			packetId = null;
		}
		String retMsg = null;
		try {
			int ret = redPacketDetailService.updatePacketUse(packetId,investId,userCookie.getUserId());
			if( ret >= 1 ){
				retMsg = "success";
			}
		} catch (Exception e) {
			retMsg = "fail";
		}
		return retMsg;
	}
	
	/**
	 * 	校验用户的的同一项目其他投资是否已经使用加息券，
	 * 	如果以使用则同一项目其他投资不可使用加息券，返回false，否则返回true
	 * @param investId
	 * @return
	 */
	@RequestMapping(value="/redPacketDetail/checkInvestNum")
	@ResponseBody
	public String checkInvestNum(String investId){
		String msg = null;
		try{
			int  ret = redPacketDetailService.readCheckInvestNum(investId);
			if( ret > 0 )
				return "false";
			else
				return "true";
		}catch(Exception e){
			return "false";
		}
	}
	/**
	 * 添加纸质优惠券
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="redPacketDetail/addRedpacketCoupon")
	public String addRedpacketCoupon(HttpServletResponse response,
			HttpServletRequest request){
		try {	
		int num=Integer.parseInt(request.getParameter("num"));
		String type=request.getParameter("type");
		String money=request.getParameter("money");
		String remark=request.getParameter("remark");
		UserCookie loginUser = GetLoginUser(request, response);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		String createTime=df.format(new Date());
		String pid=IdGenerator.randomUUID();
		Map<String, Object>map=new HashMap<>();
		map.put("id",pid);
		map.put("createId",loginUser.getUserId());
		map.put("createTime",createTime); 
		map.put("flag",0);
		map.put("remark",remark);
		map.put("num",num);
		int flag=redPacketDetailService.addRedpacketCouponRecord(map);
		for (int i = 0; i < num; i++) {
			Map<String,Object> params=new HashMap<>();
			params.put("createId", loginUser.getUserId());
			params.put("id", IdGenerator.randomUUID());
			params.put("createTime", createTime);
			params.put("useStatus", 0);
			params.put("releaseStatus",0);
			params.put("type", type);
			params.put("money",money);
			if(money.equals("200")||money.equals("600")){
				params.put("ruleId",31);
			params.put("endTime",DateUtil.addYear(createTime, 1) );
			}else {
				params.put("ruleId",264);
				params.put("endTime",DateUtil.addMonth(createTime, 6) );
			}
			params.put("redPacketCode", AES.encode(IdUtil.generateString(12))); 
			params.put("pid",pid);
			int status=redPacketDetailService.addRedpacketCoupon(params);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	/** 
	 * 查询（一条记录）
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="redPacketDetail/redpacketCouponListByPid")
	public String redpacketCouponListByPid(HttpServletResponse response,
			HttpServletRequest request,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "10";
		if (pageNo == null) {
			pageNo = "1";
		}
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		String usedStatus=request.getParameter("usedStatus");
		String releaseStatus=request.getParameter("releaseStatus");    
		String pid=request.getParameter("pid");
		String flag=request.getParameter("flag");
		String str="";
		Map<String,Object>params=new HashMap<String, Object>();
		if (start != null && !start.equals("")) {
			str += "&start=" + start;
			params.put("start",start );
		}
		if (pid != null && !pid.equals("")) {
			str += "&pid=" + pid;
			params.put("pid",pid );
		}
		if (end != null && !end.equals("")) {
			str += "&end=" + end;
			params.put("end",end+" 23:59" );
		}
		if (usedStatus != null && !usedStatus.equals("")) {
			str += "&usedStatus=" + usedStatus;
			params.put("usedStatus",usedStatus );
		}
		if (releaseStatus != null && !releaseStatus.equals("")) {
			str += "&releaseStatus=" + releaseStatus;
			params.put("releaseStatus",releaseStatus );
		}
		PageInfo<ActivateCoupon> pageInfo =redPacketDetailService.readRedpacketCouponList(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("usedStatus",usedStatus);
		model.addAttribute("start",start );
		model.addAttribute("end",end );
		model.addAttribute("releaseStatus",releaseStatus );
		model.addAttribute("pid",pid);
		model.addAttribute("flag",flag);
		return "redPacketDetail/redpacketCouponListByPid";
	}
	
	/**
	 * 查询所有批量记录
	 * @return
	 */
	@RequestMapping(value="redPacketDetail/redpacketCouponList")
	public String redpacketCouponList(HttpServletResponse response,
			HttpServletRequest request,Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "10";
		if (pageNo == null) {
			pageNo = "1";
		}
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		String flag=request.getParameter("flag");    
		String pid=request.getParameter("pid");
		
		String str="";
		Map<String,Object>params=new HashMap<String, Object>();
		if (start != null && !start.equals("")) {
			str += "&start=" + start;
			params.put("start",start );
		}
		if (pid != null && !pid.equals("")) {
			str += "&id=" + pid;
			params.put("pid",pid );
		}
		if (end != null && !end.equals("")) {
			str += "&end=" + end;
			params.put("end",end+" 23:59" );
		}
		if (flag != null && !flag.equals("")) {
			str += "&flag=" + flag;
			params.put("flag",flag);
		}
		PageInfo<ActivateCouponRecord> pageInfo =redPacketDetailService.readRedpacketCouponRecordList(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("start",start );  
		model.addAttribute("end",end );
		model.addAttribute("flag",flag);
		model.addAttribute("pid",pid);
		return "redPacketDetail/redpacketCouponList";
	}
	
	@RequestMapping(value="redPacketDetail/toAddredpacketCoupon")
	public String toAddredpacketCoupon(){
		return "redPacketDetail/createRedpacketCouponList";
	}
	
	
	@RequestMapping(value="redPacketDetail/exportRedpacketCoupon")
	public void exportRedpacketCoupon(HttpServletResponse response,
			HttpServletRequest request){				
		String pid=request.getParameter("pid");
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("pid",pid);
		List<ActivateCoupon> list=redPacketDetailService.readExportRedpacketCouponList(params);
		try {
		for (ActivateCoupon activateCoupon : list) {
			if(activateCoupon.getType().equals("1")){
				activateCoupon.setType("现金券");
			}else{
				activateCoupon.setType("加息券");
			}
			activateCoupon.setRedPacketCode(AES.decode(activateCoupon.getRedPacketCode()));
		}
		//将导出后的记录 状态设置为已发行
		int flag=redPacketDetailService.updateRedpacketCouponStatusByPid(pid);
		int status=redPacketDetailService.updateRedpacketCouponFlagByPid(pid);
		Map<String, String> title = new LinkedHashMap<>();
		title.put("pid", "批量编号");
		title.put("id", "编号");
		title.put("redPacketCode", "优惠券码");
		title.put("createTime","创建时间");
		title.put("type", "优惠券类型");
		title.put("value", "金额");
		title.put("endTime", "有效期截止时间");
		int[] style = {26,20,20,30,10,22,16};
		String fileName = DateUtil.DateToString(new Date(),
				"yyyyMMddHHmmss");
		
			request.setCharacterEncoding("UTF-8");
		
		response.setCharacterEncoding("UTF-8");
		fileName = "纸质优惠券" + fileName + ".xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ fileName);
		ExcelConvertor excelConvertor = new ExcelConvertor(
				response.getOutputStream(), fileName);
		String t="纸质优惠券";
		excelConvertor.excelExport(list,title,t,style);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据pid 查询 flag 改记录是否导出
	 */
	@ResponseBody
	@RequestMapping(value="redPacketDetail/checkRedpacketCouponPid")
	public String checkflagByPid(HttpServletRequest request,
			HttpServletResponse response){
		String pid=request.getParameter("pid");
		int flag=redPacketDetailService.readFlagByPid(pid);
		if (flag==1) {
			//已导出 不允许再次导出
		return "error";
		} else {
		return "success";
		}
		
	}
	/**
	 * 查询某个账户下所有的红包
	 */
	@RequestMapping(value="account/userRedpacket")
	public String userRedpacket(HttpServletRequest request,
			HttpServletResponse response, Model model){
	
		String params=request.getParameter("id").trim();
		
		String mob="";
		if(MyStringUtils.isNumeric(params) && params.length() == 11){
			mob=params;
		}else {
			User user = userService.read(params);
			mob=user.getMobileNumber();
		}
		Map<String,Object> map=new HashMap<>();
		map.put("mob", mob);
		List<RedPacketDetail> pageInfo = redPacketDetailService.readUserRedpacketList(map);
		model.addAttribute("id",params);
		model.addAttribute("RedpacketList",pageInfo);
		return "found/accountList";
	}
} 

