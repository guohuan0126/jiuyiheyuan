package com.duanrong.drpay.jsonservice.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import base.error.ErrorCode;
import base.exception.LoanException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.jsonservice.handler.RequestParameter;
import com.duanrong.drpay.jsonservice.handler.View;
import com.duanrong.drpay.jsonservice.param.LoanParameter;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.service.TrusteeshipLoanService;

/**
 * 项目服务
 * 
 * @author xiao
 * @datetime 2016年12月9日 下午2:09:12
 */
@Controller
@RequestMapping(value = "/loan", method = RequestMethod.POST)
public class LoanController extends BaseController {
	
	@Resource
	TrusteeshipLoanService trusteeshipLoanService;

	@RequestMapping(value = "/create")
	@ResponseBody
	public View createLoan(@RequestParameter LoanParameter parameter)
			throws UserAccountException, UserInfoException, LoanException {
		View view = getPcView();
		if (parameter == null
				|| StringUtils.isAnyEmpty(parameter.getUserId(),
						parameter.getLoanId(), parameter.getLoanName(),
						parameter.getRepayType()) || parameter.getMoney() <= 0
				|| parameter.getRate() <= 0) {
			view.setError(ErrorCode.ParametersError);
		} else {
			GeneratorJSON generatorJSON = trusteeshipLoanService.createLoan(
					parameter.getUserId(), parameter);
			view.setError(ErrorCode.SUCCESS);
			view.setData(generatorJSON);
		}

		return view;
	}

	@RequestMapping(value = "/createProject")
	@ResponseBody
	public View createProject(@RequestParameter LoanParameter parameter)throws UserAccountException {
		View view = getPcView();
		if (parameter == null|| parameter.getRate() <= 0
				|| StringUtils.isAnyEmpty(parameter.getUserId(),parameter.getLoanId(), parameter.getLoanName())) {
			view.setError(ErrorCode.ParametersError);
		} else {
			GeneratorJSON generatorJSON = trusteeshipLoanService.createProject(parameter);
			view.setError(ErrorCode.SUCCESS);
			view.setData(generatorJSON);
		}

		return view;
	}
	
	@RequestMapping(value = "/modify")
	@ResponseBody
	public View modifyLoan(@RequestParameter String loanId,
			@RequestParameter String status) throws UserAccountException,
			UserInfoException, LoanException {
		View view = getPcView();
		if (StringUtils.isAnyEmpty(loanId, status)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			GeneratorJSON generatorJSON = trusteeshipLoanService.modifyLoan(
					loanId, status);
			view.setError(ErrorCode.SUCCESS);
			view.setData(generatorJSON);
		}
		return view;
	}

	@RequestMapping(value = "/query")
	@ResponseBody
	public View queryLoan(@RequestParameter String loanId)
			throws UserAccountException, UserInfoException, LoanException {
		View view = getPcView();
		if (StringUtils.isAnyEmpty(loanId)) {
			view.setError(ErrorCode.ParametersError);
		} else {
			GeneratorJSON generatorJSON = trusteeshipLoanService
					.queryLoan(loanId);
			view.setError(ErrorCode.SUCCESS);
			view.setData(generatorJSON);
		}
		return view;
	}
	
}
