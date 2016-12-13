package com.duanrong.cps.business.xicaiwang.service;


import java.util.List;

import com.duanrong.cps.business.xicaiwang.model.XiCaiInvestInfo;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.service.PushService;
import com.duanrong.cps.business.xicaiwang.model.XiCaiLoanInfo;
import com.duanrong.cps.business.xicaiwang.model.XiCaiUserInfo;

public interface XicaiwangService extends PushService{

	public XiCaiLoanInfo getLoanInfo(String loanId);

	public List<XiCaiUserInfo> getUserInfo(String startdate, String enddate,
			String page, String pagesize);

	public List<XiCaiInvestInfo> getInvestInfo(String startdate, String enddate,
			String page, String pagesize);

	public void insertToPushLoan(Loan loan);

	public int getTotalUser(String startdate, String enddate, String page,
			String pagesize);

	public int getTotalInvest(String startdate, String enddate);

}
