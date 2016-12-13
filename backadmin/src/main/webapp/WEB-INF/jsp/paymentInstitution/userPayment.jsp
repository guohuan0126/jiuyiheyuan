<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="${ctx}/images/favicon.png"
	type="image/png">

<title>新增用户通道令牌列表</title>
<script type="text/javascript">
	
	
	function getReason1(rule){
		$('#rule').dialogBox({
			width: 500,
			height: 300,
			title: '备注信息',
			hasClose: true,
			content: rule
		});
	}
	

</script>
</head>
<body>
	<!-- Preloader -->
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<script src="/js/jquery.dialogBox.js"></script>
	<link rel="stylesheet" href="/css/jquery.dialogbox.css" />
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：新增用户通道令牌列表
				</h2>
			</div>

		<form class="form-inline" id="form1"  action="/payMent/UserPayment" method="post">		    	 
	     <div class="contentpanel">
	       	<input type="text"  name="userId" value="${userId}" class="form-control" placeholder="用户编号/手机号" id="userId">
	      <div class="input-group" >
                <input type="text" class="form-control" name="startTime" value="${startTime}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="endTime" value="${endTime}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
	   		    </div>
	          </form>
			<script type="text/javascript">	
			  jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
					<div class="panel-body">
						<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>用户编号</br>
										用户姓名</br>
										<base:active activeId="USER_LOOK">手机号</base:active>
									</th>
									<th>用户令牌</th>
									<th>第三方通道</th>
									<th>绑卡时间</th>
								</tr>
							</thead>
								<c:forEach var="paymentUserRelation" items="${pageInfo.results}">
								<tr>
								    <td>
								    	${paymentUserRelation.userId}</br>
								    	${paymentUserRelation.userName}</br>
								    	<base:active activeId="USER_LOOK">${paymentUserRelation.mobileNumber}</base:active>
								    </td>
								    <td>${paymentUserRelation.token}</td>
								    <td>${paymentUserRelation.paymentId}</td>
								   <td>
								    <fmt:formatDate value="${paymentUserRelation.createTime}" type="both"/>
								    </td>
								</tr>
							</c:forEach>	
						</table> 
					</div>
					<%@ include file="../base/page.jsp"%>
					<!-- panel-body -->
				</div> 
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>


</body>
</html>


