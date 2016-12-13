package com.duanrong.business.loan.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.loan.dao.LoanVipDao;
import com.duanrong.business.loan.model.LoanVip;

@Repository
public class LoanVipDaoImpl extends BaseDaoImpl<LoanVip> implements
		LoanVipDao {

	public LoanVipDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.loan.mapper.LoanVipMapper");
	}

	@Override
	public PageInfo<LoanVip> pageInfo(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<LoanVip> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo");
		PageInfo<LoanVip> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}
