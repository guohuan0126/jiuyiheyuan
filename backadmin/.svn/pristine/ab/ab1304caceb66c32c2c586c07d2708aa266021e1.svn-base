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

<title>扣款记录</title>

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
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：扣款记录
				</h2>
			</div>

			


		<form class="form-inline" id="form1"  action="/agricultural/payment_debit" method="post">		    	 
	     <div class="contentpanel">
	     	<input type="text" id="name" name="name" value="${name}" class="form-control" style="width:212px;display:inline-block;" placeholder="借款项目编号"></input>
	      <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                 <div class="input-group" >
                 	<span style="font-size: 20px">状态：</span>
                 	<select id="select" name="status">
                 		<option value="">全部</option>
                 		<option value="1">成功</option>
                 		<option value="0">失败</option>
                 	</select>
                 </div>
                <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
	     </div>
	     
	          </form>
			<script type="text/javascript">	
			function toAdd(){
				window.location.href="/agricultural/toAddPaymentRecords";
			}
			  jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
					<div class="panel-body">

						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>扣款编号</th>
									<th>姓名</th>
									<th>手机号</th>
									<th>项目编号</th>
									<th>项目名称</th>
									<th>扣款时间</th>
									<th>扣款金额</th>
									<th>支付通道</th>
									<th>流水号</th>
									<th>扣款结果</th>
									<th>状态</th>
								</tr>
							</thead>
								<c:forEach var="paymentDebitRecords" items="${pageInfo.results}">
								<tr>
								   <td>${paymentDebitRecords.repayId}</td>
									<td>${paymentDebitRecords.loanerName}</td>
									<td>
									${base:decrypt(paymentDebitRecords.mobileNumber)}
									</td>	
									<td>${paymentDebitRecords.loanId}</td>	
									<td>${paymentDebitRecords.loanName}</td>	
									<td>
										<fmt:formatDate value="${paymentDebitRecords.requestTime}" type="both"/>
										</td>	
									<td>${paymentDebitRecords.money}</td>	
									<td>${paymentDebitRecords.paymentChannel}</td>	
									<td>${paymentDebitRecords.serialPay}</td>	
									<td>${paymentDebitRecords.msg}</td>	
									<td>
										<c:if test="${paymentDebitRecords.status==1}">成功</c:if>
										<c:if test="${paymentDebitRecords.status==0}">失败</c:if>
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


