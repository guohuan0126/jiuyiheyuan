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
  <title>放款列表</title>
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
      <h2><i class="fa fa-table"></i>当前位置：放款列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
    	<div>
	    	 <form class="form-inline"  id="form1" action="/loan/loanMenu" method="post">		    	 
		    	     
	            <div class="input-group" >
                <input type="text" class="form-control"  name="start" value="${start}" placeholder="开始时间" id="datepicker" style="width:100px">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
	            <div class="input-group" >
                <input type="text" class="form-control"  name="end" value="${end}" placeholder="结束时间" id="datepicker1" style="width:100px">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                 <div id="error" style="display: none;">时间必须选择,天差不能超过31天</div>
                </div>               
	            <script type="text/javascript">	            		            
	            jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});					  
				jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	  
            	</script>                       
	            <button type="button" id="search" class="btn btn-primary" >查询</button>
				<button id="export" type="button"  class="btn btn-primary">导出数据</button>	          </form>
	    </div>
	 </div>
	  
     <div class="panel-body"> 
     
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">                              
                <th>所属账户</th>
                <th>所属账户真实姓名</th>
                <th>项目编号</th>
                <th>项目名称 </th>
                <th>项目类型</th>
                <th>真实贷款人姓名</th>
                <th>车型</th>
                <th>车牌号</th>
                <th>放款时间</th>
                <th>放款金额</th>
                <th>扣款金额</th>
                <th>还款时间</th>
                <th>还款金额</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="loan" items="${pageInfo.results}">
		     <tr class="font">				
				 
				 <td>${loan.borrowMoneyUserID}</td>
				 <td>${loan.borrowMoneyUserName}</td>			 
				 <td>${loan.id}</td>
				 <td>${loan.name}</td>
				 <td>${loan.loanType}</td>
				 <td>${loan.borrowerName}</td>
				 <td>${loan.brand}</td>
				 <td>${loan.licensePlateNumber}</td>
				 <td><fmt:formatDate value="${loan.giveMoneyOperationTime}" type="both"/> </td>
				 <td>${loan.totalmoney}</td>
				 <td>${loan.loanGuranteeFee}</td>
				 <td><fmt:formatDate value="${loan.maxrepayday}" type="both"/> </td>
				 <td>${loan.maxreapymoney}</td>
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
 $("button[id=export]").click(function(){
	 var date1=$("#datepicker").val();  //开始时间
	 var date2=$("#datepicker1").val();    //结束时间
	
	 if (date1=='' || date2=='' ) {
			$('#error').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			 var date3=new Date(date2).getTime()-new Date(date1).getTime();  //时间差的毫秒数
			 //计算出相差天数
			 var days=Math.floor(date3/(24*3600*1000));
			 if(days>30){
				 $('#error').attr("style", "display:block;font-size:14px;color:red");
					return false;
			 }
			$("#form1").attr("action","/loan/exportList");
		    $("#form1").submit();
		}
      });
 $("button[id=search]").click(function(){
	 var date1=$("#datepicker").val();  //开始时间
	 var date2=$("#datepicker1").val();    //结束时间
	
	 if (date1=='' || date2=='') {
			$('#error').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			 var date3=new Date(date2).getTime()-new Date(date1).getTime();  //时间差的毫秒数
			 //计算出相差天数
			 var days=Math.floor(date3/(24*3600*1000));
			 if(days>30){
				 $('#error').attr("style", "display:block;font-size:14px;color:red");
					return false;
			 }
		$("#form1").attr("action","/loan/loanMenu");
	    $("#form1").submit();
	 }
	});
</script>
</body>
</html>
               