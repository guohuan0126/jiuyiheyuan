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

<title>VIP会员</title>

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
					<i class="fa fa-table"></i>当前位置：VIP会员
				</h2>				
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
					
						<form id="form1" class="form-horizontal ">
							
							<div class="form-group">
								<label class="col-sm-1 control-label">用户名/手机号<span
									class="asterisk">*</span></label>
								<div class="col-sm-1">
									<input
										type="text" name="search" id="availableMoney"
										class="form-control"   />
								 <div id="erroravailableMoney" style="display: none;">不能为空</div>
								</div>
								<input
										class="btn btn-primary" type="button" id="saveMoney" value="添加" >
							</div>
					</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>项目编号</th>
									<th>用户编号</th>
									<th>姓名</th>
									<th>手机号</th>
									<th>时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="demo" varStatus="status">
									<tr>
										<td>${demo.loanId}</td>
										<td>${demo.userId}</td>
										<td>${demo.realname}</td>
										<td>${demo.mobileNumber}</td>
										<td><fmt:formatDate value="${demo.time }"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td><a style="cursor:pointer"  onclick="del('/loan/delVip?id=${demo.id}');">删除</a> </td>
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
		
		
		function searchFun(){
			var data=false;
			var totalMoney=$("#availableMoney").val();
			if(totalMoney==null || totalMoney==''){
				$("erroravailableMoney").attr("style", "display:block;font-size:14px;color:red");
				return false;
			}
			
			$.ajax({
				type : "POST",
				cache: false,
				url : "/loan/queryVip",
				data : {
					'search' : totalMoney,
				},
			dataType : "text",
			async : false,
			success : function(result) {
				if (result == 'notexist') {
					$("#erroravailableMoney").attr("style", "display:block;font-size:14px;color:red");
					$("#erroravailableMoney").text("用户不存在！");
				} else if(result == 'INVESTOR_NULL'){
					$("#erroravailableMoney").attr("style", "display:block;font-size:14px;color:red");
					$("#erroravailableMoney").text("该用户未实名认证");
				} else {
					data = true;
					$("#erroravailableMoney").attr("style", "display:block;font-size:14px;color:green");
					$("#erroravailableMoney").text(result);
				}
			}
		});
			return data;
		}
		
		$("input[id=saveMoney]").click(function(){
			
			var totalMoney=$("#availableMoney").val();
			if(totalMoney==null || totalMoney==''){
				alert('输入值不能为空');
				return false;
			}
			if(searchFun()){
				$.ajax({
					type : "POST",
					url : '/loan/editVip',
					data : {
							search:totalMoney,
							loanId:'${loanId}',
						  },// 你的formid
					async : false,
					error : function(e) {
						alert("异常");
					},
					success : function(data) {
						if (data == 'ok') {
							alert('操作成功');
							window.location = "/loan/vipList?id="+'${loanId}';
						}else{
							alert('操作失败');
						}
					}
				});
			}
			 
		});
		function del(url) {
			if(confirm("确认删除吗")){
				$.ajax({
					type : "POST",
					url : url,
					async : false,
					error : function(e) {
						alert("异常");
					},
					success : function(data) {
						if (data == 'ok') {
							alert('操作成功');
							window.location = "/loan/vipList?id="+'${loanId}';
						}else{
							alert('操作失败');
						}
					}
				});
			}
			} 
	</script>
</body>
</html>
