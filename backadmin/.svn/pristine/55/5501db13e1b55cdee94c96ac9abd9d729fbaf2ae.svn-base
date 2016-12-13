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
<title>富友单笔业务查询</title>
</head>
<body>
	<!-- Preloader -->
	<div id="preloader">
		<div>
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>

			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：富友单笔业务查询
				</h2>
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" action="/payMent/tradeFuiouQuery"
							method="post">
							<div class="form-group">
								<input type="text" name="orderId" value="${orderId}" 
									class="form-control" id="orderId" placeholder="流水号">
							</div>

							<button type="submit" class="btn btn-primary">查询</button>
						</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>
								<tr>
									<th>流水号</th>
									<th>用户ID</th>
									<th>姓名</th>
									<th>金额</th>
									<th>请求时间</th>
									<th>状态</th>
									<th>确认时间</th>
									<th>银行卡号</th>
								</tr>
							</thead>
							<tbody>
									<tr>
										<td>${orderId}
										</td>
										<td>${userId}
										</td>
										<td>${realname}
										</td>
										<td><c:if test="${record.money > 0 }">
												<fmt:formatNumber currencySymbol="￥" type="currency" value="${record.money}" />
											</c:if>
										</td>
										<td>
										<fmt:formatDate value="${record.requestTime}" type="both"/>
										</td>
										<td>${message }</td>
										<td>
										<fmt:formatDate value="${record.responseTime}" type="both"/>
										</td>
										<td>${record.cardNo}
										</td>
									</tr>
							</tbody>
						</table>
					</div>
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
