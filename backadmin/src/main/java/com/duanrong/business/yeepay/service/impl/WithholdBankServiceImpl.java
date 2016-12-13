package com.duanrong.business.yeepay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.Log;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.risk.service.SystemBillService;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.yeepay.dao.WithholdBankDao;
import com.duanrong.business.yeepay.model.WithholdBank;
import com.duanrong.business.yeepay.service.WithholdBankService;

@Service
public class WithholdBankServiceImpl implements WithholdBankService {

	@Resource
	WithholdBankDao withholdBankDao;
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	@Resource
	RechargeService rechargeService;
	@Resource
	UserMoneyService userMoneyService;
	@Resource
	SystemBillService systemBillService;
	
	
	@Resource
	Log log;
	
	@Override
	public boolean delete(String id) {
		try {
			withholdBankDao.delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public List<WithholdBank> readList(WithholdBank withholdBank) {
		return withholdBankDao.findList(withholdBank);
	}
	
	@Override
	public boolean insert(WithholdBank withholdBank) {
		try {
			withholdBankDao.insert(withholdBank);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/*public String withHold(WithholdBank withholdBank, String amount,User user) throws InsufficientBalance, IOException {
			Double money=Double.parseDouble(amount);
		
			// 拼接请求字符串
				GeneratorXML xml = new GeneratorXML();
				String requestNo=IdUtil.generateId(ToType.WHDE);
				xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
				xml.setRequestNo(requestNo);
				xml.setPlatformUserNo(withholdBank.getUserId());
				xml.setPayWay(withholdBank.getBankCode());
				xml.setAmount(money);
				xml.setFeeMode("PLATFORM");
				xml.setRealName(user.getRealname().trim());
				xml.setIdCardNo(user.getIdCard().trim());
				xml.setBankCardNo(withholdBank.getBankCount().trim());
				xml.setNotifyUrl(TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
						+ TrusteeshipYeepayConstants.OperationType.WHDEBITNOCARD);			
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

				log.infoLog("代扣XML", content);
				log.infoLog("代扣sign", sign);
				
				TrusteeshipOperation to = new TrusteeshipOperation();
				to.setId(IdGenerator.randomUUID());
				to.setMarkId(requestNo);
				to.setOperator(requestNo);
				to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
				to.setRequestData(MapUtil.mapToString(params));
				to.setRequestTime(new Date());
				to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
				to.setType(TrusteeshipYeepayConstants.OperationType.WHDEBITNOCARD);
				to.setUserId(user.getUserId());
				to.setTrusteeship("yeepay");
				trusteeshipOperationService.insert(to);
				// 创建直连请求
				HttpClient client = new HttpClient();
				PostMethod postMethod = new PostMethod(
						TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
				
									
				postMethod.setParameter("req", content);
				postMethod.setParameter("sign", sign);		
				postMethod.setParameter("service", "WHDEBITNOCARD_RECHARGE");
				int statusCode = client.executeMethod(postMethod);
				if (statusCode != HttpStatus.SC_OK) {
					log.errLog("Class WithholdBankServiceImpl.withHold", "Method failed: " + postMethod.getStatusLine());
				}
				// 获得返回的结果
				byte[] responseBody = postMethod.getResponseBody();
				log.infoLog(requestNo + "代扣", new String(
						responseBody, "UTF-8"));			
				@SuppressWarnings("unchecked")
				Map<String, String> respMap = Dom4jUtil.xmltoMap(new String(
						responseBody, "UTF-8"));
				String code = respMap.get("code");
				String description = respMap.get("description");
				to.setResponseData(new String(responseBody, "UTF-8"));
				to.setResponseTime(new Date());
				if ("1".equals(code)) {
					Recharge recharge=new Recharge();
					recharge.setId(IdGenerator.randomUUID());
					recharge.setUserId(user.getUserId());
					recharge.setActualMoney(money);
					recharge.setStatus("success");
					recharge.setFee(0D);
					recharge.setType("withhold");
					recharge.setRechargeWay("管理后台");
					rechargeService.insert(recharge);
					userMoneyService.transferIntoBalance(user.getUserId(), money, "用户："+user.getUserId()+ "代扣充值成功", "充值银行卡号：" +  withholdBank.getBankCount(), new Date());
					systemBillService.transferOut(10, "扣除代扣手续费", user.getUserId()+":充值银行卡号：" +  withholdBank.getBankCount());
					to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
					trusteeshipOperationService.update(to);
					return "ok";
				} else {
						to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
						trusteeshipOperationService.update(to);
						return description;
					}
			}*/
}
