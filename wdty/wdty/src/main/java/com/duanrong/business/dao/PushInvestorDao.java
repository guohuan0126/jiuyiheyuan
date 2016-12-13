package com.duanrong.business.dao;

import java.util.List;

import com.duanrong.business.model.PushInvestor;

/**
 * 投资人推送保存
 * @author Today
 *
 */
public interface PushInvestorDao {

	public int insert(List<PushInvestor> list);
	
}
