package com.duanrong.util.p2pEyeUtil;

import java.util.UUID;

public class IdGenerator {
	
	public static String randomUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
