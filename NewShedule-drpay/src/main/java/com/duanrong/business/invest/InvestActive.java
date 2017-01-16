package com.duanrong.business.invest;

import com.duanrong.business.invest.model.Invest;

/*
 * 投资触发活动接口
 */
public abstract class InvestActive {
	
	private String msg;
	
	private String[] infomation;
	
	/**
	 * 活动执行接口
	 * @param investId
	 * @return
	 */
	abstract double execute(Invest invest);

	/**
	 * 活动id
	 * @return
	 */
	abstract String getActiveId();

	/**
	 * 活动名称
	 * @return
	 */
	abstract String getTitle();
	
	protected int random() {
		return (int) (Math.random() * 200);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String[] getInfomation() {
		return infomation;
	}

	public void setInfomation(String... infomation) {
		this.infomation = infomation;
	}
	
}
