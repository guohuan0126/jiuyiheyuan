package com.duanrong.business.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.dao.PushInfoRowDao;
import com.duanrong.business.dao.PushInvestorDao;
import com.duanrong.business.dao.PushLoanDao;
import com.duanrong.business.dao.PushTransactionDao;
import com.duanrong.business.model.PushInfoRow;
import com.duanrong.business.model.PushInvestor;
import com.duanrong.business.model.PushLoan;
import com.duanrong.business.model.PushTransaction;
import com.duanrong.business.service.PushService;
import com.duanrong.util.p2pEyeUtil.IdGenerator;
import com.duanrong.util.p2pEyeUtil.AESUtil;
import com.duanrong.util.p2pEyeUtil.MD5;
import com.duanrong.util.p2pEyeUtil.P2PEyeHttpUtil;

/**
 * 推送业务逻辑
 * @author Today
 *
 */
@Service
public class PushServiceImpl implements PushService {

	private static Logger logger = Logger.getLogger(PushServiceImpl.class);
	
	@Autowired
	public PushInfoRowDao pushInfoRowDao;
	
	@Autowired
	public PushLoanDao pushLoanDao;
	
	@Autowired
	public PushTransactionDao pushTransactionDao;
	
	@Autowired
	public PushInvestorDao pushInvestorDao;
	
	@Override
	public Integer pushInvestPerson() {
		//1.查询需要推送的投资人信息
		List<PushInfoRow> pushList = this.pushInfoRowDao.getPushInvestList();
		
		//2.推送项目投资人列表
		Integer pushCode = 0;
		List<JSONObject> investList = getInvestorsList(pushList);
		logger.info("********推送投资达人数量："+investList.size()+"*****");
	    int investorsCount = 0;
		for(JSONObject param : investList){
			logger.info("投资达人推送参数："+param);
			pushCode = P2PEyeHttpUtil.sendPost(param,"loan");
			if(pushCode == 200){
				investorsCount++;
			}
		}
		logger.info("********投资达人成功数量："+investorsCount+"**************");
		//3.推送交易信息，仅天眼注册来的用户
		logger.info("****************开始推送交易信息****************");
		logger.info("遍历数量："+pushList.size());
		List<PushInfoRow> successList = new ArrayList<PushInfoRow>();
		List<PushInfoRow> failList = new ArrayList<PushInfoRow>();
		for(int i=0;i<pushList.size();i++){
			PushInfoRow person = pushList.get(i);
			//判断是否为天眼注册来的用户
			if(person.getWhetherNew() != null){
				//3.推送交易信息
				JSONObject param = getInvestInfo(person);
				logger.info((i+1)+" userId:"+person.getUserId()+" 推送参数："+param.toJSONString());
				pushCode = P2PEyeHttpUtil.sendPost(param,"order");
				if(pushCode == 200){
					int rows = savePushInvestor(person);   //推送记录保存到数据库
					logger.info("投资达人数据库保存："+rows);
					rows = savePushTransaction(person); //推送交易记录保存到数据库
					logger.info("交易记录数据库保存："+rows);
					successList.add(person); //成功保存
					
				}else{
					failList.add(person);
					logger.error("推送失败 userId:"+person.getUserId()+" 推送参数："+param.toJSONString());
				}
				logger.info("推送结果："+pushCode);
			}
		}
		logger.info("最后推送结果成功次数："+successList.size()+"，失败数量："+failList.size());
		for(int i=0;i<failList.size();i++){
			logger.error("第"+(i+1)+"条失败推送："+failList.get(i));
		}
		return pushCode;
	}
	
	
	
    /**
     * 推送项目状态
     */
	@Override
	public Integer pushLoanStatusInfo(List<PushLoan> loanList) {
		logger.info("查询到的项目数量："+loanList.size());
		Integer pushCode = 0;
		List<String> ids = new ArrayList<String>();
		for(PushLoan loan : loanList){
			//如果状态不一致则推送
			logger.info("项目ID："+loan.getId()+" 项目状态："+loan.getLoanStatus()+" 历史状态:"+loan.getStatus()+" 对比结果:"+(!loan.getLoanStatus().equals(loan.getStatus())));
			if(!loan.getLoanStatus().equals(loan.getStatus())){
				loan.setStatus(loan.getLoanStatus());
				loan.setPushStatus(exchangeStatus(loan.getLoanStatus()));
				JSONObject param = getPushLoanInfo(loan);
				logger.info("项目ID："+loan.getId()+" 推送前参数："+param.toJSONString());
				pushCode = P2PEyeHttpUtil.sendPost(param,"loan");
				//4.添加交易记录
				logger.info("推送结果："+pushCode);
				if(pushCode == 200){
					int rows = pushLoanDao.updateStatus(loan.getPushStatus(),loan.getLoanStatus(),loan.getId()); //批量更新状态
					logger.info("项目状态数据库更新："+rows);
				}else{
					logger.error("推送失败 userId:"+loan.getId()+" 推送前参数："+param.toJSONString());
				}
				
			}
		}
		logger.info("更新项目数量："+ids.size());
		return pushCode;
		
	}
	
	/**
	 * 批量添加投资人信息推送记录
	 * @param pushList
	 * @return
	 */
	public int savePushInvestor(List<PushInfoRow> pushList){
		List<PushInvestor> investlist = new ArrayList<PushInvestor>();
		for(PushInfoRow push : pushList){
			PushInvestor person = new PushInvestor();
			person.setId(IdGenerator.randomUUID());
			person.setAmount(push.getAmount());
			person.setInvestTime(push.getTrade_time());
			person.setLoanId(push.getLoan_id());
			person.setSendTime(new Date());
			person.setUserId(push.getUserId());
			investlist.add(person);
		}
		this.pushInvestorDao.insert(investlist);
		return 0;
	}
	
	
	/**
	 * 批量添加投资人信息推送记录
	 * @param pushList
	 * @return
	 */
	public int savePushInvestor(PushInfoRow push){
		List<PushInvestor> investlist = new ArrayList<PushInvestor>();
		PushInvestor person = new PushInvestor();
		person.setId(IdGenerator.randomUUID());
		person.setAmount(push.getAmount());
		person.setInvestTime(push.getTrade_time());
		person.setLoanId(push.getLoan_id());
		person.setSendTime(new Date());
		person.setUserId(push.getUserId());
		person.setType("p2peye");
		investlist.add(person);
		return this.pushInvestorDao.insert(investlist);
	}
	
    /**
    * 保存推送交易记录信息
    * @param push
    * @return
    */
	public int savePushTransaction(List<PushInfoRow> pushList) {
		List<PushTransaction> recordlist = new ArrayList<PushTransaction>();
		for(PushInfoRow push : pushList){
			PushTransaction record = new PushTransaction();
			record.setAmount(push.getAmount());
			record.setBonus(0D);
			record.setCost(push.getCost());
			record.setId(IdGenerator.randomUUID());
			record.setLoanId(push.getLoan_id());
			record.setMobile(push.getMobile());
			record.setOrderId(push.getOrder_id());
			record.setSendTime(new Date());
			record.setStatus("2");
			record.setTradeTime(push.getTrade_time());
			record.setUserkey(push.getUserkey());
			recordlist.add(record);
		}
		return this.pushTransactionDao.insert(recordlist);
	}
	
	
    /**
    * 保存推送交易记录信息
    * @param push
    * @return
    */
	public int savePushTransaction(PushInfoRow push) {
		List<PushTransaction> recordlist = new ArrayList<PushTransaction>();
		PushTransaction record = new PushTransaction();
		record.setAmount(push.getAmount());
		record.setBonus(0D);
		record.setCost(push.getCost());
		record.setId(IdGenerator.randomUUID());
		record.setLoanId(push.getLoan_id());
		record.setMobile(push.getMobile());
		record.setOrderId(push.getOrder_id());
		record.setSendTime(new Date());
		record.setStatus("2");
		record.setTradeTime(push.getTrade_time());
		record.setUserkey(push.getUserkey());
		recordlist.add(record);
		return this.pushTransactionDao.insert(recordlist);
	}
	
	/**
	 * 整理投资人推送信息
	 * @param pushList
	 * @return
	 */
	public List<JSONObject> getInvestorsList(List<PushInfoRow> pushList){
		
		Map<String,JSONObject> jsonMap = new HashMap<String,JSONObject>();
		for(PushInfoRow push : pushList){
			logger.info("需要推送的投资人："+push.toString());
			String mobile = push.getMobile();
			if(mobile.length() >= 11){
				mobile = mobile.substring(0,3)+"****"+mobile.substring(7);
			}
			JSONObject param = null;
			JSONArray investors = null;
			JSONObject invest = new JSONObject();
			invest.put("username",mobile);
			invest.put("amount",push.getAmount());
			invest.put("invest_time",push.getTrade_time().getTime()/1000);
			
			if(jsonMap.containsKey(push.getLoan_id())){
				param = jsonMap.get(push.getLoan_id());
				investors = param.getJSONArray("investors");
				investors.add(invest);
			}else{
				param = new JSONObject();
				param.put("type",2);
				param.put("loan_id",push.getLoan_id());
				investors = new JSONArray();
				investors.add(invest);
				param.put("investors",investors);
			}
			jsonMap.put(push.getLoan_id(),param);
			
		}
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		for(String key : jsonMap.keySet()){
			jsonList.add(jsonMap.get(key));
		}
		return jsonList;
	}
	
	/**
	 * 推送交易信息
	 * @param push
	 * @return
	 */
	public JSONObject getInvestInfo(PushInfoRow push){
		JSONObject json = new JSONObject();
		json.put("mobile",push.getMobile());
		json.put("loan_id",push.getLoan_id());
		json.put("userkey",push.getUserkey());
		try {
			json.put("order_id",MD5.getMd5(push.getOrder_id().getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("amount",push.getAmount());
		json.put("trade_time",push.getTrade_time().getTime()/1000);
		json.put("bonus",0);
		json.put("cost",push.getCost()*100);
		json.put("send_time",System.currentTimeMillis()/1000);
		json.put("status",2);
		return json;
	}

	/**
	 * 项目状态推送JSON
	 * @param loan
	 * @return
	 */
	public JSONObject getPushLoanInfo(PushLoan loan){
		JSONObject json = new JSONObject();
		json.put("type",3);
		json.put("loan_id",loan.getLoanId());
		json.put("status",exchangeStatus(loan.getStatus()));
		json.put("send_time",System.currentTimeMillis()/1000);
		return json;
	}
	

	/**
	 * 与天眼标状态对比转换
	 * 标的状态 1在投,2还款中,3正常还款, 4提前还款, 5下架 
	 * 短融标状态：完成、流标、还款中、已投满、筹款中、等待复核、已筹满
	 * @param status
	 * @return
	 */
	private int exchangeStatus(String status){
		switch(status){
			case "完成":return 3;
			case "流标":return 5;
			case "还款中":return 2;
			case "等待复核":return 2;
			case "已投满":return 2;
			case "已筹满":return 2;
			case "筹款中":return 1;
			case "贷前公告":return 1;
			default:return 1;
		}
	}

}
