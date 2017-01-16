package com.duanrong.drpay.trusteeship.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.Log;
import base.error.ErrorCode;
import base.exception.LoanException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.service.LoanService;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.jsonservice.param.LoanParameter;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorLoanJSON;
import com.duanrong.drpay.trusteeship.helper.model.ProjectStatus;
import com.duanrong.drpay.trusteeship.helper.model.ProjectType;
import com.duanrong.drpay.trusteeship.helper.model.RepaymentWay;
import com.duanrong.drpay.trusteeship.helper.service.TrusteeshipService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipLoanService;

@Service
public class TrusteeshipLoanServiceImpl implements TrusteeshipLoanService {

	@Resource
	UserService userService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	TrusteeshipService trusteeshipService;

	@Resource
	LoanService loanService;

	@Resource
	Log log;

	@Override
	public GeneratorJSON createLoan(String userId, LoanParameter params)
			throws UserInfoException, UserAccountException, LoanException {
		// 判断用户是否角有借款权限
		if (!(userService.hasRoleByUserId(params.getUserId(), "LOANER") || userService.hasRoleByUserId(params.getUserId(), "INTERMEDIATOR"))) {
			log.errLog("创建项目", "userId: " + params.getUserId()
					+ ", 创建项目 loanId: " + params.getLoanId() + " 失败, 用户没有借款者或居间人权限");
			throw new UserAccountException(ErrorCode.UserNoLoaner);
		}
		// TODO requestno应该使用标准流水号生成规则
		GeneratorLoanJSON json = new GeneratorLoanJSON();
		json.setRequestNo(IdUtil.randomUUID());
		json.setAnnnualInterestRate(params.getRate());
		json.setPlatformUserNo(params.getUserId());
		json.setProjectNo(params.getLoanId());
		json.setProjectName(params.getLoanName());
		json.setProjectAmount(params.getMoney());
		json.setProjectType(ProjectType.STANDARDPOWDER);
		json.setProjectPeriod(params.getPeriod());
		if (params.getRepayType().equals(LoanConstants.RepayType.DQHBFX)) {
			json.setRepaymentWay(RepaymentWay.ONE_TIME_SERVICING);
		} else if (params.getRepayType().equals(LoanConstants.RepayType.RFCL)) {
			json.setRepaymentWay(RepaymentWay.FIRSEINTREST_LASTPRICIPAL);
		} else if (params.getRepayType().equals(LoanConstants.RepayType.CAM)) {
			json.setRepaymentWay(RepaymentWay.FIXED_BASIS_MORTGAGE);
		} else if (params.getRepayType().equals(LoanConstants.RepayType.CPM)) {
			json.setRepaymentWay(RepaymentWay.FIXED_PAYMENT_MORTGAGE);
		} else {
			throw new LoanException(ErrorCode.RepayTypeNotFind);
		}
		return trusteeshipService.execute(json,
				TrusteeshipServer.ESTABLISH_PROJECT, GeneratorJSON.class)
				.getRespData();
	}

	@Override
	public GeneratorJSON createProject(LoanParameter parameter)
			throws UserAccountException {
		// 判断用户是否角有借款权限
		if (!(userService.hasRoleByUserId(parameter.getUserId(), "LOANER") || userService.hasRoleByUserId(parameter.getUserId(), "INTERMEDIATOR"))) {
			log.errLog("创建项目", "userId: " + parameter.getUserId()
					+ ", 创建项目 loanId: " + parameter.getLoanId() + " 失败, 用户没有借款者或居间人权限");
			throw new UserAccountException(ErrorCode.UserNoLoaner);
		}
		GeneratorLoanJSON json = new GeneratorLoanJSON();
		json.setRequestNo(IdUtil.randomUUID());
		json.setIntelProjectNo(parameter.getLoanId());
		json.setIntelProjectName(parameter.getLoanName());
		// json.setIntelProjectDescription("");
		json.setAnnualInterestRate(parameter.getRate());
		return trusteeshipService.execute(json,
				TrusteeshipServer.ESTABLISH_INTELLIGENT_PROJECT,
				GeneratorJSON.class).getRespData();
	}

	@Override
	public GeneratorJSON modifyLoan(String loanId, String status)
			throws LoanException {
		Loan loan = loanService.get(loanId);
		if (loan == null) {
			log.errLog("更新项目", "loanId: " + loanId + ", 不存在");
			throw new LoanException(ErrorCode.LoanNotFind);
		}
		return this.modifyLoan(loan, status);
	}

	@Override
	public GeneratorJSON modifyLoan(Loan loan, String status)
			throws LoanException {
		String loanId = loan.getId();
		if (LoanConstants.LoanStatus.CANCEL.equals(loan.getStatus())
				|| LoanConstants.LoanStatus.COMPLETE.equals(loan.getStatus())) {
			log.errLog("更新项目",
					"loanId: " + loanId + ", 项目状态为 ：" + loan.getStatus()
							+ ", 更新状态为: " + status);
			throw new LoanException(ErrorCode.LoanStatusError);
		}
		// TODO 没有使用标准流水号生成规则
		GeneratorLoanJSON json = new GeneratorLoanJSON();
		json.setRequestNo(IdUtil.randomUUID());
		json.setProjectNo(loanId);
		if (LoanConstants.LoanStatus.CANCEL.equals(status)
				&& (LoanConstants.LoanStatus.DQGS.equals(loan.getStatus())
						|| LoanConstants.LoanStatus.RAISING.equals(loan
								.getStatus()) || LoanConstants.LoanStatus.RECHECK
							.equals(loan.getStatus()))) {
			json.setStatus(ProjectStatus.MISCARRY);
		} else if (LoanConstants.LoanStatus.REPAYING.equals(status)
				&& (LoanConstants.LoanStatus.RECHECK.equals(loan.getStatus()) || LoanConstants.LoanStatus.RAISING
						.equals(loan.getStatus()))) {
			json.setStatus(ProjectStatus.REPAYING);
		} else if (LoanConstants.LoanStatus.REPAYING.equals(status)
				&& LoanConstants.LoanStatus.COMPLETE.equals(loan.getStatus())) {
			json.setStatus(ProjectStatus.FINISH);
		} else {
			log.errLog("更新项目",
					"loanId: " + loanId + ", 项目状态为 ：" + loan.getStatus()
							+ ", 更新状态为: " + status);
			throw new LoanException(ErrorCode.LoanStatusError);
		}
		return trusteeshipService.execute(json,
				TrusteeshipServer.MODIFY_PROJECT, GeneratorJSON.class)
				.getRespData();
	}

	@Override
	public GeneratorJSON queryLoan(String loanId) throws LoanException {
		Loan loan = loanService.get(loanId);
		if (loan == null) {
			log.errLog("更新项目", "loanId: " + loanId + ", 不存在");
			throw new LoanException(ErrorCode.LoanNotFind);
		}
		GeneratorLoanJSON json = new GeneratorLoanJSON();
		json.setRequestNo(IdUtil.randomUUID());
		json.setProjectNo(loanId);

		return trusteeshipService.execute(json,
				TrusteeshipServer.QUERY_PROJECT_INFORMATION,
				GeneratorLoanJSON.class).getRespData();
	}

}