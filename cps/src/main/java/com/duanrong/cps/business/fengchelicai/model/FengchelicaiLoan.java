package com.duanrong.cps.business.fengchelicai.model;

public class FengchelicaiLoan {
 
	private Integer retcode; //返回状态码：0表示查询成功
	private String retmsg; //Retcode非0时候返回错误信息
	private String invest_id; //标的id
	private String invest_title;
	private String invest_url; //标的路径
	private Integer time_limit;  //期限123 折算成天
	private String time_limit_desc; //4个月3天
	private Double total_amount; //标的总额
	private Double rate; //年化利率 10% 则为10
	private Double progress; //进度 56 为56%
	private String  start_time; // 2015-05-06 10:10:00 可以投资起始时间
	private String payback_way ; //还款方式
	private String invest_condition; //投资条件新手标invest_condition:”新手”多个限制由-分割（新手-微信-app）
	private String project_description; //项目描述关于项目情况的描述
	private String lose_invest; //流标情况 默认：0, 0表示没有流标 1表示流标
	public Integer getRetcode() {
		return retcode;
	}
	public void setRetcode(Integer retcode) {
		this.retcode = retcode;
	}
	public String getRetmsg() {
		return retmsg;
	}
	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}
	public String getInvest_id() {
		return invest_id;
	}
	public void setInvest_id(String invest_id) {
		this.invest_id = invest_id;
	}
	public String getInvest_title() {
		return invest_title;
	}
	public void setInvest_title(String invest_title) {
		this.invest_title = invest_title;
	}
	public String getInvest_url() {
		return invest_url;
	}
	public void setInvest_url(String invest_url) {
		this.invest_url = invest_url;
	}
	public Integer getTime_limit() {
		return time_limit;
	}
	public void setTime_limit(Integer time_limit) {
		this.time_limit = time_limit;
	}
	public String getTime_limit_desc() {
		return time_limit_desc;
	}
	public void setTime_limit_desc(String time_limit_desc) {
		this.time_limit_desc = time_limit_desc;
	}
	public Double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getProgress() {
		return progress;
	}
	public void setProgress(Double progress) {
		this.progress = progress;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getPayback_way() {
		return payback_way;
	}
	public void setPayback_way(String payback_way) {
		this.payback_way = payback_way;
	}
	public String getInvest_condition() {
		return invest_condition;
	}
	public void setInvest_condition(String invest_condition) {
		this.invest_condition = invest_condition;
	}
	public String getProject_description() {
		return project_description;
	}
	public void setProject_description(String project_description) {
		this.project_description = project_description;
	}
	public String getLose_invest() {
		return lose_invest;
	}
	public void setLose_invest(String lose_invest) {
		this.lose_invest = lose_invest;
	}
	
	
	


}
