package com.duanrong.business.demand.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;

import util.Log;
import base.pagehelper.PageInfo;
import base.schedule.constants.ScheduleConstants;
import base.schedule.job.CheckDemandOverExpectTime;

import com.duanrong.business.demand.dao.AvailableMoneyRecordDao;
import com.duanrong.business.demand.dao.DemandTreasureLoanDao;
import com.duanrong.business.demand.dao.DemandtreasureDao;
import com.duanrong.business.demand.model.AvailableMoneyRecord;
import com.duanrong.business.demand.model.DemandforkLoan;
import com.duanrong.business.demand.model.Demandtreasure;
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.business.demand.service.AvailableMoneyRecordService;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.IdUtil;

@Service
public class AvailableMoneyRecordServiceImpl implements
		AvailableMoneyRecordService {

	@Resource
	AvailableMoneyRecordDao availableMoneyRecordDao;

	@Resource
	DemandtreasureDao demandtreasureDao;
	@Resource
	DemandTreasureLoanDao demandTreasureLoanDao;
	@Resource
	StdScheduler scheduler;

	@Resource
	Log log;

	@Override
	public PageInfo<AvailableMoneyRecord> readPageInfo(int pageNo, int pageSize) {
		return availableMoneyRecordDao.readPageInfo(pageNo, pageSize);
	}

	@Override
	public void insert(AvailableMoneyRecord availableMoneyRecord) {
		availableMoneyRecordDao.insert(availableMoneyRecord);
	}

	@Override
	public void del(AvailableMoneyRecord availableMoneyRecord) {
		availableMoneyRecordDao.del(availableMoneyRecord);
	}

	@Override
	public AvailableMoneyRecord readRecord() {
		return availableMoneyRecordDao.readRecord();
	}

	@Override
	public void update(AvailableMoneyRecord availableMoneyRecord) {
		availableMoneyRecordDao.update(availableMoneyRecord);
	}

	@Override
	public boolean saveRecord(AvailableMoneyRecord availableMoneyRecord,
			HttpServletRequest request) {
		// 如果预计执行时间大于当前时间，添加任务调度
		Date date = availableMoneyRecord.getExpectTime();
		String attornMoney = request.getParameter("attornMoney");// 转让总金额
		String LoandataIds = request.getParameter("LoandataIds");// 新资产选中的id号
		String TransferIds = request.getParameter("TransferIds");// 赎回资产选中的id号
		if (LoandataIds != null && LoandataIds != "") {
			String[] arr = LoandataIds.split(",");
			Map<String, Object> params = new HashMap<>();
			params.put("arr", arr);
			DemandforkLoan  demandforkLoan= demandTreasureLoanDao.readforkCounts(params);
			if(demandforkLoan!=null){
			/*System.out.println(demandforkLoan.getForkId()+":"+demandforkLoan.getForkIdCount());*/
			if(demandforkLoan.getForkIdCount()>1){
				//如果查出的fork_id的数量大于1说明是有重复的，就返回错误
			  return false;
			}
			}
		}
		if (date.getTime() > new Date().getTime()) {
			// 类型等于续投，开放资产逻辑。。
			if (availableMoneyRecord.getType().equals("expired")
					|| availableMoneyRecord.getType().equals("add")) {
				try {
					double attornMoney1 = 0;
					if (attornMoney != null && !attornMoney.equals("")) {
						attornMoney1 = Double.parseDouble(attornMoney);
					}
					// 如果选中转让资产进行更新金额操作
					if (attornMoney1 > 0 && TransferIds != null
							&& TransferIds != "") {
						/*
						 * List<DemandtreasureLoan> list = demandTreasureLoanDao.readDemandtreasureLoanIds();
						 */
						String[] arr = TransferIds.split(",");
						for (String id : arr) {
							DemandtreasureLoan demandloan = demandTreasureLoanDao.get(id);
							DemandtreasureLoan dloan = demandTreasureLoanDao.readDemandTreasureLoan();
							Map<String, Object> params = new HashMap<>();
							double newMoney = demandloan.getValidMoney()+ demandloan.getOpenAmount();
							params.put("id", demandloan.getId());
							params.put("validMoney", newMoney);
							params.put("openAmount", "0");
							params.put("availableId",availableMoneyRecord.getId());
							params.put("originalRedemptionMoney",demandloan.getOpenAmount());
							if (demandloan.getLoanName() == null
									|| demandloan.getLoanName().equals("")
									|| demandloan.getLoanName().contains("车贷")) {
								// 增长逻辑
								if (dloan == null) {
									String loanName = IdUtil.getSortNum("000000");
									params.put("loanName", loanName);
								} else {
									String name = dloan.getLoanName().substring(8);
									String loanName = IdUtil.getSortNum(name);
									params.put("loanName", loanName);
								}
							}
							demandTreasureLoanDao.updateAssignmentStatusById(params);
						}
					}
					// 如果选中新的资产进行更新金额和状态操作
					if (LoandataIds != null && LoandataIds != "") {
						String[] arr = LoandataIds.split(",");
						for (int i = 0; i < arr.length; i++) {
							// 增加对车贷子标的的判断，如果parentId不为空，说明父标里的所有子标都要开放出去
							List<DemandtreasureLoan> readDemadfork = demandTreasureLoanDao.readDemadfork(arr[i]);
							Map<String, Object> params = new HashMap<>();
							if (readDemadfork != null && readDemadfork.size() > 0) {
								for (DemandtreasureLoan demandtreasure : readDemadfork) {
									DemandtreasureLoan demandloan = demandTreasureLoanDao.get(demandtreasure.getId());
									params.put("startTime", date);
									if (demandloan.getOperationType().equals("月")) {
										int month = demandloan.getMonth();
										params.put("finishTime",DateUtil.addMonth(date, month));
									} else {
										int days = demandloan.getDay();
										params.put("finishTime",DateUtil.addDay(date, days));
									}
									params.put("validMoney",demandloan.getTotalMoney());
									params.put("openStatus", "opened");
									params.put("openAmount", "0");
									params.put("availableId",availableMoneyRecord.getId());
									params.put("id", demandtreasure.getId());
									// 增长逻辑
									DemandtreasureLoan dloan = demandTreasureLoanDao
											.readDemandTreasureLoan();
									if (demandloan.getLoanName() == null
											|| demandloan.getLoanName().equals("")
											|| demandloan.getLoanName().contains(
													"车贷")) {
										if (dloan == null) {
											String loanName = IdUtil.getSortNum("000000");
											params.put("loanName", loanName);
										} else {
											String name = dloan.getLoanName().substring(8);
											String loanName = IdUtil.getSortNum(name);
											params.put("loanName", loanName);
										}
									}
									//把每个子标开放出去，并把子标的状态改为显示
									demandTreasureLoanDao.updateAssignmentStatusById(params);
									Map<String, Object> forkparams = new HashMap<>();
									forkparams.put("display", 1);
									forkparams.put("id", demandtreasure.getId());
									demandTreasureLoanDao.updateDemandDisplay(forkparams);									
								}
								//把主标的状态改为隐藏
								DemandtreasureLoan parentDemand = demandTreasureLoanDao.get(arr[i]);
								Map<String, Object> parentparams = new HashMap<>();
								parentparams.put("display", 0);
								parentparams.put("startTime", date);
								if (parentDemand.getOperationType().equals("月")) {
									int month = parentDemand.getMonth();
									parentparams.put("finishTime",DateUtil.addMonth(date, month));
								}
								parentparams.put("id",arr[i] );
								
								demandTreasureLoanDao.updateDemandDisplay(parentparams);
							} else {
								DemandtreasureLoan demandloan = demandTreasureLoanDao.get(arr[i]);
								params.put("startTime", date);
								if (demandloan.getOperationType().equals("月")) {
									int month = demandloan.getMonth();
									params.put("finishTime",DateUtil.addMonth(date, month));
								} else {
									int days = demandloan.getDay();
									params.put("finishTime",DateUtil.addDay(date, days));
								}
								params.put("validMoney",demandloan.getTotalMoney());
								params.put("openStatus", "opened");
								params.put("openAmount", "0");
								params.put("availableId",availableMoneyRecord.getId());
								params.put("id", arr[i]);
								// 增长逻辑
								DemandtreasureLoan dloan = demandTreasureLoanDao
										.readDemandTreasureLoan();
								if (demandloan.getLoanName() == null
										|| demandloan.getLoanName().equals("")
										|| demandloan.getLoanName().contains(
												"车贷")) {
									if (dloan == null) {
										String loanName = IdUtil.getSortNum("000000");
										params.put("loanName", loanName);
									} else {
										String name = dloan.getLoanName().substring(8);
										String loanName = IdUtil.getSortNum(name);
										params.put("loanName", loanName);
									}

								}
								demandTreasureLoanDao.updateAssignmentStatusById(params);
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					log.errLog("添加活期宝产品", e);
					return false;
				}
			}
			try {
				SimpleTrigger trigger = (SimpleTrigger) scheduler
						.getTrigger(TriggerKey.triggerKey(
								availableMoneyRecord.getId(),
								ScheduleConstants.TriggerGroup.CHECK_DEMAND_OVER_EXPECT_TIME));
				if (trigger != null) {
					Trigger newTrigger = trigger
							.getTriggerBuilder()
							.withSchedule(
									SimpleScheduleBuilder.simpleSchedule())
							.startAt(availableMoneyRecord.getExpectTime())
							.build();
					scheduler.rescheduleJob(trigger.getKey(), newTrigger);
				} else {
					JobDetail jobDetail = JobBuilder
							.newJob(CheckDemandOverExpectTime.class)
							.withIdentity(
									availableMoneyRecord.getId(),
									ScheduleConstants.JobGroup.CHECK_DEMAND_OVER_EXPECT_TIME)
							.build();// 任务名，任务组，任务执行类
					jobDetail.getJobDataMap().put("demandID",
							availableMoneyRecord.getId());
					jobDetail.getJobDataMap().put("demandType",
							availableMoneyRecord.getType());
					jobDetail.getJobDataMap().put("attornMoney", attornMoney);
					jobDetail.getJobDataMap().put("LoandataIds", LoandataIds);

					trigger = TriggerBuilder
							.newTrigger()
							.forJob(jobDetail)
							.startAt(availableMoneyRecord.getExpectTime())
							.withSchedule(
									SimpleScheduleBuilder.simpleSchedule())
							.withIdentity(
									availableMoneyRecord.getId(),
									ScheduleConstants.TriggerGroup.CHECK_DEMAND_OVER_EXPECT_TIME)
							.build();
					scheduler.scheduleJob(jobDetail, trigger);
				}
			} catch (Exception e) {
				log.errLog("添加活期宝产品", e);
				return false;
			}
		} else {
			availableMoneyRecord.setStatus(1);
			try {
				List<Demandtreasure> list = demandtreasureDao.findAll();
				if (list.size() > 0) {
					Demandtreasure demand = list.get(0);
					demand.setUpdateTime(new Date());
					demand.setAvailableMoney(demand.getAvailableMoney()
							+ availableMoneyRecord.getMoney());
					demand.setSumMoney(demand.getSumMoney()
							+ availableMoneyRecord.getMoney());
					System.out.println(demand);
					demandtreasureDao.update(demand);
				}
			} catch (Exception e) {
				log.errLog("添加活期宝产品", e);
				return false;
			}
		}
		return true;
	}

	@Override
	public AvailableMoneyRecord read(String id) {

		return availableMoneyRecordDao.get(id);
	}

	@Override
	public double readUserUsedMoney() {
		// TODO Auto-generated method stub
		return availableMoneyRecordDao.readUserUsedMoney();
	}

	@Override
	public void updateDemandTreasure(double money) {
		availableMoneyRecordDao.updateDemandTreasure(money);

	}

	@Override
	public double readAvailableMoneyRecord(String date) {
		// TODO Auto-generated method stub
		return availableMoneyRecordDao.readAvailableMoneyRecord(date);
	}
}