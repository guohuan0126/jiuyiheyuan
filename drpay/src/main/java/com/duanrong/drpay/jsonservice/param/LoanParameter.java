package com.duanrong.drpay.jsonservice.param;

/**
 * 项目参数
 * @author xiao
 * @datetime 2016年12月9日 下午2:08:12
 */
public class LoanParameter extends Parameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8922482236813228880L;

	//项目id
	private String loanId;
	
	
	//项目名称
	private String loanName;

	//项目描述
	private String description;
	
	//利率
	private double rate;

	//期限
	private int period;

	//还款方式
	private String repayType;

	//扩展信息
	private String extend;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

}