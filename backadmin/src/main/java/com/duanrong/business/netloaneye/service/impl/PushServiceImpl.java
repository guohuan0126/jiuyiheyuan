package com.duanrong.business.netloaneye.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;










import util.p2pEyeUtil.MD5;
import util.p2pEyeUtil.P2PEyeHttpUtilAgain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.netloaneye.dao.PushInfoRowDao;
import com.duanrong.business.netloaneye.dao.PushInvestorDao;
import com.duanrong.business.netloaneye.dao.PushLoanDao;
import com.duanrong.business.netloaneye.dao.PushTransactionDao;
import com.duanrong.business.netloaneye.model.PushInfoRow;
import com.duanrong.business.netloaneye.model.PushInvestor;
import com.duanrong.business.netloaneye.model.PushLoanAgain;
import com.duanrong.business.netloaneye.model.PushTransaction;
import com.duanrong.business.netloaneye.service.PushService;
import com.duanrong.newadmin.utility.AESUtil;
import com.duanrong.newadmin.utility.NEWAESUtil;
import com.duanrong.util.IdGenerator;


/**
 * 推送业务逻辑
 * @author Today
 *
 */
@Service
public class PushServiceImpl implements PushService {

	
	@Autowired
	public PushInfoRowDao pushInfoRowDao;
	
	@Autowired
	public PushLoanDao pushLoanDao;
	
	@Autowired
	public PushTransactionDao pushTransactionDao;
	
	@Autowired
	public PushInvestorDao pushInvestorDao;
	
	
	
	
	@Override
	public Integer pushInvestPerson(String id) {
		//1.查询需要推送的投资人信息
		List<PushInfoRow> pushList = this.pushInfoRowDao.getPushInvestList(id);
		Integer pushCode = 0;
		/*//2.推送项目投资人列表
		
		List<JSONObject> investList = getInvestorsList(pushList);
		for(JSONObject param : investList){
			System.out.println("推送投资人记录："+param.toJSONString());
			pushCode = P2PEyeHttpUtil.sendPost(param,"loan");
			if(pushCode != 200){
				return pushCode;
			}
			System.out.println("推送成功："+pushCode);
		} 
		System.out.println("投资人信息推送成功："+pushCode);*/
		//3.推送交易信息，仅天眼注册来的用户
		List<PushInfoRow> p2pEyeUsers = new ArrayList<PushInfoRow>();
		for(PushInfoRow person : pushList){
			//判断是否为天眼注册来的用户
			if(person.getWhetherNew() != null){
				//3.推送交易信息
				JSONObject param = getInvestInfo(person);
				System.out.println("推送交易记录："+param.toJSONString());
				pushCode = P2PEyeHttpUtilAgain.sendPost(param,"order");
				System.out.println("****************************************"+pushCode+"************************************");
				if(pushCode != 200){
					return pushCode;
				}
				p2pEyeUsers.add(person);
				System.out.println("推送成功："+pushCode);
			}
		}
		System.out.println("交易信息推送成功："+pushCode);
	
		
		return pushCode;
	}
	
	
	
    /**
     * 推送项目状态
     */
	@Override
	public Integer pushLoanStatusInfo(List<PushLoanAgain> loanList) {
		
		Integer pushCode = 0;
		List<String> ids = new ArrayList<String>();
		for(PushLoanAgain loan : loanList){
			//推送状态或项目状态是已完成则推送 5 下架
			if("完成".equals(loan.getStatus()) || 
			   "还款中".equals(loan.getStatus()) ||
			   "完成".equals(loan.getLoanStatus())||
			   "还款中".equals(loan.getLoanStatus())){
				JSONObject param = getPushLoanInfo(loan);
				System.out.println("推送项目状态："+param.toJSONString());
				pushCode = P2PEyeHttpUtilAgain.sendPost(param,"loan");
				//4.添加交易记录
				if(pushCode != 200){
					return pushCode;
				}
				ids.add(loan.getId());
				System.out.println("项目状态推送成功："+pushCode);
			}
		}
		if(pushCode == 200){
			if(ids.size() > 0){
				pushLoanDao.updateStatus(ids); //批量更新状态
			}
		}
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
	 * 整理投资人推送信息
	 * @param pushList
	 * @return
	 */
	public List<JSONObject> getInvestorsList(List<PushInfoRow> pushList){
		
		Map<String,JSONObject> jsonMap = new HashMap<String,JSONObject>();
		for(PushInfoRow push : pushList){
			System.out.println("需要推送的投资人："+push.toString());
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
		json.put("cost",push.getCost()*1000);
		json.put("send_time",System.currentTimeMillis()/1000);
		json.put("status",2);
		return json;
	}

	/**
	 * 项目状态推送JSON
	 * @param loan
	 * @return
	 */
	public JSONObject getPushLoanInfo(PushLoanAgain loan){
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
			case "完成":return 5;
			case "流标":return 5;
			case "还款中":return 2;
			case "已投满":return 2;
			case "已筹满":return 2;
			default:return 2;
		}
	}



	@Override
	public Integer pushInvestPersonByUserId(Map<String, Object> params) {
		//1.查询需要推送的投资人信息
				List<PushInfoRow> pushList = this.pushInfoRowDao.getPushInvestPersonByUserId(params);
				Integer pushCode = 0;
				/*//2.推送项目投资人列表
				
				List<JSONObject> investList = getInvestorsList(pushList);
				for(JSONObject param : investList){
					System.out.println("推送投资人记录："+param.toJSONString());
					pushCode = P2PEyeHttpUtil.sendPost(param,"loan");
					if(pushCode != 200){
						return pushCode;
					}
					System.out.println("推送成功："+pushCode);
				} 
				System.out.println("投资人信息推送成功："+pushCode);*/
				//3.推送交易信息，仅天眼注册来的用户
				List<PushInfoRow> p2pEyeUsers = new ArrayList<PushInfoRow>();
				for(PushInfoRow person : pushList){
					//判断是否为天眼注册来的用户
					if(person.getWhetherNew() != null){
						//3.推送交易信息
						JSONObject param = getInvestInfo(person);
						System.out.println("推送交易记录："+param.toJSONString());
						pushCode = P2PEyeHttpUtilAgain.sendPost(param,"order");
						System.out.println("****************************************"+pushCode+"************************************");
						if(pushCode != 200){
							return pushCode;
						}
						p2pEyeUsers.add(person);
						System.out.println("推送成功："+pushCode);
					}
				}
				System.out.println("交易信息推送成功："+pushCode);
			
				
				return pushCode;
	}



	


	



	

}
