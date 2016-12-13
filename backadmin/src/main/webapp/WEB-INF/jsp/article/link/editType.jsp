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
<title>友情链接类型管理</title>	
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
					<i class="fa fa-pencil"></i> 修改链接类型
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">修改链接类型</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">编号 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="id" id="id" width="10px"
										class="form-control" placeholder="编号" readonly  value="${type.id}"/>
									<div id="errorId" style="display: none;">编号不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">名称 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name" width="10px"
										class="form-control" placeholder="名称不能为空" value="${type.name}"/>
									<div id="errorName" style="display: none;">名称不能为空</div>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">描述 </label>
								<div class="col-sm-3">
									<textarea rows="4" name="description" class="form-control">${type.description}</textarea>
								</div>
								
							</div>							
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="sub()">更新</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="reset" class="btn btn-default">重置</button>
								</div>
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
		$("#errorId").attr("style", "display:none;");
		$("#errorName").attr("style", "display:none;");
		var id = $("#id").val();
		var name = $("#name").val();
		
		if(id == ''){
			$('#errorId').attr("style", "display:block;font-size:14px;color:red");
			return;
		}
		
		if(name == ''){
			$('#errorName').attr("style", "display:block;font-size:14px;color:red");
			return;
		}
		$.ajax({
			type : 'POST',
			url : '${ctx}/linkType/editType',						
			data:$('#form').serialize(),
			success:function(data) {
				if(data == 'ok'){
					alert("修改成功");
					location="${ctx}/linkType/typeView"; 					
				}else if(data == 'empty'){
					$('#errorId').text("要修改的链接类型不存在");
					$('#errorId').attr("style", "display:block;font-size:14px;color:red");
					return ;
				}else{					
					alert("修改失败");
				}
			}
		});
	}
 </script>
	
</body>
</html>
