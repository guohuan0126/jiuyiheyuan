package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import util.Log;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.business.activity.model.RedPacketDetail;
import com.duanrong.business.activity.model.RedPacketRule;
import com.duanrong.business.activity.service.RedPacketDetailService;
import com.duanrong.business.activity.service.RedPacketRuleService;
import com.duanrong.business.award.model.AwardItem;
import com.duanrong.business.award.model.AwardItemUser;
import com.duanrong.business.award.model.ItemType;
import com.duanrong.business.award.service.AwardItemService;
import com.duanrong.business.award.service.AwardItemUserService;
import com.duanrong.business.flow.model.Flow;
import com.duanrong.business.flow.model.ItemFlow;
import com.duanrong.business.flow.service.FlowService;
import com.duanrong.business.flow.service.ItemFlowService;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.FormUtil;
import com.duanrong.util.DateUtil;

@Controller
public class AwardController extends BaseController {

	@Autowired
	private AwardItemService awardItemService;

	@Autowired
	private ItemFlowService itemFlowService;

	@Autowired
	private UserService userService;

	@Autowired
	private InvestService investService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private FlowService flowService;

	@Autowired
	private Log log;

	@Autowired
	private OperaRecordService operaRecordService;
	@Autowired
	private AwardItemUserService awardItemUserService;
	@Autowired
	private RedPacketRuleService redPacketRuleService;
	@Autowired
	private RedPacketDetailService redPacketDetailService;
	/**
	 * 
	 * @description 跳转创建奖励项目页面
	 * @author 孙铮
	 * @time 2015-2-5 下午1:47:11
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/award/toCreateAwardView", method = RequestMethod.GET)
	public String toCreateAwardView(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		} else {
			List<Flow> flows = flowService.readAllFlow(1, 0, new Flow())
					.getResults();
			request.setAttribute("flows", flows);
			return "award/createAward";
		}
	}

	/**
	 * 
	 * @description 保存奖励项目
	 * @author 孙铮
	 * @time 2015-2-5 下午1:47:46
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/award/createAward", method = RequestMethod.POST)
	public String createAward(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			response.setCharacterEncoding("UTF-8");
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String saveAwardItem = awardItemService.saveAwardItem(
						getLoginUser.getUserId(), request);
				response.getWriter().write(saveAwardItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("保存奖励项目异常", e);
		}
		return null;
	}

	/**
	 * 
	 * @description 创建5%和10%奖励项目
	 * @author 孙铮
	 * @time 2015-2-5 下午1:47:46
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/award/createAward5And10", method = RequestMethod.POST)
	public String createAward5And10(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			response.setCharacterEncoding("UTF-8");
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String saveAwardItem = awardItemService.saveAwardItem5And10(
						getLoginUser.getUserId(), request);
				response.getWriter().write(saveAwardItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("保存奖励项目异常", e);
		}
		return null;
	}

	/**
	 * 
	 * @description 奖励项目与借款有关系时,查询借款项目
	 * @author 孙铮
	 * @time 2015-2-5 下午1:48:06
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryLoan", method = RequestMethod.POST)
	@ResponseBody
	public String queryLoan(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		response.setCharacterEncoding("UTF-8");
		try {
			Object queryLoan = awardItemService.readLoan(request);
			JSONObject jsonObject = new JSONObject(queryLoan);
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("奖励查询借款项目异常", e);
		}
		return null;
	}

	/**
	 * 
	 * @description 查询用户信息
	 * @author 孙铮
	 * @time 2015-2-5 下午1:48:06
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryUser", method = RequestMethod.POST)
	public String queryUser(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			String queryUser = awardItemService.readUser(request);
			response.getWriter().write(queryUser);
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("奖励查询用户信息异常", e);
		}
		return null;
	}

	/**
	 * 奖励项目列表
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/award/awardList/{itemType}")
	public String queryAwardView(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String itemType) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		String pageNo = request.getParameter("pageNo");
		if (MyStringUtils.isAnyBlank(pageNo)) {
			pageNo = "1";
		}
		AwardItem awardItem = grupWhere(request);
		if (MyStringUtils.isNotAnyBlank(itemType)) {
			awardItem.setItemType(itemType);
		}
		awardItem = (AwardItem) FormUtil.getForParamToBean(awardItem);
		PageInfo<AwardItem> page = awardItemService.readPageLite(
				Integer.parseInt(pageNo), getPageSize(), awardItem,
				getLoginUser.getUserId());
		request.setAttribute("url", "/award/awardList/" + itemType);
		request.setAttribute("str", FormUtil.getForParam(awardItem));
		request.setAttribute("pageInfo", page);
		return "award/awardList";
	}

	/**
	 * 查询组合条件
	 * 
	 * @param request
	 * @return
	 */
	public AwardItem grupWhere(HttpServletRequest request) {

		AwardItem awardItem = new AwardItem();
		String type = request.getParameter("type");
		String seleWhere = request.getParameter("selewhere");
		if (MyStringUtils.isNotAnyBlank(type)
				&& MyStringUtils.isNotAnyBlank(seleWhere)) {
			if (type.equals("name")) {
				awardItem.setName(seleWhere);
			} else if (type.equals("description")) {
				awardItem.setDescription(seleWhere);
			} else if (type.equals("loanId")) {
				awardItem.setLoanId(seleWhere);
			} else if (type.equals("status")) {
				awardItem.setStatus(seleWhere);
			}
		}
		return awardItem;

	}

	/**
	 * 奖励项目明细
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/award/awardUserList/{awardItemId}")
	public String queryAwardUserView(HttpServletResponse response,
			HttpServletRequest request, @PathVariable int awardItemId ) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		} else {						
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			AwardItemUser awardItemUser = new AwardItemUser();
			awardItemUser.setAwardItemId(awardItemId);
			request.setAttribute("pageInfo", awardItemService
					.readPageLiteForAwardUser(Integer.parseInt(pageNo),
							getPageSize(), (AwardItemUser) FormUtil
									.getForParamToBean(awardItemUser)));
			request.setAttribute("str", FormUtil.getForParam(awardItemUser));
			request.setAttribute("url", "/award/awardUserList/"+awardItemId);
			return "award/awardUser";			
		}
	}

	/**
	 * 奖励项目流程
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "award/awardFlowList")
	public String queryAwardFlowView(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		} else {
			String awardItemId = request.getParameter("awardId");
			if (MyStringUtils.isNumeric(awardItemId)) {
				List<ItemFlow> itemFlows = itemFlowService.readItemFlowByItemID(
						Integer.parseInt(awardItemId), ItemType.AWARD);
				request.setAttribute("itemFlows", itemFlows);
				request.setAttribute("str", "&awardId=" + awardItemId);
				request.setAttribute("url", "/award/awardFlowList");
				return "award/awardFlow";
			}
			return "award/awardList";
		}
	}

	/**
	 * 操作项目流程
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/award/operAward", method = RequestMethod.POST)
	public void operAward(HttpServletResponse response,
			HttpServletRequest request) throws IOException {		
		response.setCharacterEncoding("UTF-8");
		String itemId = null;
		String nodeId = null;
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			String id = request.getParameter("id");
			itemId = request.getParameter("itemId");
			nodeId = request.getParameter("nodeId");
			String status = request.getParameter("status");
			ItemFlow itemFlow = new ItemFlow();
			if (MyStringUtils.isNumeric(id)) {
				itemFlow.setId(Integer.parseInt(id));
			}
			if (MyStringUtils.isNumeric(itemId)) {
				itemFlow.setItemId(Integer.parseInt(itemId));
			}
			if (MyStringUtils.isNumeric(nodeId)) {
				itemFlow.setNodeId(Integer.parseInt(nodeId));
			}
			itemFlow.setItemType(request.getParameter("itemType"));
			itemFlow.setMessage(request.getParameter("message"));
			itemFlow.setOperateTime(new Date());
			itemFlow.setOperateUser(getLoginUser.getUserId());
			itemFlow.setStatus(status);
			String sendAwardResult = null;
			boolean boo = false;
			if (Integer.parseInt(nodeId) == 45) {// 审批奖励
				boo = awardItemService
						.createSendVerifyCodeAndMaxAwardMoneyRestrict(Integer
								.parseInt(itemId));
				if (boo) {
					sendAwardResult = "操作成功";
					response.getWriter().print("操作成功");
					itemFlowService.oprate(itemFlow, getLoginUser.getUserId());
				}
			} else if (Integer.parseInt(nodeId) == 46) {// 发送奖励
				if ("unapproved".equals(status)) {// 否决发送直接退出
					response.getWriter().print("sendOff");
					return;
				}
				sendAwardResult = awardItemService.sendAward(itemId);
				log.infoLog("发送奖励项目id:" + itemId, sendAwardResult);
				if ("发送成功".equals(sendAwardResult)) {
					itemFlowService.oprate(itemFlow, getLoginUser.getUserId());
				}
				response.getWriter().print(sendAwardResult);
			}
			operaRecordService.insertRecord("操作奖励项目", getLoginUser.getUserId(),
					"操作id:" + nodeId + "奖励项目id:" + itemId + "操作状态:"
							+ sendAwardResult);
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("奖励操作流程异常nodeId:" + nodeId + "itemId:" + itemId, e);
			response.getWriter().print("奖励操作流程异常");
		}
	}

	/**
	 * 撤销项目
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/award/awardRevoke", method = RequestMethod.POST)
	public void awardRevoke(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String itemId = request.getParameter("itemId");
		if (MyStringUtils.isNumeric(itemId)) {
			awardItemService.deleteWeiXin(Integer.parseInt(itemId));
			if (awardItemService.deleteAwardItem(Integer.parseInt(itemId)))
				response.getWriter().print("ok");
		}

	}

	/**
	 * 
	 * @description 修改跟投状态和用户跟投池金额
	 * @date 2015-4-21
	 * @time 下午1:08:41
	 * @author SunZ
	 */
	@RequestMapping(value = "/award/followInvestAwardModify", method = RequestMethod.GET)
	public void followInvestAwardModify(HttpServletRequest request,
			HttpServletResponse response) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			if ("sz123456".equals(getLoginUser.getUserId()) || "test".equals(getLoginUser.getUserId())) {
				String userId = request.getParameter("userId");
				awardItemService.followInvestAwardModify(userId);
				response.getWriter().write("success");
			}else{
				response.getWriter().write("你没有这个权限");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description 修改用户投资是否可享跟投
	 * @date 2015-4-21
	 * @time 下午1:37:39
	 * @author SunZ
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/award/modifyFollowInvestMoney", method = RequestMethod.GET)
	public void modifyFollowInvestMoney(HttpServletRequest request,
			HttpServletResponse response) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			if ("sz123456".equals(getLoginUser.getUserId()) || "test".equals(getLoginUser.getUserId())) {
				String userId = request.getParameter("userId");
				awardItemService.modifyFollowInvestMoney(userId);
				response.getWriter().write("success");
			}else{
				response.getWriter().write("你没有这个权限");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/award/export")
	public void export(HttpServletResponse response,
			HttpServletRequest request) throws IOException {				
		Map<String, Object> map = new HashMap<>();
		map.put("start", request.getParameter("start"));
		String end=request.getParameter("end");
		if (StringUtils.isNotBlank(end)) {
				String endtime=end+"23:59:59";
				map.put("end", endtime);
			} 
		
		map = FormUtil.getForParamToBean(map);
		try{
				List<AwardItem> awardItems = awardItemService.readAwards(map);
				String t = "奖励信息";
				Map<String, String> title = new LinkedHashMap<>();								
				title.put("id", "编号");
				title.put("name", "名称");
				title.put("description", "描述");
				title.put("loanType", "项目类型");				
				title.put("itmeAddress", "项目地址");
				title.put("money", "金额");
				title.put("sendTime", "发送时间");
				int[] style = {30, 30,30, 20, 30, 20,20};								
				String fileName = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				fileName = "奖励信息"+fileName+".xls";	
				response.setContentType("application/vnd.ms-excel");     
		        response.setHeader("Content-disposition", "attachment;filename=" + fileName); 	
				ExcelConvertor excelConvertor = new ExcelConvertor(response.getOutputStream(), fileName);
				excelConvertor.excelExport(awardItems, title, t,style);				
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	/**
	 * 删除奖励用户
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/award/delAwardUser", method = RequestMethod.POST)
	public String delAwardUser(HttpServletResponse response,
			HttpServletRequest request){		
		response.setCharacterEncoding("UTF-8");
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			String id = request.getParameter("id");
			String money = request.getParameter("money");
			String awardItemId= request.getParameter("awardItemId");
			if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(awardItemId)&& StringUtils.isNotBlank(money)){
			int flag =awardItemUserService.deleteAwardItemUser(Integer.parseInt(id));
			 if(flag==1){
				 AwardItem awardItem=awardItemService.readAwardItem(Integer.parseInt(awardItemId));
				 HashMap<String,Object> params = new HashMap<>();
				 double moneyAmount=awardItem.getMoneyAmount()-Double.parseDouble(money)<=0?0:awardItem.getMoneyAmount()-Double.parseDouble(money);
				 int userCount=awardItem.getUserCount()-1<=0?0:awardItem.getUserCount()-1;
				 params.put("moneyAmount",moneyAmount);
				 params.put("userCount",userCount);
				 params.put("id",awardItemId);
				 awardItemService.updateAawarditemMoney(params);
				 log.infoLog("奖励用户条目:" + id, "删除成功"); 
				 operaRecordService.insertRecord("操作删除奖励用户", getLoginUser.getUserId(),
							"奖励用户条目:" + id + "奖励项目id:" + awardItemId + "操作状态:"
									+"操作成功");
				 return "suceess";
			 }			
			}
		} catch (Exception e) {
			e.printStackTrace();
		   return "error";
		}
		return "error";
	}
	/**
	 *修改奖励用户的金额
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/award/editAwardUser", method = RequestMethod.POST)
	public String editAwardUser(HttpServletResponse response,
			HttpServletRequest request){		
		response.setCharacterEncoding("UTF-8");
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			String id = request.getParameter("id");
			String money = request.getParameter("money");
			String awardItemId= request.getParameter("awardItemId");
			String awardMoney=request.getParameter("awardMoney");
			if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(awardItemId)&& StringUtils.isNotBlank(money)
					&& StringUtils.isNotBlank(awardMoney)){
			//修改用户的奖励金额	
			HashMap<String,Object> Userparams = new HashMap<>();
			Userparams.put("money",awardMoney);
			Userparams.put("id",id);			
			awardItemUserService.updateawarditemuserMoney(Userparams);
			AwardItem awardItem=awardItemService.readAwardItem(Integer.parseInt(awardItemId));
				 HashMap<String,Object> params = new HashMap<>();
				 double moneyAmount=awardItem.getMoneyAmount()+Double.parseDouble(awardMoney)-Double.parseDouble(money);
				 params.put("moneyAmount",moneyAmount);
				 params.put("id",awardItemId);
				 awardItemService.updateAawarditemMoney(params);
				 log.infoLog("奖励用户条目:" + id, "编辑成功"); 
				 operaRecordService.insertRecord("操作编辑奖励用户金额", getLoginUser.getUserId(),
							"奖励用户条目:" + id + "奖励项目id:" + awardItemId+ "原金额:"  + money + "修改成的新金额:"  + awardMoney + "操作状态:"
									+"操作成功");
				 return "suceess";			 			
			}
		} catch (Exception e) {
			e.printStackTrace();
		   return "error";
		}
		return "error";
	}

	/**
	 * vip专享券发送页面
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/award/toCreateVipAward", method = RequestMethod.GET)
	public String toCreateVipAward(HttpServletResponse response,
			HttpServletRequest request) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		} else {
			List<RedPacketRule> redPacketRules =redPacketRuleService.readAll();
			request.setAttribute("redPacketRules", redPacketRules);
			return "award/createVipAward";
		}
	}
	@RequestMapping(value = "/award/createVipAward", method = RequestMethod.POST)
	public void createVipAward(HttpServletResponse response,
			HttpServletRequest request) {	
		UserCookie getLoginUser = GetLoginUser(request, response); 		
		//手机号 
		String details = request.getParameter("details");			
			try{
				if(StringUtils.isNotBlank(details)){
				String[] risk = details.split(",");	
				List<RedPacketDetail> list = new ArrayList<RedPacketDetail>();
				for (String param : risk) {
					RedPacketDetail reddetail = new RedPacketDetail();
					if(!StringUtils.isNumeric(param) || param.length() != 11){
						reddetail.setMobile(userService.read(param).getMobileNumber());			
						reddetail.setUserId(param);
					}else{						
						reddetail.setMobile(param);
						reddetail.setUserId(userService.readUserByMobileNumber(param).getUserId());
					}
					reddetail.setRate(0.01);
                    reddetail.setSendStatus("unused");
                    reddetail.setName("VIP新客专享");
                    reddetail.setRuleId(23);
                    reddetail.setType("rate");
                    reddetail.setUsageDetail("invest");
                    reddetail.setUsageRule("投资可用");
                    reddetail.setInvestRate(100);
                    reddetail.setRedpacketType("1");
                    list.add(reddetail);
					 
				}
			    redPacketDetailService.insertBatch(list);
			    log.infoLog("发放vip专享券", "<br>活动类型: "+"VIP新客专享，"+"券类型加息券，"+"发放数量: "+risk.length+"，发放用户手机号: "+details, 1, "zhongkeke@duanrong.com,fuyanping@duanrong.com");			    
				operaRecordService.insertRecord("发放vip专享券", getLoginUser.getUserId(), "发放数量: "+risk.length+" 活动类型: "+"VIP新客专享"+" 用户手机号: "+details);
				}
				response.getWriter().print("ok");			
			}catch(Exception e){
				e.printStackTrace();
				try {
					response.getWriter().print("error");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
	}
}