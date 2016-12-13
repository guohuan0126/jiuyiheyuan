<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">

  <title>渠道银行关系管理</title>

  
</head>

<body>

<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>


<section>
  
 <%@include file="../base/leftMenu.jsp"%>
  
  <div class="mainpanel">
    
<%@include file="../base/header.jsp"%>
        
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：渠道银行关系管理 </h2>    
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/payMentChannel/payMentShowBankChannel" method="post">
	          	 <div class="form-group">
                  <label class="col-sm-3 control-label" style="width: 81px;">银行名称 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                     <select class="select2" name="bankName" id="bankName" >	            
	                  	<option value="">请选择银行名称</option>
	                  <c:forEach var="bank" items="${bankList}">
	                  <option value="${bank.id}">${bank.name}</option>
					 </c:forEach>
	                </select>
                  </div>
                </div>
                 <div class="form-group">
                  <label class="col-sm-3 control-label" style="width: 81px;">渠道名称<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
					 <select class="select2" name="channelName" id="channelName" >	            
	                  	 <option value="">请选择渠道名称</option>
	                  <c:forEach var="channel" items="${channelList}">
	                  <option value="${channel.id}">${channel.name}</option>
					 </c:forEach>
	                </select>
                  </div>   
                                   
                </div>
	           <button type="submit" id="button" class="btn btn-primary">查询</button>
	           <button type="button" id="add" class="btn btn-primary">添加</button>
	          </form>
	          
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>编号</th>
                        <th>银行名称</th>
                        <th>渠道名称</th>
                        <th>单笔限额</th>
                        <th>单日限额</th>
                        <th>单月限额</th>
                        <th>是否支持网银</th>
                        <th>是否支持快捷</th>
                        <th>是否支持绑卡</th>
                        <th>是否支持加急提现</th>
                        <th>排序</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="paymentBankChannel">
                      <tr>
                       <td>${paymentBankChannel.id}
                       	<input value="${paymentBankChannel.id}" type="hidden" id="id">
                       </td>
                       <td>${paymentBankChannel.bankName}</td>                    
					  <td>${paymentBankChannel.channelName}</td>
					  <td>
					  	<fmt:formatNumber currencySymbol="￥" type="currency" value="${paymentBankChannel.oneMoney}"/>
					  </td>
					  <td>
					  	<fmt:formatNumber currencySymbol="￥" type="currency" value="${paymentBankChannel.dayMoney}"/>
					  </td>
					  <td>
					  <fmt:formatNumber currencySymbol="￥" type="currency" value="${paymentBankChannel.monthMoney}"/>
					  </td>
					  <td>
					  	<c:if test="${paymentBankChannel.onlineBanking=='1'}">支持</c:if>
					  	<c:if test="${paymentBankChannel.onlineBanking=='2'}">不支持</c:if>
					  </td>
					  <td>
					  	<c:if test="${paymentBankChannel.quickRecharge=='1'}">支持</c:if>
					  	<c:if test="${paymentBankChannel.quickRecharge=='2'}">不支持</c:if>
					  </td>
					  <td>
					  	<c:if test="${paymentBankChannel.tiecard=='1'}">支持</c:if>
					  	<c:if test="${paymentBankChannel.tiecard=='2'}">不支持</c:if>
					  </td>
					  <td>
					  	<c:if test="${paymentBankChannel.urgentWithdrawals=='1'}">支持</c:if>
					  	<c:if test="${paymentBankChannel.urgentWithdrawals=='2'}">不支持</c:if>
					  </td>
					  <td>
					  	<input value="${paymentBankChannel.sort}" style="width:25px" id="${paymentBankChannel.id}"/>
					  	<input type="button" onclick="editSort('${paymentBankChannel.id}')" value="保存"/>
					  </td>
					  	<td> 
					  		<a href="/payMentChannel/toEditPayMentBankChannel?id=${paymentBankChannel.id}">编辑</a>
					  	</td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
 		
<%@ include file="../base/page.jsp"%>
 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
$('#bankName').val('${bankName}'); 
$('#channelName').val('${channelName}'); 
	


$("button[id=add]").click(function(){
	window.location.href="/payMentChannel/toaddPayMentBankChannel";
});
function editSort(payMentBankChannelId){
	var sortNum=$("#"+payMentBankChannelId).val();
	$.ajax({
		type : "post",
		url : "/payMentChannel/toEditPayMentBankChannelSortNum",
		data : {
			"id" : payMentBankChannelId,
			"sortNum":sortNum
		},
		dataType : "text",
		success : function(data) {
			if(data=="ok"){
				alert("修改成功");
				window.location.href="/payMentChannel/payMentShowBankChannel";
			}else{
				alert("修改失败");
			}
			
		},
		error : function() {
			console.info("调取失败");
		}
	});
}
</script>
</body>
</html>
