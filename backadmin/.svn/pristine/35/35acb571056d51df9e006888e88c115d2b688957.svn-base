package com.duanrong.business.ruralfinance.dao.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.dao.AgricultureLoaninfoDao;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.UploadPrepaymentUser;
import com.duanrong.business.ruralfinance.model.UploadUser;
import com.duanrong.business.ruralfinance.model.UploadZhongjinUser;
@Repository
public class AgricultureLoaninfoDaoImpl<T> extends BaseDaoImpl<AgricultureLoaninfo> implements AgricultureLoaninfoDao {

	public AgricultureLoaninfoDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.ruralfinance.mapper.AgricultureLoaninfoMapper");
	}
	
	/**
	 * 保存借款人信息
	 */

	@Override
	public int saveAgricultureLoaninfo(Object obj, String type) {
		return this.getSqlSession().insert(this.getMapperNameSpace() +".saveAgricultureLoaninfo", obj);
	}
	/**
	 * 根据条件查询记录
	 */
	@Override
	public AgricultureLoaninfo findByCondition(Map map) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".findByCondition", map);
	}

	@Override
	public PageInfo<UploadUser> findUploadUser(int  pageNo,int  pageSize,String type) {
		PageHelper.startPage(pageNo, pageSize);
	List list = 	this.getSqlSession().selectList(this.getMapperNameSpace()+".findUploadUser", type);
		PageInfo<UploadUser> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@Override
	public int saveExcle(Object obj, String type) {
		return this.getSqlSession().insert(this.getMapperNameSpace() +".saveExcle", obj);
	}

	/**
	 *  获取所有的合同编号
	 */
	@Override
	public List<String> getAllContractId() {
		
	List<String> list = this.getSqlSession().selectList(this.getMapperNameSpace()+".getAllContractId");
	
	
		return list;
	}

	@Override
	public PageInfo readUploadZhongjinUser(int pageNo, int pageSize, String type) {
		PageHelper.startPage(pageNo, pageSize);
		List list = this.getSqlSession().selectList(this.getMapperNameSpace()+".finduploadZJUser", type);
		PageInfo<UploadZhongjinUser> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public PageInfo readUploadPrepaymentUser(int pageNo, int pageSize,
			String type) {
		PageHelper.startPage(pageNo, pageSize);
		List list = this.getSqlSession().selectList(this.getMapperNameSpace()+".findUploadPrepaymentUser", type);
		PageInfo<UploadPrepaymentUser> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
}