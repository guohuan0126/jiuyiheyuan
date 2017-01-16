package com.duanrong.drpay.trusteeship.helper.model;

import com.duanrong.drpay.trusteeship.constants.TrusteeshipConstants;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;

/**
 * 通用参数
 * 
 * @author xiao
 * @datetime 2016年12月8日 下午8:07:39
 */
public class Generator {

	// 接口
	private String reqUrl;

	// 接口
	private TrusteeshipServer serviceName;

	// 平台编号（固定值）
	private String platformNo = TrusteeshipConstants.PLATEFORM_NO;

	// 客户端类型
	private UserDevice userDevice = UserDevice.PC;

	// 正式序号
	private String keySerial = "1";

	// 签名
	private String sign;

	// 请求数据
	private String reqData;

	// 响应数据
	private GeneratorJSON respData;

	
	public Generator() {	
	}

	/**
	 * 
	 * @param reqUrl 接口地址
	 * @param serviceName 接口名称
	 * @param sign 签名 
	 * @param reqData 请求数据
	 */
	public Generator(String reqUrl, TrusteeshipServer serviceName, String sign,
			String reqData) {
		this.reqUrl = reqUrl;
		this.serviceName = serviceName;
		this.sign = sign;
		this.reqData = reqData;
	}
	
	/**
	 * 
	 * @param reqUrl 接口地址
	 * @param serviceName 接口名称
	 * @param userDevice 客户端类型
	 * @param sign 签名 
	 * @param reqData 请求数据
	 */
	public Generator(String reqUrl, TrusteeshipServer serviceName,
					 UserDevice userDevice, String sign, String reqData) {
		this.reqUrl = reqUrl;
		this.serviceName = serviceName;
		this.userDevice = userDevice;
		this.sign = sign;
		this.reqData = reqData;
	}
	
	/**
	 * 
	 * @param reqUrl 接口地址
	 * @param serviceName 接口名称
	 * @param userDevice 客户端类型
	 * @param sign 签名 
	 * @param reqData 请求数据
	 */
	public Generator(String reqUrl, TrusteeshipServer serviceName,
					 UserDevice userDevice, String sign, String reqData, GeneratorJSON respData) {
		this.reqUrl = reqUrl;
		this.serviceName = serviceName;
		this.userDevice = userDevice;
		this.sign = sign;
		this.reqData = reqData;
		this.respData = respData;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public TrusteeshipServer getServiceName() {
		return serviceName;
	}

	public void setServiceName(TrusteeshipServer serviceName) {
		this.serviceName = serviceName;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public UserDevice getUserDevice() {
		return userDevice;
	}

	public void setUserDevice(UserDevice userDevice) {
		this.userDevice = userDevice;
	}

	public String getKeySerial() {
		return keySerial;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getReqData() {
		return reqData;
	}

	public void setReqData(String reqData) {
		this.reqData = reqData;
	}

	public GeneratorJSON getRespData() {
		return respData;
	}

	public void setRespData(GeneratorJSON respData) {
		this.respData = respData;
	}

}