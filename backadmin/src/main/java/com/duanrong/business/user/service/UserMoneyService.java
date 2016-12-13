package com.duanrong.business.user.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserMoney;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午1:32:32
 */
public interface UserMoneyService {

	/**
	 * 从余额转出
	 * 
	 * @param userId
	 * @param money
	 * @param operatorInfo
	 * @param operatorDetail
	 * @throws InsufficientBalance
	 */
	/*@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void transferOutFromBalance(String userId, double money,
			String operatorInfo, String operatorDetail)
			throws InsufficientBalance;*/

	/**
	 * 
	 * @description 从余额转出
	 * @author 孙铮
	 * @time 2015-3-24 下午12:19:35
	 * @param userId
	 * @param money
	 * @param operatorInfo
	 * @param operatorDetail
	 * @param date
	 * @throws InsufficientBalance
	 */
	/*@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void transferOutFromBalance(String userId, double money,
			String operatorInfo, String operatorDetail, Date date)
			throws InsufficientBalance;*/

	/**
	 * 转入到余额
	 * 
	 * @param userId
	 * @param money
	 * @param operatorInfo
	 * @param operatorDetail
	 */
	/*@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void transferIntoBalance(String userId, double money,
			String operatorInfo, String operatorDetail);*/

	/**
	 * 
	 * @description 转入到余额
	 * @author 孙铮
	 * @time 2015-3-24 上午10:46:48
	 * @param userId
	 * @param money
	 * @param operatorInfo
	 * @param operatorDetail
	 */
	/*@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void transferIntoBalance(String userId, double money,
			String operatorInfo, String operatorDetail, Date date);*/

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 */
	//public PageData<UserMoney> readPaging(int pageNo, int pageSize, UserMoney um);

	/**
	 * 获取用户奖励总额
	 * 
	 * @param userId
	 * @return
	 */
	//public Double readAwardTotalMoney(String userId);

	/**
	 * 获得用户奖励详情
	 * 
	 * @param userId
	 * @return
	 */
	//public List<UserMoney> readAwardDetail(String userId);

	/**
	 * 获取用户账户可用余额
	 * 
	 * @param userId
	 * @return
	 */
	@Deprecated
	public Double readBalance(String userId);

	/**
	 * 获取用户账户冻结金额
	 * 
	 * @param userId
	 * @return
	 */
	@Deprecated
	public Double readFrozenMoney(String userId);

	/**
	 * 冻结金额.
	 * 
	 * @param userId
	 * @param money
	 *            金额
	 * @param operatorInfo
	 *            操作信息
	 * @param operatorDetail
	 *            操作详情
	 * @throws InsufficientBalance
	 *             余额不足
	 */
	/*public void freezeMoney(String userId, double money, String operatorInfo,
			String operatorDetail) throws InsufficientBalance;*/

	//public UserMoney readLastestUserMoney(String userId);

	/**
	 * @description 平台还款转入到余额
	 * @author 孙铮
	 * @time 2015-3-3 下午12:27:27
	 * @param userId
	 * @param money
	 * @param operatorInfo
	 * @param operatorDetail
	 */
	/*void platformTransferIntoBalance(String userId, double money,
			String operatorInfo, String operatorDetail);
*/
	/**
	 * 分页
	 * 
	 * @param map
	 * @return
	 */
	public PageInfo<UserMoney> readPageInfo(int pageNo, int pageSize, Map map);

	/*public void saveUserMoney(String userId, double money, String operatorInfo,
			String operatorDetail, UserMoney ibLastest, UserMoney userMoney,
			String type);*/

	/**
	 * 
	 * @description 从冻结金额中转出
	 * @author 孙铮
	 * @time 2015-3-20 下午5:51:23
	 * @param userId
	 * @param money
	 * @param operatorInfo
	 * @param operatorDetail
	 * @throws InsufficientBalance
	 */
	/*public void transferOutFromFrozen(String userId, double money,
			String operatorInfo, String operatorDetail)
			throws InsufficientBalance;
*/
	/**
	 * 
	 * @description 从冻结金额中转出
	 * @author 孙铮
	 * @time 2015-3-24 上午10:16:18
	 * @param userId
	 *            用户id
	 * @param money
	 *            金额
	 * @param operatorInfo
	 * @param operatorDetail
	 * @param date
	 *            时间
	 * @throws InsufficientBalance
	 */
	/*public void transferOutFromFrozen(String userId, double money,
			String operatorInfo, String operatorDetail, Date date)
			throws InsufficientBalance;*/

	/**
	 * 
	 * @description 该方法只正对借款管理费
	 * @author 孙铮
	 * @time 2015-3-20 下午4:03:57
	 * @param userId
	 * @param money
	 * @param operatorInfo
	 * @param operatorDetail
	 */
	/*public void transferOutFrozenToManagementCost(String userId, double money,
			String operatorInfo, String operatorDetail);*/

	/**
	 * 
	 * @description 该方法只正对借款管理费(本地放款时使用)
	 * @author 孙铮
	 * @time 2015-3-24 上午10:32:46
	 * @param borrowMoneyUserID
	 * @param loanGuranteeFee
	 * @param operatorInfo
	 * @param operatorDetail
	 * @param date
	 */
	/*public void transferOutFrozenToManagementCost(String borrowMoneyUserID,
			Double loanGuranteeFee, String operatorInfo, String operatorDetail,
			Date date);*/

	/**
	 * 解冻金额.
	 * 
	 * @param userId
	 *            用户id
	 * @param money
	 *            金额
	 * @param operatorInfo
	 *            操作信息
	 * @param operatorDetail
	 *            操作详情
	 * 
	 * @throws InsufficientBalance
	 *             余额不足
	 */
	/*public void unfreezeMoney(String userId, double money, String operatorInfo,
			String operatorDetail) throws InsufficientBalance;*/

	/**
	 * 
	 * @description 解冻金额
	 * @author 孙铮
	 * @time 2015-3-24 上午10:37:32
	 * @param borrowMoneyUserID
	 * @param abs
	 * @param string
	 * @param string2
	 * @param date
	 */
	/*public void unfreezeMoney(String borrowMoneyUserID, double abs,
			String operatorInfo, String operatorDetail, Date date)
			throws InsufficientBalance;*/
	/**
	 * //查询固定借款人userid
	 * @return
	 */
	public List<User> readBorrowerUserId();

	
	/**
	 * 根据时间 userId 查询投资金额
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public double readInvestMoney(String userId, String start, String end);
	/**
	 * 根据时间 userId 查询现在投资金额
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public double readInvestingMoeny(String userId, String start, String end);
	/**
	 * 查询可用余额
	 * @param user_Id
	 * @param time
	 * @return
	 */
	public double readUsedMoeny(String user_Id, String time);

	//@Transactional(readOnly = false, rollbackFor = Exception.class)
	/*public void transferOutFromBalance(String id, String userId, double money,
			String operatorInfo, String operatorDetail)
			throws InsufficientBalance;*/
	
	/**
	 * 获取用户账户可用余额
	 * 
	 * @param userId
	 * @return
	 */
	public double readNBalance(String userId);

	/**
	 * 获取用户账户冻结金额
	 * 
	 * @param userId
	 * @return
	 */
	public double readNFrozenMoney(String userId);

	public String readPayMnetIdBybankByUser(String cardNo, String userid);

		
}
