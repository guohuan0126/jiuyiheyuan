/*package com.duanrong.zhongjin;

import java.util.ArrayList;
import java.util.List;

import com.duanrong.util.json.FastJsonUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.duanrong.util.client.DRHTTPClient;
import com.duanrong.util.security.Base64Util;
import com.duanrong.util.security.CertificateCoder;
import com.duanrong.util.xml.JaxbXmlUtil;

*//**
 * Created by xiao on 2016/3/24.
 *//*
public class ZhongJInTest {

    //接口地址
    static String  url = "https://test.cpcn.com.cn/Gateway/InterfaceII";

    //机构名称
    String InstitutionName = "久亿财富（北京）投资有限公司";

    //机构编号
    String InstitutionID = "002042";

    //账户名称
    String PaymentAccountName = "久亿财富（北京）投资有限公司";

    //账户编号
    String PaymentAccountNumbe = "600020420001";


    static String password = "cfca1234";

    private static String  keyStorePath = "D:\\2\\test.pfx";
    private static String  certificatePath = "D:\\2\\paytest.cer";


    public void payTest() throws Exception {

        Body body = new Body();

        body.setBatchNo("201605251321");
        body.setTotalAmount(1*100);
        body.setTotalCount(1);

        body.setPayer(new Payer(PaymentAccountName, PaymentAccountNumbe));

        List<Payee> payees = new ArrayList<>();
        Payee payee = new Payee();
        payee.setItemNo("2016052513211");
        payee.setAmount(1*100);
        payee.setBankID(ZhongJinConstants.BankCode.CCB);
        payee.setAccountType("11");
        payee.setBankAccountName("刘晓硕");
        payee.setBankAccountNumber("6228480636560981368");
        payees.add(payee);

        body.setPayee(payees);

        Request request = new Request();
        request.setHeader(new RequestHeader(InstitutionID, ZhongJinConstants.payType.BatchPay));
        request.setBody(body);

        String xml = JaxbXmlUtil.objToXml(request);

        //System.out.println(xml);

        String message = Base64Util.base64Encode(xml);
        String  signature = CertificateCoder.sign(xml, keyStorePath, password);

        //System.out.println("signature" + signature);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("message", message));
        params.add(new BasicNameValuePair("signature", signature));
        send(params);
    }


    public void qeyTest() throws Exception {

        Body body = new Body();

        body.setBatchNo("201605251321");
        body.setTxType(ZhongJinConstants.payType.BatchPay);

        Request request = new Request();
        request.setHeader(new RequestHeader(InstitutionID, ZhongJinConstants.payType.QuyBatchPay));
        request.setBody(body);


        String xml = JaxbXmlUtil.objToXml(request);
        System.out.println(xml);

        //String message = Base64Util.base64Decode(xml);
        //System.out.println(message);

        //String  signature = CertificateCoder.sign(xml, keyStorePath, password);

        //System.out.println(signature);

        //List<NameValuePair> params = new ArrayList<>();
        //params.add(new BasicNameValuePair("message", message));
        //params.add(new BasicNameValuePair("signature", signature));
        //send(params);
    }




   private static void send(List<NameValuePair> params){
        try {
            String signature = "";
            String responsedata = DRHTTPClient.sendHTTPRequestPostToString(
                    DRHTTPClient.createGzipSSLClientDefault(), url, new BasicHeader[]{}, params);
            System.out.println(responsedata);

            if (responsedata.contains(",")){
                String[] strs = responsedata.split(",");
                if(strs.length >= 2){
                    responsedata = strs[0];
                    signature = strs[1];
                }
            }
            System.out.println(responsedata);
            System.out.println(signature);
            responsedata = Base64Util.base64Decode(responsedata);
            System.out.println(responsedata);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
    	Body body = new Body();

        body.setBatchNo("201605251321");
        body.setTxType(ZhongJinConstants.payType.BatchPay);

        Request request = new Request();
        request.setHeader(new RequestHeader("0100000", ZhongJinConstants.payType.QuyBatchPay));
        request.setBody(body);
        String s = FastJsonUtil.objToJson(body);
        //ZhongJInTest test = new ZhongJInTest();
        //test.qeyTest();
        //String a = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+CjxSZXNwb25zZSB2ZXJzaW9uPSIyLjAiPgo8SGVhZD4KPENvZGU+MjAwNjwvQ29kZT4KPE1lc3NhZ2U+5py65p6E5pyq5byA6YCa6K+l5Lia5Yqh77yBKDQ1MzQpPC9NZXNzYWdlPgo8L0hlYWQ+CjwvUmVzcG9uc2U+,193C84A4239078A74BE245F56FD12E9866C0F02501F8C276BDDA02B799050F10E9A18BB9EB140DFE5681657E56483835DAE5F1D62407B5A7D9C104B843C31C6DE55319B3990839AB7B1923C7415D8957D8B0A8B23B2EDF0F883CA62C8198D2CD21014CAB49AA8701A2E53065A6002ACCD3A4BD137EA5C6B5443FC73B496C14A6";
        //String b = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+CjxSZXNwb25zZSB2ZXJzaW9uPSIyLjAiPgo8SGVhZD4KPENvZGU+NDUzNDI5PC9Db2RlPgo8TWVzc2FnZT7nrKwx5p2h5piO57uG6LSm5oi35ZCN56ew5Li656m65oiW6ZW/5bqm5LiN5q2j56Gu77yM6K+35p+l55yL5Y+C5pWwQmFua0FjY291bnROYW1lPC9NZXNzYWdlPgo8L0hlYWQ+CjwvUmVzcG9uc2U+";
        //b = Base64.decode(b, "utf-8");
        //System.out.println(b);
        //BigDecimal decimal = new BigDecimal(1000);
    	System.out.println(s);
    	System.out.println(FastJsonUtil.jsonToObj(s, Body.class));

    }


}*/