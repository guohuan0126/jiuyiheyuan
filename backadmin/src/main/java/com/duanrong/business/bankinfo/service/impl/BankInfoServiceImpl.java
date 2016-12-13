package com.duanrong.business.bankinfo.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.OssUtil;

import com.duanrong.business.bankinfo.dao.BankInfoDao;
import com.duanrong.business.bankinfo.model.BankInfo;
import com.duanrong.business.bankinfo.service.BankInfoService;
import com.duanrong.newadmin.utility.FileUploadAndDownload;
import com.duanrong.util.LoadConstantProterties2;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : YUMIN
 * @CreateTime : 2016-4-28 下午5:56:31
 * @Description : 银行卡信息管理
 * 
 */
@Service
public class BankInfoServiceImpl implements BankInfoService {
   @Resource
   BankInfoDao bankInfoDao;
   
	@Override
	public List<BankInfo> readBankInfo(BankInfo bankInfo) {
		List<BankInfo> bankinfos= bankInfoDao.getBankInfo(bankInfo);
		return bankinfos;
	}

	@Override
	public int updateBankStatue(Map<String, Object> params) {		
		return bankInfoDao.updateBankStatue(params);
	}

	@Override
	public BankInfo readByid(String id){		
		return bankInfoDao.get(id);
	}

	@Override
	public void insert(BankInfo bankInfo) {
		bankInfoDao.insert(bankInfo);		
	}
	@Override
	public void update(BankInfo bankInfo) {
		bankInfoDao.update(bankInfo);
		
	}

	@Override
	public String uploadBankData(CommonsMultipartFile[] files,
			HttpServletRequest request) {
				String realPath = request.getRealPath("/");
				String imgSrc="/images/bank";
				String mkdir = FileUploadAndDownload.mkImagesdir(realPath,imgSrc);
				/*String replaceMkdir = mkdir.replaceAll("/", "\\\\");*/
				// 上传的真实文件名称
				String realName = files[0].getOriginalFilename();
				// 对文件名进行编码
				String newName = FileUploadAndDownload.getName(realName);
				
				String absPath = realPath + mkdir + "/" + newName;
				if (!files[0].isEmpty()) {
					FileOutputStream os = null;
					InputStream in = null;
					try {
						// 拿到输出流，同时重命名上传的文件
						os = new FileOutputStream(absPath);
						// 拿到上传文件的输入流
						in = files[0].getInputStream();
						//oss路径
						String osskey = mkdir + "/" + newName;;
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
				return mkdir + "/" + newName;
			}

	
	
}
