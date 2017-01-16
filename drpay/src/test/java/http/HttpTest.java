package http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;




import org.bouncycastle.util.encoders.Base64;

import com.duanrong.drpay.trusteeship.helper.sign.Sign;

import util.FastJsonUtil;


public class HttpTest {

	public static void main(String[] args) throws UnsupportedEncodingException{
		
	
	/*String url = "http://localhost:8080/test/test.do";
	String timestamp = Long.toString(new Date().getTime());

	
	RequestGeneratorJSON json = new RequestGeneratorJSON();
	json.setRequestNo(IdUtil.generateId(ToType.RPWD));
	json.setPlatformUserNo("13061533333");
	json.setCallbackUrl(DepositoryConstants.NOTIFY_URL + NotifyURL.RPWD);
	
	String data = "{\"timestamp\":\"20161212154250\",\"callbackUrl\":\"https://demodrpay.duanrong.net/depository/RPWD\",\"requestNo\":\"dmsoRPWD088720161212154250177000002\",\"platformUserNo\":\"QR3M32RzeiA3brff\"}";
	
	System.out.println(Sign.sign(data));
	

	System.out.println(data);*/
		
	/*Map<String, String> map = new HashMap<>();
	map.put("userId", "13261356043ppfh");
	System.out.println(new String(Base64.encode(FastJsonUtil.objToJsonNoWriteNull(map).getBytes())));*/
	
	String d = "{\"platformUserNo\":\"eequ6zbY7vMzmoxl\",\"realName\":\"邱飞虎\",\"idCardType\":\"PRC_ID\",\"userRole\":\"INVESTOR\",\"idCardNo\":\"412825********6710\",\"mobile\":\"18510238875\",\"bankcardNo\":\"************6183\",\"bankcode\":\"CMBC\",\"accessType\":\"FULL_CHECKED\",\"auditStatus\":\"PASSED\",\"requestNo\":\"dmsoOPAC262920161230122935257000003\",\"code\":\"0\",\"description\":\"个人用户注册成功\"}";
	String sign = "SRNh4/1PNXkfRV8AwJrcA9yR72qWjQv1Wt18Rqwgwp1PTWhuKalB7e0QbVH13IJmk9x6WcMHx8d5rd2x6Qxvp9tl12eO7hd+WfS3/CL6c2zQsPOwcRmjVfL9YziTGq3UyZicE2+GaniiBy3Oj8uE5BqEWwHKfaKl93YgFAG+93RR++yMAOe8phWhdpUfWD5Jme7RL+F1tSQy28WGPO9sMtORlrfq02m1Hf5Q1aZw5eSzUeDfSG+zPiE9PUe4z157WQ90r38nsqWItVGFedr17YYYYMXPX/Z1Bs/J/Weag+NoN8MUGFaw0wYr7kXtsvovK4q8vOPSWEnGDLRvJfx5fg==";	
	
	System.out.println(Sign.verify(sign, d));
	
	}

}
