package com.duanrong.drpay.business.demand.dao;

import java.util.List;
import com.duanrong.drpay.business.demand.model.DateRate;

import base.dao.BaseDao;

/**
 * @Description: 每日利率
 * @Author: wangjing
 * @CreateDate: Nov 24, 2014
 */
public interface DateRateDao extends BaseDao<DateRate> {
	public List<DateRate> getRateDays(int days);
}
