package com.duanrong.newadmin.tags;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class UrlDecodeTag extends TagSupport {
	
	private static final long serialVersionUID = 3174234039143531070L;

	/** 标签属性 **/
	private String value = "";		
	
	@Override
	public int doStartTag() throws JspException { //处理标签逻辑		
		
		try {
			if(value.contains("%E")){			
				value = URLDecoder.decode(value, "UTF-8");				
			}	
			pageContext.getOut().write(value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
		
	}
	@Override
	public int doEndTag() throws JspException {		
		
		return EVAL_PAGE;		 		
	}
	
	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
	}	
	@Override
	public void release() { //释放资源
		super.release();
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
