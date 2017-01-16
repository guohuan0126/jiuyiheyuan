package com.duanrong.business.lostcustomer.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.lostcustomer.dao.LostCustomerDao;
import com.duanrong.business.lostcustomer.model.LostCustomer;
import com.duanrong.business.user.model.User;

@Repository
public class LostCustomerDaoImpl extends BaseDaoImpl<LostCustomer>implements LostCustomerDao{
	
	public LostCustomerDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.lostcustomer.mapper.LostCustomerMapper");
	}
	/**
	 * 获取最晚的一个项目结束时间至今已超过15天的所有用户
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	@Override
	public List<User> getAllUser() {
		Date dBefore = null;

		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(new Date());// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -15); // 设置为前15天
		dBefore = calendar.getTime(); // 得到前一天的时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		String finishTime = sdf.format(dBefore); // 格式化前一天
		List<User> allUsers = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getAllUser",finishTime);
		return allUsers;
	}
	/**
	 * 根据userId查询在投定期项目个数
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	@Override
	public int getInvestStatusCount(String userId) {
		int count = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestStatusCount",userId);
		return count;
	}
	/**
	 * 根据userId查询用户定期投资次数
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	@Override
	public int getInvestNumByUserId(String userId) {
		int investNum = this.getSqlSession().selectOne(
					this.getMapperNameSpace() + ".getInvestNumByUserId",userId);
		return investNum;
	}
	/**
	 * 根据userId查询用户活期投资次数
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	@Override
	public int getDemandNumByUserId(String userId) {
		int demandNum = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getDemandNumByUserId",userId);
		return demandNum;
	}
	/**
	 * 根据userId获取天天赚资产
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	@Override
	public double getDemandMoneyByUserId(String userId) {
		double demandMoney = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getDemandMoney",userId);
		return demandMoney;
	}
	/**
	 * 批量插入流失预警客户
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	@Override
	public int insertAllLostCustomer(List<LostCustomer> lostCustomers) {
		int insert = this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertAllLostCustomer",lostCustomers);
		return insert;
	}
	/**
	 * 根据userId查询流失预警客户
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	@Override
	public LostCustomer getLostCustomerByUserId(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getLostCustomerByUserId",userId);
	}
	/**
	 * 将流失预警客户插入流失预警历史纪录表
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	@Override
	public void insertLostCustomerToHistory(LostCustomer lost) {
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertLostCustomerToHistory",
				lost);
	}
	/**
	 * 判断经理推荐人是否为空
	 * @author guohuan
	 * @param
	 * @return 返回true为空，false不为空
	 * @serialData 16/11/1
	 */
	@Override
	public boolean isOreferrerNull(String userId) {
		String oreferrer = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getOreferrerByUserId",
				userId);
		if (oreferrer==null||("").equals(oreferrer)) {
			return true;
		}
		return false;
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
	public List<LostCustomer> pageInfo(Map<String , Object> params) {
		List<LostCustomer> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo",params);
		return list;
	}
	@Override
	public List<LostCustomer> pageInfo1(Map<String , Object> params) {
		List<LostCustomer> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo1",params);
		return list;
	}
	/**
	 *更新流失预警客户 
	 */
	@Override
	public void updateByUserId(String userId) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateByUserId",userId);
	}
	
}