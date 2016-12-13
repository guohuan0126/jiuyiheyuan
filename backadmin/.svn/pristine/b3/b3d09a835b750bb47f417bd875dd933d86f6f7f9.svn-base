package com.duanrong.business.ruralfinance.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duanrong.business.ruralfinance.dao.AgricultureRepaymentPlanDao;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;
import com.duanrong.business.ruralfinance.model.AgricultureTimelyloansPrepayment;
import com.duanrong.business.ruralfinance.service.AgricultureLoanerInfoService;
import com.duanrong.business.ruralfinance.service.AgricultureRepaymentPlanService;
import com.duanrong.business.ruralfinance.service.AgricultureTimelyloansPrepaymentService;
import com.duanrong.business.ruralfinance.service.AgricultureZhongjinbankService;
@Service
public class AgricultureRepaymentPlanServiceImpl implements AgricultureRepaymentPlanService {
	/**
	 * 借款人表
	 */
	@Resource
	private  AgricultureRepaymentPlanDao agricultureRepaymentPlanDao;
	@Override
	public void insertBatch(List<AgricultureRepaymentPlan> list) {		
		agricultureRepaymentPlanDao.insertBatch(list);
	}

	@Override
	public int updateRepaymentplan(Map<String, Object> params) {
	
		return agricultureRepaymentPlanDao.updateRepaymentplan(params);
	}

	@Override
	public int updateRepaymentplanStatus(Map<String, Object> params) {
		return agricultureRepaymentPlanDao.updateRepaymentplanStatus(params);
	}

	@Override
	public int updateRepaymentplanFlag(String uploadExcelId) {
		return agricultureRepaymentPlanDao.updateRepaymentplanFlag(uploadExcelId);
	}

	@Override
	public int updateTimlyRepaymentplan(AgricultureRepaymentPlan repaymentPlan) {
		return agricultureRepaymentPlanDao.updateTimlyRepaymentplan(repaymentPlan);
	}

	@Override
	public int updateRepaymentplanById(Map<String, Object> params) {
		
		return agricultureRepaymentPlanDao.updateRepaymentplanById(params);
	}
}
