package com.duanrong.drpay.trusteeship.helper.service;

import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;

/**
 * 存管业务接口，定义存管业务模板
 * 
 * @author xiao
 * @datetime 2016年12月6日 下午5:17:37
 */
public interface TrusteeshipService {

	/**
	 * 创建网关请求
	 * @param json
	 * @param server
	 * @return
	 */
	Generator create(GeneratorJSON json, TrusteeshipServer server);
	
	/**
	 * 创建网关请求
	 * @param json
	 * @param server
	 * @param businessType
	 * @return
	 */
	Generator create(GeneratorJSON json, TrusteeshipServer server, BusinessEnum businessType);

	/**
	 * 直连接口执行方法
	 * 
	 * @param sign
	 * @param reqData
	 * @return
	 */
	Generator execute(GeneratorJSON json, TrusteeshipServer server,
					  Class<? extends GeneratorJSON> generatorJSONClass);
	
	/**
	 * 直连接口执行方法
	 * 
	 * @param sign
	 * @param reqData
	 * @return
	 */
	Generator execute(GeneratorJSON json, TrusteeshipServer server,
					  Class<? extends GeneratorJSON> generatorJSONClass, BusinessEnum businessType);

	/**
	 * callback
	 * 
	 * @param data
	 * @return
	 */
	String callback(String data);

	/**
	 * s2sCallback
	 * 
	 * @param data
	 * @return
	 */
	String notify(String data);
}
