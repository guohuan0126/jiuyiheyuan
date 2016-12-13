package com.duanrong.payment.service;


import org.springframework.transaction.annotation.Transactional;

public interface PaymentService {
	@Transactional(rollbackFor = Exception.class)
	public String supplementOrder(String orderId,String transferWay);
}
