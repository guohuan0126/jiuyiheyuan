package com.duanrong.business.invest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import util.HttpUtil;
import util.Log;
import util.SpringBeanUtil;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.PropReader;
import com.duanrong.util.jedis.ICustomer;
import com.duanrong.util.jedis.RollbackException;
;

@Component
public class InvestCustomer implements ICustomer {

	@Resource
	Log log;

	@Resource
	InvestService investService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	SmsService smsService;

	@Resource
	InformationService informationService;

	@Resource
	PlatformTransferService platformTransferService;

	String[] beans;

	org.apache.commons.logging.Log l = LogFactory.getLog(InvestCustomer.class);

	@PostConstruct
	void init() {
		PropReader pd = new PropReader("/activeConfig.properties");
		beans = pd.readProperty("activeBean").split(",");
	}

	@Override
	public void customer(String arg0, String arg1) throws RollbackException {
		if (beans.length > 0) {
			for (String bean : beans) {
				InvestActive investActive = (InvestActive) SpringBeanUtil
						.getBeanByName(bean);
				l.debug(investActive.getActiveId());
				log.infoLog(investActive.getActiveId() + "活动开始", "investId:"
						+ arg1);
				PlatformTransfer platformTransfer = new PlatformTransfer();
				System.out.println("###############OrderId:"+investActive.getActiveId() + arg1);
				platformTransfer.setOrderId(investActive.getActiveId() + arg1);
				platformTransfer.setStatus("平台划款成功");
				List<PlatformTransfer> ps = platformTransferService
						.getList(platformTransfer);
				if (ps == null || ps.isEmpty()) {
					Invest invest = investService.getInvest(arg1);
					System.out.println("##########if:"+(invest != null
							&& (invest.getStatus().equals("投标成功") || invest
									.getStatus().equals("筹款中"))));
					if (invest != null
							&& (invest.getStatus().equals("投标成功") || invest
									.getStatus().equals("筹款中"))) {
						System.out.println("##########邀请活动investCustomer:");
						double awardMoney = investActive.execute(invest);
						System.out.println("##########邀请活动investCustomer:awardMoney"+awardMoney);
						if (awardMoney > 0) {
							sendAward(investActive, invest, awardMoney);
						}
					}
				}
			}
		}
	}

	/**
	 * 发送奖励
	 * 
	 * @param invest
	 * @param awardMoney
	 */
	private void sendAward(InvestActive investActive, Invest invest,
			double awardMoney) {
		try {
			PlatformTransfer platformTransfer = new PlatformTransfer();
			platformTransfer.setOrderId(investActive.getActiveId()
					+ invest.getId());
			platformTransfer.setType(investActive.getActiveId());
			platformTransfer.setStatus("平台划款成功");		
		   System.out.println("#########sendAward:OrderId:"+platformTransfer.getOrderId()+"InvestUserID:"
		                      +invest.getInvestUserID()+"awardMoney:"+ArithUtil.round(awardMoney, 2)+"investActive.getMsg():"+investActive.getMsg()+
		                      " platformTransfer.getType():"+ platformTransfer.getType()+"invest.getLoanId():"+invest.getLoanId());   
		    String url = "/trans/reward.do";
			JSONObject param = new JSONObject();
			param.put("userId",invest.getInvestUserID());
			param.put("remarks","loanId:" + invest.getLoanId() + ", investId:"
					+ invest.getId() + ","
					+ investActive.getTitle() + "："
					+ ArithUtil.round(awardMoney, 2) + "元");
			param.put("businessType","reward");
			param.put("money", ArithUtil.round(awardMoney, 2));
			param.put("loanId", invest.getLoanId());
			param.put("requestNo", platformTransfer.getOrderId());
			param.put("info", "红包奖励");
			JSONObject result = HttpUtil.sendDRPayPost(param, url);		
			if(result != null && result.getInteger("code") == 1){
				JSONObject data = result.getJSONObject("data");
				String status = data.getString("status");
				if ("success".equals(status)) {
					if (null != investActive.getMsg()
							&& !("").equals(investActive.getMsg())) {
						smsService.sendSms(invest.getInvestUserID(),
								investActive.getMsg(), null);
					}
					String[] infomations = investActive.getInfomation();
					if(infomations!=null){
						for(String infomation : infomations){
							if (null != infomation
									&& !("").equals(infomation)) {
								informationService.sendInformation(
										invest.getInvestUserID(), investActive.getTitle(),
										infomation);
							}
						}
					}			
				}else if("sended".equals(status)){
					throw new RollbackException();
					
				}else if("fail".equals(status)){
					log.errLog("奖励发送失败", 
							investActive.getActiveId() + "活动" + "investId:"
									+ invest.getId() + "奖励发送失败，请重新确认", 1);
				}
			} else {
				log.errLog("奖励发送失败", 
						investActive.getActiveId() + "活动" + "investId:"
								+ invest.getId(), 1);				
			}
		} catch (Exception e) {
			log.errLog(
					investActive.getActiveId() + "活动" + "investId:"
							+ invest.getId(), e, 1);		
		}
	}
}
