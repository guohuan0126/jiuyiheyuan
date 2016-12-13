package com.duanrong.thirdPartyInterface.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import base.model.BaseModel;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-11-11 下午4:21:43
 * @Description : template com.duanrong.wdzj.model WDZJInveset.java
 * 
 */
public class WDZJInvest extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2081038127707252061L;
	/**
	 * 投标人ID 如平台出于保护隐私目的不传实际ID的，可将ID进行加密或者哈希处理，处理后的ID与原ID能一一对应即可。但不能将ID加*隐藏部分字符，
	 * 否则会导致多个投资人使用同一个ID，导致投资集中度高。
	 */
	private String subscribeUserName;
	/**
	 * 投标金额 用户初始投标的金额。（对于平台优惠奖励政策的情况（如投标人投****元自动返还**元，或是某个人获得满标奖励**）只计投标金额。
	 */
	private double amount;
	/**
	 * 有效金额 实际中标金额。 如平台无’投标金额’和’有效金额’之分，则’投标金额’和’有效金额’传一样的即可。
	 */
	private double validAmount;
	/**
	 * 投标时间 格式为标准时间格式：’2014-07-23 12:23:22’
	 */
	private String addDate;
	/**
	 * 投标状态 1：全部通过 2：部分通过 注意：平台没有这个字段的默认为1
	 */
	private int status;
	/**
	 * 标识手动或自动投标 0：手动 1：自动 注意:平台没有这个字段的默认为0
	 */
	private int type;

	/**
	 * @return the subscribeUserName
	 */
	public String getSubscribeUserName() {
		return subscribeUserName;
	}

	/**
	 * @param subscribeUserName
	 *            the subscribeUserName to set
	 */
	public void setSubscribeUserName(String subscribeUserName) {
		String code = RandomStringUtils.random(6, false, true);
		subscribeUserName = DigestUtils.md5Hex(this.addDate + code
				+ subscribeUserName);
		this.subscribeUserName = subscribeUserName;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the validAmount
	 */
	public double getValidAmount() {
		return validAmount;
	}

	/**
	 * @param validAmount
	 *            the validAmount to set
	 */
	public void setValidAmount(double validAmount) {
		this.validAmount = validAmount;
	}

	/**
	 * @return the addDate
	 */
	public String getAddDate() {
		return addDate;
	}

	/**
	 * @param addDate
	 *            the addDate to set
	 */
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WDZJInveset [subscribeUserName=" + subscribeUserName
				+ ", amount=" + amount + ", validAmount=" + validAmount
				+ ", addDate=" + addDate + ", status=" + status + ", type="
				+ type + "]";
	}

}
