package com.duanrong.drpay.jsonservice.handler;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.duanrong.drpay.jsonservice.param.Parameter;

import util.FastJsonUtil;

/**
 * @RequestParamter 参数映射器,用户解析并影射接口参数
 * @author xiao
 * @datetime 2016年11月30日 下午2:10:39
 */
public class RequestParamterHandlerMethodArgumentResolver implements
		HandlerMethodArgumentResolver {

	private String requestParamName = "data";

	private static final String ENCODING = "UTF-8";

	public String getRequestParamName() {
		return requestParamName;
	}

	public void setRequestParamName(String requestParamName) {
		this.requestParamName = requestParamName;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestParameter.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) {
		String requestParamValue = webRequest.getParameter(requestParamName);
		if (requestParamValue != null && !requestParamValue.equals("")) {
			try {
				String d = new String(Base64.decode(requestParamValue),
						ENCODING);
				if (Parameter.class.isAssignableFrom(
						parameter.getParameterType())) {
					return FastJsonUtil.jsonToObj(d,
							parameter.getParameterType());
				} else {
					Map<?, ?> map = (Map<?, ?>) FastJsonUtil.jsonToObj(d,
							Map.class);
					return map.get(parameter.getParameterName());
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
