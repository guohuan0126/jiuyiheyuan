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

<title>第三方支付流水列表</title>
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
<style type="text/css">
		.panel-body{position:relative;}
        #body{ display: none;  position: absolute;  top: 0%;  left: 0%;  width: 100%;  height: 100%;  background-color: black;  z-index:1001;  -moz-opacity: 0.7;  opacity:.70;  filter: alpha(opacity=70);}
</style>
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
					<i class="fa fa-table"></i>当前位置：第三方支付流水列表
				</h2>
			</div>

		<form class="form-inline" id="form1"  action="/payMent/payMentBill" method="post">		    	 
	     <div class="contentpanel">
	       	<input type="text"  name="userId" value="${userId}" class="form-control" placeholder="用户编号/手机号" id="userId">
	       	<input type="text"  name="markId" value="${markId}" class="form-control" placeholder="流水编号" id="markId">
  				<span style="font-size: 20px">状态：</span>
                 	<select id="statusSelect" name="status">
                 		<option value="">全部</option>
                 		<option value="sended">发起</option>
                 		<option value="passed">成功</option>
                 		<option value="failed">失败</option>
                 		<option value="refused">未完成</option>
                 	</select>
	      <div class="input-group" >
                <input type="text" class="form-control" name="startTime" value="${startTime}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="endTime" value="${endTime}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                    <div class="input-group" >
                	<select id="payMentSelect" name="payMentSelect">
                		<option value="">渠道名称</option>
                		<c:forEach var="payMent" items="${payMentList}">
                			<option value="${payMent.code}">${payMent.name}</option>
                		</c:forEach>
                	</select>
                </div>
                <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
	   		    </div>
	          </form>
			<script type="text/javascript">	
			$("#statusSelect").val('${status}');
			
			$("#payMentSelect").val('${payMentSelect}');
			  jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
					<div  class="panel-body">
						<div id="rule"></div>
						<div id="body"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>流水编号</br>
										流水指向
									</th>
									<th>金额</th>
									<th>请求数据</br>
										请求时间</br> 
										请求地址
									</th>
                        			<th>响应数据</br>
                        				响应时间
                        			</th>
									<th>第三方通道（编号）</br>
										来源</br>
										支付类型
									</th>
									<th>用户ID</br>
										真实姓名</br>
										<base:active activeId="USER_LOOK">手机号</base:active>
									</th>
									<th>状态</th>
									<th>
									银行卡号<br>
									银行卡类型</br>
									银行简称
									</th>
									<th>详细信息</th>
									<base:active activeId="JDPay_Single_Supplement"><th>补单</th></base:active> 
								</tr>
							</thead>
								<c:forEach var="paymentRechargeRecord" items="${pageInfo.results}">
								<tr>
								    <td>${paymentRechargeRecord.markId}</br>
								    ${paymentRechargeRecord.operator}
								    </td>
								    <td>${paymentRechargeRecord.money}</td>
								    <td class="only">
									<textarea rows="8" cols="50" readonly="readonly">${paymentRechargeRecord.requestData}</textarea></br>
									<fmt:formatDate value="${paymentRechargeRecord.requestTime}" type="both"/>
								    </br>${paymentRechargeRecord.requestUrl}
									</td>
                        			<td style="width:100px">
                        			<textarea rows="8" cols="50" readonly="readonly">${paymentRechargeRecord.responseData} </textarea>
                        			</br>
                        			 <fmt:formatDate value="${paymentRechargeRecord.responseTime}" type="both"/>
                        			</td>
								    <td>${paymentRechargeRecord.payMentId}</br>
								    	${paymentRechargeRecord.source}</br>
								    	<c:if test="${paymentRechargeRecord.type=='quick'}">快捷</c:if>
								    </td>
								    <td>${paymentRechargeRecord.userId}</br>
								    	${paymentRechargeRecord.userName}</br>
								    	<base:active activeId="USER_LOOK">${paymentRechargeRecord.mobileNumber}</base:active>
								    </td>
								    <td>
								    	<c:if test="${paymentRechargeRecord.status=='sended'}">发起</c:if>
								    	<c:if test="${paymentRechargeRecord.status=='passed'}">成功</c:if>
								    	<c:if test="${paymentRechargeRecord.status=='failed'}">失败</c:if>
								    	<c:if test="${paymentRechargeRecord.status=='refused'}">未完成</c:if>
								    </td>
								     <td>${paymentRechargeRecord.cardNo}</br>
								     ${paymentRechargeRecord.cradType}</br>  
								     ${paymentRechargeRecord.bankCode}
								     </td>
								  <td><a href="/payMent/CompanyYeepayTransferUserYeepay?markId=${paymentRechargeRecord.markId}" >易宝转账信息</a> </td>
								<base:active activeId="JDPay_Single_Supplement">
								  <td>
								 	<c:if test="${paymentRechargeRecord.status=='passed'&&paymentRechargeRecord.isYeepayTransfer!='passed'}">
								  		<a href="javascript:supplementOrder('${paymentRechargeRecord.markId}','${paymentRechargeRecord.transferWay}');">补单</a>
								  	</c:if>
								  </td>
								  </base:active>
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

<script type="text/javascript">
function supplementOrder(orderId,transferWay){
	if (confirm("确认补单")){
		 document.getElementById("body").style.display ="block";
		 $.ajax({
				type : 'POST',
				url : '/payMent/supplementOrder',
				data:{
					'orderId':orderId,
					'transferWay':transferWay
				},
				success : function(msg) {
					alert(msg);
					window.location = "/payMent/payMentBill";
				},
				error : function() {
					alert("异常！");
				}
			});	
	}
}

</script>

</body>
</html>


