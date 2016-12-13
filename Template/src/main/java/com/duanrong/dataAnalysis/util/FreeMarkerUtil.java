package com.duanrong.dataAnalysis.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * 生成HTML字符串工具
 * @author bj
 *
 */
public class FreeMarkerUtil {
	
    private static String templatePath = "";
    
    static{
    	templatePath = FreeMarkerUtil.class.getClassLoader().getResource("ftl").getPath();
    }
	/**
	 * 生成HTML字符串
	 * @param templateName
	 * @param root
	 * @return
	 */
	public static String analysisTemplate(String templateName,Map<?,?>root){
	  String htmlStr = "";
	  try {
	     Configuration config=new Configuration();
	     //设置要解析的模板所在的目录，并加载模板文件
	     config.setDirectoryForTemplateLoading(new File(templatePath));
	     //设置包装器，并将对象包装为数据模型
	     config.setObjectWrapper(new DefaultObjectWrapper());
	     //获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
	     Template template=config.getTemplate(templateName,"UTF-8");
	     //合并数据模型与模板
	     ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     Writer out = new OutputStreamWriter(baos);
	     template.process(root, out);
	     out.flush();
	     out.close();
	     htmlStr = baos.toString();
	    }catch(Exception e) {
	        e.printStackTrace();
	    }
	    return htmlStr;
	 }
}
