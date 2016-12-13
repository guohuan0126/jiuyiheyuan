<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="/css/jquery.ui.slider.css" />
<link rel="stylesheet" href="/css/jquery-ui-timepicker-addon.css" />
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script type="text/javascript" src="/js/jquery.ui.slider.js"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="/js/menu.js"></script>

<head>
<base href="<%=basePath%>">

<title>推送管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">

	.box2 {
	/*非IE的主流浏览器识别的垂直居中的方法*/
	display: table-cell;
	vertical-align:middle;
	/*设置水平居中*/
	text-align:center;
	/* 针对IE的Hack */
	*display: block;
	*font-size: 175px;/*约为高度的0.873，200*0.873 约为175*/
	*font-family:Arial;/*防止非utf-8引起的hack失效问题，如gbk编码*/
	width:200px;
	height:200px;
	border: 1px solid #eee;
}
.right-nav{height:auto;}
.input-list{padding-top:20px; padding-left:30px;}
.input-list input,.input-list select{border:1px solid #999;border-radius:3px;height:38px;padding-left:20px;}

</style>
</head>

<body>
	<jsp:include page="../base/header.jsp" />
	<div id="content">
		<jsp:include page="../base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>当前位置：推送记录 </li>
				</ul>
				<br/>
				<form class="form-inline" id="f1"  action="/netLoanEye/pushRecords" method="post">
					
						<table class="input-list" border="0" width="99%" cellspacing="10" cellpadding="5">
							<tr>
								<td>
									<input type="text" name="id"  value="${loan.id }" class="form-control" placeholder="项目编号" style="width:160px">
								</td>
								<td>
									<input type="text" name="name" value="${loan.name }" class="form-control" placeholder="项目名称" style="width:160px">
								</td>
								<td width="17%">
								    <span>项目状态 ：</span>
									<select id="status" name="status" class="select" required data-placeholder="请选择...">
                      				  <option value="">请选择...</option>                
				                      <option value="还款中" <c:if test="${loan.status=='还款中' }">selected</c:if>>还款中</option>
				                      <option value="筹款中" <c:if test="${loan.status=='筹款中' }">selected</c:if>>筹款中</option>
				                      <option value="等待复核" <c:if test="${loan.status=='等待复核' }">selected</c:if>>等待复核</option>
				                      <option value="完成" <c:if test="${loan.status=='完成' }">selected</c:if>>完成</option>
				                      <option value="流标" <c:if test="${loan.status=='流标' }">selected</c:if>>流标</option>                   
                                   </select> 
								</td>	
								<td width="17%">
									<span>项目类型 ：</span>
									<select id="loanType" name="loanType" class="select" required data-placeholder="请选择...">
				                      <option value="">请选择...</option>                
				                      <option value="车贷" <c:if test="${loan.loanType=='车贷' }">selected</c:if>>车贷</option>
				                      <option value="金农宝" <c:if test="${loan.loanType=='金农宝' }">selected</c:if>>金农宝</option>
				                      <option value="供应宝" <c:if test="${loan.loanType=='供应宝' }">selected</c:if>>供应宝</option>
				                      <option value="房贷" <c:if test="${loan.loanType=='房贷' }">selected</c:if>>房贷</option>
				                      <option value="企业贷" <c:if test="${loan.loanType=='企业贷' }">selected</c:if>>企业贷</option>                   
				                    </select>  
								</td>
								<td width="35%">
									<span>项目提交时间：</span>
									<input type="text" class="datepicker"  name="start" placeholder="开始时间" style="width:110px">
									<input type="text"  class="datepicker" name="end"   placeholder="结束时间" style="width:110px">
								</td>
								<td width="30%">
									<span>推送时间：</span>
									<input type="text"  class="datepicker" name="pushTimeStart" placeholder="开始时间" style="width:100px">
									 <input type="text"  class="datepicker" name="pushTimeEnd" placeholder="结束时间" style="width:100px">
								</td>
								<td>
									<button type="button" class="buttonStyle"  style="display:block;width:120px;height:40px;background:#3499df" value="查询" onclick="javascript:submit()">查询</button>
								</td>
							</tr>
							
						</table>
						<script type="text/javascript">
                    	jQuery(".select").select2({
							width : '100%',
							minimumResultsForSearch : -1
						});
					
						jQuery("#basicForm").validate(
								{
									highlight : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-success').addClass('has-error');
									},
									success : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-error');
									}
								});
																
						jQuery("#basicForm3").validate(
								{
									highlight : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-success').addClass('has-error');
									},
									success : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-error');
									}
								});
			
						jQuery("#basicForm4").validate(
								{
									highlight : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-success').addClass('has-error');
									},
									success : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-error');
									}
								});                  
                    </script>
						<script type="text/javascript">
                        	$(".datepicker").datepicker({
                        		
                        		dateFormat:'yy-mm-dd'
                        	});
								
						</script>
				</form>
			</div>
			<div class="box">
				<div id="rule"></div>
				<div class="box-table">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr class="table-tit">              
			                <th >编号</th>
			                <th >项目名称</th>
			                <th >还款方式</th>                
			                <th >周期/利率</th>              
			                <th >借款人</th>
			                <th >借款总金额</th>              
			                <th >项目状态</th>
			                <th >项目发起时间</th>
			                <th >推送时间</th>                 
			                <th >操作</th>
			             </tr>
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
								 <td><fmt:formatDate value="${loan.pushTime}" type="date"/></td>
								 <td>
								 	<a href="${ctx}/invest/investByLoan?loanId=${loan.id}">投资信息</a>
								 </td>
						     </tr>
		 				</c:forEach>
		 				<tr></tr>
					</table>
					<br/>
					  累计推送总金额：￥<c:if test="${netLoanModel.pushLoanSumMoney ge 10000}">
						<fmt:formatNumber value="${netLoanModel.pushLoanSumMoney / 10000}" maxFractionDigits="2"/><i>万</i>
					</c:if>
					<c:if test="${netLoanModel.pushLoanSumMoney lt 10000}">
						<fmt:formatNumber value="${netLoanModel.pushLoanSumMoney}" maxFractionDigits="2"/><i>元</i>
					</c:if> 
		累计推送项目个数：${netLoanModel.pushLoanNum }		
		天眼投资人数：${netLoan.investNum }
		天眼注册人数：${netLoan.regUserCount }			
					<%@ include file="../base/pageInfo.jsp"%>
				</div>
			</div>
		</div>
	</div>
	
<script type="text/javascript">
//推送天眼项目
	function pushNetLoan(id){
	alert(id);
	return false;
		$.ajax({				
			type : 'POST',
			url : '/netLoanEye/pushNetLoan',
			data:{
				id:id
			},
			dataType:'json',
			success : function(data) {
				if( data=='success' ){
					alert("推送成功！");
					window.reload();
				}else{
					alert("推送失败，请重试！");
					window.reload();
				}
			},
			error:function(){
				alert("操作失败！");
				window.reload();
			}
		});
	}
</script>
	<script>
		navList(10);
	</script>
	
</body>
</html>
