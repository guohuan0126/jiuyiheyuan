package com.duanrong.util.security;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * Created by xiao on 2016/5/13.
 */
public final class Hmac {

    private static final String HMAC_MD5 = "HmacMD5";
    private static final String HMAC_SHA256 = "HmacSHA256";

    private static Mac macMd5;
    private static Mac macSha256;
    
    static {
        try {
            macMd5  = Mac.getInstance(HMAC_MD5);
            macSha256  = Mac.getInstance(HMAC_SHA256);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 macMD5密钥
     * @return
     */
    public static byte[] initHmacMD5Key(){
        try {
            KeyGenerator generator = KeyGenerator.getInstance(HMAC_MD5);
            SecretKey key = generator.generateKey();
            return key.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 初始化 macSHA256密钥
     * @return
     */
    public static byte[] initHmacSHA256Key(){
        try {
            KeyGenerator generator = KeyGenerator.getInstance(HMAC_SHA256);
            SecretKey key = generator.generateKey();
            return key.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * macMD5 加密
     *
     * @param data
     * @return
     */
    public static byte[] hmacMD5(byte[] data, byte[] key) 
    		throws UnsupportedEncodingException, InvalidKeyException {   	
    	macMd5.init(new SecretKeySpec(key, HMAC_MD5));
        return macMd5.doFinal(data);
    	
    }
    
    /**
     * macMD5 加密
     *
     * @param data
     * @return
     */
    public static byte[] hmacSHA256(byte[] data, byte[] key) 
    		throws UnsupportedEncodingException, InvalidKeyException {   	
    	macSha256.init(new SecretKeySpec(key, HMAC_SHA256));
        return macSha256.doFinal(data);
    	
    }
}
