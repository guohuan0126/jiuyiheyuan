package com.duanrong.cps.business.xicaiwang.model;

public class XiCaiLoanInfo {

	//P2P平台产品唯一id
	private String p2p_product_id;
	//产品名称
	private String product_name;
	//是否为新手标，1为是，0为否。 默认为0(可选)
	private int isexp;
	//产品周期（天）, 如果为其他单位，请转换为天
	private int life_cycle;
	//年收益率(%)， 不要带 %
	private int ev_rate;
	//募集金额(元)
	private int amount;
	//已募集金额(元)
	private int invest_amount;
	//投资人数
	private int inverst_mans;
	//标的开始时间（yyyy-MM-dd）
	private String underlying_start;
	//标的结束时间（yyyy-MM-dd）(可选)
	private String underlying_end;
	//investSource=xicaiwang用来记录投资来源
	private String investSource;
	//产品募集状态 -1：流标，0：筹款中，1:已满标，2：已开始还款，3：预发布，4：还款完成，5：逾期
	private int product_state;
	//借款人名称
	private String borrower;
	//担保方式 0 无担保 1 本息担保 2 本金担保;
	private int guarant_mode;
	//担保方名称 第三方担保时，需提供担保方名称，否则置为空(可选)
//	private String guarantors;
	//发布时间（yyyy-MM-dd）;
	private String publish_time;
	//还款开始时间，满标时必须，未满标时没有可不需要（yyyy-MM-dd）;
	private String repay_start_time;
	//还款结束时间，满标时必须，未满标时没有可不需要（yyyy-MM-dd）
	private String repay_end_time;
	//	借款担保方式 1 抵押借款 2 信用借款 3 质押借款 4 第三方担保
	private int borrow_type;
	//还款方式 1 按月付息 到期还本 2 按季付息，到期还本3 每月等额本息 4 到期还本息 5 按周等额本息还款 6按周付息，到期还本 7：利随本清；8：等本等息；9：按日付息，到期还本；10：按半年付息，到期还本；11：按一年付息，到期还本；100：其他方式；
	private int pay_type;
	//	起投金额，单位元
	private int start_price;
	//追加投入金额，单位元(可选)
//	private String step_price;
	//手续费 (手续费说明文字)(可选)
//	private String charge;
	//可选
	private String mobile_link_website;
	//P2P产品活动信息
//	private String promotion;
	//活动说明
//	private String title;
	//活动类型(字数控制5个字以内)
//	private String type;
	//活动链接(必须以http://开始)
//	private String promotion_url;
	//过期时间
//	private String expiredtime;
	//是否参与活动(1:参与 0不参与)，系统默认为参与活动
//	private String activities;
	//
	private String link_website;
	public String getP2p_product_id() {
		return p2p_product_id;
	}
	public void setP2p_product_id(String p2p_product_id) {
		this.p2p_product_id = p2p_product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getIsexp() {
		return isexp;
	}
	public void setIsexp(int isexp) {
		this.isexp = isexp;
	}
	public int getLife_cycle() {
		return life_cycle;
	}
	public void setLife_cycle(int life_cycle) {
		this.life_cycle = life_cycle;
	}
	public int getEv_rate() {
		return ev_rate;
	}
	public void setEv_rate(int ev_rate) {
		this.ev_rate = ev_rate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getInvest_amount() {
		return invest_amount;
	}
	public void setInvest_amount(int invest_amount) {
		this.invest_amount = invest_amount;
	}
	public int getInverst_mans() {
		return inverst_mans;
	}
	public void setInverst_mans(int inverst_mans) {
		this.inverst_mans = inverst_mans;
	}
	public String getUnderlying_start() {
		return underlying_start;
	}
	public void setUnderlying_start(String underlying_start) {
		this.underlying_start = underlying_start;
	}
	public String getUnderlying_end() {
		return underlying_end;
	}
	public void setUnderlying_end(String underlying_end) {
		this.underlying_end = underlying_end;
	}
	public String getInvestSource() {
		return investSource;
	}
	public void setInvestSource(String investSource) {
		this.investSource = investSource;
	}
	public int getProduct_state() {
		return product_state;
	}
	public void setProduct_state(int product_state) {
		this.product_state = product_state;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public int getGuarant_mode() {
		return guarant_mode;
	}
	public void setGuarant_mode(int guarant_mode) {
		this.guarant_mode = guarant_mode;
	}
//	public String getGuarantors() {
//		return guarantors;
//	}
//	public void setGuarantors(String guarantors) {
//		this.guarantors = guarantors;
//	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public String getRepay_start_time() {
		return repay_start_time;
	}
	public void setRepay_start_time(String repay_start_time) {
		this.repay_start_time = repay_start_time;
	}
	public String getRepay_end_time() {
		return repay_end_time;
	}
	public void setRepay_end_time(String repay_end_time) {
		this.repay_end_time = repay_end_time;
	}
	public int getBorrow_type() {
		return borrow_type;
	}
	public void setBorrow_type(int borrow_type) {
		this.borrow_type = borrow_type;
	}
	public int getPay_type() {
		return pay_type;
	}
	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}
	public int getStart_price() {
		return start_price;
	}
	public void setStart_price(int start_price) {
		this.start_price = start_price;
	}
//	public String getStep_price() {
//		return step_price;
//	}
//	public void setStep_price(String step_price) {
//		this.step_price = step_price;
//	}
//	public String getCharge() {
//		return charge;
//	}
//	public void setCharge(String charge) {
//		this.charge = charge;
//	}
	public String getMobile_link_website() {
		return mobile_link_website;
	}
	public void setMobile_link_website(String mobile_link_website) {
		this.mobile_link_website = mobile_link_website;
	}
//	public String getPromotion() {
//		return promotion;
//	}
//	public void setPromotion(String promotion) {
//		this.promotion = promotion;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public String getPromotion_url() {
//		return promotion_url;
//	}
//	public void setPromotion_url(String promotion_url) {
//		this.promotion_url = promotion_url;
//	}
//	public String getExpiredtime() {
//		return expiredtime;
//	}
//	public void setExpiredtime(String expiredtime) {
//		this.expiredtime = expiredtime;
//	}
//	public String getActivities() {
//		return activities;
//	}
//	public void setActivities(String activities) {
//		this.activities = activities;
//	}
	public String getLink_website() {
		return link_website;
	}
	public void setLink_website(String link_website) {
		this.link_website = link_website;
	}
	
	
}
