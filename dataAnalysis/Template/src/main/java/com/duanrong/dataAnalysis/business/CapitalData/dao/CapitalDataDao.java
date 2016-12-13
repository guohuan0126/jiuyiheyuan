package com.duanrong.dataAnalysis.business.CapitalData.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.duanrong.dataAnalysis.business.CapitalData.model.CapitalData;
import com.duanrong.dataAnalysis.business.CapitalData.model.CapitalDataVO;

public interface CapitalDataDao {
	//查询资金数据
	CapitalData getCapitalData();
	/**条件查询*/
	public List<CapitalDataVO> getCount(Map<String, String> map,int argType);
	public List<CapitalDataVO> getCountByWeek(Map<String, String> map,int argType);
	public List<CapitalDataVO> getCountByMonth(Map<String, String> map,int argType);
	public List<CapitalDataVO> getCountByPhase(Map<String, String> map,int argType);

	
	
}
