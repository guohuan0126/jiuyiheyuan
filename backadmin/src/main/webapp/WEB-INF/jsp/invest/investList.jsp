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
 <%@include file="../invest/redPacketForDiaglogOpen.jsp" %>
  <div class="mainpanel">   
	<%@include file="../base/header.jsp"%>      
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：项目投资列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
    	<div>
	    	 <form class="form-inline"  action="/invest/investByLoan" method="post">		    	 
		    	<div class="form-group">              
		    		<input type="hidden" name="loanId" value="${loanId}">
	            	<input type="text" name="investUserID"  class="form-control" value="${investUserID}" placeholder="用户编号" style="width:120px">
	            </div>		    	 
	            <div class="form-group">	              
	            	<input type="text" name=investUserName  class="form-control" value="${investUserName}" placeholder="真实姓名" style="width:120px">
	            </div>           
	            <div class="input-group" >
                <input type="text" class="form-control" name="startTime" value="${startTime}" placeholder="开始时间" id="datepicker" style="width:100px">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
	            <div class="input-group" >
                <input type="text" class="form-control" name="endTime" value="${endTime}" placeholder="结束时间" id="datepicker1" style="width:100px">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>&nbsp;&nbsp;&nbsp;
			<script type="text/javascript">
					 jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			 		 jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
	            <div class="input-group" >
                <input type="text" class="form-control" name="minMoney" value="${minMoney}" placeholder="最小金额" id="minMoney" style="width:100px">               
                </div> -- 
	            <div class="input-group" >
                <input type="text" class="form-control" name="maxMoney" value="${maxMoney}" placeholder="最大金额" id="maxMoney" style="width:100px">              
                </div>&nbsp;&nbsp;&nbsp;          
	            <div class="form-group" style="width: 140px;">              	           
                    <select id="status" name="status" class="select" required data-placeholder="请选择投资状态...">
                     	<c:if test="${status != null && status != '' }">
                      		<option value="${status}">${status}</option>
                     	</c:if>
                      <option value="">请选择...</option>                
                      <option value="还款中">还款中</option>
                      <option value="投标成功">投标成功</option>
                      <option value="完成">完成</option>
                      <option value="流标">流标</option>                   
                    </select>              
                </div>	               
                 <script type="text/javascript">
                    	jQuery(".select").select2({
							width : '100%',
							minimumResultsForSearch : -1
						});
					
						jQuery("#basicForm").validate(
								{
									highlight : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-success').addClass('has-error');
									},
									success : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-error');
									}
								});
																
						jQuery("#basicForm3").validate(
								{
									highlight : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-success').addClass('has-error');
									},
									success : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-error');
									}
								});
			
						jQuery("#basicForm4").validate(
								{
									highlight : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-success').addClass('has-error');
									},
									success : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-error');
									}
								});                  
                    </script> 
                
                <label class="control-label">合并相同投资人</label> 
              <c:if test="${merge == 'on'}"></c:if>
                <input type="checkbox" id="merge"  name="merge" <c:if test="${merge == 'on'}">checked</c:if> />&nbsp;&nbsp;&nbsp;                   
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	           <!--  <button type="button" class="btn btn-primary" onclick="javascript:reset()">重置</button> -->
	          </form>
	    </div>
	 </div>
	 <div class="panel-heading">	 
	 <base:active activeId="create_invest_infomation"><button type="button" class="btn btn-primary" onclick="createInformation()">创建投资确认函</button>	
	 <a href="${cxt}/invest/getInvestInformation?loanId=${loanId}">预览投资确认函</a></base:active>	
	 <base:active activeId="create_award5and10"><button type="button" class="btn btn-primary" onclick="createAward5And10()">发起5%和10%奖励</button></base:active> 
	 <base:active activeId="Export_Excel"><a href="${cxt}/invest/exportInvest?1=1${str}" class="btn btn-primary" >导出Excel</a></base:active>
	 </div>  
     <div class="panel-body"> 
     <div>
     <span style="font-weight: bold;">项目编号：<c:if test="${not empty pageInfo.results}">${pageInfo.results[0].loanId}</c:if></span>
     <span style="margin-left:60px; font-weight: bold;">项目名称：<c:if test="${not empty pageInfo.results}">${pageInfo.results[0].loanName}</c:if></span>
     </div>
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">                              
                
                <th>投资人编号<base:active activeId="USER_LOOK">/姓名/身份证<br>
                	手机号码/Email/联系地址</base:active>
                	<base:no_active>             
                 <base:active activeId="USER_MUMBER">
                 	 <th>手机号码</th>  
                 </base:active>
                 </base:no_active>  	
               	</th>                         
                <th>归属地</th>                                                  
                <th>投资时间</th>
                <th>投资金额</th>              
                <th>跟投奖励金额</th>
                <th>投标状态</th>              
                <th>历史投资</th>
                <th>加息券</th>
                <th>操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="invest" items="${pageInfo.results}">
		     <tr class="font">						
		     	 <td>
				 <c:if test="${invest.remarkNum>0 }">
                        <img src="${pageContext.request.contextPath}/images/beizhu.png" style="width:30px;float:left;">
                 </c:if>
				 <base:active activeId="USER_REMARK"><a style="cursor:pointer" onclick="edit('${invest.investUserID}');">${invest.investUserID }</a></base:active>
				 <base:no_active>
                      ${invest.investUserID }
                 </base:no_active>  				 	
				 <base:active activeId="USER_LOOK">				 
					 <br>${invest.investUserName}
					 <br>${invest.userIdCard}
					 <br>${invest.userMobileNumber}
					 <br>${invest.email}
					 <br>${invest.userHomeAddress }
				 </base:active>
				 <base:no_active>		 					   
					 <base:active activeId="USER_MUMBER">
					 	<br>*******${fn:substring(invest.userMobileNumber, 7, 11)}
					 </base:active>				 
				 </base:no_active>				                
				 <td>${invest.userProvince}&nbsp;&nbsp;${invest.userCity}</td>				 				  
				 <td><fmt:formatDate value="${invest.time}" type="both"/> </td>
				 <td>${invest.sumMoney}元</td>
				 <td>${invest.returnPondMoney}元</td>			 
				 <td>${invest.status}</td>
				 <td><a href="${cxf}/invest/investByUser?investUserID=${invest.investUserID}">${invest.investTotal}次&nbsp;&nbsp;&nbsp;查看详情</a></td>
				 <base:active activeId="REDPACKET_UPDATE">				 
					 <td>
					 	<div class="photo">
						  <div class="ui-widget-header ui-corner-all">
						    <a href="javascript:void(0);" data-geo="" onclick="openDialog('${invest.id}','${invest.redpacketId}','${invest.userMobileNumber}','${invest.status}','${invest.loanStatus}')">${invest.redpacketId}</a>
						  </div>
						</div>
					 </td>
				 </base:active>
				 <base:no_active>
				 	<td>
					 	<div class="photo">
						  <div class="ui-widget-header ui-corner-all">
						    <a href="javascript:void(0);" data-geo="" onclick="javascript:void(0);">${invest.redpacketId}</a>
						  </div>
						</div>
					 </td>
				 </base:no_active>
				 <td><c:if test="${invest.status == '投标成功'}">
												<base:active activeId="LOCAL_GIVE_MONEY">
													<a href="javascript:giveMoneyToBorrowerFromInvest('${invest.id}')">单笔放款</a>
												</base:active>
											</c:if></td>
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>      
        <input type="hidden" id="userpid"> 	
        <%@ include file="../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>
<script type="text/javascript">
	var xval;
	
	function createInformation(){
		$.ajax({			
			type : 'POST',			
			url : "${cxt}/invest/createInformation?loanId=${loanId}", 
			beforeSend:function(){
					xval=getBusyOverlay('viewport',
					{color:'', opacity:0.5, text:'正在创建...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
				},			
			success : function(msg) {
			window.clearInterval(xval.ntime); 
					xval.remove();
					alert(msg);
				},
				error : function() {
				window.clearInterval(xval.ntime); 
					xval.remove();
					alert("创建投资确认函失败");
				}
		});	
	}
	
	function createAward5And10(){
		if(window.confirm('确认创建奖励项目?')){
			$.ajax({				
				type : 'POST',
				url : '/award/createAward5And10',
				dataType : 'text',
				data:{
					'loanId':'${loanId}'
				},
				beforeSend:function(){
					xval=getBusyOverlay('viewport',
					{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
				},
				success : function(data) {
					window.clearInterval(xval.ntime); 
					xval.remove();
					alert(data);
				},
			});
		}
	}
	function edit(id){
		var userid=id;
		$("#userpid").val(userid);
		ds.dialog({
			   title : "编辑", 
			   content : '<iframe  src="${ctx }/tormark.jsp" width="100%" height="100%"></iframe>',
			   width:800,
			   height:700
			});
	}
	
	function giveMoneyToBorrowerFromInvest(investId){
			$.ajax({			
			type : 'POST',			
			url : "${cxt}/invest/giveMoneyToBorrowerFromInvest?investId="+investId, 
			beforeSend:function(){
					xval=getBusyOverlay('viewport',
					{color:'', opacity:0.5, text:'正在放款...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
				},			
			success : function(msg) {
					window.clearInterval(xval.ntime); 
							xval.remove();
							alert(msg);
				},
			error : function() {
					window.clearInterval(xval.ntime); 
					xval.remove();
				}
		});	
		}
</script>
</section>

</body>
</html>
               