package com.duanrong.newadmin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.Log;
import util.MyCollectionUtils;
import util.MyStringUtils;
import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.autoinvest.model.AutoInvest;
import com.duanrong.business.autoinvest.service.AutoInvestService;
import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.bankcard.service.BankCardService;
import com.duanrong.business.permission.model.Black;
import com.duanrong.business.permission.model.Permission;
import com.duanrong.business.permission.service.PermissionService;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.user.model.Config;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserByFuRen;
import com.duanrong.business.user.model.UserInfoConfirmation;
import com.duanrong.business.user.model.UserInvestCheck;
import com.duanrong.business.user.model.UserLoginLog;
import com.duanrong.business.user.model.UserOtherInfo;
import com.duanrong.business.user.model.UserRemarkInfo;
import com.duanrong.business.user.model.UserVisitInfo;
import com.duanrong.business.user.model.newInvestUser;
import com.duanrong.business.user.service.AuthInfoService;
import com.duanrong.business.user.service.RedPacketService;
import com.duanrong.business.user.service.RoleService;
import com.duanrong.business.user.service.UserLoginLogService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.user.service.UserOtherInfoService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.user.service.UserVisitInfoService;
import com.duanrong.business.yeepay.service.FreezeService;
import com.duanrong.newadmin.constants.BlackListConstants;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.AESUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.FormUtil;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.udesk.service.UdeskService;
import com.duanrong.util.ArithUtil;

/**
 * 
 * @author wangjing
 * @date 2014-9-1下午5:44:28
 */
@Controller
public class UserController extends BaseController {

	@Resource
	PermissionService permissionService;
	@Resource
	RedPacketService redPacketService;
	@Resource
	RoleService roleService;
	@Resource
	AutoInvestService autoInvestService;
	@Resource
	BankCardService bankCardService;
	@Resource
	UserService userService;
	@Resource
	UserOtherInfoService userOtherInfoService;
	@Resource
	FreezeService freezeService;
	@Resource
	OperaRecordService operaRecordService;
	@Resource
	UserVisitInfoService userVisitInfoService;
	@Resource
	UserMoneyService userMoneyService;
	
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	Log log;

	@Autowired
	UserLoginLogService userLoginLogService;
	@Resource
	AuthInfoService authInfoService;
    @Resource
    UdeskService udeskService;
	/**
	 * 
	 * @description 跳转登陆页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/permis/permisList")
	public String toPermission(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 判断用户是否登录
		String pageNo = request.getParameter("pageNo");
		String pageSize = "25";
		if (pageNo == null) {
			pageNo = "1";
		}
		Permission permission = new Permission();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String permissType = request.getParameter("permissType");
		try {
			if (name != null) {
				name = java.net.URLDecoder.decode(name, "UTF-8");
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String str = "";
		if (id != null && !id.equals("")) {
			str += "&id=" + id;
		}
		if (name != null && !name.equals("")) {
			str += "&name=" + name;
		}
		if (permissType != null && !permissType.equals("")) {
			str += "&permissType=" + permissType;
		}

		permission.setId(id);
		permission.setName(name);
		permission.setType(permissType);
		PageInfo<Permission> pageInfo = permissionService.readPageLite(
				Integer.parseInt(pageNo), Integer.parseInt(pageSize),
				permission);
		model.addAttribute("name", name);
		model.addAttribute("id", id);
		model.addAttribute("url", "/permis/permisList");
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("str", str);
		model.addAttribute("permissType", permissType);
		return "user/permis/permisList";
	}

	/**
	 * 
	 * @description 跳转添加页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/permis/{type}")
	public String toAdd(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable String type)
			throws UnsupportedEncodingException {
		// 判断用户是否登录
		// model.addAttribute("url", "/permis/permisList");
		if ("toedit".equals(type)) {

			String id = request.getParameter("id");
			id = new String(id.getBytes("ISO8859-1"), "UTF-8");
			Permission p = permissionService.readPermissionById(id);
			model.addAttribute("permission", p);
			model.addAttribute("addUrl", "/permis/save/editPermis");
		} else {
			model.addAttribute("addUrl", "/permis/save/addPermis");
			model.addAttribute("permission", new Permission());
		}
		return "user/permis/addPermis";
	}

	/**
	 * 
	 * @description 跳转添加页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/permis/save/{type}", method = RequestMethod.POST)
	public void addPermis(HttpServletRequest request,
			HttpServletResponse response, Permission permission, Model model,
			@PathVariable String type) {
		try {
			permission.setId(request.getParameter("id"));
			permission.setName(request.getParameter("name"));
			permission.setType(request.getParameter("permissType"));
			permission.setDescription(request.getParameter("description"));
			if ("editPermis".equals(type)) {
				permissionService.updatePermission(permission);
			} else {
				permissionService.insertPermission(permission);
			}
			request.getSession().removeAttribute("menu");
			request.getSession().removeAttribute("permission");
			response.getWriter().print("ok");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description 跳转登陆页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/role/roleList")
	public String toRole(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		// 判断用户是否登录
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		}

		String pageNo = request.getParameter("pageNo");
		String pageSize = "50";
		if (pageNo == null) {
			pageNo = "1";
		}
		Role role = new Role();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		try {
			if (name != null) {
				name = java.net.URLDecoder.decode(name, "UTF-8");
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = "";
		if (id != null && !id.equals("")) {
			str += "&id=" + id;
		}
		if (name != null && !name.equals("")) {
			str += "&name=" + name;
		}

		role.setId(id);
		role.setName(name);

		PageInfo<Role> pageInfo = roleService.readPaging(
				Integer.parseInt(pageNo), Integer.parseInt(pageSize), role);
		model.addAttribute("name", name);
		model.addAttribute("id", id);
		model.addAttribute("url", "/role/roleList");
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("str", str);
		return "user/role/roleList";
	}

	/**
	 * 
	 * @description 跳转添加页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/role/{type}")
	public String toAddRole(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable String type)
			throws UnsupportedEncodingException {
		// 判断用户是否登录
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		}
		PageInfo<Permission> pageInfo = permissionService.readPageLite(1, 0,
				new Permission());
		List<Permission> lists = pageInfo.getResults();
		List<Permission> mList = new ArrayList<Permission>();
		List<Permission> aList = new ArrayList<Permission>();
		for (Permission p : lists) {
			if (p != null && p.getType() != null && p.getType().equals("menu")) {
				mList.add(p);
			}
			if (p != null && p.getType() != null
					&& p.getType().equals("active")) {
				aList.add(p);
			}
		}
		model.addAttribute("url", "/role/roleList");
		model.addAttribute("pageInfo", pageInfo);
		if ("toedit".equals(type)) {

			String id = request.getParameter("id");
			if (MyStringUtils.isNotAnyBlank(id)) {
				id = new String(id.getBytes("ISO8859-1"), "UTF-8");
			}
			Role role = roleService.readRoleById(id);
			List<Permission> list = permissionService
					.readPermisstionByRoleId(id);
			String str = "";
			int count = 0;
			for (Permission p : list) {
				count++;
				str += p.getId();
				if (count != list.size()) {
					str += ",";
				}
			}
			model.addAttribute("role", role);
			model.addAttribute("pids", str);

			model.addAttribute("addUrl", "/role/save/editRole");

		} else {
			model.addAttribute("addUrl", "/role/save/addRole");
		}
		model.addAttribute("aList", aList);
		model.addAttribute("mList", mList);
		return "user/role/addRole";
	}

	/**
	 * 
	 * @description 跳转添加页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/role/save/{type}", method = RequestMethod.POST)
	public void addRole(HttpServletRequest request,
			HttpServletResponse response, Role role, Model model,
			@PathVariable String type) {

		try {
			String pid = request.getParameter("pid");
			String str = request.getParameter("ids");
			String[] ids = str.split(",");
			List<Permission> permissions = new ArrayList<Permission>();
			for (String id : ids) {
				Permission permis = new Permission();
				permis.setId(id);
				permissions.add(permis);
			}
			if ("editRole".equals(type)) {
				role.setId(pid);
				boolean b = roleService.alertRole(role);
				if (b == false) {
					response.getWriter().print("fail");
				} else {
					permissionService.grantRolePermission(role, permissions);
					request.getSession().removeAttribute("menu");
					request.getSession().removeAttribute("permission");
					response.getWriter().print("ok");
				}

			} else {
				boolean b = roleService.addRole(role);
				if (b == false) {
					response.getWriter().print("fail");
				} else {
					permissionService.grantRolePermission(role, permissions);
					request.getSession().removeAttribute("menu");
					request.getSession().removeAttribute("permission");
					response.getWriter().print("ok");
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.addRole().Exception",
					e);
		}
	}

	/**
	 * 
	 * @description 充值管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/autoInvest/autoInvestList")
	public String autoInvestList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				String str = "";
				String userId = getUserId(request.getParameter("userId"));
				String status = request.getParameter("status");
				String realname = request.getParameter("realname");
				AutoInvest autoInvest = new AutoInvest();
				String[] c = new String[4];

				if (userId != null && !userId.equals("")) {
					str += "&userId=" + userId;
					c[0] = userId;
				}
				if (realname != null && !realname.equals("")) {
					String name = java.net.URLDecoder.decode(realname, "UTF-8");
					str += "&realname=" + name;
					c[2] = name;
				}

				if (status != null && status.equals("status")) {

				} else if (status != null && !status.equals("")) {
					str += "&status=" + status;
					c[1] = status;
				} else {
					str += "&status=on";
					c[1] = "on";
				}
				autoInvest.setConditions(c);
				PageInfo<AutoInvest> pageInfo = autoInvestService.readPageInfo(
						 Integer.parseInt(pageNo), 10, autoInvest);
				model.addAttribute("url", "/autoInvest/autoInvestList");
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("realname", realname);
				model.addAttribute("userId", userId);
				model.addAttribute("status", status);
				model.addAttribute("str", str);
				return "user/autoInvest/autoInvestList";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改状态
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/autoInvest/editStatus")
	public void editStatus(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String userId = request.getParameter("userId");
		String status = request.getParameter("status");
		AutoInvest autoInvest = new AutoInvest();
		autoInvest.setUserId(userId);
		if (MyStringUtils.isNotBlank(status) && status.equals("off")) {
			autoInvest.setStatus("on");
		}
		if (MyStringUtils.isNotBlank(status) && status.equals("on")) {
			autoInvest.setStatus("off");
		}
		try {
			autoInvestService.update(autoInvest);

			response.getWriter().print("ok");
		} catch (Exception e) {
			// e.printStackTrace();
			response.getWriter().print("error");
		}
	}

	@RequestMapping(value = "/permis/ipPermis/{type}")
	public String ipPermission(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type)
			throws IOException {
		if ("list".equals(type)) {
			request.setAttribute("ips", permissionService.readIpList());
			return "user/permis/ipPermis";
		} else if ("toAdd".equals(type)) {
			return "user/permis/createIpPermis";
		} else if ("add".equals(type)) {
			if (permissionService.addIPs(paramToList(request))) {
				request.getSession().removeAttribute("ips");
				response.getWriter().print("ok");
				return null;
			}
		} else if ("edit".equals(type)) {
			if (permissionService.updateIPs(paramToList(request))) {
				request.getSession().removeAttribute("ips");
				response.getWriter().print("ok");
				return null;
			}

		} else if ("del".equals(type)) {
			if (permissionService.deleteIPs(paramToList(request))) {
				request.getSession().removeAttribute("ips");
				response.getWriter().print("ok");
				return null;
			}
		}

		return null;
	}

	private List<String> paramToList(HttpServletRequest request) {
		String[] ips = request.getParameterValues("ip");
		List<String> ip = new ArrayList<>();
		if (ips.length > 0) {
			for (int i = 0; i < ips.length; i++) {
				ip.add(ips[i]);
			}
		}
		return ip;
	}

	private List<Black> paramToList(String[] ips, String type, List<String> arr) {

		List<Black> blackList = new ArrayList<>();
		if (ips != null && ips.length > 0) {
			TreeSet<String> ipSet = new TreeSet<String>();
			for (String str : ips) {
				ipSet.add(str);
			}
			Iterator it = ipSet.iterator();
			while (it.hasNext()) {
				String fruit = (String) it.next();
				boolean b = true;
				for (String str : arr) {
					if (str.equals(fruit)) {
						b = false;
					}
				}
				if (b) {
					Black bl = new Black();
					bl.setContent(fruit);
					bl.setType(type);
					blackList.add(bl);
				}

			}
		}
		return blackList;
	}

	/**
	 * 
	 * @description 验证码列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bankcard/bankcardList")
	public String bankcardList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				String str = "";
				String id = request.getParameter("userId");
				String userId = getUserId(id);
				String status = request.getParameter("status");
				String realname = request.getParameter("realname");
				String name = "";
				Map<String, Object> params = new HashMap<String, Object>();

				if (StringUtils.isNotBlank(userId)) {
					str += "&userId=" + userId;
					params.put("userId", userId);
				}

				if (StringUtils.isNotBlank(status)) {
					str += "&status=" + status;
					params.put("status", status);
				}
				if (StringUtils.isNotBlank(realname)) {
					name = java.net.URLDecoder.decode(realname, "UTF-8");
					params.put("realname", name);
					str += "&realname=" + name;

				}
				PageInfo<BankCard> pageInfo = bankCardService.readPageInfo(
						Integer.parseInt(pageNo), 10, params);
				model.addAttribute("url", "/bankcard/bankcardList");
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("status", status);
				model.addAttribute("realname", name);
				model.addAttribute("userId", id);
				model.addAttribute("str", str);
				return "user/bankcard/bankcardList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					" com.duanrong.newadmin.controller.UserController.bankcardList()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description 用户管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/userList/{type}")
	public String userList(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable String type) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);

			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}

			String param = request.getParameter("userId");
			String userId = "";
			if (param != null) {
				userId = this.getUserId(param.trim());
			} else {
				userId = this.getUserId(param);
			}

			String str = "";
			String realname = request.getParameter("realname");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String rstart = request.getParameter("rstart");
			String rend = request.getParameter("rend");
			String tstart = request.getParameter("tstart");
			String tend = request.getParameter("tend");
			String maxMoney = request.getParameter("maxMoney");
			String minMoney = request.getParameter("minMoney");
			String referrer = request.getParameter("referrer");
			String source = request.getParameter("source");
			String sooner = request.getParameter("sooner");
			String salesman = request.getParameter("salesman");
			String userIp = request.getParameter("userIp");
			String usertype = request.getParameter("usertype");
			String ids = request.getParameter("ids");

			String name = "";
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			if (StringUtils.isNotBlank(usertype) && usertype.equals("duanrongw")) {
				str += "&usertype=" + usertype;
				params.put("duanrongw", usertype);
			}
			if (StringUtils.isNotBlank(usertype) && usertype.equals("furen")) {
				str += "&usertype=" + usertype;
				params.put("furen", usertype);
			}
			if (StringUtils.isNotBlank(usertype) && usertype.equals("nelson")) {
				str += "&usertype=" + usertype;
				params.put("nelson", usertype);
			}
			if (StringUtils.isNotBlank(usertype) && usertype.equals("organization")) {
				str += "&usertype=" + usertype;
				params.put("organization", usertype);
			}
						
			if (StringUtils.isNotBlank(userId)) {
				str += "&userId=" + userId;
				params.put("userId", userId);
			}
			if (StringUtils.isNotBlank(userIp)) {
				str += "&userIp=" + userIp;
				params.put("userIp", userIp);
			}
			if (StringUtils.isNotBlank(ids)) {
				String[] st1 = ids.split(",");
				str += "&ids=" + ids;
				params.put("ids", st1);
			}

			if (StringUtils.isNotBlank(sooner)) {
				str += "&sooner=" + sooner;
				params.put("sooner", sooner);
			}
			if (StringUtils.isNotBlank(salesman)) {
				String sa = java.net.URLDecoder.decode(salesman, "UTF-8");
				String[] st = sa.split(",");
				str += "&salesman=" + sa;
				params.put("salesman", st);
				model.addAttribute("salesman", sa);

			}
			if (StringUtils.isNotBlank(minMoney)) {
				double min = Double.parseDouble(minMoney);
				str += "&minMoney=" + min;
				params.put("minMoney", min);
			}
			if (StringUtils.isNotBlank(maxMoney)) {
				double max = Double.parseDouble(maxMoney);
				str += "&maxMoney=" + max;
				params.put("maxMoney", max);
			}
			if (StringUtils.isNotBlank(realname)) {
				name = java.net.URLDecoder.decode(realname, "UTF-8");
				params.put("realname", name);
				str += "&realname=" + name;

			}
			if (StringUtils.isNotBlank(start)) {
				params.put("start", java.net.URLDecoder.decode(start, "UTF-8"));
				str += "&start=" + java.net.URLDecoder.decode(start, "UTF-8");
				model.addAttribute("start",
						java.net.URLDecoder.decode(start, "UTF-8"));
			}
			if (StringUtils.isNotBlank(end)) {
				params.put("end", java.net.URLDecoder.decode(end, "UTF-8"));
				str += "&end=" + java.net.URLDecoder.decode(end, "UTF-8");
				model.addAttribute("end",
						java.net.URLDecoder.decode(end, "UTF-8"));
			}
			if (StringUtils.isNotBlank(referrer)) {
				params.put("referrer", referrer);
				str += "&referrer=" + referrer;
			}
			if (StringUtils.isNotBlank(source)) {
				params.put("source", source);
				str += "&source=" + source;
			}
			if (StringUtils.isNotBlank(usertype)) {
				if(usertype.equals("enterprise") || usertype.equals("user")){}
				params.put("usertype", usertype);
				str += "&usertype=" + usertype;
			}
			if (StringUtils.isNotBlank(rstart)) {
				params.put("rstart", rstart);
				str += "&rstart=" + rstart;
			}

			if (StringUtils.isNotBlank(rend)) {
				params.put("rend", DateUtil.addDay(rend, 1));
				str += "&rend=" + rend;
			}
			if (StringUtils.isNotBlank(tstart)) {
				params.put("tstart", tstart);
				str += "&tstart=" + tstart;
			}
			if (StringUtils.isNotBlank(tend)) {
				params.put("tend", DateUtil.addDay(tend, 1));
				str += "&tend=" + tend;
			}
			params.put("notmobile", "notmobile");
			if (type.equals("search")) {
				params.put("type", "r.register_time");
				params.put("ordertype", "desc");
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("nosearch")) {
				params.put("type", "r.register_time");
				params.put("ordertype", "desc");
				params.put("time", "time");
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("searc")) {
				params.put("type", "r.register_time");
				params.put("ordertype", "desc");
				params.put("searc", "searc");
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("customer")) {
				params.put("type", "invest_time");
				params.put("ordertype", "desc");
				params.put("customer", "customer");
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}

			if (type.equals("blanceasc")) {
				params.put("type", "r.balance");
				params.put("ordertype", "asc");
				model.addAttribute("flag", 0);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("cblanceasc")) {
				params.put("type", "r.balance");
				params.put("ordertype", "asc");
				params.put("customer", "customer");
				model.addAttribute("flag", 0);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("noblanceasc")) {
				params.put("type", "r.balance");
				params.put("time", "time");
				params.put("ordertype", "asc");
				model.addAttribute("flag", 0);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("blancedesc")) {
				params.put("type", "r.balance");
				params.put("ordertype", "desc");
				model.addAttribute("flag", 1);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("cblancedesc")) {
				params.put("type", "r.balance");
				params.put("ordertype", "desc");
				params.put("customer", "customer");
				model.addAttribute("flag", 1);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("noblancedesc")) {
				params.put("type", "r.balance");
				params.put("time", "time");
				params.put("ordertype", "desc");
				model.addAttribute("flag", 1);
				model.addAttribute("flag1", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("moneyasc")) {
				params.put("type", "r.invest_money_total");
				params.put("ordertype", "asc");
				model.addAttribute("flag1", 0);
				model.addAttribute("flag", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("cmoneyasc")) {
				params.put("type", "r.invest_money_total");
				params.put("ordertype", "asc");
				params.put("customer", "customer");
				model.addAttribute("flag1", 0);
				model.addAttribute("flag", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("nomoneyasc")) {
				params.put("type", "r.invest_money_total");
				params.put("ordertype", "asc");
				params.put("time", "time");
				model.addAttribute("flag1", 0);
				model.addAttribute("flag", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("nomoneydesc")) {
				params.put("type", "r.invest_money_total");
				params.put("ordertype", "desc");
				params.put("time", "time");
				model.addAttribute("flag1", 1);
				model.addAttribute("flag", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("moneydesc")) {
				params.put("type", "r.invest_money_total");
				params.put("ordertype", "desc");
				model.addAttribute("flag1", 1);
				model.addAttribute("flag", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("cmoneydesc")) {
				params.put("type", "r.invest_money_total");
				params.put("ordertype", "desc");
				params.put("customer", "customer");
				model.addAttribute("flag1", 1);
				model.addAttribute("flag", -1);
				model.addAttribute("flag2", -1);
			}
			if (type.equals("investasc")) {
				params.put("type", "investnum");
				params.put("ordertype", "asc");
				model.addAttribute("flag2", 0);
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
			}
			if (type.equals("cinvestasc")) {
				params.put("type", "investnum");
				params.put("customer", "customer");
				params.put("ordertype", "asc");
				model.addAttribute("flag2", 0);
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
			}
			if (type.equals("noinvestasc")) {
				params.put("type", "investnum");
				params.put("ordertype", "asc");
				params.put("time", "time");
				model.addAttribute("flag2", 0);
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
			}
			if (type.equals("investdesc")) {
				params.put("type", "investnum");
				params.put("ordertype", "desc");
				model.addAttribute("flag2", 1);
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
			}
			if (type.equals("cinvestdesc")) {
				params.put("type", "investnum");
				params.put("ordertype", "desc");
				params.put("customer", "customer");
				model.addAttribute("flag2", 1);
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
			}
			if (type.equals("noinvestdesc")) {
				params.put("type", "investnum");
				params.put("ordertype", "desc");
				params.put("time", "time");
				model.addAttribute("flag2", 1);
				model.addAttribute("flag", -1);
				model.addAttribute("flag1", -1);
			}
			if (type.equals("search") && params.size() == 3) {
				List<Permission> ps = permissionService
						.readPermisstionByUserId(getLoginUser.getUserId());
				boolean f = false;
				for (Permission p : ps) {
					if (p.getId().equals("USER_SEARCH_ALL")) {
						f = true;
						break;
					}
				}
				if (f == false) {
					return "user/userList";
				}
			}
			PageInfo<User> pageInfo = userService.readPageInfo(
					Integer.parseInt(pageNo), 30, params);
			List<User> list = userService.readUserTotalMoney(params);
			User user = new User();
			if (MyCollectionUtils.isNotBlank(list)) {
				user = list.get(0);
				if (user != null) {
					if (user.getTotalCurrMoney() == null) {
						user.setTotalCurrMoney(0D);
					}
					if (user.getTotalMoney() == null) {
						user.setTotalMoney(0D);
					}
				} else {
					user = new User();
				}
			}
			model.addAttribute("url", "/user/userList/" + type);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("rstart", rstart);
			model.addAttribute("rend", rend);
			model.addAttribute("tstart", tstart);
			model.addAttribute("tend", tend);
			model.addAttribute("realname", name);
			model.addAttribute("sooner", sooner);
			model.addAttribute("minMoney", minMoney);
			model.addAttribute("maxMoney", maxMoney);
			model.addAttribute("userId", param);
			model.addAttribute("referrer", referrer);
			model.addAttribute("str", str);
			model.addAttribute("user", user);
			model.addAttribute("ids", ids);
			model.addAttribute("userIp", userIp);
			model.addAttribute("usertype", usertype);
			model.addAttribute("type", type);
			if (type.contains("no")) {
				return "user/noLogin";
			} else {
				return "user/userList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.userList()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description 跳转添加页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/toedit")
	public String toEdit(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try{
			// 判断用户是否登录
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			}
			String userId = request.getParameter("id");
			String url = request.getParameter("url");
			List<Role> roles = roleService.readRolesByUserId(userId);
			List<Role> list = roleService.readAllRoles();
			User user = userService.read(userId);
			
			//增加显示 是否开通易宝账户，红包信息，金额信息
			Map<String,String> investormap =new HashMap<String,String>();
			investormap=roleService.readInvestorByUserId(userId);
			String investor=String.valueOf(investormap.get("investor"));
			
			UserAccount account = userAccountService.readUserAccount(userId);
			double bbalance = 0;
			double bfreezeAmount = 0;
			if (account != null) {
				//账户可用金额
				bbalance = account.getAvailableBalance();
				//账户冻结金额
				bfreezeAmount = account.getFreezeAmount();
			}
	/*		//账户余额
			double bavailableAmount = ArithUtil.round(bbalance + bfreezeAmount,2);
			*/
			//红包信息
			String mobileNumber = user.getMobileNumber(); //电话
			Map<String,String> redmap =new HashMap<String,String>();
			redmap=redPacketService.readRedPacketByMobile(mobileNumber);
			UserOtherInfo other = userOtherInfoService.read(userId);
			String str = "";
			int count = 0;
			for (Role p : roles) {
				count++;
				str += p.getId();
				if (count != roles.size()) {
					str += ",";
				}
			}
			model.addAttribute("bbalance", bbalance);
			model.addAttribute("bfreezeAmount", bfreezeAmount);
			model.addAttribute("investor", investor);
			model.addAttribute("used", redmap.get("used"));
			model.addAttribute("redsum", redmap.get("redsum"));
			model.addAttribute("unused", redmap.get("unused"));
			
		//	model.addAttribute("used", "1");
		//	model.addAttribute("unused", "1");
			
			
			model.addAttribute("user", user);
			model.addAttribute("pids", str);
			model.addAttribute("list", list);
			model.addAttribute("other", other);
			model.addAttribute("url", url);
			return "user/editUser";
		}catch(Exception e){
			e.printStackTrace();
			log.errLog("UserController.toEdit()", e);
		}
		return null;
	}

	/**
	 * 
	 * @description 修改
	 * @author zhangyingwei
	 * @time 2016-06-17下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/edituser", method = RequestMethod.POST)
	public void edit(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		// 判断用户是否登录
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			String mobileNumber = request.getParameter("mobileNumber"); //电话
			String userId = request.getParameter("username");      //用户
			String status = request.getParameter("bct");          //状态
			
			String winXin = request.getParameter("winXin");       //微信
			String userType = request.getParameter("userType");       //用户类型
			String phoneNoCity = request.getParameter("phoneNoCity");       //手机号归属市
			String phoneNoAttribution = request.getParameter("phoneNoAttribution");       //手机号归属省
			Date registerTime =DateUtil.StringToDate(request.getParameter("registerTime"),"yyyy-MM-dd HH:mm:ss") ;       //注册时间
			String postCode = request.getParameter("postCode");       //邮编
			String investor = request.getParameter("investor");       //是否开通易宝账户  0未开通1已开通
			String registerTime1 =request.getParameter("registerTime1") ;       //注册时间
			String userSource = request.getParameter("userSource") ;         //用户来源
			
			User user = new User();
			user.setUserId(userId);
			user.setStatus(status);
			user.setMobileNumber(mobileNumber);
			user.setWeixin(winXin);
			user.setUserType(userType);
			user.setPhoneNoCity(phoneNoCity);
			user.setPhoneNoAttribution(phoneNoAttribution);
			user.setPostCode(postCode);
			user.setRegisterTime(registerTime);
			
			
			//增加显示 是否开通易宝账户，红包信息，金额信息
			Map<String,String> investormap =new HashMap<String,String>();
			investormap=roleService.readInvestorByUserId(userId);
			
			
			if(!investor.equals(String.valueOf(investormap.get("investor")))){
				
				//由已开通变为未开通
				if(investor.equals("0")){
					Map<String,String> roidsMap =new HashMap<String,String>();
					roidsMap.put("userId", userId);
					roidsMap.put("roleId", "investor");				
					permissionService.deleteUserRoleID(roidsMap);
					
				}
				//由未开通变为已开通
				else if (investor.equals("1")){
					
					List<Role> roles = new ArrayList<Role>();

							Role role = new Role();
							role.setId("investor");
							roles.add(role);
							permissionService.grantUserRole(userId, roles);											
				}		
			}
			
			String  oldmobile = userService.read(userId).getMobileNumber();
			//修改前的user
			User userold = userService.read(userId);
			
			userService.updateUser(user);
			UserOtherInfo userOtherInfo = new UserOtherInfo();
			userOtherInfo.setId(userId);
			userOtherInfo.setUserSource(userSource);
			userOtherInfoService.insertOrUpdateNoUser(userOtherInfo);
			
			
			//修改红包表手机号
			
			Map<String,String> redmap =new HashMap<String,String>();
			redmap.put("oldmobile", oldmobile);
			redmap.put("mobileNumber", mobileNumber);
			
			redPacketService.updatePacketByMobile(redmap);
			
			
			User loginuserId = userService.read(GetLoginUser(request, response).getUserId());
			
			
			operaRecordService.insertRecord("用户编辑", getLoginUser.getUserId(),
					mobileNumber);
			//记录日志发送邮件
			StringBuffer content =new StringBuffer("");
			boolean flog =false;
			content.append("当前用户:用户编号"+loginuserId.getUserId()+"用户真实姓名"+loginuserId.getRealname()+"修改了用户:用户编号"+userold.getUserId()+"用户真实姓名"+userold.getRealname()+"的基本信息,");
			
			if((userold.getStatus()==null&&status!=null&&status!="" )|| (userold.getStatus()!=null&&!userold.getStatus().equals(status))){
				content.append("修改状态,之前的内容是："+status(userold.getStatus())+",修改之后的内容是:"+status(status)+".");
				flog=true;
			}
			if((userold.getMobileNumber()==null&&mobileNumber!=null&&mobileNumber!="" )|| (userold.getMobileNumber()!=null&&!userold.getMobileNumber().equals(mobileNumber))){
				content.append("修改电话号,之前的内容是："+userold.getMobileNumber()+",修改之后的内容是:"+mobileNumber+".");
				flog=true;
			}
			if((userold.getWeixin()==null&&winXin!=null&&winXin!="")||(userold.getWeixin()!=null&&!userold.getWeixin().equals(winXin))){
				content.append("修改微信,之前的内容是："+userold.getWeixin()+",修改之后的内容是:"+winXin+".");
				flog=true;
			}
			if((userold.getUserType()==null&&userType!=null&&userType!="" )|| (userold.getUserType()!=null&&!userold.getUserType().equals(userType))){
				content.append("修改用户类型,之前的内容是："+usertype(userold.getUserType())+",修改之后的内容是:"+usertype(userType)+".");
				flog=true;
			}
			if((userold.getPhoneNoCity()==null&&phoneNoCity!=null&&phoneNoCity!="" )|| (userold.getPhoneNoCity()!=null&&!userold.getPhoneNoCity().equals(phoneNoCity))){
				content.append("修改手机号归属市,之前的内容是："+userold.getPhoneNoCity()+",修改之后的内容是:"+phoneNoCity+".");
				flog=true;
			}
			if((userold.getPhoneNoAttribution()==null&&phoneNoAttribution!=null&&phoneNoAttribution!="" )|| (userold.getPhoneNoAttribution()!=null&&!userold.getPhoneNoAttribution().equals(phoneNoAttribution))){
				content.append("修改手机号归属省,之前的内容是："+userold.getPhoneNoAttribution()+",修改之后的内容是:"+phoneNoAttribution+".");
				flog=true;
			}
			if((userold.getPostCode()==null&&postCode!=null&&postCode!="" )|| (userold.getPostCode()!=null&&!userold.getPostCode().equals(postCode))){
				content.append("修改邮编,之前的内容是："+userold.getPostCode()+",修改之后的内容是:"+postCode+".");
				flog=true;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			
			if(userold.getRegisterTime()!=null&&!(userold.getRegisterTime().toString()).equals(registerTime1)){
				content.append("修改注册时间,之前的内容是："+df.format(userold.getRegisterTime())+",修改之后的内容是:"+df.format(registerTime1)+".");
				flog=true;
			}
			if((investormap.get("investor")==null&&investor!=null&&investor!="" )|| (investormap.get("investor")!=null&&!String.valueOf(investormap.get("investor")).equals(investor))){
				content.append("修改是否开通易宝账户,之前的内容是："+investor(String.valueOf(investormap.get("investor")))+",修改之后的内容是:"+investor(investor)+".");
				flog=true;
			}
			if(flog){
			log.infoLog("管理员操作记录", content.toString(), 1,"zhongkeke@duanrong.com");
			}
			request.getSession().removeAttribute("menu");
			request.getSession().removeAttribute("permission");
			response.getWriter().print("ok");

		} catch (Exception e) {
			try {
				response.getWriter().print(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.edit().Exception",
					e);
		}

	}
	private String status(String val){
		if(val.equals("0")){
			return "禁用";
		}else{
			return "正常";
		}
	}
	private String usertype(String val){
		if(val.equals("user")){
			return "个人";
		}else{
			return "企业";
		}
	}
	private String investor(String val){
		if(val.equals("0")){
			return "未开通";
		}else{
			return "已开通";
		}
	}
	/**
	 * 
	 * @description 注销
	 * @author zhangyingwei
	 * @time 2016-06-17下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/canceluser", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		// 判断用户是否登录
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			String mobileNumber = request.getParameter("mobileNumber"); //电话
			String userId = request.getParameter("username");      //用户
			//注销用户信息
			User user = new User();
			user.setUserId(userId);
			user.setStatus("0");
			user.setMobileNumber(null);
			user.setIdCard("");
			userService.cancelUser(user);
			//注销红包信息
			redPacketService.delRedPacketByMobile(mobileNumber);
			User loginuserId = userService.read(GetLoginUser(request, response).getUserId());
			
			operaRecordService.insertRecord("用户编辑", getLoginUser.getUserId(),
					mobileNumber);
			User userold = userService.read(userId);
			StringBuffer content =new StringBuffer("");
			content.append("当前用户:用户编号"+loginuserId.getUserId()+"用户真实姓名"+loginuserId.getRealname()+"注销了用户:用户编号"+userold.getUserId()+"用户真实姓名"+userold.getRealname()+"用户手机号"+mobileNumber);
			log.infoLog("管理员操作记录", content.toString(), 1,"zhongkeke@duanrong.com");
			request.getSession().removeAttribute("menu");
			request.getSession().removeAttribute("permission");
			response.getWriter().print("ok");

		} catch (Exception e) {
			try {
				response.getWriter().print(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.edit().Exception",
					e);
		}

	}
	/**
	 * 
	 * @description 新增
	 * @author zhangyingwei
	 * @time 2016-06-17下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/editrole", method = RequestMethod.POST)
	public void editrose(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		// 判断用户是否登录
		UserCookie getLoginUser = GetLoginUser(request, response);
		try {
			
			String userId = request.getParameter("username");
			String str = request.getParameter("ids");
			String newroles = request.getParameter("newroles");
			String oldroles = request.getParameter("oldroles");
			List<Role> rolesold = roleService.readRolesByUserId(userId);
			String[] ids = str.split(",");
			List<String> pageRole = Arrays.asList(ids);
			Map<String, String> RoidsMap = new HashMap<String, String>();
			List<String> roleIDlists = new ArrayList<String>();

			for (Role p : rolesold) {
				roleIDlists.add(p.getId());
			}
			if (str != null && !"".equals(str)) {

				List<Role> roles = new ArrayList<Role>();
				for (String id : ids) {
					if (!roleIDlists.contains(id)) {
						Role role = new Role();
						role.setId(id);
						roles.add(role);
						permissionService.grantUserRole(userId, roles);
					}
				}
				List<Role> rolesnew = roleService.readRolesByUserId(userId);
				for (Role role : rolesnew) {
					if (!pageRole.contains(role.getId())) {
						RoidsMap.put("userId", userId);
						RoidsMap.put("roleId", role.getId());
						permissionService.deleteUserRoleID(RoidsMap);
					}

				}

			}
			User userold=userService.read(userId);
			String  mobileNumber = userold.getMobileNumber();
			operaRecordService.insertRecord("用户角色编辑", getLoginUser.getUserId(),
					mobileNumber);

			User loginuserId = userService.read(GetLoginUser(request, response).getUserId());
			StringBuffer content =new StringBuffer("");
			content.append("当前用户:用户编号"+loginuserId.getUserId()+"用户真实姓名"+loginuserId.getRealname()+"修改了用户:用户编号"+userold.getUserId()+"用户真实姓名"+userold.getRealname()+"的角色，修改前角色为:"+oldroles+"修改后角色为:"+newroles);
			if(!newroles.equals(oldroles)){
				
				log.infoLog("管理员操作记录", content.toString(), 1,"zhongkeke@duanrong.com");
				
			}
			request.getSession().removeAttribute("menu");
			request.getSession().removeAttribute("permission");
			response.getWriter().print("ok");

		} catch (Exception e) {
			try {
				response.getWriter().print(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.edit().Exception",
					e);
		}

	}

	/**
	 * 
	 * @description 修改
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/getusers", method = RequestMethod.POST)
	public void getusers(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {

			User user = new User();
			String accountName = request.getParameter("username");
			packageParam(accountName, user);
			List<User> users = userService.readUsers(user);
			for (User u : users) {
				if (user != null) {
					UserAccount userAccount = userAccountService.readUserAccount(u.getUserId());
					u.setBalance(userAccount.getAvailableBalance());
					u.setFrozenMoney(userAccount.getFreezeAmount());
				}
			}

			JSONArray jsonArr = JSONArray.fromObject(users);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(jsonArr.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.getusers().Exception",
					e);
		}
	}

	/**
	 * 对参数进行封装
	 * 
	 * @param accountName
	 * @param user
	 */
	public void packageParam(String accountName, User user) {
		if (StringUtils.isNumeric(accountName)
				&& StringUtils.length(accountName) == 11) {
			user.setMobileNumber(accountName);
		} else if (MyStringUtils.isChineseName(accountName)) {
			user.setRealname(accountName);
		} else {
			user.setUserId(accountName);
		}
	}

	/**
	 * @author xiao
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/config/configList")
	public String getSysConfigView(HttpServletRequest request,
			HttpServletResponse response) {
		int pageNo = MyStringUtils.isNumeric(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 0;
		Config config = new Config();
		config.setId(request.getParameter("id"));
		config.setName(request.getParameter("name"));
		config.setType(request.getParameter("type"));
		config = (Config) FormUtil.getForParamToBean(config);
		PageInfo<Config> pageInfo = userService.readConfigForPageLite(pageNo,
				10, config);
		request.setAttribute("url", "/user/config/configList");
		request.setAttribute("str", FormUtil.getForParam(config));
		request.setAttribute("pageInfo", pageInfo);
		return "user/config/configList";
	}

	/**
	 * @author xiao
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/config/{type}")
	public String toOprateConfig(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String type)
			throws IOException {
		if (type.equals("toUpdate")) {
			Config config = userService.readConfigById(request
					.getParameter("id"));
			request.setAttribute("config", config);
			return "user/config/updateConfig";
		}
		if (type.equals("toInsert")) {
			return "user/config/insertConfig";
		}
		return forward + "/user/config/configList";
	}

	/**
	 * @author xiao
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/config/oprate/{type}")
	public void oprateConfig(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String type)
			throws IOException {
		Config config = new Config();
		config.setId(request.getParameter("id"));
		config.setName(request.getParameter("name"));
		config.setValue(request.getParameter("value"));
		config.setDescription(request.getParameter("description"));
		config.setType(request.getParameter("type"));
		if (type.equals("update")) {
			if (userService.updateConfig(config)) {
				response.getWriter().print("ok");

			}
		} else if (type.equals("insert")) {
			if (userService.insertConfig(config)) {
				response.getWriter().print("ok");

			}
		}

	}

	@RequestMapping(value = "/permis/black/{type}")
	public String black(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type, Model model)
			throws IOException {
		return null;
	}

	/**
	 * 
	 * @description 未投资用户管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/nouserList")
	public String nouserList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				String str = "";
				String mobilberNum = request.getParameter("mnumber");
				String mnumber = getUserId(mobilberNum);
				String realname = request.getParameter("realname");
				String start = request.getParameter("start");
				String end = request.getParameter("end");
				String usertype=request.getParameter("usertype");
				String name = "";
				Map<String, Object> params = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(usertype) && usertype.equals("duanrongw")) {
					str += "&usertype=" + usertype;
					params.put("duanrongw", usertype);
				}
				if (StringUtils.isNotBlank(usertype) && usertype.equals("furen")) {
					str += "&usertype=" + usertype;
					params.put("furen", usertype);
				}
				if (StringUtils.isNotBlank(mnumber)) {
					str += "&mnumber=" + mnumber;
					params.put("mnumber", mnumber);
				}
				if (StringUtils.isNotBlank(usertype)&& !usertype.equals("furen") && !usertype.equals("duanrongw")) {
					params.put("usertype", usertype);
					str += "&usertype=" + usertype;
				}

				if (StringUtils.isNotBlank(realname)) {
					name = java.net.URLDecoder.decode(realname, "UTF-8");
					params.put("realname", name);
					str += "&realname=" + name;

				}
				if (StringUtils.isNotBlank(start)) {
					start=URLDecoder.decode(start);
					params.put("start", start);
					str += "&start=" + start;
				}
				if (StringUtils.isNotBlank(end)) {
					end=URLDecoder.decode(end);
					params.put("end", end);
					str += "&end=" + end;
				}
				params.put("nouser", "nouser");
				params.put("type", "r.register_time");
				params.put("ordertype", "desc");
				PageInfo<User> pageInfo = userService.readPageInfo(
						Integer.parseInt(pageNo), 30, params);
				model.addAttribute("url", "/user/nouserList/");
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("mnumber", mobilberNum);
				model.addAttribute("start", start);
				model.addAttribute("end", end);
				model.addAttribute("realname", name);
				model.addAttribute("str", str);
				model.addAttribute("usertype", usertype);
				return "user/nouserList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.nouserList()",
					e);
		}
		return null;
	}
	/**
	 * 客户同步到Udesk系统
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/synchronizeUdesk")
	public void synchronizeUdesk(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {							
				String mobilberNum = request.getParameter("mnumber");
				String mnumber = getUserId(mobilberNum);
				String realname = request.getParameter("realname");
				String start = request.getParameter("start");
				String end = request.getParameter("end");
				String usertype=request.getParameter("usertype");
				String name = "";
				Map<String, Object> params = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(usertype) && usertype.equals("duanrongw")) {					
					params.put("duanrongw", usertype);
				}
				if (StringUtils.isNotBlank(usertype) && usertype.equals("furen")) {
					params.put("furen", usertype);
				}
				if (StringUtils.isNotBlank(mnumber)) {
					params.put("mnumber", mnumber);
				}
				if (StringUtils.isNotBlank(usertype)&& !usertype.equals("furen") && !usertype.equals("duanrongw")) {
					params.put("usertype", usertype);
				}
				if (StringUtils.isNotBlank(realname)) {
					name = java.net.URLDecoder.decode(realname, "UTF-8");
					params.put("realname", name);
				}
				if (StringUtils.isNotBlank(start)) {
					start=URLDecoder.decode(start);
					params.put("start", start);
				}
				if (StringUtils.isNotBlank(end)) {
					end=URLDecoder.decode(end);
					params.put("end", end);					
				}
				params.put("nouser", "nouser");
				params.put("type", "r.register_time");
				params.put("ordertype", "desc");
				List<User> users = userService.readUsers(params);
				JSONObject userpParam = new JSONObject();		
				JSONObject customerParams = new JSONObject();
				for (User user : users) {
						//把查询的用户信息插入udesk系统里面
				 if(StringUtils.isNotBlank(user.getMobileNumber())){
					userpParam.put("email", user.getEmail());
					if(StringUtils.isBlank(user.getRealname())){
						userpParam.put("nick_name", user.getMobileNumber());	
					}else{
						userpParam.put("nick_name", user.getRealname());
					}
					
					userpParam.put("cellphone",  user.getMobileNumber());
					userpParam.put("qq", user.getQq());
					userpParam.put("weixin_id", user.getWeixin());
		             String cousname=userService.readUdeskField("姓名");
					 String idcard=userService.readUdeskField("身份证号");
					 String mobileNum=userService.readUdeskField("手机号");
					 //String userType=userService.getUdeskField("用户类型");
					 String QQ=userService.readUdeskField("QQ");
					 String regiterTime=userService.readUdeskField("注册时间");
					 String remark=userService.readUdeskField("备注");
					 String offer=userService.readUdeskField("推荐人");
					 String manageroffer=userService.readUdeskField("经理推荐人");	
					 String resource=userService.readUdeskField("来源");
					 if(StringUtils.isNotBlank(cousname)){
				     customerParams.put(cousname, user.getRealname());
				     }
					 if(StringUtils.isNotBlank(idcard)){
					     customerParams.put(idcard, user.getIdCard());
					 }
					 if(StringUtils.isNotBlank(mobileNum)){
					     customerParams.put(mobileNum, user.getMobileNumber());
					}
					if(StringUtils.isNotBlank(QQ)){
					     customerParams.put(QQ, user.getQq());
					}
					if(StringUtils.isNotBlank(regiterTime)){
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");						
					    customerParams.put(regiterTime, df.format(user.getRegisterTime()));
					}
					if(StringUtils.isNotBlank(offer)){
					     customerParams.put(offer, user.getReferrer());
					}
					if(StringUtils.isNotBlank(manageroffer)){
					     customerParams.put(manageroffer, user.getOreferrer());
					}
					if(StringUtils.isNotBlank(resource)){
					     customerParams.put(resource, user.getUserOtherInfo().getUserSource());
					}
					if(StringUtils.isNotBlank(remark)){
					     customerParams.put(remark, "");
					}
					udeskService.udeskCustomerImport(customerParams, userpParam);	
				 }
				}
				response.getWriter().print("数据同步成功！<a href=\"/user/nouserList\" >返回</a>");
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.nouserList()",
					e);
			try {
				response.getWriter().print("数据同步失败！<a href=\"/user/nouserList\" >返回</a>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
	}

	/**
	 * add by wangjing
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/export")
	public void export(HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		String mnumber = request.getParameter("mnumber");
		String realname = request.getParameter("realname");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String name = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(mnumber)) {
			params.put("mnumber", mnumber);
		}
		if (StringUtils.isNotBlank(realname)) {
			name = java.net.URLDecoder.decode(realname, "UTF-8");
			params.put("realname", name);
		}
		if (StringUtils.isNotBlank(start)) {
			params.put("start", start);
		}
		if (StringUtils.isNotBlank(end)) {
			params.put("end", end + " 23:59:59");
		}
		params.put("nouser", "nouser");
		params.put("type", "r.register_time");
		params.put("ordertype", "desc");
		try {
			List<User> users = userService.readUsers(params);
			for (User user : users) {
				if(user.getUserInterior()!=null){
					if(user.getUserInterior().equals("duanrongw")){
						user.setUserInterior("内部员工");
					}else if(user.getUserInterior().equals("furen")){
						user.setUserInterior("辅仁员工");
					}else if(user.getUserInterior().equals("nelson")){
						user.setUserInterior("离职员工");
					}
				}
			}
			String t = "未投资用户信息";
			Map<String, String> title = new LinkedHashMap<>();
			title.put("realname", "姓名");
			title.put("mobileNumber", "手机号");
			title.put("userInterior","用户类型");
			title.put("idCard", "身份证号");
			title.put("qq", "QQ");
			title.put("email", "邮箱");
			title.put("registerTime", "注册时间");
			title.put("             ", "备注");
			title.put("referrer", "推荐人");
			title.put("userSource", "来源");
			title.put("oreferrer", "经理推荐人");
			int[] style = { 8, 16, 20,20, 18, 30, 30, 20, 20, 20, 30 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "未投资用户信息" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			excelConvertor.excelExport(users, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author wangjing
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "user/tormark")
	public void tormark(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserCookie getLoginUser = GetLoginUser(request, response);
		String source = request.getParameter("source");
		String p2p = request.getParameter("p2p");
		String notice = request.getParameter("notice");
		String qq = request.getParameter("qq");
		String remark = request.getParameter("remark");
		String id = request.getParameter("id");
		String weixin=request.getParameter("weixin");
		String visitType = request.getParameter("visitType");
		UserOtherInfo userOtherInfo = new UserOtherInfo();
		userOtherInfo.setId(id);
		userOtherInfo.setHasP2p(p2p);
		userOtherInfo.setNotice(notice);
		userOtherInfo.setVisitSource(source);

		try {
			userOtherInfoService.insertUpdate(userOtherInfo,weixin, qq, remark,visitType,
					getLoginUser.getUserId());
		} catch (Exception e) {
			response.getWriter().print("fail");
			e.printStackTrace();
		}
		response.getWriter().print("ok");
	}

	/**
	 * @author wangjing
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/rmarkList")
	public void rmarkList(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		
		UserVisitInfo userVisitInfo = new UserVisitInfo();
		String userid = request.getParameter("userid");
		UserOtherInfo userOtherInfo = userOtherInfoService.read(userid);
		User user = userService.read(userid);
		userVisitInfo.setUserid(userid);
		List<UserVisitInfo> list = userVisitInfoService
				.readAllRemarks(userVisitInfo);
		response.setCharacterEncoding("utf-8");
		Object[] array = new Object[3];
		array[0] = userOtherInfo;
		array[1] = list;
		array[2] = user;
		JSONArray jsonArr = JSONArray.fromObject(array);
		response.getWriter().print(jsonArr.toString());
	}

	/**
	 * 
	 * @description
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/noLoginList/{type}")
	public String noLoginList(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable String type) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				String str = "";
				String mnumber = request.getParameter("mnumber");
				String realname = request.getParameter("realname");
				String start = request.getParameter("start");
				String end = request.getParameter("end");
				String name = "";
				Map<String, Object> params = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(mnumber)) {
					str += "&mnumber=" + mnumber;
					params.put("mnumber", mnumber);
				}

				if (StringUtils.isNotBlank(realname)) {
					name = java.net.URLDecoder.decode(realname, "UTF-8");
					params.put("realname", name);
					str += "&realname=" + name;

				}
				if (StringUtils.isNotBlank(start)) {
					params.put("start", start);
					str += "&start=" + start;
				}
				if (StringUtils.isNotBlank(end)) {
					String endtime = DateUtil.addDay(end, 1);
					params.put("end", endtime);
					str += "&end=" + end;
				}
				if (type.equals("search")) {
					params.put("type", "r.register_time");
					params.put("ordertype", "desc");
					model.addAttribute("flag1", -1);
					model.addAttribute("flag2", -1);
				}

				if (type.equals("moneyasc")) {
					params.put("type", "r.invest_money_total");
					params.put("ordertype", "asc");
					model.addAttribute("flag1", 0);
					model.addAttribute("flag2", -1);
				}
				if (type.equals("moneydesc")) {
					params.put("type", "r.invest_money_total");
					params.put("ordertype", "desc");
					model.addAttribute("flag1", 1);
					model.addAttribute("flag2", -1);
				}
				if (type.equals("investasc")) {
					params.put("type", "investnum");
					params.put("ordertype", "asc");
					model.addAttribute("flag2", 0);
					model.addAttribute("flag1", -1);
				}
				if (type.equals("investdesc")) {
					params.put("type", "investnum");
					params.put("ordertype", "desc");
					model.addAttribute("flag2", 1);
					model.addAttribute("flag1", -1);
				}
				params.put("time", "time");
				PageInfo<User> pageInfo = userService.readPageInfo(
						Integer.parseInt(pageNo), 30, params);
				model.addAttribute("url", "/user/noLoginList/" + type);
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("mnumber", mnumber);
				model.addAttribute("start", start);
				model.addAttribute("end", end);
				model.addAttribute("realname", name);
				model.addAttribute("str", str);
				return "user/nologinList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.nologinList()",
					e);
		}
		return null;
	}

	/**
	 * @author wangjing
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/delrmark")
	public void delrmark(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		try {
			userVisitInfoService.delete(id);
		} catch (Exception e) {
			response.getWriter().print("fail");
			e.printStackTrace();
		}
		response.getWriter().print("ok");
	}

	/**
	 * 
	 * @description 用户管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/referrer")
	public String referrer(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {

			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String mnumber = request.getParameter("mnumber");
			String realname = request.getParameter("realname");
			List<User> users = null;
			Map<String, Object> params = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(mnumber)) {
				params.put("mnumber", mnumber);
				params.put("type", "r.register_time");
				params.put("ordertype", "desc");
				params.put("realname", realname);
				PageInfo<User> pageInfo = userService.readPageInfo(
						Integer.parseInt(pageNo), 30, params);
				if (pageInfo != null) {
					users = pageInfo.getResults();
				}
			}
			model.addAttribute("mnumber", mnumber);
			model.addAttribute("url", "/user/referrer");
			model.addAttribute("users", users);
			model.addAttribute("realname", realname);
			return "user/referrerList";

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.referrer()",
					e);
		}
		return null;
	}

	
	
	/**
	 * 
	 * @description 用户管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/interior")
	public String interior(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			String pageNo = request.getParameter("pageNo");
			if (MyStringUtils.isAnyBlank(pageNo)) {
				pageNo = "1";
			}
			String mnumber = request.getParameter("mnumber");		
			List<User> users = null;
			Map<String, Object> params = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(mnumber)) {
				params.put("mnumber", mnumber);
				params.put("type", "r.register_time");
				params.put("ordertype", "desc");
				PageInfo<User> pageInfo = userService.readPageInfo(
						Integer.parseInt(pageNo), 30, params);
				if (pageInfo != null) {
					users = pageInfo.getResults();
				}
			}
			model.addAttribute("mnumber", mnumber);
			model.addAttribute("url", "/user/referrer");
			model.addAttribute("users", users);
			return "user/interior";

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.referrer()",
					e);
		}
		return null;
	}
	
	/**
	 * 
	 * @description 用户管理列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/editreferrer")
	public String editreferrer(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String register = request.getParameter("registerTime");
			Date registerTime = df.parse(register);
			String mnumber = request.getParameter("mnumber");
			String referrer = request.getParameter("referrer");
			String type = request.getParameter("type");
			String userId = request.getParameter("userId");
			long registerTimes = registerTime.getTime();

			// 当前时间的毫秒数
			Long nowTime = new Date().getTime();
			long date3 = nowTime - registerTimes;
			double days = Math.floor(date3 / (24 * 3600 * 1000));
			if (days > 15) {
				return "error";
			}
			User u = userService.read(userId);
			if(u.getUserInterior()!=null){
				if(u.getUserInterior().equals("duanrongw")){
					return "interior";
				}else if(u.getUserInterior().equals("furen")){
					return "furen";
				}else if(u.getUserInterior().equals("nelson")){
					return "nelson";
				}
				
			}
			User user = new User();
			user.setUserId(userId);
			user.setMobileNumber(mnumber);
			user.setOreferrer(type + referrer);
			userService.updateUser(user);
			operaRecordService.insertRecord("修改推荐人", getLoginUser.getUserId(),
					"给" + userId + "增加推荐人" + referrer + "的操作");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.editreferrer()",
					e);
		}
		return null;
	}

	/**
	 * 
	 * @description 查询管理用户列表
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/adminList")
	public String adminList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			UserCookie getLoginUser = GetLoginUser(request, response);
			if (getLoginUser == null) {
				return "user/login";
			} else {
				String pageNo = request.getParameter("pageNo");
				if (MyStringUtils.isAnyBlank(pageNo)) {
					pageNo = "1";
				}
				String str = "";

				PageInfo<User> pageInfo = userService.readAdminUsers(
						Integer.parseInt(pageNo), 30);
				model.addAttribute("url", "/user/adminList");
				model.addAttribute("pageInfo", pageInfo);
				model.addAttribute("str", str);
				return "user/adminList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.adminList()",
					e);
		}
		return null;
	}

	/**
	 * 跳转至列表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/loginLoglist")
	public String loginLogList(HttpServletRequest request,
			HttpServletResponse response) {
		int pageNo = 0;
		int pageSize = 30;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		if (MyStringUtils.isNumeric(request.getParameter("pageSize"))) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		String loginIp = request.getParameter("loginIp");
		String isSuccess = request.getParameter("isSuccess");
		String userId = request.getParameter("userId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if (startTime != null) {
			startTime = startTime.replace("%20", " ");
		}
		;
		if (endTime != null) {
			endTime = endTime.replace("%20", " ");
		}
		;

		UserLoginLog log = new UserLoginLog();
		log.setLoginIp(loginIp);
		log.setUserId(userId);
		log.setIsSuccess(isSuccess);
		log.setStartTime(startTime);
		log.setEndTime(endTime);

		PageInfo<UserLoginLog> pageInfo = this.userLoginLogService.readPageLite(
				pageNo, pageSize, log);
		List<UserLoginLog> list = pageInfo.getResults();

		pageInfo.setResults(list);
		request.setAttribute("url", "/user/loginLoglist");
		request.setAttribute("str", FormUtil.getForParam(log));
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("loginIp", loginIp);
		request.setAttribute("isSuccess", isSuccess);
		request.setAttribute("userId", userId);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		return "/user/loginLoglist";
	}

	/**
	 *
	 * @description 更新手机归属地
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/updatephone")
	public String updatephone(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			return "user/updatephone";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.updatephone()",
					e);
		}
		return null;
	}

	@RequestMapping(value = "/user/phonecity", method = RequestMethod.POST)
	public void phonecity(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			int pageNo = 1;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("phoneno", "phoneno");
			params.put("type", "r.register_time");
			params.put("ordertype", "desc");
			PageInfo<User> pageInfo = userService.readPageInfo(pageNo, 200,
					params);
			List<User> users = pageInfo.getResults();
			userService.updateList(users);
			response.getWriter().print("ok");
		} catch (Exception e) {
			try {
				response.getWriter().print("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.UserController.updatephone()",
					e);
		}

	}

	@RequestMapping(value = "/bankcard/{type}")
	public String toAddCard(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable String type)
			throws UnsupportedEncodingException {
		// 判断用户是否登录
		if ("toedit".equals(type)) {
			String id = request.getParameter("id");
			id = new String(id.getBytes("ISO8859-1"), "UTF-8");
			BankCard bankcard = bankCardService.read(id);
			model.addAttribute("bankcard", bankcard);
			model.addAttribute("addUrl", "/bankcard/save/editCard");
		} else {
			model.addAttribute("addUrl", "/bankcard/save/saveCard");
			model.addAttribute("bankcard", new BankCard());
		}
		return "user/bankcard/addcard";
	}

	@RequestMapping(value = "/bankcard/save/{type}", method = RequestMethod.POST)
	public void addCard(HttpServletRequest request,
			HttpServletResponse response, BankCard bankcard, Model model,
			@PathVariable String type) {
		try {
			String userId = request.getParameter("userId");
			if (userId == null || userId.equals("")) {
				response.getWriter().print("isnull");
			} else {
				User user = userService.read(userId);
				if (user == null) {
					response.getWriter().print("exist");
				} else {

					bankcard.setName(request.getParameter("name"));
					bankcard.setBank(request.getParameter("bank"));
					String paymentId = request.getParameter("paymentId");
					String cardNo = request.getParameter("cardNo");
					if("JDpay".equals(paymentId)&&StringUtils.isNotBlank(cardNo)){
						cardNo = AESUtil.encode(cardNo);
					}
					bankcard.setStatus(request.getParameter("status"));
					bankcard.setCardNo(cardNo);
					if ("editCard".equals(type)) {
						bankcard.setId(request.getParameter("id"));
						bankCardService.update(bankcard);
					} else {
						bankcard.setId(IdGenerator.randomUUID());
						bankcard.setUserId(userId);
						bankCardService.insert(bankcard);
					}
					response.getWriter().print("ok");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/bankcard/del")
	public String todelCard(HttpServletRequest request,
			HttpServletResponse response, Model model)
			throws UnsupportedEncodingException {
		// 判断用户是否登录
		BankCard bankcard = new BankCard();
		bankcard.setId(request.getParameter("id"));
		bankcard.setDeleteBankCard("delete");
		bankcard.setCancelBandDingTime(DateUtil.DateToString(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		bankcard.setCancelStatus("取消绑卡成功");
		bankCardService.update(bankcard);
		return "redirect:/bankcard/bankcardList";
	}

	@RequestMapping(value = "/user/userExport/{type}")
	public void userExport(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type, Model model)
			throws IOException {
		String mnumber = request.getParameter("mnumber");
		String realname = request.getParameter("realname");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String rstart = request.getParameter("rstart");
		String rend = request.getParameter("rend");
		String tstart = request.getParameter("tstart");
		String tend = request.getParameter("tend");
		String userId = request.getParameter("userId");
		String email = request.getParameter("email");
		String card = request.getParameter("card");
		String maxMoney = request.getParameter("maxMoney");
		String minMoney = request.getParameter("minMoney");
		String referrer = request.getParameter("referrer");
		String source = request.getParameter("source");
		String sooner = request.getParameter("sooner");
		String salesman = request.getParameter("salesman");
		String userIp = request.getParameter("userIp");
		String usertype = request.getParameter("usertype");
		String ids = request.getParameter("ids");
		String name = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(mnumber)) {
			params.put("mnumber", mnumber);
		}
		if (StringUtils.isNotBlank(userId)) {
			params.put("userId", userId);
		}
		if (StringUtils.isNotBlank(email)) {
			params.put("email", email);
		}
		if (StringUtils.isNotBlank(userIp)) {
			params.put("userIp", userIp);
		}
		if (StringUtils.isNotBlank(ids)) {
			String[] st1 = ids.split(",");
			params.put("ids", st1);
		}

		if (StringUtils.isNotBlank(sooner)) {
			params.put("sooner", sooner);
		}
		if (StringUtils.isNotBlank(salesman)) {
			String sa = java.net.URLDecoder.decode(salesman, "UTF-8");
			String[] st = sa.split(",");
			params.put("salesman", st);

		}
		if (StringUtils.isNotBlank(card)) {
			params.put("card", card);
		}
		if (StringUtils.isNotBlank(minMoney)) {
			double min = Double.parseDouble(minMoney);
			params.put("minMoney", min);
		}
		if (StringUtils.isNotBlank(maxMoney)) {
			double max = Double.parseDouble(maxMoney);
			params.put("maxMoney", max);
		}
		if (StringUtils.isNotBlank(realname)) {
			name = java.net.URLDecoder.decode(realname, "UTF-8");
			params.put("realname", name);
		}
		if (StringUtils.isNotBlank(start)) {
			params.put("start", start);
		}
		if (StringUtils.isNotBlank(end)) {
			params.put("end", end + " 23:59:59");
		}
		if (StringUtils.isNotBlank(referrer)) {
			params.put("referrer", referrer);
		}
		if (StringUtils.isNotBlank(source)) {
			params.put("source", source);
		}
		if (StringUtils.isNotBlank(usertype)) {
			params.put("usertype", usertype);
		}
		if (StringUtils.isNotBlank(rstart)) {
			params.put("rstart", rstart);
		}

		if (StringUtils.isNotBlank(rend)) {
			params.put("rend", DateUtil.addDay(rend, 1));
		}
		if (StringUtils.isNotBlank(tstart)) {
			params.put("tstart", tstart);
		}
		if (StringUtils.isNotBlank(tend)) {
			params.put("tend", DateUtil.addDay(tend, 1));
		}
		params.put("notmobile", "notmobile");
		String t = "";
		if (type.equals("nosearch")) {
			params.put("type", "r.register_time");
			params.put("ordertype", "desc");
			params.put("time", "time");
			t = "三个月以上未登录用户";
		}
		if (type.equals("customer")) {
			params.put("type", "r.register_time");
			params.put("ordertype", "desc");
			params.put("customer", "customer");
			t = "大客户列表";
		}

		try {
			List<User> loans = userService.readExportList(params);
			Map<String, String> title = new LinkedHashMap<>();
			title.put("realname", "姓名");
			title.put("mobileNumber", "手机号");
			title.put("registerTime", " 注册时间");
			title.put("investMoneyTotal", "投资总额");
			title.put("investNum", "投资次数");
			title.put("oreferrer", "经理推荐人");
			title.put("userRemark", "备注信息");

			int[] style = { 15, 15, 30, 15, 15, 20, 50 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = t + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			excelConvertor.excelExport(loans, title, t, style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户投资确认书查询页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "user/toUserInvestCheck")
	public String toUserInvestCheck(HttpServletRequest request, Model model) {
		int pageNo = 0;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		PageInfo<UserInvestCheck> pageInfo = userService.readUserInvestCheck(
				pageNo, getPageSize());
		model.addAttribute("pageInfo", pageInfo);
		return "user/userInvestCheck";
	}

	/**
	 * 根据时间查询投资的用户
	 */
	@RequestMapping(value = "user/userInvestCheckByTime")
	public String toUserInvestCheck2(HttpServletRequest request, Model model) {
		int pageNo = 0;
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		if (start != null) {
			start = URLDecoder.decode(start);			
		}
		if (end != null) {
			end = URLDecoder.decode(end);			
		}

		PageInfo<UserInvestCheck> pageInfo = userService
				.readUserInvestCheckByTime(pageNo, getPageSize(), start, end);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("start", start);
		return "user/userInvestCheck2";
	}

	/**
	 * 去用户信息添加页面
	 */

	@RequestMapping(value = "user/toAddUserInfo")
	public String toAddUserInfo() {
		return "user/createInvestUser";
	}

	/**
	 * addUserInfo
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "user/addUserInfo")
	public String addUserInfo(HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<>();
		String mobileNumber = request.getParameter("mobileNumber").trim();
		String userId = getUserId(mobileNumber);
		params.put("id", IdGenerator.randomUUID());
		params.put("userId", userId);
		params.put("isDel", 1);
		int flag = userService.insertUserInfoCheck(params);
		if (flag == 1) {
			return "success";
		} else {
			return "error";
		}

	}

	@ResponseBody
	@RequestMapping(value = "user/subUserInfo")
	public String subUserInfo(HttpServletRequest request) {
		String id = request.getParameter("uuid");
		int flag = userService.delUserinfo(id);
		if (flag == 1) {
			return "success";
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "user/exportNowUserInvest")
	public void exportNowUserInvest(
			@RequestParam(value = "userId", required = false) String userid,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		String start = request.getParameter("start");
		String end = request.getParameter("end");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String top = userService
				.readTemplate("invest_confirmation_for_user_top");
		String mod = userService
				.readTemplate("invest_confirmation_for_user_data");
		String bottom = userService.readTemplate("invest_confirmation_for_user");
		String userName = "";
		String userAddr = "";
		String investTime = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		NumberFormat formater = new DecimalFormat("###,###.##");

		String top1 = top;
		String userId = userid;
		// 存放导出时间
		List<UserInfoConfirmation> list = userService.readUserInfoConfirmation(
				userId, start, end);
		if (list.size() > 0) {
			String content = "";
			UserInfoConfirmation u = list.get(0);
			userName = u.getUserName();
			userAddr = u.getUser_addr();
			investTime = dateFormat.format(u.getInvestTime());
			if (userName != null) {
				top1 = top1.replace("#{userName}", userName);
			}
			if (userAddr != null) {
				String addr = userAddr;
				String addr1 = "";
				String addr2 = "";
				if (addr.length() > 18) {
					addr1 = addr.substring(0, 18);
					addr2 = addr.substring(addr.length() - 17, addr.length());
					top1 = top1.replace("#{addrees1}", addr1);
					top1 = top1.replace("#{addrees2}", addr2);
				} else {
					addr1 = addr;
					top1 = top1.replace("#{addrees1}", addr1);
					top1 = top1.replace("#{addrees2}", "");

				}

			}
			if (investTime != null) {
				top1 = top1.replace("#{investDate}", investTime);
			}

			content += top1;
			for (UserInfoConfirmation ua : list) {
				String mod1 = mod;
				mod1 = mod1.replace("#{loanName}", ua.getLoanName());
				mod1 = mod1.replace("#{money}",
						formater.format(ua.getInvestMoney()) + "元");
				String period = "";
				if (ua.getOperationType().equals("月")) {
					period = ua.getDeadline() + "个月";
				} else {
					period = ua.getDay() + "天";
				}
				mod1 = mod1.replace("#{period}", period);
				mod1 = mod1.replace("#{rate}", ua.getRate() * 100 + "%");
				mod1 = mod1.replace("#{interest}",
						formater.format(ua.getInterest()) + "元");
				content += mod1;
			}
			String bottom1 = bottom;
			bottom1 = bottom1.replace("#{date}", dateFormat.format(new Date()));
			content += bottom1;
			int num = (int) (Math.random() * 100);
			String fileNameDate = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmssS" + num);

			String fileName = "投资确认函" + fileNameDate + ".doc";
			try {
				response.setCharacterEncoding("utf-8");
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + fileName);
				InputStream is = new ByteArrayInputStream(
						content.getBytes("UTF-8"));
				OutputStream os = response.getOutputStream();
				POIFSFileSystem fs = new POIFSFileSystem();
				fs.createDocument(is, "WordDocument");
				fs.writeFilesystem(os);
				os.close();
				is.close();
				String exportTime = df.format(new Date());
				userService.updateExportTime(userId, exportTime);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		model.addAttribute("start", start);
		model.addAttribute("end", end);
	}

	/**
	 * 导出备注信息(新增投资用户的信息)
	 */

	@RequestMapping(value = "user/exportRemarkUserInfo")
	public void exportRemarkUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String start=request.getParameter("start");
			String end=request.getParameter("end");
			String remark=request.getParameter("remark");
			String remarkstart=request.getParameter("remarkstart");
			String remarkend=request.getParameter("remarkend");
			String remarkUserId=request.getParameter("remarkUserId");
			Map<String, Object> params=new HashMap<>();
			List<newInvestUser> list =null;
			if (start!=null) {
				start=URLDecoder.decode(start);
				if (StringUtils.isNotBlank(start)) {				
					params.put("start", start);
				}
			}
			if (end!=null) {
				end=URLDecoder.decode(end);
				if (StringUtils.isNotBlank(end)) {				
					params.put("end", end+" 23:59");
				}
			}
		
				if (StringUtils.isNotBlank(remark)) {										
						params.put("remark", remark);				
				}
			
			if (remarkstart!=null) {
				remarkstart=URLDecoder.decode(remarkstart);
				if (StringUtils.isNotBlank(remarkstart)) {
					params.put("remarkstart", remarkstart);
				}
			}
			if (remarkend!=null) {
				remarkend=URLDecoder.decode(remarkend);
				if (StringUtils.isNotBlank(remarkend)) {
					params.put("remarkend", remarkend+" 23:59");
					
				}
			}		
				if (StringUtils.isNotBlank(remarkUserId)) {
					if(!"all".equals(remarkUserId)){
					params.put("remarkUserId", remarkUserId);
					}
				}		
			
		    list = userService.readInvestUserInfo(params);
		    	
			List<UserRemarkInfo> infoList =  new ArrayList<>();
			UserVisitInfo userVisitInfo = new UserVisitInfo();
			for (newInvestUser user : list) {				
				UserRemarkInfo userRemark = new UserRemarkInfo();
				userRemark.setUserId(user.getId());
				userRemark.setRealName(user.getRealName());
				userRemark.setQQ(user.getQq());
				userRemark.setMobileNum(user.getMobileNumber());
				userRemark.setUserSource(user.getUserSource());
				userVisitInfo.setUserid(user.getId());
				List<UserVisitInfo> userVisit = userVisitInfoService
						.readAllRemarks(userVisitInfo);	
				if(userVisit!=null && userVisit.size()>0)
				for (int i = 0; i < 1; i++) {			
					userRemark.setRemark(userVisit.get(0).getRemark());
					userRemark.setTime(userVisit.get(0).getTime());
					userRemark.setAdmin(userVisit.get(0).getRealname());										
				}
				infoList.add(userRemark);
			}
			
			Map<String, String> title = new LinkedHashMap<>();
			title.put("userId", "用户编号");
			title.put("realName", "用户姓名");
			title.put("mobileNum", "手机号");
			title.put("QQ", "QQ");
			title.put("userSource", "用户来源");
			title.put("remark", "备注信息");
			title.put("admin", "备注人");
			title.put("time", "备注时间");
			int[] style = { 16, 8,16, 16, 10, 22, 16, 22 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "用户备注信息" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			String t = "用户备注信息";
			excelConvertor.excelExport(infoList, title, t, style);

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.userbillListExport()",
					e);
		}

	}

	@RequestMapping(value = "/user/operInterior")
	@ResponseBody
	public String operInterior(HttpServletRequest request,
			HttpServletResponse response) {
		UserCookie getLoginUser = GetLoginUser(request, response);
		String id = request.getParameter("userId");
		String interior = request.getParameter("userInterior");
		operaRecordService.insertRecord("用户类型编辑", getLoginUser.getUserId(),
				"操作员：" + getLoginUser.getUserId() + ",编辑[" + id + "] 为 "
						+ interior + " 类型");

		User user = new User();
		user.setUserId(id);
		user.setUserInterior(interior);
		try {
			userService.update(user);
			return "ok";
		} catch (Exception e) {

			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 辅仁用户投资明细
	 */
	@RequestMapping(value="userInvest/InvestByFuRen")
	public String InvestInfoByFuRen(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String pageNo = request.getParameter("pageNo");
		String pageSize = "30";
		if (pageNo == null) {
			pageNo = "1";
		}
		String userId=request.getParameter("userId");
		
		Map<String,Object>params=new HashMap<String, Object>();
		if (userId != null && !userId.equals("")) {
			params.put("userId",getUserId(userId));
		}
		PageInfo<UserByFuRen> pageInfo =userService.readInvestInfoByFuRen(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize), params);
		
		List<UserByFuRen> list=pageInfo.getResults();
		for (UserByFuRen userByFuRen : list) {
			double demandInMoney=userByFuRen.getDemandInMoney();
			double demandOutMoney=userByFuRen.getDemandOutMoney();
			userByFuRen.setDemandMoney(demandInMoney-demandOutMoney);
		}
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("userId", userId);
		return "user/investInfoByFuRen";
	}
	
	/**
	 *  导出辅仁用户投资
	 */
	@RequestMapping(value="user/InvestByFuRen")
	public void exportInvestByFuRen(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<UserByFuRen> list = userService.readExportInvestByFuRen();
			for (UserByFuRen userByFuRen : list) {
				double demandInMoney=userByFuRen.getDemandInMoney();
				double demandOutMoney=userByFuRen.getDemandOutMoney();
				userByFuRen.setDemandMoney(demandInMoney-demandOutMoney);
			}
			
			Map<String, String> title = new LinkedHashMap<>();
			
			title.put("id", "用户编号");
			title.put("realName", "用户姓名");
			title.put("mobileNumber", "手机号");
			title.put("age", "年龄");
			title.put("sex", "性别");
			title.put("investIngmoney", "定期在投金额");
			title.put("sumMoney", "定期投资总额");
			title.put("demandMoney", "活期宝本金");
			int[] style = { 16, 8, 16, 10, 22, 16, 22,20 };
			String fileName = DateUtil.DateToString(new Date(),
					"yyyyMMddHHmmss");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			fileName = "辅仁用户投资信息" + fileName + ".xls";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			ExcelConvertor excelConvertor = new ExcelConvertor(
					response.getOutputStream(), fileName);
			String t = "辅仁用户投资信息";
			excelConvertor.excelExport(list, title, t, style);

		} catch (Exception e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.newadmin.controller.FoundController.userbillListExport()",
					e);
		}

	}
	
	
}