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
  <title>活动专题管理</title>
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
      <h2><i class="fa fa-table"></i>当前位置：活动专题管理</h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="/activity/list" method="post">		    	 
	            <label class="control-label">状态 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="status" name="status" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="1"  <c:if test="${status == '1'}">selected="selected"</c:if> >启用</option>
                      <option value="2" <c:if test="${status == '2'}">selected="selected"</c:if> >禁用</option>                
                    </select>              
                </div>
                <label class="control-label">是否结束 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="isEnd" name="isEnd" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="1"  <c:if test="${isEnd == '1'}">selected="selected"</c:if> >进行中</option>
                      <option value="2" <c:if test="${isEnd == '2'}">selected="selected"</c:if> >已结束</option>                
                    </select>              
                </div>
                <label class="control-label">类型 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="type" name="type" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="1"  <c:if test="${type == '1'}">selected="selected"</c:if> >活动</option>
                      <option value="2" <c:if test="${type == '2'}">selected="selected"</c:if> >专题</option>                
                    </select>              
                </div>	
                 <script type="text/javascript">
                    	jQuery(".select").select2({
							width : '100%',
							minimumResultsForSearch : -1
						});               
                    </script>
                <label class="control-label">上传时间：</label>             
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" value="${startTime}" id="startTime" name="startTime" placeholder="开始时间" style="width:165px">
               
                </div> -- 
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" value="${endTime}" id="endTime" name="endTime" placeholder="结束时间" style="width:165px">
                
                </div>
	            <script type="text/javascript">	            		            
	            jQuery(document).ready(function(){					
					 jQuery('.datepicker').datetimepicker({
						showSecond : true,
						timeFormat : 'hh:mm:ss',
						stepHour : 1,
						stepMinute : 1,
						stepSecond : 1,
					});				 
				});

            	</script> 
            	<input type="hidden" name="pageSize" value="30" />                      
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	            <a href="/activity/goAdd" class="btn btn-primary">添加</a>	           
	          </form>
	          
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th>编号</th>
                <th>标题</th>
                <th>内容</th>                
                <th>图片</th>
                <th>地址</th>
                <th>活动时间</th>
                <th>设备分类</th>
                <th>状态</th>
                <th>是否结束</th>
                <th>类型</th>
                <th>创建时间</th>
                <th>创建用户</th>
                <th>操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="act" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${act.id}</td>
				 <td>${act.title}</td>
				 <td><textarea readonly="readonly" rows="5" cols="20">${act.content}</textarea></td>
				 <td>
			 	 <img alt="${act.title}" src="${act.imageUrl}" class="img-thumbnail" style="width: 100px;height: 100px;" />
				 </td>
				 <td>${act.url}</td>
				 <td>${act.startTime}<c:if test="${!empty act.endTime}">至<br>${act.endTime}</c:if><c:if test="${empty act.endTime}">起</c:if></td>
				 <td>
				 <c:if test="${act.target == '1'}">PC</c:if>
				 <c:if test="${act.target == '2'}">APP</c:if>
				 </td>
				 
				 <td>
				 <c:if test="${act.status == '1'}">启用</c:if>
				 <c:if test="${act.status == '2'}">禁用</c:if>
				 </td>
				 <td>
				 <c:if test="${act.isEnd == '1'}">进行中</c:if>
				 <c:if test="${act.isEnd == '2'}">已结束</c:if>
				 </td>
				 <td>
				 <c:if test="${act.type == '1'}">活动</c:if>
				 <c:if test="${act.type == '2'}">专题</c:if>
				 </td>
				 <td><fmt:formatDate value="${act.createTime}" type="both"/></td>
				 <td>
				 <c:if test="${empty act.user.realname }">${act.user.userId }</c:if>${act.user.realname }</td>
				 <td><a href="/activity/goUpdate?id=${act.id}" >修改</a>
				     <a href="javascript:del(${act.id});" >删除</a>
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

 <script type="text/javascript">
  function del(id){
	  if(confirm("您确定要删除吗？")){
		  window.location.href="/activity/delete?id="+id;
	  }
  }
 </script>
</section>

</body>
</html>
