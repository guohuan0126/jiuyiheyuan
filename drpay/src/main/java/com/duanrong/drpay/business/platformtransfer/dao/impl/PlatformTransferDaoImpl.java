package com.duanrong.drpay.business.platformtransfer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.platformtransfer.dao.PlatformTransferDao;
import com.duanrong.drpay.business.platformtransfer.model.PlatformTransfer;

@Repository
public class PlatformTransferDaoImpl extends BaseDaoImpl<PlatformTransfer>
		implements PlatformTransferDao {

	public PlatformTransferDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.platformtransfer.mapper.PlatformTransferMapper");
	}

	@Override
	public void insertBatch(List<PlatformTransfer> list) {
		getSqlSession().insert(getMapperNameSpace() + ".insertBatch", list);
	}

	@Override
	public List<PlatformTransfer> getPlatformTransfer(
			PlatformTransfer platformTransfer) {
		return this.getSqlSession().selectList(getMapperNameSpace() + ".getPlatformTransfer", platformTransfer);
	}
}
