<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../base/base.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>添加黑名单</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../base/header.jsp" />
		<jsp:include page="../base/menu.jsp" /> 
		<!-- /. NAV SIDE  -->
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<h2 class="page-header">
							当前位置： <small><c:choose>
									<c:when test="${empty cache}">添加key</c:when>
									<c:otherwise>添加黑名单</c:otherwise>
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
									<div class="col-lg-3">
										<form role="form" id="formBlack">
											<div class="form-group">
												<label class="control-label">content</label><input type="text"
													name="content" id="content" width="10px" maxlength="20" required
													class="form-control"/>												
											</div>
											<div class="form-group">
												<label class="">黑名单类型</label> <select id="type" name="type"
													class="form-control" required placeholder="黑名单类型">
													<option value="IP">IP</option>
								                    <option value="MOBILE_NO">MOBILE_NO</option>	
												</select>
											</div>											
											<div class="form-group">
												<label class="">说明</label>
												<textarea rows="3" name="info" id="info" cols="48"
													class="form-control"></textarea>

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
	$.ajax({						
			type : "POST",
			url : "${ctx}/sys/black/add",
			data : $("#formBlack").serialize(), //要发送的是ajaxFrm表单中的数据
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
				if(data == "success"){
					alert("保存成功!");
					location = '${ctx}/sys/toBlackManager';
				}else{
					alert("保存失败！");
				}
			}
		});
	}
	</script>
</body>
</html>
