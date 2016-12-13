package com.duanrong.business.bankinfo.dao;

import java.util.List;
import java.util.Map;
import com.duanrong.business.bankinfo.model.BankInfo;
import base.dao.BaseDao;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : YUMIN
 * @CreateTime : 2016-4-28 下午5:56:31
 * @Description : 银行卡信息管理
 * 
 */
public interface BankInfoDao extends BaseDao<BankInfo> {
	/**
	 * 获取银行卡信息列表
	 * @param bankInfo
	 * @return
	 */
	public List<BankInfo> getBankInfo(BankInfo bankInfo);
	/**
	 * 银行卡禁用，启用
	 * @param params
	 * @return
	 */
	public int updateBankStatue(Map<String, Object> params);
	
	public BankInfo readByid(String id);
	
	
}
