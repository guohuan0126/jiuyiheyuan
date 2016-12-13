package com.duanrong.business.ruralfinance.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.dao.AgricultureForkLoansDao;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.service.AgricultureForkLoansService;
import com.duanrong.business.ruralfinance.service.AgricultureLoanerInfoService;
import com.duanrong.business.ruralfinance.service.AgricultureRepaymentPlanService;
import com.duanrong.newadmin.utility.ForkloansCompute;
import com.duanrong.newadmin.utility.IdGenerator;
@Service
public class AgricultureForkLoansServiceImpl implements AgricultureForkLoansService {
	/**
	 * 借款人表
	 */
	@Resource
	private  AgricultureForkLoansDao agricultureForkLoansDao;

	@Autowired
	private AgricultureLoanerInfoService agricultureLoanerInfoService;
	@Autowired
	private AgricultureRepaymentPlanService agricultureRepaymentPlanService;
	@Override
	public void insertBatch(List<AgricultureForkLoans> list) {		
		agricultureForkLoansDao.insertBatch(list);
	}

	@Override
	public PageInfo<AgricultureForkLoans> readPackagingLoan(int pageNo,
			int pageSize, Map<String, Object> params) {
		return agricultureForkLoansDao.readPackagingLoan(pageNo, pageSize, params);
	}

	@Override
	public void updateBatch(Map<String, Object> params) {
		 agricultureForkLoansDao.updateBatch(params);
		
	}

	@Override
	public int updateForkLoanstatus(Map<String, Object> params) {
		return  agricultureForkLoansDao.updateForkLoanstatus(params);
	}

	@Override
	public AgricultureForkLoans readByForkId(String id) {
		return  agricultureForkLoansDao.readByForkId(id);
	}

	@Override
	public void updatePackStatus(Map<String, Object> params) {
		agricultureForkLoansDao.updatePackStatus(params);
		
	}

	@Override
	public String BatchForkLoans(String[] ids) {
		for (String id : ids) {
			// System.out.println(id);
			AgricultureLoaninfo agricultureLoaninfo = agricultureLoanerInfoService
					.readAgricultureLoaninfoById(id);
			if (agricultureLoaninfo != null && agricultureLoaninfo.getForkStatus()==0) {
				int loan_term = agricultureLoaninfo.getLoanTerm();//借款期限
				double money = agricultureLoaninfo.getMoney();//合同金额
				double rate =agricultureLoaninfo.getRate();//利率
				String childContractid = agricultureLoaninfo
						.getContractId();
				String parentId = agricultureLoaninfo.getId();
				String repayType= agricultureLoaninfo.getRepayType();//还款方式
				Date createTime = new Date();
			   double benjin = 0.0;
			   double benjinhe=0.0;
				List<AgricultureForkLoans> list = new ArrayList<AgricultureForkLoans>();
				//如果是等额本息的就拆标把子标金额存入，如果是先息后本的就把主项目复制到子标项目里面，这样金农宝定期项目里能勾选
				if("等额本息".equals(repayType)){
					for (int i = 1; i <= loan_term; i++) {
						AgricultureForkLoans agforkLoans = new AgricultureForkLoans();
						agforkLoans.setId(IdGenerator.randomUUID());
						//本金取100的整数倍					
						/*benjin =Math.floor(ForkloansCompute.corpus(money, rate, loan_term, i)/100)*100;  */
						//修改规则为1的整数倍
						benjin =Math.floor(ForkloansCompute.corpus(money, rate, loan_term, i));  
			        	if(i==loan_term){
			        		benjin=money-benjinhe;	        		
			        	}
			        	benjinhe +=benjin;
			        	//等本金原先的拆标
						/*agforkLoans.setMoney(ForkloansCompute.remainmoney(money, loan_term, i));*/
			        	agforkLoans.setMoney(benjin);
						agforkLoans.setChildContractid(childContractid + i);
						agforkLoans.setLoanTerm(i);
						agforkLoans.setPacking(0);
						agforkLoans.setParentId(parentId);
						agforkLoans.setCreateTime(createTime);
						list.add(agforkLoans);
					}
				}else if("先息后本".equals(repayType)){
					AgricultureForkLoans agforkLoans = new AgricultureForkLoans();
					agforkLoans.setId(IdGenerator.randomUUID());
		        	agforkLoans.setMoney(money);
					agforkLoans.setChildContractid(childContractid +1);
					agforkLoans.setLoanTerm(loan_term);
					agforkLoans.setPacking(0);
					agforkLoans.setParentId(parentId);
					agforkLoans.setCreateTime(createTime);
					list.add(agforkLoans);					
				}
				try {
					agricultureForkLoansDao.insertBatch(list);
					Map<String, Object> loanerInfo = new HashMap<>();
					loanerInfo.put("forkStatus", 1);
					loanerInfo.put("id", id);	
					if("先息后本".equals(repayType)){
					loanerInfo.put("packingStatus", "1");
					}
					agricultureLoanerInfoService
							.updateForkStatusById(loanerInfo);
				} catch (Exception e) {
					e.printStackTrace();						
					return "error";
				}
			}
		}
		return "success";
	}

}
