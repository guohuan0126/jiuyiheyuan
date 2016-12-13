package util;

import com.duanrong.util.*;
/*
 * 生成与SOA通信的密钥
 */
public class SOAKeyUtility {

		/**
		 * 根据密钥生成默认的密文
		 * @return
		 */
		public static String CreateSOAKey()
		{
			String key = LoadConstantProterties2.getValueByDefaultPro("soakey");
			return MD5Encry.Encry(key);
		}
		/**
		 * 根据密钥生成默认的密文
		 * @return
		 */
		public static String CreateSOAKey(String plainText)
		{
			String key = LoadConstantProterties2.getValueByDefaultPro("soakey");
			key = plainText.trim() + key;
			return MD5Encry.Encry(key);
		}
		
}
