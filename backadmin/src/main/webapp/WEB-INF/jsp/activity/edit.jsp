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
<c:if test="${editType == 'add' }">添加活动或专题</c:if>
<c:if test="${editType == 'update' }">修改活动或专题</c:if>
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
					<c:if test="${editType == 'add' }">添加活动或专题</c:if>
                    <c:if test="${editType == 'update' }">修改活动或专题</c:if>
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
								<label class="col-sm-3 control-label">标题<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="title" id="title" width="10px" value="${act.title}"
										class="form-control" placeholder="标题" maxlength="25" />
									    <label>注：标题最多25个字</label>
										<input type="hidden" id="editType" name="editType" value="${editType}" />
					                    <input type="hidden" name="id" value="${act.id}" />
					                    <input type="hidden" id="userId" name="userId" value="${act.user.userId}" />
								</div>
								
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">图片<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="imageUrl" id="imageUrl" value="${act.imageUrl}" width="10px" readonly="readonly" class="form-control"/>
										<div id="preview" class="bs-example bs-example-images" style="padding-top: 10px;" >
										 <c:if test="${editType == 'update'}">
										   <img alt="${act.title}" src="${act.imageUrl}" class="img-thumbnail" style="width: 100px;height: 100px;" />
										 </c:if>
										</div>									
								</div>
								<div class="col-sm-3">
									<form id="upload" >
											<input id="fileToUpload1" name="file" type="file"  />
											<input  type="button"  onclick="sufile()" value="上传">
									</form>
									
								</div>
							</div>

				            <div class="form-group">
								<label class="col-sm-3 control-label">URL<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="url" id="url" value="${act.url}" class="form-control" placeholder="URL"/>									
								</div>
							</div>
											            
							<div class="form-group">
								<label class="col-sm-3 control-label">状态</label>
								<div class="col-sm-3">
					        	<select name="status" id="status01" class="form-control"  style="width:150px">					            	
					            	<option value="2" <c:if test="${act.status == '2' }">selected="selected"</c:if> >禁用</option>
					            	<option value="1" <c:if test="${act.status == '1' }">selected="selected"</c:if> >启用</option>
					            </select>
					            </div>
				            </div>
				           <div class="form-group">
								<label class="col-sm-3 control-label">是否结束</label>
								<div class="col-sm-3">
					        	<select name="isEnd" id="isEnd" class="form-control"  style="width:150px">
					        	    <option value="1" <c:if test="${act.isEnd == '1' }">selected="selected"</c:if> >进行中</option>					            	
					            	<option value="2" <c:if test="${act.isEnd == '2' }">selected="selected"</c:if> >结束</option>
					            </select>
					            </div>
				            </div>
							<div class="form-group">
								<label class="col-sm-3 control-label">类型</label>
								<div class="col-sm-3">
					        	<select name="type" id="type" class="form-control"  style="width:150px">					            	
					            	<option value="1" <c:if test="${act.type == '1' }">selected="selected"</c:if> >活动</option>
					            	<option value="2" <c:if test="${act.type == '2' }">selected="selected"</c:if> >专题</option>
					            </select>
					            </div>
				            </div>
				            	<div class="form-group">
								<label class="col-sm-3 control-label">活动设备</label>
								<div class="col-sm-3">
					        	<select name="target" id="target" class="form-control"  style="width:150px">					            	
					            	<option value="1" <c:if test="${act.target == '1' }">selected="selected"</c:if> >PC</option>
					            	<option value="2" <c:if test="${act.target==  '2' }">selected="selected"</c:if> >APP</option>
					            </select>
					            </div>
				            </div>
				           <div class="form-group">
								<label class="col-sm-3 control-label">活动开始时间<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="startTime" id="startTime" width="10px" value="${act.startTime}"
										class="form-control" placeholder="活动开始时间" />
								</div>
							</div>
				           <div class="form-group">
								<label class="col-sm-3 control-label">活动结束时间</label>
								<div class="col-sm-3">
									<input type="text" name="endTime" id="endTime" width="10px" value="${act.endTime}"
										class="form-control" placeholder="活动结束时间" />
								</div>
							</div>							
							<div class="form-group">
								<label class="col-sm-3 control-label" >内容<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<textarea id="content" rows="4" name="content"  class="form-control" maxlength="200" >${act.content}</textarea>
									<label>注：内容最多100个字，超出显示...</label>
								</div>								
							</div>
							<c:if test="${editType == 'update'}">
							<div class="form-group">
								<label class="col-sm-3 control-label" >创建时间</label>
								<div class="col-sm-3">
									<input type="text" class="form-control datepicker" value="<fmt:formatDate value="${act.createTime}" type="both"/>" id="createTime" name="createTime" placeholder="开始时间" style="width:165px" />
						            <script type="text/javascript">	            		            
							            jQuery(document).ready(function(){					
											 jQuery('.datepicker').datetimepicker({
												showSecond : true,
												timeFormat : 'hh:mm:ss',
												stepHour : 1,
												stepMinute : 1,
												stepSecond : 1,
											});				 
										});
					            	</script> 
								</div>								
							</div>							
							</c:if>							
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
		
		$("#preview").find("img").remove();
		document.form.reset();
	}
	
	function sub(){
        if($("#title").val() == ""){
        	alert("标题不能为空");
        	return false;
        }
        if($("#imageUrl").val() == ""){
        	alert("图片不能为空");
        	return false;
        }
        if($("#url").val() == ""){
        	alert("URL不能为空");
        	return false;
        }
        if($("#startTime").val() == ""){
        	alert("活动开始时间不能为空");
        	return false;
        }
/*         if($("#endTime").val() == ""){
        	alert("活动结束时间不能为空");
        	return false;
        } */
        if($("#content").val() == ""){
        	alert("活动内容不能为空");
        	return false;
        }
			
		$.ajax({
			type : 'POST',
			url : 'activity/edit',						
			data:$('#form').serialize(),
			success:function(data) {
				<c:if test="${editType == 'update' }">
				if(data == 'update_success'){
					alert("修改成功");					
					window.location.href="activity/list";				 					
				}else{					
					alert("修改失败");
				}
				</c:if>
				<c:if test="${editType == 'add' }">
				if(data == 'add_success'){
					alert("添加成功");					
					window.location.href="activity/list";				 					
				}else{					
					alert("添加失败");
				}
				</c:if>
			}
		});
	}
	function sufile(){
		 var formdata = new FormData();               
		    var fileArr = document.getElementById("fileToUpload1").files;               
            if(fileArr.length <= 0){
            	alert("请选择要上传的文件");
            	return false;
            }
		    
		    for(var i=0;i<fileArr.length;i++){
            	formdata.append("file", fileArr[i]);
            }
            
		$.ajax({
			type : 'POST',
			url : '/activity/uploadImages',						
			data:formdata,
			contentType: false,
			 processData: false,
			success:function(data) {
				var arr = $.parseJSON(data);
				$('#logo').val(data);
				$('#preview').html("");
				for(var i=0;i<arr.length;i++){
					var image_src = '<%=basePath%>'+arr[i];
					
					image_src = image_src.replace("/upload","upload");
				   $('#preview').append('<img src=\"'+image_src+'\" class=\"img-thumbnail\" width=\"100\" height=\"100\" alt=\"\" />');
				   if(i == 0){					   
					   $("#imageUrl").val(arr[i]);
				   }
				}
			},
			error:function(e){
				alert(e);
			}
		});
	}
	
 </script>

</body>
</html>