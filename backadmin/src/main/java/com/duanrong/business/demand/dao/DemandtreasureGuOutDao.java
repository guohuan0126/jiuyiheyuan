package com.duanrong.business.demand.dao;



import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureGuOut;

/**
 * @Description: 活期宝转入
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureGuOutDao extends BaseDao<DemandtreasureGuOut> {
	PageInfo<DemandtreasureGuOut> readPageInfo(int pageNo, int pageSize);
	Double readGuCount();
}
