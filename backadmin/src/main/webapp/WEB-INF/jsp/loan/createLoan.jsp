﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="" />
<link rel="shortcut icon" href="images/favicon.png" type="image/png" />
<title><c:if test="${empty loan.id}">创建借款项目</c:if> <c:if
		test="${not empty loan.id}">编辑借款项目</c:if></title>
</head>
<body>
	<!-- Preloader -->
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section> <%@include file="../base/leftMenu.jsp"%>
	<div class="mainpanel">
		<%@include file="../base/header.jsp"%>
		<div class="pageheader">
			<h2>
				<i class="fa fa-pencil"></i>
				<c:if test="${empty loan.id}">创建借款项目</c:if>
				<c:if test="${not empty loan.id}">编辑借款项目</c:if>
			</h2>
		</div>
		<div class="contentpanel">

			<!-- 创建借款表单 -->
			<form id="form1" method="post" class="form-horizontal">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="panel-btns"></div>
						<h4 class="panel-title">
							<c:if test="${empty loan.id}">创建借款项目--基本信息</c:if>
							<c:if test="${not empty loan.id}">编辑借款项目--基本信息</c:if>
						</h4>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">项目名称
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="name" id="name" width="10px"
									maxlength="20" class="form-control"
									onblur="verifyParam('name')" value="${loan.name}" /> <input
									type="hidden" name="loanId" id="loanId" value="${loan.id}" />
							</div>
						</div>
						<div class="form-group">
							<c:choose>
								<c:when test="${loanType eq null}">

									<label class="col-sm-3 control-label" style="color: red;">项目类型
										<span class="asterisk">*</span>
									</label>
									<div class="col-sm-3">
										<select name="loanType" id="loanType">
											<c:if test="${empty loan.id}">
												<option value="车贷">车贷</option>
												<option value="房贷">房贷</option>
												<option value="企业贷">企业贷</option>
												<option value="金农宝">金农宝</option>
												<option value="供应宝">供应宝</option>
											</c:if>
											<c:if test="${not empty loan.id}">
												<option value="${loan.loanType }">${loan.loanType }</option>
											</c:if>
										</select>
									</div>

								</c:when>
								<c:otherwise>

									<label class="col-sm-3 control-label" style="color: red;">项目类型
										<span class="asterisk">*</span>
									</label>
									<div class="col-sm-3">
										<select name="loanType" id="loanType">
											<option value="${loanType}">${loanType}</option>
										</select>
									</div>

								</c:otherwise>
							</c:choose>
							<span class="asterisk"> 项目已经创建，类型不可再更改</span>
						</div>
						<div class="form-group" id="companyNoDiv"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label">合同编号</label>
							<div class="col-sm-3">
								<input type="text" name="instructionNo" id="instructionNo"
									width="10px" class="form-control" value="${loan.instructionNo}" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">借款人
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="borrower" id="borrower"
									onblur="verifyParam('borrower')">
									<c:forEach items="${fbs}" var="fb">
										<c:if test="${fb.status eq 'open'}">
											<option id="${fb.userId}" value="${fb.userId}">${fb.userId}--${fb.fixedUser.realname}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">借款总金额
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="totalMoney" id="totalMoney"
									width="10px" class="form-control"
									onblur="verifyParam('totalMoney')"
									value="${loan.totalmoneyStr}"
									<c:if test="${loan.status=='还款中' or not empty vehicles and vehicles[0].money > 0}">readOnly='true'</c:if> />
							</div>
							<span class="asterisk">1.多车贷项目如果不输入总金额默认创建多车对多人项目，输入金额则创建多车对一人项目</br>2.还款中和多人对多车的车贷项目，不能修改总金额
							</span>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">是否新手项目
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="newbieEnjoy" id="newbieEnjoy"
									onchange="editChange()">
									<option value="否">否</option>
									<option value="是">是</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">投资起点金额
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="minInvestMoney" id="minInvestMoney">
									<option value="1" selected>1</option>
									<option value="100">100</option>
									<option value="1000">1000</option>								
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">单位递增金额
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="ascInvestMoney" id="ascInvestMoney"
									readonly="readonly" width="10px" class="form-control"
									value="${loan.increaseMoney}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">投资上限金额
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="maxInvestMoney" id="maxInvestMoney"
									width="10px" class="form-control" placeholder="0表示没限制"
									value="${loan.maxInvestMoney}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">自动投标上限金额
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="autoInvestMoneyTotal"
									id="autoInvestMoneyTotal" width="10px" class="form-control"
									onblur="verifyParam('autoInvestMoneyTotal')"
									value="${loan.autoInvestMoneyTotal}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">借款管理费
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="guranteeFee" id="guranteeFee"
									width="10px" class="form-control"
									onblur="verifyParam('guranteeFee')"
									value="${loan.loanGuranteeFee}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">计算方式
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="operationType" id="operationType"
									onchange="verifyParam('operationType')">
									<option value="">请选择</option>
									<option value="月">月</option>
									<option value="天">天</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">计息规则
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="interestRule" id="interestRule">
									<option value="投资次日计息">投资次日计息</option>
									<option value="放款次日计息">放款次日计息</option>
								</select>
							</div>
						</div>

						<div class="form-group" style="display: none" id="deadlineDIV">
							<label class="col-sm-3 control-label" style="color: red;">借款期限(月)
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="deadline" id="deadline">
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
						<div class="form-group" style="display: none" id="dayDIV">
							<label class="col-sm-3 control-label" style="color: red;">借款期限(天)
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="day" id="day" onchange="hiddenBeforeRepayDIV()">
									<option value="1">1天</option>
									<option value="2">2天</option>
									<option value="3">3天</option>
									<option value="4">4天</option>
									<option value="5">5天</option>
									<option value="6">6天</option>
									<option value="7">7天</option>
									<option value="8">8天</option>
									<option value="9">9天</option>
									<option value="10">10天</option>
									<option value="11">11天</option>
									<option value="12">12天</option>
									<option value="13">13天</option>
									<option value="14">14天</option>
									<option value="15">15天</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">是否机构专享
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="organizationExclusive" id="organizationExclusive">
									<c:if test="${empty loan.id}">
										<option value="否">否</option>
										<option value="是">是</option>
									</c:if>
									<c:if test="${not empty loan.id}">
										<option value="${loan.organizationExclusive }">${loan.organizationExclusive }</option>
									</c:if>
								</select>
							</div>
							<span class="asterisk">机构标已经创建不能再修改</span>
						</div>
						<div class="form-group" style="display: none"
							id="organizationExclusiveTime">
							<label class="col-sm-3 control-label" style="color: red;">还款时间
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" id="time"
									value="<fmt:formatDate
									value="${loan.repayTime}"
									pattern="yyyy-MM-dd HH:mm:ss" />"
									name="time"> </input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">还款方式
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="repayType" id="repayType">
									<c:if test="${not empty loan.repayType}">
										<option value="${loan.repayType}">${loan.repayType}</option>
									</c:if>
								</select>
							</div>
							<span class="asterisk">项目已经创建，还款方式不能再修改</span>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">借款利率(%)
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="rate" id="rate" width="10px"
									class="form-control" onblur="verifyParam('rate')"
									value="${loan.ratePercent}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">是否为预告项目<span
								class="asterisk">*</span></label>
							<div class="col-sm-3">
								<select name="dqgs" id="dqgs" onchange="verifyParam('dqgs')">
									<option value="">请选择</option>
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
						</div>

						<div class="form-group" style="display: none" id="expectTimeDIV">
							<label class="col-sm-3 control-label" style="color: red;">预计执行时间
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" id="expectTime" name="expectTime"
									class="ui_timepicker" onchange="verifyParam('expectTime')"
									alt="..." title="..."> </input>
							</div>
						</div>

						<base:active activeId="LAUNCH_LOAN">
							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">是否为测试项目
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<select name="testItem" id="testItem">
										<option value="是">是</option>
										<option value="否">否</option>
									</select>
								</div>
							</div>
						</base:active>
						<div class="form-group" id="chooseinvested" style="display: none" >
								<label class="col-sm-3 control-label" style="color: red;">是否可投资
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<select name="whetherInvested" id="whetherInvested">
									    <option value="0">否</option>
										<option value="1">是</option>										
									</select>
								</div>
							</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">奥运活动项目</label>
							<div class="col-sm-3">
								<select name="specialType" id="specialType">
									<option value="">请选择</option>
									<option value="olympic">奥运专属项目</option>
									<!-- <option value="activ_12">12点</option>
										<option value="activ_14">14点</option>
										<option value="activ_16">16点</option> -->
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
			<c:if test="${loan.id ne null}">
				<div>
					<a onclick="queryPics('${loan.id}')">查看</a>
				</div>
				<div class="col-sm-3">
					<form
						action="${pageContext.request.contextPath}/loan/uploadLoanData?loanId=${loan.id}"
						class="dropzone">
						<div class="fallback">
							<input name="file" type="file" />
						</div>
					</form>
				</div>
				<div id="pics">
					<table>
						<tbody>
							<tr>
								<td>借款合同:<a
									href="${pageContext.request.contextPath}${loan.contract}"
									target="_blank">${loan.contractName }</a>
								</td>
							</tr>
						</tbody>
					</table>
					<table>
						<thead>
							<tr>
								<th><input name="checkall" type="checkbox" id="checkall"  style="vertical-align:sub;"/>全选</th>
								<input type="button" id="updateBatchPicTitle" class="btn btn-primary"  style="display:inline-block;" value="保存"></input>&nbsp&nbsp<input style="width: 130px" type="text" id="title"
										value="" />
								<th width="20%">图片</th>
								<th width="20%">位置</th>
								<th width="20%">名称</th>
								<th width="20%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pics}" var="pic" varStatus="in">
								<tr>
									<td><input name="checkname" type="checkbox" value="${pic.id}" title="${pic.title}"/></td>
									<td><img
										src="http://drwebdemo.oss-cn-qingdao.aliyuncs.com${pic.picture}"
										width="220" height="140" alt="" /></td>
									<td><input style="width: 60px" type="text" id="${pic.id}"
										value="${pic.seqNum}"
										onchange="updateImageSeqNum('${pic.id}')" /></td>
									<td><input style="width: 130px" type="text" id="update_${pic.id}"
										value="${pic.title }" />&nbsp&nbsp<input type=button onclick="updateImageTitle('${pic.id}')" value="保存"></input></td>
									<td><a onclick="updateImage('del','${pic.id}')">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
	</div>
	</div>
	</section>

	<script type="text/javascript">
	var html = '<label class="col-sm-3 control-label">是否多车</label><div class="radio">'
		+'<span style="margin-left:30px;"><input type="radio" name="companyNo" <c:if test="${loan.companyno == 0 or empty loan.companyno}">checked="checked"</c:if> <c:if test="${not empty loan.id}">disabled="disabled"</c:if> value="0" />单车</span>'
		+'<span style="margin-left:30px;"><input type="radio" name="companyNo" <c:if test="${loan.companyno == 1}">checked="checked"</c:if> <c:if test="${not empty loan.id}">disabled="disabled"</c:if> value="1"/>多车</span><span class="asterisk" style="margin-left:225px;">项目创建成功后，车贷类型不可再修改, 默认单车</span></div>';
	
	
	$(function (){
		var organizationExclusive=$("#organizationExclusive").val();	
		if(organizationExclusive=="是"){
			$("#organizationExclusiveTime").attr("style", "display:block;");
		}
		var testItem=$("#testItem").val();
		if(testItem=="是"){
		$("#chooseinvested").attr("style", "display:block;");
		}
		
		$("#companyNoDiv").empty();
		if($("#loanType").val() == "车贷"){		
			$("#companyNoDiv").html(html);
		}
		var repayTypeItem = "";	
		if($("input[name=companyNo]").val() == 0){
			repayTypeItem = '<option value="按月付息到期还本">按月付息到期还本</option><option value="一次性到期还本付息">一次性到期还本付息</option><option value="等额本息">等额本息</option>';	
		}else{
			repayTypeItem = '<option value="按月付息到期还本">按月付息到期还本</option><option value="一次性到期还本付息">一次性到期还本付息</option>';
		}
		if('${loan.repayType}' == ''){
			$("#repayType").html(repayTypeItem);
		}
	
		
	});
	
	$("input[name=companyNo]").live("click",function(){
		var repayTypeItem = "";
		if($(this).val() == 0){
			repayTypeItem = '<option value="按月付息到期还本">按月付息到期还本</option><option value="一次性到期还本付息">一次性到期还本付息</option><option value="等额本息">等额本息</option>';
		}else{
			repayTypeItem = '<option value="按月付息到期还本">按月付息到期还本</option><option value="一次性到期还本付息">一次性到期还本付息</option>';
		}
		$("#repayType").html(repayTypeItem);
		
	});
	
	$("#organizationExclusive").change( function() {
		var organization=$("#organizationExclusive").val();
		if(organization=="否"){
			$("#organizationExclusiveTime").attr("style", "display:none;");
		}else {
			$("#organizationExclusiveTime").attr("style", "display:block;");
		}
	});
	$("#testItem").change(function(){
		var testItem=$("#testItem").val();		
		if(testItem=="否"){
		$("#chooseinvested").attr("style", "display:none;");
		$("#whetherInvested option[value='1'] ").attr("selected",true);			
		}else{
		$("#chooseinvested").attr("style", "display:block;");
		$("#whetherInvested option[value='0'] ").attr("selected",true);			
		}		
	});
	
	$("#loanType").change( function() {
		if($("#loanType").val() == "车贷"){
			$("#companyNoDiv").empty();
			$("#companyNoDiv").html(html);
		}else{
			$("#companyNoDiv").empty();
		}
		
		if($("#loanType").val() =="金农宝"){
			$("#totalMoney").val("0");
			$("#totalMoney").attr("readonly", true);	
			$("#autoInvestMoneyTotal").val("0");
			$("#autoInvestMoneyTotal").attr("readonly", true);
			$("#totalMoney").removeAttr("onblur");
			baseError('totalMoney', '金农宝项目借款金额在第二步填写');
			baseError('autoInvestMoneyTotal', '金农宝项目自动投标金额在第二步填写');
		}else{
			baseError('totalMoney', '');
			baseError('autoInvestMoneyTotal', '');
			$("#totalMoney").attr("readonly", false);	
			$("#autoInvestMoneyTotal").attr("readonly", false);	
		}
	});
	
	function hiddenBeforeRepayDIV(){
		var day = $("#day").val();
		if(day == 7){
			$("#beforeRepay").val('否');
			$("#symbol").val(null);
			$("#beforeRepayDIV").attr("style", "display:none;");
			$("#symbolDIV").attr("style", "display:none;");
		}else{
			$("#beforeRepay").val('是');
			$("#symbol").val(15);
			$("#beforeRepayDIV").attr("style", "display:block;");
			$("#symbolDIV").attr("style", "display:block;");
		}
	}
	function editChange(){		
		if($("#newbieEnjoy").val()=='是'){
			$("#maxInvestMoney").val(10000);
			$("#minInvestMoney").find("option[value='100']").attr("selected",true);
			 $("#ascInvestMoney").val('100');
		}else{
			$("#minInvestMoney").find("option[value='1']").attr("selected",true);
			$("#maxInvestMoney").val(0);
			$("#ascInvestMoney").val('1');
		}
	}
	function updateImageSeqNum(picId){
		baseError(picId, '');
		var seqNum = $("#"+picId).val();
		var mre = /^[1-9]+[0-9]*]*$/;
		if(mre.test(seqNum)){
			$.ajax({
	 			url : "<%=request.getContextPath()%>/loan/updateImageSeqNum",
	 			data : {
	 				   "picId" : picId,
	 				   "seqNum" : seqNum
	 	  	   	},
	 			async : true,
	 			success : function(data) {
	 				if('ok' == data){
	 					baseError(picId, '图片更改成功..'+seqNum);
	 				}
	 			}
	 		});
		}else{
			baseError(picId, '图片序号错误..'+seqNum);
		}
	}
	
	
	function updateImage(op,picId){
		$.ajax({
			url : "<%=request.getContextPath()%>/loan/updateImage",
			data : {
				   "op"  : op,
				   "picId" : picId,
				   "loanId" : $("#loanId").val(),
	  	   	},
			async : true,
			success : function(data) {
		  		$("#pics").html(data);
			}
		});
	}
	
	
	function queryPics(loanId){
		$.ajax({
			url : "<%=request.getContextPath()%>/loan/queryUploadLoanData",
			data : {
				   "loanId" : loanId,
	  	   	},
			async : true,
			success : function(data) {
		  		$("#pics").html(data);
			}
		});
	}
	
	
	function sb(){
		var beforeRepay = $("#beforeRepay").val();
		var operationType = $("#operationType").val();
		if(beforeRepay == '是' && operationType == '天'){
			$("#symbolDIV").attr("style", "display:block;");
			var symbol = '${loan.symbol}';
			var symbolIpt = $("#symbol").val();
			if(symbol != symbolIpt){
				$("#symbol").val(symbolIpt);
			}else{
				$("#symbol").val(15);
			}
		}else{
			$("#symbol").val('');
			$("#symbolDIV").attr("style", "display:none;");
			baseError('symbol', '');
		}
	}
	
	$(function() {
		var loanId = '${loan.id}';
		if(loanId.length == ''){
			$("#minInvestMoney").val('1');
			 $("#ascInvestMoney").val('1');
			$("#maxInvestMoney").val('0');
			$("#guranteeFee").val('0');
			$("#rate").val('9');
		}
		$("#minInvestMoney").change( function() {
			  var minInvestMoney=$("#minInvestMoney").val();
		  	  var ascInvestMoney= $("#ascInvestMoney").val();
			  $("#ascInvestMoney").val(minInvestMoney);
			
		});
		if(loanId.length != ''){
			var operationType = '${loan.operationType}';
			$("#operationType").val(operationType);
			var specialType = '${loan.specialType}';
			$("#specialType").val(specialType);
			
			var borrower = '${loan.borrowMoneyUserID}';
			$("#borrower").val(borrower);
			
			var repayType = '${loan.repayType}';
			$("#repayType").val(repayType);
			var newbieEnjoy = '${loan.newbieEnjoy}';
			$("#newbieEnjoy").val(newbieEnjoy);
			
			var interestRule = '${loan.interestRule}';
			$("#interestRule").val(interestRule);
			
			var investOriginMoney = '${loan.investOriginMoney}';
			$("#minInvestMoney").val(parseInt(investOriginMoney));
			
			var increaseMoney = '${loan.increaseMoney}';
			
			if('${loan.loanType}' == '金农宝'){
				$("#totalMoney").attr("readonly", true);	
			}
							
			if(operationType == '月'){
				var deadline = '${loan.deadline}';
				$("#deadlineDIV").attr("style", "display:block;");
				$("#deadline").val(deadline);
			}else if(operationType == '天'){
				var day = '${loan.day}';
				var beforeRepay = '${loan.beforeRepay}';
				$("#dayDIV").attr("style", "display:block;");
				$("#day").val(day);
				$("#beforeRepayDIV").attr("style", "display:block;");
				$("#beforeRepay").val(beforeRepay);
				if(beforeRepay == '是'){
					$("#symbolDIV").attr("style", "display:block;");
					var symbol = '${loan.symbol}';
					$("#symbol").val(symbol);
				}
			}
			
			
			var status = '${loan.status}';
			if(status == '贷前公告'){
				$("#dqgs").val('1');
				$("#expectTimeDIV").attr("style", "display:block;");
				var expectTime = '${loan.expectTime}';
				$("#expectTime").val(Todate(expectTime));
			}else{
				$("#dqgs").val('0');
			}
			
			var testItem = '${loan.textItem}';
			$("#testItem").val(testItem);
			if(testItem=="是"){
				$("#chooseinvested").attr("style", "display:block;");
			}else{
				$("#chooseinvested").attr("style", "display:none;");
			}			
			var whetherInvested = '${loan.whetherInvested}';
			$("#whetherInvested").val(whetherInvested);
			
			var yaCarAndGPS = '${loan.yaCarAndGPS}';
			$("#yaCarAndGPS").val(yaCarAndGPS);
			
			var remark = '${loan.remark}';
			$("#remark").val(remark);
			
		}
		
		$(".ui_timepicker").datetimepicker({
			showSecond : true,
			timeFormat : 'hh:mm:ss',
			stepHour : 1,
			stepMinute : 1,
			stepSecond : 1,
		});
		
		$("#time").datetimepicker({
			showSecond : true,
			timeFormat : 'hh:mm:ss',
			stepHour : 1,
			stepMinute : 1,
			stepSecond : 1,
		});
		
	});
	
	
		function sub(){		
			var num = subVerify();				
			if(num == 0){
				if(confirm('确认操作借款项目?')){
					var  organization=$("#organizationExclusive").val();
					if(organization=="是"){
						var time=$("#time").val();
						if(time==null || time == ''){
							alert("还款时间不能为空");
							return false;
						}
					}
					$.ajax({						
						type : "POST",
						dataType : 'json',
						url : "${request.contextPath()}/loan/createLoan",
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
							var dataObj = eval(data);				
							if (dataObj.status == 'ok') {
								location = "${ctx}/loan/toCreateLoanDetailView?param="+dataObj.error;
							} else if(dataObj.status == 'fail'){
								var errs = dataObj.error;
								if(errs == 'loginUserNULL'){
									location = "/";
								}else{
									var errs = errs.split(",");
									for ( var i = 0; i < errs.length; i++) {
										var v = errs[i].trim();
										if (v.length > 0) {
											if(v =='contract'){
												alert('借款合同不能为空,请上传');
											} else if(v == 'image'){
												alert('项目图片不能为空,请上传');
											} else{
												verifyParam(v);
											}
										}
									}
								}
							} else if(dataObj.status == 'modifyOk'){
								alert('项目基本信息编辑成功');			
								location.replace(document.referrer);

							} 
						}
					});
				}
			}else{
				alert('当前借款项目信息有误,请检查');
			}
		}
	
		function subVerify() {
			var num = 0;
		var newloantype=$("#loanType").val();
		var sv;
		if("金农宝" ==newloantype){
			sv = new Array("operationType",
					"borrower", "name",
					 "rate","guranteeFee","dqgs","expectTime");
			
		}else if(newloantype == "车贷" && $("input[name='companyNo']:checked").val() == "1"){
			sv = new Array("operationType",
				"borrower", "name",
				"autoInvestMoneyTotal", "rate","guranteeFee","dqgs","expectTime");						
		}else{
			sv = new Array("operationType",
				"borrower", "name", "totalMoney",
				"autoInvestMoneyTotal", "rate","guranteeFee","dqgs","expectTime");		
		}
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
			if ('name' == vp) {
				var name = $("#name").val();
				if (name.length == 0) {
					baseError('name', '项目名称不能为空');
					return false;
				} else {
					baseError('name', '');
					return true;
				}																	
			} else if ('borrower' == vp) {
				var borrower = $("#borrower").val();
				if (borrower.length == 0) {
					baseError('borrower', '借款人不能为空');
					return false;
				} else {
					var result = queryBorrower(borrower);
					baseError('borrower', result);
					return true;
				}
			} else if ('totalMoney' == vp) {
				baseError('totalMoney', '');
				var totalMoney = $("#totalMoney").val();
				totalMoney = parseFloat(totalMoney);		
				if (!exp.test(totalMoney) || totalMoney <= 0) {
					if(newloantype == "车贷" && $("input[name='companyNo']:checked").val() == "1"){
						return true;
					}else{
						baseError('totalMoney', '借款总金额不正确');
						return false;
					}		
				} else {
					verifyParam('autoInvestMoneyTotal');
					return true;
				}
			} else if ('autoInvestMoneyTotal' == vp) {
				var autoInvestMoneyTotal = $("#autoInvestMoneyTotal").val();
				var minInvestMoney = $("#minInvestMoney").val();
				var totalMoney = $("#totalMoney").val();
				autoInvestMoneyTotal = parseFloat(autoInvestMoneyTotal);
				totalMoney = parseFloat(totalMoney);
				minInvestMoney = parseFloat(minInvestMoney);
				var totalMoney = $("#totalMoney").val();
				totalMoney = parseFloat(totalMoney);	
				if(totalMoney <= 0){
					baseError('autoInvestMoneyTotal', '');
					return true;
				}
				if (!exp.test(autoInvestMoneyTotal) || autoInvestMoneyTotal < 0) {
					baseError('autoInvestMoneyTotal', '自动投标上限金额不正确');
					return false;
				} else if(autoInvestMoneyTotal > totalMoney){
					baseError('autoInvestMoneyTotal', '自动投标上限金额不能大于借款总金额');
					return false;
				} else if(autoInvestMoneyTotal != 0 && autoInvestMoneyTotal < minInvestMoney){
					baseError('autoInvestMoneyTotal', '自动投标上限金额不能小于最小投资金额');
					return false;
				} else {
					baseError('autoInvestMoneyTotal', '');
					return true;
				};
			} else if ('guranteeFee' == vp) {
				var guranteeFee = $("#guranteeFee").val();
				guranteeFee = parseFloat(guranteeFee);
				if (!exp.test(guranteeFee) || guranteeFee < 0) {
					baseError('guranteeFee', '借款管理费不正确');
					return false;
				} else {
					baseError('guranteeFee', '');
					return true;
				};
			} else if ('operationType' == vp) {
				var operationType = $("#operationType").val();
				if (operationType.length == 0) {
					$("#deadlineDIV").attr("style", "display:none;");//输入框隐藏
					$("#deadline").val(1);//值恢复默认
					$("#dayDIV").attr("style", "display:none;");//天输入框隐藏
					$("#day").val(30);//值恢复默认
					$("#beforeRepayDIV").attr("style", "display:none;");
					baseError('operationType', '计算方式不能为空');
					sb();
					return false;
				} else if(operationType == '月'){
					//显示借款期限
					$("#deadlineDIV").attr("style", "display:block;");
					//隐藏输入天数和是否可提前还款
					$("#dayDIV").attr("style", "display:none;");			
					baseError('operationType', '');
				} else if(operationType == '天'){
					//隐藏借款期限
					$("#deadlineDIV").attr("style", "display:none;");
					//显示输入天数
					$("#dayDIV").attr("style", "display:block;");
					$("#beforeRepayDIV").attr("style", "display:block;");
					baseError('operationType', '');
				} else {
					baseError('operationType', '');
				}
				sb();
				return true;
			} else if ('rate' == vp) {
				var rate = $("#rate").val();
				if (!exp.test(rate) || rate <= 0) {
					baseError('rate', '借款利率不正确');
					return false;
				} else {
					baseError('rate', '');
					return true;
				};
			} else if('dqgs' == vp){
				var dqgs = $("#dqgs").val();
				if(dqgs.length == 0){
					$("#expectTimeDIV").attr("style", "display:none;");
					baseError('dqgs', '是否为预告项目不能为空');
					return false;
				}else{
					baseError('dqgs', '');
					if('1' == dqgs){
						$("#expectTimeDIV").attr("style", "display:block;");
					}else{
						$("#expectTimeDIV").attr("style", "display:none;");
					}
					return true;
				};
			} else if('expectTime' == vp){
				var dqgs = $("#dqgs").val();
				if('1' == dqgs){
					var nowTime = new Date();
					var expectTime = $("#expectTime").val();
					if(expectTime == '' || expectTime.length <= 0){
						var defaultTime = new Date(nowTime.getTime()+86400000);
						$("#expectTime").val(formatDate(defaultTime));
						return confirm("预告项目,预计执行时间不能为空,默认时间为:"+formatDate(defaultTime));
					}else{
						expectTime = expectTime.replace(/-/ig,'/');
						var expectTimeD = new Date(expectTime);
						if(expectTimeD.getTime() < nowTime.getTime()){
							baseError('expectTime', '预告标情况下,预计执行时间不能小于当前时间');
							return false;
						}else{
							baseError('expectTime', '');
						};
					}
				}
				return true;
			} 
		}
		
		//查询借款人账户
		function queryBorrower(borrower){
			var result;
				$.ajax({
					type : "POST",
					cache: false,
					url : "${ctx}/loan/queryBorrower",
				data : {
					'borrower' : borrower,
				},
				dataType : "text",
				async : false,
				success : function(resp) {
					result = resp;
				}
			});
			return result;
		}

		var formatDate = function(date) {
			var y = date.getFullYear(); //年 
			var m = date.getMonth() + 1; //月 
			m = m < 10 ? '0' + m : m;
			var d = date.getDate(); //日
			d = d < 10 ? ('0' + d) : d;
			var h = date.getHours(); //时
			h = h < 10 ? ('0' + h) : h;
			var minute = date.getMinutes(); //分
			minute = minute < 10 ? ('0' + minute) : minute;
			return y + '-' + m + '-' + d + ' ' + h + ':' + minute;
		};

		function Todate(num) {
			num = num + "";
			var date = "";
			var month = new Array();
			month["Jan"] = '01';
			month["Feb"] = '02';
			month["Mar"] = '03';
			month["Apr"] = '04';
			month["May"] = '05';
			month["Jun"] = '06';
			month["Jul"] = '07';
			month["Aug"] = '08';
			month["Sep"] = '09';
			month["Oct"] = 10;
			month["Nov"] = 11;
			month["Dec"] = 12;
			var week = new Array();
			week["Mon"] = "一";
			week["Tue"] = "二";
			week["Wed"] = "三";
			week["Thu"] = "四";
			week["Fri"] = "五";
			week["Sat"] = "六";
			week["Sun"] = "日";
			str = num.split(" ");
			date = str[5] + "-";
			date = date + month[str[1]] + "-" + str[2] + " " + str[3];
			return date;
		}
	function updateImageTitle(picId){
		var picTitle=$("#update_"+picId).val();
		$.ajax({
			type : "post",
			url : "${ctx}/loan/updateImageTitle",
			data : {
				"id" : picId,
				"title":picTitle,
				"loanId" : $("#loanId").val()
			},
			dataType : "text",
			success : function(data) {
				if(data=="ok"){
					alert("修改成功");
				}else{
					alert("修改失败");
				}
			
			}
		});
	}
	$(function(){
		$("#checkall").click(function(){ 
			if(this.checked){ 
				$("input:checkbox[name=checkname]").attr("checked", true);
			}else{ 
				$("input:checkbox[name=checkname]").attr("checked", false);
			} 
		});
		function checkLoan(){
			var arr = new Array();
			$("input:checkbox[name=checkname]:checked").each(function(i){						
				arr.push($(this).val());
			}); 
				return arr;
		}
		function updateBatchPicTitle(){						
			var arr=checkLoan();
			//alert(arr);
			var title=$("#title").val();
			var loadId=$("#loanId").val();
			//alert(loadId);
			//alert(title);
			if(arr.length==0){
				alert("请勾选要保存的圖片");
				return false;
			}
			if(confirm("确定把这些图片修改为【"+title+"】吗?")){			
				$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/loan/updateBatchPicTitle",						
					data: {"ids":checkLoan(),"title":title,"loadId":loadId},
					success:function(data) {
						if(data=='success'){
							alert("保存成功");
						}else{
							alert("保存失败");
						}
					}
				});	
			} 			 
		}
		$("#updateBatchPicTitle").click(function(){ 
			updateBatchPicTitle();
		});
	});
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