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

  <title>角色管理</title>
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
      <h2><i class="fa fa-table"></i>当前位置：角色管理 </h2>    
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/role/roleList" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	              <input type="text" name="id" value="${id }" class="form-control" id="exampleInputEmail2" placeholder="编号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">名称</label>
	              <input type="text" name="name" value="${name }" class="form-control" id="exampleInputPassword2" placeholder="名称">
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
                        <th>描述</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                        <td>${item.id }</td>
                        <td>${item.name }</td>
                        <td>${item.description }</td>
                        <td><a href="/role/toedit?id=${item.id}">修改</a></td>
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
  //添加按钮
	$("button[id=add]").click(function(){
					window.location.href="/role/toadd";
	});
</script>

</body>
</html>
