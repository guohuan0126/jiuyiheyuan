package com.duanrong.yeepay.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

public interface TrusteeshipTransactionAuthorizationService{

	@Transactional(rollbackFor = Exception.class)
	public void S2SCallback(HttpServletRequest request,HttpServletResponse response);
}
