<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="/css/jquery.ui.slider.css" />
<link rel="stylesheet" href="/css/jquery-ui-timepicker-addon.css" />
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script type="text/javascript" src="/js/jquery.ui.slider.js"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="/js/menu.js"></script>

<head>
<base href="<%=basePath%>">

<title>利信众客的投资记录</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!--  
	 -->
<style type="text/css">

	.box2 {
	/*非IE的主流浏览器识别的垂直居中的方法*/
	display: table-cell;
	vertical-align:middle;
	/*设置水平居中*/
	text-align:center;
	/* 针对IE的Hack */
	*display: block;
	*font-size: 175px;/*约为高度的0.873，200*0.873 约为175*/
	*font-family:Arial;/*防止非utf-8引起的hack失效问题，如gbk编码*/
	width:200px;
	height:200px;
	border: 1px solid #eee;
}
.right-nav{height:auto;}
.input-list{padding-top:20px; padding-left:30px;}
.input-list input,.input-list select{border:1px solid #999;border-radius:3px;height:38px;padding-left:20px;}

</style>
</head>
<body>
<!-- header -->
<div id="header">
	<div class="logo">cps营销系统</div>
	<div class="navigation">
		<ul>
		 	<li>欢迎你！</li>
		</ul>
	</div>
</div>

	
	<div id="content">
		
		<div class="m-right">
			<div class="right-nav">
				
			
					<div class="error-cont" style="width:700px; margin:0px auto;">
		<h3>抱歉，出现错误了！</h3>
		<p>无法访问本页的原因是：<br>
		
		 <c:choose>
		 	  <c:when test="${desc ne null and desc ne ''}">
		 	  		${ desc }
		 	  </c:when>
		 	 <c:otherwise>
		 	  所访问的页面不存在或者已被管理员删除！
		 	 </c:otherwise>
		 </c:choose>  	
管理员为网站给您带来的不便致以诚挚的歉意！</p>
	</div>			
			</div>
					<div class="box">
				
						<div class="box-table">
							
							
						</div>
					</div>
				</div>
		</div>

	
	
	
	
	
	
	
	
	

<br/><br/>
<!-- footer -->
</body>
</html>
