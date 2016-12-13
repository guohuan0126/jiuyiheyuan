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
<title>添加活期宝资产</title>
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
					<i class="fa fa-pencil"></i><c:choose>
						<c:when  test="${tp == 'add'}">
							添加活期宝资产
						</c:when>
						<c:otherwise>
							编辑活期宝资产
						</c:otherwise>
					</c:choose> 
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form1" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title"><c:choose>
					<c:when  test="${tp == 'add'}">
							添加活期宝资产
						</c:when>
						<c:otherwise>
							编辑活期宝资产
						</c:otherwise>
					</c:choose> </h4>
						</div>
						<div class="panel-body">
							<div class="form-group show">
								<label class="col-sm-3 control-label">编号</label>
								<div class="col-sm-3">
									<input type="text" name="id" width="10px" class="form-control"
										placeholder="编号 " value="${record.id}" readonly/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">金额 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="money" id="money" width="10px"
										class="form-control" placeholder="金额"
										onChange="checkMoney(this.value);" value="<fmt:formatNumber
												value="${record.money}" groupingUsed="false"
												maxFractionDigits="3"/>" readonly />
									<div id="errorMoney" style="display: none;">请输入金额</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">预计执行时间</label>
								<div class="col-sm-2">
									<input type="text" name="expect" id="expect"
										class="form-control ui_timepicker" placeholder="预计执行时间" 
										value="<fmt:formatDate value="${record.expectTime}" pattern="yyyy-MM-dd HH:mm:ss"/> " readonly/>
								</div>
								
							</div>
							<div class="form-group show">
								<label class="col-sm-3 control-label">最后更新时间</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" value="<fmt:formatDate value="${record.begintime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly/>
								</div>
							</div>
							<div class="form-group show">
								<label class="col-sm-3 control-label">结束时间</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" value="<fmt:formatDate value="${record.endtime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly/>
								</div>
							</div>
							<div class="form-group show">
								<label class="col-sm-3 control-label">最后更新人 </label>
								<div class="col-sm-3">
									<input type="text" name="borrower" width="10px"
										class="form-control" value="${record.operateorid}" readonly/>
								</div>
							</div>
						<div class="form-group show">
								<label class="col-sm-3 control-label">资产类型： </label>
								<div class="col-sm-3">									
									<select name="type" id="type">								
										<option value="add">补充</option>
										<option value="expired">续投</option>
									</select>	
								</div>
							</div>							
							
						</div>
						
						<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button" onclick="sub()">保存</button>
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
		var type = '${tp}';
		if (type == 'add') {
			$(".show").addClass("hidden");
		}
		$("#type").val('${record.type}');
		function sub() {			
			$.ajax({
				type : "POST",
				url : '/demand/record/'+type,
				data : $('#form1').serialize(),// 你的formid
				error : function(e) {
					alert("异常");
				},
				success : function(data) {		
					if (data == 'ok') {
						alert('操作成功');
						window.location = "/demand/procut";
					} else if (data == 'status') {
						alert('有未执行的资产，不能添加');
					} else if(data == 'avlid'){
						alert('此产品已过预计执行时间，不能编辑');
					} else if(data == 'in'){
						alert('有转入中的操作，不能添加');
					} else if(data == 'max'){
						alert('本期未完成，不能添加');
					}else{
						alert('操作失败');
					}
				}
			});
		}
	</script>
</body>
</html>
