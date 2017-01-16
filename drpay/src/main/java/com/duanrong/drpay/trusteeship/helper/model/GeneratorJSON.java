package com.duanrong.drpay.trusteeship.helper.model;

import java.util.Date;
import java.util.List;

import com.duanrong.drpay.jsonservice.handler.TerminalEnum;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipConstants;

import util.ArithUtil;
import util.DateUtil;
import util.FastJsonUtil;

/**
 * 存管通 通用 实体接口
 * 
 * @author xiao
 * @datetime 2016年12月7日 下午3:06:58
 */
public class GeneratorJSON {

	// 返回码
	private String code;

	// 描述
	private String description;

	// 状态
	private String status;

	// 流水号
	private String requestNo;

	// 平台用户编号
	private String platformUserNo;

	// 项目编号
	private String projectNo;

	// 时间戳
	private Date timestamp;

	private  Double amount;
	
	private String accessType;
	
	// 页面回跳URL
	private NotifyURL callbackUrl;

	private String transactionType;
	
	private String originalFreezeRequ;
	
	//终端类型，不需要序列化
	private transient TerminalEnum source = TerminalEnum.wap;

	// 平台根据自定义业务类型需要提示用户的描述
	private String bizTypeDescription;
	
	private String preTransactionNo;
	
	//理财计划编号
	private String intelProjectNo;
	
	private String intelRequestNo;
	
	private String originalFreezeRequestNo;

	private List<GeneratorDetailJSON> details,records;
	
	private static final String NOTIFY_PREFIX = TrusteeshipConstants.NOTIFY_URL
			+ TrusteeshipConstants.SERVICE;
	private static final String NOTIFY_SUFFIX = ".do";

	public String getOriginalFreezeRequ() {
		return originalFreezeRequ;
	}

	public void setOriginalFreezeRequ(String originalFreezeRequ) {
		this.originalFreezeRequ = originalFreezeRequ;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBizTypeDescription() {
		return bizTypeDescription;
	}

	public void setBizTypeDescription(String bizTypeDescription) {
		this.bizTypeDescription = bizTypeDescription;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getPlatformUserNo() {
		return platformUserNo;
	}

	public void setPlatformUserNo(String platformUserNo) {
		this.platformUserNo = platformUserNo;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getTimestamp() {
		timestamp = timestamp == null ? new Date() : timestamp;
		return DateUtil.DateToString(this.timestamp, "yyyyMMddHHmmss");
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<GeneratorDetailJSON> getDetails() {
		return details;
	}

	public TerminalEnum getSource() {
		return source;
	}

	public void setSource(TerminalEnum source) {
		if(source != null) this.source = source;
	}

	public void setDetails(List<GeneratorDetailJSON> details) {
		this.details = details;
	}

	public String getCallbackUrl() {
		return this.callbackUrl == null ? "" : NOTIFY_PREFIX + this.callbackUrl
				+ NOTIFY_SUFFIX + "?source=" + source;
	}

	public Double getAmount() {
		return amount != null ? ArithUtil.round(amount, 2) : null;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setCallbackUrl(NotifyURL callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	
	public String getPreTransactionNo() {
		return preTransactionNo;
	}

	public void setPreTransactionNo(String preTransactionNo) {
		this.preTransactionNo = preTransactionNo;
	}
	
	public List<GeneratorDetailJSON> getRecords() {
		return records;
	}

	public void setRecords(List<GeneratorDetailJSON> records) {
		this.records = records;
	}

	public String getOriginalFreezeRequestNo() {
		return originalFreezeRequestNo;
	}

	public void setOriginalFreezeRequestNo(String originalFreezeRequestNo) {
		this.originalFreezeRequestNo = originalFreezeRequestNo;
	}
	
	public String getIntelProjectNo() {
		return intelProjectNo;
	}

	public void setIntelProjectNo(String intelProjectNo) {
		this.intelProjectNo = intelProjectNo;
	}
	
	public String getIntelRequestNo() {
		return intelRequestNo;
	}

	public void setIntelRequestNo(String intelRequestNo) {
		this.intelRequestNo = intelRequestNo;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	/**
	 * 转换为json字符串
	 * 
	 * @return
	 */
	public String toJSON() {
		return FastJsonUtil.objToJsonNoWriteNull(this);
	}

	/**
	 * 转换成 GeneratorJSON 对象
	 * @param json
	 * @return
	 */
	public static GeneratorJSON toGeneratorJSON(String json){
		return (GeneratorJSON) FastJsonUtil.jsonToObj(json, GeneratorJSON.class);
	}

	@Override
	public String toString() {
		return "GeneratorJSON [code=" + code + ", description=" + description
				+ ", status=" + status + ", requestNo=" + requestNo
				+ ", platformUserNo=" + platformUserNo + ", projectNo="
				+ projectNo + ", timestamp=" + timestamp + ", amount=" + amount
				+ ", callbackUrl=" + callbackUrl + ", transactionType="
				+ transactionType + ", originalFreezeRequ="
				+ originalFreezeRequ + ", bizTypeDescription="
				+ bizTypeDescription + ", preTransactionNo=" + preTransactionNo
				+ ", intelProjectNo=" + intelProjectNo
				+ ", originalFreezeRequestNo=" + originalFreezeRequestNo
				+ ", details=" + details + ", accessType=" + accessType + ", records=" + records + "]";
	}
	
}
