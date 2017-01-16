package com.duanrong.drpay.jsonservice.param;

import java.io.Serializable;

import com.duanrong.drpay.config.BusinessEnum;

/**
 * 请求参数父类, 接口输入参数由此类定义, 根据具体业务类型和接口自由扩展, 扩展参数慎重，不要扩展无意义字段 
 * 接口绑定参数 @RequestParmter 需要继承此类，否则抛出接口参数绑定异常
 * @author xiao
 * @datetime 2016/10/20 17:10:12
 */
public class Parameter implements Serializable {

	private static final long serialVersionUID = -2510079586352986286L;

	// 用户id
	private String userId;

	private double money;
	
	// 用户来源
	private String userSource;

	// 流水号
	private String requestNo;
	
	//业务类型
	private BusinessEnum businessType;
	
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	
	public BusinessEnum getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessEnum businessType) {
		this.businessType = businessType;
	}
	
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "Parameter [userId=" + userId + ", userSource=" + userSource
				+ ", requestNo=" + requestNo + ", businessType=" + businessType
				+ "]";
	}

}
