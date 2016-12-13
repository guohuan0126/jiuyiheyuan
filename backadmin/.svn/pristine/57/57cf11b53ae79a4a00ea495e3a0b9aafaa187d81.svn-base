package com.duanrong.business.netloaneye.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.netloaneye.dao.PushInfoRowDao;
import com.duanrong.business.netloaneye.model.PushInfoRow;



@Repository
public class PushInfoRowDaoImpl extends BaseDaoImpl<PushInfoRow> implements PushInfoRowDao {

	public PushInfoRowDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.netloaneye.mapper.PushInfoMapper");
	}
	
	
	@Override
	public List<PushInfoRow> getPushInvestList(String id) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getPushInvestList",id);
	}


	@Override
	public List<PushInfoRow> getPushInvestPersonByUserId(Map<String, Object> params) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getPushInvestPersonByUserId",params);
	}

}
