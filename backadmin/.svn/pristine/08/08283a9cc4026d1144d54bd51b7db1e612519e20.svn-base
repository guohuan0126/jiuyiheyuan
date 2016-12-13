<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="${ctx}/images/favicon.png"
	type="image/png">
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
<style type="text/css">
.ui-datepicker-title {
	text-transform: uppercase;
	font-family: 'LatoBold';
	color: #1CAF9A;
}

.ui-datepicker {
	background: #1D2939;
	margin-top: 1px;
	border-radius: 2px;
	width: 280px;
}

.ui-datepicker-next {
	background: url(../../images/calendar-arrow.png) no-repeat 10px 5px;
}

.ui-datepicker-prev {
	background: url(../../images/calendar-arrow.png) no-repeat 8px -80px;
}

.ui-datepicker table {
	color: #ffffff;
}
</style>
<title>数据字典</title>
</head>
<body>
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section> <%@include file="../base/leftMenu.jsp"%>
	<div class="mainpanel">
		<%@include file="../base/header.jsp"%>
		<div class="pageheader">
			<h2>
				<i class="fa fa-table"></i>当前位置：数据字典维护
			</h2>
			<div class="breadcrumb-wrapper">
				<ol class="breadcrumb" style="padding: 16px 16px; font-size: 16px;">
					<li><a href=""></a></li>
				</ol>
			</div>
		</div>
		<div class="contentpanel ">
			<div class="panel panel-default">
				<div class="panel-heading">
					<form class="form-inline" action="${ctx }/dictionary/dicList"	method="post" id="f1">
						<label class="control-label">类别编码：</label>
						<div class="form-group" style="width: 190px;">
							<input type="text" name="typeCode" class="form-control" value="${dic.typeCode }">
						</div>
						<label class="control-label">类别名称：</label>
						<div class="form-group" style="width: 190px;">
							<input type="text" name="typeName" class="form-control" value="${dic.typeName }">
						</div>
						<button type="button" class="btn btn-primary"	onclick="javascript:submit()">查询</button>
						<button type="button" class="btn btn-primary"	onclick="javascript:addDic()">添加</button>	
					</form>
					
				</div>
				<div class="panel-body">
					<table class="table table-primary table-striped table-hover">
						<thead>
							<tr class="font">
								<th>序号</th>
								<th>类别名称</th>
								<th>类别编码</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${pageInfo.results}" varStatus="status">
								<tr class="font">
									<td>${status.index+1 }</td>
									<td>${item.typeName }</td>
									<td>${item.typeCode }</td>
									<td>
										<a href="javascript:void(0);" onclick="editByTypeCode('${item.typeCode}');">编辑</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%@ include file="../base/page2.jsp"%>
				</div>
			</div>
		</div>
	</div>
	</section>
</body>
<script>
	//跳转到添加
	function addDic(){
		window.location.href="${ctx}/dictionary/toEditByTypeCode"
	}
	
	function editByTypeCode(typeCode){
		window.location.href="${ctx}/dictionary/toEditByTypeCode?typeCode="+typeCode;
	}
</script>
</html>
