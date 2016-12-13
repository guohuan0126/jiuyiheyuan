<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter, util.Uploader"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	System.out.println(request.getParameter("action"));
	if(request.getParameter("action") != null){
		String action = request.getParameter("action");
		if(action.equals("config")){
			String rootPath = application.getRealPath( "/" );	
			System.out.println(rootPath);
			out.write( new ActionEnter( request, rootPath ).exec() );
		}else if(action.equals("uploadimage")){
			Uploader up = new Uploader(request);
		    up.setSavePath("upload");		    
		    up.setMaxSize(1024*4); //单位KB
		    up.upload();				
		    response.getWriter().print("{'original':'"+up.getFileName()+"','url':'/"+up.getUrl()+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"'}");
		    
		}
	}else{
		String rootPath = application.getRealPath( "/" );			
		out.write( new ActionEnter( request, rootPath ).exec() );
	}
	 
	
	
%>