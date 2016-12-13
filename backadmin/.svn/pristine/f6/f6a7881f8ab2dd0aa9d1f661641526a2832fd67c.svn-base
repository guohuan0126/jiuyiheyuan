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
<title>账户资金统计</title>

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
					<i class="fa fa-table"></i>当前位置：账户资金统计
				</h2>				
			</div>
			<div class="contentpanel">

				<div class="panel panel-default">
				
				<base:active activeId="CheckAccountant_Action">
					<div class="panel-heading">
						<form class="form-inline" action="/accountInfo/initiativeCheckAccount"
							method="post">
							<button type="submit" class="btn btn-primary">主动核对账户</button>
						</form>
					</div>
					</base:active>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>
								<tr>
									<th>用户数量</th>
									<th>实名用户总人数</th>
									<th>投资人数</th>
									<th>24小时内注册人数</th>
									<th>24小时内实名认证人数</th>
									<th>24小时内提现金额</th>
									<th>24小时内充值金额</th>
									<th>易宝账户</th>
									<th>检查时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="accountCount">
									<tr>
										<td>${accountCount.allUserNums}</td>
										<td>${accountCount.allInvestorsNums}</td>
										<td>${accountCount.allinvestNums}</td>
										<td>${accountCount.userNumsPerDay}</td>
										<td>${accountCount.accountUserNums}</td>
										<td><fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountCount.sumWithdrawMoneyPerDay}" /></td>
										<td><fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountCount.sumRechargeMoneyPerDay}" /></td>
										<td>账户总额<fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2" value="${accountCount.sumEbaoBalance}" />
											<br /> 可用总额<fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountCount.sumEbaoAvailableAmount}" /><br />
											冻结总额<fmt:formatNumber minIntegerDigits="1"
												minFractionDigits="2"
												value="${accountCount.sumEbaoFreezeAmount}" />
										</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
												value="${accountCount.time}" /></td>
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
