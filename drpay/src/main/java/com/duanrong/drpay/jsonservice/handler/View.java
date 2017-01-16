package com.duanrong.drpay.jsonservice.handler;

import java.io.Serializable;
import java.util.Date;

import base.error.ErrorCode;

/**
 * 视图返回类
 * 
 * @author xiao
 * @version 1.0
 */
 public class View implements Serializable{

	private static final long serialVersionUID = 2705648456094028157L;
	
	//错误码
	private String code;
	
	//错误描述
	private String msg;
	
	//响应时间
	private Date responseTime;
	
	//相应的接口版本
	private String version;
	
	//接口类型
	private transient TerminalEnum type;
	
	//输出数据
	private Object data;
	
	
	//错误码枚举，不需要序列化
	private transient ErrorCode error;

	public View() {}

	public View(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ErrorCode getError() {
		return error;
	}

	public void setError(ErrorCode error) {
		this.code = error.getErrorCode();
		this.msg = error.getErrorMessage();
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public Date getResponseTime() {		
		return responseTime == null ? new Date() : responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public TerminalEnum getType() {
		return type;
	}

	public void setType(TerminalEnum type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "View [code=" + code + ", msg=" + msg + ", responseTime="
				+ responseTime + ", version=" + version + ", data=" + data
				+ "]";
	}



}
