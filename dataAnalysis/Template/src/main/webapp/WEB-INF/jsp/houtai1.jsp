<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>

<html>
<head>
<title>后台数据分析系统</title>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/jquery.e-calendar.css"/>
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/jquery.e-calendar.js"></script>
<script type="text/javascript" src="/js/menu.js"></script>
<script src="/js/index.js"></script>

<script type="text/javascript">

function sendMail(){
	
	$.ajax({
		type:"post",
		url:"/sendMail",
		dataType : "text",
		success:function(data){
			if(data=="success")
			{
			alert("发送成功");
		  }else{
			  alert("发送失败");
		  }
			
		}
	
		});

}



function add(){
	if(confirm('确认?')){
	var year=$("#year").val();
	var month=$("#month").val();
	$.ajax({
		type:"post",
		url:"add",
		dataType : "text",
		data : {
			"year" : year,
			"month" : month,},
		success:function(data){
			if(data=="ok")
			{
			alert("发送成功");
		  }else{
			  alert("发送失败");
		  }
		}
		});
	}

}


</script>


</head>
<body>
<!--<div class="top"></div>
-->


	<jsp:include page="base/header.jsp" />
<div id="content">
	<jsp:include page="base/left.jsp" />
		<div class="m-right">
			<div class="main">
				<div class="bound">           	
                    	<div class="data">
                        	<h3 class="data-top">用户数据显示</h3>
                            <div class="data-all">
                            	<ul class="data-box">
                                	<li class="box-samll">
                                    	<p class="small-top">总注册数量（人）</p>
                                        <p class="small-bottom">${userData.allUserCount}</p>
                                    </li>
                                    <li class="box-samll">
                                    	<p class="small-top">总开户数量（人）</p>
                                        <p class="small-bottom">${userData.allUserRegisterCount }</p>
                                    </li>
                                    <li class="box-samll">
                                    	<p class="small-top">总投资数量（人）</p>
                                        <p class="small-bottom">${userData.allStateCount}</p>
                                    </li>
                                  <input type="button" id="sendMail" value="发送邮件" class="but-search" 
                                  	onclick="sendMail();"/>
                                </ul>
                                <div class="data-ul-bottom">
                                	<span class="have-bg">今日新增</span>
                                    <span class="login-peo">
                                    	<b class="left">注册用户</b>
                                        <b class="right"><strong>${userData.newAllUserCount}</strong>人</b>
                                    </span>
                                    <span class="add-peo">
                                    	<b class="left">开户用户</b>
                                        <b class="right"><strong>${userData.newAllUserRegisterCount}</strong>人</b>
                                    </span>
                                    <span class="invest-peo no-bg">
                                    	<b class="left">投资用户</b>
                                        <b class="right"><strong>${userData.newInvestCount}</strong>人</b>
                                    </span>
                                </div>
                                <div class="data-ul-bottom">
                                	<span class="have-bg">昨日新增</span>
                                    <span class="login-peo">
                                    	<b class="left">注册用户</b>
                                        <b class="right"><strong>${userData.userCount}</strong>人</b>
                                    </span>
                                    <span class="add-peo">
                                    	<b class="left">开户用户</b>
                                        <b class="right"><strong>${userData.lastAllUserRegisterCount}</strong>人</b>
                                    </span>
                                    <span class="invest-peo no-bg">
                                    	<b class="left">投资用户</b>
                                        <b class="right"><strong>${userData.lastInvestCount}</strong>人</b>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="data" style="padding-bottom:100px;">
                        	<h3 class="data-top">借款项目数据显示</h3>
                            <div class="data-all">
                            	<ul class="data-box">
                                	<li class="box-big">                                	
                                    	 <c:choose> 
                                    		<c:when test="${loanData.allSend/1000 > 1}"> 
	                                    		<p class="small-top">定期累计发标金额（万元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${loanData.allSend/10000}"  />
			                                    </p>
		                                      </c:when>
                                    		<c:otherwise>
                                    			<p class="small-top">累计发标金额（元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${loanData.allSend}" />
			                                    </p>
                                    		</c:otherwise>
                                    	</c:choose>                                 	
                                    	
                                    </li> 
                                    <li class="box-big">                                	
                                    	<c:choose>
                                    		<c:when test="${loanData.allAgent/1000 > 1}">
	                                    		<p class="small-top">定期累计代收金额（万元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${loanData.allAgent/10000}"  />
			                                    </p>
		                                    </c:when>
                                    		<c:otherwise>
                                    			<p class="small-top">累计代收金额（元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${loanData.allAgent}" />
			                                    </p>
                                    		</c:otherwise>
                                    	</c:choose>                                   	
                                    </li> 
                                    <li class="box-big">                                	
                                    	 <c:choose> 
                                    		<c:when test="${demandTreasureSubMoney/1000 > 1}"> 
	                                    		<p class="small-top">活期宝在投金额（万元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${demandTreasureSubMoney/10000}"  />
			                                    </p>
		                                     </c:when>
                                    		<c:otherwise>
                                    			<p class="small-top">活期宝在投金额（元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${demandTreasureSubMoney}" />
			                                    </p>
                                    		</c:otherwise>
                                    	</c:choose>                                 	
                                    	
                                    </li> 
                                 
                                 
                                 <li class="box-big">                                	
                                    	 <c:choose> 
                                    		<c:when test="${demandTransferin > 1}"> 
	                                    		<p class="small-top">活期宝投资总额（万元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${demandTransferin/10000}"  />
			                                    </p>
		                                     </c:when>
                                    		<c:otherwise>
                                    			<p class="small-top">活期宝投资总额（元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${demandTransferin}" />
			                                    </p>
                                    		</c:otherwise>
                                    	</c:choose>                                 	
                                    </li> 
                                    <li class="box-big">                                	
                                    	 <c:choose> 
                                    		<c:when test="${demandTreasureLoanMoney/1000 > 1}"> 
	                                    		<p class="small-top">活期宝开放资产（万元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${demandTreasureLoanMoney/10000}"  />
			                                    </p>
		                                     </c:when>
                                    		<c:otherwise>
                                    			<p class="small-top">活期宝开放资产（元）</p>
			                                    <p class="small-bottom">
			                                        <fmt:formatNumber currencySymbol="￥" type="currency" value="${demandTreasureLoanMoney}" />
			                                    </p>
                                    		</c:otherwise>
                                    	</c:choose>                                 	
                                    	
                                    </li> 
                                    
                                </ul>                               
                               <div class="data-box-table">
                               		<p class="data-table-title">累计发标金额明细（万元）</p>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr class="table-title">
                                        <td class="txt-none" width="14.2%">所有包</td>
                                        <td class="car" width="14.2%"><span>车押宝</span></td>
                                        <td class="house" width="14.2%"><span>房押宝</span></td>
                                        <td class="company" width="14.2%"><span>企业宝</span></td>
                                        <td class="flexible" width="14.2%"><span>供金宝</span></td>
                                        <td class="supply" width="14.2%"><span>活期宝</span></td>
                                        <td class="agriculture" width="14.2%"><span>农金宝</span></td>
                                      </tr>
                                      <tr>
                                        <td class="lj-bg">累计发标金额</td>
                                        <td class="blue-color">
                                        	<fmt:formatNumber type="currency" value="${loanData.allCar/10000}"  />
                                        </td>
                                        <td class="blue-color">
											
											<fmt:formatNumber  type="currency" value="${loanData.allHouse/10000}" />
                                        </td>
                                        <td class="blue-color">
                                        	<fmt:formatNumber  type="currency" value="${loanData.allCompany/10000}" />
                                        </td>
                                        <td class="blue-color">---</td>
                                        <td class="blue-color">---</td>
                                        <td class="blue-color">---</td>
                                      </tr>
                                      <tr>
                                        <td class="lj-bg">今日发标金额</td>
                                        <td class="blue-color">
                                       		<fmt:formatNumber  type="currency" value="${loanData.tCar/10000}" />
                                        </td>
                                        <td class="blue-color">
                                       		
                                       		<fmt:formatNumber  type="currency" value="${loanData.tHouse/10000}" />
                                        </td>
                                        <td class="blue-color">
                                       		 <fmt:formatNumber  type="currency" value="${loanData.tCompany/10000}" />
                                        </td>
                                        <td class="blue-color">---</td>
                                        <td class="blue-color">---</td>
                                        <td class="blue-color">---</td>
                                      </tr>
                                      <tr>
                                        <td class="lj-bg">昨日发标金额</td>
                                        <td class="blue-color">  
                                      	 <fmt:formatNumber  type="currency" value="${loanData.lCar/10000}" />
                                        </td>
                                        <td class="blue-color">  
                                      	 <fmt:formatNumber  type="currency" value=" ${loanData.lHouse/10000}" />
                                        </td>
                                        <td class="blue-color">
                                      	  <fmt:formatNumber  type="currency" value="${loanData.lCompany/10000}" />
                                          </td>
                                        <td class="blue-color">---</td>
                                        <td class="blue-color">---</td>
                                        <td class="blue-color">---</td>
                                      </tr>
                                    </table>

                               </div>
                            </div>
                        </div>
                    
                        </div>
                    </div>
                </div>
			</div>

<script>navList(12);</script>

</body>
</html>
