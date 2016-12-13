<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>
<meta charset="utf-8">
<title>CPS营销系统</title>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<script src="/js/jquery-1.11.0.min.js"></script>
<script src="/js/index.js"></script>
<script src="/js/menu.js"></script>
<script src="/js/jquery.e-calendar.js"></script>
<script src="/js/jquery.toggle-password.js"></script>
</head>
 <body style="background:#438be9;">
	<div class="login-bound">
    	<div class="login-box-big">
        	<div class="login-box-small">
        		<div class="login-box">
                	<h1><img src="/images/logo-blue.png" width="132" height="46"></h1>
                    <h3>CPS营销系统</h3>
                    <div class="box-main">
                    <form id="form">
                    <div style="margin-left:30px;">
                        <input id="username" name="username" class="name"
								placeholder="用户名／手机号／电子邮箱" maxlength="20" onblur="GetPwdAndChk()"/>
			            <input id="password" name="password" type="password" class="password"
								 placeholder="请输入密码" maxlength="16"/>
				    </div>
                  <div class="zhanghao">
           <!--  记住账号      -->  
            <div style="width:250px;height:30px;">          
                  <div style="width:30px;height:30px;margin-left:-120px;margin-top:-10px;"> 
                <input type="checkbox" id="togglePassword1">
                  </div>
                 <div style="margin-left:50px; margin-top:-18px;">记住账号</div> 
             </div>                 
                                 
          <!--   显示密码     -->    
           <div style="width:350px;height:30px; margin-top:-30px;">            
                 <div style="width:30px; height:30px;margin-left:110px;margin-top:-7px;"> 
                <input type="checkbox" id="togglePassword">
                 </div>
                    <div style="margin-left:280px;margin-top:-18px;">显示密码</div>
            </div>
             <!--  这是zhanghao的div层 -->
                 </div>                 
                 <input class="login-but" type="button" id="button" value="登录" onclick="login()">
                        </form>
                    </div>
                </div>
            </div>
        </div>
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
</body>
</html>
