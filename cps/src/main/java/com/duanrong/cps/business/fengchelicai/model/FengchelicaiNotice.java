package com.duanrong.cps.business.fengchelicai.model;

import java.util.Date;

public class FengchelicaiNotice {

	//公告ID
	private String id;
	//公告标题
	private String title;
	//公告路径
	private String url;
	//公告内容
	private String content;
	//公告发布时间
	private String release_time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRelease_time() {
		return release_time;
	}
	public void setRelease_time(String release_time) {
		this.release_time = release_time;
	}
		
}
