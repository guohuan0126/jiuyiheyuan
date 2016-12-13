<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.duanrong.business.flow.model.ItemFlow" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
  <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
<style type="text/css">
.ui-datepicker-title {
text-transform: uppercase;
font-family: 'LatoBold';
color: #1CAF9A;

}

.ui-datepicker {
background: #1D2939;
margin-top: 1px;
border-radius: 2px;
width: 280px;}

.ui-datepicker-next {
background: url(../../images/calendar-arrow.png) no-repeat 10px 5px;
}

.ui-datepicker-prev {
background: url(../../images/calendar-arrow.png) no-repeat 8px -80px;
}

.ui-datepicker table {
    color: #ffffff;
}
</style>
  <title>借款管理</title>
</head>
<body>
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section> 
 <%@include file="../base/leftMenu.jsp"%> 
  <div class="mainpanel">   
	<%@include file="../base/header.jsp"%>      
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：项目还款记录</h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">	    	 
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th>还款编号</th>
                <th>项目编号</th>
                <th>项目名称</th>
                <th>借款人</th>              
              	<th>当前还款期数</th> 
              	<th>放款日</th>  
                <th>还款日</th>                              
                <th>还款本金</th>              
                <th>还款利息</th>
                <th>罚息</th>              
                <th>状态</th>
                <th>还款操作时间</th>                            
                <th>提前还款天数</th>
                <th>操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="repay" items="${repays}">
		     <tr class="font">		     	
				 <td>${repay.id}</td>
				 <td>${repay.loanId }</td>
				 <td>${repay.loan.name}</td>						 								 
				 <td>${repay.userId}</td>
				 <td>${repay.period }</td>
				 <td><fmt:formatDate value="${repay.time}" type="both"/></td>
				 <td><fmt:formatDate value="${repay.repayDay}" type="both"/></td>						 								 
				 
				 <td>${repay.corpus}</td>
				 <td>${repay.interest }</td>
				 <td>${repay.defaultInterest}</td>						 								 
				 <td>${repay.status}</td>
				 <td><fmt:formatDate value="${repay.operationTime}" type="both"/></td>				 				
				 <td><c:if test="${repay.loan.operationType == '天' and repay.loan.beforeRepay == '是'}">${repay.loan.symbol}</c:if></td>
				 <td>
				 <c:if test="${repay.status == '还款中'}">
				 <c:if test="${repay.loan.operationType == '天' and repay.loan.beforeRepay == '是'}">
				 <a href="${ctx}/loan/toRepay?id=${repay.id}">编辑</a>
				 </c:if>
				 </c:if></td>				
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>       	      
 </div>
 </div>
 </div>
 </div>
 
</section>

</body>
</html>
