package com.duanrong.business.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import base.exception.RedPacketUseException;
import base.model.PageData;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.Rule;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.RedPacketService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.sms.SMSSend;
import com.duanrong.util.DateUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.ShortUrlGenerator;

/**
 * @author xiao
 * @date 2015年1月22日 下午3:25:03
 */
@Service
public class RedPacketServiceImpl implements RedPacketService {

	@Resource
	RedPacketDao redPacketDao;

	@Resource
	Log log;

	@Resource
	UserService userService;

	@Resource
	InvestService investService;

	@Resource
	UserDao userDao;

	@Resource
	InformationService informationService;
	
	@Resource
	SmsDao smsDao;

	public PageData<RedPacket> findPaging(int pageNo, int pageSize,
			RedPacket redPacket) {
		return redPacketDao.findPaging(pageNo, pageSize, redPacket);
	}

	@Override
	public void create(String userId) {

		User u = userService.get(userId);
		if (u == null)
			return;
		String referrer = u.getReferrer();

		Date registTime = u.getRegisterTime();

		log.infoLog("加息券", "推荐人--------:" + referrer);

		if (referrer != null
				&& (referrer.length() == 8 || MyStringUtils.isNumeric(referrer)
						&& referrer.length() == 11)
				&& u.getRegisterTime() != null
				&& DateUtil.dayDifference("2015-06-20", DateUtil.DateToString(
						u.getRegisterTime(), "yyyy-MM-dd")) > 1) {
			double rate = 0.0;
			User user = new User();
			if (MyStringUtils.isNumeric(referrer) && referrer.length() == 11) {				
				user = userDao.getUserByMobileNumber(referrer);
				if(user == null){
					log.infoLog("加息券", "推荐人不存在, referrer:" + referrer);
					return ;
				}				
				if (null != user.getReferrer() && user.getMobileNumber().equals(user.getReferrer())) {					
					log.infoLog("加息券", "推荐人和邀请人一致, userId: "+user.getUserId()+" Referrer:" + referrer);
					return ;					
				}
			} else {
				user = userDao.getUserByInviteCode(referrer);
				if(user == null){
					log.infoLog("加息券", "推荐人不存在, referrer:" + referrer);
					return ;
				}	
				if (null != user.getReferrer() && user.getInviteCode().equals(user.getReferrer())) {					
					log.infoLog("加息券", "推荐人和邀请人一致, userId: "+user.getUserId()+" referrer:" + referrer);
					return;				
				}
			}

			
			/*if (userDao.getAvlidUserByMobileNumber(user.getMobileNumber()) > 0) {
				log.infoLog("加息券", "推荐人 为内部人员:" + user.getMobileNumber());
				return;

			}*/
			
			
			Date firstInvestTime = investService
					.getFirstAvilidInvestTimeByUserId(user.getUserId());
			if (firstInvestTime == null || firstInvestTime.getTime() > registTime.getTime()) {
				log.errLog("加息券", "无效邀请人，邀请人：" + user.getUserId() + ",首次投资时间"
						+ firstInvestTime + "/// 被邀请人：" + userId + ", 注册时间 ："
						+ registTime);
				return ;
			}
		
			/**
			 * TODO 如何判断是首次投资未发加息券？
			 */
			// 判断用户是否有已发送过加息券的投资记录
			int visitors = investService
					.getCountInvestByRedpacketForVisitor(userId);
			if (visitors > 0) {
				log.infoLog("加息券", "此加息券已发送过， userID : " + user.getUserId() + "mobile: " + user.getMobileNumber());
				return;
			}

			int count = 1;
			// 统计 邀请人 邀请的好友投资成功且以发过加息券的数量
			count += userDao.getCountByReffer(user.getMobileNumber(), firstInvestTime)
					+ userDao.getCountByReffer(user.getInviteCode(), firstInvestTime);
			int redpackets = redPacketDao.getRedPacketByRuleId(user.getMobileNumber());

			
			
			if (redpackets >= count) {
				log.infoLog("加息券", "此加息券已发送过, 加息券数量大于邀请人数量， userId:" + user.getUserId() + "mobile: " + user.getMobileNumber());
				return;
			}
			if (count == 1) {
				rate = 0.01;
			} else if (count == 2) {
				rate = 0.012;
			} else if (count == 3) {
				rate = 0.015;
			} else if (count == 4) {
				rate = 0.02;
			} else if (count == 5) {
				rate = 0.025;
			} else if (count >= 6) {
				rate = 0.03;
			}
			Rule rule = redPacketDao.getRuleById(4);
			RedPacket redpacket = new RedPacket();
			redpacket.setName(rule.getRuleName());
			redpacket.setRate(rate);
			redpacket.setUserId(user.getUserId());
			redpacket.setMobileNumber(user.getMobileNumber());
			Date d = new Date();
			redpacket.setCreateTime(d);
			redpacket.setDeadLine(DateUtil.addMonth(d, 1));
			redpacket.setSendStatus("unused");
			redpacket.setType("rate");
			redpacket.setRuleId(4);
			redpacket.setUsageDetail("invest");
			redpacket.setInvestMoney(100.00);
			redpacket.setUsageRule("投资可用");

			log.infoLog("加息券", redpacket.toString());

			redPacketDao.insert(redpacket);
			informationService.sendInformation(
					redpacket.getUserId(),
					"活动奖励",
					"尊敬的短融网用户您好，恭喜您在【"
							+ rule.getRuleName()
							+ "】活动中获得一张加息券，在投资时使用可享【额外加息"
							+ redpacket.getRate()
							* 100
							+ "%】，有效期至"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(redpacket.getDeadLine())
							+ "，现已发放到您的奖励中心，赶快投资使用吧！");
			log.infoLog("邀请加息", "用户ID:" + user.getUserId() + "加息券已发送, 加息："
					+ rate + "邀请人数：" + count);

		}

	}


	@Override
	public void updateExpired() {
		redPacketDao.updateExpired();
		
	}

	
	/**
	 * 通过红包表中的手机号
	 * 
	 * @param redPacket
	 */
	@Override
	public void updateDetailMobileNumber(String oldNumber ,String newNumber) {
		redPacketDao.updateDetailMobileNumber(oldNumber ,newNumber);
	}

	/**
	 * 修改分享表中的手机号
	 * 
	 * @param redPacket
	 */
	@Override
	public void updateShareMobileNumber(String oldNumber, String newNumber) {
		redPacketDao.updateShareMobileNumber(oldNumber ,newNumber);
	}
	
	
	@Override
	public void userRedPacket(Invest invest, String type) throws RedPacketUseException{

		if (invest == null || invest.getRedpacketId() == 0)
			return;

		RedPacket redpacket = redPacketDao.get(invest.getRedpacketId());
		if (redpacket == null) {
			log.errLog("加息券使用失败", "用户ID:" + invest.getInvestUserID() + ",项目Id："
					+ invest.getLoanId() + " 加息券未找到");
			return;
		}
		if (type.equals("success")) {
			redpacket.setSendStatus("used");
			redpacket.setUseTime(new Date());
			redPacketDao.update(redpacket);
			log.infoLog("加息券使用成功", "用户ID:" + invest.getInvestUserID()
					+ "加息券已使用成功,项目Id：" + invest.getLoanId() + " 加息券ID：+"
					+ redpacket.getId() + "加息：" + redpacket.getRate());

		}
		if (type.equals("fail")) {

			redpacket.setSendStatus("unused");
			redPacketDao.update(redpacket);
			log.infoLog("加息券使用失败", "用户ID:" + invest.getInvestUserID()
					+ "投资失败,项目Id：" + invest.getLoanId() + " 加息券ID：+"
					+ redpacket.getId() + "加息：" + redpacket.getRate()
					+ "加息券已返还");
		}

	}

	@Override
	public void create4Birthday(User user) {
		if (null == user.getUserId() || null == user.getMobileNumber()) {
			log.errLog("创建红包create4Birthday", "参数为空");
			return;
		}
		int ruleId = 186;
		Rule rule = redPacketDao.getRuleById(ruleId);
		if (rule == null) {
			log.errLog("创建红包create4Birthday", "rule为空");
			return;
		}
		/**
		 *  活动名称：生日福利 
			红包总额：66元红包+1%加息券（66元：单笔投资≥10000元，期限≥5个月项目），有效期自生日当天起1个月（30天） 
			红包个数：2个 
			红包分配方式： 
			66元 门槛10000元 30天有效期 
			1%加息 门槛10000元 15天有效期
		 */
		List<RedPacket> list = new ArrayList<>();
		list.add(createRedpacket(user.getUserId(), ruleId, user.getMobileNumber(),rule.getRuleName(), 66, 10000, 1, 30,5));
		list.add(createRedpacket1(user.getUserId(), ruleId, user.getMobileNumber(),rule.getRuleName(), 1, 0, 0, 30,0));
		redPacketDao.batchInsert(list);

		try {
			String content = "亲爱的"+user.getRealname() +"，生日快乐！在这个特别的日子里，送你66元红包+1%加息券，感谢您的陪伴与支持，我们会一如既往的努力，为您提供更安全，透明的理财平台！戳 t.cn/RZeW3Q1 使用，回TD退订";
			content = StringUtils.replace(content, "#{name}",user.getRealname()+"");
			SMSSend.batchSend1(user.getMobileNumber(), content);
			Sms sms = new Sms();
			String id = ShortUrlGenerator.shortUrl(IdGenerator.randomUUID())
					+ ShortUrlGenerator.shortUrl(IdGenerator.randomUUID());
			sms.setId(id);
			sms.setUserId(user.getUserId());
			sms.setMobileNumber(user.getMobileNumber());
			sms.setContent(content);
			sms.setTime(new Date());
			sms.setType(SmsConstants.REDPACKET);
			smsDao.insert(sms);
			informationService.sendInformation(
					user.getUserId(),
					"生日礼物到账通知",
					"生日快乐！66元红包+1%加息券已发送至您账户中，请尽快使用。");
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("发送验证码错误", e);
		}
	}

	private RedPacket createRedpacket1(String userId, int ruleId,
			String mobileNumber, String ruleName, double money,
			double investMoney, int uesLoanType, int days,int investCycle) {
		RedPacket redpacket = new RedPacket();
		redpacket.setUserId(userId);
		redpacket.setRuleId(ruleId);
		redpacket.setName(ruleName);
		redpacket.setMobileNumber(mobileNumber);
		Date d = new Date();
		redpacket.setCreateTime(d);
		redpacket.setDeadLine(DateUtil.addDay(d, days));
		redpacket.setUsageDetail("invest");
		redpacket.setUsageRule("投资可用");
		redpacket.setSendStatus("unused");
		redpacket.setRate(0.01);
		redpacket.setType("rate");

		redpacket.setMoney(money);
		redpacket.setInvestMoney(investMoney);
		redpacket.setUseLoanType(uesLoanType+"");
		redpacket.setInvestCycle(investCycle);
		return redpacket;
	}
	
	
	private RedPacket createRedpacket(String userId, int ruleId,
			String mobileNumber, String ruleName, double money,
			double investMoney, int uesLoanType, int days,int investCycle) {
		RedPacket redpacket = new RedPacket();
		redpacket.setUserId(userId);
		redpacket.setRuleId(ruleId);
		redpacket.setName(ruleName);
		redpacket.setMobileNumber(mobileNumber);
		Date d = new Date();
		redpacket.setCreateTime(d);
		redpacket.setDeadLine(DateUtil.addDay(d, days));
		redpacket.setUsageDetail("invest");
		redpacket.setUsageRule("投资可用");
		redpacket.setSendStatus("unused");
		redpacket.setRate(0);
		redpacket.setType("money");

		redpacket.setMoney(money);
		redpacket.setInvestMoney(investMoney);
		redpacket.setUseLoanType(uesLoanType+"");
		redpacket.setInvestCycle(investCycle);
		return redpacket;
	}
}
