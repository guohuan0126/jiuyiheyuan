package com.duanrong.payment.service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 定义S2SCallback接口
 * @CreateDate: Nov 21, 2014
 */
public interface OperationService {
	
	@Transactional(rollbackFor = Exception.class)
	public boolean callback(ServletRequest request, ServletResponse response)throws Exception;
	/**
	 * 回调通知
	 * 
	 * @param request
	 * @param response
	 */
	@Transactional(rollbackFor = Exception.class)
	public String S2SCallback(HttpServletRequest request,HttpServletResponse response,String operationType)throws Exception;
}
