package com.duanrong.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class FileUploadAndDownload  {
	
	
//	/**
//	 * 文件上传
//	 */
//	public void upload(FileUploadEvent event) {
//		InputStream is = null;
//		UploadedFile uploadFile = event.getFile();
//		try {
//			is = uploadFile.getInputstream();
//			BannerPicture pp = new BannerPicture();
//			pp.setId(IdGenerator.randomUUID());
//			String upload = ImageUploadUtil
//					.upload(is, uploadFile.getFileName());
//			pp.setPicture(upload);
//			loanHome.getInstance().setRiskInstruction(upload);
//			loanHome.getInstance().setLoanInstruction(uploadFile.getFileName());
//			if (uploadFile != null) {
//				FacesMessage msg = new FacesMessage("上传成功...",
//						uploadFile.getFileName());
//				FacesContext.getCurrentInstance().addMessage(null, msg);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//	}
	
//	/**
//	 * 文件修改
//	 */
//	public void changeBannerPact(FileUploadEvent event) {
//		UploadedFile uploadFile = event.getFile();
//		InputStream is = null;
//		try {
//			is = uploadFile.getInputstream();
//			if (this.needChangedPact != null) {
//				this.upload(event);
//			} else {
//				FacesUtil.addErrorMessage("被更改的banner为空。");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}finally{
//			try {
//				is.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

//	/**
//	 * 
//	 * @description 投资合同下载
//	 * @author 孙铮
//	 * @time 2014-7-31 下午3:40:04
//	 * @param path 下载路径
//	 * @param fileName 文件名
//	 * @param object 投资对象
//	 */
//	public void Download(String path, String fileName, Object object){
//		try {
//			String appRealPath = FacesUtil.getAppRealPath();
//			String subFileName = path.substring(path.lastIndexOf("/")+1, path.length());
//			String copyFileName = ShortUrlGenerator.shortUrl(IdGenerator.randomUUID())
//					+ ShortUrlGenerator.shortUrl(IdGenerator.randomUUID())+".pdf";
//			//拼接下载路径
//			String contextPath = appRealPath + path.substring(path.indexOf("/")+1, path.length()).replace("/", "\\");
//			//拼接copy路径
//			String replacePath = path.substring(path.indexOf("/")+1, path.length()).replace(subFileName, copyFileName);
//			String copyReplacePath = appRealPath + replacePath.replace("/", "\\");
//			// 此处为了防止文件并发修改,将样本合同复制,生成一个新的合同,将用户数据写到新的合同中,并让用户下载新的合同
//			copyFile(contextPath, copyReplacePath);
//			// 往合同中动态写入用户的信息
//			writerPDFData2LoanPact(copyReplacePath, object, "/"+replacePath);
//			// //进行下载
//			fileDownload(copyReplacePath, fileName);
//			// //用户下载完毕后删除新合同
//			deleteTemporaryFile(copyReplacePath);
//		} catch (Exception e) {
//			FacesUtil.addErrorMessage("下载失败!");
//		}
//
//	}

	/**
	 * 
	 * @description 删除文件
	 * @author 孙铮
	 * @time 2014-7-31 下午3:40:58
	 * @param deleteFilePath 要删除的文件路径
	 */
	public static void deleteTemporaryFile(String deleteFilePath) {
		File file = new File(deleteFilePath);
		file.delete();
	}

	/**
	 * 
	 * @description 动态写入数据到pdf文件中
	 * @author 孙铮
	 * @time 2014-7-31 下午3:41:20
	 * @param contextPath
	 * @param object
	 * @param readerPath
	 */
	public void writerPDFData2LoanPact(String contextPath,
			String readerPath) {
		ByteArrayOutputStream bos = null;
		FileOutputStream fos = null;
		PdfStamper ps = null;
		try {
			PdfReader reader = new PdfReader(contextPath);
			bos = new ByteArrayOutputStream();
			ps = new PdfStamper(reader, bos);
			/**
			 * 使用中文字体 如果是利用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体就行了
			 */
			AcroFields s = ps.getAcroFields();
			ps.setFormFlattening(true);
			ps.close();
			fos = new FileOutputStream(contextPath);
			fos.write(bos.toByteArray());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fos.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @description 文件下载
	 * @author 孙铮
	 * @time 2014-7-31 下午3:41:51
	 * @param filePath 路径
	 * @param fileName 名字
	 */
	public static void fileDownload(String filePath, String fileName) {
		/*FacesContext fc = null;
		InputStream fis = null;
		OutputStream output = null;
		try {
			File file = new File(filePath);
	        fis = new FileInputStream(file);
	
	        fc = FacesContext.getCurrentInstance();
	        ExternalContext ec = fc.getExternalContext();
	        ec.responseReset();
	        ec.setResponseContentType("application/octet-stream");
	        ec.setResponseContentLength((int)file.length());
	        ec.setResponseHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
	        output = ec.getResponseOutputStream();
	
	        byte[] buffer = new byte[1024];
	        int len = -1;
	        while ((len = fis.read(buffer)) != -1) {
	            output.write(buffer, 0, len);
	        }
	        fc.responseComplete();
		} catch(Exception ex) {
			ex.printStackTrace();
		}finally{
			try {
				fis.close();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}

	/**
	 * 
	 * @description 文件copy
	 * @author 孙铮
	 * @time 2014-7-31 下午3:42:08
	 * @param oldPath copy目标路径
	 * @param newPath copy目的地路径
	 * @return
	 */
	public static String copyFile(String oldPath, String newPath) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(
					new FileInputStream(new File(oldPath)));
			bos = new BufferedOutputStream(
					new FileOutputStream(new File(newPath)));

			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1) {
				bos.write(bys, 0, len);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			// 关闭流对象
			try {
				bos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newPath;
	}
	
	/**
	 * 
	 * @description 文件下载 (文件路径)
	 * @author 孙铮
	 * @time 2014-8-28 上午9:43:33
	 * @param filePath 要下载的文件路径
	 * @param returnName 返回的文件名
	 * @param response HttpServletResponse
	 * @param delFlag 是否删除文件
	 */
	public static void download(String filePath,String returnName,HttpServletResponse response,boolean delFlag){
		prototypeDownload(new File(filePath), returnName, response, delFlag);
	}

	/**
	 * 
	 * @description 文件下载 (文件名)
	 * @author 孙铮
	 * @time 2014-8-28 上午9:45:27
	 * @param file 要下载的文件
	 * @param returnName 返回的文件名
	 * @param response HttpServletResponse
	 * @param delFlag 是否删除文件
	 */
	public static void download(File file,String returnName,HttpServletResponse response,boolean delFlag){
		prototypeDownload(file, returnName, response, delFlag);
	}
	
	/**
	 * @param file 要下载的文件
	 * @param returnName 返回的文件名
	 * @param response HttpServletResponse
	 * @param delFlag 是否删除文件
	 */
	/**
	 * 
	 * @description TODO
	 * @author 孙铮
	 * @time 2014-8-28 上午9:46:26
	 * @param file 要下载的文件
	 * @param returnName 返回的文件名
	 * @param response HttpServletResponse
	 * @param delFlag 是否删除文件
	 */
	private static void prototypeDownload(File file,String returnName,HttpServletResponse response,boolean delFlag){
		// 下载文件
		FileInputStream inputStream = null;
		ServletOutputStream outputStream = null;
		try {
			if(!file.exists()) return;
			response.reset();
			//设置响应类型	PDF文件为"application/pdf"，WORD文件为："application/msword"， EXCEL文件为："application/vnd.ms-excel"。  
			response.setContentType("application/octet-stream;charset=utf-8");

			//设置响应的文件名称,并转换成中文编码
			//returnName = URLEncoder.encode(returnName,"UTF-8");
			returnName = response.encodeURL(new String(returnName.getBytes(),"iso8859-1"));	//保存的文件名,必须和页面编码一致,否则乱码
			
			//attachment作为附件下载；inline客户端机器有安装匹配程序，则直接打开；注意改变配置，清除缓存，否则可能不能看到效果
			response.addHeader("Content-Disposition",   "attachment;filename="+returnName);  
			
			//将文件读入响应流
			inputStream = new FileInputStream(file);
			outputStream = response.getOutputStream();
			int length = 1024;
			int readLength=0;
			byte buf[] = new byte[1024];
			readLength = inputStream.read(buf, 0, length);
			while (readLength != -1) {
				outputStream.write(buf, 0, readLength);
				readLength = inputStream.read(buf, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//删除原文件
			
			if(delFlag) {				
				file.delete();
			}
		}
	}

	/**
	 * 
	 * @description TODO
	 * @author 孙铮
	 * @time 2014-8-28 上午9:49:37
	 * @param byteArrayOutputStream 将文件内容写入ByteArrayOutputStream
	 * @param response 写入response
	 * @param returnName 返回的文件名
	 * @throws IOException
	 */
	public static void download(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response, String returnName) throws IOException{
		response.setContentType("application/octet-stream;charset=utf-8");
		returnName = response.encodeURL(new String(returnName.getBytes(),"iso8859-1"));			//保存的文件名,必须和页面编码一致,否则乱码
		response.addHeader("Content-Disposition",   "attachment;filename=" + returnName);  
		response.setContentLength(byteArrayOutputStream.size());
		
		ServletOutputStream outputstream = response.getOutputStream();	//取得输出流
		byteArrayOutputStream.writeTo(outputstream);					//写到输出流
		byteArrayOutputStream.close();									//关闭
		outputstream.flush();											//刷数据
	}
}
