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

<title>用户账户 列表</title>


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
					<i class="fa fa-table"></i>当前位置：用户账户列表
				</h2>

			</div>

			<div class="contentpanel">

				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" action="/account/userAccountList"
							method="post">
							<div class="form-group">
								<label class="sr-only" for="exampleInputPassword2">编号/手机号</label> <input
									type="text" name="userId" value="${userId }"
									class="form-control" id="userId" placeholder="用户编号/手机号/邮箱/身份证号">
							</div>
							<div class="form-group">
								<label class="sr-only" for="exampleInputPassword2">用户名</label> <input
									type="text" name="realname" value="${realname }"
									class="form-control" id="realname" placeholder="用户名">
							</div>
							<button id="submit" type="submit" class="btn btn-primary">查询</button>
							<%-- <base:active activeId="USER_ADMIN">
								<button type="button" id="add" class="btn btn-primary">管理员干预</button>
							</base:active> --%>
						</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>
								<tr>
									<th>用户编号</th>
									<th>用户姓名</th>
									<th>账户余额</th>
									<th>可用余额</th>
									<th>冻结金额</th>
									<th>自动投标标识</th>
									<th>自动还款标识</th>
									<th>最近操作时间</th>
								</tr>
							</thead>
							<tbody id="treet1">
								<c:forEach items="${pageInfo.results}" var="item">
									<tr>
										<td>${item.userId }</td>
										<td>${item.realname }</td>
										<td><fmt:formatNumber value="${item.balance }" type="currency"/></td>
										<td><fmt:formatNumber value="${item.availableBalance }" type="currency"/></td>
										<td><fmt:formatNumber value="${item.freezeAmount }" type="currency"/></td>
<%-- 										<td>${item.balance }</td>
										<td>${item.availableBalance }</td>
										<td>${item.freezeAmount}</td> --%>
										<td>${item.autoInvest}</td>
										<td>${item.autoRepay}</td>
										<td><fmt:formatDate value="${item.time}" type="both" /></td>
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
	<script type="text/javascript">
		//添加按钮
		$("button[id=add]").click(function() {
			/* window.location.href="/userbill/toadd"; */
			window.open("/userbill/toadd");
		});
	</script>
</body>
</html>