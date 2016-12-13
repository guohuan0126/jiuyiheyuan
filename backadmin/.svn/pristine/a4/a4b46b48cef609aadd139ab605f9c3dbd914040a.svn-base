<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
  <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
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
  <title>投资列表</title>
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
      <h2><i class="fa fa-table"></i>当前位置：项目投资列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">       
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">   	
	 </div>
	 <div class="panel-heading">
	 <a href="${cxt}/invest/investByLoan?loanId=${loanId}" class="btn btn-primary" >返回</a>
	 <base:active activeId="sendInvestEmail"><button type="button" class="btn btn-primary" onclick="sendEmail('${loanId}');">发送</button></base:active>
	 </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th width="10%">发送用户</th>
                <th width="10%">发送邮箱</th>
                <th width="10%">创建时间</th>              
                <th width="8%">创建人</th>
                <th width="14%">标题</th>              
                <th width="6%">内容</th>
                <th width="10%">状态</th>              
                <th width="14%">借款项目</th>
                <th width="8%">发送人</th>           
                <th width="10%">发送时间</th>              
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="email" items="${emails}">
		     <tr class="font">		     	
				 <td>${email.userId}</td>
				 <td>${email.email }</td>
				 <td><fmt:formatDate value="${email.time}" type="both"/> </td>
				 <td>${email.createUserID }</td>
				 <td>${email.title}</td>
				 <td><a href="javascript:preview('${email.id}') ">预览</a></td>
				 <td>${email.status}</td>
				 <td>${email.loanId}</td>
				 <td>${email.sendUserID}</td>
				 <td><fmt:formatDate value="${email.sendTime}" type="both"/></td>			 
			</tr>
		 </c:forEach>
         </tbody>          
        </table>       	     
 </div>
 </div>
 </div>
 </div>
<script type="text/javascript">

	var xval;
	
	function preview(id){	
	
			$.ajax({
				type : 'POST',
				url : '${ctx}/invest/preview',						
				data:{
					id:id,					
				},
				success:function(data) {
					dsdialog("投资确认函", data);
				},
				error : function() {
					alert("异常！");
				}
			});	
			
	}
		
	function dsdialog(titleValue, content){
        		ds.dialog({
   				   title : titleValue,
   				   content :  content,
   				   width:800,   				  
   				   noText : '关闭',
   				   onno : function(){					
   				   },
   				});
        	}
    function sendEmail(loanId){    	
    	$.ajax({
				type : 'POST',
				url : '${ctx}/invest/sendEmail',						
				data:{
					loanId:loanId,					
				},
				beforeSend:function(){
					xval=getBusyOverlay('viewport',
					{color:'', opacity:0.5, text:'努力发送中...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
				},
				success:function(data) {
					window.clearInterval(xval.ntime); 
					xval.remove();
					alert(data);
					location="${ctx}/invest/getInvestInformation?loanId=${loanId}";
					
				},
				error : function() {
					window.clearInterval(xval.ntime); 
					xval.remove();
					alert("异常");
				}
			});	
    }
</script>
</section>

</body>
</html>
