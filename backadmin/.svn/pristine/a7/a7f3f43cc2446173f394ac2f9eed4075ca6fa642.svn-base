package com.duanrong.business.demand.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.DateUtilPlus;
import util.Log;
import util.MyStringUtils;
import util.XMLUtil;
import base.exception.InsufficientBalance;
import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.demand.DemandtreasureConstants;
import com.duanrong.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.business.demand.dao.DemandtreasureGuOutDao;
import com.duanrong.business.demand.dao.DemandtreasureGuOutDetailDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.business.demand.model.DemandTreasureBill;
import com.duanrong.business.demand.model.DemandtreasureGuOut;
import com.duanrong.business.demand.model.DemandtreasureGuOutDetail;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.business.demand.service.DemandTreasureBillService;
import com.duanrong.business.demand.service.DemandtreasureTransferOutService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.yeepay.constants.GeneralTransferConstants;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.IdUtil;
import com.duanrong.util.MapUtil;
import com.duanrong.util.ToType;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;

@Service
public class DemandtreasureTransferOutServiceImpl implements DemandtreasureTransferOutService{

	
	@Resource
	DemandtreasureTransferOutDao demandtreasureTransferOutDao;
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	@Resource
	DemandtreasureGuOutDao demandtreasureGuOutDao;
	@Resource
	DemandtreasureGuOutDetailDao demandtreasureGuOutDetailDao;
	@Resource
	UserAccountService userAccountService;
	@Resource
	DemandTreasureBillService demandTreasureBillService;
	@Resource
	DemandTreasureBillDao demandTreasureBillDao;
	@Resource
	SmsService smsService;
	@Resource
	InformationService informationService;
	
	@Resource
	Log log;
	
	@Override
	public void insert(DemandtreasureTransferOut demandOut) {
		demandtreasureTransferOutDao.insert(demandOut);
	}

	@Override
	public void update(DemandtreasureTransferOut demandOut) {
		demandtreasureTransferOutDao.update(demandOut);
		
	}

	@Override
	public DemandtreasureTransferOut readTotal() {
		 return demandtreasureTransferOutDao.gettotal();
	}

	public PageInfo<DemandtreasureTransferOut> readPageInfo(int pageNo, int pageSize,Map map) {
		return demandtreasureTransferOutDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public List<DemandtreasureTransferOut> readAll() {
		return demandtreasureTransferOutDao.findAll();
	}
	public List<String> createTransferOut(String account,
			List<DemandtreasureTransferOut> list,
			String callbackURL,double summoney){
		
			/*********************** 保存通用转账表 ***********************/
			String requestNo = IdUtil.generateId(ToType.TSCA);
			Date expired = DateUtils.addDays(new Date(), 1);
			String expiredFormate = DateUtilPlus.formatDateTime(expired);
			List<DemandtreasureGuOutDetail> details=new ArrayList<DemandtreasureGuOutDetail>();
			/*********************** XML拼接 ***********************/
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			// 商户编号:商户在易宝唯一标识
			xml.append("<request platformNo=\""
					+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
			// 请求流水号:接入方必须保证参数内的 requestNo 全局唯一，并且需要保存，留待后续业务使用
			xml.append("<requestNo>" + requestNo + "</requestNo>");
			// 出款人ID
			xml.append("<platformUserNo>" + account + "</platformUserNo>");
			// 出款人类型（个人会员）
			xml.append("<userType>MEMBER</userType>");
			xml.append("<bizType>TRANSFER</bizType>");
			xml.append("<expired>" + expiredFormate + "</expired>");
			xml.append("<details>");
			
			double totalMoney = 0D; 
			
			for (DemandtreasureTransferOut tad : list) {
				DemandtreasureGuOutDetail detail=new DemandtreasureGuOutDetail();
				detail.setSendedTime(new Date());
				detail.setId(IdGenerator.randomUUID());
				detail.setGuOutId(requestNo);
				detail.setTransferOutId(tad.getId());
				detail.setStatus(DemandtreasureConstants.TransferInStatus.SENDED);
				detail.setMoney(tad.getMoney());
				detail.setUserId(tad.getUserId());
				detail.setInterest(tad.getInterest());
				detail.setPrincipal(tad.getPrincipal());
				details.add(detail);
				xml.append("<detail>");
				Double amount = tad.getMoney();
				if (amount < 0) {
					amount = 0D;
				}
				// 转入金额
				xml.append(MyStringUtils
						.append("<amount>", amount, "</amount>"));
				// 收款人类型（个人会员）
				xml.append("<targetUserType>MEMBER</targetUserType>");
				// 收款人ID
				xml.append("<targetPlatformUserNo>" + tad.getUserId()
						+ "</targetPlatformUserNo>");
				xml.append("<bizType>TRANSFER</bizType>");
				xml.append("</detail>");
				totalMoney += tad.getMoney();
				
			}
			xml.append("</details>");
			xml.append("<notifyUrl>"
					+ new StringBuffer(
							TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL)
							.append(TrusteeshipYeepayConstants.OperationType.TO_CP_TRANSACTION)
							 + "</notifyUrl>");
			// 页面回跳URL
			xml.append("<callbackUrl>" + callbackURL + "</callbackUrl>");
			xml.append("</request>");

			String content = xml.toString();

			/*********************** 生成签名 ***********************/
			String sign = CFCASignUtil.sign(content);

			// 包装参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("req", content);
			params.put("sign", sign);

			log.infoLog("通用转账XML", content);
			log.infoLog("通用转账sign", sign);

			/*********************** 保存TO ***********************/
			TrusteeshipOperation to = new TrusteeshipOperation();
			to.setId(IdGenerator.randomUUID());
			to.setMarkId(requestNo);
			to.setOperator(requestNo);
			to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.TO_CP_TRANSACTION);
			to.setRequestData(MapUtil.mapToString(params));
			to.setType(TrusteeshipYeepayConstants.OperationType.TO_CP_TRANSACTION);
			to.setTrusteeship("yeepay");
			to.setRequestTime(new Date());
			to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
			// 需要设置userId，进行记录
			to.setUserId(account);
			trusteeshipOperationService.insert(to);
			DemandtreasureGuOut guOut=new DemandtreasureGuOut();
			guOut.setId(requestNo);
			guOut.setMoney(ArithUtil.round(totalMoney, 2));
			guOut.setRequestXml(xml.toString());
			guOut.setSendedTime(new Date());
			guOut.setUserId(account);
			guOut.setStatus(DemandtreasureConstants.TransferInStatus.SENDED);
			demandtreasureGuOutDao.insert(guOut);
			demandtreasureGuOutDetailDao.insertBatch(details);

			/*********************** 客户端需要的参数 ***********************/
			List<String> listStr = new ArrayList<String>();
			listStr.add(content);
			listStr.add(sign);
			return listStr;
		}
	public List<String> createstarTransferOut(DemandtreasureGuOut out){
			String content=out.getRequestXml();

			/*********************** 生成签名 ***********************/
			String sign = CFCASignUtil.sign(content);
			// 包装参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("req", content);
			params.put("sign", sign);

			log.infoLog("通用转账XML", content);
			log.infoLog("通用转账sign", sign);

			/*********************** 保存TO ***********************/
			TrusteeshipOperation to = new TrusteeshipOperation();
			to.setId(IdGenerator.randomUUID());
			to.setMarkId(out.getId());
			to.setOperator(out.getId());
			to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.TO_CP_TRANSACTION);
			to.setRequestData(MapUtil.mapToString(params));
			to.setType(TrusteeshipYeepayConstants.OperationType.TO_CP_TRANSACTION);
			to.setTrusteeship("yeepay");
			to.setRequestTime(new Date());
			to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
			// 需要设置userId，进行记录
			to.setUserId(out.getUserId());
			trusteeshipOperationService.insert(to);
			/*********************** 客户端需要的参数 ***********************/
			List<String> listStr = new ArrayList<String>();
			listStr.add(content);
			listStr.add(sign);
			return listStr;
		}
	@Override
	public void S2SCallback(ServletRequest request, ServletResponse response) {
		// 响应的参数 为XML格式
		String notifyXML = request.getParameter("notify");

		/********** 参数解析和对象获取 **********/
		Map<String, String> resultMap = Dom4jUtil.xmltoMap(notifyXML);
		String code = resultMap.get("code");
		String requestNo = resultMap.get("requestNo");
		String description = resultMap.get("status");

		TrusteeshipOperation to = trusteeshipOperationService.read(
				TrusteeshipYeepayConstants.OperationType.TO_CP_TRANSACTION,
				requestNo, requestNo, "yeepay");

		if (to == null) {
			return;
		}

		to.setResponseTime(new Date());

		/********** 授权成功 **********/
		if (StringUtils.equals("1", code)) {
			try {
				DemandtreasureGuOut demandtreasureGuOut=new DemandtreasureGuOut();
				DemandtreasureGuOut guOut=demandtreasureGuOutDao.get(requestNo);
				if(guOut!=null && (guOut.getStatus().equals("sended") || guOut.getStatus().equals("fail"))){
					demandtreasureGuOut.setId(requestNo);
					demandtreasureGuOut.setStatus(DemandtreasureConstants.TransferInStatus.FREEZE);
					demandtreasureGuOut.setResponseXml(notifyXML);
					demandtreasureGuOut.setFreezeTime(new Date());
					demandtreasureGuOutDao.update(demandtreasureGuOut);
					DemandtreasureGuOutDetail demandtreasureGuOutDetail=new DemandtreasureGuOutDetail();
					demandtreasureGuOutDetail.setGuOutId(requestNo);
					List<DemandtreasureGuOutDetail> ds=demandtreasureGuOutDetailDao.find(demandtreasureGuOutDetail);
					for(DemandtreasureGuOutDetail d:ds){
						DemandtreasureGuOutDetail detail=new DemandtreasureGuOutDetail();
						detail.setId(d.getId());
						detail.setFreezeTime(new Date());
						detail.setStatus(DemandtreasureConstants.TransferInStatus.FREEZE);
						demandtreasureGuOutDetailDao.update(detail);
					}
					userAccountService.freeze(guOut.getUserId(), guOut.getMoney(), BusinessEnum.demand_out, "通用转账", "通用转账  转账id:" + guOut.getId(), guOut.getId());
					/*userMoneyService.freezeMoney(guOut.getUserId(), guOut.getMoney(),
							"通用转账", "通用转账  转账id:" + guOut.getId());*/
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			to.setStatus(TrusteeshipConstants.Status.PASSED);
			to.setResponseData(notifyXML);
		} else {
			DemandtreasureGuOut demandtreasureGuOut=new DemandtreasureGuOut();
			demandtreasureGuOut.setId(requestNo);
			demandtreasureGuOut.setStatus(DemandtreasureConstants.TransferInStatus.FAIL);
			demandtreasureGuOut.setResponseXml(notifyXML);
			demandtreasureGuOutDao.update(demandtreasureGuOut);
			to.setStatus(TrusteeshipConstants.Status.REFUSED);
			to.setResponseData(description);
		}

		trusteeshipOperationService.update(to);
	}

	public String transferOutConfirm(String id, String flag) throws InsufficientBalance, IOException {
			DemandtreasureGuOut d=demandtreasureGuOutDao.get(id);
			if(d!=null && d.getStatus().equals("freeze")){
				// 拼接请求字符串
				GeneratorXML xml = new GeneratorXML();
				xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
				xml.setRequestNo(id);
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
				to.setMarkId(id);
				to.setOperator(id);
				to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
				to.setRequestData(MapUtil.mapToString(params));
				to.setRequestTime(new Date());
				to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
				to.setType("OutConfirm");
				to.setUserId(d.getUserId());
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
					log.errLog("Class DemandtreasureTransferInServiceImpl.transferInConfirm", "Method failed: " + postMethod.getStatusLine());
				}
				// 获得返回的结果
				byte[] responseBody = postMethod.getResponseBody();
				log.infoLog(id + "转账确认", new String(
						responseBody, "UTF-8"));			
				@SuppressWarnings("unchecked")
				Map<String, String> respMap = Dom4jUtil.xmltoMap(new String(
						responseBody, "UTF-8"));
				String code = respMap.get("code");
				String description = respMap.get("description");
				to.setResponseData(new String(responseBody, "UTF-8"));
				to.setResponseTime(new Date());
				try{
					if ("1".equals(code)) {
						if (GeneralTransferConstants.TransferStatus.CONFIRM.equals(flag)) {
							DemandtreasureGuOut out=new DemandtreasureGuOut();
							out.setStatus(DemandtreasureConstants.TransferInStatus.CONFIRM);
							out.setId(id);
							out.setConfirmTime(new Date());
							out.setConfirmrespXml(new String(
							responseBody, "UTF-8"));
							out.setConfirmreqXml(content);
							demandtreasureGuOutDao.update(out);
							DemandtreasureGuOutDetail detail=new DemandtreasureGuOutDetail();
							detail.setGuOutId(id);
							List<DemandtreasureGuOutDetail> deList=demandtreasureGuOutDetailDao.find(detail);
							for(DemandtreasureGuOutDetail gu:deList){
								try{
									transferOutLocal(gu);
								}catch(Exception e){							
									log.errLog("活期宝转出本地处理失败, transoutid: " + gu.getTransferOutId(), e, 1);
								}
								
							}
							userAccountService.tofreeze(d.getUserId(), d.getMoney(), BusinessEnum.demand_out, "用户："+d.getUserId()+ "活期宝转出转账确认成功!转出金额", "转账ID：" +  d.getId(),  d.getId());					
						}else{
							DemandtreasureGuOut out=new DemandtreasureGuOut();
							out.setStatus(DemandtreasureConstants.TransferInStatus.CANCEL);
							out.setId(id);
							out.setConfirmTime(new Date());
							out.setConfirmrespXml(new String(
							responseBody, "UTF-8"));
							out.setConfirmreqXml(content);
							demandtreasureGuOutDao.update(out);
							DemandtreasureGuOutDetail detail=new DemandtreasureGuOutDetail();
							detail.setGuOutId(id);
							List<DemandtreasureGuOutDetail> deList=demandtreasureGuOutDetailDao.find(detail);
							for(DemandtreasureGuOutDetail gu:deList){
								DemandtreasureGuOutDetail dg=new DemandtreasureGuOutDetail();
								dg.setId(gu.getId());
								dg.setConfirmTime(new Date());
								dg.setStatus(DemandtreasureConstants.TransferInStatus.CANCEL);
								demandtreasureGuOutDetailDao.update(dg);
							}
							userAccountService.unfreeze(d.getUserId(), d.getMoney(), BusinessEnum.demand_out, "用户："+d.getUserId()+ "活期宝转出转账取消成功!解冻金额", "转账ID：" +  d.getId(),  d.getId());	
						}
						to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
						trusteeshipOperationService.update(to);	
						return "ok";
					} else {
							DemandtreasureGuOut out=new DemandtreasureGuOut();
							out.setId(id);
							out.setConfirmrespXml(new String(
							responseBody, "UTF-8"));
							out.setConfirmreqXml(content);
							demandtreasureGuOutDao.update(out);
							to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
							trusteeshipOperationService.update(to);
							return description;
						}
				
				}catch(NoOpenAccountException | InsufficientFreezeAmountException e){
					return "未开户或冻结金额不足";
				}
				
			}else{
				return "状态不正确";
			}
		}

	@Transactional
	private void transferOutLocal(DemandtreasureGuOutDetail gu){
		DemandtreasureGuOutDetail dg=new DemandtreasureGuOutDetail();
		dg.setId(gu.getId());
		dg.setConfirmTime(new Date());
		dg.setStatus(DemandtreasureConstants.TransferInStatus.CONFIRM);
		demandtreasureGuOutDetailDao.update(dg);													
		DemandtreasureTransferOut tOut=	demandtreasureTransferOutDao.get(gu.getTransferOutId());
		if(gu.getInterest()!=null && gu.getInterest()>0){
			DemandTreasureBill bill=new DemandTreasureBill();
			bill.setCreateTime(new Date());
			bill.setDetail("用户："+gu.getUserId()+ "天天赚转让利息成功!");
			bill.setId(IdGenerator.randomUUID());
			bill.setMoney(gu.getInterest());
			bill.setType(DemandtreasureConstants.TransferInStatus.OUTINTEREST);
			bill.setTranferWay(tOut.getTransferWay());
			bill.setUserId(gu.getUserId());
			bill.setBillId(tOut.getId());
			demandTreasureBillDao.insertBill(bill);
		}
		if(gu.getPrincipal()!=null && gu.getPrincipal()>0){
			DemandTreasureBill bill=new DemandTreasureBill();
			bill.setCreateTime(new Date());
			bill.setDetail("用户："+gu.getUserId()+ "天天赚转让本金成功!");
			bill.setId(IdGenerator.randomUUID());
			bill.setMoney(gu.getPrincipal());
			bill.setType(DemandtreasureConstants.TransferInStatus.OUT);
			bill.setTranferWay(tOut.getTransferWay());
			bill.setUserId(gu.getUserId());
			bill.setBillId(tOut.getId());
			demandTreasureBillDao.insertBill(bill);
		}																										
		tOut.setStatus(DemandtreasureConstants.TransferInStatus.SUCCESS);
		tOut.setSuccessTime(new Date());
		demandtreasureTransferOutDao.update(tOut);
		try {
			userAccountService.transferIn(gu.getUserId(), gu.getMoney(), BusinessEnum.demand_out,  "天天赚转让到账", null, gu.getId());
		} catch (NoOpenAccountException e1) {
			e1.printStackTrace();
		}
		//发短信
		try{	
			String smsText = "您在天天赚账户中申请转让的"+gu.getMoney()+"元，已发放至您的资金托管账户";
			String informationTitle = "天天赚转让到账通知";
			if(tOut.getTransferWay() != null && tOut.getTransferWay().equals("expires")){
				smsText = "您的天天赚账户部分项目到期未成功续投，退还本息共计"+ArithUtil.round(gu.getMoney(), 2)+"元，已发放至您的资金托管账户。";
				informationTitle = "天天赚转让到账通知";
			}else{
				smsText = "您在天天赚账户中申请转让的"+ArithUtil.round(gu.getMoney(), 2)+"元，已发放至您的资金托管账户";
				informationTitle = "天天赚退出到账通知";
			}							
			smsService.sendSms(gu.getUserId(),smsText,SmsConstants.DEMANDOUT,new Date());
			informationService.sendInformation(gu.getUserId(), informationTitle, smsText);
		}catch(Exception e){
			log.errLog("活期宝赎回短信发送异常",e.getMessage());
		}	
		
	}
	
	@Override
	public List<DemandtreasureTransferOut> readTotalUser() {
		return demandtreasureTransferOutDao.gettotalUser();
	}

	/**
	 * 转出查询，带条件 分页
	 */
	@Override
	public PageInfo readListForTranferOut(int parseInt, int parseInt2, DemandtreasureTransferOut transferOut) {
		return demandtreasureTransferOutDao.getListForTranferOut(parseInt,parseInt2,transferOut);
	}

	/**
	 * 总转出金额
	 * @param transferOut
	 * @return
	 */
	public BigDecimal readSumMoneyTransferOut(DemandtreasureTransferOut transferOut) {
		return demandtreasureTransferOutDao.getSumMoneyTransferOut(transferOut);
	}

	@Override
	public int readSumPeopleTransferOut(DemandtreasureTransferOut transferOut) {
		// TODO Auto-generated method stub
		return demandtreasureTransferOutDao.getSumPeopleTransferOut(transferOut);
	}

	@Override
	public BigDecimal readSumMoneyFailTransferOut(
			DemandtreasureTransferOut transferOut) {
		return demandtreasureTransferOutDao.getSumMoneyFailTransferOut(transferOut);
	}

	@Override
	public int readSumPeopleFailTransferOut(DemandtreasureTransferOut transferOut) {
		return demandtreasureTransferOutDao.getSumPeopleFailTransferOut(transferOut);
	}

	
	
}
