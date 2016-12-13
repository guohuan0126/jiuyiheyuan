package com.duanrong.business.msg.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.msg.dao.EmailDao;
import com.duanrong.business.msg.model.Email;


@Repository
public class EmailDaoImpl extends BaseDaoImpl<Email> implements EmailDao {

	public EmailDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.msg.mapper.EmailMapper");
	}
	@Override
	public PageInfo<Email> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<Email> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<Email> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	@Override
	public List<Email> getEmailByLoadId(String loanId) {	
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getEmailByLoanId", loanId);
	}
	@Override
	public int deleteEmailByLoadId(String loanId) {
		
		return getSqlSession().update(
				getMapperNameSpace() + ".deleteEmailByLoanId", loanId);
	}
	@Override
	public Email getEmailById(String id) {		
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getEmailById", id);
	}
	@Override
	public String getConfigById(String id) {		
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getConfigById", id);
	}
	@Override
	public void sendEamil(Email email) {
		getSqlSession().update(
				getMapperNameSpace() + ".senEmail", email);
	}
	@Override
	public List<Email> getSendEmailByLoanId(String loanId) {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getSendEmailByLoanId", loanId);
	}
}
