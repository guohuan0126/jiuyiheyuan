package com.duanrong.business.message.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.message.dao.MessageTemplateDao;
import com.duanrong.business.message.model.MessageTemplate;
@Repository
public class MessageTemplateDaoImpl extends BaseDaoImpl<MessageTemplate> implements MessageTemplateDao{
	public MessageTemplateDaoImpl()
	{
		this.setMapperNameSpace("com.duanrong.business.message.mapper.MessageTemplateMapper");
	}
	@Override
	public PageInfo<MessageTemplate> readSqlLiteForMessageTemplate(int pageNo, int pageSize, MessageTemplate messageTemplate) {
		PageHelper.startPage(pageNo, pageSize);
		List<MessageTemplate> list = getSqlSession().selectList(
				getMapperNameSpace()+".readSqlLiteForMessageTemplate", messageTemplate);		
		PageInfo<MessageTemplate> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	@Override
	public List<MessageTemplate> readListMessageTemplate() {
		 
		return getSqlSession().selectList(getMapperNameSpace()+".readListMessageTemplate");
	}
	@Override
	public int insertMessageTemplate(MessageTemplate messageTemplate) {
		 
		return getSqlSession().insert(getMapperNameSpace()+".insertMessageTemplate", messageTemplate);
	}
	@Override
	public int updateMessageTemplate(MessageTemplate messageTemplate) {
		 
		return getSqlSession().update(getMapperNameSpace()+".updateMessageTemplate", messageTemplate);
	}
	@Override
	public MessageTemplate readMessageTemplateById(String id) {
		 
		return getSqlSession().selectOne(getMapperNameSpace()+".readMessageTemplateById", id);
	}
	@Override
	public List<MessageTemplate> readMessageTemplateByName(String name) {
		 
		return getSqlSession().selectList(getMapperNameSpace()+".readMessageTemplateByName", name);
	}
}
