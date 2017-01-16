package com.duanrong.business.recharge.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.recharge.dao.RechargeDao;
import com.duanrong.business.recharge.model.Recharge;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.user.UserConstants;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.jedis.DRJedisDLock;

@Service
public class RechargeServiceImpl implements RechargeService {

	@Resource
	RechargeDao rechargeDao;

	@Resource
	UserAccountService userAccountService;
	
	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	Log log;

	@Override
	public List<Recharge> getByCondition(Recharge recharge) {
		return rechargeDao.getByCondition(recharge);
	}

	@Override
	public Double getTotalRecharge(Recharge recharge) {
		Double d=0d;
		d= rechargeDao.getTotalRecharge(recharge);
		if( d!=null) return d ; 
		else return (double) 0;
	}

	@Override
	public PageData<Recharge> findPaging(int pageNo, int pageSize,
			Recharge recharge) {
		return rechargeDao.findPaging(pageNo, pageSize, recharge);
	}

	@Override
	public Long getRechargeCount(Recharge recharge) {
		return rechargeDao.getCount(recharge);
	}

	@Override
	public void insert(Recharge recharge) {
		rechargeDao.insert(recharge);
	}

	@Override
	public Recharge get(String id) {
		return rechargeDao.get(id);
	}

	
	@Override
	public void rechargeSuccess(String rechargeId,String type) {
		/*if (DRJedisDLock.getDLock("recharge" + rechargeId, rechargeId)) {*/
			try {
				Recharge recharge = rechargeDao.getWithLock(rechargeId);
				if (StringUtils.equals(recharge.getStatus(),
						UserConstants.RechargeStatus.WAIT_PAY)) {
					// 修改状态为成功
					recharge.setStatus(UserConstants.RechargeStatus.SUCCESS);
					// 设置充值成功时间
					recharge.setSuccessTime(new Date());
					//充值方式
					recharge.setType(type);
					String userId = recharge.getUserId();
					Double money = recharge.getActualMoney();
					String operatorInfo = "充值成功";

					rechargeDao.update(recharge);
					// 记录充值手续费
					try {
						String rechargeWay = recharge.getRechargeWay();
						double systemMoney = 0;
						if (MyStringUtils.equalsIgnoreCaseAnyString(
								rechargeWay, "pc", null)) {
							systemMoney = ArithUtil.round(
									recharge.getActualMoney() * 0.002, 2);
						} else {
							systemMoney = ArithUtil.round(
									recharge.getActualMoney() * 0.003, 2);
						}				
						platformAccountService.transferOut(systemMoney,
								BusinessEnum.recharge_fee, "充值手续费", rechargeId);
					} catch (Exception ex) {
						log.errLog("充值，系统账户操作", ex);
					}			
					// FIXME 新账户充值
					userAccountService.transferIn(userId, money,
							BusinessEnum.recharge, operatorInfo, operatorInfo,
							rechargeId);
				}
			} catch (Exception ex) {
				log.errLog("处理充值失败", ex);
			} /*finally {
				DRJedisDLock.releaseDLock("recharge" + rechargeId, rechargeId);
			}
		}*/
	}
	
	@Override
	public void update(Recharge recharge) {
		rechargeDao.update(recharge);
	}

	@Override
	public PageInfo<Recharge> getAllRecharge(int pageNo, int pageSize,
			Recharge recharge) {
		return rechargeDao.pageLite(pageNo, pageSize, recharge);
	}

	@Override
	public Double getTotalFee(Recharge recharge) {
		Double d=0d;
		d= rechargeDao.getTotalFee(recharge);
		if( d!=null) return d ; 
		else return (double) 0;
	}

	@Override
	public double getRechargeMoneyPerDay(Map<String, Object> params) {
		return rechargeDao.getRechargeMoneyPerDay(params);
	}

	@Override
	public List<Recharge> getRechargeNum(Map map) {
		return rechargeDao.getRechargeNum(map);
	}

	@Override
	public void save(Recharge recharge) {
		rechargeDao.save(recharge);
	}

	@Override
	public List<Recharge> getRechargeByDateAndStatus(Map<String, Object> map) {
		return rechargeDao.getRechargeByDateAndStatus(map);
	}
}