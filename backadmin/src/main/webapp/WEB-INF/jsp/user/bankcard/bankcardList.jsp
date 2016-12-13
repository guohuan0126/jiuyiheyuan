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

  <title>银行卡管理</title>

  
</head>

<body>

<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>


<section>
  
 <%@include file="../../base/leftMenu.jsp"%>
  
  <div class="mainpanel">
    
<%@include file="../../base/header.jsp"%>
        
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：银行卡管理 </h2>    
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/bankcard/bankcardList" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	              <input type="text" name="userId" value="${userId }" class="form-control" id="exampleInputEmail2" placeholder="用户编号/邮箱/手机号/身份证号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">用户名</label>
	              <input type="text" name="realname" value="${realname }" class="form-control" id="exampleInputPassword2" placeholder="用户名">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">绑卡状态</label>
	                <select class="select2" name="status" id="st" data-placeholder="绑卡状态...">
	                  <option value="">--绑卡状态</option>
	                  <option value="VERIFIED">审核通过</option>
	                  <option value="VERIFYING">审核中</option>
	                  <option value="FAIL">审核失败</option>
	                </select>
	            </div>
	           
	            <button type="submit" class="btn btn-primary">查询</button>
	           <base:active activeId="BANK_CARD_OPERATION"> <button type="button" id="add" class="btn btn-primary">添加</button></base:active>
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                       <base:active activeId="USER_LOOK"> <th>用户编号</th></base:active>
                        <th>用户名</th>
                        <th>时间</th>
                        <th>银行卡号</th>
                        <th>绑定渠道</th>
                        <th>开户行（中文）</th>
                        <th>开户行（英文）</th>
                        <th>卡状态</th>
                        <th>取消帮卡</th>
                        <th>取消帮卡时间</th>
                        <th>取消帮卡状态</th>
                      <base:active activeId="BANK_CARD_OPERATION">  <th>操作</th></base:active>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                       <base:active activeId="USER_LOOK"><td>${item.userId}</td></base:active>
                       <td>${item.user.realname }</td>
                       <td><fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd HH:mm" /></td>
					  <td>${item.cardNo }</td>
					   <td>
                       <c:choose>
                         <c:when test="${item.paymentId eq 'JDpay'}">京东</c:when>
                         <c:otherwise>易宝</c:otherwise>
                       </c:choose>
                       </td>
					  <td>${item.name }</td>
					  <td>${item.bank }</td>
                        <td>
                         <c:if test="${item.status eq 'VERIFIED'}">
	                       		      审核通过 
	                     </c:if>
	                      <c:if test="${item.status eq 'VERIFYING'}">
	                       		      审核中 
	                     </c:if>
	                      <c:if test="${item.status eq 'FAIL'}">
	                       		      审核失败 
	                     </c:if>
                        </td>
                       <td>${item.deleteBankCard }</td>
                       <td>${item.cancelBandDingTime }</td>
                       <td>${item.cancelStatus }</td>
                       <base:active activeId="BANK_CARD_OPERATION">
                       <td>
                       <c:if test="${item.deleteBankCard ne 'delete'}">
                       <a href="/bankcard/toedit?id=${item.id}">修改</a>|
                        <a href="/bankcard/del?id=${item.id}">删除</a>
                       </c:if>
                       </td>
                       </base:active>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
 		<%@ include file="../../base/page.jsp"%>

 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
jQuery(document).ready(function(){
	$("#st").val('${status}');
});
$("button[id=add]").click(function(){
	window.location.href="/bankcard/toadd";
});
</script>
</body>
</html>
