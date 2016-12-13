package com.duanrong.business.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.duanrong.business.dao.PushTransactionDao;
import com.duanrong.business.model.PushTransaction;

@Repository
public class PushTransactionDaoImpl extends SqlSessionDaoSupport implements PushTransactionDao {

	private String namespace = "com.duanrong.business.mapper.PushTransactionMapper.";
	
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Override
	public int insert(List<PushTransaction> recordList) {

		return this.getSqlSession().insert(namespace + "insert", recordList);
	}


}
