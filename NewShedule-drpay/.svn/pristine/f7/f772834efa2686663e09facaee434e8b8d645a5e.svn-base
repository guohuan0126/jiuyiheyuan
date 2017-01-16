package com.duanrong.business.flow.dao.impl;


import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.flow.dao.FlowDao;
import com.duanrong.business.flow.model.Flow;

/**
 * @author xiao
 * @date 2015年2月3日 下午6:11:34
 */

@Repository
public class FlowDaoImpl extends BaseDaoImpl<Flow> implements FlowDao {
	
	public FlowDaoImpl(){
		
		super.setMapperNameSpace("com.duanrong.business.flow.mapper.FlowMapper");
	}

	@Override
	public int deleteFlow(int flowId) {
		
		return this.getSqlSession().update(getMapperNameSpace()+".deleteFlowById", flowId);
	}
	
	
	
}
