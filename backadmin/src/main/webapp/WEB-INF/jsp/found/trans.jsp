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
<title>取消授权</title>
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
					<i class="fa fa-pencil"></i> 取消授权
				</h2>
				<div class="breadcrumb-wrapper">
					<span class="label"></span>
					<ol class="breadcrumb">
						<li></li>
						<li></li>
						<li class="active"></li>
					</ol>
				</div>
			</div>

			<div class="contentpanel">
				<form id="basicForm" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">确认转账</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">请求流水号</label>
								<div class="col-sm-2">
									<input type="text" name="reqNo" id="reqNo" width="10px"
										class="form-control" />
								</div>
								
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">操作类型 <!-- <span
									class="asterisk">*</span> --></label>
							 <select class="select2" name="type"  id="type">
				                  <option value="CONFIRM">确认</option>
				                  <option value="CANCEL">取消</option>
			                </select>
			             	</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button" onclick="sub();">确认</button>
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

	<script>
		function sub() {
			if (confirm("确认操作吗?")) {

				$.ajax({
					type : "POST",
					url : '${ctx}/trans/cancel',
					data : $('#basicForm').serialize(),// 你的formid
					async : false,
					error : function(e) {
						alert("异常");
					},
					success : function(data) {
						if (data == 'ok') {
							alert('操作成功');
						} else if(data == 'fail'){
							alert('操作失败')
						}else if(data == 'req'){
							alert('请求异常');
						}else{
							alert('系统异常');
						}
					}
				});

			}
		}
	</script>

</body>
</html>
