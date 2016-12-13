<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
  <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
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
  <title>投资列表</title>
</head>
<body>
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section> 
 <%@include file="../base/leftMenu.jsp"%> 
 <%@include file="../invest/redPacketForDiaglogOpen.jsp" %>
  <div class="mainpanel">   
	<%@include file="../base/header.jsp"%>      
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：用户投资信息 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">	         
	 </div>  
     <div class="panel-body">
     <div>     
	     <base:active activeId="USER_LOOK">
	     <c:if test="${not empty pageInfo.results}">
	     <c:set var="inv" value="${pageInfo.results[0]}"/>
	     <span style="font-weight: bold;">投资人(编号/姓名)：${inv.user.userId }/${inv.user.realname}</span>
	     <span style="margin-left:60px; font-weight: bold;">身份证：${inv.user.idCard}</span>
	     <span style="margin-left:60px; font-weight: bold;">联系地址：${inv.user.postAddress}</span>
	     <span style="margin-left:60px; font-weight: bold;">手机号码：${inv.user.mobileNumber}</span>
	     </c:if>	     
	     </base:active>
	     <base:no_active>
	     <c:if test="${not empty pageInfo.results}">
	     <c:set var="inv" value="${pageInfo.results[0]}"/>
	     <span style="font-weight: bold;">投资人姓名：${inv.user.realname}</span>
	     <base:active activeId="USER_MUMBER">
	     	<span style="margin-left:60px; font-weight: bold;">手机号码：*******${fn:substring(inv.user.mobileNumber, 7, 11)}</span>
	     </base:active>
	     </c:if>
	     </base:no_active>
	  </div> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th>项目编号/项目名称<br>项目类型/项目周期</th>  
                <th>预计收益</th>
                <th>投资本金</th>  
                <th>跟投金额</th>
                <th>跟投金额回池(Y/N)</th>
                <th>已还利息</th>  
                <th>已还本金</th>
                <th>年化收益</th>  
                <th>投资时间</th>
                <th>自动投标(是/否)</th>                                                                        
                <th>投标状态</th>  
                <th>加息券</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="invest" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${invest.loanId}<br>${invest.loan.name }<br>${invest.loan.type}<br>
				 <c:if test="${'月' eq invest.loan.operationType }">
							${invest.loan.deadline }个月
					</c:if>
					<c:if test="${'天' eq invest.loan.operationType }">
						${invest.loan.day }天											
					</c:if>
				 </td>
				 <td>
				 <fmt:formatNumber currencySymbol="￥" type="currency" value="${invest.interest}" />
				 </td>
				 <td>
				 <fmt:formatNumber currencySymbol="￥" type="currency" value=" ${invest.money }" />
				</td>
				 <td>${invest.returnPondMoney}</td>
				 <td>${invest.returnPond}</td>				 				 
				 <td>${invest.paidInterest }</td>
				 <td>${invest.paidMoney}</td>
				 <td>${invest.rate}</td>
				 <td><fmt:formatDate value="${invest.time}" type="both"/> </td>
				 <td><c:choose> 
				 		<c:when test="${invest.isAutoInvest}">是</c:when>
				 		<c:otherwise>否</c:otherwise>
				 </c:choose></td>				 				 					 
				 <td>${invest.status}</td>		
				 <base:active activeId="REDPACKET_UPDATE">				 
					 <td>
					 	<div class="photo">
						  <div class="ui-widget-header ui-corner-all">
						    <a href="javascript:void(0);" data-geo="" onclick="openDialog('${invest.id}','${invest.redpacketId}','${invest.userMobileNumber}','${invest.status}','${invest.loanStatus}')">${invest.redpacketId}</a>
						  </div>
						</div>
					 </td>
				 </base:active>
				 <base:no_active>
				 	<td>
					 	<div class="photo">
						  <div class="ui-widget-header ui-corner-all">
						    <a href="javascript:void(0);" data-geo="" onclick="javascript:void(0);">${invest.redpacketId}</a>
						  </div>
						</div>
					 </td>
				 </base:no_active>		 				 
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>       	
        <%@ include file="../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>
 
</section>

</body>
</html>
