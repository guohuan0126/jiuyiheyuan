package com.duanrong.business.ruralfinance.dao.imp;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;




import com.duanrong.business.ruralfinance.dao.AgricultureForkLoansDao;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;

@Repository
public class AgricultureForkLoansDaoImpl extends BaseDaoImpl<AgricultureForkLoans> implements AgricultureForkLoansDao {

	public AgricultureForkLoansDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.ruralfinance.mapper.AgricultureForkLoansMapper");
	}

	@Override
	public void insertBatch(List<AgricultureForkLoans> list) {
		this.getSqlSession().insert(this.getMapperNameSpace() +".batchAgricultureForkLoans",list);	

		
		
	}

	@Override
	public PageInfo<AgricultureForkLoans> readPackagingLoan(int pageNo,
			int pageSize, Map<String, Object> params) {		
		PageHelper.startPage(pageNo, pageSize);
		List<AgricultureForkLoans> list = getSqlSession().selectList(
				getMapperNameSpace() + ".PackAgricultureForkLoans", params);
		PageInfo<AgricultureForkLoans> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public void updateBatch(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace() +".updateBatch",params);	

		
	}

	@Override
	public int updateForkLoanstatus(Map<String, Object> params) {
		return this.getSqlSession().update(this.getMapperNameSpace() +".updateForkLoanstatus",params);	
	}

	@Override
	public AgricultureForkLoans readByForkId(String id) {
		
		return this.getSqlSession().selectOne(this.getMapperNameSpace() +".selectByForkId",id);
	}

	@Override
	public void updatePackStatus(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace() +".updatePackStatus",params);	
		
	}
	



	
}
