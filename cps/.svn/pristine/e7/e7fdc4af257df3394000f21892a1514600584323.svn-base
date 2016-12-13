package com.duanrong.cps.business.touzhija.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.repay.model.Repay;
import com.duanrong.cps.business.touzhija.dao.TouZhiJiaDao;
import com.duanrong.cps.business.touzhija.model.Assets;
import com.duanrong.cps.business.touzhija.model.BidInfo;
import com.duanrong.cps.business.touzhija.model.CreateUserReq;
import com.duanrong.cps.business.touzhija.model.InvestInfo;
import com.duanrong.cps.business.touzhija.model.LoginReq;
import com.duanrong.cps.business.touzhija.model.Message;
import com.duanrong.cps.business.touzhija.model.PlatformException;
import com.duanrong.cps.business.touzhija.model.QueryReq;
import com.duanrong.cps.business.touzhija.model.Redirect;
import com.duanrong.cps.business.touzhija.model.RepayInfo;
import com.duanrong.cps.business.touzhija.model.ServiceData;
import com.duanrong.cps.business.touzhija.model.UserInfo;
import com.duanrong.cps.business.touzhija.service.TouZhiJiaException;
import com.duanrong.cps.business.touzhija.service.TouZhiJiaService;
import com.duanrong.cps.business.user.dao.UserDao;
import com.duanrong.cps.business.user.model.User;
import com.duanrong.cps.util.ReadProperties;
import com.duanrong.cps.util.touzhijia.CryptException;
import com.duanrong.cps.util.touzhijia.DateTypeAdapter;
import com.duanrong.cps.util.touzhijia.MessageCrypt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




/**
 * Created by architect of touzhijia on 2016/3/1.
 */
@Service
public class TouZhiJiaServiceImpl implements TouZhiJiaService {

	@Autowired
	private TouZhiJiaDao touZhiJiaDao;

	@Autowired
	private UserDao userDao;
	
	/**
	 * 查询用户信息
	 */
	@Override
	public List<UserInfo> queryUser(QueryReq req) throws TouZhiJiaException {
		System.out.println("#######投之家抓取用户信息queryUser:");
		Date startTime=req.getDateRange().getStartTime();
		Date endTime=req.getDateRange().getEndTime();
		String[] platformUserArray=req.getIndex().getVals();
		String platformUserIds=this.getInParams(platformUserArray);
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("platformUserIds", platformUserIds);
		params.put("type", "TouZhiJia");
		System.out.println("#######投之家抓取用户信息queryUser:查询参数为："+JSON.toJSONString(params));
		List<PlatformUserRelation> userList=touZhiJiaDao.queryPlatformInfo(params);
		List<UserInfo>userInfoList=new ArrayList<UserInfo>();
		for(PlatformUserRelation user: userList){
			UserInfo userInfo=new UserInfo();
			userInfo.setUsername(user.getPlatformUserId());  // 投之家用户名
			userInfo.setUsernamep(user.getUserId()); //平台用户名
			userInfo.setRegisterAt(user.getRegisterTime());  //平台注册时间
			userInfo.setBindAt(user.getCreateTime());  //绑定投之家时间
			userInfo.setBindType(user.getWhetherNew()); //0:表示投之家带来的新用户，1:表示平台已有用户"
			Map<String,Object>investParams=new HashMap<String,Object>();
			investParams.put("userId", user.getUserId()); 
			List<Invest>investList=touZhiJiaDao.queryInvest(investParams);
			Float awaitAmount =(float) 0.0;//待收金额, 待收的本金和利息
			if(investList.size()>0){
				Double principal=0.0;  //应收收本金
				Double interest=0.0; //应收利息
				Double principalPaid=0.0;  //已收本金
				Double interestPaid=0.0; //已收利息
				for(Invest invest:investList){
					if(!"流标".equals(invest.getStatus())){
						principal=principal+invest.getMoney();
					    interest=interest+invest.getInterest();
					    principalPaid=principalPaid+invest.getPaidMoney();
					    interestPaid=interestPaid+invest.getPaidInterest();
					}
				}
				Double money=principal+interest;  //应该收总金额
				Double paidMoney=principalPaid+interestPaid; //已收金额
				awaitAmount=Float.parseFloat((money-paidMoney)+"");
			}
			
			Map<String,Object>accountMap=touZhiJiaDao.queryUserEnableAccount(user.getUserId());
			Assets assets=new Assets();
			assets.setAwaitAmount(awaitAmount);  //待收金额, 待收的本金和利息
			assets.setBalanceAmount(Float.parseFloat(accountMap.get("enableMoney")+""));  // 账户余额
			assets.setTotalAmount((awaitAmount+Float.parseFloat(accountMap.get("enableMoney")+"")+Float.parseFloat(accountMap.get("frozen")+"")));  //资产总额
			userInfo.setAssets(assets);
			userInfoList.add(userInfo);
		}
		return userInfoList;
	}

	/**
	 * 查询产品信息
	 * @throws ParseException 
	 */
	@Override
	public List<BidInfo> queryBids(QueryReq req) throws TouZhiJiaException{
		System.out.println("#######投之家抓取查询产品信息queryBids:");
		Date startTime=req.getDateRange().getStartTime();
		Date endTime=req.getDateRange().getEndTime();
		String[] loanIds=req.getIndex().getVals();
		String paramloanIds=this.getInParams(loanIds);
		Map<String,Object> params=new HashMap<String,Object>();
		
		if(startTime!=null){
			params.put("startTime", startTime);
		}
		if(endTime!=null){
			params.put("endTime", endTime);
		}
		if(StringUtils.isNotBlank(paramloanIds)){
			params.put("loanIds", paramloanIds);
		}
		System.out.println("#######投之家抓取用户信息queryUser:查询参数为："+JSON.toJSONString(params));
		
		List<BidInfo>bidInfoList=new ArrayList<BidInfo>();
		if(params.size()>0){  //参数都为空不进行查询
			List<Loan>loanList=touZhiJiaDao.queryLoan(params);
			if(loanList.size()>0){
				for(Loan loan:loanList){
					BidInfo bidInfo=new BidInfo();
					bidInfo.setId(loan.getId());   //标的ID
					String pcUrl=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "pc_url");
					String loanUrl=pcUrl+"loanDetail/"+loan.getId();
					bidInfo.setUrl(loanUrl);  // 标的URL
					bidInfo.setTitle(loan.getName()); //标题
					bidInfo.setDesc(""); // 描述 我们没有
					bidInfo.setBorrower(loan.getBorrowMoneyUserID()); //借款人
					bidInfo.setBorrowAmount(Float.parseFloat(loan.getTotalmoney()+""));  //借款金额
					Double sumMoney=loan.getSumMoney();   //已投金额
					if(sumMoney==null){
						sumMoney=0.0;
					}
					Double remainAmount=loan.getTotalmoney()-sumMoney;
					bidInfo.setRemainAmount(Float.parseFloat(remainAmount+"")); //剩余金额  总金额-已投金额
					bidInfo.setMinInvestAmount(Float.parseFloat(loan.getInvestOriginMoney()+""));  //起投金额
					Integer day=loan.getDay();
					Integer dayLine=loan.getDeadline();
					String operationType=loan.getOperationType();
					if("月".equals(operationType)){
						bidInfo.setPeriod(dayLine+"m");  //借款期限, 1d, 1m，如果为活期该字段为0
					}else{
						bidInfo.setPeriod(day+"d");  //借款期限, 1d, 1m，如果为活期该字段为0
					}
					bidInfo.setOriginalRate(Float.parseFloat(loan.getRate()*100+""));  //原始年化利率，13.5表示13.5%"
					bidInfo.setRewardRate(Float.parseFloat("0"));  //奖励利率 我们没有
					String bidStatus=ReadProperties.getPropetiesValue("constant/touzhijiaStatus.properties", loan.getStatus());  //配置文件读取相应的投之家对应的值 
					if(StringUtils.isNotBlank(bidStatus)){
						bidInfo.setStatus(Integer.parseInt(bidStatus));  //标的状态
					}
					bidInfo.setRepayment(loan.getRepayType());  //还款方式
					
					String newBieEnjoy=loan.getNewbieEnjoy();
					if("否".equals(newBieEnjoy)){
						bidInfo.setType(0);   //标的类型
					}else{
						bidInfo.setType(101);   //标的类型
					}
					bidInfo.setProp(loan.getLoanType()); //标的性质,比如:车贷，房贷，信用贷、融资租赁、保理等等
					bidInfo.setCreateAt(loan.getCommitTime());//标的创建时间
					bidInfo.setPublishAt(loan.getCommitTime());//标的起投时间，如果有倒计时，这个时间会晚于标的创建时间
					
					String defaultDate="1900-01-01 00:00:00";
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					try {
						if(loan.getGiveMoneyOperationTime()==null){
							bidInfo.setCloseAt(sdf.parse(defaultDate));  //标的截止购买时间
						}else{
							bidInfo.setCloseAt(loan.getGiveMoneyOperationTime());  //标的截止购买时间
						}
						if(loan.getGiveMoneyOperationTime()==null){
							bidInfo.setFullAt(sdf.parse(defaultDate));  //标的满标时间
						}else{
							bidInfo.setFullAt(loan.getGiveMoneyOperationTime());  //标的满标时间
						}
   						String[] tags={};
						bidInfo.setTags(tags);
						Map<String,Object>repayParams=new HashMap<String,Object>();
						repayParams.put("loanId", loan.getId());
						List<Repay>repayList=touZhiJiaDao.queryRepayInfo(repayParams);
						if(repayList.size()>0){
							if(repayList.get(0).getRepayDay()==null){
								bidInfo.setRepayDate(sdf.parse(defaultDate));  //预计还款日期, 放款后才有还款日
							}else{
								bidInfo.setRepayDate(repayList.get(0).getRepayDay());  //预计还款日期, 放款后才有还款日
							}
						}else{
							bidInfo.setRepayDate(sdf.parse(defaultDate));  //预计还款日期, 放款后才有还款日
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					bidInfoList.add(bidInfo);
				}
			}
		}
		return bidInfoList;
	}

	/**
	 * 投资记录查询
	 */
	@Override
	public List<InvestInfo> queryInvests(QueryReq req) throws TouZhiJiaException {
		System.out.println("#######投之家抓取投资记录queryInvests:");
		Date startTime=req.getDateRange().getStartTime();
		Date endTime=req.getDateRange().getEndTime();
		String paramName=req.getIndex().getName();
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("userType", "TouZhiJia");
		if(startTime!=null){
			params.put("startTime", startTime);
		}
		if(endTime!=null){
			params.put("endTime", endTime);
		}
		if("id".equals(paramName)){  //按invest的id进行查询
			String[] idArray=req.getIndex().getVals();
			String ids=this.getInParams(idArray);
			if(StringUtils.isNotBlank(ids)){
				params.put("id", ids);
			}
		}
		if("bid".equals(paramName)){  //按loanId查询
			String[] loanArray=req.getIndex().getVals();
			String loans=this.getInParams(loanArray);
			if(StringUtils.isNotBlank(loans)){
				params.put("loanId", loans);
			}
		}
		if("username".equals(paramName)){ //按指定用户查询
			String[] usernameArray=req.getIndex().getVals();
			String platFormUsernames=this.getInParams(usernameArray);
			Map<String,Object>platFormParams=new HashMap<String,Object>();
			platFormParams.put("platformUserIds", platFormUsernames);
			platFormParams.put("type", "TouZhiJia");
			List<PlatformUserRelation>platformList=touZhiJiaDao.queryPlatformInfo(platFormParams);  //查询第三方平台对应userid
			String[] userIdArray=new String[platformList.size()];
			int i=0;
			for(PlatformUserRelation platform:platformList){
				userIdArray[i]=platform.getUserId();
				i=i+1;
			}
			String userIds=this.getInParams(userIdArray);
			if(StringUtils.isNotBlank(userIds)){
				params.put("userIds", userIds);
			}
		}
		System.out.println("#######投之家抓取用户信息queryUser:查询参数为："+JSON.toJSONString(params));
		List<InvestInfo>investInfoList=new ArrayList<InvestInfo>();
		if(params.size()>0){  //没有参数不进行查询
			List<Invest>investList=touZhiJiaDao.queryInvest(params);
			if(investList.size()>0){
				for(Invest invest:investList){
					InvestInfo investInfo=new InvestInfo();
					investInfo.setId(invest.getId());  //订单ID
					investInfo.setBid(invest.getLoanId()); //标的ID
					String pcUrl=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "pc_url");
					String loanUrl=pcUrl+"loanDetail/"+invest.getLoanId();
					investInfo.setBurl(loanUrl);  //标的url
					investInfo.setUsername(invest.getUserId());  //用户名
					investInfo.setAmount(Float.parseFloat(invest.getMoney()+"")); //投资金额
					investInfo.setActualAmount(Float.parseFloat(invest.getMoney()+""));  //实际投资金额
					investInfo.setIncome(Float.parseFloat(invest.getInterest()+"")); //预期投资收益
					investInfo.setInvestAt(invest.getTime());  //投资时间
					
					
					String userSource=invest.getUserSource();
					
					String regSource="";
					String investSource="";
					if(StringUtils.isNotBlank(userSource)){  //投资来源是投之家
						if(userSource.contains("TouZhiJia")){
							investSource="touzhijia";
						}
					}
					if("0".equals(invest.getWhetherNew())){  //注册来源是投之家
						regSource="touzhijia";
					}
					JSONObject tags=new JSONObject();
					tags.put("reg_source", regSource);
					tags.put("invest_source", investSource);
					if(StringUtils.isNotBlank(userSource)){
						if(userSource.contains("pc")){
//							String[] tags=new String[1];
//							tags[0]="pc";
							tags.put("0", "pc");
							investInfo.setTags(tags);
						}else if(userSource.contains("android")){
							tags.put("0", "wap");
							tags.put("1", "android");
//							String[] tags=new String[2];
//							tags[0]="wap";
//							tags[1]="android";
							investInfo.setTags(tags);
						}else if(userSource.contains("ios")){
							tags.put("0", "wap");
							tags.put("1", "ios");
//							String[] tags=new String[2];
//							tags[0]="wap";
//							tags[1]="ios";
							investInfo.setTags(tags);
						}else{
							tags.put("0", "pc");
							
//							String[] tags=new String[1];
//							tags[0]="pc";
							investInfo.setTags(tags);
						}
					}else{
						tags.put("0", "pc");
//						String[] tags=new String[1];
//						tags[0]="pc";
						investInfo.setTags(tags);
					}
					
					investInfoList.add(investInfo);
				}
			}
		}
		return investInfoList;
	}

	/**
	 *  回款记录查询
	 */
	@Override
	public List<RepayInfo> queryRepays(QueryReq req) throws TouZhiJiaException {
		System.out.println("#######投之家抓取回款记录查询queryRepays:");
		Date startTime=req.getDateRange().getStartTime();
		Date endTime=req.getDateRange().getEndTime();
		String paramName=req.getIndex().getName();
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("userType", "TouZhiJia");
		if(startTime!=null){
			params.put("startTime", startTime);
		}
		if(endTime!=null){
			params.put("endTime", endTime);
		}
		List<Invest>repayList=null;
		if("id".equals(paramName)){  //按invest的id进行查询
			String[] idArray=req.getIndex().getVals();
			String ids=this.getInParams(idArray);
			Map<String,Object>queryByIdMap=new HashMap<String,Object>();
			if(StringUtils.isNotBlank(ids)){
				queryByIdMap.put("id", ids);
				queryByIdMap.put("userType", "TouZhiJia");
			}
			
			System.out.println("#######投之家抓取用户信息queryUser:查询参数为："+JSON.toJSONString(queryByIdMap));
			if(queryByIdMap.size()>0){
				repayList=touZhiJiaDao.queryInvestRepay(queryByIdMap);
			}
		}
		if("bid".equals(paramName)){  //按loanId查询
			String[] loanArray=req.getIndex().getVals();
			String loans=this.getInParams(loanArray);
			params.put("loanId", loans);
			System.out.println("#######投之家抓取用户信息queryUser:查询参数为："+JSON.toJSONString(params));
			if(params.size()>0){  //什么参数都没有则不可以查询
				repayList=touZhiJiaDao.queryInvestRepay(params);
			}
		}
		if("username".equals(paramName)){  //按指定用户查询
			String[] usernameArray=req.getIndex().getVals();
			String platFormUsernames=this.getInParams(usernameArray);
			Map<String,Object>platFormParams=new HashMap<String,Object>();
			platFormParams.put("platformUserIds", platFormUsernames);
			platFormParams.put("type", "TouZhiJia");
			List<PlatformUserRelation>platformList=touZhiJiaDao.queryPlatformInfo(platFormParams);  //查询第三方平台对应userid
			String[] userIdArray=new String[platformList.size()];
			int i=0;
			for(PlatformUserRelation platform:platformList){
				userIdArray[i]=platform.getUserId();
				i=i+1;
			}
			String userIds=this.getInParams(userIdArray);
			if(StringUtils.isNotBlank((userIds))){
				params.put("userIds", userIds);
			}
			System.out.println("#######投之家抓取用户信息queryUser:查询参数为："+JSON.toJSONString(params));
			if(params.size()>0){
				repayList=touZhiJiaDao.queryInvestRepay(params);
			}
		}
		if(params.size()>0){
			repayList=touZhiJiaDao.queryInvestRepay(params);
		}	
		
		List<RepayInfo> repayInfoList=new ArrayList<RepayInfo>();
		if(repayList.size()>0){
			for(Invest invest:repayList){
				RepayInfo repayInfo=new RepayInfo();
				repayInfo.setId(invest.getId());  //订单ID 投资id
				repayInfo.setInvestId(invest.getId()); //投资ID	
				repayInfo.setBid(invest.getLoanId()); //标的ID
				repayInfo.setUsername(invest.getUserId());
				
				int periodAccount=this.getLoanPeriodAccount(invest.getLoanId(), repayList);
				if(periodAccount==0){  //没有还款，即项目没有放款就没有还款记录
					continue;
				}
				int period=invest.getPeriod();
				
				Double inerestEvery=invest.getInterest()/periodAccount;  //总利息除以期数，算出每一期的收益 
				
				repayInfo.setIncome(Float.parseFloat(inerestEvery+"")); //回款收益(不包含管理费)
				if(period==periodAccount){  //最后一期有本金
					repayInfo.setAmount(Float.parseFloat(invest.getMoney()+""));  //回款金额(本金)
				}else{
					repayInfo.setAmount(Float.parseFloat(0+""));  //回款金额(本金)
				}
				
				repayInfo.setRepayAt(invest.getRepayDay());  //回款时间
				repayInfo.setType(0);  //回款类型0普通回款，因标的正常到期收到回款        1 转让回款，因用户主动债权转让收到回款
				repayInfoList.add(repayInfo);
			}
		}

		return repayInfoList;
	}
	
	/**
	 *统计查出的查询到的所有投资记录里某个标的是分几期还款 
	 */
	public Integer getLoanPeriodAccount(String loanId,  List<Invest>investList){
		List<String>repayId=new ArrayList<String>();
		for(Invest invest:investList){
			if(loanId.equals(invest.getLoanId())){
				if(!repayId.contains(invest.getRepayId()) && invest.getRepayId()!=null && !"".equals(invest.getRepayId())){  	
					repayId.add(invest.getRepayId());
				}
			}
		}
		return repayId.size();
	}

	/**
	 * 把要查询的参数并成sql里in所需的格式
	 * @param params
	 * @return
	 */
	public String getInParams(String[] params){
		String paramResult="";
		for(String param: params){
			if(StringUtils.isNotBlank(param)){
				paramResult=paramResult+"'"+param+"'"+",";
			}
		}
		if(paramResult.lastIndexOf(",")!=-1){
			paramResult=paramResult.substring(0,paramResult.lastIndexOf(","));
		}
		return paramResult;
	}

	@Override
	public UserInfo createUser(CreateUserReq req) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Redirect bindUser(CreateUserReq req) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Redirect login(LoginReq req) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 查询和投之家相联的老用户没有实名信息的用户
	 */
	public List<User>getTouzhijiaUser(){
		List<User>userList=userDao.getTouzhijiaUser();  //查询到所有没有实名信息的关联的老用户
		for(int i=0; i<userList.size();i++){
			User user=userList.get(i);
			String userId=user.getUserId();
		}
		
		
//		GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss");
//		Gson gson = gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter()).create();
//		ServiceData resp = new ServiceData("bindUser", gson.toJsonTree(userInfo));
//		MessageCrypt messageCrypt=new MessageCrypt();
//		Message message=null;
//		try {
//			message=messageCrypt.encryptMsg(resp);  //加密数据
//		} catch (CryptException e) {
//			
//			e.printStackTrace();
//		}
//		
//		Map<String,Object>postParams=new HashMap<String,Object>();
//		postParams.put("data", message.getData());
//		postParams.put("nonce", message.getNonce());
//		postParams.put("signature", message.getSignature());
//		postParams.put("timestamp", message.getTimestamp());
//		String appId=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "touzhijia_appid");
//		postParams.put("appId", appId);
//		
//		
//		String url=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "touzhijia_url");
//		
//		postParams.put("url",url);
//		
//		return platformService.callBackThirdPlatfrom(postParams);   
		
		
		
		return null;
	}
}
