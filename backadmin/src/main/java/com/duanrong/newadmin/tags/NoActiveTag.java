package com.duanrong.newadmin.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class NoActiveTag extends TagSupport {

	private static final long serialVersionUID = 3174234039143531070L;
	
	
	@Override
	public int doStartTag() throws JspException { //处理标签逻辑
		
		 if (!Boolean.parseBoolean(pageContext.getAttribute("if").toString())) {
             return EVAL_BODY_INCLUDE;
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
}
