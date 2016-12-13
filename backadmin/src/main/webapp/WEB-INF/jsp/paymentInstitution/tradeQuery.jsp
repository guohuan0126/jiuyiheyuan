﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<title>京东单笔业务</title>
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
					<i class="fa fa-table"></i>当前位置：京东单笔业务
				</h2>
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" action="/payMent/tradeQueryByChannelType"
							method="post">
							<div class="form-group">
								<input type="text" name="orderId" value="${orderId}" 
									class="form-control" id="orderId" placeholder="流水号">
							</div>
							
							<div class="form-group">
								<select name="channelType" id="st" >
									<option value="BaoFoo">宝付</option>
									<option value="JDpay">京东</option>
								</select>
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
									<th>代付状态</th>
									<th>确认时间</th>
									<th>银行编号</th>
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
										<td><c:if test="${map.trade_amount > 0 }">
												<fmt:formatNumber currencySymbol="￥" type="currency" value="${map.trade_amount/100}" />
											</c:if>
										</td>
										<td>
										<fmt:formatDate value="${requestTime}" type="both"/>
										</td>
										<td><c:if test="${map.trade_status eq null}">${message }</c:if>
										<c:if test="${map.trade_status eq 'BUID'}">交易建立</c:if> <c:if
												test="${map.trade_status eq 'ACSU'}">已受理</c:if> <c:if
												test="${map.trade_status eq 'WPAR'}">等待支付结果</c:if> <c:if
												test="${map.trade_status eq 'FINI'}">交易成功</c:if> <c:if
												test="${map.trade_status eq 'REFU'}">交易退款</c:if><c:if
												test="${map.trade_status eq 'CLOS'}">交易关闭，失败</c:if></td>
										<td>${map.trade_pay_time}
										</td>
										<td>${map.bank_code}
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
	
<script type="text/javascript">
$("#st").val('${channelType}');
</script>
</body>
</html>
