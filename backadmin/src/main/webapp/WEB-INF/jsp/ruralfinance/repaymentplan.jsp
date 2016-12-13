<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="${ctx}/images/favicon.png"
	type="image/png">

<title>还款记录列表</title>
<style type="text/css">
.zhezhao{width:100%;height:100%;background-color:rgba(0,0,0,0.6); position:fixed;top:0;left:0;z-index:101;}
</style>
</head>
<body>
	<!-- Preloader -->
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：还款记录列表
				</h2>
			</div>
		<form class="form-inline" id="form1"  action="/ruralfinance/repaymentplanList" method="post">		    	 
	     <div class="contentpanel">
	     	<input type="text" id="id" name="id" value="${id}" class="form-control" style="width:212px;display:inline-block;" placeholder="合同编号"/>
            <input type="text" id="name" name="name" value="${name}" class="form-control" style="width:212px;display:inline-block;" placeholder="借款人姓名">
          <div class="input-group" >
                <input type="text" class="form-control"  name="start" value="${start}" placeholder="还款开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
          </div> 
          <div class="input-group" >
                <input type="text" class="form-control"   name="end" value="${end}" placeholder="还款结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
          </div>
            <div class="input-group" >
  			       <span style="font-size: 15px">还款方式：</span>
                 	<select id="repayType" name="repayType">
                 		<option value="">全部</option>
                 		<option value="等额本息">等额本息</option>
                 		<option value="先息后本">先息后本</option>
                 	</select>
              </div>
             <div class="input-group" >
  			<span style="font-size: 15px">是否提前还款：</span>
                 	<select id="whetherEarlyRepayment" name="whetherEarlyRepayment">
                 		<option value="">全部</option>
                 		<option value="1">是</option>
                 		<option value="0">否</option>
                 	</select>
            </div>   
         <div class="input-group" >
  			<span style="font-size: 15px">还款状态：</span>
                 	<select id="repayStatus" name="repayStatus">
                 		<option value="">全部</option>
                 		<option value="unrepay">未还</option>
                 		<option value="repayfail">还款失败</option>
                 		<option value="finish">还清</option>
                 	</select>
            </div>   
            <input type="submit" id="queryAll" class="btn btn-primary"  style="display:inline-block;" value="查询"/><br>
    <base:active activeId="AGRICULTURAL_USER_EXPROT"> 
         <span style="margin-left:5px;font-size: 15px">生成还款明细截止日期：</span> 
         <input type="text" class="form-control"  name="deadlineTime" value="${deadlineTime}" placeholder="还款明细截止时间" id="datepickerDeadline">
         <input type="button" id="update" class="btn btn-primary"  onclick="updateMoney()" style="display:inline-block;" value="更新逾期罚息"/>
           <input type="button" id="export" class="btn btn-primary"  onclick="exportRepaymentplan()" style="display:inline-block;" value="生成今天还款明细" />  
           <input type="button" id="export" class="btn btn-primary"  onclick="exportTimeRepaymentplan('early')" style="display:inline-block;" value="生成提前还款明细" />  
           <input type="button" id="export" class="btn btn-primary"  onclick="exportTimeRepaymentplan('normal')" style="display:inline-block;" value="生成正常还款明细" />
           <input type="button" id="export" class="btn btn-primary"  onclick="exportTimeRepaymentplan('late')" style="display:inline-block;" value="生成逾期还款明细" />    
    </base:active>
	  <base:active activeId="AGRICULTURAL_MODIFY_REPAY_STATUS">  <span>可修改的状态：</span>
                 	<select id="loanStatus" name="loanStatus">
                 		<option value="unrepay">未还</option>
                 		<option value="repayfail">还款失败</option>
                 		<option value="finish">还清</option>
                 	</select>
          
	     	<input type="button" id="updateStatus" class="btn btn-primary" style="display:inline-block;" value="批量修改状态" />
   </base:active> 
           <br>
          <div class="input-group" style="margin-top: 24px;" >
                <input type="text" class="form-control"  name="fkstart" value="${fkstart}" placeholder="放款开始时间" id="datepickerfk">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" style="margin-top: 24px;">
                <input type="text" class="form-control"   name="fkend" value="${fkend}" placeholder="放款结束时间" id="datepickerfk1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                <div class="input-group" style="margin-top: 24px;">
  			       <span style="font-size: 15px">核算公司：</span>
                 	<select id="accountingDepartment" name="accountingDepartment">
                 		<option value="">全部</option>
                 		<option value="1">山水普惠</option>
                 		<option value="2">久亿财富</option>
                 	</select>
                 </div>
              <input type="button" id="agriculturalexport" class="btn btn-primary"  style="display:inline-block;" value="导出还款计划" />   
    	<div style="padding:0 30px;margin-top: 15px;" >
	<span style="font-size:14px;color:red;line-height:27px;">1.更新逾期罚息根据项目应该还款日期和当前时间进行判断如果当前时间大于应该还款时间说明已经逾期了就根据相差的天数计算出逾期罚息，如果当前时间小于应还款时间就说明没有
就没有罚息。</br>
	2.导出要先点击更新逾期罚息，然后才能导出，以确保导出的还款计划信息是最新的。<br>
	3.批量修改还款计划的还款状态，根据还款计划到期还款情况修改该期还款计划的还款状态是未还，还款失败，还是还清。<br>
	4.生成今天还款明细指生成到今天之前没有还清的还款计划，扣款成功的项目不用生成，并且扣款失败的项目要重新生成明细号，根据重新生成的明细号到中金那边扣款并把扣款结果更新过来。
	</span>
	</div>
	     </div>
	          </form>
	         
	         <script type="text/javascript">
	            $("#accountingDepartment").val('${accountingDepartment}');
				$("#repayType").val('${repayType}');
				$("#whetherEarlyRepayment").val('${whetherEarlyRepayment}');
				$("#repayStatus").val('${repayStatus}');
	         function updateMoney(){
	        	 if(confirm('确认更新还款信息吗?')){		
	        		 $("#show").show();
	        		 var deadlineTime=$("#datepickerDeadline").val();	
	        		 $.ajax({
							type : 'POST',
							dataType:'text',
							url : "/ruralfinance/updateRepaymentplanList",
							data: {"deadlineTime":deadlineTime},
							success:function(data) {
								if(data=='success'){
									alert("更新还款信息成功");
									window.location.href="/ruralfinance/repaymentplanList";
									$("#show").hide();
								}else{
									alert("更新还款信息失败");
									window.location.href="/ruralfinance/repaymentplanList";
									$("#show").hide();
								}
							}
						});	
					}
			}
	         </script>
	          <script type="text/javascript">	
			  jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			  jQuery('#datepickerfk').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepickerfk1').datepicker({dateFormat: 'yy-mm-dd'});
			 jQuery('#datepickerDeadline').datepicker({dateFormat: 'yy-mm-dd'});
			</script>
					<div class="panel-body">

						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
								<th><input name="checkall" type="checkbox" id="checkall"  style="vertical-align:sub;"/>全选
									<th>借款项目编号</th>
									<th>还款人姓名</th>
									<th>还款编号</th>
									<th>总合同编号</th>
									<th>明细号</th>
									<th>还款方式</th>
									<th>是否提前还款</th>
									<th>还款日期（应还日期）</th>
									<th>实际还款日期</th>
									<th>每月应还金额</th>
									<th>每月实际还金额</th>
									<th>应还本金</th>
									<th>应还利息</th>
									<th>逾期罚息</th>
									<th>期数（第几期还款）</th>
									<th>服务费</th>
									<th>退还服务费</th>
									<th>减免本金</th>
									<th>减免利息</th>
								    <th>状态</th>
									<th>备注</th>
									<th>操作</th>
								</tr>
							</thead>
								<c:forEach var="AgricultureRepaymentPlan" items="${pageInfo.results}">
								<tr>
								   <td><input name="checkname" type="checkbox" value="${AgricultureRepaymentPlan.id}"/></td>
								   <td>${AgricultureRepaymentPlan.uid}</td>
									<td>${AgricultureRepaymentPlan.name}</td>
									<td>${AgricultureRepaymentPlan.id}</td>	
									<td>${AgricultureRepaymentPlan.contractId}</td>	
									<td>${AgricultureRepaymentPlan.detailsNumber}</td>	
									<td>${AgricultureRepaymentPlan.repayType}</td>	
									<td> <c:if test="${AgricultureRepaymentPlan.whetherEarlyRepayment==0}">否</c:if>
								       <c:if test="${AgricultureRepaymentPlan.whetherEarlyRepayment==1}">是</c:if></td>	
									<td>
									<fmt:formatDate value="${AgricultureRepaymentPlan.repayDate}" type="both"/>
									</td>	
									<td>
									<fmt:formatDate value="${AgricultureRepaymentPlan.operationTime}" type="both"/>
									</td>	
									<td>${AgricultureRepaymentPlan.monthMoney}</td>	
									<td>${AgricultureRepaymentPlan.realrepayMoney}</td>	
									<td>${AgricultureRepaymentPlan.corpus}</td>	
									<td>${AgricultureRepaymentPlan.intereat}</td>
									<td>${AgricultureRepaymentPlan.latePenalty}</td>
									<td>${AgricultureRepaymentPlan.period}</td>
									<td>${AgricultureRepaymentPlan.serviceFee}</td>
									<td>${AgricultureRepaymentPlan.returnMoney}</td>
									<td>${AgricultureRepaymentPlan.remitCorpus}</td>
									<td>${AgricultureRepaymentPlan.remitIntereat}</td>
									<td>
									<c:if test="${AgricultureRepaymentPlan.repayStatus=='unrepay'}">未还</c:if>
									<c:if test="${AgricultureRepaymentPlan.repayStatus=='repayfail'}">还款失败</c:if>
									<c:if test="${AgricultureRepaymentPlan.repayStatus=='finish'}">还清</c:if>
									</td>	
									<td>
										${AgricultureRepaymentPlan.remark}
									</td>		 
									<td>
									<%-- <base:active activeId="778292152f034308baefc04652b10075"> --%>
									<%-- <c:if test="${AgricultureRepaymentPlan.repayStatus=='unrepay' or AgricultureRepaymentPlan.repayStatus=='repayfail' }">	 --%>								
									<a style="cursor:pointer" href="/ruralfinance/toeditRepaymentplan?repayId=${AgricultureRepaymentPlan.id}">修改</a>
									<%-- </c:if> --%>
									<%-- </base:active>	 --%>
									</td>									
								</tr>
							</c:forEach>	
						</table>
					</div>
				<script type="text/javascript">
				
				function exportRepaymentplan(){
					if(confirm('确认已更新最新借款数据?')){	
					 var deadlineTime=$("#datepickerDeadline").val();							
					window.location.href="/ruralfinance/exportRepaymentplanList?deadlineTime="+deadlineTime;
				}
				}
				function exportTimeRepaymentplan(id){
					if(confirm('确认更新吗?')){							
					window.location.href="/ruralfinance/exportTimeRepaymentplanList/"+id;
				}
				}		
				</script>
				<script type="text/javascript">
				$("#checkall").click(function(){ 
					 if(this.checked){ 
					        $("input:checkbox[name=checkname]").attr('checked', true);
					    }else{ 
					        $("input:checkbox[name=checkname]").attr('checked', false);
					    } 
			  });
				//获取选中的id值
				function checkLoan(){
					var arr = new Array();
					$("input:checkbox[name=checkname]:checked").each(function(i){						
						  arr.push($(this).val());
					}); 
					return arr;
				}
				
				$("#updateStatus").click(function(){ 
					var arr=checkLoan();
					if(arr.length==0){
						alert("请选中需要修改状态的记录");
						return false;
					}
					if(confirm('确认修改还款状态吗?')){		
						 $("#show").show();
						 var status=$("#loanStatus").val();
						$.ajax({
							type : 'POST',
							dataType:'text',
							url : "/ruralfinance/updateRepaymentplanStatusList",						
							data: {"ids":checkLoan(),
									"status":status},
							success:function(data) {
								if(data=='success'){
									alert("批量修改状态成功");
									window.location.href="/ruralfinance/repaymentplanList";
									$("#show").hide();
								}else{
									alert("批量修改状态失败");
									window.location.href="/ruralfinance/repaymentplanList";
									$("#show").hide();
								}
							}
						});	
					}
					
				});
			    $("#agriculturalexport").click(function(){
			    	$("#form1").attr("action","/ruralfinance/repaymentPlanexport");
           		    $("#form1").submit();
              });
			    $("#queryAll").click(function(){
			    	$("#form1").attr("action","/ruralfinance/repaymentplanList");
           		    $("#form1").submit();
              });
				</script>
					<%@ include file="../base/page.jsp"%>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>

<div class="zhezhao" id="show" style="display: none;"></div>
</body>
</html>


