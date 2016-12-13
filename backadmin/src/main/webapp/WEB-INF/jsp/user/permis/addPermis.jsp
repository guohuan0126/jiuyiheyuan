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
<%@include file="../../base/leftMenu.jsp"%>  
  <div class="mainpanel">
<%@include file="../../base/header.jsp"%>
    <div class="pageheader">
      <h2><i class="fa fa-pencil"></i> 权限 </h2>
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">               
                </div>
                <h4 class="panel-title">权限操作</h4>
              </div>
              <div class="panel-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">编号 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text"  name="id" id="id" value="${permission.id}" width="10px" class="form-control" placeholder="编号..."/>
                  </div>
                  <div id="errorId" style="display: none;">编号不能为空</div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-3 control-label">名称</label>
                  <div class="col-sm-3">
                    <input type="text" name="name" value="${permission.name}" placeholder="名称"  class="form-control"/>
                  </div>                
                </div>
                
                <div class="form-group">
                	<label class="col-sm-3 control-label">权限类型</label>
                	<div class='col-sm-9'>                         
                	<select id='permissType' name='permissType'>       
                	<c:choose>   	
                		<c:when test="${addUrl =='/permis/save/editPermis'}">
                			<c:choose>              			
                				<c:when test="${permission.type == 'active' }">
                					<option value="active">动作</option>
	                				<option value="menu">菜单 </option>
                				</c:when>
                				<c:otherwise>
                					<option value="menu">菜单</option>
	                				<option value="active">动作</option>
                				</c:otherwise>
                			</c:choose>	                		                		          				
                		</c:when>
                		<c:otherwise>
                			<option value="">请选择...</option>
                			<option value="menu">菜单 </option>
                			<option value="active">动作</option>	
                		</c:otherwise>
                	</c:choose>                    														
					</select>
					</div> 					                                      
                </div>
                
                <div class="form-group">
                  <label class="col-sm-3 control-label">描述 </label>
                  <div class="col-sm-3">
                    <textarea rows="5" name="description"  class="form-control" placeholder="描述..." >${permission.description}</textarea>
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
		if('${addUrl}'=='/permis/save/editPermis'){			
			 $("#id").attr("readonly","readonly");		
		}		
		
	});
	
	
	
	function sub(){
		$('#errorId').attr("stype", "display:none;");
	
		if($("#id").val() == ''){
			$('#errorId').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}
			
		$.ajax({
	        type: "POST",
	        url:'${addUrl}',
	        data:$('#basicForm').serialize(),// 你的formid              
	        error: function( e ) {
	        	alert("异常");
	        },
	        success: function(data) {
	        	if(data == 'ok'){
	                 alert('操作成功');
	                 location.replace(document.referrer);
	             }else{
	             	if('${addUrl}' == '/permis/save/editPermis'){
	             		 alert('编号不存在');
	             	}else{
	             		alert('编号重复');
	             	}            
	             }                 
	            }
	        });					
				
	}
</script>

</body>
</html>
    