package com.duanrong.business.platformtransfer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.platformtransfer.dao.PlatformTransferDao;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-3 上午10:19:29
 * @Description : NewAdmin com.duanrong.business.platformtransfer.dao.impl
 *              PlatformTransferDaoImpl.java
 * 
 */
@Repository
public class PlatformTransferDaoImpl extends BaseDaoImpl<PlatformTransfer>
		implements PlatformTransferDao {

	public PlatformTransferDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.platformtransfer.mapper.PlatformTransferMapper");
	}

	@Override
	public List<PlatformTransfer> getPlatformTransferGroupCondition(
			PlatformTransfer platformTransfer) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace()
						+ ".getPlatformTransferGroupCondition",
				platformTransfer);
	}

	@Override
	public PageInfo<PlatformTransfer> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<PlatformTransfer> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<PlatformTransfer> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public void insertBatch(List<PlatformTransfer> list) {
		getSqlSession().insert(getMapperNameSpace() + ".insertBatch", list);
	}

	@Override
	public List<PlatformTransfer> getByLoanId(String loanId, String repayId, String type) {

		Map<String, Object> map = new HashMap<>();
		map.put("loanId", loanId);
		map.put("repayId", repayId);
		map.put("type", type);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByLoanId", map);
	}

}
