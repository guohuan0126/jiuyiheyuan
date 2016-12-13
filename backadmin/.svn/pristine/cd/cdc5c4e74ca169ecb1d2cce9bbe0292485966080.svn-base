<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/common/activeTag" prefix="base"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link href="${ctx}/css/style.default.css" rel="stylesheet"/>
<script src="${ctx}/js/jquery-1.11.1.min.js"></script>
<%--   <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png"> --%>
<%--   <link href="${ctx}/css/flow/flow.css" rel="stylesheet" /> --%>
<%--   <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" /> --%>

  <title>加息券列表</title>
  <style type="text/css">
	body{zoom:90%;}
  </style>
</head>
<body>
<section> 
  <div class="mainpanel">   
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：用户加息券 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
	<script type="text/javascript">
		$(function(){
			$("input[type='radio'][value='${redpacketId}']").attr("checked",true);
		});
	</script>
    <div class="panel panel-default"> 
    
     <div class="panel-body">
     	<input type="hidden" value="${investId }" id="investId">
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">    
             	<th></th>          
                <th>编号</th>
                <th>手机号</th>
                <th>有效期至</th>      
                
                <th>活动名称</th>
                <th>优惠券类型</th>
                <th>优惠券金额&利率</th>
               
             </tr>
          </thead>
          <tbody>
          	<c:forEach  var="it" items="${packetUseList }">
				<tr class="font">
					<td><input type="radio" name="redPacket" value="${it.id }"></td>
					<td>${it.id }</td>
					<td>${it.mobile }</td>
					<td><fmt:formatDate value="${it.deadline}" pattern="yyyy-MM-dd"/></td>
					
					<td>${it.name}</td>
					<td>
						<c:if test="${it.type == 'money'}">现金券</c:if>
						<c:if test="${it.type == 'rate'}">加息券</c:if>
					</td>
					<td>
						<c:if test="${it.type == 'money'}">${it.money}</c:if>
						<c:if test="${it.type == 'rate'}">${it.rate *100}%</c:if>
					</td>
					
				</tr>          	
          	</c:forEach>
         </tbody>          
        </table>  
 </div>
 </div>
 </div>
</section>
</body>
</html>
