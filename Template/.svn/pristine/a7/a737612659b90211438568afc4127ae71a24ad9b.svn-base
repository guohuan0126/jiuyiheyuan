<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'left.jsp' starting page</title>
    
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
  			url:"/getMenu",
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
  				
  				navList(12);
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
				<!-- <li>
					<h4 class="M2">
						<span></span>用户数据
					</h4>
					<div class="list-item none">
						<a href='/UserData/ShowUserData' class="on">用户数据</a> 
					</div>
				</li>
				<li>
					<h4 class="M3">
						<span></span>资金数据
					</h4>
					<div class="list-item none">
						<a href='/CashData/toShowTCashData'>资金数据</a>
					</div>
				</li>
				<li>
					<h4 class="M4">
						<span></span>借款项目数据
					</h4>
					<div class="list-item none">
						<a href='/capitalData/show'>借款项目数据</a> 
					</div>
				</li>
				<li>
					<h4 class="M5">
						<span></span>活期宝资金数据
					</h4>
					<div class="list-item none">
						<a href='/currnetData/toShowCurrnetData'>活期宝资金数据</a> 
					</div>
				</li>
			 	<li>
					<h4 class="M6">
						<span></span>促销活动数据
					</h4>
					<div class="list-item none">
						<a href='/showRedPackage'>现金红包</a> <a href=''>加息券</a> <a href='/CouponByPerson'>个人优惠券查询</a> <a href='/activeData/toActiveData'>活动效果分析</a>
						<a href='/userData/toUserData'>用户信息管理</a>
					</div>
				</li> 
				<li>
					<h4 class="M6">
						<span></span>用户来源数据
					</h4>
					<div class="list-item none">
						<a href='/userData/toUserSourceData'>用户数据查询</a>
					</div>
				</li>  -->
				<!--<li>
          <h4  class="M7"><span></span>奖励管理</h4>
          <div class="list-item none">
            <a href=''>奖励管理1</a>
            <a href=''>奖励管理2</a>
            <a href=''>奖励管理3</a>
          </div>
        </li>
				<li>
          <h4   class="M8"><span></span>字典维护</h4>
          <div class="list-item none">
            <a href=''>字典维护1</a>
            <a href=''>字典维护2</a>
            <a href=''>字典维护3</a>
			<a href=''>字典维护4</a>
            <a href=''>字典维护5</a>
            <a href=''>字典维护6</a>
			<a href=''>字典维护7</a>
            <a href=''>字典维护8</a>
            <a href=''>字典维护9</a>
			<a href=''>字典维护4</a>
            <a href=''>字典维护5</a>
            <a href=''>字典维护6</a>
			<a href=''>字典维护7</a>
            <a href=''>字典维护8</a>
            <a href=''>字典维护9</a>
          </div>
        </li>
				<li>
          <h4  class="M9"><span></span>内容管理</h4>
          <div class="list-item none">
            <a href=''>内容管理1</a>
            <a href=''>内容管理2</a>
            <a href=''>内容管理3</a>
          </div>
        </li>
				<li>
          <h4   class="M10"><span></span>系统管理</h4>
          <div class="list-item none">
            <a href=''>系统管理1</a>
            <a href=''>系统管理2</a>
            <a href=''>系统管理3</a>
          </div>
        </li>-->
			</ul>
		</div>
    
  </body>
</html>
