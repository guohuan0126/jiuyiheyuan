package com.duanrong.dataAnalysis.business.user.model;

import java.util.HashMap;
import java.util.Map;

public class UserDataView {

	//今日注册用户
		private int newAllUserCount;
		//今日开户用户
		private int newAllUserRegisterCount;
		//今日投资总数
		private int newInvestCount;
		
		public int getNewAllUserCount() {
			return newAllUserCount;
		}
		public void setNewAllUserCount(int newAllUserCount) {
			this.newAllUserCount = newAllUserCount;
		}
		public int getNewAllUserRegisterCount() {
			return newAllUserRegisterCount;
		}
		public void setNewAllUserRegisterCount(int newAllUserRegisterCount) {
			this.newAllUserRegisterCount = newAllUserRegisterCount;
		}
		public int getNewInvestCount() {
			return newInvestCount;
		}
		public void setNewInvestCount(int newInvestCount) {
			this.newInvestCount = newInvestCount;
		}

		

}
