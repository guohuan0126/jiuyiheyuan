package com.duanrong.business.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.system.dao.OperaRecordDao;
import com.duanrong.business.system.model.OperaRecord;

@Repository
public class OperaRecordDaoImpl extends BaseDaoImpl<OperaRecord> implements OperaRecordDao {

	public OperaRecordDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.system.mapper.OperaRecordMapper");
	}

	@Override
	public PageInfo<OperaRecord> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<OperaRecord> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<OperaRecord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
}
