package com.duanrong.business.push.dao;

import java.util.List;

import com.duanrong.business.push.model.PushInfoRow;

/**
 * 交易信息查询
 * @author Today
 *
 */
public interface PushInfoRowDao {
  
	/**
	 * 交易记录推送集合
	 * @return
	 */
	public List<PushInfoRow> getPushInvestList();
	
}
