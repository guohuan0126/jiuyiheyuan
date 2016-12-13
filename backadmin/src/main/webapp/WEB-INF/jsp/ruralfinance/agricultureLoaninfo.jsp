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

<title>借款基本信息管理</title>

<style>
.parent{position:relative;}
.son{position:absolute;bottom:-58px;left:-86px;width:180px;background:#f7f7f7;border:1px solid #ccc;border-radius:3px;display:none;}
</style>
<script type="text/javascript">
	
	
	function getReason1(rule){
		$('#rule').dialogBox({
			width: 500,
			height: 300,
			title: '备注信息',
			hasClose: true,
			content: rule
		});
	}
	

</script>
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
		<script src="/js/jquery.dialogBox.js"></script>
	<link rel="stylesheet" href="/css/jquery.dialogbox.css" />
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：借款基本信息管理
				</h2>
			</div>
		<form class="form-inline" id="form1"  action="/ruralfinance/agricultureLoaninfo" method="post">		    	 
	     <div class="contentpanel">
	     	<input type="text" id="name" name="name" value="${id}" class="form-control" style="width:212px;display:inline-block;" placeholder="合同编号"></input>
	      <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="创建开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="创建结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
          <div class="input-group" >
  			       <span style="font-size: 15px">农贷类型：</span>
                 	<select id="agriculturalType" name="agriculturalType">
                 		<option value="">全部</option>
                 		<option value="三农">三农</option>
                 		<option value="及时贷">及时贷</option>
                 		<option value="韭农贷">韭农贷</option>
                 		<option value="惠牧贷">惠牧贷</option>
                 	</select>
          </div>
            <div class="input-group" >
  			<span style="font-size: 15px">状态：</span>
                 	<select id="Status" name="status">
                 		<option value="">全部</option>
                 		<option value="repay">还款中</option>
                 		<option value="finish">完成</option>
                 		<option value="after">逾期</option>
                 	</select>
                 </div>
               <div class="input-group" >
  			<span style="font-size: 15px">是否拆标：</span>
                 	<select id="forkStatus" name="forkStatus">
                 		<option value="">全部</option>
                 		<option value="1">是</option>
                 		<option value="0">否</option>
                 	</select>
                 </div>
            <div class="input-group" >
  			<span style="font-size: 15px">是否打包：</span>
                 	<select id="packingStatus" name="packingStatus">
                 		<option value="">全部</option>
                 		<option value="1">是</option>
                 		<option value="0">否</option>
                 	</select>
            </div>
               <div class="input-group" >
					<span style="font-size: 20px">是否生成还款计划：</span>
             	<select id="flag" name="flag">            	
             		<option value="">全部</option>
             		<option value="0">未生成</option>
             		<option value="1">已生成</option>
             	</select>
             </div>
                <div class="input-group" >
  			<span style="font-size: 15px">期限：</span>
                 	<input id="loanTerm" name="loanTerm" style="width: 60px" value="${loanTerm}">
                 </div>
                <input type="submit" id="queryAll" class="btn btn-primary"  style="display:inline-block;" value="查询"></input><br>
          <%--   <base:active activeId="AGRICULTURAL_BATCH_FORK_LOANS"> --%>    <input type="button" id="query" class="btn btn-primary"  onclick="forkLoans()" style="display:inline-block;" value="批量拆标"></input>   <%-- </base:active> --%> 
           <%--  <base:active activeId="AGRICULTURAL_REPAYMENTPLAN"> --%>  <input type="button" id="query" class="btn btn-primary"  onclick="repayment()" style="display:inline-block;" value="生成还款计划"></input>  <%-- </base:active>    --%>
	       <%--  <base:active activeId="AGRICULTURAL_MODIFY_LOANSTATUS">   --%>
	     		 <div class="input-group" >
  			<span>可修改的状态：</span>
                 	<select id="loanStatus" name="loanStatus">
                 		<option value="repay">还款中</option>
                 		<option value="finish">完成</option>
                 		<option value="after">逾期</option>
                 	</select>
                 </div>
	     		 <input type="button" id="query" class="btn btn-primary"  onclick="updateStatus()" style="display:inline-block;" value="批量修改状态"></input>
	     <%--  </base:active> --%>
	   <%--   <base:active activeId="AGRICULTURAL_Export_White_list">  --%>
	     	 <input type="button" id="exportLoanerinfo" class="btn btn-primary"   style="display:inline-block;" value="导出中金白名单" />
		<%--  </base:active> --%>   <br>
	     	   <div class="input-group" >
                <input type="text" class="form-control"  name="fkstart" value="${fkstart}" placeholder="放款开始时间" id="datepickerfk">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control"   name="fkend" value="${fkend}" placeholder="放款结束时间" id="datepickerfk1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                 <div class="input-group" >
  			       <span style="font-size: 15px">来源：</span>
                 	<select id="remark1" name="remark1">
                 		<option value="">全部</option>
                 		<option value="excel">excel</option>
                 		<option value="system">信贷系统</option>
                 	</select>
                 </div>
                <div class="input-group" >
  			       <span style="font-size: 15px">核算公司：</span>
                 	<select id="accountingDepartment" name="accountingDepartment">
                 		<option value="">全部</option>
                 		<option value="1">山水普惠</option>
                 		<option value="2">久亿财富</option>
                 	</select>
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
           <base:active activeId="AGRICULTURAL_Export_BorrowerInfo">  
              <input type="button" id="agriculturalexport" class="btn btn-primary"  style="display:inline-block;" value="导出" />  
           </base:active> 
	<div style="padding:0 30px;margin-top: 15px;" >
	<span style="font-size:14px;color:red;line-height:27px;">1.批量拆标就是把下面单个借款项目信息拆分成1-12个月的小标的，其中1-6月的标发布成定期项目，7-12月的打包成天天赚，单选多选全选进行拆标。</br>
	2.根据借款项目的借款金额，借款期限和借款利率等生成针对该项目的等额本息的还款计划，并且还款计划的期数是借款期限的月份数，根据生成的还款计划进行相应扣款。<br>
	3.批量修改项目状态，根据具体项目借款期限到期还款情况相应的修改该借款项目还款状态是逾期还是已还完款修改成完成状态。<br>
	4.导出中金白名单要先选择放款开始时间和结束时间并且核算公司为山水的进行查询，然后点击导出中金白名单按钮就会导出你选择冒个时间段的名单，下次导出要把上次的结束时间作为开始时间进行查询再导出，这样避免名单重复。</span>
	</div>
	     </div>
	     
	          </form>
			<script type="text/javascript">	
			$("#flag").val('${flag}');
			$("#Status").val('${status}');
			$("#forkStatus").val('${forkStatus}');
			$("#remark1").val('${remark1}');
			$("#accountingDepartment").val('${accountingDepartment}');
			$("#repayType").val('${repayType}');
			$("#whetherEarlyRepayment").val('${whetherEarlyRepayment}');
			$("#packingStatus").val('${packingStatus}');
			$("#agriculturalType").val('${agriculturalType}');			
			jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});
			jQuery('#datepickerfk').datepicker({dateFormat: 'yy-mm-dd'});				
			jQuery('#datepickerfk1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
					<div class="panel-body">
					<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
								<th><input name="checkall" type="checkbox" id="checkall"  style="vertical-align:sub;"/>全选
									<th>合同编号</th>
									<th>姓名</br>性别</br>年龄</th>
									<th>
									证件类型</br>
									身份证号</br>手机号</th>
									<th>借款金额(合同金额)</th>
									<th>打款金额</th>
									<th>服务费用</th>
									<th>借款期限(月)</th>
									<th>利率/</br>
									综合利率</th>
									<th>还款方式</th>
									<th>银行卡号</th>
									<th>所属银行</th>
									<th>支行名称</th>
									<th>婚姻状况</th>
									<th>地址</th>
									<th>年收入</th>
									<th>核算公司</th>
									<th>创建时间</th>
									<th>放款时间</th>
									<th>操作人员编号</th>
									<th>状态</th>									
									<th>拆标状态</th>
									<th>还款计划</th>
									<th>打包状态</th>
									<th>是否提前还款</th>
									<th>实际借款期限</th>
									<th>实际结束日期</th>									
									<th>详细信息</th>
									<th>备注</th>									
									<th>子标信息</th>
									
								</tr>
							</thead>
								<c:forEach var="agricultureLoaninfo" items="${pageInfo.results}">
								<tr>
								<td><input name="checkname" type="checkbox" value="${agricultureLoaninfo.id}"/></td>
								   <td>${agricultureLoaninfo.contractId}</td>
								    <td>${agricultureLoaninfo.name}</br>
								    <c:if test="${agricultureLoaninfo.sex==0}">男</c:if>
								    <c:if test="${agricultureLoaninfo.sex==1}">女</c:if>
								    ${agricultureLoaninfo.age}
								    </td>
								    <td>
								    ${agricultureLoaninfo.typeOfId}
									</br>
									    ${agricultureLoaninfo.idCard}
								    </br>
								     ${agricultureLoaninfo.mobileNumber}
								    </td>
								    <td>${agricultureLoaninfo.money}</td>
								    <td>${agricultureLoaninfo.loanMoney}</td>
								    <td>${agricultureLoaninfo.serviceMoney}</td>
								    <td>${agricultureLoaninfo.loanTerm}个月</td>
								    <td>${agricultureLoaninfo.rate}/</br>
								    ${agricultureLoaninfo.compositeInteresRate}</td>
								    <td>${agricultureLoaninfo.repayType}</td>
								    <td>
								     ${agricultureLoaninfo.bankcard}
								   </td>
								    <td>${agricultureLoaninfo.bank}</td>
								    <td>${agricultureLoaninfo.branchname}</td>
								    <td>
								    	<c:if test="${agricultureLoaninfo.maritalStatus==0}">未婚</c:if>
										<c:if test="${agricultureLoaninfo.maritalStatus==1}">已婚</c:if>
										<c:if test="${agricultureLoaninfo.maritalStatus==2}">离异</c:if>
										<c:if test="${agricultureLoaninfo.maritalStatus==3}">丧偶</c:if>
								    </td>
								    <td><%-- ${agricultureLoaninfo.province}${agricultureLoaninfo.city}${agricultureLoaninfo.area} --%>${agricultureLoaninfo.address}</td>
								    <td>${agricultureLoaninfo.annualIncome}</td>
								     <td>
								    	<c:if test="${agricultureLoaninfo.accountingDepartment==1}">山水</c:if>
										<c:if test="${agricultureLoaninfo.accountingDepartment==2}">久亿</c:if>
										 </td>
								    <td>
								    <fmt:formatDate value="${agricultureLoaninfo.createTime}" type="both"/></td>
								    <td>
								    <fmt:formatDate value="${agricultureLoaninfo.giveMoneyTime}" type="both"/>
								   </td>
								    <td>${agricultureLoaninfo.userId}</td>
								    <td>
								    <c:if test="${agricultureLoaninfo.status=='repay'}">还款中</c:if>
								     <c:if test="${agricultureLoaninfo.status=='finish'}">完成</c:if>
								      <c:if test="${agricultureLoaninfo.status=='after'}">逾期</c:if>
								    </td>
								    <td>
								    <c:if test="${agricultureLoaninfo.forkStatus==0}">未拆</c:if>
								    <c:if test="${agricultureLoaninfo.forkStatus==1}">已拆</c:if>
								    </td>
								    <td>
								    <c:if test="${agricultureLoaninfo.flag==0}">未生成</c:if>
								    <c:if test="${agricultureLoaninfo.flag==1}">已生成</c:if>
								    </td>
								     <td>
								    <c:if test="${agricultureLoaninfo.packingStatus=='0'}">未打包</c:if>
								    <c:if test="${agricultureLoaninfo.packingStatus=='1'}">已打包</c:if>
								    </td>
								     <td>
								    <c:if test="${agricultureLoaninfo.whetherEarlyRepayment==0}">否</c:if>
								    <c:if test="${agricultureLoaninfo.whetherEarlyRepayment=='1'}">是</c:if>
								    </td>
								        <td>${agricultureLoaninfo.actualLoanTerm}  </td>
								          <td> <fmt:formatDate value="${agricultureLoaninfo.actualEndTime}" type="both"/> </td>
								   <!-- <th></th>
									<th>营业部</th>
									<th>客户经理</th>
									<th>县审批人</th>
									<th>总部审批人</th> -->
								    <td><a href="javascript:getReason1('大区:${agricultureLoaninfo.bigArea}</br>
								    	营业部:${agricultureLoaninfo.businessOffic}</br>
								    	客户经理:${agricultureLoaninfo.customManger}</br>
								    	县审批人:${agricultureLoaninfo.countryApprover}</br>
								    	总部审批人:${agricultureLoaninfo.allApprover}');">详细信息</a>
								    	
								    </td>
								    <td>
								    <a href="javascript:getReason1('${agricultureLoaninfo.remark}');">备注</a>
								    </td>
								    <td><c:if test="${agricultureLoaninfo.forkStatus==1}"><a href="/ruralfinance/childLoansListById?id=${agricultureLoaninfo.id}">子标信息</a></c:if></td>
								</tr>
							</c:forEach>	
						</table>
					</div>
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
					
					
					function updateStatus(){
						if(confirm('确认修改项目状态吗?')){		
							var status=$("#loanStatus").val();
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/ruralfinance/updateLoans",						
								data: {"ids":checkLoan(),
										"status":status},
								success:function(data) {
									if(data=='success'){
										alert("批量修改状态成功");
										window.location.href="/ruralfinance/agricultureLoaninfo";
									}else{
										alert("批量修改状态失败");
										window.location.href="/ruralfinance/agricultureLoaninfo";
									}
								}
							});	
						}
						
					}
					
					
					
					
					function forkLoans(){	
						var arr=checkLoan();
						if(arr.length==0){
							alert("请勾选要拆标的项目");
							return false;
						}						
						if(confirm('确认对项目进行批量拆标吗?')){			
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/ruralfinance/forkLoans",						
								data: {"ids":checkLoan()},
								success:function(data) {
									if(data=='success'){
										alert("批量拆标成功");
										window.location.href="/ruralfinance/agricultureLoaninfo";
									}else{
										alert("拆标失败");
										window.location.href="/ruralfinance/agricultureLoaninfo";
									}
								}
							});	
						}
					}
					function repayment(){	
						var arr=checkLoan();
						if(arr.length==0){
							alert("请勾选要生成还款计划的项目");
							return false;
						}						
						if(confirm('确认对项目进行生成还款计划吗?')){			
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/ruralfinance/BatchLoanManagement",						
								data: {"ids":checkLoan()},
								success:function(data) {
									if(data=='success'){
										alert("还款计划生成成功");
										window.location.href="/ruralfinance/agricultureLoaninfo";
									}else{
										alert("还款计划生成失败");
										window.location.href="/ruralfinance/agricultureLoaninfo";
									}
								}
							});	
						}
					}	
					//导出的方法
					function exportLoanerinfo(){						
						window.location.href="/ruralfinance/exportLoanerinfo";
					}
					$("#exportLoanerinfo").click(function(){
					    	$("#form1").attr("action","/ruralfinance/exportLoanerinfo");
		           		    $("#form1").submit();
		            });
				    $("#agriculturalexport").click(function(){
				    	$("#form1").attr("action","/ruralfinance/agriculturalexport");
	           		    $("#form1").submit();
	              });	
				    $("#queryAll").click(function(){
				    	$("#form1").attr("action","/ruralfinance/agricultureLoaninfo");
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


</body>
</html>


