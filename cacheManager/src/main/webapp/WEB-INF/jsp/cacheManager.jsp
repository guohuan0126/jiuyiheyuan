<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="base/base.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>缓存管理</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="base/header.jsp" />
		<jsp:include page="base/menu.jsp" />
		<!-- /. NAV SIDE  -->
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<h2 class="page-header">
							当前位置： <small>缓存查询</small>
						</h2>
					</div>
				</div>
				<!--  搜索部分 -->
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading"></div>
							<div class="panel-body">
								<div class="table-responsive">
									<div class="row">
										<div class="col-sm-12">
											<span>请输入redis命令，点击执行按钮（redis 命令参考文档：<a href="http://redisdoc.com/" target="_blank">http://redisdoc.com/</a>）</span>
											<div class="form-group form-inline"
												style="margin-bottom:18px;">		
												<textarea id="msg" class="form-control" rows="4" cols="67"
													style="width: auto;"></textarea>
												<input type="button" class="btn btn-success"
													style="margin-left:30px; vertical-align:bottom;" value="执行"
													onclick="excute();" />
											</div>


											<form role="form" class="form-inline" method="post"
												id="cacheform">
												<div class="form-group">
													<input type="text" name="key" id="key" class="form-control"
														placeholder="key" value="${cacheKey.key}"
														style="width:160px">
												</div>
												<div class="form-group">
													<input type="text" name="hashKey" id="hashKey"
														class="form-control" placeholder="hashKey"
														value="${cacheKey.hashKey}" style="width:160px">
												</div>
												<label class="lable-inline">缓存类型 ：</label>
												<div class="form-group" style="width: 110px;">
													<select id="type" name="type" class="form-control" required
														placeholder="缓存类型">
														<option value="1"
															<c:if test="${cacheKey.type eq '1' }">selected</c:if>>string</option>
														<option value="2"
															<c:if test="${cacheKey.type eq '2' }">selected</c:if>>hash</option>
														<option value="3"
															<c:if test="${cacheKey.type eq '3' }">selected</c:if>>list</option>
														<option value="4"
															<c:if test="${cacheKey.type eq '4' }">selected</c:if>>set</option>
														<%-- <option value="5" <c:if test="${cacheKey.type eq '5' }">selected</c:if>>sortedSet</option>                    --%>
													</select>
												</div>
												<button class="btn btn-success form-control" type="button"
													onclick="javascript:get();" style="margin-left:26px">
													<i class="fa fa-search"></i>
												</button>
											</form>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<h4 class="page-header">数据</h4>
											<div id="cacheData"></div>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<footer></footer>
	</div>
	</div>
	<script type="text/javascript">
		function get() {
			$("#cacheData").find("div").remove();
			$
					.ajax({
						type : "POST",
						url : "${ctx}/cache/cacheManager/get",
						data : $("#cacheform").serialize(),
						error : function(e) {
							alert(e);
						},
						success : function(data) {
							var obj = JSON.parse(data);
							var str = "";
							if ($("#type").val() == "2") {
								$
										.each(
												obj,
												function(key, value) {
													str += "<div>"
															+ key
															+ ":"
															+ value
															+ "<a style='margin-left:40px;' data="+key+" class='del'>删除</a></div>"
												});
							} else {
								if ($("#type").val() == "1") {
									str += "<div>"
											+ obj
											+ "<a style='margin-left:40px;' href='javascript:void(0)'>删除</a></div>"
								} else {
									for (var i = 0; i < obj.length; i++) {
										str += "<div>"
												+ obj[i]
												+ "<a style='margin-left:40px;' data="+obj[i]+" class='del'>删除</a></div>"
									}
								}
							}
							$("#cacheData").append(str);
							var key = $("#key").val();
							var type = $("#type").val();
							$(".del").on("click", function() {
								var d = $(this).attr("data");
								if (confirm("确认删除么？")) {

									$.ajax({
										type : "POST",
										url : "${ctx}/cache/cacheManager/del",
										data : {
											key : key,
											type : type,
											d : d
										},
										error : function(e) {
											alert(e);
										},
										success : function() {
											$(this).parent('div').remove();
											alert("删除成功！")

										}
									});
								}
							});
						}
					});
		}

		function excute() {

			$("#cacheData").find("div").remove();

			$.ajax({
				type : "POST",
				url : "${ctx}/cache/cacheManager/excute",
				data : {
					msg : $("#msg").val()
				},
				error : function(e) {
					alert(e);
				},
				success : function(data) {				
					var str = "<div>" + data + "</div>";
					$("#cacheData").append(str);
				}
			});

		}
	</script>
</body>
</html>
