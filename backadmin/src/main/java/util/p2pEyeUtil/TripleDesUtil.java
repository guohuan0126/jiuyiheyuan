package util.p2pEyeUtil;


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
			System.out.println("加密方法内:"+str);
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

		//String str = encrypt("foo=bar&baz=boom&cow=milk&send_time=unix_timestamp","123456781234567812345678");
		
		//String str03 = "qgXg4cS5Q40A6yoV79Ct63Dg2OizEA1agjd0Ptpr2V0eWoq2Gvu7Kv67%2FhiMBTb1Oc5DKAxStdaV6bWovo%2Bers7bCLnZGcWOrbRhJUZSwup9oB11dMg2YbJcWhX53KAIaiPZpPzmowaeGo62RKr6loKAxzMIrGDrjoNe8NflK54lNxqxxmQLWcrVO1x%2BMUsVIukYJ7wTCIw56Z%2F9Lq9l%2BfvGfuuCcw9rxcZqDuay5YIqJ1PKSjPWcphdgDgDpKi%2F6NOtjJ3K%2FSkU%2BUzGAVkUlaqF4lOhkxLtyno2%2BT%2F83zlpGno%2BP%2BWRzQ%3D%3D";
		//str03 = URLDecoder.decode(str03,"UTF-8");
		//System.out.println(decrypt(str03,"atAAzbo2i5vS2gpaVxmn9sND"));
	
		String str04 = "laSm1879Uw7pmheV7KytRpejcB1R1pnllud40v5/k437We6q94bKvEpF8A337H2Vdhp157iN58FthxgHeMhoEaFj7oD1Rol93+8Nya1NKWXjsjLrdv8czbFNdv/kCxrBUgPRFP1Qsf24WUIysT2t3VM5nsHQUqwS4uOxETgG5A2nS7XEB8CGdoKAAMWJJClmfy7F+jCjl16DtCnQZXb74UxQqKjxZIgRf1gB+ItId7afMiNaEFTH31xqHmb2WxZPDZHOWEFyLxpLoewdY+rhHx4P5mRDyqbJzYRsSCN+AMv+uVm31GQvyw==";
		String str05 = "laSm1879Uw7pmheV7KytRpejcB1R1pnllud40v5%2Fk437We6q94bKvEpF8A337H2Vdhp157iN58FthxgHeMhoEaFj7oD1Rol93%2B8Nya1NKWXjsjLrdv8czbFNdv%2FkCxrBUgPRFP1Qsf24WUIysT2t3VM5nsHQUqwS4uOxETgG5A2nS7XEB8CGdoKAAMWJJClmfy7F%2BjCjl16DtCnQZXb74UxQqKjxZIgRf1gB%2BItId7afMiNaEFTH31xqHmb2WxZPDZHOWEFyLxpLoewdY%2BrhHx4P5mRDyqbJzYRsSCN%2BAMv%2BuVm31GQvyw%3D%3D";
		
		String str06 = "qgXg4cS5Q40A6yoV79Ct63Dg2OizEA1agjd0Ptpr2V0eWoq2Gvu7Kv67%2FhiMBTb1U8hBkYEPHEMvilO0gSvzblWBEVkBSxJBkPHP%2BUVSsAP3Kqe1ON%2BUBari6U88HSli9g8dPzuVZjyWiteaFrrjY70zyiS0nHO4Mgz%2B67XSak5yoZhpik9WQaVtD4vdYAayZINjQey%2F9eECH2LX8Ywz8yblhiPemUe9Un0iEl6xbFJNEQneWgKL%2BF7WlQ52RxkicNY4%2BHbmFeL8SDM5Z%2F%2FlK8Q0dlpRhVFyBFAEyGO%2Bo2RDnmlcL6nujw%3D%3D";
		str06 = URLDecoder.decode(str06,"UTF-8");
		System.out.println(decrypt(str06,"atAAzbo2i5vS2gpaVxmn9sND"));
	}
	
	
}
