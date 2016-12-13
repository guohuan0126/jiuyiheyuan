<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<!-- UI框架 -->
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<head>
<base href="<%=basePath%>">



<title>用户信息管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
<style type="text/css">
	.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }  
.ui-timepicker-div dl { text-align: left; }  
.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }  
.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }  
.ui-timepicker-div td { font-size: 90%; }  
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }  

</style>
<style type="text/css">
/* .box-table-1 {
	padding-top: 0px;
	overflow: scroll;
}

.box-table-1 table {
	width: 1540px;
}

.box-table-1 table tr.data-box-title1 {
	background: #eee;
}

.box-table-1 table tr td {
	font-size: 13px;
}
.table-pages{width: 1657px;}
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
	border: 1px solid #eee; */
</style>

<script type="text/javascript">
	function a(pageNo) {
		document.getElementById("myFrom").action = "/userData/toShowUserData?pageNo="+ pageNo;
		document.getElementById("myFrom").submit();
	}
</script>
<style type="text/css">
.box .input-list02 input {
	height: 12px;
	margin-right: 5px;
}
</style>
</head>

<body>
	<jsp:include page="base/header.jsp" />
	<div id="content">
		<jsp:include page="base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>用户信息管理</li>
				</ul>
			</div>
			<form method="post" id="myFrom">
				<div class="main">
					<div class="bound" >
						<div class="box">
							<div class="input-list">
								<input id="userSource" type="text" name="userSource"
									value="${userSource}" placeholder="用户来源">
							</div>
							<div class="input-list">
								<input id="registerTimeBegin" type="text" class="timeBegin datepicker" name="registerTimeBegin"
									value="${registerTimeBegin}" placeholder="注册开始时间"> --
								<input id="registerTimeEnd" type="text" class="timeEnd datepicker" name="registerTimeEnd"
									value="${registerTimeEnd}" placeholder="注册结束时间">
							</div>
							<script type="text/javascript">
                        	$(".datepicker").datepicker({
                        		dateFormat:'yy-mm-dd'
                        	});
							</script>
							<div class="input-list">
								<input id="liveInvestMin" type="text" name="liveInvestMin"
									value="${liveInvestMin}" placeholder="活期投资最小金额"> --
								<input id="liveInvestMax" type="text" name="liveInvestMax"
									value="${liveInvestMax}" placeholder="活期投资最大金额">
							</div>
							<div class="input-list">
								<input id="regularInvestMin" type="text"
									name="regularInvestMin" value="${regularInvestMin}"
									placeholder="定期投资最小金额"> -- <input
									id="regularInvestMax" type="text" name="regularInvestMax"
									value="${regularInvestMax}" placeholder="定期投资最大金额">
							</div>
							<div class="input-list">
								<input id="minRedPackCount" type="text" name="minRedPackCount"
									value="${minRedPackCount}" placeholder="最小红包可用数"> --
								<input id="maxRedPackCount" type="text" name="maxRedPackCount"
									value="${maxRedPackCount}" placeholder="最大红包可用数">
							</div>
							<div class="input-list">
								<input id="minRateCount" type="text" name="minRateCount"
									value="${minRateCount}" placeholder="最小加息券可用数"> -- <input
									id="maxRateCount" type="text" name="maxRateCount"
									value="${maxRateCount}" placeholder="最大加息券可用数">
							</div>
								<div class="input-list">
								<input id="minInvestMoney" type="text" name="minInvestMoney"
									value="${minInvestMoney}" placeholder="定期投资总额 "> -- <input
									id="maxInvestMoney" type="text" name="maxInvestMoney"
									value="${maxInvestMoney}" placeholder="定期投资总额 ">
							</div>
							<div class="input-list">
								<span class="user">实名认证：</span>
								<!--  <span class="btn-select"
								id="btn_select"> <span class="cur-select">全部</span> -->
								<select id="mySelect" name="real"
									style="width:120px;float:left;border:1px solid #ccc;height:28px;line-height:28px;border-radius:2px;margin-left:5px;">
									<option value="all">全部</option>
									<option value="yes">是</option>
									<option value="no">否</option>
								</select> 
								<script type="text/javascript">
									var v = '${real}';

									var obj = document
											.getElementsByTagName("option");
									for (var i = 0; i < obj.length; i++) {
										if (obj[i].value == v) {
											obj[i].selected = true; //相等则选中
										}
									}
								</script>



								<div class="input-list">
									<input type="button" id="query" value="查询" class="but-search"
										onclick="a('1');" />
								</div>
							</div>
							</br>
						</div>
			</form>
			<script type="text/javascript">
	function getBack(id){
		$.ajax({
			type:"post",
			url:"/userData/toShowUserDataBack",
			data:{"id":id},
			dataType : "json",
			success:function(data){
				var rule = "";
				for (var int = 0; int < data.length; int++) {
					var obj = data[i];
					rule += obj.time+" admin: ";
					rule += obj.admin+" remark: ";
					rule +=obj.remark+" ";
				}
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
			});
		}
	function getRedPackageRule(id){
		getBack(id);
	}
</script>
			<div class="box">
			<div id="rule"></div>
					<div class="row">
						<table class="table table-hover">
							<tr >
								<td style="width:50px;" >序号</td>
								<td style="width:100px;">姓名</td>
								<td style="width:200px;" >注册时间</td>
								<td style="width:200px;">来源</td>
								<td style="width:200px;" >最后登录时间</td>
								<td style="width:150px;">账户总资产</td>
								<td style="width:150px;">活期在投</td>
								<td style="width:150px;">定期在投</td>
								<td style="width:150px;" >可用余额</td>
								<td style="width:150px;">是否实名</td>
							</tr>
							<c:forEach items="${pageInfo.results}" var="userInfo" varStatus="Num" >	
							<tr>
								<td>${Num.index+1}</td>
								<td>${userInfo.name}</td>
								<td>${userInfo.registerTime}</td>
								<td>${userInfo.userSource}</td>
								<td>${userInfo.lastLoginTime}</td>
								<c:choose>
									<c:when test="${userInfo.userAllMoney<0}">
										  <td>0</td>
									</c:when>
									<c:otherwise>
										<td><fmt:formatNumber type="number" value="${userInfo.userAllMoney}" pattern="0.00" maxFractionDigits="2"/></td>
									</c:otherwise>
								</c:choose>
								<td>${userInfo.liveInvestMoney}</td>
								<td>${userInfo.regularInvestMoneyAgain}</td>
								<td>${userInfo.usedMoney}</td> 
								 <td>${userInfo.isRealName}<input id="userId" value="${userInfo.id}" type="hidden"/></td> 
							</tr>
						</c:forEach>
						</table>
					<p><font color="#FF0000">${error}</font></p> 
					<table class="table table-hover">
							<tr class="data-box-title1" >
								<td>注册总人数：  ${userSourceData.userCount}</td>
								<td>实名总人数：  ${userSourceData.realNameCount}</td>
								<td>投资总人数：  ${userSourceData.investCount}</td>
								<td>活期投资总人数：  ${userSourceData.liveInvestCount}</td>
								<td>定期投资总人数：  ${userSourceData.regularInvestCount}</td>
									<c:choose>
									<c:when test="${userSourceData.allMoney<0}">
										  <td>0</td>
									</c:when>
									<c:otherwise>
									<td>账户资产总额： <fmt:formatNumber type="number" value="${userSourceData.allMoney}" pattern="0.00" maxFractionDigits="2"/></td>
									</c:otherwise>
								</c:choose>
								<td>活期在投总额：  ${userSourceData.liveInvestMoney}</td>
								<td>定期总额： <fmt:formatNumber type="number" value="${userSourceData.regularInvestMoney}" pattern="0.00" maxFractionDigits="2"/></td>
								<td>定期在投金额：  ${userSourceData.investIngMoney}</td>
								<td>账户可用金额：  ${userSourceData.userUsedMoney}</td>
							</tr>
					</table>

					
				<div class="table-pages row">
						<span class="pages-left">共有${pageInfo.totalRecord}条，每页显示
							${pageInfo.pageSize}条</span> <span class="pages-all"> <c:if
								test="${pageInfo.hasPreviousPage}">
								<a href="javascript:a(${pageInfo.pageNo-1})"><<</a>
							</c:if> <c:forEach var="i" begin="1" end="${pageInfo.totalPage}">
								<a <c:if test="${i==pageInfo.pageNo}"> class="cur"</c:if>
									href="javascript:a(${i})">${i}</a>
							</c:forEach> <c:if test="${pageInfo.hasNextPage}">
								<a href="javascript:a(${pageInfo.pageNo+1})">>></a>
							</c:if>
						</span>

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
