package com.duanrong.business.ruralfinance.dao.imp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;




import com.duanrong.business.ruralfinance.dao.AgricultureTimelyloansPrepaymentDao;
import com.duanrong.business.ruralfinance.model.AgricultureTimelyloansPrepayment;


@Repository
public class AgricultureTimelyloansPrepaymentDaoImpl<T> extends BaseDaoImpl<AgricultureTimelyloansPrepayment> implements AgricultureTimelyloansPrepaymentDao {

	public AgricultureTimelyloansPrepaymentDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.ruralfinance.mapper.AgricultureTimelyloansPrepaymentMapper");
	}

	@Override
	public int saveAgricultureTimelyloansPrepayment(Object obj, String type) {
		return this.getSqlSession().insert(this.getMapperNameSpace() +".savetimelyloans", obj);
	}

	@Override
	public AgricultureTimelyloansPrepayment findByCondition(Map map) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".findByCondition", map);
		
	}

	@Override
	public int saveExcle(Object obj, String type) {
		return this.getSqlSession().insert(this.getMapperNameSpace() +".saveExcle", obj);
	}

	@Override
	public PageInfo<AgricultureTimelyloansPrepayment> readAgricultureTimelyPrepayment(int pageNo, int pageSize,
			Map params) {
		PageHelper.startPage(pageNo, pageSize);
		List<AgricultureTimelyloansPrepayment> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readAgricultureTimelyPrepayment",params);
		PageInfo<AgricultureTimelyloansPrepayment> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<AgricultureTimelyloansPrepayment> readTimelyPrepaymentbyupload(Map params) {
		List<AgricultureTimelyloansPrepayment> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readTimelyPrepaymentbyupload",params);
		return list;
	}

	@Override
	public int updateTimelyloanPrepaymentStatus(String id) {		
		return this.getSqlSession().update(this.getMapperNameSpace() +".updateTimelyloanPrepaymentStatus", id);
	}	
}