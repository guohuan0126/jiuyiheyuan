package com.duanrong.business.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import base.model.PageData;

import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.Rule;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.RedPacketService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.util.DateUtil;

/**
 * @author xiao
 * @date 2015年1月22日 下午3:25:03
 */
@Service
public class RedPacketServiceImpl implements RedPacketService {

	@Autowired
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

	public PageData<RedPacket> readPaging(int pageNo, int pageSize,
			RedPacket redPacket) {
		return redPacketDao.findPaging(pageNo, pageSize, redPacket);
	}

	public Map<String,String> readRedPacketByMobile(String mobile) {
		return redPacketDao.getRedPacketByMobile(mobile);
	}
	public void updatePacketByMobile(Map<String,String> mobilemap) {
		 redPacketDao.updatePacketByMobile(mobilemap);
	}
	public void delRedPacketByMobile(String mobile) {
		redPacketDao.delRedPacketByMobile(mobile);
	}
	
	@Override
	public void create(String userId) {

		User u = userService.read(userId);
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

			if (userDao.getAvlidUserByMobileNumber(user.getMobileNumber()) > 0) {
				log.infoLog("加息券", "推荐人 为内部人员:" + user.getMobileNumber());
				return;

			}
			Date firstInvestTime = investService
					.readFirstAvilidInvestTimeByUserId(user.getUserId());
			if (firstInvestTime.getTime() > registTime.getTime()) {
				log.errLog("加息券", "无效邀请人，邀请人：" + user.getUserId() + ",首次投资时间"
						+ firstInvestTime + "/// 被邀请人：" + userId + ", 注册时间 ："
						+ registTime);
			}
			/**
			 * TODO 如何判断是首次投资未发加息券？
			 */
			// 判断用户是否有已发送过加息券的投资记录
			int visitors = investService
					.readCountInvestByRedpacketForVisitor(userId);
			if (visitors > 0) {
				log.infoLog("加息券", "此加息券已发送过， userID : " + user.getUserId() + "mobile: " + user.getMobileNumber());
				return;
			}

			int count = 1;
			// 统计 邀请人 邀请的好友投资成功且以发过加息券的数量
			count += userDao.getCountByReffer(user.getMobileNumber(), firstInvestTime)
					+ userDao.getCountByReffer(user.getInviteCode(), firstInvestTime);
			int redpackets = redPacketDao.getRedPacketByRuleId(user
					.getMobileNumber());

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

}
