package com.duanrong.thirdPartyInterface.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.thirdPartyInterface.model.WDZJLoan;

import base.model.Loan;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved
 * Company : 久亿财富（北京）投资有限公司
 * @Author : 孙铮
 * @CreateTime : 2014-11-12 上午10:15:01 
 * @Description : template com.duanrong.wdzj.service WDZJService.java
 *
 */
public interface WDZJService {
	/**
	 * 
	 * @description 获取当前正在进行投标中的标信息
	 * @author 孙铮
	 * @time 2014-11-12 上午10:47:12
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<WDZJLoan> getProjects(Loan loan);
}