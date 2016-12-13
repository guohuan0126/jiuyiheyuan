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
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>友情链接管理</title>	
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
					<i class="fa fa-pencil"></i> 修改链接
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">修改链接</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">编号 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="id" id="id" width="10px"
										class="form-control" placeholder="编号" value="${link.id}" readonly/>
									<div id="errorId" style="display: none;">编号不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">名称 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name" width="10px"
										class="form-control" placeholder="名称不能为空" value="${link.name}"/>
									<div id="errorName" style="display: none;">名称不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">标题 </label>
								<div class="col-sm-3">
									<input type="text" name="secondTitle" id="secondTitle" width="10px"
										class="form-control"  value="${link.secondTitle}"/>
									
								</div>
							</div>
							<div class="form-group">
					        	<label class="col-sm-3 control-label">类型<span
									class="asterisk">*</span></label>
					        	<select name="type" id="bct" class="form-control" style="width:150px">						        						            	
					            	<c:forEach var="type" items="${types}">
					            		<%-- <c:choose>
					            			<c:when test="${type.id == link.linkType.id}">
					            				<option value="${type.id}" selected>${type.name}</option>
					            			</c:when>
					            			<c:otherwise> --%>
					            				<option value="${type.id}">${type.name}</option>
					            			<%-- </c:otherwise>
					            		</c:choose>	 --%>				            		
		            				</c:forEach>
					            </select>
				            </div>
				            <div class="form-group">
								<label class="col-sm-3 control-label">URL</label>
								<div class="col-sm-3">
									<input type="text" name="url" id="url" width="10px"
										class="form-control" value="${link.url}"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">LOGO</label>
								<div class="col-sm-3">
									<input type="text" name="logo" id="logo" width="10px"
										class="form-control" value="${link.logo}"/>	
										<div id="preview"><img src="${pageContext.request.contextPath}${link.logo}" width="100" height="100" alt="" /></div>								
								</div>
								<div class="col-sm-3">
									<form id="upload"   >
											<input id="fileToUpload2" name="file" type="file" />
											<input  type="button"  onclick="sufile()" value="上传">
									</form>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">Email</label>
								<div class="col-sm-3">
									<input type="text" name="masterEmail" id="masterEmail" width="10px"
										class="form-control" value="${link.masterEmail}"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">状态</label>
								<div class="col-sm-3">
					        	<select name="picStatus" id="picStatus" class="form-control"  style="width:150px">					            	
					            	<option value="0" <c:if test="${link.status == '0' }">selected="selected"</c:if> >禁用</option>
					            	<option value="1" <c:if test="${link.status == '1' }">selected="selected"</c:if> >启用</option>
					            </select>
					            </div>
				            </div>
				            
							<div class="form-group">
								<label class="col-sm-3 control-label">位置</label>
								<div class="col-sm-3">
									<input type="text" name="position" id="position" width="10px"
										class="form-control" value="${link.position}"/>									
								</div>
							</div>
						<div class="form-group">
		                  <label class="col-sm-3 control-label">久亿时间<!-- <span
									class="asterisk">*</span> --></label>
								
							<div class="col-sm-3">
							<input type="text" name="jiuyiTime"  value="<fmt:formatDate value="${link.jiuyiTime}" pattern="yyyy-MM-dd HH:mm:ss"  />" id="jiuyiTime"
								class="ui_timepicker" /> 
							</div>
						</div>
						
							<div class="form-group">
								<label class="col-sm-3 control-label">排序</label>
								<div class="col-sm-3">
									<input type="number" name="seqNum" id="seqNum" width="6px"
										 value="${link.seqNum}" min="0"/>																	
								</div>
							</div>
						
						<div>
							<div class="form-group">
								<label class="col-sm-3 control-label">描述 </label>
								<div class="col-sm-3">
									<textarea rows="4" name="description" class="form-control">${link.description}</textarea>
								</div>								
							</div>
							</div>								
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="sub()">更新</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="reset" class="btn btn-default">重置</button>
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
	jQuery(document).ready(function(){
		$("#bct").val('${link.type}');
		$(".ui_timepicker").datetimepicker({
// 			showOn: "button",
// 			buttonImage: "/css/images/icon_calendar.gif",
// 			buttonImageOnly: true,
			showSecond : true,
			stepHour : 1,
			stepMinute : 1,
			stepSecond : 1,
		});
	});
	function ret(){	
		$("#errorId").attr("style", "display:none;");
		$("#errorName").attr("style", "display:none;");
		document.form.reset();
	}
	
	function sub(){
		$("#errorId").attr("style", "display:none;");
		$("#errorName").attr("style", "display:none;");
		var id = $("#id").val();
		var name = $("#name").val();
		
		if(id == ''){
			$('#errorId').attr("style", "display:block;font-size:14px;color:red");
			return;
		}
		
		if(name == ''){
			$('#errorName').attr("style", "display:block;font-size:14px;color:red");
			return;
		}
		$.ajax({
			type : 'POST',
			url : '${ctx}/article/link/edit',						
			data:$('#form').serialize(),
			success:function(data) {
				if(data == 'ok'){
					alert("修改成功");
					location.replace(document.referrer);	 					
				}else if(data == 'isNull'){
					$('#errorId').text("要修改的链接不存在");
					$('#errorId').attr("style", "display:block;font-size:14px;color:red");
					return ;
				}else{					
					alert("修改失败");
				}
			}
		});
	}
	function sufile(){
		 var formdata = new FormData();               
		    var fileObj = document.getElementById("fileToUpload2").files;               
		        for (var i = 0; i < fileObj.length; i++)                   
		        formdata.append("file", fileObj[0]);   
		$.ajax({
			type : 'POST',
			url : '/article/uploadArticleData',						
			data:formdata,
			contentType: false,
			 processData: false,
			success:function(data) {
				$('#logo').val(data);
				$('#preview').html('<img src=\"${pageContext.request.contextPath}'+data+'\" width=\"100\" height=\"100\" alt=\"\" />');
			},
			error:function(e){
				alert(e);
			}
		});
	}
 </script>
	
</body>
</html>
