package com.duanrong.zhongjin;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.util.*;

import com.duanrong.util.client.DRHTTPClient;
import com.duanrong.util.security.Hmac;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.bouncycastle.util.encoders.Base64;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/9/5 14:01
 */
public class Test {


    public static void main(String[] args) throws IOException, InvalidKeyException {

        /*String url = "http://localhost:8080/test.do";
        String data = new String(Base64.encode("{\"investId\":\"hwehwqa\",\"loanId\":\"10001010\",\"money\":900,\"userId\":\"\"}".getBytes()));
        String key = "duanrongf0f22ac60d07407cfb7c587f9cab";
        long timestamp = new Date().getTime();
        String source = "pc";
        String version = "1.0.0";
        String sign = Sign.sign(timestamp + "|" + source + "|" + version + "|" + data, key);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("data", data));
        params.add(new BasicNameValuePair("source", source));
        params.add(new BasicNameValuePair("timestamp", "" + timestamp));
        params.add(new BasicNameValuePair("version", version));
        params.add(new BasicNameValuePair("sign", sign));


        Header[] headers = new Header[]{new BasicHeader("Content-Type", "application/json;charset=UTF-8")};
        String str = DRHTTPClient.sendHTTPRequestPostToString(DRHTTPClient.createSSLClientDefault(), url, headers, params);
        System.out.println(str);*/
    	
    	System.out.println(new String(Base64.decode(Hmac.hmacMD5("123".getBytes(), "abc".getBytes()))));
    	
    }
}
