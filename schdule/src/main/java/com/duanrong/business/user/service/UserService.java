package com.duanrong.business.user.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.invest.model.PassThrough;
import com.duanrong.business.maxinvestrecord.model.MaxInvestRecord;
import com.duanrong.business.user.model.Config;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.UserAccount;
import com.duanrong.business.user.model.User;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午5:44:57
 */
public interface UserService {
	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 * @param entity
	 *            条件
	 */
	public PageInfo<User> pageLite(int pageNo, int pageSize, User user);

	/**
	 * 获取每天的注册人数
	 * 
	 * @param params
	 * @return
	 */
	public Long getTheNumberOfRegisteredEveryDay(Map<String, Object> params);

	/**
	 * 获取注册用户数量
	 * 
	 * @return
	 */
	public Long getPerson();

	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @return
	 */
	public User getUserByMobileNumber(String mobileNumber);
	
	/**
	 * 根据身份证查询用户
	 * @param idCard
	 * @return
	 */
	public User getUserByIdCard(String idCard);
	
	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @return
	 */
	public User getUserByMail(String email);

	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 * @return
	 */
	public User get(String id);

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	public List<User> find();

	/**
	 * 根据id删除某一用户
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 根据用户id判断用户的某些属性是否存在 如用户名，邮箱，手机号，身份证号等
	 * 
	 * @param fieldNmae
	 * @param id
	 * @return
	 */
	public boolean isAlreadExist(String fieldName, String value);

	/**
	 * 用户登陆（用户名，手机，电邮）
	 * 
	 * @param user
	 * @return
	 */
	public User loginUser(User user);

	/**
	 * 为用户添加角色
	 * 
	 * @param role
	 */
	public void addRole(String userId, Role role);

	/**
	 * 得到用户账户金额信息
	 * 
	 * @param userId
	 * @return
	 */
	//public Double getUserMoney(String userId);

	/**
	 * 用户注册
	 * 
	 * @param user
	 */
	public String register(User user);

	/**
	 * 
	 * @description 更新用户
	 * @author 孙铮
	 * @time 2014-9-10 上午10:37:53
	 * @param user
	 * @param updatePassword
	 *            true 更新密码 false 不更新密码
	 */
	public void update(User user, boolean updatePassword);

	/**
	 * 校验身份证号是否存在
	 * 
	 * @param userId
	 *            用户ID
	 * @param idCard
	 *            身份证号
	 */
	public boolean verifyIdCard(String userId, String idCard);

	/**
	 * 根据姓名查询用户
	 * 
	 * @param realname
	 *            姓名
	 * @return
	 */
	public List<User> getUserByRealname(String realname);

	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public String updatePassword(String userId, String password, String resetPassword);
	
	//List<UserAccount> getAccountInfo(String stype, String content);
	/**
	 * 分页
	 * @param map
	 * @return
	 */
	 public PageInfo<User> findPageInfo(int pageNo, int pageSize,
			 Map map);
	 /**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public void updateUser(User user) throws Exception;
	/**
	 * 根据查询用户
	 * 
	 * @param user
	 *           
	 * @return
	 */
	public List<User> getUsers(User user);
	
	/**
	 * 获取模板
	 * @param id
	 * @return
	 */
	public Template getTemplateById(String id);

	public UserAccount getCallAccountInfo(String userId, UserAccount account);

	/** 
	 * 系统配置信息分页
	 * @author xiao
	 * @param pageNo
	 * @param pageSize
	 * @param config
	 * @return
	 */
	public PageInfo<Config> getConfigForPageLite(int pageNo, int pageSize, Config config);
	
	/** 
	 * 系统配置信息分页
	 * @author xiao
	 * @param pageNo
	 * @param pageSize
	 * @param config
	 * @return
	 */
	public Config getConfigById(String id);
	
	/** 
	 * 系统配置信息分页
	 * @author xiao
	 * @param pageNo
	 * @param pageSize
	 * @param config
	 * @return
	 */
	public boolean updateConfig(Config config);
	
	/** 
	 * 系统配置信息分页
	 * @author xiao
	 * @param pageNo
	 * @param pageSize
	 * @param config
	 * @return
	 */
	public boolean insertConfig(Config config);
	public List<User> getUserNum(Map map);
	public List<User> findUsers(Map<String, Object> map);
	/**
	 * 获取每天的开户数量
	 * 
	 * @param params
	 * @return
	 */
	public Long getTheNumberOfAccountEveryDay(Map<String, Object> params);
	/**
	 * 更新用户
	 * wangjing
	 * @param user
	 */
	public void update(User user) throws Exception;
	/**
	 * 分页
	 * @param map
	 * @return
	 */
	public PageInfo<User> findAdminUsers(int pageNo, int pageSize);
	public void updateList(List<User> users);
	
	public List<User> exportList(
		 Map map);
	public List<String> getRoles(String userId);
	
	/**
	 * 修改手机号
	 * @param user
	 */
	public void updateUserMobileNumber(User user);

	/**
	 * 修改推荐人手机号
	 * @param user
	 */
	public void updateReferrerMobileNumber(String oldNumber, String newNumber);

	public List<User> getBigClientBirthday();
	
	public List<String> readUserMobileNumber(int day);
    //查出需要发红包的用户
	public List<String> readUserMobukeNumbers();

	public int readRedpacketByMob(String mobileNumber);
	
	public List<User> getExpireRedpacket();

	public MaxInvestRecord getUserInfo(String userId);
    //获取用户活动数据
	public PassThrough getDoubleElevenPrize(String userId);
    //保存用户活动数据
	public void insert4DoubleEleven(PassThrough passThrough);
    //获得被推荐人的数据
	public List<PassThrough> getRecommendedPrize(String userId);
    //更新活动数据
	public void update4DoubleElevenReward(PassThrough passThrough);

	public void update4DoubleEleven(PassThrough passThrough);

	public List<String> readUserMobileNumberziran(int day);

	public List<String> readUserMobileNumbertuiguang(int day);
	
	public void updatePassThrough(PassThrough passThrough);
}
