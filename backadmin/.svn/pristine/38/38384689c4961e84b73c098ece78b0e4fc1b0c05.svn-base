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

<title>子标列表</title>
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
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：子标列表
				</h2>
			</div>

			


		<form class="form-inline" id="form1"  action="/ruralfinance/childLoansList" method="post">		    	 
	     <div class="contentpanel">
	       <input type="text"  name="loanId" value="${loanId}" class="form-control" placeholder="项目编号" id="loanId">
           <input type="text"  name="contractId" value="${contractId}" class="form-control" placeholder="总合同编号" id="contractId">
	      <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
               <div class="input-group" >
  			<span style="font-size: 20px">是否打包：</span>
                 	<select id="forkStatus" name="forkStatus">
                 		<option value="">全部</option>
                 		<option value="1">是</option>
                 		<option value="0">否</option>
                 	</select>
                 </div>
             <div class="input-group" >
  			<span style="font-size: 20px">子标期限：</span>
                 	<select id="loanTerm" name="loanTerm">
                 		<option value="">全部</option>
                 		<option value="1">一个月</option>
                 		<option value="2">两个月</option>
                 		<option value="3">三个月</option>
                 		<option value="4">四个月</option>
                 		<option value="5">五个月</option>
                 		<option value="6">六个月</option>
                 		<option value="7">七个月</option>
                 		<option value="8">八个月</option>
                 		<option value="9">九个月</option>
                 		<option value="10">十个月</option>
                 		<option value="11">十一个月</option>
                 		<option value="12">十二个月</option>
                 	</select>
                 </div>
                <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
	     </div>
	     
	          </form>
			<script type="text/javascript">	
			$("#loanTerm").val('${loanTerm}');
			$("#forkStatus").val('${forkStatus}');
			
			  jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
					<div class="panel-body">
						<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>子标编号</br>子合同编号</th>
									<th>项目编号</th>
									<th>子标金额</th>
									<th>子标期限</th>
									<th>姓名</th>
									<th>手机号</th>
									<th>身份证号</th>
									<th>银行卡号</th>
									<th>总合同编号</th>
									<th>发行渠道</th>
									<th>合同金额</th>
									<th>总借款金额</th>
									<th>总期限</th>
									<th>是否打包</th>
									<th>创建时间</th>
									<th>备注信息</th>
									
								</tr>
							</thead>
								<c:forEach var="childLoansList" items="${pageInfo.results}">
								<tr>
								    <td>${childLoansList.id}</br>
								    	${childLoansList.childContractid}
								    </td>
								    <td>${childLoansList.loanId}</td>
								    <td>${childLoansList.money}</td>
								    <td>${childLoansList.loanTerm}个月</td>
								    <td>${childLoansList.name}</td>
								    <td>${childLoansList.mobileNumber}</td>
								    <td>${childLoansList.idCard}</td>
								    <td> ${childLoansList.bankCard}</td>
								    <td>${childLoansList.contractId}</td>
								     <td>
								     <c:if test="${childLoansList.flag=='demand'}">天天赚</c:if>
								   	 <c:if test="${childLoansList.flag=='agriculture'}">金农宝</c:if>							     
								   	 </td>
								    <td>${childLoansList.contractMoney}</td>
								    <td>${childLoansList.loanMoney}</td>
								    <td>${childLoansList.parentLoanTerm}个月</td>
								   	 <td>
								   	 <c:if test="${childLoansList.packing==0}">否</c:if>
								   	 <c:if test="${childLoansList.packing==1}">是</c:if>
								   	 </td>
								    <td>
								    	<fmt:formatDate value="${childLoansList.createTime}" type="both"/>
								    </td>
								    <td> <a href="javascript:getReason1('${childLoansList.remark}');">备注</a></td> 
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


