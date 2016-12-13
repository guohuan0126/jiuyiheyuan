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
<title>用户借款记录</title>

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
					<i class="fa fa-table"></i>当前位置：用户借款记录
				</h2>
			</div>

			<div class="contentpanel">

				<div class="panel panel-default">					
					<!-- <form action="/agricultural/queryBankCardInfo" id="from1" method="post" >
					<div class="form-group" style="padding-top:20px;">
								&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;">借款人信息：</span> 
									 <input type="text" id="name" name="name"  class="form-control" style="width:212px;display:inline-block;" placeholder="借款人编号/手机号"></input>
									 <button type="button" id="add" class="btn btn-primary" onclick="toAddUser()" style="display:inline-block;float: right;margin-right: 20px;">添加银行卡信息</button>
									 <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
							</div>
						</form> -->
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
									<th>
										借款人姓名</br>
										借款人编号
									</th>
									<th>身份证号</th>
									<th>手机号</th>
									<th>银行卡号</th>
									<th>借款金额</th>
									<th>借款期限(月)</th>
									<th>利率</th>
									<th>还款方式</th>
									<th>创建时间</th>
									<th>创建人员</th>
									<th>复核时间</br>
										复核人员
									</th>
									<th>状态</th>
								</tr>
							</thead>
								
								<c:forEach var="ruralfinanceData" items="${ruralfinance_data}">
								<tr>
									<td>${ruralfinanceData.loaner_Id}</br>
										${ruralfinanceData.loanerName}
									</td>
									<td>
									${base:decrypt(ruralfinanceData.idCard)}	
									</td>
									<td>
									${base:decrypt(ruralfinanceData.mobileNumber)}
									</td>
									<td>
									${base:decrypt(ruralfinanceData.bankCard)}
									</td>
									<td>${ruralfinanceData.money}</td>
									<td>${ruralfinanceData.loan_term}</td>
									<td>${ruralfinanceData.rate}</td>
									<td>${ruralfinanceData.repay_type}</td>
									<td>
									<fmt:formatDate value="${ruralfinanceData.create_time}" type="both"/>
									</td>
									<td>${ruralfinanceData.create_user}</td>
									<td><fmt:formatDate value="${ruralfinanceData.update_time}" type="both"/></br>
										${ruralfinanceData.update_userid}</br>
									</td>
									<td>${ruralfinanceData.status}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<script type="text/javascript">
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


