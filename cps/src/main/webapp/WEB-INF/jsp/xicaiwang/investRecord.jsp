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

<title>希财网的投资记录</title>

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
					<li>当前位置：希财网用户投资的记录</li>
				</ul>
				<br/>
				<form action="/xicaiwangManager/investInfo" method="get">
				<table class="input-list" border="0" width="80%" cellspacing="10" cellpadding="5">
					<tr>
						<td>
						<input type="text" id="investId" value="${investId}" name="investId" placeholder="投资编号" style="width:190px; " >
						</td>
						<td>
							<input type="text" id="loanId" value="${loanId}" name="loanId" placeholder="项目编号" style="width:190px;">	
						</td>
						<td>
							<input type="text" id="userId" value="${userId}" name="userId" placeholder="用户编号" style="width:190px; " >
						</td>
						
						<td>
							<input id="investTimeBegin" value="${investTimeBegin}"  name="investTimeBegin" type="text" class="datepicker" placeholder="投资开始时间">
						</td>
						<td>
							<input id="investTimeEnd"   value="${investTimeEnd}"  name="investTimeEnd" type="text" class="datepicker"   placeholder="投资结束时间">
						</td>
						
						<td>
							<select id="userType" name="userType">
								<option value="">--全部用户类型--</option>
	                  			<option value="0">新用户</option>
	                  			<option value="1">老用户</option>
	                  			<option value="3">希财网平台投资</option>
							</select>	
						</td>
						<td >
							<input type="submit" class="buttonStyle"  style="display:block;width:120px;height:40px;background:#3499df" value="查询"/>
							
						</td>
						<td>
							<button type="button" class="buttonStyle" style="display:block;width:120px;height:40px;background:#3499df" onclick="exportInvestBill()">下载</button>
						</td>
					</tr>
				</table>
				</form>
			
			<script type="text/javascript">
			$(".datepicker").datepicker({
                        		dateFormat:'yy-mm-dd'
                        	});
			
			
			//导出已备注的用户信息
			function exportInvestBill(){
				var url="/xicaiwangManager/exportUserInvestInfoXicai";
				var investId=$("#investId").val();
				var loanId=$("#loanId").val();
				var userId=$("#userId").val();
				var investTimeBegin=$("#investTimeBegin").val();
				var investTimeEnd=$("#investTimeEnd").val();
				var userType=$("#userType").val();
				var str = "";
				if(investId!=""){
					str+="&investId="+investId;
				}
				if(loanId!=""){
					str+="&loanId="+loanId;
				}
				if(userId!=""){
					str+="&userId="+userId;
				}
				if(investTimeBegin!=""){
					str+="&investTimeBegin="+investTimeBegin;
				}
				if(investTimeEnd!=""){
					str+="&investTimeEnd="+investTimeEnd;
				}
				if(userType!=""){
					str+="&userType="+userType;
				}
				if(str!=""){
					str = "?"+str.substring(1);
				}
				window.location.href=url+=str;
			}
			
			
			var v = '${userType}';

			var obj = document
					.getElementsByTagName("option");
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].value == v) {
					obj[i].selected = true; //相等则选中
				}
			}
			
								
			</script>
							
			</div>
					<div class="box">
					<div id="rule"></div>
						<div class="box-table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="table-tit">
									<!-- <td>投资编号</td> -->
									<td>项目编号</td>
									<td>项目名称</td>
									<td>用户编号</td>
									<td>用户姓名</td>
									<td>投资金额</td>
									<td>项目周期</td>
									<td>投资时间</td>
									<!-- <td>年化利率</td>
									<td>预期收益</td> -->
									<td>投资状态</td>
									<td>投标来源</td>
									<td>用户来源</td>
									<td>优惠券</td>
									<td>活动名称</td>
									<td>活动ID</td>
									<!-- <td>手机号</td> -->
								</tr>
								<c:forEach items="${pageInfo.results}" var="investRecords" varStatus="Num">
								<tr>
									<%-- <td>${investRecords.investId}</td> --%>
									<td>${investRecords.loanId}</td>
									<td>${investRecords.loanName}</td>
									<td>${investRecords.userId}</td>
									<td>${investRecords.userName}</td>
									<td>${investRecords.investMoney}</td>
									<!-- 项目周期 -->
									<td>
										<c:if test="${investRecords.operationType=='月'}">${investRecords.deadline}月</c:if>	
										<c:if test="${investRecords.operationType=='天'}">${investRecords.days}天</c:if>		
									</td>
									<td>
										<fmt:formatDate value="${investRecords.investTime}" type="both"/>
									</td>
									<%-- <td>
										<fmt:formatNumber type="number" value="${investRecords.rate}" pattern="0.00" maxFractionDigits="2"/>  
									</td>
									<td>
										<fmt:formatNumber currencySymbol="￥" type="currency" value="${investRecords.interest}" />
									</td> --%>
									<td>${investRecords.investStatus}</td>
									<%-- <td>${investRecords.mobileNumber}</td> --%>
									<td>${investRecords.userSource}</td>
									<td>${investRecords.infoUserSource }</td>
									<td>
										<c:if test="${investRecords.redType=='money'}">${investRecords.redMoney}元</c:if>	
										<c:if test="${investRecords.redType=='rate'}">${investRecords.redRate*100}%</c:if>	
									</td>
									<td>${investRecords.activityName}</td>
									<td>${investRecords.activityId}</td>
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
