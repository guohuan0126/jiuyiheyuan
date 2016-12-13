package com.duanrong.newadmin.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.Log;

import com.duanrong.business.bankinfo.model.BankInfo;
import com.duanrong.business.bankinfo.service.BankInfoService;
import com.duanrong.business.link.service.LinkService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.IdGenerator;

/**
 * 
 * @author YUMIN
 * @date 2016-4-28下午6:44:28
 */
@Controller
public class BankInfoController extends BaseController {
	@Resource
	Log log;
	@Resource
	BankInfoService bankInfoService;
	@Resource
	LinkService linkService;
	@RequestMapping(value = "/bankinfo/bankinfoList")
	public String bankinfoList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String str = "";
				String status = request.getParameter("status");
				String name = request.getParameter("name");
				String bank = request.getParameter("bank");
				String paymentId = request.getParameter("paymentId");
				BankInfo bankinfo = new BankInfo();
				bankinfo.setName(name);				
				bankinfo.setPaymentId(paymentId);
				
				if (name != null && !name.equals("")) {
					str += "&name=" + name;
				}
				if (bank != null && !bank.equals("")) {
					str += "&bank=" + bank;
				bankinfo.setBank(bank.toString().toUpperCase());
				}
				if (status != null && !status.equals("")) {
					str += "&status=" + status;
				bankinfo.setWhetherDelete(Integer.parseInt(status));
				}
				if (paymentId != null && !paymentId.equals("")) {
					str += "&paymentId=" + paymentId;
				}
				List<BankInfo> banklist= bankInfoService.readBankInfo(bankinfo);
				model.addAttribute("url", "bankinfo/bankcardList");
				model.addAttribute("pageInfo", banklist);
				model.addAttribute("status", status);
				model.addAttribute("name", name);
				model.addAttribute("bank", bank);
				model.addAttribute("paymentId", paymentId);
				model.addAttribute("str", str);
				return "bankinfo/bankcardList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					" com.duanrong.newadmin.controller.BankIndoController.bankinfoList()",
					e);
		}
		return null;
	}
	@ResponseBody
	@RequestMapping(value = "/bankinfo/disable")
	public String bankDisable(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String id = request.getParameter("id");
	    String status=request.getParameter("status");	    
	    Map<String,Object> param = new HashedMap();
	    
	    if(status.equals("1")){
		    param.put("whetherDelete", 0);
		 }else if(status.equals("0")){
		    param.put("whetherDelete", 1);	
		 }
	    param.put("id", id);	   
		try {
			bankInfoService.updateBankStatue(param);
			return "ok";
		} catch (Exception e) {														
			e.printStackTrace();
			log.errLog("om.duanrong.newadmin.controller.BankIndoController.bankDisable()",e);
			return "fail";
		}		
	}
	@RequestMapping(value = "/bankinfo/{type}")
	public String toAddbankinfo(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable String type)
			 {
		// 判断用户是否登录
		if ("toedit".equals(type)) {
			String id = request.getParameter("id");
			BankInfo bankinfo =bankInfoService.readByid(id);			
			model.addAttribute("bankcard", bankinfo);
			model.addAttribute("addUrl", "/bankinfo/save/editCard");
		} else {
			model.addAttribute("addUrl", "/bankinfo/save/saveCard");
			model.addAttribute("bankcard", new BankInfo());
		}
		return "bankinfo/addbankcard";
	}	
	@RequestMapping(value = "/bankinfo/save/{type}", method = RequestMethod.POST)
	public void addBankInfo(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable String type) {
		try {
			    	BankInfo bankcard= new BankInfo();
			    	int sortNo=0;
                    if(request.getParameter("sortNo")!=null &&!request.getParameter("sortNo").equals("")){
                    	sortNo=Integer.parseInt(request.getParameter("sortNo"));
                    }		    	
			    	bankcard.setRechargeDesc(request.getParameter("rechargeDesc"));
					bankcard.setName(request.getParameter("name"));
					bankcard.setBank(request.getParameter("bank").toUpperCase());
					bankcard.setPaymentId(request.getParameter("paymentId"));
					bankcard.setUrl(request.getParameter("logo"));
					bankcard.setTiecard(request.getParameter("tiecard"));
					bankcard.setOnlineBank(request.getParameter("onlineBank"));
					bankcard.setQuickRecharge(request.getParameter("quickRecharge"));
					bankcard.setWhetherDelete(Integer.parseInt(request.getParameter("whetherDelete")));
					bankcard.setSortNo(sortNo);
					if ("editCard".equals(type)) {
					bankcard.setId(request.getParameter("id"));
						bankInfoService.update(bankcard);
					} else {
						bankcard.setId(IdGenerator.randomUUID());
						bankInfoService.insert(bankcard);
					}
					response.getWriter().print("ok");							
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/bankinfo/toEditSortNum")
	public String toEditSortNum(HttpServletResponse response, HttpServletRequest request){
		String id=request.getParameter("id");
		String sortNum=request.getParameter("sortNum");
		BankInfo bankcard= new BankInfo();
		bankcard.setId(id);		
		bankcard.setSortNo(Integer.parseInt(sortNum));
		try {
			bankInfoService.update(bankcard);
			 return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
	}
	/**
	 * 银行卡图片上传
	 * @param files
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/bankinfo/uploadBankData", method = RequestMethod.POST)
	public void uploadArticleData(
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String str=bankInfoService.uploadBankData(files, request);
		response.getWriter().print(str);
	}
}
