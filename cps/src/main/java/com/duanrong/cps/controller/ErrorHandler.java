package com.duanrong.cps.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.duanrong.cps.business.touzhija.model.ErrorResponse;
import com.duanrong.cps.business.touzhija.model.PlatformException;


@ControllerAdvice
@EnableWebMvc
public class ErrorHandler {
	
	@ExceptionHandler(value = PlatformException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @RequestMapping(produces = "application/json")
    public ErrorResponse platformError(PlatformException exception) {
		ErrorResponse errorResponse=new ErrorResponse(exception.getCode(), 
        		exception.getMessage() == null ? "" : exception.getMessage());
		
		System.out.println("###########ErrorResponse中的code:"+errorResponse.getCode());
		System.out.println("###########ErrorResponse中的message:"+errorResponse.getMessage());
		
        return errorResponse;
    }
	
    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @RequestMapping(produces = "application/json")
    public ErrorResponse intervalServerError(Throwable exception) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
        		exception.getMessage() == null ? "" : exception.getMessage());
    }
}

