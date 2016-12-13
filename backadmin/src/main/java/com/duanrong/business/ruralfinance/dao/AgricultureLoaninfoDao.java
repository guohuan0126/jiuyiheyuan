package com.duanrong.business.ruralfinance.dao;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.UploadPrepaymentUser;
import com.duanrong.business.ruralfinance.model.UploadUser;
import com.duanrong.business.ruralfinance.model.UploadZhongjinUser;

public interface AgricultureLoaninfoDao<T> {
	/**
	 * 保存数据借款人信息
	 * @param params
	 * @return
	 */
	public int saveAgricultureLoaninfo(T obj,String type);

	/**
	 * 通用条件查询
	 * @param map
	 * @return
	 */
	public AgricultureLoaninfo findByCondition(Map map);
	/**
	 * 分页显示上传者记录
	 * @param pageNo
	 * @param pageSize
	 * @param type
	 * @return
	 */
	public PageInfo<UploadUser> findUploadUser(int  pageNo,int  pageSize,String type);
	/**
	 * 保存excel
	 * @param obj
	 * @param type
	 * @return
	 */
	public int saveExcle(Object obj, String type);
	
	/**
	 * 获取所有的合同编号
	 * @return
	 */
	public List<String> getAllContractId();
	
	/**
	 * 分页显示上传者记录
	 * @param pageNo
	 * @param pageSize
	 * @param type
	 * @return
	 */
	public PageInfo<UploadZhongjinUser> readUploadZhongjinUser(int  pageNo,int  pageSize,String type);
	/**
	 * 分页显示上传者记录
	 * @param pageNo
	 * @param pageSize
	 * @param type
	 * @return
	 */
	public PageInfo<UploadPrepaymentUser> readUploadPrepaymentUser(int  pageNo,int  pageSize,String type);
}
