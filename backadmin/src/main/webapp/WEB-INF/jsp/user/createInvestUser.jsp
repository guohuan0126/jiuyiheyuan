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
<link href='${ctx}/ueditor/themes/default/css/ueditor.min.css' type="text/css" rel="stylesheet" >
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>  
<!-- 编辑器源码文件 -->  
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>  
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) 此处可要可不要 -->   
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/js/validate.js"></script>
<title>创建用户</title>
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
					<i class="fa fa-pencil"></i>创建用户
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h5 class="panel-title">添加投资确认书发放用户信息</h5>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="asterisk"></span></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">手机号 </label>
								<div class="col-sm-3">
									<input type="text" name="mobileNumber" id="mob_num"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							</div>
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<script type="text/javascript">
			function sub(){
			var mobileNumber=$("#mob_num").val();
			if(mobileNumber==""){
				alert("手机号不能为空");
				return false;
			}	
				$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/user/addUserInfo",						
					data: {
						"mobileNumber":mobileNumber
					},
					success:function(data) {
					
						if(data=='success'){
							alert("添加成功");
							window.location.href="/user/toUserInvestCheck";
						}else{
							alert("添加失败");
						}
					}
				});			
			}				
		</script>		
	</section>
</body>
</html>
