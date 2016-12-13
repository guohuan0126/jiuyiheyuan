package com.duanrong.business.sms.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.sms.dao.UserMessageTemplateDao;
import com.duanrong.business.sms.model.UserMessageTemplate;

@Repository
public class UserMessageTemplateDaoImpl extends
		BaseDaoImpl<UserMessageTemplate> implements UserMessageTemplateDao {

	public UserMessageTemplateDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.sms.mapper.UserMessageTemplateMapper");
	}

}
