package com.duanrong.business.invest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import util.Log;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.util.ArithUtil;

@Component
public class NationalInvestActive extends InvestActive {

	@Resource
	Log log;

	@Resource
	InvestService investService;

	private String remarks = "恭喜您获得“畅玩十一•投资秒返现”现金奖励#[money]元（#[loanName]，投资金额#[investMoney]元），您可登录短融网查询到账明细。";	

	@Override
	public double execute(Invest invest) {
		Date investTime = invest.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null, endDate = null;
		try {
			startDate = format.parse("2016-09-26 23:59:59");
			endDate = format.parse("2016-10-03 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		/**
		 * 活动开始
		 */
		log.infoLog("十一活动随即返现开始investId:" + invest.getId(), invest.toString());
		double awardMoney = 0;
		if (investTime != null && startDate.getTime() < investTime.getTime()
				&& investTime.getTime() < endDate.getTime()) {
			double investMoney = invest.getMoney();
			// 未使用红包
			if (invest.getRedpacketId() <= 0) {
				// 未使用红包
				if (investMoney >= 1000 && investMoney < 3000) {
					awardMoney = 1;
				} else if (investMoney >= 3000 && investMoney < 5000) {
					awardMoney = 2;
				} else if (investMoney >= 5000 && investMoney < 10000) {
					awardMoney = 4;
				} else if (investMoney >= 10000 && investMoney < 30000) {
					awardMoney = 8;
				} else if (investMoney >= 30000 && investMoney < 50000) {
					awardMoney = 20;
				} else if (investMoney >= 50000 && investMoney < 70000) {
					awardMoney = 40;
				} else if (investMoney >= 70000 && investMoney < 100000) {
					awardMoney = 50;
				} else if (investMoney >= 100000) {
					awardMoney = 60;
				}
			} else {
				// 使用红包，采用随机规则
				if (investMoney >= 1000 && investMoney < 10000) {
					awardMoney = randomAward1();
				} else if (investMoney >= 10000 && investMoney < 50000) {
					awardMoney = randomAward2();
				} else if (investMoney >= 50000) {
					awardMoney = 20;
				}
			}
			log.infoLog("十一活动随即返现开始investId:" + invest.getId(), "investMoney:"
					+ invest.getMoney() + ", awardMoney:" + awardMoney);
		}
		this.setMsg(remarks.replace("#[money]",
				ArithUtil.round(awardMoney, 0) + "").replace("#[loanName]",
				invest.getLoanName()).replace("#[investMoney]", ArithUtil.round(invest.getMoney(), 0)+""));
		this.setInfomation("恭喜您获得“畅玩十一•投资秒返现”现金奖励" + awardMoney
								+ "元，投资项目" + invest.getLoanName() + "，金额"
								+ ArithUtil.round(invest.getMoney(), 0)
								+ "元，现金奖励已发放到您的个人账户中，您可到交易记录里查询到账明细。");
		return awardMoney;
	}

	/**
	 * 简单离散算法生成（投资金额位于1000-10000）随机奖励金额
	 * 
	 * @return
	 */
	private double randomAward1() {
		int random = this.random();
		if (random <= 179) {
			return 1;
		} else if (random > 179 && random <= 193) {
			return 2;
		} else {
			return 3;
		}
	}

	/**
	 * 简单离散算法生成（投资金额位于10000-50000）随机奖励金额
	 * 
	 * @return
	 */
	private double randomAward2() {
		int random = this.random();
		if (random < 179) {
			return 5;
		} else {
			return 6;
		}
	}

	@Override
	String getActiveId() {
		return "NATIONAL";
	}

	@Override
	public String getTitle() {
		return "畅玩十一•投资秒返现";
	}
	
	
	

}
