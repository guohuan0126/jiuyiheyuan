package com.duanrong.util.p2pEyeUtil;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class TripleDesUtil {

public static String DESEDE = "DESede";
	
	/**
	 * 加密 
	 * @param input 加密数据
	 * @param key 秘钥
	 * @return
	 */
	public static String encrypt(String input, String key){
		byte[] crypted = null;
		String str = "";
		try{
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), DESEDE);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes("UTF-8"));
			str = new String(Base64.encodeBase64(crypted),"UTF-8");
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return str;
	}
	
	/**
	 * 解密
	 * @param input 解密数据
	 * @param key 秘钥
	 * @return
	 */
	public static String decrypt(String input, String key){
		byte[] output = null;
		String str = "";
		try{
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), DESEDE);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.decodeBase64(input.getBytes("UTF-8")));
			str = new String(output,"UTF-8");
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return str;
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {

		String str03 = "laSm1879Uw7pmheV7KytRpejcB1R1pnlwoXYzie9ROhbC17DRHLsKCxPYUmKyiSDHKOj1yD94DiyfTM8dcgu3jHzIycp9Pa73%2B8Nya1NKWXQtngf8UG48RyMOc35CeaxvvZleCnTg9EHK4ePZkBfJgAcYtxTzxXCTzAm3uX2OT3LWcifejWklEPfS2yBCh7f9c1NsLiDHCfu1pApCwN6FIoU1KAC2WQSMTBmZq7%2F2mjxrVcrUnabwnlMOhMauOULgFYrRAeSxhZpWBgHZlyAj2ZJmZIaAtFET%2Fca%2FFXGHDCDoC2Am6XqrQ%3D%3D";
		str03 = URLDecoder.decode(str03,"UTF-8");
		System.out.println(decrypt(str03,"123456781234567812345678"));
	}
}
