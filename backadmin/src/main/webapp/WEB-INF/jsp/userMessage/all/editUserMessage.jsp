<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href='${ctx}/ueditor/themes/default/css/ueditor.min.css' type="text/css" rel="stylesheet" >
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>  
<!-- 编辑器源码文件 -->  
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>  
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) 此处可要可不要 -->   
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/js/validate.js"></script>
<title>编辑短信模板</title>
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
					<i class="fa fa-pencil"></i>编辑短信模板
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">编辑短信模板</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">短信模板号 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="id" id="id"  width="10px"
										class="form-control"  value="${messageTemplate.id}" readonly />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">名称<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name"  width="10px"
										class="form-control"  value="${messageTemplate.id}"  />
										<div id="errorName" style="display: none;">名称不能为空</div>									
								</div>
							</div>																						
							<div class="form-group">
								<label class="col-sm-3 control-label">描述</label>
								<div class="col-sm-3" style="width:40%">								
									<textarea rows="3" name="description"  id="description" class="form-control" placeholder="备注">${messageTemplate.description}</textarea>	
								</div>																									
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">短信模板 <span
									class="asterisk">*</span></label>
									 
								<div class="col-sm-3" style="width:74%">
									<textarea name="template" id="template" style="width: 100%;height: 300px;" placeholder="text"
									>${messageTemplate.template}</textarea>
									<div><span class="asterisk"> 短信内容要符合此形式：金额 #{money},认证码 #{authCode},项目名称 #{loanName},日期 #{date},手续费 #{fee},可用余额 #{balance}</span>
									</div>
									<script type="text/javascript">
										window.onload = function() {        
											var ue = UE.getEditor('template');	  
										}  
									</script> 
								</div>
							</div>							
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="return sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<script type="text/javascript">
			function sub(){
				$("#errorName").attr("style", "display: none");
				var name = $("#name").val();
				if(name == ''){
					$("#errorName").attr("style", "display:block;font-size:14px;color:red");
					return ;
				}
				$.ajax({
					type : 'POST',
					url : '${ctx}/createOrEditUserMessage/edit',						
					data:$('#form').serialize(),
					
					success:function(data) {
						if(data == 1){
							alert("修改成功");
							location.replace(document.referrer);						 					
						}else if(data == 0){
							$('#errorId').text("编号不存在");
							$('#errorId').attr("style", "display:block;font-size:14px;color:red");
							return ;
						}else if(data == -1){
							$('#errorName').text("名称重复");
							$('#errorName').attr("style", "display:block;font-size:14px;color:red");
							return ;
						}else{					
							alert("修改失败");
						}
					}
				});			
			}	
			
			function ret(){
				
				$("#errorName").attr("style", "display: none");
				document.form.reset();
			}				
		</script>		
	</section>
</body>
</html>