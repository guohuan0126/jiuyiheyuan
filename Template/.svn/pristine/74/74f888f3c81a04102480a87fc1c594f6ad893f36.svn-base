<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台数据分析系统</title>
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
<style type="text/css">
	.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }  
.ui-timepicker-div dl { text-align: left; }  
.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }  
.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }  
.ui-timepicker-div td { font-size: 90%; }  
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }  
.box-span-all span{padding-right:20px;}
</style>
</head>
<body>
	<script src="/js/hcharts/highcharts.js"></script>
	<script src="/js/hcharts/exporting.js"></script>
	
	<jsp:include page="base/header.jsp" />
	<div id="content">
		<jsp:include page="base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>来源数据查询</li>
				</ul>
			</div>
			<div class="main">
				<div class="bound">
					<div class="box">
						<form action="/userData/userSourceData" id="form1" method="post">
						<div class="block-box">
							<div class="input-list" style="position:relative;">
							来源：<input id="userSource" value="${userSource}" name="userSource" type="text"></div>
							<div class="input-list" style="position:relative;">
							开始时间<input id="sTime" type="text" name="start" value="${start}" class="time-begin datepicker"></div>                        
                            <div id="dateDiv" class="input-list small-list" style="position:relative;">
                           	 结束时间<input id="eTime" type="text" name="end" value="${end}" class="time-over datepicker"></div>
                            <div class="input-list small-list">
                            <input type="button" id="query"  onclick="showData()" value="查询" class="but-search small-but" >
                            </div>
                        	<script type="text/javascript">
                        	$(".datepicker").datepicker({
                        		dateFormat:'yy-mm-dd'
                        	});
							</script>
						</div>
						</form>
					</div>
					<div class="box">
						<div class="box-span-all">
						
							<span style="font-size: 20px ;font: bold;">注册用户数：<fmt:formatNumber  value="${registerCount}"/></span>
							<span style="font-size: 20px ;font: bold;">实名用户数：<fmt:formatNumber  value="${realNameCount}"/></span>
							<span style="font-size: 20px ;font: bold;">首投人数：<fmt:formatNumber  value="${fristInvestCount}"/></span>
							<span style="font-size: 20px ;font: bold;">首投金额： <fmt:formatNumber currencySymbol="￥" type="currency" value="${fristInvestMoney}"/></span>
							<span style="font-size: 20px ;font: bold;">复投人数：<fmt:formatNumber  value="${investUserCountAgain}"/></span>
							<span style="font-size: 20px ;font: bold;">复投金额：<fmt:formatNumber currencySymbol="￥" type="currency" value="${investUserMoneyAgain}"/></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function showData(){
		$("#form1").submit();
		}
		
	</script>
	<script>navList(12);</script>
</body>
</html>
