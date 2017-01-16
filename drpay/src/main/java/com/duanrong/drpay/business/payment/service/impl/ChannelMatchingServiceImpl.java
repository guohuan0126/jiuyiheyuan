package com.duanrong.drpay.business.payment.service.impl;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.AESUtil;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.drpay.business.account.dao.BankCardDao;
import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.payment.BankMapUtil;
import com.duanrong.drpay.business.payment.dao.PaymentBankChannelDao;
import com.duanrong.drpay.business.payment.dao.RechargeDao;
import com.duanrong.drpay.business.payment.model.PaymentAdvancefund;
import com.duanrong.drpay.business.payment.model.PaymentBankChannel;
import com.duanrong.drpay.business.payment.model.PaymentBankInfo;
import com.duanrong.drpay.business.payment.model.Recharge;
import com.duanrong.drpay.business.payment.service.ChannelMatchingService;
import com.duanrong.drpay.business.payment.service.PaymentAdvancefundService;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.ConfigConstant;

/**
 * 匹配最佳充值渠道Service实现
 * @author Today
 *
 */
@Service
public class ChannelMatchingServiceImpl implements ChannelMatchingService {

	@Autowired
	public PaymentBankChannelDao paymentBankChannelDao;
	
	@Resource
	PaymentAdvancefundService paymentAdvancefundService;
	
	@Autowired
    public UserService userService;
	
	@Autowired
    public BankCardDao bankCardDao;
	
	@Autowired
	public RechargeDao rechargeDao;
	
	private static final String pictureUlr =  ConfigConstant.OSS_SERVER;
	
	
	@Override
	public Map<String,Object> findChannelByMoney(String userId, Double money,String source) {
		
		
		DecimalFormat df = new DecimalFormat("###.##");
		String oneMoneyShow = "0元/笔"; //单笔限额提示
		String surplusMoneyShow = "今日还可充值金额0元"; //今日可充值额度提示
		String bankCardShow = "无银行卡";  //绑定银行卡+卡号提示
		String onlineBankingShow = "";  //网银提示
		String bankName = ""; //银行名称
		String bankLogo = ""; //银行Logo
		String chardType = ""; //卡片类型
		String channelType = ""; //渠道类型
		// FIXME 测试修改最小金额为0.01，原金额为3
		Double minMoney = 0.01D; //最小金额
		Double oneMoney = 0.0;   //单笔限额
		Double surplusMoney = 0.0; //今日可充值额度
		Double rechareCount = 0.0; //今日已充值额度
		Double rechareCountMonth = 0.0; //当月已充值额度
		String cardNo = "";
		String status = "";
		String mess = "";
		
		//根据用户ID查询银行卡信息
		List<BankCard> bankCard = this.bankCardDao.getValidBankCardByUserId(userId,false);
		System.out.println(userId+""+bankCard+":"+bankCard.size());
		if(bankCard != null && bankCard.size() > 0){
			cardNo = AESUtil.decode(bankCard.get(0).getCardNo());
			JSONObject param = BankMapUtil.findBankInfo(cardNo);
			bankName = param.getString("name");
			chardType = param.getString("cardType");
			List<PaymentBankInfo> bankInfoList = this.paymentBankChannelDao.findBankInfoByName(param.getString("name"));
			if(bankInfoList != null && bankInfoList.size() > 0){
				PaymentBankInfo bankInfo = bankInfoList.get(0);
				bankLogo = pictureUlr+bankInfo.getLogo();
				if(cardNo != null && cardNo.length() > 4){
					bankCardShow = bankInfo.getName()+"("+cardNo.substring(cardNo.length()-4)+")";
				}else{
					bankCardShow = bankInfo.getName();
				}
				
			}

			rechareCount = sumRechargeCount(this.rechargeDao.findQuickRechargeForToday(userId),bankName);
			rechareCountMonth = sumRechargeCount(this.rechargeDao.findQuickRechargeForMonth(userId),bankName);
			
			List<PaymentBankChannel> channelList = this.paymentBankChannelDao.findByBankChannel(bankName,null,1,source);
			
			//判断第三方平台资金是否充裕
			PaymentAdvancefund advancefund= paymentAdvancefundService.query();
			if(advancefund.getMoney() <= advancefund.getWarnMoney()){
				for(int i=0;i<channelList.size();i++){
					if(!channelList.get(i).getChannel().getCode().equals("Yeepay")){
						channelList.remove(i);
						i--;
					}
				}
			}
			
			money = money == 0 ? 1: money;
			Map<String,Object> data = this.findChannel(channelList,money,rechareCount,rechareCountMonth,userId,source); //计算
			if(StringUtils.equals((String)data.get("status"),"success")){
				oneMoney = (Double)data.get("maxOneMoney");
				if(oneMoney%10000 == 0){
					oneMoneyShow = df.format(oneMoney/10000)+"万/笔";
				}else{
					oneMoneyShow = df.format(oneMoney)+"元/笔";
				}
				surplusMoney = (Double)data.get("surplusMoney");
				if(surplusMoney%10000 == 0){
					surplusMoneyShow = "今日还可充值"+df.format(surplusMoney/10000)+"万";
				}else{
					surplusMoneyShow = "今日还可充值"+df.format(surplusMoney)+"元";
				}
			}
			status = (String)data.get("status");
			mess = (String)data.get("mess");
			channelType = (String)data.get("channelType");
			
		}else{
			status = "notBankCard";
			mess = "此用户没有绑定银行卡";
		}
		
		//支持网银查询
		List<String> onlineList = this.paymentBankChannelDao.findOnlineBank();
		for(int i=0;i<onlineList.size();i++){
			onlineBankingShow += onlineList.get(i);
			if(i < onlineList.size()-1){
				onlineBankingShow += ",";
			}
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status",status);
		result.put("mess",mess);
		result.put("bankCardShow",bankCardShow);
		result.put("oneMoneyShow",oneMoneyShow);
		result.put("surplusMoneyShow",surplusMoneyShow);
		result.put("onlineBankingShow",onlineBankingShow);
		result.put("onlineList",onlineList);
		result.put("minMoney",minMoney);
		result.put("maxOneMoney",oneMoney);
		result.put("surplusMoney",surplusMoney);
		result.put("bankName",bankName);
		result.put("chardType",chardType);
		result.put("channelType",channelType);
		result.put("bankLogo",bankLogo);
		result.put("cardNo",cardNo);
		result.put("adviseMoeny","建议充值金额100元");
		result.put("userInterior",userService.get(userId).getInterior());
		return result;
	}
	
	

	@Override
	public Map<String,Object> findChannelByBankCard(String cardNo,String source) {
		
		String quotaShow = "";//限额描述
		double oneMoney = 0; //单笔最大
		double dayMoney = 0; //单日最大
		double monthMoney = 0; //单月最大
		String bankName = ""; //银行名称
		String bankLogo = ""; //银行Logo
		String bankCode = ""; //银行简称
		String chardType = ""; //卡片类型
		String channelType = ""; //渠道类型
		String status = "";
		String mess = "";
		
		JSONObject param = BankMapUtil.findBankInfo(cardNo);
		if(param != null && param.getString("status").equals("success")){
			bankName = param.getString("name");
			chardType = param.getString("cardType");
			//查询数据库保存的银行Logo和简称信息
			List<PaymentBankInfo> bankInfoList = this.paymentBankChannelDao.findBankInfoByName(bankName);
			if(bankInfoList != null && bankInfoList.size() > 0){
				PaymentBankInfo bankInfo = bankInfoList.get(0);
				bankLogo = pictureUlr+bankInfo.getLogo();
				bankCode = bankInfo.getCode();
			}
			//判断是否为借记卡
			if(chardType.equals("借记卡")){
				List<PaymentBankChannel> channelList = this.paymentBankChannelDao.findByBankChannel(bankName,1,null,source);
				Map<String,Object> data = this.findChannel(channelList,0.0,0.0,0.0,null,null);
				//查询此银行快捷充值额度
				List<PaymentBankChannel> channelList02 = this.paymentBankChannelDao.findByBankChannel(bankName,null,1,source);
				Map<String,Object> data02 = this.findChannel(channelList02,0.0,0.0,0.0,null,null);
				if(data02 != null && data02.get("status").equals("success")){
					data.put("maxOneMoney",data02.get("maxOneMoney"));
					data.put("maxDayMoney",data02.get("maxDayMoney"));
					data.put("maxMontyMoney",data02.get("maxMontyMoney"));
				}else{
					data.put("maxOneMoney",0.0);
					data.put("maxDayMoney",0.0);
					data.put("maxMontyMoney",0.0);
				}
				//整理数据，返回给前台
				if(StringUtils.equals((String)data.get("status"),"success")){
					String oneMoneyShow = "0元/笔"; //单笔限额提示
					String dayMoneyShow = "0元/日"; //单笔限额提示
					String monthMoneyShow = "0元/月"; //单笔限额提示
					DecimalFormat df = new DecimalFormat("###.##");
					oneMoney = (Double)data.get("maxOneMoney");
					if(oneMoney%10000 == 0){
						oneMoneyShow = df.format(oneMoney/10000)+"万/笔";
					}else{
						oneMoneyShow = df.format(oneMoney)+"元/笔";
					}
					dayMoney = (Double)data.get("maxDayMoney");
					if(dayMoney%10000 == 0){
						dayMoneyShow = df.format(dayMoney/10000)+"万/日";
					}else{
						dayMoneyShow = df.format(dayMoney)+"元/日";
					}
					monthMoney = (Double)data.get("maxMontyMoney");
					if(monthMoney%10000 == 0){
						monthMoneyShow = df.format(monthMoney/10000)+"万/月";
					}else{
						monthMoneyShow = df.format(monthMoney)+"元/月";
					}
					quotaShow = oneMoneyShow+"，"+dayMoneyShow+"，"+monthMoneyShow;
				}
				status = (String)data.get("status");
				mess = (String)data.get("mess");
				channelType = (String)data.get("channelType");
			}else{
				status = "nonsupportType";
				mess = "不支持的卡片类型";
			}
			
		}else{
			status = "bankNotFound";
			mess = "查询不到银行信息";
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status",status);
		result.put("mess",mess);
		result.put("quotaShow",quotaShow);
		result.put("oneMoney",oneMoney);
		result.put("dayMoney",dayMoney);
		result.put("monthMoney",monthMoney);
		result.put("bankName",bankName);
		result.put("bankCode",bankCode);
		result.put("chardType",chardType);
		result.put("channelType",channelType);
		result.put("bankLogo",bankLogo);
		result.put("cardNo",cardNo);
		return result;
	}
	
	/**
	 * 
	 * @param bankName 
	 * @param money 准备充值金额
	 * @param rechareCount 今日充值总金额
	 * @return
	 */
	public Map<String,Object> findChannel(List<PaymentBankChannel> channelList,Double money,Double rechareCount,Double rechareCountMonth,String userId,String source) {
		Map<String,Object> result = new HashMap<String,Object>();
		double surplusMoney = 0; //可充值金额
		double maxMontyMoney = 0;   //单月最大额
		double maxDayMoney = 0;   //单日最大额
		double maxOneMoney = 0;   //单笔最大额
		String channelType = null;  //渠道类型
		PaymentBankChannel lastChannel = null;    //支付渠道
		

        
        //区别内部
//        if(channelList != null && StringUtils.isNotBlank(userId)){
//    		User user = userService.query(userId);
//    		for(int i=0;i<channelList.size();i++){
//				String code = channelList.get(i).getChannel().getCode();
//    			if(user!=null && "duanrongw".equals(user.getInterior())){
//    				if(!"Baofoo".equals(code)){
//    					channelList.remove(i);
//    					i--;
//    				}
//    			}else{
//    				if("Baofoo".equals(code)){
//    					channelList.remove(i);
//    					i--;
//    				}
//    			}
//    		}
//        }
        
		//渠道集合
        if(channelList == null || channelList.size() == 0){
        	result.put("status","noChannel");
        	result.put("mess","无支付渠道支持");
        	return result;
        }
        
        if(channelList.size() > 0){
        	maxOneMoney = getMaxOneMoney(channelList);
            maxDayMoney = getMaxDayMoney(channelList);
            maxMontyMoney = getMaxMonthMoney(channelList);
        }
		
		System.out.println("#######第一次筛选#######");
		//2.过滤掉不满足单日限额或单笔限额的渠道
		for(int i=0;i<channelList.size();i++){
			//单月剩余充值金额
			double monthMoney = channelList.get(i).getMonthMoney() - rechareCountMonth;
			monthMoney = monthMoney < 0 ? 0 : monthMoney;
			//单日剩余充值金额
			double dayMoney = channelList.get(i).getDayMoney() - rechareCount; 
			dayMoney = dayMoney < 0 ? 0 : dayMoney;
			//单笔充值金额
			double oneMoney = channelList.get(i).getOneMoney();  
			
			dayMoney = dayMoney > monthMoney ? monthMoney : dayMoney;
			oneMoney = oneMoney > dayMoney ? dayMoney : oneMoney;
			
			if(money > dayMoney || money > oneMoney){//没有超额（单日）
				channelList.remove(i);
				i--;
			}else{
				channelList.get(i).setMonthMoney(monthMoney);
				channelList.get(i).setDayMoney(dayMoney);
				channelList.get(i).setOneMoney(oneMoney);
			}
		}
        for(int i=0;i<channelList.size();i++){
        	System.out.println(channelList.get(i).getBankInfo().getName() +"  单笔："+channelList.get(i).getOneMoney() +" 单日："+channelList.get(i).getDayMoney() +" 排序："+channelList.get(i).getChannel().getSort());
        }
        
        System.out.println("#######第二次按序号排序#######");
		//按充单笔最大金额排序
		Comparator<PaymentBankChannel> comparator = new Comparator<PaymentBankChannel>(){
			@Override
			public int compare(PaymentBankChannel o1, PaymentBankChannel o2) {
				return (int)(o1.getChannel().getSort() - o2.getChannel().getSort());
			}
		};
		Collections.sort(channelList,comparator);
        for(int i=0;i<channelList.size();i++){
        	System.out.println(channelList.get(i).getBankInfo().getName() +"  单笔："+channelList.get(i).getOneMoney() +" 单日："+channelList.get(i).getDayMoney() +" 排序："+channelList.get(i).getChannel().getSort());
        }

        if(channelList != null && channelList.size() > 0){
        	lastChannel = channelList.get(0);
        }
		
        System.out.println("#######页面展示#######");
        if(channelList.size() > 0){
        	maxMontyMoney = getMaxMonthMoney(channelList);
        	if(maxMontyMoney >= maxDayMoney){
        		maxOneMoney = getMaxOneMoney(channelList);
        		surplusMoney = maxDayMoney-rechareCount;
        	}else{
        		maxDayMoney = getMaxDayMoney(channelList);
            	maxOneMoney = getMaxOneMoney(channelList);
            	surplusMoney = maxDayMoney;
        	}

            
        }

        
        //surplusMoney = maxDayMoney > maxMontyMoney ? maxMontyMoney : maxDayMoney;
        
        System.out.println("单笔最大金额："+maxOneMoney);
        System.out.println("单日最大金额："+maxDayMoney);
        System.out.println("单月最大金额："+maxMontyMoney);
        System.out.println("可充值金额："+surplusMoney);
        
        System.out.println("#######运行结果#######");
        if(money > 0){
        	System.out.println("结果："+(lastChannel != null ? lastChannel.getChannel().getName() : "无法充值"));
        }
        if(lastChannel != null){
        	channelType = lastChannel.getChannel().getCode();
        }
        result.put("channelBank",lastChannel);
        result.put("maxMontyMoney",maxMontyMoney);
        result.put("maxDayMoney",maxDayMoney);
        result.put("maxOneMoney",maxOneMoney);
        result.put("surplusMoney",surplusMoney);
        result.put("channelType",channelType);
        if(lastChannel != null){
        	result.put("status","success");
        	result.put("mess","可以充值");
        }else{
        	result.put("status","noSurplusMoney");
        	result.put("mess","充值额度已用完");
        }
		return result;
	}

	/**
	 * 获取单月最大金额
	 * @param channelList
	 * @return
	 */
	public static Double getMaxMonthMoney(List<PaymentBankChannel> channelList){
		//按单日最大金额排序，并计算可充值金额（单日最大-今日充值）
		Comparator<PaymentBankChannel> comparator = new Comparator<PaymentBankChannel>() {
			@Override
			public int compare(PaymentBankChannel o1, PaymentBankChannel o2) {
				return (int)(o2.getMonthMoney() - o1.getMonthMoney());
			}
		};
		Collections.sort(channelList,comparator);
        return channelList.get(0).getMonthMoney();
	}
	
	
	/**
	 * 获取单日最大金额
	 * @param channelList
	 * @return
	 */
	public static Double getMaxDayMoney(List<PaymentBankChannel> channelList){
		//按单日最大金额排序，并计算可充值金额（单日最大-今日充值）
		Comparator<PaymentBankChannel> comparator = new Comparator<PaymentBankChannel>() {
			@Override
			public int compare(PaymentBankChannel o1, PaymentBankChannel o2) {
				return (int)(o2.getDayMoney() - o1.getDayMoney());
			}
		};
		Collections.sort(channelList,comparator);
        return channelList.get(0).getDayMoney();
	}
	
	
	/**
	 * 获取单笔最大金额
	 * @param channelList
	 * @return
	 */
	public static Double getMaxOneMoney(List<PaymentBankChannel> channelList){
		//按单日最大金额排序，并计算可充值金额（单日最大-今日充值）
		Comparator<PaymentBankChannel> comparator = new Comparator<PaymentBankChannel>() {
			@Override
			public int compare(PaymentBankChannel o1, PaymentBankChannel o2) {
				return (int)(o2.getOneMoney() - o1.getOneMoney());
			}
		};
		Collections.sort(channelList,comparator);
        return channelList.get(0).getOneMoney();
	}
	
	
	/**
	 * 统计某个银行的累计金额
	 * @param rechList
	 * @param bankName
	 * @return
	 */
	public static Double sumRechargeCount(List<Recharge> rechList,String bankName){
		
		Double money = 0.0;
		for(int i=0;i<rechList.size();i++){
			if(StringUtils.isNoneBlank(rechList.get(i).getCardNo())){
				JSONObject json = BankMapUtil.findBankInfo(AESUtil.decode(rechList.get(i).getCardNo()));
				if(json.getString("status").equals("success") &&  json.getString("name").equals(bankName)){
					money += rechList.get(i).getActualMoney();
				}
			}
		}
		return money;
	}

}
