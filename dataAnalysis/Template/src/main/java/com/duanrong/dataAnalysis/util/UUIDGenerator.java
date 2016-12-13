package com.duanrong.dataAnalysis.util;
import java.util.UUID;

public class UUIDGenerator {
	
	/*
	 * 生成UUID字符串
	 */
	public static String randomUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
