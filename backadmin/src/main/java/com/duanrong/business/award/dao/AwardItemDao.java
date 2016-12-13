package com.duanrong.business.award.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.award.model.AwardItem;
import com.duanrong.business.award.model.WeiXinActivity;
import com.duanrong.business.invest.model.Invest;

/**
 * @author xiao
 * @date 2015年2月3日 下午12:35:56
 */
public interface AwardItemDao extends BaseDao<AwardItem> {

	/**
	 * 插入奖励项目
	 * 
	 * @param awardItem
	 * @return
	 */
	public int insertAwardItem(AwardItem awardItem);

	/**
	 * @description 查看与借款项目有关的奖励项目是否已经发送过了
	 * @author 孙铮
	 * @time 2015-3-2 下午5:45:16
	 * @param loanId
	 */
	public List<AwardItem> isSendAward(AwardItem awardItem);

	/**
	 * 删除奖励项目（逻辑）
	 * 
	 * @param awardItemUser
	 * @return
	 */
	public int deleteAwardItemByItemId(int itemId);

	/**
	 * @description 获取微信奖励
	 * @author 孙铮
	 * @time 2015-3-3 下午3:30:15
	 * @param loanId
	 */
	public List<WeiXinActivity> getWeiXinActivity(WeiXinActivity weiXinActivity);

	/**
	 * @description 更新一条微信记录
	 * @author 孙铮
	 * @time 2015-3-3 下午4:12:29
	 * @param userId
	 */
	public void updateWeiXinActivity(WeiXinActivity weiXinActivity);

	/**
	 * 
	 * @description 查询百分比奖励是否创建
	 * @author 孙铮
	 * @time 2015-4-7 下午5:12:47
	 * @param id
	 * @param i
	 * @param string
	 * @param string2
	 * @return
	 */
	public AwardItem isCreatePercentageAwardItem(String loanId, int rate,
			String itemType, String moneyType);

	/**
	 * 
	 * @description 5%奖励查询条件(金额总和)
	 * @author 孙铮
	 * @time 2015-4-8 上午11:04:07
	 * @param userId
	 * @param registerTime
	 * @param endTime
	 * @param id
	 * @return
	 */
	public Double conditions5AwardBySumMoney(String userId, Date registerTime,
			Date endTime, String investId);

	/**
	 * 
	 * @description 5%奖励查询条件(投资集合)
	 * @author 孙铮
	 * @time 2015-4-8 上午11:16:24
	 * @param userId
	 * @param investStartTime
	 * @param investEndTime
	 */
	public List<Invest> conditions5AwardByInvests(String userId,
			Date investStartTime, Date investEndTime);

	/**
	 * 查询跟投奖励是否创建
	 * 
	 * @param loanId
	 * @return
	 */
	public AwardItem isCreateFollowInvestAwardItem(String loanId, String status);

	public List<Invest> findFollowInvestAward(String userId);

	/**
	 * @description 查询投资状态是还款中,跟投状态为N的投资记录
	 * @date 2015-4-21
	 * @time 下午1:42:48
	 * @author SunZ
	 */
	public List<Invest> findInvestByFollowStatusAndInvestStatus(String userId);
	/**
	 * add by wangjing
	 * @param map
	 * @return
	 */
	List<AwardItem> getAwards(Map<String, Object> map);
	/**
	 * 更改单个用户奖励金额后修改奖励项目的总金额
	 * @param map
	 * @return
	 */
	public int updateAawarditemMoney(Map<String,Object> map);


}
