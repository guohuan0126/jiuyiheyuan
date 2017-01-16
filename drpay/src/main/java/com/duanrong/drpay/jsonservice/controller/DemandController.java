package com.duanrong.drpay.jsonservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import base.error.ErrorCode;
import base.exception.DataAlreadExistException;
import base.exception.DemandException;
import base.exception.PlatformAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.jsonservice.handler.RequestParameter;
import com.duanrong.drpay.jsonservice.handler.View;
import com.duanrong.drpay.jsonservice.param.DemandParameter;
import com.duanrong.drpay.trusteeship.service.TrusteeshipDemandService;

/**
 * 活期宝业务服务即可
 * 
 * @author zhaoqin
 * @datetime 2016年12月9日 下午2:09:12
 */
@Controller
@RequestMapping(value = "/demand", method = RequestMethod.POST)
public class DemandController extends BaseController {

	@Resource
	TrusteeshipDemandService trusteeshipDemandService;

	/**
	 * 活期宝转出增强型校验（管理后台）
	 * 
	 * @throws TradeException
	 */
	@RequestMapping("/demandTransferOutCheck")
	@ResponseBody
	public View demandTransferOutCheck(HttpServletRequest request) throws TradeException {
		// 消费者IP鉴权
		View view = getPcView();
		if (!authenticationIP(request, BusinessEnum.demand_out)) {
			view.setError(ErrorCode.REFUSEIP);
			return view;
		}
		trusteeshipDemandService.demandTransferOutCheck();
		view.setError(ErrorCode.SUCCESS);
		return view;
	}

	/**
	 * 活期宝转出赎回预处理(后台管理)
	 * 
	 * @return
	 * @throws TradeException
	 * @throws DataAlreadExistException
	 * @throws PlatformAccountException
	 * @throws UserAccountException
	 */
	@RequestMapping("/demandTransferOutApply")
	@ResponseBody
	public View demandTransferOutApply(HttpServletRequest request) throws TradeException,
			UserAccountException, PlatformAccountException,
			DataAlreadExistException {
		// 消费者IP鉴权
		View view = getPcView();
//		if (!authenticationIP(request, BusinessEnum.demand_out)) {
//			view.setError(ErrorCode.REFUSEIP);
//			return view;
//		}
		trusteeshipDemandService.demandTransferOutApply();
		view.setError(ErrorCode.SUCCESS);
		return view;
	}

	/**
	 * 活期宝转出确认
	 * 
	 * @param requestNo
	 * @return
	 * @throws TradeException
	 */
	@RequestMapping("/demandTransferOutConfirm")
	@ResponseBody
	public View demandTransferOutConfirm(@RequestParameter String guOutId, HttpServletRequest request)
			throws TradeException {
		//消费者IP鉴权
		View view = getPcView();
//		if (!authenticationIP(request, BusinessEnum.demand_out)) {
//			view.setError(ErrorCode.REFUSEIP);
//			return view;
//		}
		if (StringUtils.isBlank(guOutId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipDemandService.demandTransferOutConfirm(guOutId);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 活期宝转出撤销（只有guOut中所有detail都未处理才可调用）
	 * @param guOutId
	 * @param request
	 * @return
	 * @throws DemandException 
	 */
	@RequestMapping("/demandOutCancel")
	@ResponseBody
	public View demandOutCancel(@RequestParameter String guOutId, HttpServletRequest request) throws DemandException{
		View view = getPcView();
//		if (!authenticationIP(request, BusinessEnum.demand_out)) {
//			view.setError(ErrorCode.REFUSEIP);
//			return view;
//		}
		if (StringUtils.isBlank(guOutId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipDemandService.demandOutCancel(guOutId);
		}
		return view;
	}
	
	/**
	 * 活期宝转出申请（前端调用）
	 * 
	 * @param userId
	 * @param money
	 * @param source
	 * @return
	 * @throws UserInfoException
	 */
	@RequestMapping("/demandOut")
	@ResponseBody
	public View demandOut(@RequestParameter DemandParameter paramter, String source) throws UserInfoException {
		View view = getView(source);
		if (paramter == null || StringUtils.isBlank(paramter.getUserId())
				|| paramter.getMoney() <= 0) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipDemandService.demandOut(paramter.getUserId(),
					paramter.getMoney());
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 活期宝，弹出提示语
	 * 
	 * @param userId
	 * @param money
	 * @param source
	 * @return
	 * @throws UserInfoException
	 */
	@RequestMapping("/demandOutMessage")
	@ResponseBody
	public View demandOutMessage(@RequestParameter DemandParameter paramter, String source) throws UserInfoException{
		View view = getView(source);
		if (paramter == null || StringUtils.isBlank(paramter.getUserId())
				|| paramter.getMoney() <= 0) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Map<String, String> map = new HashMap<>();
			String message = trusteeshipDemandService.demandOutMessage(
					paramter.getUserId(), paramter.getMoney());
			map.put("message", message);
			view.setData(map);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

}
