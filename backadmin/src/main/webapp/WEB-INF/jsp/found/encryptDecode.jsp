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

  <title>加密解密处理</title>
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
      <h2><i class="fa fa-table"></i>当前位置：加密解密处理 </h2>      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
    	<form class="form-inline" id="form1"  method="post">		    	 
	     <div class="contentpanel">
	      <div class="input-group">
	     	<input type="text" id="content" name="content"  class="form-control" style="width:362px;display:inline-block;" placeholder="请输入密文或明文"></input>
            <input type="button" id="encrypt" class="btn btn-primary"  style="display:inline-block;" value="加密"></input>            
	        <input type="button" id="decode" class="btn btn-primary"  style="display:inline-block;margin-left:8px;" value="解密"></input>           
	     </div>
	      <div id="contentShow" class="input-group" style="margin-left:20px;display:none;" >
	      <!-- <span  style="font-size: 15px;display:inline-block;" >加密/解密后：</span> -->
	        <input type="text" id="decodeContent" name="decodeContent"  class="form-control" style="width:462px;" ></input>
           </div>
	     </div>
	       </form>
            </div>
        </div>
    </div>
    
</section>

<script type="text/javascript">
$("#encrypt,#decode").click(function(){
	var content=$("#content").val();
	var type=$(this).val();
	if(content==''){
		alert("请输入内容");
		return false;
	}
	$.ajax({
		type : 'POST',
		dataType:'text',
		url : "/found/encryptOrDecode",						
		data: {"content":content,"type":type},
		success:function(data) {
			if(data=='fail'){
				alert(type+"失败");
			}else{		
				$("#contentShow").show();
				$("#decodeContent").val(data);
			}
		}
	});	 
})

</script>	
</body>
</html>
