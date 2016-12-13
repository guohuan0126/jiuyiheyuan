package com.duanrong.newadmin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.message.model.MessageTemplate;
import com.duanrong.business.message.service.MessageTemplateService;
import com.duanrong.newadmin.model.UserCookie;

@Controller
public class MessageTemplateController extends BaseController {
	@Resource
	private MessageTemplateService messageTemplateService;
	@Resource
	private Log log;

	@RequestMapping(value = "/userMessageList/show")
	public String toMessageTemplateList(HttpServletRequest request,
			HttpServletResponse response) {
		int pageNo = 0;
		try {
			if (StringUtils.isNotBlank(request.getParameter("pageNo"))) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			MessageTemplate messageTemplate = new MessageTemplate();
			messageTemplate.setId(request.getParameter("id"));
			messageTemplate.setName(request.getParameter("name"));
			PageInfo<MessageTemplate> pageInfo = messageTemplateService
					.readSqlLiteForMessageTemplate(pageNo, getPageSize(),
							messageTemplate);
			request.setAttribute("url", "/userMessageList/show");
			request.setAttribute("id", messageTemplate.getId());
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("name", messageTemplate.getName());
			return "userMessage/all/showUserMessageList";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	@RequestMapping(value = "/toCreateOrEditUserMessage/{type}")
	public String toCreateOrEditUserMessage(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type)
			throws UnsupportedEncodingException {
		try {
			if (type.equals("create")) {
				return "userMessage/all/addUserMessage";
			} else if (type.equals("edit")) {
				String id = request.getParameter("param");
				id = URLDecoder.decode(id, "UTF-8");
				MessageTemplate messageTemplate = messageTemplateService
						.readMessageTemplateById(id);
				request.setAttribute("messageTemplate", messageTemplate);
				return "userMessage/all/editUserMessage";
			}
			return "userMessage/all/showUserMessageList";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	@RequestMapping(value = "/createOrEditUserMessage/{type}")
	public void createOrEditUserMessage(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type)
			throws UnsupportedEncodingException, ParseException {
		MessageTemplate messageTemplate = new MessageTemplate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageTemplate.setStatus("必须");
		messageTemplate.setLastModifyTime(sdf.parse(sdf.format(new Date())));
		messageTemplate.setMessageWay("sms");
		messageTemplate.setMessageNode("binding_email");
		messageTemplate.setId(request.getParameter("id"));
		messageTemplate.setName(request.getParameter("name"));
		messageTemplate.setDescription(request.getParameter("description"));
		messageTemplate.setTemplate(request.getParameter("template"));
		UserCookie getLoginUser = GetLoginUser(request, response);
		// 设置当前操作人
		messageTemplate.setTextuserId(getLoginUser.getUserId());
		// 1表示新建，0表示假删除
		messageTemplate.setNowStatus(1);
		if (type.equals("create")) {
			try {
				int status = messageTemplateService
						.insertMessageTemplate(messageTemplate);
				response.getWriter().print(status);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (type.equals("edit")) {
			try {
				int status = messageTemplateService
						.updateMessageTemplate(messageTemplate);
				response.getWriter().print(status);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/userMessage/delete")
	public String userMessageDelete(HttpServletRequest request,
			HttpServletResponse response, Model model) throws ParseException {
		String id = request.getParameter("id");
		MessageTemplate messageTemplate = new MessageTemplate();
		messageTemplate.setId(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageTemplate.setLastModifyTime(sdf.parse(sdf.format(new Date())));
		// 0为假删除
		messageTemplate.setNowStatus(0);
		UserCookie getLoginUser = GetLoginUser(request, response);
		// 设置当前操作人
		messageTemplate.setTextuserId(getLoginUser.getUserId());
		try {
			messageTemplateService.updateMessageTemplate(messageTemplate);
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.MessageTemplateController.messageTemplateDelete()",
					e);
			return "fail";
		}
	}

}