package com.duanrong.newadmin.controllhelper;

import java.util.Date;

import com.duanrong.newadmin.utility.DateUtil;

public class LoanDetailUrl {

    public static String getLoanUrl(String loanId, String loanType,
	    String createTime) {
	String type = "";
	if ("车贷".equals(loanType)) {
	    type = "vehicle";
	} else if ("房贷".equals(loanType)) {
	    type = "house";
	} else if ("企业贷".equals(loanType)) {
	    type = "enterprise";
	}
	Date time = DateUtil.StringToDate(createTime.substring(0, 10).replace("-", "/"));
	Date splitDate = DateUtil.StringToDate("2015/1/9");
	if (time.before(splitDate)) {
	    return "/loan/" + loanId;
	} else {
	    return "/loanDetail/" + type + "?loanId=" + loanId + "&all=dr";
	}
    }
}
