package com.duanrong.newadmin.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 * @Description: 日志记录工具类
 * @Author: 林志明
 * @CreateDate: Aug 28, 2014
 */
public class LogUtil {

	private static String hostName = LoadConstantProterties2
			.getValueByDefaultPro("hostName");

	private static String server = LoadConstantProterties2
			.getValueByDefaultPro("logmachine");

	// socket 超时参数（单位：毫秒）
	private static final int SOCKETTIMEOUT = 60000;

	// connection 超时参数（单位：毫秒）
	private static final int CONNECTIONTIMEOUT = 60000;

	private static CloseableHttpClient httpClient;

	// 文本格式
	private static final String CONTENT_TYPE = "text/plain;charset=UTF-8";

	private static final String serverUrl = "http://demosoa2-log.duanrong.net/log/error";

	private static final String serverUrl2 = "http://demosoa2-log.duanrong.net/log/info";

	private static final String NAME = LoadConstantProterties2
			.getValueByDefaultPro("logusername");

	private static final String PASS = LoadConstantProterties2
			.getValueByDefaultPro("logpass");

	private static String sign;

	static {
		try {
			sign = MD5Encry.Encry(NAME + ":" + PASS + "duanrong!@#");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 配置httpRequest超时参数
	 */
	private static RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(SOCKETTIMEOUT)
			.setConnectTimeout(CONNECTIONTIMEOUT).build();

	/**
	 * 发送http请求
	 * 
	 * @param <T>
	 * 
	 * @param request
	 * @return
	 */
	private static void sendHTTPRequest(String serverUrl, String title,
			String content) {
		httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(serverUrl);
		post.setConfig(requestConfig);
		post.addHeader("sign", sign);	
		List<NameValuePair> params = new ArrayList<>(); 
		params.add(new BasicNameValuePair("title", title)); 
		params.add(new BasicNameValuePair("content", content)); 
		params.add(new BasicNameValuePair("domain", hostName));
		params.add(new BasicNameValuePair("host", server)); 		
		try {
			post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			httpClient.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送http请求
	 * 
	 * @param <T>
	 * 
	 * @param request
	 * @return
	 */
	private static void sendHTTPRequest(String serverUrl, String title,
			String content, int level) {
		httpClient = HttpClients.createDefault();		
		HttpPost post = new HttpPost(serverUrl);
		post.setConfig(requestConfig);
		post.addHeader("sign", sign);
		List<NameValuePair> params = new ArrayList<>(); 
		params.add(new BasicNameValuePair("title", title)); 
		params.add(new BasicNameValuePair("content", content)); 
		params.add(new BasicNameValuePair("domain", hostName));
		params.add(new BasicNameValuePair("host", server)); 
		params.add(new BasicNameValuePair("level", ""+level));		
		try {			
			post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			httpClient.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 记录错误日志
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static void errLog(String title, String content) {
		sendHTTPRequest(serverUrl, title, content);
	}

	/**
	 * 记录错误日志
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param level
	 *            级别
	 */
	public static void errLog(String title, String content, int level) {

		sendHTTPRequest(serverUrl, title, content, level);
	}

	/**
	 * 记录错误日志
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static void errLog(String title, Exception ex) {
		sendHTTPRequest(serverUrl, title, ExceptionUtils.getStackTrace(ex));
	}

	/**
	 * 记录错误日志
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static void errLog(String title, Exception ex, int level) {
		sendHTTPRequest(serverUrl, title, ExceptionUtils.getStackTrace(ex),
				level);
	}

	/**
	 * 记录信息日志
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static void infoLog(String title, String content) {
		sendHTTPRequest(serverUrl2, title, content);
	}

	/**
	 * 记录信息日志
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static void infoLog(String title, String content, int level) {
		sendHTTPRequest(serverUrl2, title, content, level);
	}

	/**
	 * 记录信息日志
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static void infoLog(String title, Exception ex) {
		sendHTTPRequest(serverUrl2, title, ExceptionUtils.getStackTrace(ex));
	}

	/**
	 * 记录信息日志
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static void infoLog(String title, Exception ex, int level) {
		sendHTTPRequest(serverUrl2, title, ExceptionUtils.getStackTrace(ex),
				level);
	}
	
	
	public static void main(String[] args){
		
		LogUtil.infoLog("111", "1111");
		
	}
}
