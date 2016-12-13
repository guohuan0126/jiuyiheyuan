package com.duanrong.newadmin.tags;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.duanrong.business.permission.service.PermissionService;
import com.duanrong.newadmin.controllhelper.UserCookieHelper;
import com.duanrong.newadmin.model.UserCookie;

public class ActiveTag extends TagSupport {
	
	private static final long serialVersionUID = 3174234039143531070L;

	PermissionService permissionService;
	
	/** 标签属性 **/
	private String activeId = "";		
	
	@Override
	public int doStartTag() throws JspException { //处理标签逻辑
		
		ServletContext servletContext = (pageContext).getServletContext();
		WebApplicationContext cxt = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		permissionService = (PermissionService) cxt.getBean("permissionService");			
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();			
			UserCookie user = UserCookieHelper.GetUserCookie(request, response);
			List<String> list = new ArrayList<>();
			if(request.getSession().getAttribute("permission") == null){				
				if(user !=null){				
					list = (List<String>) permissionService.readHasActivePermission(user.getUserId());
					request.getSession().setAttribute("permission", list);
				}				
			}else{
				list = (List<String>) request.getSession().getAttribute("permission");	
			}					
			for(String l : list){
				if(l.equals(activeId)){
					pageContext.setAttribute("if", true);
					return EVAL_BODY_INCLUDE;
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();			
		}
		pageContext.setAttribute("if", false);
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
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}		
	
}
