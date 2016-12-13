package com.duanrong.business.push.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.duanrong.business.push.model.PushInfoRow;
import com.duanrong.business.push.dao.PushInfoRowDao;


@Repository
public class PushInfoRowDaoImpl extends SqlSessionDaoSupport implements PushInfoRowDao {

	private String namespace = "com.duanrong.thirdPartyInterface.mapper.PushInfoRowMapper.";
	 
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Override
	public List<PushInfoRow> getPushInvestList() {
		return this.getSqlSession().selectList(namespace + "getPushInvestList");
	}

}
