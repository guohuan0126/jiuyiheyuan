<%@ page language="java" import="com.duanrong.newadmin.utility.AES" contentType="text/html; charset=UTF-8"
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
<title>银行卡管理页面</title>

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
					<i class="fa fa-table"></i>当前位置：银行卡管理页面
				</h2>
			</div>
			<div class="contentpanel">

				<div class="panel panel-default">					
					<form action="/agricultural/bankcard" id="from1" method="post" >
					<div class="form-group" style="padding-top:20px;">
								&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;">借款人信息：</span> 
									 <input type="text" id="name" name="name"  class="form-control" style="width:212px;display:inline-block;" value="${name}" placeholder="借款人编号/手机号"></input>
									 <button type="button" id="add" class="btn btn-primary" onclick="toAddUser()" style="display:inline-block;float: right;margin-right: 20px;">添加银行卡信息</button>
									 <span style="font-size: 20px">状态：</span>
					                 	<select id="select" name="status">
					                 		<option value="">全部</option>
					                 		<option value="uncheck">未鉴权</option>
					                 		<option value="checked">已鉴权</option>
					                 	</select>
									 <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
							</div>
						</form>
			<script type="text/javascript">
				function toAddUser(){
					window.location.href="/agricultural/toAddBankCardInfo";
				}
			</script>
					<div class="panel-body">
					<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>用户编号</br>
										用户姓名
									</th>
									<th>银行卡号 </th>
									<th>所属银行</th>
									<th>支付通道</th>
									<th>证件类型</th>
									<th>证件号</th>
									<th>支行名称</th>
									<th>预留手机号</th>
									<th>创建时间</th>
									<th>创建人</th>
									<th>复核状态</th>
									<th>备注</th>
									<th>操作</th>
								</tr>
							</thead>
								
								<c:forEach var="cardInfo" items="${pageInfo.results}">
								<tr>
									<td style="width:5%;">${cardInfo.loanerId}</br>
										${cardInfo.loanerName}
									</td>
									
									<td style="width:8%;">${base:decrypt(cardInfo.bankcard)}</td>
									<td style="width:8%;">${cardInfo.bank}</td>	
									<td style="width:9%;">
										<c:if test="${cardInfo.paymentChannel=='fuiou'}">富友</c:if>
										<c:if test="${cardInfo.paymentChannel=='Zhongjin'}">中金支付</c:if>
									</td>	
									<td style="width:7%;">
										<c:if test="${cardInfo.certtp=='idCard'}">身份证</c:if>
										<c:if test="${cardInfo.certtp=='postCard'}">护照</c:if>
									</td>	
									<td style="width:5%;">${base:decrypt(cardInfo.certno)}</td>
									<td style="width:5%;">${cardInfo.bankProvince}</br>${cardInfo.bankCity}</br>${cardInfo.branchname}</td>	
									<td style="width:10%;">${base:decrypt(cardInfo.bankMobile)}</td>
									<td style="width:10%;">
										<fmt:formatDate value="${cardInfo.createTime}" type="both"/></br>
									</td>	
									<td style="width:8%;">${cardInfo.userid}</td>
									<td style="width:8%;">
									<c:if test="${cardInfo.status=='uncheck'}">未鉴权</c:if>
										<c:if test="${cardInfo.status=='checked'}">已鉴权</c:if>
									</td>
									<td style="width:8%;"><c:if test="${!empty cardInfo.remark}"> 
									<a href="javascript:getReason1('${cardInfo.remark}');" >备注</a></c:if> </td>
									<td style="width:15%;">
										<c:if test="${cardInfo.status=='uncheck'}">
										<a href="javascript:sub('${cardInfo.id}')">删除</a>
										</br>
										<a href="/agricultural/toUpdateCardInfo/?id=${cardInfo.id}">修改</a></br>
										<%-- <a href="javascript:checkBankCard('${cardInfo.id}')">复核</a></br> --%>
										</c:if>
									</td> 
								</tr>
							</c:forEach>
						</table>
					</div>
					<script type="text/javascript">
					$("#select").val('${status}');
					//删除
					function sub(id){
						if(confirm('确认操作借款人信息?')){
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/agricultural/subCardInfo",						
								data: {"id":id},
								success:function(data) {
									if(data=='success'){
										alert("删除成功");
										window.location.href="/agricultural/bankcard";
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


