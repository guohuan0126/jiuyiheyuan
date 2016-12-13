package com.duanrong.thirdPartyInterface.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanrong.thirdPartyInterface.model.YHBYLoan;
import com.duanrong.thirdPartyInterface.model.YHBYresp;
import com.duanrong.thirdPartyInterface.service.YHBYService;

@Controller
public class YHBYController {

	@Resource
	YHBYService yhbyService;

	/**
	 * 
	 * @description 获取当前正在进行投标中的标信息
	 * @author 孙铮
	 * @time 2014-11-11 下午6:19:54
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getNowProjects", method = RequestMethod.GET)
	@ResponseBody
	public YHBYresp getNowProjects(HttpServletRequest request) {
		List<YHBYLoan> projects = yhbyService.getProjects();
		YHBYresp yhbYresp = new YHBYresp();
		if (projects != null && projects.size() > 0) {
			yhbYresp.setCode("0");
			yhbYresp.setMessage("数据获取成功");
			yhbYresp.setData(projects);
		} else {
			yhbYresp.setCode("1");
			yhbYresp.setMessage("数据获取失败");
		}
		return yhbYresp;
	}
}