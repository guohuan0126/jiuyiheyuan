<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="ThemeBucket">
<link rel="shortcut icon" href="#" type="image/png">

<title>登陆</title>

<link href="/css/style.css" rel="stylesheet">
<link href="/css/style-responsive.css" rel="stylesheet">
</head>

<body class="login-body">
	<div class="container">
		<form class="form-signin" action="index.html">
			<div class="form-signin-heading text-center">
				<h1 class="sign-title">Sign In</h1>
				<img src="images/login-logo.png" alt="" />
			</div>
			<div class="login-wrap">
				<input id="username" name="username" class="form-control"
					placeholder="用户名／手机号／电子邮箱" maxlength="20" onblur="GetPwdAndChk()" />
				<input id="password" name="password" type="password"
					class="form-control" placeholder="请输入密码" maxlength="16" /> <input
					class="btn btn-lg btn-login btn-block" type="button" id="button"
					value="登录" onclick="login()"> <i class="fa fa-check"></i>
			</div>
		</form>
	</div>
	<script type="text/javascript">
			$(document).keydown(function(event){
				if(event.keyCode == 13){ //绑定回车 
					$('#button').click(); //自动/触发登录按钮 
				} 
			}); 		  
//表单验证
function login(){	
				var username = $("#username").val();
				var password = $("#password").val();  	
      	
	username = $.trim(username);
	password = $.trim(password);

	if(username == ''){
		alert("账户不可以为空");
		return false;
	}
	if(password == ''){
		alert("密码不可以为空");
		return false;
	}
	
	
	$.ajax({
		type : "post",
		url : "/login",
		data : {
				"username" : username,
				"password" : password,
		},
		success : function(json) {
				if(json == "1"){  
				 	window.location.href="/showData";
				}else if (json == "-1"){
				alert("用户不存在！");
					 
				}else if(json == "-2"){
						alert("用户已被禁用！");
				} else if(json == "-3"){
						alert("密码输入错误！");
				}else if(json == "-4"){
					 alert("用户没有访问权限！");
			    }else if(json=="6"){
					alert("用户权限不足");	
					window.location.href="/";
			    }					
			}
	});}

</script>

	<!-- Placed js at the end of the document so the pages load faster -->

	<!-- Placed js at the end of the document so the pages load faster -->
	<script src="js/jquery-1.10.2.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/modernizr.min.js"></script>

</body>
</html>
