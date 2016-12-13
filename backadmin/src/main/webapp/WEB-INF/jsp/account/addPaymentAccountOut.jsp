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
<link href='${ctx}/ueditor/themes/default/css/ueditor.min.css'
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) 此处可要可不要 -->
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/js/validate.js"></script>
<title>账户管理</title>
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
					<i class="fa fa-pencil"></i>支付账户管理
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h5 class="panel-title">添加支付账户取现记录</h5>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="asterisk"></span></label>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">渠道</label>
								<div class="col-sm-3">
									<input type="text" name="channel" value="${channel}"
										readonly="true" id="channel" width="10px" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">取现金额</label>
								<div class="col-sm-3">
									<input type="text" name="money" value="" id="money"
										width="10px" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">取现手续费</label>
								<div class="col-sm-3">
									<input type="text" name="fee" value="" id="fee"
										width="10px" class="form-control" />
								</div>
							</div>
							<!-- <div class="form-group">
								<label class="col-sm-3 control-label">取现类型</label>
								<div class="col-sm-3">
									<select class="select2" name="rechargeType" id="rechargeType"
										data-placeholder="充值类型..." style="height:40px" >
										<option selected value="">请选择</option>
										<option value="gateway">网关取现</option>
										<option value="quick">快速取现</option>
									</select>
								</div>
							</div> -->
							<div class="form-group">
								<label class="col-sm-3 control-label">取现描述</label>
								<div class="col-sm-3">
									<input type="text" name="typeInfo" value="" id="typeInfo"
										width="10px" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">操作流水号 </label>
								<div class="col-sm-3">
									<input type="text" name="requestNo" value="" id="requestNo"
										width="10px" class="form-control" />
								</div>
							</div>
						</div>
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="sub()">保存</button>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="reset" class="btn btn-default">重置</button>
								</div>
							</div>
						</div>
					</div>
			</div>
			<!-- panel -->
			</form>
		</div>
		<!-- contentpanel -->
		</div>
		<script type="text/javascript">
			function sub() {
				var channel = $("#channel").val();
				var money = $("#money").val();
				var fee = $("#fee").val();
				var typeInfo = $("#typeInfo").val();
				var requestNo = $("#requestNo").val();
				/* var rechargeType = $("#rechargeType").val(); */
				if (money == "") {
					alert("取现金额不能为空");
					return false;
				}
				if (fee == "") {
					alert("取现手续费不能为空");
					return false;
				}
				/* if (rechargeType == "") {
					alert("取现类型不能为空");
					return false;
				} */
				if (typeInfo == "") {
					alert("取现描述不能为空");
					return false;
				}
				if (requestNo == "") {
					alert("取现流水号不能为空");
					return false;
				}
				$.ajax({
					type : 'POST',
					url : "/account/paymentAccountOut",
					data : {
						"channel" : channel,
						"money" : money,
						"fee" : fee,
						/* "rechargeType" : rechargeType, */
						"typeInfo" : typeInfo,
						"requestNo" : requestNo
					},
					beforeSend:function(){
						xval=getBusyOverlay('viewport',
						{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
						{color:'blue', size:25, type:'o'});	
					},
					 success:function(data) {
						window.clearInterval(xval.ntime); 
						xval.remove();
						if (data == 'success') {
							alert("添加支付账户取现记录成功");
							window.location.href="/account/paymentAccountList";
						} else {
							alert("添加支付账户取现记录失败，"+data);
						}
					}
					
				});

			}
		</script>
	</section>
</body>
</html>
