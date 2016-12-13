package base.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import base.dao.BaseDao;
import base.model.Invest;
import base.model.Loan;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-11-12 下午12:53:20
 * @Description : template base.dao.impl BaseDaoImpl.java
 * 
 */
@Repository
public class BaseDaoImpl<T> extends SqlSessionDaoSupport implements BaseDao<T> {
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	private String mapperNameSpace; // 命名空间

	public String getMapperNameSpace() {
		return mapperNameSpace;
	}

	public void setMapperNameSpace(String mapperNameSpace) {
		this.mapperNameSpace = mapperNameSpace;
	}

	public List<T> findAll() {
		List<T> ts = this.getSqlSession().selectList(
				mapperNameSpace + "findAll");
		return ts;
	}

	public List<T> findAllByYHBY() {
		List<T> ts = this.getSqlSession().selectList(
				mapperNameSpace + "findAllByYHBY");
		return ts;
	}

	public List<T> find(T t) {
		List<T> ts = this.getSqlSession().selectList(mapperNameSpace + "find",
				t);
		return ts;
	}

	public List<Invest> getInvestsByLoanID(Serializable id) {
		return this.getSqlSession().selectList(
				mapperNameSpace + "getInvestsByLoanID", id);
	}

	public double getInvestedMoney(String loanId) {
		Object o = this.getSqlSession().selectOne(
				mapperNameSpace + "getInvestedMoney", loanId);
		if (o == null) {
			return 0D;
		} else {
			return (double) o;
		}
	}

	@Override
	public List<Loan> findAllLoans(Map map) {
		return this.getSqlSession().selectList(
				mapperNameSpace + "findAllLoans", map);
	}

	@Override
	public List<Invest> getWdtyInvestsByLoanId(Map<Object, Object> map) {
		return this.getSqlSession().selectList(
				mapperNameSpace + "getWdtyInvestsByLoanId", map);
	}

	@Override
	public long getInvestedNum(String loanId) {
		Object o = this.getSqlSession().selectOne(
				mapperNameSpace + "getWdtyInvestedNum", loanId);
		if (o == null) {
			return 0L;
		} else {
			return (long) o;
		}
	}

	@Override
	public long findAllLoansSize() {
		Object o = this.getSqlSession().selectOne(
				mapperNameSpace + "findAllLoansSize");
		if (o == null) {
			return 0L;
		} else {
			return (long) o;
		}
	}

	@Override
	public long findLoansByTime(String time_from, String time_to) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("time_from", time_from);
		map.put("time_to", time_to);
		Object o = this.getSqlSession().selectOne(
				mapperNameSpace + "findLoansByTime", map);
		if (o == null) {
			return 0L;
		} else {
			return (long) o;
		}
	}

}
