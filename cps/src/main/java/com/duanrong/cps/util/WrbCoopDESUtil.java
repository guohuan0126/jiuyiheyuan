package com.duanrong.cps.util;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * 合作方RES类库
 * @author Administrator
 *
 */
public class WrbCoopDESUtil {
	
	private static final Logger LOG = Logger.getLogger(WrbCoopDESUtil.class);
	
    private static byte[] defaultIV = { 0x23, (byte)0xf2, (byte)0xa3, (byte)0xc6, 0x3e, 0x2b, (byte)0xe7, 0x28 };
    
	private static final String Algorithm = "DESede";
	
	private static final String encoding = "utf-8";
	
	/**
	 * des加密
	 * 
	 * @param keybyte
	 * @param src
	 * @return
	 * @throws Exception 
	 */
	public static String desEncrypt(String key, String src) throws Exception {
		return Base64.encodeBase64String(desEncryptBytes(key, src));
	}
	
	/**
	 * des加密
	 * 
	 * @param key
	 * @param src
	 * @param charSet
	 * @return
	 */
	private static byte[] desEncryptBytes(String key, String src) throws Exception{
			SecretKey deskey = new SecretKeySpec(Base64.decodeBase64(key), Algorithm); // 加密
			Cipher c1 = Cipher.getInstance(Algorithm +"/CBC/PKCS5Padding");
			IvParameterSpec IVSpec = IvGenerator(defaultIV);
			c1.init(Cipher.ENCRYPT_MODE, deskey, IVSpec);
			LOG.debug("to encrypt str:" + src);
			return c1.doFinal(regularCryptStr(src, encoding));
	}

	
	/**
	 * des解密
	 * 
	 * @param keybyte
	 * @param src
	 * @return
	 * @throws Exception 
	 */
	public static String desDecrypt(String key, String src) throws Exception {
		return desDecryptBytes(key, Base64.decodeBase64(src));
	}

	/**
	 * des解密
	 * 
	 * @param keybyte
	 * @param srcBytes
	 * @return
	 * @throws  
	 * @throws Exception 
	 */
	private static String desDecryptBytes(String key, byte[] srcBytes) throws Exception {
			SecretKey deskey = new SecretKeySpec(Base64.decodeBase64(key), Algorithm); // 解密
			IvParameterSpec IVSpec = IvGenerator(defaultIV);
			Cipher c1 = Cipher.getInstance(Algorithm +"/CBC/PKCS5Padding");
			c1.init(Cipher.DECRYPT_MODE, deskey, IVSpec);
			return new String(c1.doFinal(srcBytes), encoding).trim();
	}

	
	/**
	 * 格式化待加密字符串，使其长度为8的倍数
	 * 
	 * @param strIn
	 * @return
	 */
	private static byte[] regularCryptStr(String strIn, String charSet) throws Exception {
		ByteBuffer buffer = null;
		int iLen = 8 - strIn.getBytes(charSet).length % 8;
		if (iLen != 8) {
			buffer = ByteBuffer.allocate(strIn.getBytes(charSet).length + iLen);
			buffer.put(strIn.getBytes(charSet));
			return buffer.array();
		} else {
			return strIn.getBytes(charSet);
		}
	}
	
	
	public static String genKey() throws NoSuchAlgorithmException{
		byte[] keyBytes = UUID.randomUUID().toString().getBytes();
		KeyGenerator generator = KeyGenerator.getInstance("DESede");
		generator.init( new SecureRandom( keyBytes ) );
		SecretKey key = generator.generateKey();
		return Base64.encodeBase64String(key.getEncoded());
	}
	
	

	private static IvParameterSpec IvGenerator(byte b[]) throws Exception {
		IvParameterSpec IV = new IvParameterSpec(b);
		return IV;
	}
	
	public static void main(String[] args) throws Exception {
		String deskey = genKey();
  System.out.println(deskey);
	}
	
}
