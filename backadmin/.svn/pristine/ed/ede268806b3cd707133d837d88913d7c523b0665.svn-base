<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/common/activeTag" prefix="base"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/style.default.css" rel="stylesheet"/>
<link rel="stylesheet" href="${ctx}/css/bootstrap-wysihtml5.css" />
<link href="${ctx}/css/jquery.datatables.css" rel="stylesheet">
<link type="text/css" href="${ctx}/css/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
<script src="${ctx}/js/jquery-1.11.1.min.js"></script>
<script src="${ctx}/js/dropzone.min.js"></script>
<script src="${ctx}/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${ctx}/js/jquery-ui-1.10.3.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/modernizr.min.js"></script>
<script src="${ctx}/js/jquery.sparkline.min.js"></script>
<script src="${ctx}/js/toggles.min.js"></script>
<script src="${ctx}/js/retina.min.js"></script>
<script src="${ctx}/js/jquery.cookies.js"></script>



<script src="${ctx}/js/custom.js"></script>

<script src="${ctx}/js/select2.min.js"></script>






<script src="${ctx}/js/custom.js"></script>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="images/favicon.png" type="image/png">

  <title>用户管理</title>
  

</head>

<body>



<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>


<section>

<%-- <%@include file="../base/leftMenu.jsp"%>
 --%>  
  <div class="mainpanel">

    <div class="contentpanel">
        <form id="basicForm"  class="form-horizontal ">
          <div class="panel panel-default">
             
              <div class="panel-body">
              <div class="form-group">
               	&nbsp;&nbsp;&nbsp;&nbsp;用户名称：<span id="realname"></span>
                </div>
               <div class="form-group">
                   <input type="hidden" name="id" id="userid" >
                   <div class="col-sm-2">
                   <label class="col-sm-1 control-label">来源:</label>
	                <!-- <select class="select2" name="source"  id="btc" data-placeholder="来源...">
	                 -->
	                 <input name="source"id="btc"></input>
	              </div>
                </div>
                <div class="form-group">
                <div class="col-sm-2">
                   <input type="radio" name="p2p" id="radioDefault" value="1" checked="checked" />
                   <label for="radioDefault">投资过p2p</label>
                   <input type="radio" name="p2p" value="0" id="radioPrimary" />
                   <label for="radioPrimary">未投资过p2p</label>
                 </div>
                 </div> 
                <div class="form-group">
                <div class="col-sm-2">
                   <input type="radio" name="notice" id="radioDefault" value="1" checked="checked" />
                   <label for="radioDefault">有新标通知</label>
                   <input type="radio" name="notice" value="0" id="radioPrimary" />
                   <label for="radioPrimary">有新标不通知</label>
                 </div>
                 </div>         
                <div class="form-group">
                <label class="col-sm-1 control-label">QQ: </label>
                <input type="text" name="qq" id="q"  placeholder="QQ..."   />
                </div>
                <div class="form-group">
                <label class="col-sm-1 control-label">微信: </label>
                <input type="text" name="weixin" id="weixin"  placeholder="微信"   />
                </div>
                <div class="form-group">
                  <label class="col-sm-1 control-label">备注 </label>
                    <textarea rows="2" cols="30" name="remark"  placeholder="备注..." ></textarea>
                    <label><input name="visitType" type="checkbox" value="激活回访" />激活回访</label>
                </div>
              </div><!-- panel-body -->
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3">
                    <button class="btn btn-primary" id="save" type="button">保存</button>
                  </div>
                </div>
                <br/>
                <table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
<!-- 									<th>用户编号</th>
 -->								<th>备注</th>
									<th>时间</th>
									<th>回访类型</th>
									<th>备注人</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="ttab">
								<!-- <tr>
									<td>用户编号</td>
									<td>备注</td>
									<td>时间</td>
									<td>备注人</td>
								</tr> -->
							</tbody>
						</table>
              </div>
            
          </div><!-- panel -->
      </form> 
    </div><!-- contentpanel -->
    
  </div><!-- mainpanel -->
 
</section>


<script>
var id=$("#userpid", window.parent.document).val();
$("#userid").val(id);
//var qq=$("#qq", window.parent.document).val();
function func(){
	var str='';
	$.ajax({
		type : "POST",
		url : '/user/rmarkList',
		data:{userid:id},
		async : false,
		dataType : 'json',
		error : function(e) {
			alert("异常");
		},
		success : function(data) {
			list = eval(data);
			var hasP2p=list[0].hasP2p;
			var notice=list[0].notice;
			var visitSource=list[0].visitSource;
			var q=list[2].qq;
			var weixin=list[2].weixin;
			var realname=list[2].realname;
			if(visitSource!=''){
				if(visitSource=='1'){
				$("#btc").val("来自网络");
				}else if(visitSource=='0'){
					$("#btc").val("来自朋友");
				}else{
				$("#btc").val(visitSource);
				}
				
			}
			if(hasP2p!=''){
				$("input[name=p2p][value="+hasP2p+"]").attr("checked",true);
			}
			if(notice!=''){
				$("input[name=notice][value="+notice+"]").attr("checked",true);
			}
			if(q!=null && q!=''){
				$("#q").val(q);
			}
			if(weixin!=null && weixin!=''){
				$("#weixin").val(weixin);
			}
			if(realname!=null && realname!=''){
				$("#realname").html(realname);
			}else{
				$("#realname").html('无');
			}
			 $.each(list[1], function (n, user) { 
				 str += '<tr>';
/* 					str += '<td>' + user.userid + '</td>';
 */					str += '<td width="40%">' + user.remark + '</td>';
					str += '<td>'+user.time.substr(0, 19)+'</td>';
					str += '<td>'+user.visitType+'</td>';
					str += '<td>' + user.realname + '</td>';
					str += '<td><a href="javascript:del('+user.id+')">删除</a></td>';
					str += '</tr>';
	          });
			$("#ttab").html(str);
		}
	});
}
jQuery(document).ready(function(){
	
	func();
});
$("button[id=save]").click(function(){
	$.ajax({
		type : "POST",
		url : '/user/tormark',
		data:$('#basicForm').serialize(),
		error : function(e) {
			alert("异常");
		},
		success : function(data) {
			if(data == 'ok'){
			alert("保存成功!");
			func();
		 }else{
			 alert("保存失败!");
		 }
		}
	});
});
function del(id) {
	$.ajax({
		type : "POST",
		url : '/user/delrmark',
		data:{id:id},
		error : function(e) {
			alert("异常");
		},
		success : function(data) {
			if(data == 'ok'){
			alert("删除成功!");
			func();
		 }else{
			 alert("删除失败!");
		 }
		}
	});
}
</script>

</body>
</html>
    