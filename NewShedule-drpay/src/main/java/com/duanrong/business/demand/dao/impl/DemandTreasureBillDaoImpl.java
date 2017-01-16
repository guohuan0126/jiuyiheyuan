package com.duanrong.business.demand.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.business.demand.model.DemandTreasureBill;

/**
 * @author xiao
 * @date 2015年7月20日下午2:19:38
 */
@Repository
public class DemandTreasureBillDaoImpl extends BaseDaoImpl<DemandTreasureBill>
		implements DemandTreasureBillDao {

	public DemandTreasureBillDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandTreasureBillMapper"); // 设置命名空间
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
	public double getDemandTreasureInvalidMoney(String userId, String type,
			Date date) {
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
		return getSqlSession().insert(
				getMapperNameSpace() + ".insertInterestBatch", list);
	}

	@Override
	public double getDemandTreasureMoneyByType(String type) {
		Double money = getSqlSession().selectOne(
				getMapperNameSpace() + ".getTotalDemandTreasureMoneyByType",
				type);
		return money == null ? 0 : money;

	}

	@Override
	public List<DemandTreasureBill> getAccount(int days, String type) {
		Map<String, Object> param = new HashMap<>();
		param.put("days", days);
		param.put("type", type);
		return getSqlSession().selectList(getMapperNameSpace() + ".getAccount",
				param);
	}

	@Override
	public int insertBill(DemandTreasureBill bill) {
		return getSqlSession().insert(getMapperNameSpace() + ".insertBill", bill);
	}

	@Override
	public PageInfo<DemandTreasureBill> pageLite02(int pageNo, int pageSize,
			DemandTreasureBill entity) {
		PageHelper.startPage(pageNo, pageSize);
		List<DemandTreasureBill> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageLite02", entity);
		PageInfo<DemandTreasureBill> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<String> getInterestUser() {
		return getSqlSession().selectList(getMapperNameSpace() + ".getInterestUser");
	}

	@Override
	public List<String> getInterestToday() {
		return getSqlSession().selectList(getMapperNameSpace() + ".getInterestToday");
	}
	
	@Override
	public List<String> getInterestToday(Date date) {
		return getSqlSession().selectList(getMapperNameSpace() + ".getInterestToday1", date);
	}

	@Override
	public Integer getCount(String billId) {
		Integer count = getSqlSession().selectOne(
				getMapperNameSpace() + ".getCount",
				billId);
		return count == null ? 0 : count;
	}
	@Override
	public double getDemandLaterInterest(String userId) {
		Double money = getSqlSession().selectOne(getMapperNameSpace() + ".getDemandLaterInterest", userId);
		return money == null ? 0 : money;
	}

	

	@Override
	public double getDemandBillAllMoneyByStatus(String userId,String status) {
		Map<String,Object> map  = new HashMap<>();
		map.put("userId", userId);
		map.put("status", status);
		Double money = getSqlSession().selectOne(getMapperNameSpace() + ".getDemandBillSumMoney", map);
		return money == null ? 0 : money;
	}


}
