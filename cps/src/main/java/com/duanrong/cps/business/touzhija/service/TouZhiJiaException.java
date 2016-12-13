package com.duanrong.cps.business.touzhija.service;

public class TouZhiJiaException extends Exception {
	private static final long serialVersionUID = -2125031025287319917L;

	private int code;
	
	public TouZhiJiaException(int code, String message) {
		super(message);
		this.setCode(code);
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
