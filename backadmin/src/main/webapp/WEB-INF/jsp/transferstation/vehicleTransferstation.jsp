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

<title>中转站信息管理</title>

<style>
.parent{position:relative;}
.son{position:absolute;bottom:-58px;left:-86px;width:180px;background:#f7f7f7;border:1px solid #ccc;border-radius:3px;display:none;}
.qudao-tc{width:500px;height:180px;background-color:#fff;border:1px solid #333;color:#333;font-size:14px;padding:20px 0;position:absolute;top:300px;left:50%;margin-left:-250px;}
.qudao-dq-info{padding-left:220px;}
.qudao-dq-info div{padding-top:7px;}
.qudao-button{padding-top:15px;text-align:center;}
.qudao-button1,.kesjg-qx{width:80px;height:35px;text-align:center;display:inline-block;color:#000;background-color:#ddd;line-height:35px;border:none;}
.qudao-button2,.kesjg-qd{width:80px;height:35px;text-align:center;display:inline-block;color:#000;background-color:#528FD5;line-height:35px;border:none;}
.kesjg-qx{margin-right:10px;}
.ktsjg-tc{z-index:3;width:400px;height:140px;background-color:#fff;border:1px solid #333;color:#333;font-size:14px;padding:20px 0;position:absolute;top:300px;left:50%;margin-left:-200px;}
.ktsjg-input{border:1px solid #333;width:170px;}
.tuisong{cursor:pointer;color:#428bca;}

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
					<i class="fa fa-table"></i>当前位置：中转站信息管理
				</h2>
			</div>
		<form class="form-inline" id="form1"  action="/transferstation/vehicleTransferstation" method="post">		    	 
	     <div class="contentpanel">
	     	<input type="text" id="contractId" name="contractId" value="${contractId}" class="form-control" style="width:180px;display:inline-block;" placeholder="合同编号"></input>
	      	<input type="text" id="borrowerName" name="borrowerName" value="${borrowerName}" class="form-control" style="width:150px;display:inline-block;" placeholder="借款人"></input>
	      <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="创建开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
          </div> 
           <div class="input-group" >
           <input type="text" class="form-control" name="end" value="${end}" placeholder="创建结束时间" id="datepicker1">
           <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
           </div>
           <div class="input-group" >
              	<select id="repayType" name="repayType">
              		<option value="">还款方式</option>
              		<option value="等额本息">等额本息</option>
              		<option value="先息后本">先息后本</option>
              	</select>
           </div> 
          <div class="input-group" >
              	<select id="accountingDepartment" name="accountingDepartment">
              		<option value="">核算公司</option>
              		<option value="1">久亿</option>
              		<option value="0">齐海</option>
              	</select>
           </div>   
         <div class="input-group" >
              	<select id="onlineBorrower" name="onlineBorrower">
              		<option value="">线上借款人</option>
              		<option value="EBFZvmiaMrAfjswz">覃仕东</option>
              		<option value="mMZbEzYFzIrehguk">郑卉芳</option>
              		<option value="ceh1234">张凯</option>              		
              	</select>
           </div>              
            <div class="input-group" >
                 	<select id="channelName" name="channelName">
                 		<option value="">渠道分配</option>
                 		<option value="定期">定期</option>
                 		<option value="活期">活期</option>
                 		<option value="机构线上">机构线上</option>
                 		<option value="机构线下">机构线下</option>
                 		<option value="线下">线下</option>
                 	</select>
                 </div>
          <div class="input-group" >
                 	<select id="guaranteeType" name="guaranteeType">
                 		<option value="">担保方式</option>
                 		<option value="A">质押</option>
                 		<option value="BandC">抵押</option>
                 	</select>
                 </div>
       <div class="input-group" >
                 	<select id="remarkVehicle" name="remarkVehicle">
                 		<option value="">新增/展期</option>
                 		<option value="A">新增</option>
                 		<option value="BandD">展期</option>
                 	</select>
       </div>       
            <div class="input-group" >
  			<span style="font-size: 15px">是否分配：</span>
                 	<select id="allocationStatus" name="allocationStatus">
                     <option value="3">全部</option>
                        <option value="0">否</option>
                 		<option value="1">是</option>                 		
                 	</select>
            </div>

                <div class="input-group" >
                <input type="text" id="deadline" name="deadline" value="${deadline}" class="form-control" style="width:100px;display:inline-block;" placeholder="借款期限"></input>
                 </div>
               <div class="input-group" >  		
                 	<input type="text" id="institutionName" name="institutionName" value="${institutionName}" class="form-control" style="width:120px;display:inline-block;" placeholder="机构名称"></input>
                 </div>
                <div class="input-group" >  		
                 	<input type="text" id="itemAddress" name="itemAddress" value="${itemAddress}" class="form-control" style="width:120px;display:inline-block;" placeholder="项目地点"></input>
                 </div>
              <div class="input-group" >
               <input type="text" id="remark" name="remark" value="${remark}" class="form-control" style="width:120px;display:inline-block;" placeholder="备注"></input>
                 </div>
                <br>             	     	
		   <br>
              <input type="submit" id="queryAll" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>    
              <input type="button" id="allocation" class="btn btn-primary"  style="display:inline-block;" value="分配"></input>    	     	
	         <input type="button" id="exportInterMed" class="btn btn-primary"   style="display:inline-block;" value="导出" />
	          <label style="font-size: 20px;padding-left: 20px;">打包资产总金额：</label> <span style="font-size: 20px;color: red;"
									id="totalMoney"></span>			 
			  <input name="LoandataIds" id="LoandataIds" type="hidden" />
			  <input name="totalMoney" id="totalMoney2" type="hidden" />	
			  <input name="deadlineqf" id="deadlineqf" type="hidden" />	
			  <input name="company" id="company" type="hidden" />	
			  <input name="borrowerId" id="borrowerId" type="hidden" />	
	    
<!-- 	<div style="padding:0 30px;margin-top: 15px;" >
	<span style="font-size:14px;color:red;line-height:27px;">1.批量拆标就是把下面单个借款项目信息拆分成1-12个月的小标的，其中1-6月的标发布成定期项目，7-12月的打包成天天赚，单选多选全选进行拆标。</br>
	2.批量修改项目状态，根据具体项目借款期限到期还款情况相应的修改该借款项目还款状态是逾期还是已还完款修改成完成状态。<br>
	3.导出中金白名单要先选择放款开始时间和结束时间并且核算公司为山水的进行查询，然后点击导出中金白名单按钮就会导出你选择冒个时间段的名单，下次导出要把上次的结束时间作为开始时间进行查询再导出，这样避免名单重复。</span>
	</div> -->
	     </div>
	     
	          </form>
			<script type="text/javascript">	
			$("#repayType").val('${repayType}');
			$("#accountingDepartment").val('${accountingDepartment}');
			$("#onlineBorrower").val('${onlineBorrower}');
			$("#channelName").val('${channelName}');
			$("#allocationStatus").val('${allocationStatus}');
			$("#guaranteeType").val('${guaranteeType}');
			$("#remarkVehicle").val('${remarkVehicle}');
			jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});
			</script>
					<div class="panel-body">
					<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
								<th><input name="checkall" type="checkbox" id="checkall"  style="vertical-align:sub;"/>全选</th>
									<th>合同编号</th>
									<th>项目类型</th>
									<th>还款方式</th>
									<th>借款人/身份证号</th>
									<th>借款金额</th>
									<th>放款金额</th>
									<th>借款期限</th>									
									<th>分期/全款</th>
									<th>质押/抵押</th>
									<th>车牌号</th>
									<th>评估价格</th>
									<th>核算公司</th>	
									<th>项目地点</th>
									<th>新增展期</th>									
									<th>线上借款人</th>
									<th>创建时间</th>		
									<th>分配状态</th>						
									<th>分配渠道</th>																		
									<th>机构名称</th>
								    <th>可推送机构</th>
								    <th>打包状态</th>
									<th>操作</th>																		
								</tr>
							</thead>
								<c:forEach var="transferStation" items="${pageInfo.results}">
								<tr <c:if test="${transferStation.organizationRemark!='' and transferStation.organizationRemark!=null}">style="color:red;"</c:if>>
								<td><input name="checkname" type="checkbox" value="${transferStation.id}" status="${transferStation.allocationStatus}" 
								money="${transferStation.money}" repayType="${transferStation.repayType}" contractId="${transferStation.contractId}" 
								deadline="${transferStation.deadline}" LoanRemark="${transferStation.borrowerId}" department="${transferStation.accountingDepartment}"/></td>
								   <td>${transferStation.contractId}</td>
								    <td>${transferStation.loanType}</td>
								    <td>${transferStation.repayType}</td>
								    <td>   ${transferStation.vehicleInfo.borrowerName}</br>
									     ${transferStation.vehicleInfo.borrowerIdCard}								    
								    </td>
								    <td>${transferStation.money}</td>
								    <td>${transferStation.loanMoney}</td>
								    <td>${transferStation.deadline}${transferStation.operationType}</td>
								    <td>${transferStation.vehicleInfo.yaCarAndGPS} </td>
								    <td><c:choose>
								    <c:when test="${transferStation.vehicleInfo.guaranteeType=='A'}">质押</c:when>
								    <c:otherwise>抵押</c:otherwise>
								    </c:choose>								    
								    </td>
								    <td>${transferStation.vehicleInfo.licensePlateNumber}</td>
								    <td>${transferStation.vehicleInfo.assessPrice} </td>
								    <td><c:if test="${transferStation.accountingDepartment==0}">齐海</c:if>
										<c:if test="${transferStation.accountingDepartment==1}">久亿</c:if></td>
								    <td>${transferStation.vehicleInfo.itemAddress} </td>
								    <td><c:choose>
								    <c:when test="${transferStation.vehicleInfo.remark=='A'}">新增</c:when>
								    <c:otherwise>展期</c:otherwise>
								    </c:choose>	
								    </td>
								    <td><c:if test="${transferStation.borrowerId=='EBFZvmiaMrAfjswz'}">覃仕东</c:if>
								    <c:if test="${transferStation.borrowerId=='mMZbEzYFzIrehguk'}">郑卉芳</c:if>
								    <c:if test="${transferStation.borrowerId=='ceh1234'}">张凯</c:if>
								    </td>
								    <td><fmt:formatDate value="${transferStation.createTime}" type="both"/></td>
								     <td>
								    	<c:if test="${transferStation.allocationStatus==0}">未分配</c:if>
										<c:if test="${transferStation.allocationStatus==1}">已分配</c:if>
									</td>						
								    <td>${transferStation.channelName}</td>
								    <td>${transferStation.institutionName}</td>
								    <td>${transferStation.organizationRemark}</td>
								     <td>
								    <c:if test="${transferStation.allocationStatus==0}">未打包</c:if>
								    <c:if test="${transferStation.allocationStatus==1}">已打包</c:if>
								    </td>														    
								    <td> 
								    <!-- <span class="tuisong"> --><!-- </span> -->
								    <a href="javascript:tuisong('${transferStation.id}');">可推送机构</a>/
								    <c:if test="${transferStation.allocationStatus==0}">
								    	<a href="javascript:del('${transferStation.id}');">删除</a>
								    </c:if>
								     <c:if test="${transferStation.allocationStatus==1&&transferStation.channelName=='机构线下'||transferStation.channelName=='线下'}">
								    	<a href="javascript:release('${transferStation.id}');">资产释放</a>
								     </c:if>
								    </td>
								</tr>
							</c:forEach>	
						</table>
					</div>
					<script type="text/javascript">
					function release(id){
						  if(confirm("您确定要释放资产吗？")){ 
							  $.ajax({
									type : "POST",
									url : '/transferstation/releaseTransferLoan',
									data : {id:id},// 你的formid
									success : function(data) {
										if (data == 'ok') {
											alert('操作成功');
											window.location = "/transferstation/vehicleTransferstation";
										}else{
											alert('操作失败');
										}
									}
								});
						  } 
					}
					function del(id){
						  if(confirm("您确定要删除吗？")){ 
							  $.ajax({
									type : "POST",
									url : '/transferstation/delTransferLoan',
									data : {id:id},// 你的formid
									success : function(data) {
										if (data == 'ok') {
											alert('操作成功');
											window.location = "/transferstation/vehicleTransferstation";
										}else{
											alert('操作失败');
										}
									}
								});
						  } 
					}
					function tuisong(id){
						$(".ktsjg-tc").show();	
						$("#transactionId").val(id);																							
					}
					//初始化页面金额
					function noChoose(){				
						var arr2 = $("input:checkbox[name=checkname]:checked");	 				
						if(arr2.length <= 0)
						{ $("#totalMoney2").val("0.0");
						$("#totalMoney").html("￥0.0");
						$("#LoandataIds").attr("value", "");
						$("#deadlineqf").attr("value", $("#deadline").val());
						$("#company").attr("value", $("#accountingDepartment").val());
						} 	
					}
		            //勾选项目时金额变动
					function moneychange() {
						var money = 0;
						$("input:checkbox[name=checkname]").each(function() {
							if ($(this).is(':checked')) {
								money += parseFloat($(this).attr("money"));
							}
						});
						$("#totalMoney").html("￥"+parseFloat(money).toFixed(2));
						$("#totalMoney2").attr("value", parseFloat(money).toFixed(2));
						$("#LoandataIds").attr("value", checkLoan());			
					}
					$(function() {
						noChoose();
						$("#allocation").click(function(){
							$(".qudao-tc").show();
							});
					    $(".qudao-button1").click(function(){
							$(".qudao-tc").hide();
							});
					    $(".kesjg-qx").click(function(){
							$(".ktsjg-tc").hide();
						});
					    $(".kesjg-qd").click(function(){
							var ktsjigou=$("#ktsjigou").val();
							var transactionId=$("#transactionId").val();
						/* 	if(ktsjigou==''){
								alert("请填写可推送机构");
							return false;
							}else{} */
								$(".ktsjg-tc").hide();
								$.ajax({
								type : "POST",
								url : '/transferstation/remarkChannel',
								data : {"transactionId":transactionId,"ktsjigou":ktsjigou},// 你的formid
								success : function(data) {
									if (data == 'ok') {
										alert('备注成功');
										window.location = "/transferstation/vehicleTransferstation";
									}else{
										alert('备注失败');
									}
								}
							});  	
							
						});
						$("#qudao").change( function() {
							  var qudao=$("#qudao").val();
							  if(qudao=='定期'){
								//定期打包和新手标都要显示
							   $(".qudao-dq-info").show();
							   $(".qudao-jgxs-info").hide();
							   $(".qudao-jgxx-info").hide();
							  }else if(qudao=='活期'){
									//定期打包和新手标都要显示
								   $(".qudao-dq-info").hide();
								   $(".qudao-jgxs-info").hide();
								   $(".qudao-jgxx-info").hide();
							}else if(qudao=='机构线上'){
								//定期打包和新手标都要显示
								   $(".qudao-dq-info").hide();								   
								   $(".qudao-jgxx-info").hide();
								   $(".qudao-jgxs-info").show();
							}else if(qudao=='机构线下'){
								//定期打包和新手标都要显示
								   $(".qudao-dq-info").hide();
								   $(".qudao-jgxs-info").hide();
								   $(".qudao-jgxx-info").show();
							}
							else if(qudao=='线下')
							{
								   $(".qudao-dq-info").hide();
								   $(".qudao-jgxs-info").hide();
								   $(".qudao-jgxx-info").hide();
							}
						});
					});
					$("#checkall").click(function(){ 
						 if(this.checked){ 
						        $("input:checkbox[name=checkname]").attr('checked', true);
						    }else{ 
						        $("input:checkbox[name=checkname]").attr('checked', false);
						    } 
						 moneychange();
				  });
					$(":checkbox").change(function() {
						moneychange();
					});
					//获取选中的id值
					function checkLoan(){
						var arr = new Array();
						$("input:checkbox[name=checkname]:checked").each(function(i){						
							  arr.push($(this).val());
						}); 
						return arr;
					}
					//判断是否有分配过的
					function checkStatus() {
						var arrStatus = new Array();
						var arrId = new Array();
						$("input:checkbox[name=checkname]").each(function() {
							if ($(this).is(':checked')) {
								//arrStatus.push($(this).attr("status"));
								if($(this).attr("status")=="1"){
								 arrStatus.push($(this).attr("status"));
								 arrId.push($(this).attr("contractId"));								
								}
							}
						});	
						if(arrId.length>0){
							  alert("合同编号为"+arrId+"已经分配过不能再分配");
							   return false;
						}
						return true;
					}
					//判断如果勾选打包选项执行的判断并加上返回值
					function packChoose(){
						var qudao=$("#qudao").val();
						 if(qudao=='定期'){
							 if ($("#unpackdq").is(':checked')) {	
									$("#isunpack").attr("value","isunpack");
									if(!checkLoanTerm())
									return false;
								}
						}else if(qudao=='机构线上'){							
								if ($("#unpackhq").is(':checked')) {	
									$("#isunpack").attr("value","isunpack");
									if(!checkLoanTerm())
									return false;
								}
						}					
						/* if ($("#unpack").is(':checked')) {	
							$("#isunpack").attr("value","isunpack");
							if(!checkLoanTerm())
							return false;
						}  */
						if ($("#newSubject").is(':checked')) {	
							$("#isnewSubject").attr("value","isnewSubject");
						}
						return true;
					/* 	 var result = new Array();
			                $("[name = chkItem]:checkbox").each(function () {			                   		                       
			                     //如果勾选打包就判断打包的条件
			                   result.push($(this).attr("value"));
			                });
			             return result; */
					}
					//判断勾选的期限是不是一致
					function checkLoanTerm(){					
						var arrLoanTerm = new Array();
						var departments = new Array();
						var repayType =new Array();
						var LoanRemark = new Array();
						$("input:checkbox[name=checkname]").each(function() {
							if ($(this).is(':checked')) {	
								arrLoanTerm.push($(this).attr("deadline"));	
								departments.push($(this).attr("department"));
								repayType.push($(this).attr("repayType"));
								LoanRemark.push($(this).attr("LoanRemark"));
							}
						});
						//1.判断是否都是先息后本
						if(repayType.length>1){
							for(var i=0;i<repayType.length;i++){
									if(repayType[i] != '先息后本'){
										alert("您勾选的有不是先息后本的项目");
										return false;
									}							 
							}
						}
						//2.判断新增，新续贷和老续贷借款人是否一致
						var remarkleng=LoanRemark.length;
						if(remarkleng>1){
							var first = LoanRemark[0];							
							for(var i=0;i<remarkleng;i++){
								if(LoanRemark[i] != first){
									alert("您勾选的线上借款人不一致");
									return false;
								}										
							}
						  $("#borrowerId").attr("value",first); 
						}else if(remarkleng==0){
							alert("请勾选要打包的项目");
							return false;
						}		
						//3.判断期限是否一致
						var termleng=arrLoanTerm.length;
						if(termleng>1){
							var first = arrLoanTerm[0];
							for(var i=0;i<termleng;i++){
									if(arrLoanTerm[i] != first){
										alert("您勾选的借款期限不一致");
										return false;
									}							 
							}
						  $("#deadlineqf").attr("value",first); 
						}/* else if(termleng==0){
							alert("请勾选要打包的项目");
							return false;
						} */else if(termleng==1){
							 $("#deadlineqf").attr("value",arrLoanTerm);
							$("#company").attr("value",departments); 
							 $("#borrowerId").attr("value",LoanRemark);  
						}
						//4.判断公司是否一致
						var departleng=departments.length;
						if(departleng>1){
							for(var i=0;i<departleng-1;i++){							
								for(var j=1;j<departleng;j++){
									if(departments[j]!=departments[i]){
										alert("您勾选的核算公司不一致");
										return false;
									}																											
								}
							}
							 $("#company").attr("value",departments[0]); 
						}
						return true;
					}					
					function allocationLoans(){						
						var arr=checkLoan();
						var qudao=$("#qudao").val();
						var institutionName ="";
					
					if(checkStatus()&&packChoose()){
						 if(qudao=='机构线下'){
							institutionName =$("#jigouxx").val();
							if(institutionName==''){
								alert("请填写机构名称");
								return false;
							}
						}else if(qudao=='机构线上'){							
							institutionName =$("#jigouxs").val();
							if(institutionName==''){
								alert("请填写机构名称");
								return false;
							}
						}
						 if(arr.length==0){
								alert("请勾选要分配的项目");
								return false;
							} 
						//alert(checkLoan()); 
						var isunpack=$("#isunpack").val();
						var isnewSubject=$("#isnewSubject").val();
						var deadline=$("#deadlineqf").val();
						var company=$("#company").val();
						var borrowerId=$("#borrowerId").val();
						var money=$("#totalMoney2").val();
						 if(confirm("确定把这些项目分配到"+qudao+"吗?")){			
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/transferstation/allocationLoans",						
								data: {"ids":checkLoan(),"qudao":qudao,"institutionName":institutionName,"isunpack":isunpack,"isnewSubject":isnewSubject,
									"deadline":deadline,"money":money,"company":company,"borrowerId":borrowerId},
								success:function(data) {
									if(data=='success'){
										alert("分配渠道成功");
										window.location.href="/transferstation/vehicleTransferstation";
									}else{
										alert("分配渠道失败");
										window.location.href="/transferstation/vehicleTransferstation";
									}
								}
							});	
						} 
					 }
					}					
				    $("#queryAll").click(function(){
				    	$("#form1").attr("action","/transferstation/vehicleTransferstation");
	           		    $("#form1").submit();
	              });
				    //导出
				   $("#exportInterMed").click(function(){
				    	$("#form1").attr("action","/transferstation/exportTransferstation");
	           		    $("#form1").submit();
	              });
				    
					</script>
					<%@ include file="../base/page.jsp"%>
<div class="qudao-tc" style="display: none;">
     <div style="padding-left:120px;">
         <label>选择分配渠道：</label>
         <select id="qudao">
              <option class="qudao-dq">定期</option>
              <option class="qudao-hq">活期</option>
              <option class="qudao-jgxs">机构线上</option>
              <option class="qudao-jgxx">机构线下</option>
              <option class="qudao-xianxia">线下</option>
         </select>
       </div>
       <div class="qudao-dq-info">
         <div>
          <input type="checkbox" id="unpackdq" name="chkItem" value="unpack"/><label>打包（选中项目发成一个标的）</label>
         </div>
          <div>
          <input type="checkbox" id="newSubject" name="chkSubject" value="newSubject"/><label>新手标（选中项目发成一个新手标）</label>
         </div>
       </div>
       <div class="qudao-jgxs-info" style="display:none;">
         <div style="padding-left:220px;padding-top:7px;">
          <input type="checkbox" id="unpackhq" name="chkItem" value="unpack"/><label>打包（选中项目发成一个标的）</label>
         </div>
         <div style="padding-left:120px;padding-top:7px;">
            <label>输入机构名称：</label>
            <input type="text" class="qudao-srjgmc" id="jigouxs"/>
         </div>
       </div>
       <div class="qudao-jgxx-info" style="display:none;">
         <div style="padding-left:120px;padding-top:7px;">
            <label>输入机构名称：</label>
            <input type="text" class="qudao-srjgmc" id="jigouxx"/>
         </div>
       </div>
    <div class="qudao-button">
        <button class="qudao-button1">取消</button>
         <input type="hidden" id="isunpack" value=""/>
          <input type="hidden" id="isnewSubject" value=""/>
        <button class="qudao-button2" onclick="allocationLoans()">确定分配</button>
    </div>
</div>
<div class="ktsjg-tc" style="display:none;">
  <div style="padding-left:50px;padding-top:7px;">
    <label>可推送机构：</label>
    <input type="text" class="ktsjg-input" id="ktsjigou"/>
    <input type="hidden" id="transactionId"/>
  </div>
  <div style="padding-top:20px;text-align:center;">
      <button class="kesjg-qx">取消</button>
      <button class="kesjg-qd">确定</button>
  </div>
</div>
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

