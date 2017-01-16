package com.duanrong.newadmin.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.user.model.User;

public class FileUploadAndDownload {

	public final static String UPLOAD_PATH = "/upload";
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 根据字符串创建本地目录 并按照日期建立子目录返回
	 * 
	 * @param path
	 * @return
	 */
	public static String mkdir(final String path) {
		final String absPath = path + UPLOAD_PATH + "/"
				+ formater.format(new Date());
		File dir = new File(absPath);
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return UPLOAD_PATH + "/" + formater.format(new Date());
	}
	/**
	 * 根据字符串创建本地目录 连接建立连接子目录返回
	 * wangjing
	 * @param path
	 * @return
	 */
	public static String mklinkdir(final String path) {
		final String absPath = path + UPLOAD_PATH + "/link";
		File dir = new File(absPath);
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return UPLOAD_PATH + "/link";
	}
	
	/**
	 * 根据字符串创建本地目录 连接建立连接子目录返回
	 * wangjing
	 * @param path
	 * @return
	 */
	public static String mklinkdir(final String path,String folder) {
		final String absPath = path + UPLOAD_PATH + File.separator + folder;
		File dir = new File(absPath);
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return UPLOAD_PATH + File.separator + folder;
	}


	/**
	 * 依据原始文件名生成新文件名
	 * 
	 * @return
	 */
	public static String getName(String fileName) {
		Random random = new Random();
		return "" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+ random.nextInt(10000) + System.currentTimeMillis()
				+ getFileExt(fileName);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 
	 * @description 投资合同下载
	 * @author 孙铮
	 * @time 2014-7-31 下午3:40:04
	 * @param path
	 *            下载路径
	 * @param fileName
	 *            文件名
	 * @param object
	 *            投资对象
	 */
	@SuppressWarnings("deprecation")
	public static String Download(HttpServletRequest request,
			HttpServletResponse response, Invest invest, User investUser,
			User borrowMoneyUser) {
		try {
			// 1,tomcat路径
			String realPath = request.getRealPath("/");
			// 2,合同所在的磁盘路径(老文件路径)
			realPath = realPath
					+ invest.getLoan().getContract().replace('/', '\\')
							.substring(1);
			// 3,新文件路径
			String newContractPath = realPath.substring(0,
					realPath.lastIndexOf('\\') + 1)
					+ invest.getLoan().getContractName();
			// 4,copy一份新的文件,并重新命名
			copyFile(realPath, newContractPath);
			// 5,往新文件中写数据
			writerPDFData2LoanPact(newContractPath, invest, investUser,
					borrowMoneyUser);
			// 6,提示客户端下载新路径的文件
			fileDownload(newContractPath, response);

			// 7,删除生成的新文件
			deleteTemporaryFile(newContractPath);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

	/**
	 * 
	 * @description 删除文件
	 * @author 孙铮
	 * @time 2014-7-31 下午3:40:58
	 * @param deleteFilePath
	 *            要删除的文件路径
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
	private static void writerPDFData2LoanPact(String path, Invest invest,
			User investUser, User borrowMoneyUser) {
		/*ByteArrayOutputStream bos = null;
		FileOutputStream fos = null;
		PdfStamper ps = null;
		try {
			PdfReader reader = new PdfReader(path);
			bos = new ByteArrayOutputStream();
			ps = new PdfStamper(reader, bos);
			*//**
			 * 使用中文字体 如果是利用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体就行了
			 *//*
			AcroFields s = ps.getAcroFields();
			if ("201406262235000001".equals(invest.getLoanId())) {
				s.setField("loanId", "史雅昆");
				s.setField("IDcard", "410221198909082753");
				s.setField("MobileNumber", "18638123633");
				s.setField("Address", "郑州市中原区须水镇小京水村南街12号");
			} else {
				s.setField("realName", investUser.getRealname());
				s.setField("userName", investUser.getUserId());
				s.setField("investMoney", invest.getMoney().toString());
				s.setField("loanRealname", borrowMoneyUser.getRealname());
				s.setField("loanIDcard", borrowMoneyUser.getIdCard());
				s.setField("loanMobileNumber",
						borrowMoneyUser.getMobileNumber());
				s.setField("loanAddress", borrowMoneyUser.getPostAddress());
			}
			ps.setFormFlattening(true);
			ps.close();
			fos = new FileOutputStream(path);
			fos.write(bos.toByteArray());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null && fos != null && ps != null) {
				try {
					fos.close();
					bos.close();
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}*/
	}

	/**
	 * 
	 * @description 文件下载
	 * @author 孙铮
	 * @time 2014-7-31 下午3:41:51
	 * @param filePath
	 *            路径
	 * @param fileName
	 *            名字
	 */
	public static void fileDownload(String newContractPath,
			HttpServletResponse response) {

		FileInputStream fs = null;
		PrintWriter out = null;
		String fileName = null;
		try {
			if (newContractPath.lastIndexOf("\\") > 0) {
				fileName = new String(newContractPath.substring(
						newContractPath.lastIndexOf("\\") + 1,
						newContractPath.length()).getBytes("GB2312"),
						"ISO8859_1");
			}
			File file = new File(newContractPath);
			fs = new FileInputStream(file);
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			int b = 0;
			out = response.getWriter();
			while ((b = fs.read()) != -1) {
				out.write(b);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fs != null && out != null) {
				out.close();
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @description 文件copy
	 * @author 孙铮
	 * @time 2014-7-31 下午3:42:08
	 * @param oldPath
	 *            copy目标路径
	 * @param newPath
	 *            copy目的地路径
	 * @return
	 */
	public static boolean copyFile(String oldPath, String newPath) {
		boolean flag = false;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(
					new FileInputStream(new File(oldPath)));
			bos = new BufferedOutputStream(new FileOutputStream(new File(
					newPath)));

			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1) {
				bos.write(bys, 0, len);
			}
			flag = true;
			return flag;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return flag;
		} catch (IOException e) {
			e.printStackTrace();
			return flag;
		} finally {
			// 关闭流对象
			try {
				bos.close();
				bis.close();
				return flag;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
