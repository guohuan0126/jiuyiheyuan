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

<title>农贷借款项目管理</title>

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
					<i class="fa fa-table"></i>当前位置：农贷借款项目管理
				</h2>
			</div>

			<div class="contentpanel">


		<form class="form-inline" id="form1"  action="/agricultural/ruralfinance_data" method="post">		    	 
		    	<div class="form-group" style="padding-top:20px;">   
		    	<span style="font-size: 18px;">借款项目信息：</span>            
	            	
	      <button type="button" id="add" class="btn btn-primary" onclick="toAddLoan()" style="display:inline-block;float: right;margin-right: 20px;">批量放款</button>
	     </div>
	          </form>
					<div class="panel-body">

						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
								<th><input name="checkall" type="checkbox" id="checkall"  style="vertical-align:sub;"/>全选
								</th>
									<th>借款项目编号</th>
									<th>借款人姓名</th>
									<th>身份证号</th>
									<th>手机号</th>
									<th>银行卡号</th>
									<th>借款金额</th>
									<th>借款期限(月)</th>
									<th>利率</th>
									<th>还款方式</th>
									<th>创建时间</th>
									<th>创建人员</th>
									<th>复核时间</br>
										项目状态</br>
										复核人员
									</th>
									<th>操作</th>									
								</tr>
							</thead>
								<c:forEach var="ruralfinance" items="${pageInfo.results}">
								<tr>
								   <td><input name="checkname" type="checkbox" value="${ruralfinance.id}" /></td>
									<td>${ruralfinance.id}</td>
									<td>${ruralfinance.loanerinfo.userName}</td>	
									<td>${base:decrypt(ruralfinance.loanerinfo.idCard)}</td>	
									<td>${base:decrypt(ruralfinance.loanerinfo.mobileNumber)}</td>	
									<td>${base:decrypt(ruralfinance.ruralfinanceBankcard.bankcard)}</td>	
									<td>${ruralfinance.money}</td>	
									<td>${ruralfinance.loan_term}个月</td>	
									<td>${ruralfinance.rate}</td>	
									<td>${ruralfinance.repay_type}</td>	
									<td>
									<fmt:formatDate value="${ruralfinance.create_time}" type="both"/>
									</td>	
									<td>${ruralfinance.create_user}</td>	
									<td>
										<fmt:formatDate value="${ruralfinance.update_time}" type="both"/></br>
										<c:if test="${ruralfinance.status=='uncheck'}">未复核</c:if>
										<c:if test="${ruralfinance.status=='checked'}">已复核</c:if>
										<c:if test="${ruralfinance.status=='reject'}">已驳回</c:if>
										</br>
										${ruralfinance.update_userid}
									</td>	
									<td>
									<button id="sub"  onclick="advance('${ruralfinance.id}')" style=width: 40px;height: 22px;>放款</button>								
									</td> 
								</tr>
							</c:forEach>	
						</table>
					</div>
					<script type="text/javascript">
					$("#checkall").click(function(){ 
						 if(this.checked){ 
						        $("input:checkbox[name=checkname]").attr('checked', true);
						    }else{ 
						        $("input:checkbox[name=checkname]").attr('checked', false);
						    } 
						
				  });
					//获取选中的id值
					function checkLoan(){
						var arr = new Array();
						$("input:checkbox[name=checkname]:checked").each(function(i){						
							  arr.push($(this).val());
						}); 
						return arr;
					}
					function toAddLoan(){
			 			if(confirm('确认对项目进行批量放款吗?')){			
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/agricultural/BatchLoanManagement",						
								data: {"ids":checkLoan()},
								success:function(data) {
									if(data=='success'){
										alert("批量放款成功");
										window.location.href="/agricultural/payment_loan";
									}else{
										alert("放款失败");
										window.location.href="/agricultural/payment_loan";
									}
								}
							});	
							
						}
					}
					  $("#st").val('${status}');
					//删除
					function advance(uuid){
						if(confirm('确认对改项目进行放款吗?')){
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/agricultural/LoanManagement",						
								data: {"loandata_id":uuid},
								success:function(data) {
									if(data=='success'){
										alert("放款成功");
										window.location.href="/agricultural/payment_loan";
									}else{
										alert("放款失败");
										window.location.href="/agricultural/payment_loan";
									}
								}
							});	
							
						}
					}
					</script>
					
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


