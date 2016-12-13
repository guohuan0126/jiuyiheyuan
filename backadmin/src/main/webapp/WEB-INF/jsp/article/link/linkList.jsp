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
  <title>友情链接</title>
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
      <h2><i class="fa fa-table"></i>当前位置：友情链接列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="/article/linkList" method="post">
		    	 
		    	<div class="form-group">              
	              <input type="text" name="id"  class="form-control" placeholder="编号">
	            </div>
		    	 
	            <div class="form-group">	              
	              <input type="text" name="name"  class="form-control" placeholder="名称">
	            </div>
	            <div class="form-group">
		            <select name="type" class="form-control">
		            	<option value=""> -- 请选择  -- </option>
		            	<c:forEach var="type" items="${types}">
		            		<option value="${type.id}">${type.name}</option>
		            	</c:forEach>
		            </select>
	            </div>	    
	            <div class="form-group">
								<label class="col-sm-3 control-label" style="display: inline-block;height: 40px;line-height: 40px;">状态</label>
								<div class="col-sm-3">
					        	<select name="picStatus" id="picStatus" class="form-control"  style="width:150px">					            	
					            	<option value="">全部</option>
					            	<option value="0" <c:if test="${picStatus == '0' }">selected="selected"</c:if> >禁用</option>
					            	<option value="1" <c:if test="${picStatus == '1' }">selected="selected"</c:if> >启用</option>
					            </select>        
					            </div>
				            </div>                   
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>&nbsp;
	            <a href="${ctx}/article/toLink/add" class="btn btn-primary">添加友情链接</a>
	          </form>
	          
	    </div>  
	    
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th>编号</th>
                <th>名称</th>
                <th>类型</th>
                <th>URL</th>
                <th>LOGO</th>
                <th>位置</th>
                <th>状态</th>
                <th>排序</th>
                <th>描述</th>              
                <th>操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="link" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${link.id}</td>
				 <td>${link.name }</td>
				 <td>${link.type}</td>
				 <td>${link.url}</td>
				 <td>${link.logo}</td>
				 <td>${link.position}</td>	
				 <td>
				 	<c:if test="${link.status == '0' }">禁用</c:if>
				 	<c:if test="${link.status == '1' }">启用</c:if>
				 </td>
				 <td>${link.seqNum}</td>
				 <td>${link.description}</td>							 								 
				 <td>
				 	<a href="${ctx}/article/toLink/edit?param=${link.id}">编辑</a>
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
