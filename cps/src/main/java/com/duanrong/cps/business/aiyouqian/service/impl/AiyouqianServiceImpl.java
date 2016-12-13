package com.duanrong.cps.business.aiyouqian.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.Log;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.cps.business.aiyouqian.model.AiyouqianPushInvest;
import com.duanrong.cps.business.aiyouqian.model.AiyouqianPushLoan;
import com.duanrong.cps.business.aiyouqian.service.AiyouqianService;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.netloaneye.model.PushLoan;
import com.duanrong.cps.business.platform.dao.PlatformDao;
import com.duanrong.cps.business.platform.dao.PlatformPushDao;
import com.duanrong.cps.business.platform.model.PushInvest;
import com.duanrong.cps.business.platform.model.PushUserAccount;
import com.duanrong.cps.util.HttpUtil;
import com.duanrong.cps.util.MD5Encry;
import com.duanrong.cps.util.ReadProperties;
import com.duanrong.util.ArithUtil;


@Service("aiyouqianService")
public class AiyouqianServiceImpl implements AiyouqianService {

	@Autowired
	private PlatformPushDao platformPushDao;
    @Autowired
    private PlatformDao platformDao;     
    
    
    
    
	
	private String chedaifengkong=" 风险提示:本产品存在到期后借款人无力偿还本息、抵质押物贬值、抵质押物处置周期过长、资产收购不成功而导致的无法及时还款风险。 "
			+ " 风控措施:质押车辆存入短融网专有车库进行监管，并由专人维护。 "
			+ "逾期处理:如出现借款人未能按时还款的情形，1通过诉讼、拍卖、变卖等法律流程进行抵质押物处置变现；2投资人可授权短融网寻找资产管理公司、债权劣后基金等第三方收购该笔债权。";
	private String fangdaifengkong="风险提示:本产品存在到期后借款人无力偿还本息、抵质押物贬值、抵质押物处置周期过长、资产收购不成功而导致的无法及时还款风险。 "
			+ " 风控措施:1由专业房产评估人员对房产价值进行评估，合理控制抵押率；2办理房产抵押登记及借款公证；"
			+ "逾期处理:如出现借款人未能按时还款的情形，1通过诉讼、拍卖、变卖等法律流程进行抵质押物处置变现；2投资人可授权短融网寻找资产管理公司、债权劣后基金等第三方收购该笔债权。";
    private String qiyefengkong="风险提示:本产品存在到期后借款人无力偿还本息、抵质押物贬值、抵质押物处置周期过长、资产收购不成功而导致的无法及时还款风险。 "
			+ " 风控措施:1.实际控制人及另一家商贸企业提供连带保证责任担保；2.存货质押监管"
			+ "逾期处理:若借款人未按合同约定及时还本付息，且担保人未代偿的，在一定的期限内，经投资人授权短融网可寻找包括但不限于金融机构、资产管理公司、投资公司等第三方收购该笔债权，同时第三方向借款人及担保人清收、处置抵押物。";
    private String otherfengkong="风险提示:本产品存在到期后借款人无力偿还本息、抵质押物贬值、抵质押物处置周期过长、资产收购不成功而导致的无法及时还款风险。 ";
    
    private String url="";
	private String aiyouqian_partnerId="";
	private String aiyouqian_key="";
	private String version="";
	
	@Autowired
	private Log log;
	
	/**
     * 向爱有钱推送标的
     */
	@Override
	public Map<String, Object> InsertPushLoanToAiyouqian(String id, String userid) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			List<String> idList = Arrays.asList(id.split(","));
			Map<String,Object>params=new HashMap<String,Object>();
			String Ids="";
			for(String loanId:idList){
				Ids=Ids+"'"+loanId+"'"+",";
			}
			params.put("Ids", Ids.substring(0,Ids.lastIndexOf(",")));
			List<Loan> loanList = platformPushDao.getLoanByLoanId(params);  //得到要推送的产口信息
			if(loanList==null||loanList.size()<=0){
				return returnMap;
			}
			List<AiyouqianPushLoan> pushLoanList = new ArrayList<AiyouqianPushLoan>();
			for(int i=0;i<loanList.size();i++){
 				AiyouqianPushLoan aiyouqianPushLoan=new AiyouqianPushLoan();
				String loanName=loanList.get(i).getName();
				String loanNameCode="";
//				for(int j=0; j<loanName.length(); j++){
//					char namechar=loanName.charAt(j);
//					boolean flog=this.isChinese(namechar);
//					if(flog){
//						String	code=this.stringToUnicode(loanName.substring(j,1));
//						loanNameCode=loanNameCode+code;
//					}else{
//						loanNameCode=loanNameCode+namechar;
//					}
//					
//				}
				aiyouqianPushLoan.setProductname(loanName);  //理财产品名称
				String loanType=loanList.get(i).getLoanType();  //资产类型
				if("房贷".equals(loanType)){  
					aiyouqianPushLoan.setCategroy_id(2);
					aiyouqianPushLoan.setDescription(fangdaifengkong);  //项目描述
				}else if("车贷".equals(loanType)){
					aiyouqianPushLoan.setCategroy_id(1);
					aiyouqianPushLoan.setDescription(chedaifengkong);  //项目描述
				}else if("企业贷".equals(loanType)){
					aiyouqianPushLoan.setCategroy_id(3);
					aiyouqianPushLoan.setDescription(qiyefengkong);  //项目描述
				}else{
					aiyouqianPushLoan.setCategroy_id(8);
					aiyouqianPushLoan.setDescription(otherfengkong);  //项目描述
				}
				aiyouqianPushLoan.setAmount(loanList.get(i).getTotalmoney());  //金额
                if(null==loanList.get(i).getSumMoney()){
                	aiyouqianPushLoan.setSaleamount(0.0);  //已销售金额
                }else{
                	aiyouqianPushLoan.setSaleamount(loanList.get(i).getSumMoney());  //已销售金额
                }
				
				aiyouqianPushLoan.setAnnual(Float.parseFloat(loanList.get(i).getRate()+"")*100); //年化收益率
				String loanOperationType=loanList.get(i).getOperationType();
				if("天".equals(loanOperationType)){
					aiyouqianPushLoan.setTerm(loanList.get(i).getDay());  //期限
					aiyouqianPushLoan.setUnit("d");//单位（m表示月；d表示天）
				}else{
					aiyouqianPushLoan.setTerm(loanList.get(i).getDeadline());  //期限
					aiyouqianPushLoan.setUnit("m");//单位（m表示月；d表示天）
				}
				if("一次性到期还本付息".equals(loanList.get(i).getRepayType())){
					aiyouqianPushLoan.setRepay_method(3); // 投资还息还本方式
				}else if("按月付息到期还本".equals(loanList.get(i).getRepayType())){
					aiyouqianPushLoan.setRepay_method(1); // 投资还息还本方式
				}else if("等额本息".equals(loanList.get(i).getRepayType())){
					aiyouqianPushLoan.setRepay_method(2); // 投资还息还本方式
				}
				aiyouqianPushLoan.setSale_startdate( sdf.format(loanList.get(i).getCommitTime())); //销售开始日期,格式yyyyMMddhhmmss
				aiyouqianPushLoan.setStatus_id(1);  //状态值 
				if("否".equals(loanList.get(i).getNewbieEnjoy())){
					aiyouqianPushLoan.setIsnew(0);   //是否为新手标（1表示新手标）默认0,每次最多推送一个
				}else{
					aiyouqianPushLoan.setIsnew(1);   //是否为新手标（1表示新手标）默认0,每次最多推送一个
				}
				aiyouqianPushLoan.setProductid(loanList.get(i).getId());   //平台端标的唯一标识
				aiyouqianPushLoan.setAssurance(2); //保障方式
				pushLoanList.add(aiyouqianPushLoan);
			}
			returnMap.put("result",pushLoanList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	
	
	

	 /**
	  * 把推送的项目插入数据库
	 */
	@Override
	public Integer insertToPushLoan(List<AiyouqianPushLoan> params,String userId) {
		try {
			for(int i=0; i<params.size();i++){
				PushLoan pushLoan=new PushLoan();
				pushLoan.setId(UUID.randomUUID().toString().replace("-", ""));
				pushLoan.setLoanId(params.get(i).getProductid());
				pushLoan.setLoanMoney(params.get(i).getAmount());
				pushLoan.setAlreadyMoney(params.get(i).getSaleamount());
				pushLoan.setPushTime(new Date());
				pushLoan.setPushUserid(userId);
				pushLoan.setStatus("筹款中");
				pushLoan.setType("aiyouqian");
				platformPushDao.insertPushLoan(pushLoan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	//java 中文编码函数:
			public static final boolean isChinese(char c) {   
				    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
				    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
				            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
				            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
				            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
				            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
				            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
				        return true;  
				    }  
				    return false;  
				}


			public static String stringToUnicode(String s){
					String st = "";
					for(int i = 0; i < s.length(); i++){
			            if(isChinese(s.charAt(i)))
			            	st = st+"\\u"+Integer.toHexString(s.codePointAt(i)).toLowerCase();
			            else
			            	st += s.charAt(i);
					}
					return st;
				}
	
	
	
	
	 public static <T> T buildHandler(Class<T> clazz) throws InstantiationException, Exception{
		 Field[] field=clazz.getDeclaredFields();
		 Loan loan=new Loan();
		 loan.setId("123456");
		 loan.setName("test");
		 List<Loan>loanList=new ArrayList();
		 loanList.add(loan);
		 List<T> platforList=new ArrayList<T>();
		 for(Loan ll: loanList){
			 Class loanClass=ll.getClass();
			 Field[] loanNames=loanClass.getDeclaredFields();
			 for(int i=0; i<loanNames.length; i++){
				 Field ff=loanNames[i];
				 String platformName= ReadProperties.getPropetiesValue("constant/aiyouqianPushLoan.properties", ff.getName());
				 if(platformName!=null){
					 Method[] methods=clazz.getClass().getDeclaredMethods();
					 T aa= clazz.newInstance();
					 Method m=clazz.getDeclaredMethod("set"+platformName,String.class);
					 m.setAccessible(true);
					 m.invoke(aa, platformName);
					 platforList.add(aa);
				 }
				
			 }
		 }
		return (T) platforList;
		 
	 }
	 
	 public static void main(String[]args) throws InstantiationException, Exception{
		 AiyouqianServiceImpl.buildHandler(AiyouqianPushLoan.class);
	 }



/**
 * 推送所有的记录
 */
	@Override
	public void pushMethod(Map<String, Object> params) throws Exception{
		System.out.println("###################爱有钱推送");
		String investId=params.get("id")+"";
		params.put("platformType", "aiyouqian");
		params.put("id", investId);
		List<Invest>investList=platformDao.queryInvest(params);
		if(investList.size()>0){
			this.pushInvest(investList.get(0));
			this.pushUserInvest(investList.get(0));
		}
	}
	
	@Override
	public void pushLoanAbout(Map<String,Object>params)throws Exception{
		System.out.println("###########爱有钱推送所有的记录1"+params.toString());
		String investId=params.get("id")+"";
		params.put("platformType", "aiyouqian");
		params.put("id", investId);
		System.out.println("###########爱有钱推送所有的记录2");
		this.pushLoanStatus(investId);
		System.out.println("###########爱有钱推送所有的记录3");
		this.pushLoanSaleMoney(investId);
		
	}
	
    /**
     * 用户资产接口（平台推送爱有钱）
     */
	public void pushUserInvest(Invest invest){
		JSONObject data=this.getAccInfo(invest.getUserId());
		data.put("bind_username", invest.getUserId());
		Map<String,Object>result=this.aiyouqianInter(data, "user_account_company");   //调用爱有钱接口
		String code=result.get("code")+"";
		if(!"200".equals(code)){
			System.out.println("###############爱有推送 用户资产接口（平台推送爱有钱）："+result.get("result")+invest.getId()+"userId:"+invest.getUserId());
		    log.errLog("爱有钱推送投资记录", "###############爱有推送 用户资产接口（平台推送爱有钱）：："+result.get("result")+invest.getId()+"userId:"+invest.getUserId());
		}else{
			//插入数据库
			PushUserAccount pushUserAccount=new PushUserAccount();
			pushUserAccount.setAmount(data.getDouble("amount"));  //总资产
			pushUserAccount.setBalance(data.getDouble("balance"));
			pushUserAccount.setCreateTime(new Date());
			pushUserAccount.setInvestId(invest.getId());
			pushUserAccount.setTradeAmount(data.getDouble("tradeAmount"));
			pushUserAccount.setType("aiyouqian");
			pushUserAccount.setUserId(data.getString("bind_username"));
			pushUserAccount.setWaitInterest(data.getDouble("waitInterest"));
			platformPushDao.insertPushUserAccount(pushUserAccount);
		}
		
	}
	/**
	 * 调用爱有钱接口
	 * 
	 * 	 */
	public Map<String,Object> aiyouqianInter(JSONObject dataJson,String service){
		url=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_url");
		aiyouqian_partnerId=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_partnerId");
		aiyouqian_key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","aiyouqian_key");
		version=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties","version");
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		 String nowTimeStr=sdf.format(new Date());
		 String str="_input_charset=utf-8&"
		 		+ "data="+dataJson+"&"
		 		+ "partner_id="+aiyouqian_partnerId+"&"
		 		+"request_time="+nowTimeStr+"&"
		 		+ "service="+service+"&"
		 		+ "version="+version
		 		+ aiyouqian_key;
		 System.out.println("爱有钱sign明文："+str);
		 String sign=MD5Encry.MD5Low(str);
		 JSONObject jsonObject=new JSONObject();
		 jsonObject.put("version", version);
		 jsonObject.put("partner_id", aiyouqian_partnerId);
		 jsonObject.put("_input_charset", "utf-8");
		 jsonObject.put("request_time", nowTimeStr);
		 jsonObject.put("sign_type", "MD5");
		 jsonObject.put("service", service);
		 jsonObject.put("sign", sign);
		 jsonObject.put("data", dataJson);
		System.out.println("爱有钱参数："+jsonObject);
		JSONObject resultObject = HttpUtil.platformExecutePost(jsonObject, url);
		String code=resultObject.getJSONObject("content").getString("response_code");
		String message=ReadProperties.getPropetiesValue("constant/aiyouqianStatus.properties",code);
		String result="";
		if("200".equals(code)){
			result="推送投资记录成功";
		}else{
			result="推送投资记录失败!"+message+resultObject.getJSONObject("content").getString("response_message");
		}
		System.out.println(resultObject);
		Map<String, Object>resultMap=new HashMap<String,Object>();
		resultMap.put("result", result);
		resultMap.put("code", code);
		return resultMap;
	}
	
	/**
	 * 
	 * 推送投资记录
	 */
	public void pushInvest (Invest invest) throws Exception{
		System.out.println("###################爱有钱推送投资记录");
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("type", "aiyouqian");
		params.put("investId", invest.getId());
		List<PushInvest>pushInvestList=platformPushDao.getPushInvestorsInfo(params);
		if(pushInvestList.size()<=0){   //判断是否推送过，如果推送过则不再推送
			AiyouqianPushInvest aiyouqianPushInvest=new AiyouqianPushInvest();
			aiyouqianPushInvest.setOut_trade_no(invest.getId());  //交易单号
			aiyouqianPushInvest.setBind_username(invest.getUserId());  //平台端用户唯一标识
			aiyouqianPushInvest.setAmount(invest.getMoney()); //金额
			aiyouqianPushInvest.setStatus(0); //状态值
			String userSource=invest.getUserSource();  //渠道
			if("aiyouqian".equals(userSource)){
				aiyouqianPushInvest.setInvestchannels(1); // 缘心平台
			}else{
				aiyouqianPushInvest.setInvestchannels(0); // 其他平台
			}
			aiyouqianPushInvest.setProductid(invest.getLoanId());  //平台端标的唯一标识
			aiyouqianPushInvest.setProductname(invest.getLoanName());  //理财产品名称
			aiyouqianPushInvest.setInterest(invest.getInterest()); //本地投资的预计收益
			
			aiyouqianPushInvest.setAnnual(invest.getRate()*100);  //年化收益率
			if("天".equals(invest.getLoanOperationType())){
				aiyouqianPushInvest.setTerm(Integer.parseInt(invest.getLoanDay())); //期限
				aiyouqianPushInvest.setUnit("d"); //单位（m表示月；d表示天）
			}else{
				aiyouqianPushInvest.setTerm(invest.getDeadline()); //期限
				aiyouqianPushInvest.setUnit("m"); //单位（m表示月；d表示天）
			}
			String repayType=invest.getRepayType();
			String aiouqianRepayType=ReadProperties.getPropetiesValue("constant/aiyouqianStatus.properties", repayType);
			aiyouqianPushInvest.setRepay_method(Integer.parseInt(aiouqianRepayType)); //投资还息还本方式
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 		aiyouqianPushInvest.setTouzhi_time(sdf.format(invest.getTime()));
			Map<String,Object>result=this.aiyouqianInter((JSONObject)JSONObject.toJSON(aiyouqianPushInvest),"trade_order");
			String code=result.get("code")+"";
			if(!"200".equals(code)){
				System.out.println("###############爱有推送投资记录出错："+result.get("result")+invest.getId());
			    log.errLog("爱有钱推送投资记录", "###############爱有推送投资记录出错："+result.get("result")+invest.getId());
				//RollbackException rollbackException=new RollbackException();
				//throw rollbackException;
			}else{
				//插入数据库
				PushInvest pushInvest=new PushInvest();
				pushInvest.setId(UUID.randomUUID().toString().replace("-",""));
				pushInvest.setInvestId(invest.getId());
				pushInvest.setUserId(invest.getUserId());
				pushInvest.setLoanId(invest.getLoanId());
				pushInvest.setAmount(invest.getMoney());
				pushInvest.setInvestTime(invest.getTime());
				pushInvest.setSendTime(new Date());
				pushInvest.setInterest(invest.getInterest());
				pushInvest.setInvestChannel(invest.getUserSource());
				pushInvest.setType("aiyouqian");
				platformPushDao.insertPushInvest(pushInvest);
			}
		}
	}
	
	/**
	 * 推送标的状态
	 */
	public void pushLoanStatus(String  investId) throws Exception{
		System.out.println("##############爱有钱推送标的状态");
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("id", investId);
		List<Invest>investList=platformDao.queryInvest(param);
		
		if(investList.size()>0){
			System.out.println("##############爱有钱查询invest根据id，长度："+investList.get(0).getId());
			Invest invest=investList.get(0);
			String loanStatus=invest.getLoanStatus();
			System.out.println("####################爱有钱标的状态："+loanStatus);
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("loanId", invest.getLoanId());
			params.put("type", "aiyouqian");
			List<PushLoan>pushLoanList=platformPushDao.getPushLoanInfo(params);
			
			if(pushLoanList.size()>0){
				String pushLoanStatus=pushLoanList.get(0).getStatus();
				if(!loanStatus.equals(pushLoanStatus)){   //push_loan表中的项目状态和当前的项目状态不相同，推送项目状态
					JSONObject dataJson=new JSONObject();
					dataJson.put("productid", pushLoanList.get(0).getLoanId());
					if("流标".equals(loanStatus)) {
						dataJson.put("status_id", 4);
					}else if("完成".equals(loanStatus)){
						dataJson.put("status_id", 5);
					}else{
						dataJson.put("status_id", 3);
					}
					
					Map<String,Object>resultMap=this.aiyouqianInter(dataJson, "borrowing_status");
					if("200".equals(resultMap.get("code")+"")){
						PushLoan pushLoan=new PushLoan();
						pushLoan.setType("aiyouqian");
						pushLoan.setLoanId(invest.getLoanId());
						pushLoan.setStatus(loanStatus);
						pushLoan.setUpdateTime(new Date());
						platformPushDao.updatePushLoan(pushLoan);
					}else{
						System.out.println("###############爱有推送标的状态记录出错："+resultMap.get("result")+invest.getId());
						log.errLog("爱有钱推送标的状态失败", "###############爱有推送标的状态记录出错："+resultMap.get("result")+invest.getId());
					}
				}
			}
			
		}
		
	}
	
	/**
	 * 推送已售金额
	 */
	public void pushLoanSaleMoney(String investId) throws Exception{
		System.out.println("#################爱有钱推送已售金额");
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("id", investId);
		List<Invest>investList=platformDao.queryInvest(param);
		Invest invest=investList.get(0);
		String loanId=invest.getLoanId();
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("Ids", "'"+loanId+"'");
		List<Loan>loanList=platformPushDao.getLoanByLoanId(params);
		Double sumMoney=loanList.get(0).getSumMoney();
		JSONObject dataJson=new JSONObject();
		dataJson.put("productid", loanId);
		dataJson.put("saleamount", sumMoney);
		Map<String,Object>resultMap=this.aiyouqianInter(dataJson,"borrowing_saleamount");
		if("200".equals(resultMap.get("code")+"")){
			PushLoan pushLoan=new PushLoan();
			pushLoan.setType("aiyouqian");
			pushLoan.setLoanId(invest.getLoanId());
			pushLoan.setUpdateTime(new Date());
			pushLoan.setAlreadyMoney(sumMoney);
			platformPushDao.updatePushLoan(pushLoan);
		}else{
			log.errLog("爱有钱推送已售金额失败", "###############爱有推送已售金额记录出错："+resultMap.get("result")+invest.getId());
			System.out.println("###############爱有推送已售金额记录出错："+resultMap.get("result")+invest.getId());
		}
	}


	/**
	 *爱有钱刷新用户数据接口
	 */
	@Override
	public JSONObject getAccInfo(String userId) {
		JSONObject reusltObject=new JSONObject();
		Map<String,Object> selectMap = platformDao.queryUserEnableAccount(userId);
		
		Double investing_principal = round(platformDao.getRecycledMoney(userId,"还款中"), 2);   //待收本金
		double wait_interest = 0;
		wait_interest = ArithUtil.round(
						platformDao.getInvestLoanDueInterest(userId,"!流标"), 2);  //待收利息
		Double current_money = round(platformDao.getDemandInMoney(userId),2);  //活期在投资金额
		Double amount=(Double)selectMap.get("enableMoney")+(Double)selectMap.get("frozen")+investing_principal+wait_interest+current_money; // 可用+冻结金额+待收本金+待收利息+活期投资金额
		
		Double earned_interest = round(platformDao.getInvestsTotalInterest(userId), 2);
		
		double avalidMoney = round(platformDao.getDemandAvlidTreasureMoney(userId),2);    //活期转入-转出
		double principal = round(platformDao.getDemandOutTreasureMoney1(userId),2);  //活期转出中金额
		Double investing_principal1 = ArithUtil.round(avalidMoney - principal, 2);  //活期转入-转出-转出中=活期在投
		
		if(!selectMap.get("frozen").equals("0")){
			Double investing_principal2 = investing_principal + investing_principal1;
			if(investing_principal2 > 0){
				reusltObject.put("trade_amount",investing_principal2); //在投资金  待收本金
			}else{
				reusltObject.put("trade_amount",0); //在投资金  待收本金
			}
		}else{
			reusltObject.put("trade_amount",investing_principal + investing_principal1); //在投资金  待收本金
		}
		
		reusltObject.put("amount",amount);  //帐户总额
		reusltObject.put("balance", selectMap.get("enableMoney"));   //可用余额 闲置金额
		reusltObject.put("wait_interest",wait_interest);  //待收利息
		reusltObject.put("appoint", selectMap.get("frozen"));  //冻结金额
		reusltObject.put("interest",earned_interest); //已收利息（累计已回款的收益，不包含待收利息）
		reusltObject.put("current_money",current_money); //当前活期投资金额，平台无活期为0 ，一共投到活期的钱
		return reusltObject;
	}
	
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("参数scale必须为整数或零!");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 查询标的投资情况接口
	 */
	@Override
	public JSONObject getLoanInvest(String loanId){
		JSONObject resultObject=new JSONObject();
		Map<String,Object>params=new HashMap<String,Object>();
		String loanIds="'"+loanId+"'";
		params.put("loanIds", loanIds);
		List<Loan> loanList=platformDao.queryLoan(params);
		if(loanList.size()>0){
			Loan loan=loanList.get(0);
			resultObject.put("saleamount", loan.getSumMoney());
			if("筹款中".equals(loan.getStatus())){
				resultObject.put("status_id", 1);
			}
			if("等待复核".equals(loan.getStatus()) || "还款中".equals(loan.getStatus())){
				resultObject.put("status_id", 3);
			}
			if("流标".equals(loan.getStatus())){
				resultObject.put("status_id", 4);
			}
			if("完成".equals(loan.getStatus())){
				resultObject.put("status_id", 5);
			}
			resultObject.put("productid", loanId);
		}
		return resultObject;
	}
	
}

