package com.duanrong.business.platformtransfer.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.platformtransfer.model.PlatformTransfer;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-3 上午10:17:25
 * @Description : NewAdmin com.duanrong.business.platformtransfer.dao
 *              PlatformTransferDao.java
 * 
 */
public interface PlatformTransferDao extends BaseDao<PlatformTransfer> {
	/**
	 * 
	 * @description 根据组合条件查询平台还款记录
	 * @author 孙铮
	 * @time 2015-3-3 下午2:11:31
	 * @param platformTransfer
	 * @return
	 */
	public List<PlatformTransfer> getPlatformTransferGroupCondition(
			PlatformTransfer platformTransfer);
	
	PageInfo<PlatformTransfer> pageInfo(int pageNo, int pageSize, Map map);

	/**
	 * @description TODO
	 * @date 2015-5-7 
	 * @time 上午10:13:36
	 * @author SunZ
	 * @param list
	 */
	public void insertBatch(List<PlatformTransfer> list);
	
	public List<PlatformTransfer> getByLoanId(String loanId, String repayId, String type);


}
