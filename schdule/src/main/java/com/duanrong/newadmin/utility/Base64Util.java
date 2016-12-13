package com.duanrong.newadmin.utility;

import java.io.UnsupportedEncodingException;

import com.cfca.util.pki.encoders.Base64;


/*
 * base64 编码
 */
public class Base64Util {

	public static String encodeStr(String plainText){
		byte[] b=plainText.getBytes();
		Base64 base64=new Base64();
		b=base64.encode(b);
		String s=new String(b);
		return s;
	}
	

	public static String decodeStr(String encodeStr){
		byte[] b=encodeStr.getBytes();
		Base64 base64=new Base64();
		b=base64.decode(b);
		String s=new String(b);
		return s;
	}
}
