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
<title>手动添加放款记录</title>
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
					<i class="fa fa-pencil"></i>手动添加放款记录
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h5 class="panel-title">手动添加放款记录</h5>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="asterisk"></span></label>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">借款编号</label>
								<div class="col-sm-3">
									<input type="text" name="loandataId" value="${paymentLoanRecords.loandataId}" disabled="disabled"  id="loandataId"  width="10px"
										class="form-control"/>	
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">姓名</label>
								<div class="col-sm-3">
									<input type="text" name="userName" value="${paymentLoanRecords.loanerName}" disabled="disabled"  id="userName"  width="10px"
										class="form-control"/>	
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">手机号 </label>
								<div class="col-sm-3">
									<input type="text" name="mobileNumber" value="${paymentLoanRecords.mobileNumber}" disabled="disabled"  id="mobileNumber"  width="10px"
										class="form-control"/>	
										<input id="bankId" type="hidden" value="${paymentLoanRecords.bankId}"/>								
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">合同编号 </label>
								<div class="col-sm-3">
									<input type="text" name="contractId" value="${paymentLoanRecords.loanId}" disabled="disabled"  id="contractId"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">项目名称 </label>
								<div class="col-sm-3">
									<input type="text" name="loanName" value="${paymentLoanRecords.loanName}" disabled="disabled" id="loanName"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">创建时间 </label>
								<div class="col-sm-3">
									<input type="text" name="createTime" value="${paymentLoanRecords.creatTime}" disabled="disabled"  id="createTime"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">借款金额 </label>
								<div class="col-sm-3">
									<input type="text" name="money" value="${paymentLoanRecords.money}" id="money"  disabled="disabled"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">支付通道</label>
								<div class="col-sm-3">
									<input type="text" name="paymentChannel" value="${paymentLoanRecords.paymentChannel}" disabled="disabled"   id="paymentChannel"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">放款失败原因 </label>
								<div class="col-sm-3">
									<input type="text" name="msg" value="${paymentLoanRecords.msg}" id="msg"  disabled="disabled"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">备注信息  <font style="color: red">*</font> </label>
								<div class="col-sm-3">
									<input type="text" name="remark" id="remark"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							</div>
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
			function sub(){
			var money=$("#money").val();
			var bankId=$("#bankId").val();
			var loandataId=$("#loandataId").val();
			var remark=$("#remark").val();
			if(remark==""){
				alert("备注信息不能为空");
				return false;
			}
			$.ajax({
				type : 'POST',
				dataType:'json',
				url : "/agricultural/AddLoanfailure",						
				data: {
					"money":money,
					"loandataId":loandataId,
					"bankId":bankId,
					"remark":remark
				},
				success:function(data) {
					if(data=='success'){
						alert("手动添加放款记录成功");
					}else {
						alert("手动添加放款记录失败");
					}
				}
			});		
			
		}
	</script>
				
	</section>
</body>
</html>
