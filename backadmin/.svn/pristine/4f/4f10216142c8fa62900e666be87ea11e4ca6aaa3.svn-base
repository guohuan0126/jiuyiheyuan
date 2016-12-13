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
<script>
function selectAll(){
 var checklist = document.getElementsByName ("selected");
   if(document.getElementById("controlAll").checked)
   {
   for(var i=0;i<checklist.length;i++)
   {
      checklist[i].checked = 1;
   } 
 }else{
  for(var j=0;j<checklist.length;j++)
  {
     checklist[j].checked = 0;
  }
 }
}
</script>
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
	            	<input type="text" name="loandata_id"  value="${loandata_id}" class="form-control" placeholder="借款编号" style="width:160px">                     
	            	<input type="text" name="username"  value="${username}" class="form-control" placeholder="用户姓名/手机号" style="width:160px">	         
	              <label class="sr-only" for="exampleInputmobile">状态</label>
	                <select class="select2" name="status" id="st" data-placeholder="状态...">
	                  <option value="">项目状态</option>
	                  <option value="uncheck">未审核</option>
	                  <option value="checked">已复核</option>
	                  <option value="reject">驳回</option>
	                  <option value="repay">还款中</option>
	                  <option value="finish">完成</option>
	                  <option value="delete">已删除</option>
	                </select>
	                                   
	      <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	      <button type="button" id="add" class="btn btn-primary" onclick="toAddUser()" style="display:inline-block;float: right;margin-right: 20px;">添加项目信息</button>
	     </div>
	          </form>
			<script type="text/javascript">
				function toAddUser(){
					window.location.href="/agricultural/CreateLoanProject";
				}
			</script>


					<div class="panel-body">

						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
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
										复核状态</br>
										复核人员
									</th>
									<th>操作</th>									
								</tr>
							</thead>
								<c:forEach var="ruralfinance" items="${pageInfo.results}">
								<tr>
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
										<c:if test="${ruralfinance.status=='reject'}">已驳回</c:if></br>
										${ruralfinance.update_userid}
									</td>	
									<td>
									    <c:if test="${ruralfinance.status !='checked' and ruralfinance.status !='repay' and ruralfinance.status !='finish'}">
										<a href="javascript:sub('${ruralfinance.id}')">删除</a></br>
										</c:if>
										 <c:if test="${ruralfinance.status !='checked' and ruralfinance.status !='repay' and ruralfinance.status !='finish'}">
										<a href="/agricultural/UpdateLoanProject?id=${ruralfinance.id}">修改</a></br>
										</c:if>
										 <c:if test="${ruralfinance.status !='checked' and ruralfinance.status !='repay' and ruralfinance.status !='finish'}">
										<a href="/agricultural/CheckLoanProject?id=${ruralfinance.id}">复核</a></br>
										</c:if>
									</td> 
								</tr>
							</c:forEach>	
						</table>
					</div>
					<script type="text/javascript">
					  $("#st").val('${status}');
					//删除
					function sub(uuid){
						if(confirm('确认删除该借款项目?')){
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/agricultural/deleteRuralfinanceData",						
								data: {"uuid":uuid},
								success:function(data) {
									if(data=='success'){
										alert("删除成功");
										window.location.href="/agricultural/ruralfinance_data";
									}else{
										alert("删除失败");
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


