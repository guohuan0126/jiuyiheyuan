package com.duanrong.business.link.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.OssUtil;
import base.pagehelper.PageInfo;

import com.duanrong.business.link.dao.LinkDao;
import com.duanrong.business.link.dao.LinkTypeDao;
import com.duanrong.business.link.model.Link;
import com.duanrong.business.link.model.LinkType;
import com.duanrong.business.link.service.LinkService;
import com.duanrong.newadmin.utility.FileUploadAndDownload;
import com.duanrong.util.LoadConstantProterties2;

@Service
public class LinkServiceImpl implements LinkService {

	@Resource
	LinkDao linkDao;
	
	@Resource
	LinkTypeDao linkTypeDao;

	@Override
	public List<Link> readLinksByType(String type) {
		return linkDao.getLinksByType(type);
	}

	@Override
	public PageInfo<LinkType> readLikeTypePageLite(int pageNo, int pageSize, LinkType linkType) {
		
		return linkTypeDao.pageLiteForType(pageNo, pageSize, linkType);
	}

	@Override
	public void insert(LinkType linkType) {
		linkTypeDao.insert(linkType);		
	}

	@Override
	public LinkType read(String id) {
		
		return linkTypeDao.get(id);
	}

	@Override
	public void update(LinkType linkType) {
		linkTypeDao.update(linkType);	
	}

	@Override
	public List<LinkType> readTypeList() {		
		return linkTypeDao.getTypeList();
	}

	@Override
	public PageInfo<Link> readPageLite(int pageNo, int pageSize, Link link) {		
		return linkDao.pageLite(pageNo, pageSize, link);
	}

	@Override
	public String insertLink(Link link) {
		if(readLink(link.getId()) == null){
			linkDao.insert(link);
			return "ok";
		}
		return "notNull";
	}

	@Override
	public Link readLink(String id) {
		return linkDao.get(id);
	}

	@Override
	public String updateLink(Link link) {
		if(readLink(link.getId()) != null){
			linkDao.update(link);
			return "ok";
		}
		return "isNull";
	}

	@Override
	public String uploadArticleData(CommonsMultipartFile[] files,
			HttpServletRequest request) {
		//String loanId = request.getParameter("loanId");
		String realPath = request.getRealPath("/");
		String mkdir = FileUploadAndDownload.mklinkdir(realPath);
		/*String replaceMkdir = mkdir.replaceAll("/", "\\\\");*/
		// 上传的真实文件名称
		String realName = files[0].getOriginalFilename();
		// 对文件名进行编码
		String newName = FileUploadAndDownload.getName(realName);
		
		String absPath = realPath + mkdir + File.separator + newName;
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