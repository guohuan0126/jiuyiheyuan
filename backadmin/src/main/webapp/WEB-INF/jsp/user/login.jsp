<%@ page language="java" import="java.net.InetAddress, java.util.List, java.net.UnknownHostException" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="images/favicon.png" type="image/png">

  <title>短融网管理后台</title>

  <link href="css/style.default.css" rel="stylesheet">
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js" ></script>
  <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
  
</head>

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
		var validateCode = $("#validateCode").val();
		
		username = $.trim(username);
		password = $.trim(password);
		if(username == ''){
			$("#tipmgs").css("display", "block");
			$("#tipmgs p").text("用户名、手机号、电子邮箱不可以为空");
		}else if(password == ''){
			$("#tipmgs").css("display", "block");
			$("#tipmgs p").text("密码不可以为空");
		}else if($.trim(validateCode) == '' ){
			$("#tipmgs").css("display", "block");
			$("#tipmgs p").text("验证码不可以为空");
		}else{
			$.ajax({
				type : "POST",
				url : "<%=request.getContextPath()%>/memberLogin",
						data : {
							'username' : username,
							'password' : password,
							'validateCode':validateCode
						},
						dataType : "text",
						success : function(result) {
							if (result == 'fail') {
								$("#tipmgs").css("display", "block");
								reloadVerifyCode();
								$("#tipmgs p").text("登录失败!用户名、手机号、电子邮箱或密码不正确!");
							} else if (result == 'notAdmin') {
								$("#tipmgs").css("display", "block");
								$("#tipmgs p").text("没有管理员基本权限!");
							} else if (result == '/index'){
								$("#tipmgs p").text("")
								window.location.href = "/index";
							}else if(result == "validation_error_code"){
								$("#tipmgs").css("display", "block");
								reloadVerifyCode();
								$("#tipmgs p").text("验证码不正确!");
							}else{
								
							}
						}
					});
					
		}
	}
	// 重载验证码  
    function reloadVerifyCode(){  
           var timenow = new Date().getTime();                          
           document.getElementById("safecode").src="<%=request.getContextPath()%>/validateCode?d="+timenow;  
    } 
	$(function(){
		reloadVerifyCode();
	});
	</script>
<body class="signin">
<section style="background-color:#3382DF">
  
    <div class="signinpanel" >
        
        <div class="row" style="width:864px;">
            
            <div class="col-md-7">
                
                <div class="signin-info" style="color:white; padding-top:10px;">
                    <div class="logopanel">
                        <h1 style="color:white;"><span>[</span> 短融网管理后台 <span>]</span></h1>
                    </div><!-- logopanel -->
                
                    <div class="mb20"></div>
                
                    <h5><strong>欢迎使用短融网管理系统!</strong></h5>
                    <%
                    	boolean isIp = false;
                    	String ip = request.getRemoteAddr();
                    	if(ip.equals("127.0.0.1")){  
					         //根据网卡取本机配置的IP  
					    	 InetAddress inet=null;  
						     try {  
						    	  inet = InetAddress.getLocalHost();  
						     } catch (UnknownHostException e) {  
						    	   e.printStackTrace();  
						     }  
						     ip= inet.getHostAddress();  
						}  
						 //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
					     if(ip!=null && ip.length()>15){ 
					         if(ip.indexOf(",")>0){  
					        	 ip = ip.substring(0,ip.indexOf(","));  
					         }  
					     } 	   
					     
					     List<String> ips = (List<String>)request.getAttribute("ips");
					     for(String ip1 : ips){
					     	if(ip.equals(ip1)){
					     		isIp = true;
					     	}
					     }
					     System.out.print(isIp);
                     %>
                    <ul>
                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> 平台用户管理</li>
                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> 借款项目管理</li>
                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> 资金交易管理</li>
                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> 数据统计</li>
                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> 系统管理</li>
                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> 当前登录IP:<%if(isIp == false){%><span style="color:orange;"><%=ip%> (此IP不可访问系统)</span><%}else{%><%=ip%><%}%>           
                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> IP被拦截或其它问题请发送邮件至zhangjunying@duanrong.com						
                        </li>						
                    </ul>
                    <!-- 搜狐接口 获取客户端ip  -->
                    <!-- <script type="text/javascript">
                    		$(document).ready(function() {  
							  
							  alert(returnCitySN["cip"]+','+returnCitySN["cname"]);   
							})
                    
                    </script> -->
                    <div class="mb20"></div>
                    
                </div><!-- signin0-info -->
            
            </div><!-- col-sm-7 -->
            
            <div class="col-md-5" style="color:white; padding-top:10px;">
                
                <form action="/memberLogin" method="post" id="user-login-form"
					accept-charset="UTF-8">
                    <h4 class="nomargin">登录</h4>
               		<input id="username" type="text" name="username" class="form-control uname"  placeholder="用户名|手机号|电子邮箱" />
              		<input id="password" type="password" name="password" class="form-control pword" placeholder="密码"  /> 
                   <style> 
					.class-a{ float:left;width:60%;padding:10px; margin-top: 10px;margin-bottom: 10px;border-radius: 5px;} 
					.class-b{ float:right;width:40%;padding:5px;margin-top: 10px;} 
				   </style>
				    <input id="validateCode" name="validateCode" type="text" class="class-a" placeholder="验证码" onclick="JavaScript:this.value=''"/>
<%-- 			    	<img alt="验证码" class="class-b" id="safecode" src="<%=request.getContextPath()%>/validateCode.jpg" onclick="javascript:reloadVerifyCode();"/> --%>
					<img alt="验证码" class="class-b" id="safecode" src="" onclick="javascript:reloadVerifyCode();"/>
				  
				    <div class="tipmgs" id="tipmgs" style="display: none;">
								<div class="tipcont"
									style="line-height: 25px;width: 420px;margin:4px;padding:0px;float: left; ">
									<p style="font-size:12px; color:red;margin:0 0 0 0px;padding:-15px 0 0 ;"></p>
									<span></span>
								</div>
							</div>
				    
					<input type="button" value="登录" class="btn btn-success btn-block" onclick="login()" id="button"/>
					<p>${ipcheck}</p>
                </form>
            </div><!-- col-sm-5 -->
            
        </div><!-- row -->
        
        <div class="signup-footer" style="background-color:#3382DF; width:846px;">
            <div class="pull-left">
              <br>
                &copy; 2014. All Rights Reserved. 短融网
            </div>
            <div class="pull-right">
              <br>
                Created By: <a href="http://www.duanrong.com/" target="_blank">短融网-技术部</a>
            </div>
        </div>
        
    </div><!-- signin -->
  
</section>


<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/jquery-migrate-1.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/modernizr.min.js"></script>
<script src="js/jquery.sparkline.min.js"></script>
<script src="js/jquery.cookies.js"></script>

<script src="js/toggles.min.js"></script>
<script src="js/retina.min.js"></script>

<script src="js/custom.js"></script>
<script>
    jQuery(document).ready(function(){
        $(".tipmgs").css("display", "none");

		$(".tipmgs span").click(function() {
			$(".tipmgs").css("display", "none");
		});
        // Please do not use the code below
        // This is for demo purposes only
        var c = jQuery.cookie('change-skin');
        if (c && c == 'greyjoy') {
            jQuery('.btn-success').addClass('btn-orange').removeClass('btn-success');
        } else if(c && c == 'dodgerblue') {
            jQuery('.btn-success').addClass('btn-primary').removeClass('btn-success');
        } else if (c && c == 'katniss') {
            jQuery('.btn-success').addClass('btn-primary').removeClass('btn-success');
        }
    });
</script>

</body>
</html>
