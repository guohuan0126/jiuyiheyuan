package com.duanrong.drpay.jsonservice.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import base.error.ErrorCode;
import base.exception.AccountException;
import base.exception.LoanException;
import base.exception.TradeException;

import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.jsonservice.handler.RequestParameter;
import com.duanrong.drpay.jsonservice.handler.View;
import com.duanrong.drpay.jsonservice.param.InvestParameter;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.service.TrusteeshipInvestService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipRepayService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTradeService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTransactionQueryService;

/**
 * 交易服务
 * 
 * @author xiao
 * @datetime 2016年12月9日 下午2:09:12
 */
@Controller
@RequestMapping(value = "/trade", method = RequestMethod.POST)
public class TradeController extends BaseController {

	@Resource
	TrusteeshipTradeService trusteeshipTradeService;

	@Resource
	TrusteeshipInvestService trusteeshipInvestService;

	@Resource
	TrusteeshipRepayService trusteeshipRepayService;
	
	@Resource
	TrusteeshipTransactionQueryService trusteeshipTransactionQueryService;
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public View query(@RequestParameter String requestNo,
			@RequestParameter String type,@RequestParameter int handle, String source) throws TradeException, AccountException {
		View view = getView(source);
		if (StringUtils.isEmpty(requestNo)|| StringUtils.isEmpty(type)) {
			view.setError(ErrorCode.ParametersError);				
		} else {
			try{
				BusinessEnum businessType = BusinessEnum.valueOf(type);
				Generator data = trusteeshipTransactionQueryService.queryTransaction(requestNo, businessType ,handle);
				view.setData(data);
				view.setError(ErrorCode.SUCCESS);
			}catch(IllegalArgumentException e){
				view.setError(ErrorCode.BusinessEnumNotFind);
			}		
		}
		return view;
	}
	
	@RequestMapping(value = "/queryAdmin")
	@ResponseBody
	public View queryAdmin(@RequestParameter String requestNo,
			@RequestParameter String type, String source) throws TradeException, AccountException {
		View view = getView(source);
		if (StringUtils.isEmpty(requestNo)|| StringUtils.isEmpty(type)) {
			view.setError(ErrorCode.ParametersError);				
		} else {
			try{
				BusinessEnum businessType = BusinessEnum.valueOf(type);
				Generator data = trusteeshipTransactionQueryService.queryTransaction(requestNo, businessType);
				view.setData(data);
				view.setError(ErrorCode.SUCCESS);
			}catch(IllegalArgumentException e){
				view.setError(ErrorCode.BusinessEnumNotFind);
			}		
		}
		return view;
	}

	@RequestMapping(value = "/invest")
	@ResponseBody
	public View createInvest(@RequestParameter InvestParameter params,
			String source) throws AccountException, TradeException {
		View view = getView(source);
		if (params == null || StringUtils.isEmpty(params.getUserId())
				|| StringUtils.isEmpty(params.getLoanId())
				|| params.getMoney() <= 0) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator data = trusteeshipInvestService.createInvest(
					params.getUserId(), params.getLoanId(), params.getMoney(),
					params.getRedpacketId(), params.getIsAutoInvest(), view.getType(), params.getInvestSource());
			if (data != null) {
				data.setUserDevice(sourceToUserDevice(source));
			}
//			view.setData(data);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 投标订单补偿
	 * @param investId
	 * @return
	 * @throws TradeException
	 * @throws AccountException
	 */
	@RequestMapping(value = "/investCompensate")
	@ResponseBody
	public View investCompensate(@RequestParameter String investId, String source) throws TradeException, AccountException{
		View view = getView(source);
		if (StringUtils.isEmpty(investId)){
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipInvestService.investCompensate(investId);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}
	
	//@RequestMapping(value = "/investAgain")
	//@ResponseBody
	public View investAgain(@RequestParameter String userId,
			@RequestParameter String id, String source)
			throws AccountException, TradeException {
		View view = getView(source);
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(id)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator data = trusteeshipInvestService.investAgain(id, view.getType());
			if (data != null) {
				data.setUserDevice(sourceToUserDevice(source));
			}
			view.setData(data);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	@RequestMapping(value = "/giveMoneyToBorrower")
	@ResponseBody
	public View giveMoneyToBorrower(@RequestParameter String loanId, String source)
			throws Exception {
		View view = getView(source);
		if (StringUtils.isEmpty(loanId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipTradeService.giveMoneyToBorrower(loanId);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	@RequestMapping(value = "/giveMoneyToBorrowerCompensate")
	@ResponseBody
	public View giveMoneyToBorrowerCompensate(@RequestParameter String investSubloanId, String source) throws TradeException, AccountException {
		View view = getView(source);
		if (StringUtils.isEmpty(investSubloanId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipTradeService.giveMoneyToBorrowerCompensate(investSubloanId);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}
	
	/**
	 * 借款人点击还款，生成本地还款记录，标记为等待调度执行
	 * @param id
	 * @param beforeRepay
	 * @param source
	 */
	@RequestMapping(value = "/repay")
	@ResponseBody
	public View repay(@RequestParameter String id,
			@RequestParameter int beforeRepay, String source)
			throws TradeException, AccountException {
		View view = getView(source);
		if (StringUtils.isEmpty(id)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipRepayService.createSendedRepay(id,
					beforeRepay, view.getType());
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	
	/**
	 * 用于调度自动处理还款
	 * @param repayId
	 * @param source
	 * @return
	 * @throws TradeException
	 * @throws SchedulerException 
	 * @throws AccountException 
	 */
	@RequestMapping(value = "/handleRepay")
	@ResponseBody
	public View handleRepay(@RequestParameter String id, String source)
			throws TradeException, AccountException, SchedulerException {
		View view = getView(source);
		if (StringUtils.isEmpty(id)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipRepayService.handleRepay(id);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 项目流标或者单笔投资流标
	 * 
	 * @throws Exception
	 * @throws AccountException
	 * @throws LoanException
	 */
	@RequestMapping("/bidders")
	@ResponseBody
	public View bidders(@RequestParameter String loanId,
			@RequestParameter String investId, @RequestParameter String type, String source)
			throws LoanException, TradeException, AccountException, Exception {
		View view = this.getView(source);
		if (StringUtils.isBlank(type)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			if (type.equals("loan") && StringUtils.isNoneBlank(loanId)
					|| type.equals("invest")
					&& StringUtils.isNoneBlank(investId)) {
				trusteeshipTradeService.bidders(loanId, investId, type);
				view.setError(ErrorCode.SUCCESS);
			} else {
				view.setError(ErrorCode.ParametersError);
			}
		}
		return view;
	}

	
}
