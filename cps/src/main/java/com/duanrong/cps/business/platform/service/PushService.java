package com.duanrong.cps.business.platform.service;

import java.util.Map;

public interface PushService {

	
	public void pushMethod(Map<String, Object> params) throws Exception;
	
	public void pushLoanAbout(Map<String,Object>params)throws Exception;
}
