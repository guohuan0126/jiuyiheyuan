package com.duanrong.business.bankcard;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import util.AESUtil;
import util.Log;
import util.XMLUtil;

import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.bankcard.service.BankCardService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.dao.SmsDao;
import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.User;
import com.duanrong.sms.SMSSend;
import com.duanrong.util.DateUtil;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.LoadConstantProterties;
import com.duanrong.yeepay.xml.GeneratorXML;
import com.duanrong.yeepaysign.CFCASignUtil;

/*@Component*/
public class BankCardSchdule {

	@Resource
	BankCardService bankCardService;
	
	@Resource
	UserDao userDao;
	
	@Resource
	SmsDao smsDao;
	
	@Resource
	Log log;
	
	/**
	 * 每天凌晨一点执行，五分钟轮询一次
	 */
	@Scheduled(cron="0 0/5 * * * ?")
	public void handleSendedOperations() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("解绑银行卡-轮询开始", sdf.format(new Date()));
		List<BankCard> bcs = bankCardService.getCancelTheTieCard();
		if (CollectionUtils.isEmpty(bcs)) {
			return;
		}
		for (BankCard bankCard : bcs) {
			try {
				Date date = convertStringToDate(bankCard);
				if (DateUtil.calculateIntervalDays(date, new Date()) > 2) {
					log.infoLog("解绑卡", bankCard.getCardNo()+"申请时间："+bankCard.getCancelBandDingTime());
					if(bankCard.getPaymentId() != null && "JDpay".equals(bankCard.getPaymentId())){
						bankCardCancelLocal(bankCard);
					}else{
						cancelBindingCard(bankCard);
					}		
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.errLog("解绑卡异常", bankCard.toString() + e);
			}

		}
	}
	
	private Date convertStringToDate(BankCard bankCard) throws ParseException {
		String cancelBindingCardDate = bankCard.getCancelBandDingTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date  = format.parse(cancelBindingCardDate);
		return date;
	}
	
	/**
	 * 取消绑卡主动查询
	 * 
	 * @param to
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private void cancelBindingCard(BankCard bankCard) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		client.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	    postMethod.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);
	    postMethod.addRequestHeader("Connection", "Close");
		/*********************** XML拼接 ***********************/
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setRequestNo(bankCard.getId());
		xml.setMode("UNBIND_RECORD");
		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("解绑卡XML拼接异常", e);
			return;
		}

		/*********************** 生成签名 ***********************/
		String sign = CFCASignUtil.sign(content);
		postMethod.setParameter("req", content);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "QUERY");

		String userId = bankCard.getUserId();

		/*********************** 执行POST ***********************/
		try {
			int statusCode = client.executeMethod(postMethod);
			if (HttpStatus.SC_OK != statusCode) {
				log.errLog("解绑卡HTTP状态码异常", postMethod.getStatusLine()
						.toString());
			}

			// 易宝响应结果
			byte[] responseBody = postMethod.getResponseBody();
			recordData(responseBody, "cancelBindingCard");

			String respInfo = new String(responseBody, "UTF-8");
			log.infoLog("轮询数据记录", "cancelBindingCard:" + respInfo);
			// 响应信息
			Document respXML = DocumentHelper.parseText(respInfo);
			Map<String, String> resultMap = Dom4jUtil.xmltoMap(respInfo);
			String code = resultMap.get("code");
			String records = resultMap.get("records");
			if (StringUtils.equals("1", code)
					&& StringUtils.isNotBlank(records)) {
				String userNo = respXML.selectSingleNode(
						"/response/records/record/platformUserNo")
						.getStringValue();
				if (!userId.equals(userNo)) {
					log.errLog("解绑卡失败", "本地用户Id"+userId+",易宝返回用户Id"+userNo);
					return;
				}
				String status = respXML.selectSingleNode(
						"/response/records/record/status").getStringValue();
				if ("SUCCESS".equals(status)) {
					bankCardCancelLocal(bankCard);
				}
			}
		} catch (Exception e) {
			log.errLog("解绑卡异常", e);
		}
	}
	
	private void  bankCardCancelLocal(BankCard bankCard){
		bankCard.setCancelStatus("解绑成功");
		bankCard.setDeleteBankCard("delete");
		DateFormat df = DateFormat.getDateTimeInstance();
		String cancelBindingCardDate = df.format(new Date());
		bankCard.setBindingPrice(cancelBindingCardDate);
		bankCardService.update(bankCard);
		log.infoLog("解绑卡", "userId:"+bankCard.getUserId()+"卡号："+bankCard.getCardNo()+"解绑成功");
		try {
			User user = userDao.get(bankCard.getUserId());
			String cardNo = bankCard.getCardNo();
			if (user != null
					&& StringUtils.isNotBlank(user
							.getMobileNumber())
					&& StringUtils.isNotBlank(cardNo)
					&& cardNo.length() > 4) {
				cardNo = AESUtil.decode(cardNo);
				cardNo = cardNo.substring(cardNo.length() - 4,
						cardNo.length());
				String msg = "您尾号#{cardNo}的银行卡已解除成功。如非本人操作请致电客服：400-062-1008 。";
				msg = StringUtils.replace(msg, "#{cardNo}", cardNo);
				SMSSend.batchSend(user.getMobileNumber(), msg);
				Sms sms = new Sms();
				sms.setId(IdGenerator.randomUUID());
				sms.setUserId(user.getUserId());
				sms.setMobileNumber(user.getMobileNumber());
				sms.setContent(msg);
				sms.setTime(new Date());
				sms.setType(SmsConstants.CANCELBINDINGCARD);
				smsDao.insert(sms);
			}
		} catch (Exception e) {
			log.errLog("解绑卡发送验证码错误", e);
		}
	}
	
	
	/**
	 * 日志记录
	 * 
	 * @param responseBody
	 * @param type
	 */
	public void recordData(byte[] responseBody, String type) {
		try {
			List<String> list = new ArrayList<String>();
			list.add("duanrongcfcaConfig.properties");
			HashMap<String, Properties> loadConstantsPro = LoadConstantProterties
					.loadConstantsPro(list);
			String yeepayResponseDataPath = null;
			if (loadConstantsPro != null && loadConstantsPro.size() > 0) {
				Properties properties = loadConstantsPro
						.get("duanrongcfcaConfig.properties");
				yeepayResponseDataPath = properties
						.getProperty("yeepayResponseDataPath");
				if (properties == null || yeepayResponseDataPath == null) {
					return;
				} else {
					if (responseBody == null) {
						CFCASignUtil.writeMessage("响应数据为空" + type,
								yeepayResponseDataPath);
						log.errLog("响应数据为空", type);
					} else {
						// 创建一个要写入的文件路径,按天生成
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						String fileName = sdf.format(new Date());
						fileName = yeepayResponseDataPath
								+ "responseYeepayData" + fileName + ".txt";
						String respInfo = new String(responseBody, "UTF-8");
						CFCASignUtil.writeMessage(type + respInfo, fileName);
					}
				}
			}
		} catch (Exception e) {
			log.errLog("记录易宝响应数据TrusteeshipQuartzServiceImpl.recordData", e);
		}
	}
}