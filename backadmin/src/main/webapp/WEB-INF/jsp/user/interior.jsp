<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet" type="text/css" href="../jquery.multiselect.css" />
<title>用户类型编辑</title>
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
					<i class="fa fa-table"></i>当前位置：用户类型编辑
				</h2>
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" id="form1" method="post">
							<div class="form-group">
								<label class="sr-only" for="exampleInputmobile">手机号</label> <input
									type="text" name="mnumber" value="${mnumber}"
									class="form-control" id="exampleInputmobile" placeholder="手机号">
							</div>
							<button type="button" id="search" class="btn btn-primary">查询</button>
						</form>
					</div>
					<div class="panel-body">
						<table id="myTable"
							class="tablesorter table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th style="text-align:center">真实姓名</th>
									<base:active activeId="USER_LOOK">
										<th style="text-align:center">用户编号</th>
										<th style="text-align:center">手机号码</th>
									</base:active>
									<th>用户类型</th>
									<th style="text-align:center">注册时间</th>
									<base:active activeId="USER_LOOK">
										<th style="text-align:center">联系地址</th>
									</base:active>
									<th style="text-align:center">状态</th>
									<th style="text-align:center">实名认证</th>
									<base:active activeId="USER_LOOK">
										<th style="text-align:center">归属地</th>
									</base:active>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${users}" var="item">
									<tr>
										<td>${item.realname }</td>
										<base:active activeId="USER_LOOK">
											<td>${item.userId }</td>
											<td>${item.mobileNumber }</td>
										</base:active>
										<td><c:if test="${item.userInterior == 'duanrongw' }">内部员工</c:if>
											<c:if test="${item.userInterior == 'furen' }">辅仁客户</c:if>
											<c:if test="${item.userInterior == 'nelson' }">离职员工</c:if>
											<c:if test="${item.userInterior == 'organization' }">机构投资人</c:if>
											<c:if test="${item.userInterior != 'furen' }">
												<base:active activeId="EDIT_USER_INTERIOR"><input type="button" onclick="editInterior('${item.userId }','${item.userInterior}' );" value="编辑"></base:active>
											</c:if>		
										</td>
										<td><input type="hidden" name="registerTime"
											id="registerTime"
											value="<fmt:formatDate
									value="${item.registerTime }"
									pattern="yyyy-MM-dd HH:mm" />">
											<fmt:formatDate value="${item.registerTime }"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<base:active activeId="USER_LOOK">
											<td>${item.userOtherInfo.postAddress }</td>
										</base:active>
										<td><c:choose>
												<c:when test="${item.status eq '1'}">
	                           		 正常
	                           </c:when>
												<c:when test="${item.status eq '0'}">
	                           		 禁用
	                           </c:when>
												<c:otherwise>
	                            	${item.status}
	                            </c:otherwise>
											</c:choose></td>
										<td><c:choose>
												<c:when test="${item.authenname eq '实名认证'}">
													<img
														src="${pageContext.request.contextPath}/images/shiming.png"
														style="width:30px;float:left;">
												</c:when>
												<c:otherwise>
													<img
														src="${pageContext.request.contextPath}/images/weishiming.png"
														style="width:30px;float:left;">
												</c:otherwise>
											</c:choose></td>
										<base:active activeId="USER_LOOK">
											<td>${item.phoneNoAttribution}</td>
										</base:active>
										
									</tr>
								</c:forEach>
							</tbody>
						</table>
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
			var content = '<div class="form-group"><label class="col-sm-3 control-label">用户类型</label>'
								+ '<select id ="userInterior">';
			content += '<option value="">无</option>';
			if(value == "duanrongw"){
				content += '<option value="duanrongw" selected>内部员工</option>';
			}else{
				content += '<option value="duanrongw">内部员工</option>';
			}
			if(value == "nelson"){
				content += '<option value="nelson" selected>离职员工</option>';
			}else{
				content += '<option value="nelson">离职员工</option>';
			}
			if(value == "organization"){
				content += '<option value="organization" selected>机构投资人</option>';
			}else{
				content += '<option value="organization">机构投资人</option>';			
			}
			content += '</select></div>';
			ds.dialog({
						title : title,
						content : content,
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
		$("button[id=search]").click(function() {
			var mobNumber = $("#exampleInputmobile").val();
			if (mobNumber == '') {
				alert("手机号不能为空");
				return false;
			}
			$("#form1").attr("action", "/user/interior");
			$("#form1").submit();
		});
	</script>
</body>
</html>