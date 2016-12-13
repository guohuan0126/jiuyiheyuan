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

<title>中转站等额本息子标列表</title>
<style>
.parent{position:relative;}
.son{position:absolute;bottom:-58px;left:-86px;width:180px;background:#f7f7f7;border:1px solid #ccc;border-radius:3px;display:none;}
.qudao-tc{width:500px;height:180px;background-color:#fff;border:1px solid #333;color:#333;font-size:14px;padding:20px 0;position:absolute;top:300px;left:50%;margin-left:-250px;}
.qudao-dq-info{padding-left:220px;}
.qudao-dq-info div{padding-top:7px;}
.qudao-button{padding-top:15px;text-align:center;}
.qudao-button1{width:80px;height:35px;text-align:center;display:inline-block;color:#000;background-color:#ddd;line-height:35px;border:none;}
.qudao-button2{width:80px;height:35px;text-align:center;display:inline-block;color:#000;background-color:#528FD5;line-height:35px;border:none;}

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
					<i class="fa fa-table"></i>当前位置：中转站等额本息子标列表
				</h2>
			</div>
		<form class="form-inline" id="form1"  action="/transferstation/childLoansList" method="post">		    	 
	     <div class="contentpanel">
	       <input type="text"  name="loanId" value="${loanId}" class="form-control" placeholder="项目编号" id="loanId">
           <input type="text"  name="contractId" value="${contractId}" class="form-control" placeholder="总合同编号" id="contractId">
	      <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
           
             <div class="input-group" >
  			<span style="font-size: 20px">子标期限：</span>
                 	<select id="loanTerm" name="loanTerm">
                 		<option value="">全部</option>
                 		<option value="1">一个月</option>
                 		<option value="2">两个月</option>
                 		<option value="3">三个月</option>
                 		<option value="4">四个月</option>
                 		<option value="5">五个月</option>
                 		<option value="6">六个月</option>
                 		<option value="7">七个月</option>
                 		<option value="8">八个月</option>
                 		<option value="9">九个月</option>
                 		<option value="10">十个月</option>
                 		<option value="11">十一个月</option>
                 		<option value="12">十二个月</option>
                 	</select>
                 </div>
                <div class="input-group" >
  			    <span style="font-size: 20px">核算公司：</span>
                 	<select id="accountingDepartment" name="accountingDepartment">
                 		<option value="">全部</option>
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
  			<span style="font-size: 20px">是否打包：</span>
                 	<select id="forkStatus" name="forkStatus">
                 		<option value="">全部</option>
                 		<option value="1">是</option>
                 		<option value="0">否</option>
                 	</select>
                 </div>
                  <input name="orderbyMoney" id="orderbyMoney" type="hidden"  value="${orderbyMoney}"/>
                <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"></input>
	     </div>
	      <br>
  
              <input type="button" id="allocation" class="btn btn-primary"  style="display:inline-block;" value="打包分配"></input> 
              <label style="font-size: 20px;padding-left: 20px;">打包资产总金额：</label> <span style="font-size: 20px;color: red;"
									id="totalMoney"></span>			 
			  <input name="LoandataIds" id="LoandataIds" type="hidden" />
			  <input name="totalMoney" id="totalMoney2" type="hidden" />	
			  <input name="deadline" id="deadline" type="hidden" />	
			  <input name="company" id="company" type="hidden" />	
			  <input name="forkIds" id="forkIds" type="hidden" />	
			  <input name="borrowerId" id="borrowerId" type="hidden" />				  	     	
	          </form>
			<script type="text/javascript">	
			$("#loanTerm").val('${loanTerm}');
			$("#forkStatus").val('${forkStatus}');
			$("#accountingDepartment").val('${accountingDepartment}');
			$("#onlineBorrower").val('${onlineBorrower}');
			  jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
					<div class="panel-body">
						<div id="rule"></div>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr><th><input name="checkall" type="checkbox" money="0" id="checkall"  style="vertical-align:sub;"/>全选</th>
									<th>子合同编号</th>
									<th>项目编号</th>
									<th><a href="javascript:orderby('money')" style="color: red;">子标金额</a></th>
									<th>子标期限</th>
									<th>姓名</th>
									<th>身份证号</th>
									<th>总合同编号</th>
									<th>发行渠道</th>
									<th>项目类型</th>
									<th>合同金额</th>
									<th>总期限</th>
									<th>是否打包</th>
									<th>线上借款人</th>
									<th>核算公司</th>
									<th>创建时间</th>
									<th>备注信息</th>
									
								</tr>
							</thead>
								<c:forEach var="childLoansList" items="${pageInfo.results}">
								<tr>
								<td><input name="checkname" type="checkbox"  value="${childLoansList.parentId}" forkId="${childLoansList.id}" status="${childLoansList.packing}" 
								money="${childLoansList.forkMoney}" loanTerm="${childLoansList.loanTerm}" contractId="${childLoansList.childContractid}"
								department="${childLoansList.accountingDepartment}" LoanRemark="${childLoansList.borrowerId}"/></td>
								    <td>
								    	${childLoansList.childContractid}
								    </td>
								    <td>${childLoansList.loanId}</td>
								    <td>${childLoansList.forkMoney}</td>
								    <td>${childLoansList.loanTerm}个月</td>
								    <td>${childLoansList.borrowerName}</td>
								    <td>${childLoansList.borrowerIdCard}</td>
								    <td>${childLoansList.contractId}</td>
								     <td> ${childLoansList.remark}</td>
								   	 <td>${childLoansList.loanType}</td>
								    <td>${childLoansList.contractMoney}</td>
								    <td>${childLoansList.parentLoanTerm}个月</td>
								   	 <td>
								   	 <c:if test="${childLoansList.packing==0}">否</c:if>
								   	 <c:if test="${childLoansList.packing==1}">是</c:if>
								   	 </td>
								   	 <td><c:if test="${childLoansList.borrowerId=='EBFZvmiaMrAfjswz'}">覃仕东</c:if>
								    <c:if test="${childLoansList.borrowerId=='mMZbEzYFzIrehguk'}">郑卉芳</c:if>
								    <c:if test="${childLoansList.borrowerId=='ceh1234'}">张凯</c:if>
								    </td>
                                    <td>
								   	 <c:if test="${childLoansList.accountingDepartment==0}">齐海</c:if>
								   	 <c:if test="${childLoansList.accountingDepartment==1}">久亿</c:if>
								   	 </td>
								    <td>
								    	<fmt:formatDate value="${childLoansList.createTime}" type="both"/>
								    </td>
								    <td> <a href="javascript:getReason1('${childLoansList.remark}');">备注</a></td> 
								</tr>
							</c:forEach>	
						</table>
					</div>
			<script type="text/javascript">
			//初始化页面金额
			function noChoose(){				
				var arr2 = $("input:checkbox[name=checkname]:checked");	 				
				if(arr2.length <= 0)
				{ $("#totalMoney2").val("0.0");
				$("#totalMoney").html("￥0.0");
				$("#LoandataIds").attr("value", "");
				$("#forkIds").attr("value", "");
				$("#deadline").attr("value", $("#loanTerm").val());
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
				$("#forkIds").attr("value", getforkId());				
			}
			
					$(function() {
						noChoose();
						$("#allocation").click(function(){
							$(".qudao-tc").show();
							});
					    $(".qudao-button1").click(function(){
							$(".qudao-tc").hide();
							});
						$("#qudao").change( function() {
							  var qudao=$("#qudao").val();
							  if(qudao=='定期'){
								//定期打包和新手标都要显示
							   $(".qudao-dq-info").show();
							   $(".qudao-jgxs-info").hide();
							  }else if(qudao=='机构线上'){
								//定期打包和新手标都要显示
								   $(".qudao-dq-info").hide();								   								
								   $(".qudao-jgxs-info").show();
							}
						});
					});
				    //全选
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
					//获取选中的子标父id
					function getforkId(){
						var arr = new Array();
						$("input:checkbox[name=checkname]:checked").each(function(i){						
							  arr.push($(this).attr("forkId"));
						}); 
						return arr;
					}
					//1.判断是否有分配过的
					function checkStatus() {
						var arrStatus = new Array();
						var arrId = new Array();
						$("input:checkbox[name=checkname]").each(function() {
							if ($(this).is(':checked')) {
								if($(this).attr("status")=="1"){
								 arrStatus.push($(this).attr("status"));
								 arrId.push($(this).attr("contractId"));								
								}
							}
						});	
						if(arrId.length>0){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
							  alert("合同编号为"+arrId+"已经打包过不能再次打包");
							   return false;
						}
						return true;
					}
					
					//判断勾选的期限是不是一致
					function checkLoanTerm(){
						var arrLoanTerm = new Array();
						var departments = new Array();
						var LoanRemark = new Array();
						$("input:checkbox[name=checkname]").each(function() {
							if ($(this).is(':checked')) {	
								arrLoanTerm.push($(this).attr("loanTerm"));	
								departments.push($(this).attr("department"));
								LoanRemark.push($(this).attr("LoanRemark"));
							}
						});	
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
						var termleng=arrLoanTerm.length;
						if(termleng>1){
							var first = arrLoanTerm[0];
							for(var i=0;i<termleng;i++){
									if(arrLoanTerm[i] != first){
										alert("您勾选的借款期限不一致");
										return false;
									}							 
							}
						 $("#deadline").attr("value",first);
						}else if(termleng==0){
							alert("请勾选要打包的项目");
							return false;
						}else if(termleng==1){
							$("#deadline").attr("value",arrLoanTerm);
							$("#company").attr("value",departments);
						    $("#borrowerId").attr("value",LoanRemark); 
						}
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
					//点击分配进行处理
					function allocationLoans(){							
						var qudao=$("#qudao").val();
						var institutionName ="";
						if ($("#newSubject").is(':checked')) {	
							$("#isnewSubject").attr("value","isnewSubject");
						}
					if(checkStatus() && checkLoanTerm()){
						var deadline=$("#deadline").val();
						var company=$("#company").val();
						var borrowerId=$("#borrowerId").val();
						var money=$("#totalMoney2").val();
						var isnewSubject=$("#isnewSubject").val();
						if(qudao=='机构线上'){
							institutionName =$("#jigouxs").val();
							if(institutionName==''){
								alert("请填写机构名称");
								return false;
							}
						}
						 if(confirm("确定把这些项目打包到"+qudao+"吗?")){			
							$.ajax({
								type : 'POST',
								dataType:'text',
								url : "/transferstation/packedForkLoans",						
								data: {"ids":checkLoan(),"qudao":qudao,"institutionName":institutionName,"deadline":deadline,"forkIds":getforkId(),"money":money,"company":company,
									"isnewSubject":isnewSubject,"borrowerId":borrowerId},
								success:function(data) {
									if(data=='success'){
										alert("打包分配成功");
										window.location.href="/transferstation/childLoansList";
									}else{
										alert("打包分配失败");
										window.location.href="/transferstation/childLoansList";
									}
								}
							});	
						} 
					 } 
					}					
					function orderby(params){
						var orderbyMoney= $("#orderbyMoney").val();
						 if(orderbyMoney=="1"){
							$("#orderbyMoney").val("2");
						}else{
							$("#orderbyMoney").val("1");
						}						
						$("#form1").attr("action","/transferstation/childLoansList");
					    $("#form1").submit();
					}
					</script>
					<%@ include file="../base/page.jsp"%>
<div class="qudao-tc" style="display: none;">
     <div style="padding-left:120px;">
         <label>选择分配渠道：</label>
         <select id="qudao">
              <option class="qudao-dq">定期</option>            
              <option class="qudao-jgxs">机构线上</option>
         </select>
       </div>
        <div class="qudao-dq-info">
          <div>
          <input type="checkbox" id="newSubject" name="chkItem" value="newSubject"/><label>新手标（选中项目发成一个新手标）</label>
         </div>
       </div>
       <div class="qudao-jgxs-info" style="display:none;">
         
         <div style="padding-left:120px;padding-top:7px;">
            <label>输入机构名称：</label>
            <input type="text" class="qudao-srjgmc" id="jigouxs"/>
         </div>
       </div>
    <div class="qudao-button">
     <input type="hidden" id="isnewSubject" value=""/>
        <button class="qudao-button1">取消</button>
        <button class="qudao-button2" onclick="allocationLoans()">确定分配</button>
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


