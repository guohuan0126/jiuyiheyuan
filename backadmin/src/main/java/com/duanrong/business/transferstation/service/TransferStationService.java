package com.duanrong.business.transferstation.service;


import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.transferstation.model.TransferStation;
import com.duanrong.business.transferstation.model.TransferStationForkLoans;
import com.duanrong.newadmin.model.UserCookie;



/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿恒远科技有限公司
 * 
 * @Author : YuMin
 * @CreateTime : 2016-9-1 下午12:33:30
 * @Description :中转数据处理业务
 * 
 */


public interface TransferStationService {
	/**
	 * 根据条件查询中转站里的项目信息
	 * @param params
	 * @return
	 */
   public PageInfo<TransferStation> readTransferStation(int pageNo,
			int pageSize,Map<String, Object> params);
   /**
    * 中转站的导出
    * @param params
    * @return
    */
   public List<TransferStation> readTransferStationinfo(Map<String, Object> params);
	
	/**
	 * 从中转站表里删除，并且把车辆信息和图片信息一起删除
	 * @param id
	 * @return
	 */
   public int delTransferLoan(String id);
	/**
	 * 根据项目Id删除车贷中间表里的数据与主表保持一致
	 * @param id
	 * @return
	 */
   public int delVeIntermediaries(String loanId);
	/**
	 * 根据项目Id删除图片中间表里的数据与主表保持一致
	 * @param id
	 * @return
	 */
	public int delPicsIntermediaries(String loanId);
	/**
	 * 分配到活期资产里面
	 */
	public String allocationDemandTreasure(String[] ids,UserCookie getLoginUser);
	/**
	 * 修改中转站项目里面的信息
	 * @param params
	 */
	public int updateTransferStation(Map<String,Object> params);
	
	/**
	 * 批量修改中转站项目里面的信息
	 * @param params
	 */
	public int updateBatchTransferStation(Map<String,Object> params);
	/**
	 * 分配到定期里面的处理
	 */
	public String allocationLoan(String[] ids,UserCookie getLoginUser,String type,String institutionName,String newSubject);
	
	boolean verifyLoanAttrs(TransferStation transferStation, UserCookie getLoginUser,String type,String loanId,String newSubject,Integer i,Integer j,Integer loanNumber);
	
   /**
	 * 根据项目Id删除把项目变为隐藏，dispaly改为0
	 * @param id
	 * @return
	 */
	 public int updateIntermediaries(String id);
	 /**
	  * 等额本息的批量拆标
	  * @param ids
	  * @return
	  */
	 public boolean TransferStationForkLoans(TransferStation transferStation);
	 
	/**
	 * 等额本息添加子标
	 * @param ForkLoans
	 */
	 public void insert(TransferStationForkLoans transferStationForkLoans);
	 /**
	  * 查询等额本息子标列表
	  * @param pageNo
	  * @param pageSize
	  * @param params
	  * @return
	  */
	 PageInfo<TransferStationForkLoans> readTransferStationForkLoans(int pageNo,
				int pageSize, Map<String, Object> params);
	 /**
	  * 等额本息子标打包多人多车
	  * @param ids
	  * @param getLoginUser
	  * @param type
	  * @param institutionName
	  * @param deadline 
	  * @param forkIds
	  * @return
	  */
	public String allocationForkLoan(String[] ids,UserCookie getLoginUser,String type,String institutionName,String deadline,String[] forkIds,Double money,String company,String newSubject,String vehicleRemark);
	
	/**
	 * 单人单车打包成多人多车
	 * @param ids
	 * @param getLoginUser
	 * @param type
	 * @param institutionName
	 * @param deadline
	 * @param money
	 * @param company
	 * @param newSubject
	 * @return
	 */
	public String allocationVehicles(String[] ids,UserCookie getLoginUser,String type,String institutionName,String deadline,Double money,String company,String newSubject,String vehicleRemark);
	/**
	 * 根据主项目id查询子项目id
	 * @param loanId
	 * @return
	 */
	public List<TransferStationForkLoans> readLoanForkIntermediariesIdByLoadId(String loanId);
	
	/**
     * 批量修改等额本息子标打包信息
     * @param params
     * @return
     */
	public int updateBatchForkIntermediaries(List<TransferStationForkLoans> list);
	/**
     * 批量修改中转站项目信息
     * @param params
     * @return
     */
	public int updateBatchIntermediaries(List<TransferStation> list);
	
	/**
	 * 根据主项目id查询中转站项目id
	 * @param loanId
	 * @return
	 */
	public List<TransferStation> readLoanTransferIntermediariesIdByLoadId(String loanId);
	/**
	 * 获取借款项目车贷最大的编号
	 * @return
	 */
	public int readLoanNumber();
	/**
	 * 根据主键项目id查询全部项目信息 
	 * @param params
	 * @return
	 */
	public TransferStation readTransferStationById(Map<String, Object> params);	
	 /**
		 * 根据主键项目id查询和子标id全部项目信息 
		 * @param params
		 * @return
		 */
   public TransferStation readTransferStByforkId(Map<String, Object> params);
}
