<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="images/favicon.png" type="image/png">

<title>单笔自动投标</title>
<script type="text/javascript">
	
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
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-pencil"></i> 投标
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">投标</h4>
						</div>
						<div class="panel-body">
							<div class="form-group" id="investUser">
								<label class="col-sm-3 control-label">用户<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="userId" id="userId"  width="10px"
										class="form-control" placeholder="编号 / 手机号 / 邮箱"  />
									<div id="errorUser" style="display: none;">用户不能为空</div>
								</div>
								<div class="col-sm-3">
									<a class="btn btn-primary" type="button" onclick="queryInvestUser()">查看</a>
								</div>
							</div>
							
							<div class="form-group" id="loan">
								<label class="col-sm-3 control-label">项目编号<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="loanId" id="loanId"  width="10px"
										class="form-control" placeholder="编号 "  />
									<div id="errorLoan" style="display: none;">项目编号不能为空</div>
								</div>
								<div class="col-sm-3">
									<a class="btn btn-primary" type="button" onclick="queryLoan()">查看</a>
								</div>
							</div>
							
							<div class="form-group" id="investMoney">
								<label class="col-sm-3 control-label">投资金额<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="money" id="money"  width="10px"
										class="form-control" placeholder="金额 "  onblur="moneyCheck()"/>
									<div id="errorMoney" style="display: none;">投资金额不能为空</div>
								</div>
								
							</div>
							
							<div class="form-group" id="investMoney">
								<label class="col-sm-3 control-label">投标类型<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select name="auto" id="auto">																				
										<option value="1">自动投标</option>
										<option value="0">手动投标</option>
									</select>
								</div>
								
							</div>
						</div>							
						<!-- panel-body -->
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="return sub()">投资</button>
									<button type="button" class="btn btn-default" onclick="res()">重置</button>
								</div>
							</div>
						</div>
					</div>
					<!-- panel -->
				</form>
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->

	</section>

	<script type="text/javascript">
		
		
		function res(){
			$("#errorUser").attr("style", "display: none;");	
			$("#user").remove();
			$("#errorLoan").attr("style", "display: none;");
			$("#loanDatil").remove();
			document.form.reset();
			
		}
	
		var flag = true;	//防止表单重复提交
		var flag1 = true;	//防止表单重复提交	
		function  queryInvestUser(){	
		var userId = $("#userId").val();				
			$("#errorUser").attr("style", "display: none;");	
			$("#user").remove();									
			if(userId == ''){
				$("#errorUser").attr("style", "display: block;color: red;");
				return ;
			}			
			if(flag){
				flag = false; //请求开始，锁定提交表参
				$.ajax({				
						type : 'POST',
						url : '${ctx}/invest/getInvestUser',
						dataType : 'json',
						data:{userId: userId},
						error:function(){
							flag = true; //请求失败，开启提交表单
						},
						
						success : function(data) {							
							if(data == null){				
								$("#errorUser").text("该用户不存在！");
								$("#errorUser").attr("style", "display: block;color: red;");
								flag = true; 
								return ;
							}else{						
								user = eval(data);
								$str = "<table id='user' class='table table-primary table-striped table-hover'>";
								$str +="<thead><tr class='font'><th width='15%'>用户编号</th>" 
								 + "<th width='15%'>真实姓名</th><th width='25%'>手机号码</th>"
								 + "<th width='15%'>冻结金额</th><th width='15%'>可用余额</th>"
								 +" <th width='15%'>自动投标</th></tr></thead>";	
								$str += "<tbody>";							
								$str += "<tr class='font'><td>"+user[0].userId+"</td>";
								$str += "<td>"+user[0].realname+"</td>";
								$str += "<td>"+user[0].mobileNumber+"</td>";
								$str += "<td>"+user[0].frozenMoney+"</td>";
								$str += "<td>"+user[0].balance+"</td>";
								$str += "<td>"+user[0].autoInvest+"</td>";
								$str += "</tr></tbody></table>";
							}														
						$('#investUser').append($str);
						flag = true; //请求完成开启提交表单
					}
				});				
			}
		}
		
		function  queryLoan(){		
		var loanId = $("#loanId").val();
			$("#errorLoan").attr("style", "display: none;");
			$("#loanDatil").remove();					
			if(loanId == ''){
				$("#errorLoan").attr("style", "display: block;color: red;");
				return ;
			}
			if(flag1){
				flag1 = false;
				$.ajax({				
						type : 'POST',
						url : '${ctx}/loan/getLoanById',
						dataType : 'json',
						data:{loanId: loanId},
						error:function(){
							flag1 = true; //请求失败，开启提交表单
						},
						success : function(data) {
							if(data == null){
								$("#errorLoan").text("该项目不存在！");
								$("#errorLoan").attr("style", "display: block;color: red;");
								flag1 = true;
								return ;
							}else{						
								loan = eval(data);
								$str = "<table id='loanDatil' class='table table-primary table-striped table-hover'>";
								$str +="<thead><tr class='font'><th width='12%'>项目编号</th>" 
								 + "<th width='12%'>项目名称</th><th width='8%'>项目状态</th>"
								 + "<th width='8%'>周期</th><th width='8%'>利率</th>"
								 + "<th width='10%'>可提前还款</th><th width='10'>还款方式</th>"
								 + "<th width='8%'>起投金额</th><th width='8%'>递增金额</th>"					 
								 +" <th width='8%'>项目总金额</th><th width='8%'>已投金额</th></tr></thead>";	
								$str += "<tbody>";							
								$str += "<tr class='font'><td>"+loan[0].id+"</td>";
								$str += "<td>"+loan[0].name+"</td>";
								$str += "<td>"+loan[0].status+"</td>";
								var dealline = 0;							
								if("月" == loan[0].operationType){
									dealline=loan[0].deadline+"个月";
								}													
								if( '天' == loan[0].operationType ){
									if("是" == loan[0].beforeRepay){
										dealline = loan[0].day+loan[0].symbol+"天";
									}else{
										dealline = loan[0].day + "天";
									}
								}							
								$str += "<td>"+dealline+"</td>";
								$str += "<td>"+loan[0].rate+"</td>";
								$str += "<td>"+loan[0].beforeRepay+"</td>";
								$str += "<td>"+loan[0].repayType+"</td>";
								$str += "<td>"+loan[0].investOriginMoney+"</td>";
								$str += "<td>"+loan[0].increaseMoney+"</td>";
								$str += "<td>"+loan[0].totalmoney+"</td>";
								$str += "<td>"+loan[0].calculateMoneyNeedRaised+"</td>";
								$str += "</tr></tbody></table>";
							}					
							
							$('#loan').append($str);
							flag1 = true;
						}
					});
			}
		}
	
		function moneyCheck(){
		var loanId = $("#loanId").val();
		var money = $("#money").val();
			$("#errorLoan").attr("style", "display: none;");
			$("#errorMoney").attr("style", "display: none;");		
			if(loanId == ''){
				$("#errorLoan").attr("style", "display: block;color: red;");
				return ;
			}					
			if(money == ''){
				$("#errorMoney").attr("style", "display: block;color: red;");
				return ;
			}
			var re=/^(\d+)\$/g; 
			if(!re.test(money)){				
				$("#errorMoney").text("请输入整数！");									
				$("#errorMoney").attr("style", "display: block;color: red;");
				return ;
			}
			$.ajax({				
					type : 'POST',
					url : '${ctx}/loan/getLoanById',
					dataType : 'json',
					data:{loanId: loanId},
					success : function(data) {
						if(data == null){
							$("#errorLoan").text("该项目不存在！");
							$("#errorLoan").attr("style", "display: block;color: red;");
							return ;
						}else{						
							loan = eval(data);	
							if(loan[0].status != '筹款中' && loan[0].status != '贷前公告'){								
								$("#errorLoan").text("该项目不可自动投标！");
								$("#errorLoan").attr("style", "display: block;color: red;");
								return ;
							}	
							/* if(money < loan[0].investOriginMoney){
								$("#errorMoney").text("输入的金额小于该项目起投金额");				
								$("#errorMoney").attr("style", "display: block;color: red;");	
								return ;
							}				
							if(money % loan[0].increaseMoney != 0){
								$("#errorMoney").text("输入的金额不是递增金额的整数倍");				
								$("#errorMoney").attr("style", "display: block;color: red;");		
								return ;
							} */
							if(money > (loan[0].totalmoney - loan[0].calculateMoneyNeedRaised)){
								$("#errorMoney").text("输入的金额大于项目剩余可投金额");				
								$("#errorMoney").attr("style", "display: block;color: red;");	
								return ;
							}	
						}											
					}
				});			
		}
	
		function subCheckUser(){
		var userId = $("#userId").val();
			$("#errorUser").attr("style", "display: none;");			
			if(userId == ''){
				$("#errorUser").attr("style", "display: block;color: red;");
				return ;
			}
			
			$.ajax({				
					type : 'POST',
					url : '${ctx}/invest/getInvestUser',
					dataType : 'json',
					data:{userId: userId},
					success : function(data) {
						
						if(data == null){				
							$("#errorUser").text("该用户不存在！");
							$("#errorUser").attr("style", "display: block;color: red;");
							return ;
						}else{						
							user = eval(data);
							if(user[0].autoInvest == false){
								$("#errorUser").text("用户未开启自动投标！");
								$("#errorUser").attr("style", "display: block;color: red;");
							}
							return ;
						}
					}
				});
			
		}
		
		function sub(){		
			var userId = $("#userId").val();
			var loanId = $("#loanId").val();
			var money = $("#money").val();
			var auto =$("#auto").val();
			subCheckUser();
			moneyCheck();		
							
			$.ajax({				
					type : 'POST',
					url : '${ctx}/invest/invest',				
					data:{loanId: loanId,
					userId: userId,
					investMoney:money,
					auto:auto
					},
					success : function(data) {	
									
						if(data == "UL"){
							alert("该用户不存在！");
							return ;
						}else if(data == "ER"){
							alert("自动投标失败！");
							return ;
						}else if(data == "OK"){
							alert("自动投标成功");
							res();
							return ;
						}else if(data == "LL"){
							alert("项目不存在！");
							return ;
						}else if(data == "UU"){
							alert("投資人不能是借款人");
							return ;
						}else if(data == "UB"){
							$("#errorMoney").text("余额不足");				
							$("#errorMoney").attr("style", "display: block;color: red;");	
							return ;
						}else{
							alert("自动投标失败！");
						}
					}
				});
		}
	</script>

</body>
</html>
