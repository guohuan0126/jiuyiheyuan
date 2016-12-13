﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
  <title>
  <c:choose>
  <c:when test="${type eq 'customer' }">
    大客户列表
  </c:when>
  <c:otherwise>
  	三个月以上未登陆用户
  </c:otherwise>
  </c:choose>
  </title>
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
      <h2><i class="fa fa-table"></i>
      当前位置：<c:choose>
  <c:when test="${type eq 'customer' }">
    大客户列表
  </c:when>
  <c:otherwise>
  	三个月以上未登录用户
  </c:otherwise>
  </c:choose>
      
      </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline" id="form1"  method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	              <input type="text" name="userId" value="${userId }" class="form-control" id="exampleInputEmail2" placeholder="用户编号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">用户名</label>
	              <input type="text" name="realname" value="${realname }" class="form-control" id="exampleInputPassword2" placeholder="用户名">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputemail">电子邮件</label>
	              <input type="text" name="email" value="${email}" class="form-control" id="exampleInputemail" placeholder="电子邮件">
	            </div>
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">手机号</label>
	              <input type="text" name="mnumber" value="${mnumber}" class="form-control" id="exampleInputmobile" placeholder="手机号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputcard">身份证号</label>
	              <input type="text" name="card" value="${card}" class="form-control" id="exampleInputcard" placeholder="身份证号">
	            </div>
	            <div class="input-group" >
                <input type="text" class="form-control" name="rstart" value="${rstart}" placeholder="备注开始时间" id="rstart">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="rend" value="${rend}" placeholder="备注结束时间" id="rend">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
	            <div class="form-group">
	              <input type="text" name="source" value="${source}" class="form-control" id="exampleInputcard" placeholder="来源">
	            </div>
	             
	            <br/>
	            <br/>
	           
	            <div class="input-group" >
                <input type="text" class="form-control" name="tstart" value="${tstart}" placeholder="最初投资开始时间" id="tstart">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="tend" value="${tend}" placeholder="最初投资结束时间" id="tend">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputcard">推荐人</label>
	              <input type="text" name="referrer" value="${referrer}" class="form-control" id="exampleInputcard" placeholder="推荐人">
	            </div>
	             <div class="input-group" >
                <input type="text" class="form-control datepicker" name="start" value="${start}" placeholder="注册开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control datepicker" name="end" value="${end}" placeholder="注册结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>&nbsp;&nbsp;&nbsp;&nbsp;
                <div class="input-group" >
                <label class="sr-only" for="exampleInputmin">最小金额</label>
	            <input type="text" name="minMoney" value="${minMoney}" class="form-control" id="exampleInputmin" placeholder="最小金额">
                </div> -- 
                <div class="input-group" >
               <label class="sr-only" for="exampleInputmax">最大金额</label>
               <input type="hidden" name="editurl" id="editurl">
	            <input type="text" name="maxMoney" value="${maxMoney}" class="form-control" id="exampleInputmax" placeholder="最大金额">
                </div>
                </br></br>
                 <div class="form-group">
	              <input type="text" name="userIp" value="${userIp}" class="form-control" id="exampleInputcard" placeholder="注册IP">
	            </div>
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">用户类型</label>
	                <select class="select2" name="usertype" id="st" data-placeholder="用户类型...">
	                    <option value="">--用户类型</option>
	                    <option value="enterprise">企业用户</option>
					    <option value="user">个人用户</option>
					    <option value="furen">辅仁员工</option>
						<option value="duanrongw">内部员工</option>
						<option value="nelson">离职员工</option>
						<option value="organization">机构投资人</option>
	                </select>
	            </div>
                <div class="form-group">
	            <div class="checkbox block"><label><input name="sooner" value="${sooner}" type="checkbox">第一次备注时间早于第一次投资时间</label></div>
	            </div>
	            <select  id="mselect"  style="width:180px" multiple="multiple">
				  <option value="郑州业务员" >不含郑州业务员</option>
				  <option value="天津事业合伙人" >不含天津事业合伙人</option>
				  <option value="郑州事业合伙人" >不含郑州事业合伙人</option>
				  <option value="许昌事业合伙人" >不含许昌事业合伙人</option>
				</select>
				<input type="hidden" name="salesman" id="salesman">
	           <%--  <div class="form-group">
	            <div class="checkbox block"><label><input name="salesman" value="${salesman}" type="checkbox">不含郑州业务员</label></div>
	            </div> --%>
	            
	            <base:active activeId="USER_ZHONGKEKE">
	            <div class="form-group">
	             <input type="hidden" name="ids" id="ids">
	             <select  id="remark"  style="width:180px" multiple="multiple">
				  <option value="15122560089evip" >赵爱梅</option>
				  <option value="15933163114ycoa" >李艳</option>
				  <option value="keke8854" >仲可可</option>
				  <option value="Bbiy2am2Ebm2iqmn" >王慧方</option>
				  <option value="eUVfuanmu6jyulmi" >周姿芸</option>
				  <option value="QrU3auZNVJZrsmru" >陈明</option>
				  <option value="RRjMrqnI7Rnmqkjk" >李申</option>
				  <option value="uM3iMnFraAJrfybf" >李文博</option>
				  
				</select>
	           
	            </div>
	            </base:active>
            <button type="button" id="search" class="btn btn-primary">查询</button>
            <button id="export" type="button"  class="btn btn-primary">导出数据</button>	
	          </form>
	    </div>
	  <div class="panel-body" > 
   			 <table  id="myTable"  class="tablesorter table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                      <th style="text-align:center">真实姓名</th>
                       <base:active activeId="USER_LOOK"> <th style="text-align:center">用户编号</th>
                        
                        <th style="text-align:center">手机号码</th>
                        <th style="text-align:center">身份证号</th>
                        <th style="text-align:center">电子邮件</th></base:active>
                      <base:active activeId="USER_REFFER">   <th style="text-align:center">推荐人</th></base:active>
                         <th style="text-align:center">经理推荐人</th>
                         <th style="text-align:center">用户类型</th> 
                        <th  id="blance" style="text-align:center;position:relative;cursor:pointer;" >
                                                账户余额
                        <img src="${pageContext.request.contextPath}/images/up.png" id="blanceasc" style="display:none;">
    					<img src="${pageContext.request.contextPath}/images/down.png" id="blancedesc" style="display:none;position:absolute;top:72%;right:0;">
                        </th>
                         
                        <th style="text-align:center">当前投资金额</th> 
                        <th style="text-align:center">注册时间</th>
                        <th  id="money" style="text-align:center ;position: relative;cursor:pointer;">
                        投资总金额
                        <img src="${pageContext.request.contextPath}/images/up.png" id="moneyasc" style="display:none;">
    					<img src="${pageContext.request.contextPath}/images/down.png" id="moneydesc" style="display:none;">
                        </th>
                        <base:active activeId="USER_LOOK"><th style="text-align:center">联系地址</th></base:active>
                        <th style="text-align:center">来源</th>
                        <th style="text-align:center">注册IP</th>
                        <th style="text-align:center">最近登录时间/用户最近登录IP</th>
<!--                         <th style="text-align:center">用户最近登录IP</th>
 -->                        <th style="text-align:center">登录次数</th>
                        <th style="text-align:center">状态</th>
                        <th style="text-align:center">实名认证</th>
                       
                       <base:active activeId="USER_LOOK"> <th style="text-align:center">归属地</th></base:active>
                       <base:active activeId="USER_EDIT"> <th  style="text-align:center">操作
                      
                       </th></base:active>
                        <th id="invest" style="text-align:center;cursor:pointer;">
                        历史投资
                <img src="${pageContext.request.contextPath}/images/up.png" id="investasc" style="display:none;">
    					<img src="${pageContext.request.contextPath}/images/down.png" id="investdesc" style="display:none;position:absolute;top:72%;right:0;">
                                </th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                      <base:active activeId="USER_REMARK">
                       
                      <td width="100px">
                      <c:if test="${item.remarkNum>0 }">
                        <img src="${pageContext.request.contextPath}/images/beizhu.png" style="width:30px;float:left;">
                       </c:if>
<%--                       <a style="cursor:pointer" onclick="edit('${item.userId}','${item.qq }');">${item.realname }</a></td>
 --%>                  <c:if test="${ empty item.realname}">
                         <a style="cursor:pointer" onclick="edit('${item.userId}','${item.qq }');">无</a></td>
                       </c:if>
                       <c:if test="${not  empty item.realname}">
                      	<a style="cursor:pointer" onclick="edit('${item.userId}','${item.qq }');">${item.realname }</a></td>
                       </c:if>
 					 </base:active>
                      <base:no_active>
                      <td>${item.realname }</td>
                      </base:no_active>
<%--                       <td><a style="cursor:pointer" onclick="edit('${item.userId}','${item.qq }');">${item.realname }</a></td>
 --%>                      <base:active activeId="USER_LOOK">  <td>${item.userId }</td>
                        
                        <td>${item.mobileNumber }</td>
                        <td>${item.idCard }</td>
                        <td>${item.email }</td></base:active>
                       <base:active activeId="USER_REFFER"> <td>${item.referrer }</td></base:active>
                        <td>${item.oreferrer}</td>
                         <td><c:if test="${item.userInterior == 'duanrongw' }">内部员工</c:if>
											<c:if test="${item.userInterior == 'furen' }">辅仁客户</c:if> 
											<c:if test="${item.userInterior == 'nelson' }">离职员工</c:if>
											<c:if test="${item.userInterior == 'organization' }">机构投资人</c:if></td>
                        <td>${item.balance}</td>
                      <td>${item.currMoney }</td> 
                        <td><fmt:formatDate
									value="${item.registerTime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
                       
                      
                        <td>
                        <fmt:formatNumber value="${item.investMoneyTotal }" pattern="#.##" />
                        </td>
                       <base:active activeId="USER_LOOK"> <td>${item.userOtherInfo.postAddress }</td></base:active>
                        <td>${item.userOtherInfo.userSource }</td>
                        <td>${item.userOtherInfo.userIP }</td>
                        <td><fmt:formatDate
									value="${item.userLoginLog.loginTime }"
									pattern="yyyy-MM-dd HH:mm" /><br/>
									${item.userLoginLog.loginIp }
						</td>
                       <%--  <td>${item.userLoginLog.loginIp }</td> --%>
                        <td>${item.userLoginLog.num }</td>
                        <td>
                        <c:choose>
	                           <c:when test="${item.status eq '1'}">
	                           		 正常
	                           </c:when>
	                           <c:when test="${item.status eq '0'}">
	                           		 禁用
	                           </c:when>
	                            <c:otherwise>
	                            	${item.status}
	                            </c:otherwise>
	                        </c:choose>
                        </td>
                        <td>
                          <c:if test="${item.userType=='enterprise' }">
                     <a href="javascript:void(0)" alt="企业用户" title="企业用户">	<img  src="${pageContext.request.contextPath}/images/qy.jpg" style="width:30px;float:left;"></a>
                        </c:if>
                         <c:choose>
	                           <c:when test="${item.authenname eq '实名认证'}">
	                           	<img src="${pageContext.request.contextPath}/images/shiming.png" style="width:30px;float:left;">
	                           </c:when>
	                            <c:otherwise>
	                              <img src="${pageContext.request.contextPath}/images/weishiming.png" style="width:30px;float:left;">
	                            </c:otherwise>
	                        </c:choose>
                        </td>
                      
                      <base:active activeId="USER_LOOK">  <td>${item.phoneNoAttribution}&nbsp;${item.phoneNoCity}</td></base:active>
                        <base:active activeId="USER_EDIT"><td><a href="/user/toedit?id=${item.userId}&&url=${url}">编辑</a></td></base:active>
                        <td>${item.investNum}次<br/><a href="/invest/investByUser?investUserID=${item.userId}">详情</a></td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
            总账户余额：			￥<c:if test="${user.totalBlance ge 10000}">
						<fmt:formatNumber value="${user.totalBlance / 10000}" maxFractionDigits="2"/><i>万</i>
					</c:if>
					<c:if test="${user.totalBlance lt 10000}">
						<fmt:formatNumber value="${user.totalBlance}" maxFractionDigits="2"/><i>元</i>
					</c:if> &nbsp; 总当前投资金额：￥
					<c:if test="${user.totalCurrMoney ge 10000}">
						<fmt:formatNumber value="${user.totalCurrMoney / 10000}" maxFractionDigits="2"/><i>万</i>
					</c:if>
					<c:if test="${user.totalCurrMoney lt 10000}">
						<fmt:formatNumber value="${user.totalCurrMoney}" maxFractionDigits="2"/><i>元</i>
					</c:if>
					&nbsp; 总投资总金额：￥
					<c:if test="${user.totalMoney ge 10000}">
						<fmt:formatNumber value="${user.totalMoney / 10000}" maxFractionDigits="2"/><i>万</i>
					</c:if>
					<c:if test="${user.totalMoney lt 10000}">
						<fmt:formatNumber value="${user.totalMoney}" maxFractionDigits="2"/><i>元</i>
					</c:if>
        <input type="hidden" id="userpid">
     <input type="hidden" id="qq">
  		<%@ include file="../base/page.jsp"%>
 
 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
var flag=1;var flag1=1;var flag2=1;
jQuery(document).ready(function(){
	 $("#mselect").multiselect({
	        noneSelectedText: "==请选择==",
	        checkAllText: "全选",
	        uncheckAllText: '全不选',
	        selectedList:4
	    });
	 var salesman='${salesman}';
	 var salesmans = salesman.split(',');        
  if (salesmans != null) {            
      $('#mselect').val(salesmans);
      $('#mselect').multiselect("refresh");           
  }
  $("#remark").multiselect({
      noneSelectedText: "==请选择客服==",
      checkAllText: "全选",
      uncheckAllText: '全不选',
      selectedList:4
  });
  var ids='${ids}';
	 var ids1 = ids.split(',');        
  if (ids1 != null) {            
      $('#remark').val(ids1);
      $('#remark').multiselect("refresh");           
  }
	$("#st").val('${usertype}');
	/* var pids='';
	if('${ids}'!=''){
		pids='${ids}';
	}
	 if(pids!='')  {
		  var str=pids.split(",");
		  //以逗号将字符串转化为数组 
		  for(var j=0;j<str.length;j++){
			  $(":checkbox[value='"+str[j]+"']").attr("checked",true);  
		}
	}   */
	var f='${flag}';var f1='${flag1}';var f2='${flag2}';
    var sooner='${sooner}';
    var salesman='${salesman}';
    
    if(sooner!=''){
    	 $(":checkbox[value='"+sooner+"']").attr("checked",true);
    }
    if(salesman!=''){
   	 $(":checkbox[value='"+salesman+"']").attr("checked",true);
   }
	if(f==0){
		$("#blancedesc").attr("style", "display:none;position:absolute;top:72%;right:0;");
		$("#blanceasc").attr("style", "display:block;position:absolute;top:67%;right:0;");
		flag=0;
	}else if(f==1){
		$("#blanceasc").attr("style", "display:none;position:absolute;top:67%;right:0;");
		$("#blancedesc").attr("style", "display:block;position:absolute;top:72%;right:0;");
		flag=1;
	}else{
		$("#blancedesc").attr("style", "display:none;");
		$("#blanceasc").attr("style", "display:none;");
		
	}
	if(f1==0){
		$("#moneydesc").attr("style", "display:none;position: absolute; right: 0px; bottom: 12%;");
		$("#moneyasc").attr("style", "display:block;");
		flag1=0;
	}else if(f1==1){
		$("#moneyasc").attr("style", "display:none;");
		$("#moneydesc").attr("style", "display:block;position: absolute; right: 0px; bottom: 12%;");
		flag1=1;
	}else{
		$("#moneydesc").attr("style", "display:none;");
		$("#moneyasc").attr("style", "display:none;");
	}
	if(f2==0){
		$("#investdesc").attr("style", "display:none;position: absolute; right: 0px; bottom: 12%;");
		$("#investasc").attr("style", "display:block;");
		flag2=0;
	}else if(f2==1){
		$("#investasc").attr("style", "display:none;");
		$("#investdesc").attr("style", "display:block;");
		flag2=1;
	}else{
		$("#investdesc").attr("style", "display:none;");
		$("#investasc").attr("style", "display:none;");
	}
    // Date Picker
	 /*  jQuery('#datepicker').datepicker();
	  jQuery('#datepicker1').datepicker(); */
	  jQuery('#rstart').datepicker();
	  jQuery('#rend').datepicker();
	  jQuery('#tstart').datepicker();
	  jQuery('#tend').datepicker();
	  jQuery('#datepicker-inline').datepicker();
	  
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });
	  jQuery('#datepicker1').datetimepicker({
			showSecond : true,
			timeFormat : 'hh:mm:ss',
			stepHour : 1,
			stepMinute : 1,
			stepSecond : 1,
			hour:23,
			minute:59,
			second:59,
		});
	  jQuery('#datepicker').datetimepicker({
			showSecond : true,
			timeFormat : 'hh:mm:ss',
			stepHour : 1,
			stepMinute : 1,
			stepSecond : 1,
		});
});

$("button[id=search]").click(function(){
	 attendClassTime = $("#mselect").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#salesman").val(attendClassTime);
	 attendClassTime1 = $("#remark").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#ids").val(attendClassTime1);
	var type='${type}';
	 if ($("[name = sooner]:checkbox").is(":checked")) {
		 $("[name = sooner]:checkbox").attr("value","sooner");
     }
	 if ($("[name = salesman]:checkbox").is(":checked")) {
		 $("[name = salesman]:checkbox").attr("value","salesman");
     }
	// var ids="";
	 var result = new Array();
 	$("[name = remark]:checkbox").each(function () {
         if ($(this).is(":checked")) {
             result.push($(this).attr("value"));
         }
 	});
		//ids=result.join(",");
 	//$("#ids").val(ids);
		if(type=='customer'){
			$("#form1").attr("action","/user/userList/nocustomer");
		}else{
			$("#form1").attr("action","/user/userList/nosearch");
		}
		$("#form1").submit();
});
$("button[id=export]").click(function(){
	 attendClassTime = $("#mselect").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#salesman").val(attendClassTime);
	 attendClassTime1 = $("#remark").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#ids").val(attendClassTime1);
	var type='${type}';
	 if ($("[name = sooner]:checkbox").is(":checked")) {
		 $("[name = sooner]:checkbox").attr("value","sooner");
    }
	 if ($("[name = salesman]:checkbox").is(":checked")) {
		 $("[name = salesman]:checkbox").attr("value","salesman");
    }
	// var ids="";
	 var result = new Array();
	$("[name = remark]:checkbox").each(function () {
        if ($(this).is(":checked")) {
            result.push($(this).attr("value"));
        }
	});
		//ids=result.join(",");
	//$("#ids").val(ids);
	$("#form1").attr("action","/user/userExport/nosearch");
	$("#form1").submit();
 });
$("th[id=blance]").click(function(){
	 attendClassTime = $("#mselect").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#salesman").val(attendClassTime);
	 attendClassTime1 = $("#remark").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#ids").val(attendClassTime1);
	// var ids="";
	 var result = new Array();
 	$("[name = remark]:checkbox").each(function () {
         if ($(this).is(":checked")) {
             result.push($(this).attr("value"));
         }
 	});
		//ids=result.join(",");
 //	$("#ids").val(ids);
	if ($("[name = sooner]:checkbox").is(":checked")) {
		 $("[name = sooner]:checkbox").attr("value","sooner");
    }
	 if ($("[name = salesman]:checkbox").is(":checked")) {
		 $("[name = salesman]:checkbox").attr("value","salesman");
     }
	if(flag==1){
		flag=0;
		$("#form1").attr("action","/user/userList/noblanceasc");
		$("#form1").submit();
		
		//$("#blance>img").toggle();
	}else{
		flag=1;
		$("#form1").attr("action","/user/userList/noblancedesc");
		$("#form1").submit();
		
		//$("#blance>img").toggle();
	}	
	
});
$("th[id=money]").click(function(){
	 attendClassTime = $("#mselect").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#salesman").val(attendClassTime);
	 attendClassTime1 = $("#remark").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#ids").val(attendClassTime1);
	 //var ids="";
	 var result = new Array();
 	$("[name = remark]:checkbox").each(function () {
         if ($(this).is(":checked")) {
             result.push($(this).attr("value"));
         }
 	});
	//	ids=result.join(",");
 	//$("#ids").val(ids);
	if ($("[name = sooner]:checkbox").is(":checked")) {
		 $("[name = sooner]:checkbox").attr("value","sooner");
    }
	 if ($("[name = salesman]:checkbox").is(":checked")) {
		 $("[name = salesman]:checkbox").attr("value","salesman");
     }
	if(flag1==1){
		flag1=0;
		$("#form1").attr("action","/user/userList/nomoneyasc");
		$("#form1").submit();
	}else{
		flag1=1;
		$("#form1").attr("action","/user/userList/nomoneydesc");
		$("#form1").submit();
	}	
});
$("th[id=invest]").click(function(){
	 attendClassTime = $("#mselect").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#salesman").val(attendClassTime);
	 attendClassTime1 = $("#remark").multiselect("getChecked").map(function () {
         return this.value;
     }).get();
	 $("#ids").val(attendClassTime1);
	 //var ids="";
	 var result = new Array();
 	$("[name = remark]:checkbox").each(function () {
         if ($(this).is(":checked")) {
             result.push($(this).attr("value"));
         }
 	});
	//	ids=result.join(",");
 //	$("#ids").val(ids);
 	
	if ($("[name = sooner]:checkbox").is(":checked")) {
		 $("[name = sooner]:checkbox").attr("value","sooner");
    }
	 if ($("[name = salesman]:checkbox").is(":checked")) {
		 $("[name = salesman]:checkbox").attr("value","salesman");
     }
	if(flag2==1){
		flag2=0;
		$("#form1").attr("action","/user/userList/noinvestasc");
		$("#form1").submit();
	}else{
		flag2=1;
		$("#form1").attr("action","/user/userList/noinvestdesc");
		$("#form1").submit();
	}	
});
function edit(id,qq){
	var userid=id;
	$("#userpid").val(userid);
	//$("#qq").val(qq);
	ds.dialog({
		   title : "编辑", 
		   content : '<iframe  src="${ctx }/tormark.jsp" width="100%" height="100%"></iframe>',
		   width:800,
		   height:700
		});
}
</script>


</body>
</html>
