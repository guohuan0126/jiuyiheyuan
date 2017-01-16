package com.duanrong.drpay.jsonservice.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import base.error.ErrorCode;
import base.exception.BankCardException;
import base.exception.ParameterException;
import base.exception.PaymentAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.payment.service.ChannelMatchingService;
import com.duanrong.drpay.jsonservice.handler.RequestParameter;
import com.duanrong.drpay.jsonservice.handler.View;
import com.duanrong.drpay.jsonservice.param.PaymentParameter;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.service.TrusteeshipPaymentService;

@Controller
@RequestMapping(value = "/payment", method = RequestMethod.POST)
public class PaymentController extends BaseController {

	@Resource
	TrusteeshipPaymentService trusteeshipPaymentService;
	@Resource
	ChannelMatchingService channelMatchingService;

	@RequestMapping("/recharge")
	@ResponseBody
	public View recharge(@RequestParameter PaymentParameter parameter,
			String source) throws ParameterException, BankCardException {
		View view = getView(source);
		if (parameter == null || StringUtils.isEmpty(parameter.getUserId())
				|| parameter.getMoney() <= 0
				|| parameter.getRechargeWay() == null) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator reqData = trusteeshipPaymentService.recharge(parameter
					.getUserId(), parameter.getMoney(), parameter
					.getRechargeWay().toString(), source, view.getType());
			if (reqData != null) {
				reqData.setUserDevice(sourceToUserDevice(source));
			}
			view.setData(reqData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 用户提现
	 * 
	 * @param parameter
	 * @param source
	 * @return
	 * @throws UserAccountException
	 */
	@RequestMapping("/withdraw")
	@ResponseBody
	public View withdraw(@RequestParameter PaymentParameter parameter,
			String source) throws UserAccountException {
		View view = getView(source);
		if (StringUtils.isEmpty(parameter.getUserId())
				// FIXME 原判断条件为小于100，测试改为小于0.01
				|| parameter.getMoney() <= 0 ||parameter.getMoney() < 0.01
		) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator reqData = trusteeshipPaymentService.withdraw(
					parameter.getUserId(), parameter.getMoney(), view.getType());
			if (reqData != null) {
				reqData.setUserDevice(sourceToUserDevice(source));
			}
			view.setData(reqData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 根据userId返回匹配最佳充值通道信息
	 * 
	 * @param userId
	 * @param source
	 * @return
	 */
	@RequestMapping("/findChannelByMoney")
	@ResponseBody
	public View findChannelByMoney(@RequestParameter String userId,
			String source) {
		View view = getView(source);
		if (StringUtils.isEmpty(userId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Map<String, Object> respData = trusteeshipPaymentService
					.findChannelByMoney(userId, source);
			view.setData(respData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 根据用户ID 查询绑定通过的银行卡
	 * 
	 * @param userId
	 * @param source
	 * @return
	 */
	@RequestMapping("/getValidBankCardByUserId")
	@ResponseBody
	public View getValidBankCardByUserId(@RequestParameter String userId,
			String source) {
		View view = getView(source);
		if (StringUtils.isEmpty(userId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			List<BankCard> respData = trusteeshipPaymentService.getValidBankCardByUserId(userId);
			view.setData(respData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}
	
	/**
	 * 获取预计到账时间
	 * @param bankCardId 绑卡表ID
	 * @return
	 * @throws PaymentAccountException 
	 */
	@RequestMapping("/getArrivalDate")
	@ResponseBody
	public View getArrivalDate(@RequestParameter PaymentParameter parameter, String source) throws PaymentAccountException {
		View view = getView(source);
		if (StringUtils.isEmpty(parameter.getUserId()) || parameter.getMoney() <= 0
			// FIXME 原判断条件为小于100，测试改为小于0.01
				||parameter.getMoney() < 0.01) {
			view.setError(ErrorCode.ParametersError);
			return view;
		}
		view.setData(trusteeshipPaymentService.getArrivalDate(parameter.getUserId(),parameter.getMoney()));
		view.setError(ErrorCode.SUCCESS);
		return view;
	}
	
	/**
	 * 获取预计到账时间
	 * @param bankCardId 绑卡表ID
	 * @return
	 * @throws PaymentAccountException 
	 * @throws TradeException 
	 * @throws UserAccountException 
	 */
	@RequestMapping("/rechargeWithRollback")
	@ResponseBody
	public View rechargeWithRollback(@RequestParameter String requestNo, String source) throws UserAccountException, TradeException {
		View view = getView(source);
		if (StringUtils.isBlank(requestNo)) {
			view.setError(ErrorCode.ParametersError);
			return view;
		}
		view.setData(trusteeshipPaymentService.rechargeWithRollback(requestNo));
		view.setError(ErrorCode.SUCCESS);
		return view;
	}
}
