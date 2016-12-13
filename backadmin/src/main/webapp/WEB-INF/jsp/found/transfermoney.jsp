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
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>内部资金转账</title>
<script src="../js/jquery-1.11.1.min.js"></script>
<script>
	$(function(){
		
		$("#basicForm").delegate("input[type=text]","blur",function(){
			var id = $(this).attr("id");
			var m ="";
			
			if(id=='txt_accountout')
			{
				m =$(this).val();
				if(m=="")return;
				$.post("/fund/getuserinfo/"+m, function(data){
				    $("#td_accountout").html(data);
				 });
			}
			else if(id=="txt_accountin")
			{
				m =$(this).val();
				if(m=="")return;
				$.post("/fund/getuserinfo/"+m, function(data){
				    $("#td_accountin").html(data);
				 });
			}
		});
		

	});
</script>
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
					<i class="fa fa-pencil"></i> 内部资金转账
				</h2>
				<div class="breadcrumb-wrapper">
					<span class="label"></span>
					<ol class="breadcrumb">
						<li></li>
						<li></li>
						<li class="active"></li>
					</ol>
				</div>
			</div>

			<div class="contentpanel" style="font-size:16px;">
				<form id="basicForm" class="form-horizontal" action="/fund/starttransfermoney"  method="post">
					<table>
					<tr>
					<td colspan="3" id="td_error" style="color:red;">${error}</td>
					</tr>
					<tr>
					<td ></td>
					</tr>
						<tr>
						
							<td>转出方账户手机号：</td>
							<td><input id="txt_accountout" name="txt_accountout" type="text" /></td>
							<td id="td_accountout"></td>
						</tr>
						<tr>
							<td>接收方账户手机号：</td>
							<td><input id="txt_accountin" name="txt_accountin" type="text" /></td>
							<td id="td_accountin"></td>
						</tr>
						<tr>
							<td>转账金额：</td>
							<td><input id="txt_money" name="txt_money" type="text" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="发起转账" /></td>
							<td></td>
						</tr>
					</table>
					
				</form>
			</div>
			<!-- contentpanel -->

		</div>
		<!-- mainpanel -->

	</section>


</body>
</html>
