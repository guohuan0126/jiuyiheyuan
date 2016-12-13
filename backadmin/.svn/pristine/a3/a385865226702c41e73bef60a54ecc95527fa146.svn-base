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
  <title>IP前台访问黑名单</title>
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
      <h2><i class="fa fa-table"></i>当前位置：黑名单管理 </h2>      
    </div> 
    <div class="contentpanel">   
    	<form id="form" method="post" class="form-horizontal">
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    <div>列表</div>
	    
	    <a href="${cxf}/permis/black/toAdd" class="add">添加IP访问黑名单</a>
	    <a href="${cxf}/permis/black/tomobile" class="add">添加手机号访问黑名单|</a>
	    </div>
		<div class="panel-heading">
		 <form class="form-inline" id="form2" action=""  method="post">
		 <div class="col-sm-1">
				<div class="form-group">
	              <label class="sr-only" for="exampleInputPassword2">查询条件</label>
	              <input type="text" name="phone" id="phone" value="${phone }" class="form-control" id="exampleInputPassword2" placeholder="查询条件">
	            </div>
	    </div>	
	             <button  id="mbutton" class="btn btn-primary">手机号查询</button>
	             <button  id="ipbutton" class="btn btn-primary">IP查询</button>
	   </form>
	    </div>
		<div class="panel-body">   	
			<table class="table table-primary table-striped table-hover ">
            	<thead>
                   <tr>
                      <th width="65%">黑名单</th>                       
                      <th width="20%">操作</th>
                   </tr>
                </thead>
                <tbody>
                  <c:forEach items="${pageInfo.results}" var="ip" varStatus="status">
                    <tr>
                    <input type="hidden" name="id" id="hid" value="${ip.id}"/>
                    <input type="hidden" name="type" id="type" value="${ip.type}"/>
                    <input type="hidden" name="content" id="content" value="${ip.content}"/>
                      <td>${ip.content}</td>
                      <td> 
                       	<a style="cursor:pointer" onclick="del('${ip.content}')">刪除</a>
                      </td>
                    </tr>
                    </c:forEach>
               		</tbody>
        </table>
		<%@ include file="../../base/page.jsp"%>			    		
 		</div><!-- panel-body -->
 </div><!-- panel -->
 </form> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>
<script type="text/javascript">

	 
	  function del(id){
	  	//	$('#'+id).remove();
	        var type=$("#type").val();
	        if(type=="IP"){
	        sub('${cxf}/permis/black/del', "del",id,"IP");
	        }
	        if(type=="MOBILE_NO"){
	        sub('${cxf}/permis/black/mdel', "mdel",id,"MOBILE_NO");
	        }
	  		
	  }
	  	  
	  function isIP(strIP) {
			
			var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;  //匹配IP地址的正则表达式	
			if(re.test(strIP)){			
				if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true;
			
			}
			return false;
		}	 
	  function sub(url,type,id,deltype){
	
	  	$.ajax({
				type : 'POST',
				url : url,
				data:{"id":id,"deltype":deltype},
				dataType:"json",
				success : function(msg) {
					if(msg.status == 'ok'){
						alert(msg.info);
						if(type=="del"){
						window.location = "${cxf}/permis/black/list";
						}else{
						window.location = "${cxf}/permis/black/mlist";
						}			
						
					}{
					  alert(msg.info);
					}
				}
			});
	  	
	  } 
	//添加按钮
	$("button[id=mbutton]").click(function(){
		var phone=$("#phone").val();
		if(phone!=''){
			window.location.href="/permis/black/mlist?phone="+$("#phone").val();
		}else{
			window.location.href="/permis/black/mlist";
		}
		
	});
	
	$("button[id=ipbutton]").click(function(){
		var phone=$("#phone").val();
		if(phone!=''){
		window.location.href="/permis/black/list?phone="+$("#phone").val();
		}else{
			window.location.href="/permis/black/list";
		}
	});
</script>
</body>
</html>
