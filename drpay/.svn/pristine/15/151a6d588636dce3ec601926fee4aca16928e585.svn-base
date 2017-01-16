package com.duanrong.drpay.trusteeship.helper.sign;

import com.duanrong.drpay.config.ConfigConstant;
import com.duanrong.util.security.SHA1withRSA;

import org.bouncycastle.util.encoders.Base64;

import util.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 存管通 签名/验签
 * 
 * @author xiao
 * @datetime 2016年12月7日 上午10:31:38
 */
public class Sign {

	private final static String ENCODING = "utf-8";

	// 灰度存管通公钥
	private final static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsy12M/8+552dNuHUSvMSCo97vvpO07J57GkTcyrwG7u4n8T8ny/k12S9p3WQ5zImconKv8ZOEYoJzk9sDBPH1D0JVw08nr58+fKokBRJwD9KTG3f297Hz89a1LMrMV4KaJsoC8Ls6wHewFz0SrLz35IcDZsTxm+2L0PNmsczxxO5TD3BTetD4A/OSRWEbNROW0CXqokYzHRgmOTymBHIpTbpzIyZ1w1GppLR0CpUYpbXZJOiWWCT03dVuY92Vu5bDUKJJGxVtEbUyYr8fyzGY17mBpkLov39jDUZhRWD5a6NC7JodvfkSwJpL7MLgGph4l8YCMVMH/vrm4F6MR176QIDAQAB";

	// drpay 灰度密钥
	private final static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDQR2WBk4YLvJhDWxATrsj4Njh7XqUIBeNlBTF6fLqBHjoL9rboYx4p3UrjSI6j6vdoW4H4tkcYon6BdJmLrxHmDDdlxondWgg5lluiXOyMsZe9Og5SV3H7z5uITL5zmQQXyDTn2HYzNRSjNGe1pMxYvDwW+/pRo8teFdhIJrR8LnVtErv0B4zgIkk6h4uGPxmZy82E2bCkdlb1Lk+L6KAZSK2HvD3CyyoA9P0bMdOsHR1OC2cRnrG33u43ktTXccmQdaxQvqiRocuItPiwUDEdbKQzzCAG3wEsWqtDM3Ix0+qeZ7Kk1oSxEp3t2+48z3ij9nitUqo0ltoAIBTLa7GbAgMBAAECggEAWTKqeyLV3oLnZrlCGlvZf8ugxt0SqmvzJdlihkWSjieGzobbcIy6Z015e/sf8312dX/zGrPdoOQ+b2yjMZpJfQ8wTmsHsf+T1C3bkp5fRCXuiUCqz4LZsvEHeAj9633VreW1GrGudWG/M+IGpFkulrYOuf0yB/Kw7ttGJChHVYK9SAUTlq+16LkWkWSsTbnZir931A9CGujLXNNU+D9DT/43n2WO5zf2DZ6FRu4qGUNx0H+j2n1FeqrdeYInhnbH3nhQXzNwbcE4NlQWpiAS2ioFrVfV1eHSu3oZCjq50uFLDXkQYgkIVzTWd/0ks+o+JB+6rj4Tb8MPCQJz3yTxOQKBgQD/YOwn6SfJ7nre/4tC2NsEieKBxtn4FRgGJ6i3FWXMXcbIM1mma7Tk/c98WVBpPATcfJaGBqC4d8vo5U5OizPvI9EziZkB3m7aOmLoWQA2Sk6DuwNSRUIYMyGKY/KCAPXpP/URpyA9q5BPDg/GFda5KxAtqOAWiQp6cVpXUej4lwKBgQDQySKdPRd8ToY64PVteh7HOhiS9FKvMPLLX+0jrltkShLLyP7w8ChKTmrJigBFnxl+MbKSDbotYvyJQPDrwSlM7XKwzcCe8gJ28TqDdvuxSeuWsf72eaRY4schCIT0CyaSqPTXj7C6eS4pEbFtCKK3xVEXzZPhclYyOb3g/uFLnQKBgHvhWU1W9b0n2SS5k6Veqoe+F0la27tTCC9cJ6SAVbxYNShCeJeZIcv/Lxoj/TS/L6CBM0daibZSLnEQYvcI4a18k2vLsJROgTmNHxOUsNylGQcfvdG31QMGMpv+NdJIgwGdy9S/XI1t3vfqW058o4d5U0TC6ckkuxeASxGWo0uLAoGAK6oE7TF7memqNOIbMdwaV7wUq5chO7ZW8KWNKKssPdsn9yp/9s+i/XdM6f1MdUMCx9uI5var+xX6jpHgUM3C323BUKyXHiaq0Uk93YSiur8k4A01r0JdVqGG16e6dndmh8GCAxrv8O+q8bfbjVwC+7/uZ7X8uWd6E2L0DPwBDhkCgYEArfWr17vKE7YnE9OIeDxL1jBzALFNSfuljmM88WGeCkmxnYybN9yvWQqY6gV4lp2Pg8IuAhPJ0pkK3oucqAgo4DRZxPbwxNSiyJPtXv0Lg2t5rpRyI/ABuTpeWWIzDRUG3xETvIRmuOhHQJGhNWvSHs2n+nSy4jZVKcyl+a97bGo=";

	// 测试存管通公钥
//		private final static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjthU0Y9zs2gUWwTvr2dN/e4VxxcBfKAPl3HgDBZlwY8nw8LSs8298CcxI2Wnab/pRfhcYAV6PZD7huPu82yzc6MIfrRZBi6BL6KFIBDfWWQ6Ti72mUoQkUfb8g5wF1/odzlH7XM7ShZ3v5PdYKi+yb95uHJxeLh9BhpUI3Pz5x0tA9AYtMlrXr7o3qKjAQmRegT+89oistViUZGV3Xh8QWWwOvErKt9E+mIGK2r8rXbrLhxyzf0V38u+8Tw+BLglHBfcEIZIzt5GPephNNfF62ddhqbctQCxOJ/A204xQuxfhJoQ7hjIRzJeHSe9TrtCMKKAQoeGVVtmktCMYqR0ZQIDAQAB";

	// drpay 测试密钥
//	private final static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCI2FSWnWTiS4cAhyawX2SWSI2lFUuQTBH7GXZz7wkudewGfQPd1ufh3MR7ISL+lgV4BR1YdlQh+JB78sTBA+yNclyvxXmT8jU8jaRBbUYQRjsHvZu0LFlOFLGHuDcYC4KTzaFmufCAP4+4oUFBSc+YdmREmvDLLWlxmINK9cJZO4tgwwlEEyNR6aAhtov2Y9q17nSNMHp8+Lxzg7UAOrVNH8/cmSi673KLgKVF5FsD4mG4BZLUThKyExnzNXGuR5GtR6wCidnuYKaO96Z59XRqA3XGd3RKT+K4qoiANap/uVSmvtuGE4G9OUC947EqU6KiyE9heFgw6oJpuCwZkNITAgMBAAECggEALGNPfNNtB+JL1iYArLEkpYK/P08bBaFHK9XkkFJ2/fUOLESc3nZ5HNBJg08KVN5Aw9+U1z76p1JDO5FRtPIa5v6zheheOVkVX6DqKLbTRTAisa4VNWo1RZh8xXNBm7EQF/VZZ0Lk+hSj/9V+nzjb965fc8l2VdFTJJ+95rC069BgNhhha6MwwKfH3UYC2Mru1RZTLqZWyvxM7ppTzvKT8Zqn1wHponk+He0w2UBxBzXet+AH0KMX/z7FJHW+r0g8a/45jp/ST9+Sfq2lBbVifLFUneGc3cuZ7IFAsxkEAbGAzNx9viNZ/Q2qPtduJ7CtmYHeFh6uXwW2/CdzYpA+4QKBgQD5mS2kRM/adOzDLFSfB6+8G1ZktpzDjqK0O05ZXzhkVYEfBZd4lRGpJ1I8lfPHAouinwoY/WETAvjsmosBDUN4w0PvRpu2C2RJIYKYullNp3urLIGk7oYKWl2bogwrPm7ljK6gr/60RpGGyDEVqWHzovY9m0nG9tLKT5RkAHLVawKBgQCMWtUa9Z8Xlkqs9+m3MGLccSddWzHvnHtda+2OMTObjnUUGTH+tW1BFHfVvTHxDMrirJ1a9FjFkAwhgXqwic+em4N9YVRrCZ6fGr9cFLKSfcs85Pt8LQijZ3BKrMRf75mkzp0MP4lZP42BmDo+EHT0pqoFWK6Y9jhOXs0Okh/3+QKBgB8AU5bvpRFxLGchfnEyNzWZ/6UKuXXgpXzVUOnw1cRAioUb4LBtc4AbDi/QQDMbsdRFBLAN6Jy/5cMdft5mk1bQigOSNYzq5U3gB6SIoMbwYn/kS0X9ClEG4FGQUwqb+pIwYxA6S9yAV1rySoZyP2RPfV4xh3xa89uE/t1c4OZ5AoGAfRKAbvrMX7eFKift9FnA71JCUvXuROj8Ih8IXTrJE2jaOiPNon3IM0NrGmPzeiv9LAvIWYH/DWa0+LKqQ2E7S8qnXPZ7xLqYyI2MUyD6lIJWtxgSZvC2ju6haVoV0KvyDaQ03PJNkeOJVR/zXtVJc66FOihxxks7l9LxBnf+1vkCgYEAq6Bmb2LUoRa+gZIyYBzUoHZKhVc7tjco42GjFAAsD2VNfdr1zUF/GS2s9O1UZIZ7vjqTA1jHuo8eFwPGyQiwFeUHQcSKkKgch6xJIO7pfk3sPqD/q7N690fCqEfb2hlsy5CATYCz2fKfgAiFF5LhubPhMuqte2SItUSW5gZKHpQ=";

	private final static Log log = new Log();

	private final static String logLocal = ConfigConstant.LOGLOCALURL + "depository";

	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 创建日志目录
	 */
	static {
		File file = new File(logLocal);
		// 如果文件不存在，则创建新的文件
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
	}
	
	/**
	 * 签名
	 * 
	 * @param data
	 * @return
	 */
	public synchronized static String sign(String data) {
		try {
			String sign = new String(Base64.encode(SHA1withRSA.sign(
					data.getBytes(ENCODING), Base64.decode(privateKey))));	
			/*try{
				signLog(sign, data, "requestdata" + sdf2.format(new Date()) + ".log");
			}catch(Exception e){				
				log.errLog("写入本地日志错误", e);
			}	*/
			return sign;
		} catch (Exception e) {
			log.errLog("存管通签名错误", e);
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 验签
	 * 
	 * @param sign
	 * @param data
	 * @return
	 */
	public synchronized static boolean verify(String sign, String data) {
		try {			
			try{
				signLog(sign, data, "responsedata" + sdf2.format(new Date()) + ".log");
			}catch(Exception e){				
				log.errLog("写入本地日志错误", e);
			}
			return SHA1withRSA.verify(Base64.decode(sign),
					data.getBytes(ENCODING), Base64.decode(publicKey));
		} catch (Exception e) {
			log.errLog("存管通验签错误", e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 本地签名验签日志
	 * 
	 * @param str
	 * @param path
	 */
	public static void signLog(String sign, String str, String path) {
		BufferedWriter out = null;
		try {		
			out = new BufferedWriter(new FileWriter(logLocal
					+ File.separator + path, true));
			String formatDate = sdf.format(new Date());
			String message = "\r\n" + "[" + formatDate + "]\r\n" + sign + "\r\n"
					+ str;
			out.write(message);		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
