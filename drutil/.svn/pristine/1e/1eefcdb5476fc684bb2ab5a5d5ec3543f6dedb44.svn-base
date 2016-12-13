package com.duanrong.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private static MessageDigest md;
    private static final String MD5 = "MD5";
    
	static {
		try {
			md = MessageDigest.getInstance(MD5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] encode(byte[] data){
		return md.digest(data);
	}

}
