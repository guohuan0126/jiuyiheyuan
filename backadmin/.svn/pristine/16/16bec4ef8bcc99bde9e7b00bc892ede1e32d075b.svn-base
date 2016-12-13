package com.duanrong.newadmin.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/*
 * md5 加密
 */
public class MD5Encry {
	public final static String Encode="UTF-8";
	
	public static String Encry(String text)
	{
		try { 
				MessageDigest md = MessageDigest.getInstance("MD5"); 
				md.update(text.getBytes()); 
				byte b[] = md.digest(); 
	
				int i; 
	
				StringBuffer buf = new StringBuffer(""); 
				for (int offset = 0; offset < b.length; offset++) { 
					i = b[offset]; 
					if(i<0) i+= 256; 
					if(i<16) 
					buf.append("0"); 
					buf.append(Integer.toHexString(i)); 
				} 
				
				return buf.toString();

			} catch (NoSuchAlgorithmException e) { 
				return "";
			} 	
		}
	
	public static  String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

        try {
            byte[] btInput = s.getBytes(Encode);
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	public static void main(String[] args){
		//http://localhost:8080/UdeskInter/getClientComponent/?mobileNum=18514617725&timestamp=20160721164024&sign=a77f059142635abeb929bafc3b5d936c
		System.out.println(MD5Encry.Encry("mobileNum=18514617725&timestamp=20160721164024&ae2a6f05f49be42b098beff235903957"));
	}
	
}

