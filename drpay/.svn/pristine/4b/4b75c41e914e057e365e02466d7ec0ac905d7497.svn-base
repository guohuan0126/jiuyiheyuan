package depository;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.bouncycastle.util.encoders.Base64;

import util.FastJsonUtil;


public class LoanTest {

	public static void main(String[] args) throws UnsupportedEncodingException {

		//String timestamp = Long.toString(new Date().getTime());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", "13261356043ppfh");
		map.put("loanId", "2016121300002");
		map.put("loanName", "测试项目一");
		map.put("repayType", "一次性到期还本付息");
		map.put("money", "100.00");
		map.put("rate", "0.10");
		String specifyJson = FastJsonUtil.objToJson(map);
		String data = new String(Base64.encode(specifyJson.getBytes("UTF-8")), "UTF-8");
		System.out.println(data);
	}

}
