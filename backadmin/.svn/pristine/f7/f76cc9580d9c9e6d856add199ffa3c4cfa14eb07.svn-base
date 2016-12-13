package com.duanrong.newadmin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.dao.AccountCountDao;
import com.duanrong.business.user.model.AccountCount;
import com.duanrong.newadmin.model.UserCookie;

/**
 * 账户统计
 * 
 * @author Lin Zhiming
 * @version Mar 10, 2015 3:58:41 PM
 */
@Controller
public class AccountInfoController extends BaseController {

	@Resource
	AccountCountDao accountCountDao;


	/**
	 * 账户资金统计
	 */
	@RequestMapping(value = "/accountInfo/accountCountList")
	public String accountCheckingList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}

				PageInfo<AccountCount> pageInfo = accountCountDao.pageLite(
						Integer.parseInt(pageNo), 10, null);
				request.setAttribute("pageInfo", pageInfo);
				return "accountInfo/accountCountList";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 主动核对账户
	 */
	@RequestMapping(value = "/accountInfo/initiativeCheckAccount")
	public String initiativeCheckAccount(HttpServletRequest request,
			HttpServletResponse response) {
		/*try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				allUserBillService.accountChecking();
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}

				PageInfo<AccountCount> pageInfo = accountCountDao.pageLite(
						Integer.parseInt(pageNo), 10, null);
				request.setAttribute("pageInfo", pageInfo);
				return "accountInfo/accountCountList";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return null;
	}
}
