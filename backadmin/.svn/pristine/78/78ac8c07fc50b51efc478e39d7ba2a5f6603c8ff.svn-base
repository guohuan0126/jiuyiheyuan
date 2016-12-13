<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>资金管理</title>
<style type="text/css">
.ui-datepicker-title {
	text-transform: uppercase;
	font-family: 'LatoBold';
	color: #1CAF9A;
}

.ui-datepicker {
	background: #1D2939;
	margin-top: 1px;
	border-radius: 2px;
	width: 280px;
}

.ui-datepicker-next {
	background: url(../../images/calendar-arrow.png) no-repeat 10px 5px;
}

.ui-datepicker-prev {
	background: url(../../images/calendar-arrow.png) no-repeat 8px -80px;
}

.ui-datepicker table {
	color: #ffffff;
}
</style>
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
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：账户余额不平衡的用户信息
				</h2>				
			</div>

			<div class="contentpanel">

				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" action="/found/accountCheckingList"
							method="post">
							<div class="input-group">
								<input type="text" class="form-control" name="start"
									value="${startTime}" placeholder="开始时间" id="datepicker">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>
							--
							<div class="input-group">
								<input type="text" class="form-control" name="end"
									value="${endTime}" placeholder="结束时间" id="datepicker1">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>
							<button type="submit" class="btn btn-primary">查询</button>
						</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>用户ID</th>
									<th>本地账户余额</th>
									<th>本地可用余额</th>
									<th>本地冻结金额</th>
									<th>易宝账户余额</th>
									<th>易宝可用余额</th>
									<th>易宝冻结金额</th>
									<th>检查时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="accountChecking">
									<tr>
										<td>${accountChecking.userId}</td>
										<td><fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountChecking.balance}" /></td>
										<td><fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountChecking.availableAmount}" /></td>
										<td><fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountChecking.freezeAmount}" /></td>
										<td><fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountChecking.ebaoBalance}" /></td>
										<td><fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountChecking.ebaoAvailableAmount}" /></td>
										<td><fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountChecking.ebaoFreezeAmount}" /></td>
										<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
												value="${accountChecking.time}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<%@ include file="../base/page.jsp"%>

					</div>
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
			// Date Picker
			jQuery('#datepicker').datepicker();
			jQuery('#datepicker1').datepicker();
			jQuery('#datepicker-inline').datepicker();

			jQuery('#datepicker-multiple').datepicker({
				numberOfMonths : 3,
				showButtonPanel : true
			});
			$("#st").val('${status}');
		});
	</script>
</body>
</html>
