<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<title>个人优惠券查询</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<script type="text/javascript">

	
function a(pageNo){
		
		document.getElementById("myFrom").action="/showCouponByPerson?pageNo="+pageNo;
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
					<li>个人优惠券查询</li>
				</ul>
			</div>
			<form  method="post" id="myFrom" >
			<div class="main">
				<div class="bound">
					<div class="box">
						<div class="input-list">
							用户ID<input id="UserID" type="text" name="userId" value="${userId}">
						</div>
					
						<div class="input-list">
							<span class="user"></span> <!--  <span class="btn-select"
								id="btn_select"> <span class="cur-select">全部</span> -->
								
								<select id="mySelect" name="type" style="width:120px;float:left;border:1px solid #ccc;height:28px;line-height:28px;border-radius:2px;margin-left:5px;">  
      								<option value="all" >全部</option>  
      								<option value="used">已使用</option>  
     								<option value="unused">未使用</option>  
      								<option value="expired">已过期</option>  
   								 </select> 
							</span>
							<script type="text/javascript">
							
								var v = '${type}';
							
								 
							 var obj = document.getElementsByTagName("option");
								 for(var i=0;i<obj.length;i++){
										if(obj[i].value==v){
											obj[i].selected=true;  //相等则选中
										}
									}
							</script>
							   
						</div>
						<div class="input-list">
							活动ID<input type="text" name="activeId" id="activeId" value="${activeId}">
						</div>
						<div class="input-list">
							<input type="button" id="query" value="查询" class="but-search" onclick="a('1');"/>
						</div>
						
					</div>
						<p>
							<label> <input type="radio" name="status"
								value="1" checked="checked" id="RadioGroup1_0"> 现金红包
							</label>
						</p> 
						</form>
					<p><font color="#FF0000">${error}</font></p>  
					<div class="box">
						<div class="box-table">
						
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="table-tit">
									<td>序号</td>
									<td>活动ID</td>
									<td>红包金额</td>
									<td>发布日期</td>
									<td>截止日期</td>
									<td>红包状态</td>
									<td>使用日期</td>
									<td>使用门槛</td>
									<td>投资金额</td>
								</tr>
								<c:forEach items="${pageInfo.results}" var="couponByPerson" varStatus="Num" >
								<tr>
									<td>${Num.index+1}</td>
									<td>${couponByPerson.id}</td>
									<td>${couponByPerson.redPackageMoney}</td>
									<td>${couponByPerson.sendData}</td>
									<td>${couponByPerson.endData}</td>
									<td>${couponByPerson.redPackageStauts}</td>
									<td>${couponByPerson.usedData}</td>
									<td>${couponByPerson.usedRule}</td>
									<td>${couponByPerson.investMoney}</td>
								</tr>
								</c:forEach>
								<tr>   
									<td>红包总数： ${userInfo.redCount}</td>
									<td>使用总数：${userInfo.usedCount}</td>
									<td>投资总额： ${userInfo.investMoney}</td>
									<td> 红包领取金额 ： ${userInfo.sendedMoney}</td>
									<td>当前账户可用余额：${userInfo.userMoney}</td>
									
									<c:choose>
									<c:when test="${userInfo.allMoney<0}">
										  <td>当前账户总资产：0</td>
									</c:when>
									<c:otherwise>
										<td><td>当前账户总资产：${userInfo.allMoney}</td></td>
									</c:otherwise>
								</c:choose>
									
									
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
