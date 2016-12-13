package com.duanrong.business.activity.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.OssUtil;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONArray;
import com.duanrong.business.activity.dao.ActivityDao;
import com.duanrong.business.activity.model.Activity;
import com.duanrong.business.activity.service.ActivityService;
import com.duanrong.newadmin.utility.FileUploadAndDownload;
import com.duanrong.util.LoadConstantProterties2;

@Service
public class ActivityServiceImpl implements ActivityService {
   
	@Autowired
	public ActivityDao activityDao;
	
	@Override
	public void add(Activity activity) {
		this.activityDao.insert(activity);
	}

	@Override
	public void update(Activity activity) {
		this.activityDao.update(activity);
	}

	@Override
	public void delete(Integer id) {
       this.activityDao.delete(id);		
	}

	@Override
	public PageInfo<Activity> readPageLite(int pageNo, int pageSize,
			Activity activity) {
		
		return this.activityDao.pageLite(pageNo, pageSize, activity);
	}

	@Override
	public Activity read(Integer id) {
		
		return this.activityDao.get(id);
	}

	@Override
	public String uploadFile(CommonsMultipartFile[] files,
			HttpServletRequest request) {
		String realPath = request.getServletContext().getRealPath(File.separator);
		String folderPath = FileUploadAndDownload.mklinkdir(realPath,"website_activity");		
		folderPath = folderPath.replaceAll("\\\\", "/");
		String[] fileNames = new String[files.length];  //返回文件名数组
		// 上传的真实文件名称
		for(int i=0;i<files.length;i++){
			String realName = files[i].getOriginalFilename();	
			// 对文件名进行编码
			String newName = FileUploadAndDownload.getName(realName);
			fileNames[i] = folderPath.replace(File.separator, "/") + "/" + newName;
			String absPath = realPath + folderPath + File.separator + newName;
			if (!files[i].isEmpty()) {
				FileOutputStream os = null;
				InputStream in = null;
				try {
					// 拿到输出流，同时重命名上传的文件
					os = new FileOutputStream(absPath);
					// 拿到上传文件的输入流
					in = files[i].getInputStream();
					//oss路径
					String osskey = folderPath + "/" + newName;;
					if(osskey.startsWith("/")){
						osskey = osskey.substring(1);
					}
										
					// 以写字节的方式写文件
					int b = 0;
					while ((b = in.read()) != -1) {
						os.write(b);
					}
					OssUtil.putObject(LoadConstantProterties2.getValueByDefaultPro("bucketName"), osskey, absPath, files[0].getContentType());
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
		}
		
		return JSONArray.toJSONString(fileNames);
		
	}

}