package com.duanrong.business.netloaneye.model;

import java.util.Date;

/**
 * 交易记录推送Model
 * @author Today
 *
 */
public class PushInfoRow {
    
	private String mobile;   //手机号
	private String loan_id;  //项目ID
	private String userkey;  //用户绑定标识
	private String order_id; //平台交易订单唯一标识
	private Double amount;  //交易金额
	private Date trade_time; //交易时间
	private Double cost;    //利息管理费/百分比
	private String userId;  //用户ID
	private Integer whetherNew;  //是否为天眼用户，0是，1不是
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(String loan_id) {
		this.loan_id = loan_id;
	}
	public String getUserkey() {
		return userkey;
	}
	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(Date trade_time) {
		this.trade_time = trade_time;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getWhetherNew() {
		return whetherNew;
	}
	public void setWhetherNew(Integer whetherNew) {
		this.whetherNew = whetherNew;
	}
	
	@Override
	public String toString() {
		return "PushInfoRow [mobile=" + mobile + ", loan_id=" + loan_id
				+ ", userkey=" + userkey + ", order_id=" + order_id
				+ ", amount=" + amount + ", trade_time=" + trade_time
				+ ", cost=" + cost + ", userId=" + userId + ", whetherNew="
				+ whetherNew + "]";
	}
    
	
}
