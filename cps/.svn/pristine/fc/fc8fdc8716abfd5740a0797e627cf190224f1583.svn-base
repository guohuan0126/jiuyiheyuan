<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>后台数据分析系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	
	<script>
   	$(function(){
  		$.ajax({
  			url:"bsy/getMenu",
  			type:"post",
  			data:{},
  			success:function(data){
  				var menuObject=JSON.parse(data);
  				for(var i=0; i<menuObject.length; i++){
  					var htmlStr="";
  					if(menuObject[i].parentId==0){
  						htmlStr=htmlStr+"<li><h4 class='M10'><span></span>"+htmlStr+menuObject[i].menuName+"</h4><div class='list-item none'>";
  						var parentMenId=menuObject[i].id;
  						for(var j=0; j<menuObject.length; j++){
  							if(menuObject[j].parentId==parentMenId){
  								htmlStr=htmlStr+"<a href='"+menuObject[j].url+"'>"+menuObject[j].menuName+"</a>"
  							}
  						}
  						htmlStr=htmlStr+"</div></li>"
  					}
  					if(htmlStr!=""){
  	  					$("#nav_dot").append(htmlStr);
  	  				}
  				}
  				
  				//navList(12);
  				
  			//菜单隐藏
  				$(".left_menu").find("a").each(function(){ 
  					console.log(this.href.indexOf(window.location.href));
  					if(this.href.indexOf(window.location.href) == 0){
  						$(".list-item").hide(); $(".nav_dot").removeClass("selected");  
  						$(this).parent().show().parent().addClass("selected"); 
  					}  
  				});

  			}
  		});
  		
  	}); 
  	 
  </script>
	
  </head>
  <body> 
  <div class="left_menu">
			<ul id="nav_dot">
				<li>
					<h4 class="M1">
						<span></span>首页
					</h4>
					<div class="list-item none">
						<a href='/showData'>首页</a>  
					</div>
				</li>

           		  <li>
          			<h4 class="M10"><span></span>比搜益</h4>
			          <div class="list-item none">
			            <a href='/bsy/getBsyWaitPushLoanList'>推送管理</a>
			            <a href='/bsy/getPushLoanHistory'>推送项目记录</a>
			            <a href='/bsy/bsyPushInvest'>推送交易记录</a>
			            <a href='/bsy/bsyPushInterest'>推送起息状态记录</a>
			            <a href='/bsy/bsyPushRepayMent'>推送回款记录</a>
			            <a href='/bsy/investByBsy'>投资记录</a>
			            <a href='/bsy/bsyUserInfo'>比收益用户</a>
			            
			          </div>

        		</li>   
 
        	
	           <!--   <li>
          			<h4 class="M10"><span></span>八金社</h4>
			          <div class="list-item none">
			            <a href='/bjs/bjsUserInfo'>八金社用户</a>			            
			            <a href='/bjs/investByBjs'>八金社交易记录</a>			          
			          </div>
        		</li> -->
        		
        		   <li>
          			<h4 class="M10"><span></span>投之家</h4>
			          <div class="list-item none">
			            <a href='/touzhijia/UserInfo'>投之家用户</a>			            
			            <a href='/touzhijia/investInfo'>投之家交易记录</a>			          
			          </div>
        		</li> 
        		<li>
          			<h4 class="M10"><span></span>利信众客</h4>
			          <div class="list-item none">
			            <a href='/lixinzk/getUserInfo'>利信众客用户</a>			            
			            <a href='/lixinzk/investInfo'>利信众客交易记录</a>
			            <a href='/lixinzk/investInfoForThird'>交易记录</a>			          
			          </div>
        		</li>
        		
        		<li>
          			<h4 class="M10"><span></span>网贷天眼</h4>
			          <div class="list-item none">
			            <a href='/netLoanEye/investRecords'>天眼投资记录</a>			            
			            <a href='/netLoanEye/waitPushLoanList'/>天眼推送管理</a>		          
			          </div>
        		</li>
        		
        		<li>
          			<h4 class="M10"><span></span>爱有钱</h4>
			          <div class="list-item none">
			            <a href='/getPlatformWaitPushLoanList?type=aiyouqian'>爱有钱推送项目</a>			            
			            <a href='/aiyouqian/UserInfo'>爱有钱用户信息</a>
			            <a href='/aiyouqian/investInfo'>爱有钱交易记录</a>
			            <a href='/aiyouqian/getPushLoanHistory'>爱有钱推标记录</a>
			            <a href='/aiyouqian/pushInvestHistory'>爱有钱推投资记录</a>		          
			          </div>
        		</li>

				<li>
          			<h4 class="M10"><span></span>风车理财</h4>
			          <div class="list-item none">
			            <a href='/fengchelicaiManager/UserInfo'>风车用户信息</a>
			            <a href='/fengchelicaiManager/investInfo'>风车交易记录</a>
			            <a href='/fengchelicaiManager/pushInvestHistory'>风车推投资记录</a>		          
			          </div>
        		</li>
        		
        		<li>
          			<h4 class="M10"><span></span>希财网</h4>
			          <div class="list-item none">
			            <a href='/getPlatformWaitPushLoanList?type=xicaiwang'>希财网推送</a>
			            <a href='/xicaiwangManager/UserInfo'>希财用户信息</a>
			            <a href='/xicaiwangManager/investInfo'>希财交易记录</a>
			            <a href='/xicaiwangManager/pushInvestHistory'>希财推投资记录</a>
			            <a href='/xicaiwangManager/getPushLoanHistory'>希财推标的记录</a>			          
			          </div>
        		</li>

			</ul>
			
		</div>
		
		<!-- <script>
		navList(12);
	</script> -->
  </body>
  
  
</html>
