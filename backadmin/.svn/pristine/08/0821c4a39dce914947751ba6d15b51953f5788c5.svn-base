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

  <title>自动投标管理</title>

  

  
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
      <h2><i class="fa fa-table"></i>当前位置：自动投标管理 </h2>
     
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/autoInvest/autoInvestList" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	              <input type="text" name="userId" value="${userId }" class="form-control" id="exampleInputEmail2" placeholder="用户编号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">用户名</label>
	              <input type="text" name="realname" value="${realname }" class="form-control" id="exampleInputPassword2" placeholder="用户名">
	            </div>
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">手机号</label>
	              <input type="text" name="mnumber" value="${mnumber}" class="form-control" id="exampleInputmobile" placeholder="手机号">
	            </div>
	             
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">自动投标状态</label>
	                <select class="select2" name="status" id="st" data-placeholder="自动投标状态...">
	                  <option value="">--自动投标状态</option>
	                  <option value="on"  selected = "selected">开启</option>
	                  <option value="off">关闭</option>
	                </select>
	            </div>
	           
	            <button type="submit" class="btn btn-primary">查询</button>
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>用户编号</th>
                        <th>用户名</th>
                        <th>投标金额</th>
                        <th>上次自动投标时间</th>
                        <th>贷款类型</th>
                        <th>借款的最短时间</th>
                        <th>借款的最长时间</th>
                        <th>借款的最小利率</th>
                        <th>借款的最大利率</th>
                        <th>状态</th>
                       
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                        <td>${item.userId }</td>
                        <td>${item.user.realname }</td>
                        <td>${item.investMoney }</td>
                        <td><fmt:formatDate
									value="${item.lastAutoInvestTime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
                        <td>${item.loanType }</td>
                        <td>${item.minDeadline }</td>
                        <td>${item.maxDeadline}</td>
                        <td>${item.minRate}</td>
                        <td>${item.maxRate}</td>
                        <td>
                        ${item.status}
                       <%--   <c:if test="${item.status eq 'wait_verify'}">
                       		等待提现
                        </c:if>
                        <c:if test="${item.status eq 'success'}">
                       		提现成功
                        </c:if> --%>
                        </td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
 		<%@ include file="../base/page.jsp"%>

 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
jQuery(document).ready(function(){
    if($("#st").val()!='on'){
      $("#st").val('${status}');
    }else{
     $("#st").val('on');
    }
});
</script>
</body>
</html>
