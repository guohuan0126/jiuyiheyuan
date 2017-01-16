package com.duanrong.drpay.jsonservice.param;


public class InvestParameter extends Parameter {
	private static final long serialVersionUID = -4708630027509232450L;

	private String loanId;
	private int redpacketId;
	//0手动投标，1自动投标
	private int isAutoInvest;
	
	//投资来源
	private String investSource;
	
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	public int getRedpacketId() {
		return redpacketId;
	}
	public void setRedpacketId(int redpacketId) {
		this.redpacketId = redpacketId;
	}
	public int getIsAutoInvest() {
		return isAutoInvest;
	}
	public void setIsAutoInvest(int isAutoInvest) {
		this.isAutoInvest = isAutoInvest;
	}
	public String getInvestSource() {
		return investSource;
	}
	public void setInvestSource(String investSource) {
		this.investSource = investSource;
	}
	
}
