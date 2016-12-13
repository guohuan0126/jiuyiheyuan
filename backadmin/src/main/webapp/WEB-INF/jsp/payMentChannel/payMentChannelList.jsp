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

  <title>渠道管理</title>

  
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
      <h2><i class="fa fa-table"></i>当前位置：渠道管理 </h2>    
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/bankinfo/bankinfoList" method="post">
	          </form>
	          <button type="button" id="add" class="btn btn-primary">添加</button>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>编号</th>
                        <th>渠道名称</th>
                        <th>渠道缩写</th>
                        <th>渠道logo</th>
                        <th>普通充值费率</th>
                        <th>快捷充值费率</th>
                        <th>提现手续费</th>
                        <th>PC是否可用</th>
                        <th>移动端是否可用</th>
                        <th>排序</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="payMentChannel">
                      <tr>
                       <td>${payMentChannel.id}
                       	<input value="${payMentChannel.id}" type="hidden" id="id">
                       </td>
                       <td>${payMentChannel.name}</td>                    
					  <td>${payMentChannel.code}</td>
					  <td><img src="https://drwebdemo.oss-cn-qingdao.aliyuncs.com/${payMentChannel.logo}" width="35" height="35" alt="" /></td>
					  <td>${payMentChannel.rateGateway}</td>
					  <td>${payMentChannel.rateQuick}</td>
					  <td>${payMentChannel.withdrawFee}</td>
					  <td>
					  	<c:if test="${payMentChannel.usable=='1'}">可用</c:if>
					  	<c:if test="${payMentChannel.usable=='2'}">不可用</c:if>
					  </td>
					  <td>
					  	<c:if test="${payMentChannel.mobileUsable=='1'}">可用</c:if>
					  	<c:if test="${payMentChannel.mobileUsable=='2'}">不可用</c:if>
					  </td>
					  <td>
					  	<input value="${payMentChannel.sort}" style="width:25px" id="${payMentChannel.id}"/>
					  	<input type="button" onclick="editSort('${payMentChannel.id}')" value="保存"/>
					  </td>
					  	<td>
					  		<a href="/payMentChannel/toEditPayMentChannel?id=${payMentChannel.id}">编辑</a>
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
$("button[id=add]").click(function(){
	window.location.href="/payMentChannel/toaddPayMentChannel";
});
function editSort(payMentChannelId){
	var sortNum=$("#"+payMentChannelId).val();
	$.ajax({
		type : "post",
		url : "/payMentChannel/toEditSortNum",
		data : {
			"id" : payMentChannelId,
			"sortNum":sortNum
		},
		dataType : "text",
		success : function(data) {
			if(data=="ok"){
				alert("修改成功");
				window.location.href="/payMentChannel/showChannel";
			}else{
				alert("修改失败");
			}
			
		},
		error : function() {
			console.info("调取失败");
		}
	});
}
</script>
</body>
</html>
