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
<script type="text/javascript">
function editEmail(borrower){
			
        		ds.dialog({
   				   title : "编辑",
   				   content : '<div class="form-group"><label class="col-sm-3 control-label">邮箱</label><br>'+
   				   			 '<input type="text" rows="5" name="email2" id="email2" class="form-control"></div>',
   				   width:450,
   				   yesText : '确定',
   				   onyes:function(){
   				   		this.close();
   				    	$.ajax({
						type : "POST",
						cache: false,
						url : "<%=request.getContextPath()%> /loan/alterFixedBorrowersStatus",
						data : {
							'borrower' : borrower,
							'email' : $("#email2").val()
						},
						dataType : "text",
						async : false,
						success : function(resp) {
							location = "/loan/fixedborrowers";
						}
					});			
   				   },
   				   noText : '取消',
   				   onno : function(){
   				   this.close();  				  
   				   },
   				});					
			}

</script>
<title>借款人管理</title>
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
					<i class="fa fa-table"></i>当前位置：借款人列表
				</h2>
				<div class="breadcrumb-wrapper">
					<ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
						<li><a href=""></a></li>
					</ol>
				</div>
			</div>
			<div class="contentpanel ">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-horizontal" action="/loan/loanList"
							method="post">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-3 control-label">借款人 </label>
									<div class="col-sm-3">
										<input type="text" name="borrower" id="borrower" width="10px"
											class="form-control" onblur="queryBorrower()"
											placeholder="用户编号/邮箱/手机号" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">邮箱 </label>
									<div class="col-sm-3">
										<input type="text" name="email" id="email" width="10px"
											class="form-control" placeholder="邮箱" />
									</div>
								</div>
								<div class="form-group">
									<div class="row">
										<div class="col-sm-9 col-sm-offset-3">
											<button class="btn btn-primary" type="button"
												onclick="return sub()">保存</button>
										</div>
									</div>
								</div>
							</div>

						</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>
								<tr class="font">
									<th>编号</th>
									<th>真实姓名</th>
									<th>邮箱</th>
									<th>注册时间</th>
									<th>账户可用余额</th>
									<th width="5%">状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="fb" items="${fbs}">
									<tr class="font">
										<td>${fb.userId}</td>
										<td>${fb.fixedUser.realname}</td>
										<td>${fb.email}</td>
										<td><fmt:formatDate value="${fb.fixedUser.registerTime}"
												type="both" /></td>
										<td>${fb.availableBalance}</td>
										<td><input id="${fb.userId}" class="form-control"
											style="width: 60px;" value="${fb.status}" readonly="readonly"></td>
										<td><a
											onclick="updateFixedBorrowerStatus('${fb.userId}','open')" />开启</a>|
											<a id="${fb.userId}"
											onclick="updateFixedBorrowerStatus('${fb.userId}','off')" />关闭</a>|
											<a id="${fb.userId}" onclick="editEmail('${fb.userId}')" />编辑邮箱</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			function baseError(id, message) {
				$("#" + id + "Error p").remove();
				$str = "<span style=\"font-size:14px;color:black\" id=\""+id+"Error\"><p>"
						+ message + "</p></span>";
				$("#" + id).after($str);
			}
		
			
			function updateFixedBorrowerStatus(borrower,status){
				var currentStatus= $("#"+borrower).val();
				if(status == currentStatus){
					return;
				}else{
					$.ajax({
						type : "POST",
						cache: false,
						url : "<%=request.getContextPath()%> /loan/alterFixedBorrowersStatus",
						data : {
							'borrower' : borrower,
							'status' : status
						},
						dataType : "text",
						async : false,
						success : function(resp) {
							location = "/loan/fixedborrowers";
						}
					});
				}
			}
						
			//查询借款人账户
			function queryBorrower(){
				var borrower = $("#borrower").val();
					$.ajax({
							type : "POST",
							cache: false,
							url : "<%=request.getContextPath()%> /loan/queryBorrower",
							data : {
								'borrower' : borrower,
							},
							dataType : "text",
							async : true,
							success : function(resp) {
								baseError('borrower', resp);
							}
					});

			}
			
			function sub(){
				var borrower = $("#borrower").val();
				var email = $("#email").val();
				if(email == ''){
					alert("请输入借款人邮箱");
					return false;
				}
					$.ajax({
							type : "POST",
							cache: false,
							url : "<%=request.getContextPath()%>/loan/addfixedborrowers",
							data : {
								'borrower' : borrower,
								'email' : email
							},
							dataType : "text",
							async : true,
							success : function(resp) {
								location = "/loan/fixedborrowers";
							}
						});
			}
		</script>

	</section>

</body>
</html>
