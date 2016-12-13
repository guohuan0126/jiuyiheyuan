<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="${ctx}/images/favicon.png"
	type="image/png">

<title>提前还款项目管理</title>

<style>
.parent{position:relative;}
.son{position:absolute;bottom:-58px;left:-86px;width:180px;background:#f7f7f7;border:1px solid #ccc;border-radius:3px;display:none;}
</style>
<script type="text/javascript">
	function getReason1(rule){
		$('#rule').dialogBox({
			width: 500,
			height: 300,
			title: '备注信息',
			hasClose: true,
			content: rule
		});
	}
	
</script>
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
		<script src="/js/jquery.dialogBox.js"></script>
	<link rel="stylesheet" href="/css/jquery.dialogbox.css" />
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：提前还款项目管理
				</h2>
			</div>
		    	 
	     <div class="contentpanel">
	    <form class="form-inline" id="form1"  action="/agricultural/timelyloansPrepayment" method="post">	
	     	<input type="text" id="name" name="name" value="${id}" class="form-control" style="width:212px;display:inline-block;" placeholder="合同编号"></input>
	      <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>         
               <div class="input-group" >
					<span style="font-size: 20px">是否已经匹配还款计划：</span>
             	<select id="flag" name="flag">
             		<option value="">全部</option>
             		<option value="1">是</option>
             		<option value="0">否</option>
             	</select>
             </div>
                <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input><br>         
                <c:if test="${uploadExcelId !=null && uploadExcelId !=''}">
               <input type="button" id="updateStatus" class="btn btn-primary"   style="display:inline-block;" value="批量匹配还款计划状态"></input>
	            </c:if>
	     
	          </form>
			<script type="text/javascript">	
			$("#flag").val('${flag}');	
			jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});
			  $("#updateStatus").click(function(){
	    		    var uploadExcelId = '${uploadExcelId}';
	    		    if(confirm('确认批量匹配还款计划吗?')){						
						$.ajax({
							type : 'POST',
							dataType:'text',
							url : "/ruralfinance/updateTimelyRepaymentplan",	
							data: {"uploadExcelId":uploadExcelId},
							success:function(data) {
								if(data=='success'){
									alert("批量匹配还款计划成功");
									window.location.href="/ruralfinance/repaymentplanList";
								}else if(data=='HaveMatching'){
									alert("该批数据已经匹配过");									
								}else if(data=='dateError'){
									alert("提前还款数据有错误，请检查提前结束日期");									
								}else{
									alert("批量匹配还款计划失败");
								}
							}
						});	
					}
	    		});
   
			</script>
					<div class="panel-body">
					<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>合同编号</th>
									<th>客户姓名</th>
									<th>
									证件类型</br>
									身份证号</th>
									<th>手机号</th>
									<th>借款金额(合同金额)</th>
									<th>打款金额</th>
									<th>服务费用</th>
									<th>借款期限(月)</th>
									<th>客户类型</th>
									<th>还款方式</th>
									<th>银行卡号</th>
									<th>所属银行</th>
									<th>支行名称</th>
									<th>省份</br>
									城市</th>
									<th>核算公司</th>
									<th>放款时间</th>																																		
									<th>是否提前还款</th>
									<th>实际借款期限</th>
									<th>实际结束日期</th>	
									<th>是否还清</th>								
									<th>匹配状态</th>								
															
								</tr>
							</thead>
								<c:forEach var="prepayment" items="${pageInfo.results}" varStatus="idx">
								<tr>
								   <td>${prepayment.contractId }</td>
								    <td>${prepayment.name }</td>
								    <td><c:if test="${prepayment.typeOfId=='1'}">身份证</c:if>
								   	 <c:if test="${prepayment.typeOfId=='0'}">护照</c:if><br>
								   	  ${prepayment.idCard}</td>
								    <td>${prepayment.mobileNumber}</td>
								    <td>${prepayment.money}</td>									    							   
								    <td>${prepayment.loanMoney}</td>
								     <td>${prepayment.serviceMoney}</td>
								    <td>${prepayment.loanTerm}月 </td>
								    <td><c:if test="${prepayment.customerType=='0'}">个人</c:if>
								   	 <c:if test="${prepayment.customerType=='1'}">企业</c:if></td>
								    <td>${prepayment.repayType}</td>
								    <td>${prepayment.bankcard}</td>
								    <td>${prepayment.bank}</td>
								    <td>${prepayment.branchname}</td>
								    <td>${prepayment.province}</br>
								    ${prepayment.city}</td>
								    <td><c:if test="${prepayment.accountingDepartment==1}">山水</c:if>
										<c:if test="${prepayment.accountingDepartment==2}">久亿</c:if></td>
								    <td> <fmt:formatDate value="${prepayment.giveMoneyTime}" type="both" pattern="yyyy-MM-dd"/></td>							     
								   <td>${prepayment.whetherEarlyRepayment}</td>
								   <td>${prepayment.actualLoanTerm}月</td>
								   <td><fmt:formatDate value="${prepayment.actualEndTime}" type="both" pattern="yyyy-MM-dd"/></td>
								    <td>
								   <td>${prepayment.whetherSettlement}</td>
								   <td>	 <c:if test="${prepayment.flag==0}">否</c:if>
								   	 <c:if test="${prepayment.flag==1}">是</c:if>
								   	 </td>							  
								</tr>
							</c:forEach>	
						</table>
					</div>
				
					<%@ include file="../base/page.jsp"%>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>
</body>
</html>
