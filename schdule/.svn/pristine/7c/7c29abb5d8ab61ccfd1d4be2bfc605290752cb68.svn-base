package com.duanrong.business.yeepay.service;


import java.io.IOException;

import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.User;
import com.duanrong.business.yeepay.model.Withhold;
import com.duanrong.business.yeepay.model.WithholdBank;


/**
 * @Description: 冻结
 * @Author: wangjing
 * @CreateDate: Nov 24, 2014
 */
public interface WithholdService {

	public PageInfo<Withhold> findPageInfo(int pageNo, int pageSize,Withhold withhold);
	/**
	 * 删除流程信息
	 * @param flowId
	 * @return
	 */
	public boolean withholdDel(String id);
	public boolean insert(Withhold withhold);
	
	
}