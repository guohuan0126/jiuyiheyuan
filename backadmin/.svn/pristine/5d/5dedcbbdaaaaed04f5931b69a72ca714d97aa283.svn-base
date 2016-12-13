<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/common/activeTag" prefix="base"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/style.default.css" rel="stylesheet"/>
<link rel="stylesheet" href="${ctx}/css/bootstrap-wysihtml5.css" />
<link href="${ctx}/css/jquery.datatables.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/css/jqtreetable.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/css/assets/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/assets/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/assets/prettify.css" />

<link type="text/css" href="${ctx}/css/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/css/jquery-ui-timepicker-addon.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/flow/flow.css" />
<script src="${ctx}/js/jquery-1.11.1.min.js"></script>
<script src="${ctx}/js/dropzone.min.js"></script>
<script src="${ctx}/js/jquery-migrate-1.2.1.min.js"></script>

<script type="text/javascript" src="${ctx}/js/multiselectSrc/jquery.ui.core.js"></script>
<script type="text/javascript" src="${ctx}/js/multiselectSrc/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/js/multiselectSrc/prettify.js"></script>
<script type="text/javascript" src="${ctx}/js/multiselectSrc/jquery.multiselect.js"></script>



<script src="${ctx}/js/jquery-ui-1.10.3.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/modernizr.min.js"></script>
<script src="${ctx}/js/jquery.sparkline.min.js"></script>
<script src="${ctx}/js/toggles.min.js"></script>
<script src="${ctx}/js/retina.min.js"></script>
<script src="${ctx}/js/jquery.cookies.js"></script>

<script src="${ctx}/js/flot/jquery.flot.min.js"></script>
<script src="${ctx}/js/flot/jquery.flot.resize.min.js"></script>
<script src="${ctx}/js/flot/jquery.flot.spline.min.js"></script>
<script src="${ctx}/js/morris.min.js"></script>
<script src="${ctx}/js/raphael-2.1.0.min.js"></script>

<script src="${ctx}/js/custom.js"></script>
<script src="${ctx}/js/jqtreetable.js"></script>
<script src="${ctx}/js/jquery.datatables.min.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.validate.min.js"></script>
<script src="${ctx}/js/dsdialog.js"></script>
<script src="${ctx}/js/jquery-ui-timepicker-addon.js"></script>
<script src="${ctx}/js/jquery-ui-timepicker-zh-CN.js"></script>
<script src="${ctx}/js/cvi_busy_lib.js"></script> 

<script type="text/javascript">

	$(function() {
	var str1='';
	var parList=new Array();
	var list;
	$.ajax({
		type : 'GET',
		url : '/newMenu/getMenuView',
		dataType : 'json',
		success : function(data) {
			list = eval(data);
			loadMenuView();
		},
		error : function() {
					alert("异常！");
		}
	});
	
	function loadMenuView(){
	
 	for(var o in list){  
        var menu=list[o];
		    if(menu.parentId==0){
		    	parList.push(menu);
		    }
      }  	
  
	for (var j=0;j<parList.length;j++)
		{  
		    var m1=parList[j];
		    var count=0;
		    var str2='';
		    var flag=false;
		    for(var k in list){ 
		    	var m2=list[k];
		        if(m1.id==m2.parentId){
		          count++;
		          str2+='<li><a href=\"'+m2.url+'\"><i class=\"fa fa-caret-right\"></i> <span>'+ m2.menuName+'</span></a></li>';		         
		        }
		    }
		    if(count==0){
		    	str1+='<li><a href=\"'+m1.url+'\"><i class=\"fa fa-map-marker\"></i> <span>'+m1.menuName+'</span></a></li>';		    		      
		    }else{
		       if(flag==true){
		       str1+='<li class=\"nav-parent\"><a href=\"\"><i class=\"fa fa-edit\"></i> <span>'+m1.menuName+'</span></a>';
		       str1+='<ul class=\"children\">';
		       }else{
		       str1+='<li class=\"nav-parent\"><a href=\"\"><i class=\"fa fa-edit\"></i> <span>'+m1.menuName+'</span></a>';
		       str1+='<ul class=\"children\">';
		       }
		       str1+=str2;
		       str1+='</ul></li>';
		    }
		}		
		$("#menu").append(str1);
		/* 菜单cookie */
		var current = $.cookie("menu_current");	
		if (current != undefined && current != null && current != "") {	
			$("div.leftpanelinner").find("li").each(function() {	
				var parentul = $(this);	
				if($(this).find("ul")){
					var parent = $(this).find("ul");
					var li = $(parent).find("li");
					$(li).each(function(){																
						if ($(this).find("a").text() == current) {						
							$(parentul).addClass('active');
							$(parent).attr("style", "display:block;");
							$(this).addClass('active');
							return;							
						}
					});				
				}	
			});
		}else{
			 $("#index").addClass('active');
		}
		$("div.leftpanelinner").delegate("a","click",function(){			
			var menu = $(this);								
			$.cookie("menu_current", $(menu).text(),  {path: '${ctx}/'});			
		});			
	}		
});

</script>

<div class="leftpanel">

    <div class="logopanel">
        <h1><span>[</span> 短融网 <span>]</span></h1>
    </div><!-- logopanel -->

    <div class="leftpanelinner">
      <ul class="nav nav-pills nav-stacked nav-bracket" id="menu">
        <li  id="index"><a href="/index"> <i class="fa fa-home"></i><span>首页</span></a></li>          
      </ul>
    </div><!-- leftpanelinner -->    
  </div><!-- leftpanel -->