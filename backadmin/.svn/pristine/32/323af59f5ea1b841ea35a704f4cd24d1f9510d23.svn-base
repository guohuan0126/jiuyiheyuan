<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<title>编辑数据字典信息</title>
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
					<i class="fa fa-pencil"></i> 编辑数据字典信息
				</h2>
			</div>
			<div class="contentpanel">
				<form id="f1" name="form" method="post" class="form-horizontal" action="${ctx }/dictionary/editSave">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">编辑数据字典信息</h4>
						</div>
						
						<div class="panel-body">
							<table class="table table-primary table-striped table-hover">
									<thead>
										<tr class="font">
											<th>序号</th>
											<th>类别名称</th>
											<th>类别编码</th>
											<th>选项名称</th>
											<th>选项编码</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${dicLst }" varStatus="status">
											<tr class="font">
												<td>${status.index+1 }</td>
												<td>${item.typeName }</td>
												<td>${item.typeCode }</td>
												<td>${item.itemName }</td>
												<td>${item.itemCode }</td>
												<td>
													<a href="javascript:void(0);" onclick="edit('${item.id}');">编辑</a>&nbsp;|&nbsp;
													<a href="javascript:void(0);" onclick="del('${item.id}');">删除</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
						</div>
						
						<div class="panel-body">
							<input type="hidden" name="id" id="dicId" value="">
							<div class="form-group">
								<label class="col-sm-3 control-label">类别名称 </label>
								<div class="col-sm-3">
									<input type="text" name="typeName" id="typeName" width="10px" value=""
										class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">类别编码 </label>
								<div class="col-sm-3">
									<input type="text" name="typeCode" id="typeCode" id="typeCode" value=""
										width="10px"  class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">选项名称 </label>
								<div class="col-sm-3">
									<input type="text" name="itemName" id="itemName" width="10px" value="" 
										class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">选项编码 </label>
								<div class="col-sm-3">
									<input type="text" name="itemCode" id="itemCode" value=""
										width="10px" class="form-control" />
								</div>
							</div>

							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button" onclick="save()">保存</button>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" class="btn btn-primary" onclick="javascript:clearForm();">清空</button>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" class="btn btn-default" onclick="javascript:retBack();">返回</button>
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
		function retBack(){
			window.location.href='${ctx}/dictionary/dicList';
		}
		//保存
		function save(){
			if( !checkNotNull() )
				return false;
			var vle = $("#typeCode").val()
			$.ajax({				
				type : 'POST',
				url : '/dictionary/editSave',
				async: false,
				data:$("#f1").serialize(),
				success : function(data) {
					if( data == 'true' ){
						window.location.href='${ctx }/dictionary/toEditByTypeCode?typeCode='+vle;
					}else{
						alert("操作失败，请重试！");
						location.reload();
					}
				},
				error:function(){
					alert("操作失败！");
				}
			});
		}
		
		//编辑
		function edit(id){
			$.ajax({				
				type : 'POST',
				url : '/dictionary/getDicById',
				data:{
					id:id
				},
				dataType:'json',
				success : function(data) {
					$("#dicId").val(data.id);
					$("#typeName").val(data.typeName);
					$("#typeCode").val(data.typeCode);
					$("#itemName").val(data.itemName);
					$("#itemCode").val(data.itemCode);
				},
				error:function(){
					alert("操作失败！");
					window.reload();
				}
			});
		}
		
		//清空
		function clearForm(){
			$("#dicId").val("");
			$("#itemName").val("");
			$("#itemCode").val("");
		}
		
		//删除
		function del(id){
			if( confirm("确认删除？") ){
				window.location.href="${ctx}/dictionary/delDicById/"+id;
				$.ajax({				
					type : 'POST',
					url : '${ctx}/dictionary/delDicById/'+id,
					async: false,
					success : function(data) {
						if( data == 'true' ){
							location.reload();
						}else{
							alert("操作失败，请重试！");
							location.reload();
						}
					},
					error:function(){
						alert("操作失败！");
						location.reload();
					}
				});
			}
		}
		
		$(function(){
			if( $("tbody tr").length > 0 ){
				var typeName = $("tbody tr:eq(0) td:eq(1)").text();
				var typeCode = $("tbody tr:eq(0) td:eq(2)").text();
				$("#typeCode").val(typeCode);
				$("#typeName").val(typeName);
				$("#typeCode").attr("readonly",true);
				$("#typeName").attr("readonly",true);
			}else{
				$("#typeCode").blur(function(){
					$(this).val($(this).val().toUpperCase());
				});
			}
			$("#itemCode").blur(function(){
				$(this).val($(this).val().toUpperCase());
			});
		});
		
		//校验非空
		function checkNotNull(){
			var check = true;
			if( $("#typeName").val() == '' ){
				$("#typeName").css('border','1px solid red');
				return false;
			}else{
				$("#typeName").css('border','');
			}
			if( $("#typeCode").val() == '' ){
				$("#typeCode").css('border','1px solid red');
				return false;
			}else{
				$("#typeCode").css('border','');
			}
			if( $("#itemName").val() == '' ){
				$("#itemName").css('border','1px solid red');
				return false;
			}else{
				$("#itemName").css('border','');
			}
			if( $("#itemCode").val() == '' ){
				$("#itemCode").css('border','1px solid red');
				return false;
			}else{
				$("#itemCode").css('border','');
			}
			return check;
		}
	</script>
</body>
</html>
