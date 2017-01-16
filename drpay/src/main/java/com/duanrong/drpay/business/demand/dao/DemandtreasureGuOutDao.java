package com.duanrong.drpay.business.demand.dao;



import com.duanrong.drpay.business.demand.model.DemandtreasureGuOut;

import base.dao.BaseDao;

/**
 * @Description: 活期宝转入
 * @Author: 赵磊
 * @CreateDate: 2015-07-17
 */

public interface DemandtreasureGuOutDao extends BaseDao<DemandtreasureGuOut> {
	Double getGuCount();
}
