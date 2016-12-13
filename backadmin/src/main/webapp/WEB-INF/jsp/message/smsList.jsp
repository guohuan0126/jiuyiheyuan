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

  <title>短信历史管理</title>


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
      <h2><i class="fa fa-table"></i>当前位置：短信历史消息管理 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/sms/smsList" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	              <input type="text" name="userId" value="${userId }" class="form-control" id="exampleInputEmail2" placeholder="用户编号/手机号/邮箱/身份证号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">用户名</label>
	              <input type="text" name="realname" value="${realname }" class="form-control" id="exampleInputPassword2" placeholder="用户名">
	            </div>
	             <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> &nbsp;
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">类型</label>
	                <select class="select2" name="type" id="st" data-placeholder="提现状态...">
	                  <option value="">--类型</option>
	                  <option value="repay">还款</option>
	                  <option value="invest">投资</option>
	                  <option value="give_moeny_to_borrower">放款</option>
	                  <option value="award">奖励</option>
	                  <option value="withdraw_cash">提现</option>
	                  <option value="recharge">充值</option>
	                  <option value="without">without</option>
	                  <option value="wechat11">wechat11</option>
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
                        <th>手机号</th>
                        <th>用户真实姓名</th>
                        <th>内容</th>
                        <th>时间</th>
                        <th>类型</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                       <td>${item.userId }</td>
                       <td>${item.mobileNumber }</td>
                       <td>${item.user.realname }</td>
                       <td>${item.content}</td>
                       <td><fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd HH:mm" /></td>
                        <td>
	                        <c:choose>
	                           <c:when test="${item.type eq 'repay'}">
	                           		 还款
	                           </c:when>
	                           <c:when test="${item.type eq 'invest'}">
	                       		             投资
	                           </c:when>
		                        <c:when test="${item.type eq 'give_moeny_to_borrower'}">
		                       		 放款
		                        </c:when>
		                        <c:when test="${item.type eq 'withdraw_cash'}">
		                       		提现
		                        </c:when>
		                        <c:when test="${item.type eq 'award'}">
		                       		 奖励
		                        </c:when>
		                        <c:when test="${item.type eq 'recharge'}">
		                       		 充值
		                        </c:when>
	                            <c:otherwise>
	                            	${item.type}
	                            </c:otherwise>
	                        </c:choose>
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
    // Date Picker
	  jQuery('#datepicker').datepicker();
	  jQuery('#datepicker1').datepicker();	  
	  jQuery('#datepicker-inline').datepicker();
	  
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });		  
	$("#st").val('${type}');
});
</script>
</body>
</html>
