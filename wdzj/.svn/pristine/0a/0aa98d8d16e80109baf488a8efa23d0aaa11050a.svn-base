package com.duanrong.business.push.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.duanrong.business.push.dao.PushInvestorDao;
import com.duanrong.business.push.model.PushInvestor;

@Repository
public class PushInvestorDaoImpl extends SqlSessionDaoSupport implements PushInvestorDao {

	private String namespace = "com.duanrong.thirdPartyInterface.mapper.PushInvestorMapper.";
	
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
   
	@Override
	public int insert(List<PushInvestor> list) {
        
		return this.getSqlSession().insert(namespace + "insert",list);
	}

}
