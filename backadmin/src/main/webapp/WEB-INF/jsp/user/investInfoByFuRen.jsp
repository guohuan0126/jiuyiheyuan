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
<link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />

<title>辅仁用户投资信息</title>

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
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：辅仁用户投资信息
				</h2>
			</div>

			<div class="contentpanel">

				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" id="form1" method="post">
							<div class="form-group">
								<input type="text"
									class="form-control" placeholder="用户编号/手机号"
									name="userId" value="${userId}" id="userId"> 
							</div>
							<button type="button" id="search" class="btn btn-primary">查询</button>
							<button type="button" onclick="exportNow()" id="export" class="btn btn-primary">导出数据</button>
							<div id="erroruser" style="display: none;">查询内容不能为空</div>
						</form>
					</div>
				<script type="text/javascript">
					//导出已备注的用户信息
					function exportNow(){
					window.location.href="/user/InvestByFuRen";
					}	
					</script>
					<script type="text/javascript">
						var d = new Date();
						d.setDate(d.getDate() - 1);
						jQuery('#start').datetimepicker({
							dateFormat : 'yy-mm-dd',
						});
					</script>
					<script type="text/javascript">
					var d = new Date();
					d.setDate(d.getDate() - 1);
					jQuery('#end').datetimepicker({
						dateFormat : 'yy-mm-dd',
					});
					</script>
					
					
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>用户编号</th>
									<th>用户姓名</th>
									<th>用户性别</th>
									<th>用户年龄</th>
									<base:active activeId="PhoneForFuren"><th>用户手机号</th></base:active>
									<th>定期在投金额</th>
									<th>投资总额</th>
									<th>活期宝本金</th>
								</tr>
							</thead>
								
								<c:forEach items="${pageInfo.results}" var="userByFuRen">
									<tr>
										<td>${userByFuRen.id}</td>
										<td>${userByFuRen.realName}</td>
										<td>${userByFuRen.sex}</td>
										<td>${userByFuRen.age}</td>
										<base:active activeId="PhoneForFuren"><td>${userByFuRen.mobileNumber}</td></base:active>
										<td>${userByFuRen.investIngmoney}</td>
										<td>${userByFuRen.sumMoney}</td>
										<td>${userByFuRen.demandMoney}</td>
									</tr>
								</c:forEach>
						</table>
					</div>
					     <input type="hidden" id="userpid">
     						<input type="hidden" id="qq">
					<%@ include file="../base/page.jsp"%>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>

	<script>
		jQuery(document).ready(function() {
			if ('${stype}' != '') {
				$("#st").val('${stype}');
			}
			$('#st').change(function() {
				$("#content").val('');
			});

		});
		$("button[id=search]").click(
				function() {
					if ($("#content").val() == '') {
						$('#erroruser').attr("style",
								"display:block;font-size:14px;color:red");
						return false;
					} else {
						$('#erroruser').attr("style",
								"display:none;font-size:14px;color:red");
						$("#form1").attr("action", "/userInvest/InvestByFuRen");
						$("#form1").submit();
					}
				});
	</script>
<script type="text/javascript">
					function edit(id,qq){
						var userid=id;
						$("#userpid").val(userid);
						//$("#qq").val(qq);
						ds.dialog({
							   title : "编辑", 
							   content : '<iframe  src="/tormark.jsp" width="100%" height="100%"></iframe>',
							   width:800,
							   height:700
							});
					}
					</script>
</body>
</html>


