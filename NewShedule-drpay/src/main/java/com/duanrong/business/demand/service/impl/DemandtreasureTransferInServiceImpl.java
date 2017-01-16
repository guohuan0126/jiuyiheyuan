package com.duanrong.business.demand.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import base.exception.ExceedMaxInvestMoney;
import base.exception.ExceedMoneyNeedRaised;
import base.exception.InsufficientBalance;
import base.exception.InsufficientFreezeAmountException;
import base.exception.InvestMoneyException;
import base.exception.NoOpenAccountException;
import base.exception.ObjectIsNullException;
import base.exception.OutOfDateException;
import base.exception.ParameterException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.demand.DemandtreasureConstants;
import com.duanrong.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.business.demand.model.Demandtreasure;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;
import com.duanrong.business.demand.service.AvailableMoneyRecordService;
import com.duanrong.business.demand.service.DemandTreasureBillService;
import com.duanrong.business.demand.service.DemandtreasureService;
import com.duanrong.business.demand.service.DemandtreasureTransferInService;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;



@Service
public class DemandtreasureTransferInServiceImpl implements
		DemandtreasureTransferInService {
	final Lock lock = new ReentrantLock();

	@Resource
	DemandtreasureTransferInDao demandtreasureTransferInDao;

	@Resource
	DemandtreasureService demandtreasureService;
	
	@Resource
	UserAccountService userAccountService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	@Resource
	DemandTreasureBillService demandTreasureBillService;

	@Resource
	AvailableMoneyRecordService availableMoneyRecordService;

	@Resource
	Log log;

	@Override
	public List<DemandtreasureTransferIn> findAll(
			DemandtreasureTransferIn demandtreasureTransferIn) {
		return demandtreasureTransferInDao.find(demandtreasureTransferIn);
	}

	@Override
	public String transferInConfirm(DemandtreasureTransferIn demandIn,
			String flag) throws InsufficientBalance, IOException {
		return "";
		/*// 拼接请求字符串
		lock.lock();
		try {
			GeneratorXML xml = new GeneratorXML();
			String user = LoadConstantProterties2.getValueByDefaultPro("USER");
			xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
			xml.setRequestNo(demandIn.getId());
			xml.setMode(flag);
			xml.setNotifyUrl(TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
					+ TrusteeshipYeepayConstants.OperationType.THANAUTH);
			String content = null;
			try {
				content = XMLUtil.getXML(xml);
			} catch (Exception e) {
				log.errLog("创建转账确认XML拼接异常", e);
				return "fail";
			}

			String sign = CFCASignUtil.sign(content);
			// 包装参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("req", content);
			params.put("sign", sign);

			log.infoLog("转账确认XML", content);
			log.infoLog("转账确认sign", sign);

			TrusteeshipOperation to = new TrusteeshipOperation();
			to.setId(IdGenerator.randomUUID());
			to.setMarkId(demandIn.getId());
			to.setOperator(demandIn.getId());
			to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
			to.setRequestData(MapUtil.mapToString(params));
			to.setRequestTime(new Date());
			to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
			to.setType("InConfirm");
			to.setUserId(demandIn.getUserId());
			to.setTrusteeship("yeepay");
			trusteeshipOperationService.insert(to);
			// 创建直连请求
			HttpClient client = new HttpClient();
			PostMethod postMethod = new PostMethod(
					TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);

			postMethod.setParameter("req", content);
			postMethod.setParameter("sign", sign);
			postMethod.setParameter("service", "COMPLETE_TRANSACTION");
			int statusCode = client.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.errLog(
						"Class DemandtreasureTransferInServiceImpl.transferInConfirm",
						"Method failed: " + postMethod.getStatusLine());
			}
			// 获得返回的结果
			byte[] responseBody = postMethod.getResponseBody();
			log.infoLog(demandIn.getId() + "转账确认", new String(responseBody,
					"UTF-8"));
			@SuppressWarnings("unchecked")
			Map<String, String> respMap = Dom4jUtil.xmltoMap(new String(
					responseBody, "UTF-8"));
			String code = respMap.get("code");
			String description = respMap.get("description");
			to.setResponseData(new String(responseBody, "UTF-8"));
			to.setResponseTime(new Date());
			if ("1".equals(code)) {
				if (GeneralTransferConstants.TransferStatus.CONFIRM
						.equals(flag)) {
					int num = demandTreasureBillService.getCount(demandIn
							.getId());
					if (num > 0) {
						log.errLog("转入重复操作", "转入重复操作");
						return "fail";
					}
					DemandtreasureTransferIn d = new DemandtreasureTransferIn();
					d.setStatus(DemandtreasureConstants.TransferInStatus.CONFIRM);
					d.setId(demandIn.getId());
					d.setConfirmTime(new Date());
					d.setMoney(demandIn.getMoney());
					demandtreasureTransferInDao.update(d);
					DemandTreasureBill bill = new DemandTreasureBill();
					bill.setCreateTime(demandIn.getSendedTime());
					bill.setDetail("用户：" + demandIn.getUserId() + "天天赚转账确认成功!");
					bill.setId(IdGenerator.randomUUID());
					bill.setMoney(demandIn.getMoney());
					bill.setType(DemandtreasureConstants.TransferInStatus.TRANIN);
					bill.setTranferWay(demandIn.getTransferWay());
					bill.setUserId(demandIn.getUserId());
					bill.setBillId(demandIn.getId());
					demandTreasureBillService.insertBill(bill);
					
					userAccountService.tofreeze(demandIn.getUserId(),
							demandIn.getMoney(), BusinessEnum.demand_in,
							"投资天天赚成功", "转账ID：" + demandIn.getId(),
							demandIn.getId());

					userAccountService.transferIn(user, demandIn.getMoney(),
							BusinessEnum.demand_in, "向用户：" + user
									+ "活期宝转账确认成功!转入金额",
							"转账ID：" + demandIn.getId(), demandIn.getId());
				} else {
					DemandtreasureTransferIn d = new DemandtreasureTransferIn();
					d.setStatus(DemandtreasureConstants.TransferInStatus.CANCEL);
					d.setId(demandIn.getId());
					d.setMoney(demandIn.getMoney());
					d.setConfirmTime(new Date());
					demandtreasureTransferInDao.update(d);
					
					List<Demandtreasure> list = demandtreasureService.findAll();
					if (list != null && list.size() > 0) {
						Demandtreasure de = list.get(0);
						Demandtreasure dem = new Demandtreasure();
						dem.setAvailableMoney(ArithUtil.add(
								de.getAvailableMoney(), demandIn.getMoney()));
						dem.setUpdateTime(new Date());
						dem.setId(de.getId());
						demandtreasureService.update(dem);
					}

					userAccountService.unfreeze(demandIn.getUserId(),
							demandIn.getMoney(), BusinessEnum.demand_in,
							"解冻：投资天天赚", "转账ID：" + demandIn.getId(),
							demandIn.getId());
				}
				to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
				trusteeshipOperationService.update(to);
				
				
				DRJedisMQ.product("isFirst_invest", demandIn.getUserId());
				
				return "ok";
			} else {
				to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
				trusteeshipOperationService.update(to);
				return description;
			}
		} catch (NoOpenAccountException e) {
			log.errLog("活期宝转入确认", e);
			e.printStackTrace();
			return null;
		} catch (InsufficientFreezeAmountException e) {
			log.errLog("活期宝转入确认", e);
			e.printStackTrace();
			return null;
		} catch (Exception ex) {
			throw ex;
		} finally {
			lock.unlock();
		}*/
	}

	@Override
	public DemandtreasureTransferIn gettotal() {
		return demandtreasureTransferInDao.gettotal();
	}

	@Override
	public PageInfo<DemandtreasureTransferIn> pageInfo(int pageNo, int pageSize) {
		return demandtreasureTransferInDao.pageInfo(pageNo, pageSize);
	}

	@Override
	public List<DemandtreasureTransferIn> findTran(
			DemandtreasureTransferIn demandtreasureTransferIn) {
		return demandtreasureTransferInDao.findTran(demandtreasureTransferIn);
	}

	@Override
	public DemandtreasureTransferIn get(String id) {
		return demandtreasureTransferInDao.get(id);
	}

	@Override
	public List<DemandtreasureTransferIn> findStatus() {
		return demandtreasureTransferInDao.findStatus();
	}

	@Override
	public List<DemandtreasureTransferIn> findTransferInRecordTop(
			String userId, int top) {
		return demandtreasureTransferInDao.findTransferInRecordTop(userId, top);
	}

	@Override
	public void syncDemandIn(DemandtreasureTransferIn demandIn, String type)
			throws ObjectIsNullException, ParameterException,
			InvestMoneyException, InsufficientBalance, ExceedMoneyNeedRaised,
			ExceedMaxInvestMoney, OutOfDateException {
		lock.lock();
		try {

			if (StringUtils.equals(type, "S2SSuccess")) {
				// 必须保证invest表的投资状态改为冻结
				demandIn.setStatus(DemandtreasureConstants.TransferInStatus.FREEZE);
				demandIn.setFreezeTime(new Date());
				demandtreasureTransferInDao.update(demandIn);
				log.infoLog("活期宝转入成功", "demandIn:" + demandIn.toString());
			} else if (StringUtils.equals(type, "S2SFail")) {
				demandIn.setStatus(DemandtreasureConstants.TransferInStatus.FAIL);
				demandtreasureTransferInDao.update(demandIn);
				// 修改活期宝基本信息表，可投金额+投资金额
				Demandtreasure demandtreasure = demandtreasureService.findAll()
						.get(0);
				Double money = demandIn.getMoney();
				demandtreasure.setAvailableMoney(demandtreasure
						.getAvailableMoney() + money);
				demandtreasure.setUpdateTime(new Date());
				demandtreasureService.update(demandtreasure);
				// 查询活期表每次更新记录最后一条，如果募集完成时间存在，将时间置空
				// AvailableMoneyRecord availableMoneyRecord =
				// availableMoneyRecordService.getEndLine();
				//
				// if(availableMoneyRecord.getEndtime()!=null){
				// availableMoneyRecord.setEndtime(null);
				// availableMoneyRecordService.update(availableMoneyRecord);
				// }
				// 解冻用户本地账户
				userAccountService.unfreeze(demandIn.getUserId(),
						demandIn.getMoney(), BusinessEnum.demand_in,
						"解冻：投资天天赚", "转账ID：" + demandIn.getId(),
						demandIn.getId());
				// userMoneyService.unfreezeMoney(demandIn.getUserId(),
				// demandIn.getMoney(),"解冻：投资天天赚","投资天天赚失败，资金解冻！");
				log.infoLog("活期宝转入失败", "资金解冻,用户ID：" + demandIn.getUserId()
						+ ",demandIn：" + demandIn.toString());
			}

		} catch (NoOpenAccountException e) {
			log.errLog("活期宝转入确认", e);
			e.printStackTrace();

		} catch (InsufficientFreezeAmountException e) {
			log.errLog("活期宝转入确认", e);
			e.printStackTrace();

		} catch (Exception ex) {
			throw ex;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public List<DemandtreasureTransferIn> getTransferInByFork() {

		return demandtreasureTransferInDao.getTransferInByFork();
	}

	@Override
	public void update(DemandtreasureTransferIn demandtreasureTransferIn) {
		demandtreasureTransferInDao.update(demandtreasureTransferIn);

	}

	@Override
	public List<DemandtreasureTransferIn> getTransferInitByFork() {
		return demandtreasureTransferInDao.getTransferInitByFork();
	}

	@Override
	public Double getDemandMoney(String userId) {
		return this.demandtreasureTransferInDao.getDemandMoney(userId);
	}

	@Override
	public List<String> getTurnOutUserId() {
		return this.demandtreasureTransferInDao.getTurnOutUserId();
	}

}