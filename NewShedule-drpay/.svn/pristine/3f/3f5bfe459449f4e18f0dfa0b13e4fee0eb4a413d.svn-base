package com.duanrong.business.invest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import util.Log;

import com.duanrong.business.demand.dao.DemandtreasureInvestDao;
import com.duanrong.business.demand.model.DemandTreasureInvest;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.User;
import com.duanrong.sms.SMSSend;
import com.duanrong.util.DateUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.jedis.ICustomer;
import com.duanrong.util.jedis.RollbackException;


@Component
public class IsFirstInvest implements ICustomer{

	@Autowired
	private DemandtreasureInvestDao demandtreasureInvestDao;
	@Autowired
	private InvestDao investDao;
	@Autowired
	private RedPacketDao redPacketDao;
	@Autowired
	private UserDao userDao;

	@Resource
	SmsDao smsDao;
	@Resource
	Log log;
	/**
	 * 判断用户是不是首投
	 * arg0:key值
	 * arg1:value值
	 */
	@Override
	public void customer(String arg0, String arg1) throws RollbackException {
		try {
			System.out.println("#########arg0:"+arg0);
			System.out.println("#########arg1:"+arg1);
			System.out.println("#########是不是首投");
			String content="恭喜您完成首投，首投福利30元红包已到账，有效期3天！1元起投，9-13%收益，快前往 t.cn/R4L4Usl 使用，回TD退订";
			Map<String,Object>param=new HashMap<String,Object>();
			param.put("userId",arg1);
			//查询投资人是否有过活期投资
			List<DemandTreasureInvest> demandList=demandtreasureInvestDao.getDemandByUserId(param);
			if(demandList.size()==0){   //没有活期投资
				List<Invest>investList=investDao.getInvestBy(param);
				if(investList.size()==1){ //定期首投
					System.out.println("########定期首投"+investList.get(0).getId());
					User user=userDao.get(arg1);
					user.setUserId(arg1);
					createReadpacket(user, 279, user.getMobileNumber(), 30, 0, 3000, 1, "1", "首投福利", "money", 3);
					try {
						Sms sms = new Sms();
						sms.setId(IdGenerator.randomUUID());
						sms.setUserId(user.getUserId());
						sms.setMobileNumber(user.getMobileNumber());
						sms.setContent(content);
						sms.setTime(new Date());
						sms.setType(SmsConstants.INFORMATION);
						smsDao.insert(sms);
						SMSSend.batchSend1(user.getMobileNumber(), content);
						log.infoLog("短信", "完成首次投资后" + user.getMobileNumber() + "短信");
						System.out.println("**********************发送短信*****");
					} catch (Exception e) {
						log.errLog("短信", user.getMobileNumber()+ "完成首次投资后"+e);
						e.printStackTrace();
					}
				}
			}else if(demandList.size()==1){ //有一笔活期
				List<Invest>investList=investDao.getInvestBy(param);
				if(investList.size()==0){  //活期首投
					System.out.println("#####活期首投:"+demandList.get(0).getId());
					User user=userDao.get(arg1);
					createReadpacket(user, 275, user.getMobileNumber(), 30, 0, 3000, 3, "1", "首投福利", "money", 3);
					try {
						Sms sms = new Sms();
						sms.setId(IdGenerator.randomUUID());
						sms.setUserId(user.getUserId());
						sms.setMobileNumber(user.getMobileNumber());
						sms.setContent(content);
						sms.setTime(new Date());
						sms.setType(SmsConstants.INFORMATION);
						smsDao.insert(sms);
						SMSSend.batchSend1(user.getMobileNumber(), content);
						log.infoLog("短信", "完成首次投资后" + user.getMobileNumber() + "短信");
						System.out.println("**********************发送短信*****");
					} catch (Exception e) {
						log.errLog("短信", user.getMobileNumber()+ "完成首次投资后"+e);
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 创建红包
	 * @param user 用户
	 * @param mobileNumber 手机号
	 * @param money 金额
	 * @param investMoney 
	 * @param investCycle
	 * @param useLoanType
	 * @param redpacketName 红包名称
	 * @param redpacketType 红包类型现金还是加息券
	 * @param redpacketDeadLine 有效时间
	 * @return
	 */
	public int createReadpacket(User user, int ruleId,String mobileNumber, double money,double rate,
			double investMoney, int investCycle, String useLoanType,String redpacketName,String redpacketType,int  redpacketDeadLine) {
			RedPacket redpacket = new RedPacket();
			redpacket.setName(redpacketName);
			redpacket.setMoney(money);
			redpacket.setUserId(user.getUserId());
			redpacket.setMobileNumber(mobileNumber);
			Date d = new Date();
			redpacket.setCreateTime(d);
			redpacket.setDeadLine(DateUtil.addDay(d, redpacketDeadLine));
			redpacket.setSendStatus("unused");
			redpacket.setType(redpacketType);
			redpacket.setRuleId(ruleId);
			redpacket.setUseLoanType(useLoanType);
			redpacket.setRate(rate);
			redpacket.setUsageDetail("invest");
			redpacket.setInvestMoney(investMoney);
			redpacket.setUsageRule("投资可用");
			redpacket.setInvestCycle(investCycle);
			redPacketDao.insert(redpacket);
			return 1;

	}
}
