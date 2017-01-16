package com.duanrong.drpay.business.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;

import com.duanrong.drpay.business.user.dao.AuthInfoDao;
import com.duanrong.drpay.business.user.model.AuthInfo;
import com.duanrong.drpay.business.user.service.AuthInfoService;

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

	@Resource
	Log log;

	@Override
	public boolean operateAuthCode(String mobileNumber, String authCode,
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
		if (ai != null) {
			// 校验验证码是否已失效
			if (ai.getDeadline() != null && ai.getDeadline().before(new Date())) {
				// 删除数据库中已失效的验证码
				authInfoDao.delete(ai.getId());
				return false;
			}
			if (StringUtils.equals(mobileNumber, ai.getMobileNumber())
					&& StringUtils.equals(authCode, ai.getAuthCode())
					&& StringUtils.equals(type, ai.getType())) {
				return true;
			}
		}
		return false;
	}
}