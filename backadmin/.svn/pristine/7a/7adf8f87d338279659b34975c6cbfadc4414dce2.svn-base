package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import base.pagehelper.PageInfo;

import com.duanrong.business.bankinfo.model.BankInfo;
import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankInfo;
import com.duanrong.business.payMentChannel.service.PayMentChannelService;
import com.duanrong.business.paymentInstitution.model.PaymentCompany;
import com.duanrong.newadmin.model.UserCookie;

@Controller
public class PayMentChannelController extends BaseController {

	
	@Resource
	private PayMentChannelService payMentChannelService;
	
	
	/**
	 * 渠道展示
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/payMentChannel/showChannel")
	public String showChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		Map<String,Object>params=new HashMap<String, Object>();
		String str="";
		PageInfo<PayMentChannel> pageInfo =payMentChannelService.readPayMentChannel(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("str", str);
		return "payMentChannel/payMentChannelList";
			
	}
	/**
	 * 展示银行
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/payMentChannel/payMentShowBankInfo")
	public String showBankInfo(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		String bankName=request.getParameter("bankName");
		Map<String,Object>params=new HashMap<String, Object>();
		String str="";
		if (bankName != null && !bankName.equals("")) {
			str += "&bankName=" + bankName;
			params.put("bankName",bankName);
		}
		PageInfo<PaymentBankInfo> pageInfo =payMentChannelService.readPaymentBankInfo(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("str",str);
		model.addAttribute("bankName",bankName);
		
		return "payMentChannel/payMentBankInfoList";
	}
	
	/**
	 * 展示银行渠道关联
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/payMentChannel/payMentShowBankChannel")
	public String showBankChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		String bankName=request.getParameter("bankName");
 		String channelName=request.getParameter("channelName");
		Map<String,Object>params=new HashMap<String, Object>();
		String str="";
		if (bankName != null && !bankName.equals("")) {
			str += "&bankName=" + bankName;
			params.put("bankName",bankName);
		}
		if (channelName != null && !channelName.equals("")) {
			str += "&channelName=" + channelName;
			params.put("channelName",channelName);
		}
		PageInfo<PaymentBankChannel> pageInfo =payMentChannelService.readPaymentBankChannel(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		List<PaymentBankInfo> bankList=payMentChannelService.readBankList();
		List<PayMentChannel> channelList=payMentChannelService.readChannelList();
		model.addAttribute("bankList", bankList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("str",str);
		model.addAttribute("bankName",bankName);
		model.addAttribute("channelName",channelName);
		
		return "payMentChannel/payMentBankChannelList";
	}
	
	
	@RequestMapping(value="/payMentChannel/toaddPayMentBankChannel")
	public String toaddPayMentBankChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
		List<PaymentBankInfo> bankList=payMentChannelService.readBankList();
		List<PayMentChannel> channelList=payMentChannelService.readChannelList();
		model.addAttribute("bankList", bankList);
		model.addAttribute("channelList", channelList);
	return "payMentChannel/addPayMentBankChannel";
	}
	
	
	
	@RequestMapping(value="/payMentChannel/toaddPayMentChannel")
	public String toaddPayMentChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
	return "payMentChannel/addPayMentChannel";
	}
	
	@RequestMapping(value="/payMentChannel/toaddPayMentBankInfo")
	public String toaddPayMentBankInfo(HttpServletRequest request,
			HttpServletResponse response, Model model){
	return "payMentChannel/addPayMentBankInfo";
	}
	
	/**
	 * 添加渠道
	 * @param request
	 * @param response
	 * @param model
	 */
	@ResponseBody
	@RequestMapping(value="/payMentChannel/addPayMentChannel")
	public void addPayMentChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
		String name=request.getParameter("payMentChannelName");
		String code=request.getParameter("code");
		String rateGateway=request.getParameter("rateGateway");
		String rateQuick=request.getParameter("rateQuick");
		String withdrawFee=request.getParameter("withdrawFee");
		String sort=request.getParameter("sort");
		String logo=request.getParameter("logo");
		String PCusable=request.getParameter("PCusable");
		String Mobusable=request.getParameter("Mobusable");
		PayMentChannel paymentChannel=new PayMentChannel(); 
		paymentChannel.setName(name);
		paymentChannel.setCode(code);
		paymentChannel.setLogo(logo);
		paymentChannel.setSort(sort);
		paymentChannel.setRateGateway(Double.parseDouble(rateGateway));
		paymentChannel.setRateQuick(Double.parseDouble(rateQuick));
		paymentChannel.setWithdrawFee(Double.parseDouble(withdrawFee));
		paymentChannel.setUsable(PCusable);
		paymentChannel.setMobileUsable(Mobusable);
		payMentChannelService.insertPaymentChannel(paymentChannel);
			response.getWriter().print("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	/**
	 * 添加渠道银行关系
	 * @param request
	 * @param response
	 * @param model
	 */
	@ResponseBody
	@RequestMapping(value="/payMentChannel/addPayMentBankChannel")
	public void addPayMentBankChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
		String bankName=request.getParameter("bankName");
		String channelName=request.getParameter("channelName");
		String oneMoney=request.getParameter("oneMoney");
		String dayMoney=request.getParameter("dayMoney");
		String monthMoney=request.getParameter("monthMoney");
		String onlineBanking=request.getParameter("onlineBanking");
		String quickRecharge=request.getParameter("quickRecharge");
		String tiecard=request.getParameter("tiecard");
		String urgentWithdrawals=request.getParameter("urgentWithdrawals");
		String sort=request.getParameter("sort");
		PaymentBankChannel count=payMentChannelService.readPaymentBankChannelByBankIdAndChannelId(bankName,channelName);
		if (count!=null) {// 数据库中有记录
			/*String id=count.getId();
			Map<String,Object> params=new HashMap<>();
			params.put("id", id);
			params.put("isDel", "2");
			payMentChannelService.updatePayMentBankChannel(params);
			PaymentBankChannel paymentBankChannel=new PaymentBankChannel();
			paymentBankChannel.setBankId(bankName);
			paymentBankChannel.setChannelId(channelName);
			paymentBankChannel.setOneMoney(Double.parseDouble(oneMoney));
			paymentBankChannel.setDayMoney(Double.parseDouble(dayMoney));
			paymentBankChannel.setMonthMoney(Double.parseDouble(monthMoney));
			paymentBankChannel.setOnlineBanking(onlineBanking);
			paymentBankChannel.setQuickRecharge(quickRecharge);
			paymentBankChannel.setTiecard(tiecard);
			paymentBankChannel.setUrgentWithdrawals(urgentWithdrawals);
			paymentBankChannel.setSort(sort);
			payMentChannelService.insertPaymentBankChannel(paymentBankChannel);*/
			response.getWriter().print("exist");
		} else {
			PaymentBankChannel paymentBankChannel=new PaymentBankChannel();
			paymentBankChannel.setBankId(bankName);
			paymentBankChannel.setChannelId(channelName);
			paymentBankChannel.setOneMoney(Double.parseDouble(oneMoney));
			paymentBankChannel.setDayMoney(Double.parseDouble(dayMoney));
			paymentBankChannel.setMonthMoney(Double.parseDouble(monthMoney));
			paymentBankChannel.setOnlineBanking(onlineBanking);
			paymentBankChannel.setQuickRecharge(quickRecharge);
			paymentBankChannel.setTiecard(tiecard);
			paymentBankChannel.setUrgentWithdrawals(urgentWithdrawals);
			paymentBankChannel.setSort(sort);
			payMentChannelService.insertPaymentBankChannel(paymentBankChannel);
				response.getWriter().print("ok");

		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	
	/**
	 * 添加银行
	 * @param request
	 * @param response
	 * @param model
	 */
	@ResponseBody
	@RequestMapping(value="/payMentChannel/addPayMentBankInfo")
	public void addPayMentBankInfo(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
		String bankName=request.getParameter("bankName");
		String code=request.getParameter("code");
		String logo=request.getParameter("logo");
		PaymentBankInfo paymentBankInfo=new PaymentBankInfo();
		paymentBankInfo.setName(bankName);
		paymentBankInfo.setCode(code);
		paymentBankInfo.setLogo(logo);
		payMentChannelService.insertPayMentBankInfo(paymentBankInfo);
			response.getWriter().print("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	/**
	 * 编辑渠道排序
	 */
	@ResponseBody
	@RequestMapping(value="/payMentChannel/toEditSortNum")
	public void editSortNum(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
		String sortNum=request.getParameter("sortNum");
		String id=request.getParameter("id");
		Map<String,Object> params=new HashMap<>();
		params.put("id", id);
		params.put("sort", sortNum);
		payMentChannelService.updatePaymentChannel(params);
		response.getWriter().print("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * 编辑渠道银行关系排序
	 */
	@ResponseBody
	@RequestMapping(value="/payMentChannel/toEditPayMentBankChannelSortNum")
	public void editChannelBankSortNum(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
		String sortNum=request.getParameter("sortNum");
		String id=request.getParameter("id");
		Map<String,Object> params=new HashMap<>();
		params.put("id", id);
		params.put("sort", sortNum);
		payMentChannelService.updatePayMentBankChannel(params);
		response.getWriter().print("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 *  编辑渠道页面
	 */
	@RequestMapping(value="/payMentChannel/toEditPayMentChannel")
	public String toEditPayMentChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String id=request.getParameter("id");
		PayMentChannel payMentChannel=payMentChannelService.readPayMentChannelById(id);
		model.addAttribute("payMentChannel",payMentChannel);
		return "payMentChannel/editPayMentChannel";
	}
	
	/**
	 *  编辑银行页面
	 */
	@RequestMapping(value="/payMentChannel/toEditPayMentBankInfo")
	public String toEditPayMentBankInfo(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String id=request.getParameter("id");
		PaymentBankInfo paymentBankInfo=payMentChannelService.readPaymentBankInfoById(id);
		model.addAttribute("paymentBankInfo",paymentBankInfo);
		return "payMentChannel/editPayMentBankInfo";
	}
	
	/**
	 *  编辑银行页面
	 */
	@RequestMapping(value="/payMentChannel/toEditPayMentBankChannel")
	public String toEditPayMentBankChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String id=request.getParameter("id");
		PaymentBankChannel paymentBankChannel=payMentChannelService.readPaymentBankChannelById(id);
		List<PaymentBankInfo> bankList=payMentChannelService.readBankList();
		List<PayMentChannel> channelList=payMentChannelService.readChannelList();
		model.addAttribute("bankList", bankList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("paymentBankChannel",paymentBankChannel);
		
		return "payMentChannel/editPayMentBankChannel";
	}
	/**
	 * 编辑银行信息
	 * @param request
	 * @param response
	 * @param model
	 */
	@ResponseBody
	@RequestMapping(value="/payMentChannel/editPayMentBankInfo")
	public void editPayMentBankInfo(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
			String id=request.getParameter("id");
			String name=request.getParameter("name");
			String code=request.getParameter("code");
			String logo=request.getParameter("logo");
			Map<String,Object> params=new HashMap<>();
			params.put("id",id);
			params.put("name",name);
			params.put("code",code);
			params.put("logo",logo);
			payMentChannelService.updatePaymentBankInfo(params);
				response.getWriter().print("ok");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					response.getWriter().print("error");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
	}
	

	/**
	 * 编辑渠道信息
	 * @param request
	 * @param response
	 * @param model
	 */
	@ResponseBody
	@RequestMapping(value="/payMentChannel/editPayMentBankChannel")
	public void editPayMentBankChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
			String id=request.getParameter("id");
			String bankName=request.getParameter("bankName");
			String channelName=request.getParameter("channelName");
			String oneMoney=request.getParameter("oneMoney");
			String dayMoney=request.getParameter("dayMoney");
			String monthMoney=request.getParameter("monthMoney");
			String onlineBanking=request.getParameter("onlineBanking");
			String quickRecharge=request.getParameter("quickRecharge");
			String tiecard=request.getParameter("tiecard");
			String urgentWithdrawals=request.getParameter("urgentWithdrawals");
			String sort=request.getParameter("sort");
			Map<String,Object> params=new HashMap<>();
			params.put("id",id);
			params.put("bankName",bankName);
			params.put("channelName",channelName);
			params.put("oneMoney",oneMoney);
			params.put("dayMoney",dayMoney);
			params.put("monthMoney",monthMoney);
			params.put("onlineBanking",onlineBanking);
			params.put("quickRecharge",quickRecharge);
			params.put("tiecard",tiecard);
			params.put("urgentWithdrawals",urgentWithdrawals);
			params.put("sort",sort);
			payMentChannelService.updatePayMentBankChannel(params);
				response.getWriter().print("ok");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					response.getWriter().print("error");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
	}
	
	
	
	
	/**
	 * 编辑渠道信息
	 * @param request
	 * @param response
	 * @param model
	 */
	@ResponseBody
	@RequestMapping(value="/payMentChannel/editPayMentChannel")
	public void editPayMentChannel(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
			String id=request.getParameter("id");
			String name=request.getParameter("payMentChannelName");
			String code=request.getParameter("code");
			String rateGateway=request.getParameter("rateGateway");
			String rateQuick=request.getParameter("rateQuick");
			String withdrawFee=request.getParameter("withdrawFee");
			String sort=request.getParameter("sort");
			String logo=request.getParameter("logo");
			String PCusable=request.getParameter("PCusable");
			String Mobusable=request.getParameter("Mobusable");
			Map<String,Object> params=new HashMap<>();
			params.put("id",id);
			params.put("name",name);
			params.put("code",code);
			params.put("rateGateway",rateGateway);
			params.put("rateQuick",rateQuick);
			params.put("withdrawFee",withdrawFee);
			params.put("sort",sort);
			params.put("logo",logo);
			params.put("PCusable",PCusable);
			params.put("Mobusable",Mobusable);
			payMentChannelService.updatePaymentChannel(params);
				response.getWriter().print("ok");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					response.getWriter().print("error");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
	}
	
	
	
	/**
	 * 渠道图片上传
	 * @param files
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/payMentChannel/payMentChannelLogo", method = RequestMethod.POST)
	public void uploadArticleData(
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String str=payMentChannelService.uploadPayMentChannel(files, request);
		response.getWriter().print(str);
	}
	/**
	 * 银行logo图片上传
	 * @param files
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/payMentChannel/payMentBankInfoLogo", method = RequestMethod.POST)
	public void uploadArticleBankInfoData(
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String str=payMentChannelService.uploadPayMentBankInfo(files, request);
		response.getWriter().print(str);
	}
}
