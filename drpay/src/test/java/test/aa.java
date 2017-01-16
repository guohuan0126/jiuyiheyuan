package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.FastJsonUtil;
import base.exception.LoanException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.service.InvestService;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.service.LoanService;
import com.duanrong.drpay.jsonservice.param.LoanParameter;
import com.duanrong.drpay.trusteeship.helper.model.BizType;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorAsynDetailJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.service.TrusteeshipLoanService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTradeService;

public class aa {

	public static void main(String[] args) throws Exception {
		/*ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "beans-odbc.xml", "beans-aop.xml", "beans-common.xml", "beans-task.xml" });
		TrusteeshipTradeService s = ctx.getBean(TrusteeshipTradeService.class);
		
		
		s.giveMoneyToBorrower("0000000001");*/

		createDetail();
	}
	
	public static void createProject() throws UserAccountException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "beans-odbc.xml", "beans-aop.xml", "beans-common.xml", "beans-task.xml" });
		TrusteeshipLoanService s = ctx.getBean(TrusteeshipLoanService.class);
		LoanParameter p  = new LoanParameter();
		p.setLoanId("0000000001");
		p.setLoanName("0000000001");
		p.setRate(0.12);
		System.out.println(s.createProject(p).toString());
	}
	
	
	public static void createDetail() throws UserAccountException, UserInfoException, LoanException{
	
		List<GeneratorAsynDetailJSON> details = new ArrayList<>();
		
		GeneratorAsynDetailJSON p  = new GeneratorAsynDetailJSON();
		p.setAsyncRequestNo("dmsoIVSB886420170103181737647000001");
		p.setBizType(BizType.IP_TENDER);
		p.setCreateTime("20161227045201");
		p.setTransactionTime("20161227045201");
		p.setFailReason("操作成功");
		p.setFailCode("0");
		p.setStatus("SUCCESS");
		details.add(p);

		GeneratorAsynDetailJSON p2  = new GeneratorAsynDetailJSON();
		p2.setAsyncRequestNo("dmsoIVSB148220170103181737652000002");
		p2.setBizType(BizType.IP_TENDER);
		p2.setCreateTime("20161227045201");
		p2.setTransactionTime("20161227045201");
		p2.setFailReason("操作成功");
		p2.setFailCode("0");
		p2.setStatus("SUCCESS");
		details.add(p2);
		
		String a = FastJsonUtil.objToJson(details);
		
		System.out.println(a);
	}

}
