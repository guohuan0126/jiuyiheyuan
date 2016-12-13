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

  <title>渠道银行管理</title>

  
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
      <h2><i class="fa fa-table"></i>当前位置：渠道银行管理</h2>    
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/payMentChannel/payMentShowBankInfo" method="post">
	          	<div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">银行名称</label>
	              <input type="text" name="bankName" value="${bankName}" class="form-control"  placeholder="银行名称">
	            </div>
	            <button type="submit" class="btn btn-primary">查询</button>
	             <button type="button" id="add" class="btn btn-primary">添加</button>
	          </form>
	         
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>编号</th>
                        <th>银行名称</th>
                        <th>银行名称缩写</th>
                        <th>银行logo</th>
                       <!--  <th>富友银行编码</th> -->
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="payMentBankInfo">
                      <tr>
                       <td>${payMentBankInfo.id}
                       	<input value="${payMentBankInfo.id}" type="hidden" id="id">
                       </td>
                       <td>${payMentBankInfo.name}</td>                    
					  <td>${payMentBankInfo.code}</td>
					  <td><img src="https://drwebdemo.oss-cn-qingdao.aliyuncs.com/${payMentBankInfo.logo}" width="35" height="35" alt="" /></td>
					  <%-- <td>${payMentBankInfo.fuIouCode}</td> --%>
                     	<td>
					  		<a href="/payMentChannel/toEditPayMentBankInfo?id=${payMentBankInfo.id}">编辑</a>
					  	</td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
        <%@ include file="../base/page.jsp"%>
 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>

<script>
$("button[id=add]").click(function(){
	window.location.href="/payMentChannel/toaddPayMentBankInfo";
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
