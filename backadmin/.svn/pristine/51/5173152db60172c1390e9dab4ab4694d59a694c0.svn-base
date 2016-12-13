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
添加手机APP启动图
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
					<i class="fa fa-pencil"></i>添加手机APP启动图
                                                
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">添加手机APP启动图
                                  
						</div>
						<div class="panel-body">
						
						
						
						<div class="form-group">
								<label class="col-sm-3 control-label"></label>
								<div class="col-sm-3">
									<span class="asterisk">上传图片命名规则：android_/ios_+版本(例如V1_)+尺寸(尺寸共有四个：hdpi、xhdpi、xxhdpi、xxxhdpi),例如:android_V5_hdpi.png,版本不变,共八个名称对应八张图片.请严格按照命名规则来命名上传图片！</span>
								</div>								
							</div>		
							<div class="form-group">
								<label class="col-sm-3 control-label">图片<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="picPosition" id="picPosition" value="${app.picPosition}" width="10px" readonly="readonly" class="form-control"/>
										<div id="preview" class="bs-example bs-example-images" style="padding-top: 10px;" >
										 <c:if test="${type == 'update'}">
										   <img alt="${app.picName}" src="${app.picPosition}" class="img-thumbnail" style="width: 100px;height: 100px;" />
										 </c:if>
										</div>									
								</div>
								<div class="col-sm-3">
									<form id="upload" >
											<input id="fileToUpload1" name="file" type="file"  multiple="multiple" size="3" />
											<input  type="button"  onclick="sufile()" value="上传"><span class="asterisk">注意：请上传八张启动图</span>
									</form>
									
								</div>
							</div>   							
							<div class="form-group">
								<label class="col-sm-3 control-label">状态</label>
								<div class="col-sm-3">
					        	<select name="picStatus" id="picStatus" class="form-control"  style="width:150px">					            	
					            	<option value="0" <c:if test="${app.picStatus == '0' }">selected="selected"</c:if> >未使用</option>
					            	<option value="1" <c:if test="${app.picStatus == '1' }">selected="selected"</c:if> >使用</option>
					            </select>
					            </div>
				            </div>			   
							<div class="form-group">
								<label class="col-sm-3 control-label">描述 </label>
								<div class="col-sm-3">
									<textarea rows="4" name="description"  class="form-control">${app.description}</textarea>
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
		$("#preview").find("img").remove();
		document.form.reset();
	}
	
	function sub(){
         

	    if($("#picPosition").val() == ""){
	    	alert("图片不能为空！");
	    	return false;
	    }
	    
		
		
		$.ajax({
			type : 'POST',
			url : '/appstartdiagram/add',						
			data:$('#form').serialize(),
			success:function(data) {
				
				if(data == 'add_success'){
					alert("添加成功");					
					window.location.href="appstartdiagram/list";				 					
				}else{					
					alert("添加失败");
				}

			}
		});
	}
	function sufile(){
		 var formdata = new FormData();               
		    var fileArr = document.getElementById("fileToUpload1").files;      
		    if(fileArr.length!=8){
		    	alert("请上传8张APP启动图!");
			    return false;
		    }
            for(var i=0;i<fileArr.length;i++){
            	formdata.append("file", fileArr[i]);
            } 
		$.ajax({
			type : 'POST',
			url : '/appstartdiagram/uploadImages',						
			data:formdata,
			contentType: false,
			 processData: false,
			success:function(data) {
				
				if(data.substring(0,1)=="第"){
					alert(data);
				}
				else{
				var arr = $.parseJSON(data);
				
				$('#logo').val(data);
				$('#preview').html("");
				for(var i=0;i<arr.length;i++){
					var image_src = '<%=basePath%>'+arr[i];
					image_src = image_src.replace("/app","app");
				   $('#preview').append('<img src=\"'+image_src+'\" class=\"img-thumbnail\" width=\"100\" height=\"100\" alt=\"\" />');
				   if(i == 0){					   
					   $("#picPosition").val(arr[i]);
				   }
				}
				}
			},
			error:function(e){
                alert(e);
				//alert("上传图片过大，每张图片不得大于500kb！");
			}
		});
	}
 </script>

</body>
</html>