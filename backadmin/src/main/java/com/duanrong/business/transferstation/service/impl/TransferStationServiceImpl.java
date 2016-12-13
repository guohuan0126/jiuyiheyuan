package com.duanrong.business.transferstation.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;
import base.schedule.constants.ScheduleConstants;
import base.schedule.job.CheckLoanRepayTime;

import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.business.demand.service.DemandTreasureLoanService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.BannerPicture;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.RepayDate;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.loan.service.LoanDetailService;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.transferstation.dao.TransferStationDao;
import com.duanrong.business.transferstation.model.TransferStation;
import com.duanrong.business.transferstation.model.TransferStationForkLoans;
import com.duanrong.business.transferstation.service.TransferStationService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.ForkloansCompute;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.newadmin.utility.InterestAccrual;
import com.duanrong.util.IdUtil;
import com.duanrong.util.ToType;

@Service
public class TransferStationServiceImpl implements TransferStationService {
	@Resource
	TransferStationDao transferStationDao;
	@Resource
	LoanDao loanDao;
	@Resource
	LoanDetailService loanDetailService;
	@Resource
	LoanService loanService;
	@Resource
	DemandTreasureLoanService demandTreasureLoanService;
	@Resource
	RepayService repayService;
	@Resource
	StdScheduler scheduler;
	@Resource
	InvestDao investDao;
	@Resource
	UserMoneyService userMoneyService;

	private static final byte[] lock = new byte[0];
	private static final byte[] lockloan = new byte[0];

	@Override
	public PageInfo<TransferStation> readTransferStation(int pageNo,
			int pageSize, Map<String, Object> params) {
		return transferStationDao.findTransferStation(pageNo, pageSize, params);
	}

	@Override
	public int delTransferLoan(String id) {
		/*
		 * transferStationDao.delete(id); this.delVeIntermediaries(id); int
		 * flag= this.delPicsIntermediaries(id);
		 */
		int flag = this.updateIntermediaries(id);
		return flag;
	}

	@Override
	public int delVeIntermediaries(String loanId) {
		return transferStationDao.delVeIntermediaries(loanId);
	}

	@Override
	public int delPicsIntermediaries(String loanId) {
		return transferStationDao.delPicsIntermediaries(loanId);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String allocationDemandTreasure(String[] ids, UserCookie getLoginUser) {
		try {
			synchronized (lock) {
				for (String id : ids) {
					// System.out.println(id);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);
					params.put("allocationStatus", 0);
					TransferStation transferStation = this.readTransferStationById(params);
					if (transferStation != null
							&& transferStation.getAllocationStatus() == 0) {
						String repayType = transferStation.getRepayType();
						// 判断还款方式是等额本息还是先息后本，先息后本可以直接插入到活期资产表里，等额本息要进行拆标后主标和子标一起存到活期资产表
						DemandtreasureLoan demandtreasureLoan = new DemandtreasureLoan();
						demandtreasureLoan.setTotalMoney(transferStation
								.getMoney());
						demandtreasureLoan.setLoanType("车押宝");
						demandtreasureLoan.setOperationType(transferStation
								.getOperationType());
						if (transferStation.getOperationType().equals("月")) {
							demandtreasureLoan.setMonth(transferStation
									.getDeadline());
						} else {
							demandtreasureLoan.setDay(transferStation
									.getDeadline());
						}
						demandtreasureLoan.setLoanStatus("repay");
						demandtreasureLoan.setLoanAddr(transferStation
								.getVehicleInfo().getItemAddress());
						demandtreasureLoan.setStartTime(new Date());
						demandtreasureLoan.setBorrower(transferStation
								.getVehicleInfo().getBorrowerName());
						demandtreasureLoan.setIdCard(transferStation
								.getVehicleInfo().getBorrowerIdCard());
						demandtreasureLoan.setBrand(transferStation
								.getVehicleInfo().getBrand());
						demandtreasureLoan
								.setLicensePlateNumber(transferStation
										.getVehicleInfo()
										.getLicensePlateNumber());
						//活期宝里车辆购买时间变为初次登记日期
						demandtreasureLoan.setBuyTime(transferStation
										.getVehicleInfo().getRegistrationDate());
						demandtreasureLoan.setKilometreAmount(transferStation
								.getVehicleInfo().getKilometreAmount());
						demandtreasureLoan.setAssessPrice(transferStation
								.getVehicleInfo().getAssessPrice());
						if(transferStation.getVehicleInfo().getGuaranteeType()!=null && "A".equals(transferStation.getVehicleInfo().getGuaranteeType())){
							demandtreasureLoan.setGuaranteeType("质押");
					    }else{
					    	demandtreasureLoan.setGuaranteeType("抵押");		   
						}						
						demandtreasureLoan.setGuaranteeRate(transferStation
								.getVehicleInfo().getGuaranteeRate());
						demandtreasureLoan.setOtherInfo(transferStation
								.getVehicleInfo().getOtherInfo());
						demandtreasureLoan.setContractId(transferStation
								.getContractId());
						demandtreasureLoan.setSourceRemark("system");
						demandtreasureLoan
								.setAccountingDepartment(transferStation
										.getAccountingDepartment());
						if (repayType != null && "先息后本".equals(repayType)) {
							demandtreasureLoan.setRepayType("先息后本");
						} else if (repayType != null
								&& "等额本息".equals(repayType)) {
							demandtreasureLoan.setRepayType("等额本息");
						}
						// 调用活期宝添加资产的方法
						String result = demandTreasureLoanService
								.addLoanCommon(demandtreasureLoan, getLoginUser);
						if ("sucess".equals(result)) {
							// 项目添加到活期宝之后，修改中转站项目的分配状态
							Map<String, Object> demandParam = new HashMap<>();
							demandParam.put("allocationStatus", 1);
							demandParam.put("channelName", "活期");
							demandParam.put("id", id);
							transferStationDao
									.updateTransferStation(demandParam);
						}
					}
				}
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	@Override
	public int updateTransferStation(Map<String, Object> params) {
		return transferStationDao.updateTransferStation(params);
	}

	@Override
	public int updateBatchTransferStation(Map<String, Object> params) {
		return transferStationDao.updateBatchTransferStation(params);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String allocationLoan(String[] ids, UserCookie getLoginUser,
			String type, String institutionName,String newSubject) {
		try {
			synchronized (lock) {
				int nameNum=this.readLoanNumber();
				for (String id : ids) {
					// System.out.println(id);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);
					params.put("allocationStatus", 0);
					TransferStation transferStation =readTransferStationById(params);
					if (transferStation != null
							&& transferStation.getAllocationStatus() == 0) {
						String repayType = transferStation.getRepayType();
						// 判断还款方式是等额本息还是先息后本，先息后本可以直接插入定期项目里，等额本息要进行拆标后把子标信息添加到子标表里面
						boolean isTrue = false;
						String loanId = IdGenerator.generateLoanId();	
						if ("等额本息".equals(repayType) && "定期".equals(type)) {
							// 调用等额本息拆标方法存入子标表中
							isTrue = this
									.TransferStationForkLoans(transferStation);
						} else {
							// 调用存入定期和机构标方法
							if("先息后本".equals(transferStation.getRepayType())&&transferStation.getMoney()>200000.0){
								double totalMoney=transferStation.getMoney();
								double chushu =200000.0;
								double n= Math.ceil(totalMoney/chushu);
								int i=(int)n;
								for (int j = 1; j <= i; j++) {
									String loanIds = loanId.substring(0,16)+"0"+j;	
									nameNum++;
									isTrue = this.verifyLoanAttrs(transferStation,
											getLoginUser, type,loanIds,newSubject,i,j,nameNum);		
								}								
							}else{	
								nameNum++;
								isTrue = this.verifyLoanAttrs(transferStation,
											getLoginUser, type,loanId,newSubject,0,0,nameNum);
							}
						}
						if (isTrue) {
							// 项目添加到定期项目之后，修改中转站项目的分配状态
							Map<String, Object> demandParam = new HashMap<>();
							demandParam.put("allocationStatus", 1);
							demandParam.put("channelName", type);
							if ("机构线上".equals(type)) {
								demandParam.put("institutionName",
										institutionName);
							}
							if (!"等额本息".equals(repayType) || !"定期".equals(type)) {
							demandParam.put("unpackLoanId", loanId);
							}
							demandParam.put("id", id);							
							transferStationDao
									.updateTransferStation(demandParam);
						} else {
							return "fail";
						}
					}
				}
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean verifyLoanAttrs(TransferStation transferStation,
			UserCookie getLoginUser, String type,String loanId,String newSubject,Integer i,Integer j,Integer loanNumber) {
		try {
			synchronized (lock) {
			Loan loan = new Loan();
			// operationType 计算方式
			String operationType = transferStation.getOperationType();
			loan.setOperationType(operationType);
			//车辆信息里面的A新增，B展期/续贷D展期/老续贷
			String remark = transferStation.getVehicleInfo().getRemark();
			loan.setInstructionNo("");
			// 是否机构专享
			
			if ("定期".equals(type)) {
				loan.setOrganizationExclusive("否");
			} else if ("机构线上".equals(type)) {
				loan.setOrganizationExclusive("是");
			}
			// 新手专享
			if(newSubject!=null && "isnewSubject".equals(newSubject)&&"定期".equals(type)){
				loan.setNewbieEnjoy("是");
			}else{
				loan.setNewbieEnjoy("否");
			}			
			// repayType 还款方式
			if ("先息后本".equals(transferStation.getRepayType())) {
				if("月".equals(operationType)&&transferStation.getDeadline()==1){
					loan.setRepayType("一次性到期还本付息");
				}else{
					loan.setRepayType("按月付息到期还本");
				}				
			} else if ("等额本息".equals(transferStation.getRepayType())) {
				loan.setRepayType("等额本息");
			}
			// day 天标下,天数
			if ("天".equals(operationType)) {
				int dayParseInt = transferStation.getDeadline();
				loan.setDay(dayParseInt);
				loan.setBeforeRepay("否");
			}
			//新增和续贷项目全部挂到qsd001（覃世东）账户；
          //老项目进入系统后，短融的挂ceh1234（张凯）,；齐海的挂mMZbEzYFzIrehguk（郑卉芳）
         /* if(remark!=null && "D".equals(remark)){
        	  if (transferStation.getAccountingDepartment() != null
						&& transferStation.getAccountingDepartment() == 0) {
					loan.setBorrowMoneyUserID("mMZbEzYFzIrehguk");
				} else {
					loan.setBorrowMoneyUserID("ceh1234");
				}
          }else{
        	  loan.setBorrowMoneyUserID("EBFZvmiaMrAfjswz");
          }*/
			loan.setBorrowMoneyUserID(transferStation.getBorrowerId());
			loan.setLoanType(transferStation.getLoanType());
			// totalMoney 借款总金额
			Double totalMoneyD=0.0;
			//i为先息后本大于20万要分配几个项目，j为当前第几个项目
			if(i>0 && j>=0){
			  totalMoneyD =InterestAccrual.getaverageMoney(transferStation.getMoney(), 200000.0, i, j);
			}else{
			  totalMoneyD = transferStation.getMoney();	
			}			
			loan.setCompanyno("0"); // 单车
			loan.setTotalmoney(totalMoneyD);
			// minInvestMoney 投资起点金额
			loan.setInvestOriginMoney(1.0);// 投资起点金额
			loan.setIncreaseMoney(1.0);// 单位递增金额
			loan.setMaxInvestMoney(0.0);// 投资上限金额
			loan.setAutoInvestMoneyTotal(totalMoneyD / 2);// 自动投标上限金额
			loan.setLoanGuranteeFee(0.0);// 借款管理费
			loan.setInterestRule("投资次日计息");// 计息规则
			// 是否机构专享
			// vehicleLoanPic.setDeposit(0);//是否为预告项目新创建的项目状态是筹款中
			loan.setStatus(LoanConstants.LoanStatus.RAISING);
			loan.setExpectTime(DateUtil.addMonth(new Date(), 2));// 前台需要的默认值
			loan.setTextItem("是");// 是否为测试项目
			loan.setWhetherInvested(0);//是否可投0不可投，1为可投
		    // 是否新手项目
			loan.setSourceRemark("system");//
			// maxInvestMoney 投资金额上限
			// rate 借款利率
			/*
			 * 5天的新手标 年化是14% 10天的定期标 年化是8% 1-2个月的定期标 年化9% 3-4个月的定期标 年化10%
			 * 5-6个月的定期标 年化11% 7及以上的定期标 年化12%
			 */
			// guranteeFee 借款管理费
			double rate = 0.0;
			if ("月".equals(operationType)) {
				// deadline 借款期限
				int deadlineI = transferStation.getDeadline();
				loan.setDeadline(deadlineI);
				loan.setBeforeRepay("否");
				if (deadlineI >= 3 && deadlineI <= 4) {
					rate = 10.0;
				} else if (deadlineI >= 5 && deadlineI <= 6) {
					rate = 11.0;
				} else if (deadlineI >= 7) {
					rate = 12.0;
				} else {
					rate = 9.0;
				}

			} else {
				rate = 9.0;
			}
			rate = ArithUtil.round(rate / 100, 4);
			if(newSubject!=null && "isnewSubject".equals(newSubject)&&"定期".equals(type)){
				loan.setRate(0.14);
			}else{
				loan.setRate(rate);
			}	
			loan.setCommitTime(new Date());
			
			if ("是".equals(loan.getOrganizationExclusive())) {			
				loan.setStatus(LoanConstants.LoanStatus.REPAYING);
			}
			
			// 是新手标就不能是机构标
			String itemAddress = transferStation.getVehicleInfo()
					.getItemAddress();
			String borrowerName = transferStation.getVehicleInfo()
					.getBorrowerName();
			
			String yaCarAndGPS = transferStation.getVehicleInfo()
					.getYaCarAndGPS();
			loan.setItemAddress(itemAddress);
			loan.setBorrowerName(borrowerName);
			if(remark!=null && ("D".equals(remark)||"B".equals(remark))){
				loan.setRemark("展期");
	         }else{
	        	loan.setRemark("新增"); 
	         }			
			loan.setYaCarAndGPS(yaCarAndGPS);
			Date d = new Date();
			loan.setVerifyUser(getLoginUser.getUserId());
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
			loan.setSpecialType("");
			loan.setSourceRemark("system");
			loan.setContractId(transferStation.getContractId());
			loan.setAccountingDepartment(transferStation
					.getAccountingDepartment());
			loan.setExpectTime(loan.getCommitTime());
			loan.setCreateTime(loan.getCommitTime());
			if (StringUtils.isBlank(loan.getId())) {
				// 如果项目id为空则保存改项目
				loan.setId(loanId);
				// loan.setId("0" + loan.getId().substring(1));
				//Date date = null;
				if ("是".equals(loan.getOrganizationExclusive())) {
					//SimpleDateFormat sdf = new SimpleDateFormat(
						//	"yyyy-MM-dd HH:mm:ss");
					Date giveMoneyTime = loan.getCommitTime();
					loan.setGiveMoneyTime(DateUtil.addDay(giveMoneyTime, 1));//计息时间
					loan.setGiveMoneyOperationTime(giveMoneyTime);
					if(transferStation.getContractEndTime()!=null){
					loan.setRepayTime(transferStation.getContractEndTime());
					}else{
					loan.setRepayTime(DateUtil.addMonth(new Date(),
							transferStation.getDeadline()));}
				}
				
				// 获取项目名称的最大值
				String name =com.duanrong.newadmin.utility.IdUtil.getVihicheSortNum(loanNumber);
				loan.setName(name);
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
			}
			// 获取车辆的详细信息
			Vehicle vehicle = transferStation.getVehicleInfo();
			vehicle.setLoanId(loanId);
			vehicle.setMoney(0.0);
			if(remark!=null && ("D".equals(remark)||"B".equals(remark))){
				vehicle.setRemark("展期");
	         }else{
	        	 vehicle.setRemark("新增"); 
	         }	
			if(vehicle.getGuaranteeType()!=null && "A".equals(vehicle.getGuaranteeType())){
				vehicle.setGuaranteeType("质押");//质押，抵押
		    }else{
		    	vehicle.setGuaranteeType("抵押");			   
			}
			vehicle.setKilometreAmount(vehicle.getKilometreAmount().replace(".000000", "公里"));
			List<Vehicle> ve = new ArrayList<>();
			ve.add(vehicle);
			loanDetailService.insertVehicle((List<Vehicle>) ve, loan);
			// 添加图片信息
			BannerPicture bp = new BannerPicture();
			bp.setId(IdGenerator.randomUUID());
			bp.setTitle("车辆图片");
			bp.setPicture(transferStation.getUrl());
			bp.setSeqNum(0);
			loanService.insertBannerPicture(bp, loanId);
			return true;
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public int updateIntermediaries(String id) {

		return transferStationDao.updateIntermediaries(id);
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

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean TransferStationForkLoans(TransferStation transferStation) {
		try {
			int loan_term = transferStation.getDeadline();// 借款期限
			double money = transferStation.getMoney();// 合同金额
			double rate = 0.18;// 车贷利率规定0.18
			String childContractid = transferStation.getContractId();// 父标合同
			String parentId = transferStation.getId();
			Date createTime = new Date();
			double benjin = 0.0;
			double benjinhe = 0.0;
			//List<TransferStationForkLoans> list = new ArrayList<TransferStationForkLoans>();
			for (int i = 1; i <= loan_term; i++) {
				TransferStationForkLoans trsfsaforkLoans = new TransferStationForkLoans();
				trsfsaforkLoans.setId(IdGenerator.randomUUID());
				//取100的整数倍
				/*benjin = Math.floor(ForkloansCompute.corpus(money, rate,
						loan_term, i) / 100) * 100;*/
				//取整，舍小数
				benjin = Math.floor(ForkloansCompute.corpus(money, rate,
						loan_term, i));
				if (i == loan_term) {
					benjin = money - benjinhe;
				}
				benjinhe += benjin;
				trsfsaforkLoans.setForkMoney(benjin);
				trsfsaforkLoans.setChildContractid(childContractid + i);
				trsfsaforkLoans.setLoanTerm(i);
				trsfsaforkLoans.setPacking(0);
				trsfsaforkLoans.setParentId(parentId);
				trsfsaforkLoans.setCreateTime(createTime);
				trsfsaforkLoans.setLoanType("车贷");
				trsfsaforkLoans.setRepayType("先息后本");
				trsfsaforkLoans.setStatus(1);
				trsfsaforkLoans.setAccountingDepartment(transferStation
						.getAccountingDepartment());
				transferStationDao.insert(trsfsaforkLoans);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insert(TransferStationForkLoans TransferStationForkLoans) {
		transferStationDao.insert(TransferStationForkLoans);

	}

	@Override
	public PageInfo<com.duanrong.business.transferstation.model.TransferStationForkLoans> readTransferStationForkLoans(
			int pageNo, int pageSize, Map<String, Object> params) {
		return transferStationDao.readTransferStationForkLoans(pageNo,
				pageSize, params);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String allocationForkLoan(String[] ids, UserCookie getLoginUser,
			String type, String institutionName, String deadline,
			String[] forkIds, Double money, String company,String newSubject,String borrowerId) {
		boolean isTrue = false;
		Map<String, Object> params = new HashMap<String, Object>();
		try {
	   String loanId = IdGenerator.generateLoanId();
	   synchronized (lockloan) {
			int nameNum=this.readLoanNumber();
			if (ids.length == 1) {
				String id=ids[0];
				params.put("id",id);
				TransferStation transferStation =readTransferStationById(params);
				transferStation.setMoney(money);
				transferStation.setDeadline(Integer.parseInt(deadline));
				transferStation.setBorrowerId(borrowerId);
				transferStation.setAccountingDepartment(Integer.parseInt(company));
				transferStation.setOperationType("月");
				transferStation.setRepayType("先息后本");
				if (transferStation != null) {	
					if(money>200000.0){
						double totalMoney=money;
						double chushu =200000.0;
						double n= Math.ceil(totalMoney/chushu);
						int i=(int)n;
						for (int j = 1; j <= i; j++) {
							String loanIds = loanId.substring(0,16)+"0"+j;	
							nameNum++;
						    isTrue =this.verifyLoanAttrs(transferStation,
									getLoginUser, type,loanIds,newSubject,i,j,nameNum);		
						}
					}else{
						nameNum++;
						isTrue = this.verifyLoanAttrs(transferStation,
								getLoginUser, type,loanId,newSubject,0,0,nameNum);
					}					
				}
			} else if (ids.length > 1) {
				Loan loan = new Loan();
				// operationType 计算方式
				loan.setOperationType("月");
				loan.setInstructionNo("");
				// 是否机构专享
				if ("定期".equals(type)) {
					loan.setOrganizationExclusive("否");
				} else if ("机构线上".equals(type)) {
					loan.setOrganizationExclusive("是");
				}
				// 新手专享
				if(newSubject!=null && "isnewSubject".equals(newSubject)&&"定期".equals(type)){
					loan.setNewbieEnjoy("是");
				}else{
					loan.setNewbieEnjoy("否");
				}		
				// repayType 还款方式
				if(Integer.parseInt(deadline)==1){
					loan.setRepayType("一次性到期还本付息");
				}else{
					loan.setRepayType("按月付息到期还本");
				}	
				//目前打包多人多车的新增和续贷项目全部挂到qsd001（覃世东）账户；		         
				/*if(vehicleRemark!=null && "D".equals(vehicleRemark)){					
					if (company != null && company == "0") {
						loan.setBorrowMoneyUserID("mMZbEzYFzIrehguk");
					} else {
						loan.setBorrowMoneyUserID("ceh1234");
					}	
				}else{
				 loan.setBorrowMoneyUserID("EBFZvmiaMrAfjswz");	
				}	*/
				loan.setBorrowMoneyUserID(borrowerId);	
				loan.setLoanType("车贷");
				// totalMoney 借款总金额
				Double totalMoneyD = money;
				loan.setCompanyno("1"); // 多车
				loan.setTotalmoney(totalMoneyD);
				// minInvestMoney 投资起点金额
				loan.setInvestOriginMoney(1.0);// 投资起点金额
				loan.setIncreaseMoney(1.0);// 单位递增金额
				loan.setMaxInvestMoney(0.0);// 投资上限金额
				loan.setAutoInvestMoneyTotal(money / 2);// 自动投标上限金额
				loan.setLoanGuranteeFee(0.0);// 借款管理费
				loan.setInterestRule("投资次日计息");// 计息规则
				// 是否机构专享
				// vehicleLoanPic.setDeposit(0);//是否为预告项目新创建的项目状态是筹款中
				loan.setStatus(LoanConstants.LoanStatus.RAISING);
				loan.setExpectTime(DateUtil.addMonth(new Date(), 2));// 前台需要的默认值
				loan.setTextItem("是");// 是否为测试项目
				loan.setWhetherInvested(0);//是否可投0不可投，1为可投
				/*loan.setNewbieEnjoy("否");*/// 是否新手项目
				loan.setSourceRemark("system");//
				// maxInvestMoney 投资金额上限
				// rate 借款利率
				/*
				 * 5天的新手标 年化是14% 10天的定期标 年化是8% 1-2个月的定期标 年化9% 3-4个月的定期标 年化10%
				 * 5-6个月的定期标 年化11% 7及以上的定期标 年化12%
				 */
				// guranteeFee 借款管理费
				double rate = 0.0;
				// deadline 借款期限
				int deadlineI = Integer.parseInt(deadline);
				loan.setDeadline(deadlineI);
				loan.setBeforeRepay("否");
				loan.setDay(0);
				if (deadlineI >= 3 && deadlineI <= 4) {
					rate = 10.0;
				} else if (deadlineI >= 5 && deadlineI <= 6) {
					rate = 11.0;
				} else if (deadlineI >= 7) {
					rate = 12.0;
				} else {
					rate = 9.0;
				}
				rate = ArithUtil.round(rate / 100, 4);
				if(newSubject!=null && "isnewSubject".equals(newSubject)&&"定期".equals(type)){
					loan.setRate(0.14);
				}else{
					loan.setRate(rate);
				}
				loan.setCommitTime(new Date());
				if ("是".equals(loan.getOrganizationExclusive())) {					
					loan.setStatus(LoanConstants.LoanStatus.REPAYING);
				}			
				// 是新手标就不能是机构标
				loan.setItemAddress("");
				loan.setBorrowerName("");
				loan.setRemark("");
				loan.setYaCarAndGPS("");
				//Date d = new Date();
				loan.setVerifyUser(getLoginUser.getUserId());
				loan.setVerified(LoanConstants.LoanVerifyStatus.PASSED);
				loan.setType(LoanConstants.LoanType.ENTERPRISE_LOAN);
				loan.setDeposit(0D);
				loan.setSpecialType("");
				loan.setSourceRemark("system");
				loan.setContractId("");
				loan.setAccountingDepartment(Integer.parseInt(company));
				loan.setExpectTime(loan.getCommitTime());
				loan.setCreateTime(loan.getCommitTime());
				if (StringUtils.isBlank(loan.getId())) {
					// 如果项目id为空则保存改项目
					loan.setId(loanId);
					// loan.setId("0" + loan.getI`d().substring(1));
					//Date date = null;
					if ("是".equals(loan.getOrganizationExclusive())) {
						/*SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");*/
						Date giveMoneyTime = loan.getCommitTime();
						loan.setGiveMoneyTime(DateUtil.addDay(giveMoneyTime, 1));
						loan.setGiveMoneyOperationTime(giveMoneyTime);
						loan.setRepayTime(DateUtil.addMonth(new Date(),
								deadlineI));
					}
					
					// 获取项目名称的最大值
					nameNum++;
					String name =com.duanrong.newadmin.utility.IdUtil.getVihicheSortNum(nameNum);
					loan.setName(name);
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
				}
				List<Vehicle> vehicleList = new ArrayList<>();
				for (int j = 0; j < ids.length; j++) {										
						params.put("parentId", ids[j]);
						params.put("id", forkIds[j]);
						TransferStation transferStation = readTransferStByforkId(params);
						// 循环把车辆信息存入list里
						Vehicle ve = new Vehicle();
						ve.setLoanId(loanId);
						ve.setBorrowerIdCard(transferStation.getVehicleInfo().getBorrowerIdCard());//借款人身份证
						ve.setBorrowerName(transferStation.getVehicleInfo().getBorrowerName());//借款人
						ve.setGuaranteeRate(transferStation.getVehicleInfo().getGuaranteeRate());//质押率
						if(transferStation.getVehicleInfo().getGuaranteeType()!=null && "A".equals(transferStation.getVehicleInfo().getGuaranteeType())){
            				ve.setGuaranteeType("质押");//质押
            		    }else{
            		    	ve.setGuaranteeType("抵押");//质押
            			}						
						ve.setMortgagee(transferStation.getVehicleInfo().getMortgagee());//抵押权人
						ve.setOverdueHandling(transferStation.getVehicleInfo().getOverdueHandling());//逾期处理方式
						ve.setSecondHandPrice(transferStation.getVehicleInfo().getSecondHandPrice());//二手市场价格
						ve.setSupervisionMode(transferStation.getVehicleInfo().getSupervisionMode());//监管方式
                        ve.setReminderInfo(transferStation.getVehicleInfo().getReminderInfo());//风险提示
                        ve.setMeasuresInfo(transferStation.getVehicleInfo().getMeasuresInfo());//风控措施
                        ve.setOverdueInfo(transferStation.getVehicleInfo().getOverdueInfo());//逾期处理
                        ve.setOtherDescription(transferStation.getVehicleInfo().getOtherDescription());//其他说明
                        ve.setItemAddress(transferStation.getVehicleInfo().getItemAddress());//项目地点                       
                        if(transferStation.getVehicleInfo().getRemark()!=null && 
                        		("D".equals(transferStation.getVehicleInfo().getRemark())||"B".equals(transferStation.getVehicleInfo().getRemark()))){
            				ve.setRemark("展期");//新增/展期
            	         }else{
            	        	 ve.setRemark("新增");
            	         }	            			
                        ve.setYaCarAndGPS(transferStation.getVehicleInfo().getYaCarAndGPS());//押车/GPS
                        ve.setOtherLoanInfo(transferStation.getVehicleInfo().getOtherLoanInfo());//项目说明
						ve.setAssessPrice(transferStation.getVehicleInfo().getAssessPrice()); // 评估价格
						ve.setBrand(transferStation.getVehicleInfo().getBrand());// 品牌和型号
						ve.setKilometreAmount(transferStation.getVehicleInfo().getKilometreAmount().replace(".000000", "公里"));// 行驶公里数
						ve.setLicensePlateNumber(transferStation.getVehicleInfo().getLicensePlateNumber());// 车牌号
						ve.setMoney(transferStation.getForkMoney());// 子标金额,得必须是子标里面的，而不是查询车辆信息里的车的金额
						ve.setBuyTime(transferStation.getVehicleInfo().getBuyTime());// 车辆购买时间 ,可以不传
						ve.setOtherInfo(transferStation.getVehicleInfo().getOtherInfo());// 其他说明没有可以不传
						// 以下字段是新增字段，没有可以不传
						ve.setVehicleType(transferStation.getVehicleInfo().getVehicleType());// 车辆类型
						ve.setUsingProperties(transferStation.getVehicleInfo().getUsingProperties());// 使用性质
						ve.setManufactureDate(transferStation.getVehicleInfo().getManufactureDate());// 出厂日期
						ve.setRegistrationDate(transferStation.getVehicleInfo().getRegistrationDate());// 登记日期
						ve.setIdentificationNumber(transferStation.getVehicleInfo().getIdentificationNumber());// 车辆识别代号
						ve.setEngineNo(transferStation.getVehicleInfo().getEngineNo());// 发动机号
						ve.setTransmission(transferStation.getVehicleInfo().getTransmission());// 变速器
						ve.setDisplacement(transferStation.getVehicleInfo().getDisplacement());// 排量
						ve.setConditionAssessment(transferStation.getVehicleInfo().getConditionAssessment());// 车况评估
						ve.setFuel(transferStation.getVehicleInfo().getFuel());// 燃油
						ve.setTrafficInsuranceValidity(transferStation.getVehicleInfo().getTrafficInsuranceValidity());// 交强险有效期
						ve.setInspectionValidity(transferStation.getVehicleInfo().getInspectionValidity());// 年检有效期
						ve.setLllegalDeduction(transferStation.getVehicleInfo().getLllegalDeduction());// 预计违章扣分
						ve.setViolationFines(transferStation.getVehicleInfo().getViolationFines());// 预计违章罚金
						ve.setBorrowingPurposes(transferStation.getVehicleInfo().getBorrowingPurposes());
						vehicleList.add(ve);
						// 把图片信息存入
						BannerPicture bp = new BannerPicture();
						bp.setId(IdGenerator.randomUUID());
						bp.setTitle("车辆图片"+(j+1));
						bp.setPicture(transferStation.getUrl());
						bp.setSeqNum(j+1);
						loanService.insertBannerPicture(bp, loanId);					
				}
				loanDetailService.insertVehicle((List<Vehicle>) vehicleList,
						loan);
				isTrue = true;
			}
			if (isTrue) {
				//如果添加信息成功，就把子标的信息打包
				Map<String, Object> forkInterMid = new HashMap<>();
				forkInterMid.put("packing", 1);
				forkInterMid.put("loanId", loanId);
				forkInterMid.put("remark", type);
				forkInterMid.put("arr", forkIds);
				transferStationDao.updateForkIntermediaries(forkInterMid);
				return "success";
			}
	    }
		} catch (Exception e) {
			return "fail";
		}
		return "fail";
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String allocationVehicles(String[] ids, UserCookie getLoginUser,
			String type, String institutionName, String deadline, Double money,
			String company, String newSubject, String borrowerId) {
		boolean isTrue = false;
		Map<String, Object> params = new HashMap<String, Object>();
		try {
		String loanId = IdGenerator.generateLoanId();
		synchronized (lockloan) {
			int nameNum=this.readLoanNumber();
			//如果只选择一条单人单车的
			if (ids.length == 1) {
				String id=ids[0];
				params.put("id",id);
				TransferStation transferStation = readTransferStationById(params);
				transferStation.setMoney(money);
				transferStation.setDeadline(Integer.parseInt(deadline));
				transferStation.setOperationType("月");
				transferStation.setRepayType("先息后本");
				transferStation.setAccountingDepartment(Integer.parseInt(company));
				transferStation.setBorrowerId(borrowerId);
				if (transferStation != null) {		
					if(money>200000.0){
						double totalMoney=money;
						double chushu =200000.0;
						double n= Math.ceil(totalMoney/chushu);
						int i=(int)n;
						for (int j = 1; j <= i; j++) {
							String loanIds = loanId.substring(0,16)+"0"+j;	
							nameNum++;
							isTrue =this.verifyLoanAttrs(transferStation,
									getLoginUser, type,loanIds,newSubject,i,j,nameNum);		
						}
					}else{
						nameNum++;
						isTrue = this.verifyLoanAttrs(transferStation,
								getLoginUser, type,loanId,newSubject,0,0,nameNum);
					}					
				}
			} else if (ids.length > 1) {
				Loan loan = new Loan();
				// operationType 计算方式
				loan.setOperationType("月");
				loan.setInstructionNo("");
				// 是否机构专享
				if ("定期".equals(type)) {
					loan.setOrganizationExclusive("否");
				} else if ("机构线上".equals(type)) {
					loan.setOrganizationExclusive("是");
				}
				// 新手专享
				if(newSubject!=null && "isnewSubject".equals(newSubject)&&"定期".equals(type)){
					loan.setNewbieEnjoy("是");
				}else{
					loan.setNewbieEnjoy("否");
				}	
				// repayType 还款方式
				if(Integer.parseInt(deadline)==1){
					loan.setRepayType("一次性到期还本付息");
				}else{
					loan.setRepayType("按月付息到期还本");
				}
				//目前打包多人多车的新增和续贷项目全部挂到qsd001（覃世东）账户；
				/*if(vehicleRemark!=null && "D".equals(vehicleRemark)){					
						if (company != null && company == "0") {
							loan.setBorrowMoneyUserID("mMZbEzYFzIrehguk");
						} else {
							loan.setBorrowMoneyUserID("ceh1234");
						}	
				}else{
				 loan.setBorrowMoneyUserID("EBFZvmiaMrAfjswz");	
				}	*/	
				loan.setBorrowMoneyUserID(borrowerId);	
				loan.setLoanType("车贷");
				// totalMoney 借款总金额
				Double totalMoneyD = money;
				loan.setCompanyno("1"); // 多车
				loan.setTotalmoney(totalMoneyD);
				// minInvestMoney 投资起点金额
				loan.setInvestOriginMoney(1.0);// 投资起点金额
				loan.setIncreaseMoney(1.0);// 单位递增金额
				loan.setMaxInvestMoney(0.0);// 投资上限金额
				loan.setAutoInvestMoneyTotal(money / 2);// 自动投标上限金额
				loan.setLoanGuranteeFee(0.0);// 借款管理费
				loan.setInterestRule("投资次日计息");// 计息规则
				// 是否机构专享
				// vehicleLoanPic.setDeposit(0);//是否为预告项目新创建的项目状态是筹款中
				loan.setStatus(LoanConstants.LoanStatus.RAISING);
				loan.setExpectTime(DateUtil.addMonth(new Date(), 2));// 前台需要的默认值
				loan.setTextItem("是");// 是否为测试项目
				loan.setWhetherInvested(0);//是否可投0不可投，1为可投
				// 是否新手项目
				loan.setSourceRemark("system");//
				// maxInvestMoney 投资金额上限
				// rate 借款利率
				/*
				 * 5天的新手标 年化是14% 10天的定期标 年化是8% 1-2个月的定期标 年化9% 3-4个月的定期标 年化10%
				 * 5-6个月的定期标 年化11% 7及以上的定期标 年化12%
				 */
				// guranteeFee 借款管理费
				double rate = 0.0;
				// deadline 借款期限
				int deadlineI = Integer.parseInt(deadline);
				loan.setDeadline(deadlineI);
				loan.setBeforeRepay("否");
				loan.setDay(0);
				if (deadlineI >= 3 && deadlineI <= 4) {
					rate = 10.0;
				} else if (deadlineI >= 5 && deadlineI <= 6) {
					rate = 11.0;
				} else if (deadlineI >= 7) {
					rate = 12.0;
				} else {
					rate = 9.0;
				}
				rate = ArithUtil.round(rate / 100, 4);
				if(newSubject!=null && "isnewSubject".equals(newSubject)&&"定期".equals(type)){
					loan.setRate(0.14);
				}else{
					loan.setRate(rate);
				}	
				loan.setCommitTime(new Date());
				if ("是".equals(loan.getOrganizationExclusive())) {					
					loan.setStatus(LoanConstants.LoanStatus.REPAYING);
				}
				
				// 是新手标就不能是机构标

				loan.setItemAddress("");
				loan.setBorrowerName("");
				loan.setRemark("");
				loan.setYaCarAndGPS("");
				//Date d = new Date();
				loan.setVerifyUser(getLoginUser.getUserId());
				loan.setVerified(LoanConstants.LoanVerifyStatus.PASSED);
				loan.setType(LoanConstants.LoanType.ENTERPRISE_LOAN);
				loan.setDeposit(0D);
				loan.setSpecialType("");
				loan.setSourceRemark("system");
				loan.setContractId("");
				loan.setAccountingDepartment(Integer.parseInt(company));
				loan.setExpectTime(loan.getCommitTime());
				loan.setCreateTime(loan.getCommitTime());
				if (StringUtils.isBlank(loan.getId())) {
					// 如果项目id为空则保存改项目
					loan.setId(loanId);
					// loan.setId("0" + loan.getId().substring(1));
					//Date date = null;
					if ("是".equals(loan.getOrganizationExclusive())) {
						/*SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");*/
						Date giveMoneyTime = loan.getCommitTime();
						loan.setGiveMoneyTime(DateUtil.addDay(giveMoneyTime, 1));
						loan.setGiveMoneyOperationTime(giveMoneyTime);
						loan.setRepayTime(DateUtil.addMonth(new Date(),
								deadlineI));
					}					
					
					// 获取项目名称的最大值
					nameNum++;
					String name =com.duanrong.newadmin.utility.IdUtil.getVihicheSortNum(nameNum);
					loan.setName(name);
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
				}
				List<Vehicle> vehicleList = new ArrayList<>();
				for (int j = 0; j < ids.length; j++) {										
						params.put("id", ids[j]);
						TransferStation transferStation = readTransferStationById(params);
						// 循环把车辆信息存入list里
						Vehicle ve = new Vehicle();
						ve.setLoanId(loanId);
						ve.setBorrowerIdCard(transferStation.getVehicleInfo().getBorrowerIdCard());//借款人身份证
						ve.setBorrowerName(transferStation.getVehicleInfo().getBorrowerName());//借款人
						ve.setGuaranteeRate(transferStation.getVehicleInfo().getGuaranteeRate());//质押率
						if(transferStation.getVehicleInfo().getGuaranteeType()!=null && "A".equals(transferStation.getVehicleInfo().getGuaranteeType())){
							ve.setGuaranteeType("质押");//质押
						 }else{
							 ve.setGuaranteeType("抵押"); 			   
						}						
						ve.setMortgagee(transferStation.getVehicleInfo().getMortgagee());//抵押权人
						ve.setOverdueHandling(transferStation.getVehicleInfo().getOverdueHandling());//逾期处理方式
						ve.setSecondHandPrice(transferStation.getVehicleInfo().getSecondHandPrice());//二手市场价格
						ve.setSupervisionMode(transferStation.getVehicleInfo().getSupervisionMode());//监管方式
                        ve.setReminderInfo(transferStation.getVehicleInfo().getReminderInfo());//风险提示
                        ve.setMeasuresInfo(transferStation.getVehicleInfo().getMeasuresInfo());//风控措施
                        ve.setOverdueInfo(transferStation.getVehicleInfo().getOverdueInfo());//逾期处理
                        ve.setOtherDescription(transferStation.getVehicleInfo().getOtherDescription());//其他说明
                        ve.setItemAddress(transferStation.getVehicleInfo().getItemAddress());//项目地点
                        if(transferStation.getVehicleInfo().getRemark()!=null && 
                        		("D".equals(transferStation.getVehicleInfo().getRemark())||"B".equals(transferStation.getVehicleInfo().getRemark()))){
            				ve.setRemark("展期");//新增/展期
            	         }else{
            	        	 ve.setRemark("新增");
            	         }	       
                        ve.setYaCarAndGPS(transferStation.getVehicleInfo().getYaCarAndGPS());//押车/GPS
                        ve.setOtherLoanInfo(transferStation.getVehicleInfo().getOtherLoanInfo());//项目说明
						ve.setAssessPrice(transferStation.getVehicleInfo().getAssessPrice()); // 评估价格
						ve.setBrand(transferStation.getVehicleInfo().getBrand());// 品牌和型号
						ve.setKilometreAmount(transferStation.getVehicleInfo().getKilometreAmount().replace(".000000", "公里"));// 行驶公里数
						ve.setLicensePlateNumber(transferStation.getVehicleInfo().getLicensePlateNumber());// 车牌号
						ve.setMoney(transferStation.getMoney());// 子标金额,是中转站里面的主标金额
						ve.setBuyTime(transferStation.getVehicleInfo().getBuyTime());// 车辆购买时间 ,可以不传
						ve.setOtherInfo(transferStation.getVehicleInfo().getOtherInfo());// 其他说明没有可以不传
						// 以下字段是新增字段，没有可以不传
						ve.setVehicleType(transferStation.getVehicleInfo().getVehicleType());// 车辆类型
						ve.setUsingProperties(transferStation.getVehicleInfo().getUsingProperties());// 使用性质
						ve.setManufactureDate(transferStation.getVehicleInfo().getManufactureDate());// 出厂日期
						ve.setRegistrationDate(transferStation.getVehicleInfo().getRegistrationDate());// 登记日期
						ve.setIdentificationNumber(transferStation.getVehicleInfo().getIdentificationNumber());// 车辆识别代号
						ve.setEngineNo(transferStation.getVehicleInfo().getEngineNo());// 发动机号
						ve.setTransmission(transferStation.getVehicleInfo().getTransmission());// 变速器
						ve.setDisplacement(transferStation.getVehicleInfo().getDisplacement());// 排量
						ve.setConditionAssessment(transferStation.getVehicleInfo().getConditionAssessment());// 车况评估
						ve.setFuel(transferStation.getVehicleInfo().getFuel());// 燃油
						ve.setTrafficInsuranceValidity(transferStation.getVehicleInfo().getTrafficInsuranceValidity());// 交强险有效期
						ve.setInspectionValidity(transferStation.getVehicleInfo().getInspectionValidity());// 年检有效期
						ve.setLllegalDeduction(transferStation.getVehicleInfo().getLllegalDeduction());// 预计违章扣分
						ve.setViolationFines(transferStation.getVehicleInfo().getViolationFines());// 预计违章罚金
						ve.setBorrowingPurposes(transferStation.getVehicleInfo().getBorrowingPurposes());
						vehicleList.add(ve);
						// 把图片信息存入
						BannerPicture bp = new BannerPicture();
						bp.setId(IdGenerator.randomUUID());
						bp.setTitle("车辆图片"+(j+1));
						bp.setPicture(transferStation.getUrl());
						bp.setSeqNum(j+1);
						loanService.insertBannerPicture(bp, loanId);					
				}
				loanDetailService.insertVehicle((List<Vehicle>) vehicleList,
						loan);
				isTrue = true;
			}
			if (isTrue) {
				//如果添加信息成功，就把中转站的项目状态更改了
				Map<String, Object> mediariesParam = new HashMap<>();
				mediariesParam.put("allocationStatus", 1);
				mediariesParam.put("channelName", type);
				mediariesParam.put("unpackLoanId", loanId);
				mediariesParam.put("institutionName", institutionName);
				mediariesParam.put("arr", ids);
				transferStationDao 
						.updateBatchTransferStation(mediariesParam);
				return "success";
			}
		}
		} catch (Exception e) {
			return "fail";
		}
		return "fail";
	}

	@Override
	public List<TransferStation> readTransferStationinfo(
			Map<String, Object> params) {
		
		return transferStationDao.readTransferStationinfo(params);
	}

	@Override
	public List<TransferStationForkLoans> readLoanForkIntermediariesIdByLoadId(String loanId) {
		return transferStationDao.readLoanForkIntermediariesIdByLoadId(loanId);
	}

	@Override
	public int updateBatchForkIntermediaries(List<TransferStationForkLoans> list) {
		return transferStationDao.updateBatchForkIntermediaries(list);
	}

	@Override
	public int updateBatchIntermediaries(List<TransferStation> list) {
		return transferStationDao.updateBatchIntermediaries(list);
	}

	@Override
	public List<TransferStation> readLoanTransferIntermediariesIdByLoadId(
			String loanId) {
		
		return transferStationDao.readLoanTransferIntermediariesIdByLoadId(loanId);
	}

	@Override
	public int readLoanNumber() {
		String loanName = "00000";
		 if (loanDao.getLoanName() != null
				&& !"".equals(loanDao.getLoanName())) {
			 loanName=loanDao.getLoanName().substring(9,13);
		 }
		 int loanNum=Integer.parseInt(loanName);
		 return loanNum;
	}

	@Override
	public TransferStation readTransferStationById(Map<String, Object> params) {		
		return transferStationDao.findTransferStationById(params);
	}

	@Override
	public TransferStation readTransferStByforkId(Map<String, Object> params) {
		return transferStationDao.findTransferStByforkId(params);
	}
}
