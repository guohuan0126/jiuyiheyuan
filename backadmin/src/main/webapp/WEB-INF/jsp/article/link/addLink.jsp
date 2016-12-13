<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />

<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>友情链接</title>
</head>
<body>
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../../base/leftMenu.jsp"%>
		<div class="mainpanel">
			<%@include file="../../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-pencil"></i> 添加友情链接
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">添加链接链接</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">编号 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="id" id="id" width="10px"
										class="form-control" placeholder="编号" />
									<div id="errorId" style="display: none;">编号不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">名称 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name" width="10px"
										class="form-control" placeholder="名称不能为空" />
									<div id="errorName" style="display: none;">名称不能为空</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">标题 </label>
								<div class="col-sm-3">
									<input type="text" name="secondTitle" id="secondTitle"
										width="10px" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">类型<span
									class="asterisk">*</span></label> <select name="type"
									class="form-control" style="width:150px">
									<c:forEach var="type" items="${types}">
										<option value="${type.id}">${type.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">状态</label>
								<div class="col-sm-3">
									<select name="picStatus" id="picStatus" class="form-control"
										style="width:150px">
										<option value="0"
											<c:if test="${link.status == '0' }">selected="selected"</c:if>>禁用</option>
										<option value="1"
											<c:if test="${link.status == '1' }">selected="selected"</c:if>>启用</option>
									</select>
								</div>
								
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">URL</label>
								<div class="col-sm-3">
									<input type="text" name="url" id="url" width="10px"
										class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">LOGO</label>
								<div class="col-sm-3">
									<input type="text" name="logo" id="logo" width="10px"
										class="form-control" />
									<div id="preview"></div>
								</div>
								<div class="col-sm-3">
									<form id="upload">
										<input id="fileToUpload2" name="file" type="file" /> <input
											type="button" onclick="sufile()" value="上传">

									</form>

								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">Email</label>
								<div class="col-sm-3">
									<input type="text" name="masterEmail" id="masterEmail"
										width="10px" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">位置</label>
								<div class="col-sm-3">
									<input type="text" name="persition" id="persition" width="10px"
										class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">久亿时间</label>
								<div class="col-sm-3">
									<input type="text" name="jiuyiTime" id="jiuyiTime"
										width="10px" class="ui_timepicker" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">排序</label>
								<div class="col-sm-3">
									<input type="number" name="seqNum" id="seqNum" width="6px"
										value="0" min="0" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">描述 </label>
								<div class="col-sm-3">
									<textarea rows="4" name="description" class="form-control"></textarea>
								</div>
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button" onclick="sub()">保存</button>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" onclick="reset()"
											class="btn btn-default">重置</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- panel -->
				</form>

			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$(".ui_timepicker").datetimepicker({
				//showOn: "button",
				//buttonImage: "./css/images/icon_calendar.gif",
				//buttonImageOnly: true,
				showSecond : true,
				//minDate: new Date(),
				timeFormat : 'hh:mm:ss',
				stepHour : 1,
				stepMinute : 1,
				stepSecond : 1
			});
		});
		function reset() {
			$("#errorId").attr("style", "display:none;");
			$("#errorName").attr("style", "display:none;");
			document.form.reset();
		}

		function sub() {
			$("#errorId").attr("style", "display:none;");
			$("#errorName").attr("style", "display:none;");
			var id = $("#id").val();
			var name = $("#name").val();

			if (id == '') {
				$('#errorId').attr("style",
						"display:block;font-size:14px;color:red");
				return;
			}

			if (name == '') {
				$('#errorName').attr("style",
						"display:block;font-size:14px;color:red");
				return;
			}
			$.ajax({
				type : 'POST',
				url : '${ctx}/article/link/add',
				data : $('#form').serialize(),
				success : function(data) {
					if (data == 'ok') {
						alert("添加成功");
						location = "${cxt}/article/linkList";
					} else if (data == 'notNull') {
						$('#errorId').text("编号重复");
						$('#errorId').attr("style",
								"display:block;font-size:14px;color:red");
						return;
					} else {
						alert("添加失败");
					}
				}
			});
		}
		function sufile() {
			var formdata = new FormData();
			var fileObj = document.getElementById("fileToUpload2").files;
			for (var i = 0; i < fileObj.length; i++)
				formdata.append("file", fileObj[0]);
			$
					.ajax({
						type : 'POST',
						url : '/article/uploadArticleData',
						data : formdata,
						contentType : false,
						processData : false,
						success : function(data) {
							$('#logo').val(data);
							$('#preview')
									.html(
											'<img src=\"${pageContext.request.contextPath}'+data+'\" width=\"100\" height=\"100\" alt=\"\" />');

						},
						error : function(e) {
							alert(e);
						}
					});
		}
	</script>

</body>
</html>
