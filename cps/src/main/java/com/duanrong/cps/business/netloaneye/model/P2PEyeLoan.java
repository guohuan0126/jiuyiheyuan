package com.duanrong.cps.business.netloaneye.model;
/**
 * 天眼标推送Model
 * @author 
 *
 */
public class P2PEyeLoan {

	private int type;  //操作类型，提交标的固定为1
	private String loan_id; //平台端标的唯一标识
	private String name;  //标的名称
	private float amount; //标的金额
	private float rate; //标的利率（百分数）
	
	private int pay_way; //标的还款方式  1等额本息 2先息后本 3到期还本息 4等额本金 5随存随取
	private int period; //标的期限 0 活期
	private int period_type; //标的期限单位1月 2天
	private float reward;  //投标奖励
	private int reward_type; //奖励方式 1百分比/% 2金额/元
	
	private float cost; //利息管理费
	private long release_time; //发标日期时间戳
	private int project_type; //项目类型 8其他,1 车贷,2房贷,3个人信用贷, 4中小企业贷, 5债权流转,6票据抵押,7优选理财
	private int security_type; //保障方式 8其他,1平台自有资金,2平台风险准
	private int status;  //标的状态 1在投,2还款中,3正常还款, 4提前还款, 5下架 新增标的默认为1
	
	private long send_time;  //请求时间戳
	private long start_time; //【可选的】预计起息时间
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(String loan_id) {
		this.loan_id = loan_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public int getPay_way() {
		return pay_way;
	}
	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getPeriod_type() {
		return period_type;
	}
	public void setPeriod_type(int period_type) {
		this.period_type = period_type;
	}
	public float getReward() {
		return reward;
	}
	public void setReward(float reward) {
		this.reward = reward;
	}
	public int getReward_type() {
		return reward_type;
	}
	public void setReward_type(int reward_type) {
		this.reward_type = reward_type;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public long getRelease_time() {
		return release_time;
	}
	public void setRelease_time(long release_time) {
		this.release_time = release_time;
	}
	public int getProject_type() {
		return project_type;
	}
	public void setProject_type(int project_type) {
		this.project_type = project_type;
	}
	public int getSecurity_type() {
		return security_type;
	}
	public void setSecurity_type(int security_type) {
		this.security_type = security_type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getSend_time() {
		return send_time;
	}
	public void setSend_time(long send_time) {
		this.send_time = send_time;
	}
	public long getStart_time() {
		return start_time;
	}
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
	
	
}
