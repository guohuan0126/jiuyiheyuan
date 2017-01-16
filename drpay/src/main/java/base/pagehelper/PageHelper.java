package base.pagehelper;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * Mybatis - 通用分页拦截器
 * 
 * @author liuzh/abel533/isea533
 * @version 3.3.0 项目地址 : http://git.oschina.net/free/Mybatis_PageHelper
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }))
public class PageHelper implements Interceptor {
	// sql工具类
	private SqlUtil sqlUtil;

	/**
	 * 开始分页
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页显示数量
	 */
	public static Page<?> startPage(int pageNum, int pageSize) {
		return startPage(pageNum, pageSize, true);
	}

	/**
	 * 开始分页
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页显示数量
	 * @param orderBy
	 *            针对sqlserver - 建议在sql中直接包含order by
	 */
	public static Page<?> startPage(int pageNum, int pageSize, String orderBy) {
		return startPage(pageNum, pageSize, true).orderBy(orderBy);
	}

	/**
	 * 开始分页
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页显示数量
	 * @param count
	 *            是否进行count查询
	 */
	public static Page<?> startPage(int pageNum, int pageSize, boolean count) {
		return startPage(pageNum, pageSize, count, null);
	}

	/**
	 * 开始分页
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页显示数量
	 * @param count
	 *            是否进行count查询
	 * @param reasonable
	 *            分页合理化,null时用默认配置
	 */
	public static Page<?> startPage(int pageNum, int pageSize, boolean count,
			Boolean reasonable) {
		return startPage(pageNum, pageSize, count, reasonable, null);
	}

	/**
	 * 开始分页
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页显示数量
	 * @param count
	 *            是否进行count查询
	 * @param reasonable
	 *            分页合理化,null时用默认配置
	 * @param pageSizeZero
	 *            true且pageSize=0时返回全部结果，false时分页,null时用默认配置
	 */
	public static Page<?> startPage(int pageNum, int pageSize, boolean count,
			Boolean reasonable, Boolean pageSizeZero) {
		Page<?> page = new Page<>(pageNum, pageSize, count);
		page.setReasonable(reasonable);
		page.setPageSizeZero(pageSizeZero);
		SqlUtil.setLocalPage(page);
		return page;
	}

	/**
	 * 开始分页
	 * 
	 * @param params
	 *            只能是Map或ServletRequest类型
	 */
	public static Page<?> startPage(Object params) {
		Page<?> page = SqlUtil.getPageFromObject(params);
		SqlUtil.setLocalPage(page);
		return page;
	}

	/**
	 * Mybatis拦截器方法
	 * 
	 * @param invocation
	 *            拦截器入参
	 * @return 返回执行结果
	 * @throws Throwable
	 *             抛出异常
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		return sqlUtil.processPage(invocation);
	}

	/**
	 * 只拦截Executor
	 * 
	 * @param target
	 * @return
	 */
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param p
	 *            属性值
	 */
	public void setProperties(Properties p) {
		// 数据库方言
		String dialect = p.getProperty("dialect");
		sqlUtil = new SqlUtil(dialect);
		sqlUtil.setProperties(p);
	}
}
