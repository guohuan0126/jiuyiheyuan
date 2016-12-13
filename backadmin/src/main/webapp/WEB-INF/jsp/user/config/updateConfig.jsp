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
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>系统参数</title>	
</head>
<body>
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../../base/leftMenu.jsp"%>
		<div class="mainpanel">
			<%@include file="../../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-pencil"></i> 修改系统配置参数
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">修改系统配置参数</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">编号</label>
								<div class="col-sm-3">
									<input type="text" name="id" id="id" width="10px"
										class="form-control"  readOnly value="${config.id}"/>
									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">名称 </label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name" width="10px"
										class="form-control"  readOnly value="${config.name}"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">参数值 </label>
								<div class="col-sm-3">
									<input type="text" name="value" id="value" width="10px"
										class="form-control" value="${config.value}"/>	
								</div>
							</div>
							<%-- <div class="form-group">
								<label class="col-sm-3 control-label">类型</label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name" width="10px"
										class="form-control"  value="${config.type}"/>	
								</div>
							</div> --%>
							<div class="form-group">
								<label class="col-sm-3 control-label">描述</label>
								<div class="col-sm-3">
									<textarea rows="4" name="description" id="description"
										class="form-control">${config.description}</textarea>
								</div>
							</div>
						</div>
						
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="sub()">保存</button>
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
	
	<script type="text/javascript">
		function sub(){	
			$.ajax({
	            type: "POST",
	            url:"${ctx}/user/config/oprate/update?id=${config.id}",             
	            data:$('#form').serialize(),// 你的formid
	            async: false,
	            error: function( e ) {
	                alert("修改失败");
	            },
	            success: function(data) {
	            	if(data == 'ok'){
	            	alert('修改成功');
	            	window.location="${ctx}/user/config/configList";
	                }else{
	                	alert('要修改的记录不存在');
	                }                 
	            }
	        });		
		}
		
	</script>

</body>
</html>
