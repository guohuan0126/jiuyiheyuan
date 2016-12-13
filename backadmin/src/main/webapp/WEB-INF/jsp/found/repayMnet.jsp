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
  <title>还款记录</title>
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
      <h2><i class="fa fa-table"></i>当前位置：还款记录 </h2>     
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/repay/repayMentRecods" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	             	<input type="text" id="userId" value="${userId}" name="userId" placeholder="用户编号/手机号" style="width:190px;">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">用户来源</label>
	             	<input type="text" id="userSource" value="${userSource}" name="userSource" placeholder="用户来源" style="width:190px;">
	            </div>
                 <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="还款开始时间" id="start">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="还款结束时间" id="end">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
	           
	            <button type="submit" class="btn btn-primary">查询</button>
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                      <th>真实姓名/用户编号/ 手机号码/用户来源/注册时间
						</th>
						<th>项目名称</th>
						<th>项目周期</th>
						<th>投资时间</th>
						<th>投资金额</th>
						<th>项目状态</th>
						<th>预计还款时间</th>
						<th>即将还款本金</th>
						<th>即将还款利息</th>
						<th>项目结束时间</th>
						<th>本次使用红包金额</th>
						<th>加息额度</th>
						<th>投资次数</th>
						<th>可用红包金额</th>
						<th>可用加息券额度</th>
                      </tr>
                    </thead>
                    <tbody>
                  <c:forEach items="${pageInfo.results}" var="repay" varStatus="Num">
                      <tr>
                       <td><c:if test="${ empty repay.realName}">
							无</br>
						${repay.userId }<br />${repay.mobileNumber }</br>${repay.userSource }</br><fmt:formatDate
								value="${repay.registerTime }"
								pattern="yyyy-MM-dd HH:mm" />
						</c:if>
						<c:if test="${not  empty repay.realName}">
						${repay.realName }</br>
						${repay.userId }<br />${repay.mobileNumber }</br>${repay.userSource }</br><fmt:formatDate
								value="${repay.registerTime }"
								pattern="yyyy-MM-dd HH:mm" />
						</c:if>
						</td>
						<td>${repay.loanName}</td>
						<td>${repay.loanTime}</td>
						<td><fmt:formatDate
								value="${repay.investTime }"
								pattern="yyyy-MM-dd HH:mm" /><br />
							</td>
						<td>${repay.investMoney}</td>
						<%-- <td>${repay.interest}</td> --%>
						<td>${repay.loanStatus }</td>
						<td><fmt:formatDate
								value="${repay.repayData }"
								pattern="yyyy-MM-dd HH:mm" /></td>
							<td>${repay.repayMoeny}</td>
							<td>${repay.repayInterest}</td>
							<td><fmt:formatDate
								value="${repay.loanFinishTime }"
								pattern="yyyy-MM-dd HH:mm" /></td>
                      
                      <td>
							${repay.redpacketMoney}
                      </td>
                      <td>
							${repay.redpacketRate}
                      </td>
                      
                      <td>
							${repay.investCount}
                      </td>
                          <td>
							${repay.usableRedpacketMoney}
                      </td>
                          <td>
							${repay.usableRedpacketRate}
                      </td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
       	回款人数${peopleCount}</br>
       	回款金额<fmt:formatNumber value="${repayMoney}" maxFractionDigits="2"/>元
 		<%@ include file="../base/page.jsp"%>

 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
$(function(){
	var myDate = new Date();
	if ('${start}' == '') {
		var last = myDate.toLocaleDateString();
		$("#start").val(last);
		$("#end").val(last);
		
	}
});




jQuery(document).ready(function(){
    // Date Picker
	  jQuery('#start').datepicker();
	  jQuery('#end').datepicker();
	  jQuery('#datepicker-inline').datepicker();
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });		  
});
</script>
</body>
</html>
