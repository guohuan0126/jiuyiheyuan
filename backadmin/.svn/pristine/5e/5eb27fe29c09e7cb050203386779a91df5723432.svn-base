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
   <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
  <title>注册未投资用户信息</title>
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
      <h2><i class="fa fa-table"></i>当前位置：注册未投资用户信息 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline" id="form1"  method="post">
	           
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">用户名</label>
	              <input type="text" name="realname" value="${realname }" class="form-control" id="exampleInputPassword2" placeholder="用户名">
	            </div>
	           
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">手机号</label>
	              <input type="text" name="mnumber" value="${mnumber}" class="form-control" id="exampleInputmobile" placeholder="手机号/用户编号/邮箱/身份证号">
	            </div>
	          
	             <div class="input-group" >
                <input type="text" class="form-control datepicker" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control datepicker" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                <div class="form-group">
								<label class="sr-only" for="exampleInputmobile">用户类型</label> <select
									class="select2" name="usertype" id="st"
									data-placeholder="用户类型...">
									<option value="">--用户类型</option>
									<option value="enterprise">企业用户</option>
									<option value="user">个人用户</option>
									<option value="furen">辅仁员工</option>
									<option value="duanrongw">内部员工</option>
									<option value="organization">机构投资人</option>
								</select>
							</div>
	            <button type="button" id="search" class="btn btn-primary">查询</button>
	             <base:active activeId="Export_Excel">
	            <button id="export" type="button"  class="btn btn-primary">导出数据</button>	            
	            </base:active>
	            <button type="button" id="udesk" class="btn btn-primary">同步Udesk</button>
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
                        <base:active activeId="USER_REFFER"><th>推荐人</th></base:active>
                        <th>经理推荐人</th>
                       	<th>用户类型</th>
                        <th style="text-align:center">注册时间</th>
                        <base:active activeId="USER_LOOK"><th style="text-align:center">联系地址</th></base:active>
                        <th style="text-align:center">来源</th>
                        <th style="text-align:center">注册IP</th>
                        <th style="text-align:center">最近登录时间/用户最近登录IP</th>
                     	<th style="text-align:center">登录次数</th>
                        <th style="text-align:center">状态</th>
                        <th style="text-align:center">实名认证</th>
                        <th style="text-align:center">归属地</th>
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
                       <c:if test="${ empty item.realname}">
                         <a style="cursor:pointer" onclick="edit('${item.userId}','${item.qq }');">无</a></td>
                       </c:if>
                       <c:if test="${not  empty item.realname}">
                      <a style="cursor:pointer" onclick="edit('${item.userId}','${item.qq }');">${item.realname }</a></td>
                      	</c:if>
                      </base:active>
                      <base:no_active>
                      <td>${item.realname }</td>
                      </base:no_active>
                      <base:active activeId="USER_LOOK">  <td>${item.userId }</td>
                        
                        <td>${item.mobileNumber }</td>
                        <td>${item.idCard }</td>
                        <td>${item.email }</td></base:active>
                      <base:active activeId="USER_REFFER">  <td>${item.referrer}</td></base:active>
                        <td>${item.oreferrer}</td>
                         <td>
                        <c:if test="${item.userInterior == 'duanrongw' }">内部员工</c:if>
						<c:if test="${item.userInterior == 'furen' }">辅仁客户</c:if>
						<c:if test="${item.userInterior == 'nelson' }">离职员工</c:if>
						<c:if test="${item.userInterior == 'organization' }">机构投资人</c:if>
						</td>
                        <td><fmt:formatDate
									value="${item.registerTime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
                       <base:active activeId="USER_LOOK"> <td>${item.userOtherInfo.postAddress }</td></base:active>
                        <td>${item.userOtherInfo.userSource }</td>
                        <td>${item.userOtherInfo.userIP }</td>
                        <td><fmt:formatDate
									value="${item.userLoginLog.loginTime }"
									pattern="yyyy-MM-dd HH:mm" /><br/>
									${item.userLoginLog.loginIp }
						</td>
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
                         <c:choose>
	                           <c:when test="${item.authenname eq '实名认证'}">
	                           	<img src="${pageContext.request.contextPath}/images/shiming.png" style="width:30px;float:left;">
	                           </c:when>
	                            <c:otherwise>
	                              <img src="${pageContext.request.contextPath}/images/weishiming.png" style="width:30px;float:left;">
	                            </c:otherwise>
	                        </c:choose>
                        </td>
                        <td>${item.phoneNoAttribution}</td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
      <input type="hidden" id="userpid">
     <input type="hidden" id="qq">
  		<%@ include file="../base/page.jsp"%>

 
 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
jQuery(document).ready(function(){
	
    // Date Picker
	 /*  jQuery('#datepicker').datepicker();
	  jQuery('#datepicker1').datepicker(); */	  
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
$("button[id=export]").click(function(){
	$("#form1").attr("action","/user/export");
    $("#form1").submit();
});
 $("button[id=search]").click(function(){
		$("#form1").attr("action","/user/nouserList");
	    $("#form1").submit();
	});
 $("button[id=udesk]").click(function(){
		$("#form1").attr("action","/user/synchronizeUdesk");
	    $("#form1").submit();
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
 
 $("#st").val('${usertype}');
</script>


</body>
</html>
