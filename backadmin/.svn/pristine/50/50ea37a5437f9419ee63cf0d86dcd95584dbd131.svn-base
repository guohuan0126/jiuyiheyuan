package com.duanrong.business.yeepay.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.User;
import com.duanrong.business.yeepay.dao.WithholdBankDao;
import com.duanrong.business.yeepay.dao.WithholdDao;
import com.duanrong.business.yeepay.model.Withhold;
import com.duanrong.business.yeepay.model.WithholdBank;
import com.duanrong.business.yeepay.service.WithholdService;

@Service
public class WithholdServiceImpl implements WithholdService {

	@Resource
	WithholdDao withholdDao;
	@Resource
	WithholdBankDao withholdBankDao;

	@Override
	public PageInfo<Withhold> readPageInfo(int pageNo, int pageSize,
			Withhold withhold) {
		PageInfo<Withhold> pageInfo = withholdDao.pageInfo(pageNo, pageSize, withhold);
		List<Withhold> withholds=pageInfo.getResults();
		List<Withhold> n = new ArrayList<>();
		for(Withhold hold : withholds){
			WithholdBank withholdBank=new WithholdBank();
			withholdBank.setUserId(hold.getUserId());
			withholdBank.setStatus(1);
			List<WithholdBank> banks = withholdBankDao.findList(withholdBank);
			hold.setBanks(banks);
			n.add(hold);
		}
		pageInfo.setResults(n);
		return pageInfo;
	}

	@Override
	public boolean withholdDel(String id) {
		Withhold hold=withholdDao.get(id);
		if(hold!=null){
			try {
				withholdBankDao.deleteByUserId(hold.getUserId());
				withholdDao.delete(id);
				return true;
			} catch (Exception e) {
				return false;
			}
		}else{
			return false;
		}
	
	}

	@Override
	public boolean insert(Withhold withhold) {
		try {
			withholdDao.insert(withhold);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
 
}
