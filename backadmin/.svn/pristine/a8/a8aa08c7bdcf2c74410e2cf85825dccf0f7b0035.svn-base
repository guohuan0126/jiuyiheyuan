package com.duanrong.business.loan.model;

import java.util.ArrayList;
import java.util.List;

import base.model.BaseModel;

public class BannerPicture extends BaseModel {
	private static final long serialVersionUID = 3427262111338562451L;
	
	private String id;
//	private Banner banner;
	private String title;
	private String url;
	private Integer seqNum;
	/**
	 * 是否为外链
	 */
	private Boolean isOutSite;
	private String picture;
	
	private List<Loan> loans=new ArrayList<Loan>(0);
	
	private List<Loan> loanInfoPics = new ArrayList<Loan>(0);
	
	private List<Loan> guaranteeInfoPics = new ArrayList<Loan>(0);

	/** default constructor */
	public BannerPicture() {
	}

	/** full constructor */
	public BannerPicture(Banner banner, String picture) {
//		this.banner = banner;
		this.picture = picture;
	}

//	public Banner getBanner() {
//		return this.banner;
//	}

	public String getId() {
		return this.id;
	}

	public Boolean getIsOutSite() {
		return isOutSite;
	}

	public String getPicture() {
		return this.picture;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public Integer getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

//	public void setBanner(Banner product) {
//		this.banner = product;
//	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsOutSite(Boolean isOutSite) {
		this.isOutSite = isOutSite;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}
	public List<Loan> getLoanInfoPics() {
		return loanInfoPics;
	}

	public void setLoanInfoPics(List<Loan> loanInfoPics) {
		this.loanInfoPics = loanInfoPics;
	}

	public List<Loan> getGuaranteeInfoPics() {
		return guaranteeInfoPics;
	}

	public void setGuaranteeInfoPics(List<Loan> guaranteeInfoPics) {
		this.guaranteeInfoPics = guaranteeInfoPics;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BannerPicture [id=" + id + ", title=" + title + ", url=" + url
				+ ", seqNum=" + seqNum + ", isOutSite=" + isOutSite
				+ ", picture=" + picture + ", loans=" + loans
				+ ", loanInfoPics=" + loanInfoPics + ", guaranteeInfoPics="
				+ guaranteeInfoPics + "]";
	}
	
	
	
}