package com.duanrong.cps.client;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.duanrong.cps.util.FastJsonUtil;


/**
 * 请求返回数据
 * @author xiao
 * @date 2015年8月18日下午5:40:37
 * @version 1.0
 * @param <T> */
public class DREntity<T>{

	private String sign; //签名
	
	private String requestUrl; //请求url
	
	private int status;	//响应状态
	
	private String statusLine;	//响应信息
	
	private String contentType; //响应类型
	
	private String contentEncoding; //响应编码
	
	private Object result; //响应结果
	
	private Object param; //请求参数
	
	private Class<?> clazz;
	
	/**
	 * 构造函数，获取泛型类
	 */
	public DREntity() {
		
		Type type = getClass().getGenericSuperclass();
        if(!(type instanceof ParameterizedType)){
            clazz = Object.class;
        }else{
            Type[] parameters = ((ParameterizedType)type).getActualTypeArguments();
            if(!(parameters[0] instanceof Class)){
                clazz = Object.class;
            }else{
                clazz = (Class<?>)parameters[0];
            }
        }
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusLine() {
		return statusLine;
	}

	public void setStatusLine(String statusLine) {
		this.statusLine = statusLine;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(String result) {
		Object obj = null;
		if(null != result && !"".equals(result.trim())){
			obj = FastJsonUtil.jsonToObj(result, clazz);
		}
		this.result = obj;
	}

	public String getParam() {
		if(null != param){
			return FastJsonUtil.objToJson(param);
		}
		return "";
	}

	public void setParam(Object param) {
		this.param = param;
	}
	
}
