package com.duanrong.cps.business.activity.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.activity.dao.RedPacketDetailDao;
import com.duanrong.cps.business.activity.model.RedPacketDetail;
import com.duanrong.cps.business.activity.service.RedPacketDetailService;
import com.duanrong.cps.business.invest.dao.InvestDao;
import com.duanrong.cps.business.invest.model.Invest;


@Service
public class RedPacketDetailServiceImpl implements RedPacketDetailService {
    
	@Autowired
	public RedPacketDetailDao detailDao;
	@Autowired
	private InvestDao investDao;

	
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
	public PageInfo<RedPacketDetail> pageLite(int pageNo, int pageSize,
			Object detail) {
		
		return this.detailDao.pageLite(pageNo, pageSize, (RedPacketDetail) detail);
	}

	@Override
	public RedPacketDetail get(Integer id) {
		// TODO Auto-generated method stub
		return this.detailDao.get(id);
	}
	public static void main(String[]args){
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"beans-*.xml");
		RedPacketDetailDao detailDao = ctx.getBean(RedPacketDetailDao.class);
		System.out.println(detailDao.get(14849));
		
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
	public List<RedPacketDetail> getRedPacketUse(String investId,String mobileNumber,String redpacketId){
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
	public int checkInvestNum(String investId){
		return detailDao.getSamePacketUseCount(investId);
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
	public List<RedPacketDetail> readRedpacketListByMobile(
			Map<String, Object> map) {

		return detailDao.readRedpacketListByMobile(map);
	}

	@Override
	public void insertBatch(List<RedPacketDetail> list) {
		detailDao.insertBatch(list);
		
	}

	@Override
	public int updatePacketUse(String packetId, String investId, String userId) {
		// TODO Auto-generated method stub
		return 0;
	}



	
}
