<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<title>确认转出申请</title>

</head>

<body>
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：确认转出申请
				</h2>				
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>转出用户</th>
									<th>转出用户真实姓名</th>
									<th>转出用户手机号</th>
									<th>转出总金额</th>
									<th>转出时间</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="demo">
									<tr>
										<td>${demo.userId}</td>
										<td>${demo.user.realname}</td>
										<td>${demo.user.mobileNumber}</td>
										<td>${demo.money}</td>
										<td><fmt:formatDate value="${demo.sendedTime }"
												pattern="yyyy-MM-dd HH:mm" /></td>
										
										<td>${demo.status}</td>
										<td>
										<a href="/demand/userDetail?guOutId=${demo.id }">用户详情</a>|
										<c:if test="${demo.status eq 'freeze'}">
										<a href="javascript:authconfirm('${demo.id}','CONFIRM');">确认转出</a>
	                           			| <a href="javascript:authconfirm('${demo.id}','CANCEL');">取消转出</a>	
										</c:if>
										<c:if test="${demo.status eq 'fail' || demo.status eq 'sended'}">
										<a href="/demand/startransferOut?id=${demo.id }">再次发起转账</a>
										</c:if> 
										<c:if test="${demo.status eq 'sended'}">
										<a href="javascript:cancel('${demo.id }')">撤销</a>
										</c:if>
										</td>		
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<%@ include file="../base/page.jsp"%>

					</div>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>

	<script>
	$(function() {
		var userMessage = '${userMessage}';
		if (userMessage != '') {
			var title = '${title}';
			var content = '${content}';
			alert(content);
		}
	});

	function cancel(id){
		if(confirm('确认撤销吗')){
			$.ajax({
				type : 'POST',
				url:"/demand/cancel",		
				data:{
					'id':id
				},
				beforeSend:function(){
								xval=getBusyOverlay('viewport',
								{color:'blue', opacity:0.5, text:'进行中...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
								{color:'blue', size:25, type:'o'});	
				},
				error:function(){
					window.clearInterval(xval.ntime); 
					xval.remove();
				},		
				success : function(msg) {				
						window.clearInterval(xval.ntime); 
						xval.remove();
						if(msg == 'ok'){							
							alert("操作成功!");	
							window.location = "/demand/guOUtList";
						}else if(msg == 'fail'){
							alert("操作失败!");					
						}else{
							alert(msg);
						}
				}
			});
		}
	}
	function authconfirm(id,flag){
		var status = true;var str='';
		if(status == true){
			if(flag=='CONFIRM'){
				str='确认要转出吗？';
			}else{
				str='确认要取消转出吗？';
			}
			if(confirm(str)){
				status == false;
				$.ajax({
					type : 'POST',
					url:"/demand/demandOutConfirm",		
					data:{
						'tranid':id,
						'flag':flag
					},
					beforeSend:function(){
									xval=getBusyOverlay('viewport',
									{color:'blue', opacity:0.5, text:'进行中...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
									{color:'blue', size:25, type:'o'});	
					},
					error:function(){
						window.clearInterval(xval.ntime); 
						xval.remove();
						status == true;
					},		
					success : function(msg) {				
							window.clearInterval(xval.ntime); 
							xval.remove();
							if(msg == 'ok'){							
								alert("操作成功!");	
								window.location = "/demand/guOUtList";
							}else if(msg == 'fail'){
								alert("操作失败!");					
							}else{
								alert(msg);
							}
						status == true;
					}
				});
			}
		}
	}
	</script>
</body>
</html>
