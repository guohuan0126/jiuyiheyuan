package com.duanrong.drpay.business.demand.service;

import java.util.List;
import com.duanrong.drpay.business.demand.model.DateRate;


/**
 * @Description: 每日利率
 * @Author: wangjing
 * @CreateDate: Nov 24, 2014
 */
public interface DateRateService {
	/**
	 * 每日利率
	 * @param days
	 * @return
	 */
	public List<DateRate> getRateDays(int days);
}