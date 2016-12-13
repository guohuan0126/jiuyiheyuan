package com.duanrong.util;

import java.util.UUID;

public class IdGenerator {
	
	public static String randomUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
