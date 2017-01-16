package com.duanrong.drpay.business.user.service;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-28下午6:28:11
 */
public interface AuthInfoService {

	/**
	 *  校验手机号和验证码是否匹配
	 * 
	 * @param mobileNumber
	 * @param authCode
	 * @param type
	 * @return
	 */
	public boolean operateAuthCode(String mobileNumber, String authCode,
								   String type);

	

}
