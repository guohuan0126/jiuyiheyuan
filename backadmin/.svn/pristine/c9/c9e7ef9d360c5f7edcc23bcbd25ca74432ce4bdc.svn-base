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

  <title>系统数据统计</title>
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

div.all{margin:0 auto 10px;border-bottom:1px solid #ccc;padding-bottom:16px;}
div.all h2{padding:10px 0 0;font-size:24px;}
div.biao{overflow:hidden;}
.first,.two,.three,.four,.five{float:left;height:205px;margin-right:10px;}

.first .blue,.two .blue,.three .blue,.four .blue,.five .blue{width:30px;height:190px;position:relative;}
.blue .jindu{position:absolute;bottom:0;left:0;background:#428bcb;width:30px;}
.blue .txt{position:absolute;left:0;}


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
      <h2><i class="fa fa-table"></i>当前位置：系统数据统计</h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/sys/dataList/search" method="post">
	            <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
               
	            <button type="submit" class="btn btn-primary">查询</button>
	            <button type="button" id="today" class="btn btn-primary">今日</button>
	            <button type="button" id="yesterday" class="btn btn-primary">昨日</button>
	            <button type="button" id="week" class="btn btn-primary">最近一周</button>
	          </form>
	    </div>
	  <div class="panel-body"> 
	   <%--  <div class="row">
	   <h1>验证码数量:</h1>
        <c:forEach items="${authInfo}" var="item">
         <div class="col-xs-2">
                   <h3> <fmt:formatDate
									value="${item.generationTime }"
									pattern="yyyy-MM-dd" /></h3>
                   数量： ${item.num }
         </div>
         </c:forEach>
        </div>  --%>
  <div class="all">
	<h2>验证码数量</h2>
    <div class="biao">
       <c:forEach items="${authInfo}" var="item">
    	<div class="first">
            <div class="blue">
            <span style="position:absolute;left:0;bottom:${item.num/aNum*100+item.num%100-2 }px;">${item.num }</span>
            	<span style="position:absolute;bottom:0;left:0;background:#428bcb;width:30px;height:${item.num/aNum*100+item.num%100 }px;"></span>
            </div>
            <span><fmt:formatDate
									value="${item.generationTime }"
									pattern="MM/dd" /></span>
        </div>
       </c:forEach>
    </div>
</div>
   		<%-- <div class="row">
	   <h1>短信数量:</h1>
        <c:forEach items="${sms}" var="item">
         <div class="col-xs-2">
                   <h3> <fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd" /></h3>
                   数量： ${item.num }
         </div>
         </c:forEach>
        </div> --%>
        <div class="all">
	<h2>短信数量</h2>
    <div class="biao">
       <c:forEach items="${sms}" var="item">
    	<div class="first">
            <div class="blue">
            <span style="position:absolute;left:0;bottom:${item.num/smsNum*100}px;">${item.num }</span>
            	<span style="position:absolute;bottom:0;left:0;background:#428bcb;width:30px;height:${item.num/smsNum*100 }px;"></span>
            </div>
            <span><fmt:formatDate
									value="${item.time }"
									pattern="MM/dd" /></span>
        </div>
       </c:forEach>
    </div>
</div>
        <%-- <div class="row">
	   <h1>提现数量:</h1>
        <c:forEach items="${withdrawCash}" var="item">
         <div class="col-xs-2">
                   <h3> <fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd" /></h3>
                   数量： ${item.num }
         </div>
         </c:forEach>
        </div>	 --%>
          <div class="all">
	<h2>提现数量/金额</h2>
    <div class="biao">
       <c:forEach items="${withdrawCash}" var="item">
    	<div class="first">
            <div class="blue">
             <span style="position:absolute;left:0;font-size:10px;width:62px;bottom:${item.num/wNum*100+item.num%100+7 }px;">
               <c:if test="${item.actualMoney ge 10000}">
						<fmt:formatNumber value="${item.actualMoney / 10000}" maxFractionDigits="2"/><i>万</i>
				</c:if>
				<c:if test="${item.actualMoney lt 10000}">
					<fmt:formatNumber value="${item.actualMoney}" maxFractionDigits="2"/><i>元</i>
				</c:if>
             </span>
            <span style="position:absolute;left:0;font-size:10px;bottom:${item.num/wNum*100+item.num%100-3 }px;">${item.num }</span>
            	<span style="position:absolute;bottom:0;left:0;background:#428bcb;width:30px;height:${item.num/wNum*100+item.num%100 }px;"></span>
            </div>
            <span><fmt:formatDate
									value="${item.time }"
									pattern="MM/dd" /></span>
        </div>
       </c:forEach>
    </div>
</div>
        <%--  <div class="row">
	   <h1>充值数量:</h1>
        <c:forEach items="${recharge}" var="item">
         <div class="col-xs-2">
                   <h3> <fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd" /></h3>
                   数量： ${item.num }
         </div>
         </c:forEach>
        </div> --%>
         <div class="all">
	<h2>充值数量/金额</h2>
    <div class="biao">
       <c:forEach items="${recharge}" var="item">
    	<div class="first">
            <div class="blue">
            <span style="position:absolute;left:0;font-size:10px;width:62px;bottom:${item.num/rNum*100+item.num%100-25 }px;">
               <c:if test="${item.actualMoney ge 10000}">
						<fmt:formatNumber value="${item.actualMoney / 10000}" maxFractionDigits="2"/><i>万</i>
				</c:if>
				<c:if test="${item.actualMoney lt 10000}">
					<fmt:formatNumber value="${item.actualMoney}" maxFractionDigits="2"/><i>元</i>
				</c:if>
             </span>
            <span style="position:absolute;left:0;font-size:10px;bottom:${item.num/rNum*100+item.num%100-35 }px;">${item.num }</span>
            	<span style="position:absolute;bottom:0;left:0;background:#428bcb;width:30px;height:${item.num/rNum*100+item.num%100-30 }px;"></span>
            </div>
            <span><fmt:formatDate
									value="${item.time }"
									pattern="MM/dd" /></span>
        </div>
       </c:forEach>
    </div>
</div>
        <%-- <div class="row">
	   <h1>注册人数:</h1>
        <c:forEach items="${user}" var="item">
         <div class="col-xs-2">
                   <h3> <fmt:formatDate
									value="${item.registerTime }"
									pattern="yyyy-MM-dd" /></h3>
                   数量： ${item.num }
         </div>
         </c:forEach>
        </div> --%>
         <div class="all">
	<h2>注册人数</h2>
    <div class="biao">
       <c:forEach items="${user}" var="item">
    	<div class="first">
            <div class="blue">
            <span style="position:absolute;left:0;bottom:${item.num/uNum*100+item.num%100-2 }px;">${item.num }</span>
            	<span style="position:absolute;bottom:0;left:0;background:#428bcb;width:30px;height:${item.num/uNum*100+item.num%100 }px;"></span>
            </div>
            <span><fmt:formatDate
									value="${item.registerTime }"
									pattern="MM/dd" /></span>
        </div>
       </c:forEach>
    </div>
</div>
        <%-- <div class="row">
	   <h1>投资金额:</h1>
        <c:forEach items="${invest}" var="item">
         <div class="col-xs-2">
                   <h3> <fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd" /></h3>
                   数量： ${item.num }
         </div>
         </c:forEach>
        </div>	  --%>
         <div class="all">
	<h2>投资数量/金额</h2>
    <div class="biao">
       <c:forEach items="${invest}" var="item">
    	<div class="first">
            <div class="blue">
             <span style="position:absolute;left:0;font-size:10px;width:62px;bottom:${item.num/iNum*100+item.num%100+7 }px;">
               <c:if test="${item.money ge 10000}">
						<fmt:formatNumber value="${item.money / 10000}" maxFractionDigits="2"/><i>万</i>
				</c:if>
				<c:if test="${item.money lt 10000}">
					<fmt:formatNumber value="${item.money}" maxFractionDigits="2"/><i>元</i>
				</c:if>
             </span>
            <span style="position:absolute;left:0;font-size:10px;bottom:${item.num/iNum*100+item.num%100-3 }px;">${item.num }</span>
            	<span style="position:absolute;bottom:0;left:0;background:#428bcb;width:30px;height:${item.num/iNum*100+item.num%100 }px;"></span>
            </div>
            <span><fmt:formatDate
									value="${item.time }"
									pattern="MM/dd" /></span>
        </div>
       </c:forEach>
    </div>
</div>
 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
jQuery(document).ready(function(){
    // Date Picker
	  jQuery('#datepicker').datepicker();
	  jQuery('#datepicker1').datepicker();	  
	  jQuery('#datepicker-inline').datepicker();
	  
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });	
	  
$("#today").click(function(){
		window.location.href="/sys/dataList/today";
 });
 $("#yesterday").click(function(){
		window.location.href="/sys/dataList/yesterday";
 });
 $("#week").click(function(){
		window.location.href="/sys/dataList/week";
 });	  
});
</script>


</body>
</html>
