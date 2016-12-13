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
<title>新建工作流模板</title>	
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
					<i class="fa fa-pencil"></i> 创建工作流模板
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form1" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">创建工作流模板</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">工作流名称 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name" width="10px"
										class="form-control" placeholder="名称不能为空" />
									<div id="errorName" style="display: none;">名称不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">工作流描述 </label>
								<div class="col-sm-3">
									<textarea rows="4" name="description" id="description"
										class="form-control"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">添加流程节点</label>
								<div class="col-sm-3">
									<a style="font-size:30px" onclick="operationInput()">+</a>
								</div>
							</div>
						</div>
						<div class="contentpanel" style="padding:0px 20px 0px 20px;">
							<table class="table table-primary table-striped table-hover">
								<thead>
									<tr class="font">
										<th width="30%">节点名称</th>
										<th width="15%">加点顺序</th>
										<th width="25%">执行角色</th>
										<th width="30%">描述</th>
									</tr>
								</thead>
								<tbody id="flowNode">
								</tbody>
							</table>
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
		/* 多选 下拉框 */
		function multiple(num) {
			jQuery(".select"+num).select2({
				width : '100%',
				minimumResultsForSearch : -1
			});
		
			jQuery("#basicForm").validate(
					{
						highlight : function(element) {
							jQuery(element).closest('.form-group').removeClass(
									'has-success').addClass('has-error');
						},
						success : function(element) {
							jQuery(element).closest('.form-group').removeClass(
									'has-error');
						}
					});

		
			jQuery("#basicForm2").validate({
				errorLabelContainer : jQuery("#basicForm2 div.error")
			});

			jQuery("#basicForm3").validate(
					{
						highlight : function(element) {
							jQuery(element).closest('.form-group').removeClass(
									'has-success').addClass('has-error');
						},
						success : function(element) {
							jQuery(element).closest('.form-group').removeClass(
									'has-error');
						}
					});

			jQuery("#basicForm4").validate(
					{
						highlight : function(element) {
							jQuery(element).closest('.form-group').removeClass(
									'has-success').addClass('has-error');
						},
						success : function(element) {
							jQuery(element).closest('.form-group').removeClass(
									'has-error');
						}
					});
		}

		var num = 1;
		var roles;
		/* 获取角色信息  */
		jQuery(document).ready(function() {
			/* ajax 获取酒色信息  */
			$.ajax({
				type : 'GET',
				url : 'getRoles',
				dataType : 'json',
				success : function(data) {
					roles = eval(data);
				},
				error : function() {
					alert("异常！");
				}
			});
		});

		/* 添加节点  */
		function operationInput() {
			$str = '';
			$str += "<tr class='font'>";	
			$str += "<td><input type='hidden' name='nodeId"+num+"' id='nodeId"+num+"' value='0'/><input type='text' name='nodeName"+num+"' id='nodeName"+num+"' class='form-control' placeholder='名称不能为空'/><div id='errorName"+num+"' style='display: none;'>名称不能为空</div></td>";
			$str += "<td><input type='text' name='nodeOrder"+num+"' id='nodeOrder"+num+"' value="+num+" class='form-control' readOnly /></td>";
			$str += "<td><div class='col-sm-9' style='width:100%;'><select id='role"+num+"' name='role"+num+"' class='select"+num+"' multiple data-placeholder='请选择...'>";
			for (var i = 0; i < roles.length; i++) {
				$str += "<option value='"+roles[i].id+"'>" + roles[i].name
						+ "</option>";
			}
			$str += "</select></div></td>";
			$str += "<td><input type='text' name='nodeDescription"+num+"' id='nodeDescription"+num+"' class='form-control' /></td>";
			$str += "</tr>";
			$('#flowNode').append($str);
			multiple(num);
			num++;
		}					
		function sub(){
			
			$('#errorName').attr("style", "display:none;");
			for(var i = 1; i<num; i++ ){
				$("#errorName"+i).attr("style", "display:none;");
			}
			var name = $('#name').val();
			var description = $('#description').val();
			if(name == '') {
				$('#errorName').attr("style", "display:block;font-size:14px;color:red");
				return false;		
			}
			
			var nodes = "";
			for(var i = 1; i<num; i++){
				var nodeName = $("#nodeName"+i).val();
				if(nodeName == ''){
					$("#errorName"+i).attr("style", "display:block;font-size:14px;color:red;");
					return false;
				}
				var nodeOrder = $("#nodeOrder"+i).val();
				var roleId = $("#role"+i).val();
				var nodeId = $("#nodeId"+i).val();
				var nodeDescription = " "+$("#nodeDescription"+i).val();								
				nodes += nodeId+"、"+nodeName+"、"+nodeOrder+"、"+roleId+"、"+nodeDescription+";";							
			}
			if(nodes == ''){
				alert("请添加流程节点!");
				return false;
			}
			$.ajax({
				type : 'POST',
				url : 'addFlow',
				data:{
					'name':name,
					'description':description,
					'nodes':nodes
				},
				success : function(msg) {
					if(msg == 'ok'){
						alert("工作流模板创建成功!");
						window.location = "getFlowList";
					}else{
						alert("工作流创建失败!");
					}
				},
				error : function() {
					alert("异常！");
				}
			});
		};
	</script>

</body>
</html>