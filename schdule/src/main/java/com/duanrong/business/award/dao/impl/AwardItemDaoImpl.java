package com.duanrong.business.award.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.award.dao.AwardItemDao;
import com.duanrong.business.award.model.AwardItem;
import com.duanrong.business.award.model.WeiXinActivity;
import com.duanrong.business.invest.model.Invest;

/**
 * @author xiao
 * @date 2015年2月3日 下午12:37:50
 */

@Repository
public class AwardItemDaoImpl extends BaseDaoImpl<AwardItem> implements
		AwardItemDao {

	public AwardItemDaoImpl() {
		super.setMapperNameSpace("com.duanrong.business.award.mapper.AwardItemMapper");
	}

	public int insertAwardItem(AwardItem awardItem) {

		return this.getSqlSession().insert(
				getMapperNameSpace() + ".insertAwardItem", awardItem);
	}

	@Override
	public List<AwardItem> isSendAward(AwardItem awardItem) {
		return this.getSqlSession().selectList(
				getMapperNameSpace() + ".isSendAward", awardItem);
	}

	@Override
	public int deleteAwardItemByItemId(int itemId) {

		return this.getSqlSession().update(
				getMapperNameSpace() + ".deleteAwardItemById", itemId);
	}

	@Override
	public List<WeiXinActivity> getWeiXinActivity(WeiXinActivity weiXinActivity) {
		return this.getSqlSession().selectList(
				getMapperNameSpace() + ".getWeiXinActivity", weiXinActivity);
	}

	@Override
	public void updateWeiXinActivity(WeiXinActivity weiXinActivity) {
		this.getSqlSession().update(
				getMapperNameSpace() + ".updateWeiXinActivity", weiXinActivity);
	}

	@Override
	public AwardItem isCreatePercentageAwardItem(String loanId, int rate,
			String itemType, String moneyType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanId", loanId);
		map.put("rate", rate);
		map.put("itemType", itemType);
		map.put("moneyType", moneyType);
		return this.getSqlSession().selectOne(
				getMapperNameSpace() + ".isCreatePercentageAwardItem", map);
	}

	@Override
	public Double conditions5AwardBySumMoney(String userId, Date registerTime,
			Date endTime, String investId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("registerTime", registerTime);
		map.put("endTime", endTime);
		map.put("investId", investId);
		return this.getSqlSession().selectOne(
				getMapperNameSpace() + ".conditions5AwardBySumMoney", map);
	}

	@Override
	public List<Invest> conditions5AwardByInvests(String userId,
			Date investStartTime, Date investEndTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("investStartTime", investStartTime);
		map.put("investEndTime", investEndTime);
		return this.getSqlSession().selectList(
				getMapperNameSpace() + ".conditions5AwardByInvests", map);
	}

	@Override
	public AwardItem isCreateFollowInvestAwardItem(String loanId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanId", loanId);
		map.put("status", status);
		return this.getSqlSession().selectOne(
				getMapperNameSpace() + ".isCreateFollowInvestAwardItem", map);
	}

	@Override
	public List<Invest> findFollowInvestAward(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return this.getSqlSession().selectList(
				getMapperNameSpace() + ".findFollowInvestAward", map);
	}

	@Override
	public List<Invest> findInvestByFollowStatusAndInvestStatus(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return this.getSqlSession().selectList(
				getMapperNameSpace()
						+ ".findInvestByFollowStatusAndInvestStatus", map);
	}

	@Override
	public List<AwardItem> getAwards(Map<String, Object> map) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getAwards", map);
	}

}
