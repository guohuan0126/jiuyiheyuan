<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
  <link rel="stylesheet" type="text/css" href="../jquery.multiselect.css" />
  <title>推荐人</title>
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
#exampleInputcard{
width:250px;
height: 50px;
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
      <h2><i class="fa fa-table"></i>当前位置：推荐人 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline" id="form1"  method="post">
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">手机号</label>
	              <input type="text" name="mnumber" value="${mnumber}" class="form-control" id="exampleInputmobile" placeholder="手机号">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">用户姓名</label>
	              <input type="text" name="realname" value="${realname}" class="form-control" id="userRealName" placeholder="用户姓名">
	            </div>
            <button type="button" id="search" class="btn btn-primary">查询</button>
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
                        <th style="text-align:center">客户推荐人</th>
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
                       <%-- <base:active activeId="USER_EDIT"> <th  style="text-align:center">操作
                      
                       </th></base:active> --%>
                        <th id="invest" style="text-align:center;cursor:pointer;">
                        历史投资
                <img src="${pageContext.request.contextPath}/images/up.png" id="investasc" style="display:none;">
    					<img src="${pageContext.request.contextPath}/images/down.png" id="investdesc" style="display:none;position:absolute;top:72%;right:0;">
                                </th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="item">
                      <tr>
                      <td>${item.realname }</td>
<%--                       <td><a style="cursor:pointer" onclick="edit('${item.userId}','${item.qq }');">${item.realname }</a></td>
 --%>                      <base:active activeId="USER_LOOK">  <td>${item.userId }</td>
                        <td>${item.mobileNumber }</td>
                        <td>${item.idCard }</td>
                        <td>${item.email }</td></base:active>
                        <td>
                        
			              <label class="sr-only" for="exampleInputmobile">推荐类型</label>
			                <select class="select2" name="type" id="st" data-placeholder="推荐类型...">
			                
			                
			                  <option value="郑州业务员:" <c:if test="${fn:substringBefore(item.oreferrer, ':') =='郑州业务员'}">
			                  selected="selected" </c:if>>郑州业务员</option>
			                 
			                  
			                  <option value="天津事业合伙人:" <c:if test="${fn:substringBefore(item.oreferrer, ':') =='天津事业合伙人'}">
			                  selected="selected"  </c:if>>天津事业合伙人</option>
			                
			                  
			                 <option value="郑州事业合伙人:" <c:if test="${fn:substringBefore(item.oreferrer, ':') =='郑州事业合伙人'}"> selected="selected"</c:if>>郑州事业合伙人</option>
			                 
			                  
			                  <option value="许昌事业合伙人:" <c:if test="${fn:substringBefore(item.oreferrer, ':') =='许昌事业合伙人'}"> selected="selected" </c:if>>许昌事业合伙人</option>
			                 
			                </select>
                         <input type="text" class="refe"  name="referrer"  value="${fn:substringAfter(item.oreferrer, ':')}"  id="exampleInputcard" placeholder="推荐人">
			             <input type="hidden" name="mnumber" id="mnumber1" value="${mnumber}">
			             <input type="hidden" name="userId"  id="userId1" value="${item.userId}">
			             <button type="button" id="save" class="btn btn-primary">保存</button>
                       </td>
                        <td>${item.balance}</td>
                      <td>${item.currMoney }</td> 
                        <td>
                        <input type="hidden" name="registerTime" id="registerTime" value="<fmt:formatDate
									value="${item.registerTime }"
									pattern="yyyy-MM-dd HH:mm" />">
                        <fmt:formatDate
									value="${item.registerTime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
                     	<td><fmt:formatNumber type="number" value="${item.investMoneyTotal }" pattern="0.00" maxFractionDigits="2"/></td>         
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
                         <c:choose>
	                           <c:when test="${item.authenname eq '实名认证'}">
	                           	<img src="${pageContext.request.contextPath}/images/shiming.png" style="width:30px;float:left;">
	                           </c:when>
	                            <c:otherwise>
	                              <img src="${pageContext.request.contextPath}/images/weishiming.png" style="width:30px;float:left;">
	                            </c:otherwise>
	                        </c:choose>
                        </td>
                      <base:active activeId="USER_LOOK">  <td>${item.phoneNoAttribution}</td></base:active>
<%--                         <base:active activeId="USER_EDIT"><td><a href="/user/toedit?id=${item.userId}&&url=${url}">编辑</a></td></base:active>
 --%>                        <td>${item.investNum}次<br/><a href="/invest/investByUser?investUserID=${item.userId}">详情</a></td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
          <%--   总账户余额：			￥<c:if test="${user.totalBlance ge 10000}">
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
					</c:if> --%>
        <!-- <input type="hidden" id="userpid">
     <input type="hidden" id="qq"> -->
  		<%-- <%@ include file="../base/page.jsp"%> --%>
 
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
	  jQuery('#rstart').datepicker();
	  jQuery('#rend').datepicker();
	  jQuery('#tstart').datepicker();
	  jQuery('#tend').datepicker();
	  jQuery('#datepicker-inline').datepicker();
	  
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });		  
});

$("button[id=search]").click(function(){
		var mobNumber=$("#exampleInputmobile").val();
		var realname=$("#userRealName").val();
		if(realname==''){
			alert("用户名不能为空");
			return false;
		}
		if(mobNumber==''){
			alert("手机号不能为空");
			return false;
		}
		$("#form1").attr("action","/user/referrer");
		$("#form1").submit();
});

$("button[id=save]").click(function(){
		var registerTime=$("#registerTime").val();
		//现在时间
		var mnumber=$("#mnumber1").val();
		var referrer=$("#exampleInputcard").val();
		var type=$("#st").val();
		var userId=$("#userId1").val();
		
		var str = registerTime.replace(/-/g,"/");
		var sdate = new Date(str );
		var edate = new Date();	
		var iDays = edate.getTime() - sdate.getTime();			
		iDays = parseInt(iDays / 1000 / 60 / 60 /24);
		if(iDays > 7){
			alert("修改备注时间不能与注册时间超过7天"); 
			return ;
		}
		 $.ajax({
		type : "POST",
		url : '${ctx}/user/editreferrer',
		data:{"userId":userId,
			  "type":type,
			  "referrer":referrer,
			  "mnumber":mnumber,
			  "registerTime":registerTime
		},
		dataType : 'text',
		success : function(data) {
			if(data=='error'){
				alert("修改备注时间不能与注册时间超过7天");
			}else if(data == 'interior'){
				alert("内部员工不能备注");
			}else if(data == 'furen'){
				alert("辅仁客户不能备注");
			}else if(data == 'nelson'){
				alert("离职员工不能备注");
			}else if(data=='success'){
				alert("保存成功");			
			}
		location.reload();
		}
	});
		 
			
		 
		
});
		
	

</script>


</body>
</html>
