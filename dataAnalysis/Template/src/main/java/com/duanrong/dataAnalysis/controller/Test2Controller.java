package com.duanrong.dataAnalysis.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;






@Controller
public class Test2Controller {

	@RequestMapping(value="/testTest.do")
	public String Test(){
		System.out.println("访问");
		return "testData";
	}
	
}
 