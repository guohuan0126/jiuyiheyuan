package com.duanrong.business.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import util.DateUtilPlus;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.dao.UserLoginLogDao;
import com.duanrong.business.user.model.UserLoginLog;
import com.duanrong.business.user.service.UserLoginLogService;
import com.duanrong.util.IdUtil;

@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {

	@Resource
	UserLoginLogDao userLoginLogDao;

	@Override
	public void insert(UserLoginLog userLoginLog) {
		if (StringUtils.isNotBlank(userLoginLog.getUserId())) {
			userLoginLog.setId(IdUtil.randomUUID());
			userLoginLogDao.insert(userLoginLog);
		}
	}

	@Override
	public UserLoginLog getByUserId(String userId) {
		return userLoginLogDao.getByUserId(userId);
	}

	@Override
	public String getLastLoginTime(String userId) {
		// FIXME：需要测试
		List<Date> times = userLoginLogDao.getLastLoginTime(userId);

		if (times.size() <= 1) {
			return "firstTimeLogin";
		}

		Date time1 = times.get(0);
		Date time2 = times.get(1);
		Date now = new Date();

		// 比较最近日期是否跟现在时间是同一天
		if (DateUtils.isSameDay(time1, now)) {
			if (DateUtils.isSameDay(time1, time2)) {
				return "today";
			} else if (DateUtilPlus.getDay(time1, time2) == 1) {
				return "yesterday";
			}
		} else {
			// 现在时间与time2相减，得到天数
			if (DateUtilPlus.getDay(now, time2) == 1) {
				return "yesterday";
			}
		}

		return DateUtilPlus.formatDateTimeLoaclString(time2);
	}

	@Override
	public PageInfo<UserLoginLog> pageLite(int pageNo, int pageSize,
			UserLoginLog loginLog) {
		// TODO Auto-generated method stub
		return this.userLoginLogDao.pageLite(pageNo, pageSize, loginLog);
	}
}