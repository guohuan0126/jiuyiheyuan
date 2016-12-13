package com.duanrong.business.ruralfinance.model;

public class LoanerinfoExport {

	//uuid 主键
		private String id;
		//合同编号
		private String contractId;
		//借款人姓名
		private String name;
		
		//身份证号
		private String idCard;
		//手机号
		private String mobileNumber;
		//银行卡号
		private String bankcard;
		//所属银行
		private String bank;
		//支行名称
		private String branchname;				
		//证件类型 1为身份证0为护照
		private String idType;
		//机构ID
		private String organizationID;
		
		private String customerType;
		
		/**
		 * @return the organizationID
		 */
		public String getOrganizationID() {
			return organizationID;
		}
		/**
		 * @param organizationID the organizationID to set
		 */
		public void setOrganizationID(String organizationID) {
			this.organizationID = organizationID;
		}
		/**
		 * @return the customerType
		 */
		public String getCustomerType() {
			return customerType;
		}
		/**
		 * @param customerType the customerType to set
		 */
		public void setCustomerType(String customerType) {
			this.customerType = customerType;
		}
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the contractId
		 */
		public String getContractId() {
			return contractId;
		}
		/**
		 * @param contractId the contractId to set
		 */
		public void setContractId(String contractId) {
			this.contractId = contractId;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the idCard
		 */
		public String getIdCard() {
			return idCard;
		}
		/**
		 * @param idCard the idCard to set
		 */
		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		/**
		 * @return the mobileNumber
		 */
		public String getMobileNumber() {
			return mobileNumber;
		}
		/**
		 * @param mobileNumber the mobileNumber to set
		 */
		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}
		/**
		 * @return the bankcard
		 */
		public String getBankcard() {
			return bankcard;
		}
		/**
		 * @param bankcard the bankcard to set
		 */
		public void setBankcard(String bankcard) {
			this.bankcard = bankcard;
		}
		/**
		 * @return the bank
		 */
		public String getBank() {
			return bank;
		}
		/**
		 * @param bank the bank to set
		 */
		public void setBank(String bank) {
			this.bank = bank;
		}
		/**
		 * @return the branchname
		 */
		public String getBranchname() {
			return branchname;
		}
		/**
		 * @param branchname the branchname to set
		 */
		public void setBranchname(String branchname) {
			this.branchname = branchname;
		}
		/**
		 * @return the idType
		 */
		public String getIdType() {
			return idType;
		}
		/**
		 * @param idType the idType to set
		 */
		public void setIdType(String idType) {
			this.idType = idType;
		}
		
			
}
