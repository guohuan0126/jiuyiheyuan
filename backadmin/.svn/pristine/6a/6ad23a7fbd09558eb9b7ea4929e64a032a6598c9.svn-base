<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <link href="${ctx}/css/flow/flow.css" rel="stylesheet"/>
   <link href="${ctx}/css/style.default.css" rel="stylesheet">
  <title>工作流管理</title>
</head>
<body>
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section> 
 <%@include file="../base/leftMenu.jsp"%> 
  <div class="mainpanel">   
	<%@include file="../base/header.jsp"%>      
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：工作流列表 </h2>
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li></li>
        </ol>
      </div>
    </div>  
    <div class="contentpanel ">
    <div class="panel panel-default">
    <div class="panel-heading">	    
	    <a href="toAddFlow" class="add">新建工作流模板</a>
	 </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">
               <th width="10%">编号</th>
               <th width="20%">名称</th>
               <th width="30%">描述</th>
               <th width="10%">状态</th>
               <th width="30%">操作</th>
             </tr>
          </thead>
          <tbody>
		     <c:forEach  var="flow" items="${pageInfo.results}">
		     <tr class="font">
		        <td>${flow.flowId}</td>
		         <td>${flow.flowName }</td>
		         <td>${flow.description}</td>
		         <td>${flow. status}</td>
		         <td>
		         <%-- <a href="/getFlowById?id=${item.id}">查看</a> --%>
		         <a href="/flow/getFlowById?flowId=${flow.flowId}">修改</a>
		        
		         &nbsp;&nbsp;|&nbsp;&nbsp;
		         <a style="cursor:pointer" onclick="deleteFlow('/flow/deleteFlowById?flowId=${flow.flowId}');">删除</a>	         
		         </td>
		         </tr>
		         </c:forEach>
            </tbody>          
        </table>    
        <%@ include file="../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>
</section>
<script type="text/javascript">
	function deleteFlow(url){
		$.ajax({			
			type : 'POST',			
			url : url, 			
			success : function(msg) {
					if(msg == 'ok'){
						alert("删除成功!");					
					}else{
						alert("删除失败!");					
					}
					window.location = "getFlowList";
				},
				error : function() {
					alert("异常！");
				}
		});	
	}


</script>
</body>
</html>
