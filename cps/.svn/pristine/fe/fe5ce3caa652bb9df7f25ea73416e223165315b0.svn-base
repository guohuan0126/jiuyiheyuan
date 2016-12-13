package com.duanrong.cps.util;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class DesUtil1 {
	 private final static String DES = "DES";
	    public static String DesKey =  "duanRon!@";
	    public static void main(String[] args) throws Exception {
	    	
	       String url = "http://m.duanrong.com/app/goBindingCard?appData=";
	       JSONObject param = new JSONObject();
	       param.put("userId","EfqMJ322QZ7vkcfv");
	       param.put("userToken","a31937b1202f6b1d6f8727a42f18f1cb");
	       param.put("paymentId","JDpay");
	       param.put("bankCode","BOC");
	       param.put("source","ios");
	       String content = param.toString();
	       System.out.println(content.toString());
	       String data = encode(content);
	       System.out.println(url+data);
	    	//String data = null;
	        System.out.println("密文："+data);
	        System.out.println("原文："+decode(data));
	        
	    }
	    
	    
	    /**
	     * 加密
	     * @param content
	     * @return
	     */
	    public static String decode(String content){
	    	
	    	try {
				return decrypt(Base64Util.decodeStr(content), DesKey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;
	    }
	     
	    
	    /**
	     * 加密
	     * @param content
	     * @return
	     */
	    public static String encode(String content){
	    	
	    	try {
				return Base64Util.encodeStr(encrypt(content, DesKey));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;
	    }
	     
	    /**
	     * Description 根据键值进行加密
	     * @param data 
	     * @param key  加密键byte数组
	     * @return
	     * @throws Exception
	     */
	    public static String encrypt(String data, String key) throws Exception {
	        byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes("utf-8"));
	        String strs = new BASE64Encoder().encode(bt);
	        return strs;
	    }
	 
	    /**
	     * Description 根据键值进行解密
	     * @param data
	     * @param key  加密键byte数组
	     * @return
	     * @throws IOException
	     * @throws Exception
	     */
	   public static String decrypt(String data, String key) throws IOException,
	            Exception {
	        if (data == null)
	            return null;
	        BASE64Decoder decoder = new BASE64Decoder();
	        byte[] buf = decoder.decodeBuffer(data);
	        byte[] bt = decrypt(buf,key.getBytes("utf-8"));
	        return new String(bt,"utf-8");
	    }
	 
	    /**
	     * Description 根据键值进行加密
	     * @param data
	     * @param key  加密键byte数组
	     * @return
	     * @throws Exception
	     */
	   private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
	        // 生成一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	 
	        // 从原始密钥数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key);
	 
	        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	        SecretKey securekey = keyFactory.generateSecret(dks);
	 
	        // Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance(DES);
	 
	        // 用密钥初始化Cipher对象
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
	 
	        return cipher.doFinal(data);
	    }
	     
	     
	    /**
	     * Description 根据键值进行解密
	     * @param data
	     * @param key  加密键byte数组
	     * @return
	     * @throws Exception
	     */
	    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
	        // 生成一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	 
	        // 从原始密钥数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key);
	 
	        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	        SecretKey securekey = keyFactory.generateSecret(dks);
	 
	        // Cipher对象实际完成解密操作
	        Cipher cipher = Cipher.getInstance(DES);
	 
	        // 用密钥初始化Cipher对象
	        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
	 
	        return cipher.doFinal(data);
	    }
}
