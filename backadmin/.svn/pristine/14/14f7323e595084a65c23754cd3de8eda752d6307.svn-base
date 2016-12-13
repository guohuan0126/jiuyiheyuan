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
<link rel="shortcut icon" href="${ctx}/images/favicon.png"	type="image/png">
<title>调账</title>
</head>
<body>
	<!-- Preloader -->
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
					<i class="fa fa-table"></i>当前位置：调账
				</h2>
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" action="/account/userAccountInfo"
							method="post">
							<div class="form-group">
							<input type="text" name="userId" value="${userId }"
									class="form-control" id="userId" placeholder="用户编号/手机号/邮箱/身份证号">
							</div>							
							
							
							<span style="color:red;">从此seqNum开始,向后同步数据</span>
							<input type="text" name="seqNum"
									class="form-control" id="seqNum" placeholder="序号">			
									&nbsp;&nbsp;
							<button id="submit" type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp;
							<button type="button" class="btn btn-primary" onclick="transfer();">调账</button>			
						</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>
								<tr>
									<th>用户编号</th>
									<th>用户姓名</th>
									<th>账户余额</th>									
									<th>冻结金额</th>	
									
									<th>bill余额</th>	
									<th>bill冻结金额</th>						
									<th>最近操作时间</th>
								</tr>
							</thead>
							<tbody id="treet1">					
								
								<tr>
									<td>${accountInfo.userAccount.userId }</td>
									<td>${accountInfo.userAccount.realname }</td>
									<td><fmt:formatNumber value="${accountInfo.userAccount.balance}" type="currency"/></td>				
									<td><fmt:formatNumber value="${accountInfo.userAccount.freezeAmount }" type="currency"/></td>
									<td><fmt:formatNumber value="${accountInfo.balance}" type="currency"/></td>
									<td><fmt:formatNumber value="${accountInfo.frozen}" type="currency"/></td>
									<td><fmt:formatDate value="${accountInfo.userAccount.time}" type="both" /></td>
								</tr>	
							</tbody>
						</table>
					
						<table class="table table-primary table-striped table-hover">
							<thead>						
							<th>排序</th>						
							<th>变动金额</th>			
							<th>余额</th>
							<th>冻结金额</th>
							<th>费用类型</th>
							<!-- <th>业务类型</th>
							<th>流水号</th> -->					
							<th>时间</th>
							<th>费用说明(用户可见)</th>
							</thead>
							<!-- <th>费用详情(系统可见)</th> -->
							<tbody id="treet1">
								<c:forEach items="${accountInfo.userBill}" var="bill">
									<tr>
									<th>${bill.seqNum }</th>
																				
										<td><fmt:formatNumber value="${bill.money }" type="currency"/></td>		
										<td><fmt:formatNumber value="${bill.balance }" type="currency"/></td>
										<td><fmt:formatNumber value="${bill.freezeAmount }" type="currency"/></td> 															
										<td>${bill.type}</td>
										<%-- <td>${bill.businessType}</td>
										<td>${bill.requestNo }</td>	 --%>											
										<td><fmt:formatDate value="${bill.time }"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<%-- <td>${bill.detail }</td> --%>
										<td>${bill.typeInfo }</td>
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
	<script type="text/javascript">
		function transfer(){
			var userId = $("#userId").val();
			var seqNum = $("#seqNum").val();
			if(userId == '' || seqNum == ''){
				alert("请输入用户编号和排序号");
				return ;
			}
			$.ajax({
				type : 'POST',
				url:"${ctx}/account/transfer",		
				data:{
					'userId':userId,
					'seqNum':seqNum
				},
				beforeSend:function(){
								xval=getBusyOverlay('viewport',
								{color:'blue', opacity:0.5, text:'调账中...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
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
							alert("调账成功!");			
						}else if(msg == 'isnull'){
							alert("请输入用户编号和排序号");
						}else{
							alert("调账失败!");					
						}
				}
			});
		}
	</script>
</body>
</html>
