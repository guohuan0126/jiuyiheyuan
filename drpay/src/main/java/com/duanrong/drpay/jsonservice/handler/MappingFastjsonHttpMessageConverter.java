package com.duanrong.drpay.jsonservice.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import util.FastJsonUtil;

/**
 * json映射器,映射spring mvc 的 @RequestBody 和 @ResponseBody 标签, fastjson实现序列化和反序列化
 * 
 * @author xiao
 * @date 2015/8/25
 */
public class MappingFastjsonHttpMessageConverter extends
		AbstractHttpMessageConverter<Object> {

	Log log = LogFactory.getLog(MappingFastjsonHttpMessageConverter.class);

	// 序列换编码格式，UTF-8
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public Charset charset = DEFAULT_CHARSET;

	private static final String ENCODING = "UTF-8";

	private String requestParamName;

	public void setRequestParamName(String requestParamName) {
		if (null == requestParamName || ("").equals(requestParamName.trim()))
			throw new NullPointerException("requestParamName not is null");
		this.requestParamName = requestParamName;
	}

	public MappingFastjsonHttpMessageConverter() {

		super(new MediaType("application", "json", DEFAULT_CHARSET),
				new MediaType("text", "json", DEFAULT_CHARSET), new MediaType(
						"text", "plain", DEFAULT_CHARSET), new MediaType(
						"text", "xml", DEFAULT_CHARSET));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	/**
	 * @RequestBody 标签映射, 请求数据默认通过此方法来转换格式，并绑定到@RequestBody标签 标记的参数上
	 */
	@Override
	protected Object readInternal(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return readJavaType(clazz, inputMessage);
	}

	private Object readJavaType(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) {
		// String json = inputStreamToStringConver(inputMessage.getBody());
		// 从inputStream中读取数据
		/**
		 * 从paramter中读取数据, 请求参数名必须默认data
		 */
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String requestParamValue = request.getParameter(requestParamName);
		if (requestParamValue != null && !requestParamValue.equals(""))
			try {
				return FastJsonUtil.jsonToObj(
						new String(Base64.decode(requestParamValue), ENCODING),
						clazz);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		return null;
	}

	/**
	 * @ResponseBody 标签映射, 请求数据默认通过此方法来转换格式，并绑定到@ResponseBody标签 标记的参数上
	 */
	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		OutputStream out = outputMessage.getBody();
		String json = "";
		if (t instanceof View) {
			View v = (View) t;
			if (v.getData() != null) {
				// 处理存管通返回数据，不写空数据
				if (v.getType() != null && v.getType() == TerminalEnum.app)
					json = FastJsonUtil.objToJsonWriteNullToEmpty(t);
				else
					json = FastJsonUtil.objToJson(t);
			}else{
				if (v.getType() != null && v.getType() == TerminalEnum.app)
					json = FastJsonUtil.objToJsonWriteNullToEmpty(t);
				else
					json = FastJsonUtil.objToJson(t);
			}
			//json = json.replaceAll("\\\\", "");
		} else {
			log.error("返回结果不是View");
		}
		byte[] bytes = json.getBytes(charset);
		out.write(bytes);
		out.flush();
		out.close();
	}
}
