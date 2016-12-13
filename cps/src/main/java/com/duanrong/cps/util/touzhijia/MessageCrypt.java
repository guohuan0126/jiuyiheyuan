package com.duanrong.cps.util.touzhijia;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.duanrong.cps.business.touzhija.model.Message;
import com.duanrong.cps.business.touzhija.model.ServiceData;
import com.duanrong.cps.util.ReadProperties;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class MessageCrypt {
	private static Charset CHARSET = Charset.forName("utf-8");

	//@Value("${aesKey}")
	private String aesKey="";
	//@Value("${token}")
	private String token="";
	//@Value("${appId}")
	private String appId="";
	
	public Message encryptMsg(ServiceData sd) throws CryptException {
		
		aesKey=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "touzhijia_aesKey");
		token=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "touzhijia_token");
		appId=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "touzhijia_appid");
		
		
		String nonce = getRandomStr();  //随机生成16位字符串
		Message msg = new Message();  //返回的对象
		msg.setTimestamp(System.currentTimeMillis() / 1000);
		msg.setNonce(nonce);
		
		Gson gson = new Gson();
		byte[] jsonBytes = gson.toJson(sd).getBytes(CHARSET);
		byte[] networkBytesOrder = getNetworkBytesOrder(jsonBytes.length); //把数据长度变成4个字节固定长度
		byte[] appIdBytes = appId.getBytes(CHARSET);
		ByteGroup bg = new ByteGroup();
		bg.addBytes(nonce.getBytes(CHARSET));
		bg.addBytes(networkBytesOrder);
		bg.addBytes(jsonBytes);
		bg.addBytes(appIdBytes);
		byte[] padBytes = PKCS7Encoder.encode(bg.size());   //补位
		bg.addBytes(padBytes);
		// 获得最终的字节流, 未加密
		byte[] original = bg.toBytes();
		
		try {
			byte[] reqKey = getRequestKey(msg.getTimestamp());
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(reqKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(reqKey, 0, 16));
			cipher.init(Cipher.ENCRYPT_MODE, key_spec, iv);
			byte[] encrypted = cipher.doFinal(original);
			String data = Base64.encodeBase64String(encrypted);
			msg.setData(data);
			String signature = SHA1.getSHA1(token, msg);
			msg.setSignature(signature);
		} catch (Exception e) {
			throw new CryptException(CryptException.EncryptAESError);
		}
		
		return msg;
	}
	
	public ServiceData decryptMsg(Message msg) throws CryptException {
		
		aesKey=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "touzhijia_aesKey");
		token=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "touzhijia_token");
		appId=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "touzhijia_appid");
		
		
		String signature = SHA1.getSHA1(token, msg);
		if (!signature.equals(msg.getSignature())) {
			throw new CryptException(CryptException.ValidateSignatureError);
		}
		try {
			byte[] reqKey = getRequestKey(msg.getTimestamp());
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(reqKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(reqKey, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
			byte[] encrypted = Base64.decodeBase64((msg.getData()));
			byte[] original = cipher.doFinal(encrypted);
			
			// 去除补位字符
			byte[] bytes = PKCS7Encoder.decode(original);
			// 分离16位随机字符串,网络字节序和AppId
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
			int dataLength = recoverNetworkBytesOrder(networkOrder);
			String json = new String(Arrays.copyOfRange(bytes, 20, 20 + dataLength), CHARSET);
			String appId = new String(Arrays.copyOfRange(bytes, 20 + dataLength, bytes.length),
					CHARSET);
			
			if (!appId.equals(this.appId)) {
				throw new CryptException(CryptException.ValidateAppidError);
			}
			
			JsonParser parser = new JsonParser();
			JsonObject root = parser.parse(json).getAsJsonObject();
			String service = root.get("service").getAsString();
			return new ServiceData(service, root.get("body"));
		} catch(Exception e) {
			throw new CryptException(CryptException.DecryptAESError);
		}
	}
	
	private byte[] getRequestKey(long timestamp) throws CryptException {
		String a = String.format("%s%d", aesKey, timestamp);   //%s字符串类型， %d整数类型
			MessageDigest md5;
			try {
				md5 = MessageDigest.getInstance("MD5");
				return md5.digest(a.getBytes(CHARSET));
			} catch (NoSuchAlgorithmException e) {
				throw new CryptException(CryptException.EncryptAESError);
			}
		
	}
	
	public static void main(String[] args) throws CryptException{
//		String data=
//    			     "{\"timeRange\":{\"startTime\":null,\"endTime\":null},"
//    			    + "  \"index\":{\"name\":\"id\",\"vals\":[\"160713185332175001\",\"160118161000881002\"]}"
//    			    +"}";
		
//		String data=
//			     "{\"timeRange\":{\"startTime\":\"2016-06-01 11:00:00\",\"endTime\":\"2016-07-31 00:00:00\"},"
//			    + "  \"index\":{\"name\":\"username\",\"vals\":[\"touzhijiaTest1\",\"touzhijiaTest2\"]}"
//			    +"}";
		
//		String data=
//			     "{\"timeRange\":{\"startTime\":null,\"endTime\":null},"
//			    + "  \"index\":{\"name\":\"id\",\"vals\":[\"dmsoINVE110020160713114123731000017\",\"dmsoINVE695520160713102546003000014\"]}"
//			    	+	 "}";
		String data=
			     "{\"timeRange\":{\"startTime\":null,\"endTime\":null},"
			    + "  \"index\":{\"name\":\"username\",\"vals\":[\"3miiAfBfYvimfacr\",\"MFnUrqQnaUnybdcb\",\"zIryUvUfIF32xdek\"]}"
			    +"}";
    	Gson gson = new Gson();
    	JsonElement jsondata = gson.fromJson(data,JsonElement.class);
    	System.out.println(jsondata);
    	
    	//ServiceData aa=new ServiceData("queryBids",jsondata);
    	//ServiceData aa=new ServiceData("queryUser",jsondata);
    	//ServiceData aa=new ServiceData("queryInvests",jsondata);
    	ServiceData aa=new ServiceData("queryRepays",jsondata);
    	
    	MessageCrypt bb=new MessageCrypt();
    	Message message=bb.encryptMsg(aa);
    	System.out.println("data:"+message.getData());
    	
    	System.out.println("timestamp:"+message.getTimestamp());
    	System.out.println("nonce:"+message.getNonce());
    	System.out.println("signature:"+message.getSignature());
    	
		
		
	}
	
	// 生成4个字节的网络字节序
	byte[] getNetworkBytesOrder(int sourceNumber) {
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (sourceNumber & 0xFF);
		orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
		orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
		orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
		return orderBytes;
	}
	
	// 还原4个字节的网络字节序
	int recoverNetworkBytesOrder(byte[] orderBytes) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= orderBytes[i] & 0xff;
		}
		return sourceNumber;
	}

	// 随机生成16位字符串
	String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
