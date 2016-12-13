package com.duanrong.business.ruralfinance.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureDebitRecords;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.AgricultureTimelyloansPrepayment;

import java.util.List;


public interface AgricultureTimelyloansPrepaymentService<T> {
	/**
	 * 保存借款人的信息
	 * @param params
	 * @return
	 */
	public int saveAgricultureTimelyloansPrepayment(T obj,String type);

	/**
	 * 通用条件查询
	 * @param map
	 * @return
	 */
	public AgricultureTimelyloansPrepayment findByCondition(Map map);
	
	/**
	 * 上传excle
	 */
	public String uploadFile(CommonsMultipartFile files,HttpServletRequest request,HttpServletResponse response,String uploadExcelId);
	
	/**
	 * 保存到数据库用户记录
	 * @param obj
	 * @param type
	 * @return
	 */
	public int saveExcle(Object obj, String type);
	/**
	 * 查询中金扣款记录
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */

	PageInfo<AgricultureTimelyloansPrepayment> readAgricultureTimelyPrepayment(int pageNo,
			int pageSize, Map<String, Object> params);
	/**
	 * 查询该uploadid上传数据
	 * @param params
	 * @return
	 */
   public List<AgricultureTimelyloansPrepayment> readTimelyPrepaymentbyupload(Map<String, Object> params);
   
 	/**
 	 * 修改项目匹配状态
 	 * 
 	 */
 	public int updateTimelyloanPrepaymentStatus(String id);
	/**
	 * 及时贷处理还款计划
	 * @param uploadId
	 * @return
	 */
	public String updateTimelyLoansPayment(String uploadId);
	}
