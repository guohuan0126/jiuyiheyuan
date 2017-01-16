package com.duanrong.drpay.jsonservice.handler;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import util.Log;
import base.error.ErrorCode;
import base.exception.ErrorCodeException;


/**
 * 统一异常处理，用于将 Exception 转换为共客户端使用的 errorcode
 * @author xiao
 * @datetime 2016年11月7日 下午6:49:04
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	@Resource
	Log log;

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Enumeration<String> enu=request.getParameterNames();  
	    StringBuffer buf = new StringBuffer();
	    if(null!=enu){
			while(enu.hasMoreElements()){  
				String paraName=enu.nextElement();  
				buf.append(paraName+"="+request.getParameter(paraName)+";");  
			}
	    }
	    View view = new View();
	    view.setVersion("1.0.0");		
	    if(ex instanceof ErrorCodeException){
	    	ErrorCode code = ((ErrorCodeException) ex).getCodeMessage();
	    	if(code != null){
	    		view.setError(code);
		    	log.errLog("业务异常", code.toString());
	    	}else if(ex.getMessage() != null && !ex.getMessage().trim().equals("")){
	    		view = new View("50001", ex.getMessage());
	    		view.setVersion("1.0.0");
	    	} else{
	    		view.setError(ErrorCode.ServerFail);	
	    	}   	
	    }else{    	
			view.setError(ErrorCode.ServerFail);			   
	    }
	    
	    //log.errLog("服务器异常", "url:" + request.getLocalAddr() + "param: " + buf.toString() + ExceptionUtils.getMessage(ex));	
	    return new ModelAndView(new JsonView()).addObject("error",view);
	}

}