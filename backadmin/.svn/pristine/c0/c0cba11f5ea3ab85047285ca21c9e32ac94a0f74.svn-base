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

<title>创建平台奖励</title>
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
					<i class="fa fa-pencil"></i> 创建奖励项目 
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form1" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">创建奖励项目</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">奖励项目名称 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="name" id="name"  width="10px"
										class="form-control" placeholder="名称不能为空"  />
									<div id="errorName" style="display: none;">名称不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">奖励项目描述<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="description"  id="description"
										placeholder="描述不能为空" class="form-control"  />
									<div id="errorDescription" style="display: none;">描述不能为空
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">消息内容 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<textarea rows="5" name="messageContent" id="messageContent" class="form-control"
										placeholder="消息内容不能为空" ></textarea>
									<span style="color:red;">*消息内容可以自定义，但如果内容里有用户名请用#{name}代替,奖励金额用#{money}代替。</span>
									<div id="errorMessageContent" style="display: none;">
										消息内容不能为空
									
										</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">奖励项目类型 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select name="itemType" id="itemType">
										<option value="">--请选择--</option>
										<option value="register">注册奖励</option>
										<option value="newbieEnjoy">新手专享</option>
										<option value="51AddInterest">五一加息</option>
										<option value="invest">投资奖励</option>
										<option value="businessbonus">业绩提成</option>
										<option value="finance">财务专用</option>
										<option value="weixin">微信奖励</option>
										<option value="compensation">投资补偿</option>
										<option value="oneyear">1周年庆</option>
										<option value="jdpay">京东支付</option>
									</select>
									<div id="errorItemType" style="display: none;">
										奖励项目不能为空</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">金额类型 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select name="moneyType1" id="moneyType1">
										<option value="">--请选择--</option>
										<option value="fixed">固定金额</option>
										<option value="percentage">百分比金额</option>
										<option value="ladder">跟投金额</option>
										<option value="mixture">混合金额</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">填充金额类型 <span
									class="asterisk"></span></label>
								<div class="col-sm-3">
									<select name="moneyType" id="moneyType" onchange="moneyTypeSelect()">
										<option value="fixed">固定</option>
										<option id="percentageOption" value="percentage">百分比</option>
									</select>
								<input id="moneyTypeInput" name="moneyTypeInput"  placeholder="请输入金额/百分比" style="width: 130px;"/>
								<span id="moneyTypeInputError" style="display: none;color: red;"><p></p></span>
								<span id="minmax" style="display: none;">
									<input id="min"  placeholder="开始" style="width: 40px;"/>
									-用户范围-
									<input id="max"  placeholder="结束" style="width: 40px;"/>
									<p></p>
								</span>
								
								<input type="button" value="自动填写金额" onclick="updateAwardMoney()"/>
								<div id="errorMoneyType" style="display: none;">
										金额类型不正确</div>
								</div>
							</div>
						
							<div class="form-group">
								<label class="col-sm-3 control-label">是否与借款项目有关 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									无 &nbsp;<input type="radio" name="radio" id="wu" value="wu"
										checked="checked" onclick="correlation('wu')" /> 有 &nbsp;<input
										type="radio" name="radio" id="you" value="you"
										onclick="correlation('you')" />
								</div>
							</div>
							<div class="form-group" style="display:none;" id="loan">
								<label class="col-sm-3 control-label">借款项目id </label>
								<div class="col-sm-3">
									<span>
										<input type="text" id="loanId" style="width:200px" name="loanId" placeholder="若与借款项目有关请填写" />
										不合并 &nbsp;<input type="radio" name="merge"  value="NOmerge" checked="checked"/>
										合并 &nbsp;<input type="radio" name="merge"  value="Ymerge" /> 
										<input type="button" value="查询" onclick="queryLoan()">
									</span>
									<span style="font-size:14px;color:red" id="errorLoanId"><p></p></span>
								</div>
							</div>
							<div class="form-group" id="groupCountDiv">
								<label class="col-sm-3 control-label">填写奖励组数 </label>
								<div class="col-sm-3">
										<input type="text" id="groupCountIpt" name="groupCountIpt"
											onchange="groupCount()" placeholder="请填写数字"
											class="form-control"/>
										<a style="font-size:30px" onclick="operationInput('add')">+</a>
									<div id="errorGroupCount" style="display: none;">组数不正确</div>
								</div>
							</div>
							<div class="form-group">
							<table  id="groupDetailTable" style="display: none;">
								<label class="col-sm-3 control-label" id="mobileAndmoney" style="display:none;">请填写用户手机号和对应金额
								</label>
								<div id="groupDetail"></div>
							</table>
							</div>
							<div class="form-group">   
								<label class="col-sm-3 control-label">选择流程模板 <span
										class="asterisk">*</span></label>    
								<div>      
				                    <select id="itemFlow" name="itemFlow" class="select" onChange="addFlow(this)" required data-placeholder="选择一个流程模板...">
				                      <option value=""></option>
				                      <c:forEach var="flow" items="${flows}">
				                      	<option value="${flow.flowId}">${flow.flowName}</option>	                      
				                      </c:forEach>                  
				                    </select>
				                    <div id="errorItemFlow" style="display: none;">项目流程不能为空</div>
			                    </div>
			                    
			                    <script type="text/javascript">
			                    	jQuery(".select").select2({
										width : '20%',
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
							<div class="contentpanel" style="padding:8px 20px 0px 20px;" id="flowNode">								
							</div>
						</div>
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="return sub()">保存</button>
									<button type="button" class="btn btn-default" onclick="return res()">重置</button>
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
			$('#flow').remove();
		}
		/* 添加流程  */
		var flag = true;
		function addFlow(flowId) {
		$('#flow').remove();
		if(flag){
			if($(flowId).val() != ''){
					$.ajax({				
						type : 'GET',
						url : '/flow/getFlowNodeByFlowId',
						dataType : 'json',
						data:{flowId:$(flowId).val()},
						success : function(data) {					
							nodes = eval(data);					
							$str = "<table id='flow' class='table table-primary table-striped table-hover' >";
							$str +="<thead><tr class='font'><th width='10%'>节点编号</th><th width='20%'>节点名称</th><th width='15%'>节点顺序</th><th width='25%'>执行角色</th><th width='30%'>描述</th></tr></thead>";	
							$str += "<tbody>";							
							for(var i = 0; i<nodes.length; i++){
								var roles = nodes[i].roles;
								var roleName = "";
								for(var j = 0; j<roles.length; j++){
									roleName+=roles[j].name+",";
								}
								$str += "<tr class='font'><td><input type='text' id='nodeId"+i+"' name='nodeId"+i+"' value='"+nodes[i].nodeId+"' readOnly class='form-control'/></td>";
								$str += "<td><input type='text' id='nodeName"+i+"' name='nodeName"+i+"' value='"+nodes[i].nodeName+"' readOnly class='form-control'/></td>";
								$str += "<td><input type='text' id='nodeOrder"+i+"' name='nodeOrder"+i+"' value='"+nodes[i].nodeOrder+"' readOnly class='form-control'/></td>";
								$str += "<td><input type='text' id='roleName"+i+"' name='roleName"+i+"' value='"+roleName+"' readOnly class='form-control'/></td>";			
								$str += "<td><input type='text' id='description"+i+"' name='description"+i+"' value='"+nodes[i].description+"' readOnly class='form-control' /></td>";
								$str += "</tr>";
							}
							
							$str += "</tbody></table>";
							$('#flowNode').append($str);
							flag = true;		
						},
						error : function() {
							flag = true;
						}
					});
					
				}
			}
		}	
		
		
		function queryLoan() {
			var loanId = $("#loanId").val();
			var merge = $("input[name='merge']:checked").val();
			var itemType = $("#itemType").val();
			var ladder = $("#moneyType1").val();
			$("#groupDetail tr").remove();
			$("#groupDetail a").remove();
			if(loanId == ''){
				$("#errorLoanId").css("display", "block");
				$("#errorLoanId p").text("借款项目id不能为空");
			}else{
					$.ajax({
						type : "POST",
						cache: false,
						url : "<%=request.getContextPath()%> /queryLoan",
					data : {
						'loanId' : loanId,
						'merge':merge,
						'itemType':itemType,
						'ladder':ladder,
					},
					async : false,
					dataType : "json",
					success : function(result) {
						var dataObj = eval(result);
						if (dataObj.status == 'fail' && dataObj.error == 'loanIdNULL') {
							$("#errorLoanId").css("display", "block");
							$("#errorLoanId").css("color", "red");
							$("#errorLoanId p").text("借款项目id不能为空");
						} else if (dataObj.status == 'fail' && dataObj.error == 'loanNULL') {
							$("#errorLoanId").css("display", "block");
							$("#errorLoanId").css("color", "red");
							$("#errorLoanId p").text("该借款项目id没找到对应借款项目");
						} else if (dataObj.status == 'fail' && dataObj.error == 'followexist') {
							$("#errorLoanId").css("display", "block");
							$("#errorLoanId").css("color", "red");
							$("#errorLoanId p").text("该借款项目跟投已创建");
						} else if (dataObj.status == 'fail' && dataObj.error == 'notNewbieEnjoy') {
							$("#errorLoanId").css("display", "block");
							$("#errorLoanId").css("color", "red");
							$("#errorLoanId p").text("该借款项目不是新手专享项目");
						} else if(dataObj.status == 'ok'){
							var html = dataObj.html;
							var count = dataObj.investCount;
							if(count <= 0){
								$("#errorLoanId").css("display", "block");
								$("#errorLoanId").css("color", "red");
								$("#errorLoanId p").text("该借款项目id没找可奖励用户或奖励已创建");
								return;
							}
							$("#groupDetail tr").remove();
							$("#errorLoanId").css("display", "block");
							$("#errorLoanId p").text('');
							$("#errorLoanId").css("display", "block");
							$("#errorLoanId").css("color", "green");
							$("#errorLoanId p").text(html);
							var maps = dataObj.list;
							$("#min").val(1);
							$("#max").val(count);
							$str = '';
							for(var i=0; i<count; i++){ 
								$str += "<tr>";
								$str += "<td id=\"td"+i+"\">";
								$str += "用户"+(i + 1)+":";
								$str += "<input placeholder=\"手机号\" readonly=\"readonly\" value=\""+maps[i].mobileNum+"\"  style=\"width:150px\" type=\"text\" id="+i+" name=\"userMobileNum" + i + "\"/>";
								$str += "</td>";
								
								if(itemType == 'weixin' || itemType == 'compensation' || ladder == 'ladder' || itemType == 'newbieEnjoy' || itemType == '51AddInterest'){
									$str += "<td>";
									$str +=	"<input placeholder=\"奖励金额\" onblur=\"verifyMoney("+i+")\"  value=\""+maps[i].awardMoney+"\"   type=\"text\" name=\"money" + i + "\" id=\"money" + i + "\"/>";
									$str += "</td>";
								}else{
									$str += "<td>";
									$str +=	"<input placeholder=\"奖励金额\" onblur=\"verifyMoney("+i+")\"     type=\"text\" name=\"money" + i + "\" id=\"money" + i + "\"/>";
									$str +=	"<input type=\"hidden\" value=\""+maps[i].totalInvestMoney+"\" id=\"totalInvestMoney" + i + "\"/>";
									$str +=	"<input type=\"hidden\" value=\""+maps[i].followInvestTotalMoney+"\" id=\"followInvestTotalMoney" + i + "\"/>";
									$str += "</td>";
								}
								$str += "<td>";
								$str += "<span styl	e=\"display:none;font-size:14px;color:red\" id=\"spann"+i+"\"><p></p></span>"
								$str += "</td>";
								$str +=	"<input type=\"hidden\" value=\""+count+"\" name=\"count\"  id=\"count\"/>";
								$str += "</tr>";
								
								$str += "<tr>";
								$str += "<td style=\"color:blue\">";
								$str += "投资总金额:"+maps[i].totalInvestMoney;
								$str += "</td>" ;
								
								$str += "<td style=\"color:purple\">";
								$str += "可享跟投金额:"+maps[i].followInvestTotalMoney;
								$str += "</td>" ;
								
								$str += "<td style=\"color:orange\">";
								$str += "投资时间:"+maps[i].investTime;
								$str += "</td>" ;
							
								$str += "<td>";
								$str += "用户id:"+maps[i].userId;
								$str += "</td>";
								
								$str += "<td style=\"color:green\">";
								$str += "真实姓名:"+maps[i].realName;
								$str += "</td>";
							} 
							$("#mobileAndmoney").attr("style", "display:block;");
							$("#groupDetail").attr("style", "display:block;");
							$("#groupDetail").append($str);
							$("#groupDetailTable").attr("style", "display:block;");
						}
					}
				});
			}
		}

		//获取金额类型下拉选的值
// 		function moneyTypeSelect(){
// 			var moneyType = $("#moneyType").val();
// 			var radio = $("input[name='radio']:checked").val();
// 			if(moneyType == ''){
// 				return;
// 			}
// 		}
		
		function updateAwardMoney(){
			$("#errorMoneyType").attr("style", "display: none;");//隐藏错误提示
			$("#minmax p").text('');
			$("#moneyTypeInputError").attr("style", "display:none;");
			$("#moneyTypeInputError p").text('');
			
			var moneyType = $("#moneyType").val();//得到金额类型
			if(moneyType == ''){//如果金额类型没选择,则显示错误提示
				$("#errorMoneyType").attr("style", "display: block;color: red;");
				return;
			}
			
			var min;
			var max;
			var radioValue = $("input[type='radio']:checked").val();//取得是否与借款项目有关
			if(radioValue == 'you'){
	 			var mre = /^[1-9]+[0-9]*]*$/;//校验输入的开始值和结束值是否正确的正则
				min = $("#min").val();//得到开始值
				max = $("#max").val();//得到结束值
				if(!mre.test(min) || !mre.test(max)){//判断看是和结束值是否正确
					$("#minmax p").attr("style", "color: red;");
					$("#minmax p").text('开始值/结束值不正确');
					return;
				}
			
				if(max < min){
					$("#minmax p").attr("style", "color: red;");
					$("#minmax p").text('开始值必须小于结束值');
					return;
				}
			}
			
			//金额/百分比金额输入框校验
			var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
			
			//组数校验
			var re = /^[1-9]+[0-9]*]*$/;
			
			//金额/百分比金额输入框的值
			var mti = $("#moneyTypeInput").val();
			//借款项目人数
			var ct = $("#count").val();
			var gc = $("#groupCountIpt").val();

			if(radioValue == 'you'){
				for ( var int = (min - 1); int < max; int++) {
					if(moneyType == 'fixed'){
						$("#money"+int).val(mti);
					}else if(moneyType == 'percentage'){
 						var totalInvestMoney =  $("#totalInvestMoney"+int).val();
 						$("#money"+int).val(totalInvestMoney * mti / 100);
					}
					$("#spann"+int+" p").text('');
	 			}
			} else {
				if(moneyType == 'percentage'){
					$("#errorMoneyType").attr("style", "display: block;color: red;");
					return;
				}
				
				if(!re.test(gc) || gc == '' || gc <= 0){
					$("#errorGroupCount").attr("style", "display:block;color: red;");
					return;
				}
				
				
				for ( var int = 0; int < gc; int++) {
					$("#money"+int).val(mti);
					$("#spann"+int+" p").text('');
				}
			}
		}
		
		function operationInput(op){
			var gc = $("#groupCountIpt").val();
			var groupCount = parseInt(gc);
			var MinusNum = groupCount-1;
			var AddNum = groupCount+1;
			if(op == 'add'){
				$("#groupDetailTable").attr("style", "display:block;")
				$("#mobileAndmoney").attr("style", "display:block;")
				$("#groupDetail").attr("style", "display:block;")
				//如果此值大于0 ,则在此值基础上追加一行
				if(groupCount > 0){
					$str = '';
					$str += "<tr>";
					$str += "<td id=\"td"+groupCount+"\">";
					$str += "用户"
							+ (groupCount + 1)
							+ ":<input placeholder=\"手机号\"    onblur=\"queryUser("+groupCount+")\" style=\"width:150px\" type=\"text\" id="+groupCount+" name=\"userMobileNum" + groupCount + "\"/>"
					$str += "</td>";
					
					$str += "<td>";
					$str +=	"<input placeholder=\"金额\" onblur=\"verifyMoney("+groupCount+")\"     type=\"text\" name=\"money" + groupCount + "\" id=\"money" + groupCount + "\"/>"
					$str += "</td>";
					
					$str += "<tr id=\"tr"+groupCount+"\">";
					$str += "<td>";
					$str += "<span style=\"display:none;font-size:14px;color:red\" id=\"span"+groupCount+"\"><p></p></span>"
					$str += "</td>";
					
					$str += "<td>";
					$str += "<span style=\"display:none;font-size:14px;color:red\" id=\"spann"+groupCount+"\"><p></p></span>"
					$str += "</td>";
					$str += "</tr>";
					
					
					$("#tr" + MinusNum).after($str);
				}else if(groupCount = 'NaN'){
					var i = 0;
					$str = '';
					$str += "<tr>";
					$str += "<td id=\"td"+i+"\">";
					$str += "用户"
							+ (i + 1)
							+ ":<input placeholder=\"手机号\"    onblur=\"queryUser("+i+")\" style=\"width:150px\" type=\"text\" id="+i+" name=\"userMobileNum" + i + "\"/>"
					$str += "</td>";
					
					$str += "<td>";
					$str +=	"<input placeholder=\"金额\" onblur=\"verifyMoney("+i+")\"     type=\"text\" name=\"money" + i + "\" id=\"money" + i + "\"/>"
					$str += "</td>";
					
					$str += "<tr id=\"tr"+i+"\">";
					$str += "<td>";
					$str += "<span style=\"display:none;font-size:14px;color:red\" id=\"span"+i+"\"><p></p></span>"
					$str += "</td>";
					
					$str += "<td>";
					$str += "<span style=\"display:none;font-size:14px;color:red\" id=\"spann"+i+"\"><p></p></span>"
					$str += "</td>";
					$str += "</tr>";
					$("#groupDetail").append($str);
					AddNum = 1;
				}
				$("#groupCountIpt").val(AddNum);
			}else{
				
			}
		}
		
		function repetitionMobile(num,mn){//num第几组用户,mn这组用户对应的手机号
			//1,得到用户所填写的总组数
			var groupCount = $("#groupCountIpt").val();
			//3,根据总组数,遍历所有组数,并得到该组对应的手机号
			for(var i = 0; i < groupCount; i++){
				if(num == i){//4,如果当前遍历的手机号与需要检查的手机号输入同一个输入框则结束本次循环
					continue;
				}else{
					var mobileNum = $("#" + i).val();//当前正在遍历的手机号
					if(mobileNum == ''){
						continue;
					}
					if(mobileNum.length == 11){
						if(mobileNum == mn){
							$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
							$("#span"+num+" p").text("该手机号已经填写过了！");
							return false;
						}
					}else{
						$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
						$("#span"+num+" p").text("手机号不正确！");
						return false;
					}
				}
			}
			return true;
		}
		
		//根据手机号查询用户信息并显示
		function queryUser(num,from) {
			var data = false;
			var mn = $("#" + num).val();//当前需要检查的
			var falg = true;
			if(mn.length != 11){
				$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
				$("#span"+num+" p").text("手机号不正确！");
				return;
			}
			
			if('from' != from){
				falg = repetitionMobile(num,mn);
			}
			if(falg){
					$.ajax({
						type : "POST",
						cache: false,
						url : "<%=request.getContextPath()%> /queryUser",
						data : {
							'mn' : mn,
						},
					dataType : "text",
					async : false,
					success : function(result) {
						if (result == 'paramError') {
							$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
							$("#span"+num+" p").text("手机号不正确！");
						} else if(result == 'fail'){
							$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
							$("#span"+num+" p").text("用户不存在！");
						} else if(result == 'INVESTOR_NULL'){
							$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
							$("#span"+num+" p").text("该手机号对应的用户未实名认证");
						} else {
							data = true;
							$("#span"+num).attr("style", "display:block;font-size:14px;color:green");
							$("#span"+num+" p").text(result);
						}
					}
				});
			}
			return data;
		}

		function verifyMoney(num){
			var money = $("#money"+num).val();
			money = $.trim(money);
			var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
			if(!exp.test(money) || money <= 0){
				$("#spann"+num).attr("style", "display:block;font-size:14px;color:red");
				$("#spann"+num+" p").text('金额不正确');
				return false;
			}else{
				$("#spann"+num).attr("style", "display:none;font-size:14px;color:red");
				$("#spann"+num+" p").text('');
				return true;
			}
		}
		
		//切换有无状态下的显示数据
		function correlation(value) {
			$("#groupDetailTable").attr("style", "display:none;")
			$("#groupDetail tr").remove();
			$("#errorLoanId").css("display", "block");
			$("#errorLoanId p").text('');
			$("#moneyTypeInput").val(null);
			$("#mobileAndmoney").attr("style", "display:none;")
			$("#errorMoneyType").attr("style", "display: none;color: red;");
			
			var moneyType = $("#moneyType").val();
			if (value == 'you') {
				$("#groupCountIpt").val(null);
				
				$("#loan").attr("style", "display:block;")
				$("#groupCountDiv").attr("style", "display:none;")
				$("#groupDetail").attr("style", "display:none;")
				$("#percentageOption").show();
				//控制用户开始--结束(显示)
				$("#minmax").attr("style", "display:block;")
			} else {
				$("#loanId").val(null);
				$("#min").val(null);
				$("#max").val(null);
				$("#percentageOption").hide();
				$("#loan").attr("style", "display:none;")
				$("#groupCountDiv").attr("style", "display:block;")
				$("#groupDetail").attr("style", "display:block;")
			
				//控制用户开始--结束(显示)
				$("#minmax").attr("style", "display:none;")
			}
		}

		
												
		//拼接输入框
		function groupCount() {
			var groupCount = $("#groupCountIpt").val();
			$str = '';
			$("#groupDetail tr").remove();
			$("#groupDetail a").remove();
			if($.trim(groupCount) == ''){
				$("#mobileAndmoney").attr("style", "display:none;");
			}else{
				$("#mobileAndmoney").attr("style", "display:block;");
			}
			var re = /^[1-9]+[0-9]*]*$/;
			if(re.test(groupCount)){
				$("#errorGroupCount").attr("style", "display:none;color: red;");
				for ( var i = 0; i < groupCount; i++) {
					//<![CDATA[
					$str += "<tr>";
					$str += "<td id=\"td"+i+"\">";
					$str += "用户"
							+ (i + 1)
							+ ":<input placeholder=\"手机号\"    onblur=\"queryUser("+i+")\" style=\"width:150px\" type=\"text\" id="+i+" name=\"userMobileNum" + i + "\"/>"
					$str += "</td>";
					
					$str += "<td>";
					$str +=	"<input placeholder=\"金额\" onblur=\"verifyMoney("+i+")\"     type=\"text\" name=\"money" + i + "\" id=\"money" + i + "\"/>"
					$str += "</td>";
					
					$str += "<tr id=\"tr"+i+"\">";
					$str += "<td>";
					$str += "<span style=\"display:none;font-size:14px;color:red\" id=\"span"+i+"\"><p></p></span>"
					$str += "</td>";
					
					$str += "<td>";
					$str += "<span style=\"display:none;font-size:14px;color:red\" id=\"spann"+i+"\"><p></p></span>"
					$str += "</td>";
					$str += "</tr>";
					//]]> 
				}
				$("#groupDetail").append($str);
				$("#groupDetailTable").attr("style", "display:block;");
			}else{
				$("#errorGroupCount").attr("style", "display:block;color: red;");
				$("#mobileAndmoney").attr("style", "display:none;")
				$("#groupDetailTable").attr("style", "display:none;color: red;");
			}
		}

		function mobArray(mobile,array){
			for ( var int = 0; int < array.length; int++) {
				var array_element = array[int];
				if(mobile == array_element){
					return true
				}
			}
		}
		
		
		
		//ajax提交表单数据
		function sub() {
			var booleanName = true;
			var booleanDescription = true;
			var booleanMessageContent = true;
			var booleanGroupCount = true;
			var booleanMoneyType = true;
			var booleanItemType = true;
			var booleanItemFlow = true;
			
			var name = $("#name").val();
			
			if($.trim(name).length <= 0){
				$("#errorName").attr("style", "display: block;color: red;");
				booleanName = false;
			}else{
				$("#errorName").attr("style", "display: none;");
			}
			
			var description = $("#description").val();
			if($.trim(description).length <= 0){
				$("#errorDescription").attr("style", "display: block;color: red;");
				booleanDescription = false;
			}else{
				$("#errorDescription").attr("style", "display: none;");
			}
		
			var messageContent = $("#messageContent").val();
			if($.trim(messageContent).length <= 0){
				$("#errorMessageContent").attr("style", "display: blcok;color: red;");
				booleanMessageContent = false;
			}else{
				$("#errorMessageContent").attr("style", "display: none;");
			}
			   
			var moneyType = $("#moneyType1").val();
			if(moneyType == ''){
				$("#errorMoneyType").attr("style", "display: block;color: red;");
				booleanMoneyType = false;
			}else{
				$("#errorMoneyType").attr("style", "display: none;");
			}
			
			var itemType = $("#itemType").val();
			if(itemType == ''){
				$("#errorItemType").attr("style", "display: block;color: red;");
				booleanItemType = false;
			}else{
				$("#errorItemType").attr("style", "display: none;");
			}

			var itemFlow = $("#itemFlow").val();
			if(itemFlow == ''){
				$("#errorItemFlow").attr("style", "display: block;color: red; padding-left: 25%;");
				booleanItemType = false;
			}else{
				$("#errorItemFlow").attr("style", "display: none;");
			}
	
			var radioValue = $("input[type='radio']:checked").val();
			
			var isOk = [];
			if(radioValue == 'wu'){
				var reg = /^(\+|-) \d+$/;
	 			var groupCount = $("#groupCountIpt").val();
	 			if(!reg.test(groupCount) && groupCount <= 0){
	 				$("#errorGroupCount").attr("style", "display: block;color: red;");
	 				booleanGroupCount = false;
	 			}else{
	 				var array = [];
	 				//提交表单时验证输入的用户手机号,和金额校验
	 				for(var i = 0; i < groupCount; i++){
	 					var mn = $("#" + i).val();
	 					if(mn == ''){
	 						$("#span"+i).attr("style", "display:block;font-size:14px;color:red");
							$("#span"+i+" p").text("手机号不正确！");
	 						isOk[i] = "手机号为空";
	 						var falg = verifyMoney(i);
		 					if(!falg){
		 						isOk[i] = "金额不正确！";
		 					}
	 						continue;
	 					}
	 					if(mn.length == 11){
	 						if(mobArray(mn,array)){
	 							$("#span"+i).attr("style", "display:block;font-size:14px;color:red");
								$("#span"+i+" p").text("该手机号已经填写过了！");
								isOk[i] = "该手机号已经填写过了！";
	 						}else{
	 							array[i] = mn;
	 							var qu = queryUser(i,'from');
	 							if(!qu){
	 								isOk[i] = "用户不存在！";
	 							}
	 						}
	 						var falg = verifyMoney(i);
		 					if(!falg){
		 						isOk[i] = "金额不正确！";
		 					}
	 					}else{
	 						$("#span"+i).attr("style", "display:block;font-size:14px;color:red");
							$("#span"+i+" p").text("手机号不正确！");
							isOk[i] = "手机号不正确！";
	 					}
	 				}
	 			}
			}else{
				var count = $("#count").val();
				if(count == '' || count <= 0){
					isOk[0] = '奖励总人数不正确';
				}
				for ( var int = 0; int < count; int++) {
					var flagg = verifyMoney(int);
					if(!flagg){
						isOk[int] = "金额不正确！";
					}
				}
			}
			
			if(booleanName && booleanDescription && booleanMessageContent && booleanMoneyType && booleanItemType && booleanItemFlow){
				if(isOk.length == 0) {
					var radio = $("input[name='radio']:checked").val();
					var count = $("#count").val(); 
					if((radio == 'wu' && booleanGroupCount) || (radio == 'you' && count > 0)){
						subFrom();
					}
				}else{
					if (!confirm("当前提交数据有错误,是否提交?(确认提交后错误数据将不会创建)")) {
			            return false;
			        }else{
			        	subFrom();
			        }
				}
			}else{
				return;
			}
		}
		
		function subFrom(){
			if(window.confirm('确认创建奖励项目?')){
				$.ajax({
					cache : false,
					type : "POST",
					url : "<%=request.getContextPath()%>/award/createAward",
					data : $("#form1").serialize(), //要发送的是ajaxFrm表单中的数据
					async : false,
					beforeSend:function(){
						xval=getBusyOverlay('viewport',
						{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
						{color:'blue', size:25, type:'o'});	
					},
					success : function(data) {
						window.clearInterval(xval.ntime); 
						xval.remove();
						if (data == 'ok') {
							alert('创建成功!');
							location = "/award/awardList/unfinance";
						} else if(data == 'exist'){
							alert('创建失败!原因:奖励项目已存在');
						}else{
							if (data.indexOf('N') > -1) {
								$("#errorName").attr("style",
										"display: block;color: red;");
							}
							if (data.indexOf('D') > -1) {
								$("#errorDescription").attr("style",
										"display: block;color: red;");
							}
							if (data.indexOf('MC') > -1) {
								$("#errorMessageContent").attr("style",
										"display: block;color: red;");
							}
							if (data.indexOf('G') > -1) {
								$("#errorGroupCount").attr("style",
										"display: block;color: red;");
							}
							if (data.indexOf('IF') > -1) {
								$("#errorItemFlow").attr("style",
										"display: block;color: red; padding-left: 25%;");
							}
							if (data.indexOf('loanId') > -1) {
								$("#errorLoanId").css("display", "block");
								$("#errorLoanId p").text("借款项目id为空或没有找到对应的借款项目");
							}
							if (data.indexOf('UCMA') > -1) {
								alert('奖励总人数和总钱数为0,创建失败!');
							}
						}
					}
				});
			}
		}
		
		$(function(){
			var radioValue = $("input[type='radio']:checked").val();//取得是否与借款项目有关
			if(radioValue == 'wu'){
				$("#percentageOption").hide();
			}
		});
	</script>

</body>
</html>
