﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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

<title>流标</title>


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
					<i class="fa fa-table"></i>当前位置：流标
				</h2>
			</div>
			<div class="contentpanel">

				<div class="panel panel-default">
					<div class="panel-heading">
						<form id="baseform" class="form-inline" method="post">
							<div class="form-group">
								<label class="sr-only" for="exampleInputEmail2">查询条件</label> <input
									type="text" name="investId" id="investId" class="form-control"
									placeholder="投资流水号">
								<div id="errorusername" style="display: none;">查询条件不能空</div>
							</div>
							<button class="btn btn-primary" onclick="checksearch()"
								type="button">查询</button>
						</form>
						<br />
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>操作</th>
									<th>用户编号</th>
									<th>用户姓名</th>
									<th>项目编号</th>
									<th>项目名称</th>
									<th>投资金额(元)</th>
									<th>投资状态</th>
								</tr>
							</thead>
							<tbody id="ttab">

							</tbody>
						</table>

					</div>
					<div class="panel-body">
						<form id="freezeForm" class="form-horizontal ">
							<div class="form-group">
								<label class="col-sm-1 control-label">商户编号<span
									class="asterisk">*</span></label>
								<div class="col-sm-1">
									<input type="hidden" id="userid" name="userId"> <input
										type="hidden" id="userno" name="userno"> <input
										type="hidden" id="sta" name="sta"> <input
										type="hidden" id="money" name="money"> <input
										type="hidden" id="loanId" name="loanId"> <input
										type="hidden" id="ids" name="ids"> <select
										class="select2" name="bct" id="bct" data-placeholder="商户编号...">
										<option value="10012401196">正式商户编号</option>
										<option value="10040011137">测试商户编号</option>
									</select>
								</div>
								<div class="col-sm-1" style="width: 240px;">
									
									<button class="btn btn-primary" type="button"
										onclick="return sub3()">本地流标</button>
								
									<button class="btn btn-primary" type="button"
										onclick="return sub()">正常流标</button>
								
									<button class="btn btn-primary" type="button"
										onclick="return sub2()" style="margin-left: 20px;">仅易宝流标</button>
								</div>
								<div class="col-sm-1">
									<div id="errorstatus" style="display: none;">投资状态不正确</div>
									<div id="erroruserId" style="display: none;">投资流水号不能为空</div>

								</div>
							</div>
							<br/>
								<div style="color:red">
								1.正常流标：包括本地流标和易宝流标<br/>
								2.易宝流标：仅仅是易宝端的投资流标、适用于本地数据已经流标，但是易宝没有流标的情况下使用
								</div>
					</div>
					</form>

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
		jQuery(document).ready(function() {
			jQuery(".select2").select2({
				width : '100%',
				minimumResultsForSearch : -1
			});

		});
		function checksearch() {

			var username = $("#username").val();
			var list;
			var str = '';
			if (username == '') {
				$('#errorusername').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			} else {
				$('#errorusername').attr("style",
						"display:none;font-size:14px;color:red");
				$
						.ajax({
							type : "POST",
							url : '/failLoan/failLoanList',
							data : $('#baseform').serialize(),// 你的formid
							async : false,
							dataType : 'json',
							error : function(e) {
								alert("异常");
							},
							success : function(data) {
								list = eval(data);
								$("#userid").val(list.investUserID);
								$("#sta").val(list.status);
								$("#money").val(list.money);
								$("#loanId").val(list.loanId);
								str += '<tr>';
								str += '<td><input name=\"id\" value="'+list.id+'" id="'+list.id+'"   checked=\"checked\"  type=\"checkbox\"></td> ';
								str += '<td>' + list.investUserID + '</td>';
								str += '<td>' + list.investUserName + '</td>';
								str += '<td>' + list.loanId + '</td>';
								str += '<td>' + list.loanName + '</td>';
								str += '<td>' + list.money + '</td>';
								str += '<td>' + list.status + '</td>';
								str += '</tr>';
								$("#ttab").html(str);
							}
						});
				//  $("#baseform").submit();
			}
		}
		
		
		function sub() {
			var count = 0;
			var status = "";
			$("[name = id]:checkbox").each(function() {
				if ($(this).is(":checked")) {
					$("#ids").val($(this).attr("value"));
					status = $("#sta").val();
					count++;
				}
			});
			if (count == 0) {
				$('#errorstatus').remove();
				$('#erroruserId').attr("style",
						"display:block;font-size:14px;color:red");
				//return false;
			} else if (status != '投标成功') {
				$('#erroruserId').remove();
				$('#errorstatus').attr("style",
						"display:block;font-size:14px;color:red");
				//return false;
			}else {
				$('#erroruserId').attr("style",
						"display:none;font-size:14px;color:red");
				$('#errorstatus').attr("style",
						"display:none;font-size:14px;color:red");
				var bool = window.confirm("您确定要流标吗?");
				if (bool == true) {
					$
							.ajax({
								type : "POST",
								url : '/failLoan/failLoanBill',
								data : $('#baseform').serialize(),// 你的formid
								async : false,
								error : function(e) {
									alert("异常");
								},
								success : function(data) {
									if (data == 'ok') {
										$
												.ajax({
													type : "POST",
													url : '/failLoan/failLoanList',
													data : $('#baseform')
															.serialize(),// 你的formid
													async : false,
													dataType : 'json',
													error : function(e) {
														alert("异常");
													},
													success : function(data) {
														var str = '';
														$("#ttab").html("");
														list = eval(data);
														$("#userid")
																.val(
																		list.investUserID);
														$("#sta").val(
																list.status);
														$("#money").val(
																list.money);
														$("#loanId").val(
																list.loanId);
														str += '<tr>';
														str += '<td><input name=\"id\" value="'+list.id+'" id="'+list.id+'"   checked=\"checked\"  type=\"checkbox\"></td> ';
														str += '<td>'
																+ list.investUserID
																+ '</td>';
														str += '<td>'
																+ list.investUserName
																+ '</td>';
														str += '<td>'
																+ list.loanId
																+ '</td>';
														str += '<td>'
																+ list.loanName
																+ '</td>';
														str += '<td>'
																+ list.money
																+ '</td>';
														str += '<td>'
																+ list.status
																+ '</td>';
														str += '</tr>';
														$("#ttab").html(str);
													}
												});
										alert('操作成功');

									} else if (data == 'fail') {
										alert('操作失败');
									} else if (data == 'balance') {
										alert('余额不足,操作失败');
									} else if (data == 'unfreeze') {
										alert('解冻金额大于冻结金额,操作失败');
									} else {
										alert(data);
									}
								}
							});

				}

			}
		}


		function sub3() {
			var count = 0;
			var status = "";
			$("[name = id]:checkbox").each(function() {
				if ($(this).is(":checked")) {
					$("#ids").val($(this).attr("value"));
					status = $("#sta").val();
					count++;
				}
			});
			if (count == 0) {
				$('#errorstatus').remove();
				$('#erroruserId').attr("style",
						"display:block;font-size:14px;color:red");
			} else {
				$('#erroruserId').attr("style",
						"display:none;font-size:14px;color:red");
				$('#errorstatus').attr("style",
						"display:none;font-size:14px;color:red");
				var bool = window.confirm("您确定要流标吗?");
				if (bool == true) {
					$
							.ajax({
								type : "POST",
								url : '/failLoan/localfailLoanBill',
								data : $('#baseform').serialize(),// 你的formid
								async : false,
								error : function(e) {
									alert("异常");
								},
								success : function(data) {
									if (data == 'ok') {
										$
												.ajax({
													type : "POST",
													url : '/failLoan/failLoanList',
													data : $('#baseform')
															.serialize(),// 你的formid
													async : false,
													dataType : 'json',
													error : function(e) {
														alert("异常");
													},
													success : function(data) {
														var str = '';
														$("#ttab").html("");
														list = eval(data);
														$("#userid")
																.val(
																		list.investUserID);
														$("#sta").val(
																list.status);
														$("#money").val(
																list.money);
														$("#loanId").val(
																list.loanId);
														str += '<tr>';
														str += '<td><input name=\"id\" value="'+list.id+'" id="'+list.id+'"   checked=\"checked\"  type=\"checkbox\"></td> ';
														str += '<td>'
																+ list.investUserID
																+ '</td>';
														str += '<td>'
																+ list.investUserName
																+ '</td>';
														str += '<td>'
																+ list.loanId
																+ '</td>';
														str += '<td>'
																+ list.loanName
																+ '</td>';
														str += '<td>'
																+ list.money
																+ '</td>';
														str += '<td>'
																+ list.status
																+ '</td>';
														str += '</tr>';
														$("#ttab").html(str);
													}
												});
										alert('操作成功');

									} else if (data == 'fail') {
										alert('操作失败');
									} else if (data == 'balance') {
										alert('余额不足,操作失败');
									} else if (data == 'unfreeze') {
										alert('解冻金额大于冻结金额,操作失败');
									} else {
										alert(data);
									}
								}
							});

				}

			}
		}
		

		function sub2() {
			
			var id = $("#investId").val();		
			if(id == ''){
				alert('请输入流水号');
				return ;
			}
			if (window.confirm("您确定要流标吗?")) {
				$.ajax({
					type : "POST",
					url : '/failLoan/failLoanBill/yeepay',
					data : {
						id : id
					},
					success : function(data) {
						alert('操作成功');
					}
				});

			}
		}
	</script>
</body>
</html>