package com.duanrong.dataAnalysis.business.CapitalData.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.dataAnalysis.business.CapitalData.dao.CapitalDataDao;
import com.duanrong.dataAnalysis.business.CapitalData.model.CapitalData;
import com.duanrong.dataAnalysis.business.CapitalData.model.CapitalDataVO;
import com.duanrong.dataAnalysis.business.CapitalData.service.CapitalDataService;
@Service
public class CapitalDataServiceImpl implements CapitalDataService {

	@Resource
	private CapitalDataDao capitalDataDao;
	@Override
	//查询资金数据
	public CapitalData getCapitalData() {
		
		return capitalDataDao.getCapitalData();
	}
	@Override
	public List<CapitalDataVO> getCount(Map<String, String> map,int argType) {
		
		List<CapitalDataVO> capitalDataVOsList = capitalDataDao.getCount(map,argType);
		return capitalDataVOsList;
	}
	
	@Override
	public List<CapitalDataVO> getCountByWeek(Map<String, String> map,
			int argType) {
		
		List<CapitalDataVO> capitalDataVOsList = capitalDataDao.getCountByWeek(map, argType);
		return capitalDataVOsList;
	}
	@Override
	public List<CapitalDataVO> getCountByMonth(Map<String, String> map,
			int argType) {
		
		List<CapitalDataVO> capitalDataVOsList = capitalDataDao.getCountByMonth(map, argType);
		return capitalDataVOsList;
	}
	@Override
	public List<CapitalDataVO> getCountByPhase(Map<String, String> map,
			int argType) {
		List<CapitalDataVO> capitalDataVOsList = capitalDataDao.getCountByPhase(map, argType);
		
		return capitalDataVOsList;
	}

}
