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
<title>平台账户管理</title>
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
					<i class="fa fa-table"></i>当前位置：平台账户管理
				</h2>
				<div class="breadcrumb-wrapper">
					<span class="label"></span>

				</div>
			</div>

			<div class="panel-body">
				<table class="table table-primary table-striped table-hover">
					<thead>
						<tr>
							<!-- <th>编号</th> -->
							<th>账户余额</th>
							<th>可用余额</th>
							<th>冻结金额</th>
							<th>最近操作时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="treet1">
						<tr>
							<%-- <td>${platformAccount.id }</td> --%>
							<%-- <td>${platformAccount.balance }</td> --%>
							<td><fmt:formatNumber value="${platformAccount.balance}" type="currency"/></td>
							<td><fmt:formatNumber value="${platformAccount.availableBalance}" type="currency"/></td>
							<td><fmt:formatNumber value="${platformAccount.freezeAmount}" type="currency"/></td>
							<%-- <td>${platformAccount.availableBalance }</td>
							<td>${platformAccount.freezeAmount }</td> --%>
							<td><fmt:formatDate value="${platformAccount.time}"
									type="both" /></td>
							<td><a style="cursor:pointer"
								href="/platformAccount/platformAccountIn">线下充值</a>| <a
								style="cursor:pointer"
								href="/platformAccount/platformAccountOut">线下提现</a></td>
						</tr>
					</tbody>
				</table>
				<div class="pageheader">
					<h2>
						<i class="fa fa-table"></i>当前位置：平台账户流水管理
					</h2>

				</div>
				<div class="panel-heading">
					<form class="form-inline" action="/account/platformAccountList"
						method="post">
						<div class="form-group">
							<label class="sr-only" for="exampleInputEmail2">流水号</label> <input
								type="text" name="requestNo" value="${requestNo }"
								class="form-control" id="requestNo" placeholder="流水号">
						</div>
						<%-- <div class="form-group">
							<label class="sr-only" for="exampleInputPassword2">ID</label> <input
								type="text" name="id" value="${id }"
								class="form-control" id="id" placeholder="ID">
						</div> --%>
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
						jQuery('.datepicker').datepicker();
						</script>

						
						<button id="submit" type="submit" class="btn btn-primary">查询</button>
					</form>
				</div>
				<table class="table table-primary table-striped table-hover ">
					<thead>
						<tr>
							<%-- <base:active activeId="USER_LOOK">
								<th>ID</th>
							</base:active> --%>
							<th>流水号</th>
							<th>时间</th>
							<th>费用类型</th>
							<th>金额</th>
							<th>余额</th>
							<th>冻结金额</th>
							<th>业务类型</th>
							<th>费用说明</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.results}" var="item">
							<tr>
								<%-- <base:active activeId="USER_LOOK">
									<td>${item.id }</td>
								</base:active> --%>
								<td>${item.requestNo }</td>
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
								<%-- <td>${item.money }</td> --%>
								<%-- <td>${item.balance }</td> --%>
								<td><fmt:formatNumber value="${item.money}" type="currency"/></td>
								<td><fmt:formatNumber value="${item.balance}" type="currency"/></td>
								<td><fmt:formatNumber value="${item.freezeAmount}" type="currency"/></td>
								<%-- <td>${item.freezeAmount }</td> --%>
								

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
								<td>${item.typeInfo }</td>
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
		$("#submit").click(function() {
			var arys1 = new Array();
			var arys2 = new Array();
			if ($("#start").val() != null && $("#end").val() != null) {
				arys1 = $("#start").val().split('-');
				var sdate = new Date(arys1[0], parseInt(arys1[1]), arys1[2]);
				arys2 = $("#end").val().split('-');
				var edate = new Date(arys2[0], parseInt(arys2[1] - 1), arys2[2]);
				if (sdate < edate) {
					alert("开始时间与结束时间间隔不能大于1个月！");
					return false;
				}
			}
		});
	</script>
</body>
</html>