package com.duanrong.dataAnalysis.business.loanDataT.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.dataAnalysis.business.loanDataT.dao.LoanDataTDao;
import com.duanrong.dataAnalysis.business.loanDataT.model.LoanDataT;

@Repository
public class LoanDataTDaoImpl extends BaseDaoImpl<LoanDataT> implements LoanDataTDao{

	public  LoanDataTDaoImpl() {
		this.setMapperNameSpace("com.duanrong.dataAnalysis.business.loanDataT.mapper.loanDataTMapper"); // 设置命名空间
	}

	@Override
	public LoanDataT getLoanDataT() {
		
		return this.getSqlSession().selectOne("getLoanDataT");
	}

	
	
}
