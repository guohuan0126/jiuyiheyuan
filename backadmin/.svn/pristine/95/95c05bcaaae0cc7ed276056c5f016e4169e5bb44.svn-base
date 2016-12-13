package com.duanrong.business.lostcustomer.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



import com.duanrong.business.lostcustomer.dao.LostCustomerDao;
import com.duanrong.business.lostcustomer.model.LostCustomer;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

@Repository
public class LostCustomerDaoImpl extends BaseDaoImpl<LostCustomer> implements LostCustomerDao{
	
	public LostCustomerDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.lostcustomer.mapper.LostCustomerMapper"); // 设置命名空间
	}
	/**
	 * 分页
	 * @author guohuan
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	@Override
	public PageInfo<LostCustomer> pageInfo(int pageNo, int pageSize, Map<String , Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<LostCustomer> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",params);
		PageInfo<LostCustomer> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	/**
	 * 获取所有流失预警客户
	 * @author guohuan
	 * @return
	 */
	@Override
	public List<LostCustomer> getLostCustomers() {
		List<LostCustomer> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo");
		return list;
	}
	/**
	 * 更新流失预警客户
	 * @author guohuan
	 * @return
	 */
	@Override
	public int updateLostCustomer(Map<String , Object> params) {
		return this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateLostCustomer", params);
	}
	/**
	 * 导出excle
	 * @author guohuan
	 * @return
	 */
	@Override
	public List<LostCustomer> exportList(Map<String , Object> map) {
		List<LostCustomer> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		return list;
	}
	/**
	 * 获取流失预警客户流失天数
	 * @author guohuan
	 * @return
	 */
	@Override
	public int getLostDaysByUserId(String userId) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".getLostDaysByUserId", userId);
	}
	/**
	 * 根据查询条件获取流失预警客户
	 * @author guohuan
	 * @return
	 */
	@Override
	public List<LostCustomer> readUsers(Map<String, Object> params) {
		return getSqlSession().selectList(
				this.getMapperNameSpace() + ".pageInfo", params);
	}
	/**
	 * 获取用户回访后定期投资总额
	 * @author guohuan
	 * @return
	 */
	@Override
	public PageInfo<LostCustomer> getUserTotalInvest1(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<LostCustomer> list = getSqlSession().selectList(
					this.getMapperNameSpace() + ".getUserTotalInvest1", params);
		PageInfo<LostCustomer> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	/**
	 * 获取用户回访后活期投资总额
	 * @author guohuan
	 * @return
	 */
	@Override
	public PageInfo<LostCustomer> getUserTotalDemand1(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<LostCustomer> list = getSqlSession().selectList(
					this.getMapperNameSpace() + ".getUserTotalDemand1", params);
		PageInfo<LostCustomer> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	/**
	 * 获取用户在投定期总额
	 * @author guohuan
	 * @return
	 */
	/*@Override
	public PageInfo<LostCustomer> getUserTotalInvest(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<LostCustomer> list = getSqlSession().selectList(
					this.getMapperNameSpace() + ".getUserTotalInvest", params);
		PageInfo<LostCustomer> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}*/
}
	
	

