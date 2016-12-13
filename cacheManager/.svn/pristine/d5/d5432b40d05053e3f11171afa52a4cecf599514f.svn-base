<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="base/base.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>添加/编辑key</title>
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
							当前位置： <small><c:choose>
									<c:when test="${empty cache}">添加key</c:when>
									<c:otherwise>编辑key</c:otherwise>
								</c:choose></small>
						</h2>
					</div>
				</div>
				<!-- /. ROW  -->
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading"></div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<form role="form" id="formCache">
											<div class="form-group">
												<label class="control-label">key</label> <input type="text"
													name="key" id="key" width="10px" maxlength="20"
													class="form-control" value="${cache.key}" /> <input
													type="hidden" name="id" id="id" width="10px" maxlength="20"
													class="form-control" value="${cache.id}" />
											</div>
											<div class="form-group">
												<label class="">数据类型</label> <select id="type" name="type"
													class="form-control" required placeholder="缓存类型">
													<option value="1"
														<c:if test="${cache.type eq '1' }">selected</c:if>>string</option>
													<option value="2"
														<c:if test="${cache.type eq '2' }">selected</c:if>>hash</option>
													<option value="3"
														<c:if test="${cache.type eq '3' }">selected</c:if>>list</option>
													<option value="4"
														<c:if test="${cache.type eq '4' }">selected</c:if>>set</option>
													<option value="5"
														<c:if test="${cache.type eq '5' }">selected</c:if>>sortedSet</option>
												</select>
											</div>
											<div class="form-group">
												<label class="">系统</label> <select id="domain" name="domain"
													class="form-control" required data-placeholder="系统">
													<option value="1"
														<c:if test="${cache.domain eq '1' }">selected</c:if>>pc</option>
													<option value="2"
														<c:if test="${cache.domain eq '2' }">selected</c:if>>m</option>
													<option value="3"
														<c:if test="${cache.domain eq '3' }">selected</c:if>>soa-app</option>
													<option value="4"
														<c:if test="${cache.domain eq '4' }">selected</c:if>>soa-yeepay</option>
													<option value="5"
														<c:if test="${cache.domain eq '5' }">selected</c:if>>soa-pc</option>
													<option value="6"
														<c:if test="${cache.domain eq '6' }">selected</c:if>>admin</option>
													<option value="7"
														<c:if test="${cache.domain eq '7' }">selected</c:if>>cps</option>
												</select>
											</div>
											<div class="form-group">
												<label class="">说明</label>

												<textarea rows="3" name="info" id="info" cols="48"
													class="form-control">${cache.info}</textarea>

											</div>
											<div class="form-group">
												<label class="">描述</label>

												<textarea id="descript" name="descrip" rows="4" cols="48"
													class="form-control">${cache.descrip}</textarea>

											</div>

											<div class="form-group">
												<label class="">备注</label>
												<textarea id="remark" name="remark" rows="4" cols="48"
													class="form-control">${cache.remark}</textarea>
											</div>

											<div class="form-group">
												<label class="">申请人</label> <input type="text"
													name="applicant" id="applicant" width="10px" maxlength="20"
													class="form-control" value="${cache.applicant}" />
											</div>
											<div class="form-group" style="text-align:center;">
												<button type="button" class="btn btn-primary"
													style="margin-right:25%;padding:5px 24px;" onclick="sub();">提交</button>
												<button type="reset" class="btn btn-primary" style="padding:5px 24px;">重置</button>
											</div>
										</form>
									</div>
									<!-- /.col-lg-6 (nested) -->
								</div>
								<!-- /.row (nested) -->
							</div>
							<!-- /.panel-body -->
						</div>
						<!-- /.panel -->
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<footer></footer>
			</div>
			<!-- /. PAGE INNER  -->
		</div>
		<!-- /. PAGE WRAPPER  -->
	</div>
	<script>
	function sub(){
	var url = '${ctx}/cache/cacheKey/add';
	if('${cache}' != ''){
		url = '${ctx}/cache/cacheKey/edit';
	}
	$.ajax({						
			type : "POST",
			url : url,
			data : $("#formCache").serialize(), //要发送的是ajaxFrm表单中的数据
			async : false,						
			beforeSend:function(){
				xval=getBusyOverlay('viewport',
					{color:'blue', opacity:0.5, text:'正在提交...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
			},
			error:function(e){
				window.clearInterval(xval.ntime); 
				xval.remove();
				alert(e);
			},
			success : function(data) {
				window.clearInterval(xval.ntime); 
				xval.remove();
				if(data == "ok"){
					alert("保存成功!");
					location = '${ctx}/cache/toCacheKeyManager';
				}else{
					alert("保存失败！");
				}
			}
		});
	}
	</script>
</body>
</html>
