package com.duanrong.drpay.jsonservice.handler;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import util.FastJsonUtil;
import base.error.ErrorCode;

/**
 * json试图解析器,自动转换为json数据返回
 * @author xiao
 * @datetime 2016年11月30日 下午2:28:23
 */
public class JsonView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> arg0,
			HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		arg2.setCharacterEncoding("UTF-8");
		arg2.setContentType("text/json; charset=UTF-8");  
		View v;
		if(arg0 != null && arg0.containsKey("error")){
			v = (View) arg0.get("error");
		}else{
			v = new View();
			v.setVersion("1.0.0");	
			v.setError(ErrorCode.ServerFail);
		}
		PrintWriter out = arg2.getWriter();   
		out.write(FastJsonUtil.objToJsonWriteNullToEmpty(v));
		out.flush();
		out.close();
	}

}
