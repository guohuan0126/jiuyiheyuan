<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/common/activeTag" prefix="permissionAct"%>
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

<title>利信众客用户明细</title>

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
	<div id="header">
		<div class="logo">cps营销系统</div>
		<div class="navigation">
			<ul>
			 	<li>欢迎你！</li>
			</ul>
		</div>
	</div>
	
	<div class="left_menu">
			<ul id="nav_dot">
        		<li>
          			<h4 class="M10"><span></span>利信众客</h4>
			          <div class="list-item none">	
			          	<a href='/lixinzk/index/getUserInfo?key=m040bd9172333e5aefe288023a0f222'>用户信息</a>		            
			            <a href='/lixinzk/index?key=m040bd9172333e5aefe288023a0f222'>定期交易记录</a>
			            <a href='/lixinzk/index/demandBill?key=m040bd9172333e5aefe288023a0f222'>活期交易记录</a>
			          </div>
        		</li>

			</ul>
			
		</div>
	<div id="content">
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>当前位置：利信众客用户明细</li>
				</ul>
				<br/>
				<form action="/lixinzk/index/getUserInfo">
					<table class="input-list" border="0" width="80%" cellspacing="10" cellpadding="5">
						<tr>
							<%-- <td width="20%">
								<input type="text" id="userId" value="${userId}" name="userId" placeholder="用户编号" style="width:190px; " >
							</td> --%>
							<td>
								<span>用户注册时间：</span>
								<input id="TimeBegin" value="${TimeBegin}"  name="TimeBegin" type="text" class="datepicker" placeholder="开始时间"> --
								<input id="TimeEnd"   value="${TimeEnd}"  name="TimeEnd" type="text" class="datepicker"   placeholder="结束时间">
							</td>
							<td >
								<input class="buttonStyle" type="submit" style="display:block;width:120px;height:40px;background:#3499df" value="查询"/>
							</td>
						</tr>
					</table>
				</form>
			
			<script type="text/javascript">
                        	$(".datepicker").datepicker({
                        		
                        		dateFormat:'yy-mm-dd'
                        	});
								
			</script>
							
			</div>
					<div class="box">
					<div id="rule"></div>
						<div class="box-table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="table-tit">
									<!-- <td>用户编号</td> -->
									<td>用户姓名</td>
									<td>手机号</td>
									<td>注册时间</td>
									<td>注册来源</td>
								</tr>
								<c:forEach items="${pageInfo.results}" var="platformUserRelation" varStatus="Num">
								<tr>
									<%-- <td>${platformUserRelation.userId}</td> --%>
									<td>${platformUserRelation.realName}</td>
									<td>${platformUserRelation.mobile}</td>
									<td>
										<fmt:formatDate value="${platformUserRelation.registerTime}" type="both"/>
									</td>
									<td>${platformUserRelation.userSource}</td>
								</tr>
								</c:forEach>
								<tr></tr>
							</table>							
							<%@ include file="../base/pageInfo.jsp"%>

						</div>
					</div>
				</div>
			</div>
	<script>
	navList(10);
		</script>
	

</body>
</html>