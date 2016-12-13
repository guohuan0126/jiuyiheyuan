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
  <title>提现</title>
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
      <h2><i class="fa fa-table"></i>当前位置：提现管理 </h2>     
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/cash/cashList" method="post">
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
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <label class="sr-only" for="exampleInputmobile">用户来源</label>
                <input type="text" class="form-control" name="userSource" value="${userSource}" placeholder="用户来源" id="userSource">
                </div> 
                <div class="input-group" >
                <label for="exampleInputmobile">用户来源是否为空</label>
                <input <c:if test="${not empty userSourceIsNull}">checked</c:if> type="checkbox"  name="userSourceIsNull" value="userSourceIsNull" id="userSourceIsNull" style="vertical-align: middle;margin:0 0 0 5px;">
                </div> 
                </br>
                 <div class="input-group" >
                <input type="text" class="form-control" name="registerTimeStart" value="${registerTimeStart}" placeholder="注册开始时间" id="registerTimeStart">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="registerTimeEnd" value="${registerTimeEnd}" placeholder="注册结束时间" id="registerTimeEnd">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">提现状态</label>
	                <select class="select2" name="status" id="st" data-placeholder="提现状态...">
	                  <option value="">--提现状态</option>
	                  <option value="wait_verify">等待提现</option>
	                  <option value="success">提现成功</option>
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
	          <!-- 	<button type="button" onclick="exportData()" class="btn btn-primary">导出</button> -->
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                       	<th>流水号</th>
                        <th>提现时间</th>
                        <base:active activeId="USER_LOOK"><th>用户名</th></base:active>
                       	<th>注册时间</th>
                       	<th>用户来源</th>
                        <th>用户真实姓名</th>
                        <th>提现金额</th>
                        <th>手续费</th>
                        <th>银行卡</th>
                        <th>提现状态</th>
                        <th>是否为加急提现</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                        <td>${item.id}</td>
                        <td><fmt:formatDate
									value="${item.time }"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
                       <base:active activeId="USER_LOOK"> <td>${item.userId }</td></base:active>
                        <td><fmt:formatDate
									value="${item.registerTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>${item.userSource}</td>
                       
                        <td>${item.realname }</td>
                        <td>
						<fmt:formatNumber currencySymbol="￥" type="currency" value="${item.actualMoney}" />
                        </td>
                        <td>
                        		<c:choose>
                        	<c:when test="${item.fee==0}">
                      			0
                        	</c:when>
                        	<c:otherwise>"${item.fee}</c:otherwise>
                        	</c:choose>
                        </td>
                        <td>${item.bankCard.name }${item.bankCard.bank }${item.bankCard.cardNo }</td>
                        <td>
                         <c:if test="${item.status eq 'wait_verify'}">
                       		等待提现
                        </c:if>
                        <c:if test="${item.status eq 'success'}">
                       		提现成功
                        </c:if>
                        </td>
                        <td>
                        <c:if test="${item.withdrawType=='NORMAL'}">否</c:if>
                        <c:if test="${item.withdrawType=='URGENT'}">是</c:if>
                      </td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
           			总提现金额：￥<c:if test="${actualMoney ge 10000}">
						<fmt:formatNumber value="${actualMoney / 10000}" maxFractionDigits="2"/><i>万</i>
					</c:if>
					<c:if test="${actualMoney lt 10000}">
						<fmt:formatNumber value="${actualMoney}" maxFractionDigits="2"/><i>元</i>
					</c:if> &nbsp; 总提现费用：￥
					<c:if test="${fee ge 10000}">
						<fmt:formatNumber value="${fee / 10000}" maxFractionDigits="2"/><i>万</i>
					</c:if>
					<c:if test="${fee lt 10000}">
						<fmt:formatNumber value="${fee}" maxFractionDigits="2"/><i>元</i>
					</c:if>&nbsp;<br>
					 总提现金额【去除固定借款人】：￥
					<c:if test="${excludeFixedFee ge 10000}">
						<fmt:formatNumber value="${excludeFixedFee / 10000}" maxFractionDigits="2"/><i>万</i>
					</c:if>
					<c:if test="${excludeFixedFee lt 10000}">
						<fmt:formatNumber value="${excludeFixedFee}" maxFractionDigits="2"/><i>元</i>
					</c:if><br>
					提现人数：${excludeFixedFeePeopleCount} 人
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
	  jQuery('#registerTimeStart').datepicker();
	  jQuery('#registerTimeEnd').datepicker();
	  jQuery('#datepicker-inline').datepicker();
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });		  
	$("#st").val('${status}');
});


function exportData(){
	var url="/cash/exportCashList";
	var userId=$("#userId").val();
	var realname=$("#realname").val();
	var mnumber=$("#mnumber").val();
	var start=$("#datepicker").val();
	var end=$("#datepicker1").val();
	var userSource=$("#userSource").val();
	var userSourceIsNull=$("#userSourceIsNull").val();
	var registerTimeStart=$("#registerTimeStart").val();
	var registerTimeEnd=$("#registerTimeEnd").val();
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
	if(registerTimeStart!=""){
		str+="&registerTimeStart="+registerTimeStart;
	}
	if(registerTimeEnd!=""){
		str+="&registerTimeEnd="+registerTimeEnd;
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
