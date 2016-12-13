package com.duanrong.payment.model;

import java.util.List;

import javax.xml.bind.annotation.*;


@XmlRootElement(name="trans_content")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReapTransContentCallBack implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2788996017600244616L;

	
	@XmlElementWrapper(name="trans_reqDatas")    
	@XmlElement(name="trans_reqData")
	private List<TransRespBF0040002> transReqDatas;
	
	

	public List<TransRespBF0040002> getTransReqDatas() {
		return transReqDatas;
	}

	public void setTransReqDatas(List<TransRespBF0040002> transReqDatas) {
		this.transReqDatas = transReqDatas;
	}




}