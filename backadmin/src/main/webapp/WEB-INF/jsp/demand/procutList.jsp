<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<title>活期宝基本信息</title>

</head>

<body>
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
					<i class="fa fa-table"></i>当前位置：活期宝基本信息
				</h2>
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">

						<form id="form1" class="form-horizontal ">
							<div class="form-group">
								<label class="col-sm-1 control-label">产品名称<span
									class="asterisk">*</span></label>
								<div class="col-sm-1">
									<input type="hidden" name="name" value="${demond.name}">
									<input type="text" name="dname" value="${demond.name}"
										class="form-control" disabled="disabled" />
								</div>
							</div>
							<div class="form-group" style="margin-bottom:10px;">
								<label class="col-sm-1 control-label">可投金额<span
									class="asterisk">*</span></label>
								<div class="col-sm-1">
									<input type="text" name="availableMoney" id="availableMoney"
										value="<fmt:formatNumber  groupingUsed="false" value="${demond.availableMoney}" maxFractionDigits="3"  />"
										class="form-control" disabled="disabled"  />
									<div id="erroravailableMoney" style="display: none;">可投金额不能为空</div>
								</div>
								<!-- <input class="btn btn-primary" type="button" value="保存可投金额"
									onclick="sub()"> -->
							</div>
						</form>
						<form id="form2" class="form-horizontal"
							style="margin-bottom:10px;">
							<div class="form-group">
		      						<input type="hidden" name="name" value="${demond.name}">
								<label class="col-sm-1 control-label">单用户投资上限<span
									class="asterisk">*</span></label>
								<div class="col-sm-1">
									<input type="text" name="investMaxmoney" id="investMaxmoney"
										value="<fmt:formatNumber groupingUsed="false" value="${demond.investMaxmoney}" maxFractionDigits="3"/>"
										class="form-control" />
									<div id="errorinvestMaxmoney" style="display: none;">单用户投资上限不能为空</div>
								</div>
								<input class="btn btn-primary" type="button" value="单用户投资上限"
									onclick="subMax()">
							</div>
						</form>
						<form id="form3" class="form-horizontal ">
							<div class="form-group">
								<label>开始时间:<span class="asterisk">*</span></label> <select
									class="select2" name="startTime" id="startTime"
									data-placeholder="选择计算方式...">
									<c:forEach var="i" begin="0" step="1" end="24">
										<option value="${i }">${i }</option>

									</c:forEach>
								</select> 
								&nbsp;&nbsp;
								<label>结束时间:<span class="asterisk">*</span></label> <select
									class="select2" name="endTime" id="endTime"
									data-placeholder="选择计算方式...">
									<c:forEach var="i" begin="0" step="1" end="24">
										<option value="${i }">${i }</option>

									</c:forEach>
								</select> 
								&nbsp;&nbsp;&nbsp;&nbsp;
								<label>每日赎回次数:<span class="asterisk">*</span></label> <select
									class="select2" name="outNumber" id="outNumber"
									data-placeholder="选择计算方式...">
									<option value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="100">100</option>
								</select> <input class="btn btn-primary" type="button" value="保存"
									onclick="subMin()">
							</div>
						</form>


					</div>
					<div class="panel-body">

			<%-- 	  <base:active activeId="DEMAND_EDIT">  --%>	
						 
						<div style=" margin:0 20px 12px 0px; float:left;">
							<a href="/demand/toUpdateMoney" class="btn btn-primary">添加活期宝资产</a>
						</div>
						<div style=" margin:0 20px 12px 0px; float:left;">
							<a href="/record/toSub" class="btn btn-primary">减少活期宝资产</a>
						</div>
						<div style=" margin:0 20px 12px 0px;float:left;">
							<a href="/demand/procutListExport" class="btn btn-primary">导出数据</a>
						</div>
						<div style=" margin:9px 20px 0px 47px;float:left;color:red;font-size:14px;">
							(*开放金额中包含转让资产，用于续投的，不能删除)
						</div>

			<%-- 	 </base:active>  --%>

						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>编号</th>
									<th>预计执行日期</th>
									<th>执行状态</th>
									<th>更新日期</th>
									<th>类型</th>
									<th>更新金额</th>
									<th>融资完成时间</th>
									<th>应该续投金额</th>
									<th>实际续投金额</th>
									<th>应该开放金额</th>
									<th>总金额</th>
									<th>转让金额</th>
									<th>新增资产</th>
									<th>创建时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="demo"
									varStatus="status">
									<tr>
										<td>${demo.id}</td>
										<td><fmt:formatDate value="${demo.expectTime}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td><c:if test="${demo.status == 1}">
												执行成功
											</c:if> <c:if test="${demo.status == 2}">
												未执行
											</c:if> <c:if test="${demo.status == -1}">
												执行失败
											</c:if></td>
										<td><fmt:formatDate value="${demo.begintime }"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td> 
											<c:if test="${demo.type == 'add'}">
												添加资产
											</c:if> 
											<c:if test="${demo.type == 'sub'}">
												减少资产
											</c:if>
											<c:if test="${demo.type == 'expired'}">
												续投资产
											</c:if>
										</td>
										<td id="editMoney"><fmt:formatNumber
												value="${demo.money}" groupingUsed="false"
												maxFractionDigits="3" /></td>
										</td>
										<td><fmt:formatDate value="${demo.endtime}"
												pattern="yyyy-MM-dd HH:mm" /></td>
									    <td>${demo.expiredMoney}</td>
										<td>${demo.realExpiredMoney}</td>	
										<td>${demo.opendMoney}</td>	
										<td>${demo.totalMoney}</td>
										<td>${demo.transferMoney}</td>	
										<td>${demo.addMoney}</td>
										<td><fmt:formatDate value="${demo.createTime}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>	
										<td><input type="hidden" value="${demo.id }" id="id">
										  <c:if test="${demo.status == 2}">
												<a style="cursor:pointer" href="record/toEditType?id=${demo.id}">修改类型</a>
											</c:if> 
											<c:if test="${demo.status == 2}">
												<a style="cursor:pointer" href="record/toEdit?id=${demo.id}">修改</a>
											</c:if> <c:if test="${demo.status != 1}">
												<a style="cursor:pointer" onclick="del('${demo.id}');">删除</a>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
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
		jQuery(document).ready(function() {
			$(".ui_timepicker").datetimepicker({
				//showOn: "button",
				//buttonImage: "./css/images/icon_calendar.gif",
				//buttonImageOnly: true,
				showSecond : true,
				minDate : new Date(),
				timeFormat : 'hh:mm:ss',
				stepHour : 1,
				stepMinute : 1,
				stepSecond : 1
			});
			$('#startTime').val('${demond.startTime}');
			$('#endTime').val('${demond.endTime}');
			$('#outNumber').val('${demond.outNumber}');
		});

		function sub() {
			$('#erroravailableMoney').attr("style", "display:none;");
			var availableMoney = $("#availableMoney").val();
			if (availableMoney == null || availableMoney == '') {
				$('#erroravailableMoney').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}
		

			$.ajax({
				type : "POST",
				url : '/demand/save/saveMoney',
				data : $('#form1').serialize(),// 你的formid
				async : false,
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'ok') {
						alert('操作成功');
						window.location = "/demand/procut";
					} else if (data == 'status') {
						alert('有转入中的用户，不能操作');
						window.location = "/demand/procut";
					} else {
						alert('操作失败');
					}
				}
			});
		}
		function subMax() {
			var investMaxmoney = $("#investMaxmoney").val();
			if (investMaxmoney == null || investMaxmoney == '') {
				$('#errorinvestMaxmoney').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}
			var img = /^[0-9]*[1-9][0-9]*|0$/;
			//var img = /^[1-9]\d*|0$/;
			if (!img.test(investMaxmoney) || isNaN(investMaxmoney)
					|| investMaxmoney < 0) {
				$('#errorinvestMaxmoney').text("上线金额不正确");
				$('#errorinvestMaxmoney').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}
			$('#errorinvestMaxmoney').attr("style",
					"display:none;font-size:14px;color:red");
			$.ajax({
				type : "POST",
				url : '/demand/save/savemaxMoney',
				data : $('#form2').serialize(),// 你的formid
				async : false,
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'ok') {
						alert('操作成功');
						window.location = "/demand/procut";
					} else {
						alert('操作失败');
					}
				}
			});
		}
		function del(id) {
			if (confirm("确认删除吗")) {
				$.ajax({
					type : "POST",
					url : "record/del?id=" + id,
					async : false,
					error : function(e) {
						alert("异常");
					},
					success : function(data) {
						if (data == 'ok') {
							alert('删除成功');
							window.location = "/demand/procut";
						} else if (data == 'turnerror') {
							alert('开放金额中有已分配资产，不能删除');
						} else {
							alert('已执行，不能删除！');
						}
					}
				});
			}
		}
		function subMin() {
			$.ajax({
				type : "POST",
				url : '/demand/save/saveminMoney',
				data : $('#form3').serialize(),// 你的formid
				async : false,
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'ok') {
						alert('操作成功');
						window.location = "/demand/procut";
					} else if (data == 'min') {
						alert('请先保存可投金额');
					} else {
						alert('操作失败');
					}
				}
			});
		}
	</script>
</body>
</html>
