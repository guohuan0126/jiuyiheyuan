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
<title>京东提现列表</title>
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
					<i class="fa fa-table"></i>当前位置：京东提现列表
				</h2>
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" action="/cash/cashList/jdpay"
							method="post">
							<div class="form-group">
								<input type="text" name="id" value="${record.id}"
									class="form-control" id="id" placeholder="id">
							</div>
							<div class="form-group">
								<input type="text" name="markId" value="${record.markId}"
									class="form-control" id="markId" placeholder="流水号">
							</div>
							<div class="form-group">
								<input type="text" name="userId" value="${user}"
									class="form-control" id="userId" placeholder="用户编号/手机号">
							</div>
							<div class="form-group">
								<select class="form-control" name="status">
									<option value="">--提现状态--</option>
									<option value="sended"
										<c:if test="${record.status eq 'sended' }">selected</c:if>>发起</option>
									<option value="frozen"
										<c:if test="${record.status eq 'frozen' }">selected</c:if>>冻结</option>
									<option value="confirm"
										<c:if test="${record.status eq 'confirm' }">selected</c:if>>成功</option>
									<option value="fail"
										<c:if test="${record.status eq 'fail' }">selected</c:if>>确认失败</option>
									<option value="refused"
										<c:if test="${record.status eq 'refused' }">selected</c:if>>未完成</option>
								</select>
							</div>

							<div class="form-group">
								<select class="form-control" name="isDefrayPay">
									<option value="">--代付状态--</option>
									<option value="undeal"
										<c:if test="${record.isDefrayPay eq 'undeal' }">selected</c:if>>未处理</option>
									<option value="success"
										<c:if test="${record.isDefrayPay eq 'success' }">selected</c:if>>成功</option>
									<option value="fail"
										<c:if test="${record.isDefrayPay eq 'fail' }">selected</c:if>>失败</option>
									<option value="sended"
										<c:if test="${record.isDefrayPay eq 'sended' }">selected</c:if>>代付中</option>
								</select>
							</div>

							<button type="submit" class="btn btn-primary">查询</button>
						</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>
								<tr>
									<th>ID/流水号/指向</th>
									<base:active activeId="USER_LOOK">
										<th>用户名/手机号/姓名</th>
									</base:active>
									<th>银行卡/金额</th>
									<th>状态/途径/代付状态</th>
									<th>请求时间/请求数据/请求地址</th>
									<th>响应时间/响应数据</th>
									<th>确认时间/确认数据</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="item">
									<tr>
										<td>${item.id}<br>${item.markId}<br>
											${item.operator}
										</td>
										<base:active activeId="USER_LOOK">
											<td>${item.userId }<br> ${item.mobileNumber}<br>
												${item.realname}
											</td>
										</base:active>
										<td>${item.cardNo }<br> <fmt:formatNumber
												currencySymbol="￥" type="currency" value="${item.money}" /></td>
										<td><c:if test="${item.status eq 'sended'}">发起</c:if> <c:if
												test="${item.status eq 'frozen'}">冻结</c:if> <c:if
												test="${item.status eq 'confirm'}">成功</c:if> <c:if
												test="${item.status eq 'fail'}">确认失败</c:if> <c:if
												test="${item.status eq 'refused'}">未完成</c:if><br>
											${item.transferWay }<br> <c:if
												test="${item.isDefrayPay eq 'undeal'}">未处理</c:if> <c:if
												test="${item.isDefrayPay eq 'success'}">成功</c:if> <c:if
												test="${item.isDefrayPay eq 'fail'}">失败</c:if><c:if
												test="${item.isDefrayPay eq 'sended'}">代付中</c:if></td>
										<td><fmt:formatDate value="${item.requestTime }"
												pattern="yyyy-MM-dd HH:mm:ss" /><br> <textarea
												rows="4" cols="26" readonly="readonly">${item.requestData}</textarea><br>
											${item.requestUrl}</td>
										<td><fmt:formatDate value="${item.responseTime }"
												pattern="yyyy-MM-dd HH:mm:ss" /><br> <textarea
												rows="4" cols="26" readonly="readonly">${item.responseData}</textarea>
										</td>
										<td><fmt:formatDate value="${item.confirmResponseTime }"
												pattern="yyyy-MM-dd HH:mm:ss" /><br> <textarea
												rows="4" cols="26" readonly="readonly">${item.confirmResponseData}</textarea>
										</td>
										<td><br> 
										<c:if test="${item.status eq 'frozen' and item.isDefrayPay eq 'undeal'}"><a href="javascript:confirmWithdrawCash('${item.id}','JD')">单笔转账(京东)</a><br><a href="javascript:confirmWithdrawCash('${item.id}','BaoFoo')">单笔转账(宝付)</a><br></c:if>
										<br><c:if test="${(item.status eq 'frozen' or item.status eq 'sended' or item.status eq 'fail')  and item.isDefrayPay eq 'success'}"> <a href="javascript:cashconirm('${item.id}')">确认</a></c:if><br><br>
										<c:if test="${item.status eq 'frozen' and item.isDefrayPay eq 'fail'}"> <a href="javascript:cashcancel('${item.id}')">退款</a></c:if></td>
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
		function cashcancel(id) {
			if (confirm("确认退款吗?")) {
				$
						.ajax({
							type : 'POST',
							url : "${ctx}/cash/cashList/cancle",
							data : {
								'id' : id
							},
							beforeSend : function() {
								xval = getBusyOverlay(
										'viewport',
										{
											color : 'blue',
											opacity : 0.5,
											text : '正在处理...',
											style : 'text-shadow: 0 0 3px black;font-size:18px;'
										}, {
											color : 'blue',
											size : 25,
											type : 'o'
										});
							},
							error : function() {
								window.clearInterval(xval.ntime);
								xval.remove();
								status == true;
							},
							success : function(msg) {
								window.clearInterval(xval.ntime);
								xval.remove();
								if (msg == 'success') {
									alert("操作成功!");		
								}else if(msg == 'null'){
									alert("提现订单不存在!");
								}else {
									alert("退款失败");
								}
								location.reload();
							}
						});
			}
		}
		
		function cashconirm(id) {
			if (confirm("确认转账吗?")) {
				$.ajax({
							type : 'POST',
							url : "${ctx}/cash/cashList/confirm",
							data : {
								'id' : id
							},
							beforeSend : function() {
								xval = getBusyOverlay(
										'viewport',
										{
											color : 'blue',
											opacity : 0.5,
											text : '正在处理...',
											style : 'text-shadow: 0 0 3px black;font-size:18px;'
										}, {
											color : 'blue',
											size : 25,
											type : 'o'
										});
							},
							error : function() {
								window.clearInterval(xval.ntime);
								xval.remove();
								status == true;
							},
							success : function(msg) {
								window.clearInterval(xval.ntime);
								xval.remove();
								if (msg == 'success') {
									alert("操作成功!");		
								}else if(msg == 'null'){
									alert("提现订单不存在!");
								}else {
									alert("转账失败");
								}
								location.reload();
							}
						});
			}
		}
		
		function confirmWithdrawCash(id,drepayType) {
			if (confirm("确认转账吗?")) {
				$.ajax({
							type : 'POST',
							url : "${ctx}/payMent/confirmWithdrawCash",
							data : {
								'id' : id,
								'drepayType':drepayType
							},
							beforeSend : function() {
								xval = getBusyOverlay(
										'viewport',
										{
											color : 'blue',
											opacity : 0.5,
											text : '正在处理...',
											style : 'text-shadow: 0 0 3px black;font-size:18px;'
										}, {
											color : 'blue',
											size : 25,
											type : 'o'
										});
							},
							error : function() {
								window.clearInterval(xval.ntime);
								xval.remove();
								status == true;
							},
							success : function(msg) {
								window.clearInterval(xval.ntime);
								xval.remove();
								if (msg == 'success') {
									alert("操作成功!");		
								}else{
									alert(msg);
								}
								location.reload();
							}
						});
			}
		}
	</script>
</body>
</html>
