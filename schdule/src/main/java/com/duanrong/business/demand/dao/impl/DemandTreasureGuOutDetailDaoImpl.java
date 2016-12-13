package com.duanrong.business.demand.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandtreasureGuOutDetailDao;
import com.duanrong.business.demand.model.DemandtreasureGuOutDetail;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;

/**
 * @author xiao
 * @date 2015年7月20日下午2:19:38
 */
@Repository
public class DemandTreasureGuOutDetailDaoImpl extends BaseDaoImpl<DemandtreasureGuOutDetail>
		implements DemandtreasureGuOutDetailDao {

	public DemandTreasureGuOutDetailDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandtreasureGuOutDetailMapper"); // 设置命名空间
	}

	@Override
	public int insertBatch(List<DemandtreasureGuOutDetail> list) {
		return getSqlSession().insert(getMapperNameSpace() + ".insertBatch", list);
	}

	
	@Override
	public PageInfo<DemandtreasureGuOutDetail> pageInfo(int pageNo, int pageSize,DemandtreasureGuOutDetail d) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureGuOutDetail> list = getSqlSession().selectList(
				getMapperNameSpace() + ".find",d);
		PageInfo<DemandtreasureGuOutDetail> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
}
