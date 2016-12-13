<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
   <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
  <title>奖励明细</title>
</head>
<body>
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section> 
 <%@include file="../base/leftMenu.jsp"%> 
  <div class="mainpanel">   
	<%@include file="../base/header.jsp"%>      
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：奖励明细列表 </h2>     
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
     <div class="panel-heading">
     	<a type="button" class="btn btn-primary" href="${cxf}/award/awardList/unfinance">返回</a>
     
     </div>
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">
                <!-- <th width="8%">项目类型</th> -->
                <th width="6%">编号</th>
                <base:active activeId="USER_LOOK">
                <th width="10%">奖励用户</th></base:active>
                <th width="8%">真实姓名</th>
                <th width="8%">投资金额</th>
                <th width="10%">奖励金额</th>
                <th width="13%">创建日期</th>
                <th width="13%">发送日期</th>
                <th width="13%">日志记录</th>       
                <th width="10%">奖励状态</th>
                <th width="9%">操作</th>
             </tr>
          </thead>
          <tbody>
		     <c:forEach  var="awardUser" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${awardUser.id}</td>
				 <base:active activeId="USER_LOOK"><td>${awardUser.userId }</td></base:active>
				 <td>${awardUser.realName}</td>
				 <td>${awardUser.investTotalMoneyByloanID}</td>
				 <td>${awardUser.money}</td>
				 <td><fmt:formatDate value="${awardUser.createTime}" type="both"/></td>
				 <td><fmt:formatDate value="${awardUser.sendTime}" type="both"/></td>
				 <td>${awardUser.detail}</td>
				 <td>${awardUser.status}</td>
				<td><c:if test="${awardUser.status == '未发送' }"><a href="javascript:edit('${awardUser.id}','${awardUser.money}','${awardUser.awardItemId}')">修改</a> |<a href="javascript:del('${awardUser.id}','${awardUser.money}','${awardUser.awardItemId}')">删除</a></c:if></td>
		     </tr>
		     </c:forEach>
         </tbody>          
        </table>    
        <%@ include file="../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>
</section>
<script type="text/javascript">
//修改奖励金额
function edit(id,money,awardItemId) {
ds.dialog({
	  title : "修改奖励金额",
	  content : '<div class="form-group"><label class="col-sm-3 control-label">奖励金额</label><br>'+
			 '<input type="text" name="awardMoney" id="awardMoney" class="form-control"></div>',
	   width:450,
	   yesText : '保存',
	   onyes:function(){
	   		this.close();
	   		var awardMoney=$("#awardMoney").val();
	   		//alert($("#awardMoney").val());
	   	    $.ajax({
				type : "POST",
				dataType:'text',
				url : '/award/editAwardUser',
				data : {id:id,money:money,awardItemId:awardItemId,awardMoney:awardMoney},// 你的formid
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'suceess') {
						alert('修改成功');
						location.reload();
					}else{
						alert('修改失败');
						location.reload();
					}
				}
			}); 
	   },
	   noText : '取消',
	   onno : function(){
	   this.close();
	   },
 }); 
}
function del(id,money,awardItemId){
	  if(confirm("您确定要删除吗？")){
		  $.ajax({
				type : "POST",
				dataType:'text',
				url : '/award/delAwardUser',
				data : {id:id,money:money,awardItemId:awardItemId},// 你的formid
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'suceess') {
						alert('删除成功');
						location.reload();
					}else{
						alert('删除失败');
						location.reload();
					}
				}
			});
	  }
}
</script>
</body>
</html>
