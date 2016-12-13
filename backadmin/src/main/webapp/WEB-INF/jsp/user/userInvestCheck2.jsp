<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<title>发送用户投资确认书</title>

</head>
<script>
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
</script>
<body>
	<!-- Preloader -->
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：发送用户投资确认书
				</h2>
			</div>

			<div class="contentpanel">

				<div class="panel panel-default">					
					<form action="/user/userInvestCheckByTime" id="from1" method="post" >
					<div class="form-group" style="padding-top:20px;">
								&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;">用户投资时间 ： </span> <input type="text"
									class="form-control" placeholder="开始时间"
									name="start" value="${start}" id="start" style="width:160px;display:inline-block;"/> <input
									type="text" class="form-control" placeholder="结束时间"
									 name="end" id="end" value="${end}" style="width:160px;display:inline-block;">
									 <input type="hidden" value="${value}">
									 <button type="button" id="add" class="btn btn-primary" onclick="toAddUser()" style="display:inline-block;display:inline-block;float: right;margin-right: 20px;">添加用户信息</button>
								 <input type="submit" id="add" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
							</div>
						</form>
			<script type="text/javascript">
				function toAddUser(){
					window.location.href="/user/toAddUserInfo";
				}
			</script>


					<div class="panel-body">

						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>用户编号</th>
									<th>用户名字</th>
									<th>手机号</th>
									<th>用户地址</th>
									<th>上次导出时间</th>
									<th>操作</th>
								</tr>
							</thead>
								<c:forEach var="user" items="${pageInfo.results}">
								<tr>
									<td>${user.id}
									<input type="hidden" value="${user.name}">
									</td>		
									<td>${user.name}</td>
									<td>${user.mobileNumber}</td>
									<td>${user.userAddr}</td>
									<td><fmt:formatDate value="${user.exportTime}" type="both"/></td>		
									<td>
										<button id="sub1"  onclick="exportNow('${user.id}')" style=ddisplay: none;width: 40px;height: 22px;>下载</button>
										<button id="sub"  onclick="sub('${user.uuid}')" style=width: 40px;height: 22px;>删除</button>
										
									</td>
								</tr>
							</c:forEach>	
						</table>
						<input type="hidden" id="userpid">
   						  <input type="hidden" id="qq">
					</div>
					<script type="text/javascript">
						var d = new Date();
						d.setDate(d.getDate() - 1);
						jQuery('#start').datepicker({
							dateFormat : 'yy-mm-dd',
						});
					</script>
					<script type="text/javascript">
					var d = new Date();
					d.setDate(d.getDate() - 1);
					jQuery('#end').datepicker({
						dateFormat : 'yy-mm-dd',
					});
					</script>
					<script type="text/javascript">
					//删除
					function sub(uuid){
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/user/subUserInfo",						
								data: {"uuid":uuid},
								success:function(data) {
									if(data=='success'){
										alert("删除成功");
										window.location.href="/user/toUserInvestCheck";
									}else{
										alert("删除失败");
									}
								}
							});	
							
						}
					
					</script>
					<script type="text/javascript">
					//导出当前
					function exportNow(userid){
						var userId=userid; 
						var start=$("#start").val();
						var end=$("#end").val();
									window.location.href="/user/exportNowUserInvest?start="+start+"&end="+end+"&userId="+userId;
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


