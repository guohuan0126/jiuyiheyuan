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

<title>还款记录列表</title>

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
					<i class="fa fa-table"></i>当前位置：还款记录列表
				</h2>
			</div>

			


		<form class="form-inline" id="form1"  action="/agricultural/repaymentplan" method="post">		    	 
	     <div class="contentpanel">
	     	<input type="text" id="name" name="name" value="${name}" class="form-control" style="width:212px;display:inline-block;" placeholder="借款项目编号"></input>
	      <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="计息开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="计息结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                 <div class="input-group" >
                 	<span style="font-size: 20px">状态：</span>
                 	<select id="select" name="status">
                 		<option value="">全部</option>
                 		<option value="unrepay">未还</option>
                 		<option value="repaying">未还清</option>
                 		<option value="finish">已还清</option>
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
									<th>还款人编号</th>
									<th>还款人姓名</th>
									<th>还款编号</th>
									<th>项目编号</th>
									<th>还款日期（应还日期）</th>
									<th>最后一次扣款时间（实际还款日期）</th>
									<th>每月应还金额</th>
									<th>每月实际还金额</th>
									<th>应还本金</th>
									<th>应还利息</th>
									<th>违约金</th>
									<th>逾期罚息</th>
									<th>期数（第几期还款）</th>
								    <th>状态</th>
									<th>计息开始时间</th>
								</tr>
							</thead>
								<c:forEach var="ruralfinanceRepaymentplan" items="${pageInfo.results}">
								<tr>
								   <td>${ruralfinanceRepaymentplan.loanerId}</td>
									<td>${ruralfinanceRepaymentplan.loanerName}</td>
									<td>${ruralfinanceRepaymentplan.id}</td>	
									<td>${ruralfinanceRepaymentplan.loandataId}</td>	
									<td>
									<fmt:formatDate value="${ruralfinanceRepaymentplan.repayDate}" type="both"/>
									</td>	
									<td>${ruralfinanceRepaymentplan.lastOperationTime}</td>	
									<td>${ruralfinanceRepaymentplan.monthMoney}</td>	
									<td>${ruralfinanceRepaymentplan.realrepayMoney}</td>	
									<td>${ruralfinanceRepaymentplan.corpus}</td>	
									<td>${ruralfinanceRepaymentplan.intereat}</td>
									<td>${ruralfinanceRepaymentplan.liquidated}</td>
									<td>${ruralfinanceRepaymentplan.latePenalty}</td>
									<td>${ruralfinanceRepaymentplan.period}</td>
									<td>
									<c:if test="${ruralfinanceRepaymentplan.repayStatus=='unrepay'}">未还</c:if>
									<c:if test="${ruralfinanceRepaymentplan.repayStatus=='repaying'}">未还清</c:if>
									<c:if test="${ruralfinanceRepaymentplan.repayStatus=='finish'}">还清</c:if>
									</td>	
									<td>
									<fmt:formatDate value="${ruralfinanceRepaymentplan.interetime}" type="both"/>
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


