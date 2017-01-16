package com.duanrong.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.HttpUtil;
import util.Log;
import util.RandomUtil;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.invest.model.InvestSubLoan;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserService;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.LoadConstantProterties;
import com.duanrong.newadmin.utility.LoadConstantProterties2;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.UUIDGenerator;

/**
 * 
 * @author xiao
 * 
 */
@Component
public class LoanSchdule {

	@Resource
	LoanService loanService;

	@Resource
	UserService userService;

	@Resource
	RepayService repayService;

	@Resource
	InvestService investService;
	
	@Resource
	InvestDao investDao;
	
	@Resource
	RedPacketDao redPacketDao;

	@Resource
	Log log;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 自动放款轮询 整点轮询
	 */
	@Scheduled(cron = "0 10,30,50 7-23 * * ?")
	//@Scheduled(cron="0 0/1 * * * ?")
	@Scheduled(cron = "0 0/30 * * * ?")
	public void giveMoneyToBorrowers() {
		log.infoLog("自动放款","自动放款轮询开始....."+ sdf.format(new Date()));
		List<Loan> loans = loanService.getLoanForGaveMoneyToBorrower(10);
		if (loans.isEmpty()) {
			log.infoLog("自动放款", "此次没有查询到可放款的项目");
			return;
		}
		for (Loan loan : loans) {
			try {
				String url = "/trade/giveMoneyToBorrower.do";
				JSONObject param = new JSONObject();
				param.put("loanId",loan.getId());
				JSONObject result = HttpUtil.sendDRPayPost(param, url);
				if(result.getInteger("code") != 1){
					log.errLog("自动放款失败 loanId:" + loan.getId(), result.getString("msg"),1);
				}
			} catch (Exception e) {
				log.errLog("自动放款失败 loanId:" + loan.getId(), e,1);
			}			
		
		}
		log.infoLog("自动放款","自动放款轮询结束....."+ sdf.format(new Date()));
	}

	

	/**
	 * 补息和加息奖励发送
	 */
	@Scheduled(cron = "0 30 8-23 * * ?")
//	@Scheduled(cron="0 0/1 * * * ?")
	public void sendAllowanceAndRedpacketSchedule() {
		log.infoLog("补息奖励轮询执行","轮询开始....."+ sdf.format(new Date()));
		List<InvestRedpacket> irs = investService.getInvestRedpacketList();
		if (irs.isEmpty()) {
			log.infoLog("补息奖励轮询执行", "此次没有查询到相关的InvestRedpacket记录");
			return;
		}
		for (InvestRedpacket ir : irs) {
			Invest invest = investService.getInvest(ir.getInvestId());
			if(ir.getSendAllowanceStatus() != -1){
				sendAllowance(ir,invest);
			}
			if(ir.getSendRedpacketStatus() != -1){
				sendRedpacket(ir,invest);
			}
		}
		log.infoLog("补息奖励轮询执行","轮询结束....."+ sdf.format(new Date()));
	}

	
	private void sendAllowance(InvestRedpacket investRedpacket,Invest invest){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 发放补息
		if (investRedpacket.getSendAllowanceStatus() != 1
				&& investRedpacket.getInvestAllowanceInterest() > 0) {
			Date date = investRedpacket.getGiveMoneyTime()==null?new Date():investRedpacket.getGiveMoneyTime();
			Integer dayDifference = DateUtil.dayDifference(sdf.format(invest.getTime()), sdf.format(date));// 补多少天的利息
			String remarks = "尊敬的短融网客户：您投资" + investRedpacket.getLoanName()
					+ "项目，从投资次日至放款之日共补息" + dayDifference + "天，计"
					+ investRedpacket.getInvestAllowanceInterest()+ "元，现已发放到您的账户，快去交易记录查看吧。";
			String url = "/trans/reward.do";
			JSONObject param = new JSONObject();
			param.put("userId",investRedpacket.getUserId());
			param.put("remarks",remarks);
			param.put("businessType","allowance");
			param.put("money", investRedpacket.getInvestAllowanceInterest());
			param.put("loanId", investRedpacket.getLoanId());
			param.put("requestNo", investRedpacket.getAllowanceOrder());
			param.put("info", "补息奖励");
			JSONObject result = HttpUtil.sendDRPayPost(param, url);
			if(result != null && result.getInteger("code") == 1){
				JSONObject data = result.getJSONObject("data");
				String status = data.getString("status");
				log.infoLog("补息奖励轮询执行",
						"补息奖励轮询执行  发送后：InvestRedpacket:" + investRedpacket.getId()
								+ "发补息状态：" + status);
				if("success".equals(status)){
					// 更新invest已获收益
					invest.setPaidInterest(invest.getPaidInterest()
							+ investRedpacket.getInvestAllowanceInterest());
					investRedpacket.setSendAllowanceStatus(1);
					investRedpacket.setSendAllowanceResult("发送成功");
					investService.update(invest);
					investService.updatetInvestRedpacket(investRedpacket);
				}else if("sended".equals(status)){
					investRedpacket.setSendAllowanceStatus(2);
					investRedpacket.setSendAllowanceResult("预处理成功，确认失败");
					investService.updatetInvestRedpacket(investRedpacket);
				}else if("fail".equals(status)){
					//原纪录置为失败
					investRedpacket.setSendAllowanceStatus(-1);
					investRedpacket.setSendAllowanceResult("奖励发送失败");
					investService.updatetInvestRedpacket(investRedpacket);
					//重新生成记录
					InvestRedpacket ir = new InvestRedpacket();
					ir.setAllowanceOrder(investRedpacket.getAllowanceOrder());
					//根据流水号查询记录数量
					int count = investDao.getSumInvestRedpacketByOrderId(ir);		
					ir.setId(UUIDGenerator.randomUUID());
					ir.setInvestId(investRedpacket.getId());
					ir.setLoanId(investRedpacket.getLoanId());
					ir.setAllowanceOrder(ir.getAllowanceOrder() + ++count);
					ir.setRewardMoney(0);
					ir.setInvestAllowanceInterest(invest.getInvestAllowanceInterest());
					ir.setSendAllowanceStatus(0);
					ir.setSendRedpacketStatus(0);
					ir.setCreateTime(new Date());
					ir.setUserId(invest.getInvestUserID());
					investDao.insertInvestRedpacket(ir);	
				}
			}else{
				investRedpacket.setSendAllowanceStatus(-1);
				investRedpacket.setSendAllowanceResult(result.getString("msg"));
				investService.updatetInvestRedpacket(investRedpacket);
			}
		}
	}

	/**
	 * 发放红包奖励
	 * @param ir
	 * @param invest
	 */
	private void sendRedpacket(InvestRedpacket ir,Invest invest){
		try {
			if (ir.getSendRedpacketStatus() != 1
					&& ir.getRewardMoney() > 0) {
				if (invest.getRedpacketId() <= 0) {
					ir.setSendRedpacketStatus(1);
					ir.setSendRedpacketTime(new Date());
					ir.setSendRedpacketResult("未使用红包");
					investService.updatetInvestRedpacket(ir);
					return;
				}
				RedPacket packet = redPacketDao.get(ir.getRepackedId());
				if (packet == null) {
					log.errLog("后台还款红包", "加息券不存在");
					ir.setSendRedpacketStatus(1);
					ir.setSendRedpacketTime(new Date());
					ir.setSendRedpacketResult("红包不存在");
					return;
				}
				User user = userService.get(ir.getUserId());
				if ("used".equals(packet.getSendStatus())
						&& packet.getMobileNumber().equals(
								user.getMobileNumber())) {
					
					String str = "【 " + packet.getName();
					if ("rate".equals(packet.getType())) {
						str += " 加息"
								+ ArithUtil.round(
										packet.getRate() * 100, 2)
								+ "% 】加息券";
					}
					if ("money".equals(packet.getType())) {
						str += "】现金券";
					}
					String remarks = "尊敬的短融网客户：" + ir.getLoanName()
							+ "使用" + str + "所得现金奖励已发放到您短融网账户，请您注意查收。";
					
					
					String url = "/trans/reward.do";
					JSONObject param = new JSONObject();
					param.put("userId",ir.getUserId());
					param.put("remarks",remarks);
					param.put("businessType","reward");
					param.put("money", ir.getRewardMoney());
					param.put("loanId", ir.getLoanId());
					param.put("requestNo", ir.getRepackedOrder());
					param.put("info", "红包奖励");
					JSONObject result = HttpUtil.sendDRPayPost(param, url);				
					if(result != null && result.getInteger("code") == 1){
						JSONObject data = result.getJSONObject("data");
						String status = data.getString("status");
						log.infoLog("加息奖励 发送",
								"InvestRedpacket:" + ir.getId() + "发红包状态："
										+ status);
							if ("success".equals(status)) {
								try {
									packet.setSendStatus("sended");
									packet.setSendTime(new Date());
									redPacketDao.update(packet);
									// TODO这个地方发的红包吧？？
									ir.setSendRedpacketStatus(1);
									ir.setSendRedpacketResult("发送成功");
									ir.setSendRedpacketTime(new Date());
									investService.updatetInvestRedpacket(ir);
								} catch (Exception e) {
									ir.setSendRedpacketStatus(-1);
									ir.setSendRedpacketResult(ExceptionUtils
											.getStackTrace(e));
									investService.updatetInvestRedpacket(ir);
									log.errLog("加息券发放本地处理异常", e,1);
								}
							}else if("sended".equals(status)){
								ir.setSendRedpacketStatus(2);
								ir.setSendRedpacketResult("预处理成功，确认失败");
								investService.updatetInvestRedpacket(ir);
							}else if("fail".equals(status)){
								//原纪录置为失败
								ir.setSendRedpacketStatus(-1);
								ir.setSendRedpacketResult("预处理成功，确认失败");
								investService.updatetInvestRedpacket(ir);
								//重新生成记录
								InvestRedpacket ir2 = new InvestRedpacket();
								ir2.setRepackedOrder(ir.getRepackedOrder());
								//根据流水号查询记录数量
								int count = investDao.getSumInvestRedpacketByOrderId(ir2);		
								ir2.setId(UUIDGenerator.randomUUID());
								ir2.setInvestId(ir.getId());
								ir2.setLoanId(ir.getLoanId());
								ir2.setRewardMoney(ir.getRewardMoney());
								ir2.setInvestAllowanceInterest(0);
								ir2.setSendAllowanceStatus(0);
								ir2.setSendRedpacketStatus(0);
								ir2.setRepackedOrder(ir.getRepackedOrder() + ++count);
								ir2.setCreateTime(new Date());
								ir2.setUserId(invest.getInvestUserID());
								investDao.insertInvestRedpacket(ir2);	
							}
					} else {
						ir.setSendRedpacketStatus(-1);
						ir.setSendRedpacketResult(result.getString("msg"));
						investService.updatetInvestRedpacket(ir);
					}
				} else {
					ir.setSendRedpacketStatus(1);
					ir.setSendRedpacketResult("该加息券不符合发送规则, redpacketId:"
							+ ir.getRepackedId()
							+ ", packetStatus:"
							+ packet.getSendStatus()
							+ ", rewardMoney:"
							+ ir.getRewardMoney());
					investService.updatetInvestRedpacket(ir);
				}
			} else {
				ir.setSendRedpacketStatus(1);
				ir.setSendRedpacketResult("红包金额为 0");
				investService.updatetInvestRedpacket(ir);
			}
		} catch (Exception e) {
			ir.setSendRedpacketStatus(-1);
			ir.setSendRedpacketResult(ExceptionUtils.getStackTrace(e));
			log.errLog("investId : " + ir.getInvestId() + "加息券奖励发送失败",e,1);
			investService.updatetInvestRedpacket(ir);
		} 
		
	}
	
	/**
	 * 还款调度
	 */
	//@Scheduled(cron="0 0/1 * * * ?")
	public void handleRepay() {
		log.infoLog("还款调度","自动放款轮询开始....."+ sdf.format(new Date()));
		List<Repay> repays = repayService.getWaitRepay();
		if (repays.isEmpty()) {
			log.infoLog("还款调度", "此次没有查询到可放款的项目");
			return;
		}
		for (Repay repay : repays) {
			try {
				String url = "/trade/handleRepay.do";
				JSONObject param = new JSONObject();
				param.put("id",repay.getId());
				JSONObject result = HttpUtil.sendDRPayPost(param, url);
				if(result.getInteger("code") != 1){
					log.errLog("还款调度" + repay.getId(), result.getString("msg"),1);
				}
			} catch (Exception e) {
				log.errLog("还款调度" + repay.getId(), e,1);
			}			
		
		}
		log.infoLog("还款调度","自动放款轮询结束....."+ sdf.format(new Date()));
	}
	
	
	/**
	 * 投标轮询
	 * 10分钟轮询 5分钟之前 等待支付的订单
	 */
	@Scheduled(cron="0 0/10 * * * ?")
	void investConfirm(){
		List<Invest> invests = investService.getInvestByStatus();
		for(Invest invest : invests){
			try {
				String url = "/trade/investCompensate.do";
				JSONObject param = new JSONObject();
				param.put("investId",invest.getId());
				JSONObject result = HttpUtil.sendDRPayPost(param, url);
				if(result.getInteger("code") != 1){
					log.errLog("投标轮询异常" + invest.getId(), result.getString("msg"),1);
				}
			} catch (Exception e) {
				log.errLog("还款调度" + invest.getId(), e,1);
			}		
		}
		List<InvestSubLoan> investSubloans = investService.getInvestSubloanByStatus();
		for(InvestSubLoan investSubloan : investSubloans){
			try {
				String url = "/trade/giveMoneyToBorrowerCompensate.do";
				JSONObject param = new JSONObject();
				param.put("investSubloanId", investSubloan.getInvestSubloanId());
				JSONObject result = HttpUtil.sendDRPayPost(param, url);
				if(result.getInteger("code") != 1){
					log.errLog("投标轮询异常" + investSubloan.getInvestSubloanId(), result.getString("msg"),1);
				}
			} catch (Exception e) {
				log.errLog("还款调度" + investSubloan.getInvestSubloanId(), e,1);
			}		
		}
		
	}
	
}
