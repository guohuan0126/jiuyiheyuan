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
<title>用户管理</title>
</head>
<body>
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<div class="mainpanel">
			<div class="pageheader">
				<h2>
					<i class="fa fa-pencil"></i>修改用户
				</h2>
			</div>
			<div class="contentpanel">
				<form id="basicForm" class="form-horizontal ">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">修改用户</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-1 control-label">用户名<span
									class="asterisk">*</span></label>
								<div class="col-sm-2">
									<input type="text" name="userId" placeholder="用户名..."
										class="form-control" disabled="disabled" />
								</div>
								<label class="col-sm-1 control-label">电子邮件<span
									class="asterisk">*</span></label>
								<div class="col-sm-2">
									<input type="text" name="email" placeholder="电子邮件..."
										class="form-control" disabled="disabled" />
								</div>
							</div>

						</div>
						<!-- panel-body -->
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button">保存</button>
									<button type="reset" class="btn btn-default">重置</button>
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
		jQuery(document).ready(function() {
			$("#bct").val('${user.status}');
			jQuery(".select2").select2({
				width : '30%',
				minimumResultsForSearch : -1
			});

		});
	</script>

</body>
</html>
