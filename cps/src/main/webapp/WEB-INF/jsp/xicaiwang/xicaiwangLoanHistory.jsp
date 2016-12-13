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

<title>希财网推送标的记录</title>

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
					<li>当前位置：希财网推送标的记录</li>
				</ul>
				<br/>
				<form id="searchForm"  action="${ctx}/xicaiwangManager/getPushLoanHistory"	method="get">
				<input type="hidden" id="pageNo" name="pageNo" >
				<table class="input-list" border="0" width="80%" cellspacing="10" cellpadding="5">
					<tr>
						<td width="20%">
							<input  id="loanId" type="text" name="loanId" value="${loanId}" placeholder="项目编号"  style="width:190px; " >
						</td>
						<td width="45%">
							<span>项目提交时间：</span>
							<input id="loanTimeBegin" type="text" class="datepicker" name="timeBegin" value="${timeBegin}" placeholder="始时间" style="width:150px;"> --
							<input id="loanTimeEnd" type="text" class="datepicker"  name="timeEnd"  value="${timeEnd }" placeholder="结束时间" style="width:150px;">
						</td>
						<td width="17%">
							<span >项目状态：</span>
							<select id="loanStatus" name="loanStatus">
								<option value="0" >请选择</option>
								<option value="还款中">还款中</option>
								<option value="筹款中">筹款中</option>
								<option value="等待复核">等待复核</option>
								<option value="完成">完成</option>
								<option value="流标">流标</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" id="loanName" name="loanName" value="${loanName}" placeholder="项目名称" style="width:190px;">	
						</td>
						<td>
							<span>项目推送时间：</span>
							<input id="pushTimeBegin" name="pushTimeBegin" value="${pushTimeBegin}" type="text" class="datepicker"  placeholder="始时间" style="width:150px;"> --
							<input id="pushtimeEnd"   name="pushtimeEnd"  value="${pushtimeEnd}" type="text" class="datepicker"    placeholder="结束时间" style="width:150px;">
						</td>
						<td>
							<span >项目类型：</span>
							<select id="loanType" name="loanType"  style="width:100px;">
								<option value="0" selected="selected">请选择</option>
								<option value="车贷">车贷</option>
								<option value="金农宝">金农宝</option>
								<option value="供应宝">供应宝</option>
								<option value="房贷">房贷</option>
								<option value="企业贷">企业贷</option>
							</select>
						</td>
						<td >
							<button id="searchBtn" class="buttonStyle"  type="submit"  style="display:block;width:120px;height:40px;background:#3499df">查询</button>
						</td>
					</tr>
				</table>
				</form>
			
			<script type="text/javascript">
							
							$("#loanStatus").val('${!empty loanStatus ? loanStatus:'0'}');
							$("#loanType").val('${!empty loanType ? loanType:'0'}');
							
                         	$(".datepicker").datepicker({
                        		
                        		dateFormat:'yy-mm-dd'
                        	});
								
			</script>
							
				<form  action="${ctx }/xicaiwangManager/getPushLoanHistory"	method="post" id="f1">
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
									<td>推送时间</td>
									<td>更新时间</td>
									<!-- <td>操作</td> -->
									
								</tr>
								<c:forEach items="${pageInfo.results}" var="loan" varStatus="Num">
								<tr>
								
									<td></td>
									<td>${loan.id}
					 <c:if test="${loan.newbieEnjoy == '是'}"><img src="${ctx}/images/newbie.png"></c:if>
					 <c:if test="${loan.organizationExclusive == '是'}"><img src="${ctx}/images/org.png"></c:if>
					 </td>
									<td>${loan.name}
				 <c:if test="${not empty loan.emailSend and loan.emailSend != '' }"><span style="color:red; font-size:12px;">(${loan.emailSend})</span> </c:if>
				 </td>
									 <td>
					 <c:if test="${'天' eq loan.operationType }">天标  | ${loan.repayType}
					 	<c:choose>
						 <c:when test="${'是' eq loan.beforeRepay and not empty loan.symbol}"> | 可提前还款 ${loan.symbol}天</c:when>
						 <c:otherwise>
							 | 不可提前还款
						 </c:otherwise>
						 </c:choose>
					 </c:if>
					 <c:if test="${'月' eq loan.operationType }">月标 | ${loan.repayType} </c:if>
				 </td>
				
				 <td>				 
				 <c:if test="${'月' eq loan.operationType }">
							${loan.deadline }个月
						</c:if>
						<c:if test="${'天' eq loan.operationType }">
							<c:choose>
								<c:when test="${'是' eq loan.beforeRepay and not empty loan.symbol}">
									${loan.day + loan.symbol }天
								</c:when>
								<c:otherwise>
									${loan.day }天
								</c:otherwise>
							</c:choose>													
					</c:if>/
					<fmt:formatNumber value="${loan.ratePercent }" pattern="#,##0.##"/>%
				 </td>						 								 
				 <td>${loan.borrowMoneyUserID}<br>${loan.borrowMoneyUserName}</td>
				 <td><fmt:formatNumber value="${loan.totalmoney}" type="currency"/></td>
				 <td>${loan.status}</td>
				 <td><fmt:formatDate value="${loan.commitTime}" type="both"/></td>
				 <td><fmt:formatDate value="${loan.pushTime}" type="both"/></td>
				 <td><fmt:formatDate value="${loan.updateTime}" type="both"/></td>
								</tr>
								
								</c:forEach>
								
								<tr></tr>
							</table>							
							<%@ include file="../base/pageInfo.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</form>
	<script>
	navList(10);
		
		</script>

</body>
</html>
