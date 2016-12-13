package com.duanrong.thirdPartyInterface.model;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-11-11 下午4:09:39
 * @Description : template com.duanrong.wdzj.model WDZJ.java
 * 
 */
public class YHBYresp implements Serializable {
	private String code;
	private String message;
	private List<YHBYLoan> data;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the data
	 */
	public List<YHBYLoan> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<YHBYLoan> data) {
		this.data = data;
	}
	
}
