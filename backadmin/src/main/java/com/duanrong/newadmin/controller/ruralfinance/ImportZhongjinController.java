package com.duanrong.newadmin.controller.ruralfinance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.dao.AgricultureDebitRecordsDao;
import com.duanrong.business.ruralfinance.model.AgricultureDebitRecords;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.service.AgricultureDebitRecordsService;
import com.duanrong.business.ruralfinance.service.AgricultureLoaninfoService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.newadmin.utility.RegexInput;

@Controller
public class ImportZhongjinController {

	// 借款人的数据

	@Autowired
	@Resource
	private AgricultureLoaninfoService agricultureLoaninfoService;
	@Resource
	private AgricultureDebitRecordsService<AgricultureDebitRecords> agricultureDebitRecordsService;
	private Boolean jy = true;
	// 数据错误
	private int sjErr = 0;
	// 明细号重复
	private int bhErr = 0;
	// 数据错误
	private List<AgricultureDebitRecords> contractDataErrList = new ArrayList<AgricultureDebitRecords>();
	private List<AgricultureDebitRecords> contractDataErrListAno = new ArrayList<AgricultureDebitRecords>();
	// 明细号重复
	private List<String> contractHTErrList = new ArrayList<String>();
	private List<String> contractHTErrListAno = new ArrayList<String>();
	// 已经扣过款的项目

	private List<String> contractIsFK = new ArrayList<String>();
	// 最后都正确保存所有
	private List<AgricultureDebitRecords> saveAll = new ArrayList<AgricultureDebitRecords>();
	private InputStream stream1;

	// 先判断excle的明细号的重复数据
	private List<String> contractExcleCF = new ArrayList<String>();

	@RequestMapping(value = "/agricultural/impzhongjinDebit")
	public ModelAndView impzhongjinDebit(Model model,
			HttpServletRequest request, HttpServletResponse response) {

		int pageNo = 1;
		int pageSize = 20;
		if (MyStringUtils.isNumeric(request.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		model.addAttribute("bl", 1);
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageNo", String.valueOf(pageNo));
		map.put("pageSize", String.valueOf(pageSize));
		PageInfo pageInfo = agricultureLoaninfoService.readUploadZhongjinUser(
				pageNo, pageSize, "1");
		model.addAttribute("list", pageInfo.getResults());
		model.addAttribute("pageInfo", pageInfo);

		return new ModelAndView("ruralfinance/impzhongjinData");
	}

	/**
	 * 显示数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showzhongjinData", method = RequestMethod.GET)
	public String showzhongjinData() {

		return "redirect:/agricultural/impzhongjinDebit?type=1";
	}

	// 1.导入Excel
	public void inputExcel(CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response, int type,String uploadExcelId)
			throws Exception {
		InputStream input = file.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = input.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		stream1 = new ByteArrayInputStream(baos.toByteArray());
		jy = true;
		// 先清空内容防止多个excle的增加
		contractDataErrList.clear();
		contractHTErrList.clear();
		contractExcleCF.clear();
		contractDataErrListAno.clear();
		contractHTErrListAno.clear();
		bhErr = 0;
		sjErr = 0;
		// 根据type进入录入数据还是查看是否已放款的
		if (type == 0) {

		} else {
			impExcel(stream1, request, response,uploadExcelId,file);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/showzhongjinData", method = RequestMethod.POST)
	public ModelAndView impData(
			@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		//先生成一个上传的excel的id
		String uploadExcelId = IdGenerator.randomUUID();
		// 1.导入
		inputExcel(file, request, response, 1,uploadExcelId);
		// 有数据错误
		if (sjErr == 1 || bhErr == 1) {
			// 错误的
			for (int i = 0; i < contractDataErrList.size(); i++) {
				if (contractDataErrList.get(i) != null
						&& contractDataErrList.get(i).getId() != null) {
					contractDataErrListAno.add(contractDataErrList.get(i));
				}
			}
			model.addAttribute("contractDataErrList", contractDataErrListAno);
			model.addAttribute("contractHTErrList", contractHTErrList);
			// 没有数据错误
		} else {
			// 上传oss
			agricultureDebitRecordsService.uploadFile(file, request, response,uploadExcelId);
			PageInfo pageInfo = agricultureLoaninfoService.readUploadZhongjinUser(
					1, 20, "1");
			model.addAttribute("list", pageInfo.getResults());
			model.addAttribute("pageInfo", pageInfo);
			String type = request.getParameter("type");
			if (type == null) {
				model.addAttribute("sc", "success");
			}
		}
		return new ModelAndView("ruralfinance/impzhongjinData");
	}

	/**
	 * 导入Excel
	 * 
	 * @param execelFile
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean impExcel(InputStream execelFile, HttpServletRequest request,
			HttpServletResponse response,String uploadExcelId,CommonsMultipartFile files) throws Exception {
		// 构造 Workbook 对象，execelFile 是传入文件路径(获得Excel工作区)
		Workbook book = null;
		try {	
			String realName = files.getOriginalFilename();
			String ext = realName.substring(realName.lastIndexOf(".")); 
			  if(".xls".equals(ext)){  
				// Excel 2003获取方法
					book = new HSSFWorkbook(execelFile);  
	            }else if(".xlsx".equals(ext)){  
	            	// Excel 2007获取方法
	    			book = new XSSFWorkbook(execelFile);
	            }  		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 读取表格的第一个sheet页
		Sheet sheet = book.getSheetAt(0);
		// 定义 row、cell
		org.apache.poi.ss.usermodel.Row row;
		// 总共有多少行,从0开始
		row = sheet.getRow(2);
		// 总共有多少列,从0开始
		@SuppressWarnings("unused")
		int totalCells = row.getLastCellNum();

		org.apache.poi.ss.usermodel.Row rowSec = sheet.getRow(2);
		List<AgricultureDebitRecords> agricultureDebitRecords = new ArrayList<AgricultureDebitRecords>();
		// 最后一行
		AgricultureDebitRecords agriculturedebitRecords = null;
		contractHTErrList.clear();
		contractExcleCF.clear();
		saveAll.clear();
		// 判断excle的明细号重复
		// 获取所有的行
		for (int f = 2; f < sheet.getLastRowNum() + 1; f++) {
			Row rr = sheet.getRow(f);
			if (rr == null) {

			} else {
				Cell currentContent = rr.getCell(4);
				if (currentContent == null) {
				} else {
					contractExcleCF.add(currentContent.toString());
				}
			}
		}

		for (int i = 0; i < contractExcleCF.size(); i++) {
			// 跟数据库比对
			Map map1 = new HashMap<>();
			map1.put("byType", "1");
			map1.put("detailsNumber", contractExcleCF.get(i));
			AgricultureDebitRecords agricultureDebitRecords1 = agricultureDebitRecordsService
					.readByCondition(map1);
			if (agricultureDebitRecords1 == null) {

			} else {
				contractHTErrList.add(contractExcleCF.get(i));
			}
			for (int j = contractExcleCF.size() - 1; j > i; j--) {
				if (contractExcleCF.get(j).equals(contractExcleCF.get(i))) {
					contractHTErrList.add(contractExcleCF.get(i));
				} else {
					System.out.println(contractExcleCF.get(i));
					// 跟数据库比对
					Map map = new HashMap<>();
					map.put("byType", "1");
					map.put("detailsNumber", contractExcleCF.get(i));
					// 查出来没有
					AgricultureDebitRecords agricultureDebitRecords2 = agricultureDebitRecordsService
							.readByCondition(map);
					if (agricultureDebitRecords2 == null) {

					} else {
						contractHTErrList.add(contractExcleCF.get(i));
					}
				}
			}
		}
		// 显示所有的明细号重复 不往下执行
		if (contractHTErrList.size() > 0) {
			bhErr = 1;
			Set<String> set = new HashSet<String>(contractHTErrList);
			contractHTErrList.clear();
			contractHTErrList.addAll(set);
			return false;
		} else {
			// 跟数据库比较

		}
		for (int y = 2; y < sheet.getLastRowNum() + 1; y++) {
			// 明细号
			int yhm = 0;
			// 交易状态
			int sfzh = 0;
			jy = true;
			row = sheet.getRow(y);
			agriculturedebitRecords = new AgricultureDebitRecords();

			// 获取列
			for (int t = 1; t < rowSec.getLastCellNum(); t++) {
				if (row == null) {

				} else {
					String uuid = IdGenerator.randomUUID();
					agriculturedebitRecords.setId(uuid);
					agriculturedebitRecords.setFlag(0);
					agriculturedebitRecords.setUploadExcelId(uploadExcelId);
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					// 交易时间
					if (t == 1 && isOK(1, row)) {
						Date date = sdf.parse(row.getCell(1).toString());
						agriculturedebitRecords.setTransactionTime(date);

					}
					// 机构名称
					if (t == 2 && isOK(2, row)) {
						agriculturedebitRecords.setOrganizationName(row
								.getCell(2).toString());
					}
					// 批次号
					if (t == 3 && isOK(3, row)) {
						agriculturedebitRecords.setBatchNumber(row.getCell(
								3).toString());
					}
					// 明细号
					if (t == 4 && isOK(4, row)) {
						agriculturedebitRecords.setDetailsNumber(row
								.getCell(4).toString());
					} else if (t == 4 && !isOK(4, row)) {
						jy = false;
						yhm = 1;
					}
					// 金额
					if (t == 5 && isOK(5, row)) {
					String money=row.getCell(5).toString().replace(",","");
						agriculturedebitRecords.setMoney(Double
								.parseDouble(money));
					}
					// 银行ID
					if (t == 6 && isOK(6, row)) {
						agriculturedebitRecords.setBankID(row.getCell(6)
								.toString());
					}
					// 账户类型
					if (t == 7 && isOK(7, row)) {
						agriculturedebitRecords.setCustomerType(row
								.getCell(7).toString());
					}
					// 账户号码
					if (t == 8 && isOK(8, row)) {
						agriculturedebitRecords.setBankcard(row.getCell(8)
								.toString());
					}
					// 账户名称
					if (t == 9 && isOK(9, row)) {
						agriculturedebitRecords.setName(row.getCell(9)
								.toString());
					}
					// 分支行名称
					if (t == 10 && isOK(10, row)) {
						agriculturedebitRecords.setBranchName(row.getCell(
								10).toString());
					}
					// 分支行省份
					if (t == 11 && isOK(11, row)) {
						agriculturedebitRecords.setBranchProvince(row
								.getCell(11).toString());
					}
					// 分支行城市
					if (t == 12 && isOK(12, row)) {
						agriculturedebitRecords.setBranchCity(row.getCell(
								12).toString());
					}
					// 备注信息
					if (t == 13 && isOK(13, row)) {
						agriculturedebitRecords.setRemark(row.getCell(13)
								.toString());
					}
					// 协议用户编号
					if (t == 14 && isOK(14, row)) {
						agriculturedebitRecords.setProtocolUserid(row
								.getCell(14).toString());
					}
					// 协议号
					if (t == 15 && isOK(15, row)) {
						agriculturedebitRecords.setProtocolNum(row.getCell(
								15).toString());
					}
					// 手机号码
					if (t == 16 && isOK(16, row)) {
						agriculturedebitRecords.setMobileNumber(row
								.getCell(16).toString());
					}
					// 电子邮件
					if (t == 17 && isOK(17, row)) {
						agriculturedebitRecords.setEmail(row.getCell(17)
								.toString());
					}
					// 证件类型
					if (t == 18 && isOK(18, row)) {
						agriculturedebitRecords.setIdType(row.getCell(18)
								.toString());
					}
					// 证件号码
					if (t == 19 && isOK(19, row)) {
						agriculturedebitRecords.setIdCard(row.getCell(19)
								.toString());
					}
					// 银行代收的时间
					if (t == 20 && isOK(20, row)) {
						Date date = sdf.parse(row.getCell(20).toString());
						agriculturedebitRecords.setBankCollectionTime(date);
					}
					// 结算标识
					if (t == 21 && isOK(21, row)) {
						agriculturedebitRecords.setBalanceFlag(row
								.getCell(21).toString());
					}
					// 交易状态
					if (t == 22 && isOK(22, row)) {
						String status = row.getCell(22).toString();
						if (status.equals("30-代收成功")) {
							agriculturedebitRecords.setStatus(1);
						} else if (status.equals("40-代收失败")) {
							agriculturedebitRecords.setStatus(0);
						}
						agriculturedebitRecords.setTransactionStatus(row
								.getCell(22).toString());
					} else if (t == 22 && !isOK(22, row)) {
						jy = false;
						sfzh = 1;
					}
					// 银行响应代码
					if (t == 23 && isOK(23, row)) {
						agriculturedebitRecords.setBankResponseCode(row
								.getCell(23).toString());
					}
					// 银行响应消息
					if (t == 24 && isOK(24, row)) {
						agriculturedebitRecords.setBankResponseInfo(row
								.getCell(24).toString());
					}
					// 卡介质类型
					if (t == 25 && isOK(25, row)) {
						agriculturedebitRecords.setCardType(row
								.getCell(25).toString());
					}
				}
			}
			// 数据有问题
			if (!jy) {
				sjErr = 1;
			}
			if (agriculturedebitRecords.getDetailsNumber() == null) {

			} else {

				Map map = new HashMap<>();
				map.put("byType", "1");
				map.put("detailsNumber",
						agriculturedebitRecords.getDetailsNumber());
				AgricultureDebitRecords agricultureDebitRecords2 = agricultureDebitRecordsService
						.readByCondition(map);
				// 查出没有数据往数据库录数据
				if (agricultureDebitRecords2 == null) {
					saveAll.add(agriculturedebitRecords);
				} else {
					// 合同编号有重复
					bhErr = 1;
				}
			}
			// 一下列是出现错误的情况
			if (yhm == 1) {
				agriculturedebitRecords.setMxh(1);				
			}
			if (sfzh == 1) {
				agriculturedebitRecords.setJyStatus(1);
			}
			if (yhm == 1 || sfzh == 1) {
				contractDataErrList.add(agriculturedebitRecords);
			}
		}
		// 最后判断是否全部是合法数据
		if (bhErr == 1 || sjErr == 1) {
			return false;
		} else {
			// 判断excle的第一条以后的数据重复
			if (bhErr == 1) {
				return false;
			} else {
				for (int i = 0; i < saveAll.size(); i++) {
					agricultureDebitRecordsService
							.saveAgricultureDebitRecords(saveAll.get(i),
									"1");
				}
				return true;
			}
		}
		
	}

	public boolean isOK(int t, Row row) {
		return row != null && row.getCell(t) != null
				&& StringUtils.isNotBlank(row.getCell(t).toString());
	}
}