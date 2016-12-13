package com.duanrong.business.push.dao;

import java.util.List;

import com.duanrong.business.push.model.PushInvestor;

/**
 * 投资人推送保存
 * @author Today
 *
 */
public interface PushInvestorDao {

	public int insert(List<PushInvestor> list);
	
}
