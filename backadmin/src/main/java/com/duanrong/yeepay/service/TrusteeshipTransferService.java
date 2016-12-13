package com.duanrong.yeepay.service;

import java.util.Map;

import com.duanrong.business.trusteeship.model.TrusteeshipOperation;


public interface TrusteeshipTransferService{

	/**
	 * 转账
	 * @param requestNo
	 * @param type
	 * @param to
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> transfer(String requestNo, String type, TrusteeshipOperation to) throws Exception;
}
