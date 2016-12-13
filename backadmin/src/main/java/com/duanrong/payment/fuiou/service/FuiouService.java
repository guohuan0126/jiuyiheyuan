package com.duanrong.payment.fuiou.service;

import java.util.Map;


public interface FuiouService{
	public Map<String,String> queryOrderInfoByOrderId(String orderId);
}
