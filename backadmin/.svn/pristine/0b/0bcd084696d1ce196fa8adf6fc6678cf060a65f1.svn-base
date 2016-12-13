package com.duanrong.business.ruralfinance.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.UploadPrepaymentUser;
import com.duanrong.business.ruralfinance.model.UploadUser;
import com.duanrong.business.ruralfinance.model.UploadZhongjinUser;


public interface AgricultureLoaninfoService<T> {
	
	/**
	 * 保存借款人的信息
	 * @param params
	 * @return
	 */
	public int saveAgricultureLoaninfo(T obj,String type);


	/**
	 * 通用条件查询
	 * @param map
	 * @return
	 */
	public AgricultureLoaninfo readByCondition(Map map);
	/**
	 * 上传excle
	 */
	public String uploadFile(CommonsMultipartFile files,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 分页 
	 * @param pageNo
	 * @param pageSize
	 * @param type
	 * @return
	 */
	public PageInfo<UploadUser> readUploadUser(int  pageNo,int  pageSize,String type);
	/**
	 * 保存到数据库用户记录
	 * @param obj
	 * @param type
	 * @return
	 */
	public int saveExcle(Object obj, String type);
	/**
	 * 获取所有的合同编号
	 * @return
	 */
	public List<String> readAllContractId();
	/**
	 * 分页 
	 * @param pageNo
	 * @param pageSize
	 * @param type
	 * @return
	 */

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
