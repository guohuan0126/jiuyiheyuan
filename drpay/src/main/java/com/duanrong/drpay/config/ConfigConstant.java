package com.duanrong.drpay.config;

import java.io.File;

import com.duanrong.util.PropReader;

/**
 * config.properties配置
 */
public final class ConfigConstant {

	//properties 读取器
	private static PropReader rader = new PropReader("/constant/config.properties");
	
	/**
	 * 服务器
	 */
	public static String SERVERS = rader.readProperty("servers");
	
	/**
	 * 资源服务器
	 */
	public static String OSS_SERVER = rader.readProperty("oss_server");

	/**
	 * 本地日志 目录
	 */
	public static String LOGLOCALURL = System.getProperty("catalina.home")  + File.separator + "logs" + File.separator;
	
	/**
	 * pc callbackurl
	 */
	public static String CALLBACK_PC = rader.readProperty("callback_pc");
	
	/**
	 * m callbackurl
	 */
	public static String CALLBACK_M = rader.readProperty("callback_m");
}
