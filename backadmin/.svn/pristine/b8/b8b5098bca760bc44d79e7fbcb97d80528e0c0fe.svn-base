<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<title>新增投资用户明细查询</title>

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
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：新增投资用户明细查询
				</h2>
			</div>

			<div class="contentpanel">

				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" id="form1" method="post">
							<div class="form-group">
								<span style="font-size: 18px;">时间 ： </span> <input type="text"
									class="form-control" placeholder="投资开始时间"
									name="start" value="${start}" id="start"> <input
									type="text" class="form-control" placeholder="投资结束时间"
									 name="end" id="end" value="${end}">
							</div>
								<span style="font-size: 18px;">是否备注：</span>
								<select id="myselect"  name="remark" >
									<option value="all" selected="selected" >全部</option>
									<option value="remark">已备注</option>
									<option value="noRemark">未备注</option>
								</select>
								<div class="form-group">
									 <span style="font-size: 18px;">客服人员：</span>
									 <select id="remarkUserId" name="remarkUserId" style="width:180px">
										<option value="all" selected="selected">全部</option>
										<option value="15122560089evip">赵爱梅</option>
										<option value="15933163114ycoa">李艳</option>
										<option value="keke8854">仲可可</option>
										<option value="Bbiy2am2Ebm2iqmn">王慧方</option>
										<option value="eUVfuanmu6jyulmi">周姿芸</option>
										<option value="QrU3auZNVJZrsmru">陈明</option>
										<option value="RRjMrqnI7Rnmqkjk">李申</option>
										<option value="mAzUz2JveaE3ywrt">姜宇</option>
										<option value="Ave26b7JBnyevgio">白雪</option>
										<option value="E3IfIvaMVNNnlbji">常欣欣</option>
									</select>
								</div>
								<div class="form-group">
								<span style="font-size: 18px;">备注时间 ： </span> <input type="text"
									class="form-control" placeholder="备注开始时间"
									name="remarkstart" value="${remarkstart}" id="remarkstart"> <input
									type="text" class="form-control" placeholder="备注结束时间"
									 name="remarkend" id="remarkend" value="${remarkend}">
							</div>
							<button type="button" id="search" class="btn btn-primary">查询</button>
							<button type="button"  id="export" class="btn btn-primary">导出数据</button>
							<div id="erroruser" style="display: none;">查询内容不能为空</div>
						</form>
					</div>
				<script type="text/javascript">
					//导出已备注的用户信息
					function exportNow(){
						var start=$("#start").val();
						var end=$("#end").val();
					window.location.href="/user/exportRemarkUserInfo?start="+start+"&end="+end;
					}	
					</script>
					<script type="text/javascript">
					$("#myselect").val('${!empty remark ?  remark:'all'}');
					$("#remarkUserId").val('${!empty remarkUserId ?  remarkUserId:'all'}');
					</script>
					<script type="text/javascript">
						var d = new Date();
						d.setDate(d.getDate() - 1);
						jQuery('#start').datetimepicker({
							dateFormat : 'yy-mm-dd',
						});
					</script>
					<script type="text/javascript">
					
					var d = new Date();
					d.setDate(d.getDate() - 1);
					jQuery('#end').datetimepicker({
						dateFormat : 'yy-mm-dd',
					});
					</script>
					<script type="text/javascript">
						var d = new Date();
						d.setDate(d.getDate() - 1);
						jQuery('#remarkstart').datetimepicker({
							dateFormat : 'yy-mm-dd',
						});
					</script>
					<script type="text/javascript">
					var d = new Date();
					d.setDate(d.getDate() - 1);
					jQuery('#remarkend').datetimepicker({
						dateFormat : 'yy-mm-dd',
					});
					</script>
					
					<div class="panel-body">
						<span id=userCount style="font-size: 20px">用户总数量：${peopleCount}</span>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>用户编号/真实姓名/手机号/注册时间/用户类型/注册来源</th>
									<th>项目名称</th>
									<th>项目周期</th>
									<th>投资本金</th>
									<th>年化收益</th>
									<th>投资时间</th>
									<th>自动投标(手动投标/自动投标)</th>
									<th>投资状态</th>
									<th>加息券</th>
								</tr>
							</thead>
							<c:forEach items="${pageInfo.results}" var="newInvestUser">
								<tr>
									<td rowspan="${fn:length(newInvestUser.investInfo)+1}">
										${newInvestUser.id}</br>
					 <c:if test="${newInvestUser.remarkNum>0 }"> 
                        <img src="${pageContext.request.contextPath}/images/beizhu.png" style="width:30px;float:left;">
                   </c:if> 
							<c:if test="${ empty newInvestUser.realName}">
                         <a style="cursor:pointer" onclick="edit('${newInvestUser.id}','${newInvestUser.qq }');">无</a></br>
                       </c:if>
                       <c:if test="${not  empty newInvestUser.realName}">
                      	<a style="cursor:pointer" onclick="edit('${newInvestUser.id}','${newInvestUser.qq }');">${newInvestUser.realName}</a></br>
                       </c:if>
										${newInvestUser.mobileNumber}</br> 
										<fmt:formatDate value="${newInvestUser.registerTime}" type="both"/> 
										</br>
										${newInvestUser.managerName}</br>
									<c:if test="${newInvestUser.userInterior=='duanrongw'}">内部员工</c:if>
									<c:if test="${newInvestUser.userInterior=='furen'}">辅仁员工</c:if>
									</br>
									${newInvestUser.userSource }
									</td>
								</tr>
								<c:forEach items="${newInvestUser.investInfo}" var="investInfo">
									<tr>
										<td>${investInfo.loanName}</td>
										<td><c:if test="${'月' eq investInfo.operationType }">
										${investInfo.deadline }个月
										</c:if> <c:if test="${'天' eq investInfo.operationType }">
										${investInfo.day }天
										</c:if></td>
										<td><fmt:formatNumber currencySymbol="￥" type="currency"
												value="${investInfo.investMoney}" /></td>
										<td>${investInfo.rate}</td>
										<td>
										<fmt:formatDate value="${investInfo.investTime}" type="both"/> 
										</td>
										<td><c:if test="${'0' eq investInfo.isAutoInvest }">
										手动投标
										</c:if> <c:if test="${'1' eq investInfo.isAutoInvest }">
										自动投标
										</c:if></td>
										<td>${investInfo.investStauts}</td>

										<td>
											<div class="photo">
												<div class="ui-widget-header ui-corner-all">
													<a href="javascript:void(0);" data-geo="" onclick="">${investInfo.redpacketId}</a>
												</div>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</table>
					</div>
					     <input type="hidden" id="userpid">
     						<input type="hidden" id="qq">
					<%@ include file="../base/page.jsp"%>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>

	<script>
		jQuery(document).ready(function() {
			if ('${stype}' != '') {
				$("#st").val('${stype}');
			}
			$('#st').change(function() {
				$("#content").val('');
			});

		});
		$("button[id=search]").click(
				function() {
					if ($("#content").val() == '') {
						$('#erroruser').attr("style",
								"display:block;font-size:14px;color:red");
						return false;
					} else {
						$('#erroruser').attr("style",
								"display:none;font-size:14px;color:red");
						$("#form1").attr("action", "/invest/newUserInvest");
						$("#form1").submit();
					}
				});
		$("button[id=export]").click(
				function() {
					if ($("#content").val() == '') {
						$('#erroruser').attr("style",
								"display:block;font-size:14px;color:red");
						return false;
					} else {
						$('#erroruser').attr("style",
								"display:none;font-size:14px;color:red");
						$("#form1").attr("action", "/user/exportRemarkUserInfo");
						$("#form1").submit();
					}
				});
	</script>
<script type="text/javascript">
					function edit(id,qq){
						var userid=id;
						$("#userpid").val(userid);
						//$("#qq").val(qq);
						ds.dialog({
							   title : "编辑", 
							   content : '<iframe  src="/tormark.jsp" width="100%" height="100%"></iframe>',
							   width:800,
							   height:700
							});
					}
					</script>
</body>
</html>


