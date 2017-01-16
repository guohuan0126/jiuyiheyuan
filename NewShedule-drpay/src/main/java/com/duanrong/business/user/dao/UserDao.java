package com.duanrong.business.user.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.model.PassThrough;
import com.duanrong.business.maxinvestrecord.model.MaxInvestRecord;
import com.duanrong.business.user.model.Config;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserOtherInfo;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午3:14:43
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * 获取每天的注册人数
	 * 
	 * @param params
	 * @return
	 */
	public Long getTheNumberOfRegisteredEveryDay(Map<String, Object> params);

	/**
	 * 更新注册用户数量
	 */
	public void updatePersonCount4Config();

	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @return
	 */
	public User getUserByMobileNumber(String mobileNumber);
	
	/**
	 * 根据电邮查询用户
	 * 
	 * @param email
	 *            
	 * @return
	 */
	public User getUserByMail(String email);
	
	/**
	 * 根据身份证查询用户
	 * 
	 * @param email
	 *            
	 * @return
	 */
	public User getUserByCard(String idCard);

	/**
	 * 用户登录验证
	 * 
	 * @param user
	 * @return
	 */
	public User verifyLogin(User user);

	/**
	 * 为用户添加角色
	 * 
	 * @param role
	 */
	public void addRole(String userId, String roleId);

	/**
	 * 获得用户角色
	 * 
	 * @param role
	 */
	public List<String> getRoles(String userId);

	/**
	 * 获取用户数量
	 * 
	 * @return
	 */
	public Long getPerson();

	/**
	 * 获取自定义用户数量
	 * 
	 * @return
	 */
	public String getPerson2();

	/**
	 * 校验身份证号是否存在
	 * 
	 * @param userId
	 *            用户ID
	 * @param idCard
	 *            身份证号
	 */
	public boolean verifyIdCard(String userId, String idCard);

	public List<User> getUserByRealname(String realname);
	
	PageInfo<User> pageInfo(int pageNo, int pageSize, Map map);
	
	public int verifyUser(User user);
	
	public List<User> getUsers(User user);
	
	/**
	 * 根据id获取模板
	 * @param id
	 * @return
	 */
	public Template getTemplateById(String id);
	
	/**
	 * @author xiao
	 * @param config
	 * @return
	 */
	public PageInfo<Config> getConfigForPageLite(int pageNo, int pageSize, Config config);
	
	/**
	 * @author xiao
	 * @param config
	 * @return
	 */
	public Config getConfigById( String id);
	
	/**
	 * @author xiao
	 * @param config
	 * @return
	 */
	public int updateConfig(Config config);
	
	/**
	 * @author xiao
	 * @param config
	 * @return
	 */
	public int insertConfig(Config config);
	public List<User> getUserNum(Map map);
	public List<User> findUsers(Map<String, Object> map);
	
	/**
	 * 获取每天的开户人数
	 * 
	 * @param params
	 * @return
	 */
	public Long getTheNumberOfAccountEveryDay(Map<String, Object> params);
	PageInfo<User> findAdminUsers(int pageNo, int pageSize);
	public void updateUserPhoneArea(User user);
	PageInfo<User> getUsersPhone(int pageNo, int pageSize);

	public List<User> exportList(Map map);
	
	
	/**
	 * 根据邀请码获取用户
	 * @param inviteCode
	 * @return
	 */
	public User getUserByInviteCode(String inviteCode);
	
	/**
	 * 根据邀请码获取用户数量
	 * @param inviteCode
	 * @return
	 */
	public int getCountByReffer(String referrer, Date firstInvestTime);

	/**
	 * 判断是否内部员工
	 * @param inviteCode
	 * @return
	 */
	public int getAvlidUserByMobileNumber(String mobileNumber);
	
	public void updateMobileNumber(User user);
	
	public void updateReferrerMobileNumber(String oldNumber, String newNumber);

	public List<User> getBigClientBirthday();

	public List<String> readUserMobileNumber(int day);

	public List<String> readUserMobukeNumbers();

	public int readRedpacketByMob(String mobileNumber);
	
	public List<User> getExpireRedpacket();
	//获取用户信息
	public MaxInvestRecord getUserInfo(String uId);

	public PassThrough getDoubleElevenPrize(String userId);

	public void insert4DoubleEleven(PassThrough passThrough);

	public List<PassThrough> getRecommendedPrize(String userId);

	public void update4DoubleElevenReward(PassThrough passThrough);

	public void update4DoubleEleven(PassThrough passThrough);

	public List<String> readUserMobileNumberziran(int day);

	public List<String> readUserMobileNumbertuiguang(int day);
	
	
	public void updatePassThrough(PassThrough passThrough);
	
	
	public PassThrough getPassThroughByUserId(Map<String,Object>param);
	
	public UserOtherInfo getUserOtherInfoById(Map<String,Object>param);

}
