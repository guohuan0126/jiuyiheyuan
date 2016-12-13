<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>减少活期宝资产</title>
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
					<i class="fa fa-pencil"></i>
							减少活期宝资产
				</h2>
			</div>
			<div class="contentpanel">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">
							减少活期宝资产
					 </h4>
						</div>
							<div class="form-group">
								<label style="float:left;width:55px;margin-left:320px;line-height:30px;"class="col-sm-3 control-label" >金额 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="money" id="money" width="10px"
										class="form-control" placeholder="金额"
										onChange="checkMoney(this.value);"/>
									<span id="usedMoney"style="color: #ccc" value="${money}">活期宝可用资产： ${money}</span>
									<div id="errorMoney" style="display: none;">请输入金额</div>
								</div>
							
							</div>
					
							<div class="form-group">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button" onclick="sub()">保存</button>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" onclick="reset()"
											class="btn btn-default">重置</button>
									</div>
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
		
		function checkMoney(money) {
			$('#errorMoney').attr("style",
						"display:none;");
			var img = /^[0-9]*[1-9][0-9]*$/;
			if (isNaN(money) || !img.test(money)) {
				$('#errorMoney').text("金额只能是正整数");
				$('#errorMoney').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}
			return true;
		}

		function sub() {
			var money = $("#money").val();
			if (money == null || money == '') {
				$('#errorMoney').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}
			var usedMoney=${money};
			if(money>usedMoney){
				$('#errorMoney').text("减少金额不能多于活期宝资产");
				$('#errorMoney').attr("style",
				"display:block;font-size:14px;color:red");
				return false;
			}
			if(!checkMoney(money)){
				return false;
			}

			$.ajax({
				type : "post",
				url : "/demand/record/sub",
				data : {
					"money" : money		
					},
				dataType : "text",
				success : function(data) {
					if(data='ok'){
						alert('操作成功');
						window.location.href="/demand/procut";
					}else{
						alert('操作失败');
					}
				},
				error : function() {
					console.info("调取失败");
				}
			});
		}
	</script>
</body>
</html>
