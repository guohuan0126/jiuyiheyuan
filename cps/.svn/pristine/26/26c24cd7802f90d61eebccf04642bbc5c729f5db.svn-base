package com.duanrong.cps.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.cps.business.bjs.model.BjsInterest;
import com.duanrong.cps.business.bjs.model.BjsInvest;
import com.duanrong.cps.business.bjs.model.BjsProductType;
import com.duanrong.cps.business.bjs.model.CapitalFlow;
import com.duanrong.cps.business.bjs.model.UserAccount;
import com.duanrong.cps.business.bjs.service.BjsService;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.repay.model.Repay;
import com.duanrong.cps.business.user.model.UserBill;

@Controller
@RequestMapping(value="BJSInter")
public class BjsController extends BaseController{
	
	@Autowired
	private BjsService bjsService;
	
	@RequestMapping(value="/getProduct")
	@ResponseBody
	public Map<String,Object> queryLoan(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject resultJson = new JSONObject();
		String bjsParams="";
		String status=""; //入库记录标记  0为失败， 1为成功
		String message="";
		String requestId="";
		try {
			bjsParams=request.getAttribute("paramData").toString();
			JSONObject bjsParamsJson=JSONObject.parseObject(bjsParams);
			Long startTimeMills=bjsParamsJson.getLong("startTimeMills")-(5 * 24 * 60 * 60 * 1000);
			Integer start=bjsParamsJson.getInteger("start");
			Integer limit=bjsParamsJson.getInteger("limit");
			requestId=bjsParamsJson.getString("requestId");
			Date date=new Date(startTimeMills);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			System.out.println("###############八金社传来的查询产品的时间参数向前五天startTimeMills："+sdf.format(date));
			List<Loan>loanList=bjsService.queryLoan(date,start,limit);
			int productAccount=bjsService.queryLoanAccount(new Date(bjsParamsJson.getLong("startTimeMills")));
			List<BjsProductType>bjsProductList=new ArrayList<BjsProductType>();
			for(int i=0; i<loanList.size() ; i++){
				Loan loan= loanList.get(i);
				BjsProductType bjsProductType=new BjsProductType();
				bjsProductType.setProductId(loan.getId());   //loanId
				String isNewProduct=loan.getNewbieEnjoy(); //是否为新手标
				if("是".equals(isNewProduct)){
					bjsProductType.setProductType(loan.getLoanType()+"新手标");//类型
				}else{
					bjsProductType.setProductType(loan.getLoanType());//类型
				}
				bjsProductType.setProductName(loan.getName()); //产品名称
				bjsProductType.setPeriod(loan.getDay()+loan.getOperationType());  //投资周期，如30天、6月；
				bjsProductType.setBtApr(loan.getRate().toString()+"%"); //年化收益，如:“8%
				bjsProductType.setAmount(loan.getTotalmoney());//融资金额
				bjsProductType.setPmType(loan.getRepayType());//还款方式/收益方式，如：月还息到期还本
				if(loan.getGiveMoneyTime()!=null){
					
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
					String dd=format.format(loan.getGiveMoneyTime());
					bjsProductType.setBearingDate(dd.replace("-", "/"));//起息日， 设置起息日期
				}else{
					bjsProductType.setBearingDate("没有放款的项目没有起息，数据有错误");//起息日， 设置起息日期
				}
				bjsProductType.setMinIa(loan.getInvestOriginMoney()); //起投金额
				bjsProductType.setGuaranteeType("如出现借款人未能按时还款的情形，1通过诉讼、拍卖、变卖等法律流程进行抵质押物处置变现；2投资人可授权短融网寻找资产管理公司、债权劣后基金等第三方收购该笔债权");
				
				double totalMoney = loan.getTotalmoney()==null?0:loan.getTotalmoney();
				double sumInvestMoney = loan.getSumMoney()==null?0:loan.getSumMoney();
				bjsProductType.setProgress(progress(totalMoney , sumInvestMoney));  //融资进度
				
				bjsProductType.setStatus(loan.getStatus());
				bjsProductList.add(bjsProductType);  
			}
			
			//JSONObject resultJson=new JSONObject();
			resultJson.put("code", "10000");
			resultJson.put("requestId", requestId);
			resultJson.put("errors", "");
			resultJson.put("totalNum", productAccount);
			resultJson.put("datas", bjsProductList);
			response.setCharacterEncoding("utf-8");
			//response.getWriter().write(resultJson.toJSONString());
			status="1";
			return resultJson;
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("code", "10001");
			resultJson.put("requestId", requestId);
			resultJson.put("errors", e.getMessage());
			response.setCharacterEncoding("utf-8");
			status="0";
			message=e.getMessage();
			return resultJson;
		}finally{
			
			Map<String,Object>logMap=new HashMap<String,Object>();   //向数据库插入日志信息
			logMap.put("request", bjsParams);
			logMap.put("response", resultJson.toJSONString());
			logMap.put("type", "queryLoan:获取产品信息接口");
			logMap.put("createTime", new Date());
			logMap.put("message",message);
			logMap.put("status",status);
			logMap.put("ip", request.getAttribute("ip"));
			bjsService.logInsertBjsRequestLog(logMap);
		}
		
	}

	 public static double progress(double totalMoney ,double sumInvestMoney){
		    double p = 0;
		    MathContext mc = new MathContext(3, RoundingMode.HALF_DOWN);
			BigDecimal b1 = new BigDecimal(totalMoney);
			BigDecimal b2 = new BigDecimal(sumInvestMoney);
			double rate = b2.divide(b1,mc).multiply(new BigDecimal(100),mc).doubleValue();
		    return rate;
	   }
	  
	 /**
	  * 查询八金社用户信息
	  * @param request
	  * @param response
	  * @return
	  */
	@RequestMapping(value="getUserInfo") 
	@ResponseBody
	public  JSONObject userInfo(HttpServletRequest request, HttpServletResponse response ){
		JSONObject resultJson = new JSONObject();
		String bjsParams="";
		String status=""; //入库记录标记  0为失败， 1为成功
		String message="";
		String requestId="";
		try {
			bjsParams=request.getAttribute("paramData").toString();
			JSONObject bjsParamsJson=JSONObject.parseObject(bjsParams);
			if(bjsParamsJson.get("bjsUserId")!=null){
				String bjsuserId=bjsParamsJson.get("bjsUserId").toString();
				requestId=bjsParamsJson.getString("requestId");
				
				Map<String,Object>platFormUserInfoMap=bjsService.getPlatFormUserRelation(bjsuserId); //与第三方关联表信息
				
				Map<String,Object>platFormUserInfo=(Map<String, Object>) platFormUserInfoMap.get(bjsuserId);
				System.out.println(platFormUserInfo.get("userID"));
				Map<String,Object>userInfo=bjsService.queryBjsUserInfo(platFormUserInfo.get("userID")+"");
				Double userTotalMoneyMap= bjsService.getUserTotalInterest(platFormUserInfo.get("userID")+""); //得到用户总收益 
				//Map<String,Object>userTotalMoneyMap=(Map<String, Object>) userTotalMoneyMap1.get(bjsuserId);
				Double enablMoney=Double.parseDouble(userInfo.get("enableMoney")+"");
				Double frozen=Double.parseDouble(userInfo.get("frozen")+"");
				UserAccount userAccount=new UserAccount();
				userAccount.setP2pUserId(platFormUserInfo.get("userID")+"");
				userAccount.setSumIncome(userTotalMoneyMap);
				userAccount.setTotalBalance((enablMoney+frozen));
				userAccount.setAvailableBalance(enablMoney);
				userAccount.setRemark("");
				if((platFormUserInfo.get("whether_new")+"").equals("0")){
					userAccount.setChannel("bjs");
				}else{
					userAccount.setChannel("null");
				}
				List<UserAccount>userAccountList=new ArrayList<UserAccount>();
				userAccountList.add(userAccount);
				resultJson.put("code", "10000");
				resultJson.put("requestId", requestId);
				resultJson.put("errors", "");
				resultJson.put("totalNum", 1);
				resultJson.put("datas", userAccountList);
				return resultJson;
			}else{
				resultJson.put("code", "10008");
				resultJson.put("requestId", requestId);
				resultJson.put("errors", "userId不可以为空");
				resultJson.put("totalNum", 0);
				resultJson.put("datas", "");
				status="0";
				message="BjsuserId为空";
				return resultJson;
			}
			
		} catch (Exception e) {
			resultJson.put("code", "10001");
			resultJson.put("requestId", requestId);
			resultJson.put("errors", e.getMessage());
			resultJson.put("totalNum", 0);
			resultJson.put("datas", "");
			status="0";
			message=e.getMessage();
			return resultJson;
		}finally{
			Map<String,Object>logMap=new HashMap<String,Object>();   //向数据库插入日志信息
			logMap.put("request", bjsParams);
			logMap.put("response", resultJson.toJSONString());
			logMap.put("type", "getUserInfo:用户账户查询接口");
			logMap.put("createTime", new Date());
			logMap.put("message",message);
			logMap.put("status",status);
			logMap.put("ip", request.getAttribute("ip"));
			bjsService.logInsertBjsRequestLog(logMap);
		}
	}
	
	/**
	 * 用户交易流水查询接口
	 * @param args
	 */
	@RequestMapping(value="queryCapitalFlow")
	@ResponseBody
	public JSONObject queryCapitalFlow(HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		String bjsParams="";
		String status=""; //入库记录标记  0为失败， 1为成功
		String message="";
		String requestId="";
		try {
			bjsParams=request.getAttribute("paramData").toString();
			JSONObject bjsParamsJson=JSONObject.parseObject(bjsParams);
			if(bjsParamsJson.get("bjsUserId")!=null){
				String bjsUserId=bjsParamsJson.get("bjsUserId").toString();
				Long startTimeMills=bjsParamsJson.getLong("startTimeMills")-(5 * 24 * 60 * 60 * 1000);
				Integer start=bjsParamsJson.getInteger("start");
				Integer limit=bjsParamsJson.getInteger("limit");
				requestId=bjsParamsJson.getString("requestId");
				Date date=new Date(startTimeMills);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				System.out.println("###############八金社传来的查询用户交易流水查询接口的时间参数向前五天startTimeMills："+sdf.format(date));
				
				Map<String,Object>platFormUserInfoMap=bjsService.getPlatFormUserRelation(bjsUserId);
				Map<String,Object>platFormUserInfo=(Map<String, Object>) platFormUserInfoMap.get(bjsUserId);
				String userId=platFormUserInfo.get("userID").toString();  //我们平台的userID值
				System.out.println(platFormUserInfo.get("userID"));
				int captitalFlogAccount=bjsService.queryCapitalFlowAccount(userId, date);  //查询指定时间个数的帐户交易流水记录个数 
				List<UserBill>CapitalFlogList=bjsService.queryCapitalFlow(date, start, limit,userId);   // 查询指定时间个数的帐户交易流水记录
				Map<String,Object>userInfo=bjsService.queryBjsUserInfo(userId);  //查询用户帐户信息 获取可用余额
				
				List<CapitalFlow> capitalFlowList=new ArrayList<CapitalFlow>();
				for(UserBill userBill: CapitalFlogList){
					CapitalFlow capitalflow=new CapitalFlow();
					capitalflow.setP2pBusinessId(userBill.getId());
					capitalflow.setBusinessName(userBill.getTypeInfo());
					capitalflow.setBusinessType(userBill.getType());
					capitalflow.setMoney(userBill.getMoney());
					String timeStr=userBill.getTime();
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date timeDate=df.parse(timeStr);
					capitalflow.setTime(timeDate.getTime()+"");
					capitalflow.setAvailableBalance(Double.parseDouble(userInfo.get("enableMoney").toString()));  //用户可用余额
					capitalflow.setDetailed("");
					if((platFormUserInfo.get("whether_new")+"").equals("0")){
						capitalflow.setChannel("bjs");
					}else{
						capitalflow.setChannel("null");
					}
					capitalFlowList.add(capitalflow);
				}
				
				resultJson.put("code", "10000");
				resultJson.put("requestId", requestId);
				resultJson.put("errors","");
				resultJson.put("totalNum", captitalFlogAccount);
				resultJson.put("datas", capitalFlowList);
				return resultJson;
			}else{
				resultJson.put("code", "10008");
				resultJson.put("requestId", requestId);
				resultJson.put("errors", "userId不可以为空");
				resultJson.put("totalNum", 0);
				resultJson.put("datas", "");
				status="0";
				message="BjsuserId为空";
				return resultJson;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("code", "10001");
			resultJson.put("requestId", requestId);
			resultJson.put("errors", e.getMessage());
			resultJson.put("totalNum", 0);
			resultJson.put("datas", "");
			status="0";
			message=e.getMessage();
			return resultJson;
		}finally{
			Map<String,Object>logMap=new HashMap<String,Object>();   //向数据库插入日志信息
			logMap.put("request", bjsParams);
			logMap.put("response", resultJson.toJSONString());
			logMap.put("type", "queryCapitalFlow:用户交易流水查询接口");
			logMap.put("createTime", new Date());
			logMap.put("message",message);
			logMap.put("status",status);
			logMap.put("ip", request.getAttribute("ip"));
			bjsService.logInsertBjsRequestLog(logMap);
		}
	}
	
	/**
	 * 投资查询接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="queryInvest")
	@ResponseBody
	public JSONObject queryInvest(HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		String bjsParams="";
		String status=""; //入库记录标记  0为失败， 1为成功
		String message="";
		String requestId="";
		try {
			bjsParams=request.getAttribute("paramData").toString();
			JSONObject bjsParamsJson=JSONObject.parseObject(bjsParams);
			if(bjsParamsJson.get("bjsUserId")!=null){
				String bjsUserId=bjsParamsJson.get("bjsUserId").toString();
				Long startTimeMills=bjsParamsJson.getLong("startTimeMills")-(5 * 24 * 60 * 60 * 1000);
				Integer start=bjsParamsJson.getInteger("start");
				Integer limit=bjsParamsJson.getInteger("limit");
				requestId=bjsParamsJson.getString("requestId");
				Date date=new Date(startTimeMills);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				System.out.println("###############八金社传来的投资查询接口的时间参数向前五天startTimeMills："+sdf.format(date));
				
				Map<String,Object>platFormUserInfoMap=bjsService.getPlatFormUserRelation(bjsUserId);
				Map<String,Object>platFormUserInfo=(Map<String, Object>) platFormUserInfoMap.get(bjsUserId);
				String userId=platFormUserInfo.get("userID").toString();  //我们平台的userID值
				System.out.println(platFormUserInfo.get("userID"));
				
				int investAccount=bjsService.queryInvestAccount(userId, date);  //用户投资总记录数
				List<Invest> investList=bjsService.queryInvestByUserId(date, start, limit, userId);
				List<BjsInvest>bjsInvestList=new ArrayList<BjsInvest>();
				for(Invest invest:investList){
					BjsInvest bjsInvest=new BjsInvest();
					bjsInvest.setP2pInvestmentId(invest.getId());
					bjsInvest.setEncourageRate(0.0);
					bjsInvest.setManageRate(0.0);
					bjsInvest.setProductId(invest.getLoanId());
					String day=invest.getLoanDay();
					String operationType=invest.getLoanOperationType();
					bjsInvest.setPeriod(day+operationType);
					bjsInvest.setBtApr((invest.getRate()*100)+"%");
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
					String giveMoneyTime=invest.getLoanGiveMoneyTime();
					if(StringUtils.isNotBlank(giveMoneyTime)){
						Date dd=format.parse(invest.getLoanGiveMoneyTime());
						String ddd=format.format(dd);
						bjsInvest.setBearingDate(ddd.replace("-","/"));
					}else{
						bjsInvest.setBearingDate(""); //起息日放款后才有
					}	
					
					bjsInvest.setMoney(invest.getMoney());
					bjsInvest.setEstimateIncome(invest.getInterest());
					bjsInvest.setProductName(invest.getLoanName());
					bjsInvest.setState(invest.getStatus());
					Date investTime=invest.getTime();
					long investTimeStemp=investTime.getTime();
					bjsInvest.setPurchases(investTimeStemp+""); //购买时间，精确到毫秒级的时间戳,
					String userSource=invest.getUserSource();
					//System.out.println("#########userSource:"+userSource);
					if(StringUtils.isNotEmpty(userSource)){
						if(userSource.contains("BJS")){
							bjsInvest.setChannel("bjs");
						}else{
							bjsInvest.setChannel("null");
						}
					}else{
						bjsInvest.setChannel("null");
					}
					
					bjsInvestList.add(bjsInvest);
				}
				resultJson.put("code", "10000");
				resultJson.put("requestId",requestId);
				resultJson.put("errors", "");
				resultJson.put("totalNum", investAccount);
				resultJson.put("datas", bjsInvestList);
				return resultJson;
			}else{
				resultJson.put("code", "10008");
				resultJson.put("requestId", requestId);
				resultJson.put("errors", "userId不可以为空");
				resultJson.put("totalNum", 0);
				resultJson.put("datas", "");
				status="0";
				message="BjsuserId为空";
				return resultJson;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("code", "10001");
			resultJson.put("requestId", requestId);
			resultJson.put("errors", e.getMessage());
			resultJson.put("totalNum", 0);
			resultJson.put("datas", "");
			status="0";
			message=e.getMessage();
			return resultJson;
		}finally{
			Map<String,Object>logMap=new HashMap<String,Object>();   //向数据库插入日志信息
			logMap.put("request", bjsParams);
			logMap.put("response", resultJson.toJSONString());
			logMap.put("type", "queryInvest:投资查询接口");
			logMap.put("createTime", new Date());
			logMap.put("message",message);
			logMap.put("status",status);
			logMap.put("ip", request.getAttribute("ip"));
			bjsService.logInsertBjsRequestLog(logMap);
		}
	}
	/**
	 * 收益相关查询接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/queryInterest")
	@ResponseBody
	public JSONObject queryInterest(HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		String bjsParams="";
		String status=""; //入库记录标记  0为失败， 1为成功
		String message="";
		String requestId="";
		try {
			bjsParams=request.getAttribute("paramData").toString();
			JSONObject bjsParamsJson=JSONObject.parseObject(bjsParams);
			if(bjsParamsJson.get("bjsUserId")!=null){
				String bjsUserId=bjsParamsJson.get("bjsUserId").toString();
				Long startTimeMills=bjsParamsJson.getLong("startTimeMills")-(5 * 24 * 60 * 60 * 1000);
				Integer start=bjsParamsJson.getInteger("start");
				Integer limit=bjsParamsJson.getInteger("limit");
				requestId=bjsParamsJson.getString("requestId");
				Date date=new Date(startTimeMills);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				System.out.println("###############八金社传来的收益相关查询接口的时间参数向前五天startTimeMills："+sdf.format(date));
				
				Map<String,Object>platFormUserInfoMap=bjsService.getPlatFormUserRelation(bjsUserId);
				Map<String,Object>platFormUserInfo=(Map<String, Object>) platFormUserInfoMap.get(bjsUserId);
				String userId=platFormUserInfo.get("userID").toString();  //我们平台的userID值
				System.out.println(platFormUserInfo.get("userID"));
				
				int interestAccount=bjsService.queryInterestAccount(userId, date);
				List<Invest>interestList=bjsService.queryInterest(date, start, limit, userId);  //获取用户的投资记录
				List<BjsInterest>bjsInterestList=new ArrayList<BjsInterest>();
				for(Invest invest:interestList){
					String loanId=invest.getLoanId();
					List<Repay>repayList=bjsService.queryRepay(loanId);  //查询还款表
					for(int i=0; i<repayList.size();i++){
						BjsInterest bjsInterest=new BjsInterest();
						//bjsInterest.setP2pRewardId(UUID.randomUUID().toString().replace("-", ""));  //收益的主键，我们没有，这里生成uuid
						bjsInterest.setP2pRewardId(repayList.get(i).getId());  //收益的主键，我们没有，这里用repay表的id代替
						bjsInterest.setP2pRewardName("投资利息收益");  //收益的名称，描述性信息
						bjsInterest.setDescription("投资利息收益");  //收益的描述，描述性信息
						bjsInterest.setP2pInvestmentId(invest.getId());  //与投资相关的收益投资ID
						Date repayDate=repayList.get(i).getRepayDay();
						String repayTime=repayDate.getTime()+"";
						bjsInterest.setCalendar(repayTime); //还款款时间
						Double inerestEvery=invest.getInterest()/repayList.size();  //总利息除以期数，算出每一期的收益 
						if(i==0){
							inerestEvery=inerestEvery+invest.getInvestAllowanceInterest(); //第一期收益要额外加上公司补给的利息
							bjsInterest.setIncome(inerestEvery); //还款利息
						}else{
							bjsInterest.setIncome(inerestEvery);
						}
						if(i==(repayList.size()-1)){   //待收本金，只有最后一个月有待收本金
							bjsInterest.setPrincipal(invest.getMoney()); //还款本金,已还的本金
						}else{
							bjsInterest.setPrincipal(0.0); //不是最后一期，待收本金都是0
						}
						
						if(repayDate.getTime()<new Date().getTime()){  //如果还款日期小于当前日期，则证明已经还款
							bjsInterest.setIncomeState("已还款"); //还款状态 如：待收 、已收、逾期等，
						}else{
							bjsInterest.setIncomeState("待还款");
						}
						bjsInterestList.add(bjsInterest);
					}
				}
				resultJson.put("code", "10000");
				resultJson.put("requestId",requestId);
				resultJson.put("errors", "");
				resultJson.put("totalNum", interestAccount);
				resultJson.put("datas",bjsInterestList);
				
				return resultJson;
			}else{
				resultJson.put("code", "10008");
				resultJson.put("requestId", requestId);
				resultJson.put("errors", "userId不可以为空");
				resultJson.put("totalNum", 0);
				resultJson.put("datas", "");
				status="0";
				message="BjsuserId为空";
				return resultJson;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("code", "10001");
			resultJson.put("requestId", requestId);
			resultJson.put("errors", e.getMessage());
			resultJson.put("totalNum", 0);
			resultJson.put("datas", "");
			status="0";
			message=e.getMessage();
			return resultJson;
		}finally{
			Map<String,Object>logMap=new HashMap<String,Object>();   //向数据库插入日志信息
			logMap.put("request", bjsParams);
			logMap.put("response", resultJson.toJSONString());
			logMap.put("type", "queryInterest:收益相关查询接口");
			logMap.put("createTime", new Date());
			logMap.put("message",message);
			logMap.put("status",status);
			logMap.put("ip", request.getAttribute("ip"));
			bjsService.logInsertBjsRequestLog(logMap);
		}
	}
	
	public static void main(String[] args){
		Date date = new Date();
		Long time = date.getTime()-(5 * 24 * 60 * 60 * 1000);
		System.out.println(time);
		
		Date d=new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			System.out.println(sdf.format(sdf.parse("2015/01/02 22:11:22.0")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
