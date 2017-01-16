package com.duanrong.business.risk.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.exception.InsufficientBalance;

import com.duanrong.business.risk.SystemBillConstants;
import com.duanrong.business.risk.dao.SystemBillDao;
import com.duanrong.business.risk.model.SystemBill;
import com.duanrong.business.risk.service.SystemBillService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.IdGenerator;

@Service
public class SystemBillServiceImpl implements SystemBillService {

	@Resource
	SystemBillDao systemBillDao;

	@Override
	public double getBalance() {
		double in = getSumByType(SystemBillConstants.Type.IN);
		double out = getSumByType(SystemBillConstants.Type.OUT);
		return ArithUtil.sub(in, out);
	}

	@Override
	public void transferOut(double money, String reason, String detail)
			throws InsufficientBalance {
		SystemBill sbLastest = getLastestBill();

		SystemBill systemBill = new SystemBill();

		if (sbLastest == null) {
			throw new InsufficientBalance(
					"sytem_bill last bill is null .transfer out money:");
		} else {
			String type = SystemBillConstants.Type.OUT;
			systemBill(money, type, reason, detail, sbLastest, systemBill);
			systemBill.setSeqNum(sbLastest.getSeqNum() + 1);
			systemBillDao.insert(systemBill);
		}

	}

	@Override
	public void transferInto(double money, String reason, String detail) {
		SystemBill sbLastest = getLastestBill();
		SystemBill systemBill = new SystemBill();
		if (sbLastest == null) {
			// 第一条数据
			systemBill.setSeqNum(1L);
		} else {
			systemBill.setSeqNum(sbLastest.getSeqNum() + 1);
		}
		String type = SystemBillConstants.Type.IN;
		systemBill(money, type, reason, detail, sbLastest, systemBill);
		systemBillDao.insert(systemBill);
	}

	public void systemBill(double money, String type, String reason,
			String detail, SystemBill ibLastest, SystemBill systemBill) {
		systemBill.setId(IdGenerator.randomUUID());
		systemBill.setMoney(money);
		systemBill.setTime(new Date());
		systemBill.setDetail(detail);
		systemBill.setReason(reason);
		systemBill.setType(type);
	}

	private SystemBill getLastestBill() {
		return systemBillDao.getLastestBill();
	}

	private double getSumByType(String type) {
		return systemBillDao.getSumByType(type);
	}

	@Override
	public void transferInto(double money, String reason, String detail,
			Date date) {
		SystemBill sbLastest = getLastestBill();
		SystemBill systemBill = new SystemBill();
		if (sbLastest == null) {
			// 第一条数据
			systemBill.setSeqNum(1L);
		} else {
			systemBill.setSeqNum(sbLastest.getSeqNum() + 1);
		}
		String type = SystemBillConstants.Type.IN;
		systemBill(money, type, reason, detail, sbLastest, systemBill, date);
		systemBillDao.insert(systemBill);
	}

	private void systemBill(double money, String type, String reason,
			String detail, SystemBill sbLastest, SystemBill systemBill,
			Date date) {
		systemBill.setId(IdGenerator.randomUUID());
		systemBill.setMoney(money);
		systemBill.setTime(date);
		systemBill.setDetail(detail);
		systemBill.setReason(reason);
		systemBill.setType(type);
	}
}
