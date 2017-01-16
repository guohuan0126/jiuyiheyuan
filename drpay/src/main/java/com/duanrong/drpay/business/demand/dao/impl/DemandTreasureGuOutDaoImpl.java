package com.duanrong.drpay.business.demand.dao.impl;

import org.springframework.stereotype.Repository;

import com.duanrong.drpay.business.demand.dao.DemandtreasureGuOutDao;
import com.duanrong.drpay.business.demand.model.DemandtreasureGuOut;

import base.dao.impl.BaseDaoImpl;

/**
 * @author xiao
 * @date 2015年7月20日下午2:19:38
 */
@Repository
public class DemandTreasureGuOutDaoImpl extends BaseDaoImpl<DemandtreasureGuOut>
		implements DemandtreasureGuOutDao {

	public DemandTreasureGuOutDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.demand.mapper.DemandtreasureGuOutMapper"); // 设置命名空间
	}

	
	public Double getGuCount() {
		return getSqlSession().selectOne(getMapperNameSpace() + ".findGuCount");
	}
}
