package com.duanrong.business.user.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.Config;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserByFuRen;
import com.duanrong.business.user.model.UserInfoConfirmation;
import com.duanrong.business.user.model.UserInvestCheck;
import com.duanrong.business.user.model.UserRemarkInfo;
import com.duanrong.business.user.model.newInvestUser;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午3:14:43
 */
public interface UserDao extends BaseDao<User> {
	/**
	 * 查询管理员列表
	 * @param user
	 * @return
	 */
	
	public List<User> queryAdmin(User user);
	

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
	public List<User> getUserTotalMoney(Map map);
	/**
	 * 获取每天的开户人数
	 * 
	 * @param params
	 * @return
	 */
	public Long getTheNumberOfAccountEveryDay(Map<String, Object> params);
	PageInfo<User> findAdminUsers(int pageNo, int pageSize);
	public void updateUserPhoneArea(User user);
	public List<User> getUsersPhone(Map map);

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

	public PageInfo<newInvestUser> getInvestUserInfoPageLite(int pageNo,
			int pageSize, Map<String, Object> params);

	public PageInfo<UserInvestCheck> getUserInvestCheck(int pageNo, int pageSize);
	
	public int insertUserInfoCheck(Map<String, Object> params);

	public int delUserinfo(String id);

	public List<UserInfoConfirmation> getUserInfoConfirmation(String userId,
			String start, String end);

	public String getTemplate(String string);

	public int updateExportTime(String userId,String exportTime);

	public List<String> getUserIds();

	public PageInfo<UserInvestCheck> getUserInvestCheckByTime(int pageNo,
			int pageSize, String start, String end);

	public PageInfo<newInvestUser> getInvestUserInfoRemarkPageLite(int pageNo,
			int pageSize, Map<String, Object> params);

	public PageInfo<newInvestUser> getInvestUserInfoNoRemarkPageLite(
			int pageNo, int pageSize, Map<String, Object> params);

	public int getPeopleCount(
			Map<String, Object> params);

	public List<UserRemarkInfo> getRemarkInfo(
			Map<String, Object> params);

	public int getPeopleCountRemark(Map<String, Object> params);

	public int getPeopleCountNoRemark(Map<String, Object> params);

	public PageInfo<UserByFuRen> readInvestInfoByFuRen(int pageNo,
			int pageSize, Map<String, Object> params);

	public List<UserByFuRen> readExportInvestByFuRen();

	public List<newInvestUser> getInvestUserInfo(Map<String,Object>params);
	
	public List<newInvestUser> getInvestUserInfoRemarkPageLite(Map<String, Object> params);
	
	public List<newInvestUser> getInvestUserInfoNoRemarkPageLite(Map<String, Object> params);


	public String getEncodeUserId(Map<String, Object> map1);


	/**注销用户
	 * @param user
	 */
	public void cancelUser(User user) throws Exception;
}
