<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/jquery.e-calendar.css" />
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/jquery.e-calendar.js"></script>
<script type="text/javascript" src="/js/menu.js"></script>
<script src="/js/index.js"></script>
<head>
<base href="<%=basePath%>">

<title>My JSP 'houtai.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<jsp:include page="base/header.jsp" />
	<div id="content">
		<jsp:include page="base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>现金红包</li>
				</ul>
			</div>
			<div class="main">
				<div class="bound">
					<div class="box">
						<div class="input-list">
							用户编号<input type="text">
						</div>
						<div class="input-list">
							手机号码<input type="text">
						</div>
						<div class="input-list" style="position:relative;">
							开始时间<input type="text" class="time-begin">
						</div>
						<div class="input-list">
							<input type="button" value="清除" class="clear">
						</div>
						<div class="input-list">
							手机号码<input type="text">
						</div>
						<div class="input-list">
							<span class="user">用户名称</span> <span class="btn-select"
								id="btn_select"> <span class="cur-select">请选择</span> <select>
									<option>选项一</option>
									<option>选项二</option>
									<option>选项三</option>
									<option>选项四</option>
									<option>选项五</option>
							</select>
							</span>
						</div>
						<div class="input-list" style="position:relative;">
							结束时间<input type="text" class="time-over" >
							<div id="calendar" style="display:none;"></div>
						</div>
						<div class="input-list">
							<input type="button" value="搜索" class="but-search">
						</div>
					</div>
					<div class="box">
						<div class="box-title">
							<span>用户添加到组</span> <span class="btn-select" id="btn_select">
								<span class="cur-select">请选择</span> <select>
									<option>选项一</option>
									<option>选项二</option>
									<option>选项三</option>
									<option>选项四</option>
									<option>选项五</option>
							</select>
							</span>
							<script>
								var $$ = function(id) {
									return document.getElementById(id);
								}
								window.onload = function() {
									var btnSelect = $$("btn_select");
									var curSelect = btnSelect
											.getElementsByTagName("span")[0];
									var oSelect = btnSelect
											.getElementsByTagName("select")[0];
									var aOption = btnSelect
											.getElementsByTagName("option");
									oSelect.onchange = function() {
										var text = oSelect.options[oSelect.selectedIndex].text;
										curSelect.innerHTML = text;
									}
								}
							</script>
							<input type="button" value="标记已读" class="read"> <input
								type="button" value="添加客户" class="add">
						</div>
						<div class="box-table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="table-tit">
									<td><input name="" type="checkbox" value="">全选</td>
									<td>用户编号</td>
									<td>用户姓名</td>
									<td>手机号码</td>
									<td>项目编号</td>
									<td>项目名称</td>
									<td>项目类型</td>
									<td>放款时间</td>
									<td>放款金额</td>
									<td>还款时间</td>
									<td>还款金额</td>
								</tr>
								<tr>
									<td><input name="" type="checkbox" value=""></td>
									<td>hshksa1253</td>
									<td>张力</td>
									<td>182829292920</td>
									<td>61525112351236152</td>
									<td>车押宝C20151125</td>
									<td>车贷</td>
									<td>2015-12-3 11:32:52</td>
									<td>100</td>
									<td>2015-12-3 11:32:52</td>
									<td>101</td>
								</tr>
								<tr>
									<td><input name="" type="checkbox" value=""></td>
									<td>hshksa1253</td>
									<td>张力</td>
									<td>182829292920</td>
									<td>61525112351236152</td>
									<td>车押宝C20151125</td>
									<td>车贷</td>
									<td>2015-12-3 11:32:52</td>
									<td>100</td>
									<td>2015-12-3 11:32:52</td>
									<td>101</td>
								</tr>
								<tr>
									<td><input name="" type="checkbox" value=""></td>
									<td>hshksa1253</td>
									<td>张力</td>
									<td>182829292920</td>
									<td>61525112351236152</td>
									<td>车押宝C20151125</td>
									<td>车贷</td>
									<td>2015-12-3 11:32:52</td>
									<td>100</td>
									<td>2015-12-3 11:32:52</td>
									<td>101</td>
								</tr>
								<tr>
									<td><input name="" type="checkbox" value=""></td>
									<td>hshksa1253</td>
									<td>张力</td>
									<td>182829292920</td>
									<td>61525112351236152</td>
									<td>车押宝C20151125</td>
									<td>车贷</td>
									<td>2015-12-3 11:32:52</td>
									<td>100</td>
									<td>2015-12-3 11:32:52</td>
									<td>101</td>
								</tr>
								<tr>
									<td><input name="" type="checkbox" value=""></td>
									<td>hshksa1253</td>
									<td>张力</td>
									<td>182829292920</td>
									<td>61525112351236152</td>
									<td>车押宝C20151125</td>
									<td>车贷</td>
									<td>2015-12-3 11:32:52</td>
									<td>100</td>
									<td>2015-12-3 11:32:52</td>
									<td>101</td>
								</tr>
								<tr>
									<td><input name="" type="checkbox" value=""></td>
									<td>hshksa1253</td>
									<td>张力</td>
									<td>182829292920</td>
									<td>61525112351236152</td>
									<td>车押宝C20151125</td>
									<td>车贷</td>
									<td>2015-12-3 11:32:52</td>
									<td>100</td>
									<td>2015-12-3 11:32:52</td>
									<td>101</td>
								</tr>
								<tr>
									<td><input name="" type="checkbox" value=""></td>
									<td>hshksa1253</td>
									<td>张力</td>
									<td>182829292920</td>
									<td>61525112351236152</td>
									<td>车押宝C20151125</td>
									<td>车贷</td>
									<td>2015-12-3 11:32:52</td>
									<td>100</td>
									<td>2015-12-3 11:32:52</td>
									<td>101</td>
								</tr>
								<tr>
									<td><input name="" type="checkbox" value=""></td>
									<td>hshksa1253</td>
									<td>张力</td>
									<td>182829292920</td>
									<td>61525112351236152</td>
									<td>车押宝C20151125</td>
									<td>车贷</td>
									<td>2015-12-3 11:32:52</td>
									<td>100</td>
									<td>2015-12-3 11:32:52</td>
									<td>101</td>
								</tr>
								<tr></tr>
							</table>
							<div class="table-pages">
								<span class="pages-left">共有40条，每页显示</span><select
									class="pages-sec">
									<option value="1">20条</option>
									<option value="2">30</option>
									<option value="3">50</option>
									<option value="4">100条</option>
								</select> <span class="pages-all"> <a><<</a> <a><</a> <a
									class="cur">1</a> <a>2</a> <a>3</a> <a>4</a> <a>5</a> <a>...</a>
									<a>7</a> <a>8</a> <a>></a> <a>>></a>
								</span> <span class="pages-right"> <input type="text"
									class="input-txt"><input type="button" value="GO"
									class="input-but">
								</span>
							</div>


						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		navList(12);
	</script>

</body>
</html>
