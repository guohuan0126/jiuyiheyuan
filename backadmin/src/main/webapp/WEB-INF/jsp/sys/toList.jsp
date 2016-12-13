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

  <title>易宝记录列表</title>


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
.only p{width:400px;height:100px;word-wrap:break-word;overflow-y:auto;}
.only_1{width:100px;display:block;word-wrap:break-word;    }
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
      <h2><i class="fa fa-table"></i>当前位置：易宝记录列表 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/sys/toList" method="post">
	    	  <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">类型</label>
	                <select class="select2" name="type" id="st" data-placeholder="类型...">
	                  <option value="">--类型</option>
	                  <option value="create_account">create_account</option>
	                  <option value="recharge">recharge</option>
	                  <option value="invest">invest</option>
	                   <option value="repay">"repay"</option>
	                  <option value="give_moeny_to_borrower">give_moeny_to_borrower</option>
	                  <option value="platform_transfer">platform_transfer</option>
	                  <option value="withdraw_cash">withdraw_cash</option>
	                   <option value="auto_invest">auto_invest</option>
	                   <option value="AUTHORIZEAUTOINVEST">AUTHORIZEAUTOINVEST</option>
	                    <option value="CANCEL_AUTHORIZE_AUTO_TRANSFER">CANCEL_AUTHORIZE_AUTO_TRANSFER</option>
	                </select>
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	              <input type="text" name="userId" value="${userId }" class="form-control" id="exampleInputEmail2" placeholder="用户编号/邮箱/手机号/身份证号">
	            </div>
	             <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
	            <button type="submit" class="btn btn-primary">查询</button>
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>请求操作ID</th>
                        <th>操作类型</th>
                        <th>请求时间</th>
                        <th>响应时间</th>
                        <th>请求数据</th>
                        <th>响应数据</th>
                        <th>操作者</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                       <td>${item.markId }</td>
                       <td class="only_1">${item.type }</td>
                       <td><fmt:formatDate
									value="${item.requestTime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
						<td><fmt:formatDate
						value="${item.responseTime }"
						pattern="yyyy-MM-dd HH:mm" /></td>
						<td class="only">
						<textarea rows="8" cols="50">${item.requestData}</textarea>
						
						
						</td>
                        <td style="width:100px">
                        <textarea rows="8" cols="50">${item.responseData} </textarea>
                        
                        </td>
                       <td>${item.userId }</td>
                       
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
