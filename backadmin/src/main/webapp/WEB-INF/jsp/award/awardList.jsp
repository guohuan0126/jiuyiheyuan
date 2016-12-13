<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.duanrong.business.flow.model.ItemFlow" %>
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
  <title>平台奖励</title>
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
      <h2><i class="fa fa-table"></i>当前位置：平台奖励列表 </h2>     
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline" id="form1"  action="/award/awardList/unfinance" method="post">
		    	  <div class="form-group" >             
                    <select id="type" name="type" class="form-control" style="width:140px;">                    
                      <option value="">选择一个查询条件...</option>
                      <option value="name">名称</option>
                      <option value="description">项目描述</option>
                      <option value="loanId">投资项目ID</option>                                       
                    </select>                                   
                </div>
	            <div class="form-group">	              
	              <input type="text" name="selewhere"  class="form-control" placeholder="输入查询条件">
	            </div>
	            <div class="input-group" >
                <input type="text" class="form-control" name="start" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"></span>
                </div>
	            <script type="text/javascript">	            		            	       
					 jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
					 jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	  				 				
            	</script>
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	            <base:active activeId="Export_AwardNew">
	            <button id="export" type="button"  class="btn btn-primary">导出数据</button>
	            </base:active>
	          </form>
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">
               
                <th width="4%">编号</th>
                <th width="6%">名称</th>
                <th width="8%">项目描述</th>
                <th width="12%">投资项目</th>
                <th width="10%">短信内容</th>
                <th width="8%">奖励金额<br>(实发/应发)</th>
                <th width="8%">用户数量<br>(实发/应发)</th>             
                <th width="8%">状态信息</th>               
                <th width="8%">奖励状态</th>
                <th width="14%">操作</th>
             </tr>
          </thead>
          <tbody>
		     <c:forEach  var="award" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${award.id}</td>
				 <td>${award.name }</td>
				 <td>${award.description}</td>
				 <td>${award.loanId}<br>${award.loanName}</td>
				 <td>${award.userMsg}</td>
				 <td>${award.sendMoney}/${award.moneyAmount}</td>
				 <td>${award.sendCount}/${award.userCount}</td>
				 <td>${award.createUserID}<br><fmt:formatDate value="${award.createTime}" type="both"/></td>				 								 
				 <td>
				 <c:choose>
				 	<c:when test="${not empty award.itemFlow and award.itemFlow.status == 'unapproved' }">
				 		已否决			 		
				 	</c:when>
				 	<c:when test="${not empty award.itemFlow and award.itemFlow.status != 'unapproved' }">
				 		<a data-toggle="modal" href="" onclick="operater('${award.itemFlow.id}', '${award.itemFlow.itemId}', '${award.itemFlow.itemType}', '${award.itemFlow.nodeId}');">${award.itemFlow.flowNode.nodeName}</a>
				 	</c:when>
				 	<c:otherwise>${award.status}</c:otherwise>
				 </c:choose>
				 </td>				 								 
				 <td>
				 	<a href="/award/awardUserList/${award.id}">查看明细</a> | 
				 	<a href="/award/awardFlowList?awardId=${award.id}">查看流程</a>
				 	<base:active activeId="new_revoke_award">
				 		<c:if test="${award.status != '已发送' }"> | <a href="" onclick="revoke('${award.id}')">撤销</a></c:if>
				 	</base:active>	
				 </td>
		     </tr>
		     </c:forEach>
         </tbody>          
        </table> 
        <script type="text/javascript">
        	function operater(id, itemId, itemType,nodeId){
        		if(nodeId == '45'){
        			dsdislog('审批操作',id, itemId, itemType,nodeId);
        		}else if(nodeId == '46'){
        			dsdislog('发送操作',id, itemId, itemType,nodeId);
        		}
			}
        	
        	function dsdislog(titleValue,id, itemId, itemType,nodeId){
        		ds.dialog({
   				   title : titleValue,
   				   content : '<div class="form-group"><label class="col-sm-3 control-label">信息</label><br>'+
   				   			 '<textarea rows="5" name="message" id="message" class="form-control"></textarea></div>',
   				   width:450,
   				   yesText : '同意',
   				   onyes:function(){
   				   		this.close();
   				    	oper(id, $('#message').val(), 'approve', itemId, itemType,nodeId);					  
   				   },
   				   noText : '否决',
   				   onno : function(){
   				   this.close();
   				   oper(id, $('#message').val(), 'unapproved', itemId, itemType,nodeId);
   				   },
   				});
        	}
        	
        	
        function oper(id, message, status, itemId, itemType,nodeId){	
			$.ajax({
				type : 'POST',
				url : '/award/operAward',						
				data:{
					id:id,
					status:status,
					message:message,
					itemId: itemId,
					itemType: itemType,
					nodeId: nodeId
				},
				beforeSend:function(){
					xval=getBusyOverlay('viewport',
					{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
				},
				success:function(data) {
					window.clearInterval(xval.ntime); 
					xval.remove();
					if(data == 'sendOff'){
						return;
					}else{	
						alert(data);
						location.reload()  
					}
				},
				error : function() {
					window.clearInterval(xval.ntime); 
					xval.remove();
					alert("异常！");
				}
			});
		}
		
// 		 function sendAward(itemId,flowId,status){
// 	        	$.ajax({
// 					type : 'POST',
// 					url : '/award/sendAwardItem',						
// 					async : false,
// 					data:{
// 						flowId:flowId,
// 						status:status,
// 						itemId: itemId,
						
// 					},
// 					beforeSend:function(){
// 						xval=getBusyOverlay('viewport',
// 						{color:'', opacity:0.5, text:'正在发送，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
// 						{color:'blue', size:25, type:'o'});	
// 					},
// 					success:function(data) {
// 						window.clearInterval(xval.ntime); 
// 						xval.remove();
// 						location="/award/awardList/unfinance"; 
// 					},
// 					error : function() {
// 						alert("异常！");
// 					}
// 				});
// 	        }
	        
	        function revoke(awardId){
	        	if(confirm("确认要撤销该项目吗？")){
	        	$.ajax({
					type : 'POST',
					url : '/award/awardRevoke',						
					async : false,
					data:{
						itemId:awardId									
					},
					beforeSend:function(){
						xval=getBusyOverlay('viewport',
						{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
						{color:'blue', size:25, type:'o'});	
					},
					success:function(data) {
						window.clearInterval(xval.ntime); 
						xval.remove();
						if(data == 'ok'){
							alert("撤销成功");
						}else{
							alert("撤销失败");
						}											
						location.reload()  
					},
					error : function() {
						alert("异常！");
					}
				});
	        }
	        }
	        $("button[id=export]").click(function(){
        		$("#form1").attr("action","/award/export");
			    $("#form1").submit();
        	});
        </script>
        	
        <%@ include file="../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>
 
</section>



</body>
</html>
