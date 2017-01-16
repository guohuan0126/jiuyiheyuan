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
import base.exception.PlatformAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.jsonservice.handler.RequestParameter;
import com.duanrong.drpay.jsonservice.handler.View;
import com.duanrong.drpay.jsonservice.param.TransferParameter;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.service.TrusteeshipFundTransferService;

/**
 * 转账服务 平台奖励 验密扣费
 * 
 * @author xiao
 * @datetime 2016年12月26日 上午11:31:45
 */
@Controller
@RequestMapping(value = "/trans", method = RequestMethod.POST)
public class TransferController extends BaseController {

	@Resource
	TrusteeshipFundTransferService trusteeshipFundTransferService;

	/**
	 * 平台奖励发送
	 * @throws Exception 
	 */
	@RequestMapping("/reward.do")
	@ResponseBody
	public View reward(@RequestParameter TransferParameter reward,
			HttpServletRequest request) throws Exception {
		View view = this.getView();
		/*if (authenticationIP(
				request,
				reward.getBusinessType() == null ? BusinessEnum.reward : reward
						.getBusinessType())) {*/
			if (reward == null || reward.getMoney() <= 0
					|| StringUtils.isBlank(reward.getRequestNo())
					|| StringUtils.isBlank(reward.getUserId()))
				view.setError(ErrorCode.ParametersError);
			else {
				Map<String, String> map = new HashMap<>();
				map.put("status", trusteeshipFundTransferService
						.sendRewardDirect(reward.getUserId(),
								reward.getRequestNo(), reward.getMoney(),
								reward.getBusinessType(), reward.getLoanId(),
								reward.getInfo(), reward.getRemarks()));
				view.setData(map);
				view.setError(ErrorCode.SUCCESS);
			}
		/*} else {
			view.setError(ErrorCode.REFUSEIP);
		}*/
		return view;
	}

	/**
	 * 平台奖励发送确认
	 * 
	 * @throws UserAccountException
	 * @throws TradeException
	 * @throws UserInfoException
	 * @throws DataAlreadExistException
	 * @throws PlatformAccountException
	 */
	//@RequestMapping("/rewardConfirm.do")
	//@ResponseBody
	@Deprecated
	public View reward(@RequestParameter String requestNo,
			@RequestParameter String info, HttpServletRequest request)
			throws UserAccountException, TradeException,
			PlatformAccountException, DataAlreadExistException,
			UserInfoException {
		View view = this.getView();
		/*if (authenticationIP(request, BusinessEnum.reward_confirm)) {*/
			if (StringUtils.isBlank(requestNo)
					|| StringUtils.isBlank(info))
				view.setError(ErrorCode.ParametersError);
			else {
				Map<String, String> map = new HashMap<>();
				map.put("status", trusteeshipFundTransferService
						.sendRewardConfirm("", 0D, requestNo,
								info, BusinessEnum.reward_confirm));
				view.setData(map);
				view.setError(ErrorCode.SUCCESS);
			}
		/*} else {
			view.setError(ErrorCode.REFUSEIP);
		}*/
		return view;
	}

	/**
	 * 通用转账
	 * 
	 * @param requestNo
	 * @param confirmRequestNo
	 * @param info
	 * @param request
	 * @return
	 * @throws UserAccountException
	 * @throws TradeException
	 * @throws PlatformAccountException
	 * @throws DataAlreadExistException
	 * @throws UserInfoException
	 */
	@RequestMapping("/transaction.do")
	@ResponseBody
	public View transfer(@RequestParameter TransferParameter parameter,
			String source, HttpServletRequest request)
			throws UserAccountException, UserInfoException {
		View view = getView(source);
		/*if (authenticationIP(request, BusinessEnum.transfer)) {*/
			if (parameter == null || StringUtils.isBlank(parameter.getUserId())			
					|| parameter.getMoney() <= 0)
				view.setError(ErrorCode.ParametersError);
			else {
				Generator generator = trusteeshipFundTransferService.createTransaction(parameter.getUserId(),
								parameter.getMoney(),
								parameter.getDescripion(), view.getType());
				view.setData(generator);
				view.setError(ErrorCode.SUCCESS);
			}
		/*} else {
			view.setError(ErrorCode.REFUSEIP);
		}*/
		return view;
	}

}
