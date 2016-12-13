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

  <title>用户资金流水表</title>

 
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
      <h2><i class="fa fa-table"></i>当前位置：用户资金流水下载</h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/userbill/userbillList" method="post">
	             <div class="input-group" >
                <input type="text" class="form-control datepicker" name="start" value="${start}" placeholder="开始时间" id="start">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control datepicker" name="end" value="${end}" placeholder="结束时间" id="end">
                <span class="input-group-addon "><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                 <div class="input-group" >
					<select id="mySelect" name="type" style="width:250px;float:left;border:1px solid #ccc;height:41px;line-height:28px;border-radius:2px;margin-left:5px;">  
      								<c:forEach items="${list}" var="list">
      								<option value="${list.userId}">${list.userId}——${list.realname}</option>  
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
	           
	           <base:active activeId="USER_BILL_EXPORT"><button type="button" class="btn btn-primary" onclick="javascript:exportBill();">下载</button></base:active>
		        <script type="text/javascript">
		            function exportBill(){
		            	var userId=$("#mySelect").val();
		            	var start=$("#start").val();
		            	var end=$("#end").val();
		            	 location = "${ctx}/userbill/userbillListExport?userId="+userId+"&start="+start+"&end="+end; 
		            }
		        </script>
	          
	          </form>
	    </div>
	<!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
jQuery(document).ready(function(){
    // Date Picker
	  jQuery('.datepicker').datepicker();   
	  jQuery('#datepicker-inline').datepicker();
	  
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });
//添加按钮
	$("button[id=add]").click(function(){
					window.location.href="/userbill/toadd";
	});  
});
</script>
</body>
</html>
