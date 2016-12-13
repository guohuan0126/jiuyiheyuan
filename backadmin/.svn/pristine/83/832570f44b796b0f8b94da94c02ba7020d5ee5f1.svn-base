package com.duanrong.business.ruralfinance.dao.imp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureDebitRecords;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.dao.AgricultureDebitRecordsDao;


@Repository
public class AgricultureDebitRecordsDaoImpl<T> extends BaseDaoImpl<AgricultureDebitRecords> implements AgricultureDebitRecordsDao {

	public AgricultureDebitRecordsDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.ruralfinance.mapper.AgricultureDebitRecordsMapper");
	}

	@Override
	public int saveAgricultureDebitRecords(Object obj, String type) {
		return this.getSqlSession().insert(this.getMapperNameSpace() +".saveZJdebitRecords", obj);
	}

	@Override
	public AgricultureDebitRecords findByCondition(Map map) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".findByCondition", map);
		
	}

	@Override
	public int saveExcle(Object obj, String type) {
		return this.getSqlSession().insert(this.getMapperNameSpace() +".saveExcle", obj);
	}

	@Override
	public PageInfo<AgricultureDebitRecords> readAgricultureDebitRecords(int pageNo, int pageSize,
			Map params) {
		PageHelper.startPage(pageNo, pageSize);
		List<AgricultureDebitRecords> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readAgricultureDebitRecords",params);
		PageInfo<AgricultureDebitRecords> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}



	
	
	
}