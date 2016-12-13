package com.duanrong.business.demand.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandforkLoan;
import com.duanrong.business.demand.model.Demandtreasure;
import com.duanrong.business.demand.model.DemandtreasureLoan;




/**
 * @Description: 活期宝投资项目
 * @Author: 
 * @CreateDate:
 */
public interface DemandTreasureLoanDao extends BaseDao<DemandtreasureLoan> {
	public PageInfo<DemandtreasureLoan> readPageInfo(int pageNo, int pageSize,DemandtreasureLoan d);
	public void updateLoan(DemandtreasureLoan d);
	public List<DemandtreasureLoan> exportList(DemandtreasureLoan d);

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
	public double readAssignmentMoney();
	public void updateAssignmentMoneyById(Map<String,Object> params);
	public List<DemandtreasureLoan> readDemandtreasureLoan(Map<String,Object> params);
	public List<DemandtreasureLoan> readDemandtreasureLoanIds();
	public void updateAssignmentStatusByIds(Map<String, Object> params);
	public void updateAssignmentStatusById(Map<String, Object> params);
 
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
	public DemandtreasureLoan readDemandTreasureLoan();
	/**
	 * 查询所有转让资产大于0的资产信息
	 */
	public List<DemandtreasureLoan> readTransferDemandtreasureLoan();
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
	 * 查询是否有重复的fork_id
	 * @param params
	 * @return
	 */
	public DemandforkLoan readforkCounts(Map<String,Object> params);
	/**
	 * 根据农贷提前还款中实际结束时间去修改发行活期宝项目的到期时间
	 * @param params
	 */
	public int updatedemandFinishTime(Map<String,Object> params);
	
}
