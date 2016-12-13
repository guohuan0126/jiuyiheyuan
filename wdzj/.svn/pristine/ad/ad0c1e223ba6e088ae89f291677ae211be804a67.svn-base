package com.duanrong.business.token.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.token.dao.AccessTokenDao;
import com.duanrong.business.token.model.AccessToken;
import com.duanrong.business.token.service.AccessTokenService;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

	@Resource
	AccessTokenDao accessTokenDao;


	@Override
	public boolean validateToken(String tokenID) {
		AccessToken accessToken = accessTokenDao.getTokenByID(tokenID);
		if(accessToken == null){
			return false;
		}
		Date date = new Date();
		if(date.getTime() >= accessToken.getCreateTime().getTime() && date.getTime() <= accessToken.getDeadline().getTime()){
			return true;
		}
		return false;
	}



	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insert(AccessToken accessToken) {
		accessTokenDao.insert(accessToken);
	}


}