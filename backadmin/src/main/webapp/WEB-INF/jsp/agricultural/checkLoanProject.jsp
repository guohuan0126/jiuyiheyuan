<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
<meta charset="utf-8"/>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
<meta name="description" content=""/>
<meta name="author" content=""/>
<link rel="shortcut icon" href="images/favicon.png" type="image/png"/>

<title> 
		<c:if test="${empty ruralfinanceData.id}">创建农贷借款项目</c:if>
		<c:if test="${not empty ruralfinanceData.id}">编辑农贷借款项目</c:if>
</title>
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
					<i class="fa fa-pencil"></i> <c:if test="${empty ruralfinanceData.id}">创建农贷借款项目</c:if>
					<c:if test="${not empty ruralfinanceData.id}">编辑农贷借款项目</c:if>
				</h2>
			</div>
			<div class="contentpanel">

				<!-- 创建借款表单 -->
				<form id="form1" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title"><c:if test="${empty ruralfinanceData.id}">创建农贷借款项目--基本信息</c:if><c:if test="${not empty ruralfinanceData.id}">编辑农贷借款项目--基本信息</c:if></h4>
						</div>
						<div class="panel-body">
							
							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">借款人手机号
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="borrower" id="borrower"
										width="10px" class="form-control" 
										onblur="verifyParam('borrower')" value="${base:decrypt(mobilenum)}" disabled="disabled"/>	
										<input type="hidden" name=loanerId id="loanerId" value=""/>
										<input type="hidden" name=id id="id" value="${ruralfinanceData.id}"/>						
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">借款总金额
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="totalMoney" id="totalMoney"
										width="10px" class="form-control" 
										onblur="verifyParam('totalMoney')" value="${ruralfinanceData.money}" disabled="disabled"/>
								</div>
							</div>



							<div class="form-group"  id="deadlineDIV">
								<label class="col-sm-3 control-label" style="color: red;">借款期限(月)
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<select name="deadline" id="deadline" disabled="disabled">
										<option value="1">1个月</option>
										<option value="2">2个月</option>
										<option value="3">3个月</option>
										<option value="4">4个月</option>
										<option value="5">5个月</option>
										<option value="6">6个月</option>
										<option value="7">7个月</option>
										<option value="8">8个月</option>
										<option value="9">9个月</option>
										<option value="10">10个月</option>
										<option value="11">11个月</option>
										<option value="12">12个月</option>
									</select>
								</div>
							</div>	
							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">还款方式
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<select name="repayType" id="repayType" disabled="disabled" >
										<option value="等额本息">等额本息</option>
									</select>
								</div>
							</div>														
							
							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">借款利率(%)
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="rate" id="rate" width="10px"
										class="form-control" onblur="verifyParam('rate')" value="${ruralfinanceData.rate}" disabled="disabled"/>
								</div>
							</div>
                            <div class="form-group">
								<label class="col-sm-3 control-label">备注信息
								</label>
								<div class="col-sm-3">
									<input type="text" name="remark" id="remark" width="10px"
										class="form-control" value="${ruralfinanceData.remark}" disabled="disabled"/>
								</div>
							</div>
						  <div class="form-group">
								<label class="col-sm-3 control-label">复核状态
								</label>
								<div class="col-sm-3">
									<select  data-placeholder="复核状态" name="checkType"  id="checkType" data-rel="chosen" >
									<option value="uncheck">未审核</option>
		                            <option value="checked">审核通过</option>
		                            <option value="reject">驳回</option>
									</select>
							</div>
							</div>
							 <div class="form-group">
								<label class="col-sm-3 control-label" for="areaId">审核理由
								</label>
								<div class="col-sm-3">
								<input id="reason" name="reason" type="text" style="height: 60px;width: 395px;" value="${ruralfinanceData.check_reason}">
							</div>	
					        </div>	
							<div class="form-group">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button"
											onclick="return sub()">保存</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		</div>
	</section>
	
	<script type="text/javascript">
	$("#deadline").val('${ruralfinanceData.loan_term}');
	$("#repayType").val('${ruralfinanceData.repay_type}');
	$("#checkType").val('${ruralfinanceData.status}');
	var reason=$("#checkType").val();
	    if(reason=='uncheck'){
	    $("#reason").attr("disabled","disabled");
	    }
	$("#checkType").change(function(){
	    var reason=$("#checkType").val();
	    if(reason=='uncheck'){
	    	$("#reason").attr("disabled","disabled");
	    }else{
	    	$("#reason").attr("disabled",false);
	    }
	});

		function sub(){
			console.log("创建开始.........");
			var num = subVerify();
			console.log("参数校验, num="+num);
			if(num == 0){
				if(confirm('确认操作借款项目?')){
					console.log("提交请求.............");
					$.ajax({						
						type : "POST",
						dataType : 'text',
						url : "${request.contextPath()}/agricultural/CheckLoanProject",
						data : $("#form1").serialize(), //要发送的是ajaxFrm表单中的数据
						async : false,
						beforeSend:function(){
							xval=getBusyOverlay('viewport',
							{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
							{color:'blue', size:25, type:'o'});	
						},
						error:function(e){
							alert(e);
						},
						success : function(data) {
							window.clearInterval(xval.ntime); 
							xval.remove();						
							if (data == 'success') {
								alert("复核成功");
								window.location.href="/agricultural/ruralfinance_data";
							} else{
				               location = "/";	
							} 		
						}			//location.replace(document.referrer);
					});
				}
			}else{
				alert('当前借款项目信息有误,请检查');
			}
		}
	
		function subVerify() {
			var num = 0;
			var sv = new Array(
					"borrower", "totalMoney", "rate");
			for ( var i = 0; i < sv.length; i++) {
				var v = sv[i].trim();
				if (v.length > 0) {
					if(!verifyParam(v)){
						++num;
					}
				}
			}
			return num;
		}
		
		//显示或隐藏错误信息
		function baseError(id, message) {
			$("#" + id + "Error p").remove();
			$str = "<span style=\"font-size:14px;color:red\" id=\""+id+"Error\"><p>"
					+ message + "</p></span>";
			$("#" + id).after($str);
		}

		//校验参数
		function verifyParam(vp) {
			var exp = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/;
		 if ('borrower' == vp) {
				var borrower = $("#borrower").val();
				if (borrower.length == 0) {
					baseError('borrower', '借款人不能为空');
					return false;
				} else {
					var result = queryBorrower(borrower);
					if(result =="借款人手机号不存在，请添加借款人基本信息！"){
					baseError('borrower', result);
					return false;
					}else{
						baseError('borrower', '');
						$("#loanerId").val(queryloanerId(borrower));
						return true;
					}
				
				}
			}else if ('totalMoney' == vp) {
				var totalMoney = $("#totalMoney").val();
				totalMoney = parseFloat(totalMoney);
				if (!exp.test(totalMoney) || totalMoney <= 0) {
					baseError('totalMoney', '借款总金额不正确');
					return false;
				} else {
					baseError('totalMoney', '');
					return true;
				}
			} else if ('rate' == vp) {
				var rate = $("#rate").val();
				if (!exp.test(rate) || rate <= 0) {
					baseError('rate', '借款利率不正确');
					return false;
				} else {
					baseError('rate', '');
					return true;
				};
			}  
		}
		
		//查询借款人账户
		function queryBorrower(borrower){
			var result;
				$.ajax({
					type : "POST",
					cache: false,
					url : "/agricultural/checkLoanerMobileNumber",	
					data : {
						'mobileNumber' : borrower,
					},
				dataType : "text",
				async : false,
				success : function(data) {
					if(data =='error'){					
						result="借款人手机号存在！";	
					}else{
						result="借款人手机号不存在，请添加借款人基本信息！";	
					}
				}
			});
			return result;
		}
		//根据手机号查新借款人id
		function queryloanerId(borrower){
			var result;
				$.ajax({
					type : "POST",
					cache: false,
					url : "/agricultural/getLoanerIdByMobileNumber",	
					data : {
						'mobileNumber' : borrower,
					},
				dataType : "text",
				async : false,
				success : function(data) {
					result=data;
				}
			});
			return result;
		}
	</script>
	<!-- 此css会引起页面全白 -->
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/dropzone.css" />
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/bootstrap-editable.css">
	<link type="text/css"
		href="${pageContext.request.contextPath}/css/jquery-ui-1.8.17.custom.css"
		rel="stylesheet" />
	<link type="text/css"
		href="${pageContext.request.contextPath}/css/jquery-ui-timepicker-addon.css"
		rel="stylesheet" />
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-1.8.17.custom.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
</body>
</html>
