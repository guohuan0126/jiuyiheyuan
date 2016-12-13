package com.duanrong.cps.business.xicaiwang.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.Log;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.cps.business.aiyouqian.model.AiyouqianPushLoan;
import com.duanrong.cps.business.invest.dao.InvestDao;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.dao.LoanDao;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.netloaneye.model.PushLoan;
import com.duanrong.cps.business.user.dao.UserDao;
import com.duanrong.cps.business.user.model.User;
import com.duanrong.cps.business.xicaiwang.model.XiCaiInvestInfo;
import com.duanrong.cps.business.platform.dao.PlatformDao;
import com.duanrong.cps.business.platform.dao.PlatformPushDao;
import com.duanrong.cps.business.platform.model.PushInvest;
import com.duanrong.cps.business.xicaiwang.model.XiCaiLoanInfo;
import com.duanrong.cps.business.xicaiwang.model.XiCaiUserInfo;
import com.duanrong.cps.business.xicaiwang.service.XicaiwangService;
import com.duanrong.cps.util.ArithUtil;
import com.duanrong.cps.util.HttpUtil;
import com.duanrong.cps.util.ReadProperties;
import com.duanrong.cps.util.xicaiwang.DesUtil;

@Service(value="xicaiwangService")
public class XicaiwangServiceImpl implements XicaiwangService{
	
	@Resource
	LoanDao loanDao;
	
	@Resource
	InvestDao investDao;
	
	@Resource
	UserDao userDao;
	@Autowired
	private PlatformDao platformDao;
	@Autowired
	private PlatformPushDao platformPushDao;
	@Autowired
	private Log log;

	@Override
	public XiCaiLoanInfo getLoanInfo(String loanId) {
		Loan loan = loanDao.getLoanInfo(loanId);
		XiCaiLoanInfo xiCaiLoanInfo = new XiCaiLoanInfo();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
		if(null != loan){
			xiCaiLoanInfo.setP2p_product_id(loan.getId());
			xiCaiLoanInfo.setProduct_name(loan.getName());
			if(loan.getNewbieEnjoy().equals("是")){
				xiCaiLoanInfo.setIsexp(1);
			}else{
				xiCaiLoanInfo.setIsexp(0);
			}
			if(loan.getOperationType().equals("天")){
				xiCaiLoanInfo.setLife_cycle(loan.getDay()     );
			}else{
				xiCaiLoanInfo.setLife_cycle((loan.getDeadline()*30));
			}
			xiCaiLoanInfo.setEv_rate((int)(loan.getRate()*100));
			xiCaiLoanInfo.setAmount(loan.getTotalmoney().intValue());
			xiCaiLoanInfo.setInvest_amount(investDao.getAmountMoney(loanId));
			xiCaiLoanInfo.setInverst_mans((int) investDao.getTotalCount(loanId));
			xiCaiLoanInfo.setUnderlying_start(df.format(loan.getExpectTime()));
			xiCaiLoanInfo.setInvestSource("xicaiwang");
			if(loan.getStatus().equals("已完成")){
				xiCaiLoanInfo.setProduct_state(4);
			}else if(loan.getStatus().equals("流标")){
				xiCaiLoanInfo.setProduct_state(-1);
			}else if(loan.getStatus().equals("筹款中")){
				xiCaiLoanInfo.setProduct_state(0);
			}else if(loan.getStatus().equals("贷前公告")){
				xiCaiLoanInfo.setProduct_state(3);
			}else if(loan.getStatus().equals("还款中")){
				xiCaiLoanInfo.setProduct_state(2);
			}
			xiCaiLoanInfo.setBorrower(loan.getBorrowerName());
			xiCaiLoanInfo.setGuarant_mode(2);
			xiCaiLoanInfo.setPublish_time(df.format(loan.getExpectTime()));
			if(null == loan.getGiveMoneyOperationTime()){
				xiCaiLoanInfo.setRepay_start_time(null);
			}else{
				xiCaiLoanInfo.setRepay_start_time(df.format(loan.getGiveMoneyOperationTime()));
			}
			if(null == loan.getFinishTime()){
				xiCaiLoanInfo.setRepay_end_time(null);
			}else{
				xiCaiLoanInfo.setRepay_end_time(df.format(loan.getFinishTime()));
			}
			
			xiCaiLoanInfo.setBorrow_type(1);
			if(loan.getRepayType().equals("一次性到期还本付息")){
				xiCaiLoanInfo.setPay_type(4);
			}else if(loan.getRepayType().equals("按月付息到期还本")){
				xiCaiLoanInfo.setPay_type(1);
			}else if(loan.getRepayType().equals("等额本息")){
				xiCaiLoanInfo.setPay_type(3);
			}
			xiCaiLoanInfo.setStart_price(1);
			String url=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "pc_url");
			String mobUrl=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "mweb_url");
			xiCaiLoanInfo.setLink_website(url+"loanDetail/"+loan.getId()+"?investSource=xicaiwang");
			xiCaiLoanInfo.setMobile_link_website(mobUrl+"loanDetail/"+loan.getId()+"?investSource=xicaiwang");
		}
		return xiCaiLoanInfo;
	}

	@Override
	public List<XiCaiUserInfo> getUserInfo(String startdate, String enddate,
			String page, String pagesize) {
		int start = (Integer.valueOf(page)-1)*(Integer.valueOf(pagesize));
		List<User> resultList = userDao.getUserInfo(startdate,enddate,start,pagesize);
		List<XiCaiUserInfo> resultList1 = new ArrayList<XiCaiUserInfo>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		if(resultList.size()>0){
			for (User user : resultList) {
				XiCaiUserInfo xiCaiUserInfo = new XiCaiUserInfo();
				Double totalMoney = investDao.getTotalMoney4XiCai(user.getUserId());
				xiCaiUserInfo.setId(user.getUserId());
				xiCaiUserInfo.setUsername(user.getUsername());
				String userSource = userDao.getUserSource(user.getUserId());
				if(userSource.contains("pc")){
					xiCaiUserInfo.setDisplay("pc");
				}else{
					xiCaiUserInfo.setDisplay("mobile");
				}
				xiCaiUserInfo.setPhone(user.getMobileNumber());
				xiCaiUserInfo.setTotalmoney(totalMoney);
				xiCaiUserInfo.setQq(user.getQq());
				xiCaiUserInfo.setEmail(user.getEmail());
				xiCaiUserInfo.setRegtime(df.format(user.getRegisterTime()));
				resultList1.add(xiCaiUserInfo);
			}
		}
		return resultList1;
	}

	@Override
	public List<XiCaiInvestInfo> getInvestInfo(String startdate, String enddate,
			String page, String pagesize) {
		int start = (Integer.valueOf(page)-1)*(Integer.valueOf(pagesize));
		List<Invest> selectList = investDao.getInvestInfo(startdate,enddate,start,pagesize);
		List<XiCaiInvestInfo> resultList = new ArrayList<XiCaiInvestInfo>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
//		DecimalFormat df2 = new DecimalFormat("######0.00");
		
		
		if(selectList.size()>0){
			for (Invest invest : selectList) {
				XiCaiInvestInfo xiCaiInvestInfo = new XiCaiInvestInfo();
				xiCaiInvestInfo.setId(invest.getId());
				xiCaiInvestInfo.setPid(invest.getLoanId());
				xiCaiInvestInfo.setUsername(invest.getUserId());
				if(invest.getUserSource().contains("pc")){
					xiCaiInvestInfo.setDisplay("pc");
				}else{
					xiCaiInvestInfo.setDisplay("mobile");
				}
				
				xiCaiInvestInfo.setDatetime(df.format(invest.getTime()));
				xiCaiInvestInfo.setMoney(invest.getMoney());
				if(invest.getLoan().getOperationType().equals("天")){
					xiCaiInvestInfo.setLife_cycle(invest.getLoan().getDay());
					double f = (invest.getMoney()*invest.getLoan().getDay()*0.02)/365; 
					BigDecimal b = new BigDecimal(f); 
					double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
					xiCaiInvestInfo.setCommision(f1);
				}else{
					double f = (invest.getMoney()*invest.getLoan().getDeadline()*0.02)/12; 
					BigDecimal b = new BigDecimal(f); 
					double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
					xiCaiInvestInfo.setLife_cycle(invest.getLoan().getDeadline()*30);
					xiCaiInvestInfo.setCommision(f1);
				}
				resultList.add(xiCaiInvestInfo);
			}
		}
		return resultList;
	}

	/**
	 * 推送投资记录
	 */
	@Override
	public void pushMethod(Map<String, Object> params) throws Exception {
		System.out.println("推送希财网投资记录");
		String investId=params.get("id")+"";
		params.put("id", investId);
		params.put("platformType", "xicaiwang");
		List<Invest>investList=platformDao.queryInvest(params);
		String whetherNew=investList.get(0).getWhetherNew();
		if(!"1".equals(whetherNew)){   //回调只推送新用户，老用户不推送
				this.pushInvest(investList.get(0));
			}
	}

	
	
	
	/**
	 * 
	 * 推送投资记录
	 */
	public void pushInvest (Invest invest) throws Exception{
		Map<String,Object>params=new HashMap<String,Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		params.put("type", "xicaiwang");
		params.put("investId", invest.getId());
		List<PushInvest>pushInvestList=platformPushDao.getPushInvestorsInfo(params);
		Map<String,Object>paramMap=new HashMap<String,Object>();
		if(pushInvestList.size()<=0){   //判断是否推送过，如果推送过则不再推送
			paramMap.put("id", invest.getId());
			paramMap.put("phone", invest.getUserMobileNumber()); //用户手机
			paramMap.put("datetime", sdf.format(invest.getTime())); //为投资时间
			paramMap.put("pid", invest.getLoanId()); //为投资的p2p网贷平台产品id
			String url=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "pc_url");
			paramMap.put("url", url+"loanDetail/"+invest.getLoanId()+"?investSource=xicaiwang"); //为投资的p2p网贷平台产品地址
		    paramMap.put("InvestAmount", invest.getMoney()); //用户投资金额
			paramMap.put("Earnings", invest.getInterest()); //用户投资收益（若无请填0）
			String term =""; // 投资期限（以天为单位）
			Double Commision=0.00;
			if("天".equals(invest.getLoanOperationType())){
				term=invest.getLoanDay();  //投资期限 按天计算
				Commision=((invest.getMoney())*Integer.parseInt(invest.getLoanDay())*0.02)/365;
			}else{
				term=(invest.getDeadline()*30)+""; //投资期限 按天计算
				Commision=((invest.getMoney())*(invest.getDeadline())*0.02)/12;
			}
			Commision=ArithUtil.round(Commision,2);
			if(Commision<=0.00){
				paramMap.put("Commision", 0); //为p2p网贷平台返佣给希财网的金额,不能接收0.0
			}else{
				paramMap.put("Commision", Commision); //为p2p网贷平台返佣给希财网的金额
			}
			
			paramMap.put("term", term);
			paramMap.put("rate", invest.getRate()*100);//投资利率
			String display="pc";   //为用户来源，默认值为pc（pc为电脑访问，mobile为手机访问）
			if(invest.getUserSource()!=null){  
				if(invest.getUserSource().contains("mobile")){
					display="mobile";
				}
			}
			paramMap.put("display", display);
			paramMap.put("userid", invest.getThirdPlatformUserId()); // 为用户在希财网注册时的编号，该编号在自动注册时会作为参数传过去 
			
			Map<String,Object>resultMap=this.xicaiwangInter(paramMap);
			
			if(!"0".equals(resultMap.get("code")+"")){   //推送投资记录失败
				System.out.println("###############希财网推送投资记录出错："+resultMap.get("result")+invest.getId());
			    log.errLog("希财网推送投资记录", "希财网推送投资记录出错："+resultMap.get("result")+invest.getId());
			}else{  //推送成功刚插入数据库
				PushInvest pushInvestData=new PushInvest();
				pushInvestData.setId(UUID.randomUUID().toString().replace("-",""));
				pushInvestData.setInvestId(invest.getId());
				pushInvestData.setUserId(invest.getUserId());
				pushInvestData.setLoanId(invest.getLoanId());
				pushInvestData.setAmount(invest.getMoney());
				pushInvestData.setInvestTime(invest.getTime());
				pushInvestData.setSendTime(new Date());
				pushInvestData.setInterest(invest.getInterest());
				pushInvestData.setInvestChannel(invest.getUserSource());
				pushInvestData.setType("xicaiwang");
				pushInvestData.setCommission(Commision);
				platformPushDao.insertPushInvest(pushInvestData);
			}
		} 
	}
	
	
	/**
	 * 调用希财网财接口
	 * @throws Exception 
	 */
	public Map<String,Object>xicaiwangInter(Map<String,Object>params) throws Exception{
		Map<String,Object>resultMap=new HashMap<String,Object>();
		 String param="";
		 for(String key:params.keySet()){
			 param=param+key+"="+params.get(key)+"&";
		 }
		param=param.substring(0,param.length()-1);
		String key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "xicaiwang_key");
		String clientId=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "xicaiwang_client_id");
		String paramEncode=DesUtil.encrypt(param, key);
		String url=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "xicaiwang_url")+"/InvestCallBack";
		JSONObject json=new JSONObject();
		json.put("client_id", clientId);
		json.put("sign", paramEncode);
		JSONObject result=HttpUtil.platformExecutePost(json, url);
		String code=result.getJSONObject("content").getString("code");
		if("0".equals(code) || "1007".equals(code)){   //推送成功
			resultMap.put("code", "0");
			resultMap.put("result", "推送希财网投资记录成功");
		}else{
			resultMap.put("code", "1");
			resultMap.put("result", "推送希财网投资记录失败");
		}
		return resultMap;
	}
	
	
	/**
	 * 推送标的相关的信息
	 */
	@Override
	public void pushLoanAbout(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args){
		
		System.out.println(ArithUtil.round(0.0016666666666666668, 1));
		System.out.println(ArithUtil.round(1.0016666666666666668, 1));
		System.out.println(ArithUtil.round(1.1016666666666666668, 1));
		System.out.println(ArithUtil.round(2.2216666666666666668, 1));
	}

	@Override
	public void insertToPushLoan(Loan loan) {
			try {
					PushLoan pushLoan=new PushLoan();
					pushLoan.setId(UUID.randomUUID().toString().replace("-", ""));
					pushLoan.setLoanId(loan.getId());
					pushLoan.setPushUserid(loan.getAwardLink());
					pushLoan.setLoanMoney(loan.getTotalmoney());
					pushLoan.setAlreadyMoney(loan.getSumMoney());
					pushLoan.setPushTime(new Date());
					pushLoan.setPushUserid("");
					pushLoan.setStatus("筹款中");
					pushLoan.setType("xicaiwang");
					platformPushDao.insertPushLoan(pushLoan);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

	@Override
	public int getTotalUser(String startdate, String enddate, String page,
			String pagesize) {
		int start = (Integer.valueOf(page)-1)*(Integer.valueOf(pagesize));
		return userDao.getTotalUser(startdate,enddate,start,pagesize);
	}

	@Override
	public int getTotalInvest(String startdate, String enddate) {
		return investDao.getTotalInvest(startdate,enddate);
	}

}
