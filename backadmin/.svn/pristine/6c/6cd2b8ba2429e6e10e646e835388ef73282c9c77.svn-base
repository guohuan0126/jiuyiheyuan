<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.duanrong.business.flow.model.ItemFlow" %>
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
  <title>系统参数</title>
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
      <h2><i class="fa fa-table"></i>当前位置：系统配置参数 </h2>       
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    <form class="form-inline"  action="${ctx}/user/config/configList" method="post">
	    	 	<div class="form-group">	              
	              <input type="text" name="id"  class="form-control" placeholder="编号">
	            </div>
	    	 	<div class="form-group">	              
	              <input type="text" name="name"  class="form-control" placeholder="名称">
	            </div>		    	  
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	    </form>
	    <a href="${cxf}/user/config/toInsert" class="add">添加系统参数</a>
	 </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">             
                <th width="10%">编号</th>
                <th width="20%">名称</th>
                <th width="30%">值</th>
                <th width="10%">描述</th>
                <th width="10%">类型</th>                
                <th width="10%">操作</th>
             </tr>
          </thead>
          <tbody>
		     <c:forEach  var="config" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${config.id}</td>
				 <td>${config.name }</td>
				 <td>${config.value }</td>
				 <td>${config.description}</td>
				 <td>${config.type }</td>							 				 				 		 								 
				 <td>
				 	<a href="${ctx}/user/config/toUpdate?id=${config.id}">修改</a>				 
				 </td>
		     </tr>
		     </c:forEach>
         </tbody>          
        </table> 
        <script type="text/javascript">     	
        function oper(id, message, status, itemId, itemType,nodeId){	
			$.ajax({
				type : 'POST',
				url : '/award/operAward',						
				data:{
					id:id,
					status:status,
					message:message,
					itemId: itemId,
					itemType: itemType,
					nodeId: nodeId
				},
				success:function(data) {
					if(data == 'sendOff'){
						return;
					}else{					
						location="/award/awardList/unfinance"; 
					}
				},
				error : function() {
					alert("异常！");
				}
			});
		}
		
		 
        </script>
        	
        <%@ include file="../../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>
 
</section>



</body>
</html>
