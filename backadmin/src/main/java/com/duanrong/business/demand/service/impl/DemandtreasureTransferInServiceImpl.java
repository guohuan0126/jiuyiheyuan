package com.duanrong.business.demand.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import util.Log;
import util.XMLUtil;
import base.exception.InsufficientBalance;
import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.demand.DemandtreasureConstants;
import com.duanrong.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.business.demand.model.DemandTreasureBill;
import com.duanrong.business.demand.model.Demandtreasure;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;
import com.duanrong.business.demand.service.DemandTreasureBillService;
import com.duanrong.business.demand.service.DemandtreasureService;
import com.duanrong.business.demand.service.DemandtreasureTransferInService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.yeepay.constants.GeneralTransferConstants;
import com.duanrong.newadmin.utility.LoadConstantProterties2;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.MapUtil;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;

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
	Log log;

	@Override
	public List<DemandtreasureTransferIn> readAll(
			DemandtreasureTransferIn demandtreasureTransferIn) {
		return demandtreasureTransferInDao.find(demandtreasureTransferIn);
	}

	@Override
	public String transferInConfirm(DemandtreasureTransferIn demandIn,
			String flag) throws InsufficientBalance, IOException {
		// 拼接请求字符串
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
					int num = demandTreasureBillService.readCount(demandIn
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

					userAccountService.tofreeze(demandIn.getUserId(),
							demandIn.getMoney(), BusinessEnum.demand_in,
							"投资天天赚成功", "转账ID：" + demandIn.getId(),
							demandIn.getId());
					userAccountService.transferIn(user, demandIn.getMoney(),
							BusinessEnum.demand_in, "向用户：" + user
									+ "活期宝转账确认成功!转入金额",
							"转账ID：" + demandIn.getId(), demandIn.getId());

					DemandTreasureBill bill = new DemandTreasureBill();
					bill.setCreateTime(demandIn.getSendedTime());
					bill.setDetail("用户：" + demandIn.getUserId() + "活期宝转账确认成功!");
					bill.setId(IdGenerator.randomUUID());
					bill.setMoney(demandIn.getMoney());
					bill.setType(DemandtreasureConstants.TransferInStatus.TRANIN);
					bill.setTranferWay(demandIn.getTransferWay());
					bill.setUserId(demandIn.getUserId());
					bill.setBillId(demandIn.getId());
					demandTreasureBillService.insertBill(bill);
				} else {
					DemandtreasureTransferIn d = new DemandtreasureTransferIn();
					d.setStatus(DemandtreasureConstants.TransferInStatus.CANCEL);
					d.setId(demandIn.getId());
					d.setMoney(demandIn.getMoney());
					d.setConfirmTime(new Date());
					demandtreasureTransferInDao.update(d);
					userAccountService.unfreeze(demandIn.getUserId(),
							demandIn.getMoney(), BusinessEnum.demand_in,
							"解冻：投资天天赚", "转账ID：" + demandIn.getId(),
							demandIn.getId());
					List<Demandtreasure> list = demandtreasureService.readAll();
					if (list != null && list.size() > 0) {
						Demandtreasure de = list.get(0);
						Demandtreasure dem = new Demandtreasure();
						dem.setAvailableMoney(ArithUtil.add(
								de.getAvailableMoney(), demandIn.getMoney()));
						dem.setUpdateTime(new Date());
						dem.setId(de.getId());
						demandtreasureService.update(dem);
					}
				}
				to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
				trusteeshipOperationService.update(to);
				return "ok";
			} else {
				to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
				trusteeshipOperationService.update(to);
				return description;
			}
		} catch (NoOpenAccountException e) {
			log.errLog("活期宝转入确认", e);
			e.printStackTrace();
			return "用户未开户";
		} catch (InsufficientFreezeAmountException e) {
			log.errLog("活期宝转入确认", e);
			e.printStackTrace();
			return "冻结金额不足";
		} catch (Exception ex) {
			throw ex;
		}finally {
			lock.unlock();
		}
	}

	@Override
	public DemandtreasureTransferIn readTotal() {
		return demandtreasureTransferInDao.readTotal();
	}

	@Override
	public PageInfo<DemandtreasureTransferIn> readPageInfo(int pageNo, int pageSize) {
		return demandtreasureTransferInDao.readPageInfo(pageNo, pageSize);
	}

	@Override
	public List<DemandtreasureTransferIn> readTran(
			DemandtreasureTransferIn demandtreasureTransferIn) {
		return demandtreasureTransferInDao.readTran(demandtreasureTransferIn);
	}

	@Override
	public DemandtreasureTransferIn read(String id) {
		return demandtreasureTransferInDao.get(id);
	}

	@Override
	public List<DemandtreasureTransferIn> readStatus() {
		return demandtreasureTransferInDao.readStatus();
	}

	@Override
	public List<DemandtreasureTransferIn> readTransferInRecordTop(
			String userId, int top) {
		return demandtreasureTransferInDao.readTransferInRecordTop(userId, top);
	}

	/**
	 * 转入查询，带条件 分页
	 */
	@Override
	public PageInfo readListForTranferIn(int parseInt, int parseInt2,
			DemandtreasureTransferIn transferIn) {
		return demandtreasureTransferInDao.readListForTranferIn(parseInt,
				parseInt2, transferIn);
	}

	/**
	 * 总转入金额查询
	 * 
	 * @param transferIn
	 * @return
	 */
	public BigDecimal readSumMoneyTransferIn(DemandtreasureTransferIn transferIn) {
		return demandtreasureTransferInDao.readSumMoneyTransferIn(transferIn);
	}

	@Override
	public int readSumPeopleTransferIn(DemandtreasureTransferIn transferIn) {
		// TODO Auto-generated method stub
		return demandtreasureTransferInDao.readSumPeopleTransferIn(transferIn);
	}

	@Override
	public BigDecimal readSumMoneyTransferInFail(
			DemandtreasureTransferIn transferIn) {
		return demandtreasureTransferInDao.readSumMoneyTransferInFail(transferIn);
	}

	@Override
	public int readSumPeopleTransferInFail(DemandtreasureTransferIn transferIn) {
		return demandtreasureTransferInDao.readSumPeopleTransferInFail(transferIn);
	}

}
