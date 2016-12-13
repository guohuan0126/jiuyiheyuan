package com.duanrong.business.invest;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.PassThrough;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.util.ArithUtil;

@Service
public class DoubleElevenNewUserAward extends InvestActive{
	
	@Resource
	UserService userService;
	
	@Resource
	InvestService investService;

	@Override
	double execute(Invest invest) {
		double awardMoney = 0;
		String userId = invest.getInvestUserID();
		PassThrough passThrough = userService.getDoubleElevenPrize(userId);
		if(null != passThrough && null != passThrough.getRecommendedId() && passThrough.getWhetherNew().equals("1")){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			double investMoney = investService.getCurrentInvestMoney4DoubleElevec(userId,df.format(passThrough.getCreateTime()));
			double investMoney1 = investMoney - invest.getMoney();
			if(investMoney1 < 1000 && investMoney >= 1000){
				awardMoney = 11;
				PassThrough passThrough1 = userService.getDoubleElevenPrize(passThrough.getRecommendedId());
				invest.setInvestUserID(passThrough.getRecommendedId());
				Double rewardMoney = passThrough1.getRewardMoney();
				rewardMoney = rewardMoney + 11;
				passThrough1.setRewardMoney(rewardMoney);
				userService.update4DoubleElevenReward(passThrough1);
			}
			
		}
		return ArithUtil.round(awardMoney, 0);
	}

	@Override
	String getActiveId() {
		return "DoubleEleven";
	}

	@Override
	String getTitle() {
		return "DoubleEleven";
	}

}
