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
<title>${title }</title>
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
					<i class="fa fa-table"></i>当前位置：${title }
				</h2>
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<c:choose>
					<c:when test="${title == '京东代付列表' }">
						<div style="padding:20px 10px 10px; font-size: 18px; color: red;">
						<span>京东账户余额：
						<fmt:formatNumber currencySymbol="￥" type="currency" value="${jdbanlence}" /></span>  
						<span>宝付账户余额：
						<fmt:formatNumber currencySymbol="￥" type="currency" value="${BFbankence}" /></span>  
						<span style="margin-left:10px;">提现总额：
						<fmt:formatNumber currencySymbol="￥" type="currency" value="${totalMoney}" /></span> <span style="margin-left:10px;">提现数量：${total}</span>
						<span style="margin-left:10px; color: black;">代付渠道（方式）：
						<select name="drepayType" id="drepayType">
							<option value="JD">京东</option>
							<option value="BaoFoo">宝付</option>
						</select>
						<button type="button" id="batchVerify" class="btn btn-primary" onclick="verifyWithdrawCash();">校验数据</button>
						<button type="button" id="batchConfirm" class="btn btn-primary" onclick="confirmWithdrawCash();" disabled="disabled">确认代付</button>
					</div>
					</c:when>
					<c:when test="${title == '京东代付成功列表' }">
					<div style="padding:20px 10px 10px; font-size: 14px;">
						代付确认日期: <input type="text" class="datepicker" id="begin" placeholder="开始日期" style="width:140px"> -- 				
						<input type="text" class="datepicker" id="end" placeholder="结束日期" style="width:140px">	
						<button type="button" id="batchConfirm" class="btn btn-primary" onclick="subcheck();" style="margin-left: 14px; padding: 4px 18px;">查询</button>				
					</c:when>
				</c:choose>				
						<script type="text/javascript">	            		            
			            jQuery(document).ready(function(){					
							 jQuery('.datepicker').datepicker({
								showSecond : false,
								//timeFormat : 'hh:mm:ss',
								stepHour : 1,
								//stepMinute : 1,
								//stepSecond : 1,
								//hour: 13,
			  					//minute: 15
							});				 
						});

            	</script>       
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>
								<tr>
									<th>ID/流水号/指向</th>
									<base:active activeId="USER_LOOK">
										<th>用户名/手机号/姓名</th>
									</base:active>
									<th>银行卡/金额</th>
									<th>提现途径</th>
									<th>提现状态</th>
									<th>代付状态</th>
									<th>请求时间/冻结时间/确认时间</th>
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
										<td>
											${item.transferWay }</td>
										
										<td><c:if test="${item.status eq 'sended'}">发起</c:if> <c:if
												test="${item.status eq 'frozen'}">冻结</c:if> <c:if
												test="${item.status eq 'confirm'}">成功</c:if> <c:if
												test="${item.status eq 'fail'}">确认失败</c:if> <c:if
												test="${item.status eq 'refused'}">未完成</c:if>
										</td>
										<td> <c:if
												test="${item.isDefrayPay eq 'undeal'}">未处理</c:if> <c:if
												test="${item.isDefrayPay eq 'success'}">成功</c:if> <c:if
												test="${item.isDefrayPay eq 'fail'}">失败</c:if><c:if
												test="${item.isDefrayPay eq 'sended'}">代付中</c:if></td>
										<td><fmt:formatDate value="${item.requestTime }"
												pattern="yyyy-MM-dd HH:mm:ss" /><br>
											<fmt:formatDate value="${item.responseTime }"
												pattern="yyyy-MM-dd HH:mm:ss" /><br>
											<fmt:formatDate value="${item.confirmResponseTime }"
												pattern="yyyy-MM-dd HH:mm:ss" />
											</td>
										<td><br>
										<c:if test="${(item.status eq 'frozen' or item.status eq 'sended' or item.status eq 'fail') and item.isDefrayPay eq 'success'}"><a href="javascript:cashconfirm('${item.id}')">确认</a><br></c:if>
										<br><c:if test="${item.status eq 'frozen' and item.isDefrayPay eq 'fail'}"> <a href="javascript:cashcancel('${item.id}')">退款</a></c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- panel-body -->
					</div>
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<script type="text/javascript">
		
		function subcheck(){
		
			var begin = $("#begin").val();
			var end = $("#end").val();
			var url = "${ctx}${url}&begin="+begin+"&end="+end;		
			location = url;
		}
		function verifyWithdrawCash() {
			if (confirm("确认校验数据吗?")) {
				$.ajax({
							type : 'POST',
							url : "${ctx}/payMent/verifyWithdrawCash",
							data : {},
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
								if (msg.indexOf('验证完成')>=0) {
									alert(msg);
									$("#batchConfirm").attr("disabled",false);		
								}else{
									alert(msg);
								}
							}
						});
			}
		}
	
		function confirmWithdrawCash(id) {
		var drepayType=$("#drepayType").val();
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
								}else{
									alert("退款失败:"+msg);
								}
								location.reload();
							}
						});
			}
		}
		
		function cashconfirm(id) {
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
								}else{
									alert("确认转账失败:" + msg);
								}
								location.reload();
							}
						});
			}
		}
		
		
		</script>
		<!-- mainpanel -->
	</section>
	
</body>
</html>
