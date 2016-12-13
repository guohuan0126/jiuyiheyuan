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
<title>创建术语</title>
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
					<i class="fa fa-pencil"></i> 创建术语
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">创建术语</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">编号 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="id" id="id"  width="10px"
										class="form-control" placeholder="编号不能为空"  />
									<div id="errorId" style="display: none;">编号不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">名称<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name"  width="10px"
										class="form-control" placeholder="名称不能为空"/>
										<div id="errorName" style="display: none;">名称不能为空</div>									
								</div>
							</div>																						
							<div class="form-group">
								<label class="col-sm-3 control-label">描述</label>
								<div class="col-sm-3" style="width:40%">								
									<textarea rows="3" name="description"  id="description" class="form-control"></textarea>	
								</div>																									
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">顺序</label>
								<div class="col-sm-9">
					                <input type="number" id="sqlNum" name="sqlNum" value="0" min="0"/>					                
					            </div>					            																															
							</div>
												
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="return sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="btn" type="button" onclick="ret()">重置</button>
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
		<script type="text/javascript">
			function sub(){
				$("#errorId").attr("style", "display: none");
				$("#errorName").attr("style", "display: none");
				var id = $("#id").val();
				var name = $("#name").val();
				if(id == ''){
					$("#errorId").attr("style", "display:block;font-size:14px;color:red");
					return;
				}
				if(name == ''){
					$("#errorName").attr("style", "display:block;font-size:14px;color:red");
					return ;
				}
				$.ajax({
					type : 'POST',
					url : '${ctx}/article/categoryTerm/create',						
					data:$('#form').serialize(),
					success:function(data) {
						if(data == 'ok'){
							alert("添加成功");
							location = "${cxt}/article/categoryTermList";						 					
						}else if(data == 'notNull'){
							$('#errorId').text("编号重复");
							$('#errorId').attr("style", "display:block;font-size:14px;color:red");
							return ;
						}else if(data == 'notName'){
							$('#errorName').text("名称重复");
							$('#errorName').attr("style", "display:block;font-size:14px;color:red");							
							return ;
						}else{					
							alert("添加失败");
						}
					}
				});			
			}
			
			function ret(){
				$("#errorId").attr("style", "display: none");
				$("#errorName").attr("style", "display: none");
				document.form.reset();
			}				
		</script>		
	</section>
</body>
</html>
