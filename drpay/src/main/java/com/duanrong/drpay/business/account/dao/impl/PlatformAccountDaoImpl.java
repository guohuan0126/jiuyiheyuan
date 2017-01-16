package com.duanrong.drpay.business.account.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.dao.PlatformAccountDao;
import com.duanrong.drpay.business.account.model.PlatformAccount;
import com.duanrong.drpay.business.account.model.PlatformBill;

@Repository
public class PlatformAccountDaoImpl extends BaseDaoImpl<PlatformAccount>
		implements PlatformAccountDao {

	public PlatformAccountDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.account.mapper.PlatformAccountMapper");
	}

	@Override
	public PlatformAccount get(PlatformAccountEnum platformAccountType) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".get", platformAccountType.name());
	}
	
	@Override
	public PlatformAccount getWithLock(PlatformAccountEnum platformAccountType) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithLock", platformAccountType.name());
	}

	@Override
	public void insertBill(PlatformBill platformBill) {
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