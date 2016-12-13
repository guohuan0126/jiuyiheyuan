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
  <title>信息披露数据管理列表</title>

  <style type="text/css">
.ui-datepicker-title {
text-transform: uppercase;
font-family: 'LatoBold';
color: #1CAF9A;

}

.ui-datepicker {
background: #1D2939;
margin-top: 1px;
border-radius: 2px;
width: 280px;}

.ui-datepicker-next {
background: url(../../images/calendar-arrow.png) no-repeat 10px 5px;
}

.ui-datepicker-prev {
background: url(../../images/calendar-arrow.png) no-repeat 8px -80px;
}

.ui-datepicker table {
    color: #ffffff;
}
.only p{width:400px;height:100px;word-wrap:break-word;overflow-y:auto;}
.only_1{width:100px;display:block;word-wrap:break-word;    }
</style>
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
      <h2><i class="fa fa-table"></i>当前位置：信息披露数据管理列表 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline" id="form1" method="post">
	   	       <div class="input-group" >
                <input type="text" class="form-control"  name="start" value="${start}" placeholder="创建开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control"   name="end" value="${end}" placeholder="创建结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
	    	 
	            <button type="submit" id="search" class="btn btn-primary">查询</button>	         
	           <base:active activeId="add_RiskFoundinfoDisclosure"> <button type="button" id="add" class="btn btn-primary">添加披露信息</button></base:active>	
	          
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>编号</th>
                        <th>当前逾期总额</th>
                        <th>历史逾期总额</th>
                        <th>逾期坏账总额</th>
                        <th>创建时间</th>
                        <th>创建人编号</th>                       
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${disclosureList}" var="item">
                      <tr>
                       <td>${item.id }</td>                      
                       <td><fmt:formatNumber value="${item.currentTotalOverdue}" pattern="##0.####" /></td>
                       <td><fmt:formatNumber value="${item.historyTotalOverdue}" pattern="##0.####" /></td>                                            
						<td><fmt:formatNumber value="${item.badDebtOverdue}" pattern="##0.####" /></td>
						<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${item.operationUserid}</td>
     					<td><base:active activeId="del_RiskFoundinfoDisclosure"><a href="javascript:del('${item.id}');" >删除</a></base:active>	</td>
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
$("button[id=search]").click(function(){
	$("#form1").attr("action","/risk/infoDisclosureList");
	$("#form1").submit();
});
jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
function del(id){
	  if(confirm("您确定要删除吗？")){
		  $.ajax({
				type : "POST",
				url : '/risk/deleteInfoDisclosure',
				data : {id:id},// 你的formid
				async : false,
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'ok') {
						alert('操作成功');
						window.location = "/risk/infoDisclosureList";
					}else{
						alert('操作失败');
					}
				}
			});
	  }
}
$("button[id=add]").click(function(){
	window.location.href="/risk/addInfoDisclosure";
});
</script>
</body>
</html>
