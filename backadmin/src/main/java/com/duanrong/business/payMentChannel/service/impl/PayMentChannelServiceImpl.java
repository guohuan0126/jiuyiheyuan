package com.duanrong.business.payMentChannel.service.impl;

import java.io.File;
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
import base.pagehelper.PageInfo;

import com.duanrong.business.payMentChannel.dao.PayMentChannelDao;
import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankInfo;
import com.duanrong.business.payMentChannel.service.PayMentChannelService;
import com.duanrong.newadmin.utility.FileUploadAndDownload;
import com.duanrong.util.LoadConstantProterties2;

@Service
public class PayMentChannelServiceImpl  implements PayMentChannelService{

	
	@Resource
	private PayMentChannelDao payMentChannelDao;
	
	@Override
	public PageInfo<PayMentChannel> readPayMentChannel(int pageNo,
			int pageSize, Map<String, Object> params) {
		
		return payMentChannelDao.readPayMentChannel(pageNo,pageSize,params);
	}

	@Override
	public String uploadPayMentChannel(CommonsMultipartFile[] files,
			HttpServletRequest request) {
		String realPath = request.getRealPath("/");
		String imgSrc="/soa/images/paymentchannel";
		String mkdir = FileUploadAndDownload.mkImagesdir(realPath,imgSrc);
		/*String replaceMkdir = mkdir.replaceAll("/", "\\\\");*/
		// 上传的真实文件名称
		String realName = files[0].getOriginalFilename();
		// 对文件名进行编码
		String newName = FileUploadAndDownload.getName(realName);
		
		String absPath = realPath + mkdir +File.separator + newName;
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

	@Override
	public void insertPaymentChannel(PayMentChannel paymentChannel) {
		
		payMentChannelDao.insertPaymentChannel(paymentChannel);
	}

	@Override
	public void updatePaymentChannel(Map<String, Object> params) {
		// TODO Auto-generated method stub
		payMentChannelDao.updatePaymentChannel(params);
	}

	@Override
	public PayMentChannel readPayMentChannelById(String id) {
		// TODO Auto-generated method stub
		return payMentChannelDao.readPayMentChannelById(id);
	}

	@Override
	public PageInfo<PaymentBankInfo> readPaymentBankInfo(int pageNo,
			int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return payMentChannelDao.readPaymentBankInfo(pageNo,pageSize,params);
	}

	@Override
	public String uploadPayMentBankInfo(CommonsMultipartFile[] files,
			HttpServletRequest request) {
		String realPath = request.getRealPath("/");
		String imgSrc="/soa/images/bank";
		String mkdir = FileUploadAndDownload.mkImagesdir(realPath,imgSrc);
		String replaceMkdir = mkdir.replaceAll("/", "\\\\");
		// 上传的真实文件名称
		String realName = files[0].getOriginalFilename();
		// 对文件名进行编码
		String newName = FileUploadAndDownload.getName(realName);
		
		String absPath = realPath + replaceMkdir + "\\" + newName;
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

	@Override
	public void insertPayMentBankInfo(PaymentBankInfo paymentBankInfo) {
		
		
		payMentChannelDao.insertPayMentBankInfo(paymentBankInfo);
	}

	@Override
	public PaymentBankInfo readPaymentBankInfoById(String id) {
		// TODO Auto-generated method stub
		return payMentChannelDao.readPaymentBankInfoById(id);
	}

	@Override
	public void updatePaymentBankInfo(Map<String, Object> params) {
		
		payMentChannelDao.updatePaymentBankInfo(params);
	}

	@Override
	public PageInfo<PaymentBankChannel> readPaymentBankChannel(int pageNo,
			int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return payMentChannelDao.readPaymentBankChannel(pageNo,pageSize,params);
	}

	@Override
	public List<PaymentBankInfo> readBankList() {
		// TODO Auto-generated method stub
		return payMentChannelDao.readBankList();
	}

	@Override
	public List<PayMentChannel> readChannelList() {
		// TODO Auto-generated method stub
		return payMentChannelDao.readChannelList();
	}

	@Override
	public void insertPaymentBankChannel(PaymentBankChannel paymentBankChannel) {
		
		payMentChannelDao.insertPaymentBankChannel(paymentBankChannel);
	}

	@Override
	public PaymentBankChannel readPaymentBankChannelById(String id) {
		// TODO Auto-generated method stub
		return payMentChannelDao.readPaymentBankChannelById(id);
	}

	@Override
	public void updatePayMentBankChannel(Map<String, Object> params) {
		payMentChannelDao.updatePayMentBankChannel(params);
		
	}

	@Override
	public PaymentBankChannel readPaymentBankChannelByBankIdAndChannelId(String bankName,
			String channelName) {
		// TODO Auto-generated method stub
		return payMentChannelDao.readPaymentBankChannelByBankIdAndChannelId(bankName,channelName);
	}

	
}