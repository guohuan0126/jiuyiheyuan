package com.duanrong.business.token.service;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.token.model.AccessToken;

/**
 * @Description: TOKEN相关操作，获取，校验，保存操作详情等 
 * @Author:	林志明
 * @CreateDate:	Nov 7, 2014
 */
public interface AccessTokenService {

	/**
	 * 
	 * @description 保存一个token
	 * @author 孙铮
	 * @time 2014-11-13 上午10:20:42
	 * @param accessToken
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insert(AccessToken accessToken);
	
	/**
	 * 
	 * @description 验证一个token
	 * @author 孙铮
	 * @time 2014-11-13 上午10:20:50
	 * @param token
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean validateToken(String tokenID);

}