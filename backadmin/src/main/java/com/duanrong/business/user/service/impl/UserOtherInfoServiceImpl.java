package com.duanrong.business.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.MyStringUtils;

import com.duanrong.business.lostcustomer.dao.LostCustomerDao;
import com.duanrong.business.lostcustomer.model.LostCustomer;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.dao.UserOtherInfoDao;
import com.duanrong.business.user.dao.UserVisitInfoDao;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserOtherInfo;
import com.duanrong.business.user.model.UserVisitInfo;
import com.duanrong.business.user.model.newInvestUser;
import com.duanrong.business.user.service.UserOtherInfoService;

@Service
public class UserOtherInfoServiceImpl implements UserOtherInfoService {

	@Resource
	UserOtherInfoDao userOtherInfoDao;

	@Resource
	UserDao userDao;
	@Resource
	UserVisitInfoDao userVisitInfoDao;
	@Resource
	LostCustomerDao lostCustomerDao;

	@Override
	public UserOtherInfo read(String id) {
		return userOtherInfoDao.get(id);
	}

	@Override
	public void insertOrUpdate(UserOtherInfo userOtherInfo) {
		String postAddress = userOtherInfo.getPostAddress();
		String postCode = userOtherInfo.getPostCode();

		User user = new User();
		user.setUserId(userOtherInfo.getId());
		user.setPostAddress(postAddress);
		user.setPostCode(postCode);
		userDao.update(user);

		UserOtherInfo userOtherInfo2 = userOtherInfoDao.get(userOtherInfo
				.getId());
		if (userOtherInfo2 == null) {
			if (MyStringUtils.isNotNumericSpecifyLength(
					userOtherInfo.getPostCode(), 6)) {
				userOtherInfo.setPostCode(null);
			}
			userOtherInfoDao.insert(userOtherInfo);
		} else {
			userOtherInfoDao.update(userOtherInfo);
		}

	}

	@Override
	public void insertOrUpdateNoUser(UserOtherInfo userOtherInfo) {
		UserOtherInfo userOtherInfo2 = userOtherInfoDao.get(userOtherInfo
				.getId());
		if (userOtherInfo2 == null) {
			if (MyStringUtils.isNotNumericSpecifyLength(
					userOtherInfo.getPostCode(), 6)) {
				userOtherInfo.setPostCode(null);
			}
			userOtherInfoDao.insert(userOtherInfo);
		} else {
			userOtherInfoDao.update(userOtherInfo);
		}
	}
/**
 * add by wangjing
 */
	@Override
	public void insertUpdate (UserOtherInfo userOtherInfo,String weixin,String qq,String remark,String visitType,String admin) {
		User user = new User();
		user.setUserId(userOtherInfo.getId());
		user.setQq(qq);
		user.setWeixin(weixin);
		userDao.update(user);
		UserOtherInfo userOtherInfo2 = userOtherInfoDao.get(userOtherInfo
				.getId());
		if (userOtherInfo2 == null) {
			userOtherInfoDao.insert(userOtherInfo);
		} else {
			userOtherInfoDao.update(userOtherInfo);
		}
		if(MyStringUtils.isNotBlank(remark)){
			UserVisitInfo userVisitInfo=new UserVisitInfo();
			userVisitInfo.setAdmin(admin);
			userVisitInfo.setRemark(remark);
			userVisitInfo.setVisitType(visitType);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			java.util.Date date=new java.util.Date();  
			String str=sdf.format(date); 
			userVisitInfo.setTime(str);
			userVisitInfo.setUserid(userOtherInfo.getId());
			userVisitInfoDao.insert(userVisitInfo);
		}
		if ("激活回访".equals(visitType)) {
			Map<String, Object> params = new HashMap<>();
			params.put("userId", userOtherInfo.getId());
			params.put("customerService",userDao.get(admin).getRealname());
			params.put("activationTime", new Date());
			lostCustomerDao.updateLostCustomer(params);
		}
		
		
	}


	@Override
	public void update(UserOtherInfo userOtherInfo) {
		userOtherInfoDao.update(userOtherInfo);
	}
}