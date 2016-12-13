package com.duanrong.business.activity.dao;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import com.duanrong.business.activity.model.ActivateCoupon;
import com.duanrong.business.activity.model.ActivateCouponRecord;
import com.duanrong.business.activity.model.Activity;
import com.duanrong.business.activity.model.RedPacketDetail;

/**
 * 活动参与人Dao
 * @author Qiu Feihu
 *
 */
public interface RedPacketDetailDao extends BaseDao<RedPacketDetail>{
	public void del(RedPacketDetail detail);

	/**
	 * 
	 * @param investId
	 * @return
	 */
	public int getSamePacketUseCount(String investId);

	/**
	 * 查询加息券
	 * @param money
	 * @return
	 */
	public List<RedPacketDetail> getRedPacketUse(String mobileNumber,Double money,String redpacketId);

	/**
	 * 更改加息券使用时间和状态
	 * @param redpacketId
	 * @return
	 */
	public int updateForPacketUse(int redpacketId,String status);

	public PageInfo<ActivateCoupon> readRedpacketCouponList(int pageNo,
			int pageSize, Map<String, Object> params);

	public int addRedpacketCoupon(Map<String, Object> params);

	public int addRedpacketCouponRecord(Map<String, Object> map);

	public List<ActivateCoupon> readExportRedpacketCouponList(
			Map<String, Object> params);

	public int updateRedpacketCouponStatusByPid(String pid);

	public List<RedPacketDetail> readUserRedpacketList(Map<String, Object> map);

	public int updateRedpacketCouponFlagByPid(String pid);

	public int readFlagByPid(String pid);

	public PageInfo<ActivateCouponRecord> readRedpacketCouponRecordList(
			int pageNo, int pageSize, Map<String, Object> params);

	/**
	 *查询该用户是否发送过rule_id=23的券
	 * @param map
	 * @return
	 */
	public List<RedPacketDetail> readRedpacketListByMobile(Map<String, Object> map);
	/**
	 * 批量添加Vip专享券
	 * @param list
	 */
	public void insertBatch(List<RedPacketDetail> list); 

}
