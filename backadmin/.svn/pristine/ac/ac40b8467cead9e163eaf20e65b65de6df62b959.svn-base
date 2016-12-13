<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  <title>项目已用红包列表</title>
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
      <h2><i class="fa fa-table"></i>当前位置：项目已用红包列表</h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="/loan/investRedpacket" method="post">		    	 
		    	<div class="form-group">              
	            	<input type="text" name="loanId"  class="form-control" placeholder="项目编号" style="width:160px" value="${loanId}">
	            </div>		    	 
	            <div class="form-group">	              
	            	<input type="text" name="user"  class="form-control" placeholder="用户编号/姓名/手机号" style="width:160px" value="${user }">
	            </div>
	            <div class="form-group">	              
	            	<input type="text" name="investId"  class="form-control" placeholder="投资编号" style="width:160px" value="${investId}">
	            </div>
	            <label class="control-label">补息发送状态 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="sendAllowanceStatus" name="sendAllowanceStatus" class="select">
                      <option value="-2" <c:if test="${sendAllowanceStatus eq '-2' }">selected</c:if> >全部</option>                
                      <option value="0" <c:if test="${sendAllowanceStatus eq '0' }">selected</c:if> >未发</option>
                      <option value="1" <c:if test="${sendAllowanceStatus eq '1' }">selected</c:if>>已发</option>
                      <option value="-1" <c:if test="${sendAllowanceStatus eq '-1' }">selected</c:if>>发送失败</option>                 
                    </select>              
                </div>	
	            <label class="control-label">补息发送状态 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="sendRedpacketStatus" name="sendRedpacketStatus" class="select">
                      <option value="-2" <c:if test="${sendRedpacketStatus eq '-2' }">selected</c:if> >全部</option>                
                      <option value="0" <c:if test="${sendRedpacketStatus eq '0' }">selected</c:if> >未发</option>
                      <option value="1" <c:if test="${sendRedpacketStatus eq '1' }">selected</c:if>>已发</option>
                      <option value="-1" <c:if test="${sendRedpacketStatus eq '-1' }">selected</c:if>>发送失败</option>                 
                    </select>              
                </div>	
	            <button type="button" id ="queryButton" class="btn btn-primary" onclick="javascript:submit()">查询</button>	           
	          </form>
	          <div style="height:10px;"></div>          
	    </div>  
     <div class="panel-body"> 
	  <table class="<!-- table table-primary table-striped table-hover -->" style="width:100%">
          <thead>
             <tr class="font">              
                <th >项目编号/投资编号</th>
                <th >用户编号</th>                
                <th >补息流水号/补息金额<br>
                	补息发送状态/补息发送时间</th>
                <th >补息发送结果</th>      
                <th >红包编号/奖励流水号/奖励金额<br>
                	奖励发送状态/奖励发送时间</th>                                   
                <th >奖励发送结果</th>
                <th >创建时间</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="investRedpacket" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td >${investRedpacket.loanId}</br>${investRedpacket.investId}</td>				
				 <td >${investRedpacket.userId}</td>						 								 
				 <td >${investRedpacket.allowanceOrder}</br>
				 	  <fmt:formatNumber value="${investRedpacket.investAllowanceInterest}" pattern="#,##0.##"/></br>
				 	  <c:if test="${investRedpacket.sendAllowanceStatus == 0}">未发送</c:if>
					  <c:if test="${investRedpacket.sendAllowanceStatus == 1}">发送成功</c:if>
					  <c:if test="${investRedpacket.sendAllowanceStatus == -1}">发送失败</c:if></br>
					  <fmt:formatDate value="${investRedpacket.sendAllowanceTime}" type="both"/></br>
				 </td>
				 
				 
				 <td>${investRedpacket.repackedId}</br>
				 	 ${investRedpacket.repackedOrder}</br>
				 	 <fmt:formatNumber value="${investRedpacket.rewardMoney}" pattern="#,##0.##"/></br>
				 	 <c:if test="${investRedpacket.sendRedpacketStatus == 0}">未发送</c:if>
					 <c:if test="${investRedpacket.sendRedpacketStatus == 1}">发送成功</c:if>
					 <c:if test="${investRedpacket.sendRedpacketStatus == -1}">发送失败</c:if></br>
					 <fmt:formatDate value="${investRedpacket.sendRedpacketTime}" type="both"/>
				 </td>
				 <td ><textarea rows="6" cols="25">${investRedpacket.sendAllowanceResult}</textarea></td>				
				 <td ><textarea rows="6" cols="25">${investRedpacket.sendRedpacketResult}</textarea></td>
				 <td ><fmt:formatDate value="${investRedpacket.createTime}" type="both"/></td>
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>       	
        <%@ include file="../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>
</section>
</body>
</html>
