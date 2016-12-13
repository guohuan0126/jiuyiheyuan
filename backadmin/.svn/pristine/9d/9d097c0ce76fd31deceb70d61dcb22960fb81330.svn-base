<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
  <title>IP访问白名单</title>
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
      <h2><i class="fa fa-table"></i>当前位置：IP管理 </h2>      
    </div> 
    <div class="contentpanel">   
    	<form id="form" method="post" class="form-horizontal">
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    <div>IP列表</div>
	    
	    <a href="${cxf}/permis/ipPermis/toAdd" class="add">添加IP访问白名单</a>
	    </div>
		
		<div class="panel-body">   	
			<table class="table table-primary table-striped table-hover ">
            	<thead>
                   <tr>
                      <th width="15%">序号</th>
                      <th width="65%">ip</th>                       
                      <th width="20%">操作</th>
                   </tr>
                </thead>
                <tbody>
                  <c:forEach items="${ips}" var="ip" varStatus="status">
                    <tr>
                      <td>${status.index}</td>
                      <td><input name="ip" id="ip${status.index}" value="${ip}" style="border:0" readOnly/></td>
                      <td> 
                       	<a style="cursor:pointer" onclick="edit('${ip}', 'ip${status.index}');">修改</a> | 
                       	<a style="cursor:pointer" onclick="del('ip${status.index}')">刪除</a>
                      </td>
                    </tr>
                    </c:forEach>
               		</tbody>
        </table>
					    		
 		</div><!-- panel-body -->
 </div><!-- panel -->
 </form> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>
<script type="text/javascript">

	function edit(ip, id){
        		ds.dialog({
   				   title : "编辑",
   				   content : '<div class="form-group"><label class="col-sm-3 control-label">IP：</label><br>'+
   				   			 '<input name="ip" id="id" value='+ip+' /></div>',
   				   width:250,
   				   yesText : '提交',
   				   onyes:function(){  		
   				   		var ip = $("#id").val();
   				   		if(ip == ''){
   				   			alert("ip不能为空！");
   				   			return false;
   				   		}	
   				   		if(!isIP(ip)){
   				   			alert("ip格式不正确！");
   				   			return false;
   				   		} 		   		
   				   		$('#'+id).val(ip);	 				   		  		
   				   		sub('${cxf}/permis/ipPermis/edit', "edi"); 				    	 
   				   },
   				   noText : '关闭',
   				   onno : function(){}
   				});
       }
	
	  function del(id){
	  		$('#'+id).remove();
	  		sub('${cxf}/permis/ipPermis/del', "del");
	  }
	  	  
	  function isIP(strIP) {
			
			var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;  //匹配IP地址的正则表达式	
			if(re.test(strIP)){			
				if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true;
			
			}
			return false;
		}	 
	  function sub(url,type){
	  	$.ajax({
				type : 'POST',
				url : url,
				data:$('#form').serialize(),				
				success : function(msg) {
					if(msg == 'ok'){
						if(type == "edi"){
							alert("ip修改成功!");
						}else{
							alert("ip删除成功!");
						}						
						window.location = "${cxf}/permis/ipPermis/list";
					}else{
						if(type == "edi"){
							alert("ip修改失敗!");
						}else{
							alert("ip删除失败!");
						}	
						
					}
				}
			});
	  	
	  }
</script>
</body>
</html>
