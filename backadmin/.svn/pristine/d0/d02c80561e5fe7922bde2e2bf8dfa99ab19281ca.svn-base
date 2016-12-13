package com.duanrong.business.bankinfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.duanrong.business.bankinfo.model.BankInfo;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : YUMIN
 * @CreateTime : 2016-4-28 下午5:56:31
 * @Description : 银行卡信息管理
 * 
 */
public interface BankInfoService {
	/**
	 * 获取银行卡信息列表
	 * @param bankInfo
	 * @return
	 */
	public List<BankInfo> readBankInfo(BankInfo bankInfo);
	
	/**
	 * 银行卡禁用，启用
	 * @param params
	 * @return
	 */
	public int updateBankStatue(Map<String, Object> params);
	public BankInfo readByid(String id);
	public void insert(BankInfo bankInfo); 
	
	public void update(BankInfo bankInfo);
	/**
	 * 上传银行logo图片
	 * @param files
	 * @param request
	 * @return
	 */
	public String uploadBankData(CommonsMultipartFile[] files, HttpServletRequest request);
}
