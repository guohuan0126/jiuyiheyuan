package com.duanrong.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.duanrong.business.dao.PushLoanDao;
import com.duanrong.business.model.PushLoan;

@Repository
public class PushLoanDaoImpl extends SqlSessionDaoSupport implements PushLoanDao {

	private static Logger logger = Logger.getLogger(PushLoanDao.class);
	
	private String namespace = "com.duanrong.business.mapper.PushLoanMapper.";
	
	
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public int updateStatus(Integer pushType,String status,String id) {
		try{

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pushType", pushType);
			map.put("status", status);
			map.put("id", id);
			logger.info("更新项目sql参数："+map.toString());
			return this.getSqlSession().update(namespace + "updateStatus", map);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		

	}

	@Override
	public List<PushLoan> getPushLoanStatusList() {
		
		return this.getSqlSession().selectList(namespace + "getPushLoanStatusList");
	}
	


}
