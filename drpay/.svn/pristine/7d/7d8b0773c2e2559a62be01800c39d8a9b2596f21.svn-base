package com.duanrong.drpay.trusteeship.helper.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneratorPreTransactionJSON extends GeneratorJSON {

	// 预处理业务类型
	private BizType bizType;

	// 超过此时间即页面过期
	private Date expired;
	
	private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	/************* 非必填字段 ************/

	//批量理财计划年利率
	private Double annualInterestRate;
	
	//批量计划未匹配金额
	private Double unmatchAmount;
	
	
	// 预备使用的红包金额，只记录不冻结，仅限投标业务类型
	private Double preMarketingAmount;
	
	// 购买债转份额，业务类型为购买债权时，需要传此参数
	private String share;
	
	// 债权出让请求流水号，只有购买债权业务需填此参数
	private String creditsaleRequestNo;
	
	// 备注
	private String remark;

	// 收款方平台用户编号（目前只能是平台账户）
	private String targetPlatformUserNo;

	// 扣费说明
	private String customDefine;

	public BizType getBizType() {
		return bizType;
	}

	public void setBizType(BizType bizType) {
		this.bizType = bizType;
	}

	public String getExpired() {
		return format.format(new Date(new Date().getTime() + 600000) );
	}

//	public void setExpired(Date expired) {
//		this.expired = expired;
//	}

	public Double getPreMarketingAmount() {
		return preMarketingAmount;
	}

	public void setPreMarketingAmount(Double preMarketingAmount) {
		this.preMarketingAmount = preMarketingAmount;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}
	
	public Double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(Double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public Double getUnmatchAmount() {
		return unmatchAmount;
	}

	public void setUnmatchAmount(Double unmatchAmount) {
		this.unmatchAmount = unmatchAmount;
	}

	public String getCreditsaleRequestNo() {
		return creditsaleRequestNo;
	}

	public void setCreditsaleRequestNo(String creditsaleRequestNo) {
		this.creditsaleRequestNo = creditsaleRequestNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTargetPlatformUserNo() {
		return targetPlatformUserNo;
	}

	public void setTargetPlatformUserNo(String targetPlatformUserNo) {
		this.targetPlatformUserNo = targetPlatformUserNo;
	}

	public String getCustomDefine() {
		return customDefine;
	}

	public void setCustomDefine(String customDefine) {
		this.customDefine = customDefine;
	}

	@Override
	public String toString() {
		return "GeneratorPreTransactionJSON [bizType=" + bizType + ", expired="
				+ expired + ", preMarketingAmount=" + preMarketingAmount + ", share="
				+ share + ", creditsaleRequestNo=" + creditsaleRequestNo
				+ ", remark=" + remark + ", targetPlatformUserNo="
				+ targetPlatformUserNo + ", customDefine=" + customDefine + "]";
	}
}
