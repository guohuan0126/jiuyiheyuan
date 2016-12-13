package com.duanrong.payment.jd.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.duanrong.payment.jd.model.QuickPcOrderInfo;
import com.duanrong.payment.jd.utils.RSACoder;
import com.duanrong.payment.jd.utils.SHA256Util;
import com.duanrong.payment.jd.utils.SignUtil;


public class SignHelper {
	private static Logger logger = Logger.getLogger(SignHelper.class);

	public static String getSign(QuickPcOrderInfo clientPayOrderInfo, String key) throws Exception {
		List<String> unSignedKeyList = new ArrayList<String>();
		unSignedKeyList.add("merchantSign");
		unSignedKeyList.add("version");
		unSignedKeyList.add("successCallbackUrl");
		
		if(StringUtils.isBlank(clientPayOrderInfo.getSpecifyInfoJson().toString())){
            unSignedKeyList.add("specifyInfoJson");
        }
		
		String strSourceData = SignUtil.signString(clientPayOrderInfo, unSignedKeyList);
		logger.info("source:"+strSourceData);
		
		 //摘要
		byte[] sha256SourceSignByte = SHA256Util.encrypt(strSourceData.getBytes("UTF-8"));
		//byte[] sha256SourceSignByte = Sha256Util.encrypt(strSourceData.getBytes("UTF-8"));
        //私钥对摘要进行加密
        byte[] newsks = RSACoder.encryptByPrivateKey(sha256SourceSignByte, key);
        String result = RSACoder.encryptBASE64(newsks);
		

		logger.info("sign:"+result);
		return result;

	}

	

	public static String urlEncode(String input) {
		try {
			if (input == null || input.length() == 0) {
				return "";
			}
			return URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}
	
}
