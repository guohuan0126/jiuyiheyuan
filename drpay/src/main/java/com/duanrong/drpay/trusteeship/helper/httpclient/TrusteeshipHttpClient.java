package com.duanrong.drpay.trusteeship.helper.httpclient;

import com.duanrong.drpay.trusteeship.constants.TrusteeshipConstants;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.util.client.DRHTTPClient;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 存管通请求工具
 * @author xiao
 * @datetime 2016年12月7日 下午2:48:52
 */
public class TrusteeshipHttpClient {

	private static Log log = new Log();
	
	private static final String keySerial = "1";
	/**
	 * send
	 * @param url 接口地址
	 * @param server 接口
	 * @param data data(json)
	 * @param sign 签名
	 * @return
	 */
	public static String send(String url, TrusteeshipServer server, String data, String sign){
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("serviceName", server.name()));
		params.add(new BasicNameValuePair("platformNo", TrusteeshipConstants.PLATEFORM_NO));
		params.add(new BasicNameValuePair("reqData", data));
		params.add(new BasicNameValuePair("keySerial", keySerial));
		params.add(new BasicNameValuePair("sign", sign));
		try {
			return DRHTTPClient.sendHTTPRequestPostToString(DRHTTPClient.createSSLClientDefault(), url, new Header[0], params);
		} catch (IOException e) {
			log.errLog("请求发送失败", e);
			e.printStackTrace();
		}
		return "";
	}
	
}
