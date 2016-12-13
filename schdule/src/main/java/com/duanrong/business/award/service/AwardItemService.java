package com.duanrong.business.award.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import base.pagehelper.PageInfo;

import com.duanrong.business.award.model.AwardItem;
import com.duanrong.business.award.model.AwardItemUser;
import com.duanrong.business.invest.model.Invest;

/**
 * @author xiao
 * @date 2015年2月3日 下午12:45:21
 */
public interface AwardItemService {

	/**
	 * 
	 * @description 保存奖励项目
	 * @author 孙铮
	 * @time 2015-2-10 下午3:25:48
	 * @param awardItem
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String saveAwardItem(String loginUser, HttpServletRequest request);

	/**
	 * 
	 * @description 查询要发送的奖励用户是否存在
	 * @author 孙铮
	 * @time 2015-2-10 下午4:04:05
	 * @param request
	 * @return
	 */
	public String queryUser(HttpServletRequest request);

	/**
	 * 
	 * @description 查询创建奖励与借款项目有关时的,借款项目信息
	 * @author 孙铮
	 * @time 2015-2-10 下午4:04:05
	 * @param request
	 * @return
	 */
	public Object queryLoan(HttpServletRequest request);

	/**
	 * 奖励项目分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param awardItem
	 * @return
	 */
	public PageInfo<AwardItem> pageLite(int pageNo, int pageSize,
			AwardItem awardItem, String UserId);

	/**
	 * 奖励明细分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param awardItemUser
	 * @return
	 */
	public PageInfo<AwardItemUser> pageLiteForAwardUser(int pageNo,
			int pageSize, AwardItemUser awardItemUser);

	/**
	 * @description 生成发送时的验证码
	 * @author 孙铮
	 * @time 2015-3-2 上午10:56:03
	 * @param itemId
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean createSendVerifyCodeAndMaxAwardMoneyRestrict(int itemId);

	/**
	 * 
	 * @description 发送奖励
	 * @author 孙铮
	 * @time 2015-3-6 下午3:20:35
	 * @param itemId
	 * @param loginUser
	 * @return
	 */
	public String sendAward(String itemId);

	/**
	 * 删除奖励项目（逻辑）
	 * 
	 * @param itemId
	 * @return
	 */
	public boolean deleteAwardItem(int itemId);

	/**
	 * @description TODO
	 * @author 孙铮
	 * @time 2015-3-3 下午6:48:20
	 * @param itemId
	 */
	public void deleteWeiXin(int itemId);

	public String saveAwardItem5And10(String userId, HttpServletRequest request);

	/**
	 * 
	 * @description 修改跟投相关数据
	 * @date 2015-4-21
	 * @time 下午1:09:28
	 * @author SunZ
	 */
	public void followInvestAwardModify(String userId);

	/**
	 * @description 修改用户投资是否可享跟投
	 * @date 2015-4-21
	 * @time 下午1:40:42
	 * @author SunZ
	 * @param userId
	 */
	public void modifyFollowInvestMoney(String userId);
	/**
	 * add by wangjing
	 * @param map
	 * @return
	 */
	public List<AwardItem> getAwards(Map<String, Object> map);
}