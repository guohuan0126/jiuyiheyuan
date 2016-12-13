package com.duanrong.cps.util.xicaiwang;

 
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
 




import com.duanrong.cps.util.ReadProperties;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 
public class DesUtil {
 
		//加密
		public static String encrypt (String data,String key) throws Exception
		{
			byte[] bt=encrypt(data.getBytes(),key.getBytes());
			String strs=new BASE64Encoder().encode(bt);
			return strs;
		}
		//解密
		public static String decrypt(String data,String key)
		throws Exception
		{
			if(data==null)
			return null;
			BASE64Decoder decorder=new BASE64Decoder();
			byte[] buf=decorder.decodeBuffer(data);
			byte[] bt=decrypt(buf,key.getBytes());
			return new String(bt);
		}
	
		//加密方法
		public static byte[] encrypt(byte[] data,byte[] key) throws Exception
		{
			DESKeySpec dks=new DESKeySpec(key);
			SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
			SecretKey secretkey=keyFactory.generateSecret(dks);
			Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(key);
			cipher.init(Cipher.ENCRYPT_MODE, secretkey,iv);
			return cipher.doFinal(data);
		}
	
	
		//解密方法
		public static byte[] decrypt(byte[] data,byte[] key) throws Exception
		{
			DESKeySpec dks=new DESKeySpec(key);
			SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
			SecretKey secretkey=keyFactory.generateSecret(dks);
			Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(key);
			cipher.init(Cipher.DECRYPT_MODE, secretkey,iv);
			return cipher.doFinal(data);
		}
		
	   public static void main(String[] args) throws Exception{
		   String key=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "xicaiwang_key");
		   System.out.println(key);
		   String data="phone=18210132758&name=test&pid=160923162525350004&t=123456789&display=pc&userid=1083049";
		   System.out.println(URLEncoder.encode(DesUtil.encrypt(data, key))); 
		   System.out.println(DesUtil.decrypt(DesUtil.encrypt(data, key), key));
		   //t8tjeahgRVaCCu85NauMokPyPWZwXjIzKc2mNK8LLdvNfT6vzTFBYvhzI2x5ve7cDasNoATnH0UN%0D%0A36X4vffkd9OOT6me%2F%2FAQiWYyM%2Ffvkr43r2nl%2FnnN%2Ff5FSjz0fz%2BM



	   }
}
