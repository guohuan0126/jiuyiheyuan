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
  <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
  <title>管理用户列表</title>

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
      <h2><i class="fa fa-table"></i>当前位置：管理用户列表 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    </div>
	  <div class="panel-body" > 
   			 <table  id="myTable"  class="tablesorter table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                      <th style="text-align:center">真实姓名</th>
                       <base:active activeId="USER_LOOK"> <th style="text-align:center">用户编号</th>
                        <th style="text-align:center">手机号码</th>
                        <th style="text-align:center">身份证号</th>
                        <th style="text-align:center">电子邮件</th></base:active>
                        <th style="text-align:center">注册时间</th>
                       <base:active activeId="USER_EDIT"> <th  style="text-align:center">操作
                       </th></base:active>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                     
                      <td>${item.realname }</td>
<%--                       <td><a style="cursor:pointer" onclick="edit('${item.userId}','${item.qq }');">${item.realname }</a></td>
 --%>                      <base:active activeId="USER_LOOK">  <td>${item.userId }</td>
                        
                        <td>${item.mobileNumber }</td>
                        <td>${item.idCard }</td>
                        <td>${item.email }</td></base:active>
                        <td><fmt:formatDate
									value="${item.registerTime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
						<base:active activeId="USER_EDIT"><td><a href="/user/toedit?id=${item.userId}&&url=${url}">编辑</a></td></base:active>
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

</body>
</html>
