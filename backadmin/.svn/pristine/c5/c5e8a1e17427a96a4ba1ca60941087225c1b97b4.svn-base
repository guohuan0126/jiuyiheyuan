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
<link rel="shortcut icon" href="images/favicon.png" type="image/png">

<title>更新手机归属地</title>
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
					<i class="fa fa-pencil"></i> 更新手机归属地
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">更新手机归属地</h4>
						</div>
						<div class="panel-body">
																
						</div>
						<!-- panel-body -->
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<input class="btn btn-primary" type="button" onclick="sub();" value="更新手机号归属地"/>
								</div>
							</div>
						</div>

					</div>				
					<!-- panel -->
				</form>
			</div>
			<!-- contentpanel -->

		</div>
		<!-- mainpanel -->

	</section>

	<script type="text/javascript">

		function sub(){	
			$.ajax({				
				type : 'POST',
				url : '/user/phonecity',
				async : false,
				beforeSend:function(){
					xval=getBusyOverlay('viewport',
					{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
				},
				success : function(data) {
					window.clearInterval(xval.ntime); 
					xval.remove();
					if(data == "ok"){
						alert("更新成功！");
					}else {
						alert("更新失败！");
					}
												
				}
			});
		}	
	
	</script>

</body>
</html>
