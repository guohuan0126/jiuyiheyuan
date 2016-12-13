package com.duanrong.business.ruralfinance.service.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.OssUtil;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONArray;
import com.duanrong.business.ruralfinance.dao.AgricultureLoaninfoDao;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.UploadUser;
import com.duanrong.business.ruralfinance.service.AgricultureLoaninfoService;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.User;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.FileUploadAndDownload;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.util.LoadConstantProterties2;
@Service
public class AgricultureLoaninfoServiceImpl<T> implements
AgricultureLoaninfoService {
	/**
	 * 借款人表
	 */
	@Resource
	private  AgricultureLoaninfoDao agricultureLoaninfoDao;
	@Resource
	private UserDao userDao;

	/**
	 * 保存数据
	 */
	@Override
	public int saveAgricultureLoaninfo(Object obj, String type) {
		return agricultureLoaninfoDao.saveAgricultureLoaninfo(obj,type);
	}
	
	@Override
	public String uploadFile(CommonsMultipartFile files,
			HttpServletRequest request,HttpServletResponse response) {
		String realPath = request.getServletContext().getRealPath(File.separator);
		String folderPath = FileUploadAndDownload.mklinkdir(realPath,"ruralfinanceloandataexcel");		
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
					rm=rm+".xlsx";
					String rm1=df1.format(new Date());
					UserCookie user = UserCookieHelper.GetUserCookie(request, response);
					
					//oss路径
					String osskey = /*folderPath*/"/ruralfinanceloandataexcel" + "/"+rm1+"/" + user.getUserId()+rm;
					UploadUser uploadUser=new UploadUser();
					uploadUser.setCreateTime(new Date());
					uploadUser.setId(IdGenerator.randomUUID());
					uploadUser.setStatus(1);
					
					User userById = userDao.get(user.getUserId());
					if(userById!=null){
						uploadUser.setUploadUser(userById.getRealname());
					}
					uploadUser.setUploadAddress(osskey);
					agricultureLoaninfoDao.saveExcle(uploadUser, "1");
					
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
	/**
	 * 同用条件查询
	 */
	@Override
	public AgricultureLoaninfo readByCondition(Map map) {
		AgricultureLoaninfo agricultureLoaninfo = 	agricultureLoaninfoDao.findByCondition(map);
		if(agricultureLoaninfo!=null){
			return agricultureLoaninfo;
		}else{
			return null;
		}
	}

	
	@Override
	public PageInfo<UploadUser> readUploadUser(int pageNo, int pageSize, String type) {
		
		return agricultureLoaninfoDao.findUploadUser(pageNo, pageSize, type);
	}

	@Override
	public int saveExcle(Object obj, String type) {
		return agricultureLoaninfoDao.saveExcle(obj, type);
	}

	/**
	 * 获取所有的合同编号
	 * @return
	 */
	@Override
	public List<String> readAllContractId() {
		
		List<String> contractIdList = agricultureLoaninfoDao.getAllContractId();
		
		return contractIdList;
	}

	@Override
	public PageInfo readUploadZhongjinUser(int pageNo, int pageSize, String type) {
		return agricultureLoaninfoDao.readUploadZhongjinUser(pageNo, pageSize, type);
	}

	@Override
	public PageInfo readUploadPrepaymentUser(int pageNo, int pageSize,
			String type) {
		return agricultureLoaninfoDao.readUploadPrepaymentUser(pageNo, pageSize, type);
	}





}