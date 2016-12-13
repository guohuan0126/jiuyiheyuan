package com.duanrong.business.demand.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandtreasureGuOutDao;
import com.duanrong.business.demand.model.DemandtreasureGuOut;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;

/**
 * @author xiao
 * @date 2015年7月20日下午2:19:38
 */
@Repository
public class DemandTreasureGuOutDaoImpl extends BaseDaoImpl<DemandtreasureGuOut>
		implements DemandtreasureGuOutDao {

	public DemandTreasureGuOutDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandtreasureGuOutMapper"); // 设置命名空间
	}

	@Override
	public PageInfo<DemandtreasureGuOut> readPageInfo(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandtreasureGuOut> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo");
		PageInfo<DemandtreasureGuOut> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public Double readGuCount() {
		return getSqlSession().selectOne(getMapperNameSpace() + ".findGuCount");
	}
}
