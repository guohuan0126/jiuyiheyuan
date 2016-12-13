package com.duanrong.business.award.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duanrong.business.award.dao.AwardItemUserDao;
import com.duanrong.business.award.model.AwardItem;
import com.duanrong.business.award.model.AwardItemUser;
import com.duanrong.business.award.service.AwardItemUserService;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-2-10 下午3:50:35
 * @Description : NewAdmin com.duanrong.business.award.service.impl
 *              AwardItemServiceImpl.java
 * 
 */
@Service
public class AwardItemUserServiceImpl implements AwardItemUserService {

	@Autowired
	AwardItemUserDao awardItemUserDao;

	@Override
	public int deleteAwardItemUser(int id) {
		
		return awardItemUserDao.deleteAwardItemUser(id);
	}

	@Override
	public void update(AwardItemUser awardItemUser) {
		 awardItemUserDao.update(awardItemUser);
	}

	@Override
	public AwardItemUser readAwardItemUser(int id) {
		
		return awardItemUserDao.get(id);
	}

	@Override
	public int updateawarditemuserMoney(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return awardItemUserDao.updateawarditemuserMoney(map);
	}



	
}
