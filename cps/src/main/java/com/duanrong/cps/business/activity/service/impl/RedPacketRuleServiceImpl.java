package com.duanrong.cps.business.activity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.activity.dao.RedPacketRuleDao;
import com.duanrong.cps.business.activity.model.RedPacketRule;
import com.duanrong.cps.business.activity.service.RedPacketRuleService;

@Service
public class RedPacketRuleServiceImpl implements RedPacketRuleService {
    
	@Autowired
	public RedPacketRuleDao ruleDao;
	
	@Override
	public void add(RedPacketRule rule) {
		this.ruleDao.insert(rule);
		
	}

	@Override
	public void update(RedPacketRule rule) {
		this.ruleDao.update(rule);
		
	}

	@Override
	public void delete(Integer id) {
		this.ruleDao.delete(id);
		
	}

	@Override
	public PageInfo<RedPacketRule> pageLite(int pageNo, int pageSize,
			RedPacketRule rule) {
		// TODO Auto-generated method stub
		return this.ruleDao.pageLite(pageNo, pageSize, rule);
	}

	@Override
	public RedPacketRule get(Integer id) {
		// TODO Auto-generated method stub
		return this.ruleDao.get(id);
	}

	@Override
	public List<RedPacketRule> findAll() {
		return ruleDao.findAll();
	}
	
}
