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
      <h2><i class="fa fa-table"></i>当前位置：文章列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li></li>
        </ol>
      </div>
    </div>  
    
    
    
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="${cxt}/article/nodeList" method="post">		    	 
	            <div class="form-group">	              
	              <input type="text" name="title"  class="form-control" placeholder="标题">
	            </div>
	           <div class="form-group">
		            <select name="status" class="form-control">
		            	<option value="">全部</option>
		            	<option value="1">发表</option>
						<option value="0">未发表</option>	                   			                                     
					</select>	     
				</div>   
				 <div class="form-group">
		            <span style="font-size: 15px">是否推送app : </span>
		           
		            <select name="flag" class="form-control">
		            	<option value=" ">全部</option>
						<option value="1"<c:if test="${flag == '1'}">selected="selected"</c:if>>app推送</option>	
					</select>	     
				</div>
				<div class="form-group">
		            <select id="CategoryTerm"name="CategoryTerm" class="form-control">
		            	<option value="">全部</option>
		            	<c:forEach items="${categoryTermList}" var="categoryTerm">
						<option value="${categoryTerm.id}">${categoryTerm.name}</option>	  
						</c:forEach>                 			                                     
					</select>	     
				</div>     
				<script type="text/javascript">
							
								var v = '${type}';
							
								 
							 var obj = document.getElementsByTagName("option");
								 for(var i=0;i<obj.length;i++){
										if(obj[i].value==v){
											obj[i].selected=true;  //相等则选中
										}
									}
							</script>          
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	          </form>
	          <a href="${ctx}/article/toCreateNode/create" class="add">创建文章</a>
	    </div>  
	    
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">         
             	<th>序号</th>     
                <th>标题</th>
                <th style="width: 100px;">发表状态</th>                
                <th>描述</th> 
                <th>创建时间</th> 
                <th>是否给app推送</th>               
                <th>创建人</th> 
                <th>最后更新时间</th>                
                <th>最后更新人</th>
                  <th>编号</th>
                <th>操作</th>
              
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="node" items="${pageInfo.results}" varStatus="status">
		     <tr class="font">		     	
		     	 <td>${status.index}</td>
				 <td>${node.title}</td>
				 <td><c:if test="${node.status == 0}">未发表</c:if>
				 	<c:if test="${node.status == 1}">发表</c:if>
				 </td>
				 <td>${node.description}</td>
				 <td><fmt:formatDate value="${node.createTime}" type="both"/></td>
				 <td>
				 	<c:if test="${node.flag==1}">是</c:if>
				 	<c:if test="${node.flag==0}">否</c:if>
				 </td>
				 <td>${node.creator}</td>
				 <td><fmt:formatDate value="${node.updateTime }" type="both"/></td>
				 <td>${node.lastModifyUser}</td>
				  <td> <input id="${node.id}" style="width: 30px" type="text" value="${node.sortNum}" />
				  		<input type="button" onclick="editSort('${node.id}')" value="保存"></input>
				  </td>
				 <td>
				    <c:if test="${!empty node.terms}">
				        <a href="http://www.duanrong.com/node/${node.terms[0].id}/${node.id}.htm" target="_blank" >预览</a>
				    </c:if>
				    <c:if test="${empty node.terms}">
				        <a href="http://www.duanrong.com/node/${node.id}.htm" target="_blank" >预览</a>
				    </c:if>
				 	<a href="${ctx}/article/toCreateNode/edit?param=${node.id}">编辑</a>
				 	 
				 </td>
		     </tr>
		 </c:forEach> 
         </tbody>          
        </table>       	
        <%@ include file="../../base/page.jsp"%>
        <script type="text/javascript">
        		function editSort(nodeId){
        			var sortNum=$("#"+nodeId).val();
        			$.ajax({
        				type : "post",
        				url : "/article/nodeList/toEditSortNum",
        				data : {
        					"id" : nodeId,
        					"sortNum":sortNum
        				},
        				dataType : "text",
        				success : function(data) {
        					if(data=="success"){
								alert("修改成功");
								window.location.href="/article/nodeList";
							}else{
								alert("修改失败");
							}
        					
        				},
        				error : function() {

        					console.info("调取失败");
        				}
        			});
        		}
        
        </script>
 </div>
 </div>
 </div>
 </div>
  
</section>

</body>
</html>
