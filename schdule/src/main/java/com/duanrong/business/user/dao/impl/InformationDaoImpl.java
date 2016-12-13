package com.duanrong.business.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.dao.InformationDao;
import com.duanrong.business.user.model.Information;

@Repository
public class InformationDaoImpl extends BaseDaoImpl<Information> implements InformationDao {
	public InformationDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.user.mapper.InformationMapper"); // 设置命名空间
	}

	public Long getNotReadByUserId(String userId) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getNotReadByUserId", userId);
	}

	public void updateRead(String userId) {
		this.getSqlSession().selectOne(this.getMapperNameSpace() + ".updateRead", userId);		
	}

	public List<Information> getInformationByUserId(String userId) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getInformationByUserId", userId);	
	}

	@Override
	public PageInfo<Information> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<Information> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<Information> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
}
