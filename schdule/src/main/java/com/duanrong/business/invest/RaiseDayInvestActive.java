package com.duanrong.business.invest;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.util.ArithUtil;

@Component
public class RaiseDayInvestActive extends InvestActive {

	@Resource
	PlatformTransferService platformTransferService;

	@Resource
	LoanService loanService;
	
	@Override
	double execute(Invest invest) {
		double awardMoney = 0;
		Loan loan = loanService.get(invest.getLoanId());
		if (loan != null && loan.getOperationType().equals("月")) {		
			if(new SimpleDateFormat("yyyy-MM-dd").format(invest.getTime()).equals("2016-11-18")){	
				double investMoney = invest.getMoney();
				int multi = (int) investMoney / 10000;
				awardMoney = 5 * multi;
				PlatformTransfer platformTransfer = new PlatformTransfer();
				platformTransfer.setType(getActiveId());
				platformTransfer.setUsername(invest.getInvestUserID());
				double sendedMoney = platformTransferService
						.getMoneyByGroup(platformTransfer);
				if (awardMoney + sendedMoney >= 200) {
					awardMoney = 200 - sendedMoney;
				}
				this.setInfomation("恭喜你！参加第2期”18加薪日“活动，返现"
						+ ArithUtil.round(awardMoney, 0) + "元已到账，您可在交易记录查询到账明细。");
			}			
		}
		return ArithUtil.round(awardMoney, 0);
	}

	@Override
	String getActiveId() {
		return "RAISEDAY";
	}

	@Override
	String getTitle() {
		return "【18加薪日】";
	}

}
