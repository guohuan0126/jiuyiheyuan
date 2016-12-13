package com.duanrong.dataAnalysis.controller;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.duanrong.dataAnalysis.business.CapitalData.service.CapitalDataService;
import com.duanrong.dataAnalysis.business.loan.model.LoanData;
import com.duanrong.dataAnalysis.business.loan.service.LoanDataService;
import com.duanrong.dataAnalysis.business.user.dao.UserDataDao;
import com.duanrong.dataAnalysis.business.user.model.User;
import com.duanrong.dataAnalysis.business.user.model.UserData;
import com.duanrong.dataAnalysis.business.user.service.UserDataService;
import com.duanrong.dataAnalysis.controllerHelper.UserCookieHelper;
@Controller
public class DataController extends BaseController {

	@Resource
	UserDataService userDataService;
	@Resource
	private LoanDataService loanDataService;	
	//去数据显示页面
		@RequestMapping("/showData111")
		public String toShowData(Model model,HttpServletRequest request,HttpServletResponse response){
			UserData userData=userDataService.getUserData();
			model.addAttribute("userData", userData);
			LoanData loanData=loanDataService.getLoanData();
			model.addAttribute("loanData", loanData);
			/*User user=UserCookieHelper.GetUserCookie(request, response);
			model.addAttribute("user", user);*/
			//活期宝在投 转入减转出
			BigDecimal demandTreasureSubMoney= loanDataService.getDemandTreasureSubMoney();
			//活期宝总 转入
			BigDecimal demandTransferin=loanDataService.getDemandTransferin();
			//活期宝开放资产
			BigDecimal demandTreasureLoanMoney= loanDataService.getDemandTreasureLoanMoney();
		
			model.addAttribute("demandTreasureSubMoney", demandTreasureSubMoney);
			model.addAttribute("demandTransferin", demandTransferin);
			model.addAttribute("demandTreasureLoanMoney", demandTreasureLoanMoney);
			return "houtai1";
		}

}
