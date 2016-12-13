package com.duanrong.business.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.dao.InformationDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.Information;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.ShortUrlGenerator;

@Service
public class InformationServiceImpl implements InformationService {

	@Resource
	InformationDao informationDao;

	@Resource
	UserDao userDao;

	public List<Information> readInformationByUserId(String userId) {
		return informationDao.getInformationByUserId(userId);
	}

	public Long readNotReadByUserId(String userId) {
		return informationDao.getNotReadByUserId(userId);
	}

	public void updateRead(String userId) {
		informationDao.updateRead(userId);
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 */
	public PageData<Information> readPaging(int pageNo, int pageSize,
			Information information) {
		return informationDao.findPaging(pageNo, pageSize, information);
	}

	@Override
	public void sendInformation(String userId, String title, String content) {
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(content)) {
			User user = userDao.get(userId);
			if (user != null) {
				Information information = new Information();
				information.setTitle(title);
				information.setContent(content);
				information.setId(IdGenerator.randomUUID());
				information.setUsername(userId);

				// 1 为显示
				information.setStatus((byte) 1);
				information.setIsRead((byte) 0);
				information.setTime(new Date());
				informationDao.insert(information);
			}
		}
	}

	@Override
	public PageInfo<Information> readPageInfo(int pageNo, int pageSize, Map map) {
		return informationDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public void sendInformation(String investUserID, String title,
			String sendContent, Date date) {
		if (StringUtils.isNotBlank(investUserID)
				&& StringUtils.isNotBlank(sendContent)) {
			User user = userDao.get(investUserID);
			if (user != null) {
				String id = ShortUrlGenerator
						.shortUrl(IdGenerator.randomUUID())
						+ ShortUrlGenerator.shortUrl(IdGenerator.randomUUID());
				Information information = new Information();
				information.setTitle(title);
				information.setContent(sendContent);
				information.setId(id);
				information.setUsername(investUserID);
				information.setTitle(information.getTitle());
				information.setContent(information.getContent());

				// 1 为显示
				information.setStatus((byte) 1);
				information.setIsRead((byte) 0);
				information.setTime(date);
				informationDao.insert(information);
			}
		}
	}
}
