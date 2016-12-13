package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.duanrong.newadmin.utility.LoadConstantProterties;
import com.duanrong.yeepaysign.CFCASignUtil;

public class LocalLog {
	public static void recordData(byte[] responseBody, String type) {
		try {
			List<String> list = new ArrayList<String>();
			list.add("duanrongcfcaConfig.properties");
			HashMap<String, Properties> loadConstantsPro = LoadConstantProterties
					.loadConstantsPro(list);
			String yeepayResponseDataPath = null;
			if (loadConstantsPro != null && loadConstantsPro.size() > 0) {
				Properties properties = loadConstantsPro
						.get("duanrongcfcaConfig.properties");
				yeepayResponseDataPath = properties
						.getProperty("yeepayResponseDataPath");
				if (properties == null || yeepayResponseDataPath == null) {
					return;
				} else {
					if (responseBody == null) {
						CFCASignUtil.writeMessage("响应数据为空" + type,
								yeepayResponseDataPath);
					} else {
						// 创建一个要写入的文件路径,按天生成
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						String fileName = sdf.format(new Date());
						fileName = yeepayResponseDataPath
								+ "responseYeepayData" + fileName + ".txt";
						String respInfo = new String(responseBody, "UTF-8");
						CFCASignUtil.writeMessage(type + respInfo, fileName);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
