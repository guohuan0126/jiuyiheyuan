<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="/css/jquery.ui.slider.css" />
<link rel="stylesheet" href="/css/jquery-ui-timepicker-addon.css" />
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script type="text/javascript" src="/js/jquery.ui.slider.js"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="/js/menu.js"></script>

<head>
<base href="<%=basePath%>">

<title>利信众客的活期投资记录</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!--  
	 -->
<style type="text/css">

	.box2 {
	/*非IE的主流浏览器识别的垂直居中的方法*/
	display: table-cell;
	vertical-align:middle;
	/*设置水平居中*/
	text-align:center;
	/* 针对IE的Hack */
	*display: block;
	*font-size: 175px;/*约为高度的0.873，200*0.873 约为175*/
	*font-family:Arial;/*防止非utf-8引起的hack失效问题，如gbk编码*/
	width:200px;
	height:200px;
	border: 1px solid #eee;
}
.right-nav{height:auto;}
.input-list{padding-top:20px; padding-left:30px;}
.input-list input,.input-list select{border:1px solid #999;border-radius:3px;height:38px;padding-left:20px;}

</style>
</head>

<body>
	<div id="header">
		<div class="logo">cps营销系统</div>
		<div class="navigation">
			<ul>
			 	<li>欢迎你！</li>
			</ul>
		</div>
	</div>
	
	<div class="left_menu">
			<ul id="nav_dot">
        		<li>
          			<h4 class="M10"><span></span>利信众客</h4>
			          <div class="list-item none">		
			          	<a href='/lixinzk/index/getUserInfo?key=m040bd9172333e5aefe288023a0f222'>用户信息</a>	            
			            <a href='/lixinzk/index?key=m040bd9172333e5aefe288023a0f222'>定期交易记录</a>
			            <a href='/lixinzk/index/demandBill?key=m040bd9172333e5aefe288023a0f222'>活期交易记录</a>			          
			          </div>
        		</li>

			</ul>
			
		</div>
	
	<div id="content">
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>当前位置：利信众客用户活期投资的记录</li>
				</ul>
				<br/>
				<form action="/lixinzk/index/demandBill">
				<input id="key" type="hidden" name="key" value="m040bd9172333e5aefe288023a0f222">
				<table class="input-list" border="0" width="80%" cellspacing="10" cellpadding="5">
					<tr>
						<td>
							<input id="mobileNumber" value="${mobileNumber}" name="mobileNumber" type="text" class="datepicker" placeholder="用户手机号">
						</td>
						<td>
							<select id="type" name="type">
								<option value="">--全部交易类型--</option>
	                  			<option value="in">投资</option>
	                  			<option value="out">赎回</option>
							</select>	
						</td>
						<td>
							<input id="investTimeBegin" value="${investTimeBegin}"  name="investTimeBegin" type="text" class="datepicker" placeholder="交易开始时间">
						</td>
						<td>
							<input id="investTimeEnd"   value="${investTimeEnd}"  name="investTimeEnd" type="text" class="datepicker"   placeholder="交易结束时间">
						</td>
						
						<td >
							<input type="submit" class="buttonStyle"  style="display:block;width:120px;height:40px;background:#3499df" value="查询"/>
							
						</td>
						<td>
							<button type="button" class="buttonStyle" style="display:block;width:120px;height:40px;background:#3499df" onclick="exportInvestBill()">下载</button>
						</td>
					</tr>
				</table>
				</form>
			
			<script type="text/javascript">
			$(".datepicker").datepicker({
                        		dateFormat:'yy-mm-dd'
                        	});
			
			
			//导出已备注的用户信息
			function exportInvestBill(){
				var url="/lixinzk/index/exportDemandBill";
				var mobileNumber=$("#mobileNumber").val();
				var type=$("#type").val();
				var investTimeBegin=$("#investTimeBegin").val();
				var investTimeEnd=$("#investTimeEnd").val();
				var str = "?";
				
				if(mobileNumber!="" && mobileNumber!=undefined){
					str+="&mobileNumber="+mobileNumber;
				}
				if(type!="" && type!=undefined){
					str+="&type="+type;
				}
				if(investTimeBegin!=""){
					str+="&investTimeBegin="+investTimeBegin;
				}
				if(investTimeEnd!=""){
					str+="&investTimeEnd="+investTimeEnd;
				}
				window.location.href=url+=str;
			}
			
			
			var v = '${type}';

			var obj = document
					.getElementsByTagName("option");
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].value == v) {
					obj[i].selected = true; //相等则选中
				}
			}
			
								
			</script>
							
			</div>
					<div class="box">
					<div id="rule"></div>
						<div class="box-table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="table-tit">
									<td>用户名称</td>
									<td>手机号</td>
									<!-- <td>交易状态</td> -->
									<td>交易时间</td>
									<td>交易金额</td>
									<td>交易类型</td>
								</tr>
								<c:forEach items="${pageInfo.results}" var="investRecords" varStatus="Num">
									<tr>
										<td>${investRecords.realname}<input type="hidden" value="${investRecords.id}" name="demandTreasureBillId"></td>
										<td>${investRecords.mobileNumber}</td>
										<!-- <td>成功</td> -->
										<td>
											<fmt:formatDate value="${investRecords.createTime}" type="both"/>
										</td>
										<td>${investRecords.money}</td>
										<td>
											<c:if test="${investRecords.type=='in' }">投资</c:if>
											<c:if test="${investRecords.type=='out' }">赎回</c:if>
										</td>
									</tr>
								</c:forEach>
								<tr></tr>
							</table>							
							<%@ include file="../base/pageInfo.jsp"%>
						</div>
					</div>
				</div>
			</div>
	<script>
	navList(10);
		</script>
	

</body>
</html>
