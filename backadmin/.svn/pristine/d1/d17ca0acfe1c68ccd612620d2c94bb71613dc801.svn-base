package com.duanrong.business.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.OssUtil;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.alibaba.fastjson.JSONArray;
import com.duanrong.business.app.dao.AppStartDiagramDao;
import com.duanrong.business.app.model.APP;
import com.duanrong.business.app.model.AppBanner;
import com.duanrong.business.app.model.AppStartDiagram;
import com.duanrong.business.app.service.AppStartDiagramService;
import com.duanrong.newadmin.utility.FileUploadAndDownload;
import com.duanrong.util.LoadConstantProterties2;

@Service
public class AppStartDiagramServiceImpl implements AppStartDiagramService {
	
	@Autowired
	private AppStartDiagramDao appStartDiagramDao;
	
	@Override
	public void add(AppStartDiagram appStartDiagram) {
		this.appStartDiagramDao.insert(appStartDiagram);
	}

	@Override
	public void update(AppStartDiagram appStartDiagram) {
		this.appStartDiagramDao.update(appStartDiagram);
	}
	
	@Override
	public AppStartDiagram get(String id) {
		
		return this.appStartDiagramDao.get(id);
	}
	
	
	@Override
	public void delete(String id) {
		this.appStartDiagramDao.delete(id);
	}
	@Override
	public PageInfo<AppStartDiagram> pageLite(int pageNo, int pageSize,
			AppStartDiagram appStartDiagram) {
		
		return this.appStartDiagramDao.pageLite(pageNo, pageSize, appStartDiagram);
	}
	@Override
	public String getimgsize(){
		return this.appStartDiagramDao.getimgsize();
	}
	
	
	
	@Override
	public String uploadFile(CommonsMultipartFile[] files,
			HttpServletRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
		String datenumber= df.format(new Date());
		String realPath = request.getServletContext().getRealPath(File.separator);
		String folderPath = FileUploadAndDownload.mklinkdir(realPath,"yindao","/app");		
		String[] fileNames = new String[files.length];  //返回文件名数组
		// 上传的真实文件名称
		for(int i=0;i<files.length;i++){
			String realName = datenumber+files[i].getOriginalFilename();
			fileNames[i] = folderPath.replace(File.separator, "/") + "/" + realName;
			folderPath = folderPath.replace(File.separator, "/");
			String absPath = realPath + folderPath + File.separator + realName;
			if (!files[i].isEmpty()) {
				FileOutputStream os = null;
				InputStream in = null;
				try {
					// 拿到输出流，同时重命名上传的文件
					os = new FileOutputStream(absPath);
					// 拿到上传文件的输入流
					in = files[i].getInputStream();
					// 以写字节的方式写文件
					int b = 0;
					while ((b = in.read()) != -1) {
						os.write(b);
					}
					//oss路径
					String osskey = folderPath + "/" + realName;
					if(osskey.startsWith("/")){
						osskey = osskey.substring(1);
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
