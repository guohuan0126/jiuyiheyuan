package com.duanrong.business.award.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import base.model.BaseModel;

import com.duanrong.business.flow.model.ItemFlow;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.user.model.User;

/**
 * @author xiao
 * @date 2015年2月3日 上午10:12:59
 */
public class AwardItem extends BaseModel{

	
	private static final long serialVersionUID = 4477496164447886863L;

	private int id;
	private String approvMsg;
	private Date approveTime;
	private String approveUserID;	
	private String createUserID;
	private Date createTime;
	private String description;
	private String itemType;
	private double money;
	private double moneyAmount; //奖励总金额
	private double sendMoney; //实际发送金额
	private int sendCount; //实际发送人数
	private String moneyType;
	private String name;
	private String status;
	private int userCount;
	private String userMsg;
	private String loanId;
	private String recheckMessage;	
	private Date recheckTime;
	private String recheckUser;
	private String alterCause;
	private Date alterTime;
	private String alterUserID;
	private double maxAwardMoneyRestrict;
	private String sendVerifyCode;
	private double percentageRate;
	private User user;
	private Loan loan;
	private String loanName;
	private int FlowId;
	private ItemFlow itemFlow; //项目当前步骤
	
	
	private List<AwardItemUser> AwardItemUsers; //项目明细
	private List<ItemFlow> itemFlows = new ArrayList<ItemFlow>(); //项目
	//导出专用
	private String loanType;
	private String itmeAddress;
	private Date sendTime;
				
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getItmeAddress() {
		return itmeAddress;
	}
	public void setItmeAddress(String itmeAddress) {
		this.itmeAddress = itmeAddress;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public int getSendCount() {
		return sendCount;
	}
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
	
	public List<ItemFlow> getItemFlows() {
		return itemFlows;
	}
	public void setItemFlows(List<ItemFlow> itemFlows) {
		this.itemFlows = itemFlows;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public Loan getLoan() {
		return loan;
	}
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getApprovMsg() {
		return approvMsg;
	}
	public void setApprovMsg(String approvMsg) {
		this.approvMsg = approvMsg;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public String getApproveUserID() {
		return approveUserID;
	}
	public void setApproveUserID(String approveUserID) {
		this.approveUserID = approveUserID;
	}
	public String getCreateUserID() {
		return createUserID;
	}
	public void setCreateUserID(String createUserID) {
		this.createUserID = createUserID;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getMoneyAmount() {
		return moneyAmount;
	}
	public void setMoneyAmount(double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public String getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getRecheckMessage() {
		return recheckMessage;
	}
	public void setRecheckMessage(String recheckMessage) {
		this.recheckMessage = recheckMessage;
	}
	public Date getRecheckTime() {
		return recheckTime;
	}
	public void setRecheckTime(Date recheckTime) {
		this.recheckTime = recheckTime;
	}
	public String getRecheckUser() {
		return recheckUser;
	}
	public void setRecheckUser(String recheckUser) {
		this.recheckUser = recheckUser;
	}
	public String getAlterCause() {
		return alterCause;
	}
	public void setAlterCause(String alterCause) {
		this.alterCause = alterCause;
	}
	public Date getAlterTime() {
		return alterTime;
	}
	public void setAlterTime(Date alterTime) {
		this.alterTime = alterTime;
	}
	public String getAlterUserID() {
		return alterUserID;
	}
	public void setAlterUserID(String alterUserID) {
		this.alterUserID = alterUserID;
	}
	public double getMaxAwardMoneyRestrict() {
		return maxAwardMoneyRestrict;
	}
	public void setMaxAwardMoneyRestrict(double maxAwardMoneyRestrict) {
		this.maxAwardMoneyRestrict = maxAwardMoneyRestrict;
	}
	public String getSendVerifyCode() {
		return sendVerifyCode;
	}
	public void setSendVerifyCode(String sendVerifyCode) {
		this.sendVerifyCode = sendVerifyCode;
	}
	public double getPercentageRate() {
		return percentageRate;
	}
	public void setPercentageRate(double percentageRate) {
		this.percentageRate = percentageRate;
	}
	public List<AwardItemUser> getAwardItemUsers() {
		if(AwardItemUsers == null){
			return new ArrayList<AwardItemUser>();
		}
		return AwardItemUsers;
	}
	public void setAwardItemUsers(List<AwardItemUser> awardItemUsers) {
		AwardItemUsers = awardItemUsers;
	}
	public int getFlowId() {
		return FlowId;
	}
	public void setFlowId(int flowId) {
		FlowId = flowId;
	}
	public ItemFlow getItemFlow() {
		return itemFlow;
	}
	public void setItemFlow(ItemFlow itemFlow) {
		this.itemFlow = itemFlow;
	}
	public double getSendMoney() {
		return sendMoney;
	}
	public void setSendMoney(double sendMoney) {
		this.sendMoney = sendMoney;
	}
	
	
	
}
