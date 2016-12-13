package com.duanrong.business.platformtransfer.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.platformtransfer.model.PlatformTransfer;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-3 上午10:01:50
 * @Description : NewAdmin com.duanrong.business.platformtransfer.service
 *              PlatformTransferService.java
 * 
 */
public interface PlatformTransferService {
	/**
	 * 分页
	 * @param map
	 * @return
	 */
	 public PageInfo<PlatformTransfer> readPageInfo(int pageNo, int pageSize,
			 Map map);

	/**
	 * @description TODO
	 * @date 2015-5-7 
	 * @time 上午10:13:03
	 * @author SunZ
	 * @param list
	 */
	public void insertBatch(List<PlatformTransfer> list);


	/**
	 * @param loanId
	 * @author xiao
	 */
	public List<PlatformTransfer> readByLoanId(String loanId, String repayId, String type);
	
	/**
	 * @param loanId
	 * @author xiao
	 */
	public List<PlatformTransfer> readList(PlatformTransfer platformTransfer);
}
