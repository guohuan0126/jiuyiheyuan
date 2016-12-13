package com.duanrong.business.yeepay.dao;

import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.yeepay.model.TransactionAuthorization;

/**
 * 通用转账授权
 * 
 * @author Lin Zhiming
 * @version May 27, 2015 11:01:08 AM
 */
public interface TransactionAuthorizationDao extends BaseDao<TransactionAuthorization> {
	public PageInfo<TransactionAuthorization> pageInfo(int pageNo,
			int pageSize,Map map);
}