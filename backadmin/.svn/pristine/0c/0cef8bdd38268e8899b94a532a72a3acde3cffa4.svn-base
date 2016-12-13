/**
 * 
 */
package com.duanrong.business.netloaneye.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.p2pEyeUtil.P2PEyeHttpUtil;
import util.p2pEyeUtil.P2PEyeHttpUtilAgain;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.netloaneye.mapper.PushLoanMapper;
import com.duanrong.business.netloaneye.model.NetLoanModel;
import com.duanrong.business.netloaneye.model.PushLoan;
import com.duanrong.business.netloaneye.service.NetLoanEyeService;
import com.duanrong.newadmin.utility.IdGenerator;

/**
 * @author bj
 *
 */
@Service
@Transactional
public class NetLoanEyeServiceImpl implements NetLoanEyeService {

	@Autowired
	private LoanDao loanDao;
	@Autowired
	private PushLoanMapper pushLoanMapper;
	/**
	 * 查询推送记录
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	public PageInfo<Loan> readPushRecords(String pageNo, Loan loan){
		PageHelper.startPage(Integer.parseInt(pageNo), 10);
		List<Loan> lst = loanDao.getPushRecords(loan); 
		return new PageInfo<>(lst);
	}
	
	/**
	 * 向天眼推送项目
	 * @param id
	 * @return 0:失败；1：成功
	 */
	@Transactional
	public int pushNetLoan(String id,String userid){
		Loan l = loanDao.getP2pEyeLoanByLoanId(id);
		if( l == null ) return 0;
		if(P2PEyeHttpUtil.sendPost(parseP2pEyeJsonObject(l), "loan") <= 0)
			return 0;
		PushLoan p = getPushLoan(l,userid);
		return pushLoanMapper.insertSelective(p);
	}

	
	
	
	
	/**
	 * 
	 */
	public NetLoanModel readNetLoanCount(Loan loan){
		return loanDao.getNetLoanCount(loan);
	}
	
	/**
	 * 
	 */
	public NetLoanModel readNetLoanSum(Loan loan){
		return loanDao.getNetLoanSum(loan);
	}
	
	//封装推送记录
	private PushLoan getPushLoan(Loan l,String userid) {
		PushLoan p = new PushLoan();
		p.setId(IdGenerator.randomUUID());
		p.setLoanId(l.getId());
		p.setPushTime(new Date());
		p.setPushUserid(userid);
		p.setStatus(l.getStatus());
		return p;
	}

	/**
	 * 拼装天眼数据对象
	 * @param l
	 * @return
	 */
	private JSONObject parseP2pEyeJsonObject(Loan loan) {
		JSONObject eyeLoan = new JSONObject();
		eyeLoan.put("type",1);						  //操作类型，提交标的固定为1
		eyeLoan.put("loan_id",loan.getId());    	  //平台端标的唯一标识
		eyeLoan.put("name",loan.getName());			  //标的名称
		eyeLoan.put("amount",new BigDecimal(loan.getTotalmoney()).toPlainString()); //标的金额,金额不能用科学计数法
		eyeLoan.put("rate",loan.getRate()*100); 	  //标的利率（百分数）,利率要乘以100
		eyeLoan.put("pay_way",switchRepayType(loan.getRepayType()));//标的还款方式  1等额本息 2先息后本 3到期还本息 4等额本金 5随存随取
		if(loan.getOperationType().equals("月")){
			eyeLoan.put("period",loan.getDeadline()); //标的期限 0 活期
			eyeLoan.put("period_type",1);			  //标的期限单位1月 2天
		}else{
			eyeLoan.put("period",loan.getDay()); 	  //标的期限 0 活期
			eyeLoan.put("period_type",2);			  //标的期限单位1月 2天
		}
		eyeLoan.put("reward",0);					  //投标奖励
		eyeLoan.put("reward_type",1);				  //奖励方式 1百分比/% 2金额/元
		eyeLoan.put("cost",0);						  //利息管理费:cost没有给0
		eyeLoan.put("release_time",loan.getCommitTime().getTime()/1000); //发标日期时间戳
		eyeLoan.put("project_type",exchangeStatus(loan.getType()));//项目类型 8其他,1 车贷,2房贷,3个人信用贷, 4中小企业贷, 5债权流转,6票据抵押,7优选理财
		eyeLoan.put("security_type",2);				  //保障方式 8其他,1平台自有资金,2平台风险准
		eyeLoan.put("status",exchangeLoanStatus(loan.getStatus()));					  //标的状态 1在投,2还款中,3正常还款, 4提前还款, 5下架 新增标的默认为1
		eyeLoan.put("send_time",System.currentTimeMillis()/1000); //请求时间戳
		System.out.println("补推 标***************************************************************");
		System.out.println(eyeLoan.toString());
		System.out.println("补推 标***************************************************************");
		return eyeLoan;
	}
	/**
	 * 与天眼标状态对比转换
	 * 标的状态 1在投,2还款中,3正常还款, 4提前还款, 5下架 
	 * 短融标状态：完成、流标、还款中、已投满、筹款中、等待复核、已筹满
	 * @param status
	 * @return
	 */
	private int exchangeLoanStatus(String status){
		switch(status){
			case "完成":return 3;
			case "流标":return 5;
			case "还款中":return 2;
			case "等待复核":return 2;
			case "已投满":return 2;
			case "已筹满":return 2;
			case "筹款中":return 1;
			case "贷前公告":return 1;
			default:return 5;
		}
	}

	
	
	
	
	
	
	private int switchRepayType(String repayType) {
		switch(repayType){
			case "按月付息到期还本":return 2;
			default:return 3;
		}
	}

	/**
	 * 转换天眼项目类型
	 * @param status
	 * 短融项目类型:
	 *  企业借款
	 *  车贷
	 *	房贷
	 *	企业贷
	 *	供应宝
	 *	金农宝
	 *	企业宝
	 *天眼项目类型 8其他,1 车贷,2房贷,3个人信用贷, 4中小企业贷, 5债权流转,6票据抵押,7优选理财
	 * @return
	 */
	private static int exchangeStatus(String status){
		int r;
		switch (status) {
			case "企业借款": r=4;
				break;
			case "车贷":r=1;
				break;
			case "房贷":r=2;
				break;
			case "企业贷":r=4;
				break;
			case "供应宝":r=8;
				break;
			case "金农宝":r=8;
				break;
			case "企业宝":r=4;
				break;
			default: r=8;
				break;
		}
		return r;
	}

	@Override
	public PushLoan readByLoanId(String loanId) {
		
		PushLoan  pushLoan = pushLoanMapper.selectByLoanId(loanId);
		return pushLoan;
	}

	 /**
     * 推送项目状态
     */
	/*@Override
	public Integer pushLoanStatusInfo(loan loanList) {
		
		Integer pushCode = 0;
		List<String> ids = new ArrayList<String>();
		for(PushLoan loan : loanList){
			//推送状态或项目状态是已完成则推送 5 下架
			if("完成".equals(loan.getStatus()) || 
			   "还款中".equals(loan.getStatus()) ||
			   "完成".equals(loan.getLoanStatus())||
			   "还款中".equals(loan.getLoanStatus())){
				JSONObject param = getPushLoanInfo(loan);
				System.out.println("推送项目状态："+param.toJSONString());
				pushCode = P2PEyeHttpUtil.sendPost(param,"loan");
				//4.添加交易记录
				if(pushCode != 200){
					return pushCode;
				}
				ids.add(loan.getId());
				System.out.println("项目状态推送成功："+pushCode);
			}
		}
		if(pushCode == 200){
			if(ids.size() > 0){
				pushLoanDao.updateStatus(ids); //批量更新状态
			}
		}
		return pushCode;
		
	}*/
	@Override
	public int pushNetLoanStatus(Loan loan) {
		
		
			Integer pushCode = 0;
			JSONObject param = getPushLoanStatusInfo(loan);
			System.out.println("推送项目状态："+param.toJSONString());
			pushCode = P2PEyeHttpUtilAgain.sendPost(param,"loan");
			//4.添加交易记录
			if(pushCode != 200){
				return pushCode;
			}
			System.out.println("项目状态推送成功："+pushCode);
		return pushCode;
	}
	/**
	 * 项目状态推送JSON
	 * @param loan
	 * @return
	 */
	public JSONObject getPushLoanStatusInfo(Loan loan){
		JSONObject json = new JSONObject();
		json.put("type",3);
		json.put("loan_id",loan.getId());
		json.put("status",exchangeLoanStatus(loan.getStatus()));
		json.put("send_time",System.currentTimeMillis()/11000);
		return json;
	}

	
	
}
