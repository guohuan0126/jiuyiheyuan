package com.duanrong.cps.business.netloaneye.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.duanrong.cps.business.netloaneye.model.PushLoanAgain;




/**
 * 推送
 * @author Today
 *
 */
public interface PushService {
   
	/**
	 * 推送交易信息
	 */
	public Integer pushInvestPerson(String id);
	
	
	/**
	 * 推送项目状态
	 */
	public Integer pushLoanStatusInfo(List<PushLoanAgain> loanList);


	public Integer pushInvestPersonByUserId(Map<String, Object> params);

	
	
}
