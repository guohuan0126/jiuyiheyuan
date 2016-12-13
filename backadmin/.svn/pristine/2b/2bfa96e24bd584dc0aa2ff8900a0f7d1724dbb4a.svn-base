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
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>还款计划</title>	
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
					<i class="fa fa-pencil"></i> 修改还款计划
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">修改还款计划</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">借款项目名称 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									${repay.loan.name}
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">借款项目状态 </label>
								<div class="col-sm-3">
									${repay.loan.status}
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">借款项目计算方式 </label>
								<div class="col-sm-3">
									${repay.loan.operationType}
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">借款项目周期 </label>
								<div class="col-sm-3">
									 <c:if test="${'月' eq repay.loan.operationType }">
										${repay.loan.deadline }个月
									 </c:if>
									 <c:if test="${'天' eq repay.loan.operationType }">
										<c:choose>
											<c:when test="${'是' eq repay.loan.beforeRepay and not empty loan.symbol}">
												${repay.loan.day + repay.loan.symbol }天
											</c:when>
											<c:otherwise>
												${repay.loan.day }天
											</c:otherwise>
										</c:choose>													
									 </c:if>		
								</div>
							</div>
						<div class="form-group">
								<label class="col-sm-3 control-label">期数 </label>
								<div class="col-sm-3">													
									<input type="hidden" name="id" id="id" width="10px"
										class="form-control" value="${repay.id}" />					
									<input type="text" name="period" id="period" width="10px"
										class="form-control" value="${repay.period}" readonly/>
								</div>
						</div>
						<div class="form-group">
								<label class="col-sm-3 control-label">放款日 </label>
								<div class="col-sm-3">
								<input type="text" name="time" id="time" width="10px" 
										class="form-control" value="<fmt:formatDate value="${repay.time}" type="both"/>" readonly/>
									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">还款日</label>
								<div class="col-sm-3">
								<input type="text" name="repayDay" id="repayDay" width="10px"
										class="form-control datepicker" value="<fmt:formatDate value="${repay.repayDay}" type="both"/>" />
									
								</div>
							</div>
							<script type="text/javascript">
								 jQuery('.datepicker').datetimepicker({
									showSecond : true,
									timeFormat : 'hh:mm:ss',
									stepHour : 1,
									stepMinute : 1,
									stepSecond : 1,
								});				 
							
							</script>
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


function sub(){	
			$.ajax({
				type : 'POST',
				url : '/loan/alertRepay',						
				data:$('#form').serialize(),
				success:function(data) {
					if(data == 'ok'){
						alert("修改成功");
						location='${cxt}/loan/toRepayView?param=${repay.loan.id}'; 					
					}else{					
						alert("修改失败");
					}
				}
			});
		}
 </script>
	
</body>
</html>
