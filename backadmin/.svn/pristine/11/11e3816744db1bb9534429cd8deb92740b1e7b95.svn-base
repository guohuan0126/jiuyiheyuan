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
  <title>代扣</title>
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
      <h2><i class="fa fa-table"></i>当前位置：代扣管理 </h2>     
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	   <base:active activeId="WITHHOLD_ADMIN"> <button type="button" id="add" class="btn btn-primary">添加</button></base:active>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>用户姓名</th>
                        <th>手机号</th>
                        <th>身份证号</th>
                        <th>开户行/账户</th>
                        <th>代扣金额</th>
                       <base:active activeId="WITHHOLD_ADMIN"> <th>操作</th></base:active>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                      <td>${item.realname}</td>
                      <td>${item.mobileNum}</td>
                      <td>${item.card}</td>
                      <td>
                     <select class="select2" name="bank" id="st${item.id }" data-placeholder="银行卡...">
	                  <c:forEach items="${item.banks}" var="bank">
	                  <option value="${bank.id}">${bank.bankName}-${bank.bankCount}</option>
	                  </c:forEach>
	                </select>
                     </td>
                     <td>
                     <input type="text" name="amount"   id="amount${item.id}">
                     <div id="erroramount" style="display: none;">金额不能为空</div>
                     </td>
                     <base:active activeId="WITHHOLD_ADMIN">
                     <td>
                    <a style="cursor:pointer"  onclick="whthhold('${item.userId}','${item.id }');">代扣充值</a>|
                    <a style="cursor:pointer" onclick="deletePer('/found/withholdDel?id=${item.id}');"> 删除个人</a>|
                    <a style="cursor:pointer" onclick="deleteCard();"> 删除卡</a> |
                    <a style="cursor:pointer" href="/found/withholdBank/toadd?id=${item.userId }">添加卡</a>
					</td>
					</base:active>
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
jQuery(document).ready(function(){
    // Date Picker
	  jQuery('#datepicker').datepicker();
	  jQuery('#datepicker1').datepicker();	  
	  jQuery('#datepicker-inline').datepicker();
	  
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });		  
	
});
function deletePer(url){
	
	$.ajax({			
		type : 'POST',			
		url : url,
		success : function(msg) {
				if(msg == 'ok'){
					alert("删除成功!");					
				}else{
					alert("删除失败!");					
				}
				window.location = "/found/withhold";
			},
			error : function() {
				alert("异常！");
			}
	});	
}
function deleteCard(){
	var id=$("#st").val();
	$.ajax({			
		type : 'POST',			
		url : '/found/withCardDel', 
		data:{id:id},
		success : function(msg) {
				if(msg == 'ok'){
					alert("删除成功!");					
				}else{
					alert("删除失败!");					
				}
				window.location = "/found/withhold";
			},
			error : function() {
				alert("异常！");
			}
	});	
}
function whthhold(userId,id){
	var bankid=$("#st"+id).val();
	var amount=$("#amount"+id).val();
	if(amount==null ||amount==''){
		$('#erroramount').attr("style", "display:block;font-size:14px;color:red");
		return false;
	}
	var img = /^[0-9]*[1-9][0-9]*$/;
	if (!img.test(amount) || isNaN(amount) || amount <=0) {
		$('#erroramount').text("金额不正确");
		$('#erroramount').attr("style", "display:block;font-size:14px;color:red");
		return false;
	}
	if(confirm("确认充值?")){
		$.ajax({
			type : 'POST',
			url:"/found/withRecharg",		
			data:{
				'bankid':bankid,
				'amount':amount,
				'userId':userId
			},
			beforeSend:function(){
							xval=getBusyOverlay('viewport',
							{color:'blue', opacity:0.5, text:'进行中...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
							{color:'blue', size:25, type:'o'});	
			},
			error:function(){
				window.clearInterval(xval.ntime); 
				xval.remove();
			},		
			success : function(msg) {				
					window.clearInterval(xval.ntime); 
					xval.remove();
					if(msg == 'ok'){							
						alert("操作成功!");	
						window.location = "/found/withhold";
						//location.reload();			
					}else if(msg == 'bank'){
						alert("卡号不正确!");					
					}else if(msg == 'user'){
						alert("用户不正确!");
					}else if(msg == 'usereq'){
						alert("用户不相同!");
					}else if(msg == 'nouser'){
						alert("用户不存在!");
					}else if(msg == 'fail'){
						alert("操作失败!");
					}else{
						alert(msg);
					}
			}
		});
	}
}
$("button[id=add]").click(function(){
	window.location.href="/found/withhold/toadd";
}); 
</script>
</body>
</html>
