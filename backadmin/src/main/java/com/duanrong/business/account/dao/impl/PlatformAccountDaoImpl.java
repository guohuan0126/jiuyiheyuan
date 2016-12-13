package com.duanrong.business.account.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.dao.PlatformAccountDao;
import com.duanrong.business.account.model.PlatformAccount;
import com.duanrong.business.account.model.PlatformBill;

@Repository
public class PlatformAccountDaoImpl extends BaseDaoImpl<PlatformAccount>
		implements PlatformAccountDao {

	public PlatformAccountDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.account.mapper.PlatformAccountMapper");
	}

	@Override
	public PlatformAccount get() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".get");
	}
	
	@Override
	public PlatformAccount getWithLock() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithLock");
	}


	@Override
	public void insert(PlatformBill platformBill) {
		this.getSqlSession()
				.update(this.getMapperNameSpace() + ".insertPlatformBill",
						platformBill);
	}

	@Override
	public PageInfo<PlatformBill> pageLite(int pageNo, int pageSize,
			PlatformBill platformBill) {
		PageHelper.startPage(pageNo, pageSize);
		List<PlatformBill> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageLite", platformBill);
		PageInfo<PlatformBill> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}
