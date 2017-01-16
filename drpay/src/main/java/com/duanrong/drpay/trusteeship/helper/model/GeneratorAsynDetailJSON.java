package com.duanrong.drpay.trusteeship.helper.model;

/**
 * 异步通知响应结果
 * @author xiao
 * @datetime 2017年1月2日 下午4:04:30
 */
public class GeneratorAsynDetailJSON {

	//明细流水号
	private String asyncRequestNo;
	
	//业务类型
	private BizType bizType;
	
	//交易发起时间
	private String createTime;
	
	//交易完成时间
	private String transactionTime;
	
	//交易状态
	private String status;
	
	//错误码
	private String failCode;
	
	//错误描述
	private String failReason;

	public String getAsyncRequestNo() {
		return asyncRequestNo;
	}

	public void setAsyncRequestNo(String asyncRequestNo) {
		this.asyncRequestNo = asyncRequestNo;
	}

	public BizType getBizType() {
		return bizType;
	}

	public void setBizType(BizType bizType) {
		this.bizType = bizType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailCode() {
		return failCode;
	}

	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
}
