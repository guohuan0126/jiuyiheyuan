package com.duanrong.business.msg.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.msg.model.Email;


/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : wangjing
 * @CreateTime : 2014-8-28 下午12:45:16
 * @Description : drsoa com.duanrong.business.loan.dao LoanDao.java
 * 
 */
public interface EmailDao extends BaseDao<Email> {
	
	
	public PageInfo<Email> pageInfo(int pageNo, int pageSize, Map map);
	
	
	/**
	 * 获取项目eamil发送历史
	 * @param loanId
	 * @return
	 */
	public List<Email> getEmailByLoadId(String loanId);
	
	/**
	 * 删除项目eamil发送历史
	 * @param loanId
	 * @return
	 */
	public int deleteEmailByLoadId(String loanId);
	
	/**
	 * 根据ID获取Email
	 * @param id
	 * @return
	 */
	public Email getEmailById(String id);
	
	/**
	 * 获取邮件配置信息
	 * @param id
	 * @return
	 */
	public String getConfigById(String id);
	
	/**
	 * 发送邮件
	 * @param email
	 * @return
	 */
	public void sendEamil(Email email);
	
	/**
	 * 发送邮件
	 * @param email
	 * @return
	 */
	public List<Email> getSendEmailByLoanId(String loanId);
}
