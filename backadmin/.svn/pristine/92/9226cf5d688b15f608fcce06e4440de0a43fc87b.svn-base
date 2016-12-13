<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
	<meta name="viewport"
		content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<meta name="description" content="">
			<meta name="author" content="">
				<link rel="shortcut icon" href="images/favicon.png" type="image/png">


					<title>借款项目审核</title>
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
				<i class="fa fa-pencil"></i> 审核借款项目
			</h2>
		</div>
		<div class="contentpanel">

			<!-- 创建借款表单 -->
			<form id="form1" method="post" class="form-horizontal">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="panel-btns"></div>
						<h4 class="panel-title">审核借款项目</h4>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">项目名称
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="name" id="name" width="10px"
									readonly="readonly" class="form-control" value="${loan.name}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">借款人
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="borrower" id="borrower" width="10px"
									readonly="readonly" class="form-control"
									value="${loan.borrowMoneyUserID}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">真实姓名
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="borrower" id="borrower" width="10px"
									readonly="readonly" class="form-control"
									value="${loan.borrowMoneyUserName}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">投资起点金额
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="minInvestMoney" id="minInvestMoney"
									width="10px" class="form-control" readonly="readonly"
									value="${loan.investOriginMoney}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">单位递增金额
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="ascInvestMoney" id="ascInvestMoney"
									width="10px" class="form-control" readonly="readonly"
									value="${loan.increaseMoney}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">投资上限金额
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="maxInvestMoney" id="maxInvestMoney"
									readonly="readonly" width="10px" class="form-control"
									placeholder="0表示没限制" value="${loan.maxInvestMoney}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">自动投标上限金额
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="autoInvestMoneyTotal"
									readonly="readonly" id="autoInvestMoneyTotal" width="10px"
									class="form-control" value="${loan.autoInvestMoneyTotal}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">借款管理费
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="guranteeFee" id="guranteeFee"
									width="10px" class="form-control" readonly="readonly"
									value="${loan.loanGuranteeFee}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">计算方式
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="operationType" id="operationType">
									<option value=${loan.operationType }>${loan.operationType
										}</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">计息规则
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="operationType" id="operationType">
									<option value=${loan.interestRule }>${loan.interestRule
										}</option>
								</select>
							</div>
						</div>

						<div class="form-group" style="display: none" id="deadlineDIV">
							<label class="col-sm-3 control-label" style="color: red;">借款期限(月)
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="deadline" id="deadline">
									<option value="${loan.deadline }">${loan.deadline }个月</option>
								</select>
							</div>
						</div>

						<div class="form-group" style="display: none" id="dayDIV">
							<label class="col-sm-3 control-label" style="color: red;">借款期限(天)
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="day" id="day">
									<option value="${loan.day }">${loan.day }天</option>
								</select>
							</div>
						</div>
						<div class="form-group" style="display: none" id="beforeRepayDIV">
							<label class="col-sm-3 control-label" style="color: red;">是否可提前还款
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="beforeRepay" id="beforeRepay">
									<option value="${loan.beforeRepay }">${loan.beforeRepay
										}</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">是否为新手专享
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="newbieEnjoy" id="newbieEnjoy">
									<option value=${loan.newbieEnjoy }>${loan.newbieEnjoy
										}</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">还款方式
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="repayType" id="repayType">
									<option value="${loan. repayType}">${loan. repayType}</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">借款利率(%)
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" name="rate" id="rate" width="10px"
									readonly="readonly" class="form-control"
									value="${loan.ratePercent}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">是否为预告项目<span
								class="asterisk">*</span></label>
							<div class="col-sm-3">
								<select name="dqgs" id="dqgs">
									<option value="${loan.status}">${loan.status}</option>
								</select>
							</div>
						</div>

						<div class="form-group" style="display: none" id="expectTimeDIV">
							<label class="col-sm-3 control-label" style="color: red;">预计执行时间
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<input type="text" id="expectTime" name="expectTime"
									readonly="readonly" class="ui_timepicker">
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">是否为测试项目
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="testItem" id="testItem">
									<option value="${loan.textItem }">${loan.textItem }</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" style="color: red;">项目类型
								<span class="asterisk">*</span>
							</label>
							<div class="col-sm-3">
								<select name="loanType" id="loanType">
									<option value="${loan.loanType}">${loan.loanType}</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">**************</label>
							<div class="col-sm-3">
								**************华丽的分割线****************************</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">项目地点</label>
							<div class="col-sm-3">
								<input type="text" name="itemAddress" id="itemAddress"
									readonly="readonly" width="10px" class="form-control"
									value="${loan.itemAddress }" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">项目月息(%)</label>
							<div class="col-sm-3">
								<input type="text" name="itemRate" id="itemRate" width="10px"
									readonly="readonly" class="form-control"
									value="${loan.itemRate }" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">客户经理工号</label>
							<div class="col-sm-3">
								<input type="text" name="customerManagerJobNumber"
									id="customerManagerJobNumber" width="10px" readonly="readonly"
									class="form-control" value="${loan.customerManagerJobNumber }" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">客户经理姓名</label>
							<div class="col-sm-3">
								<input type="text" name="customerManagerName"
									readonly="readonly" id="customerManagerName" width="10px"
									class="form-control" value="${loan.customerManagerName }" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">借款人姓名</label>
							<div class="col-sm-3">
								<input type="text" name="borrowerName" id="borrowerName"
									readonly="readonly" width="10px" class="form-control"
									value="${loan.borrowerName }" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">新增/展期 </label>
							<div class="col-sm-3">
								<select name="remark" id="remark">
									<option value="${loan.remark}">${loan.remark}</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">押车/GPS</label>
							<div class="col-sm-3">
								<select name="yaCarAndGPS" id="yaCarAndGPS">
									<option value="${loan.yaCarAndGPS}">${loan.yaCarAndGPS}</option>										
								</select>
							</div>
						</div>						
						<div class="form-group">
							<label class="col-sm-3 control-label">借款总金额</label>
							<div class="col-sm-3">
								<input type="text" readonly="readonly" width="10px"
									style="color:#de1f24;font-size:20px;" class="form-control"
									value="${loan.totalmoney}" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">尚未募集的金额</label>
							<div class="col-sm-3">
								<input type="text" readonly="readonly" width="10px"
									style="color:#de1f24;font-size:20px;" class="form-control"
									value="${remainInvesteMoney}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">已募集的金额</label>
							<div class="col-sm-3">
								<input type="text" readonly="readonly" width="10px"
									style="color:#de1f24;font-size:20px;" class="form-control"
									value="${alreadyInvestedMoney}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">是否超募</label>
							<div class="col-sm-3">
								<c:if test="${loan.totalmoney - alreadyInvestedMoney < 0}">
									<div style="color:#de1f24;font-size:20px;">超募</div>
								</c:if>
								<c:if test="${loan.totalmoney - alreadyInvestedMoney >= 0}">
									<div style="color:#de1f24;font-size:20px;">正常</div>
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">操作选择</label>
							<div class="col-sm-3">
								<select name="actionType" id="actionType"
									onchange="selectAction()">
									<option value="">请选择</option>
									<%-- <c:if test="${loan.status eq '等待复核'}">									
										<option value="正常放款">正常放款</option>					
									</c:if> --%>
									<c:if test="${loan.status eq '等待复核' or loan.status eq '筹款中'}">
										<base:active activeId="LOAN_CANCEL">
											<option value="流标">流标</option>
										</base:active>
									</c:if>
									<base:active activeId="LOAN_LOCAL_REPAY">
										<c:if test="${loan.status eq '还款中'}">
											<option value="正常还款">正常还款</option>
											<%-- <c:if test="${loan.repayType == '等额本息'}"> --%><option value="提前还款">提前还款</option><%-- </c:if> --%>
										</c:if>
									</base:active>
								</select>
							</div>
						</div>

						<div class="form-group" style="display: none;" id="actionTimeDIV">
							<label class="col-sm-3 control-label">请选择时间</label>
							<div class="col-sm-3">
								<input type="text" id="actionTime" name="actionTime"
									class="ui_timepicker">
							</div>
						</div>

						<div class="form-group" style="display: none;"
							id="actionMessageDIV">
							<label class="col-sm-3 control-label">是否发送站内信和短信</label>
							<div class="col-sm-3">
								<select name="actionMessage" id="actionMessage">
									<option value="Y">是</option>
									<option value="N">否</option>
								</select>
							</div>
						</div>

						<div class="form-group" style="display: none;" id="repayPeriodDIV">
							<label class="col-sm-3 control-label">请选择还款期数</label>
							<div class="col-sm-3">
								<select name="repayId" id="repayId">
									<c:forEach items="${repays}" var="repay">
										<option value="${repay.id}">期数:${repay.period}-状态:${repay.status}-本金:${repay.corpus}-利息:${repay.interest}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group" style="display: none;" id="repayPeriodBeforeDIV">
							<label class="col-sm-3 control-label">还款明细</label>
							<div class="col-sm-3">
								<c:if test="${not empty repays}">
									<c:set var="r" value="${repays[0]}"/>
									<c:set var="interest" value="0"/>
									<c:set var="corpus" value="0"/>
									<c:forEach items="${repays}" var="repay">									
										<c:set var="corpus" value="${corpus + repay.corpus}"/>
									</c:forEach>
									<div style="color:red;">
									<span>本次还款ID:${r.id}</span></br>
									<span>本次还款期数:${r.period}</span><span style="margin-left:4px">本次还款利息:${r.interest}元</span><span style="margin-left:4px">本次还款本金:${corpus}元</span>
									</div>
								</c:if>
									
								
							</div>
						
						</div>

						<div class="form-group" id="button" style="display: none;">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="performAction('${loan.id}')">执行操作</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<table>
				<thead>
					<th width="10%">投资id</th>
					<th width="10%">投资人</th>
					<th width="8%">投资金额</th>
					<th width="8%">投资状态</th>
					<th width="16%">投资时间</th>
				</thead>
				<tbody>
					<c:forEach var="invest" items="${invests}">
						<tr>
							<td>${invest.id}</td>
							<td>${invest.investUserID}</td>
							<td><fmt:formatNumber value="${invest.money}"
									type="currency" /></td>
							<td>${invest.status}</td>
							<td><fmt:formatDate value="${invest.time}" type="both" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</section>

	<script type="text/javascript">
		
		function selectAction(){
			var actionType = $("#actionType").val();
			if(actionType != '' && actionType.length > 0){
				if('本地放款' == actionType || '正常还款' == actionType || '提前还款' == actionType){
					$("#actionTimeDIV").attr("style", "display:block;");
					$("#actionMessageDIV").attr("style", "display:block;");
					if('正常还款' == actionType){
						$("#repayPeriodDIV").attr("style", "display:block;");
						$("#repayPeriodBeforeDIV").attr("style", "display:none;");
					}else if('提前还款' == actionType){
						$("#repayPeriodDIV").attr("style", "display:none;");
						$("#repayPeriodBeforeDIV").attr("style", "display:block;");
						
					}else{
					$("#repayPeriodBeforeDIV").attr("style", "display:none;");
						$("#repayPeriodDIV").attr("style", "display:none;");
					}
				}else{
					$("#actionTimeDIV").attr("style", "display:none;");
					$("#actionMessageDIV").attr("style", "display:none;");
					$("#repayPeriodDIV").attr("style", "display:none;");
					$("#repayPeriodBeforeDIV").attr("style", "display:none;");
				}
				$("#button").attr("style", "display:block;");
			}else{
				$("#actionTimeDIV").attr("style", "display:none;");
				$("#actionMessageDIV").attr("style", "display:none;");
				$("#button").attr("style", "display:none;");
				$("#repayPeriodDIV").attr("style", "display:none;");
				$("#repayPeriodBeforeDIV").attr("style", "display:none;");
				alert('请选择操作方式');
				
			}
		}
	
	
		function performAction(loanId){
			if(loanId == '' || loanId.length != 18){
				alert('借款项目id不正确!');
			}else{
				var actionType = $("#actionType").val();
				if(actionType != '' && actionType.length > 0){
					if('正常放款' == actionType){
						giveMoneyToBorrower(loanId);
					}else if('本地放款' == actionType){
						giveMoneyToBorrowerByLocal(loanId);
					}else if('流标' == actionType){
						cancel(loanId);
					}else if('正常还款' == actionType){
						repayMoneyLocal(loanId, $("#repayId").val(), '否');
					}else if('提前还款' == actionType){
						repayMoneyLocal(loanId, $("#repayId").val(), '是');
					}
				}else{
					alert('请选择操作方式');
				}
			}
		}
	                                                                                                      
		function repayMoneyLocal(loanId,repayId, befoRepay){
			var actionTime = $("#actionTime").val();
			var actionMessage = $("#actionMessage").val();
			if(actionTime == '' || repayId == '' || repayId.length == 0){
				alert('时间或期数不正确!');
			}else{
				if(window.confirm('确认执行本地还款操作吗?')){
					$.ajax({
						url : "<%=request.getContextPath()%>/loan/repayMoneyLocal",
							data : {
								"loanId" : loanId,
								"actionTime" : actionTime,
								"actionMessage" : actionMessage,
								"repayId" : repayId,
								"befoRepay":befoRepay
							},
							async : false,
							beforeSend:function(){
								xval=getBusyOverlay('viewport',
								{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
								{color:'blue', size:25, type:'o'});	
							},
							success : function(data) {
								alert(data);
								window.clearInterval(xval.ntime); 
								xval.remove();
								if('本地还款成功' == data){
									location = "/loan/loanList";
								}
							}
					});
				}
			}
		}
		
		function giveMoneyToBorrowerByLocal(loanId){
			var actionTime = $("#actionTime").val();
			var actionMessage = $("#actionMessage").val();
			if(actionTime == ''){
				alert('请选择时间');
			}else{
				if(window.confirm('确认执行本地放款操作吗?')){
					$.ajax({
						url : "<%=request.getContextPath()%>/loan/giveMoneyToBorrowerByLocal",
							data : {
								"loanId" : loanId,
								"actionTime" : actionTime,
								"actionMessage" : actionMessage,
							},
							async : false,
							beforeSend:function(){
								xval=getBusyOverlay('viewport',
								{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
								{color:'blue', size:25, type:'o'});	
							},
							success : function(data) {
								alert(data);
								window.clearInterval(xval.ntime); 
								xval.remove();
								if('本地放款成功' == data){
									location = "/loan/loanList";
								}
							}
					});
				}
			}
		}
	
		function giveMoneyToBorrower(loanId){
			if(window.confirm('确认执行放款操作吗?')){
				$.ajax({
					url : "<%=request.getContextPath()%>/loan/giveMoneyToBorrower",
						data : {
							"loanId" : loanId,
						},
						async : false,
						beforeSend:function(){
							xval=getBusyOverlay('viewport',
							{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
							{color:'blue', size:25, type:'o'});	
						},
						success : function(data) {
							alert(data);
							window.clearInterval(xval.ntime); 
							xval.remove();
							if('放款成功' == data){
								location = "/loan/loanList";
							}
						}
				});
			}
		}
		
		function cancel(loanId){
			if(window.confirm('确认执行流标操作吗?流标操作风险较大，请谨慎操作')){
				 $.ajax({
					url : "<%=request.getContextPath()%> /loan/cancel",
							data : {
								"loanId" : loanId,
							},
							async : false,
							beforeSend : function() {
								xval = getBusyOverlay(
										'viewport',
										{
											color : 'black',
											opacity : 0.3,
											text : '正在处理，请稍后...',
											style : 'text-shadow: 0 0 3px black;font-size:18px;'
										}, {
											color : 'blue',
											size : 25,
											type : 'o'
										});
							},
							success : function(data) {
								alert(data);
								window.clearInterval(xval.ntime);
								xval.remove();
								if ('流标成功' == data) {
									location = "/loan/loanList";
								}
							}
						});
			}
		}

		$(function() {

			var loanId = '${loan.id}';

			if (loanId.length != '') {
				var operationType = '${loan.operationType}';
				$("#operationType").val(operationType);
				if (operationType == '月') {
					var deadline = '${loan.deadline}';
					$("#deadlineDIV").attr("style", "display:block;");
					$("#deadline").val(deadline);
				} else if (operationType == '天') {
					var day = '${loan.day}';
					var symbol = '${loan.symbol}';
					var beforeRepay = '${loan.beforeRepay}';
					$("#dayDIV").attr("style", "display:block;");
					$("#symbolDIV").attr("style", "display:block;");
					$("#day").val(day);
					$("#symbol").val(symbol);
					$("#beforeRepay").val(beforeRepay);
				}
				var repayType = '${loan.repayType}';
				$("#repayType").val(repayType);

				var status = '${loan.status}';
				if (status == '贷前公告') {
					$("#dqgs").val('1');
					$("#expectTimeDIV").attr("style", "display:block;");
					var expectTime = '${loan.expectTime}';
					$("#expectTime").val(Todate(expectTime));
				} else {
					$("#dqgs").val(status);
				}

				var testItem = '${loan.textItem}';
				$("#testItem").val(testItem);

				var loanType = '${loan.loanType}';
				$("#loanType").val(loanType);

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
		});
		//显示或隐藏错误信息
		function baseError(id, message) {
			$("#" + id + "Error p").remove();
			$str = "<span style=\"font-size:14px;color:red\" id=\""+id+"Error\"><p>"
					+ message + "</p></span>";
			$("#" + id).after($str);
		}

		function Todate(num) {
			//Fri Oct 31 18:00:00 UTC+0800 2008  
			//Thu Mar 05 14:40:00 CST 2015
			num = num + "";
			var date = "";
			var month = new Array();
			month["Jan"] = '01';
			month["Feb"] = '02';
			month["Mar"] = '03';
			month["Apr"] = '04';
			month["May"] = '05';
			month["Jan"] = '06';
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
			//date=date+" 周"+week[str[0]];
			return date;
		}
	</script>

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
