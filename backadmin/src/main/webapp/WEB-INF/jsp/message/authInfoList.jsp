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

  <title>验证码管理</title>

  

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
      <h2><i class="fa fa-table"></i>当前位置：验证码管理 </h2>
      <div class="breadcrumb-wrapper">
        <span class="label">You are here:</span>
        <ol class="breadcrumb">
          <li><a href="index.html">Bracket</a></li>
          <li class="active">Tables</li>
        </ol>
      </div>
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/authInfo/authInfoList" method="post">
	            
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">手机号</label>
	              <input type="text" name="mnumber" value="${mnumber}" class="form-control" id="exampleInputmobile" placeholder="手机号">
	            </div>
	             
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">验证码类型</label>
	                <select class="select2" name="type" id="st" data-placeholder="验证码类型...">
	                  <option value="">--验证码类型</option>
	                  <option value="register">注册</option>
	                  <option value="findPwd">找回密码</option>
	                </select>
	            </div>
	           	<div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">来源</label>
	                <select class="select2" name="source" id="source" data-placeholder="来源...">
	                  <option value="">--来源</option>
	                  <option value="baiduTG">百度推广 </option>	          
	                </select>
	            </div>
	            <button type="submit" class="btn btn-primary">查询</button>
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover " id="table">
                    <thead>
                      <tr>
                        <th>手机号</th>
                        <th>验证码</th>
                        <th>生成时间</th>
                        <th>验证状态</th>
                        <th>失效时间</th>
                        <th>类型</th>
                        <th>来源</th>
                       <base:active activeId="AUTH_TIME">  <th>操作</th></base:active>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                        <td>${item.mobileNumber}</td>
                        <td>${item.authCode}</td>
                        <td><fmt:formatDate
									value="${item.generationTime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
                        <td>
                        <c:if test="${item.status eq '1'}">验证  </c:if>
                        <c:if test="${item.status eq '0'}">未验证  </c:if>
                        </td>
                        <td><fmt:formatDate
									value="${item.deadline }"
									pattern="yyyy-MM-dd HH:mm" /></td>
                        <td>
                        <c:if test="${item.type eq 'findPwd'}">找回密码  </c:if>
                        <c:if test="${item.type eq 'register'}">注册</c:if>
                        </td>
                        <td>
                        <c:if test="${item.source eq 'baiduTG'}">百度推广 </c:if>
                         <c:if test="${item.source eq 'youxinTG'}">有信推广 </c:if>
                        </td>
                        <base:active activeId="AUTH_TIME"> <td>
                        <a href="/authInfo/edit?id=${item.id}&&mobile=${item.mobileNumber }">延长失效时间</a>
                        </td></base:active>
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
  $("#st").val('${type}');
  $("#source").val('${source}'); 
});
</script>
</body>
</html>
