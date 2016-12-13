package com.duanrong.business.ruralfinance.dao;


import java.util.List;
import java.util.Map;

import com.duanrong.business.ruralfinance.model.AgricultureZhongjinbank;



public interface AgricultureZhongjinbankDao {
	
	/**
	 * 通用条件查询
	 * @param map
	 * @return
	 */
	public AgricultureZhongjinbank findByCondition(String bankName);
	
}
