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

<title>爱有钱推送投资记录</title>

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
	<jsp:include page="../base/header.jsp" />
	<div id="content">
		<jsp:include page="../base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>当前位置：爱有钱推送投资记录</li>
				</ul>
				<br/>
				<form action="/aiyouqian/pushInvestHistory" method="get">
				<table class="input-list" border="0" width="80%" cellspacing="10" cellpadding="5">
					<tr>
					<td width="20%">
							<input type="text" id="investId" value="${investId}" name="investId" placeholder="投资编号" style="width:190px; " >
						</td>
						<td>
							<input type="text" id="loanId" value="${loanId}" name="loanId" placeholder="项目编号" style="width:190px;">	
						</td>
						<td>
							<span>投资记录推送时间：</span>
							<input id="investTimeBegin" value="${investTimeBegin}"  name="investTimeBegin" type="text" class="datepicker" placeholder="开始时间"> --
							<input id="investTimeEnd"   value="${investTimeEnd}"  name="investTimeEnd" type="text" class="datepicker"   placeholder="结束时间">
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
									<td>项目编号</br>
										项目名称
									</td>
									<td>投资编号</td>
									<td>用户编号</td>
									<td>投资金额</td>
									<td>预计收益</td>
									<td>项目周期</td>
									<td>年化利率</td>
									<td>收益方式</td>
									<td>项目状态</td>
									<td>投资状态</td>
									<td>投资来源</td>
									<td>投资时间</td>
									<td>推送时间</td>
								</tr>
								<c:forEach items="${pageInfo.results}" var="Invest" varStatus="Num">
								<tr>
								<td>${Invest.loanId}
								<br>${Invest.loanName}
								</td>
								<td>${Invest.id}</td>
								<td>${Invest.userId}</td>
								<td>${Invest.money}</td>
								<td>${Invest.interest}</td>
								<td>
									<c:if test="${Invest.loanOperationType =='月' }">
										${Invest.deadline}月
									</c:if>
									<c:if test="${Invest.loanOperationType =='天'}">
										${Invest.loanDay}天
									</c:if>
								</td>
								
								<td>
									<fmt:formatNumber type="number" value="${Invest.rate}" pattern="0.00" maxFractionDigits="2"/>  
								</td>
								<td>${Invest.repayType}</td>
								<td>${Invest.loanStatus}</td>
								<td>${Invest.status}</td>
								<td>${Invest.userSource}</td>
								<td><fmt:formatDate value="${Invest.time}" type="both"/></td>
								<td>
								<fmt:formatDate value="${Invest.sendTime}" type="both"/>
								</td>
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
