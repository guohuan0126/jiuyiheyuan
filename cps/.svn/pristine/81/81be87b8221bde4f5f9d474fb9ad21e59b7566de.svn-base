package  com.duanrong.cps.client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


/**
 * @description
 * @author xiao
 * @date 2015年8月18日下午5:40:37
 * @version 1.0
 */
public class DRClient {

	// socket 超时参数（单位：毫秒）
	private static final int SOCKETTIMEOUT = 60000;

	// connection 超时参数（单位：毫秒）
	private static final int CONNECTIONTIMEOUT = 60000;

	private static CloseableHttpClient httpClient;

	// 编码格式
	private static final String ENCODING = "UTF-8";

	// 文本格式
	private static final String CONTENT_TYPE = "text/json;charset=UTF-8";

	// 版本号
	private static final String VERSION = "1.0";

	/**
	 * 配置httpRequest超时参数
	 */
	private static RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(SOCKETTIMEOUT)
			.setConnectTimeout(CONNECTIONTIMEOUT).build();

	/**
	 * 发送 get 请求
	 * @param <T>
	 * 
	 * @param requestUrl
	 *            请求url
	 * @param sign
	 *            签名
	 * @return
	 */
	public static <T>  void sendHTTPRequestGET(DREntity<T> entity) {		
		String params = entity.getParam();
		String requestUrl = entity.getRequestUrl();		
		if(null != params && !("").equals(params.trim())){
			if (requestUrl.contains("?")) {
				requestUrl += "&req=" + params;
			} else {
				requestUrl += "?req=" + params;
			}
		}		
		HttpGet get = new HttpGet(requestUrl);
		get.setConfig(requestConfig);
		get.addHeader("sign", entity.getSign());
		sendHTTPRequest(get, entity);		
	}
	
	
	
	
	
	

	/**
	 * 发送 post 请求
	 * @param <T>
	 * 
	 * @param requestUrl
	 * @param sign
	 * @return
	 */
	public static <T> void sendHTTPRequestPOST(DREntity<T> entity) {
		String params = entity.getParam();	
		HttpPost post = new HttpPost(entity.getRequestUrl());
		post.setConfig(requestConfig);
		post.setHeader("Content-Type", CONTENT_TYPE);
		post.addHeader("sign", entity.getSign());
		if(null != params && !("").equals(params.trim())){
			StringEntity stringEntity = new StringEntity(params, ENCODING);
			entity.setContentType(CONTENT_TYPE);
			entity.setContentEncoding(ENCODING);
			post.setEntity(stringEntity);		
		}	
		sendHTTPRequest(post, entity);
	}

	/**
	 * 发送http请求
	 * @param <T>
	 * 
	 * @param request
	 * @return
	 */
	private static <T> void sendHTTPRequest(HttpRequestBase request, DREntity<T> entity) {
		httpClient = HttpClients.createDefault();
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);			
			entity.setStatus(response.getStatusLine().getStatusCode());
			//HttpEntity httpEntity = response.getEntity();
			//entity.setResult(EntityUtils.toString(httpEntity));
			entity.setStatusLine(response.getStatusLine().toString());
			//entity.setContentType(httpEntity.getContentType().toString());
			//entity.setContentEncoding(httpEntity.getContentEncoding().toString());
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {			
			try {
				httpClient.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	public static void main(String[] args){
		
		
		
		
		
		
	}
}
