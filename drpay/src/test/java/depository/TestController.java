package depository;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import com.duanrong.drpay.jsonservice.handler.Sign;

import util.FastJsonUtil;

public class TestController {

	@Test
	public void test() throws IOException {
		//String url = "http://localhost:8080/account/createAccount.do";
		String timestamp = Long.toString(new Date().getTime());
//		String timestamp = "1481883201615";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", "Ubq6nmANvMFzxlii");
		map.put("money", 0.01D);
		map.put("description", "测试吧");
//		map.put("templateType", "校验用户交易密码");
//		map.put("realName", "飞虎");
//		map.put("idCardType", "PRC_ID");
//		map.put("userRole", "INVESTOR");
//		map.put("idCardNo", "41282519902046710");
//		map.put("bankcardNo", "6214830125216183");
//		map.put("mobile", "18510238875");
//		map.put("authList", new String[] {"TENDER"});
		String specifyJson = FastJsonUtil.objToJson(map);
		String data = new String(Base64.encode(specifyJson.getBytes()), "utf-8");
		String version = "1.0.0";
		String source = "pc";
		String str = timestamp + "|" + source + "|" + version + "|" + data;
		String sign = Sign.sign(str, "duanrongf0f22ac60d07407cfb7c587f9cab");
		System.out.println("sign:"+sign);
		System.out.println("data:"+data);
		System.out.println("timestamp:"+timestamp);
		System.out.println("version:"+version);
		System.out.println("source:"+source);
//		List<NameValuePair> params = new ArrayList<>();
//		params.add(new BasicNameValuePair("version", version));
//		params.add(new BasicNameValuePair("source", source));
//		params.add(new BasicNameValuePair("timestamp", timestamp));
//		params.add(new BasicNameValuePair("sign", sign));
//		params.add(new BasicNameValuePair("data", data));
//		String result = DRHTTPClient.sendHTTPRequestPostToString(url,
//				new BasicHeader[0], params);
//		System.out.println(result);
		
	}
}
