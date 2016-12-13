package com.duanrong.business.loan.model;

import java.util.ArrayList;
import java.util.List;

import base.model.BaseModel;

public class Banner extends BaseModel {
	private static final long serialVersionUID = -2922694982610172393L;
	
	private String id;
	private String description;
	private List<BannerPicture> pictures = new ArrayList<BannerPicture>(0);

	/** default constructor */
	public Banner() {
	}

	/** full constructor */
	public Banner(String description, List<BannerPicture> bannerPictures) {
		this.description = description;
		this.pictures = bannerPictures;
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BannerPicture> getPictures() {
		return this.pictures;
	}

	public void setPictures(List<BannerPicture> bannerPictures) {
		this.pictures = bannerPictures;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Banner [id=" + id + ", description=" + description
				+ ", pictures=" + pictures + "]";
	}
	
	

}