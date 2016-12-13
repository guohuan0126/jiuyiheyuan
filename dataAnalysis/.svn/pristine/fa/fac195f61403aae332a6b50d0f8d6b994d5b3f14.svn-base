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
<link rel="stylesheet" href="/css/jquery.e-calendar.css" />
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/jquery.e-calendar.js"></script>
<script type="text/javascript" src="/js/menu.js"></script>
<script src="/js/index.js"></script>
<head>
<base href="<%=basePath%>">

<title>活动效果分析</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!--  -->
	
<script type="text/javascript">

	
function a(pageNo){
		
	document.getElementById("myFrom").action="/activeData/showActiveData?pageNo="+pageNo;
	document.getElementById("myFrom").submit();
		
}



</script>
</head>

<body>
	<jsp:include page="base/header.jsp" />
	<div id="content">
		<jsp:include page="base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>活动效果分析</li>
				</ul>
			</div>
			<form  method="post" id="myFrom" >
			<div class="main">
				<div class="bound">
					<div class="box">
						<div class="input-list">
							活动编号<input id="activeId" type="text" name="activeId" value="${activeId}">
						</div>
					
						<div class="input-list">
							<span class="user"></span> <!--  <span class="btn-select"
								id="btn_select"> <span class="cur-select">全部</span> -->
								
								<div class="input-list">
									用户来源<input type="text" name="userSource" id="userSource" value="${userSource}">
								</div>
								
								<!-- <select id="mySelect" name="type" style="width:120px;float:left;border:1px solid #ccc;height:28px;line-height:28px;border-radius:2px;margin-left:5px;">  
      								<option value="baidu" >百度</option>  
      								<option value="360">360</option>  
   								 </select>  -->
							</span>
							   
						</div>
						
						<div class="input-list">
							<input type="button" id="query" value="查询" class="but-search" onclick="a('1');"/>
						</div>
						
					</div>
						</form>
					<p><font color="#FF0000">${error}</font></p>  
					<div class="box">
						<div class="box-table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="table-tit">
									<td>序号</td>
									<td>红包金额</td>
									<td>使用门槛</td>
									<td>发放张数</td>
									<td>使用数</td>
									<td>未使用数</td>
									<td>过期数</td>
									<td>投资总额</td>
								</tr>
								<c:forEach items="${pageInfo.results}" var="activeData" varStatus="Num" >
								<tr>
									<td>${Num.index+1}</td>
									<td>${activeData.redPackageMoney}</td>
									<td>${activeData.useRule}</td>
									<td>${activeData.sendCount}</td>
									<td>${activeData.usedCount}</td>
									<td>${activeData.unusedCount}</td>
									<td>${activeData.expiredCount}</td>
									 <td><fmt:formatNumber type="number" value="${activeData.userInvestMoney}" pattern="0.00" maxFractionDigits="2"/></td>
								</tr>
								</c:forEach>
								<tr>   
									<td>发放总数： ${activeR.sendedCount}</td>
									<td>使用总数：${activeR.usedCount}</td>
									<td>未使用总数： ${activeR.unusedCount}</td>
									<td>已过期总数 ： ${activeR.expiredCount}</td>
									<td>使用红包投资总额：<fmt:formatNumber type="number" value="${activeR.redPInvestMoney}" pattern="0.00" maxFractionDigits="2"/></td>
									<td>投资总额：<fmt:formatNumber type="number" value="${activeR.investMoneyByUser}" pattern="0.00" maxFractionDigits="2"/></td>
									
								</tr>
							</table>
							<div class="table-pages">
							<span class="pages-left">共有${pageInfo.totalRecord}条，每页显示 ${pageInfo.pageSize}条</span>
									
								<span class="pages-all">
								<c:if test="${pageInfo.hasPreviousPage}">
									<a href="javascript:a(${pageInfo.pageNo-1})"><<</a>
								</c:if>  
								  <c:forEach var="i" begin="1" end="${pageInfo.totalPage}">
								 	<a <c:if test="${i==pageInfo.pageNo}"> class="cur"</c:if> href="javascript:a(${i})">${i}</a> 
	                               </c:forEach>
								<c:if test="${pageInfo.hasNextPage}">
									<a href="javascript:a(${pageInfo.pageNo+1})">>></a>
								</c:if>
								</span> 
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		navList(12);
	</script>

</body>
</html>
