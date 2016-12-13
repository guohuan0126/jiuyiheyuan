package com.duanrong.newadmin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.duanrong.newadmin.utility.*;


/**
 * @Description: 统一异常处理类
 * @Author: 林志明
 * @CreateDate: Oct 15, 2014
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		return null;
	}

//	public ModelAndView resolveException(HttpServletRequest request,
//			HttpServletResponse response, Object handler, Exception ex) {
//		LogUtil.errLog(handler.toString(), ex.getMessage());
//		ModelAndView model = new ModelAndView("base/error");
//		model.addObject("errormsg", ex.toString());
//		return model;
//	}

}