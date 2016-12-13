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

<title>待处理的转入</title>

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
					<i class="fa fa-table"></i>当前位置：待处理的转入
				</h2>				
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
					当前待处理总数量：${demand.total }条   当前待处理的总金额：${demand.summoney }元
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>用户编号</th>
									<th>用户姓名</th>
									<base:active activeId="USER_LOOK">
									<th>用户手机号</th>
									</base:active>
									<th>转入金额</th>
									<th>转入申请时间</th>
									<th>转入途径</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="demo">
									<tr>
										<td>${demo.user.userId}</td>
										<td>${demo.user.realname}</td>
										<base:active activeId="USER_LOOK">
										<td>${demo.user.mobileNumber}</td>
										</base:active>
										<td>${demo.money}</td>
										<td><fmt:formatDate value="${demo.sendedTime }"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td>${demo.transferWay}</td>
										<td>
										<a href="javascript:authconfirm('${demo.id}','CONFIRM');">确认转账</a>
	                           			| <a href="javascript:authconfirm('${demo.id}','CANCEL');">取消转账</a>	
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
	function authconfirm(id,flag){
		var status = true;var str='';
		if(status == true){
			if(flag=='CONFIRM'){
				str='确认要转账吗？';
			}else{
				str='确认要取消转账吗？';
			}
			if(confirm(str)){
				status == false;
				$.ajax({
					type : 'POST',
					url:"/demand/demandConfirm",		
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
								window.location = "/demand/transferIn";
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
