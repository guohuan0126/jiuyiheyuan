package com.duanrong.business.activity.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.activity.dao.RedPacketDetailDao;
import com.duanrong.business.activity.model.ActivateCoupon;
import com.duanrong.business.activity.model.ActivateCouponRecord;
import com.duanrong.business.activity.model.RedPacketDetail;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;

@Repository
public class RedPacketDetailImpl extends BaseDaoImpl<RedPacketDetail> implements RedPacketDetailDao {
    
   public RedPacketDetailImpl(){
	   this.setMapperNameSpace("com.duanrong.business.activity.mapper.redPacketDetailMapper");
   }

	@Override
	public void del(RedPacketDetail detail) {
		getSqlSession().update(
				getMapperNameSpace() + ".del", detail);	
	}
	

	/**
	 * 查询用户同一项目其他投资记录是否已经使用加息券
	 */
	public int getSamePacketUseCount(String investId) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getSamePacketUseCount", investId);
	}
	/**
	 * 查询加息券
	 * @param money
	 * @return
	 */
	public List<RedPacketDetail> getRedPacketUse(String mobileNumber,Double money,String redpacketId){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileNumber", mobileNumber);
		m.put("money", money);
		m.put("redpacketId", redpacketId);
		return this.getSqlSession().selectList(getMapperNameSpace()+".getRedPacketUse",m);
	}
	
	/**
	 * 更改加息券使用时间和状态
	 * @param redpacketId
	 * @return
	 */
	public int updateForPacketUse(int redpacketId,String status){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("redPacketId", redpacketId);
		m.put("status", status);
		return this.getSqlSession().update(getMapperNameSpace()+".updateForPacketUse", m);
	}

	@Override
	public PageInfo<ActivateCoupon> readRedpacketCouponList(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<ActivateCoupon> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readRedpacketCouponList",params);
		PageInfo<ActivateCoupon> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public int addRedpacketCoupon(Map<String, Object> params) {
		
		return this.getSqlSession().insert(this.getMapperNameSpace()+".addRedpacketCoupon", params);
	}

	@Override
	public int addRedpacketCouponRecord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(this.getMapperNameSpace()+".addRedpacketCouponRecord", map);
	}

	@Override
	public List<ActivateCoupon> readExportRedpacketCouponList(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readExportRedpacketCouponList", params);
	}

	@Override
	public int updateRedpacketCouponStatusByPid(String pid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateRedpacketCouponStatusByPid", pid);
	}

	@Override
	public List<RedPacketDetail> readUserRedpacketList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readUserRedpacketList", map);
	}

	@Override
	public int updateRedpacketCouponFlagByPid(String pid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateRedpacketCouponFlagByPid", pid);
	}

	@Override
	public int readFlagByPid(String pid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readFlagByPid", pid);
	}

	@Override
	public PageInfo<ActivateCouponRecord> readRedpacketCouponRecordList(
			int pageNo, int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<ActivateCouponRecord> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readRedpacketCouponRecordList",params);
		PageInfo<ActivateCouponRecord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<RedPacketDetail> readRedpacketListByMobile(
			Map<String, Object> map) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readRedpacketListByMobile", map);
	}

	@Override
	public void insertBatch(List<RedPacketDetail> list) {
		 this.getSqlSession().insert(this.getMapperNameSpace()+".batchInsertRedDetail", list);
		
	}

}
