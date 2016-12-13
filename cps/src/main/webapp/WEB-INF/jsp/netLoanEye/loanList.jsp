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

<title>推送管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
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
					<li>当前位置：待推送天眼项目列表 </li>
				</ul>
				<br/>
				<form class="form-inline" action="${ctx }/netLoanEye/waitPushLoanList"	method="post" id="f1">
				</form>
			</div>
			<div class="box">
				<div id="rule"></div>
				<div class="box-table">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				            <tr class="table-tit">              
				                <th >编号</th>
				                <th >项目名称</th>
				                <th >还款方式</th>                
				                <th >周期/利率</th>              
				                <th >借款人</th>
				                <th >借款总金额</th>              
				                <th >项目状态</th>
				                <th >项目发起时间</th>              
				                <th >操作</th>
				             </tr>
							  <c:forEach  var="loan" items="${pageInfo.results}">
							     <tr class="font">		     	
									 <td>${loan.id}
										 <c:if test="${loan.newbieEnjoy == '是'}"><img src="${ctx}/images/newbie.png"></c:if>
										 <c:if test="${loan.organizationExclusive == '是'}"><img src="${ctx}/images/org.png"></c:if>
									 </td>
									 <td>${loan.name}
									 <c:if test="${not empty loan.emailSend and loan.emailSend != '' }"><span style="color:red; font-size:12px;">(${loan.emailSend})</span> </c:if></td>
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
									 <td>
									 	<a href="javascript:void(0);" onclick="pushNetLoan('${loan.id}');">推送</a> 
									 </td>
							     </tr>
							 </c:forEach>
					</table>		
					<%@ include file="../base/pageInfo.jsp"%>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
//推送天眼项目
	function pushNetLoan(id){
		if( confirm("是否向网贷天眼推送此项目？") ){
			$.ajax({				
				type : 'POST',
				url : '/netLoanEye/pushNetLoan',
				data:{
					id:id
				},
				success : function(data) {
					alert(data.result);
					if( data.result=='success' ){
						alert("推送成功！");
						location.reload();
					}else{
						alert("推送失败，请重试！");
						location.reload();
					}
				},
				error:function(){
					alert("操作失败！");
					location.reload();
				}
			});
		}
	}
</script>
	<script>
		navList(10);
	</script>
	
</body>
</html>
