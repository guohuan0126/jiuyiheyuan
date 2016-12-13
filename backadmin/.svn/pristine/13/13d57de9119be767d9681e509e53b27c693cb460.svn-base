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
  <title>推送管理</title>
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
      <h2><i class="fa fa-table"></i>当前位置：推送记录 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	<form class="form-inline" id="f1"  action="/netLoanEye/pushRecords" method="post">		    	 
		    	<div class="form-group">              
	            	<input type="text" name="id"  value="${loan.id }" class="form-control" placeholder="项目编号" style="width:160px">
	            </div>		    	 
	            <div class="form-group">	              
	            	<input type="text" name="name" value="${loan.name }" class="form-control" placeholder="项目名称" style="width:160px">
	            </div>
	            <label class="control-label">项目状态 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="status" name="status" class="select" required data-placeholder="请选择...">
                      <option value="">请选择...</option>                
                      <option value="还款中" <c:if test="${loan.status=='还款中' }">selected</c:if>>还款中</option>
                      <option value="筹款中" <c:if test="${loan.status=='筹款中' }">selected</c:if>>筹款中</option>
                      <option value="等待复核" <c:if test="${loan.status=='等待复核' }">selected</c:if>>等待复核</option>
                      <option value="完成" <c:if test="${loan.status=='完成' }">selected</c:if>>完成</option>
                      <option value="流标" <c:if test="${loan.status=='流标' }">selected</c:if>>流标</option>                   
                    </select>              
                </div>	
                
                <label class="control-label">项目类型 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="loanType" name="loanType" class="select" required data-placeholder="请选择...">
                      <option value="">请选择...</option>                
                      <option value="车贷" <c:if test="${loan.loanType=='车贷' }">selected</c:if>>车贷</option>
                      <option value="金农宝" <c:if test="${loan.loanType=='金农宝' }">selected</c:if>>金农宝</option>
                      <option value="供应宝" <c:if test="${loan.loanType=='供应宝' }">selected</c:if>>供应宝</option>
                      <option value="房贷" <c:if test="${loan.loanType=='房贷' }">selected</c:if>>房贷</option>
                      <option value="企业贷" <c:if test="${loan.loanType=='企业贷' }">selected</c:if>>企业贷</option>                   
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
                <label class="control-label">项目提交时间：</label>             
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" name="start" placeholder="开始时间" style="width:140px">
               
                </div> -- 
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" name="end" placeholder="结束时间" style="width:140px">
                
                </div>
                
                <label class="control-label">推送时间：</label>             
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" name="pushTimeStart" placeholder="开始时间" style="width:140px">
               
                </div> -- 
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" name="pushTimeEnd" placeholder="结束时间" style="width:140px">
                
                </div>
	            <script type="text/javascript">	            		            
	            jQuery(document).ready(function(){					
					 jQuery('.datepicker').datetimepicker({
						showSecond : true,
						timeFormat : 'hh:mm:ss',
						stepHour : 1,
						stepMinute : 1,
						stepSecond : 1,
						hour: 13,
	  					minute: 15
					});				 
				});

            	</script>                       
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>	           
	          </form>
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th >编号</th>
                <th >项目名称</th>
                <th >还款方式</th>                
                <th >周期/利率</th>              
                <th >借款人</th>
                <th >借款总金额</th>              
                <th >项目状态</th>
                <th >项目发起时间</th>
                <th >推送时间</th>                 
                <th >操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="loan" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${loan.id}
					 <c:if test="${loan.newbieEnjoy == '是'}"><img src="${ctx}/images/newbie.png"></c:if>
					 <c:if test="${loan.organizationExclusive == '是'}"><img src="${ctx}/images/org.png"></c:if>
				 </td>
				 <td>${loan.name}
				 <c:if test="${not empty loan.emailSend and loan.emailSend != '' }"><span style="color:red; font-size:12px;">(${loan.emailSend})</span> </c:if></td>
				 <td>
					 <c:if test="${'天' eq loan.operationType }">天标  | ${loan.repayType}
					 	<c:choose>
						 <c:when test="${'是' eq loan.beforeRepay and not empty loan.symbol}"> | 可提前还款 ${loan.symbol}天</c:when>
						 <c:otherwise>
							 | 不可提前还款
						 </c:otherwise>
						 </c:choose>
					 </c:if>
					 <c:if test="${'月' eq loan.operationType }">月标 | ${loan.repayType} </c:if>
				 </td>
				
				 <td>				 
				 <c:if test="${'月' eq loan.operationType }">
							${loan.deadline }个月
						</c:if>
						<c:if test="${'天' eq loan.operationType }">
							<c:choose>
								<c:when test="${'是' eq loan.beforeRepay and not empty loan.symbol}">
									${loan.day + loan.symbol }天
								</c:when>
								<c:otherwise>
									${loan.day }天
								</c:otherwise>
							</c:choose>													
					</c:if>/
					<fmt:formatNumber value="${loan.ratePercent }" pattern="#,##0.##"/>%
				 </td>						 								 
				 <td>${loan.borrowMoneyUserID}<br>${loan.borrowMoneyUserName}</td>
				 <td><fmt:formatNumber value="${loan.totalmoney}" type="currency"/></td>
				 <td>${loan.status}</td>
				 <td><fmt:formatDate value="${loan.commitTime}" type="both"/></td>
				 <td><fmt:formatDate value="${loan.pushTime}" type="date"/></td>
				 <td>
				 	<a href="${ctx}/invest/investByLoan?loanId=${loan.id}">投资信息</a>
				 </td>
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>       	
                累计推送总金额：￥<c:if test="${netLoanModel.pushLoanSumMoney ge 10000}">
						<fmt:formatNumber value="${netLoanModel.pushLoanSumMoney / 10000}" maxFractionDigits="2"/><i>万</i>
					</c:if>
					<c:if test="${netLoanModel.pushLoanSumMoney lt 10000}">
						<fmt:formatNumber value="${netLoanModel.pushLoanSumMoney}" maxFractionDigits="2"/><i>元</i>
					</c:if> 
		累计推送项目个数：${netLoanModel.pushLoanNum }		
		天眼投资人数：${netLoan.investNum }
		天眼注册人数：${netLoan.regUserCount }		
        <%@ include file="../base/page2.jsp"%>
 </div>
 </div>
 </div>
 </div>
<script type="text/javascript">
//推送天眼项目
	function pushNetLoan(id){
	alert(id);
	return false;
		$.ajax({				
			type : 'POST',
			url : '/netLoanEye/pushNetLoan',
			data:{
				id:id
			},
			dataType:'json',
			success : function(data) {
				if( data=='success' ){
					alert("推送成功！");
					window.reload();
				}else{
					alert("推送失败，请重试！");
					window.reload();
				}
			},
			error:function(){
				alert("操作失败！");
				window.reload();
			}
		});
	}
</script>
 
</section>

</body>
</html>
