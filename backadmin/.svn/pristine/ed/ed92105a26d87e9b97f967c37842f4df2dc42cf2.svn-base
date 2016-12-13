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

  <title>角色管理</title>
</head>

<body>
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>


<section>

<%@include file="../../base/leftMenu.jsp"%>
  
  <div class="mainpanel">
<%@include file="../../base/header.jsp"%>
    <div class="pageheader">
      <h2><i class="fa fa-pencil"></i> 角色管理 </h2>      
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" action="" method="post" class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">
                 
                </div>
                <h4 class="panel-title">
     				<c:choose>
     					<c:when test="${addUrl == '/role/save/editRole' }">
     						编辑角色
     					</c:when>
     					<c:otherwise>
     						添加角色
     					</c:otherwise>
     				</c:choose>           
                </h4>
              </div>
              <div class="panel-body">
                <div class="form-group">
                  <label class="col-sm-1 control-label">编号 <span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="hidden" name="pid" value="${role.id}">
                    <input type="text"  name="id" id="id" value="${role.id}" width="10px" class="form-control" placeholder="编号..." required />
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-1 control-label">名称<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="name" value="${role.name}" placeholder="名称..."  class="form-control" required/>
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-1 control-label">描述 </label>
                  <div class="col-sm-5">
                    <textarea rows="2" name="description"  class="form-control" placeholder="描述..." >${role.description}</textarea>
                  </div>
                </div>
                <div class="form-group">
                <label class="col-sm-1 control-label"> </label>
                  <input type="button" id="all"  value="全选" class="btn btn-default"/>
                  <input type="button"  id="del" value="反选" class="btn btn-default"/>
                 </div>
               <label class="col-sm-1 control-label" for="checkbox"><h4>菜单权限：</h4></label>
                 <div class="form-group" style="margin-left:140px;">
                  <input type="hidden" name="ids" id="ids">				  
				  	<c:forEach items="${mList}" var="item" varStatus="status">				  						  					  		
				  		<div class="checkbox block col-sm-2" style="width:25%"><label><input name="permis" value="${item.id }" type="checkbox"> ${item.name }</label></div>				  								  						  			  	
				  	</c:forEach>
				</div>
				<label class="col-sm-1 control-label" for="checkbox"><h4>动作权限：</h4></label>
				<div class="form-group" style="margin-left:140px;">
				<div style="border:1px solid #CCC"></div>
				  	<c:forEach items="${aList}" var="item" varStatus="status">				  						  					  		
				  		<div class="checkbox block col-sm-2" style="width:25%"><label><input name="permis" value="${item.id }" type="checkbox"> ${item.name }</label></div>				  								  						  			  	
				  	</c:forEach>
				</div>
              </div><!-- panel-body -->
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3">
                    <button   class="btn btn-primary">保存</button>
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

if('${addUrl}'=='/role/save/editRole'){
	$("#id").attr("disabled", "disabled");
}
var pids='${pids}';
 if(pids!='')  {
	  var str=pids.split(",");
	  //以逗号将字符串转化为数组 
	  for(var j=0;j<str.length;j++){
		  $(":checkbox[value='"+str[j]+"']").attr("checked",true);  
	}
}  
 
$(this).attr("disabled", "disabled");
    
    "use strict";
    
    // Select2
  jQuery(".select2").select2({
    width: '100%',
    minimumResultsForSearch: -1
  });
  
  // Basic Form
  jQuery("#basicForm").validate({
  rules: { 
            id: { 
                required: true
                
            }, 
            name: { 
                required: true 
            }
        }, 
        //设置提示信息 
        messages:{ 
            id:{ 
                required: "请输入编号"   
            }, 
            name:{ 
                required:"请输入名称"
            }
        },
    highlight: function(element) {
      jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
    },
    success: function(element) {
      jQuery(element).closest('.form-group').removeClass('has-error');
    },
    submitHandler: function(form) {
               		 var result = new Array();
               		var ids="";
                	$("[name = permis]:checkbox").each(function () {
	                    if ($(this).is(":checked")) {
	                        result.push($(this).attr("value"));
	                    }
                	});
 					ids=result.join(",");
                	$("#ids").val(ids);
                    //form.submit();   
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
	             }else if(data == 'fail'){
	             	alert('编号重复');
	             }else{
	                 alert('操作失败');
	             }                 
	            }
	        });	
         }
  });
  
});

//全部选择
 $("#all").click(function(){
  $("input[name='permis']").each(function(){
   $(this).attr("checked",true);
  });  
 });
 //全部选择
 $("#del").click(function(){
  $("input[name='permis']").each(function(){
   $(this).attr("checked",false);
  });  
 });
</script>

</body>
</html>
    