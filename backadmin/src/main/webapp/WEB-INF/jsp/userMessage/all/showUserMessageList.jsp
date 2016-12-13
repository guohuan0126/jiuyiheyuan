<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
  <title>短信模板管理</title>
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
      <h2><i class="fa fa-table"></i>当前位置：短信模板列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li></li>
        </ol>
      </div> 
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="/userMessageList/show" method="post">
		    	 
		    	<div class="form-group">              
	              <input type="text" name="id" value="${id}" class="form-control" placeholder="短信模板号">
	            </div>   
	            <div class="form-group">              
	              <input type="text" name="name" value="${name}" class="form-control" placeholder="名称">
	            </div>                  
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	          </form>
	          <a href="${ctx}/toCreateOrEditUserMessage/create" class="add">创建模板内容</a>
	    </div>  
	    
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">         
             	<th >短信模板号</th> 
             	<th >名称</th>
             	<th >模板</th>     
                <th >描述</th>
                <th >操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="item" items="${pageInfo.results}" varStatus="status">
		  
		     <tr class="font">		     	
		     	  
				 <td>${item.id}</td>
				 <td>${item.name }</td>
				 <td><textarea cols="80" rows="5">${item.template }</textarea></td>
				 <td>${item.description}</td>
				 <td>
				 	 <a href="${ctx}/toCreateOrEditUserMessage/edit?param=${item.id}">编辑</a> 
				 <base:active activeId="23f4e6998b7a44b198e3497d37ba8f54">
				 	 <a href="javascript:del('${item.id}');">删除</a>
				 </base:active> 
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
 <script type="text/javascript">
  function del(id){
	   if(confirm("您确定要删除吗？")){ 
		  $.ajax({
				type : "POST",
				url : '/userMessage/delete',
				data : {"id":id},//  

				success : function(data) {
					if (data == 'ok') {
						alert('操作成功');
						window.location = "/userMessageList/show";
					}else{
						alert('操作失败');
					}
				}
			});
			
	   } 
	   }
 </script>
</body>
</html>