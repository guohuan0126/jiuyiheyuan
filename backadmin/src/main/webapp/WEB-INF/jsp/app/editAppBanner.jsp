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
<c:if test="${type == 'add' }">添加手机APPBanner图</c:if>
<c:if test="${type == 'update' }">修改手机APPBanner图</c:if>
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
					<i class="fa fa-pencil"></i><c:if test="${type == 'add' }">添加手机APPBanner图</c:if>
                                                <c:if test="${type == 'update' }">修改手机APPBanner图</c:if>
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title"><c:if test="${type == 'add' }">添加手机APPBanner图</c:if>
                                                    <c:if test="${type == 'update' }">修改手机APPBanner图</c:if></h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">标题<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="picName" id="picName" width="10px" value="${app.picName}"
										class="form-control" placeholder="标题" />
										<input type="hidden" id="type" name="type" value="${type}" />
					                    <input type="hidden" id="picID" name="picID" value="${app.picID}" />
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
											<input  type="button"  onclick="sufile()" value="上传"><span class="asterisk">注意：请上传三张Banner图片。图片命名不可以使用中文或*，建议统一为name_share（name为自定义内容）</span>
									</form>
									
								</div>
							</div>
  <div class="form-group">
			<label class="col-sm-3 control-label">分享图片<span class="asterisk">*</span></label>
			<div class="col-sm-3">
				<input type="text" name="shareImgUrl" id="shareImgUrl" value="${app.shareImgUrl}" width="10px" readonly="readonly" class="form-control"/>
				<div id="preview2" class="bs-example bs-example-images" style="padding-top: 10px;">
				 <c:if test="${type == 'update'}">
				<img alt="${app.picName}" src="${app.shareImgUrl}" class="img-thumbnail" style="width: 100px;height: 100px;" />
				</c:if>
				</div>					
			</div>
			<div class="col-sm-3">
				<form id="upload1"   >
						<input id="fileToUpload2" name="file" type="file" />
						<input  type="button"  onclick="sufile2()" value="上传">
					<span class="asterisk">注意：请上传正方形尺寸为100*100的图片。图片命名不可以使用中文或*，建议统一为name_share（name为自定义内容）</span>	
				</form>				
			</div>
</div>     							
							<div class="form-group">
								<label class="col-sm-3 control-label">状态</label>
								<div class="col-sm-3">
					        	<select name="picStatus" id="picStatus" class="form-control"  style="width:150px">					            	
					            	<option value="0" <c:if test="${app.picStatus == '0' }">selected="selected"</c:if> >禁用</option>
					            	<option value="1" <c:if test="${app.picStatus == '1' }">selected="selected"</c:if> >启用</option>
					            </select>
					            </div>
				            </div>
				            <div class="form-group">
								<label class="col-sm-3 control-label">URL<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="picUrl" id="picUrl" value="${app.picUrl}" class="form-control"/>									
								</div>
							</div>
						  <%--  <div class="form-group">
								<label class="col-sm-3 control-label">secondURL</label>
								<div class="col-sm-3">
									<input type="text" name="secondUrl" id="secondUrl" value="${app.secondUrl}" class="form-control" />									
								</div>
							</div>			 --%>											
							<div class="form-group">
								<label class="col-sm-3 control-label">排序</label>
								<div class="col-sm-3">
									<input type="number" name="seqNum" id="seqNum"  class="form-control" value="${app.seqNum}" min="0"/>									
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
         
	    if($("#picName").val() == ""){
	    	alert("标题不能为空！");
	    	return false;
	    }
	    if($("#picPosition").val() == ""){
	    	alert("图片不能为空！");
	    	return false;
	    }
	    if($("#picUrl").val() == ""){
	    	alert("URL不能为空！");
	    	return false;
	    }
		
		
		$.ajax({
			type : 'POST',
			url : 'appBanner/edit',						
			data:$('#form').serialize(),
			success:function(data) {
				<c:if test="${type == 'update' }">
				if(data == 'update_success'){
					alert("修改成功");					
					window.location.href="appBanner/list";				 					
				}else{					
					alert("修改失败");
				}
				</c:if>
				<c:if test="${type == 'add' }">
				if(data == 'add_success'){
					alert("添加成功");					
					window.location.href="appBanner/list";				 					
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
            for(var i=0;i<fileArr.length;i++){
            	formdata.append("file", fileArr[i]);
            } 
		$.ajax({
			type : 'POST',
			url : '/appBanner/uploadImages',						
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
					   $("#picPosition").val(arr[i]);
				   }
				}
			},
			error:function(e){
				alert(e);
			}
		});
	}
	function sufile2(){
		 var formdata = new FormData();               
		    var fileObj = document.getElementById("fileToUpload2").files;               
		        for (var i = 0; i < fileObj.length; i++)                   
		        formdata.append("file", fileObj[0]);   
		        $.ajax({
					type : 'POST',
					url : '/appBanner/uploadImages',						
					data:formdata,
					contentType: false,
					 processData: false,
					success:function(data) {
						var arr = $.parseJSON(data);
						$('#logo').val(data);
						$('#preview2').html("");
						for(var i=0;i<arr.length;i++){
							var image_src = '<%=basePath%>'+arr[i];
							image_src = image_src.replace("/upload","upload");
						   $('#preview2').append('<img src=\"'+image_src+'\" class=\"img-thumbnail\" width=\"100\" height=\"100\" alt=\"\" />');
						   if(i == 0){							   
							   $("#shareImgUrl").val(arr[i]);
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