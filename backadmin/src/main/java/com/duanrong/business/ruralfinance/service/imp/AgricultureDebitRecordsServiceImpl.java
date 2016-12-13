package com.duanrong.business.ruralfinance.service.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.OssUtil;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONArray;
import com.duanrong.business.ruralfinance.dao.AgricultureDebitRecordsDao;
import com.duanrong.business.ruralfinance.model.AgricultureDebitRecords;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.UploadUser;
import com.duanrong.business.ruralfinance.model.UploadZhongjinUser;
import com.duanrong.business.ruralfinance.service.AgricultureDebitRecordsService;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.User;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.FileUploadAndDownload;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.util.LoadConstantProterties2;


@Service
public class AgricultureDebitRecordsServiceImpl<T> implements AgricultureDebitRecordsService {
	/**
	 * 借款人表
	 */
	@Resource
	private  AgricultureDebitRecordsDao agricultureDebitRecordsDao;
	@Resource
	private UserDao userDao;


	@Override
	public int saveAgricultureDebitRecords(Object obj, String type) {
		return agricultureDebitRecordsDao.saveAgricultureDebitRecords(obj,type);
	}
	@Override
	public AgricultureDebitRecords readByCondition(Map map) {
		return agricultureDebitRecordsDao.findByCondition(map);		
		
	}
	@Override
	public String uploadFile(CommonsMultipartFile files,
			HttpServletRequest request, HttpServletResponse response,String uploadExcelId) {
		String realPath = request.getServletContext().getRealPath(File.separator);
		String folderPath = FileUploadAndDownload.mklinkdir(realPath,"AgricultureDebitRecordsExcel");		
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
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//设置日期格式
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
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
					String osskey ="/AgricultureDebitRecordsExcel" + "/"+rm1+"/" + user.getUserId()+rm;
					UploadZhongjinUser uploadUser=new UploadZhongjinUser();
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
		return agricultureDebitRecordsDao.saveExcle(obj, type);
	}
	@Override
	public PageInfo<AgricultureDebitRecords> readAgricultureDebitRecords(int pageNo, int pageSize,
			Map params) {
		return agricultureDebitRecordsDao.readAgricultureDebitRecords(pageNo, pageSize, params);
	}

	
	
	
}