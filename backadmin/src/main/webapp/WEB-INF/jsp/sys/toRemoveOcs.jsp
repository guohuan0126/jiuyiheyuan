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

<title>缓存清理</title>


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

.only p {
	width: 400px;
	height: 100px;
	word-wrap: break-word;
	overflow-y: auto;
}

.only_1 {
	width: 100px;
	display: block;
	word-wrap: break-word;
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
					<i class="fa fa-table"></i>当前位置：缓存清理
				</h2>

			</div>

			<div class="contentpanel">

				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline">
							<div class="form-group">
								<div class="form-group">
									<input type="text" name="key" id="key" class="form-control"
										placeholder="Key">
								</div>
								<button type="button" class="btn btn-primary"
									onclick="sub('get')">查询</button>
								<button type="button" class="btn btn-primary"
									onclick="sub('del')">清除</button>
								<button type="button" class="btn btn-primary"
									onclick="sub('flush')">清空</button>
						</form>
						<div id="ocs" style="padding:10px; word-break: break-all;"></div>
					</div>

					<script type="text/javascript">
						function sub(type) {
							$.ajax({
								type : "POST",							
								url : "/sys/ocs/removeOcs/" + type,
								data : {
									key:$("#key").val()
								}, //要发送的是ajaxFrm表单中的数据						
								success : function(data) {
									$("#ocs").text(data);
								}
							});

						}
					</script>
				</div>
				<!-- panel-body -->
			</div>
			<!-- panel -->
		</div>
		<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>
	</script>
</body>
</html>
