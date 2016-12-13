package base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.duanrong.cps.util.FastJsonUtil;


/**
 * json映射器,映射spring mvc 的 @RequestBody 和 @ResponseBody 标签, fastjson实现序列化和反序列化
 * 
 * @author xiao
 * @date 2015/8/25
 */
public class MappingFastjsonHttpMessageConverter extends
		AbstractHttpMessageConverter<Object> {

	// 序列换编码格式，UTF-8
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public Charset charset = DEFAULT_CHARSET;

	public MappingFastjsonHttpMessageConverter() {
		super(new MediaType("application", "json", DEFAULT_CHARSET),
				new MediaType("application", "*+json", DEFAULT_CHARSET));
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
		try {
			return FastJsonUtil.jsonToObj(
					inputStreamToStringConver(inputMessage.getBody()), clazz);
		} catch (IOException ex) {
			throw new HttpMessageNotReadableException("Could not read JSON: "
					+ ex.getMessage(), ex);
		}
	}

	private String inputStreamToStringConver(InputStream is) {
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						is, "UTF-8"));
				while ((line = bf.readLine()) != null) {
					sb.append(line);
				}
			} catch (UnsupportedEncodingException e) {
				throw new HttpMessageNotReadableException(
						"Could not read JSON: " + e.getMessage(), e);
			} catch (IOException e) {
				throw new HttpMessageNotReadableException(
						"Could not read JSON: " + e.getMessage(), e);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					throw new HttpMessageNotReadableException(
							"Could not read JSON: " + e.getMessage(), e);
				}
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * @ResponseBody 标签映射, 请求数据默认通过此方法来转换格式，并绑定到@ResponseBody标签 标记的参数上
	 */
	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {				
		OutputStream out = outputMessage.getBody();
		String json = "";
		if(t instanceof Map && ((Map<?, ?>) t).containsKey("version")){			
			json = FastJsonUtil.objToJsonWriteNullToEmpty(t);					
		}else{
			json = FastJsonUtil.objToJson(t);
		}		
		if (t instanceof String) {
			// 防止二次序列化导致json异常格式
			json = json.replaceAll("\\\\", "");

			if (json.startsWith("\"")) {
				json = json.substring(1);
			}
			if (json.endsWith("\"")) {
				json = json.substring(0, json.length() - 1);
			}
		}
		byte[] bytes = json.getBytes(charset);
		out.write(bytes);
		out.flush();
	}

}
