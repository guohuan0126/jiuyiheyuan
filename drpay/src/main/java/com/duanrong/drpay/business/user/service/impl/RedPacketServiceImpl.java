package com.duanrong.drpay.business.user.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.DateUtil;
import util.Log;
import base.exception.TradeException;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.service.InvestService;
import com.duanrong.drpay.business.user.dao.RedPacketDao;
import com.duanrong.drpay.business.user.dao.UserDao;
import com.duanrong.drpay.business.user.model.RedPacket;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.RedPacketService;
import com.duanrong.drpay.business.user.service.UserService;

/**
 * @author xiao
 * @date 2015年1月22日 下午3:25:03
 */
@Service
public class RedPacketServiceImpl implements RedPacketService {

	@Resource
	RedPacketDao redPacketDao;

	@Resource
	UserService userService;

	@Resource
	InvestService investService;

	@Resource
	UserDao userDao;

	@Resource
	Log log;

	@Override
	public void verifyRedPacket(Invest invest) throws TradeException {
		if (invest == null || invest.getRedpacketId() == 0)
			throw new TradeException("红包券使用失败");
		String userId = invest.getInvestUserID();
		User user = userDao.get(userId);
		if (userId == null || user == null) {
			throw new TradeException("红包券使用失败");
		}
		String mobileNumber = user.getMobileNumber();
		if (mobileNumber == null)
			throw new TradeException("红包券使用失败");
		RedPacket redpacket = new RedPacket();
		redpacket.setMobileNumber(mobileNumber);
		redpacket.setId(invest.getRedpacketId());
		List<RedPacket> list = redPacketDao.getRedPacketDetails(redpacket);

		if (list != null && list.size() == 1) {
			redpacket = list.get(0);
		} else {
			throw new TradeException("红包券使用失败");
		}
		if (redpacket != null) {
			//判断新手标是否可用
			if ("是".equals(invest.getLoan().getNewbieEnjoy())
					&& redpacket.getUseLoanType() == 1) {  //useLoanType : 1, 新手标不可使用 3，APP专享且新手标不可用
				log.errLog("加息券使用失败", "用户ID：" + invest.getInvestUserID() + ",项目ID："
						+ invest.getLoanId() + "redpacketId:" + redpacket.getId()
						+ "此加息券新手标不可使用");
				throw new TradeException("新手标不可使用该红包券");
			}
			if ("是".equals(invest.getLoan().getNewbieEnjoy())
					&& redpacket.getUseLoanType() == 3) {  //useLoanType : 1, 新手标不可使用 3，APP专享且新手标不可用
				log.errLog("加息券使用失败", "用户ID：" + invest.getInvestUserID() + ",项目ID："
						+ invest.getLoanId() + "redpacketId:" + redpacket.getId()
						+ "此红包券APP投资专享且新手标不可用");
				throw new TradeException("此红包券APP投资专享且新手标不可用");
			}
			//判断来源时候APP
			if (redpacket.getUseLoanType() == 2
					&&invest.getUserSource()!=null&&!invest.getUserSource().contains("ios")&&!invest.getUserSource().contains("android")){  //useLoanType : 2, APP专享 3，APP专享且新手标不可用
				log.errLog("加息券使用失败", "用户ID：" + invest.getInvestUserID() + ",项目ID："
						+ invest.getLoanId() + "redpacketId:" + redpacket.getId()
						+ "此加息券APP专享");
				throw new TradeException("此红包券APP投资专享");
			}
			if (redpacket.getUseLoanType() == 3
					&&invest.getUserSource()!=null&&!invest.getUserSource().contains("ios")&&!invest.getUserSource().contains("android")){  //useLoanType : 2, APP专享 3，APP专享且新手标不可用
				log.errLog("加息券使用失败", "用户ID：" + invest.getInvestUserID() + ",项目ID："
						+ invest.getLoanId() + "redpacketId:" + redpacket.getId()
						+ "此红包券APP投资专享且新手标不可用");
				throw new TradeException("此红包券APP投资专享且新手标不可用");
			}
			//投资周期限制
			if (redpacket.getInvestCycle() > 0){  
				if("天".equals(invest.getLoan().getOperationType())&&redpacket.getInvestCycle()*30>invest.getLoan().getDay()){
					throw new TradeException("此红包券仅限"+redpacket.getInvestCycle()+"个月及以上标的使用");
				}
				if( "月".equals(invest.getLoan().getOperationType())&&redpacket.getInvestCycle()>invest.getLoan().getDeadline()){
					throw new TradeException("此红包券仅限"+redpacket.getInvestCycle()+"个月及以上标的使用");
				}
			}

			if (!"unused".equals(redpacket.getSendStatus())) {
				log.errLog("加息券使用失败", "状态不是未使用" + redpacket.toString());
				String status= "被使用";
				if("expired".equals(redpacket.getSendStatus())){
					status = "过期";
				}
				throw new TradeException("您选择的红包券已"+status);
			}

			if (!StringUtils.equals("invest", redpacket.getUsageDetail())) {
				log.errLog("加息券使用失败", "投资不可使用" + redpacket.toString());
				throw new TradeException("您选择的红包券已被使用");
			}
			if (invest.getMoney() < redpacket.getInvestMoney()) {
				log.errLog(
						"加息券使用失败",
						"投资金额小于限制金额;investID:" + invest.getId() + "投资金额："
								+ invest.getMoney() + ";限制金额："
								+ redpacket.getInvestMoney());
				DecimalFormat df2 = new DecimalFormat("###");
				throw new TradeException("投资金额满"
						+ df2.format(redpacket.getInvestMoney()) + "元方可使用该红包券");
			}
			if (redpacket.getInvestRate() > 0
					&& invest.getRate() > redpacket.getInvestRate()) {
				log.errLog(
						"加息券使用失败",
						"投资利率大于限制利率;investID:" + invest.getId() + "投资利率："
								+ invest.getRate() + ";限制利率："
								+ redpacket.getInvestRate());
				throw new TradeException("投资项目利率不高于"
						+ redpacket.getInvestRate() * 100 + "%方可使用红包券");
			}
			if (DateUtil.calculateIntervalDays1(new Date(),
					redpacket.getDeadLine()) < 0) {
				redpacket.setSendStatus("expired");
				redPacketDao.update(redpacket);
				throw new TradeException("您选择的红包券已过期");
			}
			if(redpacket.getRuleId()==31){
				log.infoLog("使用纸质红包券", "userId:"+userId+",红包ID："+redpacket.getId());
				long investRecord = investService.getInvestSuccessNotNewRecordByUserId(userId);
				if (investRecord != 0) {
					log.errLog("加息券使用失败", "老用户不能使用此类红包券userId:"+userId+"red"+ redpacket.toString());
					throw new TradeException("老用户不能使用此类红包券");
				}
			}
		} else {
			throw new TradeException("红包券使用失败");
		}
	}
	
	@Override
	public void userRedPacket(Invest invest, String type) throws TradeException{

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
	
	/**
	 * 查询对应条件的明细列表
	 * 
	 */
	@Override
	public List<RedPacket> operateRedPacketDetails(RedPacket redPacket) {
		return redPacketDao.getRedPacketDetails(redPacket);
	}

	/**
	 * 通过ID修改红包对象
	 * 
	 * @param redPacket
	 */
	@Override
	public void updateRedPacket(RedPacket redPacket) {
		redPacketDao.update(redPacket);
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


}
