package com.duanrong.thirdPartyInterface.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 网贷天眼获取投资列表接口json数据对应的实体类
 * 
 * @author 尹逊志
 * @date 2014-11-12下午4:19:02
 */
public class WDTYInvest {
	private String id;// 标的唯一编号
	private String useraddress;// 投标人所在城市
	private String username;// 用户名
	private String userid;// 用户编号
	private String link;// 标的链接
	private String type;// 投标方式例如：手动、自动
	private double money;// 投标金额
	private double account;// 有效金额投标金额实际生效部分
	private String status;// 投标状态例如：成功、部分成功、失败
	private String add_time;// 投标时间 格式 2014-09-09 10:13:00

	public String getId() {
		return id;
	}

	public String getUseraddress() {
		return useraddress;
	}

	public String getUsername() {
		return username;
	}

	public String getUserid() {
		return userid;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUseraddress(String useraddress) {
		this.useraddress = useraddress;
	}

	public void setUsername(String username) {
		String code = RandomStringUtils.random(6, false, true);
		username = DigestUtils.md5Hex(this.add_time + code + username);
		this.username = username;
	}

	public void setUserid(String userid) {
		String code = RandomStringUtils.random(6, false, true);
		username = DigestUtils.md5Hex(this.add_time + code + userid);
		this.userid = userid;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the money
	 */
	public double getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(double money) {
		this.money = money;
	}

	/**
	 * @return the account
	 */
	public double getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(double account) {
		this.account = account;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

}
