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

<title>平台易宝转账个人易宝记录列表</title>
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
					<i class="fa fa-table"></i>当前位置：平台易宝转账个人易宝记录列表
				</h2>
			</div>

		<form class="form-inline" id="form1"  action="/payMent/CompanyYeepayTransferUserYeepay" method="post">		    	 
	     <div class="contentpanel">
	       	<input type="text"  name="userId" value="${userId}" class="form-control" placeholder="用户编号/手机号" id="userId">
	       	<input type="text"  name="markId" value="${markId}" class="form-control" placeholder="流水编号" id="markId">
	      <div class="input-group" >
                <input type="text" class="form-control" name="startTime" value="${startTime}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="endTime" value="${endTime}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                <div class="input-group" >
                	<select id="payMentSelect" name="payMentSelect">
                		<option value="">渠道名称</option>
                		<c:forEach var="payMent" items="${payMentList}">
                			<option value="${payMent.code}">${payMent.name}</option>
                		</c:forEach>
                	</select>
                </div>
                
                
                <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
	   		    </div>
	          </form>
			<script type="text/javascript">	
			$("#loanTerm").val('${loanTerm}');
			$("#forkStatus").val('${forkStatus}');
			$("#payMentSelect").val('${payMentSelect}');
			  jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
					<div class="panel-body">
						<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>易宝转账流水号</th>
									<th>金额</th>
									<th>用户编号</br>
										真实姓名</br>
									<base:active activeId="USER_LOOK">手机号</base:active>
									</th>
									<th>错误信息</th>
									<th>状态</th>
									<th>支付机构</th>
									<th>时间</th>
								</tr>
							</thead>
								<c:forEach var="companyYeepayTransferUserYeepay" items="${pageInfo.results}">
								<tr>
								    <td>${companyYeepayTransferUserYeepay.requetsNo}</td>
								    <td>${companyYeepayTransferUserYeepay.money}</td>
								    <td>
								    	${companyYeepayTransferUserYeepay.userId}</br>
								    	${companyYeepayTransferUserYeepay.userName}</br>
								    	<base:active activeId="USER_LOOK">${companyYeepayTransferUserYeepay.mobileNumber}</base:active>
								    </td>
								    
								    
									<c:if test="">
								    
								    </c:if>
								    <c:choose>
								    	<c:when test="${! empty companyYeepayTransferUserYeepay.msg}">
										    <td>
										    <textarea rows="8" cols="50" readonly="readonly">${companyYeepayTransferUserYeepay.msg}</textarea>
										    </td>
								    	</c:when>
								    	<c:otherwise>
								    		<td></td>
								    	</c:otherwise>
								    </c:choose>
								    <td>${companyYeepayTransferUserYeepay.status}</td>
								   	<td>${companyYeepayTransferUserYeepay.payMentId}</td>
								   <td>
								    <fmt:formatDate value="${companyYeepayTransferUserYeepay.createTime}" type="both"/>
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


