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

<title>未处理的转出申请</title>

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
					<i class="fa fa-table"></i>当前位置：未处理的转出申请
				</h2>				
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
					当前申请转出总数量：${demand.total }条   当前申请转出总金额：${demand.summoney }元    默认转出账户：${account },当前可用余额：${balance }
					</br></br>
					<form class="form-inline"  action="/demand/transferOut" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">用户编号</label>
	              <input type="text" name="userId" value="${userId }" class="form-control" id="exampleInputEmail2" placeholder="用户编号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">用户名</label>
	              <input type="text" name="realname" value="${realname }" class="form-control" id="exampleInputPassword2" placeholder="用户名">
	            </div>
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">手机号</label>
	              <input type="text" name="mobileNumber" value="${mobileNumber}" class="form-control" id="exampleInputmobile" placeholder="手机号">
	            </div>
	            <button type="submit" class="btn btn-primary">查询</button>
	            默认校验转入记录数量：
	            <input type="text" class="form-control" style="width:35px;" id="txt_top" value="6" />
	            <button type="button" id="btn_validte" class="btn btn-primary" >增强型转账校验</button>
	             <button type="button" id="add" class="btn btn-primary">发起转账</button>
	          </form>
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
									<th>转出金额</th>
									<th>转出申请时间</th>
									<th>转出途径</th>
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
	<div id="zhezhao"></div>
<script type="text/javascript">
	$(function() {
		var userMessage = '${userMessage}';
		if (userMessage != '') {
			var title = '${title}';
			var content = '${content}';
			alert(content);
		}
	});

	
	$("button[id=add]").click(function(){
		window.location.href="/demand/totransferOut";
	});
	//增强型转出校验
	$("#btn_validte").click(function(){
		var url = '/demand/EnhanceValidateTransferOut';
		var limit = $("#txt_top").val();
		var xval;
		$.ajax({
			  type: 'POST',
			  cache:false,
			  data:{'limit':limit},
			  url: url,
			  beforeSend:function(){
					xval=getBusyOverlay('viewport',
					{color:'blue', opacity:0.5, text:'验证中...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
			},
			  success: function(data){
				  xval.remove();
				  alert(data);
			  },
			  error:function(){
				  xval.remove();
				  alert('验证错误');
			  }
			});
		
		
	});
</script>


</body>
</html>
