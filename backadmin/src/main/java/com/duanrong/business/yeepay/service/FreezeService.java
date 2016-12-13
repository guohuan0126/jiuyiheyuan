package com.duanrong.business.yeepay.service;


import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;

import com.duanrong.business.yeepay.model.Freeze;


/**
 * @Description: 冻结
 * @Author: wangjing
 * @CreateDate: Nov 24, 2014
 */
public interface FreezeService {

	public String freeze(Freeze fundFreeze) throws InsufficientBalance;
	/**
	 * 分页
	 * 
	 * @return
	 */
	public PageInfo<Freeze> readPageInfo(int pageNo, int pageSize);
	public Freeze read(String id);
	public String unfreeze(Freeze fundFreeze) throws InsufficientBalance;
	public void update(Freeze freeze);
	public void dealOverExpectTime(String freezeId);
}