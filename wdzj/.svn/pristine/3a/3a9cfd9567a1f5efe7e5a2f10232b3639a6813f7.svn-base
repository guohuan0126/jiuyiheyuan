package com.duanrong.business.push.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.duanrong.business.push.dao.PushLoanDao;
import com.duanrong.business.push.model.PushLoan;

@Repository
public class PushLoanDaoImpl extends SqlSessionDaoSupport implements PushLoanDao {

	private String namespace = "com.duanrong.thirdPartyInterface.mapper.PushLoanMapper.";
	
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public int updateStatus(List<String> ids) {

		return this.getSqlSession().update(namespace + "updateStatus", ids);
	}

	@Override
	public List<PushLoan> getPushLoanStatusList() {
		
		return this.getSqlSession().selectList(namespace + "getPushLoanStatusList");
	}
	


}
