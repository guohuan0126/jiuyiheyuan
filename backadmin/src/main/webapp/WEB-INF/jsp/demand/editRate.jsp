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
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>修改利率</title>	
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
					<i class="fa fa-pencil"></i> 修改利率
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">修改利率</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">日期 <span
									class="asterisk">*</span></label>
									<input type="hidden" name="id" value="${dateRate.id }">
								<div class="col-sm-1">
								<input type="text" name="date"  
										class="form-control"  value="<fmt:formatDate
									value="${dateRate.date }"
									pattern="yyyy-MM-dd" />" disabled="disabled"/>
									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">利率<span
									class="asterisk">*</span></label>
								<div class="col-sm-1" style="width:98px;">
								<input type="text" name="rate"  onkeyup="rateFun()" id="rate" 
										class="form-control" placeholder="利率不能为空" value="${dateRate.rate*100}"/ style="width:60px;display:inline-block;">%
								<div id="errorRate" style="display: none;">利率不能为空</div>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">每万份收益 <span
									class="asterisk">*</span></label>
								<div class="col-sm-1"  >
								<input type="text" id="interest"  
										class="form-control"  value="${dateRate.interest }" disabled="disabled"/>
								
								</div>
							</div>				
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<!-- mainpanel -->
	</section>
	<script type="text/javascript">
	 function toDecimal(x) {              

		 var f = parseFloat(x);              

		 if (isNaN(f)) {  
		                 return false;              

		 }              

		 var f = Math.round(x*100)/100;              

		 var s = f.toString();              

		 var rs = s.indexOf('.');              

		 if (rs < 0) {  
				rs = s.length;                  
				s += '.';  
		             }              

		 while (s.length <= rs + 2) {  
		                 s += '0';              

		 }              

		 return s;   
		 }            
	function rateFun(){
		var rate=$("#rate").val()/100;
		 var interest = toDecimal(10000 * rate/ 365 ); 
		$("#interest").val(interest);
	}
	function sub(){
		var rate=$("#rate").val();
		if(rate==null || rate==''){
			$('#errorRate').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}
		var img = /^[0-9]+(.[0-9]{0,2})?$/;
		if (!img.test(rate) || isNaN(rate) || rate <0) {
			$('#errorRate').text("利率不正确");
			$('#errorRate').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}
		$.ajax({
			type : 'POST',
			url : '/demand/saveRate',						
			data:$('#form').serialize(),
			success:function(data) {
				if(data == 'ok'){
					alert("修改成功");
					location="/demand/everyRate"; 					
				}else{					
					alert("修改失败");
				}
			}
		});
	}
 </script>
	
</body>
</html>
