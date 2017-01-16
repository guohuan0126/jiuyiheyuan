package com.duanrong.drpay.business.account.service;

import com.duanrong.drpay.business.account.model.Freeze;

/**
 * 冻结/解冻
 * @author xiao
 * @datetime 2016年12月27日 上午10:42:32
 */
public interface FreezeService {

	/**
	 * 保存冻结记录
	 * @param freeze
	 */
	void save(Freeze freeze);
	/**
	 * 通过id获取冻结记录
	 * author guohuan
	 * @param id
	 * 2016年12月27日
	 */
	Freeze get(String id);
	/**
	 * 更新冻结记录
	 * author guohuan
	 * @param freeze
	 * 2016年12月27日
	 */
	void update(Freeze freeze);
	
}
