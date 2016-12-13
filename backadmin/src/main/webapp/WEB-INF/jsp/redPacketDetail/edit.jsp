<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="css/flow/flow.css" rel="stylesheet" />

<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>
<c:if test="${editType == 'add' }">添加活动</c:if>
<c:if test="${editType == 'update' }">修改活动</c:if>
</title>	
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
					<i class="fa fa-pencil"></i>
					<c:if test="${editType == 'add' }">添加活动</c:if>
                    <c:if test="${editType == 'update' }">修改活动</c:if>
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title"><c:if test="${editType == 'add' }">添加</c:if>
                                                    <c:if test="${editType == 'update' }">修改</c:if></h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">活动名称<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name" width="10px" value="${rule.name}"
										class="form-control" placeholder="活动名称" />
					                    <input type="hidden" name="id" value="${rule.id}" />
					                    <input type="hidden" id="editType" name="editType" value="${editType}" />
								</div>
							</div>
				            <div class="form-group">
								<label class="col-sm-3 control-label">活动类型<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="type" id="type" value="${rule.type}" class="form-control" placeholder="活动类型"/>									
								</div>
							</div>
											            
							<div class="form-group">
								<label class="col-sm-3 control-label">发送类型</label>
								<div class="col-sm-3">
                                   <input type="text" name="sendType" id="sendType" value="${rule.sendType}" class="form-control" placeholder="发送类型"/>
					            </div>
				            </div>
						    <div class="form-group">
								<label class="col-sm-3 control-label">活动规则</label>
								<div class="col-sm-3">
                                   <textarea rows="10" cols="40" id="getRule" name="getRule" >${rule.getRule}</textarea>
					            </div>
				            </div>
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" onclick="reset01()" class="btn btn-default">重置</button>
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
	function reset01(){
		document.form.reset();
	}
	
	function sub(){
        if($("#name").val() == ""){
        	alert("标题不能为空");
        	return false;
        }
        if($("#type").val() == ""){
        	alert("活动类型不能为空");
        	return false;
        }
        if($("#sendType").val() == ""){
        	alert("发送类型不能为空");
        	return false;
        }

			
		$.ajax({
			type : 'POST',
			url : 'redPacketRule/edit',						
			data:$('#form').serialize(),
			success:function(data) {
				<c:if test="${editType == 'update' }">
				if(data == 'update_success'){
					alert("修改成功");					
					window.location.href="redPacketRule/list";				 					
				}else{					
					alert("修改失败");
				}
				</c:if>
				<c:if test="${editType == 'add' }">
				if(data == 'add_success'){
					alert("添加成功");					
					window.location.href="redPacketRule/list";				 					
				}else{					
					alert("添加失败");
				}
				</c:if>
			}
		});
	}
 </script>

</body>
</html>