package com.duanrong.business.message.dao;

import java.util.List;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.message.model.MessageTemplate;

public interface MessageTemplateDao extends BaseDao<MessageTemplate> {
	/**
	 * 短信分页
	 * @param text
	 * @return
	 */
	public PageInfo<MessageTemplate> readSqlLiteForMessageTemplate(int pageNo, int pageSize, MessageTemplate messageTemplate);
	
	/**
	 * 短信列表
	 * @return
	 */
	public List<MessageTemplate> readListMessageTemplate();
	/**  
	 * 
	 * 添加短信
	 * @param text
	 * @return
	 */
	public int insertMessageTemplate(MessageTemplate messageTemplate);
	
	/**
	 * 更新短信
	 * @param text
	 * @return
	 */
	public int updateMessageTemplate(MessageTemplate messageTemplate);
	
	/**
	 * 根据id查询短信
	 * @param id
	 * @return
	 */
	public MessageTemplate readMessageTemplateById(String id);
	
	/**
	 * 根据name查询短信
	 * @param name
	 * @return
	 */
	public List<MessageTemplate> readMessageTemplateByName(String name);
	
}
