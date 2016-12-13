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

<title>垫付资金记录列表</title>
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
					<i class="fa fa-table"></i>当前位置：垫付资金记录列表
				</h2>
			</div>
		<div class="contentpanel" style="padding:5px;">		 	
		 	<span style="font-size: 20px;letter-spacing:7px;">警告阀值:</span>
		 	<input type="text" value="${warnMoney}" id="warnMoney"/>
		 	<input type="button" value="保存" class="btn btn-primary" onclick="updateWarnMoney()">
		 </div>
		 <div class="contentpanel" style="padding:5px;">
		 	<span style="font-size: 20px">垫付资金金额</span>
		 	<input type="text" value="${money}" disabled="disabled"/>
		 	
		 </div>
		
		
		<form class="form-inline" id="form1"  action="/payMent/advancefundRecord" method="post">		    	 
	     <div class="contentpanel">
	      <div class="input-group" >
                <input type="text" class="form-control" name="startTime" value="${startTime}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                <input type="text" class="form-control" name="endTime" value="${endTime}" placeholder="结束时间" id="datepicker1">
               	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group">
              	<span style="font-size: 20px">状态：</span>
                 	<select id="statusSelect" name="type">
                 		<option value="">全部</option>
                 		<option value="in">充值</option>
                 		<option value="out">提出</option>
                 	</select>
                </div>
                <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
	   		   <input type="button" id="update" class="btn btn-primary" onclick="updateAdvancefundRecord()" value="修改垫付资金" style="foat:right"/>
	   		    </div>
	          </form>
			<script type="text/javascript">	
				$("#statusSelect").val('${type}');
			  jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});
			  
			  function updateAdvancefundRecord(){
				  window.location.href="/payMent/toUpdateAdvancefundMoney";
			  }
			  
			  function updateWarnMoney(){
					var warnMoney=$("#warnMoney").val();
					$.ajax({
							type : 'POST',
							dataType:'text',
							url : "/payMent/updateAdvancefundUpdateWarnMoney",						
							data: {
								"warnMoney":warnMoney
							},
							success:function(data) {
							
								if(data=='error'){
									alert("修改失败");
								}else{
									alert("修改成功");
									window.location.href="/payMent/advancefundRecord";
								}
							}
						});		
				  
			  }
			</script>
					<div class="panel-body">
						<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>编号</th>
									<th>创建时间</th>
									<th>金额</th>
									<th>类型</th>
									<th>创建者</th>
									<th>第三方通道（编号）</th>
								</tr>
							</thead>
								<c:forEach var="paymentAdvancefundRecord" items="${pageInfo.results}">
								<tr>
								    <td>${paymentAdvancefundRecord.id}</td>
								   	<td>
								    <fmt:formatDate value="${paymentAdvancefundRecord.carateTime}" type="both"/>
								    </td>
								    <td>${paymentAdvancefundRecord.money}</td>
								    <td>
								    	<c:if test="${paymentAdvancefundRecord.type=='in'}">充值</c:if>
								    	<c:if test="${paymentAdvancefundRecord.type=='out'}">提现</c:if>
								    </td>
								    
								    <td>${paymentAdvancefundRecord.creater}</td>
								    <td>${paymentAdvancefundRecord.paymentId}</td>
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


