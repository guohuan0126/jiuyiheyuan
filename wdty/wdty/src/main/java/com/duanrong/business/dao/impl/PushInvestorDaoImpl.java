package com.duanrong.business.dao.impl;

import java.util.List;


import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.dao.PushInvestorDao;
import com.duanrong.business.model.PushInvestor;
@Transactional
@Repository
public class PushInvestorDaoImpl extends SqlSessionDaoSupport implements PushInvestorDao {

	private String namespace = "com.duanrong.business.mapper.PushInvestorMapper.";
	
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
   
	@Override
	public int insert(List<PushInvestor> list) {
        try{
        	return this.getSqlSession().insert(namespace + "insert",list);
        }catch(Exception e){
        	e.printStackTrace();
        	return 0;
        }
		
	}

}
