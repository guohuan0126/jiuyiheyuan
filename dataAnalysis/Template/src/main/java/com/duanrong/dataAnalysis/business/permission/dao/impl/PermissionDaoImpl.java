package com.duanrong.dataAnalysis.business.permission.dao.impl;



import org.springframework.stereotype.Repository;

import com.duanrong.dataAnalysis.business.permission.dao.PermissionDao;

import base.dao.impl.BaseDaoImpl;


@Repository
public class PermissionDaoImpl extends BaseDaoImpl<String> implements PermissionDao {
	public PermissionDaoImpl() {
		
		this.setMapperNameSpace("com.duanrong.business.dataAnalysis.permission.mapper.PermissionMapper"); // 设置命名空间
	}
	//根据name查取超级密码
	@Override
	public String getSpwd(String key){
		return this.getSqlSession().selectOne("getSpwd",key);
	}
	//查询ip白名单
	@Override
	public String getIps(String id) {
		return this.getSqlSession().selectOne("getIps",id);
	}
	
	
	
}
