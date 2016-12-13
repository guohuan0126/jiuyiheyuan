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
<style type="text/css">
.jkrxx-title {
	height: 30px;
	font-size: 16px;
	color: #333;
	padding-left: 23px;
}

.jnb-screen{height:40px;line-height:40px;padding-left:23px;}

.jnb-xmxq{width:1295px;border:1px solid #ddd;}
.jnb-xmxq td,.jnb-xmxq th{height:40px;line-height:40px;text-align:center;font-weight:normal;}
.jnb-xmxq th{background-color:#e8e8e8;}
.jnb-zje{overflow:hidden;width:320px;margin:10px 0;}
.jnb-zje label{width:100px;padding:5px 0;text-align:right;float:left}
.jnb-zje input{width:200px;height:17px;padding:5px ; float:right}
</style>
<title>借款项目附加信息</title>
</head>
<body>
	<!-- 配置文件 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
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
					<i class="fa fa-pencil"></i> 借款项目附加信息
				</h2>
			</div>
			<div class="contentpanel">


				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="panel-btns"></div>
						<h4 class="panel-title">借款项目附加信息-金农宝信息</h4>
					</div>

					<h3 class="jkrxx-title">借款项目打包</h3>
<div class="jnb-screen">
        <label>筛选周期</label>
		  <select name="loanterm" id="loanterm">
				<option value="1">1个月</option>
				<option value="2">2个月</option>
				<option value="3">3个月</option>
				<option value="4">4个月</option>
				<option value="5">5个月</option>
				<option value="6">6个月</option>
				<option value="7">7个月</option>
				<option value="8">8个月</option>
				<option value="9">9个月</option>
				<option value="10">10个月</option>
				<option value="11">11个月</option>
				<option value="12">12个月</option>
			</select>
   </div>
   	<form id="form1" method="post" class="form-horizontal" style="padding-left:23px;">
   	<!-- js获取打包的数据 -->
   <div id="packaging">

   </div>
			<div class="form-group" style="margin-top:20px;">
				<label class="col-sm-3 control-label" style="color: red;">借款总金额
					<span class="asterisk">*</span>
				</label>
				<div class="col-sm-3">								
					<input type="text" name="totalMoney" id="totalMoney" width="10px" class="form-control"  value="${loan.totalmoneyStr}" readonly="readonly"/>
					<input name="LoandataIds" id="LoandataIds"  type="hidden" />
				</div>
			</div>
		<div class="form-group">
				<label class="col-sm-3 control-label" style="color: red;">自动投标金额
					<span class="asterisk">*</span>
				</label>
				<div class="col-sm-3">								
					<input type="text" name="autoInvestMoneyTotal"
					id="autoInvestMoneyTotal" width="10px" class="form-control" value="${loan.autoInvestMoneyTotal}"
					 readonly="readonly"/>
								
				</div>
			</div>
		<div class="form-group">
				<label class="col-sm-3 control-label" style="color: red;">借款期限(月)
					<span class="asterisk">*</span>
				</label>
					<div class="col-sm-3">
									<select name="deadline" id="deadline">
										<option value="1">1个月</option>
										<option value="2">2个月</option>
										<option value="3">3个月</option>
										<option value="4">4个月</option>
										<option value="5">5个月</option>
										<option value="6">6个月</option>
										<option value="7">7个月</option>
										<option value="8">8个月</option>
										<option value="9">9个月</option>
										<option value="10">10个月</option>
										<option value="11">11个月</option>
										<option value="12">12个月</option>
									</select>
					</div>
		</div>
					<div class="panel-body">
						<!-- 创建借款表单 -->
					<div style="margin-left:450px;">**********华丽的分割线**********</div>
							<div class="form-group">
								  <input type="hidden" name="loanId" value="${loanId}" /> 
								  <input type="hidden" name="loanType" value="${loanType}" />
							</div>                      

							<div class="form-group">
								<label class="col-sm-3 control-label">借款用途 </label>
								<div class="col-sm-3">
									<input type="text" id="loanApplication" name="loanApplication"
										width="10px" class="form-control" value="${ruralfinance.loanApplication }"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">还款来源 </label>
								<div class="col-sm-3">
									<input type="text" id="repaymentSource" name="repaymentSource"
										width="10px" class="form-control" value="${ruralfinance.repaymentSource }" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">风控提示
									
								</label>
								<div class="col-sm-3">
									<input type="text" name="reminderInfo" id="reminderInfo"
										width="10px" class="form-control" value="${ruralfinance.reminderInfo }"
										/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">风控措施
									
								</label>
								<div class="col-sm-3">
									<input type="text" name="measuresInfo" id="measuresInfo"
										width="10px" class="form-control" value="${ruralfinance.measuresInfo }"
										 />
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">逾期处理方式
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="overdueInfo" 
										id="overdueInfo">${ruralfinance.overdueInfo }</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">其他说明
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="otherInfo"
										id="otherInfo" >${ruralfinance.otherInfo }</textarea>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button"
											onclick="return sub()">保存</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<script type="text/javascript">
	var deadline = '${loan.deadline}';
	$("#loanterm").val(deadline);
	$(function(){

		//totalMoney.value = 1111;
		
	});
	//按项目周期查询打包项目 
findInfo(1,10000,deadline,'${loanId}','default');
$("#loanterm").change( function() {
	  var loanTerm=$("#loanterm").val();
  	  var deadline= $("#loanterm").val();
	  $("#deadline").val(deadline);
	  findInfo(0,10000,loanTerm,'${loanId}','change');
	  $("#totalMoney").attr("value","");
	  $("#autoInvestMoneyTotal").attr("value","");
	
});

	  function findInfo(no,size,loanTerm,loanId,type){	  
		  
		  $.ajax({
			  url:"/agricultural/PackagingLoan",
			  type:"post",
			  data:{pageNo:no,pageSize:size,loanTerm:loanTerm,loan_id:loanId,type:type},
			  dataType:"json",
			  success:function(pageInfo){
             	  var htmlStr = "<table class=\"jnb-xmxq\" cellpadding=\"0\" cellspacing=\"0\">";
             	 /* htmlStr += "<colgroup>";
             	 htmlStr += "<col width=\"4%\" /><col width=\"7%\" /><col width=\"7%\" /><col width=\"9%\" /><col width=\"7%\" />";
             	 htmlStr += "<col width=\"7%\" /><col width=\"7%\" /><col width=\"7%\" /><col width=\"7%\" /><col width=\"7%\" />";
             	 htmlStr += "<col width=\"7%\" /><col width=\"7%\" /><col width=\"7%\" /><col width=\"10%\" />";
             	 htmlStr += "</colgroup>" */;
             	 htmlStr += "<thead style=\"display:block;\">";
            	 htmlStr += "<th width=\"42px\"><input type=\"checkbox\" id=\"checkall\" class=\"choose-all\" money=\""+0+"\"/><label>全选</label></th>";
                 htmlStr += "<th width=\"71px\">借款人</th><th width=\"90px\">手机号</th><th width=\"137px\">身份证号</th><th width=\"162px\">子合同编号</th><th width=\"77px\">子标金额</th> <th width=\"72px\">子标期限</th><th width=\"158px\">父标合同编号</th>";
                 htmlStr += "<th width=\"81px\">合同金额</th><th width=\"79px\">借款金额</th><th width=\"79px\">服务费用</th><th width=\"79px\">借款期限</th> <th width=\"74px\">利率</th><th width=\"92px\">还款方式</th>";                 
                 htmlStr += " </thead><tbody style=\"width:1293px;height:600px;overflow-y:scroll;display:block;\">";                 
				  var arr = pageInfo.list;
				  for(var i in arr){
					  var obj = arr[i];	
		              htmlStr += "<tr>";
					  htmlStr += "<td width=\"42px\"><input type=\"checkbox\" name=\"checkname\" value="+obj.id+" class=\"choose-all\" money=\""+obj.money+"\" />&nbsp;&nbsp;</td>";
					  htmlStr += " <td width=\"71px\">"+obj.username+"</td>";
					  htmlStr += " <td width=\"124px\">"+obj.mobileNum+"</td>";
					  htmlStr += " <td width=\"137px\">"+obj.idCard+"</td>";
					  htmlStr += " <td width=\"162px\">"+obj.contractid+"</td>";
					  htmlStr += " <td width=\"77px\">"+obj.money+"</td>";
					  htmlStr += " <td width=\"72px\">"+obj.loan_term+"个月</td>";
					  htmlStr += " <td width=\"158px\">"+obj.parentContractId+"</td>";
					  htmlStr += " <td width=\"81px\">"+obj.parentMoney+"</td>";
					  htmlStr += " <td width=\"79px\">"+obj.loanMoney+"</td>";
					  htmlStr += " <td width=\"79px\">"+obj.serviceMoney+"</td>";
					  htmlStr += " <td width=\"79px\">"+obj.parentLoanTerm+"个月</td>";
					  htmlStr += " <td width=\"74px\">"+obj.rate+"</td>";
					  htmlStr += " <td width=\"103px\">"+obj.repay_type+"</td>";
					  htmlStr += " </tr>"; 					  
				  }
				  htmlStr += "</tbody></table>";  
				  $("#packaging").html(htmlStr);  //表格
				  $(":checkbox").change(function(){
	            	  moneychange();
	              });
				  function checkLoan(){
						var arr = new Array();
						$("input:checkbox[name=checkname]:checked").each(function(i){						
							  arr.push($(this).val());
						}); 
						return arr;
					}
				  function moneychange(){
					  var money = 0;
	            	  $(":checkbox").each(function(){
	            		  if($(this).is(':checked')){
	            			  money += parseFloat($(this).attr("money"));
	            		  }
	            	  });        	 
	            	  $("#totalMoney").attr("value",parseFloat(money));
					  $("#autoInvestMoneyTotal").attr("value",(parseFloat(money*0.5)).toFixed(2));
					  $("#LoandataIds").attr("value",checkLoan());
					  
				  }
	              $("#checkall").click(function(){ 
						 if(this.checked){ 
						        $("input:checkbox[name=checkname]").attr('checked', true);
						    }else{ 
						        $("input:checkbox[name=checkname]").attr('checked', false);
						    } 
						 moneychange();
				  });
			  }
		  });
		  
	  }
	 
	  var deadline= $("#loanterm").val();
	  $("#deadline").attr("value",deadline);
	  var regu =/^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
	  var re = new RegExp(regu);
	  function checkMoney(){
			var totalMoney = $("#totalMoney").val();
			totalMoney = parseFloat(totalMoney);
			if (!re.test(totalMoney) || totalMoney <= 0) {
				alert("借款总金额不正确，请勾选打包项目");
				return false;
			}else{				
				return true;
			}
	  }

	  function checkautoMoney(){
			var totalMoney = $("#totalMoney").val();
			totalMoney = parseFloat(totalMoney);
			var autoInvestMoneyTotal = $("#autoInvestMoneyTotal").val();
			autoInvestMoneyTotal = parseFloat(autoInvestMoneyTotal);			
			if (!re.test(autoInvestMoneyTotal) || autoInvestMoneyTotal < 0) {
				alert("自动投标上限金额不正确");
				return false;
			}else if(autoInvestMoneyTotal > totalMoney){
				alert("自动投标上限金额不能大于借款总金额");	
				return false;
			}else {
				return true;
			}
	  }
			
	
		function sub(){			
		  if(checkMoney()&& checkautoMoney()){
			if(window.confirm('确认操作金农宝项目附加信息?')){
				$.ajax({
					cache : false,
					type : "POST",
					dataType : 'json',
					url : "<%=request.getContextPath()%>/loan/createLoanDetail",
							data : $("#form1").serialize(), //要发送的是ajaxFrm表单中的数据
							async : false,
							beforeSend : function() {
								xval = getBusyOverlay(
										'viewport',
										{
											color : 'black',
											opacity : 0.3,
											text : '正在处理，请稍后...',
											style : 'text-shadow: 0 0 3px black;font-size:18px;'
										}, {
											color : 'blue',
											size : 25,
											type : 'o'
										});
							},
							success : function(data) {
								window.clearInterval(xval.ntime);
								xval.remove();
								var dataObj = eval(data);
								if (dataObj.status == 'ok') {
									alert('附加信息创建成功!');
									location = "/loan/loanList";
								} else if (dataObj.status == 'fail') {
									if (dataObj.error == 'loanIdNULL') {
										alert('创建失败!借款项目id为空');
									} else if (dataObj.error == 'loanNULL') {
										alert('创建失败!借款项目为空');
									} else if (dataObj.error == 'loanTypeNULL') {
										alert('创建失败!借款类型不正确');
									}else{
										alert('创建失败!请重新操作');
									}
								} else if (dataObj.status == 'updateOk') {
									alert('附加信息编辑成功!');
									location = "/loan/loanList";
								}
							}
						});
			}
		  }else{
			  return false;
		  }
		}
	</script>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/dropzone.css" />
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/bootstrap-editable.css">
	<link type="text/css"
		href="${pageContext.request.contextPath}/css/jquery-ui-1.8.17.custom.css"
		rel="stylesheet" />
	<link type="text/css"
		href="${pageContext.request.contextPath}/css/jquery-ui-timepicker-addon.css"
		rel="stylesheet" />

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-1.8.17.custom.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>

</body>
</html>
