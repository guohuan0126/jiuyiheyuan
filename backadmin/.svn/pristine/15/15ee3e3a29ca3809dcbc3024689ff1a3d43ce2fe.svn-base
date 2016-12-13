package com.duanrong.payment.model;

import java.util.List;

import javax.xml.bind.annotation.*;


@XmlRootElement(name="trans_content")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransContent implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2788996017600244616L;

	@XmlElement(name="trans_head")
	private TransHead transHead;

	@XmlElementWrapper(name="trans_reqDatas")    
	@XmlElement(name="trans_reqData")
	private List<TransReqBF0040001> transReqDatas;
	
	@XmlElementWrapper(name="trans_reqDatas")    
	@XmlElement(name="trans_reqData")
	private List<TransReqBF0040002> TransReqBF0040002TransReqDatas;
	
	
	private transient String dataType;
	
	
	
	

	public List<TransReqBF0040002> getTransReqBF0040002TransReqDatas() {
		return TransReqBF0040002TransReqDatas;
	}

	public void setTransReqBF0040002TransReqDatas(
			List<TransReqBF0040002> transReqBF0040002TransReqDatas) {
		TransReqBF0040002TransReqDatas = transReqBF0040002TransReqDatas;
	}

	public TransHead getTransHead() {
		return transHead;
	}

	public void setTransHead(TransHead transHead) {
		this.transHead = transHead;
	}

	

	public List<TransReqBF0040001> getTransReqDatas() {
		return transReqDatas;
	}

	public void setTransReqDatas(List<TransReqBF0040001> transReqDatas) {
		this.transReqDatas = transReqDatas;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}



}