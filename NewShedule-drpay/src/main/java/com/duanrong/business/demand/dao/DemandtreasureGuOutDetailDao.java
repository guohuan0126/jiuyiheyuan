package com.duanrong.business.demand.dao;


import java.util.List;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureGuOutDetail;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;

/**
 * @Description: 活期宝转入
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureGuOutDetailDao extends BaseDao<DemandtreasureGuOutDetail> {

	public int insertBatch(List<DemandtreasureGuOutDetail> list);
	public PageInfo<DemandtreasureGuOutDetail> pageInfo(int pageNo, int pageSize,DemandtreasureGuOutDetail d);
}
