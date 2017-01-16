package com.duanrong.drpay.business.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;

import com.duanrong.drpay.business.user.dao.UserDao;
import com.duanrong.drpay.business.user.model.AuthenticationResult;
import com.duanrong.drpay.business.user.model.TrusteeshipUserInfo;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.UserService;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午5:45:05
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	UserDao userDao;

	@Resource
	Log log;
	
	/**
	 * 查询所有的用户
	 */
	public List<User> find() {
		List<User> userList = userDao.findAll();
		return userList;
	}

	/**
	 * 根据ID删除记录
	 * 
	 * @param id
	 */
	public void delete(String id) {
		userDao.delete(id);
	}

	public void update(User user) {
		userDao.update(user);
	}
	/**
	 * 用户注册时验证某些字段是否已经存在 使用规则： 邮箱 isAlreadyExist("eamil","1234567@qq.com") 用户名
	 * isAlreadyExist("username","admin") 电话
	 * isAlreadyExist("mobileNum","13312343210")
	 */
	public boolean isAlreadExist(String fieldName, String value) {

		User user = new User();

		if (StringUtils.equals(fieldName, "email")) {
			user.setEmail(value);
		} else if (StringUtils.equals(fieldName, "username")) {
			user.setUserId(value);
		} else if (StringUtils.equals(fieldName, "mobileNum")) {
			user.setMobileNumber(value);
		}

		User user2 = userDao.verifyLogin(user);
		if (user2 != null) {
			return true;
		}

		return false;
	}

	/**
	 * 根据id获取用户
	 */
	@Override
	public User get(String id) {
		return userDao.get(id);
	}	
	
	/**
	 * 用户添加权限
	 */
	public void addRole(String userId, String role) {
		userDao.addRole(userId, role);
	}
		
	@Override
	public User getUserByMobileNumber(String mobileNumber) {
		return userDao.getUserByMobileNumber(mobileNumber);
	}



	@Override
	public boolean getIdCard(String userId, String idCard) {
		return userDao.verifyIdCard(userId, idCard);
	}


	@Override
	public boolean updateUserMobileNumber(User user) {
		if(user==null||user.getUserId()==null||user.getMobileNumber()==null){
			return false;
		}
		User oldUser = userDao.get(user.getUserId());
		if(user.getMobileNumber().equals(oldUser.getMobileNumber())){
			log.errLog("修改手机号", "新老手机号相同USERID:"+user.getUserId());
			return false;
		}
		log.infoLog("修改手机号","USERID:"+user.getUserId() +",NEW:"+user.getMobileNumber()+",OLD:"+oldUser.getMobileNumber());
		userDao.updateMobileNumber(user);
		return true;
	}

	@Override
	public void updateReferrerMobileNumber(String oldNumber, String newNumber) {
		userDao.updateReferrerMobileNumber(oldNumber ,newNumber);
	}
	
	/**
	 * 通过红包表中的手机号
	 * 
	 * @param redPacket
	 */
	@Override
	public void updateDetailMobileNumber(String oldNumber, String newNumber) {
		userDao.updateDetailMobileNumber(oldNumber, newNumber);
	}

	/**
	 * 修改分享表中的手机号
	 * 
	 * @param redPacket
	 */
	@Override
	public void updateShareMobileNumber(String oldNumber, String newNumber) {
		userDao.updateShareMobileNumber(oldNumber, newNumber);
	}

	@Override
	public boolean hasRoleByUserId(String userId, String roleId) {
		return userDao.hasRoleByUserId(userId, roleId);
	}

	@Override
	public void insertTrusteeshipUserInfo(String userId, String idCardNo,
			String bankCardNo, String requestNo) {
		userDao.insertTrusteeshipUserInfo(userId, idCardNo, bankCardNo, requestNo);
		
	}

	@Override
	public TrusteeshipUserInfo getTrusteeshipUserInfo(String userId, String requestNo) {
		return userDao.getTrusteeshipUserInfo(userId, requestNo);
	}

	@Override
	public AuthenticationResult getAuthenticationResultByStatus(String userId,
			String result) {
		return userDao.getAuthenticationResultByStatus(userId, result);
	}
}