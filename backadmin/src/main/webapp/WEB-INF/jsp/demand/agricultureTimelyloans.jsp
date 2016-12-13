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

.jnb-screen {
	height: 40px;
	line-height: 40px;
	padding-left: 0px;
}

.jnb-xmxq {
	width: 1295px;
	border: 1px solid #ddd;
}

.jnb-xmxq td,.jnb-xmxq th {
	height: 40px;
	line-height: 40px;
	text-align: center;
	font-weight: normal;
}

.jnb-xmxq th {
	background-color: #e8e8e8;
}

.jnb-zje {
	overflow: hidden;
	width: 320px;
	margin: 10px 0;
}

.jnb-zje label {
	width: 100px;
	padding: 5px 0;
	text-align: right;
	float: left
}

.jnb-zje input {
	width: 200px;
	height: 17px;
	padding: 5px;
	float: right
}
</style>
<title>添加活期宝资产</title>
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
					<i class="fa fa-pencil"></i> 添加及时贷活期宝资产
				</h2>
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="panel-btns"></div>
						<h4 class="panel-title">添加及时贷活期宝资产</h4>
					</div>

					<form id="form1" method="post" class="form-horizontal"
						style="padding-left:23px;">
						<div class="jnb-screen">
                        <label style="padding-left:2px;">打包总金额：</label> <span
								id="totalMoney"></span> <input name="LoandataIds"
								id="LoandataIds" type="hidden" />
						</div>

						<!-- js获取打包的数据 -->
						<div id="packaging">
								<table class="jnb-xmxq" cellpadding="0" cellspacing="0">
									<thead style="display:block;">
										<th width="70px"><input type="checkbox" id="checkall"
											class="choose-all" money="0" name="newtreasure"/> <label>全选</label></th>
										<th width="61px">借款人</th>
										<th width="190px">身份证号</th>
										<th width="100px">手机号</th>
										<th width="190px">合同编号</th>
										<th width="80px">合同金额</th>
										<th width="77px">借款金额</th>
										<th width="81px">服务费用</th>
										<th width="50px">借款期限</th>
										<th width="90px">利率</th>
										<th width="150px">放款时间</th>
										<th width="90px">综合费率</th>
										<th width="60px">还款方式</th>
										<th width="50px">年收入</th>
										<th width="150px">借款用途</th>
										<th width="150px">还款来源</th>
									</thead>
									<tbody
										style="width:1728;height:600px;overflow-y:scroll;display:block;">
										<c:forEach items="${loanerinfo}" var="list"
											varStatus="status">
											<tr>
												<td width="70px"><input type="checkbox"
													name="checkname" value="${list.id}" class="choose-all"
													money="${list.money}" />&nbsp;&nbsp;</td>
												<td width="61px">${list.name}</td>
												<td width="190px">${list.idCard}</td>
												<td width="100px">${list.mobileNumber}</td>
												<td width="190px">${list.contractId}</td>
												<td width="80px">${list.money}</td>
												<td width="77px">${list.loanMoney}</td>
												<td width="81px">${list.serviceMoney}</td>
												<td width="50px">${list.loanTerm}月</td>
												<td width="60px">${list.rate}</td>
												<td width="150px"><fmt:formatDate value="${list.giveMoneyTime}" pattern="yyyy-MM-dd"/></td>
												<td width="50px">${list.compositeInteresRate}</td>
												<td width="60px">${list.repayType}</td>
												<td width="90px">${list.annualIncome}</td>
												<td width="150px">${list.loanApplication}</td>
												<td width="150px">${list.repaymentSource}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						<div style="padding-bottom:10px;">
							<button id="savebut" class="btn btn-primary" type="button"
								onclick="return sub()" style="margin:10px 600px 0;">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		</div>
	</section>

	<script type="text/javascript">
	$(function(){
		//totalMoney.value = 1111;
	});
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
   	  $("#totalMoney").html(parseFloat(money).toFixed(2));			
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
     var regu =/^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
	  var re = new RegExp(regu);
	  function checkMoney(){
			var totalMoney = $("#totalMoney").html();
			totalMoney = parseFloat(totalMoney).toFixed(2);
			if (!re.test(totalMoney) || totalMoney <= 0) {
				alert("请勾选要添加的资产");
				return false;
			}else{				
				return true;
			}
	  }

	function sub(){	
		var ids= $("#LoandataIds").val();
	    //alert(ids);
		if(checkMoney()){			
			if(confirm('确认对项目进行批量添加资产吗?')){	
				 $("#savebut").attr("disable",true); 
				$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/demand/addLoanerinfos",						
					data: {"ids":ids},
					beforeSend:function(){
						xval=getBusyOverlay('viewport',
						{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
						{color:'blue', size:25, type:'o'});	
					},
					error : function(e) {
						alert("异常");
						window.clearInterval(xval.ntime); 
						xval.remove();
					},
					success:function(data) {
						window.clearInterval(xval.ntime); 
						xval.remove();
						if(data=='success'){
							alert("资产批量添加成功");
							 $("#savebut").attr("disable",false); 
							window.location.href="/demand/loanList";
						}else{
							alert("资产批量添加失败");
							 $("#savebut").attr("disable",false); 
							window.location.href="/demand/addTimelyAgriculture";
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
