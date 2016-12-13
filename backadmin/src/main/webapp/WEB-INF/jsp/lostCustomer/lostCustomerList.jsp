﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="${ctx}/images/favicon.png"
	type="image/png">
<link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
<title>流失客户管理</title>
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
	width: 280px;
}

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
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>

		<%@include file="../base/leftMenu.jsp"%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i> 当前位置：流失客户管理
				</h2>

			</div>

			<div class="contentpanel">

				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" id="form1" method="post">
							<div class="form-group">
								<label class="sr-only" for="exampleInputEmail2">编号</label> <input
									type="text" name="userId" value="${userId }"
									class="form-control" id="exampleInputEmail2"
									placeholder="用户编号/手机号/邮箱/身份证">
							</div>
							<div class="form-group">
								<label class="sr-only" for="exampleInputPassword2">用户名</label> <input
									type="text" name="realname" value="${realname }"
									class="form-control" id="exampleInputPassword2"
									placeholder="用户名">
							</div>
									<div class="input-group">
										<input type="text" class="form-control" name="minTime"
											value="${minTime}" placeholder="回访后首投时间" id="minTime"> <span
											class="input-group-addon"><i
											class="glyphicon glyphicon-calendar"></i></span>
									</div> -- 
                <div class="input-group">
										<input type="text" class="form-control" name="maxTime"
											value="${maxTime}" placeholder="回访后首投时间" id="maxTime"> <span
											class="input-group-addon"><i
											class="glyphicon glyphicon-calendar"></i></span>
									</div>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <div class="form-group" style="width: 65px;">                            
                    <select id="lostStatus" name="lostStatus" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>
                      <option value="0" <c:if test="${lostStatus eq '0' }">selected</c:if>>已流失</option>               
                      <option value="1" <c:if test="${lostStatus eq '1' }">selected</c:if>>已激活</option>
                     </select>
                 </div> 
                 <div class="form-group" style="width: 65px;">                            
                    <select id="oreferrerStatus" name="oreferrerStatus" class="select" required data-placeholder="请选择..."> 
                      <option value="">全部</option>
                      <option value="0" <c:if test="${oreferrerStatus eq '0' }">selected</c:if>>无经理推荐人</option>              
                      <option value="1" <c:if test="${oreferrerStatus eq '1' }">selected</c:if>>有经理推荐人</option>
                     </select>
                 </div> 
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <div class="form-group" style="width: 65px;">                            
                    	<select id="customerService" name="customerService" class="select" required data-placeholder="请选择...">               
                      		<option value="">激活客服</option>
                      		<option value="李琪" <c:if test="${customerService eq '李琪' }">selected</c:if>>李琪</option>
                      		<option value="冯翠华" <c:if test="${customerService eq '冯翠华' }">selected</c:if>>冯翠华</option>                   
                     	</select>
                 	</div> 
                 
                 <div class="form-group" style="width: 65px;">                            
                    	<select id="evaluation" name="evaluation" class="select" required data-placeholder="请选择...">
                    	    <option value="">全部</option>               
                      		<option value="0"<c:if test="${evaluation eq '0' }">selected</c:if>>待评定</option>
                      		<option value="1" <c:if test="${evaluation eq '1' }">selected</c:if>>无效激活</option>
                      		<option value="2" <c:if test="${evaluation eq '2' }">selected</c:if>>有效激活</option> 
                     	</select>
                 	</div> 
                 <br><br>
							<div class="form-group">
								<label class="sr-only" for="exampleInputcard">推荐人</label> <input
									type="text" name="referrer" value="${referrer}"
									class="form-control" id="exampleInputcard" placeholder="推荐人">
							</div>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<div class="input-group">
								<label class="sr-only" for="exampleInputmin">流失天数</label> <input
									type="text" name="minDay" value="${minDay}"
									class="form-control" id="exampleInputmin" placeholder="流失天数">
							</div>
							--
							<div class="input-group">
								<label class="sr-only" for="exampleInputmax">流失天数</label> <input
									type="hidden" name="editurl" id="editurl"> <input
									type="text" name="maxDay" value="${maxDay}"
									class="form-control" id="exampleInputmax" placeholder="流失天数">
							</div>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<div class="form-group">
								<label class="sr-only" for="exampleInputmobile">用户类型</label> <select
									class="select2" name="usertype" id="st"
									data-placeholder="用户类型...">
									<option value="">--用户类型</option>
									<option value="enterprise">企业用户</option>
									<option value="user">个人用户</option>
									<option value="furen">辅仁员工</option>
									<option value="duanrongw">内部员工</option>
									<option value="nelson">离职员工</option>
									<option value="organization">机构投资人</option>
								</select>
							</div>

							<button type="button" id="search" class="btn btn-primary">查询</button>
							&nbsp;&nbsp;
							<%-- <c:if test="${type eq 'customer'}"> --%>
							<button type="button" id="export" class="btn btn-primary">导出数据</button>
							&nbsp;&nbsp;
							<button type="button" id="synchronous" class="btn btn-primary">同步Udesk</button>
							<%-- </c:if> --%>
						</form>
					</div>
					<div class="panel-body">
						<table id="myTable"
							class="tablesorter table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th style="text-align:center">真实姓名 <base:active
											activeId="USER_LOOK">
											/用户编号/ 手机号码/身份证号/ 电子邮件
										</base:active>
									</th>
									<%-- <base:active activeId="USER_REFFER"> --%>
										<th style="text-align:center">推荐人</th>
									<%-- </base:active> --%>
									<th style="text-align:center">经理推荐人</th>
									<th style="text-align:center">用户类型</th>
									<th id = 'blance' style="text-align:center;position: relative;cursor:pointer;">
										账户余额 
									</th>
									<th id = 'invest' style="text-align:center;position: relative;cursor:pointer;">
										在投定期金额 
									</th>
									<th id="money"
										style="text-align:center ;position: relative;cursor:pointer;">
										历史投资总金额  
									</th>
									<th style="text-align:center">流失状态</th>
									<th style="text-align:center">流失天数</th>
									<th style="text-align:center">注册时间/注册IP/来源</th>
									<th style="text-align:center">归属地</th>
									<th style="text-align:center">激活备注时间</th>
									<th style="text-align:center">激活客服</th>
									<th style="text-align:center">回访后首投时间</th>
									<th style="text-align:center">回访后定期投资总额</th>
									<th style="text-align:center">回访后活期投资总额</th>
									<%-- <base:active activeId="afcdcd6ac4364db5bb1b9f86fe9fb706"> --%>
										<th style="text-align:center">激活工作评定</th>
									<%-- </base:active> --%>
									<th id="invest" style="text-align:center;cursor:pointer;">
										历史投资  
									</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="item">
									<tr>
										<td><base:active activeId="USER_REMARK">
												<c:if test="${ empty item.realname}">
													<a style="cursor:pointer"
														onclick="edit('${item.userId}','${item.qq }');">无</a></br>
														<base:active activeId="USER_LOOK">
																${item.userId }<br />${item.mobileNumber }<br />${item.idCard }</br>${item.email }
														</base:active>
												</c:if>
												<c:if test="${not  empty item.realname}">
												<a style="cursor:pointer"
													onclick="edit('${item.userId}','${item.qq }');">${item.realname }</a></br>
													<base:active activeId="USER_LOOK">
																${item.userId }<br />${item.mobileNumber }<br />${item.idCard }</br>${item.email }
													</base:active>
												</c:if>
 										</base:active>
										<base:no_active>
											<c:if test="${ empty item.realname}">
													无</br>
													<base:active activeId="USER_LOOK">
															${item.userId }<br />${item.mobileNumber }<br />${item.idCard }</br>${item.email }
													</base:active>
												</c:if>
												<c:if test="${not  empty item.realname}">
												${item.realname }</br>
													<base:active activeId="USER_LOOK">
																${item.userId }<br />${item.mobileNumber }<br />${item.idCard }</br>${item.email }
													</base:active>
												</c:if>
												
										</base:no_active></td>		
										<%-- <base:active activeId="USER_REFFER"> --%>
											<td>${item.referrer}</td>
										<%-- </base:active> --%>
										<td>${item.oreferrer}</td>
										<td><c:if test="${item.userType=='enterprise' }">
												<a href="javascript:void(0)" alt="企业用户" title="企业用户"> <img
													src="${pageContext.request.contextPath}/images/qy.jpg"
													style="width:30px;float:left;"></a>
											</c:if></td>
										<td><fmt:formatNumber value="${item.balance }"
												pattern="#.##" /></td>
										
										
										<td><fmt:formatNumber value="${item.currMoney }"
												pattern="#.##" /></td>
										<td><fmt:formatNumber value="${item.historyTotalMoney1+item.historyTotalMoney2 }"
												pattern="#.##" /></td>
										<td><c:choose>
												<c:when test="${item.lostStatus eq '1'}">
	                           		 已激活
	                           </c:when>
												<c:when test="${item.lostStatus eq '0'}">
	                           		 <span style="color:red">已流失</span>
	                           </c:when>
												<c:otherwise>
	                            	${item.lostStatus}
	                            </c:otherwise>
											</c:choose></td>
											<td>${item.lostDays}</td>
										<%-- <base:active activeId="USER_LOOK">
											<td>${item.userOtherInfo.postAddress }</td>
										</base:active> --%>
										<td><fmt:formatDate value="${item.registerTime }"
												pattern="yyyy-MM-dd HH:mm" /><br />${item.userOtherInfo.userIP }<br />${item.userOtherInfo.userSource }</td>
										<td>${item.phoneNoAttribution}&nbsp;${item.phoneNoCity}</td>
										<td><fmt:formatDate
												value="${item.activationTime }"
												pattern="yyyy-MM-dd HH:mm" /></td>
											
										<td>${item.customerService }</td>
										<td><fmt:formatDate
												value="${item.returnInvestTime }"
												pattern="yyyy-MM-dd HH:mm" />
										</td>
										<td>${item.returnTotalInvest }</td>
										<td>${item.returnTotalDemand }</td>
										<%-- <base:active activeId="USER_LOOK">
											<td>${item.phoneNoAttribution}&nbsp;${item.phoneNoCity}</td>
										</base:active> --%>
									<%-- 	<base:active activeId="afcdcd6ac4364db5bb1b9f86fe9fb706"> --%>
											<td id="evaluationText_${item.userId }">
												<select id="getEvaluation_${item.userId }" onchange="saveEvaluation('${item.userId}')" name="evaluationStatus">
													<option value="0" <c:if test="${item.evaluation==0 }">selected</c:if>>待评价</option> 
                      								<option value="1" <c:if test="${item.evaluation==1 }">selected</c:if>>无效激活</option>               
                     								<option value="2" <c:if test="${item.evaluation==2 }">selected</c:if>>有效激活</option>
                     							</select>
											</td>
										<%-- </base:active> --%>
										<td>${item.investNum}次<br /> <a
											href="/invest/investByUser?investUserID=${item.userId}">详情</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						流失备注之后活期投资总额： ￥
							<fmt:formatNumber value="${lostCustomer3.totalDemand1}"
								maxFractionDigits="2" />
							<i>元</i>
						&nbsp; 流失备注之后定期投资总额：￥
							<fmt:formatNumber value="${lostCustomer2.totalInvest1}"
								maxFractionDigits="2" />
							<i>元</i>
						<input type="hidden" id="userpid"> <input type="hidden"
							id="qq">
						<%@ include file="../base/page.jsp"%>

					</div>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>

	<script>
		function editInterior(id, value) {

			dsdislog("编辑用户类型", id, value);

		}

		function dsdislog(title, id, value) {
			var v = value;
			if (v == "duanrongw") {
				v = "内部员工";
			} else if (v == "furen") {
				v = "辅仁客户";
			}
			ds
					.dialog({
						title : title,
						content : '<div class="form-group"><label class="col-sm-3 control-label">用户类型</label>'
								+ '<select id ="userInterior"><option value="'+value+'">'
								+ v
								+ '</option><option value="duanrongw">内部员工</option><option value="furen">辅仁客户</option></select></div>',
						width : 230,
						yesText : '提交',
						onyes : function() {
							this.close();
							v = $("#userInterior").val();

							oper(id, v);
						},
						noText : '取消',
						onno : function() {
							this.close();
						},
					});
		}

		function oper(id, userInterior) {
			$.ajax({
				type : 'POST',
				url : '${ctx}/user/operInterior',
				data : {
					userId : id,
					userInterior : userInterior,
				},
				success : function(data) {
					if (data == 'ok') {
						alert("编辑成功");
					} else {
						alert("编辑失败");

					}
					location.reload();
				},
			});
		}
	</script>

	<script>
		var flag = 1;
		var flag1 = 1;
		var flag2 = 1;
		jQuery(document)
				.ready(
						function() {
							jQuery('#minTime').datepicker();
							jQuery('#maxTime').datepicker();
							jQuery('#datepicker').datetimepicker({
								showSecond : true,
								timeFormat : 'hh:mm:ss',
								stepHour : 1,
								stepMinute : 1,
								stepSecond : 1,
							});

						});

		$("button[id=search]").click(
				function() {
					$("#form1").attr("action", "/lostCustomer/lostCustomerList/search");
					
					$("#form1").submit();
				});
	
		function edit(id, qq) {
			var userid = id;
			$("#userpid").val(userid);
			ds
					.dialog({
						title : "编辑",
						content : '<iframe  src="${ctx }/tormark.jsp" width="100%" height="100%"></iframe>',
						width : 800,
						height : 700
					});
		}
		
		$("button[id=export]").click(
				function() {
					$("#form1").attr("action", "/lostCustomer/lostCustomerExport");
					$("#form1").submit();
				});
		$("button[id=synchronous]").click(
				function() {

					$("#form1").attr("action", "/lostCustomer/synchronizeUdesk");
					$("#form1").submit();
				});
			function saveEvaluation(id){
				var evaluation = $("#getEvaluation_"+id).val();
				var userId = id ;
				//alert(evaluation);
				// alert(userId);
				if(confirm("确认要修改吗？")){
					$.ajax({
						type : "POST",
						url : '/lostCustomer/tormark',
						data:{
							'evaluation':evaluation,
							'userId':userId
						},
						error : function(e) {
							alert("异常");
						},
						success : function(data) {
							if(data != 'fail'){
							alert("保存成功!");
							$("#evaluationText_"+userId).html(data);
						 }else{
							 alert("保存失败!");
						 }
						}
					});
				}
				
				
			}
	</script>
</body>
</html>