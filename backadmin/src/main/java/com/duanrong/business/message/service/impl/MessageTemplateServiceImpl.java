package com.duanrong.business.message.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.message.dao.MessageTemplateDao;
import com.duanrong.business.message.model.MessageTemplate;
import com.duanrong.business.message.service.MessageTemplateService;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {
	@Resource
	private MessageTemplateDao messageTemplateDao;

	@Override
	public PageInfo<MessageTemplate> readSqlLiteForMessageTemplate(int pageNo,
			int pageSize, MessageTemplate messageTemplate) {

		return messageTemplateDao.readSqlLiteForMessageTemplate(pageNo,
				pageSize, messageTemplate);
	}

	@Override
	public List<MessageTemplate> readListMessageTemplate() {

		return messageTemplateDao.readListMessageTemplate();
	}

	@Override
	public MessageTemplate readMessageTemplateById(String id) {

		return messageTemplateDao.readMessageTemplateById(id);
	}

	@Override
	public List<MessageTemplate> readMessageTemplateByName(String name) {

		return messageTemplateDao.readMessageTemplateByName(name);
	}

	@Override
	public int insertMessageTemplate(MessageTemplate messageTemplate) {
		if (messageTemplateDao.readMessageTemplateById(messageTemplate.getId()) != null) {
			return 0;
		} else if (messageTemplateDao.readMessageTemplateByName(
				messageTemplate.getName()).size() > 0) {
			return -1;
		}

		return messageTemplateDao.insertMessageTemplate(messageTemplate);
	}

	@Override
	public int updateMessageTemplate(MessageTemplate messageTemplate) {

		return messageTemplateDao.updateMessageTemplate(messageTemplate);
	}
}
