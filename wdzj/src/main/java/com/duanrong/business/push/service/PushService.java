package com.duanrong.business.push.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.push.model.PushLoan;

/**
 * 推送
 * @author Today
 *
 */
public interface PushService {
   
	/**
	 * 推送交易信息
	 */
	public Integer pushInvestPerson();
	
	
	/**
	 * 推送项目状态
	 */
	public Integer pushLoanStatusInfo(List<PushLoan> loanList);
}
