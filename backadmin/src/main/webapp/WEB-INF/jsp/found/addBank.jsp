<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />

<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>添加代扣卡信息</title>	
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
					<i class="fa fa-pencil"></i> 添加代扣卡信息
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">添加代扣卡信息</h4>
						</div>
						<div class="panel-body">
						<form id="form" class="form-horizontal">
						<div class="form-group">
						<label class="col-sm-3 control-label">银行名称 <span
									class="asterisk">*</span></label>
							 <select class="select2" name="bank" id="st" data-placeholder="选择银行...">
			                  <option value="103">中国农业银行</option>
			                  <option value="104">中国银行</option>
			                  <option value="105">中国建设银行</option>
			                  <option value="301">交通银行</option>
			                </select>
			                </div>
							<div class="form-group">
								<label class="col-sm-3 control-label">银行卡号 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
								<input type="hidden" name="userId" value=${userId }>
									<input type="text" name="name" id="name" width="10px"
										class="form-control" placeholder="卡号不能为空" />
									<div id="errorName" style="display: none;">卡号不能为空</div>
								</div>
							</div>
							
						</form>
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" onclick="reset()" class="btn btn-default">重置</button>
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
		<!-- mainpanel -->
	</section>
	<script type="text/javascript">


	function sub(){
		$("#errorName").attr("style", "display:none;");
		var name = $("#name").val();
		if(name == ''){
			$("#errorName").attr("style", "display:block;font-size:14px;color:red;");
			return false;
		}else{
			$("#errorName").attr("style", "display:none;font-size:14px;color:red;");
		}
		$.ajax({
			type : 'POST',
			url : '/found/withhold/addBank',						
			data:$('#form').serialize(),
			success:function(data) {
				if(data == 'ok'){
					alert("添加成功");					
					location="/found/withhold";				 					
				}else if(data=='noexist'){
					$('#errorName').text("卡号已经存在");
					$('#errorName').attr("style", "display:block;font-size:14px;color:red");
				}else{					
					alert("添加失败");
				}
			}
		}); 
	}
 </script>

</body>
</html>
