package util.poi;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import com.duanrong.newadmin.utility.DateUtil;

/**
 * Excel 操作类
 * 
 * @author xiao
 * @date 下午5:51:57
 */
public class ExcelConvertor {

	private static String OUTPATH = "C:\\duanrong"; // 输出路径

	OutputStream out; // 文件输出流

	String filePath = "";

	private String dateformat="yyyy-MM-dd HH:mm:ss";
	
	
	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	public ExcelConvertor(String fileName) {
		File file = new File(fileName);
		File parent = file.getParentFile();
		if (null != parent && !parent.exists()) {
			parent.mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}
		try {
			out = new FileOutputStream(OUTPATH + "\\" + fileName + ".xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ExcelConvertor(OutputStream out, String filePath) {
		this.out = out;
		this.filePath = filePath;
	}

	/**
	 * Excel导出
	 * 
	 * @author xiao
	 * 
	 * @param table
	 *            要导出的数据表
	 * @param title
	 *            数据标题
	 * @param out
	 *            输出流
	 * @param fileName
	 *            文件名称
	 */
	public void excelExport(List<?> table, Map<String, String> title, String t,
			int[] style) {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFCellStyle cellStyle = book.createCellStyle();
		HSSFFont font = book.createFont();
		HSSFSheet sheet = book.createSheet();
		if(null != t && t.equals("还款信息")){
			dateformat="yyyy-MM-dd";
		}
		createTitle(sheet, cellStyle, font, title, t, style);
		createContext(sheet, cellStyle, font, table, title, 2);
		try {
			book.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Excel导出
	 * 
	 * @author xiao
	 * 
	 * @param table
	 *            要导出的数据表
	 * @param title
	 *            数据标题
	 * @param out
	 *            输出流
	 * @param fileName
	 *            文件名称
	 */
	public void excelExport(List<?> table, Map<String, String> title,
			int[] style) {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFCellStyle cellStyle = book.createCellStyle();
		HSSFFont font = book.createFont();
		HSSFSheet sheet = book.createSheet();
		createTitle(sheet, cellStyle, font, title, style);
		createContext(sheet, cellStyle, font, table, title, 1);
		try {
			book.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Excel导出
	 * 
	 * @author xiao
	 * 
	 * @param table
	 *            要导出的数据表
	 * @param title
	 *            数据标题
	 * @param out
	 *            输出流
	 * @param fileName
	 *            文件名称
	 */
	public void excelExport(List<?> table, Map<String, String> title,
			int[] style,String sheetName) {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFCellStyle cellStyle = book.createCellStyle();
		HSSFFont font = book.createFont();
		HSSFSheet sheet = book.createSheet();
		if(!sheetName.equals("") && sheetName!=null)
		book.setSheetName(0,sheetName);
		createTitle(sheet, cellStyle, font, title, style);
		createContext(sheet, cellStyle, font, table, title, 1);
		try {
			book.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 创建表格标题
	public void createTitle(HSSFSheet sheet, HSSFCellStyle cellStyle,
			HSSFFont font, Map<String, String> title, String t, int[] style) {

		// 设置表头
		HSSFRow row1 = sheet.createRow(0);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		row1.setHeight((short) 600);
		HSSFCell cell1 = row1.createCell(0);
		cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell1.setCellValue(t);
		font.setFontName("仿宋_GB2312");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		font.setFontHeightInPoints((short) 16);
		cellStyle.setFont(font);

		// 设置标题
		HSSFRow row = sheet.createRow(1);
		row.setHeight((short) 350);
		int i = 0;
		for (String key : title.keySet()) {
			// 设置标题
			HSSFCell cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title.get(key));
			font.setFontHeightInPoints((short) 14);
			cellStyle.setFont(font);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cell.setCellStyle(cellStyle);
			sheet.setColumnWidth(i, style[i] * 2 * 164);
			i++;

		}
		sheet.addMergedRegion(new Region((short) 0, (short) 0, (short) 0,
				(short) --i));
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		cell1.setCellStyle(cellStyle);
	}

	public void createTitle(HSSFSheet sheet, HSSFCellStyle cellStyle,
			HSSFFont font, Map<String, String> title, int[] style) {
		// 设置标题
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 350);
		int i = 0;
		for (String key : title.keySet()) {
			// 设置标题
			HSSFCell cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title.get(key));
			font.setFontHeightInPoints((short) 14);
			cellStyle.setFont(font);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cell.setCellStyle(cellStyle);
			sheet.setColumnWidth(i, style[i] * 2 * 164);
			i++;

		}
	}

	// 创建表格体
	public void createContext(HSSFSheet sheet, HSSFCellStyle cellStyle,
			HSSFFont font, List<?> table, Map<String, String> title,
			int rowIndex) {
		if (!table.isEmpty()) {

			// 遍历数据源
			for (int i = 0; i < table.size(); i++) {
				HSSFRow rows = sheet.createRow(i + rowIndex); // 创建行
				rows.setHeight((short) 500);
				int column = 0;
				// 遍历列
				for (String key : title.keySet()) {
					HSSFCell cell = rows.createCell(column);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 创建单元格
					font.setFontName("仿宋_GB2312");
					font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 粗体显示
					font.setFontHeightInPoints((short) 12);
					cellStyle.setFont(font);
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
					cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
					cell.setCellStyle(cellStyle);
					Object obj = table.get(i);
					Class<?> clazz = obj.getClass();
					Field[] fields = clazz.getDeclaredFields();
					// 反射获取值
					for (int j = 0; j < fields.length; j++) {
						if (fields[j].getName().equals(key)) {

							try {
								PropertyDescriptor pd = new PropertyDescriptor(
										fields[j].getName(), clazz);
								Object o = pd.getReadMethod().invoke(obj);
								if (o instanceof Double) {
									DecimalFormat df = new DecimalFormat(
											"#0.####");
									o = df.format(o);
									cell.setCellValue(Double.parseDouble(o
											.toString()));
								} else {
									if (o == null) {
										o = "";
									}
									if (o instanceof Date) {
										o = "" + DateUtil.DateToString((Date) o, dateformat);										
									}
									cell.setCellValue("" + o.toString());
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
					column++;
				}

			}
		}
	}

}
