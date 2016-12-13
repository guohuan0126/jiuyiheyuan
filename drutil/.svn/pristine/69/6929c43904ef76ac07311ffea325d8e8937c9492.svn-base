package com.duanrong.util.client;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * http 请求发送器, 对apache, httpclient进一步封装
 * @author xiao
 * @date 2016/3/17
 * @version 1.0
 * @description 封装 http 的 get 和 post
 * @vcersion 1.1
 * @description 修改 httpclient 为 TCP 短链接
 * 优化代码 增加 sendHTTPRequest(HttpRequestBase method, Header[] headers)
 * 和 sendHTTPRequest(HttpRequestBase method, Header[] headers) 方法（公共代码抽离封装）
 * @version 1.2
 * 添加https支持
 */
public final class DRHTTPClient {

	// socket 超时参数（单位：毫秒）
	private static final int SOCKET_TIMEOUT = 60000;

	// connection 超时参数（单位：毫秒）
	private static final int CONNECTION_TIMEOUT = 60000;

	//默认编码
	private static final String ENCODING = "UTF-8";

	//请求参数配置
	private static RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(SOCKET_TIMEOUT)
			.setConnectTimeout(CONNECTION_TIMEOUT).build();


	/**
	 * 创建httpClient
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @return CloseableHttpClient
	 */
	public static CloseableHttpClient createClientDefault(){
		return HttpClients.createDefault();
	}


	/**
	 * 实现 gzip 的httpClient支持, 需要服务端支持 gzip
	 * gzip适用于大数据传输，以节省网络传输带宽，但会消耗cpu,
	 * 优于使用了两次拦截器，会降低一定的效率，根据具体情况谨慎使用
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @return
     */
	public static CloseableHttpClient createGzipClientDefault(){

		return HttpClients.custom().addInterceptorFirst(new GzipHttpRequestInterceptor())
				.addInterceptorLast(new GzipHttpResponseInterveptor()).build();
	}

	/**
	 * 创建httpsClient
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @return CloseableHttpClient
     */
	public static CloseableHttpClient createGzipSSLClientDefault(){
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new DRTrustStrategy()).build();
			//SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);
			//允许不校验证书
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return HttpClients.custom().addInterceptorFirst(new GzipHttpRequestInterceptor())
					.addInterceptorLast(new GzipHttpResponseInterveptor()).setSSLSocketFactory(sslSocketFactory).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return createGzipClientDefault();
	}


	/**
	 * 创建httpsClient
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @return CloseableHttpClient
	 */
	public static CloseableHttpClient createSSLClientDefault(){
		try {
			//httpclient 4.3.X以前写法
			/*SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] {new DRX509TrustManager()}, null);*/

			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new DRTrustStrategy()).build();
			//SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);
			//允许不校验证书
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return createClientDefault();
	}


	/**
	 * 发送 post 请求
	 * @author xiao
	 * @date 2016/3/17
	 * @version 1.0
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @return HttpResponse
	 */
	@Deprecated
	public static HttpResponse sendHTTPRequestPost(
			String url, Header[] headers, List<NameValuePair> params)
			throws IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		return sendHTTPRequest(createClientDefault(), post, headers);
	}

	/**
	 * 发送 get 请求
	 * @author xiao
	 * @date 2016/3/17
	 * @version 1.0
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @return HttpResponse
	 */
	@Deprecated
	public static HttpResponse sendHTTPRequestGet(
			String url, Header[] headers, List<NameValuePair> params)
			throws IOException {
		HttpGet get = new HttpGet(url+ "?"+ EntityUtils.toString(
				new UrlEncodedFormEntity(params,"UTF-8")));
		return sendHTTPRequest(createClientDefault(), get, headers);
	}


	/**
	 * 发送 get 请求
	 * @author xiao
	 * @date 2016/4/18
	 * @version 1.1
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @param encoding 编码
	 * @return String
	 */
	public static String sendHTTPRequestGetToString(
			String url, Header[] headers, List<NameValuePair> params, String encoding)
			throws IOException {
		HttpGet get = new HttpGet(url + "?" + EntityUtils.toString(
				new UrlEncodedFormEntity(params, encoding)));
		return sendHTTPRequestToString(createClientDefault(), get, headers, encoding);

	}


	/**
	 * 发送 get 请求
	 * @author xiao
	 * @date 2016/3/17
	 * @version 1.0
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @return String
	 */
	public static String sendHTTPRequestGetToString(
			String url, Header[] headers, List<NameValuePair> params)
			throws IOException {
		HttpGet get = new HttpGet(url + "?" + EntityUtils.toString(
				new UrlEncodedFormEntity(params, "UTF-8")));
		return sendHTTPRequestToString(createClientDefault(), get, headers, "UTF-8");

	}

	/**
	 * 发送  post 请求
	 * @author xiao
	 * @date 2016/3/17
	 * @version 1.0
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @param encoding 编码
	 * @return String
	 */
	public static String sendHTTPRequestPostToString(
			String url, Header[] headers, List<NameValuePair> params, String encoding)
			throws IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params, encoding));
		return sendHTTPRequestToString(createClientDefault(), post, headers, encoding);
	}

	/**
	 * 发送  post 请求
	 * @author xiao
	 * @date 2016/3/17
	 * @version 1.0
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @return String
	 */
	public static String sendHTTPRequestPostToString(
			String url, Header[] headers, List<NameValuePair> params)
			throws IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params, ENCODING));
		return sendHTTPRequestToString(createClientDefault(), post, headers, ENCODING);
	}



	/**
	 * 发送 post 请求
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @param httpClient
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @return HttpResponse
	 */
	public static HttpResponse sendHTTPRequestPost(
			CloseableHttpClient httpClient, String url,
			Header[] headers, List<NameValuePair> params)
			throws IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params, ENCODING));
		return sendHTTPRequest(httpClient, post, headers);
	}

	/**
	 * 发送 get 请求
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @param httpClient
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @return HttpResponse
	 */
	public static HttpResponse sendHTTPRequestGet(
			CloseableHttpClient httpClient, String url, Header[] headers,
			List<NameValuePair> params)
			throws IOException {
		HttpGet get = new HttpGet(url+ "?"+ EntityUtils.toString(
				new UrlEncodedFormEntity(params,ENCODING)));
		return sendHTTPRequest(httpClient, get, headers);
	}


	/**
	 * 发送 get 请求
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @param httpClient
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @param encoding 编码
	 * @return String
	 */
	public static String sendHTTPRequestGetToString(
			CloseableHttpClient httpClient, String url, Header[] headers,
			List<NameValuePair> params, String encoding)
			throws IOException {
		HttpGet get = new HttpGet(url + "?" + EntityUtils.toString(
				new UrlEncodedFormEntity(params, encoding)));
		return sendHTTPRequestToString(httpClient, get, headers, encoding);

	}


	/**
	 * 发送 get 请求
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @param httpClient
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @return String
	 */
	public static String sendHTTPRequestGetToString(
			CloseableHttpClient httpClient,	String url,
			Header[] headers, List<NameValuePair> params)
			throws IOException {
		HttpGet get = new HttpGet(url + "?" + EntityUtils.toString(
				new UrlEncodedFormEntity(params, ENCODING)));
		return sendHTTPRequestToString(httpClient, get, headers, ENCODING);

	}

	/**
	 * 发送  post 请求
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @param httpClient
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @param encoding 编码
	 * @return String
	 */
	public static String sendHTTPRequestPostToString(
			CloseableHttpClient httpClient, String url, Header[] headers,
			List<NameValuePair> params, String encoding)
			throws IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params, encoding));
		return sendHTTPRequestToString(httpClient, post, headers, encoding);
	}

	/**
	 * 发送  post 请求
	 * @author xiao
	 * @date 2016/4/22
	 * @version 1.2.0
	 * @param httpClient
	 * @param url 请求url
	 * @param headers 请求header信息
	 * @param params 请求数据
	 * @return String
	 */
	public static String sendHTTPRequestPostToString(
			CloseableHttpClient httpClient, String url,
			Header[] headers, List<NameValuePair> params)
			throws IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params, ENCODING));
		return sendHTTPRequestToString(httpClient, post, headers, ENCODING);
	}




	/**
	 * 发送 http 请求HTTP（短链接）
	 * @author xiao
	 * @param method
	 * @param headers
	 * @version 1.0
	 * @date 2016/4/5
	 * @param httpClient
	 * @version 1.1
	 * @date 2016/4/22
	 * @description 优化httpClient，可以配置httpClient特性
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	private static HttpResponse sendHTTPRequest(
			CloseableHttpClient httpClient, HttpRequestBase method, Header[] headers)
			throws IOException {
		try{
			//设置请求参数
			method.setConfig(requestConfig);
			// TCP 短链接配置
			method.setProtocolVersion(HttpVersion.HTTP_1_0);
			method.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE );
			method.setHeaders(headers);
			return httpClient.execute(method);
		} finally {
			//httpClient.close();
		}
	}


	/**
	 * 发送 http 请求HTTP（短链接）
	 * @author xiao
	 * @param method
	 * @param headers
	 * @version 1.0
	 * @date 2016/4/5
	 * @param httpClient
	 * @version 1.1
	 * @date 2016/4/22
	 * @description 优化httpClient，可以配置httpClient特性
	 * @return
	 * @throws IOException
	 */
	private static String sendHTTPRequestToString(
			CloseableHttpClient httpClient, HttpRequestBase method, Header[] headers, String encoding)
			throws IOException {
		try{
			//设置请求参数
			method.setConfig(requestConfig);
			// TCP 短链接配置
			method.setProtocolVersion(HttpVersion.HTTP_1_0);
			method.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE );
			method.setHeaders(headers);
			HttpResponse response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, encoding);
			return result;
		} finally {
			httpClient.close();
		}
	}

}