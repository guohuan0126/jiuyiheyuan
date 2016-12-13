package com.duanrong.business.loan.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.DateUtilPlus;
import util.ImageUtil;
import util.Log;
import util.OssUtil;
import base.exception.InsufficientBalance;
import base.exception.InsufficientFreezeAmountException;
import base.exception.InvestMoneyException;
import base.exception.NoMatchingObjectsException;
import base.exception.NoOpenAccountException;
import base.model.PageData;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;
import base.schedule.constants.ScheduleConstants;
import base.schedule.job.CheckLoanOverExpectTime;
import base.schedule.job.CheckLoanRepayTime;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.award.model.ResposeParam;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.ApplyEnterpriseLoanDao;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.ApplyEnterpriseLoan;
import com.duanrong.business.loan.model.BannerPicture;
import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.FixedBorrowers;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.LoanProgress;
import com.duanrong.business.loan.model.RepayDate;
import com.duanrong.business.loan.model.RuralfinanceLoaners;
import com.duanrong.business.loan.model.Supplychain;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.loan.service.LoanDetailService;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.repay.dao.RepayDao;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.risk.service.SystemBillService;
import com.duanrong.business.ruralfinance.model.LoanRuralfinance;
import com.duanrong.business.ruralfinance.service.AgricultureForkLoansService;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.system.service.OperaRecordService;
import com.duanrong.business.transferstation.model.TransferStation;
import com.duanrong.business.transferstation.model.TransferStationForkLoans;
import com.duanrong.business.transferstation.service.TransferStationService;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.newadmin.exception.InvalidExpectTimeException;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.newadmin.utility.FileUploadAndDownload;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.util.IdUtil;
import com.duanrong.util.LoadConstantProterties2;
import com.duanrong.util.ToType;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-11 下午2:03:03
 * @Description : NewAdmin com.duanrong.business.loan.service.impl
 *              LoanServiceImpl.java
 * 
 */
@Service("loanService")
public class LoanServiceImpl implements LoanService {
	@Resource
	LoanDao loanDao;

	@Resource
	InvestDao investDao;

	@Resource
	RepayService repayService;

	@Resource
	SmsService smsService;

	@Resource
	RepayDao repayDao;

	@Resource
	LoanDetailService loanDetailService;

	@Resource
	StdScheduler scheduler;

	@Resource
	SystemBillService systemBillService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	InformationService informationService;

	@Resource
	ApplyEnterpriseLoanDao applyEnterpriseLoanDao;

	@Resource
	UserDao userDao;

	@Resource
	UserMoneyService userMoneyService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	Log log;

	@Resource
	OperaRecordService operaRecordService;

	@Resource
	RedPacketDao redPacketDao;
	@Resource
	TransferStationService transferStationService;

	@Resource
	AgricultureForkLoansService agricultureForkLoansService;

	public PageData<Loan> readPaging(int pageNo, int pageSize, Loan loan) {
		return loanDao.findPaging(pageNo, pageSize, loan);
	}

	/**
	 * 
	 * @description 查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:36:56
	 * @param loan
	 * @return
	 */
	public List<Loan> readAll() {
		List<Loan> loans = loanDao.findAll();
		return loans;
	}

	/**
	 * 
	 * @description 根据id查询单条借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:37:14
	 * @param id
	 * @return
	 */
	public Loan read(String id) {
		Loan loan = loanDao.get(id);
		return loan;
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	public List<Loan> readLoansByGroupCondition(Loan loan) {
		if (loan == null) {
			return null;
		} else {
			List<Loan> loans = loanDao.getLoansByGroupCondition(loan);
			return loans;
		}
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author xiao
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	public List<Loan> readLoansByGroupCondition1(Map<String, Object> map) {

		return loanDao.getLoansByGroupCondition1(map);

	}

	/**
	 * 
	 * @description 根据loanID查询对应借款人信息
	 * @author 孙铮
	 * @time 2014-8-28 下午5:40:44
	 * @param loanID
	 * @return
	 */
	public User readUserByLoanID(String loanID) {
		User user = loanDao.getUserByLoanID(loanID);
		return user;
	}

	/**
	 * 
	 * @description 根据loanID获取项目图片资料
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> readLoanInfoPics(String loanID) {
		List<BannerPicture> loanInfoPics = loanDao.getLoanInfoPics(loanID);
		return loanInfoPics;
	}

	/**
	 * 
	 * @description 根据loanID获取抵押物图片
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> readLoanGuaranteePics(String loanID) {
		List<BannerPicture> loanGuaranteePics = loanDao
				.getLoanGuaranteePics(loanID);
		return loanGuaranteePics;
	}

	public void update(Loan loan) {
		loanDao.update(loan);
	}

	public void dealRaiseComplete(String loanId)
			throws NoMatchingObjectsException {
		if (calculateMoneyNeedRaised(loanId) <= 0) {
			// 项目募集完成
			Loan loan = read(loanId);
			if (StringUtils.equals(loan.getStatus(),
					LoanConstants.LoanStatus.RAISING)) {
				loan.setStatus(LoanConstants.LoanStatus.RECHECK);
				update(loan);
			}
		}
	}

	public void verifyInvestMoney(String loanId, Double money)
			throws InvestMoneyException {
		Loan loan = this.read(loanId);
		Double investOriginMoney = loan.getInvestOriginMoney();
		Double increaseMoney = loan.getIncreaseMoney();

		if (money < investOriginMoney) {
			throw new InvestMoneyException("投资金额不能小于起点金额");
		}

		if (money % increaseMoney != 0) {
			throw new InvestMoneyException("投资金额与递增金额不符");
		}
	}

	public double calculateMoneyNeedRaised(String loanId) {
		Loan loan = this.read(loanId);
		if (loan == null) {
			return 0;
		}

		// 统计所有的此借款的投资信息，求和做减法，得出尚未募集到的金额。
		double validSumMoney = investDao.getValidInvestSumByLoan(loanId);
		double remain = ArithUtil.sub(loan.getTotalmoney(), validSumMoney);
		return remain < 0 ? 0 : remain;
	}

	/**
	 * 投资进度
	 */
	public double calculateRaiseCompletedRate(String loanId)
			throws NoMatchingObjectsException {
		double remainMoney = calculateMoneyNeedRaised(loanId);
		Loan loan = loanDao.get(loanId);
		double loanMoney = loan.getTotalmoney();
		return ArithUtil.round((loanMoney - remainMoney) / loanMoney * 100, 2);
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:02:28
	 * @param invest
	 * @return
	 */
	public List<Invest> readInvestsByGroupCondition(Invest invest) {
		if (invest == null) {
			return null;
		} else {
			List<Invest> invests = loanDao.getInvestsByGroupCondition(invest);
			return invests;
		}
	}

	@Override
	public void insertEnterpriseLoan(ApplyEnterpriseLoan applyEnterpriseLoan) {
		applyEnterpriseLoan.setId(IdGenerator.randomUUID());
		applyEnterpriseLoan.setType("郑州车贷");
		applyEnterpriseLoan
				.setStatus(LoanConstants.ApplyEnterpriseLoanStatus.WAITING_VERIFY);
		applyEnterpriseLoan.setApplyTime(new Date());
		applyEnterpriseLoanDao.insert(applyEnterpriseLoan);
	}

	@Override
	public PageData<Loan> readPaging4Personal(int pageNo, int pageSize,
			Loan loan) {
		return loanDao.findPaging4Personal(pageNo, pageSize, loan);
	}

	@Override
	public Vehicle readVehicleDetail(String loanId) {
		return loanDetailService.readVehicleDetail(loanId);
	}

	@Override
	public House readHouseDetail(String loanId) {
		return loanDetailService.readHouseDetail(loanId);
	}

	@Override
	public Enterprise readEnterpriseDetail(String loanId) {
		return loanDetailService.readEnterpriseDetail(loanId);
	}

	@Override
	public Loan readLoanDetail(Loan loan) {
		String loanId = loan.getId();
		loan = loanDao.get(loanId);
		if (loan != null) {
			String loanType = loan.getLoanType();
			if (StringUtils.equals(loanType, LoanConstants.Type4Loan.VEHICLE)) {
				loan.setVehicle(readVehicleDetail(loanId));
			} else if (StringUtils.equals(loanType,
					LoanConstants.Type4Loan.HOUSE)) {
				loan.setHouse(readHouseDetail(loanId));
			} else if (StringUtils.equals(loanType,
					LoanConstants.Type4Loan.ENTERPRISE)) {
				loan.setEnterprise(readEnterpriseDetail(loanId));
			}

			return loan;
		}
		return null;
	}

	@Override
	public LoanProgress readLoanProgress(String loanType) {
		Double allMoney = loanDetailService.readAllMoney(loanType);
		Double sumMoney = loanDetailService.readSumMoney(loanType);
		Double investMoney = loanDetailService.readInvestMoney(loanType);
		LoanProgress loanProgress = new LoanProgress();
		loanProgress.setAllMoney(allMoney);
		loanProgress.setSumMoney(sumMoney);
		loanProgress.setInvestMoney(investMoney);
		if (sumMoney == 0) {
			loanProgress.setUnderway(false);
		} else {
			loanProgress.setUnderway(true);
		}
		return loanProgress;
	}

	@Override
	public Double readRemainingInvestmentAmount() {
		return loanDetailService.readRemainingInvestmentAmount();
	}

	@Override
	public Map<String, Object> readLoanDetailDate(String loanId) {
		Loan loan = loanDao.get(loanId);
		Date releaseDate = loan.getCommitTime();
		Date quotaDate = investDao.getInvestDateLastOne(loanId);
		Date interestDate = DateUtilPlus.DateAdd4DayOfYear(
				loan.getGiveMoneyTime(), 1);
		Date completeDate = repayDao.getCompleteDate(loanId);
		Map<String, Object> map = new HashMap<>();
		// 发布日期
		map.put("releaseDate", DateUtilPlus.formatDateLoaclString(releaseDate));
		// 投标满额日期
		map.put("quotaDate", DateUtilPlus.formatDateLoaclString(quotaDate));
		// 计息日期
		map.put("interestDate",
				DateUtilPlus.formatDateLoaclString(interestDate));
		// 还款完成日期
		map.put("completeDate",
				DateUtilPlus.formatDateLoaclString(completeDate));
		return map;
	}

	@Override
	public void dealOverExpectTime(String loanId) {
		if (StringUtils.isBlank(loanId)) {
			log.errLog("项目过期调度", "项目编号不存在");
			return;
		}
		try {
			Loan loan = loanDao.get(loanId);
			log.infoLog("项目过期调度", loanId + ":" + LoanConstants.LoanStatus.DQGS);
			if (isOverExpectTime(loan)) {
				if (LoanConstants.LoanStatus.RAISING.equals(loan.getStatus())) {
					// 只有筹款中的借款，才能通过调度改成等待复核
					// loan.setStatus(LoanConstants.LoanStatus.RECHECK);
				} else if (LoanConstants.LoanStatus.DQGS.equals(loan
						.getStatus())) {
					// 只有贷前公示中的借款，才能通过调度改成筹款中
					loan.setStatus(LoanConstants.LoanStatus.RAISING);
				}
			}
			try {
				loanDao.update(loan);
			} catch (Exception e) {
				log.errLog("项目过期调度", e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("项目过期调度", e);
		}
	}

	@Override
	public boolean isOverExpectTime(String loanId) {
		Loan loan = loanDao.get(loanId);
		if (loan == null) {
			return false;
		}

		if (new Date().before(loan.getExpectTime())) {
			return false;
		}
		return true;
	}

	@Override
	public PageInfo<Loan> readPageLite(int pageNo, int pageSize, Loan loan) {
		return loanDao.pageLite(pageNo, pageSize, loan);
	}

	@Override
	public boolean isOverExpectTime(Loan loan) {
		if (loan == null) {
			return false;
		}

		if (new Date().before(loan.getExpectTime())) {
			return false;
		}
		return true;
	}

	@Override
	public String readBorrowerMessageByUserId(String param) {
		User user = new User();
		if (StringUtils.isNumeric(param) && StringUtils.length(param) == 11) {
			user.setMobileNumber(param);
		} else if (StringUtils.contains(param, "@")) {
			user.setEmail(param);
		} else {
			user.setUserId(param);
		}
		User verifyLogin = userDao.verifyLogin(user);
		if (verifyLogin == null) {
			return "用户不存在";
		}
		List<String> roles = userDao.getRoles(verifyLogin.getUserId());
		if (roles.isEmpty() || !roles.contains("INVESTOR")) {
			return "该用户未到易宝开户";
		}
		Double balance = userAccountService.readUserAccount(
				verifyLogin.getUserId()).getAvailableBalance();
		return "姓名:" + verifyLogin.getRealname() + " 手机号:"
				+ verifyLogin.getMobileNumber() + " 平台帐号:"
				+ verifyLogin.getUserId() + "账户可用余额:" + balance;
	}

	@Override
	public void uploadLoanData(CommonsMultipartFile[] files,
			HttpServletRequest request) {
		String loanId = request.getParameter("loanId");
		String realPath = request.getRealPath("/");
		String mkdir = FileUploadAndDownload.mkdir(realPath);
		/*String replaceMkdir = mkdir.replaceAll("/", "\\\\");*/
		// 上传的真实文件名称
		String realName = files[0].getOriginalFilename();
		// 对文件名进行编码
		String newName = FileUploadAndDownload.getName(realName);
		String pdf = realName.substring(realName.indexOf('.'));
		String absPath = realPath + mkdir + File.separator + newName;

		// oss路径
		String osskey = mkdir;
		if (osskey.startsWith("/")) {
			osskey = osskey.substring(1) + "/" + newName;
		}
		if (!files[0].isEmpty()) {
			FileOutputStream os = null;
			InputStream in = null;
			try {
				// 拿到输出流，同时重命名上传的文件
				os = new FileOutputStream(absPath);
				// 拿到上传文件的输入流
				in = files[0].getInputStream();
				// 以写字节的方式写文件
				int b = 0;
				while ((b = in.read()) != -1) {
					os.write(b);
				}
				os.flush();

				// 给图片添加水印
				if (!pdf.toLowerCase().equals(".pdf")) {
					try {
						ImageUtil.pressImage(realPath + "images\\wklogo.png",
								absPath);
					} catch (Exception e) {
						log.errLog("上传项目图片添加水印失败", e);
					}
				}
				OssUtil.putObject(LoadConstantProterties2
						.getValueByDefaultPro("bucketName"), osskey, absPath,
						files[0].getContentType());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (os != null && in != null) {
					try {
						os.close();
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (".pdf".equalsIgnoreCase(pdf)) {
			Loan loan = loanDao.get(loanId);
			loan.setContractName(realName);
			loan.setContract(mkdir + "/" + newName);
			loanDao.update(loan);
		} else {
			// 要写入的服务器磁盘路径
			BannerPicture bp = new BannerPicture();
			bp.setId(IdGenerator.randomUUID());
			bp.setTitle(realName);
			bp.setPicture(mkdir + "/" + newName);
			bp.setSeqNum(0);
			this.insertBannerPicture(bp, loanId);
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertBannerPicture(BannerPicture bannerPicture, String loanId) {
		loanDao.insertBannerPicture(bannerPicture);
		loanDao.insertLoanInfoPics(bannerPicture.getId(), loanId);
	}

	@Override
	public Object createLoan(HttpServletRequest request, String verifyUser)
			throws InvalidExpectTimeException, InsufficientBalance {
		String loanId = request.getParameter("loanId");
		Loan loan = null;
		String verifyLoanAttr = null;
		StringBuffer sb = new StringBuffer();
		/********************** 页面参数校验,及封装对象 **********************/
		if (StringUtils.isNotBlank(loanId) && NumberUtils.isNumber(loanId)) {
			loan = loanDao.get(loanId);
			sb.append("编辑前的" + loanId + "属性:" + loan.toString());
			verifyLoanAttr = verifyLoanAttr(loan, request, "modify");
		} else {
			loan = new Loan();
			verifyLoanAttr = verifyLoanAttr(loan, request, "create");
		}
		/***************************** 参数有错误处理 ******************************/
		ResposeParam resposeParam = new ResposeParam();
		if (StringUtils.isBlank(verifyUser)) {
			resposeParam.setStatus("fail");
			resposeParam.setError("loginUserNULL");
			return resposeParam;
		} else if (verifyLoanAttr.length() > 0) {
			resposeParam.setStatus("fail");
			resposeParam.setError(verifyLoanAttr);
			return resposeParam;
		} else {
			/***************************** 参数正确处理 ******************************/
			/*
			 * if (loan.getExpectTime() != null) { if
			 * (!StringUtils.isBlank(loan.getExpectTime().toString())) { if
			 * (!loan.getExpectTime().after(new Date())) { throw new
			 * InvalidExpectTimeException();// 预计执行时间小于当前时间,抛此异常 } } }
			 */
			if (loan.getExpectTime() == null) {
				loan.setExpectTime(loan.getCommitTime());
			}
			Date d = new Date();
			loan.setVerifyUser(verifyUser);
			if ("天".equals(loan.getOperationType())) {// 如果是天标,计算期数,以及每期还款日
				try {
					HashMap<Integer, RepayDate> loopMap = ArithUtil.Loop(d,
							loan.getDay());
					Integer loop = loopMap.keySet().size();
					loan.setDeadline(loop);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			if ("月".equals(loan.getOperationType())) {
				loan.setDay(0);
			}

			loan.setVerified(LoanConstants.LoanVerifyStatus.PASSED);
			loan.setType(LoanConstants.LoanType.ENTERPRISE_LOAN);
			loan.setDeposit(0D);
			String specialType = request.getParameter("specialType");
			loan.setSpecialType(specialType);
			if (StringUtils.isBlank(loan.getId())) {// 如果项目id为空则保存改项目
				loan.setId(IdGenerator.generateLoanId());
				// loan.setId("0" + loan.getId().substring(1));
				Date date = null;
				if ("是".equals(loan.getOrganizationExclusive())) {
					String time = request.getParameter("time");
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date giveMoneyTime = loan.getCommitTime();
					loan.setGiveMoneyTime(DateUtil.addDay(giveMoneyTime, 1));
					loan.setGiveMoneyOperationTime(giveMoneyTime);
					try {
						date = sdf.parse(time);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					loan.setRepayTime(date);
				}
				if ("车贷".equals(loan.getLoanType())) {// 判断该项目的类型,是不是新建的项目，并且是车贷项目
					// 获取项目名称的最大值
					int nameNum = transferStationService.readLoanNumber();
					nameNum++;
					String name = com.duanrong.newadmin.utility.IdUtil
							.getVihicheSortNum(nameNum);
					loan.setName(name);
				}
				loanDao.insert(loan);

				// 如果是企业专享,则投资表插入一条记录
				if ("是".equals(loan.getOrganizationExclusive())) {
					insertInvestByOrganizationExclusiveIsYes(loan);

					try {
						SimpleTrigger trigger = (SimpleTrigger) scheduler
								.getTrigger(TriggerKey.triggerKey(
										loan.getId(),
										ScheduleConstants.TriggerGroup.CHECK_ORGANIZATION_LOAN_REPAY_TIME));
						if (trigger != null) {
							Trigger newTrigger = trigger
									.getTriggerBuilder()
									.withSchedule(
											SimpleScheduleBuilder
													.simpleSchedule())
									.startAt(loan.getRepayTime()).build();
							scheduler.rescheduleJob(trigger.getKey(),
									newTrigger);
						} else {
							JobDetail jobDetail = JobBuilder
									.newJob(CheckLoanRepayTime.class)
									.withIdentity(
											loan.getId(),
											ScheduleConstants.JobGroup.CHECK_ORGANIZATION_LOAN_REPAY_TIME)
									.build();// 任务名，任务组，任务执行类
							jobDetail.getJobDataMap().put("loan_repay",
									loan.getId());
							trigger = TriggerBuilder
									.newTrigger()
									.forJob(jobDetail)
									.startAt(loan.getRepayTime())
									.withSchedule(
											SimpleScheduleBuilder
													.simpleSchedule())
									.withIdentity(
											loan.getId(),
											ScheduleConstants.TriggerGroup.CHECK_ORGANIZATION_LOAN_REPAY_TIME)
									.build();
							scheduler.scheduleJob(jobDetail, trigger);
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			} else {// 如果项目id不为空则更新该项目
				if ("是".equals(loan.getOrganizationExclusive())) {
					Date giveMoneyTime = loan.getCommitTime();
					loan.setGiveMoneyTime(DateUtil.addDay(giveMoneyTime, 1));
					loan.setGiveMoneyOperationTime(giveMoneyTime);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("loanId", loan.getId());
					List<Invest> invests = investDao.getInvestByLoan(map);
					for (Invest invest : invests) {
						invest.setTime(new Date());
						investDao.update(invest);
					}
				}
				// 如果是机构标 更新项目还款时间
				if ("是".equals(loan.getOrganizationExclusive())) {
					try {
						Date repayTime = null;
						// 获取项目创建时间 加一天 (次日计息)
						String time = request.getParameter("time");
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						repayTime = sdf.parse(time);
						loan.setRepayTime(repayTime);
						SimpleTrigger trigger = (SimpleTrigger) scheduler
								.getTrigger(TriggerKey.triggerKey(
										loan.getId(),
										ScheduleConstants.TriggerGroup.CHECK_ORGANIZATION_LOAN_REPAY_TIME));
						if (trigger != null) {
							Trigger newTrigger = trigger
									.getTriggerBuilder()
									.withSchedule(
											SimpleScheduleBuilder
													.simpleSchedule())
									.startAt(repayTime).build();
							scheduler.rescheduleJob(trigger.getKey(),
									newTrigger);
						} else {
							JobDetail jobDetail = JobBuilder
									.newJob(CheckLoanRepayTime.class)
									.withIdentity(
											loan.getId(),
											ScheduleConstants.JobGroup.CHECK_ORGANIZATION_LOAN_REPAY_TIME)
									.build();// 任务名，任务组，任务执行类
							jobDetail.getJobDataMap().put("loan_repay",
									loan.getId());
							trigger = TriggerBuilder
									.newTrigger()
									.forJob(jobDetail)
									.startAt(repayTime)
									.withSchedule(
											SimpleScheduleBuilder
													.simpleSchedule())
									.withIdentity(
											loan.getId(),
											ScheduleConstants.TriggerGroup.CHECK_ORGANIZATION_LOAN_REPAY_TIME)
									.build();
							scheduler.scheduleJob(jobDetail, trigger);
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				sb.append(" -->  编辑后的" + loanId + "属性:" + loan.toString());
				operaRecordService.insertRecord("编辑借款项目基本信息", verifyUser,
						sb.toString());
				loanDao.update(loan);
			}
			if (LoanConstants.LoanStatus.RAISING.equals(loan.getStatus())
					|| LoanConstants.LoanStatus.DQGS.equals(loan.getStatus())) {// 执行项目调度
				try {
					SimpleTrigger trigger = (SimpleTrigger) scheduler
							.getTrigger(TriggerKey.triggerKey(
									loan.getId(),
									ScheduleConstants.TriggerGroup.CHECK_LOAN_OVER_EXPECT_TIME));
					if (trigger != null) {
						Trigger newTrigger = trigger
								.getTriggerBuilder()
								.withSchedule(
										SimpleScheduleBuilder.simpleSchedule())
								.startAt(loan.getExpectTime()).build();
						scheduler.rescheduleJob(trigger.getKey(), newTrigger);
					} else {
						JobDetail jobDetail = JobBuilder
								.newJob(CheckLoanOverExpectTime.class)
								.withIdentity(
										loan.getId(),
										ScheduleConstants.JobGroup.CHECK_LOAN_OVER_EXPECT_TIME)
								.build();// 任务名，任务组，任务执行类
						jobDetail.getJobDataMap().put(
								CheckLoanOverExpectTime.LOAN_ID, loan.getId());
						trigger = TriggerBuilder
								.newTrigger()
								.forJob(jobDetail)
								.startAt(loan.getExpectTime())
								.withSchedule(
										SimpleScheduleBuilder.simpleSchedule())
								.withIdentity(
										loan.getId(),
										ScheduleConstants.TriggerGroup.CHECK_LOAN_OVER_EXPECT_TIME)
								.build();
						scheduler.scheduleJob(jobDetail, trigger);
					}
				} catch (SchedulerException e) {
					throw new RuntimeException(e);
				}
			}
		}
		String loanType = null;
		if (StringUtils.isBlank(loanId)) {// 判断该项目的类型,跳转的借款项目附加信息页面

			if ("车贷".equals(loan.getLoanType())) {
				loanType = "vehicle";
			} else if ("房贷".equals(loan.getLoanType())) {
				loanType = "house";
			} else if ("金农宝".equals(loan.getLoanType())) {
				loanType = "ruralfinance";
			} else if ("供应宝".equals(loan.getLoanType())) {
				loanType = "supplychain";
			} else {
				loanType = "enterprise";
			}
			resposeParam.setStatus("ok");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			resposeParam.setError(loan.getId() + "," + df.format(new Date())
					+ "," + loanType);
		} else {
			resposeParam.setStatus("modifyOk");
			resposeParam.setError(loan.getId());
		}
		return resposeParam;
	}

	/**
	 * @description 如果为企业专享,则插入一条固定的invest记录
	 * @date 2015-5-12
	 * @time 上午11:48:42
	 * @author SunZ
	 * @param loan
	 */
	private void insertInvestByOrganizationExclusiveIsYes(Loan loan) {
		Invest invest = new Invest();
		invest.setId(IdUtil.generateId(ToType.INVE));
		invest.setMoney(loan.getTotalmoney());
		invest.setRate(loan.getRate());
		invest.setStatus(InvestConstants.InvestStatus.REPAYING);
		invest.setInvestUserID("tongbanjie001");
		invest.setIsAutoInvest(false);
		if (invest.getInterest() == null) {
			// 按天计息 按月付息 到期还本付息 可提前还款
			if (StringUtils.equals(loan.getRepayType(),
					LoanConstants.RepayType.RFCL)
					&& StringUtils.equals("是", loan.getBeforeRepay())
					&& "天".equals(loan.getOperationType())) {
				Integer symbol = loan.getSymbol();
				int day = loan.getDay();
				day = symbol != null && symbol > 0 ? day + symbol : day;
				invest.setInterest(repayService.getRFCLInterestByPeriodDay(
						invest.getMoney(), loan.getRate(), day));
			} else if ((StringUtils.isNotEmpty(loan.getRepayType()))
					&& (loan.getRepayType().equals(LoanConstants.RepayType.CAM)
							|| loan.getRepayType().equals(
									LoanConstants.RepayType.CPM)
							|| loan.getRepayType().equals(
									LoanConstants.RepayType.DQHBFX) || loan
							.getRepayType()
							.equals(LoanConstants.RepayType.RFCL))) {
				if ("天".equals(loan.getOperationType())) {
					invest.setInterest(repayService.getRFCLInterestByPeriodDay(
							invest.getMoney(), loan.getRate(), loan.getDay()));
				} else {
					invest.setInterest(repayService.calculateInvestInterset(
							loan.getRepayType(), loan.getDeadline(),
							loan.getRate(), invest.getMoney()));
				}
			}
		}
		if (StringUtils.isEmpty(invest.getType())) {
			invest.setType(InvestConstants.InvestType.NONE);
		}
		invest.setTime(new Date());
		invest.setLoanId(loan.getId());
		investDao.insert(invest);
	}

	/**
	 * @description 验证借款项目参数
	 * @author 孙铮
	 * @time 2015-3-12 下午3:51:49
	 * @param loan
	 * @param string
	 * @return
	 */
	private String verifyLoanAttr(Loan loan, HttpServletRequest request,
			String type) {

		StringBuffer sb = new StringBuffer();
		// operationType 计算方式
		String operationType = request.getParameter("operationType");
		if (StringUtils.isBlank(operationType)) {
			sb.append("operationType,");
		} else {
			loan.setOperationType(operationType);
		}
		String instructionNo = request.getParameter("instructionNo");

		loan.setInstructionNo(instructionNo);

		// 是否机构专享
		String organizationExclusive = request
				.getParameter("organizationExclusive");
		if (StringUtils.isBlank(organizationExclusive)) {
			sb.append("organizationExclusive,");
		} else {
			loan.setOrganizationExclusive(organizationExclusive);
			loan.setNewbieEnjoy("否");
		}

		// repayType 还款方式
		String repayType = request.getParameter("repayType");
		if (StringUtils.isBlank(repayType)) {
			sb.append("repayType,");
		} else {
			loan.setRepayType(repayType);
		}

		// day 天标下,天数
		if ("天".equals(operationType)) {
			String day = request.getParameter("day");
			if (StringUtils.isBlank(day)
					|| !NumberUtils.isNumber(day.toString())) {
				sb.append("day,");
			} else {
				int dayParseInt = Integer.parseInt(day);
				loan.setDay(dayParseInt);
			}
			loan.setBeforeRepay("否");
		}

		// borrower 借款人
		String borrower = request.getParameter("borrower");
		User user = new User();
		if (StringUtils.isBlank(borrower)) {
			sb.append("borrower,");
		} else {
			if (StringUtils.isNumeric(borrower)
					&& StringUtils.length(borrower) == 11) {
				user.setMobileNumber(borrower);
			} else if (StringUtils.contains(borrower, "@")) {
				user.setEmail(borrower);
			} else {
				user.setUserId(borrower);
			}
			User verifyLogin = userDao.verifyLogin(user);
			if (verifyLogin == null) {
				sb.append("borrower,");
			} else {
				loan.setBorrowMoneyUserID(verifyLogin.getUserId());
			}
		}
		String loanType1 = request.getParameter("loanType");// 项目类型
		// name 借款项目名称
		String name = request.getParameter("name").trim();
		if (loanType1 != null && loanType1.equals("车贷")) {
			loan.setName(name);
		} else {
			if (StringUtils.isBlank(name)) {
				sb.append("name,");
			} else {
				loan.setName(name);
			}
		}
		// totalMoney 借款总金额
		String totalMoney = request.getParameter("totalMoney");
		Double totalMoneyD = null;

		String companyNo = request.getParameter("companyNo");
		if (companyNo == null) {
			companyNo = loan.getCompanyno();
		} else {
			loan.setCompanyno(companyNo);
		}
		if (companyNo == null) {
			companyNo = "0";
			loan.setCompanyno("0");
		}
		if (StringUtils.isBlank(totalMoney)
				|| !NumberUtils.isNumber(totalMoney.toString())) {
			// 多车项目不需要验证借款金额
			if (loanType1 != null && loanType1.equals("车贷")
					&& companyNo.equals("1")) {
				totalMoneyD = 0.0;
				loan.setTotalmoney(totalMoneyD);
			} else {
				sb.append("totalMoney,");
			}
		} else {
			totalMoneyD = Double.parseDouble(totalMoney);
			if (totalMoneyD < 0) {
				sb.append("totalMoney,");
			} else {
				loan.setTotalmoney(totalMoneyD);
			}
		}

		// minInvestMoney 投资起点金额
		String minInvestMoney = request.getParameter("minInvestMoney");
		Double minInvestMoneyD = null;

		if (StringUtils.isBlank(minInvestMoney)
				|| !NumberUtils.isNumber(minInvestMoney.toString())
				|| totalMoneyD == null) {
			sb.append("minInvestMoney,");
		} else {
			minInvestMoneyD = Double.parseDouble(minInvestMoney);
			String ascInvestMoney = request.getParameter("ascInvestMoney");
			Double ascInvestMoneyD = Double.parseDouble(ascInvestMoney);
			if (loanType1.equals("金农宝") || loanType1.equals("车贷")
					&& companyNo.equals("1")) {
				loan.setInvestOriginMoney(minInvestMoneyD);
				loan.setIncreaseMoney(ascInvestMoneyD);
			} else {
				if (minInvestMoneyD >= 0
						&& minInvestMoneyD <= totalMoneyD
						&& totalMoneyD % minInvestMoneyD == 0
						&& minInvestMoneyD.toString().equals(
								ascInvestMoneyD.toString())) {
					loan.setInvestOriginMoney(minInvestMoneyD);
					loan.setIncreaseMoney(ascInvestMoneyD);
				} else {
					sb.append("minInvestMoney,");
				}
			}
		}

		// maxInvestMoney 投资金额上限
		String maxInvestMoney = request.getParameter("maxInvestMoney");
		Double maxInvestMoneyD = null;
		if (loan.getCompanyno().equals("0")) {
			if (StringUtils.isBlank(maxInvestMoney)
					|| !NumberUtils.isNumber(maxInvestMoney.toString())
					|| totalMoneyD == null) {
				sb.append("maxInvestMoney,");
			} else {
				maxInvestMoneyD = Double.parseDouble(maxInvestMoney);
				if (maxInvestMoneyD > totalMoneyD) {
					sb.append("maxInvestMoney,");
				} else if (maxInvestMoneyD != 0
						&& maxInvestMoneyD < minInvestMoneyD) {
					sb.append("maxInvestMoney,");
				} else {
					loan.setMaxInvestMoney(maxInvestMoneyD);
				}
			}
			// autoInvestMoneyTotal 自动投标上限金额
			String autoInvestMoneyTotal = request
					.getParameter("autoInvestMoneyTotal");
			Double autoInvestMoneyTotalD = null;
			if (StringUtils.isBlank(autoInvestMoneyTotal)
					|| !NumberUtils.isNumber(autoInvestMoneyTotal.toString())
					|| totalMoneyD == null) {
				sb.append("autoInvestMoneyTotal,");
			} else {
				autoInvestMoneyTotalD = Double
						.parseDouble(autoInvestMoneyTotal);

				if (loanType1.equals("车贷") && companyNo.equals("1")) {
					loan.setAutoInvestMoneyTotal(autoInvestMoneyTotalD);
				} else if (autoInvestMoneyTotalD > totalMoneyD) {
					sb.append("autoInvestMoneyTotal,");
				} else if (autoInvestMoneyTotalD != 0
						&& autoInvestMoneyTotalD < minInvestMoneyD) {
					sb.append("autoInvestMoneyTotal,");
				} else {
					loan.setAutoInvestMoneyTotal(autoInvestMoneyTotalD);
				}
			}
		} else {
			if (StringUtils.isBlank(maxInvestMoney)
					|| !NumberUtils.isNumber(maxInvestMoney.toString())) {
				sb.append("maxInvestMoney,");
			} else {
				maxInvestMoneyD = Double.parseDouble(maxInvestMoney);
				loan.setMaxInvestMoney(maxInvestMoneyD);

			}
			// autoInvestMoneyTotal 自动投标上限金额
			String autoInvestMoneyTotal = request
					.getParameter("autoInvestMoneyTotal");

			if (StringUtils.isBlank(autoInvestMoneyTotal)
					|| !NumberUtils.isNumber(autoInvestMoneyTotal.toString())) {
				sb.append("autoInvestMoneyTotal,");
			} else {
				loan.setAutoInvestMoneyTotal(Double
						.parseDouble(autoInvestMoneyTotal));

			}

		}

		// rate 借款利率
		String rate = request.getParameter("rate");
		Double rateD = null;
		if (StringUtils.isBlank(rate) || !NumberUtils.isNumber(rate.toString())) {
			sb.append("rate,");
		} else {
			rateD = Double.parseDouble(rate);
			if (rateD <= 0) {
				sb.append("rate,");
			} else {
				loan.setRate(ArithUtil.round(rateD / 100, 4));
			}
		}

		// guranteeFee 借款管理费
		String guranteeFee = request.getParameter("guranteeFee");
		Double guranteeFeeD = null;
		if (StringUtils.isBlank(guranteeFee)
				|| !NumberUtils.isNumber(guranteeFee.toString())) {
			sb.append("guranteeFee,");
		} else {
			guranteeFeeD = Double.parseDouble(guranteeFee);
			if (guranteeFeeD < 0) {
				sb.append("guranteeFee,");
			} else {
				loan.setLoanGuranteeFee(guranteeFeeD);
			}
		}

		if ("月".equals(operationType)) {
			// deadline 借款期限
			String deadline = request.getParameter("deadline");
			int deadlineI = Integer.parseInt(deadline);
			loan.setDeadline(deadlineI);
			loan.setBeforeRepay("否");
		}

		String dqgs = request.getParameter("dqgs");
		String expectTime = request.getParameter("expectTime");
		if (StringUtils.isBlank(dqgs)) {
			sb.append("dqgs,");
		} else {
			if ("是".equals(organizationExclusive)) {
				loan.setExpectTime(DateUtil.addMonth(new Date(), 2));
				loan.setStatus(LoanConstants.LoanStatus.REPAYING);
			} else {
				if ("1".equals(dqgs)) {
					if (StringUtils.isBlank(expectTime)) {
						sb.append("expectTime,");
					} else {
						Date expectTimeDate = DateUtil.StringToDate(expectTime);
						if (expectTimeDate.getTime() < new Date().getTime()) {
							sb.append("expectTime,");
						} else {
							loan.setExpectTime(expectTimeDate);
							loan.setStatus(LoanConstants.LoanStatus.DQGS);
						}
					}
				} else {
					if ("create".equals(type)) {
						loan.setStatus(LoanConstants.LoanStatus.RAISING);
					} else {
						if ("0".equals(dqgs)
								&& loan.getStatus().equals(
										LoanConstants.LoanStatus.DQGS)) {
							loan.setStatus(LoanConstants.LoanStatus.RAISING);
						}
					}
					loan.setExpectTime(loan.getCommitTime());// 前台需要的默认值
				}
			}
		}

		String testItem = request.getParameter("testItem");
		if (loan.getTextItem() == null
				|| ("是".equals(loan.getTextItem()) && "否".equals(testItem))) {
			loan.setCommitTime(new Date());
		}
		if (StringUtils.isBlank(testItem)) {
			testItem = "是";
		}
		loan.setTextItem(testItem);
		// 是否可投，0为不可投，1为可投
		String whetherInvested = request.getParameter("whetherInvested");
		if (StringUtils.isBlank(whetherInvested)) {
			whetherInvested = "0";
		}
		loan.setWhetherInvested(Integer.parseInt(whetherInvested));
		String loanType = request.getParameter("loanType");
		loan.setLoanType(loanType);
		String interestRule = request.getParameter("interestRule");
		loan.setInterestRule(interestRule);
		String newbieEnjoy = request.getParameter("newbieEnjoy");
		if ("是".equals(newbieEnjoy)) {
			loan.setOrganizationExclusive("否");
			loan.setNewbieEnjoy(newbieEnjoy);
		}
		String itemAddress = request.getParameter("itemAddress");
		String itemRate = request.getParameter("itemRate");
		String customerManagerJobNumber = request
				.getParameter("customerManagerJobNumber");
		String customerManagerName = request
				.getParameter("customerManagerName");
		String borrowerName = request.getParameter("borrowerName");
		String remark = request.getParameter("remark");
		String yaCarAndGPS = request.getParameter("yaCarAndGPS");
		loan.setItemAddress(itemAddress);
		loan.setItemRate(itemRate);
		loan.setCustomerManagerJobNumber(customerManagerJobNumber);
		loan.setCustomerManagerName(customerManagerName);
		loan.setBorrowerName(borrowerName);
		loan.setRemark(remark);
		loan.setYaCarAndGPS(yaCarAndGPS);
		return sb.toString();
	}

	@Override
	public Object createLoanDetail(HttpServletRequest request) {
		ResposeParam resposeParam = new ResposeParam();
		String loanId = request.getParameter("loanId");
		if (StringUtils.isBlank(loanId)) {
			resposeParam.setStatus("fail");
			resposeParam.setError("loanIdNULL");
			return resposeParam;
		}
		String loanType = request.getParameter("loanType");
		if (loanType == null) {
			resposeParam.setStatus("fail");
			resposeParam.setError("loanTypeNULL");
			return resposeParam;
		}
		Loan loan = loanDao.get(loanId);
		if (loan == null) {
			resposeParam.setStatus("fail");
			resposeParam.setError("loanNULL");
			return resposeParam;
		}

		List<Object> verifyLoanDetailAttr = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = "2016-04-11";
		int days = 0;
		try {
			days = DateUtil.DayDifference(nowTime,
					df.format(loan.getCommitTime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String status = "";
		if (days > 0) {
			// 在现在时间之前
			status = "more";
		} else {
			// 现在时间之后
			status = "less";
		}
		String op = null;
		Object obj = null;
		if (!"enterprise".equals(loanType) && !"supplychain".equals(loanType)) {
			verifyLoanDetailAttr = verifyLoanDetailAttr(request, loanType,
					loanId, status, loan);
			op = verifyLoanDetailAttr.get(0).toString();
			obj = verifyLoanDetailAttr.get(1);
			String error = verifyLoanDetailAttr.get(2).toString();
			if (!StringUtils.isBlank(error) && error.length() > 0) {
				resposeParam.setStatus("fail");
				resposeParam.setError(error);
				return resposeParam;
			}
		}
		if ("vehicle".equals(loanType)) {
			if ("insert".equals(op)) {
				loanDetailService.insertVehicle((List<Vehicle>) obj, loan);
				resposeParam.setStatus("ok");
			} else {
				loanDetailService.updateVehicle((List<Vehicle>) obj, loan);
				resposeParam.setStatus("updateOk");
			}
		} else if ("house".equals(loanType)) {
			if ("insert".equals(op)) {
				loanDetailService.insertHouse((House) obj);
				resposeParam.setStatus("ok");
			} else {
				loanDetailService.updateHouse((House) obj);
				resposeParam.setStatus("updateOk");
			}
		} else if ("supplychain".equals(loanType)) {
			List<Object> os = createSupplychainObj(request, loanId);
			if ("insert".equals(os.get(0).toString())) {
				loanDetailService.insertSupplychain((Supplychain) os.get(1));
				resposeParam.setStatus("ok");
			} else {
				loanDetailService.updateSupplychain((Supplychain) os.get(1));
				resposeParam.setStatus("updateOk");
			}
		} else if ("ruralfinance".equals(loanType)) {
			if ("insert".equals(op)) {
				loanDetailService.insertRuralfinance((LoanRuralfinance) obj);
				Loan loanins = new Loan();
				// totalMoney 借款总金额
				double totalMoney = Double.valueOf(request
						.getParameter("totalMoney"));
				double autoInvestMoneyTotal = Double.valueOf(request
						.getParameter("autoInvestMoneyTotal"));
				Integer deadline = Integer.parseInt(request
						.getParameter("deadline"));
				String ids = request.getParameter("LoandataIds");
				if (ids != null && ids != "") {
					loanins.setId(loanId);
					loanins.setTotalmoney(totalMoney);
					loanins.setAutoInvestMoneyTotal(autoInvestMoneyTotal);
					loanins.setDeadline(deadline);
					updateLoan(loanins);
					String[] arr = ids.split(",");
					Map<String, Object> params = new HashMap<>();
					params.put("loanId", loanId);
					params.put("packing", 1);
					params.put("arr", arr);
					agricultureForkLoansService.updateBatch(params);
				}
				resposeParam.setStatus("ok");
			} else {
				loanDetailService.updateRuralfinance((LoanRuralfinance) obj);
				Loan loanins = new Loan();
				// totalMoney 借款总金额
				double totalMoney = Double.valueOf(request
						.getParameter("totalMoney"));
				double autoInvestMoneyTotal = Double.valueOf(request
						.getParameter("autoInvestMoneyTotal"));
				Integer deadline = Integer.parseInt(request
						.getParameter("deadline"));
				String ids = request.getParameter("LoandataIds");
				if (ids != null && ids != "") {
					loanins.setId(loanId);
					loanins.setTotalmoney(totalMoney);
					loanins.setAutoInvestMoneyTotal(autoInvestMoneyTotal);
					loanins.setDeadline(deadline);
					updateLoan(loanins);
					Map<String, Object> loanMap = new HashMap<>();
					loanMap.put("loanId", loanId);
					loanMap.put("packing", 0);
					agricultureForkLoansService
							.updateForkLoanstatus(loanMap);
					String[] arr = ids.split(",");
					Map<String, Object> params = new HashMap<>();
					params.put("loanId", loanId);
					params.put("packing", 1);
					params.put("arr", arr);
					agricultureForkLoansService.updateBatch(params);
				}
				resposeParam.setStatus("updateOk");

			}
		} else {
			List<Object> os = createEnterpriseObj(request, loanId);
			if ("insert".equals(os.get(0).toString())) {
				loanDetailService.insertEnterprise((Enterprise) os.get(1));
				resposeParam.setStatus("ok");
			} else {
				loanDetailService.updateEnterprise((Enterprise) os.get(1));
				resposeParam.setStatus("updateOk");
			}
		}
		return resposeParam;
	}

	@Override
	public Object createRuralfinanceLoaners(HttpServletRequest request) {
		ResposeParam resposeParam = new ResposeParam();
		String loanId = request.getParameter("loanId");
		String loanType = request.getParameter("loanType");
		if (StringUtils.isBlank(loanId)) {
			resposeParam.setStatus("fail");
			resposeParam.setError("loanIdNULL");
			return resposeParam;
		}
		Loan loan = loanDao.get(loanId);
		if (loan == null) {
			resposeParam.setStatus("fail");
			resposeParam.setError("loanNULL");
			return resposeParam;
		}
		if (StringUtils.isBlank(loanType)) {
			resposeParam.setStatus("fail");
			resposeParam.setError("loanTypeNULL");
			return resposeParam;
		}

		if ("ruralfinance".equals(loanType)) {
			List<Object> os = createRuralfinanceLoanersObj(request, loanId);
			if ("insert".equals(os.get(0).toString())) {
				loanDetailService
						.insertRuralfinanceLoaners((RuralfinanceLoaners) os
								.get(1));
				resposeParam.setStatus("ok");
			} else {
				// loanDetailService.updateRuralfinanceLoaners((RuralfinanceLoaners)
				// os.get(1));
				loanDetailService
						.updateRuralfinanceLoanerID((RuralfinanceLoaners) os
								.get(1));
				resposeParam.setStatus("updateOk");
			}
		}
		return resposeParam;
	}

	/**
	 * @description 创建一个RuralfinanceLoaners金农宝借款人对象，做增加修改
	 * @author ＹＵＭＩＮ
	 * @time 2015-１２-4 下午1:27:19
	 * @param request
	 * @param loanId
	 */

	private List<Object> createRuralfinanceLoanersObj(
			HttpServletRequest request, String loanId) {
		// 页面input属性name的值
		final String[] str = { "ruralfinanceLoanersid", "loanId", "loanerId",
				"loanerName", "money", "gender", "age", "maritalStatus",
				"industry", "annualIncome", "realEstateSituation",
				"vehicleSituation", "idCard", "area" };
		//String id = request.getParameter("ruralfinanceLoanersid");
		String idCard = request.getParameter("idCard");
		// RuralfinanceLoaners ruralfinanceLoaners =
		// loanDetailService.getRuralfinanceLoanerDetail(id);
		RuralfinanceLoaners ruralfinanceLoaners = new RuralfinanceLoaners();
		ruralfinanceLoaners.setLoanId(loanId);
		ruralfinanceLoaners.setIdCard(idCard);
		ruralfinanceLoaners = loanDetailService
				.readRuralfinanceLoanerID(ruralfinanceLoaners);
		List<Object> os = new ArrayList<Object>();
		if (ruralfinanceLoaners == null) {
			ruralfinanceLoaners = new RuralfinanceLoaners();
			ruralfinanceLoaners.setId(IdGenerator.randomUUID());
			os.add("insert");
		} else {
			// ruralfinanceLoaners.setId(id);
			os.add("update");
		}
		for (int i = 0; i < str.length; i++) {
			String key = str[i];
			String value = request.getParameter(key);
			if ("loanId".equals(key)) {
				ruralfinanceLoaners.setLoanId(value);
			} else if ("loanerId".equals(key)) {
				ruralfinanceLoaners.setLoanerId(Integer.parseInt(value));
			} else if ("loanerName".equals(key)) {
				ruralfinanceLoaners.setLoanerName(value);
			} else if ("gender".equals(key)) {
				ruralfinanceLoaners.setGender(value);
			} else if ("age".equals(key)) {
				ruralfinanceLoaners.setAge(value);
			} else if ("maritalStatus".equals(key)) {
				ruralfinanceLoaners.setMaritalStatus(value);
			} else if ("industry".equals(key)) {
				ruralfinanceLoaners.setIndustry(value);
			} else if ("annualIncome".equals(key)) {
				ruralfinanceLoaners.setAnnualIncome(value);
			} else if ("realEstateSituation".equals(key)) {
				ruralfinanceLoaners.setRealEstateSituation(value);
			} else if ("vehicleSituation".equals(key)) {
				ruralfinanceLoaners.setVehicleSituation(value);
			} else if ("idCard".equals(key)) {
				ruralfinanceLoaners.setIdCard(value);
			} else if ("area".equals(key)) {
				ruralfinanceLoaners.setArea(value);
			} else if ("money".equals(key)) {
				ruralfinanceLoaners.setMoney(Double.parseDouble(value
						.toString()));
			}
		}
		ruralfinanceLoaners.setStatus("valid");
		ruralfinanceLoaners.setLastAlterTime(new Date());
		os.add(ruralfinanceLoaners);
		return os;
	}

	/**
	 * @description 创建一个Supplychain对象，做增加修改
	 * @author
	 * @time 2015-１２-4 下午1:27:19
	 * @param request
	 * @param loanId
	 */
	private List<Object> createSupplychainObj(HttpServletRequest request,
			String loanId) {
		// 页面input属性name的值
		final String[] str = { "buildDate", "operationYear",
				"operationBusiness", "registeredCapital", "realIncomeCapital",
				"companyDesc", "organization", "staffNumber", "financeData",
				"operationProduction", "creditReport", "nationalCourtsReport",
				"dishonstPersonReport", "publicChannelNegativeInfo",
				"otherNegativeInfo", "coreEnterpriseInfo", "guaranteeMeasures",
				"loanApplication", "repaymentSource", "reminderInfo",
				"measuresInfo", "overdueInfo", "otherInfo" };
		Supplychain supplychain = loanDetailService
				.readSupplychainDetail(loanId);
		List<Object> os = new ArrayList<Object>();
		if (supplychain == null) {
			supplychain = new Supplychain();
			supplychain.setLoanId(loanId);
			os.add("insert");
		} else {
			os.add("update");
		}
		supplychain.setBorrowerIdCard(request.getParameter("borrowerIdCard"));
		supplychain.setItemAddress(request.getParameter("itemAddress"));
		supplychain.setBorrowerName(request.getParameter("borrowerName"));
		supplychain.setRemark(request.getParameter("remark"));
		for (int i = 0; i < str.length; i++) {
			String key = str[i];
			String value = request.getParameter(key);
			if ("operationYear".equals(key)) {
				supplychain.setOperationYear(value);
			} else if ("buildDate".equals(key)) {
				Date buildDate = null;
				if (!StringUtils.isBlank(value)) {
					buildDate = DateUtil.StringToDate(value);
				}
				supplychain.setBuildDate(buildDate);
			} else if ("reminderInfo".equals(key)) {
				supplychain.setReminderInfo(value);
			} else if ("measuresInfo".equals(key)) {
				supplychain.setMeasuresInfo(value);
			} else if ("overdueInfo".equals(key)) {
				supplychain.setOverdueInfo(value);
			} else if ("otherInfo".equals(key)) {
				supplychain.setOtherInfo(value);
			} else if ("operationBusiness".equals(key)) {
				supplychain.setOperationBusiness(value);
			} else if ("registeredCapital".equals(key)) {
				supplychain.setRegisteredCapital(value);
			} else if ("realIncomeCapital".equals(key)) {
				supplychain.setRealIncomeCapital(value);
			} else if ("operationProduction".equals(key)) {
				supplychain.setOperationProduction(value);
			} else if ("organization".equals(key)) {
				supplychain.setOrganization(value);
			} else if ("staffNumber".equals(key)) {
				supplychain.setStaffNumber(value);
			} else if ("companyDesc".equals(key)) {
				supplychain.setCompanyDesc(value);
			} else if ("financeData".equals(key)) {
				supplychain.setFinanceData(value);
			} else if ("guaranteeMeasures".equals(key)) {
				supplychain.setGuaranteeMeasures(value);
			} else if ("creditReport".equals(key)) {
				supplychain.setCreditReport(value);
			} else if ("nationalCourtsReport".equals(key)) {
				supplychain.setNationalCourtsReport(value);
			} else if ("dishonstPersonReport".equals(key)) {
				supplychain.setDishonstPersonReport(value);
			} else if ("publicChannelNegativeInfo".equals(key)) {
				supplychain.setPublicChannelNegativeInfo(value);
			} else if ("otherNegativeInfo".equals(key)) {
				supplychain.setOtherNegativeInfo(value);
			} else if ("coreEnterpriseInfo".equals(key)) {
				supplychain.setCoreEnterpriseInfo(value);
			} else if ("loanApplication".equals(key)) {
				supplychain.setLoanApplication(value);
			} else if ("repaymentSource".equals(key)) {
				supplychain.setRepaymentSource(value);
			}
		}
		os.add(supplychain);
		return os;
	}

	/**
	 * @description 创建一个Enterprise对象
	 * @author 孙铮
	 * @time 2015-3-16 下午1:27:19
	 * @param request
	 * @param loanId
	 */
	private List<Object> createEnterpriseObj(HttpServletRequest request,
			String loanId) {
		// 页面input属性name的值
		final String[] str = { "developmentAbilityAnalysis",
				"capabilitiesAnalysis", "profitabilityAnalysis",
				"solvencyAnalysis", "financialData", "debtHistory",
				"housingConditions", "educationalStatus", "customerGender",
				"establishTime", "businessScope", "registeredCapital",
				"inshold", "operatingPeriod", "paidInCapital",
				"affiliatedEnterprise", "companyIntroduction", "customerName",
				"customerAge", "registeredPermanentResidence",
				"locaResidenceTime", "productionAddress",
				"organizationStructure", "numberEmployees",
				"specializedProductsAndServices", "personalCreditReport",
				"ncriByPersonal", "lttoftpstelqByPersonal", "ocniqByPersonal",
				"enterpriseCreditReport", "ncriByEnterprise",
				"lttoftpstelqByEnterprise", "ocniqByEnterprise", "api",
				"guaranteesMeasures", "loanPurpose", "operatingCashIncome",
				"netEarnings", "sinkingFund", "debtRevenue",
				"assetsRealization", "externalSupport", "financialStatus",
				"maritalStatus", "otherDescription", "reminderInfo",
				"measuresInfo", "overdueInfo", "otherInfo" };
		Enterprise enterprise = loanDetailService.readEnterpriseDetail(loanId);
		List<Object> os = new ArrayList<Object>();
		if (enterprise == null) {
			enterprise = new Enterprise();
			enterprise.setLoanId(loanId);
			os.add("insert");
		} else {
			os.add("update");
		}

		enterprise.setItemAddress(request.getParameter("itemAddress"));
		enterprise.setBorrowerIdCard(request.getParameter("borrowerIdCard"));
		enterprise.setBorrowerName(request.getParameter("borrowerName"));
		enterprise.setRemark(request.getParameter("remark"));

		for (int i = 0; i < str.length; i++) {
			String key = str[i];
			String value = request.getParameter(key);
			if ("developmentAbilityAnalysis".equals(key)) {
				enterprise.setDevelopmentAbilityAnalysis(value);
			} else if ("reminderInfo".equals(key)) {
				enterprise.setReminderInfo(value);
			} else if ("measuresInfo".equals(key)) {
				enterprise.setMeasuresInfo(value);
			} else if ("overdueInfo".equals(key)) {
				enterprise.setOverdueInfo(value);
			} else if ("otherInfo".equals(key)) {
				enterprise.setOtherInfo(value);
			} else if ("capabilitiesAnalysis".equals(key)) {
				enterprise.setCapabilitiesAnalysis(value);
			} else if ("profitabilityAnalysis".equals(key)) {
				enterprise.setProfitabilityAnalysis(value);
			} else if ("solvencyAnalysis".equals(key)) {
				enterprise.setSolvencyAnalysis(value);
			} else if ("financialData".equals(key)) {
				enterprise.setFinancialData(value);
			} else if ("debtHistory".equals(key)) {
				enterprise.setDebtHistory(value);
			} else if ("housingConditions".equals(key)) {
				if ("其他".equals(value)) {
					value = request.getParameter("housingConditionsDetail");
				}
				enterprise.setHousingConditions(value);
			} else if ("educationalStatus".equals(key)) {
				enterprise.setEducationalStatus(value);
			} else if ("customerGender".equals(key)) {
				enterprise.setCustomerGender(value);
			} else if ("establishTime".equals(key)) {
				Date establishTime = null;
				if (!StringUtils.isBlank(value)) {
					establishTime = DateUtil.StringToDate(value);
				}
				enterprise.setEstablishTime(establishTime);
			} else if ("businessScope".equals(key)) {
				enterprise.setBusinessScope(value);
			} else if ("registeredCapital".equals(key)) {
				enterprise.setRegisteredCapital(value);
			} else if ("inshold".equals(key)) {
				enterprise.setInshold(value);
			} else if ("operatingPeriod".equals(key)) {
				enterprise.setOperatingPeriod(value);
			} else if ("paidInCapital".equals(key)) {
				enterprise.setPaidInCapital(value);
			} else if ("affiliatedEnterprise".equals(key)) {
				enterprise.setAffiliatedEnterprise(value);
			} else if ("companyIntroduction".equals(key)) {
				enterprise.setCompanyIntroduction(value);
			} else if ("customerName".equals(key)) {
				enterprise.setCustomerName(value);
			} else if ("customerAge".equals(key)) {
				enterprise.setCustomerAge(value);
			} else if ("registeredPermanentResidence".equals(key)) {
				enterprise.setRegisteredPermanentResidence(value);
			} else if ("locaResidenceTime".equals(key)) {
				enterprise.setLocaResidenceTime(value);
			} else if ("productionAddress".equals(key)) {
				enterprise.setProductionAddress(value);
			} else if ("organizationStructure".equals(key)) {
				enterprise.setOrganizationStructure(value);
			} else if ("numberEmployees".equals(key)) {
				enterprise.setNumberEmployees(value);
			} else if ("specializedProductsAndServices".equals(key)) {
				enterprise.setSpecializedProductsAndServices(value);
			} else if ("personalCreditReport".equals(key)) {
				enterprise.setPersonalCreditReport(value);
			} else if ("ncriByPersonal".equals(key)) {
				enterprise.setNcriByPersonal(value);
			} else if ("lttoftpstelqByPersonal".equals(key)) {
				enterprise.setLttoftpstelqByPersonal(value);
			} else if ("ocniqByPersonal".equals(key)) {
				enterprise.setOcniqByPersonal(value);
			} else if ("enterpriseCreditReport".equals(key)) {
				enterprise.setEnterpriseCreditReport(value);
			} else if ("ncriByEnterprise".equals(key)) {
				enterprise.setNcriByEnterprise(value);
			} else if ("lttoftpstelqByEnterprise".equals(key)) {
				enterprise.setLttoftpstelqByEnterprise(value);
			} else if ("ocniqByEnterprise".equals(key)) {
				enterprise.setOcniqByEnterprise(value);
			} else if ("api".equals(key)) {
				enterprise.setApi(value);
			} else if ("guaranteesMeasures".equals(key)) {
				enterprise.setGuaranteesMeasures(value);
			} else if ("loanPurpose".equals(key)) {
				enterprise.setLoanPurpose(value);
			} else if ("operatingCashIncome".equals(key)) {
				enterprise.setOperatingCashIncome(value);
			} else if ("netEarnings".equals(key)) {
				enterprise.setNetEarnings(value);
			} else if ("sinkingFund".equals(key)) {
				enterprise.setSinkingFund(value);
			} else if ("debtRevenue".equals(key)) {
				enterprise.setDebtRevenue(value);
			} else if ("assetsRealization".equals(key)) {
				enterprise.setAssetsRealization(value);
			} else if ("externalSupport".equals(key)) {
				enterprise.setExternalSupport(value);
			} else if ("financialStatus".equals(key)) {
				enterprise.setFinancialStatus(value);
			} else if ("maritalStatus".equals(key)) {
				enterprise.setMaritalStatus(value);
			} else if ("otherDescription".equals(key)) {
				enterprise.setOtherDescription(value);
			}
		}
		os.add(enterprise);
		return os;
	}

	private List<Object> verifyLoanDetailAttr(HttpServletRequest request,
			String loanType, String loanId, String status, Loan loan) {
		StringBuffer sb = new StringBuffer();
		List<Object> os = new ArrayList<Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if ("vehicle".equals(loanType)) {
			List<Vehicle> vehicles = loanDetailService.readVehicleList(loanId);
			Vehicle v1 = new Vehicle();
			if (vehicles.isEmpty()) {
				os.add("insert");
			} else {
				v1 = vehicles.get(0);
				vehicles = new ArrayList<>();
				os.add("update");
			}
			String param1 = request.getParameter("vehicles");
			String param2 = request.getParameter("loan");
			String[] l = param2.split(",");
			String[] vs = param1.split(";");
			double totalMoney = 0;
			for (String v : vs) {
				Vehicle vehicle = new Vehicle();
				if (v.length() > 0) {
					String[] v2 = v.split(",");
					vehicle.setId(request.getParameter("id"));
					vehicle.setLoanId(loanId);
					String[] brand = v2[0].split("=");
					vehicle.setBrand(brand.length == 2 ? brand[1] : null);
					String[] licensePlateNumber = v2[1].split("=");
					vehicle.setLicensePlateNumber(licensePlateNumber.length == 2 ? licensePlateNumber[1]
							: null);
					String[] kilometreAmount = v2[2].split("=");
					vehicle.setKilometreAmount(kilometreAmount.length == 2 ? kilometreAmount[1]
							: null);
					String[] money = v2[3].split("=");
					String m = money.length == 2 ? money[1] : null;
					vehicle.setMoney(m == null || m.equals("") ? 0 : Double
							.parseDouble(m));
					totalMoney += vehicle.getMoney();
					String[] assessPrice = v2[4].split("=");
					vehicle.setAssessPrice(assessPrice.length == 2 ? assessPrice[1]
							: null);
					if (loan.getCompanyno().equals("1")) {
						String[] otherInfo = v2[5].split("=");
						vehicle.setOtherInfo(otherInfo.length == 2 ? otherInfo[1] : null);
						String[] registrationDate = v2[6].split("=");
						try{
							vehicle.setRegistrationDate(format.parse(registrationDate[1]));
						}catch(Exception e){}
						String[] displacement = v2[7].split("=");
						vehicle.setDisplacement(displacement.length == 2 ? displacement[1] : null);
						String[] transmission = v2[8].split("=");
						vehicle.setTransmission(transmission.length == 2 ? transmission[1] : null);
						String[] conditionAssessment = v2[9].split("=");
						vehicle.setConditionAssessment(conditionAssessment.length == 2 ? conditionAssessment[1] : null);
						String[] borrowingPurposes = v2[10].split("=");
						vehicle.setBorrowingPurposes(borrowingPurposes.length == 2 ? borrowingPurposes[1] : null);
						if (vehicle.getMoney() > 0) {
							String[] BorrowerName = v2[11].split("=");
							vehicle.setBorrowerName(BorrowerName.length == 2 ? BorrowerName[1]
									: null);
							String[] ItemAddress = v2[12].split("=");
							vehicle.setItemAddress(ItemAddress.length == 2 ? ItemAddress[1]
									: null);
							String[] Remark = v2[13].split("=");
							vehicle.setRemark(Remark.length == 2 ? Remark[1]
									: null);
							String[] YaCarAndGPS = v2[14].split("=");
							vehicle.setYaCarAndGPS(YaCarAndGPS.length == 2 ? YaCarAndGPS[1]
									: null);
							String[] BorrowerIdCard = v2[15].split("=");
							vehicle.setBorrowerIdCard(BorrowerIdCard.length == 2 ? BorrowerIdCard[1]
									: null);
						} else {
							String[] BorrowerName = l[6].split("=");
							vehicle.setBorrowerName(BorrowerName.length == 2 ? BorrowerName[1]
									: null);
							String[] ItemAddress = l[7].split("=");
							vehicle.setItemAddress(ItemAddress.length == 2 ? ItemAddress[1]
									: null);
							String[] Remark = l[8].split("=");
							vehicle.setRemark(Remark.length == 2 ? Remark[1]
									: null);
							String[] YaCarAndGPS = l[9].split("=");
							vehicle.setYaCarAndGPS(YaCarAndGPS.length == 2 ? YaCarAndGPS[1]
									: null);
							String[] BorrowerIdCard = l[10].split("=");
							vehicle.setBorrowerIdCard(BorrowerIdCard.length == 2 ? BorrowerIdCard[1]
									: null);
						}
					} else {
						String[] BuyTime = v2[5].split("=");
						try {
							vehicle.setBuyTime(format.parse(BuyTime[1]));
						} catch (Exception e) {
						}
						String[] secondHandPrice = v2[6].split("=");
						vehicle.setSecondHandPrice(secondHandPrice.length == 2 ? secondHandPrice[1]
								: null);
						
						String[] vehicleType = v2[7].split("=");
						vehicle.setVehicleType(vehicleType.length == 2 ? vehicleType[1] : null);
						
						String[] usingProperties = v2[8].split("=");
						vehicle.setUsingProperties(usingProperties.length == 2 ? usingProperties[1] : null);
						
						String[] manufactureDate = v2[9].split("=");
						try{
							vehicle.setManufactureDate(format.parse(manufactureDate[1]));
						}catch(Exception e){}
						
						String[] registrationDate = v2[10].split("=");
						try{
							vehicle.setRegistrationDate(format.parse(registrationDate[1]));
						}catch(Exception e){}
						
						String[] identificationNumber = v2[11].split("=");
						vehicle.setIdentificationNumber(identificationNumber.length == 2 ? identificationNumber[1] : null);
						
						String[] engineNo = v2[12].split("=");
						vehicle.setEngineNo(engineNo.length == 2 ? engineNo[1] : null);
						
						String[] transmission = v2[13].split("=");
						vehicle.setTransmission(transmission.length == 2 ? transmission[1] : null);
						
						String[] displacement = v2[14].split("=");
						vehicle.setDisplacement(displacement.length == 2 ? displacement[1] : null);	
						
						String[] conditionAssessment = v2[15].split("=");
						vehicle.setConditionAssessment(conditionAssessment.length == 2 ? conditionAssessment[1] : null);
						
						String[] fuel = v2[16].split("=");
						vehicle.setFuel(fuel.length == 2 ? fuel[1] : null);
						
						String[] trafficInsuranceValidity = v2[17].split("=");
						try{
							vehicle.setTrafficInsuranceValidity(format.parse(trafficInsuranceValidity[1]));
						}catch(Exception e){}
						
						String[] inspectionValidity = v2[18].split("=");
						try{
							vehicle.setInspectionValidity(format.parse(inspectionValidity[1]));
						}catch(Exception e){}
						
						String[] lllegalDeduction = v2[19].split("=");
						vehicle.setLllegalDeduction(lllegalDeduction.length == 2 ? lllegalDeduction[1] : null);
						
						String[] violationFines = v2[20].split("=");
						vehicle.setViolationFines(violationFines.length == 2 ? violationFines[1] : null);
						
						String[] borrowingPurposes = v2[21].split("=");
						vehicle.setBorrowingPurposes(borrowingPurposes.length == 2 ? borrowingPurposes[1] : null);	
						
						String[] BorrowerName = l[6].split("=");
						vehicle.setBorrowerName(BorrowerName.length == 2 ? BorrowerName[1]
								: null);
						String[] ItemAddress = l[7].split("=");
						vehicle.setItemAddress(ItemAddress.length == 2 ? ItemAddress[1]
								: null);
						String[] Remark = l[8].split("=");
						vehicle.setRemark(Remark.length == 2 ? Remark[1] : null);
						String[] YaCarAndGPS = l[9].split("=");
						vehicle.setYaCarAndGPS(YaCarAndGPS.length == 2 ? YaCarAndGPS[1]
								: null);
						String[] OtherInfo = l[10].split("=");
						vehicle.setOtherInfo(OtherInfo.length == 2 ? OtherInfo[1]
								: null);
						String[] BorrowerIdCard = l[11].split("=");
						vehicle.setBorrowerIdCard(BorrowerIdCard.length == 2 ? BorrowerIdCard[1]
								: null);
					}
					String[] GuaranteeType = l[0].split("=");
					vehicle.setGuaranteeType(GuaranteeType.length == 2 ? GuaranteeType[1]
							: null);
					String[] GuaranteeRate = l[1].split("=");
					vehicle.setGuaranteeRate(GuaranteeRate.length == 2 ? GuaranteeRate[1]
							: null);
					String[] OtherLoanInfo = l[2].split("=");
					vehicle.setOtherLoanInfo(OtherLoanInfo.length == 2 ? OtherLoanInfo[1]
							: null);
					if (status.equals("less")) {
						String[] ReminderInfo = l[3].split("=");
						vehicle.setReminderInfo(ReminderInfo.length == 2 ? ReminderInfo[1]
								: null);
						String[] MeasuresInfo = l[4].split("=");
						vehicle.setMeasuresInfo(MeasuresInfo.length == 2 ? MeasuresInfo[1]
								: null);
						String[] OverdueInfo = l[5].split("=");
						vehicle.setOverdueInfo(OverdueInfo.length == 2 ? OverdueInfo[1]
								: null);
					} else {
						String[] Mortgagee = l[3].split("=");
						vehicle.setMortgagee(Mortgagee.length == 2 ? Mortgagee[1]
								: null);
						String[] SupervisionMode = l[4].split("=");
						vehicle.setSupervisionMode(SupervisionMode.length == 2 ? SupervisionMode[1]
								: null);
						String[] OverdueHandling = l[5].split("=");
						vehicle.setOverdueHandling(OverdueHandling.length == 2 ? OverdueHandling[1]
								: null);
					}
					vehicles.add(vehicle);
				} else {
					String[] GuaranteeType = l[0].split("=");
					vehicle.setGuaranteeType(GuaranteeType.length == 2 ? GuaranteeType[1]
							: null);
					String[] GuaranteeRate = l[1].split("=");
					vehicle.setGuaranteeRate(GuaranteeRate.length == 2 ? GuaranteeRate[1]
							: null);
					String[] OtherLoanInfo = l[2].split("=");
					vehicle.setOtherLoanInfo(OtherLoanInfo.length == 2 ? OtherLoanInfo[1]
							: null);
					if (status.equals("less")) {
						String[] ReminderInfo = l[3].split("=");
						vehicle.setReminderInfo(ReminderInfo.length == 2 ? ReminderInfo[1]
								: null);
						String[] MeasuresInfo = l[4].split("=");
						vehicle.setMeasuresInfo(MeasuresInfo.length == 2 ? MeasuresInfo[1]
								: null);
						String[] OverdueInfo = l[5].split("=");
						vehicle.setOverdueInfo(OverdueInfo.length == 2 ? OverdueInfo[1]
								: null);
					} else {
						String[] Mortgagee = l[3].split("=");
						vehicle.setMortgagee(Mortgagee.length == 2 ? Mortgagee[1]
								: null);
						String[] SupervisionMode = l[4].split("=");
						vehicle.setSupervisionMode(SupervisionMode.length == 2 ? SupervisionMode[1]
								: null);
						String[] OverdueHandling = l[5].split("=");
						vehicle.setOverdueHandling(OverdueHandling.length == 2 ? OverdueHandling[1]
								: null);
						vehicle.setLoanId(v1.getLoanId());
					}
					if (v1.getMoney() <= 0) {
						String[] BorrowerName = l[6].split("=");
						vehicle.setBorrowerName(BorrowerName.length == 2 ? BorrowerName[1]
								: null);
						String[] ItemAddress = l[7].split("=");
						vehicle.setItemAddress(ItemAddress.length == 2 ? ItemAddress[1]
								: null);
						String[] Remark = l[8].split("=");
						vehicle.setRemark(Remark.length == 2 ? Remark[1] : null);
						String[] YaCarAndGPS = l[9].split("=");
						vehicle.setYaCarAndGPS(YaCarAndGPS.length == 2 ? YaCarAndGPS[1]
								: null);
						String[] BorrowerIdCard = l[11].split("=");
						vehicle.setBorrowerIdCard(BorrowerIdCard.length == 2 ? BorrowerIdCard[1]: null);
					}

					vehicles.add(vehicle);

				}

			}
			if (totalMoney > 0) {
				loan.setTotalmoney(loan.getTotalmoney() + totalMoney);
			}
			os.add(vehicles);
			os.add(sb.toString());
			return os;
		} else if ("house".equals(loanType)) {
			House house = loanDetailService.readHouseDetail(loanId);
			if (house == null) {
				house = new House();
				house.setLoanId(loanId);
				os.add("insert");
			} else {
				os.add("update");
			}
			String position = request.getParameter("position");
			String nature = request.getParameter("nature");
			String area = request.getParameter("area");
			String assessPrice = request.getParameter("assessPrice");
			String guaranteeType = request.getParameter("guaranteeType");
			String mortgagee = request.getParameter("mortgagee");
			String guaranteeRate = request.getParameter("guaranteeRate");
			String overdueHandling = request.getParameter("overdueHandling");
			String otherDescription = request.getParameter("otherDescription");
			// 新
			String reminderInfo = request.getParameter("reminderInfo");
			String measuresInfo = request.getParameter("measuresInfo");
			String overdueInfo = request.getParameter("overdueInfo");
			String otherInfo = request.getParameter("otherInfo");

			String itemAddress = request.getParameter("itemAddress");
			String borrowerName = request.getParameter("borrowerName");
			String borrowerIdCard = request.getParameter("borrowerIdCard");
			String remark = request.getParameter("remark");
			house.setOtherInfo(otherInfo);
			house.setItemAddress(itemAddress);
			house.setBorrowerIdCard(borrowerIdCard);
			house.setBorrowerName(borrowerName);
			house.setRemark(remark);
			if (StringUtils.isBlank(reminderInfo) && status.equals("less")) {
				sb.append("reminderInfo,");
			} else {
				house.setReminderInfo(reminderInfo);
			}
			if (StringUtils.isBlank(measuresInfo) && status.equals("less")) {
				sb.append("measuresInfo,");
			} else {
				house.setMeasuresInfo(measuresInfo);
			}
			if (StringUtils.isBlank(overdueInfo) && status.equals("less")) {
				sb.append("overdueInfo,");
			} else {
				house.setOverdueInfo(overdueInfo);
			}

			if (StringUtils.isBlank(position)) {
				sb.append("position,");
			} else {
				house.setPosition(position);
			}

			if (StringUtils.isBlank(nature)) {
				sb.append("nature,");
			} else {
				house.setNature(nature);
			}

			if (StringUtils.isBlank(area)) {
				sb.append("area,");
			} else {
				house.setArea(area);
			}

			if (StringUtils.isBlank(assessPrice)) {
				sb.append("assessPrice,");
			} else {
				house.setAssessPrice(assessPrice);
			}

			if (StringUtils.isBlank(guaranteeType)) {
				sb.append("guaranteeType,");
			} else {
				house.setGuaranteeType(guaranteeType);
			}
			if (StringUtils.isBlank(guaranteeRate) && status.equals("more")) {
				sb.append("guaranteeRate,");
			} else {
				house.setGuaranteeRate(guaranteeRate);
			}

			if (StringUtils.isBlank(overdueHandling) && status.equals("more")) {
				sb.append("overdueHandling,");
			} else {
				house.setOverdueHandling(overdueHandling);
			}
			house.setMortgagee(mortgagee);
			house.setOtherDescription(otherDescription);
			os.add(house);
			os.add(sb.toString());
			return os;
		} else if ("ruralfinance".equals(loanType)) {
			LoanRuralfinance ruralfinance = loanDetailService
					.readRuralfinanceDetail(loanId);
			if (ruralfinance == null) {
				ruralfinance = new LoanRuralfinance();
				ruralfinance.setLoanId(loanId);
				ruralfinance.setId(IdGenerator.randomUUID());
				os.add("insert");
			} else {
				os.add("update");
			}
			String loanApplication = request.getParameter("loanApplication");
			String repaymentSource = request.getParameter("repaymentSource");
			String riskControlInformation = request
					.getParameter("riskControlInformation");
			String reminderInfo = request.getParameter("reminderInfo");
			String measuresInfo = request.getParameter("measuresInfo");
			String overdueInfo = request.getParameter("overdueInfo");
			String otherInfo = request.getParameter("otherInfo");
			ruralfinance.setReminderInfo(reminderInfo);
			ruralfinance.setMeasuresInfo(measuresInfo);
			ruralfinance.setOverdueInfo(overdueInfo);
			ruralfinance.setOtherInfo(otherInfo);
			ruralfinance.setLoanApplication(loanApplication);
			ruralfinance.setRepaymentSource(repaymentSource);
			ruralfinance.setRiskControlInfo(riskControlInformation);
			ruralfinance.setLastAlterTime(new Date());
			os.add(ruralfinance);
			os.add(sb.toString());
			return os;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateImage(String op, String picId, String loanId) {
		if ("del".equals(op)) {
			BannerPicture bannerPicture = loanDao.getBannerPictureById(picId);
			bannerPicture.setUrl("del");
			loanDao.updateBannerPicture(bannerPicture, loanId);
		}
	}

	@Override
	public String cancelByInvestId(String investId) {
		Invest invest = investDao.get(investId);
		String loanId = invest.getLoanId();
		Loan loan = loanDao.get(loanId);
		boolean flag = true;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		// 商户编号:商户在易宝唯一标识
		content.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
		content.append("<platformUserNo>" + invest.getInvestUserID()
				+ "</platformUserNo>");
		content.append("<requestNo>" + invest.getId() + "</requestNo>");
		content.append("</request>");

		// 保存操作信息
		TrusteeshipOperation to = trusteeshipOperationService.read(
				TrusteeshipYeepayConstants.OperationType.INVEST,
				invest.getId(), invest.getId(), "yeepay");
		if (to == null) {
			to = trusteeshipOperationService.read(
					TrusteeshipYeepayConstants.OperationType.AUTOINVEST,
					invest.getId(), invest.getId(), "yeepay");
		}
		to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
		to.setRequestTime(new Date());
		to.setRequestData(content.toString());
		trusteeshipOperationService.update(to);

		postMethod.setParameter("req", content.toString());
		String sign = CFCASignUtil.sign(content.toString());
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "REVOCATION_TRANSFER");
		// 执行post方法
		try {
			int statusCode = client.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.infoLog("流标失败", "在执行流标时失败,httpStatus错误:" + HttpStatus.SC_OK
						+ postMethod.getStatusLine());
			}
			// 获得返回的结果
			byte[] responseBody = postMethod.getResponseBody();
			// 响应信息
			@SuppressWarnings("unchecked")
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(new String(
					responseBody, "UTF-8"));
			// 返回码
			String code = resultMap.get("code");
			// 描述
			if (code.equals("1")) {// 如果取消投标成功，执行业务逻辑。
				try {
					userAccountService.unfreeze(invest.getInvestUserID(),
							invest.getMoney(), BusinessEnum.bidders, "解冻：投资"
									+ loan.getName(), "借款ID：" + loan.getId(),
							invest.getId());
				} catch (NoOpenAccountException e) {
					log.errLog("解冻异常", e);
					e.printStackTrace();
				} catch (InsufficientFreezeAmountException e) {
					log.errLog("解冻异常", e);
					e.printStackTrace();
				}
				// 更改投资状态
				invest.setStatus(InvestConstants.InvestStatus.CANCEL);
				User user = userDao.get(invest.getInvestUserID());
				user.setInvestMoneyTotal(user.getInvestMoneyTotal()
						- invest.getMoney());
				/*
				 * if ("Y".equals(invest.getReturnPond())) {
				 * user.setInvestMoneyTotal1(user.getInvestMoneyTotal1() +
				 * invest.getMoney()); } invest.setReturnPond(null);
				 */
				investDao.update(invest);
				userDao.update(user);
			} else {
				flag = false;
			}
			to.setStatus(TrusteeshipConstants.Status.PASSED);
			to.setResponseData(new String(responseBody, "UTF-8"));
			to.setResponseTime(new Date());
			trusteeshipOperationService.update(to);
		} catch (Exception e) {
			log.infoLog(invest.getId() + "流标失败", e);
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return flag ? "流标成功" : "流标失败";
	}

	@Override
	public void updateImageSeqNum(String picId, String seqNum) {
		BannerPicture bannerPicture = loanDao.getBannerPictureById(picId);
		if (!StringUtils.isBlank(seqNum) || NumberUtils.isNumber(seqNum)) {
			bannerPicture.setSeqNum(Integer.parseInt(seqNum));
		}
		loanDao.updateBannerPicture(bannerPicture, null);
	}

	@Override
	public List<FixedBorrowers> readAllFixedBorrowers() {
		List<FixedBorrowers> findAllFixedBorrowers = loanDao
				.findAllFixedBorrowers();
		return findAllFixedBorrowers;
	}

	@Override
	public void addFixedBorrower(String userId, String email) {
		User user = new User();
		if (StringUtils.isNumeric(userId) && StringUtils.length(userId) == 11) {
			user.setMobileNumber(userId);
		} else if (StringUtils.contains(userId, "@")) {
			user.setEmail(userId);
		} else {
			user.setUserId(userId);
		}
		User verifyLogin = userDao.verifyLogin(user);
		loanDao.insertFixedBorrower(verifyLogin.getUserId(), email);
	}

	@Override
	public void alterFixedBorrowersStatus(String borrower, String status,
			String email) {
		loanDao.alterFixedBorrowersStatus(borrower, status, email);
	}

	@Override
	public String updateOrganizationExclusiveStatus(String loanId) {
		if (StringUtils.isBlank(loanId)) {
			return "借款项目id不正确";
		} else {
			Loan loan = loanDao.get(loanId);
			loan.setStatus(LoanConstants.LoanStatus.COMPLETE);
			Invest i = new Invest();
			i.setStatus(InvestConstants.InvestStatus.REPAYING);
			i.setLoanId(loanId);
			List<Invest> invests = investDao.getInvestLoan(i);
			if (invests != null && invests.size() > 0) {
				i = invests.get(0);
				i.setStatus(InvestConstants.InvestStatus.COMPLETE);
			}
			loanDao.update(loan);
			investDao.update(i);
			return "ok";
		}
	}

	@Override
	public double readTotalMoney(Map map) {
		if (loanDao.getTotalMoney(map) == null)
			return 0;
		else
			return loanDao.getTotalMoney(map);
	}

	@Override
	public List<Loan> readLoan(Loan loan) {
		return loanDao.find(loan);
	}

	/**
	 * 查询可以推送网贷天眼的项目列表
	 * 
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	public PageInfo<Loan> readWaitPushLoanList(String pageNo, Loan loan) {
		PageHelper.startPage(Integer.parseInt(pageNo), 10);
		List<Loan> lst = loanDao.getWaitPushLoanList(loan);
		return new PageInfo<>(lst);
	}

	@Override
	public int updateLoan(Loan loan) {

		return loanDao.updateLoan(loan);
	}

	@Override
	public PageInfo<Loan> readPageList(int pageNo, int pageSize, Loan loan) {
		// TODO Auto-generated method stub
		return loanDao.pageList(pageNo, pageSize, loan);
	}

	@Override
	public void updateImageTitle(String picId, String title, String loanId) {
		BannerPicture bannerPicture = loanDao.getBannerPictureById(picId);
		if (!StringUtils.isBlank(title)) {
			bannerPicture.setTitle(title);
		}
		loanDao.updateBannerPicture(bannerPicture, loanId);

	}

	@Override
	public int updateBatchPicTitle(Map<String, Object> params, String loadId) {
		return loanDao.updateBatchPicTitle(params, loadId);
	}
	@Override
	public void updateSortNum(Loan loan) {
		loanDao.updateSortNum(loan);
	}

	/**
	 * 根据项目id读取所有项目
	 */
	@Override
	public List<Loan> readSubLoan(String subLoanId) {
		return loanDao.getSubLoan(subLoanId);
	}

	/**
	 * 删除测试项目释放资源
	 * 
	 */
	@Override
	public void delLoans(List<Loan> loans,String loanId) {
		//将项目isdeal设置为1，不可以
		loanDao.updateBatchLoan(loans);
		String subLoanId = loanId.substring(0, 16);
		List<TransferStationForkLoans> transferStationForkLoans = transferStationService.readLoanForkIntermediariesIdByLoadId(subLoanId);
		if (transferStationForkLoans.size()!=0) {
			/*for (int i = 0; i < transferStationForkLoans.size(); i++) {
				transferStationForkLoans.get(i).setPacking(0);
				transferStationForkLoans.get(i).setLoanId(null);
				transferStationForkLoans.get(i).setRemark(null);
				
			}*/
			transferStationService.updateBatchForkIntermediaries(transferStationForkLoans);
		}else {
			List<TransferStation> transferStations = transferStationService.readLoanTransferIntermediariesIdByLoadId(subLoanId);
			if (transferStations.size()!=0) {
				/*for (int i = 0; i < transferStations.size(); i++) {
					transferStations.get(i).setAllocationStatus(0);
					transferStations.get(i).setChannelName(null);
					transferStations.get(i).setInstitutionName(null);
					transferStations.get(i).setUnpackLoanId(null);
					System.out.println("transferStations循环。。。");
				}*/  
				System.out.println("updateBatchIntermediaries开始。。。。");
				transferStationService.updateBatchIntermediaries(transferStations);
				System.out.println("updateBatchIntermediaries结束。。。。");
			}
		}
	}
}