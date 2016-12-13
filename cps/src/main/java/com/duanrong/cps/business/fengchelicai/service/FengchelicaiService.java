package com.duanrong.cps.business.fengchelicai.service;

import java.util.List;
import java.util.Map;

import com.duanrong.cps.business.fengchelicai.model.FengchelicaiAccInvest;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiLoan;
import com.duanrong.cps.business.fengchelicai.model.FengchelicaiNotice;
import com.duanrong.cps.business.platform.service.PushService;


public interface FengchelicaiService extends PushService {

	/**
	 *查询标的信息
	 */
	public List<FengchelicaiLoan> getLoanInfo(Map<String,Object>params);

	public Map<String,Object> getaAggregate(String date);

	/**
	 * 风车理财查询投资记录
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param investStatus
	 * @param offset
	 * @param limit
	 * @param investRecordId
	 * @return
	 */
	public List<FengchelicaiAccInvest> getAccInvest(String userId,
			String startTime, String endTime, String investStatus,
			String offset, String limit, String investRecordId);

	/**
	 * 风车理财获取公告
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<FengchelicaiNotice> getNoticeInfo(String page, String limit);

	public Map<String, Object> getAccInfo(String userId);
	
	
}
