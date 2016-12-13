package com.duanrong.business.transferstation.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.transferstation.model.TransferStation;
import com.duanrong.business.transferstation.model.TransferStationForkLoans;



public interface TransferStationDao extends BaseDao<Loan> {
	/**
	 * 根据条件查询中转站里的项目信息
	 * @param params
	 * @return
	 */
    public PageInfo<TransferStation> findTransferStation(int pageNo,
			int pageSize,Map<String, Object> params);
    /**
     * 中转站的导出
     * @param params
     * @return
     */
    public List<TransferStation> readTransferStationinfo(Map<String, Object> params);
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
	 * 根据主键项目id查询全部项目信息 
	 * @param params
	 * @return
	 */
	public TransferStation findTransferStationById(Map<String, Object> params);
	
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
	 * 根据项目Id删除把项目变为隐藏，dispaly改为0
	 * @param id
	 * @return
	 */
	 public int updateIntermediaries(String id);

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
		 * 根据主键项目id查询和子标id全部项目信息 
		 * @param params
		 * @return
		 */
    public TransferStation findTransferStByforkId(Map<String, Object> params);
    /**
     * 修改等额本息子标打包信息
     * @param params
     * @return
     */
	public int updateForkIntermediaries(Map<String, Object> params);
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
	 * 批量修改中转站项目里面的信息
	 * @param params
	 */
	public int updateBatchIntermediaries(List<TransferStation> list);
	
	/**
	 * 根据主项目id查询中转站项目id
	 * @param loanId
	 * @return
	 */
	public List<TransferStation> readLoanTransferIntermediariesIdByLoadId(String loanId);
	
	
}