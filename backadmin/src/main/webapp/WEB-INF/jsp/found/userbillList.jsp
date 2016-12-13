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

<title>用户资金流水表</title>


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
					<i class="fa fa-table"></i>当前位置：用户资金流水管理
				</h2>

			</div>

			<div class="contentpanel">

				<div class="panel panel-default">
					
					<div class="panel-heading">
						<form class="form-inline" action="/userbill/userbillList"
							method="post">
							<div class="form-group">
								<label class="sr-only" for="exampleInputEmail2">编号/手机号</label> <input
									type="text" name="userId" value="${userId }"
									class="form-control" id="userId" placeholder="用户编号/手机号/邮箱/身份证号">
							</div>
							<div class="form-group">
								<label class="sr-only" for="exampleInputPassword2">用户名</label> <input
									type="text" name="realname" value="${realname }"
									class="form-control" id="realname" placeholder="用户名">
							</div>
							<div class="input-group">
								<input type="text" class="form-control datepicker" name="start"
									value="${start}" placeholder="开始时间" id="start"> <span
									class="input-group-addon"><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>
							--
							<div class="input-group">
								<input type="text" class="form-control datepicker" name="end"
									value="${end}" placeholder="结束时间" id="end"> <span
									class="input-group-addon "><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>

							<script type="text/javascript">
								var v = '${type}';
								var obj = document
										.getElementsByTagName("option");
								for (var i = 0; i < obj.length; i++) {
									if (obj[i].value == v) {
										obj[i].selected = true; //相等则选中
									}
								}
							</script>
							<button type="submit" class="btn btn-primary">查询</button>
							<script type="text/javascript">
								function exportBill() {
									var userId = $("#mySelect").val();
									var start = $("#start").val();
									var end = $("#end").val();
									location = "${ctx}/userbill/userbillListExport?userId="
											+ userId
											+ "&start="
											+ start
											+ "&end=" + end;
								}
							</script>
						<base:active activeId="USER_ADMIN">
								<button type="button" id="add" class="btn btn-primary">管理员干预</button>
							</base:active>
						</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<base:active activeId="USER_LOOK">
										<th>用户编号</th>
									</base:active>
									<th>用户真实姓名</th>
									<th>时间</th>
									<th>费用类型</th>
									<th>金额</th>
									<th>业务类型</th>
									<th>流水号</th>
									<th>余额</th>
									<th>冻结金额</th>
									<th>费用说明(用户可见)</th>
									<th>费用详情(系统可见)</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="item">
									<tr>
										<base:active activeId="USER_LOOK">
											<td>${item.userId }</td>
										</base:active>
										<td>${item.user.realname }</td>
										<td><fmt:formatDate value="${item.time }"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td><c:choose>
												<c:when test="${item.type eq 'freeze'}">
					                           		 冻结
					                           </c:when>
												<c:when test="${item.type eq 'unfreeze'}">
					                       		            解冻
					                           </c:when>
												<c:when test="${item.type eq 'to_balance'}">
						                       		 从余额转出
						                        </c:when>
												<c:when test="${item.type eq 'ti_balance'}">
						                       		转入到余额
						                        </c:when>
												<c:when test="${item.type eq 'to_frozen'}">
						                       		 从冻结金额中转出
						                        </c:when>
												<c:when test="${item.type eq 'pt_balance'}">
						                       		 平台划款转入余额
						                        </c:when>
												<c:when test="${item.type eq 'management'}">
						                       		 取出借款管理费
						                        </c:when>
												<c:otherwise>
					                            	${item.type}
					                            </c:otherwise>
											</c:choose></td>
										<td><fmt:formatNumber value="${item.money }" type="currency"/></td>
<%-- 										<td>${item.money }</td> --%>
										<td><c:choose>
												<c:when test="${item.businessType eq 'recharge'}">
					                           		 充值
					                           </c:when>
												<c:when test="${item.businessType eq 'recharge_fee'}">
					                           		 充值手续费
					                           </c:when>
												<c:when test="${item.businessType eq 'recharge_line'}">
					                       		            线下充值
					                           </c:when>
												<c:when test="${item.businessType eq 'withdraw_cash'}">
						                       		 提现
						                        </c:when>
												<c:when test="${item.businessType eq 'withdraw_cash_fee'}">
						                       		 提现手续费
						                        </c:when>
												<c:when test="${item.businessType eq 'refund'}">
						                       		 退款
						                        </c:when>
												<c:when test="${item.businessType eq 'transfer'}">
						                       		直接转账
						                        </c:when>
												<c:when test="${item.businessType eq 'invest'}">
						                       		 投资
						                        </c:when>
												<c:when test="${item.businessType eq 'bidders'}">
						                       		流标
						                        </c:when>
												<c:when
													test="${item.businessType eq 'give_money_to_borrower'}">
						                       		放款
						                        </c:when>
												<c:when test="${item.businessType eq 'repay'}">
						                       		还款
						                        </c:when>
												<c:when test="${item.businessType eq 'demand_in'}">
						                       		天天赚购买
						                        </c:when>
												<c:when test="${item.businessType eq 'demand_out'}">
						                       		天天赚赎回
						                        </c:when>
												<c:when test="${item.businessType eq 'allowance'}">
						                       		补息
						                        </c:when>
												<c:when test="${item.businessType eq 'reward'}">
						                       		红包奖励
						                        </c:when>
												<c:when test="${item.businessType eq 'pt_transfer'}">
						                       		平台转账
						                        </c:when>
												<c:when test="${item.businessType eq 'transfer_in'}">
						                       		管理员转入
						                        </c:when>
												<c:when test="${item.businessType eq 'transfer_out'}">
						                       		管理员转出
						                        </c:when>
												<c:when test="${item.businessType eq 'freeze'}">
						                       		管理员冻结
						                        </c:when>
												<c:when test="${item.businessType eq 'unfreeze'}">
						                       		管理员解冻
						                        </c:when>
												<c:otherwise>
					                            	${item.businessType}
					                            </c:otherwise>
											</c:choose></td>
										<td>${item.requestNo }</td>
										<td><fmt:formatNumber value="${item.balance }" type="currency"/></td>
										<td><fmt:formatNumber value="${item.freezeAmount }" type="currency"/></td>
<%-- 										<td>${item.balance }</td>
										<td>${item.freezeAmount }</td> --%>
										<td>${item.typeInfo }</td>
										<td>${item.detail }</td>
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
			jQuery('.datepicker').datepicker();
			jQuery('#datepicker-inline').datepicker();

			jQuery('#datepicker-multiple').datepicker({
				numberOfMonths : 3,
				showButtonPanel : true
			});
			//添加按钮
			$("button[id=add]").click(function() {
				/* window.location.href = "/userbill/toadd"; */
				window.open("/userbill/toadd");
			});
		});
	</script>
</body>
</html>
