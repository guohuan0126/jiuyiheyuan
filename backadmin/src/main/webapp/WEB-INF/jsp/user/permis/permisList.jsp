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
  <title>权限管理</title>
</head>
<body>
<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section>
  
 <%@include file="../../base/leftMenu.jsp"%>
  
  <div class="mainpanel">
    
<%@include file="../../base/header.jsp"%>
        
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：权限管理 </h2>     
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/permis/permisList" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">ID</label>
	              <input type="text" name="id" value="${id }" class="form-control" id="exampleInputEmail2" placeholder="编号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">名称</label>
	              <input type="text" name="name" value="${name }" class="form-control" id="exampleInputPassword2" placeholder="名称">
	            </div>
	             <div class="form-group">
	            	<select id='permissType' name='permissType' class="form-control" style="width:140px;"> 
	            		<option value="">请选择类型...</option>              	               		
                		<option value="menu">菜单 </option>
                		<option value="active">动作</option>													
					</select>
				</div>
	            <button type="submit" class="btn btn-primary">查询</button>
	            <button type="button" id="add" class="btn btn-primary">添加</button>
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th>类型</th>
                        <th>描述</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                        <td>${item.id }</td>
                        <td>${item.name }</td>
                        <td><c:if test="${item.type == 'menu'}">菜单</c:if>
                        	<c:if test="${item.type == 'active'}">动作</c:if>  </td>
                        <td>${item.description }</td>
                        <td><a href="/permis/toedit?id=${item.id}">修改</a></td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
      
 		<%@ include file="../../base/page.jsp"%>

 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>



<script>

		
	
  jQuery(document).ready(function() {
    
    "use strict";
    
    jQuery('#table1').dataTable();
    
    jQuery('#table2').dataTable({
      "sPaginationType": "full_numbers"
    });
    
    // Select2
    jQuery('select').select2({
        minimumResultsForSearch: -1
    });
    
    jQuery('select').removeClass('form-control');
    
    // Delete row in a table
    jQuery('.delete-row').click(function(){
      var c = confirm("Continue delete?");
      if(c)
        jQuery(this).closest('tr').fadeOut(function(){
          jQuery(this).remove();
        });
        
        return false;
    });
    
    // Show aciton upon row hover
    jQuery('.table-hidaction tbody tr').hover(function(){
      jQuery(this).find('.table-action-hide a').animate({opacity: 1});
    },function(){
      jQuery(this).find('.table-action-hide a').animate({opacity: 0});
    });
  
  
  });
  //添加按钮
	$("button[id=add]").click(function(){
					window.location.href="/permis/toadd";
	});
	$("#permissType").val('${permissType}');
</script>

</body>
</html>
