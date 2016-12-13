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

  <title>单笔业务查询</title>
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
      <h2><i class="fa fa-table"></i>当前位置：单笔业务查询 </h2>      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline" id="form1"  method="post">
	            <div class="form-group">
                <label class="sr-only" for="st">类型</label>
                 <select class="form-control input-md " name="stype" id="st">
                  <option value="WITHDRAW_RECORD" >提现记录</option>
                  <option value="RECHARGE_RECORD">充值记录</option>
                  <option value="CP_TRANSACTION">转账记录</option>
                  <option value="FREEZERE_RECORD">冻结/解冻接口</option>
                </select>
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="content">流水号</label>
	              <input type="text" name="content" value="${content }" class="form-control" id="content" placeholder="流水号">
	            </div>
	            <button type="button" id="search" class="btn btn-primary">查询</button>
	            <div id="erroruser" style="display: none;">查询内容不能为空</div>
	          </form>
	    </div>
	  <div class="panel-body"> 
	  		<c:if test="${query.type eq  'WITHDRAW_RECORD' }">
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>提现金额</th>
                        <th>提现用户</th>
                        <th>提现时间</th>
                        <th>提现状态</th>
                        <th>打款状态</th>
                         <th>描述</th>
                    </thead>
                    <tbody>
                     <tr>
                       <td>${query.wamount}</td>
                       <td>${query.wuserNo}</td>
                       <td><fmt:formatDate
									value="${query.wcreateTime }"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
                       <td>
                       ${query.wstatus}
                       </td>
                       <td>
                       <c:if test="${query.wremitStatus eq 'REMIT_SUCCESS'}">打款成功</c:if>
                       <c:if test="${query.wremitStatus eq 'REMIT_FAILURE'}">打款失败</c:if>
                       <c:if test="${query.wremitStatus eq 'REMITING'}">打款中</c:if>
                       
                       </td>
                       <td>${query.description}</td>
					  </tr>
                    </tbody>
                </table>
     </c:if>
     <c:if test="${query.type eq  'RECHARGE_RECORD' }">
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>充值金额</th>
                        <th>充值用户</th>
                        <th>充值时间</th>
                        <th>充值状态</th>
                        <th>支付方式</th>
                         <th>描述</th>
                    </thead>
                    <tbody>
                     <tr>
                       <td>${query.ramount}</td>
                       <td>${query.ruserNo}</td>
                       <td><fmt:formatDate
									value="${query.rcreateTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
                       <td>
                       <c:if test="${query.rstatus eq 'INIT'}">未付</c:if>
                       <c:if test="${query.rstatus eq 'SUCCESS'}">付款成功</c:if>
                       </td>
                       <td>
                       <c:if test="${query.rpayProduct eq 'NET_B2B'}">B2B网银</c:if>
                       <c:if test="${query.rpayProduct eq 'NET_B2C'}">B2C网银</c:if>
                       <c:if test="${query.rpayProduct eq 'A_PAY'}">一键支付</c:if>
                       <c:if test="${query.rpayProduct eq 'WH_NO_CARD'}">代充值</c:if>
                       <c:if test="${query.rpayProduct eq 'SWIFT'}">快捷充值</c:if>
                       </td>
                       <td>${query.description}</td>
					  </tr>
                    </tbody>
                </table>
     </c:if>
     <c:if test="${query.type eq  'CP_TRANSACTION' }">
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>流水号</th>
                        <th>业务类型</th>
                        <th>转账总金额</th>
                        <th>订单状态</th>
                        <th>处理状态</th>
                         <th>描述</th>
                    </thead>
                    <tbody>
                     <tr>
                       <td>${query.trequestNo}</td>
                       <td>${query.tbizType}</td>
                       <td>${query.tamount}</td>
                       <td>
                       <c:if test="${query.tstatus eq 'PREAUTH'}">已授权</c:if>
                       <c:if test="${query.tstatus eq 'CONFIRM'}">已确认出款</c:if>
                       <c:if test="${query.tstatus eq 'CANCEL'}">已取消</c:if>
                       <c:if test="${query.tstatus eq 'DIRECT'}">直接划转</c:if>
                       
                       </td>
                       <td>
                       <c:if test="${query.tsubStatus eq 'PROCESSING'}">处理中</c:if>
                       <c:if test="${query.tsubStatus eq 'SUCCESS'}">成功</c:if>
                       <c:if test="${query.tsubStatus eq 'ERROR'}">异常</c:if>
                        <c:if test="${query.tsubStatus eq 'FAIL'}">失败</c:if>
                       </td>
                       <td>${query.description}</td>
					  </tr>
                    </tbody>
                </table>
     </c:if>
     <c:if test="${query.type eq  'FREEZERE_RECORD' }">
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>流水号</th>
                        <th>平台会员编号</th>
                        <th>冻结金额</th>
                        <th>过期时间</th>
                        <th>创建时间</th>
                        <th>处理状态</th>
                        <th>描述</th>
                    </thead>
                    <tbody>
                     <tr>
                       <td>${query.frequestNo}</td>
                       <td>${query.fplatformUserNo}</td>
                       <td>${query.famount}</td>
                       <td><fmt:formatDate
									value="${query.fexpired}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
                       <td><fmt:formatDate
									value="${query.fcreateTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
                       <td>${query.fstatus}</td>
                       <td>${query.description}</td>
                       
					  </tr>
                    </tbody>
                </table>
     </c:if>
 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
jQuery(document).ready(function(){
  if('${stype}'!=''){
  	$("#st").val('${stype}');
  }

	
});
$("button[id=search]").click(function(){
	 if($("#content").val()==''){
		$('#erroruser').attr("style","display:block;font-size:14px;color:red");
		return false;
	 }else{
		$('#erroruser').attr("style","display:none;font-size:14px;color:red");
		 $("#form1").attr("action","/account/query");
		 $("#form1").submit();
	 }
});
</script>
</body>
</html>
