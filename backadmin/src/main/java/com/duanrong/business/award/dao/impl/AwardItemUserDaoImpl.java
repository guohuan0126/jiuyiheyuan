package com.duanrong.business.award.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.award.dao.AwardItemUserDao;
import com.duanrong.business.award.model.AwardItemUser;

@Repository
public class AwardItemUserDaoImpl extends BaseDaoImpl<AwardItemUser> implements
		AwardItemUserDao {

	public AwardItemUserDaoImpl() {
		super.setMapperNameSpace("com.duanrong.business.award.mapper.AwardItemUserMapper");
	}

	@Override
	public List<AwardItemUser> getAwardItemUser(String itemId) {
		return this.getSqlSession().selectList(
				getMapperNameSpace() + ".getAwardItemUser", itemId);
	}

	@Override
	public int insertAwardItemUser(AwardItemUser awardItemUser) {

		return this.getSqlSession().insert(
				getMapperNameSpace() + ".insertAwardItemUser", awardItemUser);
	}

	@Override
	public int deleteAwardItemUser(int id) {
		
		return this.getSqlSession().update(
				getMapperNameSpace() + ".deleteAwardItemUser", id);
	}

	@Override
	public int updateawarditemuserMoney(Map<String, Object> map) {
		return this.getSqlSession().update(
				getMapperNameSpace() + ".updateawarditemuserMoney", map);
	}
}
