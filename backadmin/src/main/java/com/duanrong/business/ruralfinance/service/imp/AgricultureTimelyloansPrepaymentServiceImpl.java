package com.duanrong.business.ruralfinance.service.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.OssUtil;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONArray;
import com.duanrong.business.demand.dao.DemandTreasureLoanDao;
import com.duanrong.business.demand.service.DemandTreasureLoanService;
import com.duanrong.business.ruralfinance.dao.AgricultureLoanerInfoDao;
import com.duanrong.business.ruralfinance.dao.AgricultureRepaymentPlanDao;
import com.duanrong.business.ruralfinance.dao.AgricultureTimelyloansPrepaymentDao;
import com.duanrong.business.ruralfinance.model.AgricultureDebitRecords;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;
import com.duanrong.business.ruralfinance.model.AgricultureTimelyloansPrepayment;
import com.duanrong.business.ruralfinance.model.UploadPrepaymentUser;
import com.duanrong.business.ruralfinance.model.UploadZhongjinUser;
import com.duanrong.business.ruralfinance.service.AgricultureLoanerInfoService;
import com.duanrong.business.ruralfinance.service.AgricultureRepaymentPlanService;
import com.duanrong.business.ruralfinance.service.AgricultureTimelyloansPrepaymentService;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.User;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.FileUploadAndDownload;
import com.duanrong.util.LoadConstantProterties2;


@Service
public class AgricultureTimelyloansPrepaymentServiceImpl<T> implements AgricultureTimelyloansPrepaymentService {
	/**
	 * 借款人表
	 */
	@Resource
	private  AgricultureTimelyloansPrepaymentDao agricultureTimelyloansPrepaymentDao;
	@Resource
	private UserDao userDao;
	@Resource
	private AgricultureLoanerInfoDao loaninfoDao;
	@Resource
	DemandTreasureLoanDao demandTreasureLoanDao;
	@Autowired
	private AgricultureRepaymentPlanDao agricultureRepaymentPlanDao;


	@Override
	public int saveAgricultureTimelyloansPrepayment(Object obj, String type) {
		return agricultureTimelyloansPrepaymentDao.saveAgricultureTimelyloansPrepayment(obj,type);
	}
	@Override
	public AgricultureTimelyloansPrepayment findByCondition(Map map) {
		return agricultureTimelyloansPrepaymentDao.findByCondition(map);		
		
	}
	@Override
	public String uploadFile(CommonsMultipartFile files,
			HttpServletRequest request, HttpServletResponse response,String uploadExcelId) {
		String realPath = request.getServletContext().getRealPath(File.separator);
		String folderPath = FileUploadAndDownload.mklinkdir(realPath,"AgricultureTimelyloansPrepayment");		
//		String fileNames = new String();  //返回文件名数组
		String fileNames="";
		// 上传的真实文件名称
			String realName = files.getOriginalFilename();
			fileNames = folderPath.replace(File.separator, "/") + "/" + realName;
			folderPath = folderPath.replace(File.separator, "/");
			String absPath = realPath + folderPath + File.separator + realName;
			
			if (!files.isEmpty()) {
				FileOutputStream os = null;
				InputStream in = null;
				try {
					// 拿到输出流，同时重命名上传的文件
					os = new FileOutputStream(absPath);
					// 拿到上传文件的输入流
					in = files.getInputStream();
					// 以写字节的方式写文件
					int b = 0;
					while ((b = in.read()) != -1) {
						os.write(b);
					}  
					
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
					SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
					String rm = df.format(new Date());
					String ext = realName.substring(realName.lastIndexOf(".")); 
					if(".xls".equals(ext)){  
						// Excel 2003获取方法
						  rm=rm+".xls"; 
			         }else if(".xlsx".equals(ext)){  
			            	// Excel 2007获取方法
			            rm=rm+".xlsx";
			         } 
					
					String rm1=df1.format(new Date());
					UserCookie user = UserCookieHelper.GetUserCookie(request, response);
					
					//oss路径
					String osskey ="AgricultureTimelyloansPrepayment" + "/"+rm1+"/" + user.getUserId()+rm;
					UploadPrepaymentUser uploadUser=new UploadPrepaymentUser();
					uploadUser.setCreateTime(new Date());
					uploadUser.setId(uploadExcelId);
					uploadUser.setStatus(1);
					
					User userById = userDao.get(user.getUserId());
					if(userById!=null){
						uploadUser.setUploadUser(userById.getRealname());
					}
					uploadUser.setUploadAddress(osskey);
					this.saveExcle(uploadUser, "1");
					
					if(osskey.startsWith("/")){
						osskey = osskey.substring(1);
					}
					OssUtil.putObject(LoadConstantProterties2.getValueByDefaultPro("bucketName"), osskey, absPath, files.getContentType());
					os.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (os != null && in != null) {
						try {
							os.close();
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		
		return JSONArray.toJSONString(fileNames);
	}
	@Override
	public int saveExcle(Object obj, String type) {
		return agricultureTimelyloansPrepaymentDao.saveExcle(obj, type);
	}
	@Override
	public PageInfo<AgricultureDebitRecords> readAgricultureTimelyPrepayment(int pageNo, int pageSize,
			Map params) {
		return agricultureTimelyloansPrepaymentDao.readAgricultureTimelyPrepayment(pageNo, pageSize, params);
	}
	@Override
	public List readTimelyPrepaymentbyupload(Map params) {		
		return agricultureTimelyloansPrepaymentDao.readTimelyPrepaymentbyupload(params);
	}
	@Override
	public int updateTimelyloanPrepaymentStatus(String id) {
		
		return agricultureTimelyloansPrepaymentDao.updateTimelyloanPrepaymentStatus(id);
	}

	@Override
	public String updateTimelyLoansPayment(String uploadExcelId) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("flag", "0");
			params.put("uploadExcelId", uploadExcelId);
			List<AgricultureTimelyloansPrepayment> list=this.readTimelyPrepaymentbyupload(params);
		  if( list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      			Date endTime = sdf.parse(DateUtil.DateToString(list.get(i).getActualEndTime(), "yyyy-MM-dd HH:mm:ss"));
      			Date nowTime = new Date();
				if("是".equals(list.get(i).getWhetherEarlyRepayment())&& endTime.getTime()>nowTime.getTime()){				
						//1.更新还款计划
						List<AgricultureRepaymentPlan> planList=loaninfoDao.readTimelyloansRepaymentPlan(list.get(i).getContractId().toString());
						for (int j = 0; j < planList.size(); j++) {
							SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd");
							Date endtime =fdate.parse(DateUtil.DateToString(list.get(i).getActualEndTime(), "yyyy-MM-dd"));
							Date repaytime=fdate.parse(DateUtil.DateToString(planList.get(j).getRepayDate(), "yyyy-MM-dd"));	
							double monthMoney=planList.get(j).getMonthMoney();
							double benjin=planList.get(j).getMoney();
							double realrepayMoney=monthMoney+benjin;
							if(endtime.getTime()==repaytime.getTime()){
							AgricultureRepaymentPlan repaymentPlan=new AgricultureRepaymentPlan();	
							//如果线下已经还清处理还款计划和还款数据
								if("是".equals(list.get(i).getWhetherSettlement())){								
								repaymentPlan.setOperationTime(endtime);
								repaymentPlan.setRealrepayMoney(realrepayMoney);
								repaymentPlan.setRepayStatus("finish");
								repaymentPlan.setMonthMoney(realrepayMoney);
								repaymentPlan.setCorpus(planList.get(j).getMoney()+planList.get(j).getCorpus());
								repaymentPlan.setIntereat(planList.get(j).getIntereat());
								repaymentPlan.setLatePenalty(planList.get(j).getLatePenalty());
								repaymentPlan.setEarlyRepayment(1);
								}else{
								//如果没有还清走中金的扣款流程，处理还款计划和还款数据 							
								//repaymentPlan.setOperationTime(endtime);
								repaymentPlan.setMonthMoney(realrepayMoney);
								repaymentPlan.setCorpus(planList.get(j).getMoney()+planList.get(j).getCorpus());
								repaymentPlan.setIntereat(planList.get(j).getIntereat());
								repaymentPlan.setRealrepayMoney(planList.get(j).getRealrepayMoney());
								repaymentPlan.setLatePenalty(planList.get(j).getLatePenalty());
								repaymentPlan.setEarlyRepayment(1);
								}
								repaymentPlan.setId(planList.get(j).getId());
							   agricultureRepaymentPlanDao.updateTimlyRepaymentplan(repaymentPlan);
							}else if(repaytime.getTime()>endtime.getTime()){
								AgricultureRepaymentPlan repaymentPlan1=new AgricultureRepaymentPlan();	
								repaymentPlan1.setId(planList.get(j).getId());
								repaymentPlan1.setOperationTime(endtime);
								repaymentPlan1.setMonthMoney(0);
								repaymentPlan1.setCorpus(0);
								repaymentPlan1.setIntereat(0);
								repaymentPlan1.setLatePenalty(0);
								repaymentPlan1.setRealrepayMoney(0);
								repaymentPlan1.setRepayStatus("finish");								
							  agricultureRepaymentPlanDao.updateTimlyRepaymentplan(repaymentPlan1);
							}
						}
						//2.更新主标借款信息的还款信息
						Map<String, Object> loanerinfoprm = new HashMap<>();
						loanerinfoprm.put("contractId",list.get(i).getContractId().toString());
						String whetherEarlyRepayment=list.get(i).getWhetherEarlyRepayment().toString();
						if("是".equals(whetherEarlyRepayment)){
							whetherEarlyRepayment="1";
						}else{
							whetherEarlyRepayment="0";
						}
						loanerinfoprm.put("whetherEarlyRepayment",whetherEarlyRepayment);
						loanerinfoprm.put("actualLoanTerm", list.get(i).getActualLoanTerm().toString());
						loanerinfoprm.put("actualEndTime", list.get(i).getActualEndTime());
						if("是".equals(list.get(i).getWhetherSettlement())){
						loanerinfoprm.put("status", "finish");
						}						
						loaninfoDao.updateTimelyLoaninfo(loanerinfoprm);	
						//3.修改线上的项目结束时间
						Map<String, Object> loanerinfoTime = new HashMap<>();
						loanerinfoTime.put("contractId",list.get(i).getContractId().toString());
						loanerinfoTime.put("finishTime",list.get(i).getActualEndTime());
						demandTreasureLoanDao.updateLoanerinfoTime(loanerinfoTime);	
						//4.更新该条记录的匹配状态	
						this.updateTimelyloanPrepaymentStatus(list.get(i).getId());						
				}else{
					return "dateError";
				}																												
			}
			return "success";
		  }else{
			  return "HaveMatching"; 
		  }
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}		
	
	
}