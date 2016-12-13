package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import util.Result.Mobile;

import com.duanrong.util.client.DRHTTPClient;
import com.duanrong.util.json.FastJsonUtil;

/**
 * 手机号归属地
 * 
 * @author Qiu Feihu
 * @version 2015年10月9日11:38:36
 */
public class PhoneNoAttributionUtils {

	private static Log log = new Log();
	/**
	 * 返回省份和城市数组
	 * 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static String[] getAttributions(String mobile) throws Exception {
		if (MyStringUtils.isNumeric(mobile) && mobile.length() == 11) {
			String[] arr = new String[3];
			Mobile m = getMobile(mobile);
			if (m != null) {
				arr[0] = m.getProvince();
				arr[1] = m.getCity();
				arr[2] = m.getCompany();
			}
			return arr;
		}
		return null;
	}

	/**
	 * 获取JSON数据
	 * 
	 * @return
	 */
	public static Mobile getMobile(String mobile) {
		/*String url = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi";*/
		
		String url = "http://apis.juhe.cn/mobile/get?phone="+mobile+"&key=91dcd9f6e342d16a1dce94e5bcdf7b14&dtype=xml";
	
		String responsedata = "";
		try {
			responsedata = DRHTTPClient.sendHTTPRequestGetToString(url,
					new BasicHeader[0], new ArrayList(), "gb2312");
			
			if(responsedata != null && responsedata.trim().length() > 0){
				Mobile m = FastJsonUtil.jsonToObj(responsedata, Result.class).getResult();
				System.out.println(m);
				return m;
			}
		} catch (IOException e1) {
			log.errLog("获取手机号归属地异常：" + responsedata, e1);
		}	
		return null;
	}

}
