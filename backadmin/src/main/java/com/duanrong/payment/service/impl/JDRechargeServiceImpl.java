package com.duanrong.payment.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import util.Log;

import com.duanrong.payment.PaymentConstants;
import com.duanrong.payment.jd.client.SignHelper;
import com.duanrong.payment.jd.model.BaseResponseDto;
import com.duanrong.payment.jd.model.PaySignEntity;
import com.duanrong.payment.jd.model.QueryResultTradeEntity;
import com.duanrong.payment.jd.model.QuickMoblieOrderInfo;
import com.duanrong.payment.jd.model.QuickPcOrderInfo;
import com.duanrong.payment.jd.model.TradeQueryEntity;
import com.duanrong.payment.jd.model.TradeQueryRes;
import com.duanrong.payment.jd.utils.ByteUtil;
import com.duanrong.payment.jd.utils.DESUtil;
import com.duanrong.payment.jd.utils.FormatUtil;
import com.duanrong.payment.jd.utils.HttpsClientUtil;
import com.duanrong.payment.jd.utils.JsonUtil;
import com.duanrong.payment.jd.utils.MD5Util;
import com.duanrong.payment.jd.utils.RSACoder;
import com.duanrong.payment.jd.utils.SHA256Util;
import com.duanrong.payment.jd.utils.SHAUtil;
import com.duanrong.payment.jd.utils.SignUtil;
import com.duanrong.payment.jd.utils.TDESUtil;
import com.duanrong.payment.model.PayOrderInfo;
import com.duanrong.payment.service.JDRechargeService;
import com.duanrong.util.DateUtil;
import com.duanrong.util.Dom4jUtil;

/**
 * @Description JD充值
 * @author JD
 * @CreateDate 2016-4-5 17:03:52
 */
@Service
public class JDRechargeServiceImpl implements JDRechargeService {


	@Resource
	Log log;
	
	@Override
	public Object createRechargeOrder(PayOrderInfo payOrderInfo)throws Exception {
		//PC快捷支付
		if(PaymentConstants.Mode.QUICK.equals(payOrderInfo.getType())){
			if("pc".equals(payOrderInfo.getRechargeWay())){
				return createQuickPC(payOrderInfo);
			}else{
				return createQuickMoblie(payOrderInfo);
			}
		}
		//网关支付
		if(PaymentConstants.Mode.GATEWAY.equals(payOrderInfo.getType())){
		}
		log.errLog("JD支付", "未找到第三方支付通道，type:"+payOrderInfo.getType()+",其他参数："+payOrderInfo.toString());
		return null;
	}
	private Object createQuickMoblie(PayOrderInfo payOrderInfo) {
		PaySignEntity wePayMerchantSignReqDTO = new PaySignEntity();

        wePayMerchantSignReqDTO.setVersion("3.0.5");
        wePayMerchantSignReqDTO.setToken(payOrderInfo.getToken());
        wePayMerchantSignReqDTO.setMerchantNum(PaymentConstants.JDConfig.MER_NUM_MOBILE);
        wePayMerchantSignReqDTO.setTradeNum(payOrderInfo.getId());
        wePayMerchantSignReqDTO.setTradeTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        wePayMerchantSignReqDTO.setTradeName(PaymentConstants.OperationType.RECHARGE);
        wePayMerchantSignReqDTO.setCurrency(PaymentConstants.Currency.CNY);
        wePayMerchantSignReqDTO.setMerchantRemark("京东支付移动端待结算账户");
        DecimalFormat df = new DecimalFormat("##0.##"); 
        wePayMerchantSignReqDTO.setTradeAmount(df.format(payOrderInfo.getMoney()*100));
        wePayMerchantSignReqDTO.setTradeDescription("移动快捷充值");
        wePayMerchantSignReqDTO.setSuccessCallbackUrl(payOrderInfo.getCallbackUrl());
        wePayMerchantSignReqDTO.setFailCallbackUrl(payOrderInfo.getFailCallbackUrl());
        wePayMerchantSignReqDTO.setNotifyUrl(new StringBuffer(PaymentConstants.ResponseS2SUrl)
		.append(PaymentConstants.JDConfig.RECHARGE)
		.append(PaymentConstants.Mode.MOBILE).append("_")
		.append(PaymentConstants.Mode.QUICK).append(".do").toString());
        wePayMerchantSignReqDTO.setSpecifyInfoJson(setSpecifyInfo(payOrderInfo.getIdCard(),payOrderInfo.getRealname()));
        /**
         * 商户签名
         */
        QuickMoblieOrderInfo orderInfo = new QuickMoblieOrderInfo();
        orderInfo.setVersion("3.0.5");
        orderInfo.setToken(payOrderInfo.getToken());
        orderInfo.setMerchantNum(PaymentConstants.JDConfig.MER_NUM_MOBILE);
        orderInfo.setTradeNum(payOrderInfo.getId());
        orderInfo.setTradeTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        orderInfo.setTradeName(PaymentConstants.OperationType.RECHARGE);
        orderInfo.setCurrency(PaymentConstants.Currency.CNY);
        orderInfo.setMerchantRemark("京东支付移动端待结算账户");
        orderInfo.setTradeAmount(df.format(payOrderInfo.getMoney()*100));
        orderInfo.setTradeDescription("移动快捷充值");
        orderInfo.setSuccessCallbackUrl(payOrderInfo.getCallbackUrl());
        orderInfo.setFailCallbackUrl(payOrderInfo.getFailCallbackUrl());
        orderInfo.setNotifyUrl(new StringBuffer(PaymentConstants.ResponseS2SUrl)
		.append(PaymentConstants.JDConfig.RECHARGE)
		.append(PaymentConstants.Mode.MOBILE).append("_")
		.append(PaymentConstants.Mode.QUICK).append(".do").toString());
        String signStr = SignUtil.sign4SelectedKeys(wePayMerchantSignReqDTO, PaymentConstants.JDConfig.MER_PRI_KEY,getSignList(wePayMerchantSignReqDTO));
        System.out.println("================:"+getSignList(wePayMerchantSignReqDTO));
        orderInfo.setMerchantSign(signStr);
        System.out.println("----------------"+signStr);
        	
        	//敏感信息加密
            try {
                //获取商户 DESkey
                String desKey = PaymentConstants.JDConfig.MER_DES_KEY;
                //对敏感信息进行 DES加密
                orderInfo.setMerchantRemark(DESUtil.encrypt("京东支付移动端待结算账户", desKey, "UTF-8"));
                orderInfo.setTradeNum(DESUtil.encrypt(payOrderInfo.getId(), desKey, "UTF-8"));
                orderInfo.setTradeName(DESUtil.encrypt(PaymentConstants.OperationType.RECHARGE, desKey, "UTF-8"));
                orderInfo.setTradeDescription(DESUtil.encrypt("移动快捷充值", desKey, "UTF-8"));
                orderInfo.setTradeTime(DESUtil.encrypt(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), desKey, "UTF-8"));
                orderInfo.setTradeAmount(DESUtil.encrypt(df.format(payOrderInfo.getMoney()*100), desKey, "UTF-8"));
                orderInfo.setCurrency(DESUtil.encrypt(PaymentConstants.Currency.CNY, desKey, "UTF-8"));
                orderInfo.setNotifyUrl(DESUtil.encrypt(new StringBuffer(PaymentConstants.ResponseS2SUrl)
        		.append(PaymentConstants.JDConfig.RECHARGE)
        		.append(PaymentConstants.Mode.MOBILE).append("_")
        		.append(PaymentConstants.Mode.QUICK).append(".do").toString(), desKey, "UTF-8"));
                orderInfo.setSuccessCallbackUrl(DESUtil.encrypt(payOrderInfo.getCallbackUrl(), desKey, "UTF-8"));
                orderInfo.setFailCallbackUrl(DESUtil.encrypt(payOrderInfo.getFailCallbackUrl(), desKey, "UTF-8"));
                if(StringUtils.isNotBlank(wePayMerchantSignReqDTO.getSpecifyInfoJson())){
                	orderInfo.setSpecifyInfoJson(DESUtil.encrypt(wePayMerchantSignReqDTO.getSpecifyInfoJson(), desKey, "UTF-8"));
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        return orderInfo;
	}
	
	  private List<String> getSignList(PaySignEntity wePayMerchantSignReqDTO){
	        List<String> signedKeyList = new ArrayList<String>();
	        //固定验签字段
	        signedKeyList.add("currency");
	 		signedKeyList.add("merchantNum");
	 		signedKeyList.add("merchantRemark");
	 		signedKeyList.add("tradeAmount");
	 		signedKeyList.add("tradeDescription");
	 		signedKeyList.add("tradeName");
	 		signedKeyList.add("tradeTime");
	 		signedKeyList.add("tradeNum");
	 		signedKeyList.add("notifyUrl");
	 		signedKeyList.add("successCallbackUrl");
	 		signedKeyList.add("failCallbackUrl");
	 		/* ======可选验签字段======== */
			if (StringUtils.isNotBlank(wePayMerchantSignReqDTO.getSpecifyInfoJson())) {
				signedKeyList.add("specifyInfoJson");
			}
			return signedKeyList;
	         
	    }
	private QuickPcOrderInfo createQuickPC(PayOrderInfo payOrderInfo)throws Exception{
		QuickPcOrderInfo orderInfo = new QuickPcOrderInfo();
		orderInfo.setMerchantNum(PaymentConstants.JDConfig.MER_NUM_PC);//商户号
		orderInfo.setToken(payOrderInfo.getToken());
		orderInfo.setVersion(PaymentConstants.JDConfig.VERSION);
		orderInfo.setMerchantRemark("京东支付PC端待结算账户");
		orderInfo.setTradeNum(payOrderInfo.getId());//流水号
		orderInfo.setTradeName(PaymentConstants.OperationType.RECHARGE);//充值
		orderInfo.setTradeDescription("PC快捷充值");
		DecimalFormat df = new DecimalFormat("##0.##"); 
		orderInfo.setTradeTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		orderInfo.setTradeAmount(df.format(payOrderInfo.getMoney()*100));
		orderInfo.setCurrency(PaymentConstants.Currency.CNY);
		orderInfo.setNotifyUrl(new StringBuffer(PaymentConstants.ResponseS2SUrl)
			.append(PaymentConstants.JDConfig.RECHARGE)
			.append(PaymentConstants.Mode.PC).append("_")
			.append(PaymentConstants.Mode.QUICK).append(".do").toString());
		orderInfo.setIp(payOrderInfo.getIp());
		orderInfo.setSuccessCallbackUrl(payOrderInfo.getCallbackUrl());
		orderInfo.setSpecifyInfoJson(setSpecifyInfo(payOrderInfo.getIdCard(),payOrderInfo.getRealname()));
		orderInfo.setMerchantSign(SignHelper.getSign(orderInfo, PaymentConstants.JDConfig.MER_PRI_KEY));
		return orderInfo;
	}

	@Override
	public String S2SCallback(HttpServletRequest request,HttpServletResponse response,String operationType)throws Exception{
		String resp  = request.getParameter("resp");
		byte[] decryptBASE64Arr = new BASE64Decoder().decodeBuffer(resp);
		String decryptStr = new String(decryptBASE64Arr,"UTF-8");
	    Map<String, String> resultMap = Dom4jUtil.xmltoMap(decryptStr);
	    if(verifySign(resultMap)){
	    	/********** 通知京东响应成功 **********/
//			response.getWriter().write("SUCCESS");------------
	    	//签名验证成功后解析data数据
			String dataXMl= DESUtil.decrypt(resultMap.get("DATA"), PaymentConstants.JDConfig.MER_DES_KEY, "UTF-8");
			return dataXMl;
	    }else{
	    	log.errLog("JD支付", "验证签名失败xxxxxxx");
	    }
	    return "";
	}
	
	private String getElementById(Iterator iter,String name){
		String value = "";
		while (iter.hasNext()) {
			Element childElement = (Element) iter.next();
			value = childElement.elementTextTrim(name); 
			
		}
		return value;
	}
	
	private boolean verifySign(Map<String, String> resultMap){
		boolean flag = false;
		String version = resultMap.get("VERSION");
	    String merchant = resultMap.get("MERCHANT");
	    String terminal = resultMap.get("TERMINAL");
		String data = resultMap.get("DATA");
		String sign = resultMap.get("SIGN");
		StringBuffer bf = new StringBuffer();
		bf.append(version).append(merchant).append(terminal).append(data);
		log.infoLog("JD支付", "验证签名："+bf.toString());
		try {
			flag = MD5Util.verify(bf.toString(), PaymentConstants.JDConfig.MER_MD5_KEY, sign);
		} catch (Exception e) {
			log.errLog("JD支付", "验证签名异常"+e);
		}
		return flag;
	}
	
	private String setSpecifyInfo(String idCard,String realname) {

		HashMap<String, String> map = new HashMap<String, String>();
		String specifyJson = "";
		if (StringUtils.isNotBlank(idCard)|| StringUtils.isNotBlank(realname)) {
			map.put("specIdCard", idCard);
			map.put("specName", realname);
			specifyJson = JsonUtil.write2JsonStr(map);
		}
		return specifyJson;
	}
	public  QueryResultTradeEntity queryOrderInfo(String tradeNum, String transferWay) {
		
        QueryResultTradeEntity qrte = new QueryResultTradeEntity();
        try {
        	String merNum = "";
        	if(transferWay.contains(PaymentConstants.Mode.PC)) {
    			merNum = PaymentConstants.JDConfig.MER_NUM_PC;
    		}else{
    			merNum = PaymentConstants.JDConfig.MER_NUM_MOBILE;
    		}
            String tradeJsonData = "{\"tradeNum\": \"" + tradeNum + "\"}";
            //1.对交易信息进行3DES加密
            String threeDesData = TDESUtil.encrypt2HexStr(RSACoder.decryptBASE64(PaymentConstants.JDConfig.MER_DES_KEY), tradeJsonData);

            //2.对3DES加密的数据进行签名
            String sha256Data = SHAUtil.Encrypt(threeDesData, null);
            byte[] rsaResult = RSACoder.encryptByPrivateKey(sha256Data.getBytes(), PaymentConstants.JDConfig.MER_PRI_KEY);
            String merchantSign = RSACoder.encryptBASE64(rsaResult);

            //3.构造最终交易查询请求json
            TradeQueryEntity queryTradeDTO = new TradeQueryEntity();
            queryTradeDTO.setVersion("1.0.5");
            queryTradeDTO.setMerchantNum(merNum);
            queryTradeDTO.setMerchantSign(FormatUtil.stringBlank(merchantSign));
            queryTradeDTO.setData(threeDesData);
            String json = JsonUtil.write2JsonStr(queryTradeDTO);
            log.infoLog("JD支付", "json:"+json);
            //4.发送请求
            String resultJsonData = HttpsClientUtil.sendRequest(PaymentConstants.JDConfig.QUERY, json);
            //5.验签返回数据
            BaseResponseDto<Map<String, Object>> result = (BaseResponseDto<Map<String, Object>>) JsonUtil.json2Object(resultJsonData, BaseResponseDto.class);
            log.infoLog("JD支付", "单笔业务查询："+result);
            //查询状态 成功
            if (result.getResultCode() == 0) {
                Map<String, Object> mapResult = result.getResultData();
                //有返回数据
                if (null != mapResult) {
                    String data = mapResult.get("data").toString();
                    String sign = mapResult.get("sign").toString();
                    //1.解密签名内容
                    byte[] decryptBASE64Arr = new BASE64Decoder().decodeBuffer(sign);
                    byte[] decryptArr = RSACoder.decryptByPublicKey(decryptBASE64Arr, PaymentConstants.JDConfig.MER_PUB_KEY);
                    String decryptStr = ByteUtil.byte2HexString(decryptArr);
                    //2.对data进行sha256摘要加密
                    String sha256SourceSignString = ByteUtil.byte2HexLowerCase(SHA256Util.encrypt(data.getBytes("UTF-8")));
                    //3.比对结果
                    if (decryptStr.equals(sha256SourceSignString)) {
                        /**
                         * 验签通过
                         */
                        //解密data
                        String decrypData = TDESUtil.decrypt4HexStr(RSACoder.decryptBASE64(PaymentConstants.JDConfig.MER_DES_KEY), data);
                        //注意 结果为List集合
                        List<Map<String, Object>> resultList = JsonUtil.jsonArray2List(decrypData);
                        if(resultList.size()==1){
                        	Map<String, Object> m = resultList.get(0);
                        	 qrte.setTradeCurrency(m.get("tradeCurrency") + "");
                             qrte.setTradeDate(m.get("tradeDate") + "");
                             qrte.setTradeTime(m.get("tradeTime") + "");
                             qrte.setTradeAmount(Integer.parseInt(m.get("tradeAmount") + ""));
                             qrte.setTradeNote(m.get("tradeNote") + "");
                             qrte.setTradeNum(m.get("tradeNum") + "");
                             qrte.setTradeStatus(m.get("tradeStatus") + "");
                             qrte.setCardNo(m.get("cardNo") + "");
                             qrte.setCardHolderMobile(m.get("cardHolderMobile") + "");
                             qrte.setCardHolderName(m.get("cardHolderName") + "");
                             qrte.setCardHolderId(m.get("cardHolderId") + "");
                             qrte.setPayAmount(m.get("payAmount") + "");
                             qrte.setBankCode(m.get("bankCode") + "");
                             qrte.setCardType(m.get("cardType") + "");
                        }
                    } else {
                    	log.errLog("JD支付", "验签失败");
                        /**
                         * 验签失败  不受信任的响应数据
                         * 终止
                         */
                    }
                }
            }else {
               log.errLog("JD支付", "单笔业务查询失败"+result.getResultMsg());
            }

        } catch (Exception e) {
            log.errLog("JD支付", "单笔业务查询失败"+e);
        }
        return qrte;
	}
	@Override
	public boolean callback(ServletRequest request, ServletResponse response)throws Exception {
		TradeQueryRes tradeQueryRes = new TradeQueryRes();
		String token = request.getParameter("token");
		tradeQueryRes.setToken(token);
		String tradeNum = request.getParameter("tradeNum");
		tradeQueryRes.setTradeNum(tradeNum);		
		String tradeAmount = request.getParameter("tradeAmount");
		tradeQueryRes.setTradeAmount(tradeAmount);		
		String tradeCurrency = request.getParameter("tradeCurrency");
		tradeQueryRes.setTradeCurrency(tradeCurrency);
		String tradeDate = request.getParameter("tradeDate");
		tradeQueryRes.setTradeDate(tradeDate);
		String tradeTime = request.getParameter("tradeTime");
		tradeQueryRes.setTradeTime(tradeTime);
		String tradeNote = request.getParameter("tradeNote");
		tradeQueryRes.setTradeNote(tradeNote);
		String tradeStatus = request.getParameter("tradeStatus");
		tradeQueryRes.setTradeStatus(tradeStatus);
		String sign = request.getParameter("sign");
		log.infoLog("第三方充值callback", tradeQueryRes.toString());
		log.infoLog("第三方充值callback", sign);
		List<String> list =  new ArrayList<String>();
		if(StringUtils.isBlank(tradeNote)){
			list.add("tradeNote");
		}
		String strSourceData = SignUtil.signString(tradeQueryRes,list);	
		byte[] decryptBASE64Arr = new BASE64Decoder().decodeBuffer(sign);
	    byte[] decryptArr = RSACoder.decryptByPublicKey(decryptBASE64Arr, PaymentConstants.JDConfig.MER_PUB_KEY);
	    String decryptStr = new String(decryptArr,"UTF-8");
	    String sha256SourceSignString = SHAUtil.Encrypt(strSourceData, null);
	    if (!decryptStr.equals(sha256SourceSignString)){
	    	log.errLog("第三方充值callback", "验证签名失败！");
	    	return false;
	    }else{
			return true;
	    }
	}
}
