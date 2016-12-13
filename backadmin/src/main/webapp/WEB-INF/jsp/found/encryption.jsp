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

  <title>加密管理</title>
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
      <h2><i class="fa fa-table"></i>当前位置：加密管理 </h2>      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
    	      	<form id="form" name="form" accept-charset="utf-8"   method= "post"   enctype="multipart/form-data"  >
    	      	           <div class="form-group">
                             <label for="inputPassword1" class="col-lg-1 col-sm-2 control-label"></label>
                               <div class="col-md-1 form-group">
                               <input id="filetest" name="filetest" type="file"  />
                               </div>
                               <label for="inputPassword1" class="col-lg-1 col-sm-2 control-label"></label>
                               <div class="col-md-1 form-group">
                                 <button type="button" id="search" onclick="sufile()" class="btn btn-primary">导入</button>
                               <span> <c:if test="${content !=null}">已导入</c:if> </span>
                               </div>
                            <div class="form-group">
                				<label class="sr-only" for="st">类型</label>
                               <label for="inputPassword1" class="col-lg-1 col-sm-1 control-label">导入类型</label>
                               <div class="col-md-1 form-group">
                                  <select class="form-control input-md "style="width: 135px;margin-left: -56px" name="type" id="type" data-placeholder="状态...">
				                   <option value="">请选择加密数据</option>
	                               <option value="1">用户编号</option>
	                               <option value="2">手机号发送</option>
				                </select>
                               </div>
                               <label for="inputPassword1" class="col-lg-1 col-sm-2 control-label"></label>
                               <div class="col-md-6 form-group">
                                 <button class="btn btn-primary" onclick="sub()" type="button">保存</button>
								<input name="listData" type="hidden" id="listData" value="${content}">                                
                                <button class="btn btn-primary" onclick="exportData()" type="button">导出</button>
                               </div>
                            </div>
                           
                <table class="table table-primary table-striped table-hover ">
                    <thead style="display: block;width: 100%;">
                      <tr>
                        <th style="width:1000px;">未加密数据</th>
                        <th style="width:1000px;">加密数据</th>
                    </thead>
                    <tbody style="display:block;height:600px;overflow-y:scroll;width:100%;">
                    <c:forEach items="${list}" var="item">
                     <tr>
                     <td style="width:1000px;">${item.unEncryption}</td>
					 <td style="width:1000px;">${item.encryption}</td>
					  </tr>
					</c:forEach>
                    </tbody>
                </table>
                              </div>
                            </div>
               <input type="hidden" name=content value="${content}" />
                </form>
            </div>
        </div>
    </div>
    
</section>









<script type="text/javascript">
$(document).ready(function(){
	$(".zhezhao").hide();
});

$("#type").val('${type}');

jQuery(document).ready(function(){
	$("#usertype").val('${usertype}');
});

function sufile(){
	if($("#filetest").val() == ""||$("#filetest").val() == null){
    	alert("导入文件不能为空！");
    	return false;
    }
	debugger;
	$("form").attr("action","/found/uploadinfo").submit();
}

function exportData(){
	var listData=$("#listData").val();
	var type=$("#type").val();
	var url="/found/exportEncrypation?listData="+listData+"&type="+type;
	window.location.href=url;

}

function sub(){
	if (confirm("您确定要保存吗？")) {
		if($("#type").val() == ""||$("#type").val() == null){
	    	alert("导入类型不能为空！");
	    	return false;
	    }
		$("form").attr("action","/found/subData").submit();
	}
	
}
 
</script>
<!--页面弹出loading-->
<div class="zhezhao">
<img src="/images/loading.gif"  style="position:absolute;left:50%;top:50%;">
</div>	
</body>
</html>
