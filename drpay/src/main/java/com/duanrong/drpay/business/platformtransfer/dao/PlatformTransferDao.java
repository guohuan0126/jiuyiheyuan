package com.duanrong.drpay.business.platformtransfer.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.drpay.business.platformtransfer.model.PlatformTransfer;


public interface PlatformTransferDao extends BaseDao<PlatformTransfer> {
	
	/**
	 * 批量添加
	 * 
	 * @param list
	 */
	 void insertBatch(List<PlatformTransfer> list);
	 
	 /**
	  * 条件查询
	  * @param platformTransfer
	  * @return
	  */
	 List<PlatformTransfer> getPlatformTransfer(PlatformTransfer platformTransfer);

	 
}