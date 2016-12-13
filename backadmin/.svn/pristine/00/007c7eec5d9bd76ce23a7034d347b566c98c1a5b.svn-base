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
					<i class="fa fa-pencil"></i> 添加系统配置参数
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">添加系统配置参数</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">编号<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="id" id="id" width="10px"
										class="form-control"/>
									<div id="errorId" style="display: none;">编号不能为空</div>
								</div>
							</div>
							
						<div class="form-group">
								<label class="col-sm-3 control-label">名称 <span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name" width="10px"
										class="form-control"/>	
									<div id="errorName" style="display: none;">名称不能为空</div>								
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">参数值 </label>
								<div class="col-sm-3">
									<input type="text" name="value" id="value" width="10px"
										class="form-control"/>
								</div>
							</div>
						<!-- 	<div class="form-group">
								<label class="col-sm-3 control-label">类型</label>
								<div class="col-sm-3">
									<input type="text" name="type" id="type" width="10px"
										class="form-control"/>
								</div>
							</div> -->
							<div class="form-group">
								<label class="col-sm-3 control-label">描述</label>
								<div class="col-sm-3">
									<textarea rows="4" name="description" id="description"
										class="form-control"></textarea>
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
	
			$('#errorId').attr("style", "display:none");
			$('#errorName').attr("style", "display:none");
		
			if($("#id").val() == ''){
				$('#errorId').attr("style", "display:block;font-size:14px;color:red");
				return false;
			}
			if($("#name").val() == ''){
				$('#errorName').attr("style", "display:block;font-size:14px;color:red");
				return false;
			}
			$.ajax({
	            type: "POST",
	            url:"${ctx}/user/config/oprate/insert",             
	            data:$('#form').serialize(),// 你的formid
	            async: false,
	            error: function( e ) {
	                alert("添加失败");
	            },
	            success: function(data) {
	            	if(data == 'ok'){
	            	alert('添加成功');
	            	window.location="${ctx}/user/config/configList";
	                }else{
	                	alert('编号已存在');
	                }                 
	            }
	        });		
		}
		
	</script>

</body>
</html>
