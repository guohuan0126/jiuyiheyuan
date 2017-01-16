package com.duanrong.business.activity.dao;

import base.dao.BaseDao;

import com.duanrong.business.activity.model.Activity;
import com.duanrong.business.activity.model.RedPacketDetail;

/**
 * 活动参与人Dao
 * @author Qiu Feihu
 *
 */
public interface RedPacketDetailDao extends BaseDao<RedPacketDetail>{
	public void del(RedPacketDetail detail);
}
