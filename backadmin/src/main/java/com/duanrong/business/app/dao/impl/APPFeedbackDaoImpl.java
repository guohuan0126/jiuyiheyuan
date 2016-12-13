package com.duanrong.business.app.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageInfo;

import com.duanrong.business.app.dao.AppFeedbackDao;
import com.duanrong.business.app.model.APP;
import com.duanrong.business.app.model.AppFeedback;

@Repository
public class APPFeedbackDaoImpl extends BaseDaoImpl<AppFeedback> implements
		AppFeedbackDao {

	public APPFeedbackDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.app.mapper.AppFeedback");
	}

	@Override
	public List<AppFeedback> find(AppFeedback entiy) {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".pageLite", entiy);
	}

	@Override
	public void updateChangeHandleType(String id, String handleType) {
		HashMap<String, Object> params=new HashMap<>();
		params.put("id", id);
		params.put("handleType", handleType);
		this.getSqlSession().update(this.getMapperNameSpace()+".updateChangeHandleType",params);
		
	}	
	
	
}
