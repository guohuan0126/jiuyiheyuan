package com.duanrong.cps.business.activity.service;

import java.util.List;
import java.util.Map;

import com.duanrong.cps.business.activity.model.RedPacketDetail;

import base.pagehelper.PageInfo;



/**
 * 活动参加人Service
 * @author Qiu Feihu
 * @time 2015年6月19日11:03:37
 */
public interface RedPacketDetailService {
    
	public RedPacketDetail get(Integer id);
	
	public void add(RedPacketDetail detail);
	
	public void update(RedPacketDetail detail);
	
	public void delete(Integer id);
	
	public PageInfo<RedPacketDetail> pageLite(int pageNo,int pageSize,Object detail);
	public void saveDetail(List<RedPacketDetail> rf);
	public void del(RedPacketDetail detail);

	/**
	 * 查询投资信息可以使用的加息券
	 * @see 使用规则：
	 * 		usage_detail:使用方式，投资可用【invest】类型 可以使用加息券，直接提现【withdraw】类型不可以使用。
	 * 		invest_money：投资到达指定额度之后可以使用加息券
	 * 		invest_rate：投资利率到达指定值之后可以使用，0表示没有限制；目前系统中未作判断
	 * 		use_loan_type： 0表示没有限制，1表示新手标不可用，2表示app专享，3表示APP专享且新手标不可用
	 * 		is_available: 1表示可以使用，0不可以使用
	 * 		create_time，dealine：时间区间，在此有效期内加息券可以使用
	 * @param redPacketId
	 * @return
	 */
	public List<RedPacketDetail> getRedPacketUse(String investId,String mobileNumber,String redpacketId);

	/**
	 * 更新投资信息加息券使用
	 * @param packetId
	 * @param investId
	 * @param userId  当前系统操作员ID
	 * @return
	 */
	public int updatePacketUse(String packetId, String investId,String userId);

	/**
	 * 校验用户的的同一项目其他投资是否已经使用加息券，
	 * 	如果以使用则同一项目其他投资不可使用加息券，返回false，否则返回true
	 * @param investId
	 * @return
	 */
	public int checkInvestNum(String investId);

	
	public int addRedpacketCoupon(Map<String, Object> params);

	public int addRedpacketCouponRecord(Map<String, Object> map);


	public int updateRedpacketCouponStatusByPid(String pid);

	public List<RedPacketDetail> readUserRedpacketList(Map<String, Object> map);

	public int updateRedpacketCouponFlagByPid(String pid);

	public int readFlagByPid(String pid);

	

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
