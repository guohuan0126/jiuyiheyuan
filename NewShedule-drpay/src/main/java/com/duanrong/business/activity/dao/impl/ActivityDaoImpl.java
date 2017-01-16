package com.duanrong.business.activity.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.activity.dao.ActivityDao;
import com.duanrong.business.activity.model.Activity;

@Repository
public class ActivityDaoImpl extends BaseDaoImpl<Activity> implements ActivityDao {
   
	public ActivityDaoImpl(){
		this.setMapperNameSpace("com.duanrong.business.activity.mapper.activityMapper");
	}
	
}
