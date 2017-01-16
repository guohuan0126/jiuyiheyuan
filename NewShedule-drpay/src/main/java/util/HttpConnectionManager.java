package util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Http客户端管理器
 * @author Tiger
 *
 */
public class HttpConnectionManager {

	private static int SOCKET_TIMEOUT = 30000; //socket超时时间
	private static int CONNECTION_TIMEOUT = 30000;  //连接超时
	
	private static SSLConnectionSocketFactory socketFactory = null; //私密链接工厂
	
	private static RequestConfig defaultRequestconfig = null;
	
	private static RequestConfig requestConfig = null;
	
	private static Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
	
	private static TrustManager manager = new X509TrustManager() {
		
		@Override
		public X509Certificate[] getAcceptedIssuers() { return null; }
		
		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
		
		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
	};
	
	static{
		
		//SSL套字节
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[]{manager}, null);
			socketFactory = new SSLConnectionSocketFactory(context,NoopHostnameVerifier.INSTANCE);
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}
		
		//加密链接工厂创建
		socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http",PlainConnectionSocketFactory.INSTANCE).register("https",socketFactory).build();
		
		//请求配置
		defaultRequestconfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
		
		requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
		
	}

	
	/**
	 * 执行POST请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static HttpResult doSSLPost(String url,JSONObject params,String sign) throws IOException {
		
		//链接管理器创建
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(defaultRequestconfig).build();
		
		HttpPost httpPost = new HttpPost(url);
		if(sign != null){
			httpPost.addHeader("sign", sign);
		}
		if (params != null) {
			// 设置post参数
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			String fieldName = null;
			String value = null;
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator(); 
			while(it.hasNext()){
				fieldName = it.next();
				value = params.getString(fieldName);
				if(value != null) {
					parameters.add(new BasicNameValuePair(fieldName, value)); 
				}
			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
			if(httpClient != null){
				httpClient.close();
			}
		}
	}
	
	/**
	 * 执行POST请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static HttpResult doPost(String url,JSONObject params,String sign) throws IOException {
		
		// 创建http POST请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		if(sign != null){
			httpPost.addHeader("sign", sign);
		}
		if (params != null) {
			// 设置post参数
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			String fieldName = null;
			String value = null;
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator(); 
			while(it.hasNext()){
				fieldName = it.next();
				value = params.getString(fieldName);
				if(value != null) {
					parameters.add(new BasicNameValuePair(fieldName, value)); 
				}
			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
			if(httpClient != null){
				httpClient.close();
			}
		}
	}
	
	
	
}
