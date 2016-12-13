package com.duanrong.business.netloaneye.model;

import java.util.Date;

public class PushLoan {
    private String id;

    private String loanId;

    private Date pushTime;

    private String pushUserid;
    
    private String status;
    
    

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getPushUserid() {
        return pushUserid;
    }

    public void setPushUserid(String pushUserid) {
        this.pushUserid = pushUserid;
    }
}