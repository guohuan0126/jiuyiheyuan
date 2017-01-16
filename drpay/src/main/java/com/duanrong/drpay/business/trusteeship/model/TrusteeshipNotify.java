package com.duanrong.drpay.business.trusteeship.model;

import java.util.Date;

/**
 * 通知记录
 * @author xiao
 * @datetime 2017年1月4日 上午10:29:06
 */
public class TrusteeshipNotify {

	private int id;
	
	//存管通知数据
	private String responseData;
	
	//存管通知时间
	private Date responseTime;
	
	//
	private String type;
	
	//业务类型
	private String serviceName;
	
	//签名
	private String sign;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "TrusteeshipNotify [id=" + id + ", responseData=" + responseData
				+ ", responseTime=" + responseTime + ", type=" + type
				+ ", serviceName=" + serviceName + ", sign=" + sign + "]";
	}
	
	
}
