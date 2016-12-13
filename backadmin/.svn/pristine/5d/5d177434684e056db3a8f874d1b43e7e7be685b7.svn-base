package com.duanrong.business.activity.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import util.RedisCacheKey;
import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.activity.dao.ActivityDao;
import com.duanrong.business.activity.model.Activity;
import com.duanrong.util.jedis.DRJedisCacheUtil;

@Repository
public class ActivityDaoImpl extends BaseDaoImpl<Activity> implements ActivityDao {
   
	public ActivityDaoImpl(){
		this.setMapperNameSpace("com.duanrong.business.activity.mapper.activityMapper");
	}
	
	@Override
	public void insert(Activity activity) {
		switch (activity.getType()){
			case "1":
				DRJedisCacheUtil.del(RedisCacheKey.ACTIVITIES);
				break;
			case "2":
				DRJedisCacheUtil.del(RedisCacheKey.SPECIAL_TOPIC);
				break;
			default:
		}
		super.insert(activity);
	}

	@Override
	public void update(Activity activity) {
		DRJedisCacheUtil.del(RedisCacheKey.ACTIVITIES);
		DRJedisCacheUtil.del(RedisCacheKey.SPECIAL_TOPIC);	
		super.update(activity);
	}

	@Override
	public void delete(Serializable id) {
		Activity activity = this.get(id);
		if(activity != null){
			switch (activity.getType()){
			case "1":
				DRJedisCacheUtil.del(RedisCacheKey.ACTIVITIES);
				break;
			case "2":
				DRJedisCacheUtil.del(RedisCacheKey.SPECIAL_TOPIC);
				break;
			default:
			}
			super.delete(id);
		}		
	}		
}
