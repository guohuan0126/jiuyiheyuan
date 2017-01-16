package com.duanrong.drpay.jsonservice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import base.error.ErrorCode;
import base.exception.BankCardException;
import base.exception.DataAlreadExistException;
import base.exception.ParameterException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.account.model.CgtUserAccount;
import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.jsonservice.handler.RequestParameter;
import com.duanrong.drpay.jsonservice.handler.View;
import com.duanrong.drpay.jsonservice.param.AutoInvestParamter;
import com.duanrong.drpay.jsonservice.param.Parameter;
import com.duanrong.drpay.jsonservice.param.UserAccountParameter;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.service.TrusteeshipAccountService;

/**
 * 用户账户服务
 * 
 * @author xiao
 * @datetime 2016年12月7日 下午6:42:06
 */
@Controller
@RequestMapping(value = "/account", method = RequestMethod.POST)
public class UserAccountController extends BaseController {

	@Resource
	TrusteeshipAccountService trusteeshipAccountService;

	/**
	 * 开户（绑卡注册）
	 * 
	 * @param params
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 * @throws ParameterException
	 * @throws BankCardException
	 */
	@RequestMapping(value = "/createAccount")
	@ResponseBody
	public View createAccount(@RequestParameter UserAccountParameter params,
			String source) throws UserInfoException, UserAccountException,
			ParameterException, BankCardException {
		View view = getView(source);
		if (params == null || StringUtils.isEmpty(params.getUserId())
				|| StringUtils.isEmpty(params.getRealName())
				|| StringUtils.isEmpty(params.getIdCardType().toString())
				|| StringUtils.isEmpty(params.getUserRole().toString())
				|| StringUtils.isEmpty(params.getIdCardNo())
				|| StringUtils.isEmpty(params.getBankcardNo())
				|| StringUtils.isEmpty(params.getBankCode())
				|| StringUtils.isEmpty(params.getMobile())) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator reqData = trusteeshipAccountService.createAccount(params,
					view.getType());
			if (reqData != null) {
				reqData.setUserDevice(sourceToUserDevice(source));
			}
			view.setData(reqData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 用户账户激活
	 * 
	 * @param params
	 * @param source
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 * @throws ParameterException
	 * @throws BankCardException
	 */
	@RequestMapping(value = "/activateUserAccount")
	@ResponseBody
	public View activateUserAccount(@RequestParameter String userId, @RequestParameter String bankCode, @RequestParameter String bankCardNo,
			String source) throws UserAccountException, ParameterException, BankCardException {
		View view = getView(source);
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(bankCardNo) || StringUtils.isEmpty(bankCode)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator reqData = trusteeshipAccountService.activateUserAccount(
					userId, bankCode, bankCardNo, view.getType());
			if (reqData != null) {
				reqData.setUserDevice(sourceToUserDevice(source));
			}
			view.setData(reqData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	@RequestMapping("/userInfoForActive")
	@ResponseBody
	public View userInfoForActive(@RequestParameter String userId, String source) throws UserInfoException{
		View view = getView(source);
		if (StringUtils.isBlank(userId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Map<String, Object> data = trusteeshipAccountService.userInfoForActive(userId);
			view.setData(data);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}
	
	/**
	 * 用户信息查询(cgt，调度)
	 * @param userId
	 * @param handle
	 * @return
	 * @throws UserInfoException 
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public View query(@RequestParameter String userId, @RequestParameter String type, @RequestParameter int handle) throws UserInfoException {
		View view = getPcView();
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(type)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator data = trusteeshipAccountService.queryUserInformation(userId, type, handle);
			view.setData(data);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}
	
	/**
	 * 查询存管通与本地用户账户信息
	 * 
	 * @param userId
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	@RequestMapping(value = "/queryCgtUserInfo")
	@ResponseBody
	public View queryCgtUserInfo(@RequestParameter String userId)
			throws UserInfoException, UserAccountException {
		View view = getPcView();
		if (StringUtils.isEmpty(userId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			CgtUserAccount data = trusteeshipAccountService
					.queryCgtUserInfo(userId);
			view.setData(data);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 查询本地用户账户信息
	 * 
	 * @param userId
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	@RequestMapping(value = "/queryLocalUserInfo")
	@ResponseBody
	public View queryLocalUserInfo(@RequestParameter String userId,
			String source) throws UserInfoException, UserAccountException {
		View view = getView(source);
		if (StringUtils.isEmpty(userId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			UserAccount account = trusteeshipAccountService
					.queryLocalUserInfo(userId);
			view.setData(account);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 存管通交易密码校验
	 * 
	 * @param userId
	 * @param templateType
	 *            提示信息（或信息模板类型）
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	@RequestMapping(value = "/checkPassword")
	@ResponseBody
	public View checkPassword(@RequestParameter String userId,
			@RequestParameter String templateType, String source)
			throws UserAccountException, UserInfoException {
		View view = getView(source);
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(templateType)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator reqData = trusteeshipAccountService.checkPassword(userId,
					templateType, view.getType());
			if (reqData != null) {
				reqData.setUserDevice(sourceToUserDevice(source));
			}
			view.setData(reqData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 个人绑卡
	 * 
	 * @param parameter
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 * @throws ParameterException
	 * @throws BankCardException
	 */
	@RequestMapping(value = "/bindCard")
	@ResponseBody
	public View bindCard(@RequestParameter UserAccountParameter parameter,
			String source) throws UserInfoException, UserAccountException,
			ParameterException, BankCardException {
		View view = getView(source);
		if (parameter == null || StringUtils.isEmpty(parameter.getUserId())
				|| StringUtils.isEmpty(parameter.getBankcardNo())
				|| StringUtils.isEmpty(parameter.getBankCode())) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator reqData = trusteeshipAccountService.bindCard(
					parameter.getUserId(), parameter.getBankcardNo(),
					parameter.getBankCode(), parameter.getMobile(),
					view.getType());
			if (reqData != null) {
				reqData.setUserDevice(sourceToUserDevice(source));
			}
			view.setData(reqData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	@RequestMapping("/resetPassword")
	@ResponseBody
	public View resetPassword(@RequestParameter String userId, String source)
			throws UserAccountException, UserInfoException {
		View view = getView(source);
		if (StringUtils.isEmpty(userId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator reqData = trusteeshipAccountService.resetPassword(userId,
					view.getType());
			if (reqData != null) {
				reqData.setUserDevice(sourceToUserDevice(source));
			}
			view.setData(reqData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 解绑卡(直连)
	 * 
	 * @param userId
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	@RequestMapping("/unBindCard")
	@ResponseBody
	public View unBindCard(@RequestParameter String userId, String source)
			throws UserAccountException, UserInfoException {
		View view = getView(source);
		if (StringUtils.isEmpty(userId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			String reqData = trusteeshipAccountService.unBindCard(userId);
			view.setData(reqData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 修改本地手机号
	 * 
	 * @param userId
	 * @param newMobile
	 * @param oldMobile
	 * @param password
	 * @param idCardNo
	 * @param authCode
	 * @param type
	 * @param source
	 * @return
	 * @throws DataAlreadExistException
	 * @throws UserInfoException
	 */
	@RequestMapping("/resetMobile")
	@ResponseBody
	public View resetMobile(@RequestParameter String userId,
			@RequestParameter String newMobile,
			@RequestParameter String oldMobile,
			@RequestParameter String password,
			@RequestParameter String idCardNo,
			@RequestParameter String authCode, @RequestParameter String type,
			String source) throws DataAlreadExistException, UserInfoException {
		View view = getView(source);
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(newMobile)
				|| StringUtils.isEmpty(oldMobile)
				|| StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(idCardNo)
				|| StringUtils.isEmpty(authCode) || StringUtils.isEmpty(type)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipAccountService.resetMobile(userId, newMobile, oldMobile,
					password, idCardNo, authCode);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 修改银行卡预留手机号
	 * 
	 * @param userId
	 * @param modifyType
	 * @param mobile
	 * @return
	 * @throws UserInfoException
	 * @throws UserAccountException
	 */
	@RequestMapping("/modifyMobile")
	@ResponseBody
	public View modifyMobile(@RequestParameter String userId, String source)
			throws UserInfoException, UserAccountException {
		View view = getView(source);
		if (StringUtils.isEmpty(userId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			Generator reqData = trusteeshipAccountService.modifyMobile(userId,
					view.getType());
			if (reqData != null) {
				reqData.setUserDevice(sourceToUserDevice(source));
			}
			view.setData(reqData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 冻结用户资金
	 * 
	 * @param userId
	 * @param money
	 * @return
	 */
	@RequestMapping("/freeze")
	@ResponseBody
	public View freeze(@RequestParameter Parameter parameter, @RequestParameter String loginId,
			@RequestParameter String expiredTime,
			@RequestParameter String description) {
		View view = getView();
		if (parameter == null || StringUtils.isEmpty(parameter.getUserId()) || parameter.getMoney() <= 0) {
			view.setError(ErrorCode.ParametersError);
		} else {
			try {
				trusteeshipAccountService.freezeMoney(loginId, expiredTime,
						URLEncoder.encode(description, "utf-8"), parameter.getUserId(), parameter.getMoney());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 解冻用户资金
	 * 
	 * @param userId
	 * @param money
	 * @return
	 * @throws TradeException 
	 */
	@RequestMapping("/unfreeze")
	@ResponseBody
	public View unfreeze(@RequestParameter String loginId,
			@RequestParameter String requestNo) throws TradeException {
		View view = getView();
		if (StringUtils.isEmpty(requestNo)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipAccountService.unfreezeMoney(loginId, requestNo);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 获取自动投标信息
	 * 
	 * @param AutoInvest
	 * @return
	 * @throws ParameterException
	 */
	@RequestMapping("/getAutoInvest")
	@ResponseBody
	public View getAutoInvest(@RequestParameter String userId, String source)
			throws ParameterException {
		View view = getView(source);
		if (StringUtils.isBlank(userId)) {
			view.setError(ErrorCode.ParametersError);
			return view;
		}
		view.setData(trusteeshipAccountService.getAutoInvest(userId));
		view.setError(ErrorCode.SUCCESS);
		return view;
	}

	/**
	 * 设置自动投标
	 * 
	 * @param AutoInvest
	 * @return
	 * @throws ParameterException
	 */
	@RequestMapping("/settingAutoInvest")
	@ResponseBody
	public View createAutoInvest(@RequestParameter AutoInvestParamter paramter, String source)
			throws ParameterException {
		View view = getView(source);
		if (StringUtils.isBlank(paramter.getUserId())
				|| StringUtils.isBlank(paramter.getLoanType())
				|| paramter.getMaxDeadline() == null
				|| paramter.getMaxMoney() == null
				|| paramter.getMaxRate() == null
				|| paramter.getMinDeadline() == null
				|| paramter.getMinMoney() == null
				|| paramter.getMinRate() == null) {
			view.setError(ErrorCode.ParametersError);
			return view;
		}
		trusteeshipAccountService.settingAutoInvest(paramter);
		view.setError(ErrorCode.SUCCESS);
		return view;
	}

	/**
	 * 关闭自动投标
	 */
	@RequestMapping("/cancelAutoInvest")
	@ResponseBody
	public View cancelAutoInvest(@RequestParameter AutoInvestParamter paramter, String source) {
		View view = getView(source);
		if (StringUtils.isBlank(paramter.getUserId())) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipAccountService.cancelAutoInvest(paramter.getUserId());
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}

	/**
	 * 解绑卡申请（前端，上传证件照片）
	 * 
	 * @param userId
	 * @param source
	 * @param request
	 * @return
	 * @throws UserInfoException
	 */
	@RequestMapping("/unBindCardApply")
	@ResponseBody
	public View unBindCardApply(@RequestParameter String userId,
			@RequestParameter String imgData, String source)
			throws UserInfoException {
		View view = getView(source);
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(imgData)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			trusteeshipAccountService.unBindCardApply(userId, imgData);
			view.setError(ErrorCode.SUCCESS);
		}
		return view;
	}
}
