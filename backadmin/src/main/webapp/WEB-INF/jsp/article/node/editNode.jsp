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
<link rel="shortcut icon" href="images/favicon.png" type="image/png">

<link href='${ctx}/ueditor/themes/default/css/ueditor.css' type="text/css" rel="stylesheet" >
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>  
<!-- 编辑器源码文件 -->  
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>  
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) 此处可要可不要 -->   
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	window.onload = function() {        
		var ue = UE.getEditor('content');		
	}  
</script>  
<title>编辑文章</title>
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
					<i class="fa fa-pencil"></i> 编辑文章 
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" name="upfile" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">编辑文章</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">编号</label>
								<div class="col-sm-3">
									<input type="text" name="id" id="id" width="10px" value="${node.id}" class="form-control" readonly/>							
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">标题 </label>
								<div class="col-sm-3">
									<input type="text" name="title" id="title"  width="10px"
										class="form-control" value="${node.title}"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">副标题 </label>
								<div class="col-sm-3">
									<input type="text" name="subTitle" id="subTitle"  width="10px"
										class="form-control" value="${node.subTitle}"/>									
								</div>
							</div>
							<div class="form-group">   
								<label class="col-sm-3 control-label">状态 </label>  
								<div class="col-sm-1">  
								<select name="status" >
									<c:choose>
										<c:when test="${node.status == 1}">
											<option value="1">发表</option>
											<option value="0">未发表</option>
										</c:when>										
										<c:otherwise>
											<option value="0">未发表</option>
											<option value="1">发表</option>
										</c:otherwise>
									</c:choose>					                    				                    	                   			                                     
				                </select>	
				                </div>	
				                </div>
				                <div class="form-group">  
				                	<label class="col-sm-3 control-label">文章来源</label>	
							<div class="col-sm-2">							
								<input type="text"  name="source" value="${node.source}"  class="form-control" />
						</div>		                		                   							
							</div>
							  <div class="form-group">  
				                	<label class="col-sm-3 control-label">关键字</label>	
							<div class="col-sm-2">							
								<input type="text"  name="keywords" value="${node.keywords}"  class="form-control" />
						</div>		                		                   							
							</div>
							
							
							
							
							<div class="form-group">   
		           				 <label class="col-sm-3 control-label">是否推送app : </label>
		           				 <div class="col-sm-1">
		            			<select name="flag" class="form-control" style="width: 100px">
		            			<option value="0"<c:if test="${node.flag == '0'}">selected="selected"</c:if>>否</option>
								<option value="1"<c:if test="${node.flag == '1'}">selected="selected"</c:if>>是</option>	
							</select>
							</div>
							
							
							</div>
							<div class="form-group"> 
							 <label class="col-sm-3 control-label">上传图片</label>
				             <div class="col-sm-2">
									
 											<input id="fileToUpload1" name="file" type="file"  multiple="multiple" size="3" /> 
 											<input  type="button"  onclick="sufile()" value="上传"> 
								      
									
								</div>   
								     
							</div>	
							<div class="form-group">									
								<label class="col-sm-3 control-label">图片路径</label>	
							<div class="col-sm-3">							
								<input type="text"  name="pictureUrl" id="pictureUrl" value="${node.pictureUrl}"  class="form-control" readonly ="true" />
								<div id="preview" class="bs-example bs-example-images" style="padding-top: 10px;" >
										<c:if test="${type == 'update'}">
										   <img alt="" src="" class="img-thumbnail" style="width: 100px;height: 100px;" />
										 </c:if>
										
										</div>
								<script type="text/javascript">
								window.onload = function() {        
									var ue = UE.getEditor('content');	
								 	var image_src = '<%=basePath%>'+$('#pictureUrl').val();
									image_src = image_src.replace("/upload","upload");
								   $("#preview").append('<img src=\"'+image_src+'\" class=\"img-thumbnail\" width=\"100\" height=\"100\" alt=\"\" />');
								}  
									</script> 
								
					       	</div>
							</div>
							<div class="form-group">   
								<label class="col-sm-3 control-label">分类术语 </label> 
								<center><div style="width:50%">  
										<select id="term" name="term" style="float:left;" onchange="getTrem();">
											<option value="">--请选择--</option>			                      
						                    <c:forEach var="categoryTerm" items="${categoryTerms}">
						                    	<option value="${categoryTerm.name}">${categoryTerm.name}</option>
						                    </c:forEach>	                   			                                     
						                </select><br><br>		
						                <input type="text" name="categoryTerm"  id="categoryTerm"
												class="form-control" value="分类术语：${node.term}" />	 
										</div>
								</center> 
								<script type="text/javascript">
									function getTrem(){
										var term = $("#categoryTerm").val();										
										term += $("#term").val()+"、";
										$("#categoryTerm").val(term);									
									}
								
								</script>                  							
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">描述</label>
								<div class="col-sm-3" style="width:40%">								
									<textarea rows="3" name="description"  id="description" class="form-control">${node.description}</textarea>	
								</div>																									
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">内容 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3" style="width:74%">
									<textarea name="content" id="content" style="width: 100%;height: 300px;">${node.content}</textarea>
									<div id="errorContent" style="display: none;">内容不能为空</div>
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
				$("#errorId").attr("style", "display: none");
				$("#errorContent").attr("style", "display: none");
				var id = $("#id").val();
				var content = UE.getEditor('content').getContent();												
				if(id == ''){
					$("#errorId").attr("style", "display:block;font-size:14px;color:red");
					return false;
				}
				if(content == ''){
					$("#errorContent").attr("style", "display:block;font-size:14px;color:red");
					return false;
				}
				$.ajax({
					type : 'POST',
					url : '${ctx}/article/createNode/edit',						
					data:$('#form').serialize(),
					beforeSend:function(){
						xval=getBusyOverlay('viewport',
						{color:'', opacity:0.5, text:'正在更新...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
						{color:'blue', size:25, type:'o'});	
					},
					success:function(data) {										
						window.clearInterval(xval.ntime); 
						xval.remove();
						if(data == 'ok'){
							alert("编辑成功");
							location.replace(document.referrer);						 					
						}else if(data == 'isNull'){
							alert("编号不存在");
							$('#errorId').attr("style", "display:block;font-size:14px;color:red");
							return ;
						}else{					
							alert("编辑失败");
						}
					}
				});			
			}	
			function sufile(){
				 var formdata = new FormData();               
				    var fileArr = document.getElementById("fileToUpload1").files;               
		            for(var i=0;i<fileArr.length;i++){
		            	formdata.append("file", fileArr[i]);
		            } 
				$.ajax({
					type : 'POST',
					url : '/article/uploadImages',						
					data:formdata,
					contentType: false,
					 processData: false,
					success:function(data) {	
						$("#preview").html("");
						$('#pictureUrl').val(data);
				     	var image_src = '<%=basePath%>'+data;
							image_src = image_src.replace("/upload","upload");
						   $("#preview").append('<img src=\"'+image_src+'\" class=\"img-thumbnail\" width=\"100\" height=\"100\" alt=\"\" />');						
					},
					error:function(e){
					
						alert(e);
					}
				});
			}
		</script>		
	</section>
</body>
</html>