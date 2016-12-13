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
  <title>活动类型列表</title>
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
      <h2><i class="fa fa-table"></i>当前位置：活动类型列表</h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	   <a href="/redPacketRule/goAdd" class="btn btn-primary">添加</a>
	</div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th>编号</th>
                <th>活动名称</th>
                <th>发送类型</th>
                <th>活动类型</th>                
                <th>活动规则</th>
                <th>操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="rule" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${rule.id}</td>
				 <td>${rule.name}</td>
				 <td>${rule.sendType}</td>
				 <td>${rule.type}</td>
				 <td>${rule.getRule}</td>
				 <td><a href="/redPacketRule/goUpdate?id=${rule.id}" >修改</a>
				     <a href="javascript:del(${rule.id});" >删除</a></td>
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
		  window.location.href="/redPacketRule/delete?id="+id;
	  }
  }
 </script>
</section>

</body>
</html>
