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
  <title>菜单管理</title>   
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
      <h2><i class="fa fa-table"></i>当前位置：菜单管理 </h2>
      <div class="breadcrumb-wrapper">
        <span class="label"></span>
        
      </div>
    </div>   
    <div class="contentpanel">   
	    <div class="panel panel-default">
	    <div class="panel-heading">  	   
	            <button type="button" id="add" class="btn btn-primary">添加菜单</button>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover">
                    <thead>
                      <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th>连接地址</th>
                        <th>菜单类型</th>
                        <th>序号</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody id="treet1">
		                 <c:forEach items="${menuList}" var="item">
		                      <tr>
		                        <td>${item.id }</td>
		                        <td>${item.menuName }</td>
		                        <td>${item.url }</td>
		                        <td>${item.type }</td>
		                        <td>${item.order}</td>
		                        <td>
		                        <a href="/newMenu/toedit?id=${item.id}">修改</a>| <a style="cursor:pointer" onclick="del('/newMenu/todel?id=${item.id}');">删除</a>
		                        <c:if test="${item.type!='active'}">
		                        |<a href="/newMenu/tocadd?id=${item.id}&type=${item.type}">添加子菜单</a>
		                        </c:if>
		                        </td>
		                      </tr>
		                  </c:forEach>
                    </tbody>
        </table>
      

 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>



<script>
  jQuery(document).ready(function() {

     var map = ${arrays};
            //声明参数选项
            var options = {openImg: "${ctx}/images/tv-collapsable.gif", shutImg: "${ctx}/images/tv-expandable.gif", leafImg: "${ctx}/images/tv-item.gif", lastOpenImg: "${ctx}/images/tv-collapsable-last.gif", lastShutImg: "${ctx}/images/tv-expandable-last.gif", lastLeafImg: "${ctx}/images/tv-item-last.gif", vertLineImg: "${ctx}/images/vertline.gif", blankImg: "${ctx}/images/blank.gif", collapse: false, column: 0, striped: true, highlight: true,  state:false};
             if(map!=null&&map.length>0)
            {
              //根据参数显示树
              // 注意treet1这个名字是 下文中 tbody 的名字
              $("#treet1").jqTreeTable(map, options);
              
              
            }
   
  });
  //添加按钮
	$("button[id=add]").click(function(){
					window.location.href="/newMenu/toadd";
	});
	
	
	function del(url){
	  alert(url);
		$.ajax({			
			type : 'POST',			
			url : url, 			
			success : function(msg) {
					if(msg == 'ok'){
						alert("删除成功!");
						window.location.href="/newMenu/menuList";
					}else{
						alert("删除失败!");					
					}
				},
				error : function() {
					alert("异常！");
				}
		});	
	}
</script>

</body>
</html>
