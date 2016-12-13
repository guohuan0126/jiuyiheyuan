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
  <title>资源管理</title>
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
      <h2><i class="fa fa-pencil"></i> 菜单管理</h2>     
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" action="${addUrl}" method="post" class="form-horizontal ">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">
                 
                </div>
                <h4 class="panel-title">
     				<c:choose>
     					<c:when test="${addUrl == '/menu/editmenu' }">
     						编辑菜单
     					</c:when>
     					<c:otherwise>
     						添加菜单
     					</c:otherwise>
     				</c:choose>                  
           </h4>
              </div>
              <div class="panel-body">
                <div class="form-group">
                  <input type="hidden" name="pid" value="${menu.id}">
                   <label class="col-sm-1 control-label">名称<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="menuName" value="${menu.menuName}" placeholder="名称..."  class="form-control" />
                  </div>
                  <label class="col-sm-1 control-label">菜单类型</label>
                   <div class="col-sm-2">
	                <select class="select2" name="bct" id="bct" data-placeholder="菜单类型...">
	                  <option value="menu">菜单</option>
	                  <option value="active">动作</option>
	                </select>
	              </div>
                </div>
                
               
            
			<div class="form-group">
                   <label class="col-sm-1 control-label">父菜单</label>
                  <div class="col-sm-2">
                    <select class="select2" id="fmenu" name="pno" data-placeholder="选择父菜单...">
                        <option value="0">无</option>
                      <c:forEach items="${mList}" var="item">
                       <c:if test="${item.parentId!=0}">
                       <option value="${item.id }">&nbsp;&nbsp;${item.menuName}</option>
                       </c:if>
                       <c:if test="${item.parentId==0}">
                       <option value="${item.id }">${item.menuName}</option>
                       </c:if>
	                  </c:forEach>
                    </select>
                  </div>
	              
                  <label class="col-sm-1 control-label">序号<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                   <c:choose>
     					<c:when test="${addUrl == '/menu/editmenu' }">
     						<input type="text" name="od" value="${menu.order}" placeholder="序号..."  class="form-control" />
     					</c:when>
     					<c:otherwise>
     						<input type="text" name="od"  placeholder="序号..."  class="form-control" />
     					</c:otherwise>
     				</c:choose>  
                    
                  </div>
            </div>
            
             <div class="form-group">
                
             	  <label class="col-sm-1 control-label">是否可用<span class="asterisk">*</span></label>
	              <div class="col-sm-2">
	                <select class="select2" name="bsta" data-placeholder="是否可用...">
	                  <option value="1">可用</option>
	                  <option value="0">不可用</option>
	                </select>
	              </div>
	               <label class="col-sm-1 control-label">连接地址<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="cl" value="${menu.url}"  placeholder="连接地址 ..."  class="form-control"/>
                  </div>
                   <div class="col-sm-4">
                     <span class="asterisk" >例如：   /term/zhuanjiaguwen</span>
             	   </div>
                </div>
                <div class="form-group">
                <label class="col-sm-1 control-label"> </label>
                  <input type="button" id="all"  value="全选" class="btn btn-default"/>
                  <input type="button"  id="del" value="反选" class="btn btn-default"/>
                 </div>
                <%-- <label class="col-sm-1 control-label" for="checkbox">权限</label>
                 <div class="form-group" style="margin-left:140px;">
                  <input type="hidden" name="ids" id="ids">				  
				  	<c:forEach items="${pageData.results}" var="item" varStatus="status">				  						  					  		
				  		<div class="checkbox block col-sm-2" style="width:25%"><label><input name="permis" value="${item.id }" type="checkbox"> ${item.name }</label></div>				  								  						  			  	
				  	</c:forEach>	
				  				  
				</div> --%>
				<label class="col-sm-1 control-label" for="checkbox"><h4>菜单权限：</h4></label>
                 <div class="form-group" style="margin-left:140px;">
                  <input type="hidden" name="ids" id="ids">				  
				  	<c:forEach items="${meList}" var="item" varStatus="status">				  						  					  		
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
                    <button class="btn btn-primary">保存</button>
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
 if('${addUrl}'=='/menu/editmenu'){
	$("#bno").attr("disabled", "disabled");
	$("#fmenu").val(${menu.parentId});
	$("#bct").val('${menu.type}');
}
if('${addUrl}'=='/menu/addcmenu'){
  $("#fmenu").val(${mid});
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
 
  
  // Basic Form
  jQuery("#basicForm").validate({
   rules: { 
          
            menuName: { 
                required: true 
            },
            od:{
               required: true,
               number:true
            }
        }, 
        //设置提示信息 
        messages:{ 
            
            menuName:{ 
                required:"请输入名称"
            } , 
            od:{ 
                required:"请输入序号",
                number:"请输入正确的数字"
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
    