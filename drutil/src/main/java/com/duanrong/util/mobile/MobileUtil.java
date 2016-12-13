  package com.duanrong.util.mobile;

import com.duanrong.util.client.DRHTTPClient;
import com.duanrong.util.xml.JaxbXmlUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 手机号 工具类
 * @author xiao
 * @date 2016/4/22
 * @version 1.0
 */
public final class MobileUtil {


    private static final String URL = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi";

    /**
     * 获取手机号归属地信息
     * @param mobile
     * @return
     * @throws IOException
     */
    public static Mobile getMobile(String mobile) throws IOException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("chgmobile", mobile));
        String s = DRHTTPClient.sendHTTPRequestGetToString(URL, new BasicHeader[]{}, params);
        return JaxbXmlUtil.xmlToObj(s, Mobile.class);
    }

}
