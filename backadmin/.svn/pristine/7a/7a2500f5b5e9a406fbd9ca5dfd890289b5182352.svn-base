<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />

<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>农贷手动修改还款计划</title>	
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
					<i class="fa fa-pencil"></i> 农贷手动修改还款计划
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form1" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">手动修改还款计划</h4>
						</div>
						<div class="panel-body">
						<div class="form-group">
								<label class="col-sm-3 control-label">总合同编号 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="contractId" id="contractId" value="${repaymentPlan.contractId}"  width="10px"
										class="form-control" placeholder="总合同编号" readonly="readonly" />
									<input type="hidden" name="id" value="${repaymentPlan.id}">
								</div>
						</div>
						<div class="form-group">
								<label class="col-sm-3 control-label">借款人
								<!-- <span class="asterisk">*</span> --></label>
								<div class="col-sm-3">							
									<input type="text" name="name" id="name" value="${repaymentPlan.name}" width="10px"
										class="form-control" placeholder="借款人"  readonly="readonly"/>
									<!-- <div id="errortotalMoney" style="display: none;">借款金额不能为空</div> -->
								</div>
						</div>
						<div class="form-group">
								<label class="col-sm-3 control-label">当前期数 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="period" id="period" value="第${repaymentPlan.period}期" width="10px"
										class="form-control" placeholder="期"  readonly="readonly"/>
								</div>
						</div>			
						<div class="form-group">
								<label class="col-sm-3 control-label">还款方式 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="repayType" id="repayType" value="${repaymentPlan.repayType}" width="10px"
										class="form-control" placeholder="还款方式"  readonly="readonly"/>
								</div>
						</div>			
						<div class="form-group">
								<label class="col-sm-3 control-label">应还款日期 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="repayDate" id="repayDate" value="<fmt:formatDate value="${repaymentPlan.repayDate}"
												pattern="yyyy-MM-dd" />" width="10px" class="form-control"   readonly="readonly"/>
								</div>
						</div>
						<div class="form-group">
								<label class="col-sm-3 control-label">每月应还金额 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="monthMoney" id="monthMoney" value="${repaymentPlan.monthMoney}" width="10px"
										class="form-control"   readonly="readonly"/>
								</div>
						</div>	
						<div class="form-group">
								<label class="col-sm-3 control-label">应还本金<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="corpus" id="corpus" value="${repaymentPlan.corpus}" width="10px"
										class="form-control"   readonly="readonly"/>
								</div>
						</div>	
						<div class="form-group">
								<label class="col-sm-3 control-label">应还利息<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="intereat" id="intereat" value="${repaymentPlan.intereat}" width="10px"
										class="form-control"   readonly="readonly"/>
								</div>
						</div>
			<div class="form-group">
								<label class="col-sm-3 control-label">手续费<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="serviceFee" id="serviceFee" value="${repaymentPlan.serviceFee}" width="10px"
										class="form-control"  readonly="readonly"/>
								</div>
						</div>
						<div class="form-group">
								<label class="col-sm-3 control-label">明细号<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="detailsNumber" id="detailsNumber" value="${repaymentPlan.detailsNumber}" width="10px"
										class="form-control"   readonly="readonly"/>
								</div>
						</div>
						<!-- 手动修改部分 -->
		              <div class="form-group">
		                <label class="col-sm-3 control-label">实际还款日期<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-1">
									<input type="text" name="operationTime"    id="operationTime"
										class="ui_timepicker" />
								</div>
		                </div>
						<div class="form-group">
								<label class="col-sm-3 control-label">实际还款金额 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="realrepayMoney" id="realrepayMoney"  width="10px"
										class="form-control"   />
								</div>
						</div>	
						<div class="form-group">
								<label class="col-sm-3 control-label">逾期罚息<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="latePenalty" id="latePenalty" value="${repaymentPlan.latePenalty}" width="10px"
										class="form-control"  />
								</div>
						</div>	
		
						<div class="form-group">
								<label class="col-sm-3 control-label">退还手续费<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="returnMoney" id="returnMoney" value="${repaymentPlan.returnMoney}" width="10px"
										class="form-control"  />
								</div>
						</div>	
						<div class="form-group">
								<label class="col-sm-3 control-label">减免本金<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="remitCorpus" id="remitCorpus" value="${repaymentPlan.remitCorpus}" width="10px"
										class="form-control"  />
								</div>
						</div>	
						<div class="form-group">
								<label class="col-sm-3 control-label">减免利息<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="remitIntereat" id="remitIntereat" value="${repaymentPlan.remitIntereat}" width="10px"
										class="form-control"  />
								</div>
						</div>	
						<div class="form-group">
								<label class="col-sm-3 control-label">项目状态 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
								<select class="select2" name="repayStatus" id="repayStatus" >
				                  <option value="finish" selected="selected">还清</option>
				                  <option value="repayfail">还款失败</option>
				                  <option value="unrepay">未还</option>				         
			                   </select>
								</div>
						</div>														                																																														
			             
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" onclick="reset()" class="btn btn-default">重置</button>
								</div>
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

	jQuery(document).ready(function() {		
		$(".ui_timepicker").datetimepicker({
// 			showOn: "button",
// 			buttonImage: "/css/images/icon_calendar.gif",
// 			buttonImageOnly: true,
			showSecond : true,
			timeFormat : 'hh:mm:ss',
			stepHour : 1,
			stepMinute : 1,
			stepSecond : 1,
		});
	});
	 //验证金额，小数点后两位
	 function validateNum(val){
	 var patten = /^[-]?([1-9]{1}[0-9]{0,}(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|\.[0-9]{1,2})$/g;
	     return patten.test(val);
	 }
	function sub(){
		var realrepayMoney=	$("#realrepayMoney").val();
		var latePenalty=	$("#latePenalty").val();
		var contractId=	$("#contractId").val();
		var serviceFee=	$("#serviceFee").val();
		var returnMoney=$("#returnMoney").val();
		var remitCorpus=$("#remitCorpus").val();
		var remitIntereat=$("#remitIntereat").val();
        if(realrepayMoney != ""){
        	if(!validateNum(realrepayMoney)){
        		alert("实际还款金额格式不正确");
            	return false;
        	}       	
        }
        if(latePenalty != ""){
        	if(!validateNum(latePenalty)){
        		alert("逾期罚息金额格式不正确");
            	return false;
        	}       	
        }
        if(serviceFee != ""){
        	if(!validateNum(serviceFee)){
        		alert("手续费金额格式不正确");
            	return false;
        	}       	
        }
        if(returnMoney != ""){
        	if(!validateNum(returnMoney)){
        		alert("退还手续费金额格式不正确");
            	return false;
        	}       	
        }
         if(remitCorpus!= ""){
        	if(!validateNum(remitCorpus)){
        		alert("减免本金格式不正确");
            	return false;
        	}       	
        }
         if(remitIntereat!= ""){
        	if(!validateNum(remitIntereat)){
        		alert("减免利息格式不正确");
            	return false;
        	}       	
        }
		$.ajax({
			type : 'POST',
			url : '/ruralfinance/editRepaymentplan',						
			data:$('#form1').serialize(),
			success:function(data) {			
				if(data == 'update_success'){
					alert("还款计划修改成功");					
					window.location.href="/ruralfinance/repaymentplanList?id="+contractId;				 					
				}else{					
					alert("还款计划修改失败");
				}				
			}
		}); 
	}
	
 </script>

</body>
</html>
