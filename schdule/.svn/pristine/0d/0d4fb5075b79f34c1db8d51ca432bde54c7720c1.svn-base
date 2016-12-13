package com.duanrong.business.activity.dao.impl;

import org.springframework.stereotype.Repository;
import base.dao.impl.BaseDaoImpl;
import com.duanrong.business.activity.dao.RedPacketDetailDao;
import com.duanrong.business.activity.model.RedPacketDetail;

@Repository
public class RedPacketDetailImpl extends BaseDaoImpl<RedPacketDetail> implements RedPacketDetailDao {
    
   public RedPacketDetailImpl(){
	   this.setMapperNameSpace("com.duanrong.business.activity.mapper.redPacketDetailMapper");
   }

@Override
public void del(RedPacketDetail detail) {
	getSqlSession().update(
			getMapperNameSpace() + ".del", detail);	
}
}
