﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">



  <title>充值</title>

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
      <h2><i class="fa fa-table"></i>当前位置：充值管理 </h2>
     
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/recharge/rechargeList" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	              <input type="text" name="userId" value="${userId }" class="form-control" id="userId" placeholder="用户编号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">用户名</label>
	              <input type="text" name="realname" value="${realname }" class="form-control" id="realname" placeholder="用户名">
	            </div>
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">手机号</label>
	              <input type="text" name="mnumber" value="${mnumber}" class="form-control" id="mnumber" placeholder="手机号">
	            </div>
	            <div class="input-group" >
                <label class="sr-only" for="exampleInputmobile">用户来源</label>
                <input type="text" class="form-control" name="userSource" value="${userSource}" placeholder="用户来源" id="userSource">
                </div> 
                <div class="input-group" >
                <label for="exampleInputmobile">用户来源是否为空</label>
                <input <c:if test="${not empty userSourceIsNull}">checked</c:if> type="checkbox"  name="userSourceIsNull" value="userSourceIsNull" id="userSourceIsNull" style="vertical-align: middle;margin:0 0 0 5px;">
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
	              <label class="sr-only" for="exampleInputmobile">充值状态</label>
	                <select class="select2" name="status" id="st" data-placeholder="充值状态...">
	                  <option value="">--充值状态</option>
	                  <option value="wait_pay">等待充值</option>
	                  <option value="success">充值成功</option>
	                </select>
	            </div>
	            <div class="form-group">
	              <label class="sr-only">用户类型</label>
	                <select class="select2" name="userType" id="userType">
	                  <option value="">全部</option>
					<option value="duanrongw">内部员工</option>
					<option value="!duanrongw">非内部员工</option>
					<option value="organization">机构投资人</option>
	                </select>
	            </div>
	            <button type="submit" class="btn btn-primary">查询</button>
	            <!-- <button type="buttom" onclick="exportReahargeList()" class="btn btn-primary">导出</button> -->
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>编号</th>
                        <th>时间</th>
                        <base:active activeId="USER_LOOK"> <th>用户名</th></base:active>
                        <th>用户真实姓名/注册时间</th>
                        <th>来源</th>
                        <th>充值金额</th>
                        <th>手续费</th>
                        <th>充值方式</th>
                    	<th>银行名称</th>
                        <th>银行卡号</th>
                        <th>充值状态</th>
                        <th>充值成功时间</th>
                        <th>充值通道</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                        <td>${item.id }</td>
                        <td><fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <base:active activeId="USER_LOOK"> <td>${item.userId }</td></base:active>
                        <td>${item.realname }<br/><fmt:formatDate
									value="${item.registerTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
                       	<td>${item.userSource }</td>
                        <td>
							<fmt:formatNumber currencySymbol="￥" type="currency" value="${item.actualMoney}" />
                        </td>
                        <td>${item.fee }</td>
                        <td>${item.rechargeWay }</td>
                         <td>${item.bankName}</td>
                         <td>${item.cardNo }</td>
                        <td>
                         <c:if test="${item.status eq 'wait_pay'}">
                       		等待充值
                        </c:if>
                        <c:if test="${item.status eq 'success'}">
                       		充值成功
                        </c:if>
                        </td>
                        <td><fmt:formatDate
									value="${item.successTime }"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${item.payMentId}</td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
						<%-- 总充值金额： ￥
						<c:if test="${actualMoney ge 10000}">
							<fmt:formatNumber value="${actualMoney / 10000}"
								maxFractionDigits="2" />
							<i>万</i>
						</c:if>
						<c:if test="${actualMoney lt 10000}">
							<fmt:formatNumber value="${actualMoney}" maxFractionDigits="2" />
							<i>元</i>
						</c:if> --%>
					 充值成功金额【去除固定借款人】：￥
						<c:if test="${excludeFixedBorrFee ge 10000}">
							<fmt:formatNumber value="${excludeFixedBorrFee / 10000}" maxFractionDigits="2" />
							<i>万</i>
						</c:if>
						<c:if test="${excludeFixedBorrFee lt 10000}">
							<fmt:formatNumber value="${excludeFixedBorrFee}" maxFractionDigits="2" />
							<i>元</i>
						</c:if>
						&nbsp;充值成功人数【去除固定借款人】 :${successPeopelReachargeCount} 人</br>
						&nbsp;等待充值金额【去除固定借款人】 :￥<c:if test="${excludeFixedBorrFeeFail ge 10000}">
							<fmt:formatNumber value="${excludeFixedBorrFeeFail / 10000}" maxFractionDigits="2" />
							<i>万</i>
						</c:if>
						<c:if test="${excludeFixedBorrFeeFail lt 10000}">
							<fmt:formatNumber value="${excludeFixedBorrFeeFail}" maxFractionDigits="2" />
							<i>元</i>
						</c:if></br>
						&nbsp;等待充值人数【去除固定借款人】:${failPeopelReachargeCount} 人
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
	$("#st").val('${status}');
});

function exportReahargeList(){
	var url="/recharge/exportRechargeList";
	var userId=$("#userId").val();
	var realname=$("#realname").val();
	var mnumber=$("#mnumber").val();
	var start=$("#datepicker").val();
	var end=$("#datepicker1").val();
	var userSource=$("#userSource").val();
	var userSourceIsNull=$("#userSourceIsNull").val();
	var status=$("#st").val();
	var userType=$("#userType").val();
	 var checkbox = document.getElementById('userSourceIsNull');//
	  if(checkbox.checked){
	    //选中了
		  userSourceIsNull=userSourceIsNull;
	  }else{
	     //没选中
		  userSourceIsNull="";
	  }
	var str = "";
	if(userId!=""){
		str+="&userId="+userId;
	}
	if(realname!=""){
		str+="&realname="+realname;
	}
	if(mnumber!=""){
		str+="&mnumber="+mnumber;
	}
	if(start!=""){
		str+="&start="+start;
	}
	if(end!=""){
		str+="&end="+end;
	}
	if(userSource!=""){
		str+="&userSource="+userSource;
	}
	if(userSourceIsNull!=""){
		str+="&userSourceIsNull="+userSourceIsNull;
	}
	if(status!=""){
		str+="&status="+status;
	}
	if(userType!=""){
		str+="&userType="+userType;
	}
	str = "?"+str.substring(1);
						
window.location.href=url+=str;
	
	
}


</script>
</body>
</html>