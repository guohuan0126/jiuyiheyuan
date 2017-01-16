package com.duanrong.drpay.trusteeship.service;

import base.exception.DataAlreadExistException;
import base.exception.DemandException;
import base.exception.PlatformAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

/*
 * 活期宝业务服务
 */
public interface TrusteeshipDemandService {

	/**
	 * 活期宝转出（前端调用）
	 * 
	 * @param userId
	 * @param money
	 * @throws UserInfoException
	 */
	void demandOut(String userId, Double money) throws UserInfoException;

	/**
	 * 活期宝，弹出提示语
	 * 
	 * @param userId
	 * @param money
	 * @return
	 * @throws UserInfoException 
	 */
	String demandOutMessage(String userId, Double money) throws UserInfoException;

	/**
	 * 活期宝转出增强型校验
	 * 
	 * @throws TradeException
	 */
	void demandTransferOutCheck() throws TradeException;

	/**
	 * 活期宝转出申请
	 * 
	 * @throws TradeException
	 * @throws DataAlreadExistException
	 * @throws PlatformAccountException
	 * @throws UserAccountException
	 */
	void demandTransferOutApply() throws TradeException, UserAccountException,
			PlatformAccountException, DataAlreadExistException;
	

	/**
	 * 活期宝转出确认
	 * 
	 * @param requestNo
	 * @throws TradeException
	 */
	void demandTransferOutConfirm(String guOutId) throws TradeException;

	/**
	 * 活期宝转出撤销处理
	 * @param guOutId
	 * @throws DemandException 
	 */
	void demandOutCancel(String guOutId) throws DemandException;
}
