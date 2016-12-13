package com.duanrong.cps.business.quartz;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import util.bsyUtil.BysCotanst;

import com.duanrong.cps.business.bsy.model.BsyChangeInvestStatus;
import com.duanrong.cps.business.bsy.model.BsyInterest;
import com.duanrong.cps.business.bsy.model.BsyInvest;
import com.duanrong.cps.business.bsy.model.BsyPushLoad;
import com.duanrong.cps.business.bsy.model.BsyRepayMoney;
import com.duanrong.cps.business.bsy.service.BsyInvestService;
import com.duanrong.cps.business.bsy.service.BsyService;
import com.duanrong.cps.util.ArithUtil;
import com.duanrong.cps.util.DateUtil;
import com.duanrong.cps.util.IdGenerator;
import com.duanrong.cps.util.LogUtil;
import com.gaoxin.bsy.openapi.client.PUSHProduct;
import com.gaoxin.bsy.openapi.client.PUSHTrade;
import com.gaoxin.bsy.openapi.http.data.ResponseData;
import com.gaoxin.bsy.openapi.push.product.ProductResponse;
import com.gaoxin.bsy.openapi.push.product.ProductStatusRequest;
import com.gaoxin.bsy.openapi.push.trade.TradeIncomeRequest;
import com.gaoxin.bsy.openapi.push.trade.TradeRecordRequest;
import com.gaoxin.bsy.openapi.push.trade.TradeRefundRequest;
import com.gaoxin.bsy.openapi.push.trade.TradeResponse;
import com.gaoxin.bsy.openapi.push.trade.TradeStatusRequest;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 能在quartz的job中，注入Spring的对象
 * 
 * @author Lin Zhiming
 * @version Feb 27, 2015 11:21:43 AM
 */
@Component
public class quartz  {
	
	@Resource
	BsyService bsyService;
	
	@Autowired
    private	BsyInvestService bsyInvestService;
	
	/**
	 * 描述：轮询执行处理发生改变的项目的信息
	 * @author lxw
	 * @email  dongjun@duanrong.com
	 * @date 2016年4月21日 下午6:19:51
	 */

	//@Scheduled(cron = "0/45 * * * * ?")
	public void bsyPushChangeLoan(){
		List<Map> changList = bsyService.getChangeBsyLoan(); 
		if(changList!=null&&changList.size()>0){
			
	 		for(int i=0;i<changList.size();i++){
				try{
					List<ProductStatusRequest> statusList = new ArrayList();
					Map map = changList.get(i);
					ProductStatusRequest statusReQuest = new ProductStatusRequest();
					statusReQuest.setPcode(BysCotanst.cid);
					statusReQuest.setOcode(map.get("id").toString());
					double totalMoney = map.get("money")==null?0:Double.valueOf(map.get("money").toString());
					double sumInvestMoney = map.get("sum_money")==null?0:Double.valueOf(map.get("sum_money").toString());
					statusReQuest.setProgress(progress(totalMoney , sumInvestMoney));
					statusReQuest.setRemainmoney(ArithUtil.round(ArithUtil.sub(totalMoney, sumInvestMoney), 2));
					statusReQuest.setStatus(1);
					String loanStatus = map.get("STATUS")==null?"":map.get("STATUS").toString();
					statusReQuest.setTradable(isBuy(loanStatus));
					statusList.add(statusReQuest);
					ResponseData<ProductResponse> responseData = PUSHProduct.productStatus(statusList);
					BsyPushLoad bsyPushLoad = new BsyPushLoad();
					bsyPushLoad.setId(map.get("id").toString());
					bsyPushLoad.setLoanstatus(loanStatus);
					bsyPushLoad.setInvestmoney(new BigDecimal(sumInvestMoney));
					bsyPushLoad.setLoanprogress(progress(totalMoney , sumInvestMoney));
					bsyPushLoad.setTradable(isBuy(loanStatus));
					bsyPushLoad.setMessage(responseData.getMessage());
					if("完成".equals(loanStatus)){
						bsyPushLoad.setStatus(1);
					}
					bsyService.updateByPrimaryKeySelective(bsyPushLoad);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
		}
		
	}
	/**
	 * 描述：touzijidu
	 * @author lxw
	 * @email  dongjun@duanrong.com
	 * @date 2016年4月20日 下午5:56:14
	 */
   public static double progress(double totalMoney ,double sumInvestMoney){
	    double p = 0;
	    MathContext mc = new MathContext(3, RoundingMode.HALF_DOWN);
		BigDecimal b1 = new BigDecimal(totalMoney);
		BigDecimal b2 = new BigDecimal(sumInvestMoney);
		double rate = b2.divide(b1,mc).multiply(new BigDecimal(100),mc).doubleValue();
	    return rate;
   }
   
	
   
   /**
	 * 描述：是否可购买
	 * 
	 * @author lxw
	 * @email  dongjun@duanrong.com
	 * @date 2016年4月20日 下午4:29:36
	 */
	public int isBuy(String status){
		int flag = 0;
		if("筹款中".equals(status)){
			flag = 1;
		}
		else{
			flag = 0;
		}
		return flag;
	}
	
	
	
	
	
	/**
	 *
	 * 描述：推送起息状态
	 * 
	 * */


	//@Scheduled(cron = "* * 0/2 * * ?")
	public void putBsyInterest(){
		List<BsyInvest> BsyInterestBean = bsyInvestService.getInterest();
		
		for(int i=0; i<BsyInterestBean.size(); i++){
			BsyInvest  bsyInterest=BsyInterestBean.get(i);
			TradeIncomeRequest treadIncome=new TradeIncomeRequest();
			treadIncome.setPcode("10003");  //平台接入编号
			treadIncome.setSn(bsyInterest.getInvestId());//交 易 订 单 流 水 号
			treadIncome.setOcode(bsyInterest.getLoan().getId()); //原始平台标的编码 
			treadIncome.setYaccount(bsyInterest.getPlatformUser().getUserID()); //乙方账户
			treadIncome.setSdate(bsyInterest.getLoan().getGiveMoneyTime()); //起息日
			
			String operationType=bsyInterest.getLoan().getOperationType();
			Date endDate=null;
			if("月".equals(operationType)){
				Integer deadline=bsyInterest.getLoan().getDeadline(); //月标的值，几个月
				 endDate=DateUtil.addMonth(bsyInterest.getLoan().getGiveMoneyTime(), deadline);
			}else {
				Integer day=bsyInterest.getLoan().getDay(); //天标值，多少天
			    endDate=DateUtil.addDay(bsyInterest.getLoan().getGiveMoneyTime(), day);
			}
			
			treadIncome.setEdate(endDate); //到期日
			treadIncome.setOstatus(1); //订单状态 1 成功， 0 失败， -1 处理中
			treadIncome.setPstatus(1);
			if("完成".equals(bsyInterest.getLoan().getStatus())){
				treadIncome.setBstatus(4);
			}else{
				treadIncome.setBstatus(2); //标的状态  1 待回款， 2 回款中  3 逾期中， 4 已结束
			}
			 
			treadIncome.setRemark(""); // 备注
			
			List<TradeIncomeRequest> treadIncomeList=new ArrayList<TradeIncomeRequest>();
			treadIncomeList.add(treadIncome);
			ResponseData<List<TradeResponse>> responseData=PUSHTrade.tradeIncome(treadIncomeList);
			
			LogUtil.infoLog("比搜益轮询推送起息状态", "返回值："+responseData.getMessage()+"标的ID"+bsyInterest.getLoan().getId()+"userID:"+bsyInterest.getPlatformUser().getUserID()+"investID:"+bsyInterest.getInvestId(),0);
			
			if(1==responseData.getStatus()){  // 推送成功存入数据库
				BsyInterest  bsyInterestBean=new BsyInterest();
				bsyInterestBean.setId(IdGenerator.randomUUID());
				bsyInterestBean.setInterestDate(bsyInterest.getLoan().getGiveMoneyTime());
				bsyInterestBean.setInvestId(bsyInterest.getInvestId());
				bsyInterestBean.setLoanId(bsyInterest.getLoan().getId());
				bsyInterestBean.setLoanStatus(treadIncome.getBstatus());
				bsyInterestBean.setMessage(responseData.getMessage());
				bsyInterestBean.setNowTime(new Date());
				bsyInterestBean.setOrderStatus(treadIncome.getOstatus());
				bsyInterestBean.setPayStatus(treadIncome.getPstatus());
				bsyInterestBean.setRepayDate(treadIncome.getEdate());
				bsyInterestBean.setUserId(bsyInterest.getPlatformUser().getUserID());
				bsyInvestService.insertInterest(bsyInterestBean);
			}
		}
	}
	
	/**
	 *
	 * 描述：回款状态
	 * 
	 * */

	//@Scheduled(cron = "* * 0/1 * * ?")
	public void repaymoney(){
		List<BsyRepayMoney> repayMoneyList= bsyInvestService.getBsyRepayMoney();
		
		for(int i=0; i<repayMoneyList.size(); i++){
			List<TradeRefundRequest> refundRequestList=new ArrayList<TradeRefundRequest>();
			TradeRefundRequest refundRequest=new TradeRefundRequest();
			refundRequest.setBdate(repayMoneyList.get(i).getRepayDate());  //回款日
			refundRequest.setBmoney(repayMoneyList.get(i).getCorpus()+repayMoneyList.get(i).getInterest()); //回款金额  本金+利息
			if("完成".equals(repayMoneyList.get(i).getLoanStatus())){
				refundRequest.setBstatus(4); //  标的状态    1 待回款， 2 回款中   3 逾期中， 4 已结束
			}else{
				refundRequest.setBstatus(2); 
			}
			refundRequest.setOcode(repayMoneyList.get(i).getLoanId());  //标的编码
			refundRequest.setOstatus(1);  // 订单状态   1 成功， 0 失败， -1 处理中
			refundRequest.setPcode("10003");
			refundRequest.setPstatus(1);  //支付状态  1 成功， 0 失败， -1 处理中
			refundRequest.setSn(repayMoneyList.get(i).getInvestId());
			refundRequest.setYaccount(repayMoneyList.get(i).getUserId());
			refundRequestList.add(refundRequest);
			ResponseData<List<TradeResponse>> responseData= PUSHTrade. tradeRefund(refundRequestList);
			/*LogUtil.infoLog("比搜益轮询推送回款状态","返回值:"+responseData.getMessage()+"LoanID:"+repayMoneyList.get(i).getLoanId()+
					"investID:"+repayMoneyList.get(i).getInvestId()+"UserID:"+repayMoneyList.get(i).getUserId()+
					"回款金额:"+repayMoneyList.get(i).getCorpus()+repayMoneyList.get(i).getInterest(),0);*/
			if(responseData.getStatus()==1){
			    BsyRepayMoney bsyRepayMoney= repayMoneyList.get(i);
			    bsyRepayMoney.setId(IdGenerator.randomUUID());
			    bsyRepayMoney.setNowTime(new Date());
			    bsyRepayMoney.setRepayMoney(repayMoneyList.get(i).getCorpus()+repayMoneyList.get(i).getInterest());
			    bsyRepayMoney.setOrderStatus(1);
			    bsyRepayMoney.setPayStatus(1);
			    bsyRepayMoney.setBstatus(1);
				bsyInvestService.insertRepayMoney(bsyRepayMoney);
			}
		}
		
		
	}
	
	


	//@Scheduled(cron = "0/30 * * * * ?")
	public void bsyPushInvest(){
		//用于推送给 比搜益的list
		// 查询出所有未推送给 比搜益的 比搜益用户的投资记录
		List<BsyInvest> pushInvestList=bsyService.getPushInvest();
		if(pushInvestList!=null&&pushInvestList.size()>0){
			// list 不为空 遍历list 一个一个对象封装拼接到比搜益的model 中 并放到statusList 中
			for (BsyInvest bsyInvest : pushInvestList) {
				TradeRecordRequest tradeRecordRequest =new TradeRecordRequest();
				// 开始封装参数到model 中 传给比搜益
				try {
				tradeRecordRequest.setPcode(BysCotanst.cid);
				tradeRecordRequest.setSn(bsyInvest.getInvestId());
				tradeRecordRequest.setOcode(bsyInvest.getLoanId());
				tradeRecordRequest.setYaccount(bsyInvest.getUserId());
				//tradeRecordRequest.setIdcard(bsyInvest.getIdCard());
				tradeRecordRequest.setName(bsyInvest.getLoanName());
				tradeRecordRequest.setAmoney(Double.parseDouble(bsyInvest.getInvestMoney()));
				if(bsyInvest.getTimeType().equals("月")){
					tradeRecordRequest.setUnit(2);
					tradeRecordRequest.setAperiod(Integer.parseInt(bsyInvest.getMonth())*30);
				}else if(bsyInvest.getTimeType().equals("天")){
					tradeRecordRequest.setUnit(1);
					tradeRecordRequest.setAperiod(Integer.parseInt(bsyInvest.getDay()));
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				
					tradeRecordRequest.setAdate(df.parse(bsyInvest.getInvestTime()));
					tradeRecordRequest.setRate(bsyInvest.getRate()*100);
					tradeRecordRequest.setGuarantee(bsyInvest.getRepayType());
					if(bsyInvest.getRateTime()!=null && bsyInvest.getExpectTime()!=null ){
					tradeRecordRequest.setSdate(df.parse(bsyInvest.getRateTime()));//起息日
					tradeRecordRequest.setEdate(df.parse(bsyInvest.getExpectTime()));//到期日
					}else{
						tradeRecordRequest.setSdate(df.parse("1970-01-01 00:00:00"));
						tradeRecordRequest.setEdate(df.parse("1970-01-01 00:00:00"));
					}
					//tradeRecordRequest.setBankcard("621483******9269");// 银行卡先写死 不取值 等 商量的结果在处理银行卡信息
					tradeRecordRequest.setPtype(0);//支付类型
					tradeRecordRequest.setOstatus(1);//订单状态
					tradeRecordRequest.setPstatus(1);//支付状态状态
					//项目状态
					if(bsyInvest.getLoanStatus().equals("等待复核") || bsyInvest.getLoanStatus().equals("筹款中")){
						tradeRecordRequest.setBstatus(1);
					}else if(bsyInvest.getLoanStatus().equals("还款中")){
						tradeRecordRequest.setBstatus(2);
					}else if(bsyInvest.getLoanStatus().equals("完成")){
						tradeRecordRequest.setBstatus(4);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//校验推送投资记录的状态是否成功 成功存到本地库中
				try {
					ResponseData<TradeResponse> responseData=PUSHTrade.tradeRecord(tradeRecordRequest);
					
					LogUtil.infoLog("比搜益轮询推送交易记录","loanID:"+bsyInvest.getLoanId()+"返回结果"+responseData.getMessage()+"investID:"+bsyInvest.getInvestId()+
							"userID:"+bsyInvest.getUserId(),0);
					System.out.println(responseData.getMessage());
					if(responseData.getStatus()==1){
						HashMap<String,Object> params=new HashMap<>();
						params.put("id",IdGenerator.randomUUID());
						params.put("loan_id",tradeRecordRequest.getOcode());
						params.put("invest_id", tradeRecordRequest.getSn());
						params.put("user_id", tradeRecordRequest.getYaccount());
						//params.put("id_card", tradeRecordRequest.getIdcard());
						params.put("loan_name", tradeRecordRequest.getName());
						params.put("invest_money", tradeRecordRequest.getAmoney());
						params.put("period", tradeRecordRequest.getAperiod());
						params.put("period_unit", tradeRecordRequest.getUnit());
						params.put("rate", tradeRecordRequest.getRate());
						params.put("profit_way", tradeRecordRequest.getGuarantee());
						params.put("interest_date", tradeRecordRequest.getSdate());
						params.put("repay_date", tradeRecordRequest.getEdate());
						//params.put("bank_card", tradeRecordRequest.getBankcard());
						params.put("pay_type", 2);
						params.put("order_status", 1);
						params.put("pay_status", 1);
						params.put("loan_status", tradeRecordRequest.getBstatus());
						params.put("now_time", new Date());
						params.put("invest_status",bsyInvest.getInvestStatus());
						bsyService.insertBsyPushInvest(params);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		 }
		 
	}	
	
	
	
	/**
	 * 推送所有投资记录状态修改的投资记录
	 */

	//@Scheduled(cron = "0/40 * * * * ?")
	public void pushChangeInvestStatus(){
		List<BsyChangeInvestStatus> pushInvestList=bsyService.getpushChangeInvestStatus();
		if(pushInvestList!=null&&pushInvestList.size()>0){
			List<TradeStatusRequest> tradeStatusRequestList = new ArrayList();
			for (BsyChangeInvestStatus bsyChangeInvestStatus : pushInvestList) {
				TradeStatusRequest tradeStatusRequest = new TradeStatusRequest();
				tradeStatusRequest.setPcode(BysCotanst.cid);
				tradeStatusRequest.setSn(bsyChangeInvestStatus.getInvestId());
				tradeStatusRequest.setOcode(bsyChangeInvestStatus.getLoanId());
				tradeStatusRequest.setYaccount(bsyChangeInvestStatus.getUserId());
				tradeStatusRequest.setOstatus(1);
				tradeStatusRequest.setPstatus(1);
				if(bsyChangeInvestStatus.getLoanType().equals("已投满") ||bsyChangeInvestStatus.getLoanType().equals("等待复核") ||bsyChangeInvestStatus.getLoanType().equals("筹款中")){
					tradeStatusRequest.setBstatus(1);
				}else if(bsyChangeInvestStatus.getLoanType().equals("还款中")){
					tradeStatusRequest.setBstatus(2);
				}else if(bsyChangeInvestStatus.getLoanType().equals("完成")){
					tradeStatusRequest.setBstatus(4);
				}
				tradeStatusRequestList.add(tradeStatusRequest);
				ResponseData<List<TradeResponse>>  responseData =PUSHTrade.tradeStatus(tradeStatusRequestList);
				
				LogUtil.infoLog("比搜益轮询推送投资记录状态","loanId:"+bsyChangeInvestStatus.getLoanId()+"InvestID:"+
						bsyChangeInvestStatus.getInvestId()+"userID:"+bsyChangeInvestStatus.getUserId(),0);
				System.out.println(responseData.getMessage());
				if(responseData.getStatus()==1){
					HashMap<String,Object> params=new HashMap<>();
				params.put("nowTime",new Date());
				params.put("investId",bsyChangeInvestStatus.getInvestId());
				params.put("investStatus",bsyChangeInvestStatus.getInvestStatus());
				if(bsyChangeInvestStatus.getLoanType().equals("已投满") ||bsyChangeInvestStatus.getLoanType().equals("等待复核") ||bsyChangeInvestStatus.getLoanType().equals("筹款中")){
					params.put("loanStatus",1);
				}else if(bsyChangeInvestStatus.getLoanType().equals("还款中")){
					params.put("loanStatus",2);
				}else if(bsyChangeInvestStatus.getLoanType().equals("完成")){
					params.put("loanStatus",4);  
				}
				bsyService.updatePushInvestStatus(params);
				}
				
			}
			
		}
	}
	
	
	
	
	}
