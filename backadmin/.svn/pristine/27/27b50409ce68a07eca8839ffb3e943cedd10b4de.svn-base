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
  <title>推送管理</title>
</head>
<body>
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section> 
 <%@include file="../base/leftMenu.jsp"%> 
  <div class="mainpanel">   
	<%@include file="../base/header.jsp"%>      
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：待推送天眼项目列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	<form class="form-inline" action="${ctx }/netLoanEye/waitPushLoanList"	method="post" id="f1">
			</form>
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th >编号</th>
                <th >项目名称</th>
                <th >还款方式</th>                
                <th >周期/利率</th>              
                <th >借款人</th>
                <th >借款总金额</th>              
                <th >项目状态</th>
                <th >项目发起时间</th>              
                <th >操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="loan" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${loan.id}
					 <c:if test="${loan.newbieEnjoy == '是'}"><img src="${ctx}/images/newbie.png"></c:if>
					 <c:if test="${loan.organizationExclusive == '是'}"><img src="${ctx}/images/org.png"></c:if>
				 </td>
				 <td>${loan.name}
				 <c:if test="${not empty loan.emailSend and loan.emailSend != '' }"><span style="color:red; font-size:12px;">(${loan.emailSend})</span> </c:if></td>
				 <td>
					 <c:if test="${'天' eq loan.operationType }">天标  | ${loan.repayType}
					 	<c:choose>
						 <c:when test="${'是' eq loan.beforeRepay and not empty loan.symbol}"> | 可提前还款 ${loan.symbol}天</c:when>
						 <c:otherwise>
							 | 不可提前还款
						 </c:otherwise>
						 </c:choose>
					 </c:if>
					 <c:if test="${'月' eq loan.operationType }">月标 | ${loan.repayType} </c:if>
				 </td>
				
				 <td>				 
				 <c:if test="${'月' eq loan.operationType }">
							${loan.deadline }个月
						</c:if>
						<c:if test="${'天' eq loan.operationType }">
							<c:choose>
								<c:when test="${'是' eq loan.beforeRepay and not empty loan.symbol}">
									${loan.day + loan.symbol }天
								</c:when>
								<c:otherwise>
									${loan.day }天
								</c:otherwise>
							</c:choose>													
					</c:if>/
					<fmt:formatNumber value="${loan.ratePercent }" pattern="#,##0.##"/>%
				 </td>						 								 
				 <td>${loan.borrowMoneyUserID}<br>${loan.borrowMoneyUserName}</td>
				 <td><fmt:formatNumber value="${loan.totalmoney}" type="currency"/></td>
				 <td>${loan.status}</td>
				 <td><fmt:formatDate value="${loan.commitTime}" type="both"/></td>
				 <td>
				 	<a href="javascript:void(0);" onclick="pushNetLoan('${loan.id}');">推送</a> 
				 </td>
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>       	
        <%@ include file="../base/page2.jsp"%>
 </div>
 </div>
 </div>
 </div>
<script type="text/javascript">
//推送天眼项目
	function pushNetLoan(id){
		if( confirm("是否向网贷天眼推送此项目？") ){
			$.ajax({				
				type : 'POST',
				url : '/netLoanEye/pushNetLoan',
				data:{
					id:id
				},
				success : function(data) {
					if( data=='success' ){
						alert("推送成功！");
						location.reload();
					}else{
						alert("推送失败，请重试！");
						location.reload();
					}
				},
				error:function(){
					alert("操作失败！");
					location.reload();
				}
			});
		}
	}
</script>
 
</section>

</body>
</html>
