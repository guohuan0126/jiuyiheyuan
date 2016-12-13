<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>添加活期宝资产</title>
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
	width: 280px;
}

.ui-datepicker-next {
	background: url(../../images/calendar-arrow.png) no-repeat 10px 5px;
}

.ui-datepicker-prev {
	background: url(../../images/calendar-arrow.png) no-repeat 8px -80px;
}

.ui-datepicker table {
	color: #ffffff;
}
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
/* 活期宝赎回资产 */
.cfzc-dj{text-decoration:underline;color:#0070C1;cursor:pointer;font-size:14px;}
.cfzc-tc h2{font-size:16px;}
.cfzc-tc{font-family:"微软雅黑";background-color:#fff;width:935px;height:440px;padding:10px 0 20px 20px;border:2px solid #4E81BD; position:absolute;top:475px;left:200px; }
.cfzc-tc table{width:935px;}
.cfzc-tc thead{display:block;width:935px;height:50px;}
.cfzc-tc tbody{display:block;width:910px;height:275px;overflow-y:scroll;}
.cfzc-tc th{background-color:#E7E7E7;height:45px;line-height:45px;font-weight:normal;font-size:14px;}
.cfzc-tc th label{font-size:11px;}
.cfzc-tc tbody td{text-align:center;font-size:13px;height:40px;line-height:40px;}
.cfzc-tc td:nth-child(1),.cfzc-tc th:nth-child(1){text-align:left;width:50px;}
.cfzc-tc td:nth-child(2),.cfzc-tc th:nth-child(2){width:100px;}
.cfzc-tc td:nth-child(3),.cfzc-tc th:nth-child(3){width:110px;}
.cfzc-tc td:nth-child(4),.cfzc-tc th:nth-child(4){width:80px;}
.cfzc-tc td:nth-child(5),.cfzc-tc th:nth-child(5){width:90px;}
.cfzc-tc td:nth-child(6),.cfzc-tc th:nth-child(6){width:80px;}
.cfzc-tc td:nth-child(7),.cfzc-tc th:nth-child(7){width:180px;}
.cfzc-tc td:nth-child(8),.cfzc-tc th:nth-child(8){width:110px;}
.cfzc-tc td:nth-child(9),.cfzc-tc th:nth-child(9){width:110px;}
.cfzc-but{width:935px;text-align:center;}
.cfzc-bc,.cfzc-qx{display:inline-block;border:none;width:110px;height:40px;-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;line-height:40px;background-color:#4E81BD;color:#fff;font-size:14px;text-align:center;cursor:pointer;}
.cfzc-qx{background-color:#A7A7A7;margin-left:20px;}
</style>
</head>
<body>
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
					<i class="fa fa-pencil"></i><c:choose>
						<c:when  test="${tp == 'add'}">
							添加活期宝资产
						</c:when>
						<c:otherwise>
							编辑活期宝资产
						</c:otherwise>
					</c:choose> 
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form1" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title"><c:choose>
					<c:when  test="${tp == 'add'}">
							添加活期宝资产
						</c:when>
						<c:otherwise>
							编辑活期宝资产
						</c:otherwise>
					</c:choose> </h4>
						</div>
						<div class="panel-body">
							<div class="form-group show">
								<label class="col-sm-3 control-label">编号</label>
								<div class="col-sm-3">
									<input type="text" name="id" width="10px" class="form-control"
										placeholder="编号 " value="${record.id}" readonly/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">金额 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="money" id="money" width="10px"
										class="form-control" placeholder="金额"
										onChange="checkMoney(this.value);" value="<fmt:formatNumber
												value="${record.money}" groupingUsed="false"
												maxFractionDigits="3"/>" readonly />
									<div id="errorMoney" style="display: none;">请输入金额</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">预计执行时间</label>
								<div class="col-sm-2">
									<input type="text" name="expect" id="expect"
										class="form-control ui_timepicker" placeholder="预计执行时间" 
										value="<fmt:formatDate value="${record.expectTime}" pattern="yyyy-MM-dd HH:mm:ss"/> "/>
								</div>
								<script type="text/javascript">
									$(".ui_timepicker").datetimepicker({
										showSecond : true,
										timeFormat : 'hh:mm:ss',
										stepHour : 1,
										stepMinute : 1,
										stepSecond : 1
									});
								</script>
							</div>
							<div class="form-group show">
								<label class="col-sm-3 control-label">最后更新时间</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" value="<fmt:formatDate value="${record.begintime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly/>
								</div>
							</div>
							<div class="form-group show">
								<label class="col-sm-3 control-label">结束时间</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" value="<fmt:formatDate value="${record.endtime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly/>
								</div>
							</div>
							<div class="form-group show">
								<label class="col-sm-3 control-label">最后更新人 </label>
								<div class="col-sm-3">
									<input type="text" name="borrower" width="10px"
										class="form-control" value="${record.operateorid}" readonly/>
								</div>
							</div>
						</div>
						 <input name="LoandataIds" id="LoandataIds" type="hidden" />
						<input name="totalMoney" id="totalMoney2" type="hidden" />
						<div style="font-size: 16px;margin-left: 23px;">
							<input type="checkbox" id="checkxutou" class="choose-all" name="xutou"/>&nbsp;截止&nbsp;${nowdate}&nbsp;&nbsp;转让资产总金额：<span id="newTransferMoney"></span>
                            &nbsp;&nbsp;&nbsp;&nbsp;
<span class="cfzc-dj">拆分资产</span>
<div class="cfzc-tc" style="display:none">
<h2>本次开放已转让资产：<span id="transferMoney">￥</span></h2>
<table cellpadding="0" cellspacing="0" border="0">
    <thead>
        <tr>
             <th><input type="checkbox" id="transfercheckall" transfermoney="0" name="transfer"/> <label>全选</label></th>
             <th>转让金额</th>
             <th>项目名称</th>
             <th>项目类型</th>
             <th>借款人</th>
             <th>借款期限</th>
             <th>身份证号</th>
             <th>开始时间</th>
             <th>结束时间</th>
        </tr>
    </thead>
    <tbody>
     <c:forEach items="${transferLoan}" var="list" varStatus="status">
        <tr>     
            <td><input type="checkbox" name="checktransfer" value="${list.id}" transfermoney="${list.openAmount}" /> </td>
             <td>${list.openAmount}</td>
             <td>${list.loanName}</td>
             <td>${list.loanType}</td>
             <td>${list.borrower}</td>
             <td><c:if test="${list.operationType eq '天'}"> ${list.day }</c:if>
				<c:if test="${list.operationType eq '月'}"> ${list.month}个</c:if>												
				${list.operationType}</td>
             <td>${list.idCard}</td>
            <td><fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd"/> </td>
			<td><fmt:formatDate value="${list.finishTime}" pattern="yyyy-MM-dd"/></td>
        </tr>
        </c:forEach>
    </tbody>
</table>
  <div class="cfzc-but">
           <input type="button" value="保存" class="cfzc-bc" />
           <input type="button" value="取消" class="cfzc-qx" />
  </div>
</div>	 
        <input name="transfertotalMoney" id="transfertotalMoney" type="hidden" />		
        <input name="TransferIds" id="TransferIds" type="hidden" />		
		<input type="hidden" name="attornMoney"  id="attornMoney"/>
						</div>
						<div id="packaging">
								<table class="jnb-xmxq" cellpadding="0" cellspacing="0">
									<thead style="display:block;">
										<th width="110px"><input type="checkbox" id="checkall"
											class="choose-all" money="0" name="newtreasure"/> <label>全选</label></th>
										<th width="61px">借款金额</th>
										<th width="80px">项目类型</th>
										<th width="117px">项目状态</th>
										<th width="80px">借款人</th>
										<th width="67px">借款期限</th>
										<th width="61px">婚姻状况</th>
										<th width="180px">身份证号</th>
										<th width="180px">开始时间</th>
										<th width="180px">结束时间</th>
										<th width="150px">所在地</th>
										<th width="150px">借款用途</th>
										<th width="150px">还款来源</th>
									</thead>
									<tbody
										style="width:1648;height:477px;overflow-y:scroll;display:block;">
										
										<c:forEach items="${list}" var="list"
											varStatus="status">
											<tr>
												<td width="110px"><input type="checkbox"
													name="checkname" value="${list.id}" class="choose-all"
													money="${list.totalMoney}" />&nbsp;&nbsp;</td>
												<td width="61px">${list.totalMoney}</td>
												<td width="80px">${list.loanType}</td>
												<td width="117px">
												<c:if test="${list.loanStatus=='repay'}">还款中</c:if>
												<c:if test="${list.loanStatus=='finish'}">已完成</c:if>
												</td>
												<td width="80px">${list.borrower}</td>
												<td width="67px">
												<c:if test="${list.operationType eq '天'}"> ${list.day }</c:if>
												<c:if test="${list.operationType eq '月'}"> ${list.month}个</c:if>												
												${list.operationType}</td>
												<td width="61px">
													<c:if test="${list.maritalStatus==0}">未婚</c:if>
													<c:if test="${list.maritalStatus==1}">已婚</c:if>
													<c:if test="${list.maritalStatus==2}">离异</c:if>
													<c:if test="${list.maritalStatus==3}">丧偶</c:if>
												</td>
												<td width="180px">${list.idCard}</td>
												<td width="180px"><fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
												<td width="180px"><fmt:formatDate value="${list.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td width="150px">${list.location}</td>
												<td width="150px">${list.borrowingPurposes}</td>
												<td width="150px">${list.sourceOfRepayment}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button" onclick="sub()">保存</button>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" onclick="reset()"
											class="btn btn-default">重置</button>
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
		var type = '${tp}';
		if (type == 'add') {
			$(".show").addClass("hidden");
		}
		
		$(function() {	
			$(".cfzc-dj").click(function(){
				$(".cfzc-tc").show();
				})
		    $(".cfzc-qx").click(function(){
				$(".cfzc-tc").hide();
				})
		//初始数据
		    $("#transfertotalMoney").attr("value", '0.0');
         	var attornMoney=$("#transfertotalMoney").val();
         	$("#newTransferMoney").html("￥"+$("#transfertotalMoney").val());
         	$("#transferMoney").html("￥"+$("#transfertotalMoney").val());
         	$("#checkxutou").attr("money", "0.0"); 
		function noChoose(){
				var arr1 = $("input:checkbox[name=checktransfer]:checked");	
				var arr2 = $("input:checkbox[name=checkname]:checked");	
				if(arr1.length <= 0)
				{ $("#transfertotalMoney").val("0.0");
				  $("#transferMoney").html("￥0.0");
				  $("#TransferIds").attr("value", "");				
				}
				if(arr2.length <= 0)
				{ $("#totalMoney2").val("0.0");
				$("#LoandataIds").attr("value", "");
				} 	
	  }
		$(".cfzc-bc").click(function(){
			$(".cfzc-tc").hide();
			noChoose();		
			$("#newTransferMoney").html("￥"+$("#transfertotalMoney").val());
			//alert(checktransferLoan());						
			var transfertotalMoney=parseFloat($("#transfertotalMoney").val());
			$("#checkxutou").attr("money", parseFloat(transfertotalMoney).toFixed(2)); 
			moneychange();		
			
			
		})
/* 			var attornMoney='${totalmoney}';
			if(attornMoney>0){
				$("#checkxutou").attr("checked","true");
				$("#money").val(parseFloat(attornMoney).toFixed(2));
			} */
			function checkLoan() {
				var arr = new Array();
				$("input:checkbox[name=checkname]:checked").each(function(i) {
					arr.push($(this).val());
			});
				return arr;
			}
			function moneychange() {
				var money = 0;
				var totalMoney=${record.money};
				var money1=$("#transfertotalMoney").val();
				$("input:checkbox[name=checkname],#checkxutou").each(function() {
					if ($(this).is(':checked')) {
						money += parseFloat($(this).attr("money"));
					}  
				});
				$("#money").val(parseFloat(money+totalMoney).toFixed(2));
				 $("#totalMoney2").attr("value", parseFloat(money).toFixed(2)); 
				$("#LoandataIds").attr("value", checkLoan());
					
			}
			
			
			$("#checkall").click(function() {
				if (this.checked) {
					$("input:checkbox[name=checkname]").attr('checked', true);
				} else {
					$("input:checkbox[name=checkname]").attr('checked', false);
				}
				moneychange();
			});
			//转让资产的全选
			$("#transfercheckall").click(function() {
				if (this.checked) {
					$("input:checkbox[name=checktransfer]").attr('checked', true);
				} else {
					$("input:checkbox[name=checktransfer]").attr('checked', false);
				}				
				trsanferchange();
			});
			//获取转让选中资产的id
			function checktransferLoan() {
				var arr = new Array();
				$("input:checkbox[name=checktransfer]:checked").each(function(i) {
					arr.push($(this).val());
				});
				return arr;
			}
			function trsanferchange() {
				var transfermoney = 0;
				$("input:checkbox[name=checktransfer]").each(function() {
					if ($(this).is(':checked')) {
						transfermoney += parseFloat($(this).attr("transfermoney"));
					}
				});
				$("#transferMoney").html("￥"+parseFloat(transfermoney).toFixed(2));
				$("#TransferIds").attr("value", checktransferLoan());	
				$("#transfertotalMoney").attr("value", parseFloat(transfermoney).toFixed(2));					
			}
			$("input:checkbox[name=checkname],#checkxutou").change(function() {
				moneychange();				
			});
			$("input:checkbox[name=newtreasure]").change(function() {
				moneychange();
			});
			$("input:checkbox[name=checktransfer]").change(function() {
				trsanferchange();
			});
			$("input:checkbox[name=transfer]").change(function() {
				trsanferchange();
			});
		});
		

		function sub() {
			var money = $("#money").val();
			if (money == null || money == '') {
				$('#errorMoney').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}
			var ids = $("#LoandataIds").val();
			var TransferIds = $("#TransferIds").val();
			var arr1 = $("input:checkbox[name=xutou]:checked");
			if(arr1.length > 0){
				$("#attornMoney").val($("#transfertotalMoney").val());
			}else{
				$("#attornMoney").val('0');
			}
			$.ajax({
				type : "POST",
				url : '/demand/record/'+type,
				data : $('#form1').serialize(),// 你的formid
				error : function(e) {
					alert("异常");
				},
				success : function(data) {		
					if (data == 'ok') {
						alert('操作成功');
						window.location = "/demand/procut";
					} else if (data == 'status') {
						alert('有未执行的资产，不能添加');
					} else if(data == 'avlid'){
						alert('此产品已过预计执行时间，不能编辑');
					} else if(data == 'in'){
						alert('有转入中的操作，不能添加');
					} else if(data == 'max'){
						alert('本期未完成，不能添加');
					}else{
						alert('操作失败');
					}
				}
			});
		}
	</script>
</body>
</html>
