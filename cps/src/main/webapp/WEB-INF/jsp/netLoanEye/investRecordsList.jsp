<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/common/activeTag" prefix="permissionAct"%>
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


<link type="text/css" href="/css/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
<link type="text/css" href="/css/jquery-ui-timepicker-addon.css" rel="stylesheet" /> 
<script src="${ctx}/js/dsdialog.js"></script>


<head>
<base href="<%=basePath%>">

<title>投资信息</title>

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
		<%@include file="../base/redPacketForDiaglogOpen.jsp"%>
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>当前位置：投资记录 </li>
				</ul>
				<br/>
				<form class="form-inline" id="f1"  action="/netLoanEye/investRecords" method="post">
					
						<table class="input-list" border="0" width="80%" cellspacing="10" cellpadding="5">
							<tr>
								<td>
									<input type="text" id="loanId" name="loanId" value="${loanId}" placeholder="项目编号" style="width:160px">
								</td>
								<td>
									<input type="text" id="loanName" name="loanName" value="${loanName}"  placeholder="项目名称" style="width:160px">
								</td>
								<td>
									<span>状态</span>
									<select name="status" id="stLoanType" data-placeholder="状态...">
										<option value="">--状态</option>
										<option value="等待确认">等待确认</option>
										<option value="投标成功">投标成功</option>
										<option value="还款中">还款中</option>
										<option value="完成">完成</option>
										<option value="流标">流标</option>
									</select>
								</td>
								<td>
									<label class="sr-only" for="exampleInputmobile">状态</label> <select
										class="select2" name="whetherNew" id="stUserType"
										data-placeholder="状态...">
										<option value="">--全部用户类型--</option>
										<option value="0">新用户</option>
										<option value="1">老用户</option>
									</select>
								</td>
								<td>
									<input type="text" name="userId" id="userId" value="${userId}" placeholder="投资人编号" style="width:160px">
								</td>
								<td>
									<input type="text" name="mobile" id="mobile" value="${mobile}"  placeholder="投资人手机号" style="width:160px">
								</td>
								<td>
									<input type="text" name="realname" id="realname" value="${realname}" placeholder="投资人姓名" style="width:160px">
								</td>
							</tr>
							<tr>
								<td>
									<input type="text" class="datepicker" name="start"  value="${start}" placeholder="投资开始时间" id="datepicker">
								</td>
								<td>
									<input type="text" class="datepicker"  name="end" value="${end}" placeholder="投资结束时间" id="datepicker1"> 
								</td>
								<td>
									<input type="text" class="datepicker" name="regStart" value="${regStart}" placeholder="注册开始时间" id="datepickerReg">
								</td>
								<td>
									<input type="text" class="datepicker" name="regEnd" value="${regEnd}" placeholder="注册结束时间 " id="datepickerReg2">
					            </td>
					            <td>
					            	<button type="button" id="search"  class="buttonStyle" style="display:block;width:120px;height:40px;background:#3499df">查询</button>
					            </td>
					            <td>
					            	<button type="button"  class="buttonStyle" onclick="exportInvestBill()" style="display:block;width:120px;height:40px;background:#3499df">下载</button>
					            </td>
							</tr>
						</table>
						 <script type="text/javascript">
                        	$(".datepicker").datepicker({
                        		
                        		dateFormat:'yy-mm-dd'
                        	});
								
						</script>
				</form>
				
				<script type="text/javascript">
					    $("#stUserType").val('${whetherNew}');
					    $("#stLoanType").val('${status}');
					    //导出已备注的用户信息
						function exportInvestBill(){
							var url="/netLoanEye/exportUserInvestInfo";
							var loanId=$("#loanId").val();
							var loanName=$("#loanName").val();
							var stLoanType=$("#stLoanType").val();
							var stUserType=$("#stUserType").val();
							var userId=$("#userId").val();
							var mobile=$("#mobile").val();
							var realname=$("#realname").val();
							var datepicker=$("#datepicker").val();
							var datepicker1=$("#datepicker1").val();
							var datepickerReg=$("#datepickerReg").val();
							var datepickerReg2=$("#datepickerReg2").val();
							var str = "";
							if(loanId!=""){
								str+="&loanId="+loanId;
							}
							if(loanName!=""){
								str+="&loanName="+loanName;
							}
							if(stLoanType!=""){
								str+="&stLoanType="+stLoanType;
							}
							if(stUserType!=""){
								str+="&stUserType="+stUserType;
							}
							if(userId!=""){
								str+="&userId="+userId;
							}
							if(mobile!=""){
								str+="&mobile="+mobile;
							}
							if(realname!=""){
								str+="&realname="+realname;
							}
							if(datepicker!=""){
								str+="&datepicker="+datepicker;
							}
							if(datepicker1!=""){
								str+="&datepicker1="+datepicker1;
							}
							if(datepickerReg!=""){
								str+="&datepickerReg="+datepickerReg;
							}
							if(datepickerReg2!=""){
								str+="&datepickerReg2="+datepickerReg2;
							}
							str = "?"+str.substring(1);
												
						window.location.href=url+=str;
						}
		   		 </script>
			</div>
			<div class="box">
				<div id="rule"></div>
				<div class="box-table">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr class="table-tit">
							<th>项目ID</th>
							<th>推送状态</th>
							<th>项目名称</th>
							<th>项目周期</th>
							<th>投资编号</th>
							<th>投资状态</th>
							<th>投资人编号</th>
							<th>投资人姓名</th>
							<permissionAct:active activeId="USER_LOOK">
							<th>手机号</th>
							</permissionAct:active>
							<th>投资时间</th>
							<th>投资本金</th>
							<th>预期收益</th>
							<th>已回收收益</th>
							<th>已回收本金</th>
							<th>投标方式</th>
							<th>投标来源</th>
							<th>加息券</th>
						</tr>
						<c:forEach var="invest" items="${pageInfo.results}">
							<tr class="font">
								<td>${invest.loanId}</td>
								<td>
									<c:if test="${invest.isPush==1 }">
											<a href="javascript:void(0);" onclick="pushNetLoan('${invest.loanId}');"><font style="color: red">已推送</font></a>
									</c:if> 
									<c:if test="${invest.isPush!=1 }">
											<a href="javascript:void(0);" onclick="pushNetLoan('${invest.loanId}');">推送</a>
									</c:if>
								</td>
								<td>${invest.loanName}</td>
								<td>
									<c:if test="${invest.loan.operationType=='月'}">${invest.loan.deadline}月</c:if>
									<c:if test="${invest.loan.operationType=='天'}">${invest.loan.day}天</c:if>
								</td>
								<td>${invest.id}</td>
								<td>${invest.status}</td>
								<td>${invest.investUserID}</td>
								<td>${invest.investUserName}</td>
								<permissionAct:active activeId="USER_LOOK">
								<td>${invest.userMobileNumber}</td>
								</permissionAct:active>
								<td><fmt:formatDate value="${invest.time }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td><c:if test="${invest.money ge 10000}">
										<fmt:formatNumber value="${invest.money / 10000}" maxFractionDigits="3" />万
									</c:if> 
									<c:if test="${invest.money lt 10000}">
										<fmt:formatNumber value="${invest.money}" maxFractionDigits="3" />元
			                        </c:if>
			                    </td>
								<td><c:if test="${invest.interest ge 10000}">
										<fmt:formatNumber value="${invest.interest / 10000}" maxFractionDigits="3" />万
									</c:if> 
									<c:if test="${invest.interest lt 10000}">
										<fmt:formatNumber value="${invest.interest}" maxFractionDigits="3" />元
				                     </c:if>
				                </td>
								<td><c:if test="${invest.paidInterest ge 10000}">
										<fmt:formatNumber value="${invest.paidInterest / 10000}" maxFractionDigits="3" />万
									</c:if> 
									<c:if test="${invest.paidInterest lt 10000}">
										<fmt:formatNumber value="${invest.paidInterest}" maxFractionDigits="3" />元
				                    </c:if>
				                </td>
								<td><c:if test="${invest.paidMoney ge 10000}">
										<fmt:formatNumber value="${invest.paidMoney / 10000}" maxFractionDigits="3" />万
									</c:if>
									<c:if test="${invest.paidMoney lt 10000}">
										<fmt:formatNumber value="${invest.paidMoney}" maxFractionDigits="3" />元
									</c:if>
								</td>
								<td>
									<c:choose>
									<c:when test="${invest.isAutoInvest eq true}">
										自动投标
									</c:when>
									<c:otherwise>
										手动投标
									</c:otherwise>
									</c:choose>
								</td>
								<td>${invest.userSource}</td>
								<permissionAct:active activeId="REDPACKET_UPDATE">
								<td>
								
									<%-- <div class="photo"  >
								     <a href="javascript:void(0);"  data-geo="" title="${invest.redpacketId}" >${invest.redpacketId}</a>
								 	</div>	  --%>	
								
									<div class="photo">
										<div class="ui-widget-header ui-corner-all">
											<a href="javascript:void(0);" data-geo=""
												onclick="openDialog('${invest.id}','${invest.redpacketId}','${invest.userMobileNumber}','${invest.status}')">${invest.redpacketId}</a>
										</div>
									</div> 
								</td>
								</permissionAct:active>
								<%-- <permissionAct:no_active>
								<td>
									<div class="photo">
										<div class="ui-widget-header ui-corner-all">
											<a href="javascript:void(0);" data-geo=""  onclick="javascript:void(0);">${invest.redpacketId}</a>
										</div>
									</div>
								</td>
								</permissionAct:no_active> --%>
							</tr>
						</c:forEach>
						<tr></tr>
					</table>
					<br></br>
					累计投资金额：￥${totalInvestMoney}元 累计投资用户数量：${totalInvestNum }<br/>			
					<%@ include file="../base/pageInfo.jsp"%>
				</div>
			</div>
		</div>
	</div>
	
<script type="text/javascript">
                 $("#st").val('${status}');
                 jQuery(document).ready(function(){					
                	// Date Picker
               	  jQuery('#datepicker').datepicker();
               	  jQuery('#datepicker1').datepicker();	  
               	  jQuery('#datepicker-inline').datepicker();
               	  
               	  jQuery('#datepicker-multiple').datepicker({
               	    numberOfMonths: 3,
               	    showButtonPanel: true
               	  });		 
				});
                 
               	
            	 $("#search").click(function(){
//             		 if(!datecheck()){return false};
             		$("#f1").attr("action","/netLoanEye/investRecords");
 				    $("#f1").submit();
             	});
                 
                    	function datecheck(){
                    		 var time1 = new Date($("#datepicker1").val()); //结束时间
                    		 var time2 = new Date($("#datepicker").val());  //开始时间
                    		 var date3 = time1.getTime() - time2.getTime();   //时间差的毫秒数
                    		//计算出相差天数
                    	     var days = Math.floor(date3 / (24 * 3600 * 1000));
                    		 if($("#datepicker").val()=="" || $("#datepicker1").val()==""){
                    			 alert("查询的开始时间和结束时间不能为空");
                    			 return false;
                    		 }else if(date3<0){
                    			 alert("开始时间不能大于结束时间");
                    			 return false;
                    		 }else if(days>31){
                    			 alert("只能查询一月内的数据，日期超出范围");
                    			 return false;                   			 
                    		 }
                    		 return true;
                    	}
                  
        	           
                    	 
                    	//推送天眼项目
                    		function pushNetLoan(id){
                    		console.info(id);
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
	<script>
		navList(10);
	</script>
	
</body>
</html>