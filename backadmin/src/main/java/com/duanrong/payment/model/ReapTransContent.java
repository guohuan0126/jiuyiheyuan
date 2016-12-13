package com.duanrong.payment.model;

import java.util.List;

import javax.xml.bind.annotation.*;


@XmlRootElement(name="trans_content")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReapTransContent implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2788996017600244616L;

	@XmlElement(name="trans_head")
	private TransHead transHead;

	
	
	@XmlElementWrapper(name="trans_reqDatas")    
	@XmlElement(name="trans_reqData")
	private List<TransRespBF0040002> transReqDatas;
	
	
	private transient String dataType;
	
	
	
	


	public TransHead getTransHead() {
		return transHead;
	}

	public void setTransHead(TransHead transHead) {
		this.transHead = transHead;
	}

	

	

	public List<TransRespBF0040002> getTransReqDatas() {
		return transReqDatas;
	}

	public void setTransReqDatas(List<TransRespBF0040002> transReqDatas) {
		this.transReqDatas = transReqDatas;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}



}