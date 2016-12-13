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
<link rel="stylesheet" href="/css/jquery.dialogbox.css" />
<link rel="stylesheet" href="/css/jquery.e-calendar.css" />
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/jquery.e-calendar.js"></script>
<script type="text/javascript" src="/js/jquery.dialogBox.js"></script>
<script type="text/javascript" src="/js/menu.js"></script>
<script src="/js/index.js"></script>
<head>
<base href="<%=basePath%>">

<title>现金红包</title>

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

</style>
<script type="text/javascript">
	
	
	function getRedPackageRule(rule){
		 $('#rule').dialogBox({
					width: 400,
					height: 300,
					hasClose: true,
					hasBtn: true,
					effect: 'sign',
					
			title: '活动规则',
			content: rule
		});
	}

</script>
</head>

<body>
	<jsp:include page="../base/header.jsp" />
	<div id="content">
		<jsp:include page="../base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>推送管理</li>
				</ul>
			</div>
					<div class="box">
					<div id="rule"></div>
						<div class="box-table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="table-tit">
									<td></td>
									<td>编号</td>
									<td>项目名称</td>
									<td>还款方式</td>
									<td>周期/利率</td>
									<td>借款人</td>
									<td>借款总金额</td>
									<td>项目状态</td>
									<td>项目发起时间</td>
									<td>操作</td>
									
								</tr>
								<c:forEach items="${pageInfo.results}" var="redPackage" varStatus="Num">
								<tr>
								
									<td></td>
									<td>${loan.id}
					 <c:if test="${loan.newbieEnjoy == '是'}"><img src="${ctx}/images/newbie.png"></c:if>
					 <c:if test="${loan.organizationExclusive == '是'}"><img src="${ctx}/images/org.png"></c:if></td>
									<td>${redPackage.id}</td>
									<td>${redPackage.name}</td>
									<td>${redPackage.redPackageMoney}</td>
									<td>${redPackage.count}</td>
									<td>${redPackage.byUserCount}</td>
									<td>${redPackage.usedUserCount}</td>
									<td>${redPackage.redPackageSendedCount}</td>
									<td>${redPackage.unCount}</td>
									<td>${redPackage.unUsedCount}</td>
									<td>${redPackage.usedCount}</td>
									<td>${redPackage.payMoney}</td>
									<td><fmt:formatNumber type="number" value="${redPackage.allPayMoney}" pattern="0.00" maxFractionDigits="2"/></td>         
									<td> <a href="javascript:getRedPackageRule('${redPackage.redPackageRule}');" >详情</a> </td>
								</tr>
								
								</c:forEach>
								
								<tr></tr>
							</table>							
							<div class="table-pages">
								<span class="pages-left">共有${pageInfo.totalRecord}条，每页显示  ${pageInfo.pageSize}条</span><!-- <select
									class="pages-sec">
									<option value="1">20条</option>
									<option value="2">30</option>
									<option value="3">50</option>
									<option value="4">100条</option>
								</select> --> 
								<span class="pages-all">
								<c:if test="${pageInfo.hasPreviousPage}">
									<a href="/showRedPackage?pageNo=${pageInfo.pageNo-1}"><<</a>
								</c:if>  
								  <c:forEach var="i" begin="1" end="${pageInfo.totalPage}">
								 	<a <c:if test="${i==pageInfo.pageNo}"> class="cur"</c:if> href="/showRedPackage?pageNo=${i}">${i}</a> 
	                               </c:forEach>
								<c:if test="${pageInfo.hasNextPage}">
									<a href="/showRedPackage?pageNo=${pageInfo.pageNo+1}">>></a>
								</c:if>
								</span> 
							
								<!-- <span class="pages-right"> <input type="text"
									class="input-txt"><input type="button" value="GO"
									class="input-but">
								</span> -->
								
								
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
