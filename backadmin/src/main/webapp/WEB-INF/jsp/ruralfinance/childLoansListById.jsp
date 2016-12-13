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

<title>子标信息列表</title>
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
					<i class="fa fa-table"></i>当前位置：子标信息列表
				</h2>
			</div>
					<div class="panel-body">
						<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>编号</th>
									<th>项目id</th>
									<th>金额</th>
									<th>期限</th>
									<th>是否打包</th>
									<th>创建时间</th>
									<th>备注信息</th>
								</tr>
							</thead>
								<c:forEach var="childLoansList" items="${pageInfo.results}">
								<tr>
								   <td>${childLoansList.id}</td>
									<td>${childLoansList.loanId}</td>
									<td>${childLoansList.money}</td>	
									<td>${childLoansList.loanTerm}个月</td>	
									<td>
									<c:if test="${childLoansList.packing==0}">未打包</c:if>
								    <c:if test="${childLoansList.packing==1}">已打包</c:if>
									</td>	
									<td>
									 <fmt:formatDate value="${childLoansList.createTime}" type="both"/></td>
									</td>	
									<td>
									<a href="javascript:getReason1('${childLoansList.remark}');">备注</a>
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


