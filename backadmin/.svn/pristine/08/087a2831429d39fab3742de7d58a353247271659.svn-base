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

  <title>保障金</title>
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
      <h2><i class="fa fa-table"></i>当前位置：保障金 </h2>
     
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>类型</th>
                        <th>交易总量</th>
                        <th>待收总额</th>
                        <th>累计逾期金额</th>
                        <th>累计垫付金额</th>
                        <th>时间</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${risks}" var="item">
                      <tr>
                      <td>${item.type }</td>
                      <c:if test="${item.type ne 'fund'}">
                        <td>
                        <c:if test="${item.totalmoney ge 10000}">
									<fmt:formatNumber value="${item.totalmoney / 10000}" maxFractionDigits="3"/>万
									</c:if>
						<c:if test="${item.totalmoney lt 10000}">
							<fmt:formatNumber value="${item.totalmoney}" maxFractionDigits="3"/>元
						</c:if>
                        </td>
                       </c:if>
                       <c:if test="${item.type eq 'fund'}">
                       <td>
                       <form class="form-inline" action="/user/savereferrer" id="form1"  method="post">
                         <input type="text" class="refe"  name="totalmoney"   value="${item.totalmoney}"  id="exampleInputcard" placeholder="金额">
			             <div id="errortotalmoney" style='display: none;'>金额不正确</div>
			             <input type="hidden" name="id" value="${item.id}">
			             <button type="button" onclick="check()"  class="btn btn-primary">保存</button>
				       </form>
                       </td>
                       </c:if>
                        <td>
                        <c:if test="${item.duemoney ge 10000}">
									<fmt:formatNumber value="${item.duemoney / 10000}" maxFractionDigits="3"/>万
									</c:if>
						<c:if test="${item.duemoney lt 10000}">
							<fmt:formatNumber value="${item.duemoney}" maxFractionDigits="3"/>元
						</c:if>
                        </td>
                        <td>
                        <c:if test="${item.overmoney ge 10000}">
									<fmt:formatNumber value="${item.overmoney / 10000}" maxFractionDigits="3"/>万
									</c:if>
						<c:if test="${item.overmoney lt 10000}">
							<fmt:formatNumber value="${item.overmoney}" maxFractionDigits="3"/>元
						</c:if>
                        </td>
                        <td>
                        <c:if test="${item.spotmoney ge 10000}">
									<fmt:formatNumber value="${item.spotmoney / 10000}" maxFractionDigits="3"/>万
									</c:if>
						<c:if test="${item.spotmoney lt 10000}">
							<fmt:formatNumber value="${item.spotmoney}" maxFractionDigits="3"/>元
						</c:if>
                        </td>
                        <td><fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
                       
                       <td>
                         <c:if test="${item.type ne 'all' and item.type ne 'fund'}">
                       		<a href="/risk/toedit?id=${item.id}&&type=${item.type}">编辑</a>
                        </c:if>
                        </td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
        
 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
function check(){
	
	var exp = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/;
	var money=$("#exampleInputcard").val();
	if(money==''){
		$("#errortotalmoney").attr("style", "display:block;font-size:14px;color:red;");
		return false;
	}else if(!exp.test(money)){
		$("#errortotalmoney").attr("style", "display:block;font-size:14px;color:red;");
		return false;
	}else{
		$("#errortotalmoney").attr("style", "display:none;");
		$("#form1").submit();
	}
}
</script>
</body>
</html>
