package com.duanrong.business.user.service.impl;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import util.PhoneNoAttributionUtils;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.user.UserConstants;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.Config;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserAccount;
import com.duanrong.business.user.model.UserByFuRen;
import com.duanrong.business.user.model.UserInfoConfirmation;
import com.duanrong.business.user.model.UserInvestCheck;
import com.duanrong.business.user.model.UserOtherInfo;
import com.duanrong.business.user.model.UserRemarkInfo;
import com.duanrong.business.user.model.newInvestUser;
import com.duanrong.business.user.service.AuthInfoService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.user.service.UserOtherInfoService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.jsonservice.util.RegexInput;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.udesk.service.UdeskService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.HashCrypt;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午5:45:05
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	UserDao userDao;

	@Resource
	AuthInfoService authInfoService;

	@Resource
	UserMoneyService userMoneyService;

	@Resource
	UserOtherInfoService userOtherInfoService;
	
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	Log log;
	
	@Resource
	UdeskService udeskService;

	/**
	 * 查询所管理员
	 */
	@Override
	public List<User> readAdmin(User user) {
		
		return userDao.queryAdmin(user);
	}
	
	
	/**
	 * 查询所有的用户
	 */
	public List<User> readAll() {
		List<User> userList = userDao.findAll();
		return userList;
	}

	/**
	 * 根据ID删除记录
	 * 
	 * @param id
	 */
	public void delete(String id) {
		userDao.delete(id);
	}
	
	

	/**
	 * 用户注册时验证某些字段是否已经存在 使用规则： 邮箱 isAlreadyExist("eamil","1234567@qq.com") 用户名
	 * isAlreadyExist("username","admin") 电话
	 * isAlreadyExist("mobileNum","13312343210")
	 */
	public boolean readExist(String fieldName, String value) {

		User user = new User();

		if (StringUtils.equals(fieldName, "email")) {
			user.setEmail(value);
		} else if (StringUtils.equals(fieldName, "username")) {
			user.setUserId(value);
		} else if (StringUtils.equals(fieldName, "mobileNum")) {
			user.setMobileNumber(value);
		}

		User user2 = userDao.verifyLogin(user);
		if (user2 != null) {
			return true;
		}

		return false;
	}

	public User readLoginUser(User user) {
		return userDao.verifyLogin(user);
	}

	public String register(User user) {
		// 注册时，用户ID是根据手机号进行生成的，所以无需校验用户ID
		String mobileNumber = user.getMobileNumber();
		String email = user.getEmail();
		String password = user.getPassword();

		if (MyStringUtils.isNotBlank(email)) {
			// 校验格式
			if (!MyStringUtils.RegisterRegex(mobileNumber, email, password)) {
				return null;
			}

			// 校验是否存在
			if (readExist("mobileNum", mobileNumber)
					|| readExist("email", email)) {
				return null;
			}
		} else {
			// 校验格式
			if (!(RegexInput.checkMobilePhone(mobileNumber) && RegexInput
					.checkPassword(password))) {
				return null;
			}

			// 校验是否存在
			if (readExist("mobileNum", mobileNumber)) {
				return null;
			}
		}

		// 密码加密
		password = HashCrypt.getDigestHash(user.getPassword());
		user.setPassword(password);

		// 用户名根据手机号在后面追加4位随机小写字母
		String userId = user.getMobileNumber()
				+ MyStringUtils.multipleLetter(4);
		user.setUserId(userId);
		user.setUsername(userId);

		user.setRegisterTime(new Date());
		user.setStatus(UserConstants.UserStatus.ENABLE);
		// 默认普通用户user
		if (StringUtils.isBlank(user.getUserType())) {
			user.setUserType("user");
		}

		userDao.insert(user);
		Role role1 = new Role("MEMBER");
		addRole(user.getUserId(), role1);
		Role role2 = new Role("LOANER");
		addRole(user.getUserId(), role2);

		// 更新注册用户数量
		userDao.updatePersonCount4Config();

		try {
			UserOtherInfo userOtherInfo = new UserOtherInfo();
			userOtherInfo.setId(userId);
			userOtherInfo.setUserSource(user.getUserSource());
			userOtherInfoService.insertOrUpdateNoUser(userOtherInfo);
		} catch (Exception ex) {
		}

		return userId;

	}

	/**
	 * 根据id获取用户
	 */
	public User read(String id) {
		return userDao.get(id);
	}

	/**
	 * 用户添加权限
	 */
	public void addRole(String userId, Role role) {
		// TODO:配置文件尚未实现
		userDao.addRole(userId, role.getId());
	}

	/**
	 * 保存一个用户 author:yinxunzhi time:2014-9-3下午4:15:52
	 * 
	 * @param user
	 */
	public void save(User user) {
		userDao.insert(user);
	}

	/**
	 * 
	 * @description 更新user表
	 * @author 孙铮
	 * @time 2014-9-10 上午11:12:44
	 * @param user
	 */
	public void update(User user, boolean updatePassword) {
		String userId = user.getUserId();
		if (StringUtils.isBlank(userId)) {
			return;
		}

		String password = user.getPassword();
		if (updatePassword) {
			if (StringUtils.isNotBlank(password)) {
				if (RegexInput.checkPassword(password)) {
					password = HashCrypt.getDigestHash(user.getPassword());
					user.setPassword(password);
				} else {
					user.setPassword(null);
				}

			}
		} else {
			user.setPassword(null);
		}

		String email = user.getEmail();

		if (StringUtils.isNotBlank(email)) {
			if (!RegexInput.checkEmail(email) || readExist("email", email)) {
				user.setEmail(null);
			}
		}

		String postAddress = user.getPostAddress();
		String postCode = user.getPostCode();

		if (StringUtils.isNotBlank(postAddress)
				|| StringUtils.isNotBlank(postCode)) {
			UserOtherInfo userOtherInfo = new UserOtherInfo();
			userOtherInfo.setId(userId);
			userOtherInfo.setPostCode(postCode);
			userOtherInfo.setPostAddress(postAddress);
			userOtherInfoService.insertOrUpdateNoUser(userOtherInfo);
		}

		userDao.update(user);
	}

	public String updatePassword(String userId, String password,
			String resetPassword) {
		User user = userDao.get(userId);
		if (user == null) {
			return "NU";
		} else if (!user.getPassword()
				.equals(HashCrypt.getDigestHash(password))) {
			return "RP";
		}
		if (RegexInput.checkPassword(resetPassword)) {
			User u = new User();
			u.setUserId(userId);
			resetPassword = HashCrypt.getDigestHash(resetPassword);
			u.setPassword(resetPassword);
			userDao.update(u);
			return "OK";
		}
		return "ER";

	}

	public User readUserByMobileNumber(String mobileNumber) {
		return userDao.getUserByMobileNumber(mobileNumber);
	}

	@Override
	public Long readPerson() {
		return Long.parseLong(userDao.getPerson2());
	}

	@Override
	public boolean readVerifyIdCard(String userId, String idCard) {
		return userDao.verifyIdCard(userId, idCard);
	}

	@Override
	public List<UserAccount> readAccountInfo(String userId, String realName,String start,String end) {
		List<UserAccount> list = new ArrayList<UserAccount>();
		User user = new User();
		user.setUserId(userId);
		user.setRealname(realName);
		List<User> users = readUsers(user);

		for (int i = 0; i < users.size(); i++) {
			UserAccount account = new UserAccount();
			account = getCallAccountInfo(users.get(i).getUserId(), account);
			double investingMoeny=userMoneyService.readInvestingMoeny(users.get(i).getUserId(),start,end);
			double bbalance = userMoneyService.readBalance(users.get(i)
					.getUserId());
			double bfreezeAmount = userMoneyService.readFrozenMoney(users.get(i)
					.getUserId());
			double bavailableAmount = bbalance + bfreezeAmount;
			//投资总额
			double investMoney=userMoneyService.readInvestMoney(users.get(i).getUserId(),start,end);
			//当前投资总额
			account.setNewMoney(investingMoeny);
			account.setMoney(investMoney);
			account.setBbalance(ArithUtil.round(bavailableAmount, 2));
			account.setBfreezeAmount(bfreezeAmount);
			account.setBavailableAmount(ArithUtil.round(bbalance, 2));
			account.setUserid(users.get(i).getUserId());
			account.setMobile(users.get(i).getMobileNumber());
			account.setRealname(users.get(i).getRealname());		
			account.setPayMnetId(userMoneyService.readPayMnetIdBybankByUser(account.getCardNo(),account.getUserid()));
			account.setUser(users.get(i));
			if (account != null) {
				list.add(account);
			}
		}

		return list;
	}

	@Override
	public List<User> readUserByRealname(String realname) {
		return userDao.getUserByRealname(realname);
	}

	/**
	 * @author wangjing
	 * @param userNo
	 * @return
	 */
	public UserAccount getCallAccountInfo(String userNo, UserAccount account) {

		HttpClient client = new HttpClient();
		// 创建一个post方法
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		content.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
		content.append("<platformUserNo>" + userNo + "</platformUserNo>");
		content.append("</request>");
		// 生成密文
		String sign = CFCASignUtil.sign(content.toString());
		log.infoLog("生成密文", sign);
		postMethod.setParameter("req", content.toString());
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "ACCOUNT_INFO");

		// 执行post方法
		try {
			int statusCode = client.executeMethod(postMethod);
			log.infoLog("生成密文statusCode", statusCode + "");

			byte[] responseBody = postMethod.getResponseBody();

			log.infoLog("responseBody.toString()", responseBody.toString());
			// 响应信息
			String respInfo = new String(new String(responseBody, "UTF-8"));
			@SuppressWarnings("unused")
			Document respXML = DocumentHelper.parseText(respInfo);
			@SuppressWarnings("unchecked")
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(new String(
					responseBody, "UTF-8"));

			String code = resultMap.get("code");
			String description = resultMap.get("description");
			String balance = resultMap.get("balance");
			String availableAmount = resultMap.get("availableAmount");
			String freezeAmount = resultMap.get("freezeAmount");
			String cardNo = resultMap.get("cardNo");
			String cardStatus = resultMap.get("cardStatus");
			String bank = resultMap.get("bank");
			String autoTender = resultMap.get("autoTender");
			String activeStatus = resultMap.get("activeStatus");
			String memberType = resultMap.get("memberType");
			String paySwift = resultMap.get("paySwift");
			account.setAutoTender(autoTender);
			if (availableAmount != null && !"".equals(availableAmount)) {
				account.setAvailableAmount(Double.parseDouble(availableAmount));
			}
			if (balance != null && !"".equals(balance)) {
				account.setBalance(Double.parseDouble(balance));
			}
			if(bank != null && !bank.equals("")){
				account.setBank(bank);
			}
			if(cardNo != null && !cardNo.equals("")){
				account.setCardNo(cardNo.substring(0,4)+"********"+cardNo.substring(cardNo.length()-4));
			}
			if(cardStatus != null && !cardStatus.equals("")){
				account.setCardStatus(cardStatus);
			}
			if(paySwift != null && !paySwift.equals("")){
				account.setPaySwift(paySwift);
			}					
			account.setCode(code);
			account.setDescription(description);
			account.setActiveStatus(activeStatus);
			account.setMemberType(memberType);			
			if (freezeAmount != null && !"".equals(freezeAmount)) {
				account.setFreezeAmount(Double.parseDouble(freezeAmount));
			}
			return account;

		} catch (HttpException e) {
			log.errLog(
					"com.duanrong.business.user.service.impl.UserServiceImpl.getCallAccountInfo().Exception",
					e);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.business.user.service.impl.UserServiceImpl.getCallAccountInfo().Exception",
					e);
		} catch (DocumentException e) {
			e.printStackTrace();
			log.errLog(
					"com.duanrong.business.user.service.impl.UserServiceImpl.getCallAccountInfo().Exception",
					e);
		} finally {
			postMethod.releaseConnection();
		}
		// return resultMap;
		return null;
	}

	@Override
	public Long readTheNumberOfRegisteredEveryDay(Map<String, Object> params) {
		return userDao.getTheNumberOfRegisteredEveryDay(params);
	}

	@Override
	public PageInfo<User> readPageInfo(int pageNo, int pageSize, Map map) {
		// TODO Auto-generated method stub
		return userDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public PageInfo<User> readPageLite(int pageNo, int pageSize, User user) {
		return userDao.pageLite(pageNo, pageSize, user);
	}

	@Override
	public void updateUser(User user) throws Exception {
		String userId = user.getUserId();
		if (StringUtils.isBlank(userId)) {
			throw new Exception("用户不存在！");
		}
		if (StringUtils.isNoneBlank(user.getMobileNumber())) {
			// 校验格式
			if (!(RegexInput.checkMobilePhone(user.getMobileNumber()))) {
				throw new Exception("手机号格式不正确！");
			}
			// 校验是否存在
			if (readExistMobileNumber(user)) {
				throw new Exception("手机号已经存在！");
			}
			String[] strs = PhoneNoAttributionUtils.getAttributions(user
					.getMobileNumber());
			//如果用户手机号没有所在省市，则通过程序去判断，若有则以前台传过来的为准
			if (strs[0] != null) {
				if(user.getPhoneNoAttribution()==null){
				user.setPhoneNoAttribution(strs[0]);
				}
			}
			if (strs[0] != null) {
				if(user.getPhoneNoCity()==null){
				user.setPhoneNoCity(strs[1]);
				}
			}
			
		}

		userDao.update(user);
	}

	@Override
	public void update(User user) throws Exception {
		String userId = user.getUserId();
		if (StringUtils.isBlank(userId)) {
			throw new Exception("UserService.class updateUser : 用户不存在！");
		}
		userDao.update(user);
	}

	public boolean readExistMobileNumber(User user) {

		int num = userDao.verifyUser(user);
		if (num != 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<User> readUsers(User user) {
		return userDao.getUsers(user);
	}

	@Override
	public Template readTemplateById(String id) {
		return userDao.getTemplateById(id);
	}

	@Override
	public PageInfo<Config> readConfigForPageLite(int pageNo, int pageSize,
			Config config) {
		return userDao.getConfigForPageLite(pageNo, pageSize, config);
	}

	@Override
	public Config readConfigById(String id) {

		return userDao.getConfigById(id);
	}

	@Override
	public boolean updateConfig(Config config) {
		if (userDao.getConfigById(config.getId()) != null)
			return userDao.updateConfig(config) > 0;
		return false;
	}

	@Override
	public boolean insertConfig(Config config) {
		if (userDao.getConfigById(config.getId()) == null)
			return userDao.insertConfig(config) > 0;
		return false;
	}

	@Override
	public User readUserByMail(String email) {

		return userDao.getUserByMail(email);
	}

	@Override
	public List<User> readUserNum(Map map) {
		return userDao.getUserNum(map);
	}

	/**
	 * add by wangjing
	 */
	@Override
	public List<User> readUsers(Map<String, Object> map) {
		return userDao.findUsers(map);
	}

	@Override
	public List<User> readUserTotalMoney(Map map) {
		return userDao.getUserTotalMoney(map);
	}

	@Override
	public Long readTheNumberOfAccountEveryDay(Map<String, Object> params) {
		return userDao.getTheNumberOfAccountEveryDay(params);
	}

	@Override
	public PageInfo<User> readAdminUsers(int pageNo, int pageSize) {
		return userDao.findAdminUsers(pageNo, pageSize);
	}

	@Override
	public void updateList(List<User> users) {
		for (User user : users) {

			if (!StringUtils.isBlank(user.getMobileNumber())) {

				try {
					String[] strs = PhoneNoAttributionUtils
							.getAttributions(user.getMobileNumber());
					if (strs[0] != null) {
						user.setPhoneNoAttribution(strs[0]);
					}
					if (strs[1] != null) {
						user.setPhoneNoCity(strs[1]);
					}
					if (StringUtils.isBlank(user.getPhoneNoAttribution())
							&& StringUtils.isBlank(user.getPhoneNoCity())) {
						continue;
					} else {
						userDao.updateUserPhoneArea(user);
					}
				} catch (Exception e) {
					log.errLog("更新用户手机号码归属地报错" + user.getMobileNumber(), e);
					continue;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	@Override
	public void updateUsers() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phoneno", "phoneno");
		params.put("type", "r.register_time");
		params.put("ordertype", "desc");
		List<User> users = userDao.getUsersPhone(params);
		if (users != null) {
			log.infoLog("手机号归属地调度开始查询用户数：", users.size() + "");
		}
		int count = 0;
		for (User user : users) {
			try {
				if (!StringUtils.isBlank(user.getMobileNumber())
						&& (RegexInput.checkMobilePhone(user.getMobileNumber()))) {
					String[] strs = PhoneNoAttributionUtils
							.getAttributions(user.getMobileNumber());
					if (strs[0] != null) {
						user.setPhoneNoAttribution(strs[0]);
					}
					if (strs[1] != null) {
						user.setPhoneNoCity(strs[1]);
					}
					if (StringUtils.isBlank(strs[0])
							&& StringUtils.isBlank(strs[1])) {
						continue;
					} else {
						userDao.updateUserPhoneArea(user);
						count++;
					}
				}
				Thread.sleep(100);
			} catch (Exception e) {
				log.errLog("手机号归属地调度执行", e);
			}
		}
		log.infoLog("手机号归属地调度执行完毕后更新数量：", count + "");

	}

	@Override
	public List<User> readExportList(Map map) {
		return userDao.exportList(map);
	}

	@Override
	public List<String> readRoles(String userId) {
		return userDao.getRoles(userId);
	}

	@Override
	public User readUserByIdCard(String idCard) {
		// TODO Auto-generated method stub
		return userDao.getUserByCard(idCard);
	}

	@Override
	public PageInfo<newInvestUser> readInvestUserInfo(int pageNo, int pageSize,
			Map<String,Object> params) {
		return userDao.getInvestUserInfoPageLite(pageNo, pageSize, params);
	}

	@Override
	public PageInfo<UserInvestCheck> readUserInvestCheck(int pageNo, int pageSize) {
		
		return userDao.getUserInvestCheck(pageNo,pageSize);
	}

	public int insertUserInfoCheck(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return userDao.insertUserInfoCheck(params);
	}

	@Override
	public int delUserinfo(String id) {
		
		return userDao.delUserinfo(id);
	}

	public List<UserInfoConfirmation> readUserInfoConfirmation(String userId,String start,String end) {
	
		return userDao.getUserInfoConfirmation(userId,start,end);
	}


	@Override
	public String readTemplate(String string) {

		return userDao.getTemplate(string);
	}

	@Override
	public int updateExportTime(String userId,String exportTime) {
		
		return userDao.updateExportTime(userId,exportTime);
	}

	@Override
	public List<String> readUserIds() {
		return userDao.getUserIds();
	}

	@Override
	public PageInfo<UserInvestCheck> readUserInvestCheckByTime(int pageNo,
			int pageSize, String start, String end) {
		
		return userDao.getUserInvestCheckByTime(pageNo,pageSize,start,end);
	}

	@Override
	public PageInfo<newInvestUser> readInvestUserInfoRemark(int pageNo,
			int pageSize,Map<String,Object> params) {
		return userDao.getInvestUserInfoRemarkPageLite(pageNo, pageSize, params);
	}
	
	@Override
	public PageInfo<newInvestUser> readInvestUserInfoNoRemark(int pageNo,
			int pageSize, Map<String,Object> params) {
		return userDao.getInvestUserInfoNoRemarkPageLite(pageNo, pageSize, params);
	}

	@Override
	public int readPeopleCount(Map<String, Object> params) {
		return userDao.getPeopleCount(params);
	}

	@Override
	public List<UserRemarkInfo> readRemarkInfo(String start, String end) {
		Map<String,Object> params=new HashMap<>();
		params.put("start", start);
		if (end!=null) {
			end=URLDecoder.decode(end);
			if (StringUtils.isNotBlank(end)) {
				params.put("end", end+" 23:59:59");
			}
		}
		return userDao.getRemarkInfo(params);
	}

	@Override
	public int readPeopleCountRemark(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return userDao.getPeopleCountRemark(params);
	}

	@Override
	public int readPeopleCountNoRemark(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return userDao.getPeopleCountNoRemark(params);
	}

	@Override
	public PageInfo<UserByFuRen> readInvestInfoByFuRen(int pageNo,
			int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return userDao.readInvestInfoByFuRen(pageNo,pageSize,params);
	}

	@Override
	public List<UserByFuRen> readExportInvestByFuRen() {
		// TODO Auto-generated method stub
		return userDao.readExportInvestByFuRen();
	}


	@Override
	public String readUdeskField(String fieldLabel) {
		JSONObject json=udeskService.udeskUserfields();
		System.out.println(json);
		JSONArray dataArray =json.getJSONArray("user_fields");
		String fieldName="";
		for (int i = 0; i < dataArray.size(); i++) {
			JSONObject bean=(JSONObject) JSONObject.toJSON(dataArray.get(i));
			//System.out.println(bean);
		   if(bean.getString("field_label").equals(fieldLabel)){
			   //System.out.println(bean.getString("field_name"));
			   fieldName=bean.getString("field_name");
		   }
		}
		return fieldName; 
	}


	@Override
	public List<newInvestUser> readInvestUserInfo(Map<String, Object> params) {
		return userDao.getInvestUserInfo(params);
	}


	@Override
	public List<newInvestUser> readInvestUserInfoRemark(
			Map<String, Object> params) {
		return userDao.getInvestUserInfoRemarkPageLite(params);
	}


	@Override
	public List<newInvestUser> readInvestUserInfoNoRemark(
			Map<String, Object> params) {
		return userDao.getInvestUserInfoNoRemarkPageLite(params);
	}


	@Override
	public String readEncodeUserId(Map<String, Object> map1) {
		// TODO Auto-generated method stub
		return userDao.getEncodeUserId(map1);
	}


	/* 
	 * 注销用户
	 */
	@Override
	public void cancelUser(User user) throws Exception {
		String userId = user.getUserId();
		if (StringUtils.isBlank(userId)) {
			throw new Exception("用户不存在！");
		}
		userDao.cancelUser(user);
	}

}