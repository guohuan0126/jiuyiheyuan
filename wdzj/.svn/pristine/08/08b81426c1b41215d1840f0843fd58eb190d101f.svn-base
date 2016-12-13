package base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import base.model.Invest;
import base.model.Loan;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-11-12 下午12:53:12
 * @Description : template base.dao BaseDao.java
 * 
 */
public interface BaseDao<T> {
	public List<T> findAll();

	public List<T> findAllByYHBY();

	public List<T> find(T t);

	public List<Invest> getInvestsByLoanID(Serializable id);

	public void setMapperNameSpace(String mapperNameSpace);

	/**
	 * 获得某项目已经募集的金额
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-12下午6:20:00
	 * @param loanId
	 * @return
	 */
	public double getInvestedMoney(String loanId);

	/**
	 * 根据条件查询loan集合
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-13下午5:42:02
	 * @return
	 */
	public List<Loan> findAllLoans(Map map);

	/**
	 * 获取网贷天眼所需要的投资集合列表
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-13下午6:37:58
	 * @param map
	 * @return
	 */
	public List<Invest> getWdtyInvestsByLoanId(Map<Object, Object> map);

	/**
	 * 获取某个项目的投资次数
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-14下午1:17:31
	 * @param loanId
	 * @return
	 */
	public long getInvestedNum(String loanId);

	/**
	 * @description TODO
	 * @date 2015-5-5 
	 * @time 下午2:20:18
	 * @author SunZ
	 * @return
	 */
	public long findAllLoansSize();

	/**
	 * @description TODO
	 * @date 2015-5-5 
	 * @time 下午6:29:13
	 * @author SunZ
	 * @return
	 */
	public long findLoansByTime(String time_from, String time_to);

}
