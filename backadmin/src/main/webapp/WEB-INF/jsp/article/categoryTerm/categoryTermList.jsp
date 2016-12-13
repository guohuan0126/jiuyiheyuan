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
  <title>文章管理</title>
</head>
<body>
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section> 
 <%@include file="../../base/leftMenu.jsp"%> 
  <div class="mainpanel">   
	<%@include file="../../base/header.jsp"%>      
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：术语列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="/article/categoryTermList" method="post">
		    	 
		    	<div class="form-group">              
	              <input type="text" name="id"  class="form-control" placeholder="编号">
	            </div>
		    	 
	            <div class="form-group">
	              
	              <input type="text" name="name"  class="form-control" placeholder="名称">
	            </div>	                       
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	          </form>
	          <a href="${ctx}/article/toCategoryTerm/create" class="add">创建术语</a>
	    </div>  
	    
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">         
             	<th width="8%">序号</th>     
                <th width="16%">编号</th>
                <th width="16%">名称</th>
                <th width="32%">描述</th>
                <th width="8%">顺序</th>              
                <th width="20%">操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="categoryTerm" items="${pageInfo.results}" varStatus="status">
		     <tr class="font">		     	
		     	 <td>${status.index}</td>
				 <td>${categoryTerm.id}</td>
				 <td>${categoryTerm.name }</td>
				 <td>${categoryTerm.description}</td>
				 <td>${categoryTerm.seqNum}</td>						 								 
				 <td>
				 	<a href="${ctx}/article/toCategoryTerm/edit?param=${categoryTerm.id}">编辑</a>
				 	<!-- <a href="">删除</a> -->
				 </td>
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>       	
        <%@ include file="../../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>
 
</section>

</body>
</html>
