package com.duanrong.business.user.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.Config;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.model.UserAccount;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserByFuRen;
import com.duanrong.business.user.model.UserInfoConfirmation;
import com.duanrong.business.user.model.UserInvestCheck;
import com.duanrong.business.user.model.UserRemarkInfo;
import com.duanrong.business.user.model.newInvestUser;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午5:44:57
 */
public interface UserService {
	
	/**
	 * 描述：查询系统管理员
	 * 
	 * @author 董君 Demon
	 * @email  dongjun@duanrong.com
	 * @date 2016年3月11日 下午2:55:39
	 */
	public List<User> readAdmin(User user);
	
	
	
	
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
	public PageInfo<User> readPageLite(int pageNo, int pageSize, User user);

	/**
	 * 获取每天的注册人数
	 * 
	 * @param params
	 * @return
	 */
	public Long readTheNumberOfRegisteredEveryDay(Map<String, Object> params);

	/**
	 * 获取注册用户数量
	 * 
	 * @return
	 */
	public Long readPerson();

	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @return
	 */
	public User readUserByMobileNumber(String mobileNumber);
	
	/**
	 * 根据身份证查询用户
	 * @param idCard
	 * @return
	 */
	public User readUserByIdCard(String idCard);
	
	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobileNumber
	 *            手机号
	 * @return
	 */
	public User readUserByMail(String email);

	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 * @return
	 */
	public User read(String id);

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	public List<User> readAll();

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
	public boolean readExist(String fieldName, String value);

	/**
	 * 用户登陆（用户名，手机，电邮）
	 * 
	 * @param user
	 * @return
	 */
	public User readLoginUser(User user);

	/**
	 * 为用户添加角色
	 * 
	 * @param role
	 */
	public void addRole(String userId, Role role);


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
	public boolean readVerifyIdCard(String userId, String idCard);

	/**
	 * 根据姓名查询用户
	 * 
	 * @param realname
	 *            姓名
	 * @return
	 */
	public List<User> readUserByRealname(String realname);

	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public String updatePassword(String userId, String password, String resetPassword);
	
	List<UserAccount> readAccountInfo(String userId, String realName,String start,String end);
	/**
	 * 分页
	 * @param map
	 * @return
	 */
	 public PageInfo<User> readPageInfo(int pageNo, int pageSize,
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
	public List<User> readUsers(User user);
	
	/**
	 * 获取模板
	 * @param id
	 * @return
	 */
	public Template readTemplateById(String id);

	public UserAccount getCallAccountInfo(String userId, UserAccount account);

	/** 
	 * 系统配置信息分页
	 * @author xiao
	 * @param pageNo
	 * @param pageSize
	 * @param config
	 * @return
	 */
	public PageInfo<Config> readConfigForPageLite(int pageNo, int pageSize, Config config);
	
	/** 
	 * 系统配置信息分页
	 * @author xiao
	 * @param pageNo
	 * @param pageSize
	 * @param config
	 * @return
	 */
	public Config readConfigById(String id);
	
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
	 public List<User> readUserNum(Map map);
	 public List<User> readUsers(Map<String, Object> map);
	 public List<User> readUserTotalMoney(Map map);
	 /**
		 * 获取每天的开户数量
		 * 
		 * @param params
		 * @return
		 * 
		 * 
		 */
public Long readTheNumberOfAccountEveryDay(Map<String, Object> params);
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


 public PageInfo<User> readAdminUsers(int pageNo, int pageSize);
 public void updateList(List<User> users);
 public void updateUsers();
 public List<User> readExportList(
		 Map map);
public List<String> readRoles(String userId);

public PageInfo<newInvestUser> readInvestUserInfo(int pageNo, int pageSize,
		Map<String,Object>params);

public PageInfo<UserInvestCheck> readUserInvestCheck(int pageNo, int pageSize);

public int insertUserInfoCheck(Map<String, Object> params);

public int delUserinfo(String id);

public List<UserInfoConfirmation> readUserInfoConfirmation(String userId,String start,String end);


public String readTemplate(String string);

public int updateExportTime(String userId,String exportTime);

public List<String> readUserIds();

public PageInfo<UserInvestCheck> readUserInvestCheckByTime(int pageNo,
		int pageSize, String start, String end);

public PageInfo<newInvestUser> readInvestUserInfoRemark(int pageNo,
		int pageSize, Map<String, Object> params);

public PageInfo<newInvestUser> readInvestUserInfoNoRemark(int pageNo,
		int pageSize,Map<String, Object> params);

public int readPeopleCount(Map<String, Object> params);

public List<UserRemarkInfo> readRemarkInfo(String start, String end);

public int readPeopleCountRemark(Map<String, Object> params);

public int readPeopleCountNoRemark(Map<String, Object> params);

public PageInfo<UserByFuRen> readInvestInfoByFuRen(int parseInt, int parseInt2,
		Map<String, Object> params);

public List<UserByFuRen> readExportInvestByFuRen();
/**
 * 通过传字段值名字获取udesk的字段
 * @param field_label
 * @return
 */
public String readUdeskField(String field_label);

public List<newInvestUser> readInvestUserInfo(Map<String,Object>params);

public List<newInvestUser> readInvestUserInfoRemark(Map<String, Object> params);

public List<newInvestUser> readInvestUserInfoNoRemark(Map<String, Object> params);




public String readEncodeUserId(Map<String, Object> map1);




/**
 * 注销用户
 * @param user
 * @throws Exception 
 */
public void cancelUser(User user) throws Exception;
}