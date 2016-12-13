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

<title>投资信息</title>
</head>
<body>
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<%@include file="../invest/redPacketForDiaglogOpen.jsp"%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>

			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：投资信息
				</h2>
				<div class="breadcrumb-wrapper">
					<ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
						<li><a href=""></a></li>
					</ol>
				</div>
			</div>
			<div class="contentpanel ">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" id="form1" action="/invest/investList"
							method="post">
							<div class="form-group">
								<input type="text" name="loanId" value="${loanId}"
									class="form-control" placeholder="项目编号" style="width:160px">
							</div>
							<div class="form-group">
								<input type="text" name="loanName" value="${loanName}"
									class="form-control" placeholder="项目名称" style="width:160px">
							</div>
							<div class="form-group">
								<label class="sr-only" for="exampleInputmobile">状态</label> <select
									class="select2" name="status" id="st" data-placeholder="状态...">
									<option value="">--状态</option>
									<option value="等待确认">等待确认</option>
									<option value="投标成功">投标成功</option>
									<option value="还款中">还款中</option>
									<option value="完成">完成</option>
									<option value="流标">流标</option>
								</select>
							</div>
							<div class="form-group">
								<input type="text" name="userId" value="${userId}"
									class="form-control" placeholder="投资人编号" style="width:160px">
							</div>
							<div class="form-group">
								<input type="text" name="mobile" value="${mobile}"
									class="form-control" placeholder="投资人手机号" style="width:160px">
							</div>
							<div class="form-group">
								<input type="text" name="realname" value="${realname}"
									class="form-control" placeholder="投资人姓名" style="width:160px">
							</div>
							 <div class="input-group" >
				                <label class="sr-only" for="exampleInputmobile">用户来源</label>
				                <input type="text" class="form-control" name="userSource" value="${userSource}" placeholder="用户来源" id="userSource">
				                </div> 
				                <div class="input-group" >
				                <label for="exampleInputmobile">用户来源是否为空</label>
				                <input <c:if test="${not empty userSourceIsNull}">checked</c:if> type="checkbox"  name="userSourceIsNull" value="userSourceIsNull" id="userSourceIsNull" style="vertical-align: middle;margin:0 0 0 5px;">
				               </div> 
							<br>
							<br>
							<div class="input-group">
								<input type="text" class="form-control" name="start"
									value="${start}" placeholder="开始时间" id="start"> <span
									class="input-group-addon"><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>
							--
							<div class="input-group">
								<input type="text" class="form-control" name="end"
									value="${end}" placeholder="结束时间" id="end"> <span
									class="input-group-addon"><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>
							<script type="text/javascript">
								var myDate = new Date();
								if ('${start}' == '') {

									var last = myDate.toLocaleDateString();
									$("#end").val(last);
									var now = getPreMonth(last);
									$("#start").val(now);
								}

								/**
								 * 获取上一个月
								 *
								 * @date 格式为yyyy-mm-dd的日期，如：2014-01-25
								 */
								function getPreMonth(date) {
									var arr = date.split("/");
									var year = arr[0]; //获取当前日期的年份
									var month = arr[1]; //获取当前日期的月份
									var day = arr[2]; //获取当前日期的日
									var days = new Date(year, month, 0);
									days = days.getDate(); //获取当前日期中月的天数
									var year2 = year;
									var month2 = parseInt(month) - 1;
									if (month2 == 0) {
										year2 = parseInt(year2) - 1;
										month2 = 12;
									}
									var day2 = day;
									var days2 = new Date(year2, month2, 0);
									days2 = days2.getDate();
									if (day2 > days2) {
										day2 = days2;
									}
									if (month2 < 10) {
										month2 = '0' + month2;
									}
									var t2 = year2 + '/' + month2 + '/' + day2;
									return t2;
								}
							</script>
							<script type="text/javascript">
								jQuery('#start').datepicker({
									dateFormat : 'yy-mm-dd'
								});
								jQuery('#end').datepicker({
									dateFormat : 'yy-mm-dd'
								});
							</script>
							<button type="button" id="search" class="btn btn-primary">查询</button>
							<base:active activeId="Export_Excel">
								<button id="export" type="button" class="btn btn-primary">导出数据</button>
							</base:active>
						</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>

								<tr class="font">
									<th>项目ID/项目名称/投资编号/项目周期</th>
									<th>投资状态</th>
									<th>投资人编号/注册时间/用户来源/投资人姓名<base:active activeId="USER_LOOK">/手机号</base:active>
										<base:no_active>
											<base:active activeId="USER_MUMBER">手机号</base:active>
										</base:no_active>
									</th>
									<th>投资时间</th>
									<th>投资本金</th>
									<th>预期收益</th>
									<th>已回收收益</th>
									<th>已回收本金</th>
									<th>投标方式</th>
									<th>投标来源</th>
									<th>加息券</th>
									<th>是否首投</th>									
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="invest" items="${pageInfo.results}">
									<tr class="font">
										<td>${invest.loanId}<br> ${invest.loanName}<br>${invest.id}<br>
											<c:if test="${invest.loan.operationType=='月'}">${invest.loan.deadline}月</c:if>
											<c:if test="${invest.loan.operationType=='天'}">${invest.loan.day}天</c:if>
										</td>
										<td>${invest.status}</td>
										<td>${invest.investUserID}<br><fmt:formatDate value="${invest.registerTime}" pattern="yyyy-MM-dd HH:mm:ss" /><br>${invest.userSource}</br>${invest.investUserName}<br><base:active
												activeId="USER_LOOK">${invest.userMobileNumber}</base:active>
											<base:no_active>
												<base:active activeId="USER_MUMBER">
													<td>*******${fn:substring(invest.userMobileNumber, 7, 11)}</td>
												</base:active>
											</base:no_active>
										</td>
										<td>
											<fmt:formatDate value="${invest.time}" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td><fmt:formatNumber value="${invest.money}" />元</td>
										<td><fmt:formatNumber currencySymbol="￥" type="currency"
												value="${invest.interest}" /></td>
										<td><fmt:formatNumber currencySymbol="￥" type="currency"
												value="${invest.paidInterest}" /></td>
										<td><fmt:formatNumber currencySymbol="￥" type="currency"
												value="${invest.paidMoney}" /></td>

										<td><c:choose>
												<c:when test="${invest.isAutoInvest eq true}">
					自动投标
				</c:when>
												<c:otherwise>
					手动投标
				</c:otherwise>
											</c:choose></td>
										<td>${invest.userSource}</td>
										<base:active activeId="REDPACKET_UPDATE">
											<td>
												<div class="photo">
													<div class="ui-widget-header ui-corner-all">
														<a href="javascript:void(0);" data-geo=""
															onclick="openDialog('${invest.id}','${invest.redpacketId}','${invest.userMobileNumber}','${invest.status}','${invest.loanStatus}')">${invest.redpacketId}</a>
													</div>
												</div>
											</td>
										</base:active>
										<base:no_active>
											<td>
												<div class="photo">
													<div class="ui-widget-header ui-corner-all">
														<a href="javascript:void(0);" data-geo=""
															onclick="javascript:void(0);">${invest.redpacketId}</a>
													</div>
												</div>
											</td>
										</base:no_active>
										<td>${invest.isFirstInvest}</td>
										<td><c:if test="${invest.status == '投标成功'}">
												<base:active activeId="COMPLETE_TRANSACTION">
													<a href="javascript:giveMoneyToBorrowerFromInvest('${invest.id}')">单笔放款</a>
												</base:active>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
						总金额： ￥
						<c:if test="${actualMoney ge 10000}">
							<fmt:formatNumber value="${actualMoney / 10000}"
								maxFractionDigits="2" />
							<i>万</i>
						</c:if>
						<c:if test="${actualMoney lt 10000}">
							<fmt:formatNumber value="${actualMoney}" maxFractionDigits="2" />
							<i>元</i>
						</c:if>
						投资总人数：${actualPeopelCount} 人 
						首投金额：￥<c:if test="${firstInvestMoney ge 10000}">
							<fmt:formatNumber value="${firstInvestMoney / 10000}"
								maxFractionDigits="2" />
							<i>万</i>
						</c:if>
						<c:if test="${firstInvestMoney lt 10000}">
							<fmt:formatNumber value="${firstInvestMoney}" maxFractionDigits="2" />
							<i>元</i>
						</c:if>
						首投人数： ${firstInvestPeople}人
						<%@ include file="../base/page.jsp"%>
					</div>
				</div>
			</div>
		</div>

	</section>
	<script type="text/javascript">
		$("#st").val('${status}');
		jQuery(document).ready(function() {
			// Date Picker
			jQuery('#datepicker').datepicker();
			jQuery('#datepicker1').datepicker();
			jQuery('#datepicker-inline').datepicker();

			jQuery('#datepicker-multiple').datepicker({
				numberOfMonths : 3,
				showButtonPanel : true
			});
		});
		jQuery(".select").select2({
			width : '100%',
			minimumResultsForSearch : -1
		});
		function datecheck() {
			var time1 = new Date($("#datepicker1").val()); //结束时间
			var time2 = new Date($("#datepicker").val()); //开始时间
			var date3 = time1.getTime() - time2.getTime(); //时间差的毫秒数
			//计算出相差天数
			var days = Math.floor(date3 / (24 * 3600 * 1000));
			if ($("#datepicker").val() == "" || $("#datepicker1").val() == "") {
				alert("查询的开始时间和结束时间不能为空");
				return false;
			} else if (date3 < 0) {
				alert("开始时间不能大于结束时间");
				return false;
			} else if (days > 31) {
				alert("只能查询一月内的数据，日期超出范围");
				return false;
			}
			return true;
		}
		$("button[id=export]").click(function() {
			if (!datecheck()) {
				return false
			}
			;
			$("#form1").attr("action", "/invest/export");
			$("#form1").submit();
		});
		$("button[id=search]").click(function() {
			if (!datecheck()) {
				return false
			}
			;
			$("#form1").attr("action", "/invest/investList");
			$("#form1").submit();
		});
		
		function giveMoneyToBorrowerFromInvest(investId){
			$.ajax({			
			type : 'POST',			
			url : "${cxt}/invest/giveMoneyToBorrowerFromInvest?investId="+investId, 
			beforeSend:function(){
					xval=getBusyOverlay('viewport',
					{color:'', opacity:0.5, text:'正在放款...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
				},			
			success : function(msg) {
					window.clearInterval(xval.ntime); 
							xval.remove();
							alert(msg);
				},
			error : function() {
					window.clearInterval(xval.ntime); 
					xval.remove();
				}
		});	
		}
	</script>
</body>
</html>
