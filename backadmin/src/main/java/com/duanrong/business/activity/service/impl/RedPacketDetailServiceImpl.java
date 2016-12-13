package com.duanrong.business.activity.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import base.pagehelper.PageInfo;

import com.duanrong.business.activity.dao.RedPacketDetailDao;
import com.duanrong.business.activity.model.ActivateCoupon;
import com.duanrong.business.activity.model.ActivateCouponRecord;
import com.duanrong.business.activity.model.RedPacketDetail;
import com.duanrong.business.activity.service.RedPacketDetailService;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.riskfund.model.Riskfund;
import com.duanrong.business.riskfund.model.Riskfunddetail;
import com.duanrong.business.system.service.OperaRecordService;

@Service
public class RedPacketDetailServiceImpl implements RedPacketDetailService {
    
	@Autowired
	public RedPacketDetailDao detailDao;
	@Autowired
	private InvestDao investDao;
	@Autowired
	private OperaRecordService operaRecordService;
	
	@Override
	public void add(RedPacketDetail detail) {
		this.detailDao.insert(detail);
		
	}

	@Override
	public void update(RedPacketDetail detail) {
		this.detailDao.update(detail);
		
	}

	@Override
	public void delete(Integer id) {
		this.detailDao.delete(id);
		
	}

	@Override
	public PageInfo<RedPacketDetail> readPageLite(int pageNo, int pageSize,
			RedPacketDetail detail) {
		
		return this.detailDao.pageLite(pageNo, pageSize,  detail);
	}

	@Override
	public RedPacketDetail read(Integer id) {
		// TODO Auto-generated method stub
		return this.detailDao.get(id);
	}
	@Override
	public void saveDetail(List<RedPacketDetail> rf) {
		for(RedPacketDetail d:rf){
			detailDao.insert(d);
		}
	}

	@Override
	public void del(RedPacketDetail detail) {
		detailDao.del(detail);
	}
	
	/**
	 * 查询投资信息可以使用的加息券
	 * @see 使用规则：
	 * 		1、用户同一笔投资只能使用一张加息券,排除流标
	 * 
	 * 		2、usage_detail:使用方式，投资可用【invest】类型 可以使用加息券，直接提现【withdraw】类型不可以使用。
	 * 		3、invest_money：投资到达指定额度之后可以使用加息券
	 * 		4、invest_rate：投资利率到达指定值之后可以使用，0表示没有限制；目前系统中未作判断
	 * 		5、is_available: 1表示可以使用，0不可以使用
	 * 		6、create_time，dealine：时间区间，在此有效期内加息券可以使用
	 * 
	 * 		7、use_loan_type： 0表示没有限制，1表示新手标不可用，2表示app专享，3表示APP专享且新手标不可用;根据业务需要只限制1、3
	 * @param investId
	 * @return
	 */
	public List<RedPacketDetail> readRedPacketUse(String investId,String mobileNumber,String redpacketId){
		List<RedPacketDetail> retLst = new ArrayList<RedPacketDetail>();
		//2查询投资信息
		Invest invest = investDao.getInvestById(investId);
		//3查询可用加息券[包括当前加息券]
		retLst = detailDao.getRedPacketUse(mobileNumber,invest.getMoney(),redpacketId);		
		//4 排除条件7限制
		Iterator<RedPacketDetail> iter = retLst.iterator();
		while(iter.hasNext()){
			RedPacketDetail dvo = iter.next();
			if( "1".equals(dvo.getUseLoanType()) ){
				if( "是".equals(invest.getLoan().getNewbieEnjoy()) ){
					iter.remove();
				}
			}
			else if( "3".equals(dvo.getUseLoanType()) ){
				if( "是".equals(invest.getLoan().getNewbieEnjoy()) ){
					iter.remove();
				}
			}
		}
		return retLst;
	}
	
	/**
	 * 更新投资信息加息券使用
	 * @param packetId
	 * @param investId
	 * @param userId 当前系统操作员ID
	 * @return
	 */
	@Transactional
	public int updatePacketUse(String packetId, String investId,String userId){
		//操作记录
		StringBuffer logContent = new StringBuffer();
		//查询投资记录
		Invest invest = investDao.getInvestById(investId);
		//判断加息券ID相同，则不作更改
		if( packetId !=null && packetId.equals(Integer.toString(invest.getRedpacketId())) ){
			return 1;
		}
		
		//更新red_packet_detail,之前使用的加息券置为未使用状态,使用时间置空
		if( invest.getRedpacketId() > 0 ){
			RedPacketDetail preUpdate = detailDao.get(invest.getRedpacketId());
			detailDao.updateForPacketUse(invest.getRedpacketId(),"unused");
			logContent.append("red_packet_detail表,ID:"+invest.getRedpacketId()+";更新：send_status["+preUpdate.getSendStatus()+"]-->unused,use_time-->null;");
		}
		//更改invest信息，加息券ID
		logContent.append("invest表,ID:"+investId+";更新：redpacket_id["+invest.getRedpacketId()+"]-->"+packetId+";");
		invest.setRedpacketId(Integer.parseInt(packetId==null?"0":packetId));
		int upc = investDao.updateForPacketUse(invest);
		
		//更新red_packet_detail，状态修改为已经使用，更新使用时间
		if( packetId != null ){
			detailDao.updateForPacketUse(Integer.parseInt(packetId),"used");
			logContent.append("red_packet_detail表,ID:"+packetId+";更新：send_status[unused]-->used,use_time[null]-->"+getDateTime()+";");
		}
		//插入操作记录
		operaRecordService.insertRecord("加息券使用变更记录", userId, logContent.toString());
		return upc;
	}
	
	private static String getDateTime(){
		Date d = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(d);
	}
	
	/**
	 * 校验用户的的同一项目其他投资是否已经使用加息券，【排除流标项目】
	 * 	如果以使用则同一项目其他投资不可使用加息券，返回false，否则返回true
	 * @param investId
	 * @return
	 */
	public int readCheckInvestNum(String investId){
		return detailDao.getSamePacketUseCount(investId);
	}

	@Override
	public PageInfo<ActivateCoupon> readRedpacketCouponList(int pageNo,
			int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return detailDao.readRedpacketCouponList(pageNo,pageSize,params);
	}

	@Override
	public int addRedpacketCoupon(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return detailDao.addRedpacketCoupon(params);
	}

	@Override
	public int addRedpacketCouponRecord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return detailDao.addRedpacketCouponRecord(map);
	}

	@Override
	public List<ActivateCoupon> readExportRedpacketCouponList(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return detailDao.readExportRedpacketCouponList(params);
	}

	@Override
	public int updateRedpacketCouponStatusByPid(String pid) {
		// TODO Auto-generated method stub
		return detailDao.updateRedpacketCouponStatusByPid(pid);
	}

	@Override
	public List<RedPacketDetail> readUserRedpacketList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return detailDao.readUserRedpacketList(map);
	}

	@Override
	public int updateRedpacketCouponFlagByPid(String pid) {
		// TODO Auto-generated method stub
		return detailDao.updateRedpacketCouponFlagByPid(pid);
	}

	@Override
	public int readFlagByPid(String pid) {
		// TODO Auto-generated method stub
		return detailDao.readFlagByPid(pid);
	}

	@Override
	public PageInfo<ActivateCouponRecord> readRedpacketCouponRecordList(
			int pageNo, int pageSize, Map<String, Object> params) {
		
		return detailDao.readRedpacketCouponRecordList(pageNo,pageSize,params);
	}

	@Override
	public List<RedPacketDetail> readRedpacketListByMobile(
			Map<String, Object> map) {

		return detailDao.readRedpacketListByMobile(map);
	}

	@Override
	public void insertBatch(List<RedPacketDetail> list) {
		detailDao.insertBatch(list);
		
	}



	
}
