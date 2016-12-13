<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="${ctx}/images/favicon.png"
	type="image/png">
<title>借款人信息管理</title>

</head>
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

</style>
<!-- <script>
function selectAll(){
 var checklist = document.getElementsByName ("selected");
   if(document.getElementById("controlAll").checked)
   {
   for(var i=0;i<checklist.length;i++)
   {
      checklist[i].checked = 1;
   } 
 }else{
  for(var j=0;j<checklist.length;j++)
  {
     checklist[j].checked = 0;
  }
 }
}
</script> -->
<script type="text/javascript">
	
	
	function getReason(rule){
		$('#rule').dialogBox({
			width: 500,
			height: 300,
			title: '审核信息',
			hasClose: true,
			content: rule
		});
	}
	
	
		function getReason1(rule){
			$('#rule').dialogBox({
				width: 500,
				height: 300,
				title: '备注信息',
				hasClose: true,
				content: rule
			});
		}
		
	

</script>
<body>
	<!-- Preloader -->
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
		<%@include file="../base/leftMenu.jsp"%>
		<script src="/js/jquery.dialogBox.js"></script>
	<link rel="stylesheet" href="/css/jquery.dialogbox.css" />
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：借款人信息管理
				</h2>
			</div>

			<div class="contentpanel">

				<div class="panel panel-default">					
					<form action="/agricultural/loanerinfo" id="from1" method="post" >
					<div class="form-group" style="padding-top:20px;">
								&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;">借款人信息：</span> 
									 <input type="text" id="name" name="name"  class="form-control" value="${name}" style="width:212px;display:inline-block;" placeholder="姓名/手机号/身份证号"></input>
									 <button type="button" id="add" class="btn btn-primary" onclick="toAddUser()" style="display:inline-block;float: right;margin-right: 20px;">添加借款人信息</button>
					                 	<span style="font-size: 20px">状态：</span>
					                 	<select id="select" name="status">
					                 		<option value="">全部</option>
					                 		<option value="uncheck">未复核</option>
					                 		<option value="checked">已复核</option>
					                 		<option value="reject">已驳回</option>
					                 	</select>
									 <input type="submit" id="quety" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
							</div>
						</form>
			<script type="text/javascript">
			$("#select").val('${status}');
			function toAddUser(){
					window.location.href="/agricultural/toAddLoanerInfo";
				}
			</script>
					<div class="panel-body">
					<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>编号</br>
										用户姓名</br>
										性别</br>
										年龄
									</th>
									<th>身份证号 </br>
										手机号
									</th>
									<th>婚姻状况</th>
									<th>省份</br>
										市</br>
										区、县、镇</th>
									<th>详细地址</th>
									<th>年收入</th>
									<th>创建时间</th>
									<th>创建人员</th>
									<th>复核时间</br>
										复核状态</br>
										复核人员
									</th>
									<th>客户经理</th>
									<th>备注信息</th>
									<th>操作</th>
									<th>审核理由</th>
									<th>借款记录</th>
								</tr>
							</thead>
								<c:forEach var="loanerinfo" items="${pageInfo.results}">
								<tr>
									<td>${loanerinfo.id}</br>
										${loanerinfo.userName}</br>
										<c:if test="${loanerinfo.sex==0}">男</c:if>
										<c:if test="${loanerinfo.sex==1}">女</c:if></br>
										${loanerinfo.age}
									</td>
									<td>
									${base:decrypt(loanerinfo.idCard)}</br>
										${base:decrypt(loanerinfo.mobileNumber)}
									</td>	
									<td>
										<c:if test="${loanerinfo.maritalStatus==0}">未婚</c:if>
										<c:if test="${loanerinfo.maritalStatus==1}">已婚</c:if>
										<c:if test="${loanerinfo.maritalStatus==2}">离异</c:if>
										<c:if test="${loanerinfo.maritalStatus==3}">丧偶</c:if>
									</td>	
									<td>${loanerinfo.province}</br>${loanerinfo.city}</br>${loanerinfo.area}</td>	
									<td>${loanerinfo.address}</td>	
									<td>
										${loanerinfo.annualIncome}
									</td>	
									<td>
									<fmt:formatDate value="${loanerinfo.createTime}" type="both"/>
									</td>	
									<td>${loanerinfo.userId}</td>	
									<td>
										<fmt:formatDate value="${loanerinfo.updateTime}" type="both"/></br>
										<c:if test="${loanerinfo.status=='uncheck'}">未复核</c:if>
										<c:if test="${loanerinfo.status=='checked'}">已复核</c:if>
										<c:if test="${loanerinfo.status=='reject'}">已驳回</c:if></br>
										${loanerinfo.updateUserid}
									</td>	
									<td>${loanerinfo.offlineReferrer}</td>
									<td><c:if test="${!empty loanerinfo.remark}"> 
									<a href="javascript:getReason1('${loanerinfo.remark}');" >备注</a></c:if> </td>
									<td>
										<c:if test="${loanerinfo.status!='checked'}">
										<a href="javascript:sub('${loanerinfo.id}')">删除</a></br>
										<c:if test="${loanerinfo.status!='reject'}">
										<%-- <a href="/agricultural/toCheckAgain/?id=${loanerinfo.id}">复核</a></br> --%>
										</c:if>
										</c:if>
										<a href="/agricultural/toUpdateLoanerinfo/?id=${loanerinfo.id}">修改</a></br>
									</td> 
									<td><c:if test="${!empty loanerinfo.reason}"> 
									<a href="javascript:getReason('${loanerinfo.reason}');" >详情</a></c:if> </td>
									<td><a href="/agricultural/ruralfinanceDataByLoanerId?loanerId=${loanerinfo.id}">借款记录</a> </td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<script type="text/javascript">
				
					//删除
					function sub(uuid){
						if(confirm('确认操作借款人信息?')){
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/agricultural/subLoanerinfo",						
								data: {"uuid":uuid},
								success:function(data) {
									if(data=='success'){
										alert("删除成功");
										window.location.href="/agricultural/loanerinfo";
									}else{
										alert("删除失败");
									}
								}
							});	
							
						}
					}
					
					</script>
					
					
					<%@ include file="../base/page.jsp"%>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>


</body>
</html>


