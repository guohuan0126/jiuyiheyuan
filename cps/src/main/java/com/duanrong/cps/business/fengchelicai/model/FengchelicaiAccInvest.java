package com.duanrong.cps.business.fengchelicai.model;

import java.math.BigDecimal;
import java.util.Date;

public class FengchelicaiAccInvest {

	//投资时间
	private Date invest_time;
	//投资金额
	private Double invest_money;
	//已回款本金
	private Double all_back_principal;
	//已回款利息
	private Double all_back_interest;
	//投资奖励
	private BigDecimal invest_reward;
	//投资记录id
	private String invest_record_id;
	//项目标题
	private String project_title;
	//项目id
	private String project_id;
	//项目url
	private String project_url;
	//项目利率
	private float project_rate;
	//项目期限描述(折算天)
	private int project_timelimit;
	//项目期限描述(如：4个月)
	private String project_timelimit_desc;
	//项目期限描述
	private int invest_status;
	//每月回款日
	private String monthly_back_date;
	//下一回款日
	private String next_back_date;
	//下一回款金额
	private Double next_back_money;
	//下一回款本金
	private Double next_back_principal;
	//下一回款利息
	private Double next_back_interest;
	//还款方式
	private String payback_way;
	//转让状态
	private int attorn_state;
	//转让时间
	private Date attorn_time;

	public Date getInvest_time() {
		return invest_time;
	}

	public void setInvest_time(Date invest_time) {
		this.invest_time = invest_time;
	}

	public Double getInvest_money() {
		return invest_money;
	}

	public void setInvest_money(Double invest_money) {
		this.invest_money = invest_money;
	}

	public Double getAll_back_principal() {
		return all_back_principal;
	}

	public void setAll_back_principal(Double all_back_principal) {
		this.all_back_principal = all_back_principal;
	}

	public Double getAll_back_interest() {
		return all_back_interest;
	}

	public void setAll_back_interest(Double all_back_interest) {
		this.all_back_interest = all_back_interest;
	}

	public BigDecimal getInvest_reward() {
		return invest_reward;
	}

	public void setInvest_reward(BigDecimal invest_reward) {
		this.invest_reward = invest_reward;
	}

	public String getInvest_record_id() {
		return invest_record_id;
	}

	public void setInvest_record_id(String invest_record_id) {
		this.invest_record_id = invest_record_id;
	}

	public String getProject_title() {
		return project_title;
	}

	public void setProject_title(String project_title) {
		this.project_title = project_title;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getProject_url() {
		return project_url;
	}

	public void setProject_url(String project_url) {
		this.project_url = project_url;
	}

	public float getProject_rate() {
		return project_rate;
	}

	public void setProject_rate(float project_rate) {
		this.project_rate = project_rate;
	}

	public int getProject_timelimit() {
		return project_timelimit;
	}

	public void setProject_timelimit(int project_timelimit) {
		this.project_timelimit = project_timelimit;
	}

	public String getProject_timelimit_desc() {
		return project_timelimit_desc;
	}

	public void setProject_timelimit_desc(String project_timelimit_desc) {
		this.project_timelimit_desc = project_timelimit_desc;
	}

	public int getInvest_status() {
		return invest_status;
	}

	public void setInvest_status(int invest_status) {
		this.invest_status = invest_status;
	}

	public String getMonthly_back_date() {
		return monthly_back_date;
	}

	public void setMonthly_back_date(String monthly_back_date) {
		this.monthly_back_date = monthly_back_date;
	}

	public String getNext_back_date() {
		return next_back_date;
	}

	public void setNext_back_date(String next_back_date) {
		this.next_back_date = next_back_date;
	}

	public Double getNext_back_money() {
		return next_back_money;
	}

	public void setNext_back_money(Double next_back_money) {
		this.next_back_money = next_back_money;
	}

	public Double getNext_back_principal() {
		return next_back_principal;
	}

	public void setNext_back_principal(Double next_back_principal) {
		this.next_back_principal = next_back_principal;
	}

	public Double getNext_back_interest() {
		return next_back_interest;
	}

	public void setNext_back_interest(Double next_back_interest) {
		this.next_back_interest = next_back_interest;
	}

	public String getPayback_way() {
		return payback_way;
	}

	public void setPayback_way(String payback_way) {
		this.payback_way = payback_way;
	}

	public int getAttorn_state() {
		return attorn_state;
	}

	public void setAttorn_state(int attorn_state) {
		this.attorn_state = attorn_state;
	}

	public Date getAttorn_time() {
		return attorn_time;
	}

	public void setAttorn_time(Date attorn_time) {
		this.attorn_time = attorn_time;
	}
	
}
