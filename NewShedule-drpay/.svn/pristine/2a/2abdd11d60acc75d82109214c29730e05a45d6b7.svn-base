package base.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import base.dao.BaseDao;
import base.model.PageData;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

/**
 * @Description: 所有DAO实现类必须继承此类，提供增删改查的方法，继承此类需要设置命名空间
 * @Author: 林志明
 * @CreateDate: Aug 28, 2014
 */
public abstract class BaseDaoImpl<T> extends SqlSessionDaoSupport implements
		BaseDao<T> {
	// mybatis-spring 1.0无需此方法；mybatis-spring1.2必须注入。
	@Autowired
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

	public PageData<T> findPaging(int pageNo, int pageSize, T entity) {
		PageData<T> pageData = new PageData<T>(pageNo, pageSize);
		pageData.getParams().put("entity", entity);
		List<T> results = getSqlSession().selectList(
				mapperNameSpace + ".findPaging", pageData);
		pageData.setResults(results);
		return pageData;
	}

	public List<T> findAll() {
		List<T> oList = this.getSqlSession().selectList(
				mapperNameSpace + ".findAll");
		return oList;
	}

	public List<T> find(T entiy) {
		List<T> oList = this.getSqlSession().selectList(
				mapperNameSpace + ".find", entiy);
		return oList;
	}

	public T get(Serializable id) {
		return this.getSqlSession().selectOne(mapperNameSpace + ".get", id);
	}

	public void insert(T entity) {
		this.getSqlSession().insert(mapperNameSpace + ".insert", entity);
	}

	public void update(T entity) {
		this.getSqlSession().update(mapperNameSpace + ".update", entity);
	}

	public void delete(Serializable id) {
		this.getSqlSession().delete(mapperNameSpace + ".delete", id);
	}

	public void deleteBatch(Serializable[] ids) {
		this.getSqlSession().delete(mapperNameSpace + ".deleteBatch", ids);
	}

	public Long getCount(T entity) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCount", entity);
	}

	public PageData<T> findVerbosePaging(int pageNo, int pageSize, T entity) {
		long count = getCount(entity);
		PageData<T> pageData = new PageData<T>(pageNo, pageSize);
		pageData.setTotalRecord((int) count);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		int start = 1;
		if (pageNo == 1) {
			start = 0;
		} else {
			start = (pageNo - 1) * pageSize;
		}
		map.put("start", start);
		map.put("pageSize", pageSize);
		List<T> results = getSqlSession().selectList(
				mapperNameSpace + ".findVerbosePaging", map);
		pageData.setResults(results);
		return pageData;
	}

	@Override
	public PageInfo<T> pageLite(int pageNo, int pageSize, T entity) {
		PageHelper.startPage(pageNo, pageSize);
		List<T> list = getSqlSession().selectList(
				mapperNameSpace + ".pageLite", entity);
		PageInfo<T> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<T> pageLite4Map(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<T> list = getSqlSession().selectList(
				mapperNameSpace + ".pageLite4Map", params);
		PageInfo<T> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
}
