package com.duanrong.business.award.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;

import com.duanrong.business.award.model.AwardItemUser;

public interface AwardItemUserDao extends BaseDao<AwardItemUser> {

	/**
	 * @description 查询某个奖励下明细为未发送的用户
	 * @author 孙铮
	 * @time 2015-3-2 下午1:51:28
	 * @param itemId
	 */
	public List<AwardItemUser> getAwardItemUser(String itemId);

	/**
	 * 插入奖励明细
	 * 
	 * @param awardItemUser
	 * @return
	 */
	public int insertAwardItemUser(AwardItemUser awardItemUser);
	
	/**
	 * 删除单个奖励用户
	 * @param id
	 * @return
	 */
	public int deleteAwardItemUser(int id);
	
	/**
	 * 更改单个用户奖励金额后修改奖励项目的总金额
	 * @param map
	 * @return
	 */
	public int updateawarditemuserMoney(Map<String,Object> map);
}
