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
<link href='${ctx}/ueditor/themes/default/css/ueditor.min.css' type="text/css" rel="stylesheet" >
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>  
<!-- 编辑器源码文件 -->  
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>  
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) 此处可要可不要 -->   
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/js/validate.js"></script>
<title>添加垫付资金记录</title>
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
					<i class="fa fa-pencil"></i>添加垫付资金记录
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h5 class="panel-title">添加垫付资金记录</h5>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="asterisk"></span></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">类型 </label>
								<div class="col-sm-3">
									<select name="type" id="type" class="form-control" style="width:50%;">
										<option value="in">增加</option>
										<option value="out">减少</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">支付通道 </label>
								<div class="col-sm-3">
									<select name="paymentId" id="paymentId" class="form-control" style="width:50%;">
										<option value="Yeepay">易宝支付</option>
										<option value="JDpay">京东支付</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">金额 </label>
								<div class="col-sm-3">
									<input style="width: 191px" id="money"/>
								</div>
						<!-- panel-body -->
					</div>
					<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="updateAdvancefundRecord()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<script type="text/javascript">
		function updateAdvancefundRecord(){
			var type=$("#type").val();
			var money=$("#money").val();
			var paymentId=$("#paymentId").val();
			$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/payMent/updateAdvancefundMoney",						
					data: {
						"type":type,
						"money":money,
						"paymentId":paymentId
					},
					success:function(data) {
					
						if(data=='error'){
							alert("操作失败");
						}else{
							alert("操作成功");
							window.location.href="/payMent/advancefundRecord";
						}
					}
				});		
			}
		</script>
	</section>
</body>
</html>
