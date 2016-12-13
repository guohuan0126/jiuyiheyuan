package com.duanrong.business.award.service;
import java.util.Map;

import com.duanrong.business.award.model.AwardItemUser;


/**
 * @author xiao
 * @date 2015年2月3日 下午12:45:21
 */
public interface AwardItemUserService {

	/**
	 * 删除单个奖励用户
	 * @param id
	 * @return
	 */
	public int deleteAwardItemUser(int id);
	/**
	 * 修改用户奖励用户金额
	 * @param awardItem
	 * @return
	 */
	public void update(AwardItemUser awardItemUser);
	/** 
	 * 获取奖励用户的详细的信息
	 * @param id
	 * @return
	 */
	public AwardItemUser readAwardItemUser(int id);
	
	/**
	 * 更改单个用户奖励金额后修改奖励项目的总金额
	 * @param map
	 * @return
	 */
	public int updateawarditemuserMoney(Map<String,Object> map);
}