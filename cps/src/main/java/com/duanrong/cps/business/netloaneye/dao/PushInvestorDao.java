package com.duanrong.cps.business.netloaneye.dao;

import java.util.List;

import com.duanrong.cps.business.netloaneye.model.PushInvestor;



/**
 * 投资人推送保存
 * @author Today
 *
 */
public interface PushInvestorDao {

	public int insert(List<PushInvestor> list);
	
}