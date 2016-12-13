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
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>操作用户账户</title>
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
					<i class="fa fa-pencil"></i> 操作用户账户
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

			<div class="contentpanel">
				<form id="basicForm" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">操作用户账户</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">用户编号 <span
									class="asterisk">*</span></label>
								<div class="col-sm-2">
									<input type="text" name="userId" id="userId" width="10px"
										class="form-control" placeholder="用户编号..." />
								</div>
								<div id="errorId" style="display: none;">用户编号不能为空</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">操作<span
									class="asterisk">*</span></label>
								<div class='col-sm-9'>
									<select id="type" name="type" class='moneyType' required
										onchange="show(this)">
										<option selected value="">请选择</option>
										<option value="ti_balance">转入到余额</option>
										<option value="ti_balance_recharge">转入到余额（充值）</option>
										<option value="to_balance">从余额转出</option>
										<option value="to_balance_withdraw">从余额转出（提现）</option>
										<option value="freeze">冻结金额</option>
										<option value="unfreeze">解冻金额</option>
										<option value="to_frozen_normal">提现退款（普通）</option>
										<option value="to_frozen_quick">提现退款（加急）</option>
									</select>
								</div>
							</div>
							<div id="payment_channel" style="display: none;"
								class="form-group">
								<label class="col-sm-3 control-label">支付渠道<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select class="select2" name="channel" id="channel"
										data-placeholder="支付渠道" style="height:40px">
										<option selected value="">请选择支付渠道</option>
										<option value="Baofoo">宝付</option>
										<option value="Fuiou">富友</option>
										<option value="JDpay">京东</option>
										<option value="Yeepay">易宝</option>
									</select>
								</div>
							</div>
							<div id="recharge_type" style="display: none;" class="form-group">
								<label class="col-sm-3 control-label">充值类型<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select class="select2" name="rechargeType" id="rechargeType"
										data-placeholder="充值类型..." style="height:40px">
										<option selected value="">请选择</option>
										<option value="gateway">网关</option>
										<option value="quick">快速</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">金额<span
									class="asterisk">*</span></label>
								<div class="col-sm-2">
									<input type="text" name="money" id="money" placeholder="金额..."
										class="form-control" />
								</div>
								<div id="errorMoney" style="display: none;">金额不能为空</div>
								<div id="errorMoneynum" style="display: none;">金额格式不正确</div>
							</div>


							<div class="form-group">
								<label class="col-sm-3 control-label">说明(用户可见) </label>
								<div class="col-sm-3">
									<textarea rows="5" name="typeInfo" class="form-control"
										placeholder="说明..."></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">详情(系统可见) </label>
								<div class="col-sm-3">
									<textarea rows="5" name="detail" class="form-control"
										placeholder="详情..."></textarea>
								</div>
							</div>
						</div>
						<!-- panel-body -->
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="submitUserBill();">保存</button>
									<button type="reset" class="btn btn-default">重置</button>
								</div>
							</div>
						</div>

					</div>
					<!-- panel -->
				</form>
			</div>
			<!-- contentpanel -->

		</div>
		<!-- mainpanel -->

	</section>
	<script type="text/javascript">
		function show(channel) {
			var value = channel.value;
			if (value == "ti_balance_recharge"
					|| value == "to_balance_withdraw") {
				document.getElementById("payment_channel").style.display = "";
				document.getElementById("recharge_type").style.display = "";
			} else {
				document.getElementById("payment_channel").style.display = "none";
				document.getElementById("recharge_type").style.display = "none";
			}
		}
	</script>
	<script>
		jQuery(document).ready(function() {
			jQuery(".moneyType").select2({
				width : '21%',
				minimumResultsForSearch : -1
			});
		});

		function submitUserBill() {
			if ($("#userId").val() == '') {
				$('#errorId').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}

			if ($("#money").val() == '') {
				$('#errorMoney').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			} else if (isNaN($("#money").val())) {
				$('#errorMoneynum').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}
			if ($("#type").val() == 'ti_balance_recharge'
					|| $("#type").val() == 'to_balance_withdraw') {
				if ($("#channel").val() == '') {
					alert("转入到余额（充值）或从余额转出（提现）时，支付渠道不能为空");
					return false;
				}
				if ($("#rechargeType").val() == '') {
					alert("转入到余额（充值）或从余额转出（提现）时，充值类型不能为空");
					return false;
				}
			}

			$.ajax({
				type : "POST",
				url : '${addUrl}',
				data : $('#basicForm').serialize(),// 你的formid
				async : false,
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'ok') {
						alert('操作成功');
						window.location.href = "/userbill/toadd";
					} else {
						alert('操作失败');
					}
				}
			});

		}
	</script>

</body>
</html>
