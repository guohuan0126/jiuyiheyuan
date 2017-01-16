  package com.duanrong.business.invest;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.PassThrough;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.service.UserService;
import com.duanrong.util.ArithUtil;

@Service
public class InvitationActiveAward extends InvestActive{

	@Resource
	UserService userService;
	
	@Resource
	InvestService investService;
	
	@Resource
	UserDao userDao;
	
	@Override
	double execute(Invest invest) {
		System.out.println("#####################邀请活动发奖励");
		double awardMoney = 0;
		String userId = invest.getInvestUserID();  //投资人userID
		System.out.println("##############邀请活动发奖励：投资人userId:"+userId);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("type","invatation01");
		PassThrough passThrough = userDao.getPassThroughByUserId(param);  //pass_through表中对应的userID
		if(null != passThrough && null != passThrough.getRecommendedId() && !"".equals(passThrough.getRecommendedId())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			double investMoney = investService.getCurrentInvestMoney4DoubleElevec(userId,df.format(passThrough.getCreateTime()));  //当前投资人的累计投资额
			String invatationUserId=userService.getUserByMobileNumber(passThrough.getRecommendedId()).getUserId();
			Map<String,Object> param1=new HashMap<String,Object>();
			param1.put("userId", invatationUserId);
			param1.put("type", "invatation01");
			PassThrough passThrough1 =  userDao.getPassThroughByUserId(param1);
			Integer newUserReward=passThrough.getNewUserReward();  
			invest.setInvestUserID(invatationUserId);  //给邀请用户的用户发奖励
			String phone=passThrough.getMobileNumber();
			String scretPhone=phone.substring(0, 3)+"****"+phone.substring(7);
			if(investMoney>=1000 && investMoney<10000 && newUserReward==0){ //投资人投满1000元，并没有发放过奖励.测试投2元给0.2,投10元给0.5
				awardMoney=20;
				passThrough.setNewUserReward(1);
				passThrough1.setRewardMoney(passThrough1.getRewardMoney()+20);
				this.setInfomation("恭喜您获得“邀请好友，拿千元现金”活动20元现金奖励，奖励已发放到您的个人账户中，您可到交易记录里查询到账明细");
				userService.updatePassThrough(passThrough1); 
				passThrough.setInvestMoney(investMoney);
				userService.updatePassThrough(passThrough);
			}else if(investMoney>=10000 && newUserReward==1 ){ //投资人投满10000元，并只发过20元的奖励，则再发50.测试投满10元并发过0.2元，再发0.5元
				awardMoney=50;
				passThrough.setNewUserReward(2);
				passThrough1.setRewardMoney(passThrough1.getRewardMoney()+50);
				this.setInfomation("您邀请的好友"+scretPhone+"，投资指定产品已满10000，特额外奖励您50元，奖励已发放到您的个人账户中，您可到交易记录里查询到账明细");
				userService.updatePassThrough(passThrough1); 
				passThrough.setInvestMoney(investMoney);
				userService.updatePassThrough(passThrough);
			}else if(investMoney>=10000 && newUserReward==0){  //投资人投满10000元并没有发过20元奖励，再发70元
				awardMoney=70;
				passThrough.setNewUserReward(2);
				passThrough1.setRewardMoney(passThrough1.getRewardMoney()+70);
				this.setInfomation("恭喜您获得“邀请好友，拿千元现金”活动20元现金奖励，奖励已发放到您的个人账户中，您可到交易记录里查询到账明细",
						"您邀请的好友"+scretPhone+"，投资指定产品已满10000，特额外奖励您50元，奖励已发放到您的个人账户中，您可到交易记录里查询到账明细");
				userService.updatePassThrough(passThrough1); 
				passThrough.setInvestMoney(investMoney);
				userService.updatePassThrough(passThrough);
			}
			
	}
		System.out.println("#############awardMoney:"+awardMoney);
		return ArithUtil.round(awardMoney, 2);
}
	@Override
	String getActiveId() {
		// TODO Auto-generated method stub
		return "invitation01";
	}

	@Override
	String getTitle() {
		
		return "邀请活动";
	}

	
	public static void main(String[] args){
		String phone="18210132758";
		//System.out.println(phone.substring(0, 3));
		//System.out.println(phone.substring(7,4));
		String scretPhone=phone.substring(0, 3)+"****"+phone.substring(7);
		System.out.println(scretPhone);
	}
}
