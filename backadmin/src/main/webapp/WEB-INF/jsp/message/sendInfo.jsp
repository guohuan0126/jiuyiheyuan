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

<title>站内信群发</title>
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
					<i class="fa fa-table"></i>当前位置：站内信管理
				</h2>

			</div>

			<div class="contentpanel">

				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" name="form" id="form" method="post">
							<div class="form-group">
								<label class="sr-only">标题</label> <input type="text"
									name="title" class="form-control" placeholder="标题">
							</div>
							<div class="form-group">
								<label class="sr-only">内容</label> <input type="text"
									name="content" class="form-control" placeholder="内容">
							</div>
							<button type="button" class="btn btn-primary" onclick="send();">发送</button>
						</form>
					</div>
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>

	<script>
		function send() {			
			$.ajax({
					type : 'POST',
					url : '${ctx}/info/send',						
					data:$('#form').serialize(),
					beforeSend:function(){
						xval=getBusyOverlay('viewport',
						{color:'', opacity:0.5, text:'正在发送...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
						{color:'blue', size:25, type:'o'});	
					},
					success:function(data) {
						window.clearInterval(xval.ntime); 
						xval.remove();
						alert("批量发送完毕，共发送 "+data+"条信息");				 					
						
					}
				});		
			
		}
	</script>
</body>
</html>
