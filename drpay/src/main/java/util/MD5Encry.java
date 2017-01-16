package util;

import java.io.UnsupportedEncodingException;

import com.duanrong.util.security.HexConvers;
import com.duanrong.util.security.SHA;

/*
 * md5 加密
 */
public class MD5Encry {
	public final static String ENCODING = "UTF-8";
	static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static String Encry(String text) {
		try {
			return HexConvers.hexDigits(SHA.encodeSHA1(text.getBytes(ENCODING)), hexDigits);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}