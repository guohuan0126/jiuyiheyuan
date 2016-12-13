package com.duanrong.payment.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="trans_head")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransHead implements java.io.Serializable{
	
	
	@XmlElement(name="return_code")
	private String returnCode;
	
	@XmlElement(name="return_msg")
	private String returnMsg;
	
	@XmlElement(name="trans_count")
	private String transCount;
	
	@XmlElement(name="trans_totalMoney")
	private String transTotalMoney;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getTransCount() {
		return transCount;
	}

	public void setTransCount(String transCount) {
		this.transCount = transCount;
	}

	public String getTransTotalMoney() {
		return transTotalMoney;
	}

	public void setTransTotalMoney(String transTotalMoney) {
		this.transTotalMoney = transTotalMoney;
	}

	
}
