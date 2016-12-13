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

  <title>银行卡管理</title>

  
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
      <h2><i class="fa fa-table"></i>当前位置：银行卡管理 </h2>    
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/bankinfo/bankinfoList" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">银行名称</label>
	              <input type="text" name="name" value="${name}" class="form-control"  placeholder="银行名称">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail1">银行简称</label>
	              <input type="text" name="bank" value="${bank}" class="form-control"  placeholder="银行简称">
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">银行卡状态</label>
	                <select class="select2" name="status" id="st" data-placeholder="银行卡状态...">
	                  <option value="">--银行卡状态</option>
	                  <option value="1">启用</option>
	                  <option value="0">禁用</option>	                  
	                </select>
	            </div>
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">支付平台</label>
	                <select class="select2" name="paymentId" id="paymentId" data-placeholder="支付平台...">
	                  <option value="">--支付平台</option>
	                  <option value="Yeepay">易宝</option>
	                  <option value="JDpay">京东</option>	                  
	                </select>
	            </div>
	            <button type="submit" class="btn btn-primary">查询</button>
	           <base:active activeId="BANKINFO_OPERATION">  <button type="button" id="add" class="btn btn-primary">添加</button> </base:active>
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>银行卡编号</th>
                        <th>银行名称</th>
                        <th>银行简称</th>
                        <th>内容描述</th>
                        <th>支持功能</th>
                        <th>排序号</th>
                        <th>图片路径</th>
                        <th>银行卡状态</th>
                        <th>支付通道</th>                        
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo}" var="item">
                      <tr>
                       <td>${item.id}</td>
                       <td>${item.name}</td>                    
					  <td>${item.bank }</td>
					  <td>${item.rechargeDesc }</td>
					  <td>
					  <c:if test="${item.quickRecharge eq '1'}">
	                       		      快捷，
	                   </c:if>	                  
	                   <c:if test="${item.onlineBank eq '1'}">
	                       		      网银，
	                   </c:if>	           
	                   <c:if test="${item.tiecard eq '1'}">
	                       		      绑卡
	                   </c:if>
					  </td>
					  <td>
					  <input id="${item.id}" style="width: 30px" type="text" value="${item.sortNo }" />
				     <%--  <base:active activeId="BANKINFO_OPERATION">  --%><input type="button" onclick="editSort('${item.id}')" value="保存"></input><%-- </base:active> --%>
					  </td>
					  <td><img src="https://duanrongweb.oss-cn-qingdao.aliyuncs.com/${item.url}" width="35" height="35" alt="" /></td>
                        <td>
                         <c:if test="${item.whetherDelete eq '0'}">
	                       		      禁用
	                     </c:if>
	                      <c:if test="${item.whetherDelete eq '1'}">
	                       		      启用
	                     </c:if>	                  
                        </td>
                       <td> <c:if test="${item.paymentId eq 'Yeepay'}">
	                       		     易宝
	                     </c:if>
	                      <c:if test="${item.paymentId eq 'JDpay'}">
	                       		      京东
	                     </c:if>	
	                     </td>        
                       <td>                     
                   <%--   <base:active activeId="BANKINFO_OPERATION">  --%> <a href="/bankinfo/toedit?id=${item.id}">修改</a>|
                         <c:if test="${item.whetherDelete eq '1'}">
                        <a href="javascript:del('${item.id}','${item.whetherDelete}');">禁用</a>
                        </c:if>
                         <c:if test="${item.whetherDelete eq '0'}">
                        <a href="javascript:del('${item.id}','${item.whetherDelete}');">启用</a>
                        </c:if>
                      <%--  </base:active>  --%>
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
jQuery(document).ready(function(){
	$("#st").val('${status}');
	$("#paymentId").val('${paymentId}');
});
$("button[id=add]").click(function(){
	window.location.href="/bankinfo/toadd";
});
function del(id,status){
	 /*  if(confirm("您确定要删除吗？")){ */
		  $.ajax({
				type : "POST",
				url : '/bankinfo/disable',
				data : {id:id,status:status},// 你的formid
				success : function(data) {
					if (data == 'ok') {
						alert('操作成功');
						window.location = "/bankinfo/bankinfoList";
					}else{
						alert('操作失败');
					}
				}
			});
	 /*  } */
}
function editSort(nodeId){
	var sortNum=$("#"+nodeId).val();
	$.ajax({
		type : "post",
		url : "/bankinfo/toEditSortNum",
		data : {
			"id" : nodeId,
			"sortNum":sortNum
		},
		dataType : "text",
		success : function(data) {
			if(data=="ok"){
				alert("修改成功");
				window.location.href="/bankinfo/bankinfoList";
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
