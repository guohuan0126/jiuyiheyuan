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
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
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
<title>查看手机用户反馈</title>
</head>
<body>
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
					<i class="fa fa-table"></i>当前位置：查看手机用户反馈
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
						<form class="form-inline" action="/appFeedback/list" method="post">
							<label class="control-label">系统版本 ：</label>
							<div class="form-group" style="width: 110px;">
								<select id="osName" name="osName" class="select" required
									data-placeholder="请选择...">
									<option value="">全部</option>
									<option value="android"
										<c:if test="${osName == 'android'}">selected="selected"</c:if>>android</option>
									<option value="ios"
										<c:if test="${osName == 'ios'}">selected="selected"</c:if>>ios</option>
								</select>
							</div>
							<script type="text/javascript">
								jQuery(".select").select2({
									width : '100%',
									minimumResultsForSearch : -1
								});
							</script>
							<label class="control-label">反馈提交时间：</label>
							<div class="input-group">
								<input type="text" class="form-control datepicker"
									value="${startTime}" id="startTime" name="startTime"
									placeholder="开始时间" style="width:165px">

							</div>
							--
							<div class="input-group">
								<input type="text" class="form-control datepicker"
									value="${endTime}" id="endTime" name="endTime"
									placeholder="结束时间" style="width:165px">

							</div>
							<script type="text/javascript">
								jQuery(document).ready(function() {
									jQuery('.datepicker').datetimepicker({
										showSecond : true,
										timeFormat : 'hh:mm:ss',
										stepHour : 1,
										stepMinute : 1,
										stepSecond : 1,
									});
								});
							</script>
							<input type="hidden" name="pageSize" value="30" />
							<button type="button" class="btn btn-primary"
								onclick="javascript:submit()">查询</button>
							<!--  <button type="button" class="btn btn-primary" onclick="javascript:reset()">重置</button> -->
							<base:active activeId="loan_export">

								<a href="javascript:exportLoan();" class="btn btn-primary">导出</a>
								<script type="text/javascript">
									function exportLoan() {
										var beginTime = $("#startTime").val();
										var endTime = $("#endTime").val();
										var osName = $("#osName").val();
										location = "${ctx}/appFeedback/export?startTime="
												+ beginTime
												+ "&endTime="
												+ endTime + "&osName=" + osName;

									}
								</script>

							</base:active>
						</form>

					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>
								<tr class="font">
									<th>编号</th>
									<th>系统类型</th>
									<th>反馈内容</th>
									<base:active activeId="USER_LOOK">
										<th>联系方式/真实姓名</th>
									</base:active>
									<th>反馈状态</th>
									<th>反馈时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="appFeed" items="${pageInfo.results}">
									<tr class="font">
										<td>${appFeed.id}</td>
										<td>${appFeed.osName}</td>
										<td>										
										
											<base:urlDecoder value="${appFeed.content}"/>			
										</td>
										
										<base:active activeId="USER_LOOK">
											<td>${appFeed.contact}<br> <c:if
													test="${not  empty appFeed.realname}">
													<a style="cursor:pointer"
														onclick="edit('${appFeed.userId}');">${appFeed.realname}</a>
												</c:if>
											</td>
										</base:active>
										<td><c:if test="${appFeed.handleType==0}"><a href="/appFeedback/changeHandleType?id=${appFeed.id}&handleType=1" style="color:red; ">未处理</a> </c:if>
											<c:if test="${appFeed.handleType==1}"><a href="/appFeedback/changeHandleType?id=${appFeed.id}&handleType=0" style="color: gray;">已处理</a> </c:if>
										</td>
										<td><fmt:formatDate value="${appFeed.time}" type="both" /></td>
									</tr>
								</c:forEach>
							</tbody> 
						</table>
						<input type="hidden" id="userpid">
						<script type="text/javascript">
							function edit(id) {
								var userid = id;
								$("#userpid").val(userid);
								ds
										.dialog({
											title : "编辑",
											content : '<iframe  src="${ctx }/tormark.jsp" width="100%" height="100%"></iframe>',
											width : 800,
											height : 700
										});
							}
						</script>
						<%@ include file="../base/page.jsp"%>
					</div>
				</div>
			</div>
		</div>


	</section>

</body>
</html>
