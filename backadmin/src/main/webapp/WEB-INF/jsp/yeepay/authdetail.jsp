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

  <title>转账用户列表</title> 

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
      <h2><i class="fa fa-table"></i>当前位置：转账用户列表 </h2>
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>编号</th>
                        <th>用户编号</th>
                        <th>金额</th>
                        <th>状态</th>
                        <th>转账编号</th>
                      </tr>
                    </thead>
                   <tbody>
                    <c:forEach items="${details}" var="item">
                      <tr>
                       <td>${item.id}</td>
                      <td>${item.userId}</td>
						 <td>${item.amount }</td>
						 <td>${item.status }</td>
						 <td>${item.transactionAuthorizationId}</td>
                      </tr>
                    </c:forEach>
                  </tbody>
        </table>
 	
 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>
</body>
</html>
