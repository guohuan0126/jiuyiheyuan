package com.duanrong.business.demand.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.newadmin.model.UserCookie;


public interface DemandTreasureLoanService {
	public PageInfo<DemandtreasureLoan> readPageInfo(int pageNo, int pageSize,DemandtreasureLoan d);
	public void update(DemandtreasureLoan demandtreasureLoan);
	public void insert(DemandtreasureLoan demandtreasureLoan);
	public DemandtreasureLoan read(String id);
	public void updateLoan(DemandtreasureLoan d);
	public List<DemandtreasureLoan> readexportList(DemandtreasureLoan d);

	/**
	 * 把金农宝子标的信息批量添加到活期宝资产里面
	 * @param list
	 * @return 
	 */
	public int insertBatch(List<DemandtreasureLoan> list);
	/**
	 * 通过子标的id查询活期宝资产信息
	 * @param id
	 * @return
	 */
	public DemandtreasureLoan readByForkId(String id);
	
	public double readMoneyBydate(String date);
	
	/**
	 * 查询所有转让资产总额
	 */
	public double readAssignmentMoney();
	/**
	 *  更新单个转让金额
	 */
	public void updateAssignmentMoneyById(Map<String,Object> params);
	
	/**
	 * 查询所有未开放的活期宝资产列表
	 */
	public List<DemandtreasureLoan> readDemandtreasureLoan(Map<String,Object> params);
	
	/**
	 * 查询所有id
	 */
	public List<DemandtreasureLoan> readDemandtreasureLoanIds();
	/**
	 * 更新项目的开放状态及金额
	 */
	public void updateAssignmentStatusByIds(Map<String,Object>params);
	
	
	public void updateAssignmentStatusById(Map<String,Object>params);
	
	/**
	 * 查询
	 */
	/**
	 * 通过执行计划的id查询活期宝资产信息
	 * @param id
	 * @return
	 */
	public List<DemandtreasureLoan> readByAvailableId(Map<String, Object> params);
	
	/**
	 * 通过执行计划的id查询活期宝资产信息新增资产
	 * @param id
	 * @return
	 */
	public List<DemandtreasureLoan> readByAvailableIdNew(Map<String, Object> params);

	
	public String insertTreasureLoan(String ids, String userId);
	
	/**
	 * 查询所有转让资产大于0的资产信息
	 */
	public List<DemandtreasureLoan> readTransferDemandtreasureLoan();
	
	/**
	 * 添加及时贷资产到活期宝资产里
	 * @param ids
	 * @param userId
	 * @return
	 */
	public String insertTreasureLoanTimely(String ids, String userId);
	
	/**
	 * 通过农贷的id查询活期宝资产信息
	 * @param id
	 * @return
	 */
	public DemandtreasureLoan readByLoaninfoId(String id);
	/**
	 * 根据及时贷提前还款中实际结束时间去修改发行项目的到期时间
	 * @param params
	 */
	public int updateLoanerinfoTime(Map<String,Object> params);
	
	/**
	 * 根据父id查询子标的信息
	 * @param id
	 * @return
	 */
	public List<DemandtreasureLoan> readDemadfork(String id);
	
	/**
	 * 修改等额本息主标和子标的显示状态
	 * @param params
	 * @return
	 */	
	public int updateDemandDisplay(Map<String,Object> params);
	/**
	 * 添加活期宝资产的公共方法
	 * @param demandtreasureLoan
	 * @param getLoginUser
	 */
	public String addLoanCommon( DemandtreasureLoan demandtreasureLoan,UserCookie getLoginUser);
	/**
	 * 根据农贷提前还款中实际结束时间去修改发行活期宝项目的到期时间
	 * @param params
	 */
	public int updatedemandFinishTime(Map<String,Object> params);
}