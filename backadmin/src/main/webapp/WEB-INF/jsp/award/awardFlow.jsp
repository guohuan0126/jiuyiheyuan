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
  <title>奖励明细</title>
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
      <h2><i class="fa fa-table"></i>当前位置：奖励流程列表 </h2>     
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
     <div class="panel-heading">
     	<a type="button" class="btn btn-primary" href="${cxf}/award/awardList/unfinance">返回</a>
     
     </div>
     <div class="panel-body"> 
     <c:if test="${not empty itemFlows}">
     	<span style="font-weight: bold;">项目编号：${itemFlows[0].flowId}</span>
     	<span style="font-weight: bold; margin-left:60px;">流程编号：${itemFlows[0].itemId}</span>
     </c:if>
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">
                <th width="8%">节点编号</th>   
                <th width="8%">节点名称</th>             
                <th width="8%">流程状态</th>
                <th width="18%">创建日期</th>
                <th width="18%">操作日期</th>
                <th width="8%">操作人</th>     
                <th width="24%">操作记录</th>
             </tr>
          </thead>
          <tbody>
		     <c:forEach  var="itemFlow" items="${itemFlows}">
		     <tr class="font">		     	
				 <td>${itemFlow.id}</td>	
				 <td>${itemFlow.flowNode.nodeName}</td>				 
				 <td><c:if test="${itemFlow.status eq 'undeal'}">
				 	未处理
				 </c:if>
				 <c:if test="${itemFlow.status eq 'approve'}">
				 	同意
				 </c:if>
				 <c:if test="${itemFlow.status eq 'unapproved'}">
				 	否决
				 </c:if></td>				
				 <td><fmt:formatDate value="${itemFlow.createTime}" type="both"/></td>
				 <td><fmt:formatDate value="${itemFlow.operateTime}" type="both"/></td>
				 <td>${itemFlow.operateUser}</td>
				 <td>${itemFlow.message}</td>
		     </tr>
		     </c:forEach>
         </tbody>          
        </table>    
 </div>
 </div>
 </div>
 </div>
</section>
<script type="text/javascript">
</script>
</body>
</html>
