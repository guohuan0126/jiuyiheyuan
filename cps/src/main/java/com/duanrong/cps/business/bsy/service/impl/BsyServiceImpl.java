package com.duanrong.cps.business.bsy.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.bsyUtil.BysCotanst;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.dao.BsyDao;
import com.duanrong.cps.business.bsy.model.BsyChangeInvestStatus;
import com.duanrong.cps.business.bsy.model.BsyInvest;
import com.duanrong.cps.business.bsy.model.BsyPushInterest;
import com.duanrong.cps.business.bsy.model.BsyPushLoad;
import com.duanrong.cps.business.bsy.model.BsyPushRepayMent;
import com.duanrong.cps.business.bsy.model.BsyPushedInvest;
import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.bsy.model.PlatformUserRelation;
import com.duanrong.cps.business.bsy.service.BsyService;
import com.duanrong.cps.business.loan.dao.impl.LoanDaoImpl;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.util.ArithUtil;
import com.duanrong.cps.util.DateUtil;
import com.duanrong.cps.util.IdGenerator;
import com.gaoxin.bsy.openapi.client.PUSHProduct;
import com.gaoxin.bsy.openapi.http.data.ResponseData;
import com.gaoxin.bsy.openapi.push.product.ProductBasiceRequest;
import com.gaoxin.bsy.openapi.push.product.ProductResponse;
@Service
@Transactional
public class BsyServiceImpl implements BsyService{
	@Resource
	private BsyDao bsyDao;
	@Resource
	private LoanDaoImpl loanDao;
	@Override
	public Map insertBsyPushLoan(String id, String userid) {
		Map returnMap = new HashMap();
		returnMap.put("flag", 0);
		try {
			System.out.println("############比搜益推送标的记录");
			List idList = Arrays.asList(id.split(","));
			List<Loan> lList = loanDao.getBsyLoanByLoanId(idList);
			System.out.println("###############比搜益#############：推送标的"+lList.toString());
			
			Map loanMap = new HashMap();
			List<BsyPushLoad> insertList  = new ArrayList(); 
			if(lList==null||lList.size()<=0){
				return returnMap;
			}
			List<ProductBasiceRequest> productList = new ArrayList();
			for(int i=0;i<lList.size();i++){
				loanMap.put(lList.get(i).getId(), lList.get(i));
				parseToProductBasiceRequest( productList, lList.get(i));
			}
			System.out.println("############比搜益：########productList:"+productList.toString());
			ResponseData<ProductResponse> resData = PUSHProduct.productBasice(productList);
			System.out.println("#############################################"+resData.getMessage()+"##########################################");
			List<String> successcodes = null;
			List<String> failurecodes = null;
			if(resData.getContent()!=null){
				 successcodes = resData.getContent().getSuccesscodes();
				 failurecodes = resData.getContent().getFailurecodes();
				 if(successcodes!=null&&successcodes.size()>0){
					for(int i=0;i<successcodes.size();i++){
						Loan l = (Loan)loanMap.get(successcodes.get(i));
						BsyPushLoad bsyPushLoad  = new BsyPushLoad();
						bsyPushLoad.setId(IdGenerator.randomUUID());
						bsyPushLoad.setLoanid(l.getId());
						bsyPushLoad.setLoanprogress(progress(l.getTotalmoney() ,l.getCalculateMoneyNeedRaised()==null?0:l.getCalculateMoneyNeedRaised()));
						bsyPushLoad.setLoanstatus(l.getStatus());
						bsyPushLoad.setIsline(1);
						bsyPushLoad.setStatus(0);
						bsyPushLoad.setPushuserid(userid);
						bsyPushLoad.setD_pushtime(new Date());
						bsyPushLoad.setD_nowtime(new Date());
						bsyPushLoad.setInvestmoney(new BigDecimal(l.getCalculateMoneyNeedRaised()==null?0:l.getCalculateMoneyNeedRaised()));
						bsyPushLoad.setTradable(1);
						insertList.add(bsyPushLoad);
					}
					bsyDao.insertBsyPushLoad(insertList);
					returnMap.put("flag", 1);
			 } 
			}
		   if(failurecodes!=null&&failurecodes.size()>0){
			   returnMap.put("failurecodes", failurecodes);
		   }
		  
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return returnMap;
	}
	
	/**
	 * 拼装比搜益数据对象
	 * @param l
	 * @return
	 */
	private void parseToProductBasiceRequest(List<ProductBasiceRequest> productList,Loan loan) {
		ProductBasiceRequest product = new ProductBasiceRequest();
		product.setPcode( BysCotanst.cid);//平台接入编号
		product.setOcode(loan.getId());//原始平台标的编码
		product.setOurl("http://m.duanrong.net/BSY/investInfo?loanId="+loan.getId());//原始平台标的网址
		product.setType(getLeiXing(loan.getNewbieEnjoy()));//标的类型
		product.setAttribute(loan.getLoanType());//标的性质
		product.setCategory(0);//标的分类
		product.setName(loan.getName());//标的名称
		product.setBidmoney(new BigDecimal(loan.getTotalmoney()).doubleValue());//标的金额
		product.setRate(loan.getRate()*100);//年化利率
		product.setRdate(loan.getCommitTime());//发布日期
		if(loan.getOperationType().equals("天")){
			product.setPeriod(loan.getDay());//投资周期
			product.setUnit(1); //周期单位
			product.setEdate(DateUtil.addDay(loan.getCommitTime(), loan.getDay())); //截止日期
		}
		else if(loan.getOperationType().equals("月")){
			product.setPeriod(loan.getDeadline()*30);//投资周期
			product.setUnit(2); //周期单位
			product.setEdate(DateUtil.addDay(loan.getCommitTime(), loan.getDeadline()*30)); //截止日期
		}
	   else if(loan.getOperationType().equals("年")){
		    product.setPeriod(loan.getDeadline()*360);//投资周期
			product.setUnit(3); //周期单位
			product.setEdate(DateUtil.addDay(loan.getCommitTime(), loan.getDeadline()*360)); //截止日期
		}
		product.setProgress(progress(loan.getTotalmoney() ,loan.getCalculateMoneyNeedRaised()==null?0:loan.getCalculateMoneyNeedRaised()));//投资进度
		product.setLaunchmoney(loan.getInvestOriginMoney());//起投金额
		product.setRemainmoney(ArithUtil.round(ArithUtil.sub(loan.getTotalmoney(), loan.getCalculateMoneyNeedRaised()==null?0:loan.getCalculateMoneyNeedRaised()), 2));//剩余金额
		product.setRepayment(loan.getRepayType());//还款方式
		
		if(loan.getLoanType().equals("车贷")){
			Map cheMap = bsyDao.getVehicle(loan.getId());
			if(cheMap!=null){
				StringBuffer cheBuf = new StringBuffer();
				cheBuf.append("车辆品牌型号："+cheMap.get("brand"));
				cheBuf.append("|车辆车牌号码："+cheMap.get("license_plate_number"));
				cheBuf.append("|已行驶公里数："+cheMap.get("kilometre_amount"));
				cheBuf.append("|同类市场价格："+cheMap.get("second_hand_price"));
				cheBuf.append("|车辆评估价格："+cheMap.get("assess_price"));
				product.setDetail(cheBuf.toString());//产品详情
					product.setGuarantee("如出现借款人未能按时还款的情形，1通过诉讼、拍卖、变卖等法律流程进行抵质押物处置变现；2投资人可授权短融网寻找资产管理公司等第三方收购该笔债权；3由短融网债权劣后基金进行收购。");//资金保障
			}
			else{
				StringBuffer cheBuf = new StringBuffer();
				cheBuf.append("车辆品牌型号：测试");
				cheBuf.append("|车辆车牌号码：测试");
				cheBuf.append("|已行驶公里数：测试");
				cheBuf.append("|同类市场价格：测试");
				cheBuf.append("|车辆评估价格：测试");
				product.setDetail(cheBuf.toString());//产品详情
				product.setGuarantee("测试");//资金保障
			}
			
		}
		if(loan.getLoanType().equals("房贷")){
			Map houseMap = bsyDao.getHouse(loan.getId());
			if(houseMap!=null){
				StringBuffer houseBuf = new StringBuffer();
				houseBuf.append("所处位置："+houseMap.get("position"));
				houseBuf.append("|建筑面积："+houseMap.get("area"));
				houseBuf.append("|楼房性质："+houseMap.get("nature"));
				houseBuf.append("|评估价格："+houseMap.get("assess_price"));
				product.setDetail(houseBuf.toString());//产品详情
					product.setGuarantee("如出现借款人未能按时还款的情形:1.通过诉讼、拍卖、变卖等法律流程进行抵押物处置变现；2.投资人可授权短融网寻找资产管理公司等第三方收购该笔债权；");//资金保障
			}
			else{
				StringBuffer houseBuf = new StringBuffer();
				houseBuf.append("所处位置：测试");
				houseBuf.append("|建筑面积：测试");
				houseBuf.append("|楼房性质：测试");
				houseBuf.append("|评估价格：测试");
				product.setDetail(houseBuf.toString());//产品详情
				product.setGuarantee("测试");//资金保障
			}
			
		}
		if(loan.getLoanType().equals("企业贷")){
			Map enterpriseMap = bsyDao.getEnterprise(loan.getId());
			if(enterpriseMap!=null){
				StringBuffer enterpriseBuf = new StringBuffer();
				if(enterpriseMap.get("establish_time")==null){
					enterpriseBuf.append("成立时间："+"");
				}else{
					enterpriseBuf.append("成立时间："+enterpriseMap.get("establish_time"));
				}
				enterpriseBuf.append("|已经经营年限："+enterpriseMap.get("operating_period"));
				enterpriseBuf.append("|经营范围："+enterpriseMap.get("business_scope"));
				enterpriseBuf.append("|注册资本："+enterpriseMap.get("registered_capital"));
				enterpriseBuf.append("|实收资本："+enterpriseMap.get("paid_in_capital"));
				enterpriseBuf.append("|借款人持股比列："+enterpriseMap.get("inshold"));
				enterpriseBuf.append("|公司简介："+enterpriseMap.get("company_introduction"));
				product.setDetail(enterpriseBuf.toString());//产品详情
				product.setGuarantee("由企业实际控制人承担无限连带保证；企业及其子公司承担无限连带保证；企业财务总监、企业董事承担无限连带保证；当地另一家企业的法定代表人承担无限连带保证");//资金保障
			}
			else{
				StringBuffer enterpriseBuf = new StringBuffer();
				enterpriseBuf.append("成立时间：测试");
				enterpriseBuf.append("|已经经营年限：测试");
				enterpriseBuf.append("|经营范围：测试");
				enterpriseBuf.append("|注册资本：测试");
				enterpriseBuf.append("|实收资本：测试");
				enterpriseBuf.append("|借款人持股比列：测试");
				enterpriseBuf.append("|公司简介：测试");
				product.setDetail(enterpriseBuf.toString());//产品详情
				product.setGuarantee("测试");//资金保障
			}
			
		}
		if(loan.getLoanType().equals("供应宝")){
			Map supplyBufMap = bsyDao.getSupplychain(loan.getId());
			if(supplyBufMap!=null){
				StringBuffer supplyBuf = new StringBuffer();
				supplyBuf.append("成立时间："+supplyBufMap.get("build_date"));
				supplyBuf.append("|已经经营年限："+supplyBufMap.get("operation_year"));
				supplyBuf.append("|经营范围："+supplyBufMap.get("operation_business"));
				supplyBuf.append("|注册资本："+supplyBufMap.get("registered_capital"));
				supplyBuf.append("|实收资本："+supplyBufMap.get("real_income_capital"));
				supplyBuf.append("|公司简介："+supplyBufMap.get("company_desc"));
				product.setDetail(supplyBuf.toString());//产品详情
				product.setGuarantee("若借款人未按合同约定及时还本付息，且担保人未代偿的，在一定的期限内，经投资人授权短融网可寻找包括但不限于金融机构、资产管理公司、投资公司等第三方收购该笔债权。");//资金保障
			}
			else{
				StringBuffer supplyBuf = new StringBuffer();
				supplyBuf.append("成立时间：测试");
				supplyBuf.append("|已经经营年限：测试");
				supplyBuf.append("|经营范围：测试");
				supplyBuf.append("|注册资本：测试");
				supplyBuf.append("|实收资本：测试");
				supplyBuf.append("|公司简介：测试");
				product.setDetail(supplyBuf.toString());//产品详情
				product.setGuarantee("测试");//资金保障
			}
			
		}
		product.setTradable(isBuy(loan.getStatus()));//购买状态
		product.setStatus(1);//产品状态
		product.setAppurl("http://m.duanrong.com/BSY/investInfo?loanId="+loan.getId());//APP 交易URL
		product.setWeburl("http://m.duanrong.com/BSY/investInfo?loanId="+loan.getId());//WEB 交易URL
		productList.add(product);
	}
	
	
	public static void main(String[] args) {
		System.out.println(progress(360000,227700));

	}
	/**
	 * 描述：获取标的类型
	 * @author lxw
	 * @email  dongjun@duanrong.com
	 * @date 2016年4月20日 下午4:09:44
	 */
	public static int getLeiXing(String newbieEnjoy){
		int flag = 2;
		if("是".equals(newbieEnjoy)){
			flag = 1;
		}
		if("否".equals(newbieEnjoy)){
			flag = 2;
		}
		return flag;
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
    * 描述：定常截取字符串
    * 
    * @author lxw
    * @email  dongjun@duanrong.com
    * @date 2016年4月21日 下午1:59:15
    */
   
   public String subString(String str,int len){
	     if(str.trim().equals("")){
	    	 return "";
	     }
	     return str.substring(0, len)+"..";
   }

   public List<Map> getChangeBsyLoan() {

	   return bsyDao.getChangeBsyLoan();
   	}

   @Override
	public void updateByPrimaryKeySelective(BsyPushLoad bsyPushLoad) {
	   bsyDao.updateByPrimaryKeySelective(bsyPushLoad);
	}


@Override
public List<BsyInvest> getPushInvest() {
	
	return bsyDao.getPushInvest();

}

@Override
public void insertBsyPushInvest(HashMap<String,Object> params) {

	bsyDao.insertBsyPushInvest(params);
}

@Override
public List<BsyChangeInvestStatus> getpushChangeInvestStatus() {
	// TODO Auto-generated method stub
	return bsyDao.getpushChangeInvestStatus();
}

@Override
public void updatePushInvestStatus(HashMap<String, Object> params) {
	bsyDao.updatePushInvestStatus(params);
	
}

@Override
public PageInfo<BsyPushedInvest> getInvestStatus(int pageNo, int pageSize,
		Map<String, Object> params) {
	// TODO Auto-generated method stub
	return bsyDao.getInvestStatus( pageNo,pageSize,
			params);
}

@Override
public PageInfo<InvestByBsy> getInvestRecordsByBsy(int pageNo, int pageSize,
		Map<String, Object> params) {
	// TODO Auto-generated method stub
	return bsyDao.getInvestRecordsByBsy(pageNo,pageSize,
			params);
}

@Override
public PageInfo<BsyPushInterest> getbsyPushInterest(int pageNo,
		int pageSize, Map<String, Object> params) {
	// TODO Auto-generated method stub
	return bsyDao.getbsyPushInterest(pageNo,pageSize,
			params);
}

@Override
public PageInfo<BsyPushRepayMent> getbsyPushRepayMent(int pageNo,
		int pageSize, Map<String, Object> params) {
	// TODO Auto-generated method stub
	return bsyDao.getbsyPushRepayMent(pageNo,pageSize,
			params);
}

@Override
public PageInfo<PlatformUserRelation> getBsyUserInfo(int pageNo,
		int pageSize, Map<String, Object> params) {
	// TODO Auto-generated method stub
	return bsyDao.getBsyUserInfo(pageNo,pageSize,
			params);
}

@Override
public PageInfo<InvestByBsy> getInvestRecordsByBjs(int pageNo, int pageSize,
		Map<String, Object> params) {
	
	return bsyDao.getInvestRecordsByBjs(pageNo, pageSize, params);
}



 

}
