package base.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import base.dao.BaseDao;
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

	@Override
	public List<T> findAll() {
		List<T> oList = this.getSqlSession().selectList(
				mapperNameSpace + ".findAll");
		return oList;
	}

	@Override
	public List<T> find(T entiy) {
		List<T> oList = this.getSqlSession().selectList(
				mapperNameSpace + ".find", entiy);
		return oList;
	}

	@Override
	public T get(Serializable id) {
		return this.getSqlSession().selectOne(mapperNameSpace + ".get", id);
	}

	@Override
	public void insert(T entity) {
		this.getSqlSession().insert(mapperNameSpace + ".insert", entity);
	}

	@Override
	public void update(T entity) {
		this.getSqlSession().update(mapperNameSpace + ".update", entity);
	}

	@Override
	public void delete(Serializable id) {
		this.getSqlSession().delete(mapperNameSpace + ".delete", id);
	}

	@Override
	public void deleteBatch(Serializable[] ids) {
		this.getSqlSession().delete(mapperNameSpace + ".deleteBatch", ids);
	}

	@Override
	public Long getCount(T entity) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCount", entity);
	}

	@Override
	public PageInfo<T> pageLite(int pageNo, int pageSize, T entity) {
		PageHelper.startPage(pageNo, pageSize);
		List<T> list = getSqlSession().selectList(mapperNameSpace + ".pageLite",
				entity);
		PageInfo<T> pageInfo = new PageInfo<>(list);
		if(pageNo>pageInfo.getLastPage()){
			pageInfo.setResults(null);
		}
		return pageInfo;
	}
}
