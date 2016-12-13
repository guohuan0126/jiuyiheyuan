package com.duanrong.util.sms;

import com.duanrong.util.client.DRHTTPClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信发送
 *
 * Created by xiao on 2015/12/2.
 *
 * @version 1.0  基本短信接口
 *
 * @version 1.1  增加营销短信接口
 *
 */
public class SMSSend {

    static final String httpBatchSendSM = "http://222.73.117.156/msg/HttpBatchSendSM";// 批量发送
    static final String account = "duanrongw";// 注册号
    static final String account2 = "vip_drw";// 营销短信

    static final String pswd2 = "Duanrongw123";
    static final String pswd = "Tch123456";// 密码
    
    static final String httpEmay = "http://sdk999in.eucp.b2m.cn:8080/sdkproxy/sendsms.action";// 即时发送企业短信
    static final String cdkey="9SDK-EMY-0999-RJYUS";// 注册号
    static final String password="103903";//密码
    
    static final String httpEmaySales = "http://sdktaows.eucp.b2m.cn:8080/sdkproxy/sendsms.action";// 即时发送营销短信
    static final String cdkey1="6SDK-EMY-6666-RJROL";// 注册号
    static final String password1="347880";//密码

    /**
     * 批量发送，多个手机号用","隔开
     * @param mobile
     * @param msg
     * @vesion 1.0
     */
    public static void batchSend(String mobile, String msg){
        try {
            sendSM(account, pswd, mobile, msg, true, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量发送(营销短信)，多个手机号用","隔开
     * @param mobile
     * @param msg
     * @version 1.1
     */
    public static void batchSend1(String mobile, String msg){
        try {
            sendSM(account2, pswd2, mobile, msg, true, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    /**
     * 即时企业短信发送，多个手机号用","隔开
     * @param phone
     * @param message
     * @version 1.0
     */
    public static void sendEmayMsg(String phone, String message){
        try {
        	sendEmaySM(httpEmay, cdkey, password, phone, message, null,null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 批量即时发送(营销短信)，多个手机号用","隔开
     * @param phone
     * @param message
     * @version 1.1
     */
    public static void sendEmaySalesMsg(String phone, String message){
        try {
        	sendEmaySM(httpEmaySales,cdkey1, password1, phone, message, null,null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    /**
     * 发送短信
     * @param mobile
     * @param msg
     * @param needstatus
     * @param product
     * @param extno
     * @version 1.0
     */  
    private static void sendSM(String account, String pswd,  String mobile, String msg,
                               boolean needstatus, String product, String extno) throws IOException{
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("account", account));
        params.add(new BasicNameValuePair("pswd", pswd));
        params.add(new BasicNameValuePair("mobile", mobile));
        params.add(new BasicNameValuePair("needstatus", String.valueOf(needstatus)));
        params.add(new BasicNameValuePair("msg", msg));
        params.add(new BasicNameValuePair("product", product));
        params.add(new BasicNameValuePair("extno", extno));
        DRHTTPClient.sendHTTPRequestGetToString(httpBatchSendSM, new BasicHeader[0], params);
        
    }
    /**
     * 即时发送短信
     * @param phone
     * @param message
     * @param addserial
     * @param seqid
     * @param smspriority
     * @version 1.1
     */  
    private static void sendEmaySM(String url, String cdkey, String password, String phone, String message,
    		String addserial ,String seqid ,String smspriority) throws IOException{
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("cdkey", cdkey));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("message", message));
        params.add(new BasicNameValuePair("addserial", addserial));
        params.add(new BasicNameValuePair("seqid", seqid));
        params.add(new BasicNameValuePair("smspriority", smspriority));
        DRHTTPClient.sendHTTPRequestGetToString(url, new BasicHeader[0], params);
    }
}
