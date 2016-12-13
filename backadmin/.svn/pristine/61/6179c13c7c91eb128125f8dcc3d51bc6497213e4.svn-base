package com.duanrong.business.message.service;

import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.business.message.model.MessageTemplate;

public interface MessageTemplateService {
	/**
	 * 短信模板分页
	 * 
	 * @param MessageTemplate
	 * @return
	 */
	public PageInfo<MessageTemplate> readSqlLiteForMessageTemplate(int pageNo,
			int pageSize, MessageTemplate messageTemplate);

	/**
	 * 短信模板列表
	 * 
	 * @return
	 */
	public List<MessageTemplate> readListMessageTemplate();

	/**
	 * 
	 * 添加短信模板
	 * 
	 * @param MessageTemplate
	 * @return
	 */
	public int insertMessageTemplate(MessageTemplate messageTemplate);

	/**
	 * 更新短信模板
	 * 
	 * @param MessageTemplate
	 * @return
	 */
	public int updateMessageTemplate(MessageTemplate messageTemplate);

	/**
	 * 根据id查询短信模板
	 * 
	 * @param id
	 * @return
	 */
	public MessageTemplate readMessageTemplateById(String id);

	/**
	 * 根据name查询短信模板
	 * 
	 * @param name
	 * @return
	 */
	public List<MessageTemplate> readMessageTemplateByName(String name);

}
