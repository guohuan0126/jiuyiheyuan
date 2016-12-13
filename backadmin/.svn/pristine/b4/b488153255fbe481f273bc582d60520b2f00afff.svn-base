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

  <title>app版本</title>

  

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="js/html5shiv.js"></script>
  <script src="js/respond.min.js"></script>
  <![endif]-->
  
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
      <h2><i class="fa fa-table"></i>当前位置：app版本管理 </h2>     
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/app/version" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">来源</label>
	                <select class="select2" name="name" id="st" data-placeholder="来源...">
	                  <option value="">--请选择来源</option>
	                  <option value="android">android</option>
	                  <option value="ios">ios</option>
	                </select>
	            </div>
	           
	            <button type="submit" class="btn btn-primary">查询</button>
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover " id="table">
                    <thead>
                      <tr>
                       <th>编号</th>
                        <th>版本名称</th>
                        <th>版本号</th>
                        <th>下载地址</th>
                        <th>来源</th>
                        <th>时间</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item" varStatus="status">
                      <tr>
                      <td>${item.id }</td>
                      <td>${item.versionName }</td>
                      <td>${item.versionCode }</td>
                      <td>${item.downloadAddress }</td>
                      <td>${item.osName }</td>
                        <td><fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd HH:mm" /></td>
					<td><a href="/app/toversion?id=${item.id}">编辑</a></td>
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

<script>
jQuery(document).ready(function(){
   var name='${name}'
     $("#st").val(name);
});

</script>
</body>
</html>
