<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">


  <title>还款详情</title>
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
      <h2><i class="fa fa-table"></i>当前位置：还款详情 </h2>     
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
                <th >还款编号</th>
                
                <th >本金</th>
                <th >利息</th>
                <th >补息金额</th>
                <th >投资用户编号</th> 
                <th >投资编号</th> 
                <th >奖励金额</th>             
                <th >补息状态</th>
                <th >补息时间</th>
                <th >补息原因</th>
                <th >加息券状态</th>
                <th >加息券时间 </th>  
                <th >加息券原因</th>          
             
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="repay" items="${list}">
		     <tr class="font">		     	
		    <td>
			 ${repay.repayId}
			</td>
			
			<td>
			 ${repay.corpus}
			</td>
			<td>
			 ${repay.interest}
			</td>
			<td>
			 ${repay.investAllowanceInterest}
			</td>
			<td>
			 ${repay.investUserId}
			</td>
			<td>
			 ${repay.investId}
			</td>
			<td>
			 ${repay.rewardMoney}
			</td>
			<td>
			 <c:if test="${repay.sendAllowanceStatus eq 0}">
				未发送
			</c:if>
			<c:if test="${repay.sendAllowanceStatus eq 1}">
				发送成功
			</c:if>
			<c:if test="${repay.sendAllowanceStatus eq -1}">
				发送失败
			</c:if>
			</td>
			<td>
			<fmt:formatDate value="${repay.sendAllowanceTime }" pattern="yyyy-MM-dd HH:mm" /> 
			</td>
			<td>
			${repay.sendAllowanceResult}
			</td>
			<td>
			<c:if test="${repay.sendRedpacketStatus eq 0}">
				未发送
			</c:if>
			<c:if test="${repay.sendRedpacketStatus eq 1}">
				发送成功
			</c:if>
			<c:if test="${repay.sendRedpacketStatus eq -1}">
				发送失败
			</c:if>
			</td>
			<td>
			<fmt:formatDate value="${repay.sendRedpacketTime }"
			pattern="yyyy-MM-dd HH:mm" /> 
			</td>
			<td>
			${repay.sendRedpacketResult}
			</td>	 
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
