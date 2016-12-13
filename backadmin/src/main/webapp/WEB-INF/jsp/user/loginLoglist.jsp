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
      <h2><i class="fa fa-table"></i>当前位置：用户登录日志查看</h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="/user/loginLoglist" method="post">		    	 
	            <label class="control-label">状态 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="isSuccess" name="isSuccess" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="1"  <c:if test="${isSuccess == 1}">selected="selected"</c:if> >登录成功</option>
                      <option value="0" <c:if test="${isSuccess == 0}">selected="selected"</c:if> >登录失败</option>                
                    </select>              
                </div>
                 <script type="text/javascript">
                    	jQuery(".select").select2({
							width : '100%',
							minimumResultsForSearch : -1
						});               
                   </script>
                <label class="control-label">登录IP:</label>
	            <div class="form-group" style="width: 110px;">              	           
                     <input type="text" class="form-control" value="${loginIp}" id="loginIp" name="loginIp" placeholder="输入登录IP" style="width:120px">            
                </div>	
                <label class="control-label">用户ID:</label>
	            <div class="form-group" style="width: 110px;">              	           
                     <input type="text" class="form-control" value="${userId}" id="userId" name="userId" placeholder="输入用户ID" style="width:120px">            
                </div>	
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
	                 
	          </form>
	          
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th>编号</th>
                <th>登录时间</th>
                <th>登录IP</th>                
                <th>状态</th>
                <th>用户ID</th>
                <th>信息</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="log" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${log.id}</td>
				 <td><fmt:formatDate value="${log.loginTime}" type="both"/></td>
				 <td>${log.loginIp }</td>
				 <td>
				 <c:if test="${log.isSuccess == '0'}">登录失败</c:if>
				 <c:if test="${log.isSuccess == '1'}">登录成功</c:if>
				 </td>
				 <td>${log.userId }</td>
				 <td>${log.loginInfo }</td>
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