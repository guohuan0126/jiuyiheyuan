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
<link rel="shortcut icon" href="images/favicon.png" type="image/png">

<title>借款项目附加信息</title>
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
					<i class="fa fa-pencil"></i> 借款项目附加信息
				</h2>
			</div>
			<div class="contentpanel">

				<!-- 创建借款表单 -->
				<form id="form1" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">借款项目附加信息--房贷信息</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">所处位置
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="position" id="position" width="10px"
										class="form-control" onblur="verifyParam('position')" value="${house.position }"/> <input
										type="hidden" name="loanId" id="loanId" value="${loanId}" />
									<input type="hidden" name="loanType" id="loanType"
										value="${loanType}" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">性质
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<select name="nature" id="nature">
										<option value="住宅">住宅</option>
										<option value="商铺">商铺</option>
										<option value="写字楼">写字楼</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">建筑面积
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="area" id="area" width="10px"
										class="form-control" onblur="verifyParam('area')" value="${house.area }"/>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">评估价格
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="assessPrice" id="assessPrice"
										width="10px" class="form-control"
										onblur="verifyParam('assessPrice')" value="${house.assessPrice }"/>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">担保方式(抵押方式)
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<select name="guaranteeType" id="guaranteeType">
										<option value="一抵">一抵</option>
										<option value="二抵">二抵</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">抵押权人 </label>
								<div class="col-sm-3">
									<input type="text" name="mortgagee" id="mortgagee" width="10px"
										class="form-control" value="${house.mortgagee }"/>
								</div>
							</div>

							<c:if test="${status eq 'more'}">
							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">抵/质押率
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="guaranteeRate" id="guaranteeRate"
										width="10px" class="form-control"
										onblur="verifyParam('guaranteeRate')" value="${house.guaranteeRate }"/>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">逾期处理方式
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="overdueHandling"
										id="overdueHandling" onblur="verifyParam('overdueHandling')">${house.overdueHandling }</textarea>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">其他说明
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="otherDescription"
										id="otherDescription" >${house.otherDescription }</textarea>
								</div>
							</div>
							</c:if>
							
							<c:if test="${status eq 'less'}">
						<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">风控提示
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="reminderInfo" id="reminderInfo"
										width="10px" class="form-control" value="${house.reminderInfo }"
										onblur="verifyParam('reminderInfo')" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">风控措施
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="measuresInfo" id="measuresInfo"
										width="10px" class="form-control" value="${house.measuresInfo }"
										onblur="verifyParam('measuresInfo')" />
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">逾期处理方式
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="overdueInfo" 
										id="overdueInfo" onblur="verifyParam('overdueInfo')">${house.overdueInfo }</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">其他说明
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="otherInfo"
										id="otherInfo" >${house.otherInfo }</textarea>
								</div>
							</div>
						</c:if>
							<div class="form-group">
										<label class="col-sm-3 control-label">项目地点</label>
										<div class="col-sm-3">
											<input type="text" name="itemAddress" id="itemAddress"
												width="10px" class="form-control" value="${house.itemAddress}" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">借款人姓名</label>
										<div class="col-sm-3">
											<input type="text" name="borrowerName" id="borrowerName"
												width="10px" class="form-control" value="${house.borrowerName }" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label">借款人身份证</label>
										<div class="col-sm-3">
											<input type="text" name="borrowerIdCard" id="borrowerIdCard"
												width="10px" class="form-control" value="${house.borrowerIdCard }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">新增/展期 </label>
										<div class="col-sm-3">
											<select name="remark" id="remark">
												<option value="">请选择</option>
												<option value="新增"
													<c:if test="${house.remark == '新增'}">selected</c:if>>新增</option>
												<option value="展期"
													<c:if test="${house.remark == '展期'}">selected</c:if>>展期</option>
											</select>
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
	
		function sub(){
			var num = subVerify();
			if(num == 0){
				if(window.confirm('确认操作借款项目附加信息?')){
					$.ajax({
						cache : false,
						type : "POST",
						dataType : 'json',
						url : "<%=request.getContextPath()%>/loan/createLoanDetail",
								data : $("#form1").serialize(), //要发送的是ajaxFrm表单中的数据
								async : false,
								beforeSend:function(){
									xval=getBusyOverlay('viewport',
									{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
									{color:'blue', size:25, type:'o'});	
								},
								success : function(data) {
									window.clearInterval(xval.ntime); 
									xval.remove();
									var dataObj = eval(data);
									if (dataObj.status == 'ok') {
										alert('附加信息创建成功!');
										location = "/loan/loanList";
									} else if (dataObj.status == 'fail') {
										if (dataObj.error == 'loanIdNULL') {
											alert('创建失败!借款项目id为空')
										} else if (dataObj.error == 'loanNULL') {
											alert('创建失败!借款项目为空')
										} else if (dataObj.error == 'loanTypeNULL') {
											alert('创建失败!借款类型不正确')
										} else {
											var errs = dataObj.error;
											var errs = errs.split(",");
											for ( var i = 0; i < errs.length; i++) {
												var v = errs[i].trim();
												if (v.length > 0) {
													verifyParam(v);
												}
											}
										}
									}else if(dataObj.status == 'updateOk'){
										alert('附加信息编辑成功!');
										location.replace(document.referrer);
									}
								}
							});
				}
			}else{
				alert('当前借款项目附加信息有误,请检查');
			}
		}

		function subVerify() {
			var num = 0;
			var sv = new Array("position", "nature", "area", "assessPrice", "guaranteeRate", "overdueHandling","reminderInfo","measuresInfo","overdueInfo");
			for ( var i = 0; i < sv.length; i++) {
				var v = sv[i].trim();
				if (v.length > 0) {     
					if (!verifyParam(v)) {
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
			if ('position' == vp) {
				var position = $("#position").val();
				if (position.length == 0) {
					baseError('position', '所处位置不能为空');
					return false;
				} else {
					baseError('position', '');
					return true;
				}
			} else if ('nature' == vp) {
				var nature = $("#nature").val();
				if (nature.length == 0) {
					baseError('nature', '性质不能为空');
					return false;
				} else {
					baseError('nature', '');
					return true;
				}
			} else if ('area' == vp) {
				var area = $("#area").val();
				if (area.length == 0) {
					baseError('area', '建筑面积不能为空');
					return false;
				} else {
					baseError('area', '');
					return true;
				}
			} else if ('assessPrice' == vp) {
				var assessPrice = $("#assessPrice").val();
				if (assessPrice.length == 0) {
					baseError('assessPrice', '评估价格不能为空');
					return false;
				} else {
					baseError('assessPrice', '');
					return true;
				}
			} else if ('guaranteeRate' == vp) {
				var guaranteeRate = $("#guaranteeRate").val();
				if( '${status}'=='more'){
				if (guaranteeRate.length == 0) {
					baseError('guaranteeRate', '抵/质押率不能为空');
					return false;
				} else {
					baseError('guaranteeRate', '');
					return true;
				}
				}else {
					return true;
				}
			} else if ('overdueHandling' == vp) {
				var overdueHandling = $("#overdueHandling").val();
				if( '${status}'=='more'){
				if (overdueHandling.length == 0) {
					baseError('overdueHandling', '逾期处理方式不能为空');
					return false;
				} else {
					baseError('overdueHandling', '');
					return true;
				}
				}else {
					return true;
				}
			}else if ('overdueInfo' == vp) {
				var overdueInfo = $("#overdueInfo").val();
				if( '${status}'=='less'){
				if (overdueInfo.length == 0) {
					baseError('overdueInfo', '逾期处理方式不能为空');
					return false;
				} else {
					baseError('overdueInfo', '');
					return true;
				}
				}else {
					return true;
				
				}
			}else if ('reminderInfo' == vp) {
				var reminderInfo = $("#reminderInfo").val();
				if( '${status}'=='less'){
				if (reminderInfo.length == 0) {
					baseError('reminderInfo', '风控提示不能为空');
					return false;
				} else {
					baseError('reminderInfo', '');
					return true;
				}
				}else {
					return true;
				
				}
			}else if ('measuresInfo' == vp) {
				var measuresInfo = $("#measuresInfo").val();
				if( '${status}'=='less'){
				if (measuresInfo.length == 0) {
					baseError('measuresInfo', '风控措施不能为空');
					return false;
				} else {
					baseError('measuresInfo', '');
					return true;
				}
				}else {return true;
				
				}
			}

		}

		$(function() {
			var guaranteeType = '${house.guaranteeType}';
			if(guaranteeType != '' && guaranteeType.length > 0){
				$("#guaranteeType").val(guaranteeType);
			}
			
			var nature = '${house.nature}';
			if(nature != '' && nature.length > 0){
				$("#nature").val(nature);
			}
		});
	</script>
<!-- 	<link rel="stylesheet" -->
<!-- 		href="${pageContext.request.contextPath}/css/dropzone.css" /> -->
<!-- 	<link rel="stylesheet" -->
<!-- 		href="${pageContext.request.contextPath}/css/bootstrap-editable.css"> -->
<!-- 	<link type="text/css" -->
<!-- 		href="${pageContext.request.contextPath}/css/jquery-ui-1.8.17.custom.css" -->
<!-- 		rel="stylesheet" /> -->
<!-- 	<link type="text/css" -->
<!-- 		href="${pageContext.request.contextPath}/css/jquery-ui-timepicker-addon.css" -->
<!-- 		rel="stylesheet" /> -->

<!-- 	<script type="text/javascript" -->
<!-- 		src="${pageContext.request.contextPath}/js/jquery-ui-1.8.17.custom.min.js"></script> -->
<!-- 	<script type="text/javascript" -->
<!-- 		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script> -->
<!-- 	<script type="text/javascript" -->
<!-- 		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script> -->
</body>
</html>
