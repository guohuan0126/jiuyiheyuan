package com.duanrong.business.demand.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import util.DateUtilPlus;
import util.Log;
import util.MyStringUtils;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.DemandtreasureConstants;
import com.duanrong.business.demand.dao.DemandtreasureGuOutDao;
import com.duanrong.business.demand.dao.DemandtreasureGuOutDetailDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.business.demand.model.DemandtreasureGuOut;
import com.duanrong.business.demand.model.DemandtreasureGuOutDetail;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.business.demand.service.DemandtreasureTransferOutService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.IdUtil;
import com.duanrong.util.MapUtil;
import com.duanrong.util.ToType;
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
	Log log;
	@Resource
	InformationService informationService;
	
	@Override
	public void insert(DemandtreasureTransferOut demandOut) {
		demandtreasureTransferOutDao.insert(demandOut);
	}

	@Override
	public void update(DemandtreasureTransferOut demandOut) {
		demandtreasureTransferOutDao.update(demandOut);
		
	}

	@Override
	public DemandtreasureTransferOut gettotal() {
		 return demandtreasureTransferOutDao.gettotal();
	}

	public PageInfo<DemandtreasureTransferOut> pageInfo(int pageNo, int pageSize,Map map) {
		return demandtreasureTransferOutDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public List<DemandtreasureTransferOut> findAll() {
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
			guOut.setMoney(summoney);
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
	


	

	@Override
	public List<DemandtreasureTransferOut> gettotalUser() {
		return demandtreasureTransferOutDao.gettotalUser();
	}

	@Override
	public List<DemandtreasureTransferOut> findAllSuccess() {
		return demandtreasureTransferOutDao.findAllSuccess();
	}
	
	
}
