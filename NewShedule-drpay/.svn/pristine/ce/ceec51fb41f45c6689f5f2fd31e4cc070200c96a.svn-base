package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.newadmin.utility.LogUtil;
import com.duanrong.util.LoadConstantProterties2;
import com.duanrong.util.security.Hmac;
/**
 * Http请求工具类
 * @author Qfh
 *
 */
public class HttpUtil {

	private static final Logger logger = Logger.getLogger(HttpUtil.class);
	
	/***SOA***/
	private static final String host = LoadConstantProterties2.getValueByDefaultPro("host");                //普通SOA域名
	private static final String yeepayhost = LoadConstantProterties2.getValueByDefaultPro("yeepayhost");    //易宝SOA域名
	private static final String webkey = LoadConstantProterties2.getValueByDefaultPro("webKey");  //Webkey
	
	/***DRPay***/
	private static final String drpayUrl = LoadConstantProterties2.getValueByDefaultPro("drpay_url");  //DRPay域名
	private static final String drpaykey = LoadConstantProterties2.getValueByDefaultPro("drpay_key");  //drpayKey
	private static final String drpaySource = LoadConstantProterties2.getValueByDefaultPro("drpay_source");  //来源
	private static final String drpayVersion = "1.0";
	
	private static String ip = "";   //当前服务器IP地址
	
    static{
    	try {
			ip = InetAddress.getLocalHost().getHostAddress();
		}catch(UnknownHostException e) {
			ip = "无法获得IP";
		}
    }
	
    /**
     * 发送普通soa请求
     * @param param
     * @param method
     * @return
     */
	public static JSONObject sendPost(JSONObject param,String method){
		param.put("webkey",webkey);
		param.put("tempstam",new Date().getTime());
	    String url = host+method;
		return requestServer(param, url);
	}
	
	
	/**
	 * DRPay调用请求工具方法
	 * @param json
	 * @param url
	 * @return
	 */
	public static JSONObject sendDRPayPost(JSONObject json,String url){
		
		
		try {
			Long timestamp= System.currentTimeMillis();
			//数据加密
			logger.info(url+" 请求参数加密前："+json.toJSONString());
			String data = new String(Base64.encode(json.toJSONString().getBytes("utf-8")));
			byte[] signByte = Hmac.hmacSHA256((timestamp+"|"+drpaySource+"|"+drpayVersion+"|"+data).getBytes("utf-8"),
					drpaykey.getBytes("utf-8"));
			//签名
			String sign=new String(Base64.encode(signByte));
			//参数封装
			JSONObject param=new JSONObject();
			param.put("timestamp",timestamp);
			param.put("source",drpaySource);
			param.put("version",drpayVersion);
			param.put("ip", ip);
			param.put("data", data);
			param.put("sign", sign);
			url = drpayUrl+url;
			logger.info(url+" 请求参数加密后："+param.toJSONString());
			return requestServer(param,url);
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 服务器请求
	 * @param param 参数
	 * @param url 相对地址
	 * @param type: 1.yeepayhost 2.soa
	 * @return
	 * @throws IOException 
	 */
	public static JSONObject requestServer(JSONObject param,String url){
		LogUtil.infoLog(url+"请求参数",param.toJSONString());
		logger.info(url+"请求参数:"+param.toJSONString());
		JSONObject object = null;
		try{
			HttpResult result = null;
			if(url.contains("https")){
				result = HttpConnectionManager.doSSLPost(url,param,null);
			}else{
				result = HttpConnectionManager.doPost(url,param,null);
			}
			LogUtil.infoLog(url+"返回值",result.toString());
			logger.info(url+"返回值:"+result.toString());
			if(result.getStatus() == 200){
				object = JSONObject.parseObject(result.getData());
			}else{
				LogUtil.errLog("调用"+url+"异常！",result.toString());
				logger.error("调用"+url+"异常！返回值:"+result.toString());
			}
		}catch(Exception e){
			LogUtil.errLog("请求"+url+"异常！",e);
			logger.error("请求"+url+"异常！"+e.getMessage());
		}
		return object;
	}
	
	
	/**
	 * 第三方请求专用
	 * @param json
	 * @param httpUrl
	 * @return
	 */
   public static JSONObject platformExecutePost(JSONObject param,String url){
	   
		LogUtil.infoLog(url+"请求参数",param.toJSONString());
		logger.info(url+"请求参数:"+param.toJSONString());
		JSONObject object = new JSONObject();
		try{
			HttpResult result = HttpConnectionManager.doPost(url,param,null);
			LogUtil.infoLog(url+"返回值",result.toString());
			logger.info(url+"返回值:"+result.toString());
			object.put("code",result.getStatus());
			object.put("content",JSONObject.parse(result.getData()));
		}catch(Exception e){
			LogUtil.errLog("请求"+url+"异常！",e);
			logger.error("请求"+url+"异常！"+e.getMessage());
			object.put("code",500);
			object.put("content","Http工具类异常");
		}
		return object;
	}
}
