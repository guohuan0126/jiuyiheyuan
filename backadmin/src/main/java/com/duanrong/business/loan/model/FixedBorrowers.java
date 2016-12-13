/**
 * 
 */
package com.duanrong.business.loan.model;

import com.duanrong.business.user.model.User;

/**
 * @author SunZ
 * 
 */
public class FixedBorrowers {
	private String userId;
	private User fixedUser;
	private Double availableBalance;
	private String status;
	private String email;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the fixedUser
	 */
	public User getFixedUser() {
		return fixedUser;
	}

	/**
	 * @param fixedUser
	 *            the fixedUser to set
	 */
	public void setFixedUser(User fixedUser) {
		this.fixedUser = fixedUser;
	}

	
	/**
	 * @return the availableBalance
	 */
	public Double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance the availableBalance to set
	 */
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FixedBorrowers [userId=" + userId + ", fixedUser=" + fixedUser
				+ ", availableBalance=" + availableBalance + ", status="
				+ status + "]";
	}

	
	
}
