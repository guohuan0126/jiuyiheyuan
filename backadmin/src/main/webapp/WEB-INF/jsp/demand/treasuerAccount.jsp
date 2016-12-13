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

<title>活期宝数据统计</title>
<style type="text/css">
#demandaccount span {
	margin-right: 20px;
}

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

div.all {
	margin: 0 auto 10px;
	border-bottom: 1px solid #ccc;
	padding-bottom: 16px;
}

div.all h2 {
	padding: 10px 0 0;
	font-size: 24px;
}

div.biao {
	overflow: hidden;
}

.first,.two,.three,.four,.five {
	float: left;
	height: 205px;
	margin-right: 10px;
}

.first .blue,.two .blue,.three .blue,.four .blue,.five .blue {
	width: 30px;
	height: 190px;
	position: relative;
}

.blue .jindu {
	position: absolute;
	bottom: 0;
	left: 0;
	background: #428bcb;
	width: 30px;
}

.blue .txt {
	position: absolute;
	left: 0;
}

.count {
	font-size: 20px;
	font-weight: 400;
	padding: 10px;
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
					<i class="fa fa-table"></i>当前位置：活期宝数据统计
				</h2>

			</div>

			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="count">
							活期宝总金额：
							<fmt:formatNumber type="currency" value="${account[0]}" />
						</div>
						<div class="count">
							活期宝累计产生收益：
							<fmt:formatNumber type="currency" value="${account[1]}" />
						</div>
						<div class="count">
							参与用户数量：
							<fmt:formatNumber type="number" value="${account[2]}" />
						</div>
					</div>
					<div class="panel-body">
						<form class="form-inline" action="${ctx}/demand/toTreasuerAccount"
							method="post">
							
							<base:active activeId="demand_interest_manual">
									<div class="panel-heading">
									<label  style="font-size: 20px;font-weight: bold;">手动派息</label>
									<div class="form-inline" style="margin-top:6px;">
										<input type="text" class="form-control"
											placeholder="派息时间（派发所选时间当日利息）" class="datepicker"
											id="demandDate" onchange="demandByUser()"> 
											
										<input type="text" class="form-control" placeholder="用户id/手机号"
											id="userId" onchange="demandByUser()">
											
										<button type="button" class="btn btn-primary"
											onclick="javascript:interest()">手动派息</button>
									</div>
									
									
									<script type="text/javascript">
										var d = new Date();
										d.setDate(d.getDate() - 1);
										jQuery('#demandDate').datepicker({
											dateFormat : 'yy-mm-dd',
											maxDate : d
										});
									</script>									
									<div id="demandaccount" style="color:red; margin-top:10px;"></div>
							</div>
							</base:active>
							<div class="panel-heading"> 
							<label  style="font-size: 20px;font-weight: bold;">活期宝累计发放利息</label>
							 <div class="form-inline" style="margin-top:20px;">
								<input type="text" class="form-control"
											placeholder="开始时间" class="datepicker"
											id="start" onchange="demandByUser()"> 
								<input type="text" class="form-control"
											placeholder="结束时间" class="datepicker"
											id="end" onchange="demandByUser()"> 
								<button type="button" class="btn btn-primary"
											onclick="javascript:query()">查询</button>
							</div>
							<div id="demandInterest" style="font-size:16px ;color:red; margin-top:10px;"></div>
							</div>
							
							<div class="panel-heading">
							<label  style="font-size: 20px;font-weight: bold;">活期宝数据统计</label>
							<div class="form-inline">
								<div >
									<select id="type" name="days" class="form-control"
										style="width:140px;">
										<option value="7">最近一周</option>
										<option value="30">最近一个月</option>
										<option value="90">最近三个月
											</option>
									</select>
									<button type="button" class="btn btn-primary"
									onclick="javascript:submit()">查询</button>
								</div>
								
								
							</div>
							</div>
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
						<!-- 查询活期宝派息 -->	
							<script type="text/javascript">
								function query(){
									var start=$("#start").val();
									var end=$("#end").val();
									$.ajax({
										dataType:"json",
										type : 'POST',
										url : '/demand/demandMoney',
												
										data : {
											start : start,
											end:end
										},
										success : function(data) {
											
											var money = data.money;
												money=money+"元";
											
											$("#demandInterest").html('活期宝利息：'+money);
										}
									});	
									
								}
							
							</script>

							<script type="text/javascript">
								/* 查询用户活期宝信息  */
								function demandByUser() {
									var userId = $("#userId").val();
									var d = $("#demandDate").val();
									if (userId != '') {
										if (d == '') {
											d = new Date();
										}
										$.ajax({
													type : 'POST',
													url : '/demand/demandByUser',
													data : {
														userId : userId,
														d : d
													},
													beforeSend : function() {
														xval = getBusyOverlay(
																'viewport',
																{
																	color : 'black',
																	opacity : 0.3,
																	text : '正在处理，请稍后...',
																	style : 'text-shadow: 0 0 3px black;font-size:18px;'
																},
																{
																	color : 'blue',
																	size : 25,
																	type : 'o'
																});
													},
													success : function(data) {
														var $str = '';
														$("#demandaccount span")
																.remove();
														window
																.clearInterval(xval.ntime);
														xval.remove();
														if (data == "not") {
															$str += "<span>该用户未在平台注册</span>";
														} else if (data == "nod") {
															$str += "<span>该用户未参与活期宝</span>";
														} else {
															var acount = eval(data);
															var isInterest = acount[0].isInterest == 1 ? "是"
																	: "否";
															$str += "<span>用户ID："
																	+ acount[0].userId
																	+ "</span><span>用户手机号："
																	+ acount[0].mobile
																	+ "</span><span>姓名："
																	+ acount[0].realname
																	+ "</span><span>计息本金："
																	+ acount[0].money
																	+ "元</span><span>是否已派息："
																	+ isInterest
																	+ "</span>";
														}

														$("#demandaccount")
																.append($str);
													},
													error : function() {
														window
																.clearInterval(xval.ntime);
														xval.remove();
													}
												});

									}

								}

								/* 手动派息  */
								function interest() {

									var userId = $("#userId").val();
									var date = $("#demandDate").val();
									if (date == '') {
										alert("请输入日期");
										return;
									}

									$.ajax({
												type : 'POST',
												url : '/demand/insertDayDate/'
														+ date,
												data : {
													userId : userId

												},
												beforeSend : function() {
													xval = getBusyOverlay(
															'viewport',
															{
																color : 'black',
																opacity : 0.3,
																text : '正在处理，请稍后...',
																style : 'text-shadow: 0 0 3px black;font-size:18px;'
															}, {
																color : 'blue',
																size : 25,
																type : 'o'
															});
												},
												success : function(data) {
													window
															.clearInterval(xval.ntime);
													xval.remove();
													if (data == 'ok') {
														alert("派息成功");
													} else if (data == "not") {

														alert("不能派发当日即以后利息");
													} else {
														alert("派息失败");
														location.reload()
													}
												},
												error : function() {
													window
															.clearInterval(xval.ntime);
													xval.remove();
													alert("异常！");
												}
											});
								}
							</script>
						</form>
						<div class="all">
							<h2>转入数据</h2>
							<div class="biao">
								<c:forEach items="${in}" var="in">
									<div class="first">
										<div class="blue">
											<span
												style="position:absolute;left:0;bottom:${in.totalMoney/in.highMoney*100+in.totalMoney%100-2 }px;">
												<c:if test="${in.totalMoney < 10000}">
													<fmt:formatNumber type="currency" value="${in.totalMoney}" />
												</c:if> <c:if test="${in.totalMoney >= 10000}">
													<fmt:formatNumber type="currency"
														value="${in.totalMoney / 10000}" />万
												</c:if>
											</span> <span
												style="position:absolute;bottom:0;left:0;background:#428bcb;width:80%;height:${in.totalMoney/in.highMoney*100+in.totalMoney%100}px;"></span>
										</div>
										<span><fmt:formatDate value="${in.createTime }"
												pattern="MM/dd" /></span>
									</div>
								</c:forEach>
							</div>
						</div>

						<div class="all">
							<h2>转出数据</h2>
							<div class="biao">
								<c:forEach items="${out}" var="out">
									<div class="first">
										<div class="blue">
											<span
												style="position:absolute;left:0;bottom:${out.totalMoney/out.highMoney*100+out.totalMoney%100-2 }px;">
												<c:if test="${out.totalMoney < 10000}">
													<fmt:formatNumber type="currency" value="${out.totalMoney}" />
												</c:if> <c:if test="${out.totalMoney >= 10000}">
													<fmt:formatNumber type="currency"
														value="${out.totalMoney / 10000}" />万
												</c:if>
											</span> <span
												style="position:absolute;bottom:0;left:0;background:#428bcb;width:80%;height:${out.totalMoney/out.highMoney*100+out.totalMoney%100}px;"></span>
										</div>
										<span><fmt:formatDate value="${out.createTime }"
												pattern="MM/dd" /></span>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="all">
							<h2>派息数据</h2>
							<div class="biao">
								<c:forEach items="${interest}" var="interest">
									<div class="first">
										<div class="blue">
											<span
												style="position:absolute;left:0;bottom:${interest.totalMoney/interest.highMoney*100+interest.totalMoney%100-2 }px;">
												<c:if test="${interest.totalMoney < 10000}">
													<fmt:formatNumber type="currency"
														value="${interest.totalMoney}" />
												</c:if> <c:if test="${interest.totalMoney >= 10000}">
													<fmt:formatNumber type="currency"
														value="${interest.totalMoney / 10000}" />万
												</c:if>

											</span> <span
												style="position:absolute;bottom:0;left:0;background:#428bcb;width:80%;height:${interest.totalMoney/interest.highMoney*100+interest.totalMoney%100}px;"></span>
										</div>
										<span><fmt:formatDate value="${interest.createTime }"
												pattern="MM/dd" /></span>
									</div>
								</c:forEach>
							</div>
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
