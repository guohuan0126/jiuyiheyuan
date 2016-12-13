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
  <title>手机APPBanner图管理</title>
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
      <h2><i class="fa fa-table"></i>当前位置：手机APPBanner图管理</h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="/appBanner/list" method="post">		    	 
	            <label class="control-label">状态 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="picStatus" name="picStatus" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="1"  <c:if test="${picStatus == 1}">selected="selected"</c:if> >启动</option>
                      <option value="0" <c:if test="${picStatus == 0}">selected="selected"</c:if> >禁用</option>                
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
            	<input type="hidden" name="pageSize" value="10" />                       
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	            <a href="/appBanner/goAdd" class="btn btn-primary">添加</a>	           
	          </form>
	          
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th>编号</th>
                <th>标题</th>
                <th>地址</th>                
                <th>页面地址</th>
                <th>排序</th>
                <th>状态</th>
                <th>描述</th>
                <th>日期</th>
                <th>操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="appBanner" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${appBanner.picID}</td>
				 <td>${appBanner.picName}</td>
				 <td>
				 <img alt="${appBanner.picName}" src="${appBanner.picPosition}" class="img-thumbnail" style="width: 100px;height: 100px;" />
				 </td>
				 <td>${appBanner.picUrl}</td>
				 <td>${appBanner.seqNum}</td>
				 <td>
				 <c:if test="${appBanner.picStatus == '0'}">禁用</c:if>
				 <c:if test="${appBanner.picStatus == '1'}">启动</c:if>
				 </td>
				 <td>${appBanner.description}</td>
				 <td><fmt:formatDate value="${appBanner.time}" type="both"/></td>
				 <td><a href="/appBanner/goUpdate?picID=${appBanner.picID}" >修改</a>
				     <a href="/appBanner/delete?picID=${appBanner.picID}" >删除</a>
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

</body>
</html>
