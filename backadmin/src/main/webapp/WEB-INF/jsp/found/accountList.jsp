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

<title>账户查询</title>
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
					<i class="fa fa-table"></i>当前位置：账户查询
				</h2>
			</div>

			<div class="contentpanel">

				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" id="form1" method="post">
							<div class="form-group">
								<div class="form-group">
									<label class="sr-only" for="exampleInputEmail2">编号</label> <input
										type="text" name="userId" value="${userId }"
										class="form-control" id="exampleInputEmail2"
										placeholder="用户编号/手机号/身份证号/邮箱">
								</div>
								<div class="form-group">
									<label class="sr-only" for="exampleInputEmail2">编号</label> <input
										type="text" name="realName" value="${realName }"
										class="form-control" id="exampleInputEmail1"
										placeholder="用户姓名">
								</div>
								<span style="font-size: 18px;">投资时间 ： </span> <input type="text"
									class="form-control" placeholder="开始时间" class="datepicker"
									name="start" value="${start}" id="start"
									onchange="demandByUser()"> <input type="text"
									class="form-control" placeholder="结束时间" class="datepicker"
									name="end" id="end" value="${end}" onchange="demandByUser()">

							</div>
							<button type="button" id="search" class="btn btn-primary">查询</button>
							<div id="erroruser" style="display: none;">查询内容不能为空</div>
							<base:active activeId="USER_ADMIN">
								<button type="button" id="add" class="btn btn-primary">管理员干预</button>
							</base:active>
						</form>
					</div>
					<script type="text/javascript">
						var myDate = new Date();
						if ('${start}' == '') {
							var last = myDate.toLocaleDateString();
							$("#end").val(last);
						}
					</script>
					<script type="text/javascript">
						var d = new Date();
						d.setDate(d.getDate() - 1);
						jQuery('#start').datepicker({
							dateFormat : 'yy-mm-dd',
						});
					</script>
					<script type="text/javascript">
						var d = new Date();
						d.setDate(d.getDate() - 1);
						jQuery('#end').datepicker({
							dateFormat : 'yy-mm-dd',
						});
					</script>
					<script type="text/javascript">
						$("button[id=add]").click(function() {
						/* window.location.href = "/userbill/toadd"; */
							window.open("/userbill/toadd");
						});
					</script>
					<div class="panel-body">
						<label style="font-size: 20px;font-weight: bold;">本地账户信息
						</label>
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
								<tr>
									<td>${userAccount.userId }</td>
									<td>${userAccount.realname }</td>
									<td>${userAccount.balance }</td>
									<td>${userAccount.availableBalance }</td>
									<td>${userAccount.freezeAmount}</td>
									<td>${userAccount.autoInvest}</td>
									<td>${userAccount.autoRepay}</td>
									<td><fmt:formatDate value="${userAccount.time}"
											type="both" /></td>
								</tr>
							</tbody>
						</table>
						<label style="font-size: 20px;font-weight: bold;">易宝账户信息
						</label>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>用户编号</br> <base:active activeId="USER_LOOK">
										真实姓名/手机号</br>
										身份证号/电子邮件/联系地址/归属地
									</base:active>
									</th>
									<th style="text-align:center"><base:active
											activeId="USER_REFFER">
											推荐人/										
										</base:active> 经理推荐人/用户类型</th>
									<th style="text-align:center">注册时间/注册IP/来源</th>
									<th>账户类型</th>
									<th>激活状态</th>
									<th>自动投标</th>
									<th>投资金额/当前投资金额</th>
									<th>易宝账户余额/本地账户余额</th>
									<th>易宝可用金额/本地可用金额</th>
									<th>易宝冻结金额/本地冻结金额</th>
									<th>绑卡渠道</th>
									<th>银行卡号</th>
									<th>开户银行</th>
									<th>银行卡审核状态</th>
									<th>是否开通快捷支付</th>
									<th>状态码--描述</th>
									<base:active activeId="USER_EDIT">
										<th>操作</th>
									</base:active>
									<th>历史投资</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${list.size() > 0 }">
									<c:forEach items="${list}" var="account">
										<tr>
											<td>${account.userid}</br> <base:active activeId="USER_LOOK">
													${account.realname}</br>
													${account.mobile}</br>
													${account.user.idCard}</br>
													${account.user.email}</br>
													${account.user.userOtherInfo.postAddress }
													${account.user.phoneNoAttribution}&nbsp;${account.user.phoneNoCity}
												</base:active>
											</td>
											<td><base:active activeId="USER_REFFER">
												${account.user.referrer }</br>
												</base:active> ${account.user.oreferrer}</br> <c:if
													test="${account.user.userInterior == 'duanrongw' }">内部员工</c:if>
												<c:if test="${account.user.userInterior == 'furen' }">辅仁客户</c:if>
												<c:if test="${account.user.userInterior == 'nelson' }">离职员工</c:if>
											</td>
											<td><fmt:formatDate
													value="${account.user.registerTime }"
													pattern="yyyy-MM-dd HH:mm" /><br />${account.user.userOtherInfo.userIP }<br />${account.user.userOtherInfo.userSource }</td>
											<td>${account.memberType }</td>
											<td>${account.activeStatus }</td>
											<td><c:if test="${account.autoTender=='true'}">是</c:if>
												<c:if test="${account.autoTender=='false'}">否</c:if></td>
											<td><fmt:formatNumber currencySymbol="￥" type="currency"
													value="${account.money}" /> </br> <fmt:formatNumber
													currencySymbol="￥" type="currency"
													value="${account.newMoney}" /></td>
											<td><c:if test="${account.code eq '1'}">
					   ${account.balance } <br />
												</c:if> ${account.bbalance }</td>
											<td><c:if test="${account.code eq '1'}">
					   ${account.availableAmount } <br />
												</c:if> ${account.bavailableAmount }</td>
											<td><c:if test="${account.code eq '1'}">
					   ${account.freezeAmount } <br />
												</c:if> ${account.bfreezeAmount }</td>
											<td><c:choose>
													<c:when test="${empty account.payMnetId}">YEEPAY</c:when>
													<c:otherwise>${account.payMnetId}</c:otherwise>
												</c:choose></td>
											<td>${account.cardNo }</td>
											<td>${account.bank }</td>
											<td>${account.cardStatus }</td>
											<td><c:if test="${account.paySwift eq 'NORMAL' }">
					   未开通
					   </c:if> <c:if test="${account.paySwift eq 'UPGRADE' }">
					   已开通
					   </c:if></td>
											<td>${account.code }--${account.description }</td>
											<base:active activeId="USER_EDIT">
												<td><a
													href="/user/toedit?id=${account.userid}&url=${url}">编辑</a></td>
											</base:active>
											<td>${account.user.investNum}次<br /> <a
												href="/invest/investByUser?investUserID=${account.userid}">详情</a></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						<label style="font-size: 20px;font-weight: bold;">账户最近30条流水记录<button type="button" id="more" class="btn btn-primary">更多</button>
						</label>
					<table class="table table-primary table-striped table-hover ">
						<thead>
							<tr>
								<base:active activeId="USER_LOOK">
									<th>用户编号</th>
								</base:active>
								<!-- <th>用户真实姓名</th> -->
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
							<c:forEach items="${userBills}" var="item">
								<tr>
									<base:active activeId="USER_LOOK">
										<td>${item.userId }</td>
									</base:active>
									<%-- <td>${item.user.realname }</td> --%>
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
									<td>${item.money }</td>
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
									<td>${item.balance }</td>
									<td>${item.freezeAmount }</td>
									<td>${item.typeInfo }</td>
									<td>${item.detail }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
					<div class="panel-body" style="border-top: 1px solid #e7e7e7;">
						<label style="font-size: 20px;font-weight: bold;">查询账户某个日期之前的余额
						</label>
						<form id="from2" method="post">
							<div class="form-group">
								<label class="sr-only" for="exampleInputEmail2">编号</label> <input
									type="text" id="userId" name="userId" value="${userId }"
									class="form-control"
									style="width: 240px;display:inline;margin-right:30px;"
									id="exampleInputEmail2" placeholder="用户编号/手机号/身份证号/邮箱">
								<span style="font-size: 18px;width:240px">截止时间 ： </span> <input
									type="text" class="form-control"
									style="width: 240px;display:inline;" placeholder="截止时间"
									class="datepicker" name="end" id="endTime" value="${time}" />
								<button type="button" class="btn btn-primary"
									onclick="readUsedMoney()">查询</button>
							</div>
						</form>
						<div id="demandInterest"
							style="font-size:16px ;color:red; margin-top:10px;"></div>
					</div>
					<div class="panel-body" style="border-top: 1px solid #e7e7e7;">
						<label style="font-size: 20px;font-weight: bold;">查询账户的活动红包信息</label>
						<form id="from3" method="post">
							<div class="form-group">
								<label class="sr-only" for="exampleInputEmail2">编号</label> <input
									type="text" id="id" name="id" value="${id}"
									class="form-control"
									style="width: 240px;display:inline;margin-right:30px;"
									id="exampleInputEmail2" placeholder="用户编号/手机号">
								<button type="button" class="btn btn-primary"
									onclick="readUsedRedpacket()">查询</button>
							</div>
						</form>
						<div class="panel-body" style="padding-top:20px; padding-left:0px">

							<table class="table table-primary table-striped table-hover ">
								<thead style="width:100%;display:block;">
									<tr class="font">
										<th style="width:140px;">编号</th>
										<th style="width:150px;">活动名</th>
										<th style="width:160px;">有效时间</th>
										<th style="width:100px;">金额</th>
										<th style="width:100px;">活动利率</th>
										<th style="width:100px;">券类型</th>
										<th style="width:140px;">可用类型</th>
										<th style="width:140px;">投资金额限制</th>
										<th style="width:140px;">投资项目周期限制</th>
										<th style="width:140px;">发送时间</th>
										<th style="width:140px;">发送状态</th>

									</tr>
								</thead>
								<tbody
									style="width:100%;height:552px;overflow-y:scroll;display:block;">

									<c:forEach items="${RedpacketList}" var="redpacket">
										<tr class="font">
											<td style="width:140px;">${redpacket.id}</td>
											<td style="width:150px;">${redpacket.name}</td>
											<td style="width:160px;"><fmt:formatDate
													value="${redpacket.createTime}" /></br> <fmt:formatDate
													value="${redpacket.deadline}" /></td>
											<td style="width:100px;">${redpacket.money}</td>
											<td style="width:100px;">${redpacket.rate}</td>
											<td style="width:100px;"><c:if
													test="${redpacket.type eq 'money' }">
				 现金券
				 </c:if> <c:if test="${redpacket.type eq 'rate' }">
				 加息券
				 </c:if></td>
											<td style="width:140px;"><c:if
													test="${redpacket.usageDetail eq 'invest' }">
				投资可用
				 </c:if> <c:if test="${redpacket.usageDetail eq 'withdraw' }">
				 可申请提现
				 </c:if></td>
											<td style="width:140px;">${redpacket.investMoney}</td>
											<td style="width:140px;"><c:choose>
													<c:when test="${redpacket.investCycle==0}">
				 	无限制
				 	</c:when>
													<c:otherwise>${redpacket.investCycle}个月及以上可用</c:otherwise>
												</c:choose></td>

											<td style="width:140px;"><fmt:formatDate
													value="${redpacket.deadline}" /></td>
											<td style="width:140px;"><c:if
													test="${redpacket.sendStatus eq 'unused' }">未使用</c:if> <c:if
													test="${redpacket.sendStatus eq 'used' }">已使用</c:if> <c:if
													test="${redpacket.sendStatus eq 'sended' }">已发送</c:if> <c:if
													test="${redpacket.sendStatus eq 'expired' }">已过期</c:if></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<script type="text/javascript">
						function readUsedMoney() {
							var endTime = $("#endTime").val();
							var userId = $("#userId").val();
							if (isNull(endTime)) {
								alert("截止时间不能为空");
								return false;
							}
							if (isNull(userId)) {
								alert("手机号不能为空");
								return false;
							}
							$("#from2")
									.attr("action", "/account/userUsedMoney");
							$("#from2").submit();
						}

						function readUsedRedpacket() {
							var id = $("#id").val();
							if (isNull(id)) {
								alert("用户信息不能为空");
								return false;
							}
							$("#from3")
									.attr("action", "/account/userRedpacket");
							$("#from3").submit();
						}

						//判断是否为空
						var isNull = function(str) {
							if (str == "")
								return true;
							var regu = "^[ ]+$";
							var re = new RegExp(regu);
							return re.test(str);
						};
					</script>

					<script type="text/javascript">
						if ('${usedMoney}' != '') {

							$("#demandInterest").html(
									'账户可用余额 ：' + '${usedMoney}');
						}
					</script>

					<script type="text/javascript">
						/* var d = new Date();
						d.setDate(d.getDate() - 1); */
						jQuery('#endTime').datetimepicker({
							showSecond : true,
							timeFormat : 'hh:mm:ss',
							stepHour : 1,
							stepMinute : 1,
							stepSecond : 1,
						});
					</script>
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
						$("#form1").attr("action", "/account/accountList");
						$("#form1").submit();
					}
				});
	</script>
<script>
		jQuery(document).ready(function() {
			//查看更多按钮
			$("button[id=more]").click(function() {
				window.open("/userbill/userbillList");
			});
		});
	</script>
</body>
</html>