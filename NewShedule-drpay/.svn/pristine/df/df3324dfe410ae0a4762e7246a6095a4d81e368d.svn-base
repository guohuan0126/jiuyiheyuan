package base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import base.model.PageData;
import base.pagehelper.PageInfo;

/**
 * @Description: 所有DAO接口必须继承此接口
 * @Author: 林志明
 * @CreateDate: Aug 28, 2014
 */
public interface BaseDao<T> {
	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 * @param entity
	 *            条件
	 */
	public PageInfo<T> pageLite(int pageNo, int pageSize, T entity);

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 * @param entity
	 *            条件
	 */
	@Deprecated
	public PageData<T> findPaging(int pageNo, int pageSize, T entity);

	/**
	 * 获得表总计录数
	 * 
	 * @param entity
	 * @return
	 */
	public Long getCount(T entity);

	/**
	 * 分页查询啰嗦版
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	public PageData<T> findVerbosePaging(int pageNo, int pageSize, T entity);

	public List<T> findAll();

	public List<T> find(T entity);

	public T get(Serializable id);

	public void insert(T entity);

	public void update(T entity);

	public void delete(Serializable id);

	public void deleteBatch(Serializable[] ids);

	public PageInfo<T> pageLite4Map(int pageNo, int pageSize,
			Map<String, Object> params);

}
