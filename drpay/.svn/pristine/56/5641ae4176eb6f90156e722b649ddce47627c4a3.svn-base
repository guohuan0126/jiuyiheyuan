package depository;

import java.util.Date;

import com.duanrong.drpay.jsonservice.handler.Sign;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorUserAccountJSON;
import com.duanrong.drpay.trusteeship.helper.model.IdCardType;
import com.duanrong.drpay.trusteeship.helper.model.UserRole;

public class Test {

	
	static String url = "http://220.181.25.233:8602/bha-neo-app/lanmaotech/service";
	static String platformNo = "3000000066";
	
	public static void main(String[] args) {

		GeneratorUserAccountJSON json = new GeneratorUserAccountJSON();
		json.setRealName("xiao");
		json.setPlatformUserNo("13261356043ppfh");
		json.setRequestNo("SB00000004");
		json.setUserRole(UserRole.BORROWERS);
		//json.setCallbackUrl("http://demodrpay.duanrong.com/depository/notify.do");
		json.setIdCardType(IdCardType.PRC_ID);
		json.setIdCardNo("13052819920616841X");
		json.setMobile("13261356043");
		json.setBankcardNo("6217000010032651468");
		json.setTimestamp(new Date());
		String s = json.toJSON();
		System.out.println(s);
		String sign = Sign.sign(s, "");
		System.out.println(sign);
		//String a = DepositoryHttpClient.send(url, DepositoryServer.ESTABLISH_PROJECT, s, sign);
		//System.out.println(a);
		
	}

}
