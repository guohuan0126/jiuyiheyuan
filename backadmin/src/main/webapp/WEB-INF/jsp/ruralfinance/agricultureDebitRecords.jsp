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

<title>中金扣款记录管理</title>

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
					<i class="fa fa-table"></i>当前位置：中金扣款记录管理
				</h2>
			</div>
			    	 
	     <div class="contentpanel">
	     	<form class="form-inline" id="form1"  action="/agricultural/zhongjindebitRecords" method="post">
	     	<input type="text" id="name" name="name" value="${id}" class="form-control" style="width:212px;display:inline-block;" placeholder="明细号"></input>
	      <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
            <div class="input-group" >
  			<span style="font-size: 15px">状态：</span>
                 	<select id="Status" name="status">
                 		<option value="">全部</option>
                 		<option value="1">扣款成功</option>
                 		<option value="0">扣款失败</option>                	
                 	</select>
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
               <input type="button" id="query" class="btn btn-primary"  onclick="updateStatus('${uploadExcelId}')" style="display:inline-block;" value="批量匹配还款计划状态"></input>
	            </c:if>
	     
	          </form>
			<script type="text/javascript">	
			$("#flag").val('${flag}');
			$("#Status").val('${status}');
			
			jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
					<div class="panel-body">
					<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>序号</th>
									<th>交易时间</th>
									<th>机构名称</th>
									<th>批次号</th>
									<th>明细号</th>
									<th>是否匹配过</th>
									<th>扣款金额</th>
									<th>银行ID</th>
									<th>账户名称</br>
									账户号码</th>
									<th>账户类型</th>
									<th>分支行名称</th>
									<th>分支行省份</th>
									<th>分支行城市</th>
									<th>协议用户编号</br>
									协议号
									</th>
									<th>手机号码</th>
									<th>电子邮件</th>
									<th>证件类型</br>
									证件号码</th>
									<th>银行代收的时间</th>
									<th>交易状态</th>
									<th>银行响应代码</th>
									<th>银行响应消息</th>
									<th>结算标识</th>
									<th>卡介质类型</th>											
									<th>备注信息</th>									
								</tr>
							</thead>
								<c:forEach var="debitRecords" items="${pageInfo.results}" varStatus="idx">
								<tr>
								   <td>${idx.index+1 }</td>
								    <td><fmt:formatDate value="${debitRecords.transactionTime}" type="both"/>
								    </td>
								    <td>${debitRecords.organizationName} </td>
								    <td>${debitRecords.batchNumber}</td>
								    <td>${debitRecords.detailsNumber}</td>	
								     <td>
								   	 <c:if test="${debitRecords.flag==0}">否</c:if>
								   	 <c:if test="${debitRecords.flag==1}">是</c:if>
								   	 </td>							   
								    <td>${debitRecords.money}</td>
								     <td>${debitRecords.bankID}</td>
								    <td>${debitRecords.name}</br>
								    ${debitRecords.bankcard}
								    </td>
								    <td>${debitRecords.customerType}</td>
								    <td>${debitRecords.branchName}</td>
								    <td>${debitRecords.branchProvince}</td>
								    <td>${debitRecords.branchCity}</td>
								    <td>${debitRecords.protocolUserid}</br>
								    ${debitRecords.protocolNum}</td>
								    <td>${debitRecords.mobileNumber}</td>
								    <td>${debitRecords.email}</td>
								    <td>${debitRecords.idType}</br>
								    ${debitRecords.idCard}</td>
								     <td><fmt:formatDate value="${debitRecords.bankCollectionTime}" type="both"/>
								    </td>
								   <td>${debitRecords.transactionStatus}</td>
								   <td>${debitRecords.bankResponseCode}</td>
								   <td>${debitRecords.bankResponseInfo}</td>
								   <td>${debitRecords.balanceFlag}</td>
								   <td>${debitRecords.cardType}</td>
								    <td>
								    <a href="javascript:getReason1('${debitRecords.remark}');">备注</a>
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
					
					
					function updateStatus(uploadExcelId){
						if(confirm('确认批量匹配还款计划吗?')){						
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/ruralfinance/updateRepaymentplan",	
								data: {"uploadExcelId":uploadExcelId},
								success:function(data) {
									if(data=='success'){
										alert("批量匹配还款计划成功");
										window.location.href="/ruralfinance/repaymentplanList";
									}else{
										alert("批量匹配还款计划失败");
										window.location.href="/ruralfinance/repaymentplanList";
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
