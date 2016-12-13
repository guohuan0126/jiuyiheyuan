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
<link rel="stylesheet" href="/css/jquery.dialogbox.css" />
<link rel="stylesheet" href="css/covercss.css">
<link rel="stylesheet" href="/css/jquery.e-calendar.css" />
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/jquery.e-calendar.js"></script>
<script type="text/javascript" src="/js/jquery.dialogBox.js"></script>
<script type="text/javascript" src="/js/menu.js"></script>
<script src="/js/index.js"></script>
<head>
<base href="<%=basePath%>">

<title>爱有钱推送管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!--  
	 -->
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

</style>
</head>

<body>
	<jsp:include page="../base/header.jsp" />
	<div id="content">
		<jsp:include page="../base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>当前位置：爱有钱推送管理</li>
				</ul>
				<form  action="${ctx }/getPlatformWaitPushLoanList"	method="post" id="f1">
			</div>
					<div class="box">
					<div id="rule"></div>
						<div class="box-table">
							<input type="button" id="submitAllButn"  class="buttonStyle" value="批量推送" style="display:block;width:120px;height:40px;background:#3499df;float:right;margin-bottom:8px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="table-tit">
									<td><input type="checkbox" id="loanCheckbox"></td>
									<td>编号</td>
									<td>项目名称</td>
									<td>还款方式</td>
									<td>周期/利率</td>
									<td>借款人</td>
									<td>借款总金额</td>
									<td>项目状态</td>
									<td>项目发起时间</td>
									<td>操作</td>
									
								</tr>
								<c:forEach items="${pageInfo.results}" var="loan" varStatus="Num">
								<tr>
								
									<td><input type="checkbox" value="${loan.id}"></td>
									<td>${loan.id}
					 <c:if test="${loan.newbieEnjoy == '是'}"><img src="${ctx}/images/newbie.png"></c:if>
					 <c:if test="${loan.organizationExclusive == '是'}"><img src="${ctx}/images/org.png"></c:if>
					 </td>
									<td>${loan.name}
				 <c:if test="${not empty loan.emailSend and loan.emailSend != '' }"><span style="color:red; font-size:12px;">(${loan.emailSend})</span> </c:if>
				 </td>
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
				 	<a href="javascript:void(0);" onclick="bsyPushLoan('${loan.id}');">推送</a> 
				 </td>
								</tr>
								
								</c:forEach>
								
								<tr></tr>
							</table>							
							<%-- <jsp:include page="../base/page2.jsp" /> --%>
							<%@ include file="../base/pageInfo.jsp"%>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</form>
	
	<div class="marsking" style="display:none">
	<div class="timespan">
		<!--请稍后，系统正在处理中.....(<span class="time">0</span>) style="display:none" -->
		<span style="font-size:15px">请稍后，系统正在处理中.....</span>
	</div>
</div>
	
	<script>
	navList(10);
		//推送天眼项目
			function bsyPushLoan(id){
				if( confirm("是否向爱有钱推送此项目？") ){
					$.ajax({				
						type : 'POST',
						url : '/aiyouqian/aiyouqianPushLoan',
						data:{
							id:id
						},
						success : function(data) {
							alert(data.result);
							location.reload();
						},
						error:function(data){
							alert(data.result);
							location.reload();
						}
					});
				}
			}
		//批量提交推送项目    
		$("#submitAllButn").bind("click",function(){
			if(confirm("是否向爱有钱推送已选择的所有项目？")){
				var checkArray=$("input[type='checkbox']:not('#loanCheckbox')");
				var loanArray=new Array();
				$.each(checkArray,function(i,object){
					if($(this).is(":checked")){
						var loanId=$(this).val();
						loanArray.push(loanId);
					}
				});
				$.ajax({
					url:'/aiyouqian/aiyouqianPushLoanList',
					type:'post',
					data:{loanIds:JSON.stringify(loanArray)},
					success:function(data){
						alert(data.result);
						location.reload();
						$("input[type='checkbox']").removeAttr("checked");
					},
					error:function(data){
						alert(data.result);
						location.reload();
						$("input[type='checkbox']").removeAttr("checked");
					},
					beforeSend:function(){
				    	$(".marsking").fadeIn();
				     },
				     complete:function(){
				    	 $(".marsking").fadeOut();
				     },
				})
			}
		});
		
		$(function(){
			//全选 
			$("#loanCheckbox").bind("click", function(){
				if($(this).is(":checked")){
					$("input[type='checkbox']").prop("checked","checked");
				}else{
					$("input[type='checkbox']").removeAttr("checked");
				}
			}); 
			
			//所有子选择框取消父选反框也取消
			var checkArray=$("input[type='checkbox']:not('#loanCheckbox')");
			$("input[type='checkbox']:not('#loanCheckbox')").bind("click",function(){
				var n=0;
				$.each(checkArray,function(i,object){
					if($(this).is(":checked")){
						n=n+1;
					}
				})
				if(n<1){
					$("#loanCheckbox").removeAttr("checked","checked");
				}
				if(n>=10){
					$("#loanCheckbox").prop("checked","checked");
				}
			});
			
			
			/* 
			$("input[menuId='"+parentId+"']").filter("input[menuType='menu']").prop("checked","checked")
			$("#loanCheckbox").click(function(){
				if($("#loanCheckbox").is(":checked")){
					$("input[type='checkbox']").attr("checked","true");
				}else{
					$("input[type='checkbox']").attr("checked","false");
				}
			}); */
			
		});
		
		</script>
	

</body>
</html>
