<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="images/favicon.png" type="image/png">
  <title>权限管理</title>  
</head>
<body>
<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section>
<%@include file="../base/leftMenu.jsp"%>  
  <div class="mainpanel">
<%@include file="../base/header.jsp"%>
    <div class="pageheader">
      <h2><i class="fa fa-pencil"></i> app版本 </h2>
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">               
                </div>
                <h4 class="panel-title">app版本操作</h4>
              </div>
              <div class="panel-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">版本名称 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <input type="hidden" name="id" value="${app.id }">
                    <input type="text"  name="versionName" id="versionName" value="${app.versionName}" width="10px" class="form-control" placeholder="版本名称..."/>
                  </div>
<!--                   <div id="errorId" style="display: none;">编号不能为空</div>
 -->                </div>
                
                <div class="form-group">
                  <label class="col-sm-3 control-label">版本号</label>
                  <div class="col-sm-3">
                    <input type="text" name="versionCode" value="${app.versionCode}" placeholder="版本号"  class="form-control"/>
                  </div>                
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">下载地址</label>
                  <div class="col-sm-3">
                    <input type="text" name="downloadAddress" value="${app.downloadAddress}" placeholder="下载地址"  class="form-control"/>
                  </div>                
                </div>
                
                <div class="form-group">
                	<label class="col-sm-3 control-label">来源</label>
                	<div class='col-sm-9'>                         
                	<select id='st' name='osName'>       
                			<option value="android">android </option>
                			<option value="ios">ios</option>	
					</select>
					</div> 					                                      
                </div>
              </div><!-- panel-body -->
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3">
                    <input type="button" class="btn btn-primary" onclick="sub();" value="保存"/>
                    <button type="reset" class="btn btn-default">重置</button>
                  </div>
                </div>
              </div>
            
          </div><!-- panel -->
      </form> 
    </div><!-- contentpanel -->
    
  </div><!-- mainpanel -->
 
</section>

<script>
	
	jQuery(document).ready(function(){		
	  $("#st").val('${app.osName}');		
	});
	
	
	
	function sub(){
		$.ajax({
	        type: "POST",
	        url:'/app/saveVesrion',
	        data:$('#basicForm').serialize(),// 你的formid              
	        error: function( e ) {
	        	alert("异常");
	        },
	        success: function(data) {
	        	if(data == 'ok'){
	                 alert('操作成功');
	                 location.replace(document.referrer);
	             }else{
	            	 alert('操作失败');         
	             }                 
	            }
	        });					
	}
</script>

</body>
</html>
    