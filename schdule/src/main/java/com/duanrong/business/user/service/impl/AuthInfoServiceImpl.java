package com.duanrong.business.user.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.MyStringUtils;
import base.exception.AuthInfoOutOfDateException;
import base.exception.NoMatchingObjectsException;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.dao.AuthInfoDao;
import com.duanrong.business.user.model.AuthInfo;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.service.AuthInfoService;
import com.duanrong.util.IdUtil;

/**
 * 验证码实现类
 * 
 * @author 尹逊志
 * @date 2014-8-28下午6:28:52
 */
@Service
public class AuthInfoServiceImpl implements AuthInfoService {
	@Resource
	AuthInfoDao authInfoDao;

	@Override
	public boolean sendAuthInfo(String mobileNumber, String type) {

		if (MyStringUtils.authAboutFormat(mobileNumber, type)) {

			// 保存验证码到数据库
			AuthInfo authInfo = createAuthInfo(mobileNumber, type);

			// 发送验证码
			String content = new StringBuffer("尊敬的用户：您在短融网上的验证码是 ")
					.append(authInfo.getAuthCode())
					.append("。请及时输入，工作人员不会向您索取，请勿泄漏。【短融网】").toString();

			

			return true;
		}

		return false;
	}

	@Override
	public AuthInfo createAuthInfo(String mobileNumber, String type) {
		AuthInfo ai = new AuthInfo();
		ai.setId(IdUtil.randomUUID());
		ai.setAuthCode(RandomStringUtils.random(6, false, true));
		ai.setMobileNumber(mobileNumber);
		Date generationTime = new Date();
		ai.setGenerationTime(generationTime);
		ai.setStatus("0");
		ai.setDeadline(getDeadline(generationTime));
		ai.setType(type);
		authInfoDao.insert(ai);
		return ai;
	}

	/**
	 * 计算验证码失效日期
	 * 
	 * @param generationTime
	 */
	private Date getDeadline(Date generationTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(generationTime);
		cal.add(Calendar.MINUTE, 30);
		Date deadline = cal.getTime();
		return deadline;
	}

	@Override
	public String verifyAuthInfo(String mobileNumber, String authCode,
			String type) throws NoMatchingObjectsException,
			AuthInfoOutOfDateException {

		if (StringUtils.isBlank(mobileNumber) || StringUtils.isBlank(authCode)
				|| StringUtils.isBlank(type)) {
			throw new NoMatchingObjectsException(AuthInfo.class,
					"mobileNumber:" + mobileNumber + " authCode:" + authCode
							+ " type:" + type);
		} else {
			List<AuthInfo> list = authInfoDao.getAuthInfo(mobileNumber,
					authCode, type);

			AuthInfo ai = null;

			if (list.size() == 1) {
				ai = list.get(0);
			}

			String dbMobileNumber;
			String dbAuthCode;
			String dbType;

			if (ai != null) {

				// 校验验证码是否已失效
				if (ai.getDeadline() != null
						&& ai.getDeadline().before(new Date())) {
					// 删除数据库中已失效的验证码
					authInfoDao.delete(ai.getId());
					throw new AuthInfoOutOfDateException("mobileNumber:"
							+ mobileNumber + " authCode:" + authCode + " type:"
							+ type);
				}

				dbMobileNumber = ai.getMobileNumber();
				dbAuthCode = ai.getAuthCode();
				dbType = ai.getType();

				if (mobileNumber.equals(dbMobileNumber)
						&& authCode.equals(dbAuthCode) && type.equals(dbType)) {
					authInfoDao.update(ai);
					return "success";
				}
			}

			throw new NoMatchingObjectsException(AuthInfo.class,
					"mobileNumber:" + mobileNumber + " authCode:" + authCode
							+ " type:" + type);

		}
	}

	@Override
	public Boolean verifyAuthCode(String mobileNumber, String authCode,
			String type) {

		if (StringUtils.isBlank(mobileNumber) || StringUtils.isBlank(authCode)
				|| StringUtils.isBlank(type)) {
			return false;
		}

		List<AuthInfo> list = authInfoDao.getAuthInfo(mobileNumber, authCode,
				type);

		AuthInfo ai = null;

		if (list.size() == 1) {
			ai = list.get(0);
		}

		String dbMobileNumber;
		String dbAuthCode;
		String dbType;

		if (ai != null) {

			// 校验验证码是否已失效
			if (ai.getDeadline() != null && ai.getDeadline().before(new Date())) {
				// 删除数据库中已失效的验证码
				authInfoDao.delete(ai.getId());
				return false;
			}

			dbMobileNumber = ai.getMobileNumber();
			dbAuthCode = ai.getAuthCode();
			dbType = ai.getType();

			if (StringUtils.equals(mobileNumber, dbMobileNumber)
					&& StringUtils.equals(authCode, dbAuthCode)
					&& StringUtils.equals(type, dbType)) {
				return true;
			}
		}

		return false;

	}

	@Override
	public PageInfo<AuthInfo> findPageInfo(int pageNo, int pageSize, Map map) {
		return authInfoDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public List<AuthInfo> getAuthNum(Map map) {
		return authInfoDao.getAuthNum(map);
	}

	@Override
	public AuthInfo getAuthInfoById(String id) {
		return authInfoDao.get(id);
	}

	@Override
	public void updateauth(AuthInfo authInfo) {
		authInfoDao.updateauth(authInfo);
	}

}