package com.duanrong.business.repay;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.Log;
import util.RedisCacheKey;

import com.duanrong.business.node.model.Node;
import com.duanrong.business.node.service.NodeService;
import com.duanrong.business.repay.model.RepayMail;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.user.model.Template;
import com.duanrong.business.user.service.UserService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.IdUtil;
import com.duanrong.util.jedis.DRJedisCacheUtil;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;

/**
 * 还款 补息和加息奖励轮询发送
 * 
 * @author xiao
 * @time 2015/8/29
 */

@Component
public class RepaySchedule {

	@Resource
	Log log;

	@Resource
	RepayService repayService;

	@Resource
	UserService userService;

	@Resource
	NodeService nodeService;
	
	
	/*@Scheduled(cron = "0 0 8 * * ?")*/
	public void sendRepayRemindMail(){		
		List<RepayMail> userIds = repayService.getInterval(3);
		Template t1 = userService.getTemplateById("repay_email_header");
		Template t2 = userService.getTemplateById("repay_email_data");
		Template t3 = userService.getTemplateById("repay_email_footer");
		String header = t1 == null ? "" : t1.getContent();
		String footer = t3 == null ? "" : t3.getContent();
		
		String host = userService.getConfigById("mail_smtp").getValue();
		String from = userService.getConfigById("mail_username").getValue();
		String password = userService.getConfigById("mail_password").getValue();
		for(RepayMail rm : userIds){			
			String data = t2 == null ? "" : t2.getContent();
			String headerd = header.replace("#{userId}", rm.getUserId()+"--"+rm.getRealName());
			String rd = "";
			for(int i = 0; i<= 2; i++){
				String s = replaceData(data, rm.getUserId(), i);
				rd+=s;
			}			
			data = headerd+rd+footer;
			
			MailSendInfo  sendInfo = new MailSendInfo();
			sendInfo.setFromAddress(from);
			sendInfo.setMailServerHost(host);
			sendInfo.setPassword(password);
			sendInfo.setUserName(from);
			sendInfo.setContent(data);
			sendInfo.setSubject("还款提醒");
			sendInfo.setToAddress(rm.getEmail());			
			SimpleEmailSend send = new SimpleEmailSend();
			try {
				send.sendHtmlMail(sendInfo);
				data = "";
			} catch (MessagingException e) {
				e.printStackTrace();
				log.errLog("邮件发送失败", e);
			}
		}
		
		
	}
	
	
	private String replaceData(String data, String userId, int day){	
		List<RepayMail> repayMails = repayService.getRepayInterval(userId, day);
		if(day==0){
			data = data.replace("#{title}", "今日还款项目");
		}else if(day == 1){
			data = data.replace("#{title}", "明日还款项目");
		}else if(day == 2){
			data = data.replace("#{title}", "后天还款项目");
		}				
		String s = "";
		double total = 0;
		for(RepayMail repayMail : repayMails){
			
			String p = "";
			if(repayMail.getOperationType().equals("月")){
				p += repayMail.getDeadline()+"个月";
			}else{
				p += repayMail.getDay()+"天";
			}
			String loanMoney = repayMail.getLoanMoney() > 10000 ? ArithUtil.round(repayMail.getLoanMoney()/10000, 1)+"（万元）" : ArithUtil.round(repayMail.getLoanMoney(), 1) + "（元）";
			String corpus = repayMail.getCorpus() > 10000 ? ArithUtil.round(repayMail.getCorpus()/10000, 2)+"（万元）" : ArithUtil.round(repayMail.getCorpus(), 2) + "（元）";
			String interest = repayMail.getInterest() > 10000 ? ArithUtil.round(repayMail.getInterest()/10000, 2)+"（万元）" : ArithUtil.round(repayMail.getInterest(), 2) + "（元）";
			String money = repayMail.getCorpus() + repayMail.getInterest() > 10000 ? ArithUtil.round((repayMail.getCorpus()+repayMail.getInterest())/10000, 2)+"（万元）" : ArithUtil.round(repayMail.getCorpus()+repayMail.getInterest(), 2) + "（元）";
			total += repayMail.getCorpus() + repayMail.getInterest();
			String str = "<tr>"
					+ "<td><a href=\"https://www.duanrong.com/loanDetail/"+repayMail.getLoanId()+"\">"+repayMail.getLoanName()+"</a></td>"
					+ "<td>"+repayMail.getLoanType()+"</td><td>"+p+"</td>"
					+ "<td>"+ArithUtil.round(repayMail.getRate()*100, 1)+"%</td><td>"+loanMoney+"</td><td>"+ repayMail.getRepayType() +"</td>"
					+ "<td>"+repayMail.getPeriod()+"</td><td>"+corpus+"</td><td>"+interest+"</td>"
					+ "<td>"+money+"</td><td>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(repayMail.getRepayDay())+"</td></tr>";
			s += str;		
		}
		String totalM = total > 10000 ? ArithUtil.round(total/10000, 2)+"（万元）" : ArithUtil.round(total, 2) + "（元）";
		s += "<tr><td colspan=\"10\" style=\"text-align: right; padding-right: 10px\">本日还款数量："+repayMails.size()+"</td><td>本日还款金额："+totalM+"</td></tr>";
		data = data.replace("#{data}", s);
		return data;
	}
	
	/*@Scheduled(cron = "0 0 4 * * ?")*/
	void sendRepayNotice(){
		log.infoLog("项目公告轮询", "项目公告轮询发送开始......"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		List<RepayMail> rms = repayService.getRepayFinishInterval(-1);
		Template t = userService.getTemplateById("repay_notice");
		String tm = t == null ? "" : t.getContent();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		int i = 1;
		log.infoLog("项目公告轮询", "昨天共完成"+rms.size()+"个项目");
		DecimalFormat df = new DecimalFormat("####.##");
		for(RepayMail rm : rms){
			
			String notice = tm;
			notice = notice.replace("#{loanName}", rm.getLoanName());
			notice = notice.replace("#{operateTime}", format.format(rm.getOperationTime()));
			String corpus = rm.getCorpus()/10000 > 1 ? df.format(rm.getCorpus()/10000)+"万元" : df.format(rm.getCorpus())+"元";
			notice = notice.replace("#{corpus}", corpus);
			String interest = rm.getInterest()/10000 > 1 ? ArithUtil.round(rm.getInterest()/10000, 2)+"万元" : ArithUtil.round(rm.getInterest(), 2)+"元";
			notice = notice.replace("#{interest}", interest);
			notice = notice.replace("#{time}", format.format(new Date()));
			
			Node node = new Node();
			node.setId(IdUtil.randomUUID());
			String j = i < 10 ? "0"+i : ""+i;
			String title = "【结束公告】"+format.format(rm.getOperationTime())+"项目结束公告"+j;		
			node.setTitle(title);
			node.setContent(notice);
			node.setCreateTime(new Date());
			node.setCreator("admin");
			node.setUpdateTime(new Date());
			node.setLastModifyUser("admin");
			node.setStatus(""+1);
			node.setTerm("项目公告");
			node.setSeqNum(0);
			try{
				nodeService.insert(node);
			}catch(Exception e){
				e.printStackTrace();
				log.errLog("项目公告轮询", e);
			}	
			i++;
			try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				
			}
		}
		DRJedisCacheUtil.del(RedisCacheKey.NOTICES_LOAN);
		log.infoLog("项目公告轮询", "项目公告轮询发送结束......"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}

}
