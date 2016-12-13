package com.duanrong.business.loan.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import base.exception.InsufficientBalance;
import base.exception.InvestMoneyException;
import base.exception.NoMatchingObjectsException;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.dictionary.model.Dictionary;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.ApplyEnterpriseLoan;
import com.duanrong.business.loan.model.BannerPicture;
import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.FixedBorrowers;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.LoanProgress;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.user.model.User;
import com.duanrong.newadmin.exception.InvalidExpectTimeException;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 下午12:33:30
 * @Description : drsoa com.duanrong.business.loan.service LoanService.java
 * 
 */
public interface LoanService {

	/**
	 * 借款到预计执行时间，处理借款
	 * 
	 * @param loanId
	 *            项目ID
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void dealOverExpectTime(String loanId);

	/**
	 * 获得项目状态转换的日期
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public Map<String, Object> readLoanDetailDate(String loanId);

	/**
	 * 根据项目类型获得项目融资进度
	 * 
	 * @param loanType
	 *            项目类型
	 * @return
	 */
	public LoanProgress readLoanProgress(String loanType);

	/**
	 * 今日可投金额
	 * 
	 * @return
	 */
	public Double readRemainingInvestmentAmount();

	/**
	 * 项目详细信息
	 * 
	 * @param loan
	 * @return
	 */
	public Loan readLoanDetail(Loan loan);

	/**
	 * 车押宝详细信息
	 * 
	 * @param loanId
	 * @return
	 */
	public Vehicle readVehicleDetail(String loanId);

	/**
	 * 房押宝详细信息
	 * 
	 * @param loanId
	 * @return
	 */
	public House readHouseDetail(String loanId);

	/**
	 * 车押宝详细信息
	 * 
	 * @param loanId
	 * @return
	 */
	public Enterprise readEnterpriseDetail(String loanId);

	/**
	 * 申请企业借款
	 * 
	 * @param applyEnterpriseLoan
	 *            企业借款对象
	 */
	public void insertEnterpriseLoan(ApplyEnterpriseLoan applyEnterpriseLoan);

	/**
	 * 验证投资金额是否符合要求，主要是递增金额，其次是起点金额
	 * 
	 * @param loanId
	 * @param money
	 * @throws InvestMoneyException
	 */
	public void verifyInvestMoney(String loanId, Double money)
			throws InvestMoneyException;

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 */
	public PageData<Loan> readPaging(int pageNo, int pageSize, Loan loan);

	/**
	 * 
	 * @description 查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:36:56
	 * @param loan
	 * @return
	 */
	public List<Loan> readAll();

	/**
	 * 
	 * @description 根据id查询单条借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:37:14
	 * @param id
	 * @return
	 */
	public Loan read(String id);

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param 将查询条件封装到loan对象中
	 * @return
	 */
	public List<Loan> readLoansByGroupCondition(Loan loan);

	
	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author xiao
	 * @time 2014-8-28 下午5:29:29
	 * @param 将查询条件封装到loan对象中
	 * @return
	 */
	public List<Loan> readLoansByGroupCondition1(Map<String, Object> map);

	
	/**
	 * 
	 * @description 根据loanID查询对应借款人信息
	 * @author 孙铮
	 * @time 2014-8-28 下午5:40:44
	 * @param loanID
	 * @return
	 */
	public User readUserByLoanID(String loanID);

	/**
	 * 
	 * @description 根据loanID获取项目图片
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> readLoanInfoPics(String loanID);

	/**
	 * 
	 * @description 根据loanID获取抵押物图片
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> readLoanGuaranteePics(String loanID);

	/**
	 * 更新
	 * 
	 * @param loan
	 */
	public void update(Loan loan);

	/**
	 * 处理借款募集完成
	 * 
	 * @param loanId
	 *            借款ID
	 */
	public void dealRaiseComplete(String loanId)
			throws NoMatchingObjectsException;

	/**
	 * 计算借款尚未募集的金额
	 * 
	 * @param loanId
	 *            借款ID
	 * @return 尚未募集的金额
	 */
	public double calculateMoneyNeedRaised(String loanId);

	/**
	 * 
	 * @description 页面投资进度
	 * @author 孙铮
	 * @time 2014-9-3 下午5:43:50
	 * @param loanId
	 * @return
	 */
	public double calculateRaiseCompletedRate(String loanId)
			throws NoMatchingObjectsException;

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:02:28
	 * @param invest
	 * @return
	 */
	public List<Invest> readInvestsByGroupCondition(Invest invest);

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
	public PageData<Loan> readPaging4Personal(int pageNo, int pageSize,
			Loan loan);

	/**
	 * 检查借款是否到预计执行时间
	 * 
	 * @param loanId
	 *            借款ID
	 */
	public boolean isOverExpectTime(String loanId);

	/**
	 * 分页
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loan
	 * @return
	 */
	public PageInfo<Loan> readPageLite(int pageNo, int pageSize, Loan loan);

	/**
	 * 检查借款是否到预计执行时间
	 * 
	 * @param loan
	 */
	public boolean isOverExpectTime(Loan loan);

	/**
	 * 
	 * @description (创建借款项目)查询借款人信息
	 * @author 孙铮
	 * @time 2015-3-11 下午2:00:59
	 * @param userId
	 * @return
	 */
	public String readBorrowerMessageByUserId(String param);

	/**
	 * 
	 * @description 上传项目资料(图片,和合同文本.pdf)
	 * @author 孙铮
	 * @time 2015-3-11 下午6:20:12
	 * @param files
	 * @param request
	 */
	public void uploadLoanData(CommonsMultipartFile[] files,
			HttpServletRequest request);

	/**
	 * 
	 * @description 保存项目图片
	 * @author 孙铮
	 * @time 2015-3-11 下午7:41:47
	 * @param bannerPicture
	 */
	public void insertBannerPicture(BannerPicture bannerPicture, String loanId);

	/**
	 * 
	 * @description 保存借款项目
	 * @author 孙铮
	 * @time 2015-3-12 下午3:24:35
	 * @param loan
	 *            对象
	 * @param uuid
	 *            主要用于找到上传的项目资料相关标记
	 */
	public Object createLoan(HttpServletRequest request, String verifyUser)
			throws InvalidExpectTimeException, InsufficientBalance;

	/**
	 * @description 保存借款明细
	 * @author 孙铮
	 * @time 2015-3-13 下午2:52:40
	 * @param request
	 * @param userId
	 * @return
	 */
	public Object createLoanDetail(HttpServletRequest request);

	/**
	 * @description 保存金农宝借款人明细
	 * @author YUMIN
	 * @time 2015-12-6 下午4:52:40
	 * @param request
	 * @param userId
	 * @return
	 */
	public Object createRuralfinanceLoaners(HttpServletRequest request);
	/**
	 * 
	 * @description 更新图片
	 * @author 孙铮
	 * @time 2015-3-20 上午11:44:38
	 * @param op
	 * @param picId
	 * @param loanId
	 * @return
	 */
	public void updateImage(String op, String picId, String loanId);

	/**
	 * 
	 * @description 执行本地放款操作,主要针对正常放款操作中,易宝处理成功,背地处理失败后,进行补救措施的放款
	 * @author 孙铮
	 * @time 2015-4-2 下午11:07:21
	 * @param userId
	 *            执行人
	 * @param loanId
	 *            借款项目id
	 * @param actionTime
	 *            执行时间
	 * @param actionMessage
	 *            是否发送站内信或短信
	 * @return
	 */
	/*public String localGiveMoneyToBorrower(String userId, String loanId,
			String actionTime, String actionMessage);*/

	public String cancelByInvestId(String investId);

	/**
	 * @param picId
	 * @param seqNum
	 */
	public void updateImageSeqNum(String picId, String seqNum);

	/**
	 * 
	 * @description 查询所有固定借款人
	 * @date 2015-4-22
	 * @time 下午3:18:34
	 * @author SunZ
	 * @return 固定借款人集合
	 */
	public List<FixedBorrowers> readAllFixedBorrowers();

	/**
	 * 
	 * @description 添加固定借款人
	 * @date 2015-4-22
	 * @time 下午3:18:15
	 * @author SunZ
	 * @param userId
	 *            要添加的固定借款人id
	 */
	public void addFixedBorrower(String userId, String email);

	/**
	 * @description 修改固定借款人状态
	 * @date 2015-4-22
	 * @time 下午3:16:33
	 * @author SunZ
	 * @param borrower
	 *            借款人id
	 * @param status
	 *            状态(off=关闭,open=关闭)
	 */
	public void alterFixedBorrowersStatus(String borrower, String status, String email);

	/**
	 * @description 更改机构借款项目状态,和固定投资人的投资状态
	 * @date 2015-5-12
	 * @time 下午2:05:43
	 * @author SunZ
	 * @param loanId
	 */
	public String updateOrganizationExclusiveStatus(String loanId);
	public double readTotalMoney(Map map);
	public List<Loan> readLoan(Loan loan);

	/**
	 * 查询可以推送网贷天眼的项目列表
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	public PageInfo<Loan> readWaitPushLoanList(String pageNo, Loan loan);
	/**
	 * 更新
	 * 
	 * @param loan
	 */
	public int updateLoan(Loan loan);
	
	PageInfo<Loan> readPageList(int pageNo, int pageSize, Loan loan);

	public void updateImageTitle(String picId, String title, String loanId);
	/**
	 * 批量修改项目图片的名称
	 * @param params
	 */
	public int updateBatchPicTitle(Map<String,Object> params, String loadId);

	/**更新项目排序编号
	 * @param loan
	 */
	public void updateSortNum(Loan loan);

	/**查询拆标项目的其他所有项目
	 * @param subLoanId
	 */
	public List<Loan> readSubLoan(String subLoanId);

	/**
	 * @param loans
	 */
	public void delLoans(List<Loan> loans,String loanId);
}