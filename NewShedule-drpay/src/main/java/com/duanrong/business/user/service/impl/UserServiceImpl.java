package com.duanrong.business.user.service.impl;

import java.io.IOException;
import java.util.Date;
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

import com.duanrong.business.invest.model.PassThrough;
import com.duanrong.business.maxinvestrecord.model.MaxInvestRecord;
import com.duanrong.business.token.dao.AccessTokenDao;
import com.duanrong.business.token.model.AccessToken;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.user.UserConstants;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.Config;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserAccount;
import com.duanrong.business.user.model.UserOtherInfo;
import com.duanrong.business.user.service.AuthInfoService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.user.service.UserOtherInfoService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.jsonservice.util.RegexInput;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.util.HashCrypt;

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
	AccessTokenDao accessTokenDao;
	
	@Resource
	Log log;

	/**
	 * 查询所有的用户
	 */
	public List<User> find() {
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
	public boolean isAlreadExist(String fieldName, String value) {

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
	
	public User loginUser(User user) {
		return userDao.verifyLogin(user);
	}

//	public Double getUserMoney(String userId) {
//		double balanceMoney = userMoneyService.getBalance(userId);
//		double fozenMoney = userMoneyService.getFrozenMoney(userId);
//		return ArithUtil.add(balanceMoney, fozenMoney);
//	}

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
			if (isAlreadExist("mobileNum", mobileNumber)
					|| isAlreadExist("email", email)) {
				return null;
			}
		} else {
			// 校验格式
			if (!(RegexInput.checkMobilePhone(mobileNumber) && RegexInput
					.checkPassword(password))) {
				return null;
			}

			// 校验是否存在
			if (isAlreadExist("mobileNum", mobileNumber)) {
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
	public User get(String id) {
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

	public String updatePassword(String userId, String password, String resetPassword) {
		User user = userDao.get(userId);
		if (user == null) {
			return "NU";
		}else if(!user.getPassword().equals(HashCrypt.getDigestHash(password))){			
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

	public User getUserByMobileNumber(String mobileNumber) {
		return userDao.getUserByMobileNumber(mobileNumber);
	}

	@Override
	public Long getPerson() {
		return Long.parseLong(userDao.getPerson2());
	}
	
	@Override
	public boolean verifyIdCard(String userId, String idCard) {
		return userDao.verifyIdCard(userId, idCard);
	}

	/*@Override
	public List<UserAccount> getAccountInfo(String stype, String content) {
		List<UserAccount> list = new ArrayList<UserAccount>();
		if ("mobile".equals(stype)) {
			User u = getUserByMobileNumber(content.trim());
			UserAccount account = new UserAccount();
			if(u!=null){
				account = getCallAccountInfo(u.getUserId(), account);
				double bbalance = userMoneyService.getBalance(u.getUserId());
				double bfreezeAmount = userMoneyService.getFrozenMoney(u
						.getUserId());
				double bavailableAmount = bbalance + bfreezeAmount;
				account.setBbalance(ArithUtil.round(bavailableAmount,2));
				account.setBfreezeAmount(bfreezeAmount);
				account.setBavailableAmount(ArithUtil.round(bbalance,2));
				account.setUserid(u.getUserId());
				account.setMobile(u.getMobileNumber());
				account.setRealname(u.getRealname());
				if (account != null) {
					list.add(account);
				}
			}
			
			
		} else if ("name".equals(stype)) {
			List<User> users = getUserByRealname(content.trim());
			for (int i = 0; i < users.size(); i++) {
				UserAccount account = new UserAccount();
				account = getCallAccountInfo(users.get(i).getUserId(), account);
				double bbalance = userMoneyService.getBalance(users.get(i)
						.getUserId());
				double bfreezeAmount = userMoneyService.getFrozenMoney(users
						.get(i).getUserId());
				double bavailableAmount = bbalance + bfreezeAmount;
				account.setBbalance(ArithUtil.round(bavailableAmount, 2));
				account.setBfreezeAmount(bfreezeAmount);
				account.setBavailableAmount(ArithUtil.round(bbalance,2));
				account.setUserid(users.get(i).getUserId());
				account.setMobile(users.get(i).getMobileNumber());
				account.setRealname(users.get(i).getRealname());
				if (account != null) {
					list.add(account);
				}
			}
		} else if ("userid".equals(stype)) {
			UserAccount account = new UserAccount();
			User u = get(content.trim());
			if(u!=null){
				account = getCallAccountInfo(content.trim(), account);
				double bbalance = userMoneyService.getBalance(content.trim());
				double bfreezeAmount = userMoneyService.getFrozenMoney(content
						.trim());
				double bavailableAmount = bbalance + bfreezeAmount;
				account.setBbalance(ArithUtil.round(bavailableAmount, 2));
				account.setBfreezeAmount(bfreezeAmount);
				account.setBavailableAmount(ArithUtil.round(bbalance,2));
				account.setUserid(content.trim());
				account.setMobile(u.getMobileNumber());
				account.setRealname(u.getRealname());
				if (account != null) {
					list.add(account);
				}
			}			
		} else {

		}
		return list;
	}
*/
	@Override
	public List<User> getUserByRealname(String realname) {
		return userDao.getUserByRealname(realname);
	}

	/**
	 * @author wangjing
	 * @param userNo
	 * @return
	 */
	public UserAccount getCallAccountInfo(String userNo, UserAccount account) {

		/*HttpClient client = new HttpClient();
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
			account.setBank(bank);
			account.setCardNo(cardNo);
			account.setCardStatus(cardStatus);
			account.setCode(code);
			account.setDescription(description);
			account.setActiveStatus(activeStatus);
			account.setMemberType(memberType);
			account.setPaySwift(paySwift);
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
		// return resultMap;*/
		return null;
	}

	@Override
	public Long getTheNumberOfRegisteredEveryDay(Map<String, Object> params) {
		return userDao.getTheNumberOfRegisteredEveryDay(params);
	}

	@Override
	public PageInfo<User> findPageInfo(int pageNo, int pageSize, Map map) {
		// TODO Auto-generated method stub
		return userDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public PageInfo<User> pageLite(int pageNo, int pageSize, User user) {
		return userDao.pageLite(pageNo, pageSize, user);
	}

	@Override
	public void updateUser(User user) throws Exception {
		String userId = user.getUserId();
		if (StringUtils.isBlank(userId)) {
			throw new Exception(
					"用户不存在！");
		}
		if (StringUtils.isNoneBlank(user.getMobileNumber())) {
			// 校验格式
			if (!(RegexInput.checkMobilePhone(user.getMobileNumber()))) {
				throw new Exception(
						"手机号格式不正确！");
			}
			// 校验是否存在
			if (isExistMobileNumber(user)) {
				throw new Exception(
						"手机号已经存在！");
			}
			String[] strs=PhoneNoAttributionUtils.getAttributions(user.getMobileNumber());
			if(strs[0]!=null){
				user.setPhoneNoAttribution(strs[0]);
			}
			if(strs[0]!=null){
				user.setPhoneNoCity(strs[1]);
			}
		}
		
		userDao.update(user);
	}
	@Override
	public void update(User user) throws Exception {
		String userId = user.getUserId();
		if (StringUtils.isBlank(userId)) {
			throw new Exception(
					"UserService.class updateUser : 用户不存在！");
		}
		userDao.update(user);
	}
	public boolean isExistMobileNumber(User user) {

		int num = userDao.verifyUser(user);
		if (num!=0) {
			return true;
		}
		return false;
	}

	@Override
	public List<User> getUsers(User user) {
		return userDao.getUsers(user);
	}

	@Override
	public Template getTemplateById(String id) {		
		return userDao.getTemplateById(id);
	}

	@Override
	public PageInfo<Config> getConfigForPageLite(int pageNo, int pageSize,
			Config config) {
		return userDao.getConfigForPageLite(pageNo, pageSize, config);
	}

	@Override
	public Config getConfigById(String id) {
		
		return userDao.getConfigById(id);
	}

	@Override
	public boolean updateConfig(Config config) {
		if(userDao.getConfigById(config.getId()) != null) return userDao.updateConfig(config) > 0;
		return false;
	}

	@Override
	public boolean insertConfig(Config config) {
		if(userDao.getConfigById(config.getId()) == null) return userDao.insertConfig(config) > 0;
		return false;
	}

	@Override
	public User getUserByMail(String email) {
		
		return userDao.getUserByMail(email);
	}

	@Override
	public List<User> getUserNum(Map map) {
		return userDao.getUserNum(map);
	}
	/**
	 * add by wangjing
	 */
	@Override
	public List<User> findUsers(Map<String, Object> map) {
		return userDao.findUsers(map);
	}


	@Override
	public Long getTheNumberOfAccountEveryDay(Map<String, Object> params) {
		return userDao.getTheNumberOfAccountEveryDay(params);
	}

	@Override
	public PageInfo<User> findAdminUsers(int pageNo, int pageSize) {
		return userDao.findAdminUsers(pageNo, pageSize);
	}

	@Override
	public void updateList(List<User> users){
		for(User user:users){
		
			if (!StringUtils.isBlank(user.getMobileNumber())) {
				
				try
				{
					String[] strs=PhoneNoAttributionUtils.getAttributions(user.getMobileNumber());
					if(strs[0]!=null){
						user.setPhoneNoAttribution(strs[0]);
					}
					if(strs[1]!=null){
						user.setPhoneNoCity(strs[1]);
					}
					if(StringUtils.isBlank(user.getPhoneNoAttribution()) && StringUtils.isBlank(user.getPhoneNoCity())){
						continue;
					}else{
						userDao.updateUserPhoneArea(user);
					}
				}
				catch(Exception e)
				{
					log.errLog(
							"更新用户手机号码归属地报错"+user.getMobileNumber(),
							e);
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
	public List<User> exportList(Map map) {
		return userDao.exportList(map);
	}

	@Override
	public List<String> getRoles(String userId) {
		return userDao.getRoles(userId);
	}

	@Override
	public User getUserByIdCard(String idCard) {
		// TODO Auto-generated method stub
		return userDao.getUserByCard(idCard);
	}
	
	@Override
	public void updateUserMobileNumber(User user) {
		//查询出用户的UserToken，置为已过期
		AccessToken accessToken = accessTokenDao.get(user.getUserId());
		if (accessToken != null) {
			accessTokenDao.update(accessToken);
		}
		userDao.updateMobileNumber(user);
	}

	@Override
	public void updateReferrerMobileNumber(String oldNumber, String newNumber) {
		userDao.updateReferrerMobileNumber(oldNumber ,newNumber);
	}

	@Override
	public List<User> getBigClientBirthday() {
		return userDao.getBigClientBirthday();
	}
	
	
	@Override
	public List<String> readUserMobileNumber(int day) {
		// TODO Auto-generated method stub
		return userDao.readUserMobileNumber(day) ;
	}

	@Override
	public List<String> readUserMobukeNumbers() {
		// TODO Auto-generated method stub
		return userDao.readUserMobukeNumbers();
	}

	@Override
	public int readRedpacketByMob(String mobileNumber) {
		// TODO Auto-generated method stub
		return userDao.readRedpacketByMob(mobileNumber);
	}
	
	@Override
	public List<User> getExpireRedpacket() {
		return userDao.getExpireRedpacket();
	}

	@Override
	public MaxInvestRecord getUserInfo(String userId) {
		return this.userDao.getUserInfo(userId);
	}

	@Override
	public PassThrough getDoubleElevenPrize(String userId) {
		return  userDao.getDoubleElevenPrize(userId);
	}

	@Override
	public void insert4DoubleEleven(PassThrough passThrough) {
		userDao.insert4DoubleEleven(passThrough);
	}

	@Override
	public List<PassThrough> getRecommendedPrize(String userId) {
		return userDao.getRecommendedPrize(userId);
	}

	@Override
	public void update4DoubleElevenReward(PassThrough passThrough) {
		userDao.update4DoubleElevenReward(passThrough);
	}

	@Override
	public void update4DoubleEleven(PassThrough passThrough) {
		userDao.update4DoubleEleven(passThrough);
		
	}
	
	

	@Override
	public List<String> readUserMobileNumberziran(int day) {
		return userDao.readUserMobileNumberziran(day);
	}

	@Override
	public List<String> readUserMobileNumbertuiguang(int day) {
		// TODO Auto-generated method stub
		return userDao.readUserMobileNumbertuiguang(day);
	}
	
	@Override
	public void updatePassThrough(PassThrough passThrough){
		 userDao.updatePassThrough(passThrough);
	}

	
}