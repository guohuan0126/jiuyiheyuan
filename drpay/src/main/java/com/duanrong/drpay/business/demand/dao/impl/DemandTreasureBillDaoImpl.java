package com.duanrong.drpay.business.demand.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.duanrong.drpay.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.drpay.business.demand.model.DemandTreasureBill;

import base.dao.impl.BaseDaoImpl;

/**
 * @author xiao
 * @date 2015年7月20日下午2:19:38
 */
@Repository
public class DemandTreasureBillDaoImpl extends BaseDaoImpl<DemandTreasureBill>
		implements DemandTreasureBillDao {

	public DemandTreasureBillDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.demand.mapper.DemandTreasureBillMapper"); // 设置命名空间
	}

	@Override
	public double getDemandTreasureMoneyByType(String userId, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("type", type);
		Double money = getSqlSession().selectOne(
				getMapperNameSpace() + ".getDemandTreasureMoneyByType", param);
		return money == null ? 0 : money;

	}

	@Override
	public double getDemandTreasureInvalidMoney(String userId, String type, Date date) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("type", type);
		param.put("date", date);
		Double money = getSqlSession().selectOne(
				getMapperNameSpace() + ".getDemandTreasureInvalidMoney", param);
		return money == null ? 0 : money; 
	}

	
	@Override
	public List<String> getDemandTreasureUser() {
		
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getDemandTreasureUser");
	}

	
	@Override
	public int insertInterestBatch(List<DemandTreasureBill> list) {
		Integer count = getSqlSession().insert(getMapperNameSpace() + ".insertInterestBatch", list);
		return count == null ? 0 : count;
	}

	
	@Override
	public double getDemandLaterInterest(String userId) {
		Double money = getSqlSession().selectOne(getMapperNameSpace() + ".getDemandLaterInterest", userId);
		return money == null ? 0 : money;
	}

	
	@Override
	public double getDemandTreasureMoney(String type) {
		
		Double money =  getSqlSession().selectOne(getMapperNameSpace() + ".getDemandTreasureMoney", type);
		
		return money == null ? 0 : money;
	}

	
	@Override
	public double getDemandTranferOut(String userId) {
		
		return 0;
	}

}
