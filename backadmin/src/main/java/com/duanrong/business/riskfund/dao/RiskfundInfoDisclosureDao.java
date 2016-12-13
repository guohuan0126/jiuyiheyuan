package com.duanrong.business.riskfund.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.riskfund.model.RiskfundInfoDisclosure;



/**
 * @Description: 信息披露管理
 * @Author: YUMIN
 * @CreateDate: 6 02, 2016
 */
public interface RiskfundInfoDisclosureDao extends BaseDao<RiskfundInfoDisclosure> {
	//查询信息披露列表信息
	public List<RiskfundInfoDisclosure> findList(Map<String,Object> params);
	//删除信息
	public int deleteByPrimaryKey(String id);
}