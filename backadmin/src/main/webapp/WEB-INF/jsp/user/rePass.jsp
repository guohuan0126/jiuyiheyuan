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

<title>修改密码</title>
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
					<i class="fa fa-pencil"></i> 修改密码
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">修改密码</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">用户编号 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="id" id="id"  width="10px"
										class="form-control" readonly value="${loginUser.userId }"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">旧密码<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="password" name="oldPass"  id="oldPass"
										placeholder="密码不能为空" class="form-control"  />
									<div id="errorOld" style="display: none;">旧密码不能为空
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">新密码<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="password" name="newPass"  id="newPass"
										placeholder="密码不能为空" class="form-control"  />
									<div id="errorNew" style="display: none;">新密码不能为空
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">确认密码<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="password" name="confirmPass"  id="confirmPass"
										placeholder="密码不能为空" class="form-control"  />
									<div id="errorConfirm" style="display: none;">确认密码不能为空
									</div>
								</div>
							</div>													
						</div>
						<!-- panel-body -->
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<input class="btn btn-primary" type="button" onclick="sub();" value="保存"/>
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
			if(checkForm()){		
				$.ajax({				
					type : 'GET',
					url : '/rePass',					
					data:$('#form').serialize(),
					success : function(data) {
						if(data == "OK"){
							alert("密码修改成功！");
							location="/index";							
						}else if(data == "NU"){
							$("#errorConfirm").text("用户不存在！");
							$("#errorConfirm").attr("style", "display:block;font-size:14px;color:red");							
						}else if(data == "RP"){
							$("#errorOld").text("旧密码输入错误！");
							$("#errorOld").attr("style", "display:block;font-size:14px;color:red");
						}else if(data == "ER"){
							$("#errorNew").text("密码格式错误");
							$("#errorNew").attr("style", "display:block;font-size:14px;color:red");
						}
													
					}
				});
			}				
		}	
	
		function checkForm(){
			$("#errorOld").attr("style", "display:none;");
			$("#errorNew").attr("style", "display:none;");
			$("#errorConfirm").attr("style", "display:none;");
			
			var old = $("#oldPass").val();
			
			var new1 = $("#newPass").val();
			
			var confirm = $("#confirmPass").val();
		
			if(old == ""){
				$("#errorOld").attr("style", "display:block;font-size:14px;color:red");
				return false;
			}
			
			if(new1 == ''){
				$("#errorNew").attr("style", "display:block;font-size:14px;color:red");
				return false;
			}
					
			var ret = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/ig;
			if(!ret.test(new1)){			
				$("#errorNew").text("密码为6-16位的数字和字母");
				$("#errorNew").attr("style", "display:block;font-size:14px;color:red");
				return false;
			}
			if(confirm == ''){
				$("#errorConfirm").attr("style", "display:block;font-size:14px;color:red");
				return false;
			}
			
			if(confirm != new1){
				$("#errorConfirm").text("两次输入的密码不一致");
				$("#errorConfirm").attr("style", "display:block;font-size:14px;color:red");
				return false;
			}
			return true;			
		}		
		
	</script>

</body>
</html>
