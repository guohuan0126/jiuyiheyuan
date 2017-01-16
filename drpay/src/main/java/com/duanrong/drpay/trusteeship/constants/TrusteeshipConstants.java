package com.duanrong.drpay.trusteeship.constants;

import com.duanrong.util.PropReader;

/**
 * 存管通常量
 * @author xiao
 * @datetime 2016年12月7日 下午1:00:38
 */
public final class TrusteeshipConstants {
	
	//propertise 读取器
	private static PropReader reader = new PropReader("/constant/DepositoryConstants.properties");
	
	//存管网关
	public static final String REQUEST_URL = reader.readProperty("requestUrl") ;
	
	//网关服务
	public static final String GATEWAY = reader.readProperty("gateway");
	
	//直连服务
	public static final String SERVICE = reader.readProperty("service");
	
	//下载服务
	public static final String DOWNLOAD = reader.readProperty("download");
	
	//商户号
	public static final String PLATEFORM_NO = reader.readProperty("platformNo");

	//存管服务器通知地址
	public static final String NOTIFY_URL = reader.readProperty("notifyUrl");

}
