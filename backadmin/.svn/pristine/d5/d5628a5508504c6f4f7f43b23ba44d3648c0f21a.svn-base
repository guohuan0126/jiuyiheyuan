package com.duanrong.business.ruralfinance.dao.imp;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.ruralfinance.dao.AgricultureRepaymentPlanDao;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;

@Repository
public class AgricultureRepaymentPlanDaoImpl extends BaseDaoImpl<AgricultureForkLoans> implements AgricultureRepaymentPlanDao {

	public AgricultureRepaymentPlanDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.ruralfinance.mapper.AgricultureRepaymentPlanMapper");
	}

	@Override
	public void insertBatch(List<AgricultureRepaymentPlan> list) {
		this.getSqlSession().insert(this.getMapperNameSpace() +".batchAgricultureRepaymentPlan",list);	
	
	}

	@Override
	public int updateRepaymentplan(Map<String, Object> params) {		
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateRepaymentplan",params);
	}

	@Override
	public int updateRepaymentplanStatus(Map<String, Object> params) {
	return this.getSqlSession().update(this.getMapperNameSpace()+".updateMatchRepaymentplan",params);
	}

	@Override
	public int updateRepaymentplanFlag(String uploadExcelId) {
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateDebitRecordsStatus",uploadExcelId);
	}

	@Override
	public int updateTimlyRepaymentplan(AgricultureRepaymentPlan repaymentPlan) {
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateTimlyRepaymentplan",repaymentPlan);
	}

	@Override
	public int updateRepaymentplanById(Map<String, Object> params) {
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateRepaymentplanById",params);
		
	}


}
