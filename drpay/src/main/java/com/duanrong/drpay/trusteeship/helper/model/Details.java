package com.duanrong.drpay.trusteeship.helper.model;

import java.util.List;

/**
 * 批量回调实体
 * @author xiao
 * @datetime 2017年1月4日 下午7:09:01
 */
public class Details {
	
	private List<GeneratorAsynDetailJSON> details;

	public List<GeneratorAsynDetailJSON> getDetails() {
		return details;
	}

	public void setDetails(List<GeneratorAsynDetailJSON> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Details [details=" + details + "]";
	}
	
	
}
