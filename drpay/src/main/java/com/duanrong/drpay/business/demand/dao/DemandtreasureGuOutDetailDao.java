package com.duanrong.drpay.business.demand.dao;


import java.util.List;

import com.duanrong.drpay.business.demand.model.DemandtreasureGuOutDetail;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

/**
 * @Description: 活期宝转入
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureGuOutDetailDao extends BaseDao<DemandtreasureGuOutDetail> {

	public int insertBatch(List<DemandtreasureGuOutDetail> list);
	public PageInfo<DemandtreasureGuOutDetail> pageInfo(int pageNo, int pageSize,DemandtreasureGuOutDetail d);
	public List<DemandtreasureGuOutDetail> findGuOutDetails(DemandtreasureGuOutDetail guOutDetail);
	/**
	 * 获取guOutId对应的所有detail数据
	 * @param guOutId
	 * @return
	 */
	List<DemandtreasureGuOutDetail> getDemandtreasureGuOutDetails(String guOutId);
}