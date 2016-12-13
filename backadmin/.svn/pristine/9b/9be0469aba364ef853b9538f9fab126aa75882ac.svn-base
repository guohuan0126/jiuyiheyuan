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
   <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
  <title>三个月以上未登录用户信息</title>
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
      <h2><i class="fa fa-table"></i>当前位置：三个月以上未登录用户信息 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline" id="form1"  method="post">
	           
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">真实姓名</label>
	              <input type="text" name="realname" value="${realname }" class="form-control" id="exampleInputPassword2" placeholder="真实姓名">
	            </div>
	           
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">手机号</label>
	              <input type="text" name="mnumber" value="${mnumber}" class="form-control" id="exampleInputmobile" placeholder="手机号">
	            </div>
	          
	             <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
	            <button type="button" id="search" class="btn btn-primary">查询</button>
	            <%--  <base:active activeId="Export_Excel">
	            <button id="export" type="button"  class="btn btn-primary">导出数据</button>
	            </base:active> --%>
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
                        <th>推荐人</th>
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
                     	<th style="text-align:center">登录次数</th>
                        <th style="text-align:center">状态</th>
                        <th style="text-align:center">实名认证</th>
                        <th style="text-align:center">归属地</th>
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
<%--                       <td>${item.realname }</td>
 --%>                
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
 					<base:active activeId="USER_LOOK">  <td>${item.userId }</td>
                        
                        <td>${item.mobileNumber }</td>
                        <td>${item.idCard }</td>
                        <td>${item.email }</td></base:active>
                         <td>${item.referrer }</td>
                        <td><fmt:formatDate
									value="${item.registerTime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
									<td>${item.investMoneyTotal }</td>
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
                       <td>${item.investNum}次<br/><a href="/invest/investByUser?investUserID=${item.userId}">详情</a></td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
        <input type="hidden" id="userpid">
  		<%@ include file="../base/page.jsp"%>

 
 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
var flag1=1;var flag2=1;
jQuery(document).ready(function(){
	
    // Date Picker
	  jQuery('#datepicker').datepicker();
	  jQuery('#datepicker1').datepicker();	  
	  jQuery('#datepicker-inline').datepicker();
	  
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });
	 var f1='${flag1}';var f2='${flag2}';

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
	     
});

 $("button[id=search]").click(function(){
		$("#form1").attr("action","/user/noLoginList/search");
	    $("#form1").submit();
});
 $("th[id=money]").click(function(){
		if(flag1==1){
			flag1=0;
			$("#form1").attr("action","/user/noLoginList/moneyasc");
			$("#form1").submit();
		}else{
			flag1=1;
			$("#form1").attr("action","/user/noLoginList/moneydesc");
			$("#form1").submit();
		}	
	});
	$("th[id=invest]").click(function(){
		if(flag2==1){
			flag2=0;
			$("#form1").attr("action","/user/noLoginList/investasc");
			$("#form1").submit();
		}else{
			flag2=1;
			$("#form1").attr("action","/user/noLoginList/investdesc");
			$("#form1").submit();
		}	
	});
	function edit(id){
		var userid=id;
		$("#userpid").val(userid);
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
