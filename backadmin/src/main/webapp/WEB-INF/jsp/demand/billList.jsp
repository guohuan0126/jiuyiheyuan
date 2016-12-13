<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
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
  <title>转入转出查询</title>
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
      <h2><i class="fa fa-table"></i>当前位置：转入转出查询</h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="<%=basePath%>demand/billList" method="post">		    	 
	            <label class="control-label">类型：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="type" name="type" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="in"  <c:if test="${bill.type == 'in'}">selected="selected"</c:if> >转入</option>
                      <option value="out" <c:if test="${bill.type == 'out'}">selected="selected"</c:if> >转出</option>
                      <option value="interest" <c:if test="${bill.type == 'interest'}">selected="selected"</c:if> >派息</option>  
                      <option value="outinterest" <c:if test="${bill.type == 'outinterest'}">selected="selected"</c:if> >转出派息</option>              
                    </select>              
                </div>
                <label class="control-label">状态：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="status" name="status" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="success"  <c:if test="${bill.status == 'success'}">selected="selected"</c:if> >success</option>
                      <option value="fail" <c:if test="${bill.status == 'fail'}">selected="selected"</c:if> >fail</option>                
                    </select>              
                </div>
                <label class="control-label">途径 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="tranferWay" name="tranferWay" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="pc"  <c:if test="${bill.tranferWay == 'pc'}">selected="selected"</c:if> >pc</option>
                      <option value="mobile" <c:if test="${bill.tranferWay == 'mobile'}">selected="selected"</c:if> >mobile</option>
                      <option value="ios" <c:if test="${bill.tranferWay == 'ios'}">selected="selected"</c:if> >ios</option>
                      <option value="andriod" <c:if test="${bill.tranferWay == 'andriod'}">selected="selected"</c:if> >andriod</option>               
                    </select>              
                </div>	
                 <script type="text/javascript">
                    	jQuery(".select").select2({
							width : '100%',
							minimumResultsForSearch : -1
						});               
                    </script>
                <label class="control-label">申请时间：</label>             
	            <div class="input-group" >
	            
                <input type="text" class="form-control datepicker" value="<fmt:formatDate value="${bill.startTime}" pattern="yyy-MM-dd HH:mm:ss" />" id="startTime" name="startTime" placeholder="开始时间" style="width:165px">
               
                </div> -- 
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" value="<fmt:formatDate value="${bill.endTime}" pattern="yyy-MM-dd HH:mm:ss" />" id="endTime" name="endTime" placeholder="结束时间" style="width:165px">
                
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
                <th>用户编号</th>
                <th>用户姓名</th>
                <base:active activeId="USER_LOOK">
                <th>用户手机号</th>
                </base:active>                
                <th>金额</th>
                <th>申请时间</th>
                <th>类型</th>
                <th>途径</th>
                <th>状态</th>
                <th>描述</th>
                <th>备注</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="b" items="${pageInfo.results}">
		       <tr class="font">		     	
				 <td>${b.userId}</td>
				 <td>${b.user.realname}</td>
				 <base:active activeId="USER_LOOK">
				 <td>${b.user.mobileNumber}</td>
				 </base:active>
				 <td>${b.money}</td>
				 <td><fmt:formatDate value="${b.createTime }" type="both"/></td>
				 <td>
				 <c:if test="${b.type == 'in'}">转入</c:if>
				 <c:if test="${b.type == 'out'}">转出</c:if>
				 <c:if test="${b.type == 'interest'}">派息</c:if>
				 </td>
				 <td>${b.tranferWay}</td>
				 <td>${b.status}</td>
				 <td>${b.detail }</td>
				 <td>${b.info }</td>
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
