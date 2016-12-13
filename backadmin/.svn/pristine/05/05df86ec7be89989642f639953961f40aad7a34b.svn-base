package com.duanrong.business.msg.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.msg.model.Email;

/**
 * @Description: 邮件处理
 * @Author: wangjing
 * @CreateDate: Nov 24, 2014
 */
public interface EmailService {

	/**
	 * 分页
	 * @param map
	 * @return
	 */
	public PageInfo<Email> readPageInfo(int pageNo, int pageSize,
			 Map map);
	
	/**
	 * 获取项目Email发送历史记录
	 * @param loanID
	 * @return
	 */
	public List<Email> readEmailByLoan(String loanId);
	

	/**
	 * 获取项目Email发送历史记录
	 * @param loanID
	 * @return
	 */
	public boolean deleteEmailByLoan(String loanId);
	
	/**
	 * 插入email历史记录
	 * @param email
	 */
	public void insertEmial(Email email);
	
	/**
	 * 获取Email
	 * @param id
	 * @return
	 */
	public Email readEmailById(String id);
	
	/**
	 * 发送Email
	 * @param id
	 * @return
	 */
	public String sendEmail(String loanId, String userId);
	
	/**
	 * 获取项目未发送Eamil
	 * @param loanID
	 * @return
	 */
	public List<Email> readSendEmailByLoan(String loanId);
}