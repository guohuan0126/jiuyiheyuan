package com.duanrong.business.loan.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.dictionary.model.Dictionary;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.BannerPicture;
import com.duanrong.business.loan.model.FixedBorrowers;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.netloaneye.model.NetLoanModel;
import com.duanrong.business.netloaneye.model.P2PEyeLoan;
import com.duanrong.business.user.model.User;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 下午12:45:16
 * @Description : drsoa com.duanrong.business.loan.dao LoanDao.java
 * 
 */
public interface LoanDao extends BaseDao<Loan> {

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	public List<Loan> getLoansByGroupCondition(Loan loan);
	
	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	public List<Loan> getLoansByGroupCondition1(Map<String, Object> map);

	/**
	 * 
	 * @description 根据loanID查询对应借款人信息
	 * @author 孙铮
	 * @time 2014-8-28 下午5:40:44
	 * @param loanID
	 * @return
	 */
	public User getUserByLoanID(String loanID);

	/**
	 * 
	 * @description 根据loanID获取项目图片资料
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> getLoanInfoPics(String loanID);

	/**
	 * 
	 * @description 根据loanID获取抵押物图片
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> getLoanGuaranteePics(String loanID);

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:01:22
	 * @param invest
	 * @return
	 */
	public List<Invest> getInvestsByGroupCondition(Invest invest);

	/**
	 * 个人项目分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 * @param entity
	 *            条件
	 */
	public PageData<Loan> findPaging4Personal(int pageNo, int pageSize,
			Loan loan);

	/**
	 * 
	 * @description 保存项目图片
	 * @author 孙铮
	 * @time 2015-3-11 下午7:41:47
	 * @param bannerPicture
	 */
	public void insertBannerPicture(BannerPicture bannerPicture);

	/**
	 * @description TODO
	 * @author 孙铮
	 * @time 2015-3-12 下午4:53:15
	 * @param string
	 * @return
	 */
	public List<Loan> generateId(String str);

	/**
	 * @description TODO
	 * @author 孙铮
	 * @time 2015-3-18 上午11:16:00
	 * @param id
	 * @param loanId
	 */
	public void insertLoanInfoPics(String bpId, String loanId);

	/**
	 * 
	 * @description 查询一条图片信息
	 * @author 孙铮
	 * @time 2015-3-20 下午12:15:10
	 * @param picId
	 * @return
	 */
	public BannerPicture getBannerPictureById(String picId);

	/**
	 * 
	 * @description 更新一条图片信息
	 * @author 孙铮
	 * @time 2015-3-20 下午12:18:04
	 * @param bannerPicture
	 */
	public void updateBannerPicture(BannerPicture bannerPicture, String loanId);

	/**
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<FixedBorrowers> findAllFixedBorrowers();

	/**
	 * @param userId
	 */
	public void insertFixedBorrower(String userId, String email);

	/**
	 * @description 修改固定借款人状态
	 * @date 2015-4-22
	 * @time 下午3:19:45
	 * @author SunZ
	 * @param borrower
	 *            借款人id
	 * @param status
	 *            状态(off=关闭,open=关闭)
	 */
	public void alterFixedBorrowersStatus(String borrower, String status, String email);
	public Double getTotalMoney(Map map);

	/**
	 * 查询可以推送网贷天眼的项目列表
	 * @param loan
	 * @return
	 */
	public List<Loan> getWaitPushLoanList(Loan loan);

	public List<Loan> getPushRecords(Loan loan);

	public Loan getP2pEyeLoanByLoanId(String id);

	public NetLoanModel getNetLoanCount(Loan loan);

	public NetLoanModel getNetLoanSum(Loan loan);

	public int updateLoan(Loan loan);
	
	PageInfo<Loan> pageList(int pageNo, int pageSize, Loan loan);
	/**
	 * 获取项目名称，取后五位的最大值
	 * @return
	 */
	public String getLoanName();
	/**
	 * 批量修改项目图片的名称
	 * @param params
	 */
	public int updateBatchPicTitle(Map<String,Object> params, String loadId );

	/**更新项目排序编号
	 * @param loan
	 */
	public void updateSortNum(Loan loan);

	/**
	 * @param subLoanId
	 * @return
	 */
	public List<Loan> getSubLoan(String subLoanId);

	/**
	 * @param loans
	 */
	public void updateBatchLoan(List<Loan> loans);

}