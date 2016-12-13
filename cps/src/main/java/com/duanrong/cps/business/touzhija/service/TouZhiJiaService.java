package com.duanrong.cps.business.touzhija.service;

import java.util.List;

import com.duanrong.cps.business.touzhija.model.BidInfo;
import com.duanrong.cps.business.touzhija.model.CreateUserReq;
import com.duanrong.cps.business.touzhija.model.InvestInfo;
import com.duanrong.cps.business.touzhija.model.LoginReq;
import com.duanrong.cps.business.touzhija.model.PlatformException;
import com.duanrong.cps.business.touzhija.model.QueryReq;
import com.duanrong.cps.business.touzhija.model.Redirect;
import com.duanrong.cps.business.touzhija.model.RepayInfo;
import com.duanrong.cps.business.touzhija.model.UserInfo;



/**
 * Created by architect of touzhijia on 2016/3/1.
 */
public interface TouZhiJiaService {
	
	// 查询用户信息
	public List<UserInfo> queryUser(QueryReq req) throws TouZhiJiaException;
	
	// 查询标的信息
	public List<BidInfo> queryBids(QueryReq req) throws TouZhiJiaException;
	
	// 查询投资记录
	public List<InvestInfo> queryInvests(QueryReq req) throws TouZhiJiaException;
	
	// 查询回款记录
	public List<RepayInfo> queryRepays(QueryReq req) throws TouZhiJiaException;
	
	// 创建新用户
	public UserInfo createUser(CreateUserReq req) throws PlatformException;
			
	// 绑定老用户，需要跳转到用户授权界面
	public Redirect bindUser(CreateUserReq req) throws PlatformException;
			
	// 登录，设置登录态并跳转到平台
	public Redirect login(LoginReq req) throws PlatformException;
}
