package com.duanrong.thirdPartyInterface.service;

import java.util.List;
import java.util.Map;

import base.model.Invest;
import base.model.Loan;

/**
 * 网贷天眼service
 * 
 * @author 尹逊志
 * @date 2014-11-12下午5:53:35
 */
public interface WDTYService {

	/**
	 * 查询某个项目已经募集的金额
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-12下午6:18:20
	 * @param loanId
	 * @return
	 */
	public double getInvestedMoney(String loanId);

	/**
	 * 查询某项目的所有投资
	 * 
	 * @author:yinxunzhi
	 * @param map 
	 * @time:2014-11-13下午3:41:48
	 * @return
	 */
	public List<Invest> findAllInvests(Map<Object, Object> map);

	/**
	 * 查询所有借款
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-12下午6:18:11
	 * @return
	 */
	public List<Loan> findAllLoans(Map<Object, Object> map);
	/**
	 * 查询某笔项目的投资次数
	 * @author:yinxunzhi
	 * @time:2014-11-14下午1:15:59
	 * @param id
	 * @return
	 */
	public Long getInvestNum(String id);

	/**
	 * @description TODO
	 * @date 2015-5-5 
	 * @time 下午3:33:08
	 * @author SunZ
	 * @return
	 */
	long getAllLoansSize();

	/**
	 * @description TODO
	 * @date 2015-5-5 
	 * @time 下午6:28:32
	 * @author SunZ
	 * @param time_from
	 * @param time_to
	 */
	public long findLoansByTime(String time_from, String time_to);
	
}
