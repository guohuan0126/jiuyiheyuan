package com.duanrong.payment.fuiou.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.duanrong.business.paymentInstitution.model.CompanyYeepayTransferUserYeepay;
import com.duanrong.business.paymentInstitution.service.PaymentCompanyService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.payment.PaymentConstants;
import com.duanrong.payment.fuiou.service.FuiouService;
import com.duanrong.util.Dom4jUtil;
import com.duanrong.util.client.DRHTTPSClient;
import com.fuiou.util.MD5;

@Service
public class FuiouServiceImpl  implements FuiouService{

	@Resource
	UserService userService;
	
	@Resource
	PaymentCompanyService companyService;
	
	/**
	 * 单笔业务查询 移动端
	 * @param orderInfo
	 * @return
	 */
	public Map<String,String> queryOrderInfoByOrderId(String orderId){
		CompanyYeepayTransferUserYeepay transfer = companyService.readByRequestNo(orderId);
		if(transfer==null||StringUtils.isBlank(transfer.getPaymentNo())){
			return null;
		}
		StringBuffer buf = new StringBuffer();
		String MchntCd=PaymentConstants.FuiouConfig.MER_MCHNTCD;
		String sign=MD5.MD5Encode(MchntCd+"|"+transfer.getPaymentNo()+"|"+PaymentConstants.FuiouConfig.MER_MD5_KEY);
		buf.append("<FM>");
		buf.append("<MchntCd>"+MchntCd+"</MchntCd>");
		buf.append("<OrderId>"+transfer.getPaymentNo()+"</OrderId>");
		buf.append("<Sign>"+sign+"</Sign>");
		buf.append("</FM>");
		List<NameValuePair>postMethod=new ArrayList<>();
		postMethod.add(new BasicNameValuePair("FM", buf.toString()));
		String	responseBody=null;
		Map<String, String> resultMap=null;
		
		try {
			responseBody = DRHTTPSClient.sendHTTPRequestPostToString(PaymentConstants.FuiouConfig.FUIOU_QUERY_URL, new BasicHeader[0], postMethod);
			if(responseBody != null && responseBody.trim().length()>0){		
				 resultMap = Dom4jUtil.xmltoMap(responseBody);		
				 String Rcd=resultMap.get("Rcd");
				 String RDesc=resultMap.get("RDesc");
				 /*String sign=resultMap.get("Sign");*/
				 if(!Rcd.equals("5185")&&!RDesc.equals("订单已支付")){
					 resultMap=null;
//					 Log.errLog("富友单笔订单查询", "该订单"+RDesc+"订单号："+orderId);
				 }
				 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	
	public static void main(String[] args) {
		
//		String signDataStr="0001000F0040992|Uvqy63fYzYjifpwn|Fuiou1473077565041|200|6214830125216183|邱飞虎|0|412825199202046710||http://demosoa-yeepay.duanrong.net/S2SCallback/notify.do?callbackData=Fuiou_recharge_pc";
//		String rsa=null;
//		try {
//			byte[] decryptBASE64Arr = new BASE64Decoder().decodeBuffer(PaymentConstants.FuiouConfig.MER_PRI_KEY_FUIOU.trim());
//			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decryptBASE64Arr);
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
//			Signature signature = Signature.getInstance("MD5WithRSA");
//			signature.initSign(priKey);
//			signature.update(signDataStr.getBytes("GBK"));
//			rsa = (new BASE64Encoder()).encodeBuffer(signature.sign());
//			System.out.println(rsa);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
	


}