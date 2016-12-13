package com.duanrong.dataAnalysis.client;

import java.nio.charset.CodingErrorAction;

import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpConnectionManager {	

	private static int socketTimeout = 50;
	
	private static int connectTimeout = 50;
	
	private static int maxTotal = 200;
	
	private static int connectionRequestTimeout = 50;

	private static RequestConfig requestConfig;

	private static PoolingHttpClientConnectionManager connManager = null;

	//初始化httpClient连接池
	static {
		//实例化连接池管理器
		connManager = new PoolingHttpClientConnectionManager();
		
		//设置最大连接数
		connManager.setMaxTotal(maxTotal);
		
		 //将目标主机的最大连接数增加到50
	    HttpHost localhost = new HttpHost("localhost", 8080);
	    connManager.setMaxPerRoute(new HttpRoute(localhost), 50);
		
		SocketConfig socketConfig = SocketConfig.custom()
				.setTcpNoDelay(true).build();
		
		connManager.setDefaultSocketConfig(socketConfig);
		
		MessageConstraints messageConstraints = MessageConstraints.custom()
				.setMaxHeaderCount(200).setMaxLineLength(2000).build();
		
		ConnectionConfig connectionConfig = ConnectionConfig.custom()
				.setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE)
				.setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
		
		connManager.setDefaultConnectionConfig(connectionConfig);
		
		connManager.setMaxTotal(200);
		
		connManager.setDefaultMaxPerRoute(20);
		
	}
	
	
	public static  HttpClient getPoolHttpClient(){
		return HttpClients.custom().setConnectionManager(connManager).build();
		/*requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout).build();*/
		
	}
	
	public static  HttpClient getHttpClient(){
		return HttpClientBuilder.create().build();		
	}



}
