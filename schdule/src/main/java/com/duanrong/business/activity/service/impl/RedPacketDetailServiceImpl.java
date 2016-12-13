package com.duanrong.business.activity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.activity.dao.RedPacketDetailDao;
import com.duanrong.business.activity.model.RedPacketDetail;
import com.duanrong.business.activity.service.RedPacketDetailService;
import com.duanrong.business.riskfund.model.Riskfund;
import com.duanrong.business.riskfund.model.Riskfunddetail;

@Service
public class RedPacketDetailServiceImpl implements RedPacketDetailService {
    
	@Autowired
	public RedPacketDetailDao detailDao;
	
	@Override
	public void add(RedPacketDetail detail) {
		this.detailDao.insert(detail);
		
	}

	@Override
	public void update(RedPacketDetail detail) {
		this.detailDao.update(detail);
		
	}

	@Override
	public void delete(Integer id) {
		this.detailDao.delete(id);
		
	}

	@Override
	public PageInfo<RedPacketDetail> pageLite(int pageNo, int pageSize,
			Object detail) {
		
		return this.detailDao.pageLite(pageNo, pageSize, (RedPacketDetail) detail);
	}

	@Override
	public RedPacketDetail get(Integer id) {
		// TODO Auto-generated method stub
		return this.detailDao.get(id);
	}
	@Override
	public void saveDetail(List<RedPacketDetail> rf) {
		for(RedPacketDetail d:rf){
			detailDao.insert(d);
		}
	}

	@Override
	public void del(RedPacketDetail detail) {
		detailDao.del(detail);
	}
}
