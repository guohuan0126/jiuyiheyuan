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
  <title>编辑页面</title>  
</head>

<body>



<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>


<section>

<%-- <%@include file="../base/leftMenu.jsp"%>
 --%>  
  <%@include file="../base/leftMenu.jsp"%>  
  <div class="mainpanel">
<%@include file="../base/header.jsp"%>

    <div class="contentpanel">
        <form id="basicForm"  class="form-horizontal ">
          <div class="panel panel-default">
              <div class="panel-body">
                <div class="form-group">
                <label class="col-sm-2 control-label">项目名称: </label>
                   <input type="hidden" name="id" id="${id}" value="${id}" >
                <input type="text" name="loanName" id="loanName"  placeholder="项目名称..."   />
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
									<th>项目编号</th>
									<th>项目名称</th>
									<th>金额</th>
									<th>时间</th>
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
Date.prototype.format1 = function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}

	if(/(y+)/.test(format)) {
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}

	for(var k in o) {
	if(new RegExp("("+ k +")").test(format)) {
	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
	}
	}
	return format;
	} 

function func(id){
	var str='';
	$.ajax({
		type : "POST",
		url : '/found/detailList',
		data:{detailid:id},
		async : false,
		dataType : 'json',
		error : function(e) {
			alert("异常");
		},
		success : function(data) {
			 var list = eval(data);
			
			 $.each(list[0], function (n, m) { 
				 var d1=new Date(m.time.time).format1("yyyy-MM-dd hh:mm:ss");
				 str += '<tr>';
/* 					str += '<td>' + user.userid + '</td>';
 */					str += '<td width="40%">' + m.loanId + '</td>';
 					str += '<td>' + m.loanName + '</td>';
 					str += '<td>' + m.loanmoney + '</td>';
					str += '<td>'+d1+'</td>';
					
					str += '<td><a href="javascript:del('+m.id+','+list[1]+')">删除</a></td>';
					str += '</tr>';
	          });
			$("#ttab").html(str);
		}
	});
}
jQuery(document).ready(function(){
	var id=${id};
	func(id);
});
$("button[id=save]").click(function(){
	$.ajax({
		type : "POST",
		url : '/found/detail',
		data:$('#basicForm').serialize(),
		error : function(e) {
			alert("异常");
		},
		success : function(data) {
			if(data == 'loan'){
			 alert("项目名称不正确!");
		 }else if(data=='fail'){
			 alert("保存失败!");
		 }else{
					alert("保存成功!");
					func(data);
		
		 }
		}
	});
});
function del(id,detailid) {
	$.ajax({
		type : "POST",
		url : '/found/deldetail',
		data:{id:id},
		error : function(e) {
			alert("异常");
		},
		success : function(data) {
			if(data == 'ok'){
			alert("删除成功!");
			func(detailid);
		 }else{
			 alert("删除失败!");
		 }
		}
	});
}
</script>

</body>
</html>
    